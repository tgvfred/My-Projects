//package com.disney.composite.api.seatedEventsComponentService;
//
//import org.testng.annotations.BeforeMethod;
//import org.testng.annotations.Parameters;
//import org.testng.annotations.Test;
//
//import com.disney.api.soapServices.seatedEventsComponentService.operations.Book;
//import com.disney.utils.Randomness;
//import com.disney.utils.Regex;
//import com.disney.utils.TestReporter;
//import com.disney.utils.dataFactory.guestFactory.HouseHold;
//
//public class TestBook_old {
//	// Defining global variables
//	protected ThreadLocal<String> testName = new ThreadLocal<String>();
//	protected ThreadLocal<String> environment = new ThreadLocal<String>();
//	protected ThreadLocal<String> bookingDate = new ThreadLocal<String>();
//	protected ThreadLocal<Book> book = new ThreadLocal<Book>();
//	protected ThreadLocal<HouseHold> hh = new ThreadLocal<HouseHold>();
//	
//	@BeforeMethod(alwaysRun = true)
//	@Parameters({ "environment" })
//	public void setup(String environment) {this.environment.set(environment);}
//
//	@Test(groups = {"api", "regression", "dining", "eventDiningService"})
//	public void testBook(){
//		TestReporter.logScenario("Book Cirque Reservation");
//		testName.set(new Object() {}.getClass().getEnclosingMethod().getName());
//		bookingDate.set(Randomness.generateCurrentXMLDatetime(45));
//		generateHousehold();
//		
//		TestReporter.logStep("Book Seated Events Component Service");
//		book.set(new Book(environment.get(), "Main"));
//		book.get().setPrimaryGuestAddress1(hh.get().primaryGuest().primaryAddress().getAddress1());
//		book.get().setPrimaryGuestAddressCountry(hh.get().primaryGuest().primaryAddress().getCountry());
//		book.get().setPrimaryGuestAddressPostalCode(hh.get().primaryGuest().primaryAddress().getZipCode());
//		book.get().setPrimaryGuestFirstName(hh.get().primaryGuest().getFirstName());
//		book.get().setPrimaryGuestLastName(hh.get().primaryGuest().getLastName());
//		book.get().setPrimaryGuestPhoneNumber(hh.get().primaryGuest().primaryPhone().getNumber());
//		book.get().setServiceStartDate(bookingDate.get());
//		book.get().sendRequest();
//		TestReporter.logAPI(!book.get().getResponseStatusCode().equals("200"), "An error occurred during booking.", book.get());
//		TestReporter.assertTrue(Regex.match("[0-9]+", book.get().getReservationNumber()), "The reservation number ["+book.get().getReservationNumber()+"] was not numeric as expected.");
//		TestReporter.assertTrue(Regex.match("[0-9]+", book.get().getTravelPlanId()), "The travel plan ID ["+book.get().getTravelPlanId()+"] was not numeric as expected.");
//	}
//	
//	private void generateHousehold(){
//		hh.set(new HouseHold(Randomness.randomNumberBetween(1, 3)));
//		hh.get().sendToApi(environment.get());
//	}
//}
