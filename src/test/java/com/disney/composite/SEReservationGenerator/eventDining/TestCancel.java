package com.disney.composite.SEReservationGenerator.eventDining;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.BaseTest;
import com.disney.utils.Regex;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.guestFactory.HouseHold;
import com.disney.utils.dataFactory.staging.bookSEReservation.EventDiningReservation;
import com.disney.utils.dataFactory.staging.bookSEReservation.ScheduledEventReservation;

/**
 * This test class tests the ability to cancel an event dining reservation
 * @author Justin Phlegar
 *
 */
public class TestCancel extends BaseTest{
	private ScheduledEventReservation res;
	private String cancellationNumber;
	
	@Override
	@BeforeMethod(alwaysRun=true)
	@Parameters("environment")
	public void setup(String environment){
		this.environment = environment;
		HouseHold party = new HouseHold(4);
		party.sendToApi(environment);
		
		res = new EventDiningReservation(environment, party);
		res.book("BookGuaranteedTS");
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