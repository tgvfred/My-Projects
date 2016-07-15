package com.disney.composite.api.tableServiceDiningService.retrieve;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.tableServiceDiningServicePort.operations.Book;
import com.disney.api.soapServices.tableServiceDiningServicePort.operations.Cancel;
import com.disney.api.soapServices.tableServiceDiningServicePort.operations.Retrieve;
import com.disney.composite.BaseTest;
import com.disney.utils.Regex;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.database.LogItems;
import com.disney.utils.dataFactory.guestFactory.HouseHold;

public class TestRetrieve extends BaseTest{
	// Defining global variables
	protected String TPS_ID = null;
	Book book = null;
	
	@Override
	@BeforeTest(alwaysRun = true)
	@Parameters({ "environment" })
	public void setup(@Optional String environment){
		this.environment = environment;
		hh = new HouseHold(1);
		book = new Book(this.environment, "NoComponentsNoAddOns");
		book.setParty(hh);
		book.sendRequest();
		TPS_ID = book.getTravelPlanSegmentId();
	}
	
	@AfterTest(alwaysRun=true)
	public void teardown(){
		try{
			if(TPS_ID != null)
				if(!TPS_ID.isEmpty()){
					Cancel cancel = new Cancel(environment, "Main");
					cancel.setReservationNumber(TPS_ID);
					cancel.sendRequest();
				}
		}catch(Exception e){}
	}
	
	@Test(groups = {"api", "regression", "dining", "tableServiceDiningService"})
	public void testRetrieve(){
		TestReporter.logScenario("Retrieve");
		Retrieve retrieve = new Retrieve(this.environment, "Main");
		retrieve.setReservationNumber(book.getTravelPlanSegmentId());
		retrieve.sendRequest();
		TestReporter.logAPI(!retrieve.getResponseStatusCode().equals("200"), retrieve.getFaultString(), retrieve);
		TestReporter.logAPI(!Regex.match("[0-9]+", retrieve.getPartyId()), "The Party Id ["+retrieve.getPartyId()+"] was not numeric as expected.",retrieve);
		LogItems logItems = new LogItems();
		
		logItems.addItem("AccommodationInventoryRequestComponentServiceIF", "retrieveAssignmentOwner", false);
		logItems.addItem("TableServiceDiningServiceIF", "retrieve", false);
		logItems.addItem("FolioServiceIF", "retrieveAccountingTransactions", false);
		logItems.addItem("PartyIF", "retrieveParty", false);
		logItems.addItem("PartyIF", "retrievePartyBasicInformation", false);
		validateLogs(retrieve, logItems);
	}
}
