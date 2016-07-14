package com.disney.composite.api.tableServiceDiningService.book;

import org.testng.annotations.Test;

import com.disney.api.soapServices.core.BaseSoapCommands;
import com.disney.api.soapServices.eventDiningService.operations.Book;
import com.disney.composite.BaseTest;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.database.LogItems;
import com.disney.utils.dataFactory.guestFactory.HouseHold;

public class TestBook_Negative extends BaseTest{
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
	@Test(groups = {"api", "regression", "dining", "eventDiningService", "negative"})
	public void missingPrimaryGuest(){
		Book book = new Book(environment, "NoComponentsNoAddOns");
		book.setParty(hh);
		book.setRequestNodeValueByXPath("//primaryGuest", BaseSoapCommands.REMOVE_NODE.toString());
		book.sendRequest();
		TestReporter.logAPI(!book.getFaultString().contains("Travel Plan Guest is required : TRAVEL PLAN GUEST REQUIRED"), book.getFaultString() ,book);

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
	public void missingPrimaryGuestLastName(){
		Book book = new Book(environment, "NoComponentsNoAddOns");
		book.setParty(hh);
		book.setRequestNodeValueByXPath("//primaryGuest/lastName", BaseSoapCommands.REMOVE_NODE.toString());
		book.sendRequest();
		TestReporter.logAPI(!book.getFaultString().contains("Travel Plan Guest is required : TRAVEL PLAN GUEST REQUIRED"), book.getFaultString() ,book);

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
	public void missingPrimaryGuestFirstName(){
		Book book = new Book(environment, "NoComponentsNoAddOns");
		book.setParty(hh);
		book.setRequestNodeValueByXPath("//primaryGuest/firstName", BaseSoapCommands.REMOVE_NODE.toString());
		book.sendRequest();
		TestReporter.logAPI(!book.getFaultString().contains("Travel Plan Guest is required : TRAVEL PLAN GUEST REQUIRED"), book.getFaultString() ,book);

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
	public void missingSalesChannel(){
		Book book = new Book(environment, "NoComponentsNoAddOns");
		book.setParty(hh);
		book.setSalesChannel(BaseSoapCommands.REMOVE_NODE.toString());
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
	public void missingCommunicationChannel(){
		Book book = new Book(environment, "NoComponentsNoAddOns");
		book.setParty(hh);
		book.setCommunicationChannel(BaseSoapCommands.REMOVE_NODE.toString());
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
	public void missingSourceAccountingCenter(){
		Book book = new Book(environment, "NoComponentsNoAddOns");
		book.setParty(hh);
		book.setSourceAccountingCenter(BaseSoapCommands.REMOVE_NODE.toString());
		book.sendRequest();
		TestReporter.logAPI(!book.getFaultString().contains("SOURCER ACCOUNTING CENTER IS REQUIRED! : SOURCER ACCOUNTING CENTER IS REQUIRED!"), book.getFaultString() ,book);
		
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
	public void missingFacilityId(){
		Book book = new Book(environment, "NoComponentsNoAddOns");
		book.setParty(hh);
		book.setFacilityId(BaseSoapCommands.REMOVE_NODE.toString());
		book.sendRequest();
		TestReporter.logAPI(!book.getFaultString().contains("FACILITY ID/NAME IS REQUIRED! : FACILITY ID IS REQUIRED!"), book.getFaultString() ,book);
		
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
	public void missingProductId(){
		Book book = new Book(environment, "NoComponentsNoAddOns");
		book.setParty(hh);
		book.setProductId(BaseSoapCommands.REMOVE_NODE.toString());
		book.sendRequest();
		TestReporter.logAPI(!book.getFaultString().contains("PRODUCT ID IS REQUIRED !! : DREAMS/ENTERPRISE PRODUCT ID IS REQUIRED!!"), book.getFaultString() ,book);

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
	public void missingProductType(){
		Book book = new Book(environment, "NoComponentsNoAddOns");
		book.setParty(hh);
		book.setProductType(BaseSoapCommands.REMOVE_NODE.toString());
		book.sendRequest();
		TestReporter.logAPI(!book.getFaultString().contains("PRODUCT TYPE NAME IS REQUIRED!! : PRODUCT TYPE NAME IS REQUIRED!!"), book.getFaultString() ,book);

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
	public void missingServiceStartDate(){
		Book book = new Book(environment, "NoComponentsNoAddOns");
		book.setParty(hh);
		book.setServiceStartDateTime(BaseSoapCommands.REMOVE_NODE.toString());
		book.sendRequest();
		TestReporter.logAPI(!book.getFaultString().contains("INVALID  SERVICE START DATE!! : INVALID SERVICE START DATE!!"), book.getFaultString() ,book);

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
	public void missingFreeze(){
		Book book = new Book(environment, "NoComponentsNoAddOns");
		book.setParty(hh);
		book.setRequestNodeValueByXPath("//freezeId",BaseSoapCommands.REMOVE_NODE.toString());
		book.sendRequest();
		TestReporter.logAPI(!book.getFaultString().contains("Freeze Id is required : FREEZE ID IS REQUIRED"), book.getFaultString() ,book);

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
	public void missingReservableResourceID(){
		Book book = new Book(environment, "NoComponentsNoAddOns");
		book.setParty(hh);
		book.setReservableResourceId(BaseSoapCommands.REMOVE_NODE.toString());
		book.sendRequest();
		TestReporter.logAPI(!book.getFaultString().contains("RESERVABLE RESOURCE ID IS REQUIRED! : RESERVABLE RESOURCE ID IS REQUIRED!"), book.getFaultString() ,book);

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
	public void missingPartyRoles(){
		Book book = new Book(environment, "NoComponentsNoAddOns");
		book.setParty(hh);
		book.setRequestNodeValueByXPath("//partyRoles",BaseSoapCommands.REMOVE_NODE.toString());
		book.sendRequest();
		TestReporter.logAPI(!book.getFaultString().contains("Invalid PartyMix. Please send valid partymix : INVALID PARTY SIZE"), book.getFaultString() ,book);
		
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
	public void missingPartyRoleAgeType(){
		Book book = new Book(environment, "NoComponentsNoAddOns");
		book.setParty(hh);
		book.setRequestNodeValueByXPath("//partyRoles/ageType",BaseSoapCommands.REMOVE_NODE.toString());
		book.sendRequest();
		TestReporter.logAPI(!book.getFaultString().contains("Age Type is required : AGE TYPE IS REQUIRED."), book.getFaultString() ,book);

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
	public void missingServicePeriodId(){
		Book book = new Book(environment, "NoComponentsNoAddOns");
		book.setParty(hh);
		book.setServicePeriodId(BaseSoapCommands.REMOVE_NODE.toString());
		book.sendRequest();
		TestReporter.logAPI(!book.getFaultString().contains("ENTERPRISE SERVICE PERIOD ID IS REQUIRED.! : ENTERPRISE SERVICE PERIOD ID IS REQUIRED."), book.getFaultString() ,book);
		
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

}
