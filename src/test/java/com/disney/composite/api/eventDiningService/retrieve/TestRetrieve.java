package com.disney.composite.api.eventDiningService.retrieve;

import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.eventDiningService.operations.Book;
import com.disney.api.soapServices.eventDiningService.operations.Retrieve;
import com.disney.composite.BaseTest;
import com.disney.utils.Regex;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.database.LogItems;
import com.disney.utils.dataFactory.guestFactory.HouseHold;
import com.disney.utils.dataFactory.staging.bookSEReservation.EventDiningReservation;
import com.disney.utils.dataFactory.staging.bookSEReservation.ScheduledEventReservation;

public class TestRetrieve extends BaseTest{
	// Defining global variables
	protected String TPS_ID = null;
	Book book = null;
	
	@Override
	@BeforeTest(alwaysRun = true)
	@Parameters({ "environment" })
	public void setup(@Optional String environment){
		this.environment = "Bashful";
		hh = new HouseHold(1);
		book = new Book(this.environment, "NoComponentsNoAddOns");
		book.setParty(hh);
		book.sendRequest();
	}
	@Test(groups = {"api", "regression", "dining", "eventDiningService"})
	public void testRetrieve(){
		Retrieve retrieve = new Retrieve(this.environment, "RetrieveDiningEvent");
		retrieve.setReservationNumber(book.getTravelPlanSegmentId());
		retrieve.sendRequest();
		TestReporter.logAPI(!retrieve.getResponseStatusCode().equals("200"), retrieve.getFaultString(), retrieve);
		TestReporter.logAPI(!Regex.match("[0-9]+", retrieve.getPartyId()), "The Party Id ["+retrieve.getPartyId()+"] was not numeric as expected.",retrieve);
		LogItems logItems = new LogItems();
		
		logItems.addItem("AccommodationInventoryRequestComponentServiceIF", "retrieveAssignmentOwner", false);
		logItems.addItem("EventDiningServiceIF", "retrieve", false);
		logItems.addItem("FolioServiceIF", "retrieveAccountingTransactions", false);
		logItems.addItem("PartyIF", "retrieveParty", false);
		logItems.addItem("PackagingService", "getProducts", false);
		logItems.addItem("PartyIF", "retrievePartyBasicInformation", false);
		logItems.addItem("PricingService", "priceComponents", false);
		validateLogs(retrieve, logItems);
	}
}
