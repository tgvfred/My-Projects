package com.disney.composite.api.eventDiningService;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.utils.Regex;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.guestFactory.HouseHold;
import com.disney.utils.dataFactory.staging.bookSEReservation.EventDiningReservation;
import com.disney.utils.dataFactory.staging.bookSEReservation.ScheduledEventReservation;

public class TestNoShow {
	// Defining global variables
	protected String environment;
	protected ScheduledEventReservation res =null;
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

	@Test(groups = {"api", "regression", "dining", "eventDiningService"})
	public void testNoShow(){
		res.noShow();
		TestReporter.assertEquals(res.getStatus(), "No Show", "The reservation status ["+res.getStatus()+"] was not 'No Show' as expected.");
		TestReporter.assertTrue(Regex.match("[0-9]+", res.getCancellationNumber()), "The cancellation number ["+res.getCancellationNumber()+"] was not numeric as expected.");
	}
}
