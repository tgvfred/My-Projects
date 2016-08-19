package com.disney.composite.api.diningModule.eventDiningService._compensationFlow.book;

import org.testng.annotations.Test;

import com.disney.api.soapServices.diningModule.eventDiningService.operations.Book;
import com.disney.composite.BaseTest;
import com.disney.test.utils.Sleeper;
import com.disney.utils.Randomness;
import com.disney.utils.Regex;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.database.LogItems;
import com.disney.utils.dataFactory.guestFactory.HouseHold;

public class TestCompensationFlow_Book_Positive extends BaseTest{
	
	@Test(groups = {"api", "regression", "activity", "activityService"})
	public void testCompensationFlow_Positive(){
		
	}
	
	@Test(groups = {"api", "regression", "dining", "eventDiningService"})
	public void testBook(){
		Book book = new Book(environment, "NoComponentsNoAddOns");
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
		logItems.addItem("AccommodationInventoryRequestComponentServiceIF", "createInventory", false);
		logItems.addItem("ChargeGroupIF", "createChargeGroupAndPostCharges", false);
		logItems.addItem("PartyIF", "createAndRetrieveParty", false);	
		logItems.addItem("TravelPlanServiceV3", "create", false);
		logItems.addItem("UpdateInventory", "updateInventory", false);			
		validateLogs(book, logItems);
	}
}
