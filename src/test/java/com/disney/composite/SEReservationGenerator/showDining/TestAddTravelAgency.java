package com.disney.composite.SEReservationGenerator.showDining;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import com.disney.composite.BaseTest;
import com.disney.utils.Regex;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.guestFactory.HouseHold;
import com.disney.utils.dataFactory.staging.bookSEReservation.ScheduledEventReservation;
import com.disney.utils.dataFactory.staging.bookSEReservation.ShowDiningReservation;

/**
 * This test class tests the ability to generate a user-defined household and use that household to book an show dining reservation
 * @author Justin Phlegar
 *
 */
public class TestAddTravelAgency extends BaseTest{
	private ScheduledEventReservation res;
	
	@AfterMethod(alwaysRun=true)
	public void teardown(){
		try{res.cancel();}
		catch(Exception e){}
	}
	
	@Test
	public void testAddTravelAgency(){
		res = new ShowDiningReservation(environment);
		res.setParty(new HouseHold(1));
		res.addTravelAgency();
		res.book(ScheduledEventReservation.ONECOMPONENTSNOADDONS);
		TestReporter.assertTrue(Regex.match("[0-9]+", res.getTravelPlanId()), "The travel plan ID ["+res.getTravelPlanId()+"] was not numeric as expected.");
		TestReporter.assertTrue(Regex.match("[0-9]+", res.getConfirmationNumber()), "The reservation number ["+res.getConfirmationNumber()+"] was not numeric as expected.");
		TestReporter.assertEquals(res.getStatus(), "Booked", "The reservation status ["+res.getStatus()+"] was not 'Booked' as expected.");
	}
}