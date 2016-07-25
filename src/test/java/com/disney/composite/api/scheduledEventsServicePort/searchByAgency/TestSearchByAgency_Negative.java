package com.disney.composite.api.scheduledEventsServicePort.searchByAgency;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.applicationError.ApplicationErrorCode;
import com.disney.api.soapServices.applicationError.DiningErrorCode;
import com.disney.api.soapServices.applicationError.LiloSystemErrorCode;
import com.disney.api.soapServices.core.BaseSoapCommands;
import com.disney.api.soapServices.scheduledEventsServicePort.operations.SearchByAgency;
import com.disney.composite.BaseTest;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.database.LogItems;
import com.disney.utils.dataFactory.guestFactory.HouseHold;
import com.disney.utils.dataFactory.staging.bookSEReservation.EventDiningReservation;
import com.disney.utils.dataFactory.staging.bookSEReservation.ScheduledEventReservation;

public class TestSearchByAgency_Negative extends BaseTest{
	private ScheduledEventReservation book = null;
	String invalidValue = "INVALID";
	
	@BeforeClass(alwaysRun = true)
	@Parameters({ "environment" })
	public void preReq_BookReservation(String environment) {
		this.environment = environment;
		hh = new HouseHold(1);
		hh.sendToApi(environment);
		book = new EventDiningReservation(environment);
		book.setParty(hh);
		book.addTravelAgency();
		book.book("BookGuaranteedTS");
	}
	
	@AfterClass(alwaysRun=true)
	public void cancelReservation(){
		book.cancel();
	}

	@Test(groups = {"api", "regression", "dining", "scheduledEventsServicePort", "negative"})
	public void testInvalidAgencyNumber(){
		TestReporter.logStep("Invalid Agency Number");
		SearchByAgency searchByAgency = new SearchByAgency(environment, "OnlyAgency");
		searchByAgency.setAgencyIataNumber("99");
		searchByAgency.setGuestLastName(book.party().primaryGuest().getLastName());
		searchByAgency.setReservationStatus("Booked");
		searchByAgency.setSourceAccountingCenter(book.getSourceAccountingCenter());
		sendRequestAndValidateLogs(searchByAgency, "No travel plan data found. : NO RESULTS FOUND", DiningErrorCode.TRAVEL_PLAN_SEARCH_NO_RESULT);	
	}
	@Test(groups = {"api", "regression", "dining", "scheduledEventsServicePort", "negative"})
	public void testMissingAgencyNumber(){
		TestReporter.logStep("Missing Agancy Number");
		SearchByAgency searchByAgency = new SearchByAgency(environment, "OnlyAgency");
		searchByAgency.setAgencyIataNumber(BaseSoapCommands.REMOVE_NODE.toString());
		searchByAgency.setGuestLastName(book.party().primaryGuest().getLastName());
		searchByAgency.setReservationStatus("Booked");
		searchByAgency.setSourceAccountingCenter(book.getSourceAccountingCenter());
		sendRequestAndValidateLogs(searchByAgency, "Search Criteria is Invalid : INVALID SEARCH CRITERIA", DiningErrorCode.INVALID_SEARCH_CRITERIA);	
	}
	@Test(groups = {"api", "regression", "dining", "scheduledEventsServicePort", "negative"})
	public void testInvalidSourceAccountingCenter(){
		TestReporter.logStep("Invalid Source Accounting Center");
		SearchByAgency searchByAgency = new SearchByAgency(environment, "OnlyAgency");
		searchByAgency.setAgencyIataNumber(book.getTravelAgencyId());
		searchByAgency.setGuestLastName(book.party().primaryGuest().getLastName());
		searchByAgency.setReservationStatus("Booked");
		searchByAgency.setSourceAccountingCenter("99");
		sendRequestAndValidateLogs(searchByAgency, "No travel plan data found. : NO RESULTS FOUND", DiningErrorCode.TRAVEL_PLAN_SEARCH_NO_RESULT);	
	}
	@Test(groups = {"api", "regression", "dining", "scheduledEventsServicePort", "negative"})
	public void testInvalidGuestName(){
		TestReporter.logStep("Invalid Guest Name");
		SearchByAgency searchByAgency = new SearchByAgency(environment, "OnlyAgency");
		searchByAgency.setAgencyIataNumber(book.getTravelAgencyId());
		searchByAgency.setGuestLastName(invalidValue);
		searchByAgency.setReservationStatus("Booked");
		searchByAgency.setSourceAccountingCenter(book.getSourceAccountingCenter());
		sendRequestAndValidateLogs(searchByAgency, "No travel plan data found. : NO RESULTS FOUND", DiningErrorCode.TRAVEL_PLAN_SEARCH_NO_RESULT);
	}
	@Test(groups = {"api", "regression", "dining", "scheduledEventsServicePort", "negative"})
	public void testInvalidReservationStatus(){
		TestReporter.logStep("Invalid Reservation Status");
		SearchByAgency searchByAgency = new SearchByAgency(environment, "OnlyAgency");
		searchByAgency.setAgencyIataNumber(book.getTravelAgencyId());
		searchByAgency.setGuestLastName(book.party().primaryGuest().getLastName());
		searchByAgency.setReservationStatus(invalidValue);
		searchByAgency.setSourceAccountingCenter(book.getSourceAccountingCenter());
		sendRequestAndValidateLogs(searchByAgency, "Unexpected Error occurred : searchByAgency : "+invalidValue, LiloSystemErrorCode.UNEXPECTED_ERROR);
	}
	@Test(groups = {"api", "regression", "dining", "scheduledEventsServicePort", "negative"})
	public void testNoData(){
		TestReporter.logStep("No Data");
		SearchByAgency searchByAgency = new SearchByAgency(environment, "OnlyAgency");
		searchByAgency.setAgencyIataNumber(BaseSoapCommands.REMOVE_NODE.toString());
		searchByAgency.setGuestLastName(BaseSoapCommands.REMOVE_NODE.toString());
		searchByAgency.setReservationStatus(BaseSoapCommands.REMOVE_NODE.toString());
		searchByAgency.setSourceAccountingCenter(BaseSoapCommands.REMOVE_NODE.toString());
		sendRequestAndValidateLogs(searchByAgency, "Search Criteria is Invalid : INVALID SEARCH CRITERIA", DiningErrorCode.INVALID_SEARCH_CRITERIA);		
	}
	
	private void sendRequestAndValidateLogs(SearchByAgency searchByAgency, String faultString, ApplicationErrorCode errorCode){
		searchByAgency.sendRequest();
		validateApplicationError(searchByAgency, errorCode);
		TestReporter.logAPI(!searchByAgency.getFaultString().contains(faultString), searchByAgency.getFaultString(), searchByAgency);
		
		LogItems logItems = new LogItems();
		logItems.addItem("ScheduledEventsServiceIF", "searchByAgency", true);	
		validateLogs(searchByAgency, logItems);
		
		LogItems logInvalidItems = new LogItems();	
		logInvalidItems.addItem("PartyIF", "retrieveParty", false);
		validateNotInLogs(searchByAgency, logInvalidItems);
	}
}