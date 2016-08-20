package com.disney.composite.api.activityModule.activityService.noShow;

import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.activityModule.activityServicePort.operations.NoShow;
import com.disney.api.soapServices.applicationError.ActivityErrorCode;
import com.disney.api.soapServices.applicationError.ApplicationErrorCode;
import com.disney.api.soapServices.core.BaseSoapCommands;
import com.disney.composite.BaseTest;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.database.LogItems;
import com.disney.utils.dataFactory.guestFactory.HouseHold;
import com.disney.utils.dataFactory.staging.bookSEReservation.ActivityEventReservation;
import com.disney.utils.dataFactory.staging.bookSEReservation.ScheduledEventReservation;

public class TestNoShow_Negative extends BaseTest{
	// Defining global variables
	protected String TPS_ID = null;
	protected ScheduledEventReservation res = null;
	
	
	@Override
	@BeforeClass(alwaysRun = true)
	@Parameters({ "environment" })
	public void setup(@Optional String environment){
		this.environment = environment;
		hh = new HouseHold(1);
		hh.primaryGuest().setAge("6");
		res = new ActivityEventReservation(this.environment, hh);
		res.book(ScheduledEventReservation.NOCOMPONENTSNOADDONS);
	}

	@AfterClass(alwaysRun=true)
	private void teardown(){
		try{res.cancel();}
		catch(Exception e){}
	}
	
	@Test(groups = {"api", "regression", "activity", "activityService", "negative"})
	public void testMissingReservationNumber(){
		TestReporter.logScenario("Missing Reservation Number");
		NoShow noShow = new NoShow(environment, "Main");
		noShow.setReservationNumber(BaseSoapCommands.REMOVE_NODE.toString());
		sendRequestAndValidateLogs(noShow, ActivityErrorCode.RECORD_NOT_FOUND_EXCEPTION, "RECORD NOT FOUND : NO RESERVATION FOUND WITH 0");
	}	

	@Test(groups = {"api", "regression", "activity", "activityService", "negative"})
	public void testInvalidReservationNumber(){
		TestReporter.logScenario("Invalid Reservation Number");
		NoShow noShow = new NoShow(environment, "Main");
		noShow.setReservationNumber("123456789011");
		sendRequestAndValidateLogs(noShow, ActivityErrorCode.RECORD_NOT_FOUND_EXCEPTION, "RECORD NOT FOUND : NO RESERVATION FOUND WITH 123456789011");
	}

	@Test(groups = {"api", "regression", "activity", "activityService", "negative"})
	public void testMissingSalesChannel(){
		TestReporter.logScenario("Missing Sales Channel");
		NoShow noShow = new NoShow(environment, "Main");
		noShow.setReservationNumber(res.getConfirmationNumber());		
		noShow.setSalesChannel(BaseSoapCommands.REMOVE_NODE.toString());
		sendRequestAndValidateLogs(noShow, ActivityErrorCode.SALES_CHANNEL_REQUIRED, "Sales Channel is required : null");
	}

	@Test(groups = {"api", "regression", "activity", "activityService", "negative"})
	public void testInvalidSalesChannel(){
		TestReporter.logScenario("Invalid Sales Channel");
		NoShow noShow = new NoShow(environment, "Main");
		noShow.setReservationNumber(res.getConfirmationNumber());		
		noShow.setSalesChannel("Blah");
		sendRequestAndValidateLogs(noShow, ActivityErrorCode.SALES_CHANNEL_REQUIRED, "Sales Channel is required : null");
	}
	
	@Test(groups = {"api", "regression", "activity", "activityService", "negative"})
	public void testMissingCommunicationChannel(){
		TestReporter.logScenario("Missing Communications Channel");
			NoShow noShow = new NoShow(environment, "Main");
			noShow.setReservationNumber(res.getConfirmationNumber());		
			noShow.setCommunicationChannel(BaseSoapCommands.REMOVE_NODE.toString());
			sendRequestAndValidateLogs(noShow, ActivityErrorCode.COMMUNICATION_CHANNEL_REQUIRED, "communication Channel is required : null");
	}

	@Test(groups = {"api", "regression", "activity", "activityService", "negative"})
	public void testInvalidCommunicationChannel(){
		TestReporter.logScenario("Invalid Communications Channel");
			NoShow noShow = new NoShow(environment, "Main");
			noShow.setReservationNumber(res.getConfirmationNumber());		
			noShow.setCommunicationChannel("Blah");
			sendRequestAndValidateLogs(noShow,ActivityErrorCode.COMMUNICATION_CHANNEL_REQUIRED,  "communication Channel is required : null");
	}

	@Test(groups = {"api", "regression", "activity", "activityService", "negative"})
	public void cancelledReservation(){
		TestReporter.logScenario("Cancelled Reservation");
		ScheduledEventReservation res2 = new ActivityEventReservation(this.environment, hh);
		res2.book(ScheduledEventReservation.NOCOMPONENTSNOADDONS);
		res2.cancel();
		NoShow noShow = new NoShow(environment, "Main");
		noShow.setReservationNumber(res2.getConfirmationNumber());
		sendRequestAndValidateLogs(noShow, ActivityErrorCode.INVALID_TRAVEL_STATUS, "Travel Status is invalid  : INVALID RESERVATION STATUS.CANNOT CHANGE THE STATUS TO NO-SHOW!");
	}
	
	@Test(groups = {"api", "regression", "activity", "activityService", "negative"})
	public void arrivedReservation(){
		TestReporter.logScenario("Arrived Reservation");
		ScheduledEventReservation res2 = new ActivityEventReservation(this.environment, hh);
		res2.book(ScheduledEventReservation.NOCOMPONENTSNOADDONS);
		res2.arrived();
		NoShow noShow = new NoShow(environment, "Main");
		noShow.setReservationNumber(res2.getConfirmationNumber());
		sendRequestAndValidateLogs(noShow, ActivityErrorCode.INVALID_TRAVEL_STATUS, " Travel Status is invalid  : INVALID RESERVATION STATUS.");
	}
	
	private void sendRequestAndValidateLogs(NoShow noShow, ApplicationErrorCode error, String faultString){	
		noShow.sendRequest();
		validateApplicationError(noShow, error);
		TestReporter.logAPI(!noShow.getFaultString().contains(faultString), noShow.getFaultString() ,noShow);

		LogItems logItems = new LogItems();
		logItems.addItem("ActivityServiceIF", "noShow", true);
		validateLogs(noShow, logItems);		
		
		LogItems logInvalidItems = new LogItems();
		logInvalidItems.addItem("PricingService", "getCancellationCharges", false);
		logInvalidItems.addItem("TravelPlanServiceCrossReferenceV3", "updateOrder", false);
		logInvalidItems.addItem("TravelPlanServiceCrossReferenceV3SEI", "updateOrder", false);
		logInvalidItems.addItem("UpdateInventory", "updateInventory", false);
		logInvalidItems.addItem("FolioServiceIF", "retrieveAccountingTransactions", false);
		logInvalidItems.addItem("PartyIF", "retrieveParty", false);
		logInvalidItems.addItem("PartyIF", "retrievePartyBasicInformation", false);
		logInvalidItems.addItem("AccommodationInventoryRequestComponentServiceIF", "releaseInventory", false);
		logInvalidItems.addItem("AccommodationInventoryRequestComponentServiceIF", "retrieveAssignmentOwner", false);
		logInvalidItems.addItem("ActivityServiceIF", "retrieve", false);
		logInvalidItems.addItem("PackagingService", "getProducts", false);
		logInvalidItems.addItem("PricingService", "priceComponents", false);
		validateNotInLogs(noShow, logInvalidItems);
		
	}
}