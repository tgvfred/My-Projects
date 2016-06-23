package com.disney.composite.SEReservationGenerator.activityService;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.utils.Randomness;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.guestFactory.HouseHold;
import com.disney.utils.dataFactory.staging.bookSEReservation.ActivityEventReservation;
import com.disney.utils.dataFactory.staging.bookSEReservation.ScheduledEventReservation;

/**
 * This test class tests the ability update the status of an activity event reservation to 'Arrived'
 * @author Justin Phlegar
 *
 */
public class TestArrived {
	private String environment;
	private String reservationNumber;
	private ScheduledEventReservation res;
	private ScheduledEventReservation childRes;
	private ScheduledEventReservation recRes;
	private HouseHold childParty;
	private HouseHold recParty;
	/**
	 * Recreation activity fields
	 */
	private String recFacilityId = "80008044";
	private String recProductId = "53972";
	private String recServicePeriod = "0";
	private String recProductType = "RecreationActivityProduct";
	
	@BeforeMethod(alwaysRun=true)
	@Parameters("environment")
	public void setup(String environment){
		this.environment = environment;
		childParty = new HouseHold("1 Child");
		childParty.primaryGuest().setAge("9");
		childRes = new ActivityEventReservation(this.environment, childParty);
		recParty = new HouseHold(1);
		recRes = new ActivityEventReservation(this.environment, recParty);
	}
	
	@AfterMethod(alwaysRun=true)
	public void teardown(){
		if(reservationNumber != null)
			if(!reservationNumber.isEmpty())
				res.cancel();
	}
	
	@Test
	public void testArrived_ChildActivity(){
		childRes.book(ScheduledEventReservation.NOCOMPONENTSNOADDONS);
		
		childRes.arrived();
		TestReporter.assertEquals(childRes.getArrivedStatus(), "SUCCESS", "Arrived status ["+childRes.getArrivedStatus()+"] was not 'SUCCESS' as expected.");
		TestReporter.assertEquals(childRes.getStatus(), "Arrived", "The reservation status ["+childRes.getStatus()+"] was not 'Arrived' as expected.");
		res = childRes;
	}
	
	@Test
	public void testArrived_RecreationActivity(){
		recRes.setProductType(recProductType);
		recRes.book(recFacilityId, Randomness.generateCurrentXMLDatetime(45), recServicePeriod, recProductId);
		
		recRes.arrived();
		TestReporter.assertEquals(recRes.getArrivedStatus(), "SUCCESS", "Arrived status ["+recRes.getArrivedStatus()+"] was not 'SUCCESS' as expected.");
		TestReporter.assertEquals(recRes.getStatus(), "Arrived", "The reservation status ["+recRes.getStatus()+"] was not 'Arrived' as expected.");
		res = recRes;
	}
}