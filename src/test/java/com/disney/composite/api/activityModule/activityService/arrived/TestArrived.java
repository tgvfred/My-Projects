package com.disney.composite.api.activityModule.activityService.arrived;

import org.testng.annotations.Test;

import com.disney.api.BaseTest;
import com.disney.api.soapServices.activityModule.activityServicePort.operations.Arrived;
import com.disney.api.soapServices.activityModule.activityServicePort.operations.Book;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.database.LogItems;
import com.disney.utils.dataFactory.guestFactory.HouseHold;
import com.disney.utils.dataFactory.staging.bookSEReservation.ScheduledEventReservation;

public class TestArrived  extends BaseTest{
	protected ScheduledEventReservation res = null;

	@Test(groups = {"api", "regression", "activity", "activityService"})
	public void testArrive_Child(){
		HouseHold hh = new HouseHold(1);
		hh.primaryGuest().setAge("10");
		Book book = new Book(environment, "NoComponentsNoAddOns");
		book.setParty(hh);
		book.addDetailsByFacilityNameAndProductName("Bibbidi Bobbidi Boutique - Magic Kingdom", "C E - Bibbidi Bobbidi Boutique - MK");
		book.setProductType("ChildActivityProduct");
		bookAndArrive(book);
	}
	

	@Test(groups = {"api", "regression", "activity", "activityService"})
	public void testArrive_Recreation(){
		TestReporter.logScenario("Arrived Recreation");
		Book book = new Book(environment, "NoComponentsNoAddOns");
		book.setParty(hh);
		book.addDetailsByFacilityNameAndProductName("Bay Lake Pool", "Cabana - CO - 4 Hour Rental");
		book.setProductType("RecreationActivityProduct");
		bookAndArrive(book);
	}
	@Test(groups = {"api", "regression", "activity", "activityService"})
	public void testArrive_Child_DLR(){
		HouseHold hh = new HouseHold(1);
		hh.primaryGuest().setAge("10");
		Book book = new Book(environment, "NoComponentsNoAddOns");
		book.setParty(hh);
		book.setFacilityId("432145");
		book.setProductId("162725");
		book.setSourceAccountingCenter("5005");
		book.setProductType("ChildActivityProduct");
		book.setReservableResourceId("CAF43AD9-457B-7044-E043-9906F4D17044");
		bookAndArrive(book);
	}
	

	@Test(groups = {"api", "regression", "activity", "activityService"})
	public void testArrive_Recreation_DLR(){
		TestReporter.logScenario("Arrived Recreation");
		Book book = new Book(environment, "NoComponentsNoAddOns");
		book.setParty(hh);
		book.setFacilityId("2135034");
		book.setProductId("162265");
		book.setSourceAccountingCenter("5005");
		book.setProductType("RecreationActivityProduct");
		book.setReservableResourceId("CAF43AD9-459F-7044-E043-9906F4D17044");
		bookAndArrive(book);
	}
	
	private void bookAndArrive(Book book){
		book.sendRequest();
		TestReporter.logAPI(!book.getResponseStatusCode().equals("200"), "An error occurred during booking: " + book.getFaultString() , book);
		
		TestReporter.log("Reservation Number: <b>" + book.getTravelPlanSegmentId() + "</b>");
		Arrived arrived = new Arrived(this.environment,"Main");
		arrived.setReservationNumber(book.getTravelPlanSegmentId());
		arrived.sendRequest();
		TestReporter.logAPI(!arrived.getResponseStatusCode().contains("200"), arrived.getFaultString() ,arrived);
		TestReporter.logAPI(!arrived.getArrivalStatus().equals("SUCCESS"), "The response ["+arrived.getArrivalStatus()+"] was not 'SUCCESS' as expected.", arrived);		

		LogItems logItems = new LogItems();
		logItems.addItem("ActivityServiceIF", "arrived", false);
		logItems.addItem("ChargeGroupIF", "checkIn", false);
		logItems.addItem("TravelPlanServiceCrossReferenceV3", "updateOrder", false);
		logItems.addItem("TravelPlanServiceCrossReferenceV3SEI", "updateOrder", false);
		logItems.addItem("FolioServiceIF", "retrieveAccountingTransactions", false);
		logItems.addItem("PartyIF", "retrievePartyBasicInformation", false);
		logItems.addItem("PartyIF", "retrieveParty", false);
		logItems.addItem("AccommodationInventoryRequestComponentServiceIF", "retrieveAssignmentOwner", false);
		logItems.addItem("ActivityServiceIF", "retrieve", false);
		logItems.addItem("PackagingService", "getProducts", false);
		logItems.addItem("PricingService", "priceComponents", false);		
		validateLogs(arrived, logItems);
	}
}