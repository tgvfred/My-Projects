package com.disney.composite.SEReservationGenerator.tableServiceDining;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.utils.Regex;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.guestFactory.HouseHold;
import com.disney.utils.dataFactory.staging.bookSEReservation.ScheduledEventReservation;
import com.disney.utils.dataFactory.staging.bookSEReservation.TableServiceDiningReservation;

/**
 * This test class tests the ability to generate a user-defined household with multiple mail and email addresses and use that household to book an event dining reservation
 * @author Justin Phlegar
 *
 */
public class TestBookWithExistingHouseHoldWithMultiAddress {
	private String environment;
	private ThreadLocal<ScheduledEventReservation> res = new ThreadLocal<ScheduledEventReservation>();
	private HouseHold party;
	
	@BeforeMethod(alwaysRun=true)
	@Parameters("environment")
	public void setup(String environment){
		this.environment = environment;
		party = new HouseHold(1);
		party.primaryGuest().addAddress();
		party.primaryGuest().addEmail();
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
		party.sendToApi(environment);
		book();
	}
	
	private void book(){
		res.set(new TableServiceDiningReservation(environment, party));
		res.get().book(ScheduledEventReservation.NOCOMPONENTSNOADDONS);
		TestReporter.assertTrue(Regex.match("[0-9]+", res.get().getTravelPlanId()), "The travel plan ID ["+res.get().getTravelPlanId()+"] was not numeric as expected.");
		TestReporter.assertTrue(Regex.match("[0-9]+", res.get().getConfirmationNumber()), "The reservation number ["+res.get().getConfirmationNumber()+"] was not numeric as expected.");
		TestReporter.assertEquals(res.get().getStatus(), "Booked", "The reservation status ["+res.get().getStatus()+"] was not 'Booked' as expected.");
	}
}