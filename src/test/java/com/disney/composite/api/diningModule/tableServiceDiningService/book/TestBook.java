package com.disney.composite.api.diningModule.tableServiceDiningService.book;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import com.disney.api.soapServices.ServiceConstants;
import com.disney.api.soapServices.diningModule.scheduledEventsServicePort.operations.RetrieveAllergies;
import com.disney.api.soapServices.diningModule.tableServiceDiningServicePort.operations.Book;
import com.disney.api.soapServices.diningModule.tableServiceDiningServicePort.operations.Cancel;
import com.disney.composite.BaseTest;
import com.disney.test.utils.Sleeper;
import com.disney.utils.Randomness;
import com.disney.utils.Regex;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.database.LogItems;
import com.disney.utils.dataFactory.guestFactory.HouseHold;
import com.disney.utils.dataFactory.staging.bookSEReservation.ScheduledEventReservation;

public class TestBook extends BaseTest{
	// Defining global variables
	protected ThreadLocal<String> TPS_ID = new ThreadLocal<String>();
	
	@AfterMethod(alwaysRun=true)
	public void teardown(){
		try{
			if(TPS_ID.get() != null)
				if(!TPS_ID.get().isEmpty()){
					Cancel cancel = new Cancel(environment, "Main");
					cancel.setReservationNumber(TPS_ID.get());
					cancel.sendRequest();
				}
		}catch(Exception e){}
	}
	
	@Test(groups = {"api", "regression", "dining", "tableServiceDiningService"})
	public void testBook(){
		TestReporter.logScenario("1 Adult");
		Book book = new Book(environment, ScheduledEventReservation.NOCOMPONENTSNOADDONS);
		hh = new HouseHold(1);
		book.setParty(hh);

		bookAndValidate(book);
	}
	
	@Test(groups = {"api", "regression", "dining", "tableServiceDiningService"})
	public void testBookWithComponentsAndAddon(){
		TestReporter.logScenario("Book table Service with components and Addon");
		Book book = new Book(environment, "TableServiceAddOn");
		hh = new HouseHold(1);
		book.setParty(hh);
		bookAndValidate(book);
	}
	
	@Test(groups = {"api", "regression", "dining", "tableServiceDiningService"})
	public void testBookWithMultipleResources(){
		TestReporter.logScenario("1 Adult");
		Book book = new Book(environment, ScheduledEventReservation.NOCOMPONENTSNOADDONS);
		hh = new HouseHold(1);
		book.setParty(hh);
		book.setNumberOfResource("2");
		bookAndValidate(book);
	}
	@Test(groups = {"api", "regression", "dining", "tableServiceDiningService"})
	public void testBook_DLR(){
		TestReporter.logScenario("Book with DLR Product");
		Book book = new Book(environment, "DLRTableServiceOneChild");
		hh = new HouseHold(1);
		hh.primaryGuest().setAge("10");
		book.setParty(hh);

		bookAndValidate(book);
	}
	@Test(groups = {"api", "regression", "dining", "tableServiceDiningService" })
	public void testBookWithGuestRequests(){
		TestReporter.logStep("Book a show dining reservation with a Guest Request.");
		Book book = new Book(environment, ScheduledEventReservation.NOCOMPONENTSNOADDONS);
		hh = new HouseHold(1);
		book.setParty(hh);
		book.setProfileDetailIdAndType(ServiceConstants.SeGuestRequests.BOOSTER_SEAT_ID, "GuestRequest");

		bookAndValidate(book);
	}

	@Test(groups = {"api", "regression", "dining", "tableServiceDiningService" })
	public void testBookWithMultipleGuestRequests(){
		TestReporter.logStep("Book a show dining reservation with multiple Guest Requests.");
		Book book = new Book(environment, ScheduledEventReservation.NOCOMPONENTSNOADDONS);
		hh = new HouseHold(1);
		book.setParty(hh);
		book.setProfileDetailIdAndType(ServiceConstants.SeGuestRequests.BOOSTER_SEAT_ID, "GuestRequest");
		book.setProfileDetailIdAndType(ServiceConstants.SeGuestRequests.REQUEST_TWO_HIGH_CHAIRS_ID, "GuestRequest");
		book.setProfileDetailIdAndType(ServiceConstants.SeGuestRequests.HIGH_CHAIR_ID, "GuestRequest");
		book.sendRequest();

		bookAndValidate(book);
	}
	
	@Test(groups = {"api", "regression", "dining", "tableServiceDiningService" })
	public void testBookWithSpecialNeeds(){
		TestReporter.logStep("Book a show dining reservation with a Special Need.");
		Book book = new Book(environment, ScheduledEventReservation.NOCOMPONENTSNOADDONS);
		hh = new HouseHold(1);
		book.setParty(hh);
		book.setProfileDetailIdAndType(ServiceConstants.SeSpecialNeeds.HEARING_LOSS_ID, "SeSpecialNeed");
		bookAndValidate(book);
	}

	@Test(groups = {"api", "regression", "dining", "tableServiceDiningService" })
	public void testBookWithMultipleSpecialNeeds(){
		TestReporter.logStep("Book a show dining reservation with multiple Special Needs.");
		Book book = new Book(environment, ScheduledEventReservation.NOCOMPONENTSNOADDONS);
		hh = new HouseHold(1);
		book.setParty(hh);
		book.setProfileDetailIdAndType(ServiceConstants.SeSpecialNeeds.HEARING_LOSS_ID, "SeSpecialNeed");
		book.setProfileDetailIdAndType(ServiceConstants.SeSpecialNeeds.LIMITED_MOBILITY_ID, "SeSpecialNeed");
		book.setProfileDetailIdAndType(ServiceConstants.SeSpecialNeeds.OXYGEN_TANK_USE_ID, "SeSpecialNeed");

		bookAndValidate(book);
	}

	@Test(groups = {"api", "regression", "dining", "tableServiceDiningService" })
	public void testBookWithComments(){
		TestReporter.logStep("Book a show dining reservation with an Internal Comment.");
		Book book = new Book(environment, ScheduledEventReservation.NOCOMPONENTSNOADDONS);
		hh = new HouseHold(1);
		book.setParty(hh);
		book.setComments("Internal Comments", "Internal");

		bookAndValidate(book);
	}

	@Test(groups = {"api", "regression", "dining", "tableServiceDiningService" })
	public void testBookWithMultipleComments(){
		TestReporter.logStep("Book a show dining reservation with multiple Comments.");
		Book book = new Book(environment, ScheduledEventReservation.NOCOMPONENTSNOADDONS);
		hh = new HouseHold(1);
		book.setParty(hh);
		book.setComments("Internal Comments", "Internal");
		book.setComments("More Internal Comments", "External");

		bookAndValidate(book);
	}

	@Test(groups = {"api", "regression", "dining", "tableServiceDiningService" })
	public void testBookWithMultipleGuestRequestsAndSpecialNeedsAndComments(){
		TestReporter.logStep("Book a show dining reservation with very needy guest.");
		Book book = new Book(environment, ScheduledEventReservation.NOCOMPONENTSNOADDONS);
		hh = new HouseHold(1);
		book.setParty(hh);
		book.setProfileDetailIdAndType(ServiceConstants.SeGuestRequests.BOOSTER_SEAT_ID, "GuestRequest");
		book.setProfileDetailIdAndType(ServiceConstants.SeGuestRequests.REQUEST_TWO_HIGH_CHAIRS_ID, "GuestRequest");
		book.setProfileDetailIdAndType(ServiceConstants.SeGuestRequests.HIGH_CHAIR_ID, "GuestRequest");
		book.setProfileDetailIdAndType(ServiceConstants.SeSpecialNeeds.HEARING_LOSS_ID, "SeSpecialNeed");
		book.setProfileDetailIdAndType(ServiceConstants.SeSpecialNeeds.LIMITED_MOBILITY_ID, "SeSpecialNeed");
		book.setProfileDetailIdAndType(ServiceConstants.SeSpecialNeeds.OXYGEN_TANK_USE_ID, "SeSpecialNeed");
		book.setComments("Internal Comments", "Internal");
		book.setComments("More Internal Comments", "External");

		bookAndValidate(book);
	}

	@Test(groups = {"api", "regression", "dining", "tableServiceDiningService"})
	public void testBookWithVipLevel2() {
		TestReporter.logStep("Book a show dining reservation with VIP Level 2.");
		Book book = new Book(environment, ScheduledEventReservation.NOCOMPONENTSNOADDONS);
		hh = new HouseHold(1);
		book.setParty(hh);
		book.setVipLevel("2");
		bookAndValidate(book);
	}

	@Test(groups = {"api", "regression", "dining", "tableServiceDiningService"})
	public void testBookWithTaxExemption(){
		TestReporter.logStep("Book a show dining reservation for a Guest with Tax Exemption.");
		hh = new HouseHold(1);
		Book book = new Book(environment, ScheduledEventReservation.NOCOMPONENTSNOADDONS);
		book.setTaxExemptDetails("123465789", "Military");
		book.setParty(hh);

		bookAndValidate(book);
	}
	
	@Test(groups = {"api", "regression", "dining", "tableServiceDiningService"})
	public void testBookGuestWithMembership() {
		TestReporter.logStep("Book a show dining reservation for a Guest with a Memebership.");
		Book book = new Book(environment, "GuestWithMembership");
		hh = new HouseHold(1);
		book.setParty(hh);
		bookAndValidate(book);
	}
	
	@Test(groups = {"api", "regression", "dining", "tableServiceDiningService"})
	public void testBookWithDefaultTravelAgency() {
		TestReporter.logStep("Book a show dining reservation with Inventory Override.");
		Book book = new Book(environment, ScheduledEventReservation.NOCOMPONENTSNOADDONS);
		hh = new HouseHold(1);
		book.setParty(hh);
		book.addTravelAgency("99999998");
		bookAndValidate(book);
	}

	@Test(groups = {"api", "regression", "dining", "tableServiceDiningService"})
	public void testBookWithInventoryOverride() {
		TestReporter.logStep("Book a show dining reservation with Inventory Override.");
		Book book = new Book(environment, ScheduledEventReservation.NOCOMPONENTSNOADDONS);
		hh = new HouseHold(1);
		book.setParty(hh);
		book.setInventoryOverrideReasonId("800060");
		bookAndValidate(book);
	}
	@Test(groups = {"api", "regression", "dining", "tableServiceDiningService"})
	public void testBookWith2Adults(){
		TestReporter.logScenario("2 Adults");
		Book book = new Book(environment, ScheduledEventReservation.NOCOMPONENTSNOADDONS);
		book.setParty(hh);

		bookAndValidate(book);
	}

	@Test(groups = {"api", "regression", "dining", "tableServiceDiningService"})
	public void testBookWith4Adults(){
		TestReporter.logScenario("4 Adults");
		hh = new HouseHold("4 Adults");
		Book book = new Book(environment, ScheduledEventReservation.NOCOMPONENTSNOADDONS);
		book.setParty(hh);

		bookAndValidate(book);
	}

	@Test(groups = {"api", "regression", "dining", "tableServiceDiningService"})
	public void testBookWith2Adults2Child(){
		TestReporter.logScenario("2 Adults, 2 Children");
		hh = new HouseHold("2 Adults 2 Child");	
		Book book = new Book(environment, ScheduledEventReservation.NOCOMPONENTSNOADDONS);
		book.setParty(hh);

		bookAndValidate(book);
	}

	@Test(groups = {"api", "regression", "dining", "tableServiceDiningService"})
	public void testBookWith4Adults2Child2Infant(){
		TestReporter.logScenario("4 Adults, 2 Children, 2 Infants");
		hh = new HouseHold("4 Adults 2 Child 2 Infant");	
		Book book = new Book(environment, ScheduledEventReservation.NOCOMPONENTSNOADDONS);
		book.setParty(hh);

		bookAndValidate(book);
	}
	

	@Test(groups = {"api", "regression", "dining", "tableServiceDiningService"})
	public void testBookWith12InParty(){
		TestReporter.logScenario("12 Adults");
		hh = new HouseHold(12);		
		Book book = new Book(environment, ScheduledEventReservation.NOCOMPONENTSNOADDONS);
		book.setParty(hh);

		bookAndValidate(book);
	}
	@Test(groups = {"api", "regression", "dining", "tableServiceDiningService", "it4", "s138180" })
	public void testAddAllergy(){
		Book book = new Book(environment, ScheduledEventReservation.NOCOMPONENTSNOADDONS);

		book.setParty(hh);
		book.setAllergies("Egg");
		bookAndValidate(book);
	
	}
	@Test(groups = {"api", "regression", "dining", "tableServiceDiningService", "it4", "s138180" })
	public void testAddTwoAllergies(){
		Book book = new Book(environment, ScheduledEventReservation.NOCOMPONENTSNOADDONS);

		book.setParty(hh);
		book.setAllergies("Egg");
		book.setAllergies("Corn");

		bookAndValidate(book);
		
	}
	@Test(groups = {"api", "regression", "dining", "tableServiceDiningService", "it4", "s138180" })
	public void testAddAllAllergies(){
		Book book = new Book(environment, ScheduledEventReservation.NOCOMPONENTSNOADDONS);

		book.setParty(hh);
		RetrieveAllergies retrieveAllergies = new RetrieveAllergies(environment);
		retrieveAllergies.sendRequest();
		TestReporter.logAPI(!retrieveAllergies.getResponseStatusCode().equals("200"), "An error occurred during booking: " + retrieveAllergies.getFaultString(), retrieveAllergies);
		
		for(String allergy : retrieveAllergies.getAllergies().values()){
			book.setAllergies(allergy);
		}		

		bookAndValidate(book);	
	}
	
	private void bookAndValidate(Book book){
		
		book.sendRequest();
		
		TestReporter.logAPI(!book.getResponseStatusCode().contains("200"), book.getFaultString() ,book);
		TestReporter.assertTrue(Regex.match("[0-9]+", book.getTravelPlanId()), "The travel plan ID ["+book.getTravelPlanId()+"] is not numeric as expected.");
		TestReporter.assertTrue(Regex.match("[0-9]+", book.getTravelPlanSegmentId()), "The reservation number ["+book.getTravelPlanSegmentId()+"] is not numeric as expected.");
		TPS_ID.set(book.getTravelPlanSegmentId());
		
		LogItems logItems = new LogItems();
		//	logItems.addItem("TableServiceDiningServiceIF", "book", false);
			logItems.addItem("AccommodationInventoryRequestComponentServiceIF", "createInventory", true);
		//	logItems.addItem("ChargeGroupIF", "createChargeGroupAndPostCharges", false);
			logItems.addItem("PartyIF", "createAndRetrieveParty", false);	
		//	logItems.addItem("TravelPlanServiceV3", "create", false);
		//	logItems.addItem("UpdateInventory", "updateInventory", false);
		//	logItems.addItem("TravelPlanServiceV3SEI", "create", false);
			logItems.addItem("GuestServiceV1", "create", false);
		//	logItems.addItem("GuestLinkServiceV1", "createEntitlementReference", false);
			logItems.addItem("PartyIF", "updateExternalPartyAndLocatorId", false);			
			
			if(environment.equalsIgnoreCase("Sleepy")){
				logItems.addItem("GuestServiceV1", "create", false); //Sleepy only
				logItems.addItem("GuestLinkServiceV1SEI", "createEntitlementReference", false); //Sleepy only
			}
				
			validateLogs(book, logItems, 10000);
		
	}
	
}
