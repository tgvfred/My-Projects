package com.disney.composite.SEReservationGenerator.activityService;

import org.testng.annotations.Test;

import com.disney.api.BaseTest;
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
public class TestCancel extends BaseTest{
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
	
	@Test
	public void testCancel_ChildActivity(){		
		childParty = new HouseHold("1 Child");
		childParty.primaryGuest().setAge("9");
		res.set(new ActivityEventReservation(this.environment, childParty));
		res.get().setProductType(childProductType);
		res.get().book(childFacilityId, Randomness.generateCurrentXMLDatetime(45), childServicePeriod, childProductId);
		res.get().cancel();
		TestReporter.assertTrue(Regex.match("[0-9]+", res.get().getCancellationNumber()), "The cancellation number ["+res.get().getCancellationNumber()+"] was not numeric as expected.");
		TestReporter.assertEquals(res.get().getStatus(), "Cancelled", "The reservation status ["+res.get().getStatus()+"] was not 'Cancelled' as expected.");
	}
	
	@Test
	public void testCancel_RecreationActivity(){
		recParty = new HouseHold(1);
		res.set(new ActivityEventReservation(this.environment, recParty));
		res.get().setProductType(recProductType);
		res.get().book(recFacilityId, Randomness.generateCurrentXMLDatetime(45), recServicePeriod, recProductId);
		res.get().cancel();
		TestReporter.assertTrue(Regex.match("[0-9]+", res.get().getCancellationNumber()), "The cancellation number ["+res.get().getCancellationNumber()+"] was not numeric as expected.");
		TestReporter.assertEquals(res.get().getStatus(), "Cancelled", "The reservation status ["+res.get().getStatus()+"] was not 'Cancelled' as expected.");
	}
}