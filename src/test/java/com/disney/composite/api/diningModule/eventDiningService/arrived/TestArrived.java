package com.disney.composite.api.diningModule.eventDiningService.arrived;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.diningModule.eventDiningService.operations.Arrived;
import com.disney.composite.BaseTest;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.database.LogItems;
import com.disney.utils.dataFactory.guestFactory.HouseHold;
import com.disney.utils.dataFactory.staging.bookSEReservation.EventDiningReservation;
import com.disney.utils.dataFactory.staging.bookSEReservation.ScheduledEventReservation;

public class TestArrived  extends BaseTest{
	// Defining global variables
	protected String TPS_ID = null;
	protected ScheduledEventReservation res = null;

	@Test(groups = {"api", "regression", "dining", "eventDiningService"})
	public void testArrived(){
		ScheduledEventReservation res = null;
		hh = new HouseHold(1);
		res = new EventDiningReservation(this.environment, hh);
		res.book("DLRTableServiceOneChild");
		
		
		Arrived arrived = new Arrived(this.environment,"Main");
		arrived.setReservationNumber(res.getConfirmationNumber());
		arrived.sendRequest();
		TestReporter.logAPI(!arrived.getResponseStatusCode().contains("200"), arrived.getFaultString() ,arrived);
		TestReporter.logAPI(!arrived.getArrivalStatus().equals("SUCCESS"), "The response ["+arrived.getArrivalStatus()+"] was not 'SUCCESS' as expected.", arrived);
		

		LogItems logItems = new LogItems();
		logItems.addItem("ChargeGroupIF", "checkIn", false);
		logItems.addItem("EventDiningServiceIF", "arrived", false);
		logItems.addItem("TravelPlanServiceCrossReferenceV3", "updateOrder", false);
		logItems.addItem("TravelPlanServiceCrossReferenceV3SEI", "updateOrder", false);
		validateLogs(arrived, logItems);
		
		try{
			res.cancel();
		}catch(Exception e){}
	}
	
	@Test(groups = {"api", "regression", "dining", "eventDiningService"})
	public void testArrived_DLR(){
		ScheduledEventReservation res = null;
		hh = new HouseHold(1);
		res = new EventDiningReservation(this.environment, hh);
		res.book("DLRTableServiceOneChild");
		
		Arrived arrived = new Arrived(this.environment,"Main");
		arrived.setReservationNumber(res.getConfirmationNumber());
		arrived.sendRequest();
		TestReporter.logAPI(!arrived.getResponseStatusCode().contains("200"), arrived.getFaultString() ,arrived);
		TestReporter.logAPI(!arrived.getArrivalStatus().equals("SUCCESS"), "The response ["+arrived.getArrivalStatus()+"] was not 'SUCCESS' as expected.", arrived);
		

		LogItems logItems = new LogItems();
		logItems.addItem("ChargeGroupIF", "checkIn", false);
		logItems.addItem("EventDiningServiceIF", "arrived", false);
		logItems.addItem("TravelPlanServiceCrossReferenceV3", "updateOrder", false);
		logItems.addItem("TravelPlanServiceCrossReferenceV3SEI", "updateOrder", false);
		validateLogs(arrived, logItems);

		try{
			res.cancel();
		}catch(Exception e){}
	}
}
