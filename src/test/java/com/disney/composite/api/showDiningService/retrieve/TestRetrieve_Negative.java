package com.disney.composite.api.showDiningService.retrieve;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.core.BaseSoapCommands;
import com.disney.api.soapServices.showDiningService.operations.Cancel;
import com.disney.api.soapServices.showDiningService.operations.Retrieve;
import com.disney.composite.BaseTest;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.database.LogItems;
import com.disney.utils.dataFactory.guestFactory.HouseHold;

public class TestRetrieve_Negative extends BaseTest{
	protected ThreadLocal<LogItems> logValidItems = new ThreadLocal<LogItems>();
	protected ThreadLocal<String[]> expectedLogs = new ThreadLocal<String[]>();
	protected ThreadLocal<String> TPS_ID = new ThreadLocal<String>();
	
	@Override
	@BeforeMethod(alwaysRun = true)
	@Parameters({ "environment" })
	public void setup(@Optional String environment){
		this.environment = environment;
		hh = new HouseHold(1);		
		logValidItems.set(new LogItems());
	}
	
	@AfterMethod
	public void teardown(){
		try{
			Cancel cancel = new Cancel(environment, "CancelDiningEvent");
			cancel.setTravelPlanSegmentId(TPS_ID.get());
			cancel.sendRequest();
		}catch(Exception e){}
	}
	
	@Test(groups = {"api", "regression", "dining", "showDiningService", "negative"})
	public void invalidReservationNumber(){
		TestReporter.logScenario("Invalid Reservation Number");
		String number = "1234";
		Retrieve retrieve = new Retrieve(environment, "RetrieveDiningEvent");
		retrieve.setReservationNumber(number);
		sendRequestAndValidateFaultString("RECORD NOT FOUND : NO RESERVATION FOUND WITH "+number, retrieve);
	}
	@Test(groups = {"api", "regression", "dining", "showDiningService", "negative"})
	public void missingReservationNumber(){
		TestReporter.logScenario("Missing Reservation Number");
		Retrieve retrieve = new Retrieve(environment, "RetrieveDiningEvent");
		retrieve.setReservationNumber(BaseSoapCommands.REMOVE_NODE.toString());
		sendRequestAndValidateFaultString("RECORD NOT FOUND : NO RESERVATION FOUND WITH 0", retrieve);
	}
	
    private void sendRequestAndValidateFaultString(String fault, Retrieve retrieve){
    	retrieve.sendRequest();
    	TestReporter.logAPI(!retrieve.getFaultString().contains(fault), retrieve.getFaultString(), retrieve);
		logItems(retrieve);
    }
	
	private void logItems(Retrieve retrieve){
		logValidItems.get().addItem("ShowDiningServiceIF", "retrieve", true);
		validateLogs(retrieve, logValidItems.get(), 10000);
		
		LogItems logInvalidItems = new LogItems();
		logInvalidItems.addItem("FolioServiceIF", "retrieveAccountingTransactions", false);
		logInvalidItems.addItem("PartyIF", "retrieveParty", false);
		logInvalidItems.addItem("PartyIF", "retrievePartyBasicInformation", false);
		logInvalidItems.addItem("AccommodationInventoryRequestComponentServiceIF", "retrieveAssignmentOwner", false);
		logInvalidItems.addItem("PackagingService", "getProducts", false);
		validateNotInLogs(retrieve, logInvalidItems);
	}
}