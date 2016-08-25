package com.disney.composite.api.activityModule.activityService._compensationFlow.arrived;

import org.testng.SkipException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.diningModule.eventDiningService.operations.Cancel;
import com.disney.composite.BaseTest;
import com.disney.utils.dataFactory.guestFactory.HouseHold;
import com.disney.utils.dataFactory.staging.bookSEReservation.EventDiningReservation;
import com.disney.utils.dataFactory.staging.bookSEReservation.ScheduledEventReservation;

public class TestCompensationFlow_Arrived_Negative extends BaseTest{
	private ScheduledEventReservation res;
	
	@Override
	@BeforeMethod(alwaysRun = true)
	@Parameters("environment")
	public void setup(@Optional String environment){
		this.environment = environment;
		hh = new HouseHold(1);
		res = new EventDiningReservation(environment, hh);
		res.book(ScheduledEventReservation.NOCOMPONENTSNOADDONS);
	}
	
	@AfterMethod(alwaysRun=true)
	public void teardown(){
		try{
			Cancel cancel = new Cancel(environment, "CancelDiningEvent");
			cancel.setReservationNumber(res.getConfirmationNumber());
			cancel.sendRequest();
		}catch(Exception e){}
	}
	
	@Test(groups = {"api", "regression", "dining", "eventDiningService", "negative", "compensation"})
	public void TestCompensationFlow_Arrived_Negative_DineFail(){
		throw new SkipException("The testing solution for this scenario has not been determined.");
	}
	
	@Test(groups = {"api", "regression", "dining", "eventDiningService", "negative", "compensation"})
	public void TestCompensationFlow_Arrived_Negative_FolioFail(){
		throw new SkipException("The testing solution for this scenario has not been determined.");
	}
}