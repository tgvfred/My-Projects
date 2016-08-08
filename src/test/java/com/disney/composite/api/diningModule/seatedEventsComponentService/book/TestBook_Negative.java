package com.disney.composite.api.diningModule.seatedEventsComponentService.book;

import org.testng.annotations.Test;

import com.disney.api.soapServices.applicationError.ApplicationErrorCode;
import com.disney.api.soapServices.applicationError.CoreResErrorCode;
import com.disney.api.soapServices.applicationError.DiningErrorCode;
import com.disney.api.soapServices.applicationError.LiloPartyErrorCode;
import com.disney.api.soapServices.applicationError.LiloSystemErrorCode;
import com.disney.api.soapServices.core.BaseSoapCommands;
import com.disney.api.soapServices.diningModule.seatedEventsComponentService.operations.Book;
import com.disney.composite.BaseTest;
import com.disney.utils.Randomness;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.database.LogItems;

public class TestBook_Negative extends BaseTest{
	protected String bookingDate = Randomness.generateCurrentXMLDatetime(45);
	
	@Test(groups = {"api", "regression", "dining", "seatedEventsComponentService", "negative"})
	public void missingPrimaryGuest(){
		TestReporter.logScenario("Missing Primary Guest");
		Book book = book();
		book.setRequestNodeValueByXPath("/Envelope/Body/book/bookCirqueRequest/primaryGuest", BaseSoapCommands.REMOVE_NODE.toString());
		sendRequestValidateLogs(book, "Travel Plan Guest is required : TRAVEL PLAN GUEST REQUIRED", DiningErrorCode.TRAVEL_PLAN_GUEST_REQUIRED);
	}
	@Test(groups = {"api", "regression", "dining", "seatedEventsComponentService", "negative"})
	public void missingPrimaryGuestFirstName(){
		TestReporter.logScenario("Missing Primary Guest First Name");
		Book book = book();
		book.setPrimaryGuestFirstName(BaseSoapCommands.REMOVE_NODE.toString());
		sendRequestValidateLogs(book, "Travel Plan Guest is required : TRAVEL PLAN GUEST REQUIRED", DiningErrorCode.TRAVEL_PLAN_GUEST_REQUIRED);
	}
	@Test(groups = {"api", "regression", "dining", "seatedEventsComponentService", "negative"})
	public void missingPrimaryGuestLastName(){
		TestReporter.logScenario("Missing Primary Guest Last Name");
		Book book = book();
		book.setPrimaryGuestLastName(BaseSoapCommands.REMOVE_NODE.toString());
		sendRequestValidateLogs(book, "Travel Plan Guest is required : TRAVEL PLAN GUEST REQUIRED", DiningErrorCode.TRAVEL_PLAN_GUEST_REQUIRED);
	}
	@Test(groups = {"api", "regression", "dining", "seatedEventsComponentService", "negative"})
	public void missingAdmissionProducts(){
		TestReporter.logScenario("Missing Admission Products");
		Book book = book();
		book.setRequestNodeValueByXPath("/Envelope/Body/book/bookCirqueRequest/admissionProducts", BaseSoapCommands.REMOVE_NODE.toString());
		sendRequestValidateLogs(book, "Unexpected Error occurred : book : Index: 0, Size: 0", LiloSystemErrorCode.UNEXPECTED_ERROR);
	}
	@Test(groups = {"api", "regression", "dining", "seatedEventsComponentService", "negative"})
	public void missingAdmissionServiceStartDate(){
		TestReporter.logScenario("Missing Admission Service Start Date");
		Book book = book();
		book.setServiceStartDate(BaseSoapCommands.REMOVE_NODE.toString());
		sendRequestValidateLogs(book, "Unexpected Error occurred : book : java.lang.NullPointerException", LiloSystemErrorCode.UNEXPECTED_ERROR);
	}
	@Test(groups = {"api", "regression", "dining", "seatedEventsComponentService", "negative"})
	public void missingAdmissionTicketQuantity(){
		TestReporter.logScenario("Missing Admission Ticket Quantity");
		Book book = book();
		book.setTicketQuantity(BaseSoapCommands.REMOVE_NODE.toString());
		sendRequestValidateLogs(book, "Unexpected Error occurred : book : java.lang.NullPointerException", LiloSystemErrorCode.UNEXPECTED_ERROR);
	}
	@Test(groups = {"api", "regression", "dining", "seatedEventsComponentService", "negative"})
	public void missingCommunicationsChannel(){
		TestReporter.logScenario("Missing Communications Channel");
		Book book = book();
		book.setCommunicationChannel(BaseSoapCommands.REMOVE_NODE.toString());
		sendRequestValidateLogs(book, "communication Channel is required : null", DiningErrorCode.COMMUNICATION_CHANNEL_REQUIRED);
	}
	@Test(groups = {"api", "regression", "dining", "seatedEventsComponentService", "negative"})
	public void missingNexusReservationCode(){
		TestReporter.logScenario("Missing Nexus Reservation Code");
		Book book = book();
		book.setNexusReservationCode(BaseSoapCommands.REMOVE_NODE.toString());
		sendRequestValidateLogs(book, "Unexpected Error occurred : The following required parameters are missing from TravelPlanServiceV3.create: travelPlanRequest.salesOrderRequests.externalReferences.value  -- Parent Exception: The following required parameters are missing from TravelPlanServiceV3.create: travelPlanRequest.salesOrderRequests.externalReferences.value", LiloSystemErrorCode.UNEXPECTED_ERROR);
	}
	@Test(groups = {"api", "regression", "dining", "seatedEventsComponentService", "negative"})
	public void missingSalesChannel(){
		TestReporter.logScenario("Missing Sales Channel");
		Book book = book();
		book.setSalesChannel(BaseSoapCommands.REMOVE_NODE.toString());
		sendRequestValidateLogs(book, "Sales Channel is required : null", DiningErrorCode.SALES_CHANNEL_REQUIRED);
	}
	@Test(groups = {"api", "regression", "dining", "seatedEventsComponentService", "negative"})
	public void missingSourceAccountingCenter(){
		TestReporter.logScenario("Missing Source Accounting Center");
		Book book = book();
		book.setSourceAccountingCenter(BaseSoapCommands.REMOVE_NODE.toString());
		sendRequestValidateLogs(book, "SOURCER ACCOUNTING CENTER IS REQUIRED! : Invalid Source Accounting Center#0", CoreResErrorCode.SRC_ACCOUNTING_CENTER_REQUIRED);
	}
	@Test(groups = {"api", "regression", "dining", "seatedEventsComponentService", "negative"})
	public void invalidPrimaryGuestTitle(){
		String title = "Mre.";
		TestReporter.logScenario("Invalid Primary Guest Title");
		Book book = book();
		book.setPrimaryGuestTitle(title);
		sendRequestValidateLogs(book, "Salutation is invalid : Salutation "+title+" is invalid", LiloPartyErrorCode.INVALID_TITLE);
	}
	@Test(groups = {"api", "regression", "dining", "seatedEventsComponentService", "negative"})
	public void invalidPrimaryGuestCountry(){
		String country = "INVALID";
		TestReporter.logScenario("Invalid Primary Guest Country");
		Book book = book();
		book.setPrimaryGuestCountry(country);
		sendRequestValidateLogs(book, "Create Party Error : Please enter valid country code", LiloPartyErrorCode.INVALID_COUNTRY);
	}
	@Test(groups = {"api", "regression", "dining", "seatedEventsComponentService", "negative"})
	public void invalidTravelPlanId(){
		String tp_id = "11111";
		TestReporter.logScenario("Invalid Travel Plan ID");
		Book book = book();
		book.setTravelPlanId(tp_id);
		sendRequestValidateLogs(book, "TRAVEL_PLAN_NOT_FOUND : TRAVEL PLAN NOT FOUND", DiningErrorCode.TRAVEL_PLAN_NOT_FOUND);
	}
	@Test(groups = {"api", "regression", "dining", "seatedEventsComponentService", "negative"})
	public void invalidSalesChannel(){
		String salesChannel = "abcd";
		TestReporter.logScenario("Invalid Sales Channel");
		Book book = book();
		book.setSalesChannel(salesChannel);
		sendRequestValidateLogs(book, "Sales Channel is required : null", DiningErrorCode.SALES_CHANNEL_REQUIRED);
	}
	@Test(groups = {"api", "regression", "dining", "seatedEventsComponentService", "negative"})
	public void invalidCommunicationsChannel(){
		String communicationsChannel = "abcd";
		TestReporter.logScenario("Invalid Communications Channel");
		Book book = book();
		book.setCommunicationChannel(communicationsChannel);
		sendRequestValidateLogs(book, "communication Channel is required : null", DiningErrorCode.COMMUNICATION_CHANNEL_REQUIRED);
	}
	@Test(groups = {"api", "regression", "dining", "seatedEventsComponentService", "negative"})
	public void invalidSourceAccountingCenter_Negative(){
		String sac = "-1";
		TestReporter.logScenario("Invalid Source Accounting Center - Negative Number");
		Book book = book();
		book.setSourceAccountingCenter(sac);
		sendRequestValidateLogs(book, "SOURCER ACCOUNTING CENTER IS REQUIRED! : Invalid Source Accounting Center#"+sac, CoreResErrorCode.SRC_ACCOUNTING_CENTER_REQUIRED);
	}
	@Test(groups = {"api", "regression", "dining", "seatedEventsComponentService", "negative"})
	public void invalidSourceAccountingCenter_NotFound(){
		String sac = "9999";
		TestReporter.logScenario("Invalid Primary Guest Title - Non-Existent Center");
		Book book = book();
		book.setSourceAccountingCenter(sac);
		sendRequestValidateLogs(book, "SQL Query Error  :  SQL Query Error in Component Service com.wdw.resmanagement.service.ejb.SeatedEventsComponentService.book", LiloSystemErrorCode.SQL_QUERY_ERROR);
	}

	private Book book(){
		Book book = new Book(environment, "Main");
		book.setPrimaryGuestAddress1(hh.primaryGuest().primaryAddress().getAddress1());
		book.setPrimaryGuestAddressCountry(hh.primaryGuest().primaryAddress().getCountry());
		book.setPrimaryGuestAddressPostalCode(hh.primaryGuest().primaryAddress().getZipCode());
		book.setPrimaryGuestFirstName(hh.primaryGuest().getFirstName());
		book.setPrimaryGuestLastName(hh.primaryGuest().getLastName());
		book.setPrimaryGuestPhoneNumber(hh.primaryGuest().primaryPhone().getNumber());
		book.setServiceStartDate(bookingDate);
		return book;
	}
	
	private void sendRequestValidateLogs(Book book, String faultString, ApplicationErrorCode errorCode){
		book.sendRequest();
		validateApplicationError(book, errorCode);
		TestReporter.logAPI(!book.getFaultString().contains(faultString), book.getFaultString(), book);
		
		LogItems logValidItems = new LogItems();
		logValidItems.addItem("SeatedEventsComponentService", "book", true);
		validateLogs(book, logValidItems, 10000);
		
		LogItems logInvalidItems = new LogItems();	
		logInvalidItems.addItem("GuestServiceV1", "create", false);
		logInvalidItems.addItem("PartyIF", "updateExternalPartyAndLocatorId", false);
		validateNotInLogs(book, logInvalidItems);
	}
}