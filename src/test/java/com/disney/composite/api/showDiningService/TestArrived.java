package com.disney.composite.api.showDiningService;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.guestFactory.HouseHold;
import com.disney.utils.dataFactory.staging.bookSEReservation.ScheduledEventReservation;
import com.disney.utils.dataFactory.staging.bookSEReservation.ShowDiningReservation;

public class TestArrived {

	// *******************
	// Test Class Fields
	// *******************
	protected String environment;
	private ScheduledEventReservation res;
	private HouseHold party;
	
	@BeforeMethod(alwaysRun = true)
	@Parameters({ "environment" })
	public void setup(String environment) {
		this.environment = environment;
		party = new HouseHold(1);
		party.sendToApi(environment);
		res = new ShowDiningReservation(environment, party);
		res.book(ScheduledEventReservation.ONECOMPONENTSNOADDONS);
	}

	@Test(groups = {"api", "regression", "dining", "showDiningService"})
	public void testArrived() {
		res.arrived();
		TestReporter.assertEquals(res.getArrivedStatus(), "SUCCESS", "Arrived status ["+res.getArrivedStatus()+"] was not 'SUCCESS' as expected.");
		TestReporter.assertEquals(res.getStatus(), "Arrived", "The reservation status ["+res.getStatus()+"] was not 'Arrived' as expected.");
	}
}
