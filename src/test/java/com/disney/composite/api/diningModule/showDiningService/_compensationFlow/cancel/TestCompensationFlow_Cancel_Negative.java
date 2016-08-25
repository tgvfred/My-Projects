package com.disney.composite.api.diningModule.showDiningService._compensationFlow.cancel;

import org.testng.SkipException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.diningModule.showDiningService.operations.Book;
import com.disney.composite.BaseTest;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.guestFactory.HouseHold;
import com.disney.utils.dataFactory.staging.bookSEReservation.ScheduledEventReservation;

public class TestCompensationFlow_Cancel_Negative extends BaseTest{
	private ThreadLocal<Book> book = new ThreadLocal<Book>();
	private String reservableResourceId;
	private String dateTime;
	
	@Override
	@BeforeMethod(alwaysRun = true)
	@Parameters("environment")
	public void setup(@Optional String environment){
		this.environment = environment;
		hh = new HouseHold(1);
		book.set(new Book(environment, ScheduledEventReservation.ONECOMPONENTSNOADDONS));
		book.get().setParty(hh);
		book.get().sendRequest();
		TestReporter.logAPI(!book.get().getResponseStatusCode().equals("200"), "An error occurred during booking: " + book.get().getFaultString(), book.get());
		reservableResourceId = book.get().getReservableResourceId();
		dateTime = book.get().getDateTime();
	}
	
	@Test(groups = {"api", "regression", "dining", "eventDiningService", "negative", "compensation"})
	public void TestCompensationFlow_Cancel_Negative_RIMFail(){
		throw new SkipException("The testing solution for this scenario has not been determined.");
	}
	@Test(groups = {"api", "regression", "dining", "eventDiningService", "negative", "compensation"})
	public void TestCompensationFlow_Cancel_Negative_DineFail(){
		throw new SkipException("The testing solution for this scenario has not been determined.");
	}
	@Test(groups = {"api", "regression", "dining", "eventDiningService", "negative", "compensation"})
	public void TestCompensationFlow_Cancel_Negative_FolioFail(){
		throw new SkipException("The testing solution for this scenario has not been determined.");
	}
}