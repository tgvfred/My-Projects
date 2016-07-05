package com.disney.composite.api.eventDiningService.validateBooking;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.eventDiningService.operations.Book;
import com.disney.api.soapServices.eventDiningService.operations.ValidateBooking;
import com.disney.composite.BaseTest;
import com.disney.utils.Regex;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.database.LogItems;
import com.disney.utils.dataFactory.staging.bookSEReservation.EventDiningReservation;
import com.disney.utils.dataFactory.staging.bookSEReservation.ScheduledEventReservation;

public class TestValidateBooking extends BaseTest{

	@BeforeMethod(alwaysRun = true)
	@Parameters({ "environment" })
	public void setup(@Optional String environment) {
		this.environment = "Bashful";
		//res = new EventDiningReservation(this.environment);
	}

	@Test(groups = {"api", "regression", "dining", "eventDiningService"})
	public void testValidateBooking(){
		Book eventDiningBook = new Book(this.environment, "NoComponentsNoAddOns");
		ValidateBooking validate = new ValidateBooking(this.environment, "Main");
		validate.setFacilityId(eventDiningBook.getRequestFacilityId());
		validate.setProductId(eventDiningBook.getRequestProductId());		
		validate.setServiceStartDate(eventDiningBook.getRequestServiceStartDate());
		validate.setServicePeriodId(eventDiningBook.getRequestServicePeriodId());
		validate.sendRequest();
		System.out.println(validate.getResponse());
		TestReporter.logAPI(!validate.getResponseStatusCode().equals("200"), validate.getFaultString(), validate);
		TestReporter.logAPI(!validate.getStopReservation().equals("false"), "The Stop Reservation Status was not false as expected",validate);
		LogItems logItems = new LogItems();
		
		logItems.addItem("EventDiningServiceIF", "validateBooking", false);
		validateLogs(validate, logItems);
	}
}
