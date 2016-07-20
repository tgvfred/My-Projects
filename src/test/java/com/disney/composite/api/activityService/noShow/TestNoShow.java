package com.disney.composite.api.activityService.noShow;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.activityServicePort.operations.NoShow;
import com.disney.composite.BaseTest;
import com.disney.utils.Regex;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.database.LogItems;
import com.disney.utils.dataFactory.guestFactory.HouseHold;
import com.disney.utils.dataFactory.staging.bookSEReservation.ActivityEventReservation;
import com.disney.utils.dataFactory.staging.bookSEReservation.ScheduledEventReservation;

public class TestNoShow extends BaseTest{
	protected ScheduledEventReservation res = null;	
	
	@Override
	@BeforeClass(alwaysRun = true)
	@Parameters({ "environment" })
	public void setup(@Optional String environment){
		this.environment = environment;
		hh = new HouseHold(1);
		hh.primaryGuest().setAge("6");
		res = new ActivityEventReservation(this.environment, hh);
		res.book(ScheduledEventReservation.NOCOMPONENTSNOADDONS);
	}

	@Test(groups = {"api", "regression", "activity", "activityService"})
	public void testNoShow(){
		TestReporter.logScenario("No Show");
		TestReporter.log("Reservation Number: <b>" + res.getConfirmationNumber() + "</b>");
		NoShow noShow = new NoShow(environment, "Main");
		noShow.setReservationNumber(res.getConfirmationNumber());
		noShow.sendRequest();		
		TestReporter.logAPI(!noShow.getResponseStatusCode().contains("200"), noShow.getFaultString() ,noShow);
		TestReporter.assertTrue(Regex.match("[0-9]+", noShow.getCancellationNumber()), "The cancellation number ["+noShow.getCancellationNumber()+"] was not numeric as expected.");

		LogItems logItems = new LogItems();
		logItems.addItem("ChargeGroupIF", "processNoShow", false);
		logItems.addItem("ActivityServiceIF", "noShow", false);
		logItems.addItem("PricingService", "getCancellationCharges", false);
		logItems.addItem("TravelPlanServiceCrossReferenceV3", "updateOrder", false);
		logItems.addItem("TravelPlanServiceCrossReferenceV3SEI", "updateOrder", false);
		logItems.addItem("UpdateInventory", "updateInventory", false);
		logItems.addItem("FolioServiceIF", "retrieveAccountingTransactions", false);
		logItems.addItem("PartyIF", "retrieveParty", false);
		logItems.addItem("PartyIF", "retrievePartyBasicInformation", false);
		logItems.addItem("AccommodationInventoryRequestComponentServiceIF", "releaseInventory", false);
		logItems.addItem("AccommodationInventoryRequestComponentServiceIF", "retrieveAssignmentOwner", false);
		logItems.addItem("ActivityServiceIF", "retrieve", false);
		logItems.addItem("PackagingService", "getProducts", false);
		logItems.addItem("PricingService", "priceComponents", false);
		validateLogs(noShow, logItems);
	}
}