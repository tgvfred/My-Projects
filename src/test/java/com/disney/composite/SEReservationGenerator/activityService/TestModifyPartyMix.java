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
 * This test class tests the ability to generate a user-defined household and use that household to book an event dining reservation
 * @author Justin Phlegar
 *
 */
public class TestModifyPartyMix {
	private String environment;
	private String reservationNumber;
	private ScheduledEventReservation res;
	private HouseHold childParty;
	private HouseHold recParty;
	private String childAge = "9";
	private String modChildAge = "5";
	private int recPartySize = 1;
	private int recModPartySize = 2;
	
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
	public void testModifyPartyMix_ChildActivity(){	
		childParty = new HouseHold("1 Child");
		childParty.primaryGuest().setAge(childAge);
		
		res = new ActivityEventReservation(environment, childParty);
		res.book(ScheduledEventReservation.NOCOMPONENTSNOADDONS);
		reservationNumber = res.getConfirmationNumber();
		
		childParty = new HouseHold("1 Child");
		childParty.primaryGuest().setAge(modChildAge);
		res.modify().modifyPartyMix(childParty);
		TestReporter.assertEquals(modChildAge, res.getPrimaryGuestAge(), "The primary guest age ["+res.getPrimaryGuestAge()+"] did not match the expected age ["+modChildAge+"].");
	}
	
	@Test
	public void testModifyPartyMix_RecreationActivity(){
		recParty = new HouseHold(recPartySize);
		
		res = new ActivityEventReservation(environment, recParty);
		res.book(ScheduledEventReservation.ONECOMPONENTSNOADDONS);
		reservationNumber = res.getConfirmationNumber();
		
		recParty = new HouseHold(recModPartySize);
		res.modify().modifyPartyMix(recParty, ScheduledEventReservation.ONECOMPONENTSNOADDONS);
		TestReporter.assertEquals(recModPartySize, res.getNumberOfGuests(), "The number of guests ["+res.getNumberOfGuests()+"] did not match the expected party size ["+recModPartySize+"].");
	}
}