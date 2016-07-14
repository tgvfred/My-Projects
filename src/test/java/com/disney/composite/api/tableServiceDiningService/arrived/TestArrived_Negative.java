package com.disney.composite.api.tableServiceDiningService.arrived;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.core.BaseSoapCommands;
import com.disney.api.soapServices.tableServiceDiningServicePort.operations.Arrived;
import com.disney.api.soapServices.tableServiceDiningServicePort.operations.Cancel;
import com.disney.composite.BaseTest;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.database.LogItems;
import com.disney.utils.dataFactory.guestFactory.HouseHold;
import com.disney.utils.dataFactory.staging.bookSEReservation.ScheduledEventReservation;
import com.disney.utils.dataFactory.staging.bookSEReservation.TableServiceDiningReservation;

public class TestArrived_Negative  extends BaseTest{
	// Defining global variables
	protected ThreadLocal<String> TPS_ID = new ThreadLocal<String>();
	
	@Override
	@BeforeMethod(alwaysRun = true)
	@Parameters({ "environment" })
	public void setup(@Optional String environment){
		this.environment = environment;
		hh = new HouseHold(1);
	}

	@AfterMethod(alwaysRun=true)
	public void teardown(){
		try{
			if(TPS_ID.get() != null)
				if(!TPS_ID.get().isEmpty()){
					Cancel cancel = new Cancel(environment, "Main");
					cancel.setReservationNumber(TPS_ID.get());
					cancel.sendRequest();
				}
		}catch(Exception e){}
	}
	
	@Test(groups = {"api", "regression", "dining", "eventDiningService", "negative"})
	public void missingReservationNumber(){
		TestReporter.logScenario("Missing Reservation");
		Arrived arrived = new Arrived(this.environment,"Main");
		arrived.setReservationNumber(BaseSoapCommands.REMOVE_NODE.toString());
		sendRequestAndValidateLogs(arrived, "RECORD NOT FOUND : NO RESERVATION FOUND WITH 0");
	}
	

	@Test(groups = {"api", "regression", "dining", "eventDiningService", "negative"})
	public void invalidReservationNumber(){
		TestReporter.logScenario("Arrived");
		Arrived arrived = new Arrived(this.environment,"Main");
		arrived.setReservationNumber("11111");
		sendRequestAndValidateLogs(arrived, "RECORD NOT FOUND : NO RESERVATION FOUND WITH 11111");
	}
	
	@Test(groups = {"api", "regression", "dining", "eventDiningService", "negative"})
	public void cancelledReservation(){
		TestReporter.logScenario("Cancelled Reservation");
		ScheduledEventReservation res = new TableServiceDiningReservation(this.environment, hh);
		res.book(ScheduledEventReservation.NOCOMPONENTSNOADDONS);
		TPS_ID.set(res.getConfirmationNumber());
		res.cancel();
		Arrived arrived = new Arrived(this.environment,"Main");
		arrived.setReservationNumber(res.getConfirmationNumber());
		sendRequestAndValidateLogs(arrived, "Travel Status is invalid  : INVALID RESERVATION STATUS.CANNOT CHANGE THE STATUS TO ARRIVED!");
	}
	
	private void sendRequestAndValidateLogs(Arrived arrived, String faultString){
		arrived.sendRequest();
		TestReporter.logAPI(!arrived.getFaultString().contains(faultString), arrived.getFaultString() ,arrived);

		LogItems logValidItems = new LogItems();
		logValidItems.addItem("TableServiceDiningServiceIF", "arrived", true);
		validateLogs(arrived, logValidItems, 10000);
		
		LogItems logInvalidItems = new LogItems();
		logInvalidItems.addItem("ChargeGroupIF", "checkIn", false);
		logInvalidItems.addItem("TravelPlanServiceCrossReferenceV3", "updateOrder", false);
		logInvalidItems.addItem("TravelPlanServiceCrossReferenceV3SEI", "updateOrder", false);
		logInvalidItems.addItem("PartyIF", "retrieveParty", false);
		validateNotInLogs(arrived, logInvalidItems);
	}
}
