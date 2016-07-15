package com.disney.composite.SEReservationGenerator.activityService;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

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
public class TestModifyServiceStartDate {
	private String environment;
	private String reservationNumber;
	private ScheduledEventReservation res;
	private String serviceStartDate = Randomness.generateCurrentXMLDatetime(60);
	
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
	public void testModifyServiceStartDate_ChildActivity(){	
		HouseHold party = new HouseHold(1);
		party.primaryGuest().setAge("9");
		res = new ActivityEventReservation(environment, party);
		res.book(ScheduledEventReservation.NOCOMPONENTSNOADDONS);
		reservationNumber = res.getConfirmationNumber();
		
		res.modify().modifyServiceStartDate(serviceStartDate);
		TestReporter.assertEquals(res.getModifyResponseStatus(), "SUCCESS", "The status from modification ["+res.getModifyResponseStatus()+"] was not 'SUCCESS' as expected.");
	}
	
	@Test
	public void testModifyServiceStartDate__RecreationActivity(){		
		res = new ActivityEventReservation(environment);
		res.book(ScheduledEventReservation.ONECOMPONENTSNOADDONS);
		reservationNumber = res.getConfirmationNumber();
		
		res.modify().modifyServiceStartDate(serviceStartDate, ScheduledEventReservation.ONECOMPONENTSNOADDONS);
		TestReporter.assertEquals(res.getModifyResponseStatus(), "SUCCESS", "The status from modification ["+res.getModifyResponseStatus()+"] was not 'SUCCESS' as expected.");		
	}
}