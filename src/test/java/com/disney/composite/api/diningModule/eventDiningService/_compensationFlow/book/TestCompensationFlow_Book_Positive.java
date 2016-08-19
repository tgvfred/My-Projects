package com.disney.composite.api.diningModule.eventDiningService._compensationFlow.book;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import com.disney.api.soapServices.diningModule.eventDiningService.operations.Book;
import com.disney.api.soapServices.diningModule.eventDiningService.operations.Cancel;
import com.disney.composite.BaseTest;
import com.disney.test.utils.Sleeper;
import com.disney.utils.Randomness;
import com.disney.utils.Regex;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.database.LogItems;

public class TestCompensationFlow_Book_Positive extends BaseTest{
	private Book book;
	
	@AfterMethod(alwaysRun=true)
	public void teardown(){
		try{
			Cancel cancel = new Cancel(environment, "CancelDiningEvent");
			cancel.setReservationNumber(book.getTravelPlanSegmentId());
			cancel.sendRequest();
		}catch(Exception e){}
	}
	
	@Test(groups = {"api", "regression", "dining", "eventDiningService", "compensation"})
	public void testCompensationFlow_Book_Positive(){
		TestReporter.logScenario("Test Positive Activity Book Compensation Flow");
		book = new Book(environment, "NoComponentsNoAddOns");
		book.setParty(hh);
		book.sendRequest();
		if(book.getResponse().toLowerCase().contains("unique constraint")){
			Sleeper.sleep(Randomness.randomNumberBetween(1, 5) * 1000);
			book.sendRequest();
		}
		TestReporter.logAPI(!book.getResponseStatusCode().contains("200"), book.getFaultString() ,book);
		TestReporter.assertTrue(Regex.match("[0-9]+", book.getTravelPlanId()), "The travel plan ID ["+book.getTravelPlanId()+"] is not numeric as expected.");
		TestReporter.assertTrue(Regex.match("[0-9]+", book.getTravelPlanSegmentId()), "The reservation number ["+book.getTravelPlanSegmentId()+"] is not numeric as expected.");
		
		LogItems logItems = new LogItems();
		logItems.addItem("EventDiningServiceIF", "book", false);
		logItems.addItem("AccommodationInventoryRequestComponentServiceIF", "createInventory", false);
		logItems.addItem("PartyIF", "createAndRetrieveParty", false);	
		logItems.addItem("UpdateInventory", "updateInventory", false);	
		if(environment.toLowerCase().contains("_cm")){
			logItems.addItem("ChargeGroupIF", "createChargeGroupsAndPostCharges", false);
			logItems.addItem("FolioServiceIF", "retrieveAccountingTransactions", false);
			logItems.addItem("AccommodationInventoryRequestComponentServiceIF", "retrieveAssignmentOwner", false);
		}else if(environment.equals("stage")){
			logItems.addItem("ChargeGroupIF", "createChargeGroupAndPostCharges", false);
			logItems.addItem("TravelPlanServiceV3", "create", false);
		}
		validateLogs(book, logItems, 5000);
	}
}