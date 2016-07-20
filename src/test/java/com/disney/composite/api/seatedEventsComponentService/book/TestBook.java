package com.disney.composite.api.seatedEventsComponentService.book;

import org.testng.annotations.Test;

import com.disney.api.soapServices.seatedEventsComponentService.operations.Book;
import com.disney.composite.BaseTest;
import com.disney.utils.Randomness;
import com.disney.utils.Regex;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.database.LogItems;

public class TestBook extends BaseTest{

	@Test(groups = {"api", "regression", "dining", "seatedEventsComponentService"})
	public void testBook(){
		TestReporter.logScenario("Test Book");
		String bookingDate = Randomness.generateCurrentXMLDatetime(45);
		Book book = new Book(environment, "Main");
		book.setPrimaryGuestAddress1(hh.primaryGuest().primaryAddress().getAddress1());
		book.setPrimaryGuestAddressCountry(hh.primaryGuest().primaryAddress().getCountry());
		book.setPrimaryGuestAddressPostalCode(hh.primaryGuest().primaryAddress().getZipCode());
		book.setPrimaryGuestFirstName(hh.primaryGuest().getFirstName());
		book.setPrimaryGuestLastName(hh.primaryGuest().getLastName());
		book.setPrimaryGuestPhoneNumber(hh.primaryGuest().primaryPhone().getNumber());
		book.setServiceStartDate(bookingDate);
		book.sendRequest();
		TestReporter.logAPI(!book.getResponseStatusCode().equals("200"), "An error occurred during booking.", book);
		TestReporter.assertTrue(Regex.match("[0-9]+", book.getReservationNumber()), "Verify the reservation number ["+book.getReservationNumber()+"] is numeric.");
		TestReporter.assertTrue(Regex.match("[0-9]+", book.getTravelPlanId()), "Verify the travel plan ID ["+book.getTravelPlanId()+"] is numeric.");
		
		LogItems logItems = new LogItems();
		logItems.addItem("SeatedEventsComponentService", "book", false);	
		logItems.addItem("PartyIF", "createAndRetrieveParty", false);
		logItems.addItem("GuestServiceV1", "create", false);
		logItems.addItem("PartyIF", "updateExternalPartyAndLocatorId", false);
		logItems.addItem("TravelPlanServiceV3", "create", false);
		logItems.addItem("TravelPlanServiceV3SEI", "create", false);	
		validateLogs(book, logItems);
	}
}