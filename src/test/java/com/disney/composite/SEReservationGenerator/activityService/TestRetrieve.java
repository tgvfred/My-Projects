package com.disney.composite.SEReservationGenerator.activityService;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import com.disney.composite.BaseTest;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.guestFactory.HouseHold;
import com.disney.utils.dataFactory.staging.bookSEReservation.ActivityEventReservation;
import com.disney.utils.dataFactory.staging.bookSEReservation.ScheduledEventReservation;

/**
 * This test class tests the ability to generate a user-defined household and use that household to book an show dining reservation
 * @author Justin Phlegar
 *
 */
public class TestRetrieve extends BaseTest{
	private ThreadLocal<ScheduledEventReservation> res = new ThreadLocal<ScheduledEventReservation>();
	private int partySize = 1;
	
	@AfterMethod(alwaysRun=true)
	public void teardown(){
		try{res.get().cancel();}
		catch(Exception e){}
	}
	
	@Test
	public void testRetrieve_ChildActivity(){	
		HouseHold party = new HouseHold(1);
		party.primaryGuest().setAge("9");
		res.set(new ActivityEventReservation(environment, party));
		res.get().book(ScheduledEventReservation.NOCOMPONENTSNOADDONS);

		res.get().retrieve();
		TestReporter.assertEquals(partySize, res.get().getNumberOfGuests(), "The number of guests ["+res.get().getNumberOfGuests()+"] did not match the expected number of guests ["+partySize+"].");
	}
	
	@Test
	public void testRetrieve_RecreationActivity(){		
		res.set(new ActivityEventReservation(environment));
		res.get().book(ScheduledEventReservation.ONECOMPONENTSNOADDONS);

		res.get().retrieve();
		TestReporter.assertEquals(partySize, res.get().getNumberOfGuests(), "The number of guests ["+res.get().getNumberOfGuests()+"] did not match the expected number of guests ["+partySize+"].");		
	}
}