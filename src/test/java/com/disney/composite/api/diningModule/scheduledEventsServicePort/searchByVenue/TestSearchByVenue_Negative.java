package com.disney.composite.api.diningModule.scheduledEventsServicePort.searchByVenue;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.applicationError.ApplicationErrorCode;
import com.disney.api.soapServices.applicationError.DiningErrorCode;
import com.disney.api.soapServices.core.BaseSoapCommands;
import com.disney.api.soapServices.core.exceptions.XPathNullNodeValueException;
import com.disney.api.soapServices.diningModule.scheduledEventsServicePort.operations.SearchByVenue;
import com.disney.composite.BaseTest;
import com.disney.utils.Randomness;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.database.LogItems;
import com.disney.utils.dataFactory.guestFactory.HouseHold;
import com.disney.utils.dataFactory.staging.bookSEReservation.EventDiningReservation;
import com.disney.utils.dataFactory.staging.bookSEReservation.ScheduledEventReservation;
import com.disney.utils.dataFactory.staging.bookSEReservation.ShowDiningReservation;

public class TestSearchByVenue_Negative extends BaseTest{
	protected ScheduledEventReservation res = null;
	
	@Override
	@BeforeMethod(alwaysRun = true)
	@Parameters({ "environment" })
	public void setup(
		String environment){this.environment = environment;
		hh = new HouseHold(1);
		hh.sendToApi(this.environment);
		res = new EventDiningReservation(environment, hh);
		res.book(ScheduledEventReservation.NOCOMPONENTSNOADDONS);
	}
	
	@AfterMethod(alwaysRun = true)
	public void closeSession() {
		if(res != null)
			if(res.getConfirmationNumber() != null)
				if(!res.getConfirmationNumber().isEmpty())
					res.cancel();
	}	
	
	@Test(groups = {"api", "regression", "dining", "scheduledEventsServicePort", "negative"})
	public void testFacilityIdOnly(){
		TestReporter.logStep("Facility ID Only");
		SearchByVenue search = new SearchByVenue(environment, "Main");
		search.setFacilityId(res.getFacilityId());
		search.setServiceWindowEnd(BaseSoapCommands.REMOVE_NODE.toString());
		search.setServiceWindowStart(BaseSoapCommands.REMOVE_NODE.toString());
		search.setReservationStatus(BaseSoapCommands.REMOVE_NODE.toString());
		search.setServiceDate(BaseSoapCommands.REMOVE_NODE.toString());
		search.setSourceAccountingCenter(BaseSoapCommands.REMOVE_NODE.toString());
		search.setProductIds(BaseSoapCommands.REMOVE_NODE.toString());
		sendRequestAndValidateLogs(search, "Search Criteria is Invalid : INVALID SEARCH CRITERIA", DiningErrorCode.INVALID_SEARCH_CRITERIA);
	}
	@Test(groups = {"api", "regression", "dining", "scheduledEventsServicePort", "negative"})
	public void testFacilityAndProductIdOnly(){
		TestReporter.logStep("Facility And Product ID Only");
		SearchByVenue search = new SearchByVenue(environment, "Main");
		search.setFacilityId(res.getFacilityId());
		search.setServiceWindowEnd(BaseSoapCommands.REMOVE_NODE.toString());
		search.setServiceWindowStart(BaseSoapCommands.REMOVE_NODE.toString());
		search.setReservationStatus(BaseSoapCommands.REMOVE_NODE.toString());
		search.setServiceDate(BaseSoapCommands.REMOVE_NODE.toString());
		search.setSourceAccountingCenter(BaseSoapCommands.REMOVE_NODE.toString());
		search.setProductIds(res.getProductId());
		sendRequestAndValidateLogs(search, "Search Criteria is Invalid : INVALID SEARCH CRITERIA", DiningErrorCode.INVALID_SEARCH_CRITERIA);
	}
	@Test(groups = {"api", "regression", "dining", "scheduledEventsServicePort", "negative"})
	public void testFacilityAndProductIdAndReservationStatusOnly(){
		TestReporter.logStep("Facility And Product ID And Reservation Status Only");
		SearchByVenue search = new SearchByVenue(environment, "Main");
		search.setFacilityId(res.getFacilityId());
		search.setServiceWindowEnd(BaseSoapCommands.REMOVE_NODE.toString());
		search.setServiceWindowStart(BaseSoapCommands.REMOVE_NODE.toString());
		search.setReservationStatus("Booked");
		search.setServiceWindowStart(BaseSoapCommands.REMOVE_NODE.toString());
		search.setSourceAccountingCenter(BaseSoapCommands.REMOVE_NODE.toString());
		search.setProductIds(res.getProductId());
		sendRequestAndValidateLogs(search, "Search Criteria is Invalid : INVALID SEARCH CRITERIA", DiningErrorCode.INVALID_SEARCH_CRITERIA);
	}
	@Test(groups = {"api", "regression", "dining", "scheduledEventsServicePort", "negative"})
	public void testFacilityIdAndDateInPast(){
		TestReporter.logStep("Facility ID And Service Date In The Past");
		SearchByVenue search = new SearchByVenue(environment, "Main");
		search.setFacilityId(res.getFacilityId());
		search.setServiceWindowEnd(BaseSoapCommands.REMOVE_NODE.toString());
		search.setServiceWindowStart(BaseSoapCommands.REMOVE_NODE.toString());
		search.setReservationStatus(BaseSoapCommands.REMOVE_NODE.toString());
		search.setServiceDate(Randomness.generateCurrentXMLDatetime(-730));
		search.setSourceAccountingCenter(BaseSoapCommands.REMOVE_NODE.toString());
		search.setProductIds(BaseSoapCommands.REMOVE_NODE.toString());
		sendRequestAndValidateLogs(search, "No travel plan data found. : NO RESULTS FOUND", DiningErrorCode.TRAVEL_PLAN_SEARCH_NO_RESULT);
	}
	@Test(groups = {"api", "regression", "dining", "scheduledEventsServicePort", "negative"})
	public void testWindowEndDateBeforeStartDate(){
		TestReporter.logStep("Window End Date Before Start Date");
		SearchByVenue search = new SearchByVenue(environment, "Main");
		search.setFacilityId(BaseSoapCommands.REMOVE_NODE.toString());
		search.setServiceWindowEnd(Randomness.generateCurrentXMLDatetime(-2));
		search.setServiceWindowStart(Randomness.generateCurrentXMLDatetime(-1));
		search.setReservationStatus(BaseSoapCommands.REMOVE_NODE.toString());
		search.setServiceDate(BaseSoapCommands.REMOVE_NODE.toString());
		search.setSourceAccountingCenter(BaseSoapCommands.REMOVE_NODE.toString());
		search.setProductIds(BaseSoapCommands.REMOVE_NODE.toString());
		sendRequestAndValidateLogs(search, "Search Criteria is Invalid : INVALID SEARCH CRITERIA", DiningErrorCode.INVALID_SEARCH_CRITERIA);
	}
	@Test(groups = {"api", "regression", "dining", "scheduledEventsServicePort", "negative"})
	public void testFacilityIdAndWindowEndDateOnly(){
		TestReporter.logStep("Facility ID And Window End Date Only");
		SearchByVenue search = new SearchByVenue(environment, "Main");
		search.setFacilityId(res.getFacilityId());
		search.setServiceWindowEnd(Randomness.generateCurrentXMLDatetime(0));
		search.setServiceWindowStart(BaseSoapCommands.REMOVE_NODE.toString());
		search.setReservationStatus(BaseSoapCommands.REMOVE_NODE.toString());
		search.setServiceDate(BaseSoapCommands.REMOVE_NODE.toString());
		search.setSourceAccountingCenter(BaseSoapCommands.REMOVE_NODE.toString());
		search.setProductIds(BaseSoapCommands.REMOVE_NODE.toString());
		sendRequestAndValidateLogs(search, "Search Criteria is Invalid : INVALID SEARCH CRITERIA", DiningErrorCode.INVALID_SEARCH_CRITERIA);
	}
	@Test(groups = {"api", "regression", "dining", "scheduledEventsServicePort", "negative"})
	public void testFacilityIdAndWindowStartDateOnly(){
		TestReporter.logStep("Facility ID And Window Start Date Only");
		SearchByVenue search = new SearchByVenue(environment, "Main");
		search.setFacilityId(res.getFacilityId());
		search.setServiceWindowEnd(BaseSoapCommands.REMOVE_NODE.toString());
		search.setServiceWindowStart(Randomness.generateCurrentXMLDatetime(0));
		search.setReservationStatus(BaseSoapCommands.REMOVE_NODE.toString());
		search.setServiceDate(BaseSoapCommands.REMOVE_NODE.toString());
		search.setSourceAccountingCenter(BaseSoapCommands.REMOVE_NODE.toString());
		search.setProductIds(BaseSoapCommands.REMOVE_NODE.toString());
		sendRequestAndValidateLogs(search, "Search Criteria is Invalid : INVALID SEARCH CRITERIA", DiningErrorCode.INVALID_SEARCH_CRITERIA);
	}
	@Test(groups = {"api", "regression", "dining", "scheduledEventsServicePort", "negative"})
	public void testFacilityIdAndSourceAccountingCenterOnly(){
		TestReporter.logStep("Facility ID And Source Accounting Center Only");
		SearchByVenue search = new SearchByVenue(environment, "Main");
		search.setFacilityId(res.getFacilityId());
		search.setServiceWindowEnd(BaseSoapCommands.REMOVE_NODE.toString());
		search.setServiceWindowStart(BaseSoapCommands.REMOVE_NODE.toString());
		search.setReservationStatus(BaseSoapCommands.REMOVE_NODE.toString());
		search.setServiceDate(BaseSoapCommands.REMOVE_NODE.toString());
		search.setSourceAccountingCenter(res.getSourceAccountingCenter());
		search.setProductIds(BaseSoapCommands.REMOVE_NODE.toString());
		sendRequestAndValidateLogs(search, "Search Criteria is Invalid : INVALID SEARCH CRITERIA", DiningErrorCode.INVALID_SEARCH_CRITERIA);
	}
	@Test(groups = {"api", "regression", "dining", "scheduledEventsServicePort", "negative"})
	public void testFacilityAndServiceWindowDatesOnly(){
		TestReporter.logStep("Facility ID And Service Window Dates Only");
		SearchByVenue search = new SearchByVenue(environment, "Main");
		search.setFacilityId("80010835");
		search.setServiceWindowEnd(Randomness.generateCurrentXMLDatetime(30));
		search.setServiceWindowStart(res.getServiceStartDate());
		search.setReservationStatus(BaseSoapCommands.REMOVE_NODE.toString());
		search.setServiceDate(BaseSoapCommands.REMOVE_NODE.toString());
		search.setSourceAccountingCenter(BaseSoapCommands.REMOVE_NODE.toString());
		search.setProductIds(BaseSoapCommands.REMOVE_NODE.toString());
		sendRequestAndValidateLogs(search, "No travel plan data found. : NO RESULTS FOUND", DiningErrorCode.TRAVEL_PLAN_SEARCH_NO_RESULT);
	}
	@Test(groups = {"api", "regression", "dining", "scheduledEventsServicePort", "negative"})
	public void testFacilityIDServiceWindowDatesAndSourceAccountingCenter(){
		TestReporter.logStep("Facility ID And Service Window Dates And Source Accounting Center Only");
		SearchByVenue search = new SearchByVenue(environment, "Main");
		search.setFacilityId("80010835");
		search.setServiceWindowEnd(Randomness.generateCurrentXMLDatetime(30));
		search.setServiceWindowStart(res.getServiceStartDate());
		search.setReservationStatus(BaseSoapCommands.REMOVE_NODE.toString());
		search.setServiceDate(BaseSoapCommands.REMOVE_NODE.toString());
		search.setSourceAccountingCenter(res.getSourceAccountingCenter());
		search.setProductIds(BaseSoapCommands.REMOVE_NODE.toString());
		sendRequestAndValidateLogs(search, "No travel plan data found. : NO RESULTS FOUND", DiningErrorCode.TRAVEL_PLAN_SEARCH_NO_RESULT);
	}
	@Test(groups = {"api", "regression", "dining", "scheduledEventsServicePort", "negative"})
	public void testBookShowDineAndSearchForReservation(){
		TestReporter.logStep("Search For Show Dine");
		ScheduledEventReservation res = new ShowDiningReservation(environment, hh);
		res.book(ScheduledEventReservation.ONECOMPONENTSNOADDONS);
		SearchByVenue search = new SearchByVenue(environment, "Main");
		search.setFacilityId(res.getFacilityId());
		search.setServiceWindowEnd(Randomness.generateCurrentXMLDatetime(1));
		search.setServiceWindowStart(res.getServiceStartDate());
		search.setReservationStatus("Booked");
		search.setServiceDate(res.getServiceStartDate());
		try{search.setSourceAccountingCenter(res.getSourceAccountingCenter());}
		catch(XPathNullNodeValueException e){search.setSourceAccountingCenter(BaseSoapCommands.REMOVE_NODE.toString());}
		search.setProductIds(res.getProductId());
		sendRequestAndValidateLogs(search, "Search Criteria is Invalid : INVALID SEARCH CRITERIA", DiningErrorCode.INVALID_SEARCH_CRITERIA);
	}

	private void sendRequestAndValidateLogs(SearchByVenue search, String faultString, ApplicationErrorCode errorCode){
		search.sendRequest();
		validateApplicationError(search, errorCode);
		TestReporter.logAPI(!search.getFaultString().contains(faultString), search.getFaultString(), search);
		
		LogItems logItems = new LogItems();
		logItems.addItem("ScheduledEventsServiceIF", "searchByVenue", true);	
		validateLogs(search, logItems);
		
		LogItems logInvalidItems = new LogItems();	
		logInvalidItems.addItem("FolioServiceIF", "retrieveFolioPaymentSummary", false);
		logInvalidItems.addItem("PartyIF", "retrieveParty", false);
		logInvalidItems.addItem("PartyIF", "retrievePartyBasicInformation", false);
		validateNotInLogs(search, logInvalidItems);
	}
}