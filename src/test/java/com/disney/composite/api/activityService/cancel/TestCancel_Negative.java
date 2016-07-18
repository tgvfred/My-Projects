package com.disney.composite.api.activityService.cancel;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.core.BaseSoapCommands;
import com.disney.api.soapServices.activityServicePort.operations.Cancel;
import com.disney.composite.BaseTest;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.database.LogItems;
import com.disney.utils.dataFactory.guestFactory.HouseHold;
import com.disney.utils.dataFactory.staging.bookSEReservation.ActivityEventReservation;
import com.disney.utils.dataFactory.staging.bookSEReservation.ScheduledEventReservation;

public class TestCancel_Negative  extends BaseTest{
	protected ScheduledEventReservation res = null;
	
	@Override
	@BeforeTest(alwaysRun = true)
	@Parameters({ "environment" })
	public void setup(@Optional String environment){
		this.environment = environment;
		hh = new HouseHold(1);
		hh.primaryGuest().setAge("6");
		res = new ActivityEventReservation(this.environment, hh);
		res.book(ScheduledEventReservation.NOCOMPONENTSNOADDONS);
	}

	@AfterTest(alwaysRun=true)
	public void teardown(){
		try{
			if(res != null)
				if(!res.getConfirmationNumber().isEmpty())
					res.cancel();
		}catch(Exception e){}
	}
	
	@Test(groups = {"api", "regression", "activity", "activityService", "negative"})
	public void missingReservationNumber(){
		Cancel cancel = new Cancel(this.environment,"CancelDiningEvent");
		cancel.setReservationNumber(BaseSoapCommands.REMOVE_NODE.toString());
		cancel.sendRequest();
		TestReporter.logAPI(!cancel.getFaultString().contains("RECORD NOT FOUND : NO RESERVATION FOUND WITH 0"), cancel.getFaultString() ,cancel);

		LogItems logValidItems = new LogItems();
		logValidItems.addItem("ActivityServiceIF", "cancel", true);
		validateLogs(cancel, logValidItems);
		
		LogItems logInvalidItems = new LogItems();
		logInvalidItems.addItem("AccommodationInventoryRequestComponentServiceIF", "releaseInventory", false);
		logInvalidItems.addItem("ChargeGroupIF", "cancelChargeGroups", false);
		logInvalidItems.addItem("TravelPlanServiceCrossReferenceV3", "cancelOrder", false);
		logInvalidItems.addItem("UpdateInventory", "updateInventory", false);
		validateNotInLogs(cancel, logInvalidItems);
	}
	

	@Test(groups = {"api", "regression", "activity", "activityService", "negative"})
	public void invalidReservationNumber(){
		Cancel cancel = new Cancel(this.environment,"CancelDiningEvent");
		cancel.setReservationNumber("11111");
		cancel.sendRequest();
		TestReporter.logAPI(!cancel.getFaultString().contains("RECORD NOT FOUND : NO RESERVATION FOUND WITH 11111"), cancel.getFaultString() ,cancel);

		LogItems logValidItems = new LogItems();
		logValidItems.addItem("ActivityServiceIF", "cancel", true);
		validateLogs(cancel, logValidItems);
		
		LogItems logInvalidItems = new LogItems();
		logInvalidItems.addItem("AccommodationInventoryRequestComponentServiceIF", "releaseInventory", false);
		logInvalidItems.addItem("ChargeGroupIF", "cancelChargeGroups", false);
		logInvalidItems.addItem("TravelPlanServiceCrossReferenceV3", "cancelOrder", false);
		logInvalidItems.addItem("UpdateInventory", "updateInventory", false);
		validateNotInLogs(cancel, logInvalidItems);
	}
	
	@Test(groups = {"api", "regression", "activity", "activityService", "negative"})
	public void arrivedReservation(){
		ScheduledEventReservation res2 = new ActivityEventReservation(this.environment, hh);
		res2.book(ScheduledEventReservation.NOCOMPONENTSNOADDONS);
		res2.arrived();
		Cancel cancel = new Cancel(this.environment,"CancelDiningEvent");
		cancel.setReservationNumber(res2.getConfirmationNumber());
		cancel.sendRequest();
		TestReporter.logAPI(!cancel.getFaultString().contains("Travel Status is invalid  : INVALID RESERVATION STATUS."), cancel.getFaultString() ,cancel);

		LogItems logValidItems = new LogItems();
		logValidItems.addItem("ActivityServiceIF", "cancel", true);
		validateLogs(cancel, logValidItems);
		
		LogItems logInvalidItems = new LogItems();
		logInvalidItems.addItem("AccommodationInventoryRequestComponentServiceIF", "releaseInventory", false);
		logInvalidItems.addItem("ChargeGroupIF", "cancelChargeGroups", false);
		logInvalidItems.addItem("TravelPlanServiceCrossReferenceV3", "cancelOrder", false);
		logInvalidItems.addItem("UpdateInventory", "updateInventory", false);
		validateNotInLogs(cancel, logInvalidItems);
	}
	
	@Test(groups = {"api", "regression", "activity", "activityService", "negative"})
	public void noShowReservation(){
		ScheduledEventReservation res2 = new ActivityEventReservation(this.environment, hh);
		res2.book(ScheduledEventReservation.NOCOMPONENTSNOADDONS);
		res2.noShow();
		Cancel cancel = new Cancel(this.environment,"CancelDiningEvent");
		cancel.setReservationNumber(res2.getConfirmationNumber());
		cancel.sendRequest();
		TestReporter.logAPI(!cancel.getFaultString().contains("Travel Status is invalid  : INVALID RESERVATION STATUS."), cancel.getFaultString() ,cancel);

		LogItems logValidItems = new LogItems();
		logValidItems.addItem("ActivityServiceIF", "cancel", true);
		validateLogs(cancel, logValidItems);
		
		LogItems logInvalidItems = new LogItems();
		logInvalidItems.addItem("AccommodationInventoryRequestComponentServiceIF", "releaseInventory", false);
		logInvalidItems.addItem("ChargeGroupIF", "cancelChargeGroups", false);
		logInvalidItems.addItem("TravelPlanServiceCrossReferenceV3", "cancelOrder", false);
		logInvalidItems.addItem("UpdateInventory", "updateInventory", false);
		validateNotInLogs(cancel, logInvalidItems);
	}
	
	@Test(groups = {"api", "regression", "activity", "activityService", "negative"})
	public void cancelledReservation(){
		ScheduledEventReservation res2 = new ActivityEventReservation(this.environment, hh);
		res2.book(ScheduledEventReservation.NOCOMPONENTSNOADDONS);
		res2.cancel();
		Cancel cancel = new Cancel(this.environment,"CancelDiningEvent");
		cancel.setReservationNumber(res2.getConfirmationNumber());
		cancel.sendRequest();
		TestReporter.logAPI(!cancel.getFaultString().contains("Travel Status is invalid  : INVALID RESERVATION STATUS."), cancel.getFaultString() ,cancel);

		LogItems logValidItems = new LogItems();
		logValidItems.addItem("ActivityServiceIF", "cancel", true);
		validateLogs(cancel, logValidItems);
		
		LogItems logInvalidItems = new LogItems();
		logInvalidItems.addItem("AccommodationInventoryRequestComponentServiceIF", "releaseInventory", false);
		logInvalidItems.addItem("ChargeGroupIF", "cancelChargeGroups", false);
		logInvalidItems.addItem("TravelPlanServiceCrossReferenceV3", "cancelOrder", false);
		logInvalidItems.addItem("UpdateInventory", "updateInventory", false);
		logInvalidItems.addItem("FolioServiceIF", "retrieveAccountingTransactions", false);
		logInvalidItems.addItem("TravelPlanServiceCrossReferenceV3SEI", "cancelOrder", false);
		logInvalidItems.addItem("ChargeGroupIF", "getTaxExempt", false);
		logInvalidItems.addItem("PartyIF", "retrievePartyBasicInformation", false);
		logInvalidItems.addItem("PartyIF", "retrieveParty", false);
		logInvalidItems.addItem("AccommodationInventoryRequestComponentServiceIF", "retrieveAssignmentOwner", false);
		logInvalidItems.addItem("ActivityServiceIF", "retrieve", false);
		logInvalidItems.addItem("PackagingService", "getProducts", false);
		logInvalidItems.addItem("PricingService", "priceComponents", false);
		validateNotInLogs(cancel, logInvalidItems);
	}
}
