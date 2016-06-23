package com.disney.composite.api.tableServiceDiningService;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.guestFactory.HouseHold;
import com.disney.utils.dataFactory.staging.bookSEReservation.ScheduledEventReservation;
import com.disney.utils.dataFactory.staging.bookSEReservation.TableServiceDiningReservation;

public class TestModify {
	protected String environment = null;
	protected ScheduledEventReservation res = null;
	protected String serviceStartDate = null;
	protected HouseHold hh = null; 
	protected Integer newHouseHoldSize =null; 
	
	@BeforeMethod(alwaysRun = true)
	@Parameters({ "environment" })
	public void setup(String environment) {
		this.environment = environment;
		hh = new HouseHold(1);
		hh.sendToApi(this.environment);
		res = new TableServiceDiningReservation(environment, hh);
		res.book(ScheduledEventReservation.NOCOMPONENTSNOADDONS);
	}

	@AfterMethod(alwaysRun=true)
	public void teardown() {res.cancel();}

	@Test(groups = {"api", "regression", "dining", "tableServiceDiningService"})
	public void testModify(){
		newHouseHoldSize = 2;
		hh = new HouseHold(newHouseHoldSize);
		hh.sendToApi(this.environment);
		res.modify().modifyPartyMix(hh);
		TestReporter.assertEquals(res.getNumberOfGuests(), newHouseHoldSize, "The number of guests ["+res.getNumberOfGuests()+"] does not match the expected number of guests ["+newHouseHoldSize+"].");
	}
}