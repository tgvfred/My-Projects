package com.disney.composite.SEReservationGenerator.tableServiceDining;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.AutomationException;
import com.disney.utils.Regex;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.guestFactory.HouseHold;
import com.disney.utils.dataFactory.staging.bookSEReservation.ScheduledEventReservation;
import com.disney.utils.dataFactory.staging.bookSEReservation.TableServiceDiningReservation;

/**
 * This test class tests the ability update the status of an event dining reservation to 'Arrived'
 * @author Justin Phlegar
 *
 */
public class TestNoShow {
	private String reservationNumber;
	private ScheduledEventReservation res;
	private String cancellationNumber;
	
	@BeforeMethod(alwaysRun=true)
	@Parameters("environment")
	public void setup(String environment){
		HouseHold party = new HouseHold(4);
		party.sendToApi(environment);
		
		res = new TableServiceDiningReservation(environment,party);
		res.book(ScheduledEventReservation.NOCOMPONENTSNOADDONS);
		reservationNumber = res.getConfirmationNumber();
	}
	
	@Test
	public void testNoShow(){
		if(reservationNumber != null)
			if(!reservationNumber.isEmpty()){
				TestReporter.log("Travel Plan ID: " + res.getTravelPlanId());
				TestReporter.log("Reservation Number: " + res.getConfirmationNumber());
				res.noShow();
				cancellationNumber = res.getCancellationNumber();
				TestReporter.assertTrue(new Regex().match("[0-9]+", cancellationNumber), "The cancellation number ["+cancellationNumber+"] was not numeric as expected.");
				TestReporter.assertEquals(res.getStatus(), "No Show", "The reservation status ["+res.getStatus()+"] was not 'No Show' as expected.");
			}else throw new AutomationException("The reservation number was blank, possibly indicating an error during the prerequisite booking.");
		else throw new AutomationException("The reservation number was null, possibly indicating an error during the prerequisite booking.");
	}
}