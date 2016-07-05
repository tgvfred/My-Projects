package com.disney.composite.api.eventDiningService.modify;

import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.eventDiningService.operations.Modify;
import com.disney.composite.BaseTest;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.database.LogItems;
import com.disney.utils.dataFactory.guestFactory.HouseHold;
import com.disney.utils.dataFactory.staging.bookSEReservation.EventDiningReservation;
import com.disney.utils.dataFactory.staging.bookSEReservation.ScheduledEventReservation;

public class TestModify extends BaseTest{
	// Defining global variables
	protected ScheduledEventReservation res = null;
	
	@BeforeMethod(alwaysRun = true)
	@Parameters({ "environment" })
	public void setup(@Optional String environment){
		this.environment = environment;
		HouseHold hh = new HouseHold(1);
		res = new EventDiningReservation(this.environment, hh);
		res.book(ScheduledEventReservation.NOCOMPONENTSNOADDONS);
	}
	
	@AfterMethod(alwaysRun = true)
	public synchronized void closeSession(ITestResult test) {res.cancel();}

	@Test(groups = {"api", "regression", "dining", "eventDiningService"})
	public void testModify(){
		Modify modify = new Modify(this.environment, "NoComponentsNoAddOns");
		modify.setReservationNumber(res.getConfirmationNumber());
		modify.setTravelPlanId(res.getTravelPlanId());
		modify.setParty(res.party());
		modify.setFacilityId(res.getFacilityId());
		modify.setServiceStartDate(res.getServiceStartDate());
		modify.setServicePeriodId(res.getServicePeriodId());
		modify.setProductId(res.getProductId());
		modify.sendRequest();
		TestReporter.logAPI(!modify.getResponseStatus().equals("SUCCESS"),"The Response status was not SUCCESS as expected", modify);
		

		LogItems logItems = new LogItems();
		logItems.addItem("ChargeGroupIF", "modifyGuestContainerChargeGroup", false);
		logItems.addItem("ChargeGroupIF", "modifyRootChargeGroup", false);
		logItems.addItem("EventDiningServiceIF", "modify", false);
		logItems.addItem("TravelPlanServiceCrossReferenceV3", "updateOrder", false);
		logItems.addItem("TravelPlanServiceCrossReferenceV3SEI", "updateOrder", false);
		validateLogs(modify, logItems);
	}
}
