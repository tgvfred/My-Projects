package com.disney.composite.SEReservationGenerator.eventDining;

import org.testng.annotations.AfterMethod;
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
 * This test class tests the ability to generate a user-defined household with a specific party mix and use that household to book an event dining reservation
 * @author Justin Phlegar
 *
 */
public class TestBookWithExistingLargeHouseHold extends BaseTest{
	private ThreadLocal<ScheduledEventReservation> res = new ThreadLocal<ScheduledEventReservation>();
	private ThreadLocal<HouseHold> party = new ThreadLocal<HouseHold>();

	@Override
	@BeforeMethod(alwaysRun=true)
	@Parameters("environment")
	public void setup(String environment){
		this.environment = environment;
		party.set(new HouseHold("2 Adults 2 Child"));
		party.get().primaryGuest().addAddress();
		party.get().primaryGuest().addEmail();
	}
	
	@AfterMethod(alwaysRun=true)
	public void teardown(){
		try{res.get().cancel();}
		catch(Exception e){}
	}
	
	@Test(priority = 1)
	public void testBookWithExistingLargeHouseHold(){
		book();
	}
	
	@Test(priority = 2)
	public void testBookWithExistingLargeHouseHold_UpdatePartyRoles(){
		party.get().sendToApi(environment);
		book();	
	}
	
	private void book(){
		res.set(new EventDiningReservation(environment, party.get()));
		res.get().book("BookGuaranteedTS");
		TestReporter.assertTrue(Regex.match("[0-9]+", res.get().getTravelPlanId()), "The travel plan ID ["+res.get().getTravelPlanId()+"] was not numeric as expected.");
		TestReporter.assertTrue(Regex.match("[0-9]+", res.get().getConfirmationNumber()), "The travel plan ID ["+res.get().getConfirmationNumber()+"] was not numeric as expected.");
		TestReporter.assertEquals(res.get().getStatus(), "Booked", "The reservation status ["+res.get().getStatus()+"] was not 'Booked' as expected.");
	}
}