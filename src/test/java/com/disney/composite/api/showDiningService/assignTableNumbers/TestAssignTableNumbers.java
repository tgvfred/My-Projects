package com.disney.composite.api.showDiningService.assignTableNumbers;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.utils.Randomness;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.guestFactory.HouseHold;
import com.disney.utils.dataFactory.staging.bookSEReservation.ScheduledEventReservation;
import com.disney.utils.dataFactory.staging.bookSEReservation.ShowDiningReservation;

public class TestAssignTableNumbers {

	// *******************
	// Test Class Fields
	// *******************
	protected String environment;
	private ScheduledEventReservation res;
	private HouseHold party;
	private String tableNumber = String.valueOf(Randomness.randomNumberBetween(1, 99));

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
	public void testAssignedTableNumber() {
		tableNumber = res.getTableNumber();
		res.assignTableNumbers();
		TestReporter.assertEquals(res.getAssignTableNumberStatus(), "SUCCESS", "The status ["+res.getStatus()+"] was not 'SUCCESS' as expected.");
		TestReporter.assertEquals(tableNumber, res.getTableNumber(), "The table number ["+res.getTableNumber()+"] did not match the expected table number ["+tableNumber+"].");
	}
}
