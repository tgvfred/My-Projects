package com.disney.composite.SEReservationGenerator.eventDining;

import org.testng.annotations.Test;

import com.disney.composite.BaseTest;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.staging.bookSEReservation.EventDiningReservation;
import com.disney.utils.dataFactory.staging.bookSEReservation.ScheduledEventReservation;

public class TestValidateBooking extends BaseTest{
	private ThreadLocal<ScheduledEventReservation> res = new ThreadLocal<ScheduledEventReservation>();
	private String scenarioToValidate = "BookGuaranteedTS";
	
	@Test
	public void testValidateBooking_DefaultScenario(){
		res.set(new EventDiningReservation(this.environment));
		res.get().validateBooking();
		TestReporter.assertEquals(res.get().getValidateBookingStatus(),"false", "The 'Stop Reservation' value ["+res.get().getValidateBookingStatus()+"] was not 'false' as expected.");
	}
	
	@Test
	public void testValidateBooking(){
		res.set(new EventDiningReservation(this.environment));
		res.get().validateBooking(scenarioToValidate);
		TestReporter.assertEquals(res.get().getValidateBookingStatus(),"false", "The 'Stop Reservation' value ["+res.get().getValidateBookingStatus()+"] was not 'false' as expected.");
	}
}