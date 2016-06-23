package com.disney.composite.SEReservationGenerator.eventDining;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.AutomationException;
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
public class TestCancel {
	private String reservationNumber;
	private String cancellationNumber;
	private ScheduledEventReservation res;
	
	@BeforeMethod(alwaysRun=true)
	@Parameters("environment")
	public void setup(String environment){
		HouseHold party = new HouseHold(4);
		party.sendToApi(environment);
		
		res = new EventDiningReservation(environment,party);
		res.book("BookGuaranteedTS");
		reservationNumber = res.getConfirmationNumber();
	}
	
	@Test
	public void testCancel(){
		if(reservationNumber != null)
			if(!reservationNumber.isEmpty()){
				TestReporter.log("Travel Plan ID: " + res.getTravelPlanId());
				TestReporter.log("Reservation Number: " + res.getConfirmationNumber());
				res.cancel();
				cancellationNumber = res.getCancellationNumber();
				TestReporter.assertTrue(new Regex().match("[0-9]+", cancellationNumber), "The cancellation number ["+cancellationNumber+"] was not numeric as expected.");
				TestReporter.assertEquals(res.getStatus(), "Cancelled", "The reservation status ["+res.getStatus()+"] was not 'Cancelled' as expected.");
			}else throw new AutomationException("The reservation number was blank, possibly indicating an error during the prerequisite booking.");
		else throw new AutomationException("The reservation number was null, possibly indicating an error during the prerequisite booking.");
	}
}