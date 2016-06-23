package com.disney.composite.SEReservationGenerator.activityService;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.utils.Randomness;
import com.disney.utils.Regex;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.guestFactory.HouseHold;
import com.disney.utils.dataFactory.staging.bookSEReservation.ActivityEventReservation;
import com.disney.utils.dataFactory.staging.bookSEReservation.ScheduledEventReservation;

/**
 * This test class tests the ability to generate a user-defined household and use that household to book an event dining reservation
 * @author Justin Phlegar
 *
 */
public class TestBookWith_FacilityId_Date_ServicePeriod_ProductId {
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
	/**
	 * Child activity fields
	 */
	private String childFacilityId = "210507";
	private String childProductId = "53905";
	private String childServicePeriod = "0";
	private String childProductType = "ChildActivityProduct";
	
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
	public void testBookWith_FacilityId_Date_ServicePeriod_ProductId_ChildActivity(){
		childRes.setProductType(childProductType);
		childRes.book(childFacilityId, Randomness.generateCurrentXMLDatetime(45), childServicePeriod, childProductId);
		res = childRes;
		reservationNumber = res.getConfirmationNumber();
		TestReporter.assertTrue(new Regex().match("[0-9]+", res.getTravelPlanId()), "The travel plan ID ["+res.getTravelPlanId()+"] is not numeric as expected.");
		TestReporter.assertTrue(new Regex().match("[0-9]+", res.getConfirmationNumber()), "The reservation number ["+res.getConfirmationNumber()+"] is not numeric as expected.");
	}
	
	@Test
	public void testBookWith_FacilityId_Date_ServicePeriod_ProductId_RecreationActivity(){
		recRes.setProductType(recProductType);
		recRes.book(recFacilityId, Randomness.generateCurrentXMLDatetime(45), recServicePeriod, recProductId);
		res = recRes;
		reservationNumber = res.getConfirmationNumber();
		TestReporter.assertTrue(new Regex().match("[0-9]+", res.getTravelPlanId()), "The travel plan ID ["+res.getTravelPlanId()+"] is not numeric as expected.");
		TestReporter.assertTrue(new Regex().match("[0-9]+", res.getConfirmationNumber()), "The reservation number ["+res.getConfirmationNumber()+"] is not numeric as expected.");
	}
}