package com.disney.composite.api.tableServiceDiningService.cancel;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.applicationError.ApplicationErrorCode;
import com.disney.api.soapServices.applicationError.DiningErrorCode;
import com.disney.api.soapServices.core.BaseSoapCommands;
import com.disney.api.soapServices.tableServiceDiningServicePort.operations.Cancel;
import com.disney.composite.BaseTest;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.database.LogItems;
import com.disney.utils.dataFactory.guestFactory.HouseHold;
import com.disney.utils.dataFactory.staging.bookSEReservation.TableServiceDiningReservation;
import com.disney.utils.dataFactory.staging.bookSEReservation.ScheduledEventReservation;

public class TestCancel_Negative  extends BaseTest{
	// Defining global variables
	protected String TPS_ID = null;
	protected ScheduledEventReservation res = null;
	
	@Override
	@BeforeTest(alwaysRun = true)
	@Parameters({ "environment" })
	public void setup(@Optional String environment){
		this.environment = environment;
		hh = new HouseHold(1);
		res = new TableServiceDiningReservation(this.environment, hh);
		res.book(ScheduledEventReservation.NOCOMPONENTSNOADDONS);
	}

	@AfterTest(alwaysRun=true)
	public void teardown(){
		try{
			if(res != null)
				if(!res.getConfirmationNumber().isEmpty()){
					Cancel cancel = new Cancel(this.environment,"Main");
					cancel.setReservationNumber(res.getConfirmationNumber());
					cancel.sendRequest();
				}
		}catch(Exception e){}
	}
	
	@Test(groups = {"api", "regression", "dining", "tableDiningService", "negative"})
	public void missingReservationNumber(){
		TestReporter.logScenario("Missing Reservation");
		Cancel cancel = new Cancel(this.environment,"Main");
		cancel.setReservationNumber(BaseSoapCommands.REMOVE_NODE.toString());
		sendRequestAndValidateLogs(cancel, DiningErrorCode.RECORD_NOT_FOUND_EXCEPTION, "RECORD NOT FOUND : NO RESERVATION FOUND WITH 0");
	}
	

	@Test(groups = {"api", "regression", "dining", "tableDiningService", "negative"})
	public void invalidReservationNumber(){
		TestReporter.logScenario("Invalid Reservation Number");
		Cancel cancel = new Cancel(this.environment,"Main");
		cancel.setReservationNumber("11111");
		sendRequestAndValidateLogs(cancel, DiningErrorCode.RECORD_NOT_FOUND_EXCEPTION, "RECORD NOT FOUND : NO RESERVATION FOUND WITH 11111");
	}
	
	@Test(groups = {"api", "regression", "dining", "tableDiningService", "negative"})
	public void arrivedReservation(){
		TestReporter.logScenario("Arrived Reservation");
		ScheduledEventReservation res2 = new TableServiceDiningReservation(this.environment, hh);
		res2.book(ScheduledEventReservation.NOCOMPONENTSNOADDONS);
		res2.arrived();
		Cancel cancel = new Cancel(this.environment,"Main");
		cancel.setReservationNumber(res2.getConfirmationNumber());
		sendRequestAndValidateLogs(cancel, DiningErrorCode.INVALID_TRAVEL_STATUS, "Travel Status is invalid  : INVALID RESERVATION STATUS.");
	}
	
	@Test(groups = {"api", "regression", "dining", "tableDiningService", "negative"})
	public void noShowReservation(){
		TestReporter.logScenario("No Show Reservation");
		ScheduledEventReservation res2 = new TableServiceDiningReservation(this.environment, hh);
		res2.book(ScheduledEventReservation.NOCOMPONENTSNOADDONS);
		res2.noShow();
		Cancel cancel = new Cancel(this.environment,"Main");
		cancel.setReservationNumber(res2.getConfirmationNumber());
		sendRequestAndValidateLogs(cancel, DiningErrorCode.INVALID_TRAVEL_STATUS, "Travel Status is invalid  : INVALID RESERVATION STATUS.");
	}
	
	@Test(groups = {"api", "regression", "dining", "tableDiningService", "negative"})
	public void cancelledReservation(){
		TestReporter.logScenario("Cancelled Reservation");
		ScheduledEventReservation res2 = new TableServiceDiningReservation(this.environment, hh);
		res2.book(ScheduledEventReservation.NOCOMPONENTSNOADDONS);
		res2.cancel();		
		Cancel cancel = new Cancel(this.environment,"Main");
		cancel.setReservationNumber(res2.getConfirmationNumber());
		sendRequestAndValidateLogs(cancel, DiningErrorCode.INVALID_TRAVEL_STATUS,  "Travel Status is invalid  : INVALID RESERVATION STATUS.");	
	}
	
	private void sendRequestAndValidateLogs(Cancel cancel, ApplicationErrorCode error, String faultString){
		cancel.sendRequest();
		validateApplicationError(cancel, error);
		TestReporter.logAPI(!cancel.getFaultString().contains(faultString), cancel.getFaultString() ,cancel);

		LogItems logValidItems = new LogItems();
		logValidItems.addItem("TableServiceDiningServiceIF", "cancel", true);
		validateLogs(cancel, logValidItems, 10000);
		
		LogItems logInvalidItems = new LogItems();
		logInvalidItems.addItem("AccommodationInventoryRequestComponentServiceIF", "releaseInventory", false);
		logInvalidItems.addItem("ChargeGroupIF", "cancelChargeGroups", false);
		logInvalidItems.addItem("TravelPlanServiceCrossReferenceV3", "cancelOrder", false);
		logInvalidItems.addItem("UpdateInventory", "updateInventory", false);
		logInvalidItems.addItem("TravelPlanServiceCrossReferenceV3SEI", "cancelOrder", false);
		logInvalidItems.addItem("ChargeGroupIF", "cancelChargeGroups", false);
		logInvalidItems.addItem("PartyIF", "retrieveParty", false);
		logInvalidItems.addItem("GuestLinkServiceV1SEI", "createEntitlementReference", false);
		validateNotInLogs(cancel, logInvalidItems);
	}
}
