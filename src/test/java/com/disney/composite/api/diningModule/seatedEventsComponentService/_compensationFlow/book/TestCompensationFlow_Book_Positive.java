package com.disney.composite.api.diningModule.seatedEventsComponentService._compensationFlow.book;

import org.testng.annotations.Test;

import com.disney.api.soapServices.diningModule.seatedEventsComponentService.operations.Book;
import com.disney.composite.BaseTest;
import com.disney.utils.Randomness;
import com.disney.utils.Regex;
import com.disney.utils.TestReporter;

public class TestCompensationFlow_Book_Positive extends BaseTest{

	@Test(groups = {"api", "regression", "dining", "seatedEventsComponentService", "compensation"})
	public void testCompensationFlow_Book_Positive(){
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
		TestReporter.logAPI(!book.getResponseStatusCode().equals("200"), "An error occurred during booking: " + book.getFaultString(), book);
		TestReporter.assertTrue(Regex.match("[0-9]+", book.getReservationNumber()), "Verify the reservation number ["+book.getReservationNumber()+"] is numeric.");
		TestReporter.assertTrue(Regex.match("[0-9]+", book.getTravelPlanId()), "Verify the travel plan ID ["+book.getTravelPlanId()+"] is numeric.");
	}
}
