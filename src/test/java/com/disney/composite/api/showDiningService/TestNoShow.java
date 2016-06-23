package com.disney.composite.api.showDiningService;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.utils.Regex;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.guestFactory.HouseHold;
import com.disney.utils.dataFactory.staging.bookSEReservation.ScheduledEventReservation;
import com.disney.utils.dataFactory.staging.bookSEReservation.ShowDiningReservation;

public class TestNoShow {
	protected String environment;
	private ScheduledEventReservation res;
	private HouseHold party;
	private String cancellationNumber;
	
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
	public void testNoShow() {
		res.noShow();
		cancellationNumber = res.getCancellationNumber();
		TestReporter.assertTrue(new Regex().match("[0-9]+", cancellationNumber), "The cancellation number ["+cancellationNumber+"] was not numeric as expected.");
		TestReporter.assertEquals(res.getStatus(), "No Show", "The reservation status ["+res.getStatus()+"] was not 'No Show' as expected.");	
	}
}
