package com.disney.composite.api.showDiningService.reprintTicket;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.guestFactory.HouseHold;
import com.disney.utils.dataFactory.staging.bookSEReservation.ScheduledEventReservation;
import com.disney.utils.dataFactory.staging.bookSEReservation.ShowDiningReservation;

public class TestReprintTicket {
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
		res.printTicket();
	}
	
	@AfterMethod(alwaysRun=true)
	public void teardown(){
		if(res.getConfirmationNumber() != null)
			if(!res.getConfirmationNumber().isEmpty())
				res.cancel();
	}

	@Test(groups = {"api", "regression", "dining", "showDiningService"})
	public void testReprintTicket() {
		res.reprintTicket();
		TestReporter.assertEquals(res.getReprintTicketStatus(), "SUCCESS", "The status ["+res.getReprintTicketStatus()+"] was not 'SUCCESS' as expected.");
	}
}