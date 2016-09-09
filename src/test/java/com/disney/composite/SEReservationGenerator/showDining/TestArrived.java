package com.disney.composite.SEReservationGenerator.showDining;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.BaseTest;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.guestFactory.HouseHold;
import com.disney.utils.dataFactory.staging.bookSEReservation.ScheduledEventReservation;
import com.disney.utils.dataFactory.staging.bookSEReservation.ShowDiningReservation;

/**
 * This test class tests the ability update the status of an show dining reservation to 'Arrived'
 * @author Justin Phlegar
 *
 */
public class TestArrived extends BaseTest{
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
	public void testArrived(){
		TestReporter.log("Travel Plan ID: " + res.getTravelPlanId());
		TestReporter.log("Reservation Number: " + res.getConfirmationNumber());
		res.arrived();
		TestReporter.assertEquals(res.getArrivedStatus(), "SUCCESS", "Arrived status ["+res.getArrivedStatus()+"] was not 'SUCCESS' as expected.");
		TestReporter.assertEquals(res.getStatus(), "Arrived", "The reservation status ["+res.getStatus()+"] was not 'Arrived' as expected.");
	}
}