package com.disney.composite.api.diningModule.eventDiningService.book;

import org.testng.annotations.Test;

import com.disney.api.soapServices.ServiceConstants;
import com.disney.api.soapServices.core.BaseSoapCommands;
import com.disney.api.soapServices.diningModule.eventDiningService.operations.Book;
import com.disney.api.soapServices.diningModule.scheduledEventsServicePort.operations.RetrieveAllergies;
import com.disney.composite.BaseTest;
import com.disney.test.utils.Sleeper;
import com.disney.utils.Randomness;
import com.disney.utils.Regex;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.database.LogItems;
import com.disney.utils.dataFactory.folioInterface.FolioInterfaceSettlement;
import com.disney.utils.dataFactory.guestFactory.HouseHold;
import com.disney.utils.dataFactory.staging.GenerateReservation;
import com.disney.utils.dataFactory.staging.Reservation;
import com.disney.utils.dataFactory.staging.bookSEReservation.EventDiningReservation;
import com.disney.utils.dataFactory.staging.bookSEReservation.ScheduledEventReservation;

public class TestBook extends BaseTest{
	// Defining global variables
	protected String TPS_ID = null;
	
	@Test(groups = {"api", "regression", "dining", "eventDiningService"})
	public void testBook(){
		hh = new HouseHold(1);
		Book book = new Book(environment, "NoComponentsNoAddOns");
		book.setParty(hh);
		book.sendRequest();
		if(book.getResponse().toLowerCase().contains("unique constraint")){
			Sleeper.sleep(Randomness.randomNumberBetween(1, 5) * 1000);
			book.sendRequest();
		}
		TestReporter.logAPI(!book.getResponseStatusCode().contains("200"), book.getFaultString() ,book);
		TestReporter.assertTrue(Regex.match("[0-9]+", book.getTravelPlanId()), "The travel plan ID ["+book.getTravelPlanId()+"] is numeric as expected.");
		TestReporter.assertTrue(Regex.match("[0-9]+", book.getTravelPlanSegmentId()), "The reservation number ["+book.getTravelPlanSegmentId()+"] is numeric as expected.");
		TPS_ID=book.getTravelPlanSegmentId();
		
		LogItems logItems = new LogItems();
		logItems.addItem("AccommodationInventoryRequestComponentServiceIF", "createInventory", false);
		logItems.addItem("ChargeGroupIF", "createChargeGroupAndPostCharges", false);
		logItems.addItem("PartyIF", "createAndRetrieveParty", false);	
		logItems.addItem("TravelPlanServiceV3", "create", false);
		logItems.addItem("UpdateInventory", "updateInventory", false);
		
		if(environment.equalsIgnoreCase("Sleepy")){
			logItems.addItem("GuestServiceV1", "create", false); //Sleepy only
		}
			
		validateLogs(book, logItems);
	}

	@Test(groups = {"api", "regression", "dining", "eventDiningService"})
	public void testBookWithGuestRequests(){
		hh = new HouseHold(1);
		Book book = new Book(environment, "NoComponentsNoAddOns");
		book.setParty(hh);
		book.setProfileDetailIdAndType(ServiceConstants.SeGuestRequests.BOOSTER_SEAT_ID, "GuestRequest");
		book.sendRequest();
		if(book.getResponse().toLowerCase().contains("unique constraint")){
			Sleeper.sleep(Randomness.randomNumberBetween(1, 5) * 1000);
			book.sendRequest();
		}
		TestReporter.logAPI(!book.getResponseStatusCode().contains("200"), book.getFaultString() ,book);
		TestReporter.assertTrue(Regex.match("[0-9]+", book.getTravelPlanId()), "The travel plan ID ["+book.getTravelPlanId()+"] is numeric as expected.");
		TestReporter.assertTrue(Regex.match("[0-9]+", book.getTravelPlanSegmentId()), "The reservation number ["+book.getTravelPlanSegmentId()+"] is numeric as expected.");
		TPS_ID=book.getTravelPlanSegmentId();
		
		LogItems logItems = new LogItems();
		logItems.addItem("AccommodationInventoryRequestComponentServiceIF", "createInventory", false);
		logItems.addItem("ChargeGroupIF", "createChargeGroupAndPostCharges", false);
		logItems.addItem("PartyIF", "createAndRetrieveParty", false);	
		logItems.addItem("TravelPlanServiceV3", "create", false);
		logItems.addItem("UpdateInventory", "updateInventory", false);
		
		if(environment.equalsIgnoreCase("Sleepy")){
			logItems.addItem("GuestServiceV1", "create", false); //Sleepy only
		}
			
		validateLogs(book, logItems);
	}
	
	@Test(groups = {"api", "regression", "dining", "eventDiningService"})
	public void testBookWithComments(){
		hh = new HouseHold(1);
		Book book = new Book(environment, "NoComponentsNoAddOns");
		book.setParty(hh);
		book.setComments("Internal Comments", "Internal");
		book.sendRequest();
		if(book.getResponse().toLowerCase().contains("unique constraint")){
			Sleeper.sleep(Randomness.randomNumberBetween(1, 5) * 1000);
			book.sendRequest();
		}
		TestReporter.logAPI(!book.getResponseStatusCode().contains("200"), book.getFaultString() ,book);
		TestReporter.assertTrue(Regex.match("[0-9]+", book.getTravelPlanId()), "The travel plan ID ["+book.getTravelPlanId()+"] is numeric as expected.");
		TestReporter.assertTrue(Regex.match("[0-9]+", book.getTravelPlanSegmentId()), "The reservation number ["+book.getTravelPlanSegmentId()+"] is numeric as expected.");
		TPS_ID=book.getTravelPlanSegmentId();
		
		LogItems logItems = new LogItems();
		logItems.addItem("AccommodationInventoryRequestComponentServiceIF", "createInventory", false);
		logItems.addItem("ChargeGroupIF", "createChargeGroupAndPostCharges", false);
		logItems.addItem("PartyIF", "createAndRetrieveParty", false);	
		logItems.addItem("TravelPlanServiceV3", "create", false);
		logItems.addItem("UpdateInventory", "updateInventory", false);
		
		if(environment.equalsIgnoreCase("Sleepy")){
			logItems.addItem("GuestServiceV1", "create", false); //Sleepy only
		}
			
		validateLogs(book, logItems);
	}

	@Test(groups = {"api", "regression", "dining", "eventDiningService"})
	public void testBookWithMultipleComments(){
		hh = new HouseHold(1);
		Book book = new Book(environment, "NoComponentsNoAddOns");
		book.setParty(hh);
		book.setComments("Internal Comments", "Internal");
		book.setComments("More Internal Comments", "External");
		book.sendRequest();
		if(book.getResponse().toLowerCase().contains("unique constraint")){
			Sleeper.sleep(Randomness.randomNumberBetween(1, 5) * 1000);
			book.sendRequest();
		}
		TestReporter.logAPI(!book.getResponseStatusCode().contains("200"), book.getFaultString() ,book);
		TestReporter.assertTrue(Regex.match("[0-9]+", book.getTravelPlanId()), "The travel plan ID ["+book.getTravelPlanId()+"] is numeric as expected.");
		TestReporter.assertTrue(Regex.match("[0-9]+", book.getTravelPlanSegmentId()), "The reservation number ["+book.getTravelPlanSegmentId()+"] is numeric as expected.");
		TPS_ID=book.getTravelPlanSegmentId();
		
		LogItems logItems = new LogItems();
		logItems.addItem("AccommodationInventoryRequestComponentServiceIF", "createInventory", false);
		logItems.addItem("ChargeGroupIF", "createChargeGroupAndPostCharges", false);
		logItems.addItem("PartyIF", "createAndRetrieveParty", false);	
		logItems.addItem("TravelPlanServiceV3", "create", false);
		logItems.addItem("UpdateInventory", "updateInventory", false);
		
		if(environment.equalsIgnoreCase("Sleepy")){
			logItems.addItem("GuestServiceV1", "create", false); //Sleepy only
		}
			
		validateLogs(book, logItems);
	}
	@Test(groups = {"api", "regression", "dining", "eventDiningService"})
	public void testBookWithMultipleGuestRequests(){
		hh = new HouseHold(1);
		Book book = new Book(environment, "NoComponentsNoAddOns");
		book.setParty(hh);
		book.setProfileDetailIdAndType(ServiceConstants.SeGuestRequests.BOOSTER_SEAT_ID, "GuestRequest");
		book.setProfileDetailIdAndType(ServiceConstants.SeGuestRequests.REQUEST_TWO_HIGH_CHAIRS_ID, "GuestRequest");
		book.setProfileDetailIdAndType(ServiceConstants.SeGuestRequests.HIGH_CHAIR_ID, "GuestRequest");
		book.sendRequest();
		if(book.getResponse().toLowerCase().contains("unique constraint")){
			Sleeper.sleep(Randomness.randomNumberBetween(1, 5) * 1000);
			book.sendRequest();
		}
		TestReporter.logAPI(!book.getResponseStatusCode().contains("200"), book.getFaultString() ,book);
		TestReporter.assertTrue(Regex.match("[0-9]+", book.getTravelPlanId()), "The travel plan ID ["+book.getTravelPlanId()+"] is numeric as expected.");
		TestReporter.assertTrue(Regex.match("[0-9]+", book.getTravelPlanSegmentId()), "The reservation number ["+book.getTravelPlanSegmentId()+"] is numeric as expected.");
		TPS_ID=book.getTravelPlanSegmentId();
		
		LogItems logItems = new LogItems();
		logItems.addItem("AccommodationInventoryRequestComponentServiceIF", "createInventory", false);
		logItems.addItem("ChargeGroupIF", "createChargeGroupAndPostCharges", false);
		logItems.addItem("PartyIF", "createAndRetrieveParty", false);	
		logItems.addItem("TravelPlanServiceV3", "create", false);
		logItems.addItem("UpdateInventory", "updateInventory", false);
		
		if(environment.equalsIgnoreCase("Sleepy")){
			logItems.addItem("GuestServiceV1", "create", false); //Sleepy only
		}
			
		validateLogs(book, logItems);
	}
	
	@Test(groups = {"api", "regression", "dining", "eventDiningService"})
	public void testBookWithSpecialNeeds(){
		hh = new HouseHold(1);
		Book book = new Book(environment, "NoComponentsNoAddOns");
		book.setParty(hh);
		book.setProfileDetailIdAndType(ServiceConstants.SeSpecialNeeds.HEARING_LOSS_ID, "SeSpecialNeed");
		book.sendRequest();
		if(book.getResponse().toLowerCase().contains("unique constraint")){
			Sleeper.sleep(Randomness.randomNumberBetween(1, 5) * 1000);
			book.sendRequest();
		}
		TestReporter.logAPI(!book.getResponseStatusCode().contains("200"), book.getFaultString() ,book);
		TestReporter.assertTrue(Regex.match("[0-9]+", book.getTravelPlanId()), "The travel plan ID ["+book.getTravelPlanId()+"] is numeric as expected.");
		TestReporter.assertTrue(Regex.match("[0-9]+", book.getTravelPlanSegmentId()), "The reservation number ["+book.getTravelPlanSegmentId()+"] is numeric as expected.");
		TPS_ID=book.getTravelPlanSegmentId();
		
		LogItems logItems = new LogItems();
		logItems.addItem("AccommodationInventoryRequestComponentServiceIF", "createInventory", false);
		logItems.addItem("ChargeGroupIF", "createChargeGroupAndPostCharges", false);
		logItems.addItem("PartyIF", "createAndRetrieveParty", false);	
		logItems.addItem("TravelPlanServiceV3", "create", false);
		logItems.addItem("UpdateInventory", "updateInventory", false);
		
		if(environment.equalsIgnoreCase("Sleepy")){
			logItems.addItem("GuestServiceV1", "create", false); //Sleepy only
		}
			
		validateLogs(book, logItems);
	}
	

	@Test(groups = {"api", "regression", "dining", "eventDiningService"})
	public void testBookWithMultipleSpecialNeeds(){
		hh = new HouseHold(1);
		Book book = new Book(environment, "NoComponentsNoAddOns");
		book.setParty(hh);
		book.setProfileDetailIdAndType(ServiceConstants.SeSpecialNeeds.HEARING_LOSS_ID, "SeSpecialNeed");
		book.setProfileDetailIdAndType(ServiceConstants.SeSpecialNeeds.LIMITED_MOBILITY_ID, "SeSpecialNeed");
		book.setProfileDetailIdAndType(ServiceConstants.SeSpecialNeeds.OXYGEN_TANK_USE_ID, "SeSpecialNeed");
		book.sendRequest();
		if(book.getResponse().toLowerCase().contains("unique constraint")){
			Sleeper.sleep(Randomness.randomNumberBetween(1, 5) * 1000);
			book.sendRequest();
		}
		TestReporter.logAPI(!book.getResponseStatusCode().contains("200"), book.getFaultString() ,book);
		TestReporter.assertTrue(Regex.match("[0-9]+", book.getTravelPlanId()), "The travel plan ID ["+book.getTravelPlanId()+"] is numeric as expected.");
		TestReporter.assertTrue(Regex.match("[0-9]+", book.getTravelPlanSegmentId()), "The reservation number ["+book.getTravelPlanSegmentId()+"] is numeric as expected.");
		TPS_ID=book.getTravelPlanSegmentId();
		
		LogItems logItems = new LogItems();
		logItems.addItem("AccommodationInventoryRequestComponentServiceIF", "createInventory", false);
		logItems.addItem("ChargeGroupIF", "createChargeGroupAndPostCharges", false);
		logItems.addItem("PartyIF", "createAndRetrieveParty", false);	
		logItems.addItem("TravelPlanServiceV3", "create", false);
		logItems.addItem("UpdateInventory", "updateInventory", false);
		
		if(environment.equalsIgnoreCase("Sleepy")){
			logItems.addItem("GuestServiceV1", "create", false); //Sleepy only
		}
			
		validateLogs(book, logItems);
	}
	

	@Test(groups = {"api", "regression", "dining", "eventDiningService"})
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
		book.sendRequest();
		if(book.getResponse().toLowerCase().contains("unique constraint")){
			Sleeper.sleep(Randomness.randomNumberBetween(1, 5) * 1000);
			book.sendRequest();
		}
		TestReporter.logAPI(!book.getResponseStatusCode().contains("200"), book.getFaultString() ,book);
		TestReporter.assertTrue(Regex.match("[0-9]+", book.getTravelPlanId()), "The travel plan ID ["+book.getTravelPlanId()+"] is numeric as expected.");
		TestReporter.assertTrue(Regex.match("[0-9]+", book.getTravelPlanSegmentId()), "The reservation number ["+book.getTravelPlanSegmentId()+"] is numeric as expected.");
		TPS_ID=book.getTravelPlanSegmentId();
		
		LogItems logItems = new LogItems();
		logItems.addItem("AccommodationInventoryRequestComponentServiceIF", "createInventory", false);
		logItems.addItem("ChargeGroupIF", "createChargeGroupAndPostCharges", false);
		logItems.addItem("PartyIF", "createAndRetrieveParty", false);	
		logItems.addItem("TravelPlanServiceV3", "create", false);
		logItems.addItem("UpdateInventory", "updateInventory", false);
		
		if(environment.equalsIgnoreCase("Sleepy")){
			logItems.addItem("GuestServiceV1", "create", false); //Sleepy only
		}
			
		validateLogs(book, logItems);
	}
	@Test(groups = {"api", "regression", "dining", "eventDiningService"})
	public void testBook_DLR(){
		hh = new HouseHold(1);
		Book book = new Book(environment, "DLRTableServiceOneChild");
		book.sendRequest();
		
		TestReporter.logAPI(!book.getResponseStatusCode().contains("200"), book.getFaultString() ,book);
		TestReporter.assertTrue(Regex.match("[0-9]+", book.getTravelPlanId()), "The travel plan ID ["+book.getTravelPlanId()+"] is numeric as expected.");
		TestReporter.assertTrue(Regex.match("[0-9]+", book.getTravelPlanSegmentId()), "The reservation number ["+book.getTravelPlanSegmentId()+"] is numeric as expected.");
		TPS_ID=book.getTravelPlanSegmentId();
		
		LogItems logItems = new LogItems();
		logItems.addItem("AccommodationInventoryRequestComponentServiceIF", "createInventory", false);
		logItems.addItem("ChargeGroupIF", "createChargeGroupAndPostCharges", false);
		logItems.addItem("PartyIF", "createAndRetrieveParty", false);	
		logItems.addItem("TravelPlanServiceV3", "create", false);
		logItems.addItem("UpdateInventory", "updateInventory", false);
		
		if(environment.equalsIgnoreCase("Sleepy")){
			logItems.addItem("GuestServiceV1", "create", false); //Sleepy only
		}
			
		validateLogs(book, logItems);
	}


	@Test(groups = {"api", "regression", "dining", "eventDiningService"})
	public void testBookWithVipLevel2(){
		hh = new HouseHold(1);
		Book book = new Book(environment, "NoComponentsNoAddOns");
		book.setParty(hh);
		book.setVipLevel("2");
		book.sendRequest();
		if(book.getResponse().toLowerCase().contains("unique constraint")){
			Sleeper.sleep(Randomness.randomNumberBetween(1, 5) * 1000);
			book.sendRequest();
		}
		TestReporter.logAPI(!book.getResponseStatusCode().contains("200"), book.getFaultString() ,book);
		TestReporter.assertTrue(Regex.match("[0-9]+", book.getTravelPlanId()), "The travel plan ID ["+book.getTravelPlanId()+"] is numeric as expected.");
		TestReporter.assertTrue(Regex.match("[0-9]+", book.getTravelPlanSegmentId()), "The reservation number ["+book.getTravelPlanSegmentId()+"] is numeric as expected.");
		TPS_ID=book.getTravelPlanSegmentId();
		
		LogItems logItems = new LogItems();
		logItems.addItem("AccommodationInventoryRequestComponentServiceIF", "createInventory", false);
		logItems.addItem("ChargeGroupIF", "createChargeGroupAndPostCharges", false);
		logItems.addItem("PartyIF", "createAndRetrieveParty", false);	
		logItems.addItem("TravelPlanServiceV3", "create", false);
		logItems.addItem("UpdateInventory", "updateInventory", false);
		
		if(environment.equalsIgnoreCase("Sleepy")){
			logItems.addItem("GuestServiceV1", "create", false); //Sleepy only
		}
			
		validateLogs(book, logItems);
	}

	@Test(groups = {"api", "regression", "dining", "eventDiningService"})
	public void testBookWithInventoryOverride(){
		hh = new HouseHold(1);
		Book book = new Book(environment, "NoComponentsNoAddOns");
		book.setParty(hh);
		book.setInventoryOverrideReasonId("800060");
		book.sendRequest();
		if(book.getResponse().toLowerCase().contains("unique constraint")){
			Sleeper.sleep(Randomness.randomNumberBetween(1, 5) * 1000);
			book.sendRequest();
		}
		TestReporter.logAPI(!book.getResponseStatusCode().contains("200"), book.getFaultString() ,book);
		TestReporter.assertTrue(Regex.match("[0-9]+", book.getTravelPlanId()), "The travel plan ID ["+book.getTravelPlanId()+"] is numeric as expected.");
		TestReporter.assertTrue(Regex.match("[0-9]+", book.getTravelPlanSegmentId()), "The reservation number ["+book.getTravelPlanSegmentId()+"] is numeric as expected.");
		TPS_ID=book.getTravelPlanSegmentId();
		
		LogItems logItems = new LogItems();
		logItems.addItem("AccommodationInventoryRequestComponentServiceIF", "createInventory", false);
		logItems.addItem("ChargeGroupIF", "createChargeGroupAndPostCharges", false);
		logItems.addItem("PartyIF", "createAndRetrieveParty", false);	
		logItems.addItem("TravelPlanServiceV3", "create", false);
		logItems.addItem("UpdateInventory", "updateInventory", false);
		
		if(environment.equalsIgnoreCase("Sleepy")){
			logItems.addItem("GuestServiceV1", "create", false); //Sleepy only
		}
			
		validateLogs(book, logItems);
	}

	@Test(groups = {"api", "regression", "dining", "eventDiningService"})
	public void testBookWithMembership(){
		hh = new HouseHold(1);
		Book book = new Book(environment, "GuestWithMembership");
		book.setParty(hh);
		book.sendRequest();
		if(book.getResponse().toLowerCase().contains("unique constraint")){
			Sleeper.sleep(Randomness.randomNumberBetween(1, 5) * 1000);
			book.sendRequest();
		}
		TestReporter.logAPI(!book.getResponseStatusCode().contains("200"), book.getFaultString() ,book);
		TestReporter.assertTrue(Regex.match("[0-9]+", book.getTravelPlanId()), "The travel plan ID ["+book.getTravelPlanId()+"] is numeric as expected.");
		TestReporter.assertTrue(Regex.match("[0-9]+", book.getTravelPlanSegmentId()), "The reservation number ["+book.getTravelPlanSegmentId()+"] is numeric as expected.");
		TPS_ID=book.getTravelPlanSegmentId();
		
		LogItems logItems = new LogItems();
		logItems.addItem("AccommodationInventoryRequestComponentServiceIF", "createInventory", false);
		logItems.addItem("ChargeGroupIF", "createChargeGroupAndPostCharges", false);
		logItems.addItem("PartyIF", "createAndRetrieveParty", false);	
		logItems.addItem("TravelPlanServiceV3", "create", false);
		logItems.addItem("UpdateInventory", "updateInventory", false);
		
		if(environment.equalsIgnoreCase("Sleepy")){
			logItems.addItem("GuestServiceV1", "create", false); //Sleepy only
		}
			
		validateLogs(book, logItems);
	}
	@Test(groups = {"api", "regression", "dining", "eventDiningService"})
	public void testBookWithDefaultTravelAgency(){
		hh = new HouseHold(1);
		Book book = new Book(environment, "NoComponentsNoAddOns");
		book.addTravelAgency("99999998");
		book.setParty(hh);
		book.sendRequest();
		if(book.getResponse().toLowerCase().contains("unique constraint")){
			Sleeper.sleep(Randomness.randomNumberBetween(1, 5) * 1000);
			book.sendRequest();
		}
		TestReporter.logAPI(!book.getResponseStatusCode().contains("200"), book.getFaultString() ,book);
		TestReporter.assertTrue(Regex.match("[0-9]+", book.getTravelPlanId()), "The travel plan ID ["+book.getTravelPlanId()+"] is numeric as expected.");
		TestReporter.assertTrue(Regex.match("[0-9]+", book.getTravelPlanSegmentId()), "The reservation number ["+book.getTravelPlanSegmentId()+"] is numeric as expected.");
		TPS_ID=book.getTravelPlanSegmentId();
		
		LogItems logItems = new LogItems();
		logItems.addItem("AccommodationInventoryRequestComponentServiceIF", "createInventory", false);
		logItems.addItem("ChargeGroupIF", "createChargeGroupAndPostCharges", false);
		logItems.addItem("PartyIF", "createAndRetrieveParty", false);	
		logItems.addItem("TravelPlanServiceV3", "create", false);
		logItems.addItem("UpdateInventory", "updateInventory", false);
		
		if(environment.equalsIgnoreCase("Sleepy")){
			logItems.addItem("GuestServiceV1", "create", false); //Sleepy only
		}
			
		validateLogs(book, logItems);
	}
	
	@Test(groups = {"api", "regression", "dining", "eventDiningService"})
	public void testBookWithRoomReservation(){
		hh = new HouseHold(1);
		Reservation res = new GenerateReservation().bookResortReservation().CONTEMPORARY(environment.toUpperCase().replace("_CM", ""));
		res.setGuestInfo(hh.primaryGuest());
		res.quickBook();
		
		Book book = new Book(environment, "NoComponentsNoAddOns");
		book.setTravelPlanId(res.getTravelPlanId());
		book.setParty(hh);
		book.sendRequest();
		if(book.getResponse().toLowerCase().contains("unique constraint")){
			Sleeper.sleep(Randomness.randomNumberBetween(1, 5) * 1000);
			book.sendRequest();
		}
		TestReporter.logAPI(!book.getResponseStatusCode().contains("200"), book.getFaultString() ,book);
		TestReporter.assertTrue(Regex.match("[0-9]+", book.getTravelPlanId()), "The travel plan ID ["+book.getTravelPlanId()+"] is numeric as expected.");
		TestReporter.assertTrue(Regex.match("[0-9]+", book.getTravelPlanSegmentId()), "The reservation number ["+book.getTravelPlanSegmentId()+"] is numeric as expected.");
		TPS_ID=book.getTravelPlanSegmentId();
		
		LogItems logItems = new LogItems();
		logItems.addItem("AccommodationInventoryRequestComponentServiceIF", "createInventory", false);
		logItems.addItem("ChargeGroupIF", "createChargeGroupAndPostCharges", false);
		logItems.addItem("PartyIF", "createAndRetrieveParty", false);	
		logItems.addItem("TravelPlanServiceV3", "create", false);
		logItems.addItem("UpdateInventory", "updateInventory", false);
		
		if(environment.equalsIgnoreCase("Sleepy")){
			logItems.addItem("GuestServiceV1", "create", false); //Sleepy only
		}
			
		validateLogs(book, logItems);
	}
	
	@Test(groups = {"api", "regression", "dining", "eventDiningService"})
	public void testBookWithComponents(){	
		ScheduledEventReservation res = new EventDiningReservation(environment, hh);
		res.setFacilityName("Biergarten Restaurant");
		res.setProductName("Biergarten Lunch");
		res.book("NoComponentsNoAddOns");
		TPS_ID=res.getConfirmationNumber();
	}

	
	@Test(groups = {"api", "regression", "dining", "eventDiningService"})
	public void testBookWithSettlement(){	
		ScheduledEventReservation res = new EventDiningReservation(environment, hh);
		res.setFacilityName("Biergarten Restaurant");
		res.setProductName("Biergarten Lunch");
		res.book("NoComponentsNoAddOns");
		TPS_ID=res.getConfirmationNumber();
		res.folio(environment.replace("_CM", "")).settlement().createSettlementMethod(FolioInterfaceSettlement.defaultSettlementScenario);
	}
	
	@Test(groups = {"api", "regression", "dining", "eventDiningService"})
	public void testBookWithPayment(){	
		ScheduledEventReservation res = new EventDiningReservation(environment, hh);
		res.setFacilityName("Pioneer Hall");
		res.setProductName("Hoop-Dee-Doo-Cat 2-1st Show");
		res.book("NoComponentsNoAddOns");
		TPS_ID=res.getConfirmationNumber();
		res.folio(environment.replace("_CM", "")).payment().makeSplitCardPayment("Split Payment Visa And MC");
	
	}
	@Test(groups = {"api", "regression", "dining", "eventDiningService"})

	public void testBookWith2Adults(){
		hh = new HouseHold("2 Adults");
		Book book = new Book(environment, "NoComponentsNoAddOns");
		book.setParty(hh);
		book.sendRequest();
		
		TestReporter.logAPI(!book.getResponseStatusCode().contains("200"), book.getFaultString() ,book);
		TestReporter.assertTrue(Regex.match("[0-9]+", book.getTravelPlanId()), "The travel plan ID ["+book.getTravelPlanId()+"] is numeric as expected.");
		TestReporter.assertTrue(Regex.match("[0-9]+", book.getTravelPlanSegmentId()), "The reservation number ["+book.getTravelPlanSegmentId()+"] is numeric as expected.");
		TPS_ID=book.getTravelPlanSegmentId();
		
		LogItems logItems = new LogItems();
		logItems.addItem("AccommodationInventoryRequestComponentServiceIF", "createInventory", false);
		logItems.addItem("ChargeGroupIF", "createChargeGroupAndPostCharges", false);
		logItems.addItem("PartyIF", "createAndRetrieveParty", false);	
		logItems.addItem("TravelPlanServiceV3", "create", false);
		logItems.addItem("UpdateInventory", "updateInventory", false);
		
		if(environment.equalsIgnoreCase("Sleepy")){
			logItems.addItem("GuestServiceV1", "create", false); //Sleepy only
		}
			
		validateLogs(book, logItems);
	}

	@Test(groups = {"api", "regression", "dining", "eventDiningService"})
	public void testBookWith4Adults(){
		hh = new HouseHold("4 Adults");
		Book book = new Book(environment, "NoComponentsNoAddOns");
		book.setParty(hh);
		book.sendRequest();
		if(book.getResponse().toLowerCase().contains("unique constraint")){
			Sleeper.sleep(Randomness.randomNumberBetween(1, 5) * 1000);
			book.sendRequest();
		}
		TestReporter.logAPI(!book.getResponseStatusCode().contains("200"), book.getFaultString() ,book);
		TestReporter.assertTrue(Regex.match("[0-9]+", book.getTravelPlanId()), "The travel plan ID ["+book.getTravelPlanId()+"] is numeric as expected.");
		TestReporter.assertTrue(Regex.match("[0-9]+", book.getTravelPlanSegmentId()), "The reservation number ["+book.getTravelPlanSegmentId()+"] is numeric as expected.");
		TPS_ID=book.getTravelPlanSegmentId();
		
		LogItems logItems = new LogItems();
		logItems.addItem("AccommodationInventoryRequestComponentServiceIF", "createInventory", false);
		logItems.addItem("ChargeGroupIF", "createChargeGroupAndPostCharges", false);
		logItems.addItem("PartyIF", "createAndRetrieveParty", false);	
		logItems.addItem("TravelPlanServiceV3", "create", false);
		logItems.addItem("UpdateInventory", "updateInventory", false);
		
		if(environment.equalsIgnoreCase("Sleepy")){
			logItems.addItem("GuestServiceV1", "create", false); //Sleepy only
		}
			
		validateLogs(book, logItems);
	}

	@Test(groups = {"api", "regression", "dining", "eventDiningService"})
	public void testBookWith2Adults2Child(){
		hh = new HouseHold("2 Adults 2 Child");
		Book book = new Book(environment, "NoComponentsNoAddOns");
		book.setParty(hh);
		book.sendRequest();
		if(book.getResponse().toLowerCase().contains("unique constraint")){
			Sleeper.sleep(Randomness.randomNumberBetween(1, 5) * 1000);
			book.sendRequest();
		}
		TestReporter.logAPI(!book.getResponseStatusCode().contains("200"), book.getFaultString() ,book);
		TestReporter.assertTrue(Regex.match("[0-9]+", book.getTravelPlanId()), "The travel plan ID ["+book.getTravelPlanId()+"] is numeric as expected.");
		TestReporter.assertTrue(Regex.match("[0-9]+", book.getTravelPlanSegmentId()), "The reservation number ["+book.getTravelPlanSegmentId()+"] is numeric as expected.");
		TPS_ID=book.getTravelPlanSegmentId();
		
		LogItems logItems = new LogItems();
		logItems.addItem("AccommodationInventoryRequestComponentServiceIF", "createInventory", false);
		logItems.addItem("ChargeGroupIF", "createChargeGroupAndPostCharges", false);
		logItems.addItem("PartyIF", "createAndRetrieveParty", false);	
		logItems.addItem("TravelPlanServiceV3", "create", false);
		logItems.addItem("UpdateInventory", "updateInventory", false);
		
		if(environment.equalsIgnoreCase("Sleepy")){
			logItems.addItem("GuestServiceV1", "create", false); //Sleepy only
		}
			
		validateLogs(book, logItems);
	}

	@Test(groups = {"api", "regression", "dining", "eventDiningService"})
	public void testBookWith4Adults2Child2Infant(){
		hh = new HouseHold("4 Adults 2 Child 2 Infant");
		Book book = new Book(environment, "NoComponentsNoAddOns");
		book.setParty(hh);
		book.sendRequest();
		if(book.getResponse().toLowerCase().contains("unique constraint")){
			Sleeper.sleep(Randomness.randomNumberBetween(1, 5) * 1000);
			book.sendRequest();
		}
		TestReporter.logAPI(!book.getResponseStatusCode().contains("200"), book.getFaultString() ,book);
		TestReporter.assertTrue(Regex.match("[0-9]+", book.getTravelPlanId()), "The travel plan ID ["+book.getTravelPlanId()+"] is numeric as expected.");
		TestReporter.assertTrue(Regex.match("[0-9]+", book.getTravelPlanSegmentId()), "The reservation number ["+book.getTravelPlanSegmentId()+"] is numeric as expected.");
		TPS_ID=book.getTravelPlanSegmentId();
		
		LogItems logItems = new LogItems();
		logItems.addItem("AccommodationInventoryRequestComponentServiceIF", "createInventory", false);
		logItems.addItem("ChargeGroupIF", "createChargeGroupAndPostCharges", false);
		logItems.addItem("PartyIF", "createAndRetrieveParty", false);	
		logItems.addItem("TravelPlanServiceV3", "create", false);
		logItems.addItem("UpdateInventory", "updateInventory", false);
		
		if(environment.equalsIgnoreCase("Sleepy")){
			logItems.addItem("GuestServiceV1", "create", false); //Sleepy only
		}
			
		validateLogs(book, logItems);
	}
	

	@Test(groups = {"api", "regression", "dining", "eventDiningService"})
	public void testBookWith12InParty(){
		hh = new HouseHold(12);
		Book book = new Book(environment, "NoComponentsNoAddOns");
		book.setParty(hh);
		book.sendRequest();
		if(book.getResponse().toLowerCase().contains("unique constraint")){
			Sleeper.sleep(Randomness.randomNumberBetween(1, 5) * 1000);
			book.sendRequest();
		}
		TestReporter.logAPI(!book.getResponseStatusCode().contains("200"), book.getFaultString() ,book);
		TestReporter.assertTrue(Regex.match("[0-9]+", book.getTravelPlanId()), "The travel plan ID ["+book.getTravelPlanId()+"] is numeric as expected.");
		TestReporter.assertTrue(Regex.match("[0-9]+", book.getTravelPlanSegmentId()), "The reservation number ["+book.getTravelPlanSegmentId()+"] is numeric as expected.");
		TPS_ID=book.getTravelPlanSegmentId();
		
		LogItems logItems = new LogItems();
		logItems.addItem("AccommodationInventoryRequestComponentServiceIF", "createInventory", false);
		logItems.addItem("ChargeGroupIF", "createChargeGroupAndPostCharges", false);
		logItems.addItem("PartyIF", "createAndRetrieveParty", false);	
		logItems.addItem("TravelPlanServiceV3", "create", false);
		logItems.addItem("UpdateInventory", "updateInventory", false);
		
		if(environment.equalsIgnoreCase("Sleepy")){
			logItems.addItem("GuestServiceV1", "create", false); //Sleepy only
		}
			
		validateLogs(book, logItems);
	}
	@Test(groups = {"api", "regression", "dining", "eventDiningService"})
	public void testBookWithAddOn	(){
		hh = new HouseHold(1);
		Book book = new Book(environment, "TableServiceAddOn");
		book.setParty(hh);
		book.sendRequest();
		TestReporter.logAPI(!book.getResponseStatusCode().contains("200"), book.getFaultString() ,book);
		TestReporter.assertTrue(Regex.match("[0-9]+", book.getTravelPlanId()), "The travel plan ID ["+book.getTravelPlanId()+"] is numeric as expected.");
		TestReporter.assertTrue(Regex.match("[0-9]+", book.getTravelPlanSegmentId()), "The reservation number ["+book.getTravelPlanSegmentId()+"] is numeric as expected.");
		TPS_ID=book.getTravelPlanSegmentId();
		
		LogItems logItems = new LogItems();
		logItems.addItem("AccommodationInventoryRequestComponentServiceIF", "createInventory", false);
		logItems.addItem("ChargeGroupIF", "createChargeGroupAndPostCharges", false);
		logItems.addItem("PartyIF", "createAndRetrieveParty", false);	
		logItems.addItem("TravelPlanServiceV3", "create", false);
		logItems.addItem("UpdateInventory", "updateInventory", false);
		
		if(environment.equalsIgnoreCase("Sleepy")){
			logItems.addItem("GuestServiceV1", "create", false); //Sleepy only
		}
			
		validateLogs(book, logItems);
	}
	@Test(groups = {"api", "regression", "dining", "eventDiningService", "it4", "s138180" })
	public void testAddAllergy(){
		hh = new HouseHold("1 Adult");
		Book book = new Book(environment, "NoComponentsNoAddOns");
		book.setParty(hh);
		book.setAllergies("Egg","1");
		book.sendRequest();
		if(book.getResponse().toLowerCase().contains("unique constraint")){
			Sleeper.sleep(Randomness.randomNumberBetween(1, 5) * 1000);
			book.sendRequest();
		}
		TestReporter.logAPI(!book.getResponseStatusCode().contains("200"), book.getFaultString() ,book);
		TestReporter.assertTrue(Regex.match("[0-9]+", book.getTravelPlanId()), "The travel plan ID ["+book.getTravelPlanId()+"] is numeric as expected.");
		TestReporter.assertTrue(Regex.match("[0-9]+", book.getTravelPlanSegmentId()), "The reservation number ["+book.getTravelPlanSegmentId()+"] is numeric as expected.");
		TPS_ID=book.getTravelPlanSegmentId();
		
		LogItems logItems = new LogItems();
		logItems.addItem("AccommodationInventoryRequestComponentServiceIF", "createInventory", false);
		logItems.addItem("ChargeGroupIF", "createChargeGroupAndPostCharges", false);
		logItems.addItem("PartyIF", "createAndRetrieveParty", false);	
		logItems.addItem("TravelPlanServiceV3", "create", false);
		logItems.addItem("UpdateInventory", "updateInventory", false);
		
		if(environment.equalsIgnoreCase("Sleepy")){
			logItems.addItem("GuestServiceV1", "create", false); //Sleepy only
		}
			
		validateLogs(book, logItems);
	}
	

	@Test(groups = {"api", "regression", "dining", "eventDiningService", "it4", "s138180" })
	public void testAddTwoAllergies(){
		hh = new HouseHold("1 Adult");
		Book book = new Book(environment, "NoComponentsNoAddOns");
		book.setParty(hh);
		book.setAllergies("Egg","1");
		book.setAllergies("Corn","2");		
		book.sendRequest();
		if(book.getResponse().toLowerCase().contains("unique constraint")){
			Sleeper.sleep(Randomness.randomNumberBetween(1, 5) * 1000);
			book.sendRequest();
		}
		
		TestReporter.logAPI(!book.getResponseStatusCode().contains("200"), book.getFaultString() ,book);
		TestReporter.assertTrue(Regex.match("[0-9]+", book.getTravelPlanId()), "The travel plan ID ["+book.getTravelPlanId()+"] is numeric as expected.");
		TestReporter.assertTrue(Regex.match("[0-9]+", book.getTravelPlanSegmentId()), "The reservation number ["+book.getTravelPlanSegmentId()+"] is numeric as expected.");
		TPS_ID=book.getTravelPlanSegmentId();
		
		LogItems logItems = new LogItems();
		logItems.addItem("AccommodationInventoryRequestComponentServiceIF", "createInventory", false);
		logItems.addItem("ChargeGroupIF", "createChargeGroupAndPostCharges", false);
		logItems.addItem("PartyIF", "createAndRetrieveParty", false);	
		logItems.addItem("TravelPlanServiceV3", "create", false);
		logItems.addItem("UpdateInventory", "updateInventory", false);
		
		if(environment.equalsIgnoreCase("Sleepy")){
			logItems.addItem("GuestServiceV1", "create", false); //Sleepy only
		}
			
		validateLogs(book, logItems);
	}
	

	@Test(groups = {"api", "regression", "dining", "eventDiningService", "it4", "s138180" })
	public void testAddAllAllergies(){
		hh = new HouseHold(1);
		Book book = new Book(environment, "NoComponentsNoAddOns");
		book.setParty(hh);
		book.addDetailsByFacilityNameAndProductName("Pioneer Hall", "Hoop-Dee-Doo-Cat 1-1st Show");
		RetrieveAllergies retrieveAllergies = new RetrieveAllergies(environment);
		retrieveAllergies.sendRequest();
		TestReporter.logAPI(!retrieveAllergies.getResponseStatusCode().contains("200"), "An error occurred retrieving allergies: " + retrieveAllergies.getFaultString() ,retrieveAllergies);

		int index = 1;
		for(String allergy : retrieveAllergies.getAllergies().values()){
			book.setAllergies(allergy,String.valueOf(index));
			index++;
		}

		book.sendRequest();
		TestReporter.logAPI(!book.getResponseStatusCode().contains("200"), "An error occurred bookingan event dining reservation: " + book.getFaultString() ,book);
		TestReporter.assertTrue(Regex.match("[0-9]+", book.getTravelPlanId()), "The travel plan ID ["+book.getTravelPlanId()+"] is numeric as expected.");
		TestReporter.assertTrue(Regex.match("[0-9]+", book.getTravelPlanSegmentId()), "The reservation number ["+book.getTravelPlanSegmentId()+"] is numeric as expected.");
		TPS_ID=book.getTravelPlanSegmentId();
		
		LogItems logItems = new LogItems();
//		logItems.addItem("AccommodationInventoryRequestComponentServiceIF", "createInventory", false);
		logItems.addItem("ChargeGroupIF", "createChargeGroupAndPostCharges", false);
		logItems.addItem("PartyIF", "createAndRetrieveParty", false);	
		logItems.addItem("TravelPlanServiceV3", "create", false);
		logItems.addItem("UpdateInventory", "updateInventory", false);
		
		if(environment.equalsIgnoreCase("Sleepy")){
			logItems.addItem("GuestServiceV1", "create", false); //Sleepy only
		}
			
		validateLogs(book, logItems);
	}

	@Test
	public void testTrueDiningReservation(){
		Book book = new Book(environment, ScheduledEventReservation.NOCOMPONENTSNOADDONS);
		book.setParty(new HouseHold(1));
		book.setReservableResourceId("BA054CBB-D573-C672-BE95-173042178DBE");
		book.addDetailsByFacilityNameAndProductName("The Hollywood Brown Derby", "Brown Derby Lunch F! 1st Show");
		book.addSpecialEventByProductName("Fantasmic! Viewing 1st Show", "4cafc352-62d4-4e88-98f3-16474db25aa7");
		book.sendRequest();
		
		TestReporter.logAPI(!book.getResponseStatusCode().contains("200"), "An error occurred bookingan event dining reservation: " + book.getFaultString() ,book);
		TestReporter.assertTrue(Regex.match("[0-9]+", book.getTravelPlanId()), "The travel plan ID ["+book.getTravelPlanId()+"] is numeric as expected.");
		TestReporter.assertTrue(Regex.match("[0-9]+", book.getTravelPlanSegmentId()), "The reservation number ["+book.getTravelPlanSegmentId()+"] is numeric as expected.");
		TPS_ID=book.getTravelPlanSegmentId();
	}
}