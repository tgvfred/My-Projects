package com.disney.composite.api.activityModule.activityService.noShow;

import org.testng.annotations.Test;

import com.disney.api.soapServices.activityModule.activityServicePort.operations.Book;
import com.disney.api.soapServices.activityModule.activityServicePort.operations.NoShow;
import com.disney.composite.BaseTest;
import com.disney.utils.Regex;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.database.LogItems;
import com.disney.utils.dataFactory.guestFactory.HouseHold;

public class TestNoShow extends BaseTest{	

	@Test(groups = {"api", "regression", "activity", "activityService"})
	public void testNoShow_Child(){
		TestReporter.logScenario("No Show Child Activity");
		HouseHold hh = new HouseHold(1);
		hh.primaryGuest().setAge("10");
		Book book = new Book(environment, "NoComponentsNoAddOns");
		book.setParty(hh);
		book.addDetailsByFacilityNameAndProductName("Bibbidi Bobbidi Boutique - Magic Kingdom", "C E - Bibbidi Bobbidi Boutique - MK");
		book.setProductType("ChildActivityProduct");
		bookAndNoShow(book);
	}
	

	@Test(groups = {"api", "regression", "activity", "activityService"})
	public void testNoShow_Recreation(){
		TestReporter.logScenario("No Show Recreation");
		Book book = new Book(environment, "NoComponentsNoAddOns");
		book.setParty(hh);
		book.addDetailsByFacilityNameAndProductName("Bay Lake Pool", "Cabana - CO - 4 Hour Rental");
		book.setProductType("RecreationActivityProduct");
		bookAndNoShow(book);
	}
	@Test(groups = {"api", "regression", "activity", "activityService"})
	public void testNoShow_Child_DLR(){
		TestReporter.logScenario("No Show DLR Child Activity");
		HouseHold hh = new HouseHold(1);
		hh.primaryGuest().setAge("10");
		Book book = new Book(environment, "NoComponentsNoAddOns");
		book.setParty(hh);
		book.setFacilityId("432145");
		book.setProductId("162725");
		book.setSourceAccountingCenter("5005");
		book.setProductType("ChildActivityProduct");
		book.setReservableResourceId("CAF43AD9-457B-7044-E043-9906F4D17044");
		bookAndNoShow(book);
	}
	

	@Test(groups = {"api", "regression", "activity", "activityService"})
	public void testNoShow_Recreation_DLR(){
		TestReporter.logScenario("No Show DLR Recreation");
		Book book = new Book(environment, "NoComponentsNoAddOns");
		book.setParty(hh);
		book.setFacilityId("2135034");
		book.setProductId("162265");
		book.setSourceAccountingCenter("5005");
		book.setProductType("RecreationActivityProduct");
		book.setReservableResourceId("CAF43AD9-459F-7044-E043-9906F4D17044");
		bookAndNoShow(book);
	}
	
	private void bookAndNoShow(Book book){
		book.sendRequest();
		TestReporter.logAPI(!book.getResponseStatusCode().equals("200"), "An error occurred during booking: " + book.getFaultString() , book);
		
		TestReporter.log("Reservation Number: <b>" + book.getTravelPlanSegmentId() + "</b>");
		NoShow noShow = new NoShow(environment, "Main");
		noShow.setReservationNumber(book.getTravelPlanSegmentId());
		noShow.sendRequest();		
		TestReporter.logAPI(!noShow.getResponseStatusCode().contains("200"), noShow.getFaultString() ,noShow);
		TestReporter.assertTrue(Regex.match("[0-9]+", noShow.getCancellationNumber()), "The cancellation number ["+noShow.getCancellationNumber()+"] was not numeric as expected.");

		LogItems logItems = new LogItems();
		logItems.addItem("ChargeGroupIF", "processNoShow", false);
		logItems.addItem("ActivityServiceIF", "noShow", false);
		logItems.addItem("PricingService", "getCancellationCharges", false);
		logItems.addItem("TravelPlanServiceCrossReferenceV3", "updateOrder", false);
		logItems.addItem("TravelPlanServiceCrossReferenceV3SEI", "updateOrder", false);
		logItems.addItem("UpdateInventory", "updateInventory", false);
		logItems.addItem("FolioServiceIF", "retrieveAccountingTransactions", false);
		logItems.addItem("PartyIF", "retrieveParty", false);
		logItems.addItem("PartyIF", "retrievePartyBasicInformation", false);
		logItems.addItem("AccommodationInventoryRequestComponentServiceIF", "releaseInventory", false);
		logItems.addItem("AccommodationInventoryRequestComponentServiceIF", "retrieveAssignmentOwner", false);
		logItems.addItem("ActivityServiceIF", "retrieve", false);
		logItems.addItem("PackagingService", "getProducts", false);
		logItems.addItem("PricingService", "priceComponents", false);
		validateLogs(noShow, logItems);
	}
}