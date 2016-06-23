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
 * This test class tests the ability to generate a user-defined household and use that household to book an event dining reservation
 * @author Justin Phlegar
 *
 */
public class TestModifyFacility {
	private String environment;
	private String travelPlanId;
	private String reservationNumber;
	private ScheduledEventReservation res;
	private HouseHold party;
	private String facilityId = "266442";
	
	@BeforeMethod(alwaysRun=true)
	@Parameters("environment")
	public void setup(String environment){
		this.environment = environment;
		party = new HouseHold(4);
	}
	
	@AfterMethod(alwaysRun=true)
	public void teardown(){
		if(reservationNumber != null)
			if(!reservationNumber.isEmpty())
				res.cancel();
	}
	
	@Test
	public void testModifyFacility(){
		book();
		res.modify().modifyFacility(facilityId);
		TestReporter.assertEquals(res.getStatus(), "Booked", "The reservation status ["+res.getStatus()+"] was not 'Booked' as expected.");
	}
	
	@Test
	public void testModifyFacility_UpdatePartyRoles(){
		party.sendToApi(environment);
		book();		
		res.modify().modifyFacility(facilityId);
		TestReporter.assertEquals(res.getStatus(), "Booked", "The reservation status ["+res.getStatus()+"] was not 'Booked' as expected.");
	}
	
	private void book(){
		res = new TableServiceDiningReservation(environment, party);
		res.book(ScheduledEventReservation.NOCOMPONENTSNOADDONS);
		travelPlanId = res.getTravelPlanId();
		reservationNumber = res.getConfirmationNumber();
		TestReporter.assertTrue(new Regex().match("[0-9]+", travelPlanId), "The travel plan ID ["+travelPlanId+"] was not numeric as expected.");
		TestReporter.assertTrue(new Regex().match("[0-9]+", reservationNumber), "The reservation number ["+reservationNumber+"] was not numeric as expected.");
		TestReporter.assertEquals(res.getStatus(), "Booked", "The reservation status ["+res.getStatus()+"] was not 'Booked' as expected.");
	}
}