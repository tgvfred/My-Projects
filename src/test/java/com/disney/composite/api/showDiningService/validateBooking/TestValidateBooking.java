package com.disney.composite.api.showDiningService.validateBooking;

import org.testng.annotations.Test;

import com.disney.api.soapServices.showDiningService.operations.Book;
import com.disney.api.soapServices.showDiningService.operations.ValidateBooking;
import com.disney.composite.BaseTest;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.staging.bookSEReservation.ScheduledEventReservation;

public class TestValidateBooking extends BaseTest{	
	@Test
	public void testValidateBooking(){
		TestReporter.logStep("Validate an show Dining Booking");
		Book book = new Book(environment, ScheduledEventReservation.ONECOMPONENTSNOADDONS);
		ValidateBooking validate = new ValidateBooking(environment, "Main");
		validate.setFacilityId(book.getRequestFacilityId());
		validate.setProductId(book.getRequestProductId());		
		validate.setServiceStartDate(book.getRequestServiceStartDate());
		validate.setServicePeriodId(book.getRequestServicePeriodId());
		validate.sendRequest();
		TestReporter.logAPI(!validate.getResponseStatusCode().equals("200"), "An error occurred validating an show dining service reservation", validate);
		TestReporter.assertEquals(validate.getStopReservation(),"false", "The 'Stop Reservation' value ["+validate.getStopReservation()+"] was not 'false' as expected.");
	}
}