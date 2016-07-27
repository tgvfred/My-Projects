package com.disney.composite.api.activityService.retrieve;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.core.BaseSoapCommands;
import com.disney.api.soapServices.activityServicePort.operations.Retrieve;
import com.disney.api.soapServices.applicationError.ActivityErrorCode;
import com.disney.api.soapServices.applicationError.ApplicationErrorCode;
import com.disney.composite.BaseTest;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.database.LogItems;
import com.disney.utils.dataFactory.guestFactory.HouseHold;
import com.disney.utils.dataFactory.staging.bookSEReservation.ActivityEventReservation;
import com.disney.utils.dataFactory.staging.bookSEReservation.ScheduledEventReservation;

public class TestRetrieve_Negative  extends BaseTest{
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

	@AfterTest(alwaysRun=true)
	public void teardown(){
		if(res != null)
			if(res.getConfirmationNumber() != null)
				if(!res.getConfirmationNumber().isEmpty())
					res.cancel();
	}
	
	@Test(groups = {"api", "regression", "activity", "activityService", "negative"})
	public void missingReservationNumber(){
		TestReporter.logScenario("Missing Reservation Number");
		Retrieve retrieve = new Retrieve(this.environment, "RetrieveDiningEvent");
		retrieve.setReservationNumber(BaseSoapCommands.REMOVE_NODE.toString());
		sendRequestAndValidateLogs(retrieve, ActivityErrorCode.RECORD_NOT_FOUND_EXCEPTION, "RECORD NOT FOUND : NO RESERVATION FOUND WITH 0");
	}
	
	@Test(groups = {"api", "regression", "activity", "activityService", "negative"})
	public void invalidReservationNumber(){
		TestReporter.logScenario("Invalid Reservation Number");
		Retrieve retrieve = new Retrieve(this.environment, "RetrieveDiningEvent");
		retrieve.setReservationNumber("11111");
		sendRequestAndValidateLogs(retrieve, ActivityErrorCode.RECORD_NOT_FOUND_EXCEPTION, "RECORD NOT FOUND : NO RESERVATION FOUND WITH 11111");
	}
	
	private void sendRequestAndValidateLogs(Retrieve retrieve, ApplicationErrorCode error, String faultString){
		retrieve.sendRequest();
		validateApplicationError(retrieve, error);
		TestReporter.logAPI(!retrieve.getFaultString().contains(faultString), retrieve.getFaultString() ,retrieve);

		LogItems logValidItems = new LogItems();
		logValidItems.addItem("ActivityServiceIF", "retrieve", true);
		validateLogs(retrieve, logValidItems);
		
		LogItems logInvalidItems = new LogItems();
		logInvalidItems.addItem("AccommodationInventoryRequestComponentServiceIF", "retrieveAssignmentOwner", false);
		logInvalidItems.addItem("FolioServiceIF", "retrieveAccountingTransactions", false);
		logInvalidItems.addItem("PartyIF", "retrieveParty", false);
		logInvalidItems.addItem("PackagingService", "getProducts", false);
		logInvalidItems.addItem("PartyIF", "retrievePartyBasicInformation", false);
		logInvalidItems.addItem("PricingService", "priceComponents", false);
		validateNotInLogs(retrieve, logInvalidItems);
	}
}