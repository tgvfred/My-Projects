package com.disney.composite.SEReservationGenerator.activityService;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.composite.BaseTest;
import com.disney.utils.Randomness;
import com.disney.utils.Regex;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.staging.bookSEReservation.ActivityEventReservation;
import com.disney.utils.dataFactory.staging.bookSEReservation.ScheduledEventReservation;

/**
 * This test class tests the ability to generate a user-defined household and use that household to book an event dining reservation
 * @author Justin Phlegar
 *
 */
public class TestBookWithDefaultHouseHold extends BaseTest{
	private ScheduledEventReservation res;
	private String recFacilityId = "80008044";
	private String recProductId = "53972";
	private String recServicePeriod = "0";
	private String recProductType = "RecreationActivityProduct";

	@Override
	@BeforeMethod(alwaysRun=true)
	@Parameters("environment")
	public void setup(String environment){
		this.environment = environment;
		res = new ActivityEventReservation(this.environment);
	}
	
	@AfterMethod(alwaysRun=true)
	public void teardown(){
		try{res.cancel();}
		catch(Exception e){}
	}
	
	@Test
	public void testBookWithDefaultHouseHold_RecreationActivity(){
		res.setProductType(recProductType);
		res.book(recFacilityId, Randomness.generateCurrentXMLDatetime(45), recServicePeriod, recProductId);
		TestReporter.assertTrue(Regex.match("[0-9]+", res.getTravelPlanId()), "The travel plan ID ["+res.getTravelPlanId()+"] is not numeric as expected.");
		TestReporter.assertTrue(Regex.match("[0-9]+", res.getConfirmationNumber()), "The reservation number ["+res.getConfirmationNumber()+"] is not numeric as expected.");
	}
}