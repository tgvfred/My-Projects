package com.disney.composite.api.eventDiningService.book;

import org.testng.annotations.Test;

import com.disney.api.soapServices.core.BaseSoapCommands;
import com.disney.api.soapServices.eventDiningService.operations.Book;
import com.disney.composite.BaseTest;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.database.LogItems;
import com.disney.utils.dataFactory.guestFactory.HouseHold;

public class TestBook_Negative_InvalidData extends BaseTest{
	// Defining global variables
	protected String TPS_ID = null;
	
	@Test(groups = {"api", "regression", "dining", "eventDiningService", "negative"})
	public void invalidFacilityId(){
		Book book = new Book(environment, "NoComponentsNoAddOns");
		book.setParty(hh);
		book.setFacilityId("1010");
		book.sendRequest();
		TestReporter.logAPI(!book.getFaultString().contains("FACILITY SERVICE UNAVAILABLE OR RETURED INVALID FACILITY!! : INVALID FACILITY ID"), book.getFaultString() ,book);

		LogItems logValidItems = new LogItems();
		logValidItems.addItem("EventDiningServiceIF", "book", true);
		validateLogs(book, logValidItems);
		
		LogItems logInvalidItems = new LogItems();
		logInvalidItems.addItem("AccommodationInventoryRequestComponentServiceIF", "createInventory", false);
		logInvalidItems.addItem("ChargeGroupIF", "createChargeGroupAndPostCharges", false);
		logInvalidItems.addItem("PartyIF", "createAndRetrieveParty", false);	
		logInvalidItems.addItem("TravelPlanServiceV3", "create", false);
		logInvalidItems.addItem("UpdateInventory", "updateInventory", false);
		validateNotInLogs(book, logInvalidItems);
	}

	@Test(groups = {"api", "regression", "dining", "eventDiningService", "negative"})
	public void invalidCommunicationChannel(){
		Book book = new Book(environment, "NoComponentsNoAddOns");
		book.setParty(hh);
		book.setCommunicationChannel("Invalid Center");
		book.sendRequest();
		TestReporter.logAPI(!book.getFaultString().contains("communication Channel is required : null"), book.getFaultString() ,book);

		LogItems logValidItems = new LogItems();
		logValidItems.addItem("EventDiningServiceIF", "book", true);
		validateLogs(book, logValidItems);
		
		LogItems logInvalidItems = new LogItems();
		logInvalidItems.addItem("AccommodationInventoryRequestComponentServiceIF", "createInventory", false);
		logInvalidItems.addItem("ChargeGroupIF", "createChargeGroupAndPostCharges", false);
		logInvalidItems.addItem("PartyIF", "createAndRetrieveParty", false);	
		logInvalidItems.addItem("TravelPlanServiceV3", "create", false);
		logInvalidItems.addItem("UpdateInventory", "updateInventory", false);
		validateNotInLogs(book, logInvalidItems);
	}
	
	@Test(groups = {"api", "regression", "dining", "eventDiningService", "negative"})
	public void invalidTravelPlanIdFormat(){
		Book book = new Book(environment, "NoComponentsNoAddOns");
		book.setParty(hh);
		book.setTravelPlanId("Invalid Id");
		book.sendRequest();
		TestReporter.logAPI(!book.getFaultString().contains("Unmarshalling Error: For input string: \"Invalid Id\""), book.getFaultString() ,book);
	}

	@Test(groups = {"api", "regression", "dining", "eventDiningService", "negative"})
	public void invalidTravelPlanId(){
		Book book = new Book(environment, "NoComponentsNoAddOns");
		book.setParty(hh);
		book.setTravelPlanId("1111111");
		book.sendRequest();
		TestReporter.logAPI(!book.getFaultString().contains("TRAVEL_PLAN_NOT_FOUND : TRAVEL PLAN NOT FOUND"), book.getFaultString() ,book);

		LogItems logValidItems = new LogItems();
		logValidItems.addItem("EventDiningServiceIF", "book", true);
		logValidItems.addItem("PartyIF", "createAndRetrieveParty", false);
		validateLogs(book, logValidItems);
		
		LogItems logInvalidItems = new LogItems();
		logInvalidItems.addItem("AccommodationInventoryRequestComponentServiceIF", "createInventory", false);
		logInvalidItems.addItem("ChargeGroupIF", "createChargeGroupAndPostCharges", false);	
		logInvalidItems.addItem("TravelPlanServiceV3", "create", false);
		logInvalidItems.addItem("UpdateInventory", "updateInventory", false);
		validateNotInLogs(book, logInvalidItems);
	}
	
	@Test(groups = {"api", "regression", "dining", "eventDiningService", "negative"})
	public void invalidPrimaryGuestTitle(){
		Book book = new Book(environment, "NoComponentsNoAddOns");
		HouseHold party = new HouseHold(1);
		party.primaryGuest().setTitle("Mre.");
		book.setParty(party);
		book.sendRequest();
		TestReporter.logAPI(!book.getFaultString().contains("Salutation is invalid : Salutation Mre. is invalid"), book.getFaultString() ,book);

		LogItems logValidItems = new LogItems();
		logValidItems.addItem("EventDiningServiceIF", "book", true);
		logValidItems.addItem("PartyIF", "createAndRetrieveParty", true);
		validateLogs(book, logValidItems);
		
		LogItems logInvalidItems = new LogItems();
		logInvalidItems.addItem("AccommodationInventoryRequestComponentServiceIF", "createInventory", false);
		logInvalidItems.addItem("ChargeGroupIF", "createChargeGroupAndPostCharges", false);	
		logInvalidItems.addItem("TravelPlanServiceV3", "create", false);
		logInvalidItems.addItem("UpdateInventory", "updateInventory", false);
		validateNotInLogs(book, logInvalidItems);
	}
	
	@Test(groups = {"api", "regression", "dining", "eventDiningService", "negative"})
	public void invalidPrimaryGuestCountry(){
		Book book = new Book(environment, "NoComponentsNoAddOns");
		HouseHold party = new HouseHold(1);
		party.primaryGuest().primaryAddress().setCountry("Randland");
		book.setParty(party);
		book.sendRequest();
		TestReporter.logAPI(!book.getFaultString().contains("Create Party Error : Please enter valid country code"), book.getFaultString() ,book);

		LogItems logValidItems = new LogItems();
		logValidItems.addItem("EventDiningServiceIF", "book", true);
		logValidItems.addItem("PartyIF", "createAndRetrieveParty", true);
		validateLogs(book, logValidItems);
		
		LogItems logInvalidItems = new LogItems();
		logInvalidItems.addItem("AccommodationInventoryRequestComponentServiceIF", "createInventory", false);
		logInvalidItems.addItem("ChargeGroupIF", "createChargeGroupAndPostCharges", false);	
		logInvalidItems.addItem("TravelPlanServiceV3", "create", false);
		logInvalidItems.addItem("UpdateInventory", "updateInventory", false);
		validateNotInLogs(book, logInvalidItems);
	}
	
	@Test(groups = {"api", "regression", "dining", "eventDiningService", "negative"})
	public void invalidSalesChannel(){
		Book book = new Book(environment, "NoComponentsNoAddOns");
		book.setParty(hh);
		book.setSalesChannel("blah");
		book.sendRequest();
		TestReporter.logAPI(!book.getFaultString().contains("Sales Channel is required : null"), book.getFaultString() ,book);

		LogItems logValidItems = new LogItems();
		logValidItems.addItem("EventDiningServiceIF", "book", true);
		validateLogs(book, logValidItems);
		
		LogItems logInvalidItems = new LogItems();
		logInvalidItems.addItem("AccommodationInventoryRequestComponentServiceIF", "createInventory", false);
		logInvalidItems.addItem("ChargeGroupIF", "createChargeGroupAndPostCharges", false);	
		logInvalidItems.addItem("PartyIF", "createAndRetrieveParty", false);
		logInvalidItems.addItem("TravelPlanServiceV3", "create", false);
		logInvalidItems.addItem("UpdateInventory", "updateInventory", false);
		validateNotInLogs(book, logInvalidItems);
	}
	
	@Test(groups = {"api", "regression", "dining", "eventDiningService", "negative"})
	public void invalidAuthorizationNumber(){
		Book book = new Book(environment, "NoComponentsNoAddOns");
		book.setParty(hh);
		book.setRequestNodeValueByXPath("//authorizationNumber", "12345431");
		book.sendRequest();
		TestReporter.logAPI(!book.getFaultString().contains("INVALID AUTHORIZATION CODE !! : INVALID AUTHORIZATION CODE !"), book.getFaultString() ,book);

		LogItems logValidItems = new LogItems();
		logValidItems.addItem("EventDiningServiceIF", "book", true);
		logValidItems.addItem("PartyIF", "createAndRetrieveParty", false);
		validateLogs(book, logValidItems);
		
		LogItems logInvalidItems = new LogItems();
		logInvalidItems.addItem("AccommodationInventoryRequestComponentServiceIF", "createInventory", false);
		logInvalidItems.addItem("ChargeGroupIF", "createChargeGroupAndPostCharges", false);	
		logInvalidItems.addItem("TravelPlanServiceV3", "create", false);
		logInvalidItems.addItem("UpdateInventory", "updateInventory", false);
		validateNotInLogs(book, logInvalidItems);
	}
	
	@Test(groups = {"api", "regression", "dining", "eventDiningService", "negative"})
	public void invalidProductId(){
		Book book = new Book(environment, "NoComponentsNoAddOns");
		book.setParty(hh);
		book.setProductId( "1491863");
		book.sendRequest();
		TestReporter.logAPI(!book.getFaultString().contains("Data not found. : No Product could be found for  productTypes [] productID=1491863"), book.getFaultString() ,book);

		LogItems logValidItems = new LogItems();
		logValidItems.addItem("EventDiningServiceIF", "book", true);
		validateLogs(book, logValidItems);
		
		LogItems logInvalidItems = new LogItems();
		logInvalidItems.addItem("AccommodationInventoryRequestComponentServiceIF", "createInventory", false);
		logInvalidItems.addItem("ChargeGroupIF", "createChargeGroupAndPostCharges", false);	
		logInvalidItems.addItem("PartyIF", "createAndRetrieveParty", false);
		logInvalidItems.addItem("TravelPlanServiceV3", "create", false);
		logInvalidItems.addItem("UpdateInventory", "updateInventory", false);
		validateNotInLogs(book, logInvalidItems);
	}
	
	@Test(groups = {"api", "regression", "dining", "eventDiningService", "negative"})
	public void invalidEnterpriseFacilityId(){
		Book book = new Book(environment, "NoComponentsNoAddOns");
		book.setParty(hh);
		book.setRequestNodeValueByXPath("//enterpriseProductId",  "12214");
		book.sendRequest();
		TestReporter.logAPI(!book.getFaultString().contains("Data not found. : No Product could be found for  productTypes [] productID=149863 enterpriseProductID=12214"), book.getFaultString() ,book);

		LogItems logValidItems = new LogItems();
		logValidItems.addItem("EventDiningServiceIF", "book", true);
		validateLogs(book, logValidItems);
		
		LogItems logInvalidItems = new LogItems();
		logInvalidItems.addItem("AccommodationInventoryRequestComponentServiceIF", "createInventory", false);
		logInvalidItems.addItem("ChargeGroupIF", "createChargeGroupAndPostCharges", false);	
		logInvalidItems.addItem("PartyIF", "createAndRetrieveParty", false);
		logInvalidItems.addItem("TravelPlanServiceV3", "create", false);
		logInvalidItems.addItem("UpdateInventory", "updateInventory", false);
		validateNotInLogs(book, logInvalidItems);
	}
	
	@Test(groups = {"api", "regression", "dining", "eventDiningService", "negative"})
	public void invalidBookDateInPast(){
		Book book = new Book(environment, "NoComponentsNoAddOns");
		book.setParty(hh);
		book.setServiceStartDateTime(BaseSoapCommands.GET_DATE_TIME.commandAppend("-30"));
		book.sendRequest();
		TestReporter.logAPI(!book.getFaultString().contains("RESManagement suggests to stop this reservation : Book Date is greater than Service date"), book.getFaultString() ,book);

		LogItems logValidItems = new LogItems();
		logValidItems.addItem("EventDiningServiceIF", "book", true);
		logValidItems.addItem("PartyIF", "createAndRetrieveParty", false);
		validateLogs(book, logValidItems);
		
		LogItems logInvalidItems = new LogItems();
		logInvalidItems.addItem("AccommodationInventoryRequestComponentServiceIF", "createInventory", false);
		logInvalidItems.addItem("ChargeGroupIF", "createChargeGroupAndPostCharges", false);	
		logInvalidItems.addItem("TravelPlanServiceV3", "create", false);
		logInvalidItems.addItem("UpdateInventory", "updateInventory", false);
		validateNotInLogs(book, logInvalidItems);
	}
	
	@Test(groups = {"api", "regression", "dining", "eventDiningService", "negative"})
	public void invalidBookDateExceeds180DaysInFuture(){
		Book book = new Book(environment, "NoComponentsNoAddOns");
		book.setParty(hh);
		book.setServiceStartDateTime(BaseSoapCommands.GET_DATE_TIME.commandAppend("181"));
		book.sendRequest();
		TestReporter.logAPI(!book.getFaultString().contains("RESManagement suggests to stop this reservation : Day Guest cannot book a Dining Reservation beyond 180 days from booking date"), book.getFaultString() ,book);

		LogItems logValidItems = new LogItems();
		logValidItems.addItem("EventDiningServiceIF", "book", true);
		logValidItems.addItem("PartyIF", "createAndRetrieveParty", false);
		validateLogs(book, logValidItems);
		
		LogItems logInvalidItems = new LogItems();
		logInvalidItems.addItem("AccommodationInventoryRequestComponentServiceIF", "createInventory", false);
		logInvalidItems.addItem("ChargeGroupIF", "createChargeGroupAndPostCharges", false);	
		logInvalidItems.addItem("TravelPlanServiceV3", "create", false);
		logInvalidItems.addItem("UpdateInventory", "updateInventory", false);
		validateNotInLogs(book, logInvalidItems);
	}
	
}
