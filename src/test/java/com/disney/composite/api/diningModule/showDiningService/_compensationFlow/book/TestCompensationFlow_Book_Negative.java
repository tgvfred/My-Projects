package com.disney.composite.api.diningModule.showDiningService._compensationFlow.book;

import org.testng.SkipException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import com.disney.api.soapServices.diningModule.showDiningService.operations.Book;
import com.disney.api.soapServices.diningModule.showDiningService.operations.Cancel;
import com.disney.composite.BaseTest;
import com.disney.utils.Randomness;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.staging.bookSEReservation.ScheduledEventReservation;

public class TestCompensationFlow_Book_Negative extends BaseTest{
	private ThreadLocal<Book> book = new ThreadLocal<Book>();
	
	@AfterMethod(alwaysRun=true)
	public void teardown(){
		try{
			Cancel cancel = new Cancel(environment, "CancelDiningEvent");
			cancel.setTravelPlanSegmentId(book.get().getTravelPlanSegmentId());
			cancel.sendRequest();
		}catch(Exception e){}
	}
	
	@Test(groups = {"api", "regression", "activity", "activityService", "negative", "compensation"})
	public void TestCompensationFlow_Book_Negative_RIMFail(){
		TestReporter.setDebugLevel(1);
		TestReporter.logScenario("Test Positive Activity Book Compensation Flow");
		book.set(new Book(environment, ScheduledEventReservation.ONECOMPONENTSNOADDONS));
		book.get().setParty(hh);
		book.get().setFreezeIdForError(Randomness.randomAlphaNumeric(36));
		book.get().sendRequest();
		TestReporter.logAPI(!book.get().getResponse().contains("RELEASE INVENTORY REQUEST IS INVALID"), book.get().getFaultString() ,book.get());
		TestReporter.assertTrue(Integer.parseInt(book.get().getInventoryCountBefore()) == Integer.parseInt(book.get().getInventoryCountAfter()), "Verify the booked inventory count ["+book.get().getInventoryCountAfter()+"] for reservable resource ID ["+book.get().getReservableResourceId()+"] does not increment from the count prior to booking ["+book.get().getInventoryCountBefore()+"]");
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
