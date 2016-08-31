package com.disney.composite.api.activityModule.activityService.book;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import com.disney.api.soapServices.ServiceConstants;
import com.disney.api.soapServices.activityModule.activityServicePort.operations.Book;
import com.disney.api.soapServices.activityModule.activityServicePort.operations.Cancel;
import com.disney.api.soapServices.diningModule.scheduledEventsServicePort.operations.RetrieveAllergies;
import com.disney.composite.BaseTest;
import com.disney.test.utils.Sleeper;
import com.disney.utils.Randomness;
import com.disney.utils.Regex;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.database.LogItems;
import com.disney.utils.dataFactory.guestFactory.HouseHold;

public class TestBook extends BaseTest{
	protected ThreadLocal<String> TPS_ID = new ThreadLocal<String>();
	
	@AfterMethod(alwaysRun=true)
	public void teardown(){
		try{
			Cancel cancel = new Cancel(environment, "CancelDiningEvent");
			cancel.setReservationNumber(TPS_ID.get());
			cancel.sendRequest();
		}catch(Exception e){}
	}
	
	@Test(groups = {"api", "regression", "activity", "activityService"})
	public void testBook(){
		TestReporter.logScenario("1 Adult");
		HouseHold hh = new HouseHold("1 Adult");
		Book book = new Book(environment, "NoComponentsNoAddOns");
		book.setParty(hh);
		book.addDetailsByFacilityNameAndProductName("Bay Lake Pool", "Cabana - CO - 4 Hour Rental");
		book.setProductType("RecreationActivityProduct");
		bookAndValidateLogs(book);
	}
	@Test(groups = {"api", "regression", "activity", "activityService"})
	public void testBookWith2Adults(){
		TestReporter.logScenario("2 Adults");
		HouseHold hh = new HouseHold("2 Adults");
		Book book = new Book(environment, "NoComponentsNoAddOns");
		book.setParty(hh);
		book.addDetailsByFacilityNameAndProductName("Bay Lake Pool", "Cabana - CO - 4 Hour Rental");
		book.setProductType("RecreationActivityProduct");
		bookAndValidateLogs(book);
	}
	@Test(groups = {"api", "regression", "activity", "activityService"})
	public void testBookWith4Adults(){
		TestReporter.logScenario("4 Adults");
		HouseHold hh = new HouseHold("4 Adults");
		Book book = new Book(environment, "NoComponentsNoAddOns");
		book.setParty(hh);
		book.addDetailsByFacilityNameAndProductName("Bay Lake Pool", "Cabana - CO - 4 Hour Rental");
		book.setProductType("RecreationActivityProduct");
		bookAndValidateLogs(book);
	}
	@Test(groups = {"api", "regression", "activity", "activityService"})
	public void testBookWith2Adults2Child(){
		TestReporter.logScenario("2 Adults, 2 Children");
		HouseHold hh = new HouseHold("2 Adults 2 Child");
		Book book = new Book(environment, "NoComponentsNoAddOns");
		book.setParty(hh);
		book.addDetailsByFacilityNameAndProductName("Bay Lake Pool", "Cabana - CO - 4 Hour Rental");
		book.setProductType("RecreationActivityProduct");
		bookAndValidateLogs(book);
	}
	@Test(groups = {"api", "regression", "activity", "activityService"})
	public void testBookWith4Adults2Child2Infant(){
		TestReporter.logScenario("4 Adults, 2 Children, 2 Infants");
		HouseHold hh = new HouseHold("4 Adults 2 Child 2 Infant");
		Book book = new Book(environment, "NoComponentsNoAddOns");
		book.setParty(hh);
		book.addDetailsByFacilityNameAndProductName("Bay Lake Pool", "Cabana - CO - 4 Hour Rental");
		book.setProductType("RecreationActivityProduct");
		bookAndValidateLogs(book);
	}
	@Test(groups = {"api", "regression", "activity", "activityService"})
	public void testBookWith12InParty(){
		TestReporter.logScenario("12 Adults");
		HouseHold hh = new HouseHold(12);
		Book book = new Book(environment, "NoComponentsNoAddOns");
		book.setParty(hh);
		book.addDetailsByFacilityNameAndProductName("Bay Lake Pool", "Cabana - CO - 4 Hour Rental");
		book.setProductType("RecreationActivityProduct");
		bookAndValidateLogs(book);
	}

	@Test(groups = {"api", "regression", "activity", "activityService"})
	public void testBookWithLargeRequest(){
		TestReporter.logScenario("12 Adults");
		HouseHold hh = new HouseHold(12);
		Book book = new Book(environment, "GuestWithMembership");
		book.setParty(hh);
		book.addDetailsByFacilityNameAndProductName("Bay Lake Pool", "Cabana - CO - 4 Hour Rental");
		book.setTaxExemptDetails("123465789", "Military");
		book.addTravelAgency("99999998");
		book.setComments("Internal Comments", "Internal");
		book.setComments("More Internal Comments", "External");
		book.setProfileDetailIdAndType(ServiceConstants.SeGuestRequests.BOOSTER_SEAT_ID, "GuestRequest");
		book.setProfileDetailIdAndType(ServiceConstants.SeGuestRequests.REQUEST_TWO_HIGH_CHAIRS_ID, "GuestRequest");
		book.setProfileDetailIdAndType(ServiceConstants.SeGuestRequests.HIGH_CHAIR_ID, "GuestRequest");
		book.setProfileDetailIdAndType(ServiceConstants.SeSpecialNeeds.HEARING_LOSS_ID, "SeSpecialNeed");
		book.setProfileDetailIdAndType(ServiceConstants.SeSpecialNeeds.LIMITED_MOBILITY_ID, "SeSpecialNeed");
		book.setProfileDetailIdAndType(ServiceConstants.SeSpecialNeeds.OXYGEN_TANK_USE_ID, "SeSpecialNeed");
		book.setProductType("RecreationActivityProduct");
		
		RetrieveAllergies retrieveAllergies = new RetrieveAllergies(environment);
		retrieveAllergies.sendRequest();
		TestReporter.logAPI(!retrieveAllergies.getResponseStatusCode().contains("200"), "An error occurred retrieving allergies: " + retrieveAllergies.getFaultString() ,retrieveAllergies);

		for(String allergy : retrieveAllergies.getAllergies().values()){
			book.setAllergies(allergy);
		}
		bookAndValidateLogs(book);
	}
	

	@Test(groups = {"api", "regression", "dining", "activityService"})
	public void testBookWithGuestRequests(){
		hh = new HouseHold(1);
		Book book = new Book(environment, "NoComponentsNoAddOns");
		book.setParty(hh);
		book.setProfileDetailIdAndType(ServiceConstants.SeGuestRequests.BOOSTER_SEAT_ID, "GuestRequest");

		bookAndValidateLogs(book);
	}
	
	@Test(groups = {"api", "regression", "dining", "activityService"})
	public void testBookWithComments(){
		hh = new HouseHold(1);
		Book book = new Book(environment, "NoComponentsNoAddOns");
		book.setParty(hh);
		book.setComments("Internal Comments", "Internal");

		bookAndValidateLogs(book);
	}

	@Test(groups = {"api", "regression", "dining", "activityService"})
	public void testBookWithMultipleComments(){
		hh = new HouseHold(1);
		Book book = new Book(environment, "NoComponentsNoAddOns");
		book.setParty(hh);
		book.setComments("Internal Comments", "Internal");
		book.setComments("More Internal Comments", "External");

		bookAndValidateLogs(book);
	}
	@Test(groups = {"api", "regression", "dining", "activityService"})
	public void testBookWithMultipleGuestRequests(){
		hh = new HouseHold(1);
		Book book = new Book(environment, "NoComponentsNoAddOns");
		book.setParty(hh);
		book.setProfileDetailIdAndType(ServiceConstants.SeGuestRequests.BOOSTER_SEAT_ID, "GuestRequest");
		book.setProfileDetailIdAndType(ServiceConstants.SeGuestRequests.REQUEST_TWO_HIGH_CHAIRS_ID, "GuestRequest");
		book.setProfileDetailIdAndType(ServiceConstants.SeGuestRequests.HIGH_CHAIR_ID, "GuestRequest");

		bookAndValidateLogs(book);
	}
	
	@Test(groups = {"api", "regression", "dining", "activityService"})
	public void testBookWithSpecialNeeds(){
		hh = new HouseHold(1);
		Book book = new Book(environment, "NoComponentsNoAddOns");
		book.setParty(hh);
		book.setProfileDetailIdAndType(ServiceConstants.SeSpecialNeeds.HEARING_LOSS_ID, "SeSpecialNeed");

		bookAndValidateLogs(book);
	}
	

	@Test(groups = {"api", "regression", "dining", "activityService"})
	public void testBookWithMultipleSpecialNeeds(){
		hh = new HouseHold(1);
		Book book = new Book(environment, "NoComponentsNoAddOns");
		book.setParty(hh);
		book.setProfileDetailIdAndType(ServiceConstants.SeSpecialNeeds.HEARING_LOSS_ID, "SeSpecialNeed");
		book.setProfileDetailIdAndType(ServiceConstants.SeSpecialNeeds.LIMITED_MOBILITY_ID, "SeSpecialNeed");
		book.setProfileDetailIdAndType(ServiceConstants.SeSpecialNeeds.OXYGEN_TANK_USE_ID, "SeSpecialNeed");

		bookAndValidateLogs(book);
	}
	

	@Test(groups = {"api", "regression", "dining", "activityService"})
	public void testBookWithMultipleGuestRequestsAndSpecialNeedsAndComments(){
		hh = new HouseHold(1);
		Book book = new Book(environment, "NoComponentsNoAddOns");
		book.setParty(hh);
		book.setProfileDetailIdAndType(ServiceConstants.SeGuestRequests.BOOSTER_SEAT_ID, "GuestRequest");
		book.setProfileDetailIdAndType(ServiceConstants.SeGuestRequests.REQUEST_TWO_HIGH_CHAIRS_ID, "GuestRequest");
		book.setProfileDetailIdAndType(ServiceConstants.SeGuestRequests.HIGH_CHAIR_ID, "GuestRequest");
		book.setProfileDetailIdAndType(ServiceConstants.SeSpecialNeeds.HEARING_LOSS_ID, "SeSpecialNeed");
		book.setProfileDetailIdAndType(ServiceConstants.SeSpecialNeeds.LIMITED_MOBILITY_ID, "SeSpecialNeed");
		book.setProfileDetailIdAndType(ServiceConstants.SeSpecialNeeds.OXYGEN_TANK_USE_ID, "SeSpecialNeed");
		book.setComments("Internal Comments", "Internal");
		book.setComments("More Internal Comments", "External");

		bookAndValidateLogs(book);
	}
	

	@Test(groups = {"api", "regression", "dining", "eventDiningService"})
	public void testBookWithVipLevel2(){
		hh = new HouseHold(1);
		Book book = new Book(environment, "NoComponentsNoAddOns");
		book.setParty(hh);
		book.setVipLevel("2");

		bookAndValidateLogs(book);
	}

	@Test(groups = {"api", "regression", "dining", "eventDiningService"})
	public void testBookWithInventoryOverride(){
		hh = new HouseHold(1);
		Book book = new Book(environment, "NoComponentsNoAddOns");
		book.setParty(hh);
		book.setInventoryOverrideReasonId("800060");

		bookAndValidateLogs(book);
	}

	@Test(groups = {"api", "regression", "dining", "eventDiningService"})
	public void testBookGuestWithMembership(){
		hh = new HouseHold(1);
		Book book = new Book(environment, "GuestWithMembership");
		book.setParty(hh);
		
		bookAndValidateLogs(book);
	}
	@Test(groups = {"api", "regression", "dining", "eventDiningService"})
	public void testBookWithDefaultTravelAgency(){
		hh = new HouseHold(1);
		Book book = new Book(environment, "NoComponentsNoAddOns");
		book.addTravelAgency("99999998");
		book.setParty(hh);

		bookAndValidateLogs(book);
	}

	@Test(groups = {"api", "regression", "dining", "eventDiningService"})
	public void testBookWithTaxExemption(){
		hh = new HouseHold(1);
		Book book = new Book(environment, "NoComponentsNoAddOns");
		book.setTaxExemptDetails("123465789", "Military");
		book.setParty(hh);

		bookAndValidateLogs(book);
	}
	@Test(groups = {"api", "regression", "activity", "activityService"})
	public void testBookChildActivity(){
		TestReporter.logScenario("Child Activity");
		HouseHold hh = new HouseHold(1);
		hh.primaryGuest().setAge("10");
		Book book = new Book(environment, "NoComponentsNoAddOns");
		book.setParty(hh);
		book.addDetailsByFacilityNameAndProductName("Bibbidi Bobbidi Boutique - Magic Kingdom", "C E - Bibbidi Bobbidi Boutique - MK");
		book.setProductType("ChildActivityProduct");
		bookAndValidateLogs(book);
	}
	
	@Test(groups = {"api", "regression", "activity", "activityService"})
	public void testBook_Recreation_DLR(){
		TestReporter.logScenario("DLR Recreation Activity");
		HouseHold hh = new HouseHold(1);
		Book book = new Book(environment, "NoComponentsNoAddOns");
		book.setParty(hh);
		book.setFacilityId("2135034");
		book.setProductId("162265");
		book.setSourceAccountingCenter("5005");
		book.setProductType("RecreationActivityProduct");
		book.setReservableResourceId("CAF43AD9-459F-7044-E043-9906F4D17044");
		bookAndValidateLogs(book);
	}
	

	@Test(groups = {"api", "regression", "activity", "activityService"})
	public void testBook_ChildActivity_DLR(){
		TestReporter.logScenario("DLR Child Activity");
		HouseHold hh = new HouseHold(1);
		hh.primaryGuest().setAge("10");
		Book book = new Book(environment, "NoComponentsNoAddOns");
		book.setParty(hh);
		book.setFacilityId("432145");
		book.setProductId("162725");
		book.setSourceAccountingCenter("5005");
		book.setProductType("ChildActivityProduct");
		book.setReservableResourceId("CAF43AD9-457B-7044-E043-9906F4D17044");
		bookAndValidateLogs(book);
	}

	@Test(groups = {"api", "regression", "activity", "activityService"})
	public void testAddAllergy(){
		hh = new HouseHold("1 Adult");
		Book book = new Book(environment, "NoComponentsNoAddOns");
		book.setParty(hh);
		book.setAllergies("Egg");

		bookAndValidateLogs(book);
	}
	

	@Test(groups = {"api", "regression", "activity", "activityService" })
	public void testAddTwoAllergies(){
		hh = new HouseHold("1 Adult");
		Book book = new Book(environment, "NoComponentsNoAddOns");
		book.setParty(hh);
		book.setAllergies("Egg");
		book.setAllergies("Corn");		

		bookAndValidateLogs(book);
	}
	

	@Test(groups = {"api", "regression", "activity", "activityService" })
	public void testAddAllAllergies(){
		hh = new HouseHold(1);
		Book book = new Book(environment, "NoComponentsNoAddOns");
		book.setParty(hh);
		book.addDetailsByFacilityNameAndProductName("Pioneer Hall", "Hoop-Dee-Doo-Cat 1-1st Show");
		RetrieveAllergies retrieveAllergies = new RetrieveAllergies(environment);
		retrieveAllergies.sendRequest();
		TestReporter.logAPI(!retrieveAllergies.getResponseStatusCode().contains("200"), "An error occurred retrieving allergies: " + retrieveAllergies.getFaultString() ,retrieveAllergies);

		for(String allergy : retrieveAllergies.getAllergies().values()){
			book.setAllergies(allergy);
		}

		bookAndValidateLogs(book);
	}

	private void bookAndValidateLogs(Book book){
		book.sendRequest();
		
		if(book.getResponse().contains("existingRootChargeBookEvent :Unexpected Error occurred")){
			Sleeper.sleep(Randomness.randomNumberBetween(1, 10) * 1000);
			book.sendRequest();
		}
		
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
//		logItems.addItem("ActivityServiceIF", "retrieve", false);
		logItems.addItem("FolioServiceIF", "retrieveAccountingTransactions", false);
		logItems.addItem("TravelPlanServiceV3SEI", "create", false);
		logItems.addItem("PartyIF", "retrievePartyBasicInformation", false);
		logItems.addItem("PartyIF", "retrieveParty", false);
		logItems.addItem("PartyIF", "createAndRetrieveParty", false);
		logItems.addItem("AccommodationInventoryRequestComponentServiceIF", "retrieveAssignmentOwner", false);
		logItems.addItem("FacilityMasterServiceSEI", "findFacilityByEnterpriseID", false);
		logItems.addItem("PackagingService", "getProducts", false);
		logItems.addItem("PricingService", "priceComponents", false);
		logItems.addItem("PartyIF", "updateExternalPartyAndLocatorId", false);
		
		if(environment.equalsIgnoreCase("Sleepy")){
			
		}
			
	//	validateLogs(book, logItems, 8000);
	}
}
