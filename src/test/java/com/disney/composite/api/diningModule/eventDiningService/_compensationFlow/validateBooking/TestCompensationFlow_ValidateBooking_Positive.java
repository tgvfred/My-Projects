package com.disney.composite.api.diningModule.eventDiningService._compensationFlow.validateBooking;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.diningModule.eventDiningService.operations.Book;
import com.disney.api.soapServices.diningModule.eventDiningService.operations.ValidateBooking;
import com.disney.composite.BaseTest;
import com.disney.utils.TestReporter;

public class TestCompensationFlow_ValidateBooking_Positive extends BaseTest{
	private Book book = null;
	@BeforeClass(alwaysRun = true)
	@Parameters({ "environment" })
	public void setup(@Optional String environment) {
		this.environment = environment;
		book = new Book(this.environment, "NoComponentsNoAddOns");
	}

	@Test(groups = {"api", "regression", "dining", "eventDiningService", "compensation"})
	public void testCompensationFlow_ValidateBooking_Positive(){		
		ValidateBooking validate = setupValidateBookingInstance(book.getRequestFacilityId(), book.getRequestProductId(), book.getRequestServiceStartDate(), book.getRequestServicePeriodId());
		TestReporter.logAPI(!validate.getResponseStatusCode().equals("200"), validate.getFaultString(), validate);
		TestReporter.logAPI(!validate.getStopReservation().equals("false"), "The Stop Reservation Status was not false as expected",validate);
	}
	
	private ValidateBooking setupValidateBookingInstance(String facilityId, String productId, String serviceStartDate, String servicePeriodId){
		ValidateBooking validate = new ValidateBooking(environment, "Main");
		validate.setFacilityId(facilityId);
		validate.setProductId(productId);		
		validate.setServiceStartDate(serviceStartDate);
		validate.setServicePeriodId(servicePeriodId); 
		validate.sendRequest();
		TestReporter.logAPI(!validate.getResponseStatusCode().equals("200"), "An error occurred validating an event dining service reservation: " + validate.getFaultString(), validate);
		return validate;
	}
}
