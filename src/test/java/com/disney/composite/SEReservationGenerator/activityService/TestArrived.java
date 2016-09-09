package com.disney.composite.SEReservationGenerator.activityService;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import com.disney.api.BaseTest;
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
public class TestArrived extends BaseTest{
	private ThreadLocal<ScheduledEventReservation> res = new ThreadLocal<ScheduledEventReservation>();
	/**
	 * Recreation activity fields
	 */
	private String recFacilityId = "80008044";
	private String recProductId = "53972";
	private String recServicePeriod = "0";
	private String recProductType = "RecreationActivityProduct";
	
	@AfterMethod(alwaysRun=true)
	public void teardown(){
		try{res.get().cancel();}
		catch(Exception e){}
	}
	
	@Test
	public void testArrived_ChildActivity(){
		HouseHold childParty = new HouseHold("1 Child");
		childParty.primaryGuest().setAge("9");
		res.set(new ActivityEventReservation(this.environment, childParty));
		res.get().book(ScheduledEventReservation.NOCOMPONENTSNOADDONS);
		verifyValues(res.get());
	}
	
	@Test
	public void testArrived_RecreationActivity(){
		HouseHold recParty = new HouseHold(1);
		res.set(new ActivityEventReservation(this.environment, recParty));
		res.get().setProductType(recProductType);
		res.get().book(recFacilityId, Randomness.generateCurrentXMLDatetime(45), recServicePeriod, recProductId);
		verifyValues(res.get());
	}
	
	private void verifyValues(ScheduledEventReservation localRes){		
		localRes.arrived();
		TestReporter.assertEquals(localRes.getArrivedStatus(), "SUCCESS", "Arrived status ["+localRes.getArrivedStatus()+"] was not 'SUCCESS' as expected.");
		TestReporter.assertEquals(localRes.getStatus(), "Arrived", "The reservation status ["+localRes.getStatus()+"] was not 'Arrived' as expected.");
		res.set(localRes);
	}
}