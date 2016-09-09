package com.disney.composite.api.activityModule.activityService.cancel;

import org.testng.annotations.Test;

import com.disney.api.BaseTest;
import com.disney.api.soapServices.activityModule.activityServicePort.operations.Book;
import com.disney.api.soapServices.activityModule.activityServicePort.operations.Cancel;
import com.disney.utils.Regex;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.database.LogItems;
import com.disney.utils.dataFactory.guestFactory.HouseHold;
import com.disney.utils.dataFactory.staging.bookSEReservation.ScheduledEventReservation;

public class TestCancel extends BaseTest{
	// Defining global variables
	protected String TPS_ID = null;
	protected ScheduledEventReservation res = null;
	
	@Test(groups = {"api", "regression", "activity", "activityService"})
	public void testCancel_Child(){
		TestReporter.logScenario("Cancel Child Activity");
		HouseHold hh = new HouseHold(1);
		hh.primaryGuest().setAge("10");
		Book book = new Book(environment, "NoComponentsNoAddOns");
		book.setParty(hh);
		book.addDetailsByFacilityNameAndProductName("Bibbidi Bobbidi Boutique - Magic Kingdom", "C E - Bibbidi Bobbidi Boutique - MK");
		book.setProductType("ChildActivityProduct");
		bookAndCancel(book);
	}
	

	@Test(groups = {"api", "regression", "activity", "activityService"})
	public void testCancel_Recreation(){
		TestReporter.logScenario("Cancel Recreation");
		Book book = new Book(environment, "NoComponentsNoAddOns");
		book.setParty(hh);
		book.addDetailsByFacilityNameAndProductName("Bay Lake Pool", "Cabana - CO - 4 Hour Rental");
		book.setProductType("RecreationActivityProduct");
		bookAndCancel(book);
	}
	@Test(groups = {"api", "regression", "activity", "activityService"})
	public void testCancel_Child_DLR(){
		TestReporter.logScenario("Cancel DLR Child Activity");
		HouseHold hh = new HouseHold(1);
		hh.primaryGuest().setAge("10");
		Book book = new Book(environment, "NoComponentsNoAddOns");
		book.setParty(hh);
		book.setFacilityId("432145");
		book.setProductId("162725");
		book.setSourceAccountingCenter("5005");
		book.setProductType("ChildActivityProduct");
		book.setReservableResourceId("CAF43AD9-457B-7044-E043-9906F4D17044");
		bookAndCancel(book);
	}
	

	@Test(groups = {"api", "regression", "activity", "activityService"})
	public void testCancel_Recreation_DLR(){
		TestReporter.logScenario("Cancel DLR Recreation");
		Book book = new Book(environment, "NoComponentsNoAddOns");
		book.setParty(hh);
		book.setFacilityId("2135034");
		book.setProductId("162265");
		book.setSourceAccountingCenter("5005");
		book.setProductType("RecreationActivityProduct");
		book.setReservableResourceId("CAF43AD9-459F-7044-E043-9906F4D17044");
		bookAndCancel(book);
	}
	
	private void bookAndCancel(Book book){
		book.sendRequest();
		TestReporter.logAPI(!book.getResponseStatusCode().equals("200"), "An error occurred during booking: " + book.getFaultString() , book);
		
		TestReporter.log("Reservation Number: <b>" + book.getTravelPlanSegmentId() + "</b>");
		Cancel cancel = new Cancel(environment, "CancelDiningEvent");
		cancel.setReservationNumber(book.getTravelPlanSegmentId());
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
		validateLogs(cancel, logItems, 8000);
	}
}