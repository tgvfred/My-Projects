package com.disney.composite.api.scheduledEventsServicePort.searchByGuest;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.applicationError.ApplicationErrorCode;
import com.disney.api.soapServices.applicationError.DiningErrorCode;
import com.disney.api.soapServices.applicationError.LiloSystemErrorCode;
import com.disney.api.soapServices.core.BaseSoapCommands;
import com.disney.api.soapServices.scheduledEventsServicePort.operations.SearchByGuest;
import com.disney.composite.BaseTest;
import com.disney.test.utils.Randomness;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.database.LogItems;
import com.disney.utils.dataFactory.guestFactory.HouseHold;
import com.disney.utils.dataFactory.staging.bookSEReservation.EventDiningReservation;
import com.disney.utils.dataFactory.staging.bookSEReservation.ScheduledEventReservation;

public class TestSearchByGuest_Negative extends BaseTest{
	// Defining global variables
	protected String environment = null;
	protected ScheduledEventReservation res = null;
	protected HouseHold hh = null; 
	protected String invalidNumber = "1";
	protected String invalidString = "INVALID";
	
	@Override
	@BeforeClass(alwaysRun = true)
	@Parameters({ "environment" })
	public void setup(
		String environment){this.environment = environment;
		hh = new HouseHold(1);
		hh.sendToApi(this.environment);
		res = new EventDiningReservation(environment, hh);
		res.book(ScheduledEventReservation.NOCOMPONENTSNOADDONS);
	}
	
	@AfterClass(alwaysRun = true)
	public void teardown() {res.cancel();}
	
	
	@Test(groups = {"api", "regression", "dining", "scheduledEventsServicePort", "negative"})
	public void testNoData(){
		TestReporter.logStep("No Data");
		SearchByGuest search = new SearchByGuest(environment, "Main");
		search.setReservationNumber(BaseSoapCommands.REMOVE_NODE.toString());
		search.setCancellationNumber(BaseSoapCommands.REMOVE_NODE.toString());
		sendRequestAndValidateLogs(search, "Search Criteria is Invalid : INVALID SEARCH CRITERIA", DiningErrorCode.INVALID_SEARCH_CRITERIA);
	}
	@Test(groups = {"api", "regression", "dining", "scheduledEventsServicePort", "negative"})
	public void testInvalidCancellationNumberOnly(){
		TestReporter.logStep("Invalid Cancellation Number Only");
		SearchByGuest search = new SearchByGuest(environment, "Main");
		search.setReservationNumber(BaseSoapCommands.REMOVE_NODE.toString());
		search.setCancellationNumber(invalidNumber);
		sendRequestAndValidateLogs(search, "No travel plan data found. : NO RESULTS FOUND", DiningErrorCode.TRAVEL_PLAN_SEARCH_NO_RESULT);
	}
	@Test(groups = {"api", "regression", "dining", "scheduledEventsServicePort", "negative"})
	public void testInvalidEmailOnly(){
		TestReporter.logStep("Invalid Email Only");
		SearchByGuest search = new SearchByGuest(environment, "Main");
		search.setReservationNumber(BaseSoapCommands.REMOVE_NODE.toString());
		search.setCancellationNumber(BaseSoapCommands.REMOVE_NODE.toString());
		search.setEmail(invalidString);
		sendRequestAndValidateLogs(search, "No travel plan data found. : NO RESULTS FOUND", DiningErrorCode.TRAVEL_PLAN_SEARCH_NO_RESULT);
	}
	@Test(groups = {"api", "regression", "dining", "scheduledEventsServicePort", "negative"})
	public void testInvalidFirstNameOnly(){
		TestReporter.logStep("Invalid First Name Only");
		SearchByGuest search = new SearchByGuest(environment, "Main");
		search.setReservationNumber(BaseSoapCommands.REMOVE_NODE.toString());
		search.setCancellationNumber(BaseSoapCommands.REMOVE_NODE.toString());
		search.setFirstName(invalidString);
		sendRequestAndValidateLogs(search, "Search Criteria is Invalid : INVALID SEARCH CRITERIA", DiningErrorCode.INVALID_SEARCH_CRITERIA);		
	}
	@Test(groups = {"api", "regression", "dining", "scheduledEventsServicePort", "negative"})
	public void testInvalidLastNameOnly(){
		TestReporter.logStep("Invalid Last Name Only");
		SearchByGuest search = new SearchByGuest(environment, "Main");
		search.setReservationNumber(BaseSoapCommands.REMOVE_NODE.toString());
		search.setCancellationNumber(BaseSoapCommands.REMOVE_NODE.toString());
		search.setLastName(invalidString);
		sendRequestAndValidateLogs(search, "Search Criteria is Invalid : INVALID SEARCH CRITERIA", DiningErrorCode.INVALID_SEARCH_CRITERIA);
	}
	@Test(groups = {"api", "regression", "dining", "scheduledEventsServicePort", "negative"})
	public void testInvalidPhoneNumberOnly(){
		TestReporter.logStep("Invalid Phone Number Only");
		SearchByGuest search = new SearchByGuest(environment, "Main");
		search.setReservationNumber(BaseSoapCommands.REMOVE_NODE.toString());
		search.setCancellationNumber(BaseSoapCommands.REMOVE_NODE.toString());
		search.setPhoneNumber(invalidNumber);
		sendRequestAndValidateLogs(search, "Search Criteria is Invalid : INVALID SEARCH CRITERIA", DiningErrorCode.INVALID_SEARCH_CRITERIA);
	}
	@Test(groups = {"api", "regression", "dining", "scheduledEventsServicePort", "negative"})
	public void testInvalidPostalCodeOnly(){
		TestReporter.logStep("Invalid Postal Code Only");
		SearchByGuest search = new SearchByGuest(environment, "Main");
		search.setReservationNumber(BaseSoapCommands.REMOVE_NODE.toString());
		search.setCancellationNumber(BaseSoapCommands.REMOVE_NODE.toString());
		search.setPostalCode(invalidNumber);
		sendRequestAndValidateLogs(search, "Search Criteria is Invalid : INVALID SEARCH CRITERIA", DiningErrorCode.INVALID_SEARCH_CRITERIA);
	}
	@Test(groups = {"api", "regression", "dining", "scheduledEventsServicePort", "negative"})
	public void testInvalidReservationNumberOnly(){
		TestReporter.logStep("Invalid Reservation Number Only");
		SearchByGuest search = new SearchByGuest(environment, "Main");
		search.setReservationNumber(invalidNumber);
		search.setCancellationNumber(BaseSoapCommands.REMOVE_NODE.toString());
		sendRequestAndValidateLogs(search, "No travel plan data found. : NO RESULTS FOUND", DiningErrorCode.TRAVEL_PLAN_SEARCH_NO_RESULT);
	}
	@Test(groups = {"api", "regression", "dining", "scheduledEventsServicePort", "negative"})
	public void testInvalidReservationStatusOnly(){
		TestReporter.logStep("Invalid Reservation Status Only");
		SearchByGuest search = new SearchByGuest(environment, "Main");
		search.setReservationNumber(BaseSoapCommands.REMOVE_NODE.toString());
		search.setCancellationNumber(BaseSoapCommands.REMOVE_NODE.toString());
		search.setReservationStatus(invalidString);
		sendRequestAndValidateLogs(search, "Search Criteria is Invalid : INVALID SEARCH CRITERIA", DiningErrorCode.INVALID_SEARCH_CRITERIA);
	}
	@Test(groups = {"api", "regression", "dining", "scheduledEventsServicePort", "negative"})
	public void testInvalidGuestOdsIdOnly(){
		TestReporter.logStep("Invalid Guest ODS IDs Only");
		SearchByGuest search = new SearchByGuest(environment, "Main");
		search.setReservationNumber(BaseSoapCommands.REMOVE_NODE.toString());
		search.setCancellationNumber(BaseSoapCommands.REMOVE_NODE.toString());
		search.setGuestOdsIds(invalidNumber);
		sendRequestAndValidateLogs(search, "Unexpected Error occurred : searchByGuest : org.hibernate.exception.SQLGrammarException: could not extract ResultSet : org.hibernate.exception.SQLGrammarException: could not extract ResultSet; nested exception is javax.persistence.PersistenceException: org.hibernate.exception.SQLGrammarException: could not extract ResultSet", LiloSystemErrorCode.UNEXPECTED_ERROR);
	}
	@Test(groups = {"api", "regression", "dining", "scheduledEventsServicePort", "negative"})
	public void testInvalidServiceStartDateOnly(){
		TestReporter.logStep("Invalid Service Start Date Only");
		SearchByGuest search = new SearchByGuest(environment, "Main");
		search.setReservationNumber(BaseSoapCommands.REMOVE_NODE.toString());
		search.setCancellationNumber(BaseSoapCommands.REMOVE_NODE.toString());
		search.setServiceDate(Randomness.generateCurrentXMLDatetime(-730));
		sendRequestAndValidateLogs(search, "Search Criteria is Invalid : INVALID SEARCH CRITERIA", DiningErrorCode.INVALID_SEARCH_CRITERIA);
	}
	@Test(groups = {"api", "regression", "dining", "scheduledEventsServicePort", "negative"})
	public void testInvalidServiceWindowEndOnly(){
		TestReporter.logStep("Invalid Service Window End Only");
		SearchByGuest search = new SearchByGuest(environment, "Main");
		search.setReservationNumber(BaseSoapCommands.REMOVE_NODE.toString());
		search.setCancellationNumber(BaseSoapCommands.REMOVE_NODE.toString());
		search.setServiceWindowEnd(Randomness.generateCurrentXMLDatetime(-730));
		sendRequestAndValidateLogs(search, "Search Criteria is Invalid : INVALID SEARCH CRITERIA", DiningErrorCode.INVALID_SEARCH_CRITERIA);
	}
	@Test(groups = {"api", "regression", "dining", "scheduledEventsServicePort", "negative"})
	public void testInvalidServiceWindowStartOnly(){
		TestReporter.logStep("Invalid Service Window End Only");
		SearchByGuest search = new SearchByGuest(environment, "Main");
		search.setReservationNumber(BaseSoapCommands.REMOVE_NODE.toString());
		search.setCancellationNumber(BaseSoapCommands.REMOVE_NODE.toString());
		search.setServiceWindowStart(Randomness.generateCurrentXMLDatetime(-730));
		sendRequestAndValidateLogs(search, "Search Criteria is Invalid : INVALID SEARCH CRITERIA", DiningErrorCode.INVALID_SEARCH_CRITERIA);
	}
	@Test(groups = {"api", "regression", "dining", "scheduledEventsServicePort", "negative"})
	public void testInvalidSourceAccountingCenterOnly(){
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
