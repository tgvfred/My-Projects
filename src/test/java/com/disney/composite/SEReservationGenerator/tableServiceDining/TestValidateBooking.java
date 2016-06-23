package com.disney.composite.SEReservationGenerator.tableServiceDining;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.staging.bookSEReservation.ScheduledEventReservation;
import com.disney.utils.dataFactory.staging.bookSEReservation.TableServiceDiningReservation;

public class TestValidateBooking {
	private String environment;
	private ScheduledEventReservation res;
	private String scenarioToValidate = ScheduledEventReservation.NOCOMPONENTSNOADDONS;
	
	@BeforeMethod(alwaysRun=true)
	@Parameters("environment")
	public void setup(String environment){
		this.environment = environment;
		res = new TableServiceDiningReservation(this.environment);
	}
	
	@Test
	public void testValidateBooking_DefaultScenario(){
		res.validateBooking();
		TestReporter.assertEquals(res.getValidateBookingStatus(),"false", "The 'Stop Reservation' value ["+res.getValidateBookingStatus()+"] was not 'false' as expected.");
	}
	
	@Test
	public void testValidateBooking(){
		res.validateBooking(scenarioToValidate);
		TestReporter.assertEquals(res.getValidateBookingStatus(),"false", "The 'Stop Reservation' value ["+res.getValidateBookingStatus()+"] was not 'false' as expected.");
	}
}