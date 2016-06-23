package com.disney.composite.SEReservationGenerator.showDining;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.AutomationException;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.guestFactory.HouseHold;
import com.disney.utils.dataFactory.staging.bookSEReservation.ScheduledEventReservation;
import com.disney.utils.dataFactory.staging.bookSEReservation.ShowDiningReservation;

/**
 * This test class tests the ability update the status of an show dining reservation to 'Arrived'
 * @author Justin Phlegar
 *
 */
public class TestArrived {
	private String reservationNumber;
	private ScheduledEventReservation res;
	
	@BeforeMethod(alwaysRun=true)
	@Parameters("environment")
	public void setup(String environment){
		HouseHold party = new HouseHold(4);
		party.sendToApi(environment);
		
		res = new ShowDiningReservation(environment,party);
		res.book(ScheduledEventReservation.ONECOMPONENTSNOADDONS);
		reservationNumber = res.getConfirmationNumber();
	}
	
	@Test
	public void testArrived(){
		if(reservationNumber != null)
			if(!reservationNumber.isEmpty()){
				TestReporter.log("Travel Plan ID: " + res.getTravelPlanId());
				TestReporter.log("Reservation Number: " + res.getConfirmationNumber());
				res.arrived();
				TestReporter.assertEquals(res.getArrivedStatus(), "SUCCESS", "Arrived status ["+res.getArrivedStatus()+"] was not 'SUCCESS' as expected.");
				TestReporter.assertEquals(res.getStatus(), "Arrived", "The reservation status ["+res.getStatus()+"] was not 'Arrived' as expected.");
			}else throw new AutomationException("The reservation number was blank, possibly indicating an error during the prerequisite booking.");
		else throw new AutomationException("The reservation number was null, possibly indicating an error during the prerequisite booking.");
	}
}