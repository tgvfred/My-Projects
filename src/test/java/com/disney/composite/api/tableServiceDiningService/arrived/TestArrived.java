package com.disney.composite.api.tableServiceDiningService.arrived;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.tableServiceDiningServicePort.operations.Arrived;
import com.disney.composite.BaseTest;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.database.LogItems;
import com.disney.utils.dataFactory.guestFactory.HouseHold;
import com.disney.utils.dataFactory.staging.bookSEReservation.TableServiceDiningReservation;
import com.disney.utils.dataFactory.staging.bookSEReservation.ScheduledEventReservation;

public class TestArrived  extends BaseTest{
	// Defining global variables
	protected String TPS_ID = null;
	protected ScheduledEventReservation res = null;
	
	@Override
	@BeforeMethod(alwaysRun = true)
	@Parameters({ "environment" })
	public void setup(@Optional String environment){
		this.environment = environment;
		hh = new HouseHold(1);
		res = new TableServiceDiningReservation(this.environment, hh);
		res.book(ScheduledEventReservation.NOCOMPONENTSNOADDONS);
	}

	@Test(groups = {"api", "regression", "dining", "tableServiceDiningService"})
	public void testArrived(){
		Arrived arrived = new Arrived(this.environment,"Main");
		arrived.setReservationNumber(res.getConfirmationNumber());
		arrived.sendRequest();
		TestReporter.logAPI(!arrived.getResponseStatusCode().contains("200"), arrived.getFaultString() ,arrived);
		TestReporter.logAPI(!arrived.getArrivalStatus().equals("SUCCESS"), "The response ["+arrived.getArrivalStatus()+"] was not 'SUCCESS' as expected.", arrived);
		

		LogItems logItems = new LogItems();
		logItems.addItem("ChargeGroupIF", "checkIn", false);
		logItems.addItem("TableServiceDiningServiceIF", "arrived", false);
		logItems.addItem("TravelPlanServiceCrossReferenceV3", "updateOrder", false);
		logItems.addItem("TravelPlanServiceCrossReferenceV3SEI", "updateOrder", false);
		

		logItems.addItem("PartyIF", "retrieveParty", false);
		
		
		if(environment.equalsIgnoreCase("Sleepy")){
			logItems.addItem("GuestLinkServiceV1SEI", "createEntitlementReference", false); //Sleepy only
		}
		validateLogs(arrived, logItems, 10000);
	}
}