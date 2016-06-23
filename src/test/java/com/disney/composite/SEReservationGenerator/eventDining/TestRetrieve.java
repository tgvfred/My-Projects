package com.disney.composite.SEReservationGenerator.eventDining;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.utils.Regex;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.guestFactory.HouseHold;
import com.disney.utils.dataFactory.staging.bookSEReservation.EventDiningReservation;
import com.disney.utils.dataFactory.staging.bookSEReservation.ScheduledEventReservation;

/**
 * This test class tests the ability to generate a user-defined household and use that household to book an show dining reservation
 * @author Justin Phlegar
 *
 */
public class TestRetrieve {
	private String environment;
	private String travelPlanId;
	private String reservationNumber;
	private ScheduledEventReservation res;
	private HouseHold party;
	private int partySize = 2;
	
	@BeforeMethod(alwaysRun=true)
	@Parameters("environment")
	public void setup(String environment){
		this.environment = environment;
		party = new HouseHold(partySize);
		book();
	}
	
	@AfterMethod(alwaysRun=true)
	public void teardown(){
		if(reservationNumber != null)
			if(!reservationNumber.isEmpty())
				res.cancel();
	}
	
	@Test
	public void testRetrieve(){
		res.retrieve();
		TestReporter.assertEquals(partySize, res.getNumberOfGuests(), "The number of guests ["+res.getNumberOfGuests()+"] did not match the expected number of guests ["+partySize+"].");
	}
	
	private void book(){
		res = new EventDiningReservation(environment,party);
		res.book("BookGuaranteedTS");
		travelPlanId = res.getTravelPlanId();
		reservationNumber = res.getConfirmationNumber();
		TestReporter.assertTrue(new Regex().match("[0-9]+", travelPlanId), "The travel plan ID ["+travelPlanId+"] was not numeric as expected.");
		TestReporter.assertTrue(new Regex().match("[0-9]+", reservationNumber), "The reservation number ["+reservationNumber+"] was not numeric as expected.");
		TestReporter.assertEquals(res.getStatus(), "Booked", "The reservation status ["+res.getStatus()+"] was not 'Booked' as expected.");
	}
}