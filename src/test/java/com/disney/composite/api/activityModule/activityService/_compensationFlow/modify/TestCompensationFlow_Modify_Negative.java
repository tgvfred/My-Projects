package com.disney.composite.api.activityModule.activityService._compensationFlow.modify;

import org.testng.SkipException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.activityModule.activityServicePort.operations.Book;
import com.disney.api.soapServices.activityModule.activityServicePort.operations.Cancel;
import com.disney.composite.BaseTest;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.guestFactory.HouseHold;
import com.disney.utils.dataFactory.staging.bookSEReservation.ScheduledEventReservation;

public class TestCompensationFlow_Modify_Negative extends BaseTest{
	private ThreadLocal<Book> book = new ThreadLocal<Book>();
	
	@Override
	@BeforeClass(alwaysRun = true)
	@Parameters("environment")
	public void setup(@Optional String environment){
		this.environment = environment;
		hh = new HouseHold(1);
		hh.primaryGuest().setAge("9");
		book.set(new Book(environment, ScheduledEventReservation.NOCOMPONENTSNOADDONS));
		book.get().setParty(hh);
		book.get().sendRequest();
		TestReporter.logAPI(!book.get().getResponseStatusCode().equals("200"), "An error occurred during booking: " + book.get().getFaultString(), book.get());
	}
	
	@AfterMethod(alwaysRun=true)
	public void teardown(){
		try{
			Cancel cancel = new Cancel(environment, "CancelDiningEvent");
			cancel.setReservationNumber(book.get().getTravelPlanSegmentId());
			cancel.sendRequest();
		}catch(Exception e){}
	}

	@Test(groups = {"api", "regression", "activity", "activityService", "negtive", "compensation"})
	public void TestCompensationFlow_Modify_Negative_RIMFail(){
		throw new SkipException("The testing solution for this scenario has not been determined.");
	}

	@Test(groups = {"api", "regression", "activity", "activityService", "negtive", "compensation"})
	public void TestCompensationFlow_Modify_Negative_ActivityFail(){
		throw new SkipException("The testing solution for this scenario has not been determined.");
	}

	@Test(groups = {"api", "regression", "activity", "activityService", "negtive", "compensation"})
	public void TestCompensationFlow_Modify_Negative_FolioFail(){
		throw new SkipException("The testing solution for this scenario has not been determined.");
	}
}