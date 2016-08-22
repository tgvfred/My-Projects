package com.disney.composite.api.diningModule.eventDiningService._compensationFlow.book;

import org.testng.SkipException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import com.disney.api.soapServices.diningModule.eventDiningService.operations.Book;
import com.disney.api.soapServices.diningModule.eventDiningService.operations.Cancel;
import com.disney.composite.BaseTest;
import com.disney.utils.Randomness;
import com.disney.utils.TestReporter;

public class TestCompensationFlow_Book_Negative extends BaseTest{
	private Book book;
	
	@AfterMethod(alwaysRun=true)
	public void teardown(){
		try{
			Cancel cancel = new Cancel(environment, "CancelDiningEvent");
			cancel.setReservationNumber(book.getTravelPlanSegmentId());
			cancel.sendRequest();
		}catch(Exception e){}
	}
	
	@Test(groups = {"api", "regression", "activity", "activityService", "negative", "compensation"})
	public void testRimFailure(){
		TestReporter.logScenario("Test Positive Activity Book Compensation Flow");
		book = new Book(environment, "NoComponentsNoAddOns");
		book.setParty(hh);
		book.setFreezeIdForError(Randomness.randomAlphaNumeric(36));
		book.sendRequest();
		TestReporter.logAPI(!book.getResponse().contains("RELEASE INVENTORY REQUEST IS INVALID"), book.getFaultString() ,book);
		TestReporter.assertTrue(Integer.parseInt(book.getInventoryCountBefore()) == Integer.parseInt(book.getInventoryCountAfter()), "Verify the booked inventory count ["+book.getInventoryCountAfter()+"] for reservable resource ID ["+book.getReservableResourceId()+"] does not increment from the count prior to booking ["+book.getInventoryCountBefore()+"]");
		
//		LogItems logItems = new LogItems();
//		logItems.addItem("EventDiningServiceIF", "book", true);
//		logItems.addItem("AccommodationInventoryRequestComponentServiceIF", "createInventory", true);
//		validateLogs(book, logItems, 5000);
//		// Validate records ate not in the logs
//		logItems = new LogItems();
//		logItems.addItem("ChargeGroupIF", "createChargeGroupAndPostCharges", false);
//		validateNotInLogs(book, logItems, 5000);
	}
	@Test(groups = {"api", "regression", "activity", "activityService", "negative", "compensation"})
	public void testDineFailure(){
		throw new SkipException("The testing solution for this scenario has not been determined.");
		
//		LogItems logItems = new LogItems();
//		logItems.addItem("EventDiningServiceIF", "book", true);
//		logItems.addItem("AccommodationInventoryRequestComponentServiceIF", "createInventory", true);
//		logItems.addItem("AccommodationInventoryRequestComponentServiceIF", "releaseInventory", true);
//		validateLogs(book, logItems, 5000);
//		// Validate records ate not in the logs
//		logItems = new LogItems();
//		logItems.addItem("ChargeGroupIF", "createChargeGroupAndPostCharges", false);
//		validateNotInLogs(book, logItems, 5000);
	}
	@Test(groups = {"api", "regression", "activity", "activityService", "negative", "compensation"})
	public void testFolioFailure(){
		throw new SkipException("The testing solution for this scenario has not been determined.");
		
//		LogItems logItems = new LogItems();
//		logItems.addItem("EventDiningServiceIF", "book", true);
//		logItems.addItem("AccommodationInventoryRequestComponentServiceIF", "createInventory", true);
//		logItems.addItem("ChargeGroupIF", "createChargeGroupAndPostCharges", true);
//		logItems.addItem("AccommodationInventoryRequestComponentServiceIF", "releaseInventory", true);
//		validateLogs(book, logItems, 5000);
	}
}
