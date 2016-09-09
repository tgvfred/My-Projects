package com.disney.composite.SEReservationGenerator.activityService;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;

import com.disney.api.BaseTest;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.staging.bookSEReservation.ActivityEventReservation;
import com.disney.utils.dataFactory.staging.bookSEReservation.ScheduledEventReservation;

public class TestValidateBooking extends BaseTest{
	private String environment;
	private ScheduledEventReservation res;
	
	@Override
	@BeforeMethod(alwaysRun=true)
	@Parameters("environment")
	public void setup(String environment){
		this.environment = environment;
		res = new ActivityEventReservation(this.environment);
	}
	
//	@Test
	public void testValidateBooking_ChildActivity(){	
		res.validateBooking();
		TestReporter.assertEquals(res.getValidateBookingStatus(),"false", "The 'Stop Reservation' value ["+res.getValidateBookingStatus()+"] was not 'false' as expected.");
	}
	
//	@Test
	public void testValidateBooking_RecreationActivity(){
		res.validateBooking();
		TestReporter.assertEquals(res.getValidateBookingStatus(),"false", "The 'Stop Reservation' value ["+res.getValidateBookingStatus()+"] was not 'false' as expected.");		
	}
}