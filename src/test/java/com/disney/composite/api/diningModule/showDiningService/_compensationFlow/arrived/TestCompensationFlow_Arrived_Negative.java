package com.disney.composite.api.diningModule.showDiningService._compensationFlow.arrived;

import org.testng.SkipException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.composite.BaseTest;
import com.disney.utils.dataFactory.guestFactory.HouseHold;
import com.disney.utils.dataFactory.staging.bookSEReservation.ScheduledEventReservation;
import com.disney.utils.dataFactory.staging.bookSEReservation.ShowDiningReservation;

public class TestCompensationFlow_Arrived_Negative extends BaseTest{
	private ScheduledEventReservation res;
	
	@Override
	@BeforeClass(alwaysRun = true)
	@Parameters("environment")
	public void setup(@Optional String environment){
		this.environment = environment;
		hh = new HouseHold(1);
		res = new ShowDiningReservation(environment, hh);
		res.book(ScheduledEventReservation.ONECOMPONENTSNOADDONS);
	}
	
	@AfterClass(alwaysRun=true)
	public void teardown(){
		try{res.cancel();}
		catch(Exception e){}
	}
	
	@Test(groups = {"api", "regression", "dining", "showDiningService", "negative", "compensation"})
	public void TestCompensationFlow_Arrived_Negative_DineFail(){
		throw new SkipException("The testing solution for this scenario has not been determined.");
	}
	
	@Test(groups = {"api", "regression", "dining", "showDiningService", "negative", "compensation"})
	public void TestCompensationFlow_Arrived_Negative_FolioFail(){
		throw new SkipException("The testing solution for this scenario has not been determined.");
	}
}