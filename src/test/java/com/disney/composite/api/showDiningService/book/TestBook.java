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
	protected Book book;
	protected String TP_ID;
	protected ThreadLocal<String> TPS_ID = new ThreadLocal<String>();
	protected LogItems logValidItems = new LogItems();
	protected String[] defaultExpectedLogs = {"PartyIF;createAndRetrieveParty",
			"FacilityMasterServiceSEI;findFacilityByEnterpriseID",
			"PackagingService;getProducts"};
	protected HouseHold hh = null;
	protected String reservationNumber;
	
	@Override
	@BeforeMethod(alwaysRun=true)
	@Parameters("environment")
	public void setup(@Optional String environment){
		this.environment = environment;
		hh = new HouseHold(1);
	}
	
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
	public void testBook() {
		TestReporter.logStep("Book a show dining reservation.");
		hh = new HouseHold(1);
		sendRequestAndvalidateLogs(book, hh);
	}
	
	@Test(groups = {"api", "regression", "dining", "eventDiningService"})
	public void testBookWith2Adults(){
		TestReporter.logStep("Book a show dining reservation with 2 adults.");
		hh = new HouseHold("2 Adults");
		sendRequestAndvalidateLogs(book, hh);
	}
	@Test(groups = {"api", "regression", "dining", "eventDiningService"})
	public void testBookWith4Adults(){
		TestReporter.logStep("Book a show dining reservation with 4 adults.");
		hh = new HouseHold("4 Adults");
		sendRequestAndvalidateLogs(book, hh);
	}
	@Test(groups = {"api", "regression", "dining", "eventDiningService"})
	public void testBookWith2Adults2Child(){
		TestReporter.logStep("Book a show dining reservation with 2 adults and 2 children.");
		hh = new HouseHold("2 Adults 2 Child");
		sendRequestAndvalidateLogs(book, hh);
	}
	@Test(groups = {"api", "regression", "dining", "eventDiningService"})
	public void testBookWith4Adults2Child2Infant(){
		TestReporter.logStep("Book a show dining reservation with 4 adults, 2 children, and 2 infants.");
		hh = new HouseHold("4 Adults 2 Child 2 Infant");
		sendRequestAndvalidateLogs(book, hh);
	}
	@Test(groups = {"api", "regression", "dining", "eventDiningService"})
	public void testBookWith12InParty(){
		TestReporter.logStep("Book a show dining reservation with 12 adults.");
		hh = new HouseHold(12);
		sendRequestAndvalidateLogs(book, hh);
	}
	
	private void sendRequestAndvalidateLogs(Book book, HouseHold hh){
		book = new Book(environment, ScheduledEventReservation.ONECOMPONENTSNOADDONS);
		book.setParty(hh);
		book.sendRequest();
		TestReporter.logAPI(!book.getResponseStatusCode().equals("200"), "An error occurred during booking", book);
		TP_ID = book.getTravelPlanId();
		TPS_ID.set(book.getTravelPlanSegmentId());
		TestReporter.assertTrue(Regex.match("[0-9]+", TP_ID), "The travel plan ID ["+TP_ID+"] was not numeric as expected.");
		TestReporter.assertTrue(Regex.match("[0-9]+", TPS_ID.get()), "The reservation number ["+TPS_ID.get()+"] was not numeric as expected.");
		
		logValidItems = new LogItems();
		logValidItems.addItem("ShowDiningServiceIF", "book", false);
		logValidItems.addItem("TravelPlanServiceV3SEI", "create", false);
		logValidItems.addItem("ChargeGroupIF", "createChargeGroupAndPostCharges", false);
		logValidItems.addItem("PartyIF", "createAndRetrieveParty", false);
		logValidItems.addItem("AccommodationInventoryRequestComponentServiceIF", "createInventory", false);	
		logValidItems.addItem("FacilityMasterServiceSEI", "findFacilityByEnterpriseID", false);
		logValidItems.addItem("PackagingService", "getProducts", false);
		logValidItems.addItem("TravelPlanServiceV3", "create", false);		
		logValidItems.addItem("UpdateInventory", "updateInventory", false);
		validateLogs(book, logValidItems, 10000);
	}
}
