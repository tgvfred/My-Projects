package com.disney.composite.SEReservationGenerator.activityService;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.composite.BaseTest;
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
public class TestBookWith_FacilityId_Date_ServicePeriod_ProductId extends BaseTest{
	private ThreadLocal<ScheduledEventReservation> res = new ThreadLocal<ScheduledEventReservation>();
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

	@Override
	@BeforeMethod(alwaysRun=true)
	@Parameters("environment")
	public void setup(String environment){
		this.environment = environment;
	}
	
	@AfterMethod(alwaysRun=true)
	public void teardown(){
		try{res.get().cancel();}
		catch(Exception e){}
	}
	
	@Test
	public void testBookWith_FacilityId_Date_ServicePeriod_ProductId_ChildActivity(){
		childParty = new HouseHold("1 Child");
		childParty.primaryGuest().setAge("9");
		res.set(new ActivityEventReservation(this.environment, childParty));
		res.get().setProductType(childProductType);
		res.get().book(childFacilityId, Randomness.generateCurrentXMLDatetime(45), childServicePeriod, childProductId);
		TestReporter.assertTrue(Regex.match("[0-9]+", res.get().getTravelPlanId()), "The travel plan ID ["+res.get().getTravelPlanId()+"] is not numeric as expected.");
		TestReporter.assertTrue(Regex.match("[0-9]+", res.get().getConfirmationNumber()), "The reservation number ["+res.get().getConfirmationNumber()+"] is not numeric as expected.");
	}
	
	@Test
	public void testBookWith_FacilityId_Date_ServicePeriod_ProductId_RecreationActivity(){
		recParty = new HouseHold(1);
		res.set(new ActivityEventReservation(this.environment, recParty));
		res.get().setProductType(recProductType);
		res.get().book(recFacilityId, Randomness.generateCurrentXMLDatetime(45), recServicePeriod, recProductId);
		TestReporter.assertTrue(Regex.match("[0-9]+", res.get().getTravelPlanId()), "The travel plan ID ["+res.get().getTravelPlanId()+"] is not numeric as expected.");
		TestReporter.assertTrue(Regex.match("[0-9]+", res.get().getConfirmationNumber()), "The reservation number ["+res.get().getConfirmationNumber()+"] is not numeric as expected.");
	}
}