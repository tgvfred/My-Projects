package com.disney.composite.api.showDiningService.book;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import com.disney.api.soapServices.core.BaseSoapCommands;
import com.disney.api.soapServices.showDiningService.operations.Book;
import com.disney.api.soapServices.showDiningService.operations.Cancel;
import com.disney.composite.BaseTest;
import com.disney.utils.Regex;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.database.LogItems;
import com.disney.utils.dataFactory.staging.bookSEReservation.ScheduledEventReservation;

public class TestBookWithSpecialRequests extends BaseTest{
	protected Book book;
	protected String TP_ID;
	protected ThreadLocal<String> TPS_ID = new ThreadLocal<String>();
	protected String[] defaultExpectedLogs = {"PartyIF;createAndRetrieveParty",
			"FacilityMasterServiceSEI;findFacilityByEnterpriseID",
			"PackagingService;getProducts"};
	
	@AfterMethod(alwaysRun=true)
	public void teardown(){
		try{
			if(TPS_ID.get() != null)
				if(!TPS_ID.get().isEmpty()){
					Cancel cancel = new Cancel(environment, "CancelDiningEvent");
					cancel.setTravelPlanSegmentId(TPS_ID.get());
					cancel.sendRequest();
				}			
		}catch(Exception e){}
	}
	@Test(groups = {"api", "regression", "dining", "showDiningService"})
	public void testBookWithAddOns(){
		Book book = new Book(environment, ScheduledEventReservation.SPECIALREQUESTS);
		book.setParty(hh);
		book.setSignInLocation(BaseSoapCommands.REMOVE_NODE.toString());
		book.sendRequest();
		TestReporter.logAPI(!book.getResponseStatusCode().equals("200"), "An error occurred during booking", book);
		TP_ID = book.getTravelPlanId();
		TPS_ID.set(book.getTravelPlanSegmentId());
		TestReporter.assertTrue(Regex.match("[0-9]+", TP_ID), "Verify the travel plan ID ["+TP_ID+"] is numeric as expected.");
		TestReporter.assertTrue(Regex.match("[0-9]+", TPS_ID.get()), "Verify the reservation number ["+TPS_ID.get()+"] is numeric as expected.");
		
		LogItems logValidItems = new LogItems();
		logValidItems.addItem("ShowDiningServiceIF", "book", false);
		logValidItems.addItem("TravelPlanServiceV3SEI", "create", false);
		logValidItems.addItem("ChargeGroupIF", "createChargeGroupAndPostCharges", false);
		logValidItems.addItem("PartyIF", "createAndRetrieveParty", false);
		logValidItems.addItem("AccommodationInventoryRequestComponentServiceIF", "createInventory", false);
		logValidItems.addItem("PackagingService", "getProducts", false);
		logValidItems.addItem("TravelPlanServiceV3", "create", false);		
		logValidItems.addItem("UpdateInventory", "updateInventory", false);
		validateLogs(book, logValidItems, 10000);
	}
}