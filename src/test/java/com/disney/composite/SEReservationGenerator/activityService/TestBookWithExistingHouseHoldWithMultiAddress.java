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
 * This test class tests the ability to generate a user-defined household with multiple mail and email addresses and use that household to book an event dining reservation
 * @author Justin Phlegar
 *
 */
public class TestBookWithExistingHouseHoldWithMultiAddress {

	private String environment;
	private String reservationNumber;
	private ScheduledEventReservation recRes;
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
		recParty = new HouseHold(1);
		recParty.primaryGuest().addAddress();
		recParty.primaryGuest().addEmail();
		recRes = new ActivityEventReservation(this.environment, recParty);
	}
	
	@AfterMethod(alwaysRun=true)
	public void teardown(){
		if(reservationNumber != null)
			if(!reservationNumber.isEmpty())
				recRes.cancel();
	}
	
	@Test
	public void testBookWithExistingHouseHoldWithMultiAddress_RecreationActivity(){
		recRes.setProductType(recProductType);
		recRes.book(recFacilityId, Randomness.generateCurrentXMLDatetime(45), recServicePeriod, recProductId);
		TestReporter.assertTrue(new Regex().match("[0-9]+", recRes.getTravelPlanId()), "The travel plan ID ["+recRes.getTravelPlanId()+"] is not numeric as expected.");
		TestReporter.assertTrue(new Regex().match("[0-9]+", recRes.getConfirmationNumber()), "The reservation number ["+recRes.getConfirmationNumber()+"] is not numeric as expected.");
		reservationNumber = recRes.getConfirmationNumber();
	}
}