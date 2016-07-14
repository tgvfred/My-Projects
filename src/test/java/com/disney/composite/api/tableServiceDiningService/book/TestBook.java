package com.disney.composite.api.tableServiceDiningService.book;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import com.disney.api.soapServices.tableServiceDiningServicePort.operations.Book;
import com.disney.api.soapServices.tableServiceDiningServicePort.operations.Cancel;
import com.disney.composite.BaseTest;
import com.disney.utils.Regex;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.database.LogItems;
import com.disney.utils.dataFactory.guestFactory.HouseHold;

public class TestBook extends BaseTest{
	// Defining global variables
	protected ThreadLocal<String> TPS_ID = new ThreadLocal<String>();
	
	@AfterMethod(alwaysRun=true)
	public void teardown(){
		try{
			if(TPS_ID.get() != null)
				if(!TPS_ID.get().isEmpty()){
					Cancel cancel = new Cancel(environment, "Main");
					cancel.setReservationNumber(TPS_ID.get());
					cancel.sendRequest();
				}
		}catch(Exception e){}
	}
	
	@Test(groups = {"api", "regression", "dining", "tableDiningService"})
	public void testBook(){
		TestReporter.logScenario("1 Adult");
		hh = new HouseHold(1);	
		addAndValidateLogs(bookAndValidate(hh));
	}

	@Test(groups = {"api", "regression", "dining", "tableDiningService"})
	public void testBookWith2Adults(){
		TestReporter.logScenario("2 Adults");
		hh = new HouseHold("2 Adults");	
		addAndValidateLogs(bookAndValidate(hh));
	}

	@Test(groups = {"api", "regression", "dining", "tableDiningService"})
	public void testBookWith4Adults(){
		TestReporter.logScenario("4 Adults");
		hh = new HouseHold("4 Adults");	
		addAndValidateLogs(bookAndValidate(hh));
	}

	@Test(groups = {"api", "regression", "dining", "tableDiningService"})
	public void testBookWith2Adults2Child(){
		TestReporter.logScenario("2 Adults, 2 Children");
		hh = new HouseHold("2 Adults 2 Child");	
		addAndValidateLogs(bookAndValidate(hh));
	}

	@Test(groups = {"api", "regression", "dining", "tableDiningService"})
	public void testBookWith4Adults2Child2Infant(){
		TestReporter.logScenario("4 Adults, 2 Children, 2 Infants");
		hh = new HouseHold("4 Adults 2 Child 2 Infant");	
		addAndValidateLogs(bookAndValidate(hh));
	}
	

	@Test(groups = {"api", "regression", "dining", "tableDiningService"})
	public void testBookWith12InParty(){
		TestReporter.logScenario("12 Adults");
		hh = new HouseHold(12);		
		addAndValidateLogs(bookAndValidate(hh));
	}
	
	private Book bookAndValidate(HouseHold hh){
		Book book = new Book(environment, "NoComponentsNoAddOns");
		book.setParty(hh);
		book.sendRequest();
		TestReporter.logAPI(!book.getResponseStatusCode().contains("200"), book.getFaultString() ,book);
		TestReporter.assertTrue(Regex.match("[0-9]+", book.getTravelPlanId()), "The travel plan ID ["+book.getTravelPlanId()+"] is not numeric as expected.");
		TestReporter.assertTrue(Regex.match("[0-9]+", book.getTravelPlanSegmentId()), "The reservation number ["+book.getTravelPlanSegmentId()+"] is not numeric as expected.");
		TPS_ID.set(book.getTravelPlanSegmentId());
		return book;
	}
	
	private void addAndValidateLogs(Book book){
		LogItems logItems = new LogItems();
		logItems.addItem("AccommodationInventoryRequestComponentServiceIF", "createInventory", false);
		logItems.addItem("ChargeGroupIF", "createChargeGroupAndPostCharges", false);
		logItems.addItem("PartyIF", "createAndRetrieveParty", false);	
		logItems.addItem("TravelPlanServiceV3", "create", false);
		logItems.addItem("UpdateInventory", "updateInventory", false);
		
		if(environment.equalsIgnoreCase("Sleepy")){
			logItems.addItem("GuestServiceV1", "create", false); //Sleepy only
		}
			
		validateLogs(book, logItems);
	}
}