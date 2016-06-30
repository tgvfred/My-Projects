package com.disney.composite.api.eventDiningService.book;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.disney.api.soapServices.eventDiningService.operations.Book;
import com.disney.api.soapServices.eventDiningService.operations.Cancel;
import com.disney.composite.BaseTest;
import com.disney.utils.Regex;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.database.LogItems;
import com.disney.utils.dataFactory.guestFactory.HouseHold;

public class TestBook extends BaseTest{
	// Defining global variables
	protected String TPS_ID = null;
	
	@BeforeMethod(alwaysRun=true)
	public void testBook(){
		hh = new HouseHold(1);
		Book book = new Book(environment, "NoComponentsNoAddOns");
		book.setParty(hh);
		book.sendRequest();
		TPS_ID=book.getTravelPlanSegmentId();
		
	}

	@Test(dependsOnMethods = {"testBook"}, groups = {"api", "regression", "dining", "eventDiningService"})
	public void testCancel() {
		Cancel cancel = new Cancel(environment, "CancelDiningEvent");
		cancel.setReservationNumber(TPS_ID);
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