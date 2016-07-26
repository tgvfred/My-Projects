package com.disney.composite.api.scheduledEventsServicePort.searchByVenue;

import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.core.BaseSoapCommands;
import com.disney.api.soapServices.core.exceptions.XPathNullNodeValueException;
import com.disney.api.soapServices.scheduledEventsServicePort.operations.SearchByVenue;
import com.disney.composite.BaseTest;
import com.disney.utils.Randomness;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.database.LogItems;
import com.disney.utils.dataFactory.guestFactory.HouseHold;
import com.disney.utils.dataFactory.staging.bookSEReservation.ScheduledEventReservation;
import com.disney.utils.dataFactory.staging.bookSEReservation.TableServiceDiningReservation;

public class TestSearchByVenue extends BaseTest{
	protected ScheduledEventReservation res = null;
	
	@Override
	@BeforeMethod(alwaysRun = true)
	@Parameters({ "environment" })
	public void setup(
		String environment){this.environment = environment;
		hh = new HouseHold(1);
		hh.sendToApi(this.environment);
		res = new TableServiceDiningReservation(environment, hh);
		res.book(ScheduledEventReservation.NOCOMPONENTSNOADDONS);
	}
	
	@AfterMethod(alwaysRun = true)
	public void closeSession(ITestResult test) {
		if(res != null)
			if(!res.getConfirmationNumber().isEmpty())
				res.cancel();
	}

	@Test(groups = {"api", "regression", "dining", "scheduledEventsServicePort"})
	public void testSearchByVenue(){
		TestReporter.logStep("Search By Venue");
		SearchByVenue search = new SearchByVenue(environment, "Main");
		search = new SearchByVenue(environment, "Main");
		search.setFacilityId(res.getFacilityId());
		search.setServiceWindowEnd(Randomness.generateCurrentXMLDatetime(Integer.parseInt("90")));
		search.setServiceWindowStart(Randomness.generateCurrentXMLDatetime(Integer.parseInt("-90")));
		try{search.setSourceAccountingCenter(res.getSourceAccountingCenter());}
		catch(XPathNullNodeValueException e){search.setSourceAccountingCenter(BaseSoapCommands.REMOVE_NODE.toString());}
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