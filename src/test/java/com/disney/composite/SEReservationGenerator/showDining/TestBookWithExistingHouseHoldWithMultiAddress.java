package com.disney.composite.SEReservationGenerator.showDining;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.composite.BaseTest;
import com.disney.utils.Regex;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.guestFactory.HouseHold;
import com.disney.utils.dataFactory.staging.bookSEReservation.ScheduledEventReservation;
import com.disney.utils.dataFactory.staging.bookSEReservation.ShowDiningReservation;

/**
 * This test class tests the ability to generate a user-defined household with multiple mail and email addresses and use that household to book an show dining reservation
 * @author Justin Phlegar
 *
 */
public class TestBookWithExistingHouseHoldWithMultiAddress extends BaseTest{
	private ThreadLocal<ScheduledEventReservation> res = new ThreadLocal<ScheduledEventReservation>();
	private ThreadLocal<HouseHold> party = new ThreadLocal<HouseHold>();
	
	@BeforeMethod(alwaysRun=true)
	@Parameters("environment")
	public void setup(String environment){
		this.environment = environment;
		party.set(new HouseHold(1));
		party.get().primaryGuest().addAddress();
		party.get().primaryGuest().addEmail();
	}
	
	@AfterMethod(alwaysRun=true)
	public void teardown(){
		try{res.get().cancel();}
		catch(Exception e){}
	}
	
	@Test
	public void testBookWithExistingHouseHoldWithMultiAddress(){
		book();
	}
	
	@Test
	public void testBookWithExistingHouseHoldWithMultiAddress_UpdatePartyRoles(){
		party.get().sendToApi(environment);
		book();
	}
	
	private void book(){
		res.set(new ShowDiningReservation(environment, party.get()));
		res.get().book(ScheduledEventReservation.ONECOMPONENTSNOADDONS);
		TestReporter.assertTrue(Regex.match("[0-9]+", res.get().getTravelPlanId()), "The travel plan ID ["+res.get().getTravelPlanId()+"] was not numeric as expected.");
		TestReporter.assertTrue(Regex.match("[0-9]+", res.get().getConfirmationNumber()), "The reservation number ["+res.get().getConfirmationNumber()+"] was not numeric as expected.");
		TestReporter.assertEquals(res.get().getStatus(), "Booked", "The reservation status ["+res.get().getStatus()+"] was not 'Booked' as expected.");
	}
}