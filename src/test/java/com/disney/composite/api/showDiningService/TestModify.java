package com.disney.composite.api.showDiningService;

import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.guestFactory.HouseHold;
import com.disney.utils.dataFactory.staging.bookSEReservation.ScheduledEventReservation;
import com.disney.utils.dataFactory.staging.bookSEReservation.ShowDiningReservation;

/**
 * This class contains all fields, methods and classes required to test
 * 
 * @author Venkatesh A
 * @version 1/11/2016 Venkatesh A - Original
 */

public class TestModify {
	protected String environment;
	private ScheduledEventReservation res;
	private HouseHold party;
	private int partySize = 3;
	
	@BeforeMethod(alwaysRun = true)
	@Parameters({ "environment" })
	public void setup(String environment) {
		this.environment = environment;
		party = new HouseHold(1);
		party.sendToApi(environment);
		res = new ShowDiningReservation(environment, party);
		res.book(ScheduledEventReservation.ONECOMPONENTSNOADDONS);
	}
	
	@AfterMethod(alwaysRun=true)
	public void teardown(){
		if(res.getConfirmationNumber() != null)
			if(!res.getConfirmationNumber().isEmpty())
				res.cancel();
	}

	@Test(groups = {"api", "regression", "dining", "showDiningService"})
	public void testModify() {
		party = new HouseHold(partySize);
		res.modify().modifyPartyMix(party);
		TestReporter.assertEquals(res.getStatus(), "Booked", "The reservation status ["+res.getStatus()+"] was not 'Booked' as expected.");
		TestReporter.assertEquals(partySize, res.getNumberOfGuests(), "The number of guest ["+res.getNumberOfGuests()+"] does not equal the expected number of guests ["+partySize+"]");
	}
}
