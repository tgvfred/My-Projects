package com.disney.composite.api.showDiningService.book;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.showDiningService.operations.Book;
import com.disney.api.soapServices.showDiningService.operations.Cancel;
import com.disney.composite.BaseTest;
import com.disney.utils.Regex;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.database.LogItems;
import com.disney.utils.dataFactory.guestFactory.HouseHold;
import com.disney.utils.dataFactory.staging.bookSEReservation.ScheduledEventReservation;

public class TestBook extends BaseTest{
	private Book book;
	private String TP_ID;
	private String TPS_ID;
	protected ThreadLocal<LogItems> logValidItems = new ThreadLocal<LogItems>();
	protected String[] defaultExpectedLogs = {"PartyIF;createAndRetrieveParty",
			"FacilityMasterServiceSEI;findFacilityByEnterpriseID",
			"PackagingService;getProducts"};
	protected HouseHold hh = null;
	
	@Override
	@BeforeMethod(alwaysRun=true)
	@Parameters("environment")
	public void setup(@Optional String environment){
		this.environment = environment;
		hh = new HouseHold(1);
	}
	
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
		logValidItems.get().addItem("ShowDiningServiceIF", "book", false);
		logValidItems.get().addItem("TravelPlanServiceV3SEI", "create", false);
		logValidItems.get().addItem("ChargeGroupIF", "createChargeGroupAndPostCharges", false);
		logValidItems.get().addItem("PartyIF", "createAndRetrieveParty", false);
		logValidItems.get().addItem("AccommodationInventoryRequestComponentServiceIF", "createInventory", false);	
		logValidItems.get().addItem("FacilityMasterServiceSEI", "findFacilityByEnterpriseID", false);
		logValidItems.get().addItem("PackagingService", "getProducts", false);
		logValidItems.get().addItem("TravelPlanServiceV3", "create", false);		
		logValidItems.get().addItem("UpdateInventory", "updateInventory", false);
		validateLogs(book, logValidItems.get());
	}
}
