package com.disney.composite.api.scheduledEventsServicePort.searchByVenue;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.core.BaseSoapCommands;
import com.disney.api.soapServices.scheduledEventsServicePort.operations.SearchByVenue;
import com.disney.api.soapServices.tableServiceDiningServicePort.operations.Book;
import com.disney.api.soapServices.tableServiceDiningServicePort.operations.Cancel;
import com.disney.composite.BaseTest;
import com.disney.utils.Randomness;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.database.LogItems;
import com.disney.utils.dataFactory.guestFactory.HouseHold;
import com.disney.utils.dataFactory.staging.bookSEReservation.ScheduledEventReservation;

public class TestSearchByVenue extends BaseTest{
	private String facilityId = "266442";
	private String serviceDate = Randomness.generateCurrentXMLDatetime(1);
	private String bookingStatus = "Booked";
	private String TPS_ID;
	
	@Override
	@BeforeMethod(alwaysRun = true)
	@Parameters({ "environment" })
	public void setup(String environment){
		this.environment = environment;
		hh = new HouseHold(1);
		Book res = new Book(environment, ScheduledEventReservation.NOCOMPONENTSNOADDONS);
		res.setParty(hh);
		res.setFacilityId(facilityId);
		res.setServiceStartDateTime(serviceDate);
		res.sendRequest();
		TestReporter.logAPI(!res.getResponseStatusCode().equals("200"), "An error occurred booking a table service reservation: " + res.getFaultString(), res);
		TPS_ID = res.getTravelPlanSegmentId();
	}
	
	@AfterMethod(alwaysRun = true)
	public void closeSession() {
		if(TPS_ID != null)
			if(!TPS_ID.isEmpty()){
				Cancel cancel = new Cancel(environment, "Main");
				cancel.setReservationNumber(TPS_ID);
				cancel.sendRequest();
			}
	}

	@Test(groups = {"api", "regression", "dining", "scheduledEventsServicePort"})
	public void testSearchByVenue(){
		TestReporter.logStep("Search By Venue: Facility ID: " + facilityId);
		SearchByVenue search = new SearchByVenue(environment, "Main");
		search.setFacilityId(facilityId);
		search.setServiceDate(serviceDate);
		search.setReservationStatus(bookingStatus);
		search.setServiceWindowEnd(BaseSoapCommands.REMOVE_NODE.toString());
		search.setServiceWindowStart(BaseSoapCommands.REMOVE_NODE.toString());
		search.setSourceAccountingCenter(BaseSoapCommands.REMOVE_NODE.toString());
		search.sendRequest();
		TestReporter.logAPI(!search.getResponseStatusCode().equals("200"), "An error occurred during the search."+ search.getFaultString(), search);
		TestReporter.assertTrue(search.getResponse().contains(TPS_ID), "Verify the response contains the reservation number ["+TPS_ID+"] for facility ID ["+facilityId+"] on the service date ["+serviceDate+"].");
		
		LogItems logItems = new LogItems();
		logItems.addItem("ScheduledEventsServiceIF", "searchByVenue", false);
		logItems.addItem("FolioServiceIF", "retrieveFolioPaymentSummary", false);
		logItems.addItem("PartyIF", "retrieveParty", false);
		logItems.addItem("PartyIF", "retrievePartyBasicInformation", false);		
		validateLogs(search, logItems, 10000);
	}
}