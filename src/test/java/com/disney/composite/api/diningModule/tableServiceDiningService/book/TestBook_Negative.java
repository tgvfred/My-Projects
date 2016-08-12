package com.disney.composite.api.diningModule.tableServiceDiningService.book;

import org.testng.annotations.Test;

import com.disney.api.soapServices.applicationError.ApplicationErrorCode;
import com.disney.api.soapServices.applicationError.DiningErrorCode;
import com.disney.api.soapServices.applicationError.PartyErrorCode;
import com.disney.api.soapServices.core.BaseSoapCommands;
import com.disney.api.soapServices.diningModule.tableServiceDiningServicePort.operations.Book;
import com.disney.composite.BaseTest;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.database.LogItems;
import com.disney.utils.dataFactory.guestFactory.HouseHold;

public class TestBook_Negative extends BaseTest{
	// Defining global variables
	protected String TPS_ID = null;
	
	@Test(groups = {"api", "regression", "dining", "TableServiceDiningService", "negative"})
	public void invalidFacilityId(){
		TestReporter.logScenario("Invalid Facility ID");
		Book book = new Book(environment, "NoComponentsNoAddOns");
		book.setParty(hh);
		book.setFacilityId("1010");
		sendRequestAndValidateLogs(book, DiningErrorCode.INVALID_FACILITY_ID, "FACILITY SERVICE UNAVAILABLE OR RETURED INVALID FACILITY!! : INVALID FACILITY ID");
	}

	@Test(groups = {"api", "regression", "dining", "TableServiceDiningService", "negative"})
	public void invalidCommunicationChannel(){
		TestReporter.logScenario("Invalid Communications Channel");
		Book book = new Book(environment, "NoComponentsNoAddOns");
		book.setParty(hh);
		book.setCommunicationChannel("Invalid Center");
		sendRequestAndValidateLogs(book, DiningErrorCode.COMMUNICATION_CHANNEL_REQUIRED, "communication Channel is required : null");
	}
	
	@Test(groups = {"api", "regression", "dining", "TableServiceDiningService", "negative"})
	public void invalidTravelPlanIdFormat(){
		TestReporter.logScenario("Invalid Travel Plan ID Format");
		Book book = new Book(environment, "NoComponentsNoAddOns");
		book.setParty(hh);
		book.setTravelPlanId("Invalid Id");
		book.sendRequest();
		TestReporter.logAPI(!book.getFaultString().contains("Unmarshalling Error: For input string: \"Invalid Id\""), book.getFaultString() ,book);
	}

	@Test(groups = {"api", "regression", "dining", "TableServiceDiningService", "negative"})
	public void invalidTravelPlanId(){
		TestReporter.logScenario("Invalid Travel Plan ID");
		Book book = new Book(environment, "NoComponentsNoAddOns");
		book.setParty(hh);
		book.setTravelPlanId("1111111");
		sendRequestAndValidateLogs(book, DiningErrorCode.TRAVEL_PLAN_NOT_FOUND, "TRAVEL_PLAN_NOT_FOUND : TRAVEL PLAN NOT FOUND");
	}
	
	@Test(groups = {"api", "regression", "dining", "TableServiceDiningService", "negative"})
	public void invalidPrimaryGuestTitle(){
		TestReporter.logScenario("Invalid Primary Guest Title");
		Book book = new Book(environment, "NoComponentsNoAddOns");
		HouseHold party = new HouseHold(1);
		party.primaryGuest().setTitle("Mre.");
		book.setParty(party);
		sendRequestAndValidateLogs(book, PartyErrorCode.SALUTATION_INVALID, "Salutation is invalid : Salutation Mre. is invalid");
	}
	
	@Test(groups = {"api", "regression", "dining", "TableServiceDiningService", "negative"})
	public void invalidPrimaryGuestCountry(){
		TestReporter.logScenario("Invalid Primary Guest Country");
		Book book = new Book(environment, "NoComponentsNoAddOns");
		HouseHold party = new HouseHold(1);
		party.primaryGuest().primaryAddress().setCountry("Randland");
		book.setParty(party);
		sendRequestAndValidateLogs(book, PartyErrorCode.CREATE_PARTY_ERROR, "Create Party Error : Please enter valid country code");
	}
	
	@Test(groups = {"api", "regression", "dining", "TableServiceDiningService", "negative"})
	public void invalidSalesChannel(){
		TestReporter.logScenario("Invalid Sales Channel");
		Book book = new Book(environment, "NoComponentsNoAddOns");
		book.setParty(hh);
		book.setSalesChannel("blah");
		sendRequestAndValidateLogs(book, DiningErrorCode.SALES_CHANNEL_REQUIRED, "Sales Channel is required : null");
	}
	
	@Test(groups = {"api", "regression", "dining", "TableServiceDiningService", "negative"})
	public void invalidAuthorizationNumber(){
		TestReporter.logScenario("Invalid Authorization Number");
		Book book = new Book(environment, "NoComponentsNoAddOns");
		book.setParty(hh);
		book.setRequestNodeValueByXPath("//authorizationNumber", "12345431");
		sendRequestAndValidateLogs(book, DiningErrorCode.INVALID_AUTHORIZATION_CODE, "INVALID AUTHORIZATION CODE !! : INVALID AUTHORIZATION CODE !");
	}
	
	@Test(groups = {"api", "regression", "dining", "TableServiceDiningService", "negative"})
	public void invalidBookDateInPast(){
		TestReporter.logScenario("Book Date in the Past");
		Book book = new Book(environment, "NoComponentsNoAddOns");
		book.setParty(hh);
		book.setServiceStartDateTime(BaseSoapCommands.GET_DATE_TIME.commandAppend("-30"));
		sendRequestAndValidateLogs(book, DiningErrorCode.EXCEPTION_RULE_FIRED, "RESManagement suggests to stop this reservation : Book Date is greater than Service date");
	}
	
	@Test(groups = {"api", "regression", "dining", "TableServiceDiningService", "negative"})
	public void invalidBookDateExceeds180DaysInFuture(){
		TestReporter.logScenario("Book More Than 180 Days In Advance");
		Book book = new Book(environment, "NoComponentsNoAddOns");
		book.setParty(hh);
		book.setServiceStartDateTime(BaseSoapCommands.GET_DATE_TIME.commandAppend("182"));
		sendRequestAndValidateLogs(book, DiningErrorCode.EXCEPTION_RULE_FIRED, "RESManagement suggests to stop this reservation : Day Guest cannot book a Dining Reservation beyond 180 days from booking date");
	}
	@Test(groups = {"api", "regression", "dining", "TableServiceDiningService", "negative"})
	public void missingPrimaryGuest(){
		TestReporter.logScenario("Missing Primary Guest");
		Book book = new Book(environment, "NoComponentsNoAddOns");
		book.setParty(hh);
		book.setRequestNodeValueByXPath("//primaryGuest", BaseSoapCommands.REMOVE_NODE.toString());
		sendRequestAndValidateLogs(book, DiningErrorCode.TRAVEL_PLAN_GUEST_REQUIRED, "Travel Plan Guest is required : TRAVEL PLAN GUEST REQUIRED");
	}
	
	@Test(groups = {"api", "regression", "dining", "TableServiceDiningService", "negative"})
	public void missingPrimaryGuestLastName(){
		TestReporter.logScenario("Missing Primary Guest Last Name");
		Book book = new Book(environment, "NoComponentsNoAddOns");
		book.setParty(hh);
		book.setRequestNodeValueByXPath("//primaryGuest/lastName", BaseSoapCommands.REMOVE_NODE.toString());
		sendRequestAndValidateLogs(book, DiningErrorCode.TRAVEL_PLAN_GUEST_REQUIRED,"Travel Plan Guest is required : TRAVEL PLAN GUEST REQUIRED");
	}

	@Test(groups = {"api", "regression", "dining", "TableServiceDiningService", "negative"})
	public void missingPrimaryGuestFirstName(){
		TestReporter.logScenario("Missing Primary Guest First Name");
		Book book = new Book(environment, "NoComponentsNoAddOns");
		book.setParty(hh);
		book.setRequestNodeValueByXPath("//primaryGuest/firstName", BaseSoapCommands.REMOVE_NODE.toString());
		sendRequestAndValidateLogs(book, DiningErrorCode.TRAVEL_PLAN_GUEST_REQUIRED, "Travel Plan Guest is required : TRAVEL PLAN GUEST REQUIRED");
	}

	@Test(groups = {"api", "regression", "dining", "TableServiceDiningService", "negative"})
	public void missingSalesChannel(){
		TestReporter.logScenario("Missing Sales Channel");
		Book book = new Book(environment, "NoComponentsNoAddOns");
		book.setParty(hh);
		book.setSalesChannel(BaseSoapCommands.REMOVE_NODE.toString());
		sendRequestAndValidateLogs(book,  DiningErrorCode.SALES_CHANNEL_REQUIRED,"Sales Channel is required : null");
	}
	
	@Test(groups = {"api", "regression", "dining", "TableServiceDiningService", "negative"})
	public void missingCommunicationChannel(){
		TestReporter.logScenario("Missing Communication Channel");
		Book book = new Book(environment, "NoComponentsNoAddOns");
		book.setParty(hh);
		book.setCommunicationChannel(BaseSoapCommands.REMOVE_NODE.toString());
		sendRequestAndValidateLogs(book,  DiningErrorCode.COMMUNICATION_CHANNEL_REQUIRED,"communication Channel is required : null");
	}
	
	@Test(groups = {"api", "regression", "dining", "TableServiceDiningService", "negative"})
	public void missingSourceAccountingCenter(){
		TestReporter.logScenario("Missing Source Accounting Center");
		Book book = new Book(environment, "NoComponentsNoAddOns");
		book.setParty(hh);
		book.setSourceAccountingCenter(BaseSoapCommands.REMOVE_NODE.toString());
		sendRequestAndValidateLogs(book,  DiningErrorCode.SRC_ACCOUNTING_CENTER_REQUIRED,"SOURCER ACCOUNTING CENTER IS REQUIRED! : SOURCER ACCOUNTING CENTER IS REQUIRED!");
	}

	@Test(groups = {"api", "regression", "dining", "TableServiceDiningService", "negative"})
	public void missingFacilityId(){
		TestReporter.logScenario("Missing Facility ID");
		Book book = new Book(environment, "NoComponentsNoAddOns");
		book.setParty(hh);
		book.setFacilityId(BaseSoapCommands.REMOVE_NODE.toString());
		sendRequestAndValidateLogs(book,  DiningErrorCode.INVALID_FACILITY,"FACILITY ID/NAME IS REQUIRED! : FACILITY ID IS REQUIRED!");
	}

	@Test(groups = {"api", "regression", "dining", "TableServiceDiningService", "negative"})
	public void missingProductType(){
		TestReporter.logScenario("Missing Product Type");
		Book book = new Book(environment, "NoComponentsNoAddOns");
		book.setParty(hh);
		book.setProductType(BaseSoapCommands.REMOVE_NODE.toString());
		sendRequestAndValidateLogs(book,  DiningErrorCode.PRODUCT_TYPE_NAME_REQUIRED,"PRODUCT TYPE NAME IS REQUIRED!! : PRODUCT TYPE NAME IS REQUIRED!!");
	}

	@Test(groups = {"api", "regression", "dining", "TableServiceDiningService", "negative"})
	public void missingServiceStartDate(){
		TestReporter.logScenario("Missing Service Start Date");
		Book book = new Book(environment, "NoComponentsNoAddOns");
		book.setParty(hh);
		book.setServiceStartDateTime(BaseSoapCommands.REMOVE_NODE.toString());
		sendRequestAndValidateLogs(book,  DiningErrorCode.SERVICE_START_DATE_REQUIRED,"INVALID  SERVICE START DATE!! : INVALID SERVICE START DATE!!");
	}

	@Test(groups = {"api", "regression", "dining", "TableServiceDiningService", "negative"})
	public void missingFreeze(){
		TestReporter.logScenario("Missing Freeze ID");
		Book book = new Book(environment, "NoComponentsNoAddOns");
		book.setParty(hh);
		book.setRequestNodeValueByXPath("//freezeId",BaseSoapCommands.REMOVE_NODE.toString());
		sendRequestAndValidateLogs(book,  DiningErrorCode.FREEZE_ID_REQUIRED,"Freeze Id is required : FREEZE ID IS REQUIRED");
	}

	@Test(groups = {"api", "regression", "dining", "TableServiceDiningService", "negative"})
	public void missingReservableResourceID(){
		TestReporter.logScenario("Missing Reservable Resource ID");
		Book book = new Book(environment, "NoComponentsNoAddOns");
		book.setParty(hh);
		book.setReservableResourceId(BaseSoapCommands.REMOVE_NODE.toString());
		sendRequestAndValidateLogs(book,  DiningErrorCode.NO_RESERVABLE_RESOURCE_ID,"RESERVABLE RESOURCE ID IS REQUIRED! : RESERVABLE RESOURCE ID IS REQUIRED!");
	}

	@Test(groups = {"api", "regression", "dining", "TableServiceDiningService", "negative"})
	public void missingPartyRoles(){
		TestReporter.logScenario("Missing Party Roles");
		Book book = new Book(environment, "NoComponentsNoAddOns");
		book.setParty(hh);
		book.setRequestNodeValueByXPath("//partyRoles",BaseSoapCommands.REMOVE_NODE.toString());
		sendRequestAndValidateLogs(book,  DiningErrorCode.INVALID_PARTYMIX,"Invalid PartyMix. Please send valid partymix : INVALID PARTY SIZE");
	}

	@Test(groups = {"api", "regression", "dining", "TableServiceDiningService", "negative"})
	public void missingPartyRoleAgeType(){
		TestReporter.logScenario("Missing Party Role Age Type");
		Book book = new Book(environment, "NoComponentsNoAddOns");
		book.setParty(hh);
		book.setRequestNodeValueByXPath("//partyRoles/ageType",BaseSoapCommands.REMOVE_NODE.toString());
		sendRequestAndValidateLogs(book,  DiningErrorCode.AGE_TYPE_REQUIRED,"Age Type is required : AGE TYPE IS REQUIRED.");
	}

	@Test(groups = {"api", "regression", "dining", "TableServiceDiningService", "negative"})
	public void missingServicePeriodId(){
		TestReporter.logScenario("Missing Service Period ID");
		Book book = new Book(environment, "NoComponentsNoAddOns");
		book.setParty(hh);
		book.setServicePeriodId(BaseSoapCommands.REMOVE_NODE.toString());
		sendRequestAndValidateLogs(book, DiningErrorCode.SERVICE_PERIOD_REQUIRED, "ENTERPRISE SERVICE PERIOD ID IS REQUIRED.! : ENTERPRISE SERVICE PERIOD ID IS REQUIRED.");
	}

	private void sendRequestAndValidateLogs(Book book, ApplicationErrorCode error, String fault){
		book.sendRequest();
		validateApplicationError(book, error);
		TestReporter.logAPI(!book.getFaultString().contains(fault), book.getFaultString() ,book);
		
		LogItems logValidItems = new LogItems();
		logValidItems.addItem("TableServiceDiningServiceIF", "book", true);		
		validateLogs(book, logValidItems, 10000);
		
		LogItems logInvalidItems = new LogItems();
		logInvalidItems.addItem("AccommodationInventoryRequestComponentServiceIF", "createInventory", false);
		logInvalidItems.addItem("ChargeGroupIF", "createChargeGroupAndPostCharges", false);
		logInvalidItems.addItem("TravelPlanServiceV3", "create", false);
		logInvalidItems.addItem("UpdateInventory", "updateInventory", false);
		logInvalidItems.addItem("TravelPlanServiceV3SEI", "create", false);
		logInvalidItems.addItem("GuestServiceV1", "create", false);
		logInvalidItems.addItem("GuestLinkServiceV1", "createEntitlementReference", false);
		logInvalidItems.addItem("PartyIF", "updateExternalPartyAndLocatorId", false);
		logInvalidItems.addItem("GuestServiceV1", "create", false);
		logInvalidItems.addItem("GuestLinkServiceV1SEI", "createEntitlementReference", false);
		validateNotInLogs(book, logInvalidItems);
	}
}
