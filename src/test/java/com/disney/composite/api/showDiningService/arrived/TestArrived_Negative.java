package com.disney.composite.api.showDiningService.arrived;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.showDiningService.operations.Arrived;
import com.disney.composite.BaseTest;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.database.LogItems;
import com.disney.utils.dataFactory.guestFactory.HouseHold;
import com.disney.utils.dataFactory.staging.bookSEReservation.ScheduledEventReservation;
import com.disney.utils.dataFactory.staging.bookSEReservation.ShowDiningReservation;

public class TestArrived_Negative  extends BaseTest{
	// Defining global variables
	protected String TPS_ID = null;
	protected ThreadLocal<ScheduledEventReservation> res = new ThreadLocal<ScheduledEventReservation>();
	protected ThreadLocal<Arrived> arrived = new ThreadLocal<Arrived>();
	
	@Override
	@BeforeMethod(alwaysRun = true)
	@Parameters({ "environment" })
	public void setup(@Optional String environment){
		this.environment = environment;
		hh = new HouseHold(1);
		res.set(new ShowDiningReservation(this.environment, hh));
		res.get().book(ScheduledEventReservation.ONECOMPONENTSNOADDONS);
	}

	@AfterMethod
	public void teardown(){
		try{
			if(res != null)
				if(!res.get().getConfirmationNumber().isEmpty())
					res.get().cancel();
		}catch(Exception e){}
	}
	
	@Test(groups = {"api", "regression", "dining", "showDiningService", "negative"})
	public void missingReservationNumber(){
		TestReporter.logScenario("Test Arrived with a Missing Reservation Number Node");
		arrived.set(new Arrived(this.environment,"ContactCenter"));
		arrived.get().setReservationNumber("fx:removenode");
		arrived.get().sendRequest();
		TestReporter.logAPI(!arrived.get().getFaultString().contains("RECORD NOT FOUND : NO RESERVATION FOUND WITH 0"), arrived.get().getFaultString() ,arrived.get());
		logItems();
	}	
	
	@Test(groups = {"api", "regression", "dining", "showDiningService", "negative"})
	public void invalidReservationNumber(){
		TestReporter.logScenario("Test Arrived with a Invalid Reservation Number");
		arrived.set(new Arrived(this.environment,"ContactCenter"));
		arrived.get().setReservationNumber("11111");
		arrived.get().sendRequest();
		TestReporter.logAPI(!arrived.get().getFaultString().contains("RECORD NOT FOUND : NO RESERVATION FOUND WITH 11111"), arrived.get().getFaultString() ,arrived.get());
		logItems();
	}
	
	@Test(groups = {"api", "regression", "dining", "showDiningService", "negative"})
	public void cancelledReservation(){
		TestReporter.logScenario("Test Arrived with a Cancelled Reservation Number");
		res.get().book(ScheduledEventReservation.ONECOMPONENTSNOADDONS);
		res.get().cancel();
		arrived();
	}

	@Test(groups = {"api", "regression", "dining", "showDiningService", "negative"})
	public void arrivedReservation() {
		TestReporter.logScenario("Test Setting Arrived Reservation to Arrived");
		res.get().arrived();		
		arrived();
	}
	
	private void arrived(){		
		arrived.set(new Arrived(environment, "ContactCenter"));
		arrived.get().setReservationNumber(res.get().getConfirmationNumber());
		arrived.get().sendRequest();
		TestReporter.logAPI(!arrived.get().getFaultString().contains(" Travel Status is invalid  : INVALID RESERVATION STATUS.CANNOT CHANGE THE STATUS TO ARRIVED!"), arrived.get().getFaultString() ,arrived.get());
		logItems();
	}
	
	private void logItems(){
		LogItems logValidItems = new LogItems();
		logValidItems.addItem("ShowDiningServiceIF", "arrived", true);
		validateLogs(arrived.get(), logValidItems);
		
		LogItems logInvalidItems = new LogItems();
		logInvalidItems.addItem("TravelPlanServiceCrossReferenceV3SEI", "updateOrder", false);
		logInvalidItems.addItem("ChargeGroupIF", "checkIn", false);
		logInvalidItems.addItem("TravelPlanServiceCrossReferenceV3", "updateOrder", false);
		logInvalidItems.addItem("PartyIF", "retrieveParty", false);
		validateNotInLogs(arrived.get(), logInvalidItems);
	}
}