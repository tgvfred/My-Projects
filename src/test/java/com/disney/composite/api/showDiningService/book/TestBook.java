package com.disney.composite.api.showDiningService.book;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.utils.Regex;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.guestFactory.HouseHold;
import com.disney.utils.dataFactory.staging.bookSEReservation.ScheduledEventReservation;
import com.disney.utils.dataFactory.staging.bookSEReservation.ShowDiningReservation;

public class TestBook {
	protected String environment;
	protected String TP_ID = null;
	protected String TPS_ID = null;
	private ScheduledEventReservation res;
	private HouseHold party;
	
	@BeforeMethod(alwaysRun = true)
	@Parameters({ "environment" })
	public void setup(String environment) {
		this.environment = environment;
		party = new HouseHold(1);
		party.sendToApi(environment);
	}

	@AfterMethod(alwaysRun=true)
	public void teardown(){
		if(res != null)
			if(!res.getConfirmationNumber().isEmpty()){
				res.cancel();
			}				
	}
	
	@Test(groups = {"api", "regression", "dining", "showDiningService"})
	public void testBook() {
		res = new ShowDiningReservation(environment, party);
		res.book(ScheduledEventReservation.ONECOMPONENTSNOADDONS);
		TP_ID = res.getTravelPlanId();
		TPS_ID = res.getConfirmationNumber();
		TestReporter.assertTrue(Regex.match("[0-9]+", TP_ID), "The travel plan ID ["+TP_ID+"] was not numeric as expected.");
		TestReporter.assertTrue(Regex.match("[0-9]+", TPS_ID), "The reservation number ["+TPS_ID+"] was not numeric as expected.");
		TestReporter.assertEquals(res.getStatus(), "Booked", "The reservation status ["+res.getStatus()+"] was not 'Booked' as expected.");
	}
}
