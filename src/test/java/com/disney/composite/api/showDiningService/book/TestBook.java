package com.disney.composite.api.showDiningService.book;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import com.disney.api.soapServices.showDiningService.operations.Book;
import com.disney.api.soapServices.showDiningService.operations.Cancel;
import com.disney.composite.BaseTest;
import com.disney.utils.Regex;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.database.LogItems;
import com.disney.utils.dataFactory.staging.bookSEReservation.ScheduledEventReservation;

public class TestBook extends BaseTest{
	private Book book;
	private String TP_ID;
	private String TPS_ID;
	protected ThreadLocal<LogItems> logValidItems = new ThreadLocal<LogItems>();
	protected String[] defaultExpectedLogs = {"PartyIF;createAndRetrieveParty",
			"FacilityMasterServiceSEI;findFacilityByEnterpriseID",
			"PackagingService;getProducts"};
	
	@AfterMethod
	public void teardown(){
		if(book != null)
			if(!book.getTravelPlanSegmentId().isEmpty()){
				Cancel cancel = new Cancel(environment, "CancelDiningEvent");
				cancel.setTravelPlanSegmentId(book.getTravelPlanSegmentId());
				cancel.sendRequest();
			}
	}
	
	@Test(groups = {"api", "regression", "dining", "showDiningService"})
	public void testBook() {
		TestReporter.logStep("Book a show dining reservation.");
		book = new Book(environment, ScheduledEventReservation.ONECOMPONENTSNOADDONS);
		book.setParty(hh);
		book.sendRequest();
		TestReporter.logAPI(!book.getResponseStatusCode().equals("200"), "An error occurred during booking", book);
		TP_ID = book.getTravelPlanId();
		TPS_ID = book.getTravelPlanSegmentId();
		TestReporter.assertTrue(Regex.match("[0-9]+", TP_ID), "The travel plan ID ["+TP_ID+"] was not numeric as expected.");
		TestReporter.assertTrue(Regex.match("[0-9]+", TPS_ID), "The reservation number ["+TPS_ID+"] was not numeric as expected.");

		logValidItems.set(new LogItems());
		logValidItems.get().addItem("ShowDiningServiceIF", "book", true);
		logValidItems.get().addItem("FolioServiceIF", "retrieveAccountingTransactions", true);
		logValidItems.get().addItem("GuestLinkServiceV1SEI", "createEntitlementReference", true);
		logValidItems.get().addItem("TravelPlanServiceV3SEI", "create", true);
		logValidItems.get().addItem("ChargeGroupIF", "createChargeGroupAndPostCharges", true);
		logValidItems.get().addItem("PartyIF", "createAndRetrieveParty", true);
		logValidItems.get().addItem("PartyIF", "retrieveParty", true);
		logValidItems.get().addItem("PartyIF", "retrievePartyBasicInformation", true);
		logValidItems.get().addItem("AccommodationInventoryRequestComponentServiceIF", "createInventory", true);
		logValidItems.get().addItem("AccommodationInventoryRequestComponentServiceIF", "retrieveAssignmentOwner", true);		
		logValidItems.get().addItem("FacilityMasterServiceSEI", "findFacilityByEnterpriseID", true);
		logValidItems.get().addItem("PackagingService", "getProducts", true);
		logValidItems.get().addItem("GuestLinkServiceV1", "createEntitlementReference", true);
		logValidItems.get().addItem("GuestServiceV1", "create", true);
		logValidItems.get().addItem("PartyIF", "updateExternalPartyAndLocatorId", true);
		logValidItems.get().addItem("TravelPlanServiceV3", "create", true);		
		logValidItems.get().addItem("UpdateInventory", "updateInventory", true);
		logValidItems.get().addItem("ShowDiningServiceIF", "retrieve", true);	
		validateLogs(book, logValidItems.get());
	}
}
