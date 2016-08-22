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
	public void TestCompensationFlow_Book_Negative_RIMFail(){
		TestReporter.logScenario("Test Positive Activity Book Compensation Flow");
		book = new Book(environment, "NoComponentsNoAddOns");
		book.setParty(hh);
		book.setFreezeIdForError(Randomness.randomAlphaNumeric(36));
		book.sendRequest();
		TestReporter.logAPI(!book.getResponse().contains("RELEASE INVENTORY REQUEST IS INVALID"), book.getFaultString() ,book);
		TestReporter.assertTrue(Integer.parseInt(book.getInventoryCountBefore()) == Integer.parseInt(book.getInventoryCountAfter()), "Verify the booked inventory count ["+book.getInventoryCountAfter()+"] for reservable resource ID ["+book.getReservableResourceId()+"] does not increment from the count prior to booking ["+book.getInventoryCountBefore()+"]");
	}
	@Test(groups = {"api", "regression", "activity", "activityService", "negative", "compensation"})
	public void TestCompensationFlow_Book_Negative_DineFail(){
		throw new SkipException("The testing solution for this scenario has not been determined.");
	}
	@Test(groups = {"api", "regression", "activity", "activityService", "negative", "compensation"})
	public void TestCompensationFlow_Book_Negative_FolioFail(){
		throw new SkipException("The testing solution for this scenario has not been determined.");
	}
}
