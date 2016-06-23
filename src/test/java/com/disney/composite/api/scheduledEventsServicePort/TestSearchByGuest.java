package com.disney.composite.api.scheduledEventsServicePort;

import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.scheduledEventsServicePort.operations.SearchByGuest;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.guestFactory.HouseHold;
import com.disney.utils.dataFactory.staging.bookSEReservation.EventDiningReservation;
import com.disney.utils.dataFactory.staging.bookSEReservation.ScheduledEventReservation;

public class TestSearchByGuest {
	// Defining global variables
	protected String environment = null;
	protected ScheduledEventReservation res = null;
	protected HouseHold hh = null; 
	
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
	public void testSearchByGuest(){

		TestReporter.logStep("Search By Guest");
		SearchByGuest search = new SearchByGuest(environment, "Main");
		search.setReservationNumber(res.getConfirmationNumber());
		search.sendRequest();
		TestReporter.logAPI(!search.getResponseStatusCode().equals("200"), "An error occurred during the search.", search);
		TestReporter.assertEquals(search.getReservationNumber(), res.getConfirmationNumber(), "The actual reservation number ["+search.getReservationNumber()+"] did not match the expected reservation number ["+res.getConfirmationNumber()+"].");
		TestReporter.assertEquals(search.getFacilityId(), res.getFacilityId(), "The actual facility ID ["+search.getFacilityId()+"] did not match the expected facility ID ["+res.getFacilityId()+"].");
	}
}
