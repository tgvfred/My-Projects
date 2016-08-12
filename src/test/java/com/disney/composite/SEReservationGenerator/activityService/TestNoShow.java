package com.disney.composite.SEReservationGenerator.activityService;

import org.testng.annotations.Test;

import com.disney.composite.BaseTest;
import com.disney.utils.Regex;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.guestFactory.HouseHold;
import com.disney.utils.dataFactory.staging.bookSEReservation.ActivityEventReservation;
import com.disney.utils.dataFactory.staging.bookSEReservation.ScheduledEventReservation;

/**
 * This test class tests the ability update the status of an event dining reservation to 'Arrived'
 * @author Justin Phlegar
 *
 */
public class TestNoShow extends BaseTest{
	private ThreadLocal<ScheduledEventReservation> res = new ThreadLocal<ScheduledEventReservation>();
	private String cancellationNumber;
	
	@Test
	public void testNoShow_ChildActivity(){	
		HouseHold party = new HouseHold(1);
		party.primaryGuest().setAge("9");
		res.set(new ActivityEventReservation(environment, party));
		res.get().book(ScheduledEventReservation.NOCOMPONENTSNOADDONS);
		
		res.get().noShow();
		cancellationNumber = res.get().getCancellationNumber();
		TestReporter.assertTrue(Regex.match("[0-9]+", cancellationNumber), "The cancellation number ["+cancellationNumber+"] was not numeric as expected.");
		TestReporter.assertEquals(res.get().getStatus(), "No Show", "The reservation status ["+res.get().getStatus()+"] was not 'No Show' as expected.");
	}
	
	@Test
	public void testNoShow_RecreationActivity(){		
		res.set(new ActivityEventReservation(environment));
		res.get().book(ScheduledEventReservation.ONECOMPONENTSNOADDONS);
		
		res.get().noShow();
		cancellationNumber = res.get().getCancellationNumber();
		TestReporter.assertTrue(Regex.match("[0-9]+", cancellationNumber), "The cancellation number ["+cancellationNumber+"] was not numeric as expected.");
		TestReporter.assertEquals(res.get().getStatus(), "No Show", "The reservation status ["+res.get().getStatus()+"] was not 'No Show' as expected.");		
	}
}