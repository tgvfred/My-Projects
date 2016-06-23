package com.disney.composite.api.activityService;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.utils.Regex;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.guestFactory.HouseHold;
import com.disney.utils.dataFactory.staging.bookSEReservation.ActivityEventReservation;
import com.disney.utils.dataFactory.staging.bookSEReservation.ScheduledEventReservation;

public class TestNoShow {
	private ScheduledEventReservation res;
	private String cancellationNumber;
	private String environment;
	
	@BeforeMethod(alwaysRun=true)
	@Parameters("environment")
	public void setup(String environment){this.environment = environment;}
	
	@Test
	public void testNoShow_ChildActivity(){	
		HouseHold party = new HouseHold(1);
		party.primaryGuest().setAge("9");
		res = new ActivityEventReservation(environment, party);
		res.book(ScheduledEventReservation.NOCOMPONENTSNOADDONS);
		
		res.noShow();
		cancellationNumber = res.getCancellationNumber();
		TestReporter.assertTrue(new Regex().match("[0-9]+", cancellationNumber), "The cancellation number ["+cancellationNumber+"] was not numeric as expected.");
		TestReporter.assertEquals(res.getStatus(), "No Show", "The reservation status ["+res.getStatus()+"] was not 'No Show' as expected.");
	}
	
	@Test
	public void testNoShow_RecreationActivity(){		
		res = new ActivityEventReservation(environment);
		res.book(ScheduledEventReservation.ONECOMPONENTSNOADDONS);
		
		res.noShow();
		cancellationNumber = res.getCancellationNumber();
		TestReporter.assertTrue(new Regex().match("[0-9]+", cancellationNumber), "The cancellation number ["+cancellationNumber+"] was not numeric as expected.");
		TestReporter.assertEquals(res.getStatus(), "No Show", "The reservation status ["+res.getStatus()+"] was not 'No Show' as expected.");		
	}
}