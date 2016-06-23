package com.disney.composite.SEReservationGenerator.activityService;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.guestFactory.HouseHold;
import com.disney.utils.dataFactory.staging.bookSEReservation.ActivityEventReservation;
import com.disney.utils.dataFactory.staging.bookSEReservation.ScheduledEventReservation;

/**
 * This test class tests the ability to generate a user-defined household and use that household to book an show dining reservation
 * @author Justin Phlegar
 *
 */
public class TestRetrieve {
	private String environment;
	private String reservationNumber;
	private ScheduledEventReservation res;
	private int partySize = 1;
	
	@BeforeMethod(alwaysRun=true)
	@Parameters("environment")
	public void setup(String environment){this.environment = environment;}
	
	@AfterMethod(alwaysRun=true)
	public void teardown(){
		if(reservationNumber != null)
			if(!reservationNumber.isEmpty())
				res.cancel();
	}
	
	@Test
	public void testRetrieve_ChildActivity(){	
		HouseHold party = new HouseHold(1);
		party.primaryGuest().setAge("9");
		res = new ActivityEventReservation(environment, party);
		res.book(ScheduledEventReservation.NOCOMPONENTSNOADDONS);
		reservationNumber = res.getConfirmationNumber();

		res.retrieve();
		TestReporter.assertEquals(partySize, res.getNumberOfGuests(), "The number of guests ["+res.getNumberOfGuests()+"] did not match the expected number of guests ["+partySize+"].");
	}
	
	@Test
	public void testRetrieve_RecreationActivity(){		
		res = new ActivityEventReservation(environment);
		res.book(ScheduledEventReservation.ONECOMPONENTSNOADDONS);
		reservationNumber = res.getConfirmationNumber();

		res.retrieve();
		TestReporter.assertEquals(partySize, res.getNumberOfGuests(), "The number of guests ["+res.getNumberOfGuests()+"] did not match the expected number of guests ["+partySize+"].");		
	}
}