package com.disney.composite.SEReservationGenerator.eventDining;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.utils.Randomness;
import com.disney.utils.Regex;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.guestFactory.HouseHold;
import com.disney.utils.dataFactory.staging.bookSEReservation.EventDiningReservation;
import com.disney.utils.dataFactory.staging.bookSEReservation.ScheduledEventReservation;

/**
 * This test class tests the ability to generate a user-defined household and use that household to book an event dining reservation
 * @author Justin Phlegar
 *
 */
public class TestBookWith_FacilityId_Date_ServicePeriod_ProductId {
	private String environment;
	private String travelPlanId;
	private String reservationNumber;
	private ScheduledEventReservation res;
	private String facilityId = "266442";
	private String productId = "149863";
	private String servicePeriod = "272257";
	private String bookingDate;
	private int daysOut = 45;
	
	@BeforeMethod(alwaysRun=true)
	@Parameters("environment")
	public void setup(String environment){
		this.environment = environment;
		bookingDate = Randomness.generateCurrentXMLDatetime(daysOut);
	}
	
	@AfterMethod(alwaysRun=true)
	public void teardown(){
		if(reservationNumber != null)
			if(!reservationNumber.isEmpty())
				res.cancel();
	}
	
	@Test
	public void testBookWith_FacilityId_Date_ServicePeriod_ProductId(){
		HouseHold party = new HouseHold(4);
		
		res = new EventDiningReservation(environment,party);
		res.book(facilityId, bookingDate, servicePeriod, productId);
		travelPlanId = res.getTravelPlanId();
		reservationNumber = res.getConfirmationNumber();
		TestReporter.assertTrue(new Regex().match("[0-9]+", travelPlanId), "The travel plan ID ["+travelPlanId+"] was not numeric as expected.");
		TestReporter.assertTrue(new Regex().match("[0-9]+", reservationNumber), "The travel plan ID ["+reservationNumber+"] was not numeric as expected.");
		TestReporter.assertEquals(res.getStatus(), "Booked", "The reservation status ["+res.getStatus()+"] was not 'Booked' as expected.");
	}
}