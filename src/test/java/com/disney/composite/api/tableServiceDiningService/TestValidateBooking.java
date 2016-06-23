package com.disney.composite.api.tableServiceDiningService;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.staging.bookSEReservation.ScheduledEventReservation;
import com.disney.utils.dataFactory.staging.bookSEReservation.TableServiceDiningReservation;

public class TestValidateBooking {
	private String environment;
	protected ScheduledEventReservation res = null;
	
	@BeforeMethod(alwaysRun = true)
	@Parameters({ "environment" })
	public void setup(String environment) {
		this.environment = environment;
		res = new TableServiceDiningReservation(this.environment);
	}

	@Test(groups = {"api", "regression", "dining", "eventDiningService"})
	public void testValidateBooking(){
		res.validateBooking();
		TestReporter.assertEquals(res.getValidateBookingStatus(), "false", "The validation status ["+res.getValidateBookingStatus()+"] was not 'false' as expected.");
	}
}
