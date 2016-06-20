package com.disney.composite.api.seatedEventsComponentService;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.seatedEventsComponentService.operations.Book;
import com.disney.api.soapServices.seatedEventsComponentService.operations.Retrieve;
import com.disney.utils.Randomness;
import com.disney.utils.Regex;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.guestFactory.HouseHold;

public class TestRetrieve {
	// Defining global variables
	protected ThreadLocal<String> testName = new ThreadLocal<String>();
	protected ThreadLocal<String> environment = new ThreadLocal<String>();
	protected ThreadLocal<String> bookingDate = new ThreadLocal<String>();
	protected ThreadLocal<Book> book = new ThreadLocal<Book>();
	protected ThreadLocal<HouseHold> hh = new ThreadLocal<HouseHold>();
	protected ThreadLocal<Retrieve> retrieve = new ThreadLocal<Retrieve>();
	
	@BeforeMethod(alwaysRun = true)
	@Parameters({ "environment" })
	public void setup(String environment) {this.environment.set(environment);}

	@Test(groups = {"api", "regression", "dining", "eventDiningService"})
	public void testRetrieve(){
		TestReporter.logScenario("Book Cirque Reservation and Retrieve the Reservation Information");
		testName.set(new Object() {}.getClass().getEnclosingMethod().getName());
		bookingDate.set(Randomness.generateCurrentXMLDatetime(45));
		generateHousehold();
		bookRes();
		retrieveRes();
	}
	
	private void generateHousehold(){
		hh.set(new HouseHold(Randomness.randomNumberBetween(1, 3)));
		hh.get().sendToApi(environment.get());
	}
	
	private void bookRes(){
		TestReporter.logStep("Book Seated Events Component Service");
		book.set(new Book(environment.get(), "Main"));
		book.get().setPrimaryGuestAddress1(hh.get().primaryGuest().primaryAddress().getAddress1());
		book.get().setPrimaryGuestAddressCountry(hh.get().primaryGuest().primaryAddress().getCountry());
		book.get().setPrimaryGuestAddressPostalCode(hh.get().primaryGuest().primaryAddress().getZipCode());
		book.get().setPrimaryGuestFirstName(hh.get().primaryGuest().getFirstName());
		book.get().setPrimaryGuestLastName(hh.get().primaryGuest().getLastName());
		book.get().setPrimaryGuestPhoneNumber(hh.get().primaryGuest().primaryPhone().getNumber());
		book.get().setServiceStartDate(bookingDate.get());
		book.get().sendRequest();
		TestReporter.logAPI(!book.get().getResponseStatusCode().equals("200"), "An error occurred during booking.", book.get());
	}
	
	private void retrieveRes(){
		TestReporter.logStep("Retrieve Seated Events Component Service");
		retrieve.set(new Retrieve(environment.get(), "Main"));
		retrieve.get().setReservationnumber(book.get().getReservationNumber());
		retrieve.get().sendRequest();
		TestReporter.logAPI(!retrieve.get().getResponseStatusCode().equals("200"), "An error occurred during retrieval.", retrieve.get());
		
		TestReporter.assertTrue(new Regex().match("[0-9]+", retrieve.get().getPrimaryGuestGuestId()), "The primary guest ID ["+retrieve.get().getPrimaryGuestGuestId()+"] is not numeric as expected.");
		TestReporter.assertTrue(new Regex().match("[0-9]+", retrieve.get().getPrimaryGuestPartyId()), "The primary guest party ID ["+retrieve.get().getPrimaryGuestPartyId()+"] is not numeric as expected.");
		TestReporter.assertTrue(new Regex().match("[0-9]+", retrieve.get().getReservationNumber()), "The reservation number ["+retrieve.get().getReservationNumber()+"] is not numeric as expected.");
		TestReporter.assertEquals(retrieve.get().getReservationStatus(), "Booked", "The reservation status ["+retrieve.get().getReservationStatus()+"] was not 'Booked' as expected.");
		TestReporter.assertTrue(new Regex().match("[0-9]+", retrieve.get().getTravelPlanId()), "The travel plan ID ["+retrieve.get().getTravelPlanId()+"] is not numeric as expected.");
	}
}
