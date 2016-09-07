package com.disney.composite.api.diningModule.eventDiningService.cancel;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.diningModule.eventDiningService.operations.Cancel;
import com.disney.api.soapServices.applicationError.DiningErrorCode;
import com.disney.api.soapServices.core.BaseSoapCommands;
import com.disney.composite.BaseTest;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.database.LogItems;
import com.disney.utils.dataFactory.guestFactory.HouseHold;
import com.disney.utils.dataFactory.staging.bookSEReservation.EventDiningReservation;
import com.disney.utils.dataFactory.staging.bookSEReservation.ScheduledEventReservation;

public class TestCancel_Negative  extends BaseTest{
	// Defining global variables
	protected String TPS_ID = null;
	protected ScheduledEventReservation res = null;
	
	@Override
	@BeforeClass(alwaysRun = true)
	@Parameters({ "environment" })
	public void setup(@Optional String environment){
		this.environment = environment;
		hh = new HouseHold(1);
		res = new EventDiningReservation(this.environment, hh);
		res.book(ScheduledEventReservation.NOCOMPONENTSNOADDONS);
	}

	@Test(groups = {"api", "regression", "dining", "eventDiningService", "negative"})
	public void missingReservationNumber(){
		Cancel cancel = new Cancel(this.environment,"CancelDiningEvent");
		cancel.setReservationNumber(BaseSoapCommands.REMOVE_NODE.toString());
		cancel.sendRequest();
		validateApplicationError(cancel, DiningErrorCode.RECORD_NOT_FOUND_EXCEPTION);
		TestReporter.logAPI(!cancel.getFaultString().contains("RECORD NOT FOUND : NO RESERVATION FOUND WITH 0"), cancel.getFaultString() ,cancel);

		LogItems logValidItems = new LogItems();
		logValidItems.addItem("EventDiningServiceIF", "cancel", true);
		validateLogs(cancel, logValidItems);
		
		LogItems logInvalidItems = new LogItems();
		logInvalidItems.addItem("AccommodationInventoryRequestComponentServiceIF", "releaseInventory", false);
		logInvalidItems.addItem("ChargeGroupIF", "cancelChargeGroups", false);
		logInvalidItems.addItem("TravelPlanServiceCrossReferenceV3", "cancelOrder", false);
		logInvalidItems.addItem("UpdateInventory", "updateInventory", false);
		validateNotInLogs(cancel, logInvalidItems);
	}
	

	@Test(groups = {"api", "regression", "dining", "eventDiningService", "negative"})
	public void invalidReservationNumber(){
		Cancel cancel = new Cancel(this.environment,"CancelDiningEvent");
		cancel.setReservationNumber("11111");
		cancel.sendRequest();
		validateApplicationError(cancel, DiningErrorCode.RECORD_NOT_FOUND_EXCEPTION);
		TestReporter.logAPI(!cancel.getFaultString().contains("RECORD NOT FOUND : NO RESERVATION FOUND WITH 11111"), cancel.getFaultString() ,cancel);

		LogItems logValidItems = new LogItems();
		logValidItems.addItem("EventDiningServiceIF", "cancel", true);
		validateLogs(cancel, logValidItems);
		
		LogItems logInvalidItems = new LogItems();
		logInvalidItems.addItem("AccommodationInventoryRequestComponentServiceIF", "releaseInventory", false);
		logInvalidItems.addItem("ChargeGroupIF", "cancelChargeGroups", false);
		logInvalidItems.addItem("TravelPlanServiceCrossReferenceV3", "cancelOrder", false);
		logInvalidItems.addItem("UpdateInventory", "updateInventory", false);
		validateNotInLogs(cancel, logInvalidItems);
	}
	
	@Test(groups = {"api", "regression", "dining", "eventDiningService", "negative"})
	public void arrivedReservation(){
		ScheduledEventReservation res2 = new EventDiningReservation(this.environment, hh);
		res2.book(ScheduledEventReservation.NOCOMPONENTSNOADDONS);
		res2.arrived();
		Cancel cancel = new Cancel(this.environment,"CancelDiningEvent");
		cancel.setReservationNumber(res2.getConfirmationNumber());
		cancel.sendRequest();
		validateApplicationError(cancel, DiningErrorCode.INVALID_TRAVEL_STATUS);
		TestReporter.logAPI(!cancel.getFaultString().contains("Travel Status is invalid  : INVALID RESERVATION STATUS."), cancel.getFaultString() ,cancel);

		LogItems logValidItems = new LogItems();
		logValidItems.addItem("EventDiningServiceIF", "cancel", true);
		validateLogs(cancel, logValidItems);
		
		LogItems logInvalidItems = new LogItems();
		logInvalidItems.addItem("AccommodationInventoryRequestComponentServiceIF", "releaseInventory", false);
		logInvalidItems.addItem("ChargeGroupIF", "cancelChargeGroups", false);
		logInvalidItems.addItem("TravelPlanServiceCrossReferenceV3", "cancelOrder", false);
		logInvalidItems.addItem("UpdateInventory", "updateInventory", false);
		validateNotInLogs(cancel, logInvalidItems);
	}
	
	@Test(groups = {"api", "regression", "dining", "eventDiningService", "negative"})
	public void noShowReservation(){
		ScheduledEventReservation res2 = new EventDiningReservation(this.environment, hh);
		res2.book(ScheduledEventReservation.NOCOMPONENTSNOADDONS);
		res2.noShow();
		Cancel cancel = new Cancel(this.environment,"CancelDiningEvent");
		cancel.setReservationNumber(res2.getConfirmationNumber());
		cancel.sendRequest();
		validateApplicationError(cancel, DiningErrorCode.INVALID_TRAVEL_STATUS);
		TestReporter.logAPI(!cancel.getFaultString().contains("Travel Status is invalid  : INVALID RESERVATION STATUS."), cancel.getFaultString() ,cancel);

		LogItems logValidItems = new LogItems();
		logValidItems.addItem("EventDiningServiceIF", "cancel", true);
		validateLogs(cancel, logValidItems);
		
		LogItems logInvalidItems = new LogItems();
		logInvalidItems.addItem("AccommodationInventoryRequestComponentServiceIF", "releaseInventory", false);
		logInvalidItems.addItem("ChargeGroupIF", "cancelChargeGroups", false);
		logInvalidItems.addItem("TravelPlanServiceCrossReferenceV3", "cancelOrder", false);
		logInvalidItems.addItem("UpdateInventory", "updateInventory", false);
		validateNotInLogs(cancel, logInvalidItems);
	}
	
	@Test(groups = {"api", "regression", "dining", "eventDiningService", "negative"})
	public void cancelledReservation(){
		ScheduledEventReservation res2 = new EventDiningReservation(this.environment, hh);
		res2.book(ScheduledEventReservation.NOCOMPONENTSNOADDONS);
		res2.cancel();
		Cancel cancel = new Cancel(this.environment,"CancelDiningEvent");
		cancel.setReservationNumber(res2.getConfirmationNumber());
		cancel.sendRequest();
		validateApplicationError(cancel, DiningErrorCode.INVALID_TRAVEL_STATUS);
		TestReporter.logAPI(!cancel.getFaultString().contains("Travel Status is invalid  : INVALID RESERVATION STATUS."), cancel.getFaultString() ,cancel);

		LogItems logValidItems = new LogItems();
		logValidItems.addItem("EventDiningServiceIF", "cancel", true);
		validateLogs(cancel, logValidItems);
		
		LogItems logInvalidItems = new LogItems();
		logInvalidItems.addItem("AccommodationInventoryRequestComponentServiceIF", "releaseInventory", false);
		logInvalidItems.addItem("ChargeGroupIF", "cancelChargeGroups", false);
		logInvalidItems.addItem("TravelPlanServiceCrossReferenceV3", "cancelOrder", false);
		logInvalidItems.addItem("UpdateInventory", "updateInventory", false);
		validateNotInLogs(cancel, logInvalidItems);
	}
}
