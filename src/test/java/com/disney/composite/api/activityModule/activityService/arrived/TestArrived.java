package com.disney.composite.api.activityModule.activityService.arrived;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.activityModule.activityServicePort.operations.Arrived;
import com.disney.composite.BaseTest;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.database.LogItems;
import com.disney.utils.dataFactory.guestFactory.HouseHold;
import com.disney.utils.dataFactory.staging.bookSEReservation.ActivityEventReservation;
import com.disney.utils.dataFactory.staging.bookSEReservation.ScheduledEventReservation;

public class TestArrived  extends BaseTest{
	protected ScheduledEventReservation res = null;
	
	@Override
	@BeforeMethod(alwaysRun = true)
	@Parameters({ "environment" })
	public void setup(@Optional String environment){
		this.environment = environment;
		hh = new HouseHold(1);
		hh.primaryGuest().setAge("6");
		res = new ActivityEventReservation(this.environment, hh);
		res.book(ScheduledEventReservation.NOCOMPONENTSNOADDONS);
	}
	
	@AfterMethod(alwaysRun=true)
	public void teardown(){
		try{res.cancel();}
		catch(Exception e){}
	}

	@Test(groups = {"api", "regression", "activity", "activityService"})
	public void testArrived(){
		TestReporter.logScenario("Arrived");
		TestReporter.log("Reservation Number: <b>" + res.getConfirmationNumber() + "</b>");
		Arrived arrived = new Arrived(this.environment,"Main");
		arrived.setReservationNumber(res.getConfirmationNumber());
		arrived.sendRequest();
		TestReporter.logAPI(!arrived.getResponseStatusCode().contains("200"), arrived.getFaultString() ,arrived);
		TestReporter.logAPI(!arrived.getArrivalStatus().equals("SUCCESS"), "The response ["+arrived.getArrivalStatus()+"] was not 'SUCCESS' as expected.", arrived);		

		LogItems logItems = new LogItems();
		logItems.addItem("ActivityServiceIF", "arrived", false);
		logItems.addItem("ChargeGroupIF", "checkIn", false);
		logItems.addItem("TravelPlanServiceCrossReferenceV3", "updateOrder", false);
		logItems.addItem("TravelPlanServiceCrossReferenceV3SEI", "updateOrder", false);
		logItems.addItem("FolioServiceIF", "retrieveAccountingTransactions", false);
		logItems.addItem("PartyIF", "retrievePartyBasicInformation", false);
		logItems.addItem("PartyIF", "retrieveParty", false);
		logItems.addItem("AccommodationInventoryRequestComponentServiceIF", "retrieveAssignmentOwner", false);
		logItems.addItem("ActivityServiceIF", "retrieve", false);
		logItems.addItem("PackagingService", "getProducts", false);
		logItems.addItem("PricingService", "priceComponents", false);		
		validateLogs(arrived, logItems);
	}
}