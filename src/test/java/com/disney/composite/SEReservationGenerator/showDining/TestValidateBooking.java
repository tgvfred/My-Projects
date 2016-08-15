package com.disney.composite.SEReservationGenerator.showDining;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.composite.BaseTest;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.staging.bookSEReservation.ScheduledEventReservation;
import com.disney.utils.dataFactory.staging.bookSEReservation.ShowDiningReservation;

public class TestValidateBooking extends BaseTest{
	protected ThreadLocal<ScheduledEventReservation> res = new ThreadLocal<ScheduledEventReservation>();
	private String scenarioToValidate = "DinnerShowWithAddOn";
	
	@BeforeMethod(alwaysRun=true)
	@Parameters("environment")
	public void setup(String environment){
		this.environment = environment;
		res.set(new ShowDiningReservation(this.environment));
	}
	
	@Test
	public void testValidateBooking_DefaultScenario(){
		res.get().validateBooking();
		TestReporter.assertEquals(res.get().getValidateBookingStatus(),"false", "The 'Stop Reservation' value ["+res.get().getValidateBookingStatus()+"] was not 'false' as expected.");
	}
	
	@Test
	public void testValidateBooking(){
		res.get().validateBooking(scenarioToValidate);
		TestReporter.assertEquals(res.get().getValidateBookingStatus(),"false", "The 'Stop Reservation' value ["+res.get().getValidateBookingStatus()+"] was not 'false' as expected.");
	}
}