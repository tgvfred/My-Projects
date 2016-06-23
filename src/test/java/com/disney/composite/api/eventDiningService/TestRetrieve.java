package com.disney.composite.api.eventDiningService;

import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.guestFactory.HouseHold;
import com.disney.utils.dataFactory.staging.bookSEReservation.EventDiningReservation;
import com.disney.utils.dataFactory.staging.bookSEReservation.ScheduledEventReservation;

public class TestRetrieve {
	// Defining global variables
	protected String environment;
	protected ScheduledEventReservation res = null;
	protected HouseHold hh = null;
	
	@BeforeMethod(alwaysRun = true)
	@Parameters({ "environment" })
	public void setup(
		String environment){this.environment = environment;
		hh = new HouseHold(2);
		hh.sendToApi(this.environment);
		res = new EventDiningReservation(environment, hh);
		res.book(ScheduledEventReservation.NOCOMPONENTSNOADDONS);
	}

	@AfterMethod(alwaysRun = true)
	public synchronized void closeSession(ITestResult test) {res.cancel();}

	@Test(groups = {"api", "regression", "dining", "eventDiningService"})
	public void testRetrieve(){
		TestReporter.assertEquals(res.getNumberOfGuests(), hh.getAllGuests().size(), "The number of guests ["+res.getNumberOfGuests()+"] does not match the number of expected guests ["+hh.getAllGuests().size()+"].");
		}
}
