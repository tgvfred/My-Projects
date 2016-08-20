package com.disney.composite.api.diningModule.scheduledEventsServicePort.searchByGuest;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import com.disney.api.soapServices.applicationError.ApplicationErrorCode;
import com.disney.api.soapServices.applicationError.DiningErrorCode;
import com.disney.api.soapServices.applicationError.LiloSystemErrorCode;
import com.disney.api.soapServices.core.BaseSoapCommands;
import com.disney.api.soapServices.diningModule.eventDiningService.operations.Cancel;
import com.disney.api.soapServices.diningModule.scheduledEventsServicePort.operations.SearchByGuest;
import com.disney.composite.BaseTest;
import com.disney.test.utils.Randomness;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.database.LogItems;
import com.disney.utils.dataFactory.staging.bookSEReservation.EventDiningReservation;
import com.disney.utils.dataFactory.staging.bookSEReservation.ScheduledEventReservation;

public class TestSearchByGuest_Negative extends BaseTest{
	protected ThreadLocal<ScheduledEventReservation> res = new ThreadLocal<ScheduledEventReservation>();;
	protected String invalidNumber = "1";
	protected String invalidString = "INVALID";
	protected ThreadLocal<String> TPS_ID = new ThreadLocal<String>(); 
	
	@AfterMethod(alwaysRun = true)
	public void teardown() {
		try{
			Cancel cancel = new Cancel(environment, "CancelEventDining");
			cancel.setReservationNumber(TPS_ID.get());
			cancel.sendRequest();
		}catch(Exception e){}					
	}
	
	
	@Test(groups = {"api", "regression", "dining", "scheduledEventsServicePort", "negative"})
	public void testNoData(){
		res.set(new EventDiningReservation(environment, hh));
		res.get().book(ScheduledEventReservation.NOCOMPONENTSNOADDONS);
		TPS_ID.set(res.get().getConfirmationNumber());
		TestReporter.logStep("No Data");
		SearchByGuest search = new SearchByGuest(environment, "Main");
		search.setReservationNumber(BaseSoapCommands.REMOVE_NODE.toString());
		search.setCancellationNumber(BaseSoapCommands.REMOVE_NODE.toString());
		sendRequestAndValidateLogs(search, "Search Criteria is Invalid : INVALID SEARCH CRITERIA", DiningErrorCode.INVALID_SEARCH_CRITERIA);
	}
	@Test(groups = {"api", "regression", "dining", "scheduledEventsServicePort", "negative"})
	public void testInvalidCancellationNumberOnly(){
		res.set(new EventDiningReservation(environment, hh));
		res.get().book(ScheduledEventReservation.NOCOMPONENTSNOADDONS);
		TPS_ID.set(res.get().getConfirmationNumber());
		TestReporter.logStep("Invalid Cancellation Number Only");
		SearchByGuest search = new SearchByGuest(environment, "Main");
		search.setReservationNumber(BaseSoapCommands.REMOVE_NODE.toString());
		search.setCancellationNumber(invalidNumber);
		sendRequestAndValidateLogs(search, "No travel plan data found. : NO RESULTS FOUND", DiningErrorCode.TRAVEL_PLAN_SEARCH_NO_RESULT);
	}
	@Test(groups = {"api", "regression", "dining", "scheduledEventsServicePort", "negative"})
	public void testInvalidEmailOnly(){
		res.set(new EventDiningReservation(environment, hh));
		res.get().book(ScheduledEventReservation.NOCOMPONENTSNOADDONS);
		TPS_ID.set(res.get().getConfirmationNumber());
		TestReporter.logStep("Invalid Email Only");
		SearchByGuest search = new SearchByGuest(environment, "Main");
		search.setReservationNumber(BaseSoapCommands.REMOVE_NODE.toString());
		search.setCancellationNumber(BaseSoapCommands.REMOVE_NODE.toString());
		search.setEmail(invalidString);
		sendRequestAndValidateLogs(search, "No travel plan data found. : NO RESULTS FOUND", DiningErrorCode.TRAVEL_PLAN_SEARCH_NO_RESULT);
	}
	@Test(groups = {"api", "regression", "dining", "scheduledEventsServicePort", "negative"})
	public void testInvalidFirstNameOnly(){
		res.set(new EventDiningReservation(environment, hh));
		res.get().book(ScheduledEventReservation.NOCOMPONENTSNOADDONS);
		TPS_ID.set(res.get().getConfirmationNumber());
		TestReporter.logStep("Invalid First Name Only");
		SearchByGuest search = new SearchByGuest(environment, "Main");
		search.setReservationNumber(BaseSoapCommands.REMOVE_NODE.toString());
		search.setCancellationNumber(BaseSoapCommands.REMOVE_NODE.toString());
		search.setFirstName(invalidString);
		sendRequestAndValidateLogs(search, "Search Criteria is Invalid : INVALID SEARCH CRITERIA", DiningErrorCode.INVALID_SEARCH_CRITERIA);		
	}
	@Test(groups = {"api", "regression", "dining", "scheduledEventsServicePort", "negative"})
	public void testInvalidLastNameOnly(){
		res.set(new EventDiningReservation(environment, hh));
		res.get().book(ScheduledEventReservation.NOCOMPONENTSNOADDONS);
		TPS_ID.set(res.get().getConfirmationNumber());
		TestReporter.logStep("Invalid Last Name Only");
		SearchByGuest search = new SearchByGuest(environment, "Main");
		search.setReservationNumber(BaseSoapCommands.REMOVE_NODE.toString());
		search.setCancellationNumber(BaseSoapCommands.REMOVE_NODE.toString());
		search.setLastName(invalidString);
		sendRequestAndValidateLogs(search, "Search Criteria is Invalid : INVALID SEARCH CRITERIA", DiningErrorCode.INVALID_SEARCH_CRITERIA);
	}
	@Test(groups = {"api", "regression", "dining", "scheduledEventsServicePort", "negative"})
	public void testInvalidPhoneNumberOnly(){
		res.set(new EventDiningReservation(environment, hh));
		res.get().book(ScheduledEventReservation.NOCOMPONENTSNOADDONS);
		TPS_ID.set(res.get().getConfirmationNumber());
		TestReporter.logStep("Invalid Phone Number Only");
		SearchByGuest search = new SearchByGuest(environment, "Main");
		search.setReservationNumber(BaseSoapCommands.REMOVE_NODE.toString());
		search.setCancellationNumber(BaseSoapCommands.REMOVE_NODE.toString());
		search.setPhoneNumber(invalidNumber);
		sendRequestAndValidateLogs(search, "Search Criteria is Invalid : INVALID SEARCH CRITERIA", DiningErrorCode.INVALID_SEARCH_CRITERIA);
	}
	@Test(groups = {"api", "regression", "dining", "scheduledEventsServicePort", "negative"})
	public void testInvalidPostalCodeOnly(){
		res.set(new EventDiningReservation(environment, hh));
		res.get().book(ScheduledEventReservation.NOCOMPONENTSNOADDONS);
		TPS_ID.set(res.get().getConfirmationNumber());
		TestReporter.logStep("Invalid Postal Code Only");
		SearchByGuest search = new SearchByGuest(environment, "Main");
		search.setReservationNumber(BaseSoapCommands.REMOVE_NODE.toString());
		search.setCancellationNumber(BaseSoapCommands.REMOVE_NODE.toString());
		search.setPostalCode(invalidNumber);
		sendRequestAndValidateLogs(search, "Search Criteria is Invalid : INVALID SEARCH CRITERIA", DiningErrorCode.INVALID_SEARCH_CRITERIA);
	}
	@Test(groups = {"api", "regression", "dining", "scheduledEventsServicePort", "negative"})
	public void testInvalidReservationNumberOnly(){
		res.set(new EventDiningReservation(environment, hh));
		res.get().book(ScheduledEventReservation.NOCOMPONENTSNOADDONS);
		TPS_ID.set(res.get().getConfirmationNumber());
		TestReporter.logStep("Invalid Reservation Number Only");
		SearchByGuest search = new SearchByGuest(environment, "Main");
		search.setReservationNumber(invalidNumber);
		search.setCancellationNumber(BaseSoapCommands.REMOVE_NODE.toString());
		sendRequestAndValidateLogs(search, "No travel plan data found. : NO RESULTS FOUND", DiningErrorCode.TRAVEL_PLAN_SEARCH_NO_RESULT);
	}
	@Test(groups = {"api", "regression", "dining", "scheduledEventsServicePort", "negative"})
	public void testInvalidReservationStatusOnly(){
		res.set(new EventDiningReservation(environment, hh));
		res.get().book(ScheduledEventReservation.NOCOMPONENTSNOADDONS);
		TPS_ID.set(res.get().getConfirmationNumber());
		TestReporter.logStep("Invalid Reservation Status Only");
		SearchByGuest search = new SearchByGuest(environment, "Main");
		search.setReservationNumber(BaseSoapCommands.REMOVE_NODE.toString());
		search.setCancellationNumber(BaseSoapCommands.REMOVE_NODE.toString());
		search.setReservationStatus(invalidString);
		sendRequestAndValidateLogs(search, "Search Criteria is Invalid : INVALID SEARCH CRITERIA", DiningErrorCode.INVALID_SEARCH_CRITERIA);
	}
	@Test(groups = {"api", "regression", "dining", "scheduledEventsServicePort", "negative"})
	public void testInvalidGuestOdsIdOnly(){
		res.set(new EventDiningReservation(environment, hh));
		res.get().book(ScheduledEventReservation.NOCOMPONENTSNOADDONS);
		TPS_ID.set(res.get().getConfirmationNumber());
		TestReporter.logStep("Invalid Guest ODS IDs Only");
		SearchByGuest search = new SearchByGuest(environment, "Main");
		search.setReservationNumber(BaseSoapCommands.REMOVE_NODE.toString());
		search.setCancellationNumber(BaseSoapCommands.REMOVE_NODE.toString());
		search.setGuestOdsIds("-1");
		sendRequestAndValidateLogs(search, "No travel plan data found", LiloSystemErrorCode.UNEXPECTED_ERROR);
	}
	@Test(groups = {"api", "regression", "dining", "scheduledEventsServicePort", "negative"})
	public void testInvalidServiceStartDateOnly(){
		res.set(new EventDiningReservation(environment, hh));
		res.get().book(ScheduledEventReservation.NOCOMPONENTSNOADDONS);
		TPS_ID.set(res.get().getConfirmationNumber());
		TestReporter.logStep("Invalid Service Start Date Only");
		SearchByGuest search = new SearchByGuest(environment, "Main");
		search.setReservationNumber(BaseSoapCommands.REMOVE_NODE.toString());
		search.setCancellationNumber(BaseSoapCommands.REMOVE_NODE.toString());
		search.setServiceDate(Randomness.generateCurrentXMLDatetime(-730));
		sendRequestAndValidateLogs(search, "Search Criteria is Invalid : INVALID SEARCH CRITERIA", DiningErrorCode.INVALID_SEARCH_CRITERIA);
	}
	@Test(groups = {"api", "regression", "dining", "scheduledEventsServicePort", "negative"})
	public void testInvalidServiceWindowEndOnly(){
		res.set(new EventDiningReservation(environment, hh));
		res.get().book(ScheduledEventReservation.NOCOMPONENTSNOADDONS);
		TPS_ID.set(res.get().getConfirmationNumber());
		TestReporter.logStep("Invalid Service Window End Only");
		SearchByGuest search = new SearchByGuest(environment, "Main");
		search.setReservationNumber(BaseSoapCommands.REMOVE_NODE.toString());
		search.setCancellationNumber(BaseSoapCommands.REMOVE_NODE.toString());
		search.setServiceWindowEnd(Randomness.generateCurrentXMLDatetime(-730));
		sendRequestAndValidateLogs(search, "Search Criteria is Invalid : INVALID SEARCH CRITERIA", DiningErrorCode.INVALID_SEARCH_CRITERIA);
	}
	@Test(groups = {"api", "regression", "dining", "scheduledEventsServicePort", "negative"})
	public void testInvalidServiceWindowStartOnly(){
		res.set(new EventDiningReservation(environment, hh));
		res.get().book(ScheduledEventReservation.NOCOMPONENTSNOADDONS);
		TPS_ID.set(res.get().getConfirmationNumber());
		TestReporter.logStep("Invalid Service Window End Only");
		SearchByGuest search = new SearchByGuest(environment, "Main");
		search.setReservationNumber(BaseSoapCommands.REMOVE_NODE.toString());
		search.setCancellationNumber(BaseSoapCommands.REMOVE_NODE.toString());
		search.setServiceWindowStart(Randomness.generateCurrentXMLDatetime(-730));
		sendRequestAndValidateLogs(search, "Search Criteria is Invalid : INVALID SEARCH CRITERIA", DiningErrorCode.INVALID_SEARCH_CRITERIA);
	}
	@Test(groups = {"api", "regression", "dining", "scheduledEventsServicePort", "negative"})
	public void testInvalidSourceAccountingCenterOnly(){
		res.set(new EventDiningReservation(environment, hh));
		res.get().book(ScheduledEventReservation.NOCOMPONENTSNOADDONS);
		TPS_ID.set(res.get().getConfirmationNumber());
		TestReporter.logStep("Invalid Service Window End Only");
		SearchByGuest search = new SearchByGuest(environment, "Main");
		search.setReservationNumber(BaseSoapCommands.REMOVE_NODE.toString());
		search.setCancellationNumber(BaseSoapCommands.REMOVE_NODE.toString());
		search.setSourceAccountingCenter(invalidNumber);
		sendRequestAndValidateLogs(search, "Search Criteria is Invalid : INVALID SEARCH CRITERIA", DiningErrorCode.INVALID_SEARCH_CRITERIA);
	}
	
	private void sendRequestAndValidateLogs(SearchByGuest search, String faultString, ApplicationErrorCode errorCode){
		search.sendRequest();
		validateApplicationError(search, errorCode);
		TestReporter.logAPI(!search.getFaultString().contains(faultString), search.getFaultString(), search);
		
		LogItems logItems = new LogItems();
		logItems.addItem("ScheduledEventsServiceIF", "searchByGuest", true);	
		validateLogs(search, logItems);
		
		LogItems logInvalidItems = new LogItems();	
		logInvalidItems.addItem("PartyIF", "retrieveParty", false);
		validateNotInLogs(search, logInvalidItems);
	}
}
