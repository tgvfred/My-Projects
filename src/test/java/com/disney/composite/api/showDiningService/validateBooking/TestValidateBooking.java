package com.disney.composite.api.showDiningService.validateBooking;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.core.BaseSoapCommands;
import com.disney.api.soapServices.showDiningService.operations.Book;
import com.disney.api.soapServices.showDiningService.operations.ValidateBooking;
import com.disney.composite.BaseTest;
import com.disney.utils.Randomness;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.database.LogItems;
import com.disney.utils.dataFactory.guestFactory.HouseHold;
import com.disney.utils.dataFactory.staging.bookSEReservation.ScheduledEventReservation;

public class TestValidateBooking extends BaseTest{	
	protected HouseHold hh = null;
	protected Book book = null;
	
	@Override
	@BeforeTest(alwaysRun=true)
	@Parameters("environment")
	public void setup(@Optional String environment){
		this.environment = environment;
		hh = new HouseHold(1);
		book = new Book(environment, ScheduledEventReservation.ONECOMPONENTSNOADDONS);
	}
	
	@Test(groups = {"api", "regression", "dining", "showDiningService", "negative"})
	public void testValidateBooking(){
		TestReporter.logStep("Validate a Show Dining Booking");
		ValidateBooking validate = setupValidateBookingInstance(book.getRequestFacilityId(), book.getRequestProductId(), book.getRequestServiceStartDate(), book.getRequestServicePeriodId());
		TestReporter.assertEquals(validate.getStopReservation(),"false", "The 'Stop Reservation' value ["+validate.getStopReservation()+"] was not 'false' as expected.");
		logItems(validate);
	}	
	@Test(groups = {"api", "regression", "dining", "showDiningService", "negative"})
	public void missingFacilityId(){
		TestReporter.logStep("Validate a Show Dining Booking - Missing Facility Id");
		ValidateBooking validate = setupValidateBookingInstance(BaseSoapCommands.REMOVE_NODE.toString(), book.getRequestProductId(), book.getRequestServiceStartDate(), book.getRequestServicePeriodId());
		TestReporter.assertEquals(validate.getStopReservation(),"true", "The 'Stop Reservation' value ["+validate.getStopReservation()+"] was not 'true' as expected.");
		TestReporter.assertEquals(validate.getStopReason(),"Destination/Time Zone is not configured in drools for the given facility Id : 0", "Verify the stop reason ["+validate.getStopReason()+"] matches that which is expected [Destination/Time Zone is not configured in drools for the given facility Id : 0]");
		TestReporter.assertEquals(validate.getRulesFired(),"FacilityToDestinationLookup", "Verify the rules fired ["+validate.getRulesFired()+"] matches that which is expected [FacilityToDestinationLookup].");
		logItems(validate);		
	}
	@Test(groups = {"api", "regression", "dining", "showDiningService", "negative"})
	public void invalidFacilityId_Integer(){
		String facilityId= "-1";
		TestReporter.logStep("Validate a Show Dining Booking - Invalid Facility Id_Integer");
		ValidateBooking validate = setupValidateBookingInstance(facilityId, book.getRequestProductId(), book.getRequestServiceStartDate(), book.getRequestServicePeriodId());
		TestReporter.assertEquals(validate.getStopReservation(),"true", "The 'Stop Reservation' value ["+validate.getStopReservation()+"] was not 'true' as expected.");
		TestReporter.assertEquals(validate.getStopReason(),"Destination/Time Zone is not configured in drools for the given facility Id : "+facilityId, "Verify the stop reason ["+validate.getStopReason()+"] matches that which is expected [Destination/Time Zone is not configured in drools for the given facility Id : "+facilityId+"].");
		TestReporter.assertEquals(validate.getRulesFired(),"FacilityToDestinationLookup", "Verify the rules fired ["+validate.getRulesFired()+"] matches that which is expected [FacilityToDestinationLookup].");
		logItems(validate);	
	}
	@Test(groups = {"api", "regression", "dining", "showDiningService", "negative"})
	public void expiredServiceStartDate(){
		TestReporter.logStep("Validate aShow Show Dining Booking - Expired Service Start Date");
		ValidateBooking validate = setupValidateBookingInstance(book.getRequestFacilityId(), book.getRequestProductId(), Randomness.generateCurrentXMLDatetime(-1), book.getRequestServicePeriodId());
		TestReporter.assertEquals(validate.getStopReservation(),"true", "The 'Stop Reservation' value ["+validate.getStopReservation()+"] was not 'true' as expected.");
		TestReporter.assertEquals(validate.getStopReason(),"Book Date is greater than Service date", "Verify the stop reason ["+validate.getStopReason()+"] matches that which is expected [Book Date is greater than Service date].");
		TestReporter.assertEquals(validate.getRulesFired(),"BookDate_ServiceDate_Check_1", "Verify the rules fired ["+validate.getRulesFired()+"] matches that which is expected [BookDate_ServiceDate_Check_1].");
		logItems(validate);
	}
	@Test(groups = {"api", "regression", "dining", "showDiningService", "negative"})
	public void bookDateBeyond180Days(){
		TestReporter.logStep("Validate a Show Dining Booking - Book Date Beyond 180 Days");
		ValidateBooking validate = setupValidateBookingInstance(book.getRequestFacilityId(), book.getRequestProductId(), Randomness.generateCurrentXMLDatetime(182), book.getRequestServicePeriodId());
		TestReporter.assertEquals(validate.getStopReservation(),"true", "The 'Stop Reservation' value ["+validate.getStopReservation()+"] was not 'true' as expected.");
		TestReporter.assertEquals(validate.getStopReason(),"Day Guest cannot book a Dining Reservation beyond 180 days from booking date", "Verify the stop reason ["+validate.getStopReason()+"] matches that which is expected [Day Guest cannot book a Dining Reservation beyond 180 days from booking date].");
		TestReporter.assertEquals(validate.getRulesFired(),"OneEighty_Ten_Rule_DAY_GUEST_2", "Verify the rules fired ["+validate.getRulesFired()+"] matches that which is expected [OneEighty_Ten_Rule_DAY_GUEST_2].");
		logItems(validate);
	}
	
	private ValidateBooking setupValidateBookingInstance(String facilityId, String productId, String serviceStartDate, String servicePeriodId){
		ValidateBooking validate = new ValidateBooking(environment, "Main");
		validate.setFacilityId(facilityId);
		validate.setProductId(productId);		
		validate.setServiceStartDate(serviceStartDate);
		validate.setServicePeriodId(servicePeriodId);
		validate.sendRequest();
		TestReporter.logAPI(!validate.getResponseStatusCode().equals("200"), "An error occurred validating an show dining service reservation", validate);
		return validate;
	}
	
	private void logItems(ValidateBooking validate){
		LogItems logValidItems = new LogItems();
		logValidItems.addItem("ShowDiningServiceIF", "validateBooking", false);
		validateLogs(validate, logValidItems);
	}
}