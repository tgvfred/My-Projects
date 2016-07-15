package com.disney.composite.SEReservationGenerator.activityService;

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
 * This test class tests the ability to cancel an event dining reservation
 * @author Justin Phlegar
 *
 */
public class TestCancel {
	private String environment;
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
	private String cancellationNumber;
	
	@BeforeMethod(alwaysRun=true)
	@Parameters("environment")
	public void setup(String environment){this.environment = environment;}
	
	@Test
	public void testCancel_ChildActivity(){		
		childParty = new HouseHold("1 Child");
		childParty.primaryGuest().setAge("9");
		childRes = new ActivityEventReservation(this.environment, childParty);
		childRes.setProductType(childProductType);
		childRes.book(childFacilityId, Randomness.generateCurrentXMLDatetime(45), childServicePeriod, childProductId);
		childRes.cancel();
		cancellationNumber = childRes.getCancellationNumber();
		TestReporter.assertTrue(new Regex().match("[0-9]+", cancellationNumber), "The cancellation number ["+cancellationNumber+"] was not numeric as expected.");
		TestReporter.assertEquals(childRes.getStatus(), "Cancelled", "The reservation status ["+childRes.getStatus()+"] was not 'Cancelled' as expected.");
	}
	
	@Test
	public void testCancel_RecreationActivity(){
		recParty = new HouseHold(1);
		recRes = new ActivityEventReservation(this.environment, recParty);
		recRes.setProductType(recProductType);
		recRes.book(recFacilityId, Randomness.generateCurrentXMLDatetime(45), recServicePeriod, recProductId);
		recRes.cancel();
		cancellationNumber = recRes.getCancellationNumber();
		TestReporter.assertTrue(new Regex().match("[0-9]+", cancellationNumber), "The cancellation number ["+cancellationNumber+"] was not numeric as expected.");
		TestReporter.assertEquals(recRes.getStatus(), "Cancelled", "The reservation status ["+recRes.getStatus()+"] was not 'Cancelled' as expected.");
	}
}