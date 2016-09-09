package com.disney.composite.SEReservationGenerator.eventDining;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import com.disney.api.BaseTest;
import com.disney.utils.Randomness;
import com.disney.utils.Regex;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.staging.bookSEReservation.EventDiningReservation;
import com.disney.utils.dataFactory.staging.bookSEReservation.ScheduledEventReservation;

/**
 * This test class tests the ability to generate a user-defined household and use that household to book an event dining reservation
 * @author Justin Phlegar
 *
 */
public class TestBookWith_FacilityId_Date_ServicePeriod_ProductId  extends BaseTest{
	private ScheduledEventReservation res;
	private String facilityId = "266442";
	private String productId = "149863";
	private String servicePeriod = "272257";
	private int daysOut = 45;
	private String bookingDate = Randomness.generateCurrentXMLDatetime(daysOut);
	
	@AfterMethod(alwaysRun=true)
	public void teardown(){
		try{res.cancel();}
		catch(Exception e){}
	}
	
	@Test
	public void testBookWith_FacilityId_Date_ServicePeriod_ProductId(){		
		res = new EventDiningReservation(environment, hh);
		res.book(facilityId, bookingDate, servicePeriod, productId);
		TestReporter.assertTrue(Regex.match("[0-9]+", res.getTravelPlanId()), "The travel plan ID ["+res.getTravelPlanId()+"] was not numeric as expected.");
		TestReporter.assertTrue(Regex.match("[0-9]+", res.getConfirmationNumber()), "The travel plan ID ["+res.getConfirmationNumber()+"] was not numeric as expected.");
		TestReporter.assertEquals(res.getStatus(), "Booked", "The reservation status ["+res.getStatus()+"] was not 'Booked' as expected.");
	}
}