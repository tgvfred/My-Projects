package com.disney.composite.SEReservationGenerator.activityService;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import com.disney.composite.BaseTest;
import com.disney.utils.Randomness;
import com.disney.utils.Regex;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.guestFactory.HouseHold;
import com.disney.utils.dataFactory.staging.bookSEReservation.ActivityEventReservation;
import com.disney.utils.dataFactory.staging.bookSEReservation.ScheduledEventReservation;

/**
 * This test class tests the ability to generate a user-defined household and use that household to book an activity event reservation
 * @author Justin Phlegar
 *
 */
public class TestAddTravelAgency extends BaseTest{
	protected ThreadLocal<ScheduledEventReservation> res = new ThreadLocal<ScheduledEventReservation>();
	/**
	 * Recreation activity fields
	 */
	private String recFacilityId = "80008044";
	private String recProductId = "53972";
	private String resServicePeriod = "0";
	private String resProductType = "RecreationActivityProduct";
	
	@AfterMethod(alwaysRun=true)
	public void teardown(){
		try{res.get().cancel();}
		catch(Exception e){}
	}
	
	@Test
	public void testAddTravelAgency_ChildActivity(){
		hh = new HouseHold("1 Child");
		hh.primaryGuest().setAge("9");
		res.set(new ActivityEventReservation(environment, hh));
		res.get().addTravelAgency();
		res.get().book(ScheduledEventReservation.NOCOMPONENTSNOADDONS);
		verifyValues(res);
	}
	
	@Test
	public void testAddTravelAgency_RecreationActivity(){
		res.set(new ActivityEventReservation(environment, hh));
		res.get().setProductType(resProductType);
		res.get().addTravelAgency();
		res.get().book(recFacilityId, Randomness.generateCurrentXMLDatetime(45), resServicePeriod, recProductId);
		verifyValues(res);
	}
	
	private void verifyValues(ThreadLocal<ScheduledEventReservation> res){		
		String travelPlanId = res.get().getTravelPlanId();
		String reservationNumber = res.get().getConfirmationNumber();
		String status = res.get().getStatus();
		TestReporter.assertTrue(Regex.match("[0-9]+", travelPlanId), "Verify the travel plan ID ["+travelPlanId+"] is numeric as expected.");
		TestReporter.assertTrue(Regex.match("[0-9]+", reservationNumber), "Verifey the reservation number ["+reservationNumber+"] is numeric as expected.");
		TestReporter.assertEquals(status, "Booked", "Verify the reservation status ["+status+"] is [Booked] as expected.");
	}
}