package com.disney.composite.api.diningModule.eventDiningService.arrived;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.BaseTest;
import com.disney.api.soapServices.diningModule.eventDiningService.operations.Arrived;
import com.disney.api.soapServices.applicationError.DiningErrorCode;
import com.disney.api.soapServices.core.BaseSoapCommands;
import com.disney.utils.Randomness;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.database.LogItems;
import com.disney.utils.dataFactory.guestFactory.HouseHold;
import com.disney.utils.dataFactory.staging.bookSEReservation.EventDiningReservation;
import com.disney.utils.dataFactory.staging.bookSEReservation.ScheduledEventReservation;

public class TestArrived_Negative  extends BaseTest{
	// Defining global variables
	protected String TPS_ID = null;
	protected ScheduledEventReservation res = null;
	
	@Override
	@BeforeClass(alwaysRun = true)
	@Parameters({ "environment" })
	public void setup(@Optional String environment){
		this.environment = environment;
		hh = new HouseHold(1);
		res = new EventDiningReservation(this.environment, hh);
		res.setServiceStartDate(Randomness.generateCurrentXMLDatetime(Randomness.randomNumberBetween(15, 45)));
		res.book(ScheduledEventReservation.NOCOMPONENTSNOADDONS);
	}
	
	@AfterClass(alwaysRun=true)
	public void tearDown(){
		try{res.cancel();}
		catch(Exception e){}
	}

	@Test(groups = {"api", "regression", "dining", "eventDiningService", "negative"})
	public void missingReservationNumber(){
		Arrived arrived = new Arrived(this.environment,"Main");
		arrived.setReservationNumber(BaseSoapCommands.REMOVE_NODE.toString());
		arrived.sendRequest();
		
		validateApplicationError(arrived, DiningErrorCode.RECORD_NOT_FOUND_EXCEPTION);
		TestReporter.logAPI(!arrived.getFaultString().contains("RECORD NOT FOUND : NO RESERVATION FOUND WITH 0"), arrived.getFaultString() ,arrived);

		LogItems logValidItems = new LogItems();
		logValidItems.addItem("EventDiningServiceIF", "arrived", true);
		validateLogs(arrived, logValidItems);
		
		LogItems logInvalidItems = new LogItems();
		logInvalidItems.addItem("ChargeGroupIF", "checkIn", false);
		logInvalidItems.addItem("TravelPlanServiceCrossReferenceV3", "updateOrder", false);
		logInvalidItems.addItem("TravelPlanServiceCrossReferenceV3SEI", "updateOrder", false);
		validateNotInLogs(arrived, logInvalidItems);
	}
	

	@Test(groups = {"api", "regression", "dining", "eventDiningService", "negative"})
	public void invalidReservationNumber(){
		Arrived arrived = new Arrived(this.environment,"Main");
		arrived.setReservationNumber("11111");
		arrived.sendRequest();
		
		validateApplicationError(arrived, DiningErrorCode.RECORD_NOT_FOUND_EXCEPTION);
		TestReporter.logAPI(!arrived.getFaultString().contains("RECORD NOT FOUND : NO RESERVATION FOUND WITH 11111"), arrived.getFaultString() ,arrived);

		LogItems logValidItems = new LogItems();
		logValidItems.addItem("EventDiningServiceIF", "arrived", true);
		validateLogs(arrived, logValidItems);
		
		LogItems logInvalidItems = new LogItems();
		logInvalidItems.addItem("ChargeGroupIF", "checkIn", false);
		logInvalidItems.addItem("TravelPlanServiceCrossReferenceV3", "updateOrder", false);
		logInvalidItems.addItem("TravelPlanServiceCrossReferenceV3SEI", "updateOrder", false);
		validateNotInLogs(arrived, logInvalidItems);
	}
	
	@Test(groups = {"api", "regression", "dining", "eventDiningService", "negative"})
	public void cancelledReservation(){
		ScheduledEventReservation res2 = new EventDiningReservation(this.environment, hh);
		res2.book(ScheduledEventReservation.NOCOMPONENTSNOADDONS);
		res2.cancel();
		Arrived arrived = new Arrived(this.environment,"Main");
		arrived.setReservationNumber(res2.getConfirmationNumber());
		arrived.sendRequest();

		validateApplicationError(arrived, DiningErrorCode.INVALID_TRAVEL_STATUS);
		TestReporter.logAPI(!arrived.getFaultString().contains("Travel Status is invalid  : INVALID RESERVATION STATUS.CANNOT CHANGE THE STATUS TO ARRIVED!"), arrived.getFaultString() ,arrived);

		LogItems logValidItems = new LogItems();
		logValidItems.addItem("EventDiningServiceIF", "arrived", true);
		validateLogs(arrived, logValidItems);
		
		LogItems logInvalidItems = new LogItems();
		logInvalidItems.addItem("ChargeGroupIF", "checkIn", false);
		logInvalidItems.addItem("TravelPlanServiceCrossReferenceV3", "updateOrder", false);
		logInvalidItems.addItem("TravelPlanServiceCrossReferenceV3SEI", "updateOrder", false);
		validateNotInLogs(arrived, logInvalidItems);
	}
}
