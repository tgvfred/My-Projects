package com.disney.composite.api.diningModule.tableServiceDiningService.book;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import com.disney.api.soapServices.diningModule.scheduledEventsServicePort.operations.RetrieveAllergies;
import com.disney.api.soapServices.diningModule.tableServiceDiningServicePort.operations.Book;
import com.disney.api.soapServices.diningModule.tableServiceDiningServicePort.operations.Cancel;
import com.disney.composite.BaseTest;
import com.disney.test.utils.Sleeper;
import com.disney.utils.Randomness;
import com.disney.utils.Regex;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.database.LogItems;
import com.disney.utils.dataFactory.guestFactory.HouseHold;
import com.disney.utils.dataFactory.staging.bookSEReservation.ScheduledEventReservation;

public class TestBook extends BaseTest{
	// Defining global variables
	protected ThreadLocal<String> TPS_ID = new ThreadLocal<String>();
	
//	@AfterMethod(alwaysRun=true)
//	public void teardown(){
//		try{
//			if(TPS_ID.get() != null)
//				if(!TPS_ID.get().isEmpty()){
//					Cancel cancel = new Cancel(environment, "Main");
//					cancel.setReservationNumber(TPS_ID.get());
//					cancel.sendRequest();
//				}
//		}catch(Exception e){}
//	}
	
	@Test(groups = {"api", "regression", "dining", "tableServiceDiningService"})
	public void testBook(){
		TestReporter.logScenario("1 Adult");
		hh = new HouseHold(1);	
		addAndValidateLogs(bookAndValidate(hh));
	}

	@Test(groups = {"api", "regression", "dining", "tableServiceDiningService"})
	public void testBookWith2Adults(){
		TestReporter.logScenario("2 Adults");
		hh = new HouseHold("2 Adults");	
		addAndValidateLogs(bookAndValidate(hh));
	}

	@Test(groups = {"api", "regression", "dining", "tableServiceDiningService"})
	public void testBookWith4Adults(){
		TestReporter.logScenario("4 Adults");
		hh = new HouseHold("4 Adults");	
		addAndValidateLogs(bookAndValidate(hh));
	}

	@Test(groups = {"api", "regression", "dining", "tableServiceDiningService"})
	public void testBookWith2Adults2Child(){
		TestReporter.logScenario("2 Adults, 2 Children");
		hh = new HouseHold("2 Adults 2 Child");	
		addAndValidateLogs(bookAndValidate(hh));
	}

	@Test(groups = {"api", "regression", "dining", "tableServiceDiningService"})
	public void testBookWith4Adults2Child2Infant(){
		TestReporter.logScenario("4 Adults, 2 Children, 2 Infants");
		hh = new HouseHold("4 Adults 2 Child 2 Infant");	
		addAndValidateLogs(bookAndValidate(hh));
	}
	

	@Test(groups = {"api", "regression", "dining", "tableServiceDiningService"})
	public void testBookWith12InParty(){
		TestReporter.logScenario("12 Adults");
		hh = new HouseHold(12);		
		addAndValidateLogs(bookAndValidate(hh));
	}
	@Test(groups = {"api", "regression", "dining", "tableServiceDiningService", "it4", "s138180" })
	public void testAddAllergy(){
		Book book = new Book(environment, ScheduledEventReservation.NOCOMPONENTSNOADDONS);

		book.setParty(hh);
		book.setAllergies("Egg", "1");
		book.sendRequest();
		if(book.getResponse().contains("existingRootChargeBookEvent :Unexpected Error occurred : createChargeGroupsAndPostCharges") ||
				book.getResponse().contains("Row was updated or deleted by another transaction")|| 
				book.getResponse().contains("existingRootChargeBookEvent") ||
				book.getResponse().toUpperCase().contains("FACILITY SERVICE UNAVAILABLE OR RETURED INVALID FACILITY")||
				book.getResponse().toLowerCase().contains("org.hibernate.exception.constraintviolationexception")){
			Sleeper.sleep(Randomness.randomNumberBetween(1, 10)*1000);
			book.sendRequest();
		}
		TestReporter.logAPI(!book.getResponseStatusCode().equals("200"), "An error occurred during booking: " + book.getFaultString(), book);
		TPS_ID.set(book.getTravelPlanSegmentId());
		TestReporter.assertTrue(Regex.match("[0-9]+", book.getTravelPlanId()), "The travel plan ID ["+book.getTravelPlanId()+"] was not numeric as expected.");
		TestReporter.assertTrue(Regex.match("[0-9]+", TPS_ID.get()), "The reservation number ["+TPS_ID.get()+"] was not numeric as expected.");
	
	}
	@Test(groups = {"api", "regression", "dining", "tableServiceDiningService", "it4", "s138180" })
	public void testAddTwoAllergies(){
		Book book = new Book(environment, ScheduledEventReservation.NOCOMPONENTSNOADDONS);

		book.setParty(hh);
		book.setAllergies("Egg", "1");
		book.setAllergies("Corn", "2");
		book.sendRequest();
		if(book.getResponse().contains("existingRootChargeBookEvent :Unexpected Error occurred : createChargeGroupsAndPostCharges") ||
				book.getResponse().contains("Row was updated or deleted by another transaction")|| 
				book.getResponse().contains("existingRootChargeBookEvent") ||
				book.getResponse().toUpperCase().contains("FACILITY SERVICE UNAVAILABLE OR RETURED INVALID FACILITY")||
				book.getResponse().toLowerCase().contains("org.hibernate.exception.constraintviolationexception")){
			Sleeper.sleep(Randomness.randomNumberBetween(1, 10)*1000);
			book.sendRequest();
		}
		TestReporter.logAPI(!book.getResponseStatusCode().equals("200"), "An error occurred during booking: " + book.getFaultString(), book);
		TPS_ID.set(book.getTravelPlanSegmentId());
		TestReporter.assertTrue(Regex.match("[0-9]+", book.getTravelPlanId()), "The travel plan ID ["+book.getTravelPlanId()+"] was not numeric as expected.");
		TestReporter.assertTrue(Regex.match("[0-9]+", TPS_ID.get()), "The reservation number ["+TPS_ID.get()+"] was not numeric as expected.");
		
	}
	@Test(groups = {"api", "regression", "dining", "tableServiceDiningService", "it4", "s138180" })
	public void testAddAllAllergies(){
		Book book = new Book(environment, ScheduledEventReservation.NOCOMPONENTSNOADDONS);

		book.setParty(hh);
		RetrieveAllergies retrieveAllergies = new RetrieveAllergies(environment);
		retrieveAllergies.sendRequest();
		TestReporter.logAPI(!retrieveAllergies.getResponseStatusCode().equals("200"), "An error occurred during booking: " + retrieveAllergies.getFaultString(), retrieveAllergies);
		
		int index = 1;
		for(String allergy : retrieveAllergies.getAllergies().values()){
			book.setAllergies(allergy,String.valueOf(index));
			index++;
		}		
		
		book.sendRequest();
		if(book.getResponse().contains("existingRootChargeBookEvent :Unexpected Error occurred : createChargeGroupsAndPostCharges") ||
				book.getResponse().contains("Row was updated or deleted by another transaction")|| 
				book.getResponse().contains("existingRootChargeBookEvent") ||
				book.getResponse().toUpperCase().contains("FACILITY SERVICE UNAVAILABLE OR RETURED INVALID FACILITY")||
				book.getResponse().toLowerCase().contains("org.hibernate.exception.constraintviolationexception")){
			Sleeper.sleep(Randomness.randomNumberBetween(1, 10)*1000);
			book.sendRequest();
		}
		TestReporter.logAPI(!book.getResponseStatusCode().equals("200"), "An error occurred during booking: " + book.getFaultString(), book);
		
		TPS_ID.set(book.getTravelPlanSegmentId());
		TestReporter.assertTrue(Regex.match("[0-9]+",  book.getTravelPlanId()), "The travel plan ID ["+ book.getTravelPlanId()+"] was not numeric as expected.");
		TestReporter.assertTrue(Regex.match("[0-9]+", TPS_ID.get()), "The reservation number ["+TPS_ID.get()+"] was not numeric as expected.");
		
		
	}
	
	private Book bookAndValidate(HouseHold hh){
		Book book = new Book(environment, "NoComponentsNoAddOns");
		book.setParty(hh);
		book.setReservableResourceId();
	//	book.setFreezeId();
		book.sendRequest();
		if(book.getResponse().contains("existingRootChargeBookEvent :Unexpected Error occurred : createChargeGroupsAndPostCharges") ||
				book.getResponse().contains("Row was updated or deleted by another transaction")|| 
				book.getResponse().contains("existingRootChargeBookEvent") ||
				book.getResponse().toUpperCase().contains("FACILITY SERVICE UNAVAILABLE OR RETURED INVALID FACILITY")||
				book.getResponse().toLowerCase().contains("org.hibernate.exception.constraintviolationexception")){
			Sleeper.sleep(Randomness.randomNumberBetween(1, 10)*1000);
			book.sendRequest();
		}
		TestReporter.logAPI(!book.getResponseStatusCode().contains("200"), book.getFaultString() ,book);
		TestReporter.assertTrue(Regex.match("[0-9]+", book.getTravelPlanId()), "The travel plan ID ["+book.getTravelPlanId()+"] is not numeric as expected.");
		TestReporter.assertTrue(Regex.match("[0-9]+", book.getTravelPlanSegmentId()), "The reservation number ["+book.getTravelPlanSegmentId()+"] is not numeric as expected.");
		TPS_ID.set(book.getTravelPlanSegmentId());
		return book;
	}
	
	private void addAndValidateLogs(Book book){
		LogItems logItems = new LogItems();
	//	logItems.addItem("TableServiceDiningServiceIF", "book", false);
		logItems.addItem("AccommodationInventoryRequestComponentServiceIF", "createInventory", true);
	//	logItems.addItem("ChargeGroupIF", "createChargeGroupAndPostCharges", false);
		logItems.addItem("PartyIF", "createAndRetrieveParty", false);	
	//	logItems.addItem("TravelPlanServiceV3", "create", false);
	//	logItems.addItem("UpdateInventory", "updateInventory", false);
	//	logItems.addItem("TravelPlanServiceV3SEI", "create", false);
		logItems.addItem("GuestServiceV1", "create", false);
	//	logItems.addItem("GuestLinkServiceV1", "createEntitlementReference", false);
		logItems.addItem("PartyIF", "updateExternalPartyAndLocatorId", false);			
		
		if(environment.equalsIgnoreCase("Sleepy")){
			logItems.addItem("GuestServiceV1", "create", false); //Sleepy only
			logItems.addItem("GuestLinkServiceV1SEI", "createEntitlementReference", false); //Sleepy only
		}
			
		validateLogs(book, logItems, 10000);
	}
}
