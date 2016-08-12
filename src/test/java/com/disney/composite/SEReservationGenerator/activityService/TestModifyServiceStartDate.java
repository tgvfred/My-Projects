package com.disney.composite.SEReservationGenerator.activityService;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import com.disney.composite.BaseTest;
import com.disney.utils.Randomness;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.guestFactory.HouseHold;
import com.disney.utils.dataFactory.staging.bookSEReservation.ActivityEventReservation;
import com.disney.utils.dataFactory.staging.bookSEReservation.ScheduledEventReservation;

/**
 * This test class tests the ability to generate a user-defined household and use that household to book an event dining reservation
 * @author Justin Phlegar
 *
 */
public class TestModifyServiceStartDate extends BaseTest{
	private ThreadLocal<ScheduledEventReservation> res = new ThreadLocal<ScheduledEventReservation>();
	private String serviceStartDate = Randomness.generateCurrentXMLDatetime(60);
	
	@AfterMethod(alwaysRun=true)
	public void teardown(){
		try{res.get().cancel();}
		catch(Exception e){}
	}
	
	@Test
	public void testModifyServiceStartDate_ChildActivity(){	
		HouseHold party = new HouseHold(1);
		party.primaryGuest().setAge("9");
		res.set(new ActivityEventReservation(environment, party));
		res.get().book(ScheduledEventReservation.NOCOMPONENTSNOADDONS);
		
		res.get().modify().modifyServiceStartDate(serviceStartDate);
		TestReporter.assertEquals(res.get().getModifyResponseStatus(), "SUCCESS", "The status from modification ["+res.get().getModifyResponseStatus()+"] was not 'SUCCESS' as expected.");
	}
	
	@Test
	public void testModifyServiceStartDate__RecreationActivity(){		
		res.set(new ActivityEventReservation(environment));
		res.get().book(ScheduledEventReservation.ONECOMPONENTSNOADDONS);
		
		res.get().modify().modifyServiceStartDate(serviceStartDate, ScheduledEventReservation.ONECOMPONENTSNOADDONS);
		TestReporter.assertEquals(res.get().getModifyResponseStatus(), "SUCCESS", "The status from modification ["+res.get().getModifyResponseStatus()+"] was not 'SUCCESS' as expected.");		
	}
}