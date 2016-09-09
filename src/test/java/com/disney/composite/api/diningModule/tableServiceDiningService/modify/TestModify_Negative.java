package com.disney.composite.api.diningModule.tableServiceDiningService.modify;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.BaseTest;
import com.disney.api.soapServices.SoapException;
import com.disney.api.soapServices.applicationError.DiningErrorCode;
import com.disney.api.soapServices.applicationError.PartyErrorCode;
import com.disney.api.soapServices.core.BaseSoapCommands;
import com.disney.api.soapServices.diningModule.tableServiceDiningServicePort.operations.Modify;
import com.disney.utils.Randomness;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.database.LogItems;
import com.disney.utils.dataFactory.guestFactory.HouseHold;
import com.disney.utils.dataFactory.staging.bookSEReservation.ScheduledEventReservation;
import com.disney.utils.dataFactory.staging.bookSEReservation.TableServiceDiningReservation;

public class TestModify_Negative extends BaseTest{
	// Defining global variables
	protected String TPS_ID = null;
	protected ScheduledEventReservation res = null;
	
	@BeforeClass(alwaysRun = true)
	@Parameters({ "environment" })
	public void setup(@Optional String environment){
		this.environment = environment;
		HouseHold hh = new HouseHold(1);
		res = new TableServiceDiningReservation(this.environment, hh);
		try{
			res.book(ScheduledEventReservation.NOCOMPONENTSNOADDONS);
		}catch(SoapException se){
			res.book(ScheduledEventReservation.NOCOMPONENTSNOADDONS);
		}
	}
	
	@AfterTest(alwaysRun = true)
	public synchronized void closeSession() {
		try{
			if(res.getConfirmationNumber() != null)
				if(!res.getConfirmationNumber().isEmpty())
					res.cancel();
		}catch(Exception e){}
	}

	@Test(groups = {"api", "regression", "dining", "tableDiningService", "negative"})
	public void invalidFacilityId(){
		TestReporter.logScenario("Invalid Facility ID");
		Modify modify = new Modify(this.environment, "NoComponentsNoAddOns");
		modify.setFreezeId(Randomness.generateMessageId());
		modify.setReservationNumber(res.getConfirmationNumber());
		modify.setTravelPlanId(res.getTravelPlanId());
		modify.setParty(res.party());
		modify.setFacilityId("1010");
		modify.setServiceStartDate(res.getServiceStartDate());
		modify.setServicePeriodId(res.getServicePeriodId());
		modify.sendRequest();
		TestReporter.logAPI(!modify.getFaultString().contains("FACILITY SERVICE UNAVAILABLE OR RETURED INVALID FACILITY!! : INVALID FACILITY ID"), modify.getFaultString() ,modify);

		LogItems logValidItems = new LogItems();
		logValidItems.addItem("TableServiceDiningServiceIF", "modify", true);
		validateLogs(modify, logValidItems);
		
		LogItems logInvalidItems = new LogItems();
		logInvalidItems.addItem("ChargeGroupIF", "modifyGuestContainerChargeGroup", false);
		logInvalidItems.addItem("ChargeGroupIF", "modifyRootChargeGroup", false);
		logInvalidItems.addItem("TravelPlanServiceCrossReferenceV3", "updateOrder", false);
		logInvalidItems.addItem("TravelPlanServiceCrossReferenceV3SEI", "updateOrder", false);
		validateNotInLogs(modify, logInvalidItems);
	}

	@Test(groups = {"api", "regression", "dining", "tableDiningService", "negative"})
	public void invalidCommunicationChannel(){
		TestReporter.logScenario("Invalid Communication Channel");
		Modify modify = new Modify(this.environment, "NoComponentsNoAddOns");
		modify.setFreezeId(Randomness.generateMessageId());
		modify.setReservationNumber(res.getConfirmationNumber());
		modify.setTravelPlanId(res.getTravelPlanId());
		modify.setParty(res.party());
		modify.setFacilityId(res.getFacilityId());
		modify.setServiceStartDate(res.getServiceStartDate());
		modify.setServicePeriodId(res.getServicePeriodId());
		modify.setCommunicationChannel("Blah");
		modify.sendRequest();

		validateApplicationError(modify, DiningErrorCode.COMMUNICATION_CHANNEL_REQUIRED);
		TestReporter.logAPI(!modify.getFaultString().contains("communication Channel is required : null"), modify.getFaultString() ,modify);


		LogItems logValidItems = new LogItems();
		logValidItems.addItem("TableServiceDiningServiceIF", "modify", true);
		validateLogs(modify, logValidItems);
		
		LogItems logInvalidItems = new LogItems();
		logInvalidItems.addItem("ChargeGroupIF", "modifyGuestContainerChargeGroup", false);
		logInvalidItems.addItem("ChargeGroupIF", "modifyRootChargeGroup", false);
		logInvalidItems.addItem("TravelPlanServiceCrossReferenceV3", "updateOrder", false);
		logInvalidItems.addItem("TravelPlanServiceCrossReferenceV3SEI", "updateOrder", false);
		validateNotInLogs(modify, logInvalidItems);
	}
	
	@Test(groups = {"api", "regression", "dining", "tableDiningService", "negative"})
	public void invalidTravelPlanIdFormat(){
		TestReporter.logScenario("Invalid Travel Plan ID Format");
		Modify modify = new Modify(this.environment, "NoComponentsNoAddOns");
		modify.setFreezeId(Randomness.generateMessageId());
		modify.setReservationNumber(res.getConfirmationNumber());
		modify.setTravelPlanId("Invalid Id");
		modify.setParty(res.party());
		modify.setFacilityId(res.getFacilityId());
		modify.setServiceStartDate(res.getServiceStartDate());
		modify.setServicePeriodId(res.getServicePeriodId());
		modify.sendRequest();
		TestReporter.logAPI(!modify.getFaultString().contains("Unmarshalling Error: For input string: \"Invalid Id\""), modify.getFaultString() ,modify);
	}

	@Test(groups = {"api", "regression", "dining", "tableDiningService", "negative"})
	public void invalidReservationNumberFormat(){
		TestReporter.logScenario("Invalid Reservation Number Format");
		Modify modify = new Modify(this.environment, "NoComponentsNoAddOns");
		modify.setFreezeId(Randomness.generateMessageId());
		modify.setReservationNumber("Invalid Id");
		modify.setTravelPlanId(res.getTravelPlanId());
		modify.setParty(res.party());
		modify.setFacilityId(res.getFacilityId());
		modify.setServiceStartDate(res.getServiceStartDate());
		modify.setServicePeriodId(res.getServicePeriodId());
		modify.sendRequest();
	
		TestReporter.logAPI(!modify.getFaultString().contains("Unmarshalling Error: For input string: \"Invalid Id\""), modify.getFaultString() ,modify);
	}
	
	@Test(groups = {"api", "regression", "dining", "tableDiningService", "negative"})
	public void invalidReservationNumber(){
		TestReporter.logScenario("Invalid Reservation Number");
		Modify modify = new Modify(this.environment, "NoComponentsNoAddOns");
		modify.setFreezeId(Randomness.generateMessageId());
		modify.setReservationNumber("12345678910");
		modify.setTravelPlanId(res.getTravelPlanId());
		modify.setParty(res.party());
		modify.setFacilityId(res.getFacilityId());
		modify.setServiceStartDate(res.getServiceStartDate());
		modify.setServicePeriodId(res.getServicePeriodId());
		modify.sendRequest();
		validateApplicationError(modify, DiningErrorCode.RECORD_NOT_FOUND_EXCEPTION);
		TestReporter.logAPI(!modify.getFaultString().contains("RECORD NOT FOUND : NO RESERVATION FOUND WITH 12345678910"), modify.getFaultString() ,modify);

		LogItems logValidItems = new LogItems();
		logValidItems.addItem("TableServiceDiningServiceIF", "modify", true);
		validateLogs(modify, logValidItems);
		
		LogItems logInvalidItems = new LogItems();
		logInvalidItems.addItem("ChargeGroupIF", "modifyGuestContainerChargeGroup", false);
		logInvalidItems.addItem("ChargeGroupIF", "modifyRootChargeGroup", false);
		logInvalidItems.addItem("TravelPlanServiceCrossReferenceV3", "updateOrder", false);
		logInvalidItems.addItem("TravelPlanServiceCrossReferenceV3SEI", "updateOrder", false);
		validateNotInLogs(modify, logInvalidItems);
	}
	
	@Test(groups = {"api", "regression", "dining", "tableDiningService", "negative"})
	public void invalidPrimaryGuestTitle(){
		TestReporter.logScenario("Invalid Primary Guest Title");
		
		Modify modify = new Modify(this.environment, "NoComponentsNoAddOns");
		modify.setFreezeId(Randomness.generateMessageId());
		modify.setReservationNumber(res.getConfirmationNumber());
		modify.setTravelPlanId(res.getTravelPlanId());
		HouseHold party = new HouseHold(1);
		party.primaryGuest().setTitle("Mre.");
		modify.setParty(party);
		modify.setFacilityId(res.getFacilityId());
		modify.setServiceStartDate(res.getServiceStartDate());
		modify.setServicePeriodId(res.getServicePeriodId());
		modify.sendRequest();
		validateApplicationError(modify, PartyErrorCode.SALUTATION_INVALID);
		TestReporter.logAPI(!modify.getFaultString().contains("Salutation is invalid : Salutation Mre. is invalid"), modify.getFaultString() ,modify);

		LogItems logValidItems = new LogItems();
		logValidItems.addItem("TableServiceDiningServiceIF", "modify", true);
		logValidItems.addItem("PartyIF", "createAndRetrieveParty", true);
		validateLogs(modify, logValidItems);
		
		LogItems logInvalidItems = new LogItems();
		logInvalidItems.addItem("ChargeGroupIF", "modifyGuestContainerChargeGroup", false);
		logInvalidItems.addItem("ChargeGroupIF", "modifyRootChargeGroup", false);
		logInvalidItems.addItem("TravelPlanServiceCrossReferenceV3", "updateOrder", false);
		logInvalidItems.addItem("TravelPlanServiceCrossReferenceV3SEI", "updateOrder", false);
		validateNotInLogs(modify, logInvalidItems);
		
	}
	
	@Test(groups = {"api", "regression", "dining", "tableDiningService", "negative"})
	public void invalidPrimaryGuestCountry(){
		TestReporter.logScenario("Invalid Primary Guest Country");
		Modify modify = new Modify(this.environment, "NoComponentsNoAddOns");
		modify.setFreezeId(Randomness.generateMessageId());
		modify.setReservationNumber(res.getConfirmationNumber());
		modify.setTravelPlanId(res.getTravelPlanId());
		HouseHold party = new HouseHold(1);
		party.primaryGuest().primaryAddress().setCountry("Randland");
		modify.setParty(party);
		modify.setFacilityId(res.getFacilityId());
		modify.setServiceStartDate(res.getServiceStartDate());
		modify.setServicePeriodId(res.getServicePeriodId());
		modify.sendRequest();

		validateApplicationError(modify, PartyErrorCode.CREATE_PARTY_ERROR);
		TestReporter.logAPI(!modify.getFaultString().contains("Create Party Error : Please enter valid country code"), modify.getFaultString() ,modify);

		LogItems logValidItems = new LogItems();
		logValidItems.addItem("TableServiceDiningServiceIF", "modify", true);
		logValidItems.addItem("PartyIF", "createAndRetrieveParty", true);
		validateLogs(modify, logValidItems);
		
		LogItems logInvalidItems = new LogItems();
		logInvalidItems.addItem("AccommodationInventoryRequestComponentServiceIF", "createInventory", false);
		logInvalidItems.addItem("ChargeGroupIF", "createChargeGroupAndPostCharges", false);	
		logInvalidItems.addItem("TravelPlanServiceV3", "create", false);
		logInvalidItems.addItem("UpdateInventory", "updateInventory", false);
		validateNotInLogs(modify, logInvalidItems);
	}
	
	@Test(groups = {"api", "regression", "dining", "tableDiningService", "negative"})
	public void invalidSalesChannel(){
		TestReporter.logScenario("Invalid Sales Channel");
		Modify modify = new Modify(this.environment, "NoComponentsNoAddOns");
		modify.setFreezeId(Randomness.generateMessageId());
		modify.setReservationNumber(res.getConfirmationNumber());
		modify.setTravelPlanId(res.getTravelPlanId());
		modify.setParty(res.party());
		modify.setFacilityId(res.getFacilityId());
		modify.setServiceStartDate(res.getServiceStartDate());
		modify.setServicePeriodId(res.getServicePeriodId());
		modify.setSalesChannel("Blah");
		modify.sendRequest();
		validateApplicationError(modify, DiningErrorCode.SALES_CHANNEL_REQUIRED);
		TestReporter.logAPI(!modify.getFaultString().contains("Sales Channel is required : null"), modify.getFaultString() ,modify);


		LogItems logValidItems = new LogItems();
		logValidItems.addItem("TableServiceDiningServiceIF", "modify", true);
		validateLogs(modify, logValidItems);
		
		LogItems logInvalidItems = new LogItems();
		logInvalidItems.addItem("ChargeGroupIF", "modifyGuestContainerChargeGroup", false);
		logInvalidItems.addItem("ChargeGroupIF", "modifyRootChargeGroup", false);
		logInvalidItems.addItem("TravelPlanServiceCrossReferenceV3", "updateOrder", false);
		logInvalidItems.addItem("TravelPlanServiceCrossReferenceV3SEI", "updateOrder", false);
		validateNotInLogs(modify, logInvalidItems);
	}
	
	@Test(groups = {"api", "regression", "dining", "tableDiningService", "negative"})
	public void invalidAuthorizationNumber(){
		TestReporter.logScenario("Invalid Authorization Number");
		Modify modify = new Modify(this.environment, "NoComponentsNoAddOns");
		modify.setFreezeId(Randomness.generateMessageId());
		modify.setReservationNumber(res.getConfirmationNumber());
		modify.setTravelPlanId(res.getTravelPlanId());
		modify.setParty(res.party());
		modify.setFacilityId(res.getFacilityId());
		modify.setServiceStartDate(res.getServiceStartDate());
		modify.setServicePeriodId(res.getServicePeriodId());
		modify.setRequestNodeValueByXPath("//authorizationNumber", "12345431");
		modify.sendRequest();
		validateApplicationError(modify, DiningErrorCode.INVALID_AUTHORIZATION_CODE);
		TestReporter.logAPI(!modify.getFaultString().contains("INVALID AUTHORIZATION CODE !! : INVALID AUTHORIZATION CODE !"), modify.getFaultString() ,modify);

		LogItems logValidItems = new LogItems();
		logValidItems.addItem("TableServiceDiningServiceIF", "modify", true);
		logValidItems.addItem("PartyIF", "createAndRetrieveParty", false);
		validateLogs(modify, logValidItems);
		
		LogItems logInvalidItems = new LogItems();
		logInvalidItems.addItem("AccommodationInventoryRequestComponentServiceIF", "createInventory", false);
		logInvalidItems.addItem("ChargeGroupIF", "createChargeGroupAndPostCharges", false);	
		logInvalidItems.addItem("TravelPlanServiceV3", "create", false);
		logInvalidItems.addItem("UpdateInventory", "updateInventory", false);
		validateNotInLogs(modify, logInvalidItems);
	}
	
	@Test(groups = {"api", "regression", "dining", "tableDiningService", "negative"})
	public void invalidBookDateInPast(){
		TestReporter.logScenario("Booking Date in the Past");
		Modify modify = new Modify(this.environment, "NoComponentsNoAddOns");
		modify.setFreezeId(Randomness.generateMessageId());
		modify.setReservationNumber(res.getConfirmationNumber());
		modify.setTravelPlanId(res.getTravelPlanId());
		modify.setParty(res.party());
		modify.setFacilityId(res.getFacilityId());
		modify.setServiceStartDate(res.getServiceStartDate());
		modify.setServicePeriodId(res.getServicePeriodId());
		modify.setServiceStartDate(BaseSoapCommands.GET_DATE_TIME.commandAppend("-30"));
		modify.sendRequest();
		validateApplicationError(modify, DiningErrorCode.EXCEPTION_RULE_FIRED);
		TestReporter.logAPI(!modify.getFaultString().contains("RESManagement suggests to stop this reservation : Book Date is greater than Service date"), modify.getFaultString() ,modify);

		LogItems logValidItems = new LogItems();
		logValidItems.addItem("TableServiceDiningServiceIF", "modify", true);
		logValidItems.addItem("PartyIF", "createAndRetrieveParty", false);
		validateLogs(modify, logValidItems);
		
		LogItems logInvalidItems = new LogItems();
		logInvalidItems.addItem("AccommodationInventoryRequestComponentServiceIF", "createInventory", false);
		logInvalidItems.addItem("ChargeGroupIF", "createChargeGroupAndPostCharges", false);	
		logInvalidItems.addItem("TravelPlanServiceV3", "create", false);
		logInvalidItems.addItem("UpdateInventory", "updateInventory", false);
		validateNotInLogs(modify, logInvalidItems);
	}
	
	@Test(groups = {"api", "regression", "dining", "tableDiningService", "negative"})
	public void invalidBookDateExceeds180DaysInFuture(){
		TestReporter.logScenario("Booking Date More Then 180 Days in the Future");
		Modify modify = new Modify(this.environment, "NoComponentsNoAddOns");
		modify.setFreezeId(Randomness.generateMessageId());
		modify.setReservationNumber(res.getConfirmationNumber());
		modify.setTravelPlanId(res.getTravelPlanId());
		modify.setParty(res.party());
		modify.setFacilityId(res.getFacilityId());
		modify.setServiceStartDate(res.getServiceStartDate());
		modify.setServicePeriodId(res.getServicePeriodId());
		modify.setServiceStartDate(BaseSoapCommands.GET_DATE_TIME.commandAppend("182"));
		modify.sendRequest();
		validateApplicationError(modify, DiningErrorCode.EXCEPTION_RULE_FIRED);
		TestReporter.logAPI(!modify.getFaultString().contains("RESManagement suggests to stop this reservation : Day Guest cannot book a Dining Reservation beyond 180 days from booking date"), modify.getFaultString() ,modify);

		LogItems logValidItems = new LogItems();
		logValidItems.addItem("TableServiceDiningServiceIF", "modify", true);
		logValidItems.addItem("PartyIF", "createAndRetrieveParty", false);
		validateLogs(modify, logValidItems);
		
		LogItems logInvalidItems = new LogItems();
		logInvalidItems.addItem("AccommodationInventoryRequestComponentServiceIF", "createInventory", false);
		logInvalidItems.addItem("ChargeGroupIF", "createChargeGroupAndPostCharges", false);	
		logInvalidItems.addItem("TravelPlanServiceV3", "create", false);
		logInvalidItems.addItem("UpdateInventory", "updateInventory", false);
		validateNotInLogs(modify, logInvalidItems);
	}
	
	@Test(groups = {"api", "regression", "dining", "tableDiningService", "negative"})
	public void missingPrimaryGuest(){
		TestReporter.logScenario("Missing Primary Guest");
		Modify modify = new Modify(this.environment, "NoComponentsNoAddOns");
		modify.setFreezeId(Randomness.generateMessageId());
		modify.setReservationNumber(res.getConfirmationNumber());
		modify.setTravelPlanId(res.getTravelPlanId());
		modify.setParty(res.party());
		modify.setFacilityId(res.getFacilityId());
		modify.setServiceStartDate(res.getServiceStartDate());
		modify.setServicePeriodId(res.getServicePeriodId());
		modify.setRequestNodeValueByXPath("//primaryGuest", BaseSoapCommands.REMOVE_NODE.toString());
		modify.sendRequest();
		validateApplicationError(modify, DiningErrorCode.TRAVEL_PLAN_GUEST_REQUIRED);
		TestReporter.logAPI(!modify.getFaultString().contains("Travel Plan Guest is required : TRAVEL PLAN GUEST REQUIRED"), modify.getFaultString() ,modify);

		LogItems logValidItems = new LogItems();
		logValidItems.addItem("TableServiceDiningServiceIF", "modify", true);
		validateLogs(modify, logValidItems);
		
		LogItems logInvalidItems = new LogItems();
		logInvalidItems.addItem("AccommodationInventoryRequestComponentServiceIF", "createInventory", false);
		logInvalidItems.addItem("ChargeGroupIF", "createChargeGroupAndPostCharges", false);
		logInvalidItems.addItem("PartyIF", "createAndRetrieveParty", false);	
		logInvalidItems.addItem("TravelPlanServiceV3", "create", false);
		logInvalidItems.addItem("UpdateInventory", "updateInventory", false);
		validateNotInLogs(modify, logInvalidItems);
	}
	
	@Test(groups = {"api", "regression", "dining", "tableDiningService", "negative"})
	public void missingPrimaryGuestLastName(){
		TestReporter.logScenario("Missing Primary Guest Last Name");
		Modify modify = new Modify(this.environment, "NoComponentsNoAddOns");
		modify.setFreezeId(Randomness.generateMessageId());
		modify.setReservationNumber(res.getConfirmationNumber());
		modify.setTravelPlanId(res.getTravelPlanId());
		modify.setParty(res.party());
		modify.setFacilityId(res.getFacilityId());
		modify.setServiceStartDate(res.getServiceStartDate());
		modify.setServicePeriodId(res.getServicePeriodId());
		modify.setRequestNodeValueByXPath("//primaryGuest/lastName", BaseSoapCommands.REMOVE_NODE.toString());
		modify.sendRequest();
		validateApplicationError(modify, DiningErrorCode.TRAVEL_PLAN_GUEST_REQUIRED);
		TestReporter.logAPI(!modify.getFaultString().contains("Travel Plan Guest is required : TRAVEL PLAN GUEST REQUIRED"), modify.getFaultString() ,modify);

		LogItems logValidItems = new LogItems();
		logValidItems.addItem("TableServiceDiningServiceIF", "modify", true);
		validateLogs(modify, logValidItems);
		
		LogItems logInvalidItems = new LogItems();
		logInvalidItems.addItem("AccommodationInventoryRequestComponentServiceIF", "createInventory", false);
		logInvalidItems.addItem("ChargeGroupIF", "createChargeGroupAndPostCharges", false);
		logInvalidItems.addItem("PartyIF", "createAndRetrieveParty", false);	
		logInvalidItems.addItem("TravelPlanServiceV3", "create", false);
		logInvalidItems.addItem("UpdateInventory", "updateInventory", false);
		validateNotInLogs(modify, logInvalidItems);
	}

	@Test(groups = {"api", "regression", "dining", "tableDiningService", "negative"})
	public void missingPrimaryGuestFirstName(){
		TestReporter.logScenario("Missing Primary Guest First Name");
		Modify modify = new Modify(this.environment, "NoComponentsNoAddOns");
		modify.setFreezeId(Randomness.generateMessageId());
		modify.setReservationNumber(res.getConfirmationNumber());
		modify.setTravelPlanId(res.getTravelPlanId());
		modify.setParty(res.party());
		modify.setFacilityId(res.getFacilityId());
		modify.setServiceStartDate(res.getServiceStartDate());
		modify.setServicePeriodId(res.getServicePeriodId());
		modify.setRequestNodeValueByXPath("//primaryGuest/firstName", BaseSoapCommands.REMOVE_NODE.toString());
		modify.sendRequest();
		validateApplicationError(modify, DiningErrorCode.TRAVEL_PLAN_GUEST_REQUIRED);
		TestReporter.logAPI(!modify.getFaultString().contains("Travel Plan Guest is required : TRAVEL PLAN GUEST REQUIRED"), modify.getFaultString() ,modify);

		LogItems logValidItems = new LogItems();
		logValidItems.addItem("TableServiceDiningServiceIF", "modify", true);
		validateLogs(modify, logValidItems);
		
		LogItems logInvalidItems = new LogItems();
		logInvalidItems.addItem("AccommodationInventoryRequestComponentServiceIF", "createInventory", false);
		logInvalidItems.addItem("ChargeGroupIF", "createChargeGroupAndPostCharges", false);
		logInvalidItems.addItem("PartyIF", "createAndRetrieveParty", false);	
		logInvalidItems.addItem("TravelPlanServiceV3", "create", false);
		logInvalidItems.addItem("UpdateInventory", "updateInventory", false);
		validateNotInLogs(modify, logInvalidItems);
	}

	@Test(groups = {"api", "regression", "dining", "tableDiningService", "negative"})
	public void missingSalesChannel(){
		TestReporter.logScenario("Missing Sales Channel");
		Modify modify = new Modify(this.environment, "NoComponentsNoAddOns");
		modify.setFreezeId(Randomness.generateMessageId());
		modify.setReservationNumber(res.getConfirmationNumber());
		modify.setTravelPlanId(res.getTravelPlanId());
		modify.setParty(res.party());
		modify.setFacilityId(res.getFacilityId());
		modify.setServiceStartDate(res.getServiceStartDate());
		modify.setServicePeriodId(res.getServicePeriodId());
		modify.setSalesChannel(BaseSoapCommands.REMOVE_NODE.toString());
		modify.sendRequest();
		validateApplicationError(modify, DiningErrorCode.SALES_CHANNEL_REQUIRED);
		TestReporter.logAPI(!modify.getFaultString().contains("Sales Channel is required : null"), modify.getFaultString() ,modify);

		LogItems logValidItems = new LogItems();
		logValidItems.addItem("TableServiceDiningServiceIF", "modify", true);
		validateLogs(modify, logValidItems);
		
		LogItems logInvalidItems = new LogItems();
		logInvalidItems.addItem("AccommodationInventoryRequestComponentServiceIF", "createInventory", false);
		logInvalidItems.addItem("ChargeGroupIF", "createChargeGroupAndPostCharges", false);
		logInvalidItems.addItem("PartyIF", "createAndRetrieveParty", false);	
		logInvalidItems.addItem("TravelPlanServiceV3", "create", false);
		logInvalidItems.addItem("UpdateInventory", "updateInventory", false);
		validateNotInLogs(modify, logInvalidItems);
	}
	
	@Test(groups = {"api", "regression", "dining", "tableDiningService", "negative"})
	public void missingCommunicationChannel(){
		TestReporter.logScenario("Missing Communications Channel");
		Modify modify = new Modify(this.environment, "NoComponentsNoAddOns");
		modify.setFreezeId(Randomness.generateMessageId());
		modify.setReservationNumber(res.getConfirmationNumber());
		modify.setTravelPlanId(res.getTravelPlanId());
		modify.setParty(res.party());
		modify.setFacilityId(res.getFacilityId());
		modify.setServiceStartDate(res.getServiceStartDate());
		modify.setServicePeriodId(res.getServicePeriodId());
		modify.setCommunicationChannel(BaseSoapCommands.REMOVE_NODE.toString());
		modify.sendRequest();
		validateApplicationError(modify, DiningErrorCode.COMMUNICATION_CHANNEL_REQUIRED);
		TestReporter.logAPI(!modify.getFaultString().contains("communication Channel is required : null"), modify.getFaultString() ,modify);
		
		LogItems logValidItems = new LogItems();
		logValidItems.addItem("TableServiceDiningServiceIF", "modify", true);
		validateLogs(modify, logValidItems);
		
		LogItems logInvalidItems = new LogItems();
		logInvalidItems.addItem("AccommodationInventoryRequestComponentServiceIF", "createInventory", false);
		logInvalidItems.addItem("ChargeGroupIF", "createChargeGroupAndPostCharges", false);
		logInvalidItems.addItem("PartyIF", "createAndRetrieveParty", false);	
		logInvalidItems.addItem("TravelPlanServiceV3", "create", false);
		logInvalidItems.addItem("UpdateInventory", "updateInventory", false);
		validateNotInLogs(modify, logInvalidItems);
	}

	@Test(groups = {"api", "regression", "dining", "tableDiningService", "negative"})
	public void missingFacilityId(){
		TestReporter.logScenario("Missing Facility ID");
		Modify modify = new Modify(this.environment, "NoComponentsNoAddOns");
		modify.setFreezeId(Randomness.generateMessageId());
		modify.setFreezeId();
		modify.setReservationNumber(res.getConfirmationNumber());
		modify.setTravelPlanId(res.getTravelPlanId());
		modify.setParty(res.party());
		modify.setFacilityId(res.getFacilityId());
		modify.setServiceStartDate(res.getServiceStartDate());
		modify.setServicePeriodId(res.getServicePeriodId());
		modify.setFacilityId(BaseSoapCommands.REMOVE_NODE.toString());
		modify.sendRequest();
		validateApplicationError(modify, DiningErrorCode.INVALID_FACILITY);
		TestReporter.logAPI(!modify.getFaultString().contains("FACILITY ID/NAME IS REQUIRED! : FACILITY ID IS REQUIRED!"), modify.getFaultString() ,modify);
		
		LogItems logValidItems = new LogItems();
		logValidItems.addItem("TableServiceDiningServiceIF", "modify", true);
		validateLogs(modify, logValidItems);
		
		LogItems logInvalidItems = new LogItems();
		logInvalidItems.addItem("AccommodationInventoryRequestComponentServiceIF", "createInventory", false);
		logInvalidItems.addItem("ChargeGroupIF", "createChargeGroupAndPostCharges", false);
		logInvalidItems.addItem("PartyIF", "createAndRetrieveParty", false);	
		logInvalidItems.addItem("TravelPlanServiceV3", "create", false);
		logInvalidItems.addItem("UpdateInventory", "updateInventory", false);
		validateNotInLogs(modify, logInvalidItems);
	}

	@Test(groups = {"api", "regression", "dining", "tableDiningService", "negative"})
	public void missingProductType(){
		TestReporter.logScenario("Missing Product Type");
		Modify modify = new Modify(this.environment, "NoComponentsNoAddOns");
		modify.setFreezeId(Randomness.generateMessageId());
		modify.setReservationNumber(res.getConfirmationNumber());
		modify.setTravelPlanId(res.getTravelPlanId());
		modify.setParty(res.party());
		modify.setFacilityId(res.getFacilityId());
		modify.setServiceStartDate(res.getServiceStartDate());
		modify.setServicePeriodId(res.getServicePeriodId());
		modify.setProductType(BaseSoapCommands.REMOVE_NODE.toString());
		modify.sendRequest();
		validateApplicationError(modify, DiningErrorCode.PRODUCT_TYPE_NAME_REQUIRED);
		TestReporter.logAPI(!modify.getFaultString().contains("PRODUCT TYPE NAME IS REQUIRED!! : PRODUCT TYPE NAME IS REQUIRED!!"), modify.getFaultString() ,modify);

		LogItems logValidItems = new LogItems();
		logValidItems.addItem("TableServiceDiningServiceIF", "modify", true);
		validateLogs(modify, logValidItems);
		
		LogItems logInvalidItems = new LogItems();
		logInvalidItems.addItem("AccommodationInventoryRequestComponentServiceIF", "createInventory", false);
		logInvalidItems.addItem("ChargeGroupIF", "createChargeGroupAndPostCharges", false);
		logInvalidItems.addItem("PartyIF", "createAndRetrieveParty", false);	
		logInvalidItems.addItem("TravelPlanServiceV3", "create", false);
		logInvalidItems.addItem("UpdateInventory", "updateInventory", false);
		validateNotInLogs(modify, logInvalidItems);
	}

	@Test(groups = {"api", "regression", "dining", "tableDiningService", "negative"})
	public void missingServiceStartDate(){
		TestReporter.logScenario("Missing Service Start Date");
		Modify modify = new Modify(this.environment, "NoComponentsNoAddOns");
		modify.setFreezeId(Randomness.generateMessageId());
		modify.setFreezeId();
		modify.setReservationNumber(res.getConfirmationNumber());
		modify.setTravelPlanId(res.getTravelPlanId());
		modify.setParty(res.party());
		modify.setFacilityId(res.getFacilityId());
		modify.setServiceStartDate(res.getServiceStartDate());
		modify.setServicePeriodId(res.getServicePeriodId());
		modify.setServiceStartDate(BaseSoapCommands.REMOVE_NODE.toString());
		modify.sendRequest();
		validateApplicationError(modify, DiningErrorCode.SERVICE_START_DATE_REQUIRED);
		TestReporter.logAPI(!modify.getFaultString().contains("INVALID  SERVICE START DATE!! : INVALID SERVICE START DATE!!"), modify.getFaultString() ,modify);

		LogItems logValidItems = new LogItems();
		logValidItems.addItem("TableServiceDiningServiceIF", "modify", true);
		validateLogs(modify, logValidItems);
		
		LogItems logInvalidItems = new LogItems();
		logInvalidItems.addItem("AccommodationInventoryRequestComponentServiceIF", "createInventory", false);
		logInvalidItems.addItem("ChargeGroupIF", "createChargeGroupAndPostCharges", false);
		logInvalidItems.addItem("PartyIF", "createAndRetrieveParty", false);	
		logInvalidItems.addItem("TravelPlanServiceV3", "create", false);
		logInvalidItems.addItem("UpdateInventory", "updateInventory", false);
		validateNotInLogs(modify, logInvalidItems);
	}

	@Test(groups = {"api", "regression", "dining", "tableDiningService", "negative"})
	public void missingReservableResourceID(){
		TestReporter.logScenario("Missing Reservable Resource ID");
		Modify modify = new Modify(this.environment, "NoComponentsNoAddOns");
		modify.setFreezeId(Randomness.generateMessageId());
		modify.setFreezeId();
		modify.setReservationNumber(res.getConfirmationNumber());
		modify.setTravelPlanId(res.getTravelPlanId());
		modify.setParty(res.party());
		modify.setFacilityId(res.getFacilityId());
		modify.setServiceStartDate(res.getServiceStartDate());
		modify.setServicePeriodId(res.getServicePeriodId());
		modify.setReservableResourceId(BaseSoapCommands.REMOVE_NODE.toString());
		modify.sendRequest();
		validateApplicationError(modify, DiningErrorCode.NO_RESERVABLE_RESOURCE_ID);
		TestReporter.logAPI(!modify.getFaultString().contains("RESERVABLE RESOURCE ID IS REQUIRED! : RESERVABLE RESOURCE ID IS REQUIRED!"), modify.getFaultString() ,modify);

		LogItems logValidItems = new LogItems();
		logValidItems.addItem("TableServiceDiningServiceIF", "modify", true);
		logValidItems.addItem("PartyIF", "createAndRetrieveParty", false);
		validateLogs(modify, logValidItems);
		
		LogItems logInvalidItems = new LogItems();
		logInvalidItems.addItem("AccommodationInventoryRequestComponentServiceIF", "createInventory", false);
		logInvalidItems.addItem("ChargeGroupIF", "createChargeGroupAndPostCharges", false);	
		logInvalidItems.addItem("TravelPlanServiceV3", "create", false);
		logInvalidItems.addItem("UpdateInventory", "updateInventory", false);
		validateNotInLogs(modify, logInvalidItems);
	}

	@Test(groups = {"api", "regression", "dining", "tableDiningService", "negative"})
	public void missingPartyRoles(){
		TestReporter.logScenario("Missing Party Roles");
		Modify modify = new Modify(this.environment, "NoComponentsNoAddOns");
		modify.setFreezeId(Randomness.generateMessageId());
		modify.setReservationNumber(res.getConfirmationNumber());
		modify.setTravelPlanId(res.getTravelPlanId());
		modify.setParty(res.party());
		modify.setFacilityId(res.getFacilityId());
		modify.setServiceStartDate(res.getServiceStartDate());
		modify.setServicePeriodId(res.getServicePeriodId());
		modify.setRequestNodeValueByXPath("//partyRoles",BaseSoapCommands.REMOVE_NODE.toString());
		modify.sendRequest();
		validateApplicationError(modify, DiningErrorCode.INVALID_PARTYMIX);
		TestReporter.logAPI(!modify.getFaultString().contains("Invalid PartyMix. Please send valid partymix : INVALID PARTY SIZE"), modify.getFaultString() ,modify);
		
		LogItems logValidItems = new LogItems();
		logValidItems.addItem("TableServiceDiningServiceIF", "modify", true);
		validateLogs(modify, logValidItems);
		
		LogItems logInvalidItems = new LogItems();
		logInvalidItems.addItem("AccommodationInventoryRequestComponentServiceIF", "createInventory", false);
		logInvalidItems.addItem("ChargeGroupIF", "createChargeGroupAndPostCharges", false);
		logInvalidItems.addItem("PartyIF", "createAndRetrieveParty", false);	
		logInvalidItems.addItem("TravelPlanServiceV3", "create", false);
		logInvalidItems.addItem("UpdateInventory", "updateInventory", false);
		validateNotInLogs(modify, logInvalidItems);
	}

	@Test(groups = {"api", "regression", "dining", "tableDiningService", "negative"})
	public void missingPartyRoleAgeType(){
		TestReporter.logScenario("Missing Party Role Age Type");
		Modify modify = new Modify(this.environment, "NoComponentsNoAddOns");
		modify.setFreezeId(Randomness.generateMessageId());
		modify.setReservationNumber(res.getConfirmationNumber());
		modify.setTravelPlanId(res.getTravelPlanId());
		modify.setParty(res.party());
		modify.setFacilityId(res.getFacilityId());
		modify.setServiceStartDate(res.getServiceStartDate());
		modify.setServicePeriodId(res.getServicePeriodId());
		modify.setRequestNodeValueByXPath("//partyRoles/ageType",BaseSoapCommands.REMOVE_NODE.toString());
		modify.sendRequest();
		validateApplicationError(modify, DiningErrorCode.AGE_TYPE_REQUIRED);
		TestReporter.logAPI(!modify.getFaultString().contains("Age Type is required : AGE TYPE IS REQUIRED."), modify.getFaultString() ,modify);

		LogItems logValidItems = new LogItems();
		logValidItems.addItem("TableServiceDiningServiceIF", "modify", true);
		validateLogs(modify, logValidItems);
		
		LogItems logInvalidItems = new LogItems();
		logInvalidItems.addItem("AccommodationInventoryRequestComponentServiceIF", "createInventory", false);
		logInvalidItems.addItem("ChargeGroupIF", "createChargeGroupAndPostCharges", false);
		logInvalidItems.addItem("PartyIF", "createAndRetrieveParty", false);	
		logInvalidItems.addItem("TravelPlanServiceV3", "create", false);
		logInvalidItems.addItem("UpdateInventory", "updateInventory", false);
		validateNotInLogs(modify, logInvalidItems);
	}

	@Test(groups = {"api", "regression", "dining", "tableDiningService", "negative"})
	public void missingServicePeriodId(){
		TestReporter.logScenario("Missing Service Period ID");
		Modify modify = new Modify(this.environment, "NoComponentsNoAddOns");
		modify.setFreezeId(Randomness.generateMessageId());
		modify.setReservationNumber(res.getConfirmationNumber());
		modify.setTravelPlanId(res.getTravelPlanId());
		modify.setParty(res.party());
		modify.setFacilityId(res.getFacilityId());
		modify.setServiceStartDate(res.getServiceStartDate());
		modify.setServicePeriodId(res.getServicePeriodId());
		modify.setServicePeriodId(BaseSoapCommands.REMOVE_NODE.toString());
		modify.sendRequest();
		validateApplicationError(modify, DiningErrorCode.SERVICE_PERIOD_REQUIRED);
		TestReporter.logAPI(!modify.getFaultString().contains("ENTERPRISE SERVICE PERIOD ID IS REQUIRED.! : ENTERPRISE SERVICE PERIOD ID IS REQUIRED."), modify.getFaultString() ,modify);
		
		LogItems logValidItems = new LogItems();
		logValidItems.addItem("TableServiceDiningServiceIF", "modify", true);
		validateLogs(modify, logValidItems);
		
		LogItems logInvalidItems = new LogItems();
		logInvalidItems.addItem("AccommodationInventoryRequestComponentServiceIF", "createInventory", false);
		logInvalidItems.addItem("ChargeGroupIF", "createChargeGroupAndPostCharges", false);
		logInvalidItems.addItem("PartyIF", "createAndRetrieveParty", false);	
		logInvalidItems.addItem("TravelPlanServiceV3", "create", false);
		logInvalidItems.addItem("UpdateInventory", "updateInventory", false);
		validateNotInLogs(modify, logInvalidItems);
	}
	
	@Test(groups = {"api", "regression", "dining", "tableDiningService", "negative"})
	public void arrivedReservation(){
		TestReporter.logScenario("Arrived Reservation");
		ScheduledEventReservation res2 = new TableServiceDiningReservation(this.environment, new HouseHold(1));
		res2.book(ScheduledEventReservation.NOCOMPONENTSNOADDONS);
		Modify modify = new Modify(this.environment, "NoComponentsNoAddOns");
		modify.setFreezeId(Randomness.generateMessageId());
		modify.setReservationNumber(res2.getConfirmationNumber());
		modify.setTravelPlanId(res2.getTravelPlanId());
		modify.setParty(res2.party());
		modify.setFacilityId(res2.getFacilityId());
		modify.setServiceStartDate(res2.getServiceStartDate());
		modify.setServicePeriodId(res2.getServicePeriodId());
		res2.arrived();
		modify.sendRequest();
		validateApplicationError(modify, DiningErrorCode.INVALID_TRAVEL_STATUS);
		TestReporter.logAPI(!modify.getFaultString().contains("Travel Status is invalid  : INVALID RESERVATION STATUS."), modify.getFaultString() ,modify);
		
		LogItems logValidItems = new LogItems();
		logValidItems.addItem("TableServiceDiningServiceIF", "modify", true);
		validateLogs(modify, logValidItems);
		
		LogItems logInvalidItems = new LogItems();
		logInvalidItems.addItem("AccommodationInventoryRequestComponentServiceIF", "createInventory", false);
		logInvalidItems.addItem("ChargeGroupIF", "createChargeGroupAndPostCharges", false);
		logInvalidItems.addItem("PartyIF", "createAndRetrieveParty", false);	
		logInvalidItems.addItem("TravelPlanServiceV3", "create", false);
		logInvalidItems.addItem("UpdateInventory", "updateInventory", false);
		validateNotInLogs(modify, logInvalidItems);
	}

	@Test(groups = {"api", "regression", "dining", "tableDiningService", "negative"})
	public void noShowReservation(){
		TestReporter.logScenario("NoShow Reservation");
		ScheduledEventReservation res2 = new TableServiceDiningReservation(this.environment, new HouseHold(1));
		res2.book(ScheduledEventReservation.NOCOMPONENTSNOADDONS);
		Modify modify = new Modify(this.environment, "NoComponentsNoAddOns");
		modify.setFreezeId(Randomness.generateMessageId());
		modify.setReservationNumber(res2.getConfirmationNumber());
		modify.setTravelPlanId(res2.getTravelPlanId());
		modify.setParty(res2.party());
		modify.setFacilityId(res2.getFacilityId());
		modify.setServiceStartDate(res2.getServiceStartDate());
		modify.setServicePeriodId(res2.getServicePeriodId());
		res2.noShow();
		modify.sendRequest();
		validateApplicationError(modify, DiningErrorCode.INVALID_TRAVEL_STATUS);
		TestReporter.logAPI(!modify.getFaultString().contains("Travel Status is invalid  : INVALID RESERVATION STATUS."), modify.getFaultString() ,modify);
		
		LogItems logValidItems = new LogItems();
		logValidItems.addItem("TableServiceDiningServiceIF", "modify", true);
		validateLogs(modify, logValidItems);
		
		LogItems logInvalidItems = new LogItems();
		logInvalidItems.addItem("AccommodationInventoryRequestComponentServiceIF", "createInventory", false);
		logInvalidItems.addItem("ChargeGroupIF", "createChargeGroupAndPostCharges", false);
		logInvalidItems.addItem("PartyIF", "createAndRetrieveParty", false);	
		logInvalidItems.addItem("TravelPlanServiceV3", "create", false);
		logInvalidItems.addItem("UpdateInventory", "updateInventory", false);
		validateNotInLogs(modify, logInvalidItems);
	}
}