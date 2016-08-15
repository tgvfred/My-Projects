package com.disney.composite.SEReservationGenerator.showDining;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import com.disney.composite.BaseTest;
import com.disney.utils.Regex;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.staging.bookSEReservation.ScheduledEventReservation;
import com.disney.utils.dataFactory.staging.bookSEReservation.ShowDiningReservation;

/**
 * This test class tests the ability to generate a user-defined household and use that household to book an show dining reservation
 * @author Justin Phlegar
 *
 */
public class TestBookWithDefaultHouseHold extends BaseTest{
	private ThreadLocal<ScheduledEventReservation> res = new ThreadLocal<ScheduledEventReservation>();
	
	@AfterMethod(alwaysRun=true)
	public void teardown(){
		try{res.get().cancel();}
		catch(Exception e){}
	}
	
	@Test
	public void testBookWithDefaultHouseHold(){
		book();
	}
	
	@Test
	public void testBookWithDefaultHouseHold_UpdatePartyRoles(){
		hh.sendToApi(environment);
		book();
	}
	
	private void book(){
		res.set(new ShowDiningReservation(environment, hh));
		res.get().book(ScheduledEventReservation.ONECOMPONENTSNOADDONS);
		TestReporter.assertTrue(Regex.match("[0-9]+", res.get().getTravelPlanId()), "The travel plan ID ["+res.get().getTravelPlanId()+"] was not numeric as expected.");
		TestReporter.assertTrue(Regex.match("[0-9]+", res.get().getConfirmationNumber()), "The reservation number ["+res.get().getConfirmationNumber()+"] was not numeric as expected.");
		TestReporter.assertEquals(res.get().getStatus(), "Booked", "The reservation status ["+res.get().getStatus()+"] was not 'Booked' as expected.");
	}
}