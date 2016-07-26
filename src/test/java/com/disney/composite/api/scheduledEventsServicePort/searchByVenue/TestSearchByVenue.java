package com.disney.composite.api.scheduledEventsServicePort.searchByVenue;

import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.scheduledEventsServicePort.operations.SearchByVenue;
import com.disney.composite.BaseTest;
import com.disney.utils.Randomness;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.database.LogItems;
import com.disney.utils.dataFactory.guestFactory.HouseHold;
import com.disney.utils.dataFactory.staging.bookSEReservation.EventDiningReservation;
import com.disney.utils.dataFactory.staging.bookSEReservation.ScheduledEventReservation;

public class TestSearchByVenue extends BaseTest{
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
	public synchronized void closeSession(ITestResult test) {res.cancel();}

	@Test(groups = {"api", "regression", "dining", "scheduledEventsServicePort"})
	public void testSearchByVenue(){
		TestReporter.logStep("Search By Venue");
		SearchByVenue search = new SearchByVenue(environment, "Main");
		search = new SearchByVenue(environment, "Main");
		search.setServiceWindowEnd(Randomness.generateCurrentXMLDatetime(Integer.parseInt("30")));
		search.setServiceWindowStart(Randomness.generateCurrentXMLDatetime(Integer.parseInt("-30")));
		search.sendRequest();
		TestReporter.logAPI(!search.getResponseStatusCode().equals("200"), "An error occurred during the search.", search);
		TestReporter.assertTrue(search.getEventReservations().getLength() > 0, "No reservations were returned for facility id ["+res.getFacilityId()+"].");
		
		LogItems logItems = new LogItems();
		logItems.addItem("ScheduledEventsServiceIF", "searchByVenue", false);
		logItems.addItem("FolioServiceIF", "retrieveFolioPaymentSummary", false);
		logItems.addItem("PartyIF", "retrieveParty", false);
		logItems.addItem("PartyIF", "retrievePartyBasicInformation", false);		
		validateLogs(search, logItems);
	}
}
