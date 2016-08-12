package com.disney.composite.SEReservationGenerator.eventDining;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.composite.BaseTest;
import com.disney.utils.Regex;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.guestFactory.HouseHold;
import com.disney.utils.dataFactory.staging.bookSEReservation.EventDiningReservation;
import com.disney.utils.dataFactory.staging.bookSEReservation.ScheduledEventReservation;

/**
 * This test class tests the ability update the status of an event dining reservation to 'Arrived'
 * @author Justin Phlegar
 *
 */
public class TestNoShow extends BaseTest{
	private ScheduledEventReservation res;
	private String cancellationNumber;
	
	@Override
	@BeforeMethod(alwaysRun=true)
	@Parameters("environment")
	public void setup(String environment){
		HouseHold party = new HouseHold(4);
		party.sendToApi(environment);
		
		res = new EventDiningReservation(environment,party);
		res.book("BookGuaranteedTS");
	}
	
	@Test
	public void testNoShow(){
		TestReporter.log("Travel Plan ID: " + res.getTravelPlanId());
		TestReporter.log("Reservation Number: " + res.getConfirmationNumber());
		res.noShow();
		cancellationNumber = res.getCancellationNumber();
		TestReporter.assertTrue(Regex.match("[0-9]+", cancellationNumber), "The cancellation number ["+cancellationNumber+"] was not numeric as expected.");
		TestReporter.assertEquals(res.getStatus(), "No Show", "The reservation status ["+res.getStatus()+"] was not 'No Show' as expected.");
	}
}