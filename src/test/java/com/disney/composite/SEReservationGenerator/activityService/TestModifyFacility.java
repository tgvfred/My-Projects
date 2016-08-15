package com.disney.composite.SEReservationGenerator.activityService;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import com.disney.composite.BaseTest;
import com.disney.utils.Randomness;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.guestFactory.HouseHold;
import com.disney.utils.dataFactory.staging.bookSEReservation.ActivityEventReservation;
import com.disney.utils.dataFactory.staging.bookSEReservation.ScheduledEventReservation;

/**
 * This test class tests the ability to generate a user-defined household and use that household to book an activity event reservation
 * @author Justin Phlegar
 *
 */
public class TestModifyFacility extends BaseTest{
	private ScheduledEventReservation childRes;
	private ScheduledEventReservation recRes;
	private ThreadLocal<ScheduledEventReservation> res = new ThreadLocal<ScheduledEventReservation>();
	private HouseHold childParty;
	private HouseHold recParty;
	/**
	 * Recreation activity fields
	 */
	private String recFacilityId = "80008180";
	private String recProductId = "53881";
	private String recServicePeriod = "0";
	private String recProductType = "RecreationActivityProduct";
	private String recModFacilityId = "80007944";
	private String recModProductId = "53921";
	/**
	 * Child activity fields
	 */
	private String childFacilityId = "80008181";
	private String childProductId = "53937";
	private String childProductType = "ChildActivityProduct";
	
	@AfterMethod
	public void teardown(){
		try{res.get().cancel();}
		catch(Exception e){}
	}
	
	@Test
	public void testModifyFacility_ChildActivity(){		
		childParty = new HouseHold("1 Child");
		childParty.primaryGuest().setAge("9");
		childRes = new ActivityEventReservation(this.environment, childParty);
		childRes.setProductType(childProductType);
		childRes.book(ScheduledEventReservation.NOCOMPONENTSNOADDONS);
		
		childRes.modify().modifyFacility(childFacilityId, childProductId);
		TestReporter.assertEquals(childRes.getRetrieveResponseFacilityID(), childFacilityId, "The modified facility ID ["+childRes.getRetrieveResponseFacilityID()+"] did not match the expected facility ID ["+childFacilityId+"].");
		res.set(childRes);
	}
	
	@Test
	public void testModifyFacility_RecreationActivity(){
		recParty = new HouseHold(1);
		recRes = new ActivityEventReservation(this.environment, recParty);
		recRes.setProductType(recProductType);
		recRes.book(recFacilityId, Randomness.generateCurrentXMLDatetime(45), recServicePeriod, recProductId);
		
		recRes.setBookingScenario(ScheduledEventReservation.ONECOMPONENTSNOADDONS);
		recRes.modify().modifyFacility(recModFacilityId, recModProductId);
		TestReporter.assertEquals(recRes.getRetrieveResponseFacilityID(), recModFacilityId, "The modified facility ID ["+recRes.getRetrieveResponseFacilityID()+"] did not match the expected facility ID ["+recModFacilityId+"].");
		res.set(recRes);
	}
}