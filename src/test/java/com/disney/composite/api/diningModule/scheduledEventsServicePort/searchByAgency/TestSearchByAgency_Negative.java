package com.disney.composite.api.diningModule.scheduledEventsServicePort.searchByAgency;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import com.disney.api.BaseTest;
import com.disney.api.soapServices.applicationError.ApplicationErrorCode;
import com.disney.api.soapServices.applicationError.DiningErrorCode;
import com.disney.api.soapServices.applicationError.LiloSystemErrorCode;
import com.disney.api.soapServices.core.BaseSoapCommands;
import com.disney.api.soapServices.diningModule.eventDiningService.operations.Cancel;
import com.disney.api.soapServices.diningModule.scheduledEventsServicePort.operations.SearchByAgency;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.database.LogItems;
import com.disney.utils.dataFactory.staging.bookSEReservation.EventDiningReservation;
import com.disney.utils.dataFactory.staging.bookSEReservation.ScheduledEventReservation;

public class TestSearchByAgency_Negative extends BaseTest{
	protected String invalidValue = "INVALID";
	protected ThreadLocal<String> TPS_ID = new ThreadLocal<String>(); 
	
	@AfterMethod(alwaysRun=true)
	public void cancelReservation(){
		try{
			Cancel cancel = new Cancel(environment, "CancelDiningEvent");
			cancel.setReservationNumber(TPS_ID.get());
			cancel.sendRequest();
		}
		catch(Exception e){}
	}

	@Test(groups = {"api", "regression", "dining", "scheduledEventsServicePort", "negative"})
	public void testInvalidAgencyNumber(){
		ScheduledEventReservation book = new EventDiningReservation(environment);
		book.setParty(hh);
		book.addTravelAgency();
		book.book(ScheduledEventReservation.NOCOMPONENTSNOADDONS);
		TPS_ID.set(book.getConfirmationNumber());
	
		TestReporter.logStep("Invalid Agency Number");
		SearchByAgency search = new SearchByAgency(environment, "OnlyAgency");
		search.setAgencyIataNumber("99");
		search.setGuestLastName(book.party().primaryGuest().getLastName());
		search.setReservationStatus("Booked");
		search.setSourceAccountingCenter(book.getSourceAccountingCenter());
		sendRequestAndValidateLogs(search, "Travel Agency is invalid", DiningErrorCode.INVALID_TRAVEL_AGENCY);	
	}
	@Test(groups = {"api", "regression", "dining", "scheduledEventsServicePort", "negative"})
	public void testMissingAgencyNumber(){
		ScheduledEventReservation book = new EventDiningReservation(environment);
		book.setParty(hh);
		book.addTravelAgency();
		book.book(ScheduledEventReservation.NOCOMPONENTSNOADDONS);
		TPS_ID.set(book.getConfirmationNumber());
	
		TestReporter.logStep("Missing Agancy Number");
		SearchByAgency search = new SearchByAgency(environment, "OnlyAgency");
		search.setAgencyIataNumber(BaseSoapCommands.REMOVE_NODE.toString());
		search.setGuestLastName(book.party().primaryGuest().getLastName());
		search.setReservationStatus("Booked");
		search.setSourceAccountingCenter(book.getSourceAccountingCenter());
		sendRequestAndValidateLogs(search, "Search Criteria is Invalid : INVALID SEARCH CRITERIA", DiningErrorCode.INVALID_SEARCH_CRITERIA);	
	}
	@Test(groups = {"api", "regression", "dining", "scheduledEventsServicePort", "negative"})
	public void testInvalidSourceAccountingCenter(){
		ScheduledEventReservation book = new EventDiningReservation(environment);
		book.setParty(hh);
		book.addTravelAgency();
		book.book(ScheduledEventReservation.NOCOMPONENTSNOADDONS);
		TPS_ID.set(book.getConfirmationNumber());
	
		TestReporter.logStep("Invalid Source Accounting Center");
		SearchByAgency search = new SearchByAgency(environment, "OnlyAgency");
		search.setAgencyIataNumber(book.getTravelAgencyId());
		search.setGuestLastName(book.party().primaryGuest().getLastName());
		search.setReservationStatus("Booked");
		search.setSourceAccountingCenter("99");
		sendRequestAndValidateLogs(search, "No travel plan data found.", DiningErrorCode.TRAVEL_PLAN_SEARCH_NO_RESULT);	
	}
	@Test(groups = {"api", "regression", "dining", "scheduledEventsServicePort", "negative"})
	public void testInvalidGuestName(){
		ScheduledEventReservation book = new EventDiningReservation(environment);
		book.setParty(hh);
		book.addTravelAgency();
		book.book(ScheduledEventReservation.NOCOMPONENTSNOADDONS);
		TPS_ID.set(book.getConfirmationNumber());
	
		TestReporter.logStep("Invalid Guest Name");
		SearchByAgency search = new SearchByAgency(environment, "OnlyAgency");
		search.setAgencyIataNumber(book.getTravelAgencyId());
		search.setGuestLastName(invalidValue);
		search.setReservationStatus("Booked");
		search.setSourceAccountingCenter(book.getSourceAccountingCenter());
		sendRequestAndValidateLogs(search, "No travel plan data found. : NO RESULTS FOUND", DiningErrorCode.TRAVEL_PLAN_SEARCH_NO_RESULT);
	}
	@Test(groups = {"api", "regression", "dining", "scheduledEventsServicePort", "negative"})
	public void testInvalidReservationStatus(){
		ScheduledEventReservation book = new EventDiningReservation(environment);
		book.setParty(hh);
		book.addTravelAgency();
		book.book(ScheduledEventReservation.NOCOMPONENTSNOADDONS);
		TPS_ID.set(book.getConfirmationNumber());
	
		TestReporter.logStep("Invalid Reservation Status");
		SearchByAgency search = new SearchByAgency(environment, "OnlyAgency");
		search.setAgencyIataNumber(book.getTravelAgencyId());
		search.setGuestLastName(book.party().primaryGuest().getLastName());
		search.setReservationStatus(invalidValue);
		search.setSourceAccountingCenter(book.getSourceAccountingCenter());
		sendRequestAndValidateLogs(search, "Unexpected Error occurred : searchByAgency : "+invalidValue, LiloSystemErrorCode.UNEXPECTED_ERROR);
	}
	@Test(groups = {"api", "regression", "dining", "scheduledEventsServicePort", "negative"})
	public void testNoData(){
		ScheduledEventReservation book = new EventDiningReservation(environment);
		book.setParty(hh);
		book.addTravelAgency();
		book.book(ScheduledEventReservation.NOCOMPONENTSNOADDONS);
		TPS_ID.set(book.getConfirmationNumber());
	
		TestReporter.logStep("No Data");
		SearchByAgency search = new SearchByAgency(environment, "OnlyAgency");
		search.setAgencyIataNumber(BaseSoapCommands.REMOVE_NODE.toString());
		search.setGuestLastName(BaseSoapCommands.REMOVE_NODE.toString());
		search.setReservationStatus(BaseSoapCommands.REMOVE_NODE.toString());
		search.setSourceAccountingCenter(BaseSoapCommands.REMOVE_NODE.toString());
		sendRequestAndValidateLogs(search, "Search Criteria is Invalid : INVALID SEARCH CRITERIA", DiningErrorCode.INVALID_SEARCH_CRITERIA);		
	}
	
	private void sendRequestAndValidateLogs(SearchByAgency search, String faultString, ApplicationErrorCode errorCode){
		search.sendRequest();
		validateApplicationError(search, errorCode);
		TestReporter.logAPI(!search.getFaultString().trim().contains(faultString), "Response Fault message [" +search.getFaultString() + "] matches expected fault string [" +faultString+"]", search);
		
		LogItems logItems = new LogItems();
		logItems.addItem("ScheduledEventsServiceIF", "searchByAgency", true);	
		validateLogs(search, logItems);
		
		LogItems logInvalidItems = new LogItems();	
		logInvalidItems.addItem("PartyIF", "retrieveParty", false);
		validateNotInLogs(search, logInvalidItems);
	}
}