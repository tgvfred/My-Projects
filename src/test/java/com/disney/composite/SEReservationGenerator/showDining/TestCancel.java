package com.disney.composite.SEReservationGenerator.showDining;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.BaseTest;
import com.disney.utils.Regex;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.guestFactory.HouseHold;
import com.disney.utils.dataFactory.staging.bookSEReservation.ScheduledEventReservation;
import com.disney.utils.dataFactory.staging.bookSEReservation.ShowDiningReservation;

/**
 * This test class tests the ability to cancel an event dining reservation
 * @author Justin Phlegar
 *
 */
public class TestCancel extends BaseTest{
	private String cancellationNumber;
	private ScheduledEventReservation res;
	
	@BeforeMethod(alwaysRun=true)
	@Parameters("environment")
	public void setup(String environment){
		HouseHold party = new HouseHold(4);
		party.sendToApi(environment);
		
		res = new ShowDiningReservation(environment,party);
		res.book(ScheduledEventReservation.ONECOMPONENTSNOADDONS);
	}
	
	@Test
	public void testCancel(){
		TestReporter.log("Travel Plan ID: " + res.getTravelPlanId());
		TestReporter.log("Reservation Number: " + res.getConfirmationNumber());
		res.cancel();
		cancellationNumber = res.getCancellationNumber();
		TestReporter.assertTrue(Regex.match("[0-9]+", cancellationNumber), "The cancellation number ["+cancellationNumber+"] was not numeric as expected.");
		TestReporter.assertEquals(res.getStatus(), "Cancelled", "The reservation status ["+res.getStatus()+"] was not 'Cancelled' as expected.");
	}
}