package com.disney.composite.api.activityService;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.utils.Randomness;
import com.disney.utils.Regex;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.staging.bookSEReservation.ActivityEventReservation;
import com.disney.utils.dataFactory.staging.bookSEReservation.ScheduledEventReservation;

public class TestBookAndCancel {
	private String environment;
	private String reservationNumber;
	private ScheduledEventReservation res;
	private String recFacilityId = "80008044";
	private String recProductId = "53972";
	private String recServicePeriod = "0";
	private String recProductType = "RecreationActivityProduct";
	private String cancellationNumber;
	
	@BeforeClass(alwaysRun=true)
	@Parameters("environment")
	public void setup(String environment){
		this.environment = environment;
		res = new ActivityEventReservation(this.environment);
	}
	
	@Test(dependsOnMethods="testBook", alwaysRun=true)
	public void testCancel(){
		if(reservationNumber != null)
			if(!reservationNumber.isEmpty()){
				res.cancel();
				cancellationNumber = res.getCancellationNumber();
				TestReporter.assertTrue(Regex.match("[0-9]+", cancellationNumber), "The cancellation number ["+cancellationNumber+"] was not numeric as expected.");
				TestReporter.assertEquals(res.getStatus(), "Cancelled", "The reservation status ["+res.getStatus()+"] was not 'Cancelled' as expected.");
			}
	}
	
	@Test
	public void testBook(){
		res.setProductType(recProductType);
		res.book(recFacilityId, Randomness.generateCurrentXMLDatetime(45), recServicePeriod, recProductId);
		TestReporter.assertTrue(Regex.match("[0-9]+", res.getTravelPlanId()), "The travel plan ID ["+res.getTravelPlanId()+"] is not numeric as expected.");
		TestReporter.assertTrue(Regex.match("[0-9]+", res.getConfirmationNumber()), "The reservation number ["+res.getConfirmationNumber()+"] is not numeric as expected.");
		reservationNumber = res.getConfirmationNumber();
	}
}