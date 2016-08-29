package com.disney.composite.api.diningModule.tableServiceDiningService.noShow;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.diningModule.tableServiceDiningServicePort.operations.Book;
import com.disney.api.soapServices.diningModule.tableServiceDiningServicePort.operations.NoShow;
import com.disney.composite.BaseTest;
import com.disney.utils.Regex;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.database.LogItems;
import com.disney.utils.dataFactory.guestFactory.HouseHold;
import com.disney.utils.dataFactory.staging.bookSEReservation.ScheduledEventReservation;
import com.disney.utils.dataFactory.staging.bookSEReservation.TableServiceDiningReservation;

public class TestNoShow extends BaseTest{
	// Defining global variables


	@Test(groups = {"api", "regression", "dining", "tableServiceDiningService"})
	public void testNoShow(){
		hh = new HouseHold(1);
		ScheduledEventReservation res = new TableServiceDiningReservation(this.environment, hh);
		res.book(ScheduledEventReservation.NOCOMPONENTSNOADDONS);
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

	@Test(groups = {"api", "regression", "dining", "tableServiceDiningService"})
	public void testNoShow_DLR(){
		Book book = new Book(environment, "DLRTableServiceOneChild");
		hh = new HouseHold(1);
		hh.primaryGuest().setAge("10");
		book.setParty(hh);
		book.sendRequest();
		TestReporter.logAPI(!book.getResponseStatusCode().contains("200"), book.getFaultString() ,book);
		
		TestReporter.logScenario("DLR NoShow");
		NoShow noShow = new NoShow(environment, "Main");
		noShow.setReservationNumber(book.getTravelPlanSegmentId());
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
