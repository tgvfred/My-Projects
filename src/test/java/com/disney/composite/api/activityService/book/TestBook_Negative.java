package com.disney.composite.api.activityService.book;

import org.testng.annotations.Test;

import com.disney.api.soapServices.core.BaseSoapCommands;
import com.disney.api.soapServices.activityServicePort.operations.Book;
import com.disney.api.soapServices.applicationError.ActivityErrorCode;
import com.disney.api.soapServices.applicationError.ApplicationErrorCode;
import com.disney.api.soapServices.applicationError.PartyErrorCode;
import com.disney.composite.BaseTest;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.database.LogItems;
import com.disney.utils.dataFactory.guestFactory.HouseHold;

public class TestBook_Negative extends BaseTest{
	
	@Test(groups = {"api", "regression", "activity", "activityService", "negative"})
	public void invalidFacilityId(){
		TestReporter.logScenario("Invalid Facility ID");
		Book book = new Book(environment, "NoComponentsNoAddOns");
		book.setParty(hh);
		book.setFacilityId("1010");
		sendRequestAndValidateLogs(book, ActivityErrorCode.INVALID_FACILITY_ID, "FACILITY SERVICE UNAVAILABLE OR RETURED INVALID FACILITY!! : INVALID FACILITY ID");
	}

	@Test(groups = {"api", "regression", "activity", "activityService", "negative"})
	public void invalidCommunicationChannel(){
		TestReporter.logScenario("Invalid Communications Channel");
		Book book = new Book(environment, "NoComponentsNoAddOns");
		book.setParty(hh);
		book.setCommunicationChannel("Invalid Center");
		sendRequestAndValidateLogs(book, ActivityErrorCode.COMMUNICATION_CHANNEL_REQUIRED, "communication Channel is required : null");
	}
	
	@Test(groups = {"api", "regression", "activity", "activityService", "negative"})
	public void invalidTravelPlanIdFormat(){
		TestReporter.logScenario("Invalid Travel Plan ID Format");
		Book book = new Book(environment, "NoComponentsNoAddOns");
		book.setParty(hh);
		book.setTravelPlanId("Invalid Id");
		book.sendRequest();
		TestReporter.logAPI(!book.getFaultString().contains("Unmarshalling Error: For input string: \"Invalid Id\""), book.getFaultString() ,book);
	}

	@Test(groups = {"api", "regression", "activity", "activityService", "negative"})
	public void invalidTravelPlanId(){
		TestReporter.logScenario("Invalid Travel Plan ID");
		Book book = new Book(environment, "NoComponentsNoAddOns");
		book.setParty(hh);
		book.setTravelPlanId("1111111");
		sendRequestAndValidateLogs(book, ActivityErrorCode.TRAVEL_PLAN_NOT_FOUND, "TRAVEL_PLAN_NOT_FOUND : TRAVEL PLAN NOT FOUND");
	}
	
	@Test(groups = {"api", "regression", "activity", "activityService", "negative"})
	public void invalidPrimaryGuestTitle(){
		TestReporter.logScenario("Invalid Primary Guest Title");
		Book book = new Book(environment, "NoComponentsNoAddOns");
		HouseHold party = new HouseHold(1);
		party.primaryGuest().setTitle("Mre.");
		book.setParty(party);
		sendRequestAndValidateLogs(book, PartyErrorCode.SALUTATION_INVALID, "Salutation is invalid : Salutation Mre. is invalid");
	}
	
	@Test(groups = {"api", "regression", "activity", "activityService", "negative"})
	public void invalidPrimaryGuestCountry(){
		TestReporter.logScenario("Invalid Primary Guest Country");
		Book book = new Book(environment, "NoComponentsNoAddOns");
		HouseHold party = new HouseHold(1);
		party.primaryGuest().primaryAddress().setCountry("Randland");
		book.setParty(party);
		sendRequestAndValidateLogs(book, PartyErrorCode.CREATE_PARTY_ERROR, "Create Party Error : Please enter valid country code");
	}
	
	@Test(groups = {"api", "regression", "activity", "activityService", "negative"})
	public void invalidSalesChannel(){
		TestReporter.logScenario("Invalid Sales Channel");
		Book book = new Book(environment, "NoComponentsNoAddOns");
		book.setParty(hh);
		book.setSalesChannel("blah");
		sendRequestAndValidateLogs(book, ActivityErrorCode.SALES_CHANNEL_REQUIRED, "Sales Channel is required : null");
	}
	
	@Test(groups = {"api", "regression", "activity", "activityService", "negative"})
	public void invalidAuthorizationNumber(){
		TestReporter.logScenario("Invalid Authorization Number");
		Book book = new Book(environment, "NoComponentsNoAddOns");
		book.setParty(hh);
		book.setRequestNodeValueByXPath("//authorizationNumber", "12345431");
		sendRequestAndValidateLogs(book, ActivityErrorCode.INVALID_AUTHORIZATION_CODE, "INVALID AUTHORIZATION CODE !! : INVALID AUTHORIZATION CODE !");
	}
	
	@Test(groups = {"api", "regression", "activity", "activityService", "negative"})
	public void invalidProductId(){
		TestReporter.logScenario("Invalid Product ID");
		Book book = new Book(environment, "NoComponentsNoAddOns");
		book.setParty(hh);
		book.setProductId( "1491863");
		sendRequestAndValidateLogs(book, ActivityErrorCode.DATA_NOT_FOUND_SERVICE_EXCEPTION, "Data not found. : No Product could be found for  productTypes [] productID=1491863");
	}
	
	@Test(groups = {"api", "regression", "activity", "activityService", "negative"})
	public void invalidBookDateInPast(){
		TestReporter.logScenario("Invalid Booking Date In The Past");
		Book book = new Book(environment, "NoComponentsNoAddOns");
		book.setParty(hh);
		book.setServiceStartDateTime(BaseSoapCommands.GET_DATE_TIME.commandAppend("-30"));
		sendRequestAndValidateLogs(book, ActivityErrorCode.EXCEPTION_RULE_FIRED, "RESManagement suggests to stop this reservation : Book Date is greater than Service date");
	}
	
	@Test(groups = {"api", "regression", "activity", "activityService", "negative"})
	public void invalidBookDateExceeds180DaysInFuture(){
		TestReporter.logScenario("Invalid Booking Date 180 Days In The Future");
		Book book = new Book(environment, "NoComponentsNoAddOns");
		book.setParty(hh);
		book.setServiceStartDateTime(BaseSoapCommands.GET_DATE_TIME.commandAppend("183"));
		sendRequestAndValidateLogs(book, ActivityErrorCode.EXCEPTION_RULE_FIRED, "RESManagement suggests to stop this reservation : Day Guest cannot book a Dining Reservation beyond 180 days from booking date");
	}
	
	@Test(groups = {"api", "regression", "activity", "activityService", "negative"})
	public void missingPrimaryGuest(){
		TestReporter.logScenario("Missing Primary Guest");
		Book book = new Book(environment, "NoComponentsNoAddOns");
		book.setParty(hh);
		book.setRequestNodeValueByXPath("//primaryGuest", BaseSoapCommands.REMOVE_NODE.toString());
		sendRequestAndValidateLogs(book, ActivityErrorCode.TRAVEL_PLAN_GUEST_REQUIRED, "Travel Plan Guest is required : TRAVEL PLAN GUEST REQUIRED");
	}
	
	@Test(groups = {"api", "regression", "activity", "activityService", "negative"})
	public void missingPrimaryGuestLastName(){
		TestReporter.logScenario("Missing Primary Guest Last Name");
		Book book = new Book(environment, "NoComponentsNoAddOns");
		book.setParty(hh);
		book.setRequestNodeValueByXPath("//primaryGuest/lastName", BaseSoapCommands.REMOVE_NODE.toString());
		sendRequestAndValidateLogs(book, ActivityErrorCode.TRAVEL_PLAN_GUEST_REQUIRED, "Travel Plan Guest is required : TRAVEL PLAN GUEST REQUIRED");
	}

	@Test(groups = {"api", "regression", "activity", "activityService", "negative"})
	public void missingPrimaryGuestFirstName(){
		TestReporter.logScenario("Missing Primary Guest First Name");
		Book book = new Book(environment, "NoComponentsNoAddOns");
		book.setParty(hh);
		book.setRequestNodeValueByXPath("//primaryGuest/firstName", BaseSoapCommands.REMOVE_NODE.toString());
		sendRequestAndValidateLogs(book, ActivityErrorCode.TRAVEL_PLAN_GUEST_REQUIRED, "Travel Plan Guest is required : TRAVEL PLAN GUEST REQUIRED");
	}

	@Test(groups = {"api", "regression", "activity", "activityService", "negative"})
	public void missingSalesChannel(){
		TestReporter.logScenario("Missing Sales Channel");
		Book book = new Book(environment, "NoComponentsNoAddOns");
		book.setParty(hh);
		book.setSalesChannel(BaseSoapCommands.REMOVE_NODE.toString());
		sendRequestAndValidateLogs(book, ActivityErrorCode.SALES_CHANNEL_REQUIRED, "Sales Channel is required : null");
	}
	
	@Test(groups = {"api", "regression", "activity", "activityService", "negative"})
	public void missingCommunicationChannel(){
		TestReporter.logScenario("Missing Communications Channel");
		Book book = new Book(environment, "NoComponentsNoAddOns");
		book.setParty(hh);
		book.setCommunicationChannel(BaseSoapCommands.REMOVE_NODE.toString());
		sendRequestAndValidateLogs(book, ActivityErrorCode.COMMUNICATION_CHANNEL_REQUIRED, "communication Channel is required : null");
	}
	
	@Test(groups = {"api", "regression", "activity", "activityService", "negative"})
	public void missingSourceAccountingCenter(){
		TestReporter.logScenario("Missing Source Accounting Center");
		Book book = new Book(environment, "NoComponentsNoAddOns");
		book.setParty(hh);
		book.setSourceAccountingCenter(BaseSoapCommands.REMOVE_NODE.toString());
		sendRequestAndValidateLogs(book, ActivityErrorCode.SRC_ACCOUNTING_CENTER_REQUIRED, "SOURCER ACCOUNTING CENTER IS REQUIRED! : SOURCE");
	}

	@Test(groups = {"api", "regression", "activity", "activityService", "negative"})
	public void missingFacilityId(){
		TestReporter.logScenario("Missing Facility ID");
		Book book = new Book(environment, "NoComponentsNoAddOns");
		book.setParty(hh);
		book.setFacilityId(BaseSoapCommands.REMOVE_NODE.toString());
		sendRequestAndValidateLogs(book, ActivityErrorCode.INVALID_FACILITY, "FACILITY ID/NAME IS REQUIRED! : FACILITY ID IS REQUIRED!");
	}

	@Test(groups = {"api", "regression", "activity", "activityService", "negative"})
	public void missingProductId(){
		TestReporter.logScenario("Missing Product ID");
		Book book = new Book(environment, "NoComponentsNoAddOns");
		book.setParty(hh);
		book.setProductId(BaseSoapCommands.REMOVE_NODE.toString());
		sendRequestAndValidateLogs(book, ActivityErrorCode.PRODUCT_ID_REQUIRED, "PRODUCT ID IS REQUIRED !! : DREAMS/ENTERPRISE PRODUCT ID IS REQUIRED!!");
	}

	@Test(groups = {"api", "regression", "activity", "activityService", "negative"})
	public void missingProductType(){
		TestReporter.logScenario("Missing Product Type");
		Book book = new Book(environment, "NoComponentsNoAddOns");
		book.setParty(hh);
		book.setProductType(BaseSoapCommands.REMOVE_NODE.toString());
		sendRequestAndValidateLogs(book, ActivityErrorCode.PRODUCT_TYPE_NAME_REQUIRED, "PRODUCT TYPE NAME IS REQUIRED!! : PRODUCT TYPE NAME IS REQUIRED!!");
	}

	@Test(groups = {"api", "regression", "activity", "activityService", "negative"})
	public void missingServiceStartDate(){
		TestReporter.logScenario("Missing Service Start Date");
		Book book = new Book(environment, "NoComponentsNoAddOns");
		book.setParty(hh);
		book.setServiceStartDateTime(BaseSoapCommands.REMOVE_NODE.toString());
		sendRequestAndValidateLogs(book, ActivityErrorCode.SERVICE_START_DATE_REQUIRED, "INVALID  SERVICE START DATE!! : INVALID SERVICE START DATE!!");
	}

	@Test(groups = {"api", "regression", "activity", "activityService", "negative"})
	public void missingFreeze(){
		TestReporter.logScenario("Missing Freeze ID");
		Book book = new Book(environment, "NoComponentsNoAddOns");
		book.setParty(hh);
		book.setRequestNodeValueByXPath("//freezeId",BaseSoapCommands.REMOVE_NODE.toString());
		sendRequestAndValidateLogs(book, ActivityErrorCode.FREEZE_ID_REQUIRED, "Freeze Id is required : FREEZE ID IS REQUIRED");
	}

	@Test(groups = {"api", "regression", "activity", "activityService", "negative"})
	public void missingReservableResourceID(){
		TestReporter.logScenario("Missing Reservable Resource ID");
		Book book = new Book(environment, "NoComponentsNoAddOns");
		book.setParty(hh);
		book.setReservableResourceId(BaseSoapCommands.REMOVE_NODE.toString());
		sendRequestAndValidateLogs(book, ActivityErrorCode.NO_RESERVABLE_RESOURCE_ID, "RESERVABLE RESOURCE ID IS REQUIRED! : RESERVABLE RESOURCE ID IS REQUIRED!");
	}

	@Test(groups = {"api", "regression", "activity", "activityService", "negative"})
	public void missingPartyRoles(){
		TestReporter.logScenario("Missing Party Roles");
		Book book = new Book(environment, "NoComponentsNoAddOns");
		book.setParty(hh);
		book.setRequestNodeValueByXPath("//partyRoles",BaseSoapCommands.REMOVE_NODE.toString());
		sendRequestAndValidateLogs(book, ActivityErrorCode.INVALID_PARTYMIX, "Invalid PartyMix. Please send valid partymix : INVALID PARTY SIZE");
	}

	@Test(groups = {"api", "regression", "activity", "activityService", "negative"})
	public void missingPartyRoleAgeType(){
		TestReporter.logScenario("Missing Party Role Age Type");
		Book book = new Book(environment, "NoComponentsNoAddOns");
		book.setParty(hh);
		book.setRequestNodeValueByXPath("//partyRoles/ageType",BaseSoapCommands.REMOVE_NODE.toString());
		sendRequestAndValidateLogs(book, ActivityErrorCode.AGE_TYPE_REQUIRED, "Age Type is required : AGE TYPE IS REQUIRED.");
	}
	
	private void sendRequestAndValidateLogs(Book book,  ApplicationErrorCode error, String faultString){
		book.sendRequest();
		validateApplicationError(book, error);
		TestReporter.logAPI(!book.getFaultString().contains(faultString), book.getFaultString() ,book);

		LogItems logValidItems = new LogItems();
		logValidItems.addItem("ActivityServiceIF", "book", true);
		validateLogs(book, logValidItems, 8000);
		
		LogItems logInvalidItems = new LogItems();
		logInvalidItems.addItem("AccommodationInventoryRequestComponentServiceIF", "createInventory", false);
		logInvalidItems.addItem("ChargeGroupIF", "createChargeGroupAndPostCharges", false);
		logInvalidItems.addItem("TravelPlanServiceV3", "create", false);
		logInvalidItems.addItem("UpdateInventory", "updateInventory", false);
		logInvalidItems.addItem("ActivityServiceIF", "retrieve", false);
		logInvalidItems.addItem("FolioServiceIF", "retrieveAccountingTransactions", false);
		logInvalidItems.addItem("TravelPlanServiceV3SEI", "create", false);
		logInvalidItems.addItem("PartyIF", "retrievePartyBasicInformation", false);
		logInvalidItems.addItem("PartyIF", "retrieveParty", false);
		logInvalidItems.addItem("AccommodationInventoryRequestComponentServiceIF", "retrieveAssignmentOwner", false);
		logInvalidItems.addItem("CampusServiceIF", "findAllCampuses", false);
		logInvalidItems.addItem("CampusServiceIF", "findAllLocationsByCampus", false);
		logInvalidItems.addItem("DateServiceIF", "retrievePostingDateByCampus", false);
		logInvalidItems.addItem("DateServiceIF", "retrievePostingDateBySource", false);
		logInvalidItems.addItem("AccommodationFacilityServiceSEI", "getAllFacilities", false);
		logInvalidItems.addItem("PricingService", "priceComponents", false);
		logInvalidItems.addItem("PartyIF", "updateExternalPartyAndLocatorId", false);
		validateNotInLogs(book, logInvalidItems);
	}
}
