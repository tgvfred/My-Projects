package com.disney.composite.api.tableServiceDiningService.noShow;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.tableServiceDiningServicePort.operations.NoShow;
import com.disney.composite.BaseTest;
import com.disney.utils.Regex;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.database.LogItems;
import com.disney.utils.dataFactory.guestFactory.HouseHold;
import com.disney.utils.dataFactory.staging.bookSEReservation.TableServiceDiningReservation;
import com.disney.utils.dataFactory.staging.bookSEReservation.ScheduledEventReservation;

public class TestNoShow extends BaseTest{
	// Defining global variables
	protected String TPS_ID = null;
	protected ScheduledEventReservation res = null;	
	
	@Override
	@BeforeClass(alwaysRun = true)
	@Parameters({ "environment" })
	public void setup(@Optional String environment){
		this.environment = environment;
		hh = new HouseHold(1);
		res = new TableServiceDiningReservation(this.environment, hh);
		res.book(ScheduledEventReservation.NOCOMPONENTSNOADDONS);
	}

	@Test(groups = {"api", "regression", "dining", "tableServiceDiningService"})
	public void testNoShow(){
		TestReporter.logScenario("NoShow");
		NoShow noShow = new NoShow(environment, "Main");
		noShow.setReservationNumber(res.getConfirmationNumber());
		noShow.sendRequest();
		
		TestReporter.logAPI(!noShow.getResponseStatusCode().contains("200"), noShow.getFaultString() ,noShow);
		TestReporter.assertTrue(Regex.match("[0-9]+", noShow.getCancellationNumber()), "The cancellation number ["+noShow.getCancellationNumber()+"] was not numeric as expected.");

		LogItems logItems = new LogItems();
		logItems.addItem("TableServiceDiningServiceIF", "noShow", false);
		logItems.addItem("ChargeGroupIF", "processNoShow", false);
		logItems.addItem("TravelPlanServiceCrossReferenceV3", "updateOrder", false);
		logItems.addItem("TravelPlanServiceCrossReferenceV3SEI", "updateOrder", false);
		logItems.addItem("UpdateInventory", "updateInventory", false);	
		logItems.addItem("PartyIF", "retrieveParty", false);
		logItems.addItem("AccommodationInventoryRequestComponentServiceIF", "releaseInventory", false);
		logItems.addItem("GuestLinkServiceV1", "createEntitlementReference", false);
		if(environment.equalsIgnoreCase("Sleepy")){
			logItems.addItem("GuestLinkServiceV1SEI", "createEntitlementReference", false);
		}		
		validateLogs(noShow, logItems);
	}
}
