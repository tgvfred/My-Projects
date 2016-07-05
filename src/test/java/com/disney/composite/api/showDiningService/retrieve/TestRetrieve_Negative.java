package com.disney.composite.api.showDiningService.retrieve;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.core.BaseSoapCommands;
import com.disney.api.soapServices.showDiningService.operations.Book;
import com.disney.api.soapServices.showDiningService.operations.Cancel;
import com.disney.api.soapServices.showDiningService.operations.Retrieve;
import com.disney.composite.BaseTest;
import com.disney.utils.Randomness;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.database.LogItems;
import com.disney.utils.dataFactory.guestFactory.HouseHold;
import com.disney.utils.dataFactory.staging.bookSEReservation.ScheduledEventReservation;

public class TestRetrieve_Negative extends BaseTest{
	protected ThreadLocal<Book> book = new ThreadLocal<Book>();
	protected ThreadLocal<Retrieve> retrieve = new ThreadLocal<Retrieve>();
	protected ThreadLocal<LogItems> logValidItems = new ThreadLocal<LogItems>();
	protected ThreadLocal<String[]> expectedLogs = new ThreadLocal<String[]>();
	
	@Override
	@BeforeMethod(alwaysRun = true)
	@Parameters({ "environment" })
	public void setup(@Optional String environment){
		this.environment = environment;
		hh = new HouseHold(1);
		book.set(new Book(this.environment, ScheduledEventReservation.ONECOMPONENTSNOADDONS));
		book.get().setParty(hh);
		book.get().sendRequest();
		logValidItems.set(new LogItems());
		retrieve.set(new Retrieve(environment, "RetrieveDiningEvent"));
	}
	
	@AfterMethod
	public void teardown(){
		try{
			Cancel cancel = new Cancel(environment, "CancelDiningEvent");
			cancel.setTravelPlanSegmentId(book.get().getTravelPlanSegmentId());
			cancel.sendRequest();
		}catch(Exception e){}
	}
	
	@Test(groups = {"api", "regression", "dining", "showDiningService", "negative"})
	public void invalidReservationNumber(){
		TestReporter.logScenario("Invalid Reservation Number");
		String number = Randomness.randomNumber(4);
		retrieve.get().setReservationNumber(number);
		sendRequestAndValidateFaultString("RECORD NOT FOUND : NO RESERVATION FOUND WITH "+number);
	}
	@Test(groups = {"api", "regression", "dining", "showDiningService", "negative"})
	public void missingReservationNumber(){
		TestReporter.logScenario("Missing Reservation Number");
		retrieve.get().setReservationNumber(BaseSoapCommands.REMOVE_NODE.toString());
		sendRequestAndValidateFaultString("RECORD NOT FOUND : NO RESERVATION FOUND WITH 0");
	}	

    private void sendRequestAndValidateFaultString(String fault){
    	retrieve.get().sendRequest();
    	TestReporter.logAPI(!retrieve.get().getFaultString().contains(fault), retrieve.get().getFaultString(), retrieve.get());
		logItems();
    }
	
	private void logItems(){
		logValidItems.get().addItem("ShowDiningServiceIF", "retrieve", true);
		validateLogs(retrieve.get(), logValidItems.get());
		
		LogItems logInvalidItems = new LogItems();
		logInvalidItems.addItem("FolioServiceIF", "retrieveAccountingTransactions", false);
		logInvalidItems.addItem("PartyIF", "retrieveParty", false);
		logInvalidItems.addItem("PartyIF", "retrievePartyBasicInformation", false);
		logInvalidItems.addItem("AccommodationInventoryRequestComponentServiceIF", "retrieveAssignmentOwner", false);
		logInvalidItems.addItem("PackagingService", "getProducts", false);
		validateNotInLogs(retrieve.get(), logInvalidItems);
	}
}