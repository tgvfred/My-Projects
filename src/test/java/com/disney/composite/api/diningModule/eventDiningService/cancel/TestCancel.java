package com.disney.composite.api.diningModule.eventDiningService.cancel;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.SoapException;
import com.disney.api.soapServices.diningModule.eventDiningService.operations.Cancel;
import com.disney.composite.BaseTest;
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
		ScheduledEventReservation res = new EventDiningReservation(this.environment, hh);
		res.book("DLRTableServiceOneChild");
		Cancel cancel = new Cancel(environment, "CancelDiningEvent");
		cancel.setReservationNumber(res.getConfirmationNumber());
		cancel.sendRequest();
		TestReporter.assertTrue(Regex.match("[0-9]+", cancel.getCancellationConfirmationNumber()), "The cancellation number ["+cancel.getCancellationConfirmationNumber()+"] was not numeric as expected.");

		LogItems logItems = new LogItems();
		logItems.addItem("AccommodationInventoryRequestComponentServiceIF", "releaseInventory", false);
		logItems.addItem("ChargeGroupIF", "cancelChargeGroups", false);
		logItems.addItem("TravelPlanServiceCrossReferenceV3", "cancelOrder", false);
		logItems.addItem("UpdateInventory", "updateInventory", false);
		validateLogs(cancel, logItems);
	}
}