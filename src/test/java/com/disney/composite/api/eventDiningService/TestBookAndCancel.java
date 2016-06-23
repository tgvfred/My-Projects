package com.disney.composite.api.eventDiningService;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.utils.Regex;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.guestFactory.HouseHold;
import com.disney.utils.dataFactory.staging.bookSEReservation.EventDiningReservation;
import com.disney.utils.dataFactory.staging.bookSEReservation.ScheduledEventReservation;

public class TestBookAndCancel {
	// Defining global variables
	protected String environment;
	protected String TPS_ID = null;
	protected ScheduledEventReservation res;
	protected HouseHold hh = null;
	
	@BeforeMethod(alwaysRun = true)
	@Parameters({ "environment" })
	public void setup(
		String environment){this.environment = environment;
		hh = new HouseHold(1);
		hh.sendToApi(this.environment);
	}

	@Test(groups = {"api", "regression", "dining", "eventDiningService"})
	public void testBook(){
		res = new EventDiningReservation(environment, hh);
		res.book(ScheduledEventReservation.NOCOMPONENTSNOADDONS);
		TestReporter.assertTrue(new Regex().match("[0-9]+", res.getTravelPlanId()), "The travel plan ID ["+res.getTravelPlanId()+"] is not numeric as expected.");
		TestReporter.assertTrue(new Regex().match("[0-9]+", res.getConfirmationNumber()), "The reservation number ["+res.getConfirmationNumber()+"] is not numeric as expected.");
		TestReporter.assertEquals(res.getStatus(), "Booked", "The reservation status ["+res.getStatus()+"] was not 'Booked' as expected.");
	}
	
	@Test(dependsOnMethods = {"testBook"}, groups = {"api", "regression", "dining", "eventDiningService"})
	public void testCancel() {
		res.cancel();
		TestReporter.assertTrue(new Regex().match("[0-9]+", res.getCancellationNumber()), "The cancellation number ["+res.getCancellationNumber()+"] was not numeric as expected.");
		TestReporter.assertEquals(res.getStatus(), "Cancelled", "The reservation status ["+res.getStatus()+"] was not 'Cancelled' as expected.");
	}

}