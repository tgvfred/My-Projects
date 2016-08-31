package com.disney.composite.api.diningModule.tableServiceDiningService.cancel;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.diningModule.tableServiceDiningServicePort.operations.Book;
import com.disney.api.soapServices.diningModule.tableServiceDiningServicePort.operations.Cancel;
import com.disney.composite.BaseTest;
import com.disney.utils.Regex;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.database.LogItems;
import com.disney.utils.dataFactory.guestFactory.HouseHold;
import com.disney.utils.dataFactory.staging.bookSEReservation.ScheduledEventReservation;
import com.disney.utils.dataFactory.staging.bookSEReservation.TableServiceDiningReservation;

public class TestCancel extends BaseTest{
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
		TPS_ID = res.getConfirmationNumber();
	}

	@Test( groups = {"api", "regression", "dining", "tableDiningService"})
	public void testCancel() {
		TestReporter.logScenario("Cancel Table Service Reservation");
		TestReporter.log("Reservation Number: " + TPS_ID);
		Cancel cancel = new Cancel(environment, "Main");
		cancel.setReservationNumber(TPS_ID);
		cancel.sendRequest();
		TestReporter.assertTrue(Regex.match("[0-9]+", cancel.getCancellationConfirmationNumber()), "The cancellation number ["+cancel.getCancellationConfirmationNumber()+"] was not numeric as expected.");

		LogItems logItems = new LogItems();
		logItems.addItem("TableServiceDiningServiceIF", "cancel", false);
		logItems.addItem("AccommodationInventoryRequestComponentServiceIF", "releaseInventory", false);
		logItems.addItem("ChargeGroupIF", "cancelChargeGroups", false);
		logItems.addItem("TravelPlanServiceCrossReferenceV3", "cancelOrder", false);
		logItems.addItem("UpdateInventory", "updateInventory", false);
		logItems.addItem("TravelPlanServiceCrossReferenceV3SEI", "cancelOrder", false);
		logItems.addItem("ChargeGroupIF", "cancelChargeGroups", false);
		logItems.addItem("PartyIF", "retrieveParty", false);
		if(environment.equalsIgnoreCase("Sleepy")){
			logItems.addItem("GuestLinkServiceV1SEI", "createEntitlementReference", false); //Sleepy only
		}
		validateLogs(cancel, logItems, 10000);
	}


	@Test( groups = {"api", "regression", "dining", "tableDiningService"})
	public void testCancel_DLR() {
		TestReporter.logScenario("Cancel DLR Table Service Reservation");
		Book book = new Book(environment, "DLRTableServiceOneChild");
		hh = new HouseHold(1);
		hh.primaryGuest().setAge("10");
		book.setParty(hh);
		book.sendRequest();
		TestReporter.logAPI(!book.getResponseStatusCode().contains("200"), book.getFaultString() ,book);
		
		Cancel cancel = new Cancel(environment, "Main");
		cancel.setReservationNumber(book.getTravelPlanSegmentId());
		cancel.sendRequest();
		TestReporter.assertTrue(Regex.match("[0-9]+", cancel.getCancellationConfirmationNumber()), "The cancellation number ["+cancel.getCancellationConfirmationNumber()+"] was not numeric as expected.");

		LogItems logItems = new LogItems();
		logItems.addItem("TableServiceDiningServiceIF", "cancel", false);
		logItems.addItem("AccommodationInventoryRequestComponentServiceIF", "releaseInventory", false);
		logItems.addItem("ChargeGroupIF", "cancelChargeGroups", false);
		logItems.addItem("TravelPlanServiceCrossReferenceV3", "cancelOrder", false);
		logItems.addItem("UpdateInventory", "updateInventory", false);
		logItems.addItem("TravelPlanServiceCrossReferenceV3SEI", "cancelOrder", false);
		logItems.addItem("ChargeGroupIF", "cancelChargeGroups", false);
		logItems.addItem("PartyIF", "retrieveParty", false);
		if(environment.equalsIgnoreCase("Sleepy")){
			logItems.addItem("GuestLinkServiceV1SEI", "createEntitlementReference", false); //Sleepy only
		}
		validateLogs(cancel, logItems, 10000);
	}
	
}