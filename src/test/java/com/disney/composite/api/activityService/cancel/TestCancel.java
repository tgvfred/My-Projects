package com.disney.composite.api.activityService.cancel;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.activityServicePort.operations.Cancel;
import com.disney.composite.BaseTest;
import com.disney.utils.Regex;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.database.LogItems;
import com.disney.utils.dataFactory.guestFactory.HouseHold;
import com.disney.utils.dataFactory.staging.bookSEReservation.ActivityEventReservation;
import com.disney.utils.dataFactory.staging.bookSEReservation.ScheduledEventReservation;

public class TestCancel extends BaseTest{
	// Defining global variables
	protected String TPS_ID = null;
	protected ScheduledEventReservation res = null;
	
	
	@Override
	@BeforeClass(alwaysRun = true)
	@Parameters({ "environment" })
	public void setup(@Optional String environment){
		this.environment = environment;
		hh = new HouseHold(1);
		hh.primaryGuest().setAge("6");
		res = new ActivityEventReservation(this.environment, hh);
		res.book(ScheduledEventReservation.NOCOMPONENTSNOADDONS);
	}

	@Test( groups = {"api", "regression", "activity", "eventDiningService"})
	public void testCancel() {
		TestReporter.logScenario("Cancel");
		TestReporter.log("Reservation Number: <b>" + res.getConfirmationNumber() + "</b>");
		Cancel cancel = new Cancel(environment, "CancelDiningEvent");
		cancel.setReservationNumber(res.getConfirmationNumber());
		cancel.sendRequest();
		TestReporter.logAPI(!cancel.getResponseStatusCode().equals("200"), "An error occurred during cancellation.", cancel);
		TestReporter.assertTrue(Regex.match("[0-9]+", cancel.getCancellationConfirmationNumber()), "The cancellation number ["+cancel.getCancellationConfirmationNumber()+"] was not numeric as expected.");

		LogItems logItems = new LogItems();
		logItems.addItem("ActivityServiceIF", "cancel", false);
		logItems.addItem("AccommodationInventoryRequestComponentServiceIF", "releaseInventory", false);
		logItems.addItem("ChargeGroupIF", "cancelChargeGroups", false);
		logItems.addItem("TravelPlanServiceCrossReferenceV3", "cancelOrder", false);
		logItems.addItem("UpdateInventory", "updateInventory", false);
		logItems.addItem("FolioServiceIF", "retrieveAccountingTransactions", false);
		logItems.addItem("TravelPlanServiceCrossReferenceV3SEI", "cancelOrder", false);
		logItems.addItem("ChargeGroupIF", "getTaxExempt", false);
		logItems.addItem("PartyIF", "retrievePartyBasicInformation", false);
		logItems.addItem("PartyIF", "retrieveParty", false);
		logItems.addItem("AccommodationInventoryRequestComponentServiceIF", "retrieveAssignmentOwner", false);
		logItems.addItem("ActivityServiceIF", "retrieve", false);
		logItems.addItem("PackagingService", "getProducts", false);
		logItems.addItem("PricingService", "priceComponents", false);
		validateLogs(cancel, logItems);
	}
}