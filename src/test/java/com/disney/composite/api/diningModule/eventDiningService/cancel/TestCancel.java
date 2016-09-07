package com.disney.composite.api.diningModule.eventDiningService.cancel;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.SoapException;
import com.disney.api.soapServices.diningModule.eventDiningService.operations.Arrived;
import com.disney.api.soapServices.diningModule.eventDiningService.operations.Book;
import com.disney.api.soapServices.diningModule.eventDiningService.operations.Cancel;
import com.disney.composite.BaseTest;
import com.disney.utils.Randomness;
import com.disney.utils.Regex;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.database.LogItems;
import com.disney.utils.dataFactory.guestFactory.HouseHold;
import com.disney.utils.dataFactory.staging.bookSEReservation.EventDiningReservation;
import com.disney.utils.dataFactory.staging.bookSEReservation.ScheduledEventReservation;

public class TestCancel extends BaseTest{
	// Defining global variables
	protected String TPS_ID = null;
	protected ScheduledEventReservation res = null;

	@Test( groups = {"api", "regression", "dining", "eventDiningService"})
	public void testCancel() {
		hh = new HouseHold(1);
		ScheduledEventReservation res = new EventDiningReservation(this.environment, hh);
		res.book(ScheduledEventReservation.NOCOMPONENTSNOADDONS);
		Cancel cancel = new Cancel(environment, "CancelDiningEvent");
		cancel.setReservationNumber(res.getConfirmationNumber());
		cancel.sendRequest();
		TestReporter.logAPI(!cancel.getResponseStatusCode().contains("200"), cancel.getFaultString() ,cancel);
		TestReporter.assertTrue(Regex.match("[0-9]+", cancel.getCancellationConfirmationNumber()), "The cancellation number ["+cancel.getCancellationConfirmationNumber()+"] was not numeric as expected.");

		LogItems logItems = new LogItems();
		logItems.addItem("AccommodationInventoryRequestComponentServiceIF", "releaseInventory", false);
		logItems.addItem("ChargeGroupIF", "cancelChargeGroups", false);
		logItems.addItem("TravelPlanServiceCrossReferenceV3", "cancelOrder", false);
		logItems.addItem("UpdateInventory", "updateInventory", false);
		validateLogs(cancel, logItems);
	}

	@Test( groups = {"api", "regression", "dining", "eventDiningService"})
	public void testCancel_DLR() {
		hh = new HouseHold(1);
		Book book = new Book(environment, "DLRTableServiceOneChild");
		book.setParty(hh);
		book.setServiceStartDateTime(Randomness.generateCurrentXMLDate(1));
		book.sendRequest();
		
		Cancel cancel = new Cancel(environment, "CancelDiningEvent");
		cancel.setReservationNumber(book.getTravelPlanSegmentId());
		cancel.sendRequest();
		TestReporter.logAPI(!cancel.getResponseStatusCode().contains("200"), cancel.getFaultString() ,cancel);
		TestReporter.assertTrue(Regex.match("[0-9]+", cancel.getCancellationConfirmationNumber()), "The cancellation number ["+cancel.getCancellationConfirmationNumber()+"] was not numeric as expected.");

		LogItems logItems = new LogItems();
		logItems.addItem("AccommodationInventoryRequestComponentServiceIF", "releaseInventory", false);
		logItems.addItem("ChargeGroupIF", "cancelChargeGroups", false);
		logItems.addItem("TravelPlanServiceCrossReferenceV3", "cancelOrder", false);
		logItems.addItem("UpdateInventory", "updateInventory", false);
		validateLogs(cancel, logItems);
	}
	@Test(groups = {"api", "regression", "dining", "eventDiningService"})
	public void testCancelTrueDiningReservation(){
		Book book = new Book(environment, ScheduledEventReservation.NOCOMPONENTSNOADDONS);
		book.setParty(new HouseHold(1));
		book.setServiceStartDateTime(Randomness.generateCurrentXMLDate(Randomness.randomNumberBetween(1, 15)));
		book.setReservableResourceId("BA054CBB-D573-C672-BE95-173042178DBE");
		book.addDetailsByFacilityNameAndProductName("The Hollywood Brown Derby", "Brown Derby Lunch F! 1st Show");
		book.addSpecialEventByProductName("Fantasmic! Viewing 1st Show");
		book.sendRequest();
		
		TestReporter.logAPI(!book.getResponseStatusCode().contains("200"), "An error occurred bookingan event dining reservation: " + book.getFaultString() ,book);
		TestReporter.assertTrue(Regex.match("[0-9]+", book.getTravelPlanId()), "The travel plan ID ["+book.getTravelPlanId()+"] is numeric as expected.");
		TestReporter.assertTrue(Regex.match("[0-9]+", book.getTravelPlanSegmentId()), "The reservation number ["+book.getTravelPlanSegmentId()+"] is numeric as expected.");
		TPS_ID=book.getTravelPlanSegmentId();
		
		Cancel cancel = new Cancel(environment, "CancelDiningEvent");
		cancel.setReservationNumber(book.getTravelPlanSegmentId());
		cancel.sendRequest();
		TestReporter.logAPI(!cancel.getResponseStatusCode().contains("200"), cancel.getFaultString() ,cancel);
		TestReporter.assertTrue(Regex.match("[0-9]+", cancel.getCancellationConfirmationNumber()), "The cancellation number ["+cancel.getCancellationConfirmationNumber()+"] was not numeric as expected.");

		LogItems logItems = new LogItems();
		logItems.addItem("AccommodationInventoryRequestComponentServiceIF", "releaseInventory", false);
		logItems.addItem("ChargeGroupIF", "cancelChargeGroups", false);
		logItems.addItem("TravelPlanServiceCrossReferenceV3", "cancelOrder", false);
		logItems.addItem("UpdateInventory", "updateInventory", false);
		validateLogs(cancel, logItems);	
	}
}