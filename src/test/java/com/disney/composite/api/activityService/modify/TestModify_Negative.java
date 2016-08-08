package com.disney.composite.api.activityService.modify;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.activityServicePort.operations.Modify;
import com.disney.api.soapServices.applicationError.ActivityErrorCode;
import com.disney.api.soapServices.applicationError.ApplicationErrorCode;
import com.disney.api.soapServices.applicationError.PartyErrorCode;
import com.disney.api.soapServices.core.BaseSoapCommands;
import com.disney.composite.BaseTest;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.database.LogItems;
import com.disney.utils.dataFactory.guestFactory.HouseHold;
import com.disney.utils.dataFactory.staging.bookSEReservation.ActivityEventReservation;
import com.disney.utils.dataFactory.staging.bookSEReservation.ScheduledEventReservation;

public class TestModify_Negative extends BaseTest{
	protected ScheduledEventReservation res = null;
	
	@BeforeClass(alwaysRun = true)
	@Parameters({ "environment" })
	public void setup(@Optional String environment){
		this.environment = environment;
		HouseHold hh = new HouseHold(1);
		hh.primaryGuest().setAge("6");
		res = new ActivityEventReservation(this.environment, hh);
		res.book(ScheduledEventReservation.NOCOMPONENTSNOADDONS);
	}
	
	@AfterTest(alwaysRun = true)
	public synchronized void closeSession() {res.cancel();}

	@Test(groups = {"api", "regression", "activity", "activityService", "negative"})
	public void invalidFacilityId(){
		Modify modify = new Modify(this.environment, "NoComponentsNoAddOns");
		modify.setReservationNumber(res.getConfirmationNumber());
		modify.setTravelPlanId(res.getTravelPlanId());
		modify.setParty(res.party());
		modify.setFacilityId("-1");
		modify.setServiceStartDate(res.getServiceStartDate());
		modify.setServicePeriodId(res.getServicePeriodId());
		modify.setProductId(res.getProductId());
		modify.sendRequest();
		validateApplicationError(modify, ActivityErrorCode.INVALID_FACILITY);
		TestReporter.logAPI(!modify.getFaultString().contains("FACILITY ID/NAME IS REQUIRED! : FACILITY ID IS REQUIRED!"), modify.getFaultString() ,modify);

		LogItems logValidItems = new LogItems();
		logValidItems.addItem("ActivityServiceIF", "modify", true);
		validateLogs(modify, logValidItems);
		
		LogItems logInvalidItems = new LogItems();
		logInvalidItems.addItem("ChargeGroupIF", "modifyGuestContainerChargeGroup", false);
		logInvalidItems.addItem("ChargeGroupIF", "modifyRootChargeGroup", false);
		logInvalidItems.addItem("TravelPlanServiceCrossReferenceV3", "updateOrder", false);
		logInvalidItems.addItem("TravelPlanServiceCrossReferenceV3SEI", "updateOrder", false);
		validateNotInLogs(modify, logInvalidItems);
	}

	@Test(groups = {"api", "regression", "activity", "activityService", "negative"})
	public void invalidCommunicationChannel(){
		Modify modify = new Modify(this.environment, "NoComponentsNoAddOns");
		modify.setReservationNumber(res.getConfirmationNumber());
		modify.setTravelPlanId(res.getTravelPlanId());
		modify.setParty(res.party());
		modify.setFacilityId(res.getFacilityId());
		modify.setServiceStartDate(res.getServiceStartDate());
		modify.setServicePeriodId(res.getServicePeriodId());
		modify.setProductId(res.getProductId());
		modify.setCommunicationChannel("Blah");
		modify.sendRequest();

		validateApplicationError(modify, ActivityErrorCode.COMMUNICATION_CHANNEL_REQUIRED);
		TestReporter.logAPI(!modify.getFaultString().contains("communication Channel is required : null"), modify.getFaultString() ,modify);


		LogItems logValidItems = new LogItems();
		logValidItems.addItem("ActivityServiceIF", "modify", true);
		validateLogs(modify, logValidItems);
		
		LogItems logInvalidItems = new LogItems();
		logInvalidItems.addItem("ChargeGroupIF", "modifyGuestContainerChargeGroup", false);
		logInvalidItems.addItem("ChargeGroupIF", "modifyRootChargeGroup", false);
		logInvalidItems.addItem("TravelPlanServiceCrossReferenceV3", "updateOrder", false);
		logInvalidItems.addItem("TravelPlanServiceCrossReferenceV3SEI", "updateOrder", false);
		validateNotInLogs(modify, logInvalidItems);
	}
	
	@Test(groups = {"api", "regression", "activity", "activityService", "negative"})
	public void invalidTravelPlanIdFormat(){
		Modify modify = new Modify(this.environment, "NoComponentsNoAddOns");
		modify.setReservationNumber(res.getConfirmationNumber());
		modify.setTravelPlanId("Invalid Id");
		modify.setParty(res.party());
		modify.setFacilityId(res.getFacilityId());
		modify.setServiceStartDate(res.getServiceStartDate());
		modify.setServicePeriodId(res.getServicePeriodId());
		modify.setProductId(res.getProductId());
		modify.sendRequest();
	
		TestReporter.logAPI(!modify.getFaultString().contains("Unmarshalling Error: For input string: \"Invalid Id\""), modify.getFaultString() ,modify);
	}

	@Test(groups = {"api", "regression", "activity", "activityService", "negative"})
	public void invalidReservationNumberFormat(){
		Modify modify = new Modify(this.environment, "NoComponentsNoAddOns");
		modify.setReservationNumber("Invalid Id");
		modify.setTravelPlanId(res.getTravelPlanId());
		modify.setParty(res.party());
		modify.setFacilityId(res.getFacilityId());
		modify.setServiceStartDate(res.getServiceStartDate());
		modify.setServicePeriodId(res.getServicePeriodId());
		modify.setProductId(res.getProductId());
		modify.sendRequest();
	
		TestReporter.logAPI(!modify.getFaultString().contains("Unmarshalling Error: For input string: \"Invalid Id\""), modify.getFaultString() ,modify);
	}
	
	@Test(groups = {"api", "regression", "activity", "activityService", "negative"})
	public void invalidReservationNumber(){
		Modify modify = new Modify(this.environment, "NoComponentsNoAddOns");
		modify.setReservationNumber("12345678910");
		modify.setTravelPlanId(res.getTravelPlanId());
		modify.setParty(res.party());
		modify.setFacilityId(res.getFacilityId());
		modify.setServiceStartDate(res.getServiceStartDate());
		modify.setServicePeriodId(res.getServicePeriodId());
		modify.setProductId(res.getProductId());
		modify.sendRequest();
		validateApplicationError(modify, ActivityErrorCode.RECORD_NOT_FOUND_EXCEPTION);
		TestReporter.logAPI(!modify.getFaultString().contains("RECORD NOT FOUND : NO RESERVATION FOUND WITH 12345678910"), modify.getFaultString() ,modify);

		LogItems logValidItems = new LogItems();
		logValidItems.addItem("ActivityServiceIF", "modify", true);
		validateLogs(modify, logValidItems);
		
		LogItems logInvalidItems = new LogItems();
		logInvalidItems.addItem("ChargeGroupIF", "modifyGuestContainerChargeGroup", false);
		logInvalidItems.addItem("ChargeGroupIF", "modifyRootChargeGroup", false);
		logInvalidItems.addItem("TravelPlanServiceCrossReferenceV3", "updateOrder", false);
		logInvalidItems.addItem("TravelPlanServiceCrossReferenceV3SEI", "updateOrder", false);
		validateNotInLogs(modify, logInvalidItems);
	}
	
	@Test(groups = {"api", "regression", "activity", "activityService", "negative"})
	public void invalidPrimaryGuestTitle(){
		HouseHold hh = new HouseHold(1);
		hh.primaryGuest().setAge("6");
		ScheduledEventReservation res2 = new ActivityEventReservation(this.environment, hh);
		res2.book(ScheduledEventReservation.NOCOMPONENTSNOADDONS);
		Modify modify = new Modify(this.environment, "NoComponentsNoAddOns");
		modify.setReservationNumber(res2.getConfirmationNumber());
		modify.setTravelPlanId(res2.getTravelPlanId());
		res2.party().primaryGuest().setTitle("Mre.");
		modify.setParty(res2.party());
		modify.setFacilityId(res2.getFacilityId());
		modify.setServiceStartDate(res2.getServiceStartDate());
		modify.setServicePeriodId(res2.getServicePeriodId());
		modify.setProductId(res2.getProductId());
		modify.sendRequest();
		validateApplicationError(modify, PartyErrorCode.SALUTATION_INVALID);
		TestReporter.logAPI(!modify.getFaultString().contains("Salutation is invalid : Salutation Mre. is invalid"), modify.getFaultString() ,modify);

		LogItems logValidItems = new LogItems();
		logValidItems.addItem("ActivityServiceIF", "modify", true);
		logValidItems.addItem("PartyIF", "createAndRetrieveParty", true);
		validateLogs(modify, logValidItems);
		
		LogItems logInvalidItems = new LogItems();
		logInvalidItems.addItem("ChargeGroupIF", "modifyGuestContainerChargeGroup", false);
		logInvalidItems.addItem("ChargeGroupIF", "modifyRootChargeGroup", false);
		logInvalidItems.addItem("TravelPlanServiceCrossReferenceV3", "updateOrder", false);
		logInvalidItems.addItem("TravelPlanServiceCrossReferenceV3SEI", "updateOrder", false);
		validateNotInLogs(modify, logInvalidItems);
		
	}
	
	@Test(groups = {"api", "regression", "activity", "activityService", "negative"})
	public void invalidPrimaryGuestCountry(){
		HouseHold hh = new HouseHold(1);
		hh.primaryGuest().setAge("6");
		ScheduledEventReservation res2 = new ActivityEventReservation(this.environment, hh);
		res2.book(ScheduledEventReservation.NOCOMPONENTSNOADDONS);
		Modify modify = new Modify(this.environment, "NoComponentsNoAddOns");
		modify.setReservationNumber(res2.getConfirmationNumber());
		modify.setTravelPlanId(res2.getTravelPlanId());
		res2.party().primaryGuest().primaryAddress().setCountry("Randland");
		modify.setParty(res2.party());
		modify.setFacilityId(res2.getFacilityId());
		modify.setServiceStartDate(res2.getServiceStartDate());
		modify.setServicePeriodId(res2.getServicePeriodId());
		modify.setProductId(res2.getProductId());
		modify.sendRequest();

		validateApplicationError(modify, PartyErrorCode.CREATE_PARTY_ERROR);
		TestReporter.logAPI(!modify.getFaultString().contains("Create Party Error : Please enter valid country code"), modify.getFaultString() ,modify);

		LogItems logValidItems = new LogItems();
		logValidItems.addItem("ActivityServiceIF", "modify", true);
		logValidItems.addItem("PartyIF", "createAndRetrieveParty", true);
		validateLogs(modify, logValidItems);
		
		LogItems logInvalidItems = new LogItems();
		logInvalidItems.addItem("AccommodationInventoryRequestComponentServiceIF", "createInventory", false);
		logInvalidItems.addItem("ChargeGroupIF", "createChargeGroupAndPostCharges", false);	
		logInvalidItems.addItem("TravelPlanServiceV3", "create", false);
		logInvalidItems.addItem("UpdateInventory", "updateInventory", false);
		validateNotInLogs(modify, logInvalidItems);
	}
	
	@Test(groups = {"api", "regression", "activity", "activityService", "negative"})
	public void invalidSalesChannel(){
		Modify modify = new Modify(this.environment, "NoComponentsNoAddOns");
		modify.setReservationNumber(res.getConfirmationNumber());
		modify.setTravelPlanId(res.getTravelPlanId());
		modify.setParty(res.party());
		modify.setFacilityId(res.getFacilityId());
		modify.setServiceStartDate(res.getServiceStartDate());
		modify.setServicePeriodId(res.getServicePeriodId());
		modify.setProductId(res.getProductId());
		modify.setSalesChannel("Blah");
		modify.sendRequest();
		validateApplicationError(modify, ActivityErrorCode.SALES_CHANNEL_REQUIRED);
		TestReporter.logAPI(!modify.getFaultString().contains("Sales Channel is required : null"), modify.getFaultString() ,modify);


		LogItems logValidItems = new LogItems();
		logValidItems.addItem("ActivityServiceIF", "modify", true);
		validateLogs(modify, logValidItems);
		
		LogItems logInvalidItems = new LogItems();
		logInvalidItems.addItem("ChargeGroupIF", "modifyGuestContainerChargeGroup", false);
		logInvalidItems.addItem("ChargeGroupIF", "modifyRootChargeGroup", false);
		logInvalidItems.addItem("TravelPlanServiceCrossReferenceV3", "updateOrder", false);
		logInvalidItems.addItem("TravelPlanServiceCrossReferenceV3SEI", "updateOrder", false);
		validateNotInLogs(modify, logInvalidItems);
	}
	
	@Test(groups = {"api", "regression", "activity", "activityService", "negative"})
	public void invalidAuthorizationNumber(){
		Modify modify = new Modify(this.environment, "NoComponentsNoAddOns");
		modify.setReservationNumber(res.getConfirmationNumber());
		modify.setTravelPlanId(res.getTravelPlanId());
		modify.setParty(res.party());
		modify.setFacilityId(res.getFacilityId());
		modify.setServiceStartDate(res.getServiceStartDate());
		modify.setServicePeriodId(res.getServicePeriodId());
		modify.setProductId(res.getProductId());
		modify.setRequestNodeValueByXPath("//authorizationNumber", "12345431");
		modify.sendRequest();
		validateApplicationError(modify, ActivityErrorCode.INVALID_AUTHORIZATION_CODE);
		TestReporter.logAPI(!modify.getFaultString().contains("INVALID AUTHORIZATION CODE !! : INVALID AUTHORIZATION CODE !"), modify.getFaultString() ,modify);

		LogItems logValidItems = new LogItems();
		logValidItems.addItem("ActivityServiceIF", "modify", true);
		logValidItems.addItem("PartyIF", "createAndRetrieveParty", false);
		validateLogs(modify, logValidItems);
		
		LogItems logInvalidItems = new LogItems();
		logInvalidItems.addItem("AccommodationInventoryRequestComponentServiceIF", "createInventory", false);
		logInvalidItems.addItem("ChargeGroupIF", "createChargeGroupAndPostCharges", false);	
		logInvalidItems.addItem("TravelPlanServiceV3", "create", false);
		logInvalidItems.addItem("UpdateInventory", "updateInventory", false);
		validateNotInLogs(modify, logInvalidItems);
	}
	
	@Test(groups = {"api", "regression", "activity", "activityService", "negative"})
	public void invalidProductId(){
		Modify modify = new Modify(this.environment, "NoComponentsNoAddOns");
		modify.setReservationNumber(res.getConfirmationNumber());
		modify.setTravelPlanId(res.getTravelPlanId());
		modify.setParty(res.party());
		modify.setFacilityId(res.getFacilityId());
		modify.setServiceStartDate(res.getServiceStartDate());
		modify.setServicePeriodId(res.getServicePeriodId());
		modify.setProductId("1491863");
		modify.sendRequest();
		validateApplicationError(modify, ActivityErrorCode.DATA_NOT_FOUND_SERVICE_EXCEPTION);
		TestReporter.logAPI(!modify.getFaultString().contains("Data not found. : No Product could be found for  productTypes [] productID=1491863"), modify.getFaultString() ,modify);

		LogItems logValidItems = new LogItems();
		logValidItems.addItem("ActivityServiceIF", "modify", true);
		validateLogs(modify, logValidItems);
		
		LogItems logInvalidItems = new LogItems();
		logInvalidItems.addItem("AccommodationInventoryRequestComponentServiceIF", "createInventory", false);
		logInvalidItems.addItem("ChargeGroupIF", "createChargeGroupAndPostCharges", false);	
		logInvalidItems.addItem("PartyIF", "createAndRetrieveParty", false);
		logInvalidItems.addItem("TravelPlanServiceV3", "create", false);
		logInvalidItems.addItem("UpdateInventory", "updateInventory", false);
		validateNotInLogs(modify, logInvalidItems);
	}
	
	@Test(groups = {"api", "regression", "activity", "activityService", "negative"})
	public void invalidBookDateInPast(){
		Modify modify = new Modify(this.environment, "NoComponentsNoAddOns");
		modify.setReservationNumber(res.getConfirmationNumber());
		modify.setTravelPlanId(res.getTravelPlanId());
		modify.setParty(res.party());
		modify.setFacilityId(res.getFacilityId());
		modify.setServiceStartDate(res.getServiceStartDate());
		modify.setServicePeriodId(res.getServicePeriodId());
		modify.setServiceStartDateTime(BaseSoapCommands.GET_DATE_TIME.commandAppend("-30"));
		modify.sendRequest();
		validateApplicationError(modify, ActivityErrorCode.EXCEPTION_RULE_FIRED);
		TestReporter.logAPI(!modify.getFaultString().contains("RESManagement suggests to stop this reservation : Book Date is greater than Service date"), modify.getFaultString() ,modify);

		LogItems logValidItems = new LogItems();
		logValidItems.addItem("ActivityServiceIF", "modify", true);
		logValidItems.addItem("PartyIF", "createAndRetrieveParty", false);
		validateLogs(modify, logValidItems);
		
		LogItems logInvalidItems = new LogItems();
		logInvalidItems.addItem("AccommodationInventoryRequestComponentServiceIF", "createInventory", false);
		logInvalidItems.addItem("ChargeGroupIF", "createChargeGroupAndPostCharges", false);	
		logInvalidItems.addItem("TravelPlanServiceV3", "create", false);
		logInvalidItems.addItem("UpdateInventory", "updateInventory", false);
		validateNotInLogs(modify, logInvalidItems);
	}
	
	@Test(groups = {"api", "regression", "activity", "activityService", "negative"})
	public void invalidBookDateExceeds180DaysInFuture(){
		Modify modify = new Modify(this.environment, "NoComponentsNoAddOns");
		modify.setReservationNumber(res.getConfirmationNumber());
		modify.setTravelPlanId(res.getTravelPlanId());
		modify.setParty(res.party());
		modify.setFacilityId(res.getFacilityId());
		modify.setServiceStartDate(res.getServiceStartDate());
		modify.setServicePeriodId(res.getServicePeriodId());
		modify.setServiceStartDateTime(BaseSoapCommands.GET_DATE_TIME.commandAppend("182"));
		modify.sendRequest();
		validateApplicationError(modify, ActivityErrorCode.EXCEPTION_RULE_FIRED);
		TestReporter.logAPI(!modify.getFaultString().contains("RESManagement suggests to stop this reservation : Day Guest cannot book a Dining Reservation beyond 180 days from booking date"), modify.getFaultString() ,modify);

		LogItems logValidItems = new LogItems();
		logValidItems.addItem("ActivityServiceIF", "modify", true);
		logValidItems.addItem("PartyIF", "createAndRetrieveParty", false);
		validateLogs(modify, logValidItems);
		
		LogItems logInvalidItems = new LogItems();
		logInvalidItems.addItem("AccommodationInventoryRequestComponentServiceIF", "createInventory", false);
		logInvalidItems.addItem("ChargeGroupIF", "createChargeGroupAndPostCharges", false);	
		logInvalidItems.addItem("TravelPlanServiceV3", "create", false);
		logInvalidItems.addItem("UpdateInventory", "updateInventory", false);
		validateNotInLogs(modify, logInvalidItems);
	}
	
	@Test(groups = {"api", "regression", "activity", "activityService", "negative"})
	public void missingPrimaryGuest(){
		Modify modify = new Modify(this.environment, "NoComponentsNoAddOns");
		modify.setReservationNumber(res.getConfirmationNumber());
		modify.setTravelPlanId(res.getTravelPlanId());
		modify.setParty(res.party());
		modify.setFacilityId(res.getFacilityId());
		modify.setServiceStartDate(res.getServiceStartDate());
		modify.setServicePeriodId(res.getServicePeriodId());
		modify.setRequestNodeValueByXPath("//primaryGuest", BaseSoapCommands.REMOVE_NODE.toString());
		modify.sendRequest();
		validateApplicationError(modify, ActivityErrorCode.TRAVEL_PLAN_GUEST_REQUIRED);
		TestReporter.logAPI(!modify.getFaultString().contains("Travel Plan Guest is required : TRAVEL PLAN GUEST REQUIRED"), modify.getFaultString() ,modify);

		LogItems logValidItems = new LogItems();
		logValidItems.addItem("ActivityServiceIF", "modify", true);
		validateLogs(modify, logValidItems);
		
		LogItems logInvalidItems = new LogItems();
		logInvalidItems.addItem("AccommodationInventoryRequestComponentServiceIF", "createInventory", false);
		logInvalidItems.addItem("ChargeGroupIF", "createChargeGroupAndPostCharges", false);
		logInvalidItems.addItem("PartyIF", "createAndRetrieveParty", false);	
		logInvalidItems.addItem("TravelPlanServiceV3", "create", false);
		logInvalidItems.addItem("UpdateInventory", "updateInventory", false);
		validateNotInLogs(modify, logInvalidItems);
	}
	
	@Test(groups = {"api", "regression", "activity", "activityService", "negative"})
	public void missingPrimaryGuestLastName(){
		Modify modify = new Modify(this.environment, "NoComponentsNoAddOns");
		modify.setReservationNumber(res.getConfirmationNumber());
		modify.setTravelPlanId(res.getTravelPlanId());
		modify.setParty(res.party());
		modify.setFacilityId(res.getFacilityId());
		modify.setServiceStartDate(res.getServiceStartDate());
		modify.setServicePeriodId(res.getServicePeriodId());
		modify.setRequestNodeValueByXPath("//primaryGuest/lastName", BaseSoapCommands.REMOVE_NODE.toString());
		modify.sendRequest();
		validateApplicationError(modify, ActivityErrorCode.TRAVEL_PLAN_GUEST_REQUIRED);
		TestReporter.logAPI(!modify.getFaultString().contains("Travel Plan Guest is required : TRAVEL PLAN GUEST REQUIRED"), modify.getFaultString() ,modify);

		LogItems logValidItems = new LogItems();
		logValidItems.addItem("ActivityServiceIF", "modify", true);
		validateLogs(modify, logValidItems);
		
		LogItems logInvalidItems = new LogItems();
		logInvalidItems.addItem("AccommodationInventoryRequestComponentServiceIF", "createInventory", false);
		logInvalidItems.addItem("ChargeGroupIF", "createChargeGroupAndPostCharges", false);
		logInvalidItems.addItem("PartyIF", "createAndRetrieveParty", false);	
		logInvalidItems.addItem("TravelPlanServiceV3", "create", false);
		logInvalidItems.addItem("UpdateInventory", "updateInventory", false);
		validateNotInLogs(modify, logInvalidItems);
	}

	@Test(groups = {"api", "regression", "activity", "activityService", "negative"})
	public void missingPrimaryGuestFirstName(){
		Modify modify = new Modify(this.environment, "NoComponentsNoAddOns");
		modify.setReservationNumber(res.getConfirmationNumber());
		modify.setTravelPlanId(res.getTravelPlanId());
		modify.setParty(res.party());
		modify.setFacilityId(res.getFacilityId());
		modify.setServiceStartDate(res.getServiceStartDate());
		modify.setServicePeriodId(res.getServicePeriodId());
		modify.setRequestNodeValueByXPath("//primaryGuest/firstName", BaseSoapCommands.REMOVE_NODE.toString());
		modify.sendRequest();
		validateApplicationError(modify, ActivityErrorCode.TRAVEL_PLAN_GUEST_REQUIRED);
		TestReporter.logAPI(!modify.getFaultString().contains("Travel Plan Guest is required : TRAVEL PLAN GUEST REQUIRED"), modify.getFaultString() ,modify);

		LogItems logValidItems = new LogItems();
		logValidItems.addItem("ActivityServiceIF", "modify", true);
		validateLogs(modify, logValidItems);
		
		LogItems logInvalidItems = new LogItems();
		logInvalidItems.addItem("AccommodationInventoryRequestComponentServiceIF", "createInventory", false);
		logInvalidItems.addItem("ChargeGroupIF", "createChargeGroupAndPostCharges", false);
		logInvalidItems.addItem("PartyIF", "createAndRetrieveParty", false);	
		logInvalidItems.addItem("TravelPlanServiceV3", "create", false);
		logInvalidItems.addItem("UpdateInventory", "updateInventory", false);
		validateNotInLogs(modify, logInvalidItems);
	}

	@Test(groups = {"api", "regression", "activity", "activityService", "negative"})
	public void missingSalesChannel(){
		Modify modify = new Modify(this.environment, "NoComponentsNoAddOns");
		modify.setReservationNumber(res.getConfirmationNumber());
		modify.setTravelPlanId(res.getTravelPlanId());
		modify.setParty(res.party());
		modify.setFacilityId(res.getFacilityId());
		modify.setServiceStartDate(res.getServiceStartDate());
		modify.setServicePeriodId(res.getServicePeriodId());
		modify.setProductId(res.getProductId());
		modify.setSalesChannel(BaseSoapCommands.REMOVE_NODE.toString());
		modify.sendRequest();
		validateApplicationError(modify, ActivityErrorCode.SALES_CHANNEL_REQUIRED);
		TestReporter.logAPI(!modify.getFaultString().contains("Sales Channel is required : null"), modify.getFaultString() ,modify);

		LogItems logValidItems = new LogItems();
		logValidItems.addItem("ActivityServiceIF", "modify", true);
		validateLogs(modify, logValidItems);
		
		LogItems logInvalidItems = new LogItems();
		logInvalidItems.addItem("AccommodationInventoryRequestComponentServiceIF", "createInventory", false);
		logInvalidItems.addItem("ChargeGroupIF", "createChargeGroupAndPostCharges", false);
		logInvalidItems.addItem("PartyIF", "createAndRetrieveParty", false);	
		logInvalidItems.addItem("TravelPlanServiceV3", "create", false);
		logInvalidItems.addItem("UpdateInventory", "updateInventory", false);
		validateNotInLogs(modify, logInvalidItems);
	}
	
	@Test(groups = {"api", "regression", "activity", "activityService", "negative"})
	public void missingCommunicationChannel(){
		Modify modify = new Modify(this.environment, "NoComponentsNoAddOns");
		modify.setReservationNumber(res.getConfirmationNumber());
		modify.setTravelPlanId(res.getTravelPlanId());
		modify.setParty(res.party());
		modify.setFacilityId(res.getFacilityId());
		modify.setServiceStartDate(res.getServiceStartDate());
		modify.setServicePeriodId(res.getServicePeriodId());
		modify.setProductId(res.getProductId());
		modify.setCommunicationChannel(BaseSoapCommands.REMOVE_NODE.toString());
		modify.sendRequest();
		validateApplicationError(modify, ActivityErrorCode.COMMUNICATION_CHANNEL_REQUIRED);
		TestReporter.logAPI(!modify.getFaultString().contains("communication Channel is required : null"), modify.getFaultString() ,modify);
		
		LogItems logValidItems = new LogItems();
		logValidItems.addItem("ActivityServiceIF", "modify", true);
		validateLogs(modify, logValidItems);
		
		LogItems logInvalidItems = new LogItems();
		logInvalidItems.addItem("AccommodationInventoryRequestComponentServiceIF", "createInventory", false);
		logInvalidItems.addItem("ChargeGroupIF", "createChargeGroupAndPostCharges", false);
		logInvalidItems.addItem("PartyIF", "createAndRetrieveParty", false);	
		logInvalidItems.addItem("TravelPlanServiceV3", "create", false);
		logInvalidItems.addItem("UpdateInventory", "updateInventory", false);
		validateNotInLogs(modify, logInvalidItems);
	}

	@Test(groups = {"api", "regression", "activity", "activityService", "negative"})
	public void missingFacilityId(){
		Modify modify = new Modify(this.environment, "NoComponentsNoAddOns");
		modify.setReservationNumber(res.getConfirmationNumber());
		modify.setTravelPlanId(res.getTravelPlanId());
		modify.setParty(res.party());
		modify.setFacilityId(res.getFacilityId());
		modify.setServiceStartDate(res.getServiceStartDate());
		modify.setServicePeriodId(res.getServicePeriodId());
		modify.setProductId(res.getProductId());
		modify.setFacilityId(BaseSoapCommands.REMOVE_NODE.toString());
		modify.sendRequest();
		validateApplicationError(modify, ActivityErrorCode.INVALID_FACILITY);
		TestReporter.logAPI(!modify.getFaultString().contains("FACILITY ID/NAME IS REQUIRED! : FACILITY ID IS REQUIRED!"), modify.getFaultString() ,modify);
		
		LogItems logValidItems = new LogItems();
		logValidItems.addItem("ActivityServiceIF", "modify", true);
		validateLogs(modify, logValidItems);
		
		LogItems logInvalidItems = new LogItems();
		logInvalidItems.addItem("AccommodationInventoryRequestComponentServiceIF", "createInventory", false);
		logInvalidItems.addItem("ChargeGroupIF", "createChargeGroupAndPostCharges", false);
		logInvalidItems.addItem("PartyIF", "createAndRetrieveParty", false);	
		logInvalidItems.addItem("TravelPlanServiceV3", "create", false);
		logInvalidItems.addItem("UpdateInventory", "updateInventory", false);
		validateNotInLogs(modify, logInvalidItems);
	}

	@Test(groups = {"api", "regression", "activity", "activityService", "negative"})
	public void missingProductId(){
		Modify modify = new Modify(this.environment, "NoComponentsNoAddOns");
		modify.setReservationNumber(res.getConfirmationNumber());
		modify.setTravelPlanId(res.getTravelPlanId());
		modify.setParty(res.party());
		modify.setFacilityId(res.getFacilityId());
		modify.setServiceStartDate(res.getServiceStartDate());
		modify.setServicePeriodId(res.getServicePeriodId());
		modify.setProductId(BaseSoapCommands.REMOVE_NODE.toString());
		modify.sendRequest();
		validateApplicationError(modify, ActivityErrorCode.PRODUCT_ID_REQUIRED);
		TestReporter.logAPI(!modify.getFaultString().contains("PRODUCT ID IS REQUIRED !! : DREAMS/ENTERPRISE PRODUCT ID IS REQUIRED!!"), modify.getFaultString() ,modify);

		LogItems logValidItems = new LogItems();
		logValidItems.addItem("ActivityServiceIF", "modify", true);
		validateLogs(modify, logValidItems);
		
		LogItems logInvalidItems = new LogItems();
		logInvalidItems.addItem("AccommodationInventoryRequestComponentServiceIF", "createInventory", false);
		logInvalidItems.addItem("ChargeGroupIF", "createChargeGroupAndPostCharges", false);
		logInvalidItems.addItem("PartyIF", "createAndRetrieveParty", false);	
		logInvalidItems.addItem("TravelPlanServiceV3", "create", false);
		logInvalidItems.addItem("UpdateInventory", "updateInventory", false);
		validateNotInLogs(modify, logInvalidItems);
	}

	@Test(groups = {"api", "regression", "activity", "activityService", "negative"})
	public void missingProductType(){
		Modify modify = new Modify(this.environment, "NoComponentsNoAddOns");
		modify.setReservationNumber(res.getConfirmationNumber());
		modify.setTravelPlanId(res.getTravelPlanId());
		modify.setParty(res.party());
		modify.setFacilityId(res.getFacilityId());
		modify.setServiceStartDate(res.getServiceStartDate());
		modify.setServicePeriodId(res.getServicePeriodId());
		modify.setProductId(res.getProductId());
		modify.setProductType(BaseSoapCommands.REMOVE_NODE.toString());
		modify.sendRequest();
		validateApplicationError(modify, ActivityErrorCode.PRODUCT_TYPE_NAME_REQUIRED);
		TestReporter.logAPI(!modify.getFaultString().contains("PRODUCT TYPE NAME IS REQUIRED!! : PRODUCT TYPE NAME IS REQUIRED!!"), modify.getFaultString() ,modify);

		LogItems logValidItems = new LogItems();
		logValidItems.addItem("ActivityServiceIF", "modify", true);
		validateLogs(modify, logValidItems);
		
		LogItems logInvalidItems = new LogItems();
		logInvalidItems.addItem("AccommodationInventoryRequestComponentServiceIF", "createInventory", false);
		logInvalidItems.addItem("ChargeGroupIF", "createChargeGroupAndPostCharges", false);
		logInvalidItems.addItem("PartyIF", "createAndRetrieveParty", false);	
		logInvalidItems.addItem("TravelPlanServiceV3", "create", false);
		logInvalidItems.addItem("UpdateInventory", "updateInventory", false);
		validateNotInLogs(modify, logInvalidItems);
	}

	@Test(groups = {"api", "regression", "activity", "activityService", "negative"})
	public void missingServiceStartDate(){
		Modify modify = new Modify(this.environment, "NoComponentsNoAddOns");
		modify.setReservationNumber(res.getConfirmationNumber());
		modify.setTravelPlanId(res.getTravelPlanId());
		modify.setParty(res.party());
		modify.setFacilityId(res.getFacilityId());
		modify.setServiceStartDate(res.getServiceStartDate());
		modify.setServicePeriodId(res.getServicePeriodId());
		modify.setProductId(res.getProductId());
		modify.setServiceStartDateTime(BaseSoapCommands.REMOVE_NODE.toString());
		modify.sendRequest();
		validateApplicationError(modify, ActivityErrorCode.SERVICE_START_DATE_REQUIRED);
		TestReporter.logAPI(!modify.getFaultString().contains("INVALID  SERVICE START DATE!! : INVALID SERVICE START DATE!!"), modify.getFaultString() ,modify);

		LogItems logValidItems = new LogItems();
		logValidItems.addItem("ActivityServiceIF", "modify", true);
		validateLogs(modify, logValidItems);
		
		LogItems logInvalidItems = new LogItems();
		logInvalidItems.addItem("AccommodationInventoryRequestComponentServiceIF", "createInventory", false);
		logInvalidItems.addItem("ChargeGroupIF", "createChargeGroupAndPostCharges", false);
		logInvalidItems.addItem("PartyIF", "createAndRetrieveParty", false);	
		logInvalidItems.addItem("TravelPlanServiceV3", "create", false);
		logInvalidItems.addItem("UpdateInventory", "updateInventory", false);
		validateNotInLogs(modify, logInvalidItems);
	}

	@Test(groups = {"api", "regression", "activity", "activityService", "negative"})
	public void missingReservableResourceID(){
		Modify modify = new Modify(this.environment, "NoComponentsNoAddOns");
		modify.setReservationNumber(res.getConfirmationNumber());
		modify.setTravelPlanId(res.getTravelPlanId());
		modify.setParty(res.party());
		modify.setFacilityId(res.getFacilityId());
		modify.setServiceStartDate(res.getServiceStartDate());
		modify.setServicePeriodId(res.getServicePeriodId());
		modify.setProductId(res.getProductId());
		modify.setReservableResourceId(BaseSoapCommands.REMOVE_NODE.toString());
		modify.sendRequest();
		validateApplicationError(modify, ActivityErrorCode.NO_RESERVABLE_RESOURCE_ID);
		TestReporter.logAPI(!modify.getFaultString().contains("RESERVABLE RESOURCE ID IS REQUIRED! : RESERVABLE RESOURCE ID IS REQUIRED!"), modify.getFaultString() ,modify);

		LogItems logValidItems = new LogItems();
		logValidItems.addItem("ActivityServiceIF", "modify", true);
		logValidItems.addItem("PartyIF", "createAndRetrieveParty", false);
		validateLogs(modify, logValidItems);
		
		LogItems logInvalidItems = new LogItems();
		logInvalidItems.addItem("AccommodationInventoryRequestComponentServiceIF", "createInventory", false);
		logInvalidItems.addItem("ChargeGroupIF", "createChargeGroupAndPostCharges", false);	
		logInvalidItems.addItem("TravelPlanServiceV3", "create", false);
		logInvalidItems.addItem("UpdateInventory", "updateInventory", false);
		validateNotInLogs(modify, logInvalidItems);
	}

	@Test(groups = {"api", "regression", "activity", "activityService", "negative"})
	public void missingPartyRoles(){
		Modify modify = new Modify(this.environment, "NoComponentsNoAddOns");
		modify.setReservationNumber(res.getConfirmationNumber());
		modify.setTravelPlanId(res.getTravelPlanId());
		modify.setParty(res.party());
		modify.setFacilityId(res.getFacilityId());
		modify.setServiceStartDate(res.getServiceStartDate());
		modify.setServicePeriodId(res.getServicePeriodId());
		modify.setProductId(res.getProductId());
		modify.setRequestNodeValueByXPath("//partyRoles",BaseSoapCommands.REMOVE_NODE.toString());
		modify.sendRequest();
		validateApplicationError(modify, ActivityErrorCode.INVALID_PARTYMIX);
		TestReporter.logAPI(!modify.getFaultString().contains("Invalid PartyMix. Please send valid partymix : INVALID PARTY SIZE"), modify.getFaultString() ,modify);
		
		LogItems logValidItems = new LogItems();
		logValidItems.addItem("ActivityServiceIF", "modify", true);
		validateLogs(modify, logValidItems);
		
		LogItems logInvalidItems = new LogItems();
		logInvalidItems.addItem("AccommodationInventoryRequestComponentServiceIF", "createInventory", false);
		logInvalidItems.addItem("ChargeGroupIF", "createChargeGroupAndPostCharges", false);
		logInvalidItems.addItem("PartyIF", "createAndRetrieveParty", false);	
		logInvalidItems.addItem("TravelPlanServiceV3", "create", false);
		logInvalidItems.addItem("UpdateInventory", "updateInventory", false);
		validateNotInLogs(modify, logInvalidItems);
	}

	@Test(groups = {"api", "regression", "activity", "activityService", "negative"})
	public void missingPartyRoleAgeType(){
		Modify modify = new Modify(this.environment, "NoComponentsNoAddOns");
		modify.setReservationNumber(res.getConfirmationNumber());
		modify.setTravelPlanId(res.getTravelPlanId());
		modify.setParty(res.party());
		modify.setFacilityId(res.getFacilityId());
		modify.setServiceStartDate(res.getServiceStartDate());
		modify.setServicePeriodId(res.getServicePeriodId());
		modify.setProductId(res.getProductId());
		modify.setRequestNodeValueByXPath("//partyRoles/ageType",BaseSoapCommands.REMOVE_NODE.toString());
		modify.sendRequest();
		validateApplicationError(modify, ActivityErrorCode.AGE_TYPE_REQUIRED);
		TestReporter.logAPI(!modify.getFaultString().contains("Age Type is required : AGE TYPE IS REQUIRED."), modify.getFaultString() ,modify);

		LogItems logValidItems = new LogItems();
		logValidItems.addItem("ActivityServiceIF", "modify", true);
		validateLogs(modify, logValidItems);
		
		LogItems logInvalidItems = new LogItems();
		logInvalidItems.addItem("AccommodationInventoryRequestComponentServiceIF", "createInventory", false);
		logInvalidItems.addItem("ChargeGroupIF", "createChargeGroupAndPostCharges", false);
		logInvalidItems.addItem("PartyIF", "createAndRetrieveParty", false);	
		logInvalidItems.addItem("TravelPlanServiceV3", "create", false);
		logInvalidItems.addItem("UpdateInventory", "updateInventory", false);
		validateNotInLogs(modify, logInvalidItems);
	}
	
	@Test(groups = {"api", "regression", "activity", "activityService", "negative"})
	public void arrivedReservation(){
		HouseHold hh = new HouseHold(1);
		hh.primaryGuest().setAge("6");
		ScheduledEventReservation res2 = new ActivityEventReservation(this.environment, hh);
		res2.book(ScheduledEventReservation.NOCOMPONENTSNOADDONS);
		Modify modify = new Modify(this.environment, "NoComponentsNoAddOns");
		modify.setReservationNumber(res2.getConfirmationNumber());
		modify.setTravelPlanId(res2.getTravelPlanId());
		modify.setParty(res2.party());
		modify.setFacilityId(res2.getFacilityId());
		modify.setServiceStartDate(res2.getServiceStartDate());
		modify.setServicePeriodId(res2.getServicePeriodId());
		modify.setProductId(res2.getProductId());
		res2.arrived();
		modify.sendRequest();
		validateApplicationError(modify, ActivityErrorCode.INVALID_TRAVEL_STATUS);
		TestReporter.logAPI(!modify.getFaultString().contains("Travel Status is invalid  : INVALID RESERVATION STATUS."), modify.getFaultString() ,modify);
		
		LogItems logValidItems = new LogItems();
		logValidItems.addItem("ActivityServiceIF", "modify", true);
		validateLogs(modify, logValidItems);
		
		LogItems logInvalidItems = new LogItems();
		logInvalidItems.addItem("AccommodationInventoryRequestComponentServiceIF", "createInventory", false);
		logInvalidItems.addItem("ChargeGroupIF", "createChargeGroupAndPostCharges", false);
		logInvalidItems.addItem("PartyIF", "createAndRetrieveParty", false);	
		logInvalidItems.addItem("TravelPlanServiceV3", "create", false);
		logInvalidItems.addItem("UpdateInventory", "updateInventory", false);
		validateNotInLogs(modify, logInvalidItems);
	}

	@Test(groups = {"api", "regression", "activity", "activityService", "negative"})
	public void noShowReservation(){
		HouseHold hh = new HouseHold(1);
		hh.primaryGuest().setAge("6");
		ScheduledEventReservation res2 = new ActivityEventReservation(this.environment, hh);
		res2.book(ScheduledEventReservation.NOCOMPONENTSNOADDONS);
		Modify modify = new Modify(this.environment, "NoComponentsNoAddOns");
		modify.setReservationNumber(res2.getConfirmationNumber());
		modify.setTravelPlanId(res2.getTravelPlanId());
		modify.setParty(res2.party());
		modify.setFacilityId(res2.getFacilityId());
		modify.setServiceStartDate(res2.getServiceStartDate());
		modify.setServicePeriodId(res2.getServicePeriodId());
		modify.setProductId(res2.getProductId());
		res2.noShow();
		sendRequestAndValidateLogs(modify, "Travel Status is invalid  : INVALID RESERVATION STATUS.", ActivityErrorCode.INVALID_TRAVEL_STATUS);
	}
	
	private void sendRequestAndValidateLogs(Modify modify, String faultSTring, ApplicationErrorCode errorCode){
		modify.sendRequest();
		validateApplicationError(modify, errorCode);
		TestReporter.logAPI(!modify.getFaultString().contains(faultSTring), modify.getFaultString() ,modify);
		
		LogItems logValidItems = new LogItems();
		logValidItems.addItem("ActivityServiceIF", "modify", true);
		validateLogs(modify, logValidItems);
		
		LogItems logInvalidItems = new LogItems();
		logInvalidItems.addItem("ChargeGroupIF", "modifyGuestContainerChargeGroup", false);
		logInvalidItems.addItem("ChargeGroupIF", "modifyRootChargeGroup", false);
		logInvalidItems.addItem("TravelPlanServiceCrossReferenceV3", "updateOrder", false);
		logInvalidItems.addItem("TravelPlanServiceCrossReferenceV3SEI", "updateOrder", false);
		logInvalidItems.addItem("FolioServiceIF", "retrieveAccountingTransactions", false);
		logInvalidItems.addItem("PartyIF", "retrieveParty", false);
		logInvalidItems.addItem("PartyIF", "retrieveParties", false);
		logInvalidItems.addItem("PartyIF", "retrievePartyBasicInformation", false);
		logInvalidItems.addItem("PartyIF", "createAndRetrieveParty", false);
		logInvalidItems.addItem("PartyIF", "updateExternalPartyAndLocatorId", false);
		logInvalidItems.addItem("AccommodationInventoryRequestComponentServiceIF", "retrieveAssignmentOwner", false);
		logInvalidItems.addItem("ActivityServiceIF", "retrieve", false);
		logInvalidItems.addItem("PackagingService", "getProducts", false);
		logInvalidItems.addItem("PricingService", "priceComponents", false);
		logInvalidItems.addItem("GuestServiceV1", "create", false);
		validateNotInLogs(modify, logInvalidItems);
	}
}