package com.disney.composite.api.diningModule.eventDiningService._compensationFlow.validateBooking;

import org.testng.SkipException;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.diningModule.eventDiningService.operations.Book;
import com.disney.composite.BaseTest;

public class TestCompensationFlow_ValidateBooking_Negative extends BaseTest{	
	private Book book;

	@BeforeClass(alwaysRun = true)
	@Parameters({ "environment" })
	public void setup(@Optional String environment) {
		this.environment = environment;
		book = new Book(this.environment, "NoComponentsNoAddOns");
	}

	@Test(groups = {"api", "regression", "dining", "eventDiningService", "negative", "compensation"})
	public void TestCompensationFlow_ValidateBooking_Negative_RIMFail(){
		throw new SkipException("The testing solution for this scenario has not been determined.");
	}

	@Test(groups = {"api", "regression", "dining", "eventDiningService", "negative", "compensation"})
	public void TestCompensationFlow_ValidateBooking_Negative_DineFail(){
		throw new SkipException("The testing solution for this scenario has not been determined.");
	}

	@Test(groups = {"api", "regression", "dining", "eventDiningService", "negative", "compensation"})
	public void TestCompensationFlow_ValidateBooking_Negative_FolioFail(){
		throw new SkipException("The testing solution for this scenario has not been determined.");
	}
}
