package com.disney.composite.api.showDiningService.retrieve;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.showDiningService.operations.Book;
import com.disney.api.soapServices.showDiningService.operations.Cancel;
import com.disney.api.soapServices.showDiningService.operations.Retrieve;
import com.disney.composite.BaseTest;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.database.LogItems;
import com.disney.utils.dataFactory.guestFactory.HouseHold;
import com.disney.utils.dataFactory.staging.bookSEReservation.ScheduledEventReservation;

public class TestRetrieve extends BaseTest{
	private Book book;
	protected HouseHold hh = null;
	
	@Override
	@BeforeMethod(alwaysRun=true)
	@Parameters("environment")
	public void setup(@Optional String environment){
		this.environment = environment;
		hh = new HouseHold(1);
	}
	
	@AfterMethod
	public void teardown(){
		if(book != null)
			if(!book.getTravelPlanSegmentId().isEmpty()){
				Cancel cancel = new Cancel(environment, "CancelDiningEvent");
				cancel.setTravelPlanSegmentId(book.getTravelPlanSegmentId());
				cancel.sendRequest();
			}
	}
	
	@Test(groups = {"api", "regression", "dining", "showDiningService", "negative"})
	public void testRetrieve(){
		TestReporter.logStep("Book a show dining reservation.");
		book = new Book(environment, ScheduledEventReservation.ONECOMPONENTSNOADDONS);
		book.setParty(hh);
		book.sendRequest();
		TestReporter.logAPI(!book.getResponseStatusCode().equals("200"), "An error occurred during booking", book);		
		
		TestReporter.logStep("Retrieve a show dining reservation.");
		Retrieve retrieve = new Retrieve(environment, "RetrieveDiningEvent");
		retrieve.setReservationNumber(book.getTravelPlanSegmentId());
		retrieve.sendRequest();
		TestReporter.logAPI(!retrieve.getResponseStatusCode().equals("200"), "An error occurred retrieving an show dining service reservation", retrieve);
		TestReporter.assertEquals(hh.getAllGuests().size(), retrieve.getNumberOfGuests(), "The number of guests ["+retrieve.getNumberOfGuests()+"] did not match the expected number of guests ["+hh.getAllGuests().size()+"].");
		logItems(retrieve);
	}
	
	private void logItems(Retrieve retrieve){
		LogItems logValidItems = new LogItems();
		logValidItems.addItem("ShowDiningServiceIF", "retrieve", true);
		logValidItems.addItem("FolioServiceIF", "retrieveAccountingTransactions", false);
		logValidItems.addItem("PartyIF", "retrieveParty", false);
		logValidItems.addItem("PartyIF", "retrievePartyBasicInformation", false);
		logValidItems.addItem("AccommodationInventoryRequestComponentServiceIF", "retrieveAssignmentOwner", false);
		logValidItems.addItem("PackagingService", "getProducts", false);
		validateLogs(retrieve, logValidItems);
	}
}