package com.disney.composite.api.eventDiningService.retrieve;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.core.BaseSoapCommands;
import com.disney.api.soapServices.eventDiningService.operations.Arrived;
import com.disney.api.soapServices.eventDiningService.operations.Retrieve;
import com.disney.composite.BaseTest;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.database.LogItems;
import com.disney.utils.dataFactory.guestFactory.HouseHold;
import com.disney.utils.dataFactory.staging.bookSEReservation.EventDiningReservation;
import com.disney.utils.dataFactory.staging.bookSEReservation.ScheduledEventReservation;

public class TestRetrieve_Negative  extends BaseTest{
	// Defining global variables
	protected String TPS_ID = null;
	protected ScheduledEventReservation res = null;
	
	@Override
	@BeforeTest(alwaysRun = true)
	@Parameters({ "environment" })
	public void setup(@Optional String environment){
		this.environment = environment;
		hh = new HouseHold(1);
		res = new EventDiningReservation(this.environment, hh);
		res.book(ScheduledEventReservation.NOCOMPONENTSNOADDONS);
	}

	@Test(groups = {"api", "regression", "dining", "eventDiningService", "negative"})
	public void missingReservationNumber(){
		Retrieve retrieve = new Retrieve(this.environment, "RetrieveDiningEvent");
		retrieve.setReservationNumber(BaseSoapCommands.REMOVE_NODE.toString());
		retrieve.sendRequest();
		TestReporter.logAPI(!retrieve.getFaultString().contains("RECORD NOT FOUND : NO RESERVATION FOUND WITH 0"), retrieve.getFaultString() ,retrieve);

		LogItems logValidItems = new LogItems();
		logValidItems.addItem("EventDiningServiceIF", "retrieve", true);
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
	
	@Test(groups = {"api", "regression", "dining", "eventDiningService", "negative"})
	public void invalidReservationNumber(){
		Retrieve retrieve = new Retrieve(this.environment, "RetrieveDiningEvent");
		retrieve.setReservationNumber("11111");
		retrieve.sendRequest();
		TestReporter.logAPI(!retrieve.getFaultString().contains("RECORD NOT FOUND : NO RESERVATION FOUND WITH 11111"), retrieve.getFaultString() ,retrieve);

		LogItems logValidItems = new LogItems();
		logValidItems.addItem("EventDiningServiceIF", "retrieve", true);
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
