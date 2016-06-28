package com.disney.composite.api.eventDiningService.book;

import org.testng.annotations.Test;

import com.disney.api.soapServices.core.BaseSoapCommands;
import com.disney.api.soapServices.eventDiningService.operations.Book;
import com.disney.composite.BaseTest;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.database.LogItems;

public class TestBook_Negative_MissingRequiredFields extends BaseTest{
	// Defining global variables
	protected String TPS_ID = null;
	
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
