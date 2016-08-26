//package com.disney.composite.api.diningModule.seatedEventsComponentService._compensationFlow.retrieve;
//
//import org.testng.annotations.BeforeClass;
//import org.testng.annotations.Parameters;
//import org.testng.annotations.Test;
//
//import com.disney.api.soapServices.diningModule.seatedEventsComponentService.operations.Book;
//import com.disney.api.soapServices.diningModule.seatedEventsComponentService.operations.Retrieve;
//import com.disney.composite.BaseTest;
//import com.disney.utils.Randomness;
//import com.disney.utils.Regex;
//import com.disney.utils.TestReporter;
//import com.disney.utils.dataFactory.guestFactory.HouseHold;
//
//public class TestCompensationFlow_Retrieve_Positive extends BaseTest{
//	String bookingDate = Randomness.generateCurrentXMLDatetime(45);
//	String TPS_ID;
//	
//	@Override
//	@BeforeClass(alwaysRun = true)
//	@Parameters({ "environment" })
//	public void setup(String environment){
//		this.environment = environment;
//		hh = new HouseHold(1);
//
//		TestReporter.logStep("Book Seated Events Component Service");
//		Book book = new Book(environment, "Main");
//		book.setPrimaryGuestAddress1(hh.primaryGuest().primaryAddress().getAddress1());
//		book.setPrimaryGuestAddressCountry(hh.primaryGuest().primaryAddress().getCountry());
//		book.setPrimaryGuestAddressPostalCode(hh.primaryGuest().primaryAddress().getZipCode());
//		book.setPrimaryGuestFirstName(hh.primaryGuest().getFirstName());
//		book.setPrimaryGuestLastName(hh.primaryGuest().getLastName());
//		book.setPrimaryGuestPhoneNumber(hh.primaryGuest().primaryPhone().getNumber());
//		book.setServiceStartDate(bookingDate);
//		book.sendRequest();
//		TestReporter.logAPI(!book.getResponseStatusCode().equals("200"), "An error occurred during booking: " + book.getFaultString(), book);
//		TPS_ID = book.getReservationNumber();
//	}
//
//	@Test(groups = {"api", "regression", "dining", "seatedEventsComponentService", "compensation"})
//	public void testCompensationFlow_Retrieve_Positive(){
//		TestReporter.logStep("Retrieve Seated Events Component Service");
//		Retrieve retrieve = new Retrieve(environment, "Main");
//		retrieve.setReservationNumber(TPS_ID);
//		retrieve.sendRequest();
//		TestReporter.logAPI(!retrieve.getResponseStatusCode().equals("200"), "An error occurred during retrieval.", retrieve);
//		
//		TestReporter.assertEquals(TPS_ID, retrieve.getReservationNumber(), "Verify the actual reservation number ["+retrieve.getReservationNumber()+"] matches the expected reservation number ["+TPS_ID+"].");
//		TestReporter.assertTrue(Regex.match("[0-9]+", retrieve.getPrimaryGuestGuestId()), "Verify the primary guest ID ["+retrieve.getPrimaryGuestGuestId()+"] is numeric.");
//		TestReporter.assertTrue(Regex.match("[0-9]+", retrieve.getPrimaryGuestPartyId()), "Verify the primary guest party ID ["+retrieve.getPrimaryGuestPartyId()+"] is numeric.");
//		TestReporter.assertTrue(Regex.match("[0-9]+", retrieve.getReservationNumber()), "Verify the reservation number ["+retrieve.getReservationNumber()+"] is numeric.");
//		TestReporter.assertEquals(retrieve.getReservationStatus(), "Booked", "Verify the reservation status ["+retrieve.getReservationStatus()+"] is 'Booked'.");
//		TestReporter.assertTrue(Regex.match("[0-9]+", retrieve.getTravelPlanId()), "Verify the travel plan ID ["+retrieve.getTravelPlanId()+"] is numeric.");
//	}
//}
