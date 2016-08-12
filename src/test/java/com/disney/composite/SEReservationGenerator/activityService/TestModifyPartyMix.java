package com.disney.composite.SEReservationGenerator.activityService;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import com.disney.composite.BaseTest;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.guestFactory.HouseHold;
import com.disney.utils.dataFactory.staging.bookSEReservation.ActivityEventReservation;
import com.disney.utils.dataFactory.staging.bookSEReservation.ScheduledEventReservation;

/**
 * This test class tests the ability to generate a user-defined household and use that household to book an event dining reservation
 * @author Justin Phlegar
 *
 */
public class TestModifyPartyMix extends BaseTest{
	private ThreadLocal<ScheduledEventReservation> res = new ThreadLocal<ScheduledEventReservation>();
	private HouseHold childParty;
	private HouseHold recParty;
	private String childAge = "9";
	private String modChildAge = "5";
	private int recPartySize = 1;
	private int recModPartySize = 2;
	
	@AfterMethod(alwaysRun=true)
	public void teardown(){
		try{res.get().cancel();}
		catch(Exception e){}
	}
	
	@Test
	public void testModifyPartyMix_ChildActivity(){	
		childParty = new HouseHold("1 Child");
		childParty.primaryGuest().setAge(childAge);
		
		res.set(new ActivityEventReservation(environment, childParty));
		res.get().book(ScheduledEventReservation.NOCOMPONENTSNOADDONS);
		
		childParty = new HouseHold("1 Child");
		childParty.primaryGuest().setAge(modChildAge);
		res.get().modify().modifyPartyMix(childParty);
		TestReporter.assertEquals(modChildAge, res.get().getPrimaryGuestAge(), "The primary guest age ["+res.get().getPrimaryGuestAge()+"] did not match the expected age ["+modChildAge+"].");
	}
	
	@Test
	public void testModifyPartyMix_RecreationActivity(){
		recParty = new HouseHold(recPartySize);
		
		res.set(new ActivityEventReservation(environment, recParty));
		res.get().book(ScheduledEventReservation.ONECOMPONENTSNOADDONS);
		
		recParty = new HouseHold(recModPartySize);
		res.get().modify().modifyPartyMix(recParty, ScheduledEventReservation.ONECOMPONENTSNOADDONS);
		TestReporter.assertEquals(recModPartySize, res.get().getNumberOfGuests(), "The number of guests ["+res.get().getNumberOfGuests()+"] did not match the expected party size ["+recModPartySize+"].");
	}
}