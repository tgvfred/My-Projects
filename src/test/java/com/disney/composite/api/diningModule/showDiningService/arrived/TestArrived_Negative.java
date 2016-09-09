package com.disney.composite.api.diningModule.showDiningService.arrived;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import com.disney.api.BaseTest;
import com.disney.api.soapServices.applicationError.DiningErrorCode;
import com.disney.api.soapServices.diningModule.showDiningService.operations.Arrived;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.database.LogItems;
import com.disney.utils.dataFactory.guestFactory.HouseHold;
import com.disney.utils.dataFactory.staging.bookSEReservation.ScheduledEventReservation;
import com.disney.utils.dataFactory.staging.bookSEReservation.ShowDiningReservation;

public class TestArrived_Negative  extends BaseTest{
	// Defining global variables
	protected String TPS_ID = null;
	protected ThreadLocal<ScheduledEventReservation> res = new ThreadLocal<ScheduledEventReservation>();

	@AfterMethod(alwaysRun = true)
	public void teardown(){
		try{res.get().cancel();}
		catch(Exception e){}
	}
	
	@Test(groups = {"api", "regression", "dining", "showDiningService", "negative"})
	public void missingReservationNumber(){
		TestReporter.logScenario("Test Arrived with a Missing Reservation Number Node");
		Arrived arrived = new Arrived(this.environment, "ContactCenter");
		arrived.setReservationNumber("fx:removenode");
		arrived.sendRequest();
		validateApplicationError(arrived, DiningErrorCode.RECORD_NOT_FOUND_EXCEPTION);
		TestReporter.logAPI(!arrived.getFaultString().contains("RECORD NOT FOUND : NO RESERVATION FOUND WITH 0"), arrived.getFaultString() ,arrived);
		logItems(arrived);
	}	
	
	@Test(groups = {"api", "regression", "dining", "showDiningService", "negative"})
	public void invalidReservationNumber(){
		TestReporter.logScenario("Test Arrived with a Invalid Reservation Number");
		Arrived arrived = new Arrived(this.environment, "ContactCenter");
		arrived.setReservationNumber("11111");
		arrived.sendRequest();
		validateApplicationError(arrived, DiningErrorCode.RECORD_NOT_FOUND_EXCEPTION);
		TestReporter.logAPI(!arrived.getFaultString().contains("RECORD NOT FOUND : NO RESERVATION FOUND WITH 11111"), arrived.getFaultString() ,arrived);
		logItems(arrived);
	}
	
	@Test(groups = {"api", "regression", "dining", "showDiningService", "negative"})
	public void cancelledReservation(){
		TestReporter.logScenario("Test Arrived with a Cancelled Reservation Number");
		hh = new HouseHold(1);	
		res.set(new ShowDiningReservation(this.environment, hh));
		res.get().book(ScheduledEventReservation.ONECOMPONENTSNOADDONS);
		res.get().cancel();
		arrived();
	}

	@Test(groups = {"api", "regression", "dining", "showDiningService", "negative"})
	public void arrivedReservation() {
		TestReporter.logScenario("Test Setting Arrived Reservation to Arrived");
		hh = new HouseHold(1);	
		res.set(new ShowDiningReservation(this.environment, hh));
		res.get().book(ScheduledEventReservation.ONECOMPONENTSNOADDONS);
		res.get().arrived();		
		arrived();
	}
	
	private void arrived(){
		Arrived arrived = new Arrived(this.environment, "ContactCenter");
		arrived.setReservationNumber(res.get().getConfirmationNumber());
		arrived.sendRequest();
		validateApplicationError(arrived, DiningErrorCode.INVALID_TRAVEL_STATUS);
		TestReporter.logAPI(!arrived.getFaultString().contains(" Travel Status is invalid  : INVALID RESERVATION STATUS.CANNOT CHANGE THE STATUS TO ARRIVED!"), arrived.getFaultString() ,arrived);
		logItems(arrived);
	}
	
	private void logItems(Arrived arrived){
		LogItems logValidItems = new LogItems();
		logValidItems.addItem("ShowDiningServiceIF", "arrived", true);
		validateLogs(arrived, logValidItems, 10000);
		
		LogItems logInvalidItems = new LogItems();
		logInvalidItems.addItem("TravelPlanServiceCrossReferenceV3SEI", "updateOrder", false);
		logInvalidItems.addItem("ChargeGroupIF", "checkIn", false);
		logInvalidItems.addItem("TravelPlanServiceCrossReferenceV3", "updateOrder", false);
		logInvalidItems.addItem("PartyIF", "retrieveParty", false);
		validateNotInLogs(arrived, logInvalidItems);
	}
}