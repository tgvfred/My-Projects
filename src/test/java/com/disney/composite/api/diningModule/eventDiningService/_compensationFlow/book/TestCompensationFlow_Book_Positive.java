package com.disney.composite.api.diningModule.eventDiningService._compensationFlow.book;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import com.disney.api.soapServices.diningModule.eventDiningService.operations.Book;
import com.disney.api.soapServices.diningModule.eventDiningService.operations.Cancel;
import com.disney.composite.BaseTest;
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
		TestReporter.logAPI(!book.getResponseStatusCode().contains("200"), book.getFaultString() ,book);
		TestReporter.assertTrue(Integer.parseInt(book.getInventoryCountBefore()) < Integer.parseInt(book.getInventoryCountAfter()), "Verify the booked inventory count ["+book.getInventoryCountAfter()+"] for reservable resource ID ["+book.getReservableResourceId()+"] increments from the count prior to booking ["+book.getInventoryCountBefore()+"]");
		
		LogItems logItems = new LogItems();
		logItems.addItem("EventDiningServiceIF", "book", false);
//		validateLogs(book, logItems, 5000);
	}
}