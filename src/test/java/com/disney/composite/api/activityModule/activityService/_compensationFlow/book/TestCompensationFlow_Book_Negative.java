package com.disney.composite.api.activityModule.activityService._compensationFlow.book;

import org.testng.SkipException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import com.disney.api.BaseTest;
import com.disney.api.soapServices.activityModule.activityServicePort.operations.Book;
import com.disney.api.soapServices.activityModule.activityServicePort.operations.Cancel;
import com.disney.utils.Randomness;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.database.Database;
import com.disney.utils.dataFactory.database.Recordset;
import com.disney.utils.dataFactory.database.databaseImpl.OracleDatabase;
import com.disney.utils.dataFactory.database.sqlStorage.Dreams;

public class TestCompensationFlow_Book_Negative extends BaseTest{
	private ThreadLocal<Book> book = new ThreadLocal<Book>();
	
	@AfterMethod(alwaysRun=true)
	public void teardown(){
		try{
			Cancel cancel = new Cancel(environment, "CancelDiningEvent");
			cancel.setReservationNumber(book.get().getTravelPlanSegmentId());
			cancel.sendRequest();
		}catch(Exception e){}
	}
	
	@Test(groups = {"api", "regression", "activity", "activityService", "negative", "compensation"})
	public void TestCompensationFlow_Book_Negative_RIMFail(){
		hh.primaryGuest().setAge("9");
		TestReporter.logScenario("Test Positive Activity Book Compensation Flow");
		book.set(new Book(environment, "NoComponentsNoAddOns"));
		book.get().setParty(hh);
		book.get().setFreezeIdForError(Randomness.randomAlphaNumeric(36));
		book.get().sendRequest();
		TestReporter.logAPI(!book.get().getResponse().contains("RELEASE INVENTORY REQUEST IS INVALID"), book.get().getFaultString() ,book.get());
		TestReporter.assertTrue(Integer.parseInt(book.get().getInventoryCountBefore()) == Integer.parseInt(book.get().getInventoryCountAfter()), "Verify the booked inventory count ["+book.get().getInventoryCountAfter()+"] for reservable resource ID ["+book.get().getReservableResourceId()+"] remains the same as the count prior to booking ["+book.get().getInventoryCountBefore()+"]");
	}
	@Test(groups = {"api", "regression", "activity", "activityService", "negative", "compensation"})
	public void TestCompensationFlow_Book_Negative_ActivityFail(){
		hh.primaryGuest().setAge("9");
		throw new SkipException("The testing solution for this scenario has not been determined.");
	}
	@Test(groups = {"api", "regression", "activity", "activityService", "negative", "compensation"})
	public void TestCompensationFlow_Book_Negative_FolioFail(){
		hh.primaryGuest().setAge("9");
		throw new SkipException("The testing solution for this scenario has not been determined.");
//		hh.primaryGuest().setAge("9");
//		TestReporter.logScenario("Test Positive Activity Book Compensation Flow");
//		book = new Book(environment, "NoComponentsNoAddOns");
//		book.setParty(hh);
//		book.addDetailsByFacilityNameAndProductName("Bibbidi Bobbidi Boutique - Magic Kingdom", "C E - Bibbidi Bobbidi Boutique - MK");
////		book.setRequestNodeValueByXPath("/Envelope/Body/book/bookActivityComponentRequest/activity/componentPrices/unitPrices/price", BaseSoapCommands.REMOVE_NODE.toString());
////		book.setRequestNodeValueByXPath("/Envelope/Body/book/bookActivityComponentRequest/activity", "fx:addnode;node:componentPrices");
////		book.setRequestNodeValueByXPath("/Envelope/Body/book/bookActivityComponentRequest/activity/componentPrices", "fx:addnode;node:invalidNode");
//		book.sendRequest();
//		TestReporter.logAPI(!book.getResponse().contains("RELEASE INVENTORY REQUEST IS INVALID"), book.getFaultString() ,book);
//		TestReporter.assertTrue(Integer.parseInt(book.getInventoryCountBefore()) == Integer.parseInt(book.getInventoryCountAfter()), "Verify the booked inventory count ["+book.getInventoryCountAfter()+"] for reservable resource ID ["+book.getReservableResourceId()+"] remains the same as the count prior to booking ["+book.getInventoryCountBefore()+"]");
	}
}
