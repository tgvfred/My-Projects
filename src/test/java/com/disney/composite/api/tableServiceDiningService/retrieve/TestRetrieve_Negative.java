package com.disney.composite.api.tableServiceDiningService.retrieve;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.applicationError.DiningErrorCode;
import com.disney.api.soapServices.core.BaseSoapCommands;
import com.disney.api.soapServices.tableServiceDiningServicePort.operations.Retrieve;
import com.disney.composite.BaseTest;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.database.LogItems;
import com.disney.utils.dataFactory.guestFactory.HouseHold;
import com.disney.utils.dataFactory.staging.bookSEReservation.TableServiceDiningReservation;
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
		res = new TableServiceDiningReservation(this.environment, hh);
		res.book(ScheduledEventReservation.NOCOMPONENTSNOADDONS);
	}

	@AfterTest(alwaysRun=true)
	public void teardown(){
		try{
			if(res.getConfirmationNumber() != null)
				if(!res.getConfirmationNumber().isEmpty())
					res.cancel();
		}catch(Exception e){}
	}
	
	@Test(groups = {"api", "regression", "dining", "tableServiceDiningService", "negative"})
	public void missingReservationNumber(){
		TestReporter.logScenario("Missing Reservation");
		Retrieve retrieve = new Retrieve(this.environment, "Main");
		retrieve.setReservationNumber(BaseSoapCommands.REMOVE_NODE.toString());
		retrieve.sendRequest();
		validateApplicationError(retrieve, DiningErrorCode.RECORD_NOT_FOUND_EXCEPTION);
		TestReporter.logAPI(!retrieve.getFaultString().contains("RECORD NOT FOUND : NO RESERVATION FOUND WITH 0"), retrieve.getFaultString() ,retrieve);

		LogItems logValidItems = new LogItems();
		logValidItems.addItem("TableServiceDiningServiceIF", "retrieve", true);
		validateLogs(retrieve, logValidItems);
		
		LogItems logInvalidItems = new LogItems();	
		logInvalidItems.addItem("AccommodationInventoryRequestComponentServiceIF", "retrieveAssignmentOwner", false);
		logInvalidItems.addItem("FolioServiceIF", "retrieveAccountingTransactions", false);
		logInvalidItems.addItem("PartyIF", "retrieveParty", false);
		logInvalidItems.addItem("PartyIF", "retrievePartyBasicInformation", false);
		validateNotInLogs(retrieve, logInvalidItems);
	}
	
	@Test(groups = {"api", "regression", "dining", "tableServiceDiningService", "negative"})
	public void invalidReservationNumber(){
		TestReporter.logScenario("Invalid Reservation");
		Retrieve retrieve = new Retrieve(this.environment, "Main");
		retrieve.setReservationNumber("11111");
		retrieve.sendRequest();
		validateApplicationError(retrieve, DiningErrorCode.RECORD_NOT_FOUND_EXCEPTION);
		TestReporter.logAPI(!retrieve.getFaultString().contains("RECORD NOT FOUND : NO RESERVATION FOUND WITH 11111"), retrieve.getFaultString() ,retrieve);

		LogItems logValidItems = new LogItems();
		logValidItems.addItem("TableServiceDiningServiceIF", "retrieve", true);
		validateLogs(retrieve, logValidItems);
		
		LogItems logInvalidItems = new LogItems();
		logInvalidItems.addItem("AccommodationInventoryRequestComponentServiceIF", "retrieveAssignmentOwner", false);
		logInvalidItems.addItem("FolioServiceIF", "retrieveAccountingTransactions", false);
		logInvalidItems.addItem("PartyIF", "retrieveParty", false);
		logInvalidItems.addItem("PartyIF", "retrievePartyBasicInformation", false);
		validateNotInLogs(retrieve, logInvalidItems);
	}	
}