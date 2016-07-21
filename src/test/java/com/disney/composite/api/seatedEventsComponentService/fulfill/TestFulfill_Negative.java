package com.disney.composite.api.seatedEventsComponentService.fulfill;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.applicationError.ApplicationErrorCode;
import com.disney.api.soapServices.applicationError.DiningErrorCode;
import com.disney.api.soapServices.applicationError.LiloSystemErrorCode;
import com.disney.api.soapServices.core.BaseSoapCommands;
import com.disney.api.soapServices.seatedEventsComponentService.operations.Book;
import com.disney.api.soapServices.seatedEventsComponentService.operations.Fulfill;
import com.disney.composite.BaseTest;
import com.disney.utils.Randomness;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.database.LogItems;
import com.disney.utils.dataFactory.guestFactory.HouseHold;

public class TestFulfill_Negative extends BaseTest{
	String bookingDate = Randomness.generateCurrentXMLDatetime(45);
	String TPS_ID;
	String invalidValue = "INVALID";
	
	@Override
	@BeforeClass(alwaysRun=true)
	@Parameters({"environment"})
	public void setup(@Optional String environment){
		this.environment = environment;
		hh = new HouseHold(1);

		TestReporter.logStep("Book Seated Events Component Service");
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
		TPS_ID = book.getReservationNumber();
	}	
	
	@Test(groups = {"api", "regression", "dining", "seatedEventsComponentService", "negative"})
	public void invalidCommunicationsChannel(){
		TestReporter.logScenario("Invalid Communications Channel");
		Fulfill fulfill = new Fulfill(environment, "Main");
		fulfill.setCommunicationChannel(invalidValue);
		sendRequestValidateLogs(fulfill, "communication Channel is required : null", DiningErrorCode.COMMUNICATION_CHANNEL_REQUIRED);
	}
	@Test(groups = {"api", "regression", "dining", "seatedEventsComponentService", "negative"})
	public void missingCommunicationsChannel(){
		TestReporter.logScenario("Missing Communications Channel");
		Fulfill fulfill = new Fulfill(environment, "Main");
		fulfill.setCommunicationChannel(BaseSoapCommands.REMOVE_NODE.toString());
		sendRequestValidateLogs(fulfill, "communication Channel is required : null", DiningErrorCode.COMMUNICATION_CHANNEL_REQUIRED);
	}
	@Test(groups = {"api", "regression", "dining", "seatedEventsComponentService", "negative"})
	public void invalidOperatingArea(){
		TestReporter.logScenario("Invalid Operating Area");
		Fulfill fulfill = new Fulfill(environment, "Main");
		fulfill.setOperatingArea(invalidValue);
		sendRequestValidateLogs(fulfill, "Unexpected Error occurred : fulfill : org.hibernate.exception.DataException: could not extract ResultSet : org.hibernate.exception.DataException: could not extract ResultSet; nested exception is javax.persistence.PersistenceException: org.hibernate.exception.DataException: could not extract ResultSet", LiloSystemErrorCode.UNEXPECTED_ERROR);
	}
	@Test(groups = {"api", "regression", "dining", "seatedEventsComponentService", "negative"})
	public void missingOperatingArea(){
		TestReporter.logScenario("Missing Operating Area");
		Fulfill fulfill = new Fulfill(environment, "Main");
		fulfill.setOperatingArea(BaseSoapCommands.REMOVE_NODE.toString());
		sendRequestValidateLogs(fulfill, "No detials provided to fulfill will call request : Operating area is required!", DiningErrorCode.NO_WILL_CALL_DETAILS_PROVIDED);
	}
	@Test(groups = {"api", "regression", "dining", "seatedEventsComponentService", "negative"})
	public void invalidExternalReferenceWithNoResNumber(){
		TestReporter.logScenario("Invalid External Reference With No Reservation Number");
		Fulfill fulfill = new Fulfill(environment, "Main");
		fulfill.setExternalReferenceNumber(invalidValue);
		fulfill.setReservationNumber(BaseSoapCommands.REMOVE_NODE.toString());
		sendRequestValidateLogs(fulfill, "RECORD NOT FOUND : No Reservation found for the Reservation number : INVALID", DiningErrorCode.RECORD_NOT_FOUND_EXCEPTION);
	}
	@Test(groups = {"api", "regression", "dining", "seatedEventsComponentService", "negative"})
	public void invalidExternalReferenceWithNoResNumber_Zero(){
		TestReporter.logScenario("Invalid External Reference With No Reservation Number - Zero");
		Fulfill fulfill = new Fulfill(environment, "Main");
		fulfill.setExternalReferenceNumber("0");
		fulfill.setReservationNumber(BaseSoapCommands.REMOVE_NODE.toString());
		sendRequestValidateLogs(fulfill, "Unexpected Error occurred : fulfill : org.hibernate.exception.DataException: could not extract ResultSet : org.hibernate.exception.DataException: could not extract ResultSet; nested exception is javax.persistence.PersistenceException: org.hibernate.exception.DataException: could not extract ResultSet", LiloSystemErrorCode.UNEXPECTED_ERROR);
	}
	@Test(groups = {"api", "regression", "dining", "seatedEventsComponentService", "negative"})
	public void missingExternalReferenceAndResNumber(){
		TestReporter.logScenario("Missing External Reference and Reservation Number");
		Fulfill fulfill = new Fulfill(environment, "Main");
		fulfill.setExternalReferenceNumber(BaseSoapCommands.REMOVE_NODE.toString());
		fulfill.setReservationNumber(BaseSoapCommands.REMOVE_NODE.toString());
		sendRequestValidateLogs(fulfill, "RECORD NOT FOUND : Either Reservation number or Nexus external referenace number should be provided !", DiningErrorCode.RECORD_NOT_FOUND_EXCEPTION);
	}
	@Test(groups = {"api", "regression", "dining", "seatedEventsComponentService", "negative"})
	public void invalidSalesChannel(){
		TestReporter.logScenario("Invalid Sales Channel");
		Fulfill fulfill = new Fulfill(environment, "Main");
		fulfill.setSalesChannel(invalidValue);
		sendRequestValidateLogs(fulfill, "Sales Channel is required : null", DiningErrorCode.SALES_CHANNEL_REQUIRED);
	}
	@Test(groups = {"api", "regression", "dining", "seatedEventsComponentService", "negative"})
	public void missingSalesChannel(){
		TestReporter.logScenario("Missing Sales Channel");
		Fulfill fulfill = new Fulfill(environment, "Main");
		fulfill.setSalesChannel(BaseSoapCommands.REMOVE_NODE.toString());
		sendRequestValidateLogs(fulfill, "Sales Channel is required : null", DiningErrorCode.SALES_CHANNEL_REQUIRED);
	}
	
	private void sendRequestValidateLogs(Fulfill fulfill, String faultString, ApplicationErrorCode errorCode){
		fulfill.sendRequest();
		validateApplicationError(fulfill, errorCode);
		TestReporter.logAPI(!fulfill.getFaultString().contains(faultString), fulfill.getFaultString(), fulfill);
		
		LogItems logValidItems = new LogItems();
		logValidItems.addItem("SeatedEventsComponentService", "book", true);
		validateLogs(fulfill, logValidItems, 10000);
		
//		LogItems logInvalidItems = new LogItems();	
//		logInvalidItems.addItem("PartyIF", "createAndRetrieveParty", false);
//		logInvalidItems.addItem("GuestServiceV1", "create", false);
//		logInvalidItems.addItem("PartyIF", "updateExternalPartyAndLocatorId", false);
//		logInvalidItems.addItem("TravelPlanServiceV3", "create", false);
//		logInvalidItems.addItem("TravelPlanServiceV3SEI", "create", false);
//		validateNotInLogs(fulfill, logInvalidItems);
	}
}
