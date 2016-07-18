package com.disney.composite.api.activityService.book;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import com.disney.api.soapServices.activityServicePort.operations.Book;
import com.disney.api.soapServices.activityServicePort.operations.Cancel;
import com.disney.composite.BaseTest;
import com.disney.test.utils.Randomness;
import com.disney.utils.Regex;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.database.LogItems;
import com.disney.utils.dataFactory.guestFactory.HouseHold;

public class TestBook extends BaseTest{
	protected ThreadLocal<String> TPS_ID = new ThreadLocal<String>();
	
	@AfterMethod(alwaysRun=true)
	public void teardown(){
		try{
			if(TPS_ID.get() != null)
				if(!TPS_ID.get().isEmpty()){
					Cancel cancel = new Cancel(environment, "CancelDiningEvent");
					cancel.setReservationNumber(TPS_ID.get());
					cancel.sendRequest();
				}
		}catch(Exception e){}
	}
	
	@Test(groups = {"api", "regression", "activity", "activityService"})
	public void testBook(){
		TestReporter.logScenario("1 Adult");
		HouseHold hh = new HouseHold("1 Adult");
		bookAndValidateLogs(hh, false, false);
	}
	@Test(groups = {"api", "regression", "activity", "activityService"})
	public void testBookWith2Adults(){
		TestReporter.logScenario("2 Adults");
		HouseHold hh = new HouseHold("2 Adults");
		bookAndValidateLogs(hh, false, false);
	}
	@Test(groups = {"api", "regression", "activity", "activityService"})
	public void testBookWith4Adults(){
		TestReporter.logScenario("4 Adults");
		HouseHold hh = new HouseHold("4 Adults");
		bookAndValidateLogs(hh, false, false);
	}
	@Test(groups = {"api", "regression", "activity", "activityService"})
	public void testBookWith2Adults2Child(){
		TestReporter.logScenario("2 Adults, 2 Children");
		HouseHold hh = new HouseHold("2 Adults 2 Child");
		bookAndValidateLogs(hh, false, false);
	}
	@Test(groups = {"api", "regression", "activity", "activityService"})
	public void testBookWith4Adults2Child2Infant(){
		TestReporter.logScenario("4 Adults, 2 Children, 2 Infants");
		HouseHold hh = new HouseHold("4 Adults 2 Child 2 Infant");
		bookAndValidateLogs(hh, false, false);
	}
	@Test(groups = {"api", "regression", "activity", "activityService"})
	public void testBookWith12InParty(){
		TestReporter.logScenario("12 Adults");
		HouseHold hh = new HouseHold(12);
		bookAndValidateLogs(hh, false, false);
	}
	
	private void bookAndValidateLogs(HouseHold hh, boolean oneHundredEighty, boolean past){
		Book book = new Book(environment, "NoComponentsNoAddOns");
		book.setParty(hh);
		book.setFacilityId("80008044");
		book.setProductId("53972");
		book.setProductType("RecreationActivityProduct");
		if(oneHundredEighty) book.setServiceStartDateTime(Randomness.generateCurrentXMLDatetime(200));
		if(past) book.setServiceStartDateTime(Randomness.generateCurrentXMLDatetime(-1));
		book.sendRequest();
		TestReporter.logAPI(!book.getResponseStatusCode().contains("200"), book.getFaultString() ,book);
		TestReporter.assertTrue(Regex.match("[0-9]+", book.getTravelPlanId()), "Verify travel plan ID ["+book.getTravelPlanId()+"] is numeric.");
		TestReporter.assertTrue(Regex.match("[0-9]+", book.getTravelPlanSegmentId()), "Verify reservation number ["+book.getTravelPlanSegmentId()+"] is numeric.");
		TPS_ID.set(book.getTravelPlanSegmentId());
		
		LogItems logItems = new LogItems();
		logItems.addItem("ActivityServiceIF", "book", false);
		logItems.addItem("AccommodationInventoryRequestComponentServiceIF", "createInventory", false);
		logItems.addItem("ChargeGroupIF", "createChargeGroupAndPostCharges", false);
		logItems.addItem("PartyIF", "createAndRetrieveParty", false);	
		logItems.addItem("TravelPlanServiceV3", "create", false);
		logItems.addItem("UpdateInventory", "updateInventory", false);
		logItems.addItem("ActivityServiceIF", "retrieve", false);
		logItems.addItem("FolioServiceIF", "retrieveAccountingTransactions", false);
		logItems.addItem("TravelPlanServiceV3SEI", "create", false);
		logItems.addItem("PartyIF", "retrievePartyBasicInformation", false);
		logItems.addItem("PartyIF", "retrieveParty", false);
		logItems.addItem("PartyIF", "createAndRetrieveParty", false);
		logItems.addItem("AccommodationInventoryRequestComponentServiceIF", "retrieveAssignmentOwner", false);
		logItems.addItem("CampusServiceIF", "findAllCampuses", false);
		logItems.addItem("CampusServiceIF", "findAllLocationsByCampus", false);
		logItems.addItem("DateServiceIF", "retrievePostingDateByCampus", false);
		logItems.addItem("DateServiceIF", "retrievePostingDateBySource", false);
		logItems.addItem("AccommodationFacilityServiceSEI", "getAllFacilities", false);
		logItems.addItem("FacilityMasterServiceSEI", "findFacilityByEnterpriseID", false);
		logItems.addItem("PackagingService", "getProducts", false);
		logItems.addItem("PricingService", "priceComponents", false);
		logItems.addItem("PartyIF", "updateExternalPartyAndLocatorId", false);
		
		if(environment.equalsIgnoreCase("Sleepy")){
			
		}
			
		validateLogs(book, logItems);
	}
}