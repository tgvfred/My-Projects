package com.disney.composite.api.diningModule.showDiningService.book;

import org.testng.SkipException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import com.disney.api.BaseTest;
import com.disney.api.soapServices.applicationError.DiningErrorCode;
import com.disney.api.soapServices.core.BaseSoapCommands;
import com.disney.api.soapServices.diningModule.showDiningService.operations.Book;
import com.disney.api.soapServices.diningModule.showDiningService.operations.Cancel;
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
	
	@Test(groups = {"api", "regression", "dining", "showDiningService", "negative", "compensation", "debug"})
	public void TestCompensationFlow_Book_Negative_RIMFail(){
		TestReporter.logScenario("Test Positive Activity Book Compensation Flow");
		book.set(new Book(environment, ScheduledEventReservation.ONECOMPONENTSNOADDONS));
		book.get().setParty(hh);
		book.get().setFreezeIdForError(Randomness.randomAlphaNumeric(36));
		book.get().setValidateInventory(true);
		book.get().sendRequest();
		TestReporter.logAPI(!book.get().getResponse().contains("RELEASE INVENTORY REQUEST IS INVALID"), book.get().getFaultString() ,book.get());
		TestReporter.assertTrue(Integer.parseInt(book.get().getInventoryCountBefore()) == Integer.parseInt(book.get().getInventoryCountAfter()), "Verify the booked inventory count ["+book.get().getInventoryCountAfter()+"] for reservable resource ID ["+book.get().getReservableResourceId()+"] does not increment from the count prior to booking ["+book.get().getInventoryCountBefore()+"]");
	}

	@Test(groups = {"api", "regression", "dining", "showDiningService", "negative", "compensation"})
	public void TestCompensationFlow_Book_Negative_FolioFail(){
		TestReporter.logScenario("Test Positive Activity Book Compensation Flow");
		book.set(new Book(environment, ScheduledEventReservation.NOCOMPONENTSNOADDONS));
		book.get().setParty(hh);
		book.get().addDetailsByFacilityNameAndProductName("Pioneer Hall", "Hoop-Dee-Doo-Cat 2-1st Show");
		book.get().setRequestNodeValueByXPath("/Envelope/Body/book/bookShowDiningRequest/dinnerShowPackage/componentPrices[1]/unitPrices/taxes/revenueType", BaseSoapCommands.REMOVE_NODE.toString());
		book.get().setValidateInventory(true);
		book.get().sendRequest();
		TestReporter.logAPI(!book.get().getFaultString().contains("Invalid input fields"), book.get().getFaultString() ,book.get());
		validateApplicationError(book.get(), DiningErrorCode.FOLIO_MANAGEMENT_SERVICE_FAILURE);
		TestReporter.assertTrue(Integer.parseInt(book.get().getInventoryCountBefore()) == Integer.parseInt(book.get().getInventoryCountAfter()), "Verify the booked inventory count ["+book.get().getInventoryCountAfter()+"] for reservable resource ID ["+book.get().getReservableResourceId()+"] is equal to the count prior to booking ["+book.get().getInventoryCountBefore()+"]");
	}
}
