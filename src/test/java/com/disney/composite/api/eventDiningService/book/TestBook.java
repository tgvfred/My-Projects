package com.disney.composite.api.eventDiningService.book;

import org.testng.annotations.Test;

import com.disney.api.soapServices.__OLD__eventDiningService.operations.Book;
import com.disney.api.soapServices.scheduledEventsServicePort.operations.RetrieveAllergies;
import com.disney.composite.BaseTest;
import com.disney.utils.Regex;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.database.LogItems;
import com.disney.utils.dataFactory.guestFactory.HouseHold;

public class TestBook extends BaseTest{
	// Defining global variables
	protected String TPS_ID = null;
	
	@Test(groups = {"api", "regression", "dining", "eventDiningService"})
	public void testBook(){
		hh = new HouseHold(1);
		Book book = new Book(environment, "NoComponentsNoAddOns");
		book.setParty(hh);
		book.sendRequest();
		TestReporter.logAPI(!book.getResponseStatusCode().contains("200"), book.getFaultString() ,book);
		TestReporter.assertTrue(Regex.match("[0-9]+", book.getTravelPlanId()), "The travel plan ID ["+book.getTravelPlanId()+"] is not numeric as expected.");
		TestReporter.assertTrue(Regex.match("[0-9]+", book.getTravelPlanSegmentId()), "The reservation number ["+book.getTravelPlanSegmentId()+"] is not numeric as expected.");
		TPS_ID=book.getTravelPlanSegmentId();
		
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

	@Test(groups = {"api", "regression", "dining", "eventDiningService"})
	public void testBookWith2Adults(){
		hh = new HouseHold("2 Adults");
		Book book = new Book(environment, "NoComponentsNoAddOns");
		book.setParty(hh);
		book.sendRequest();
		TestReporter.logAPI(!book.getResponseStatusCode().contains("200"), book.getFaultString() ,book);
		TestReporter.assertTrue(Regex.match("[0-9]+", book.getTravelPlanId()), "The travel plan ID ["+book.getTravelPlanId()+"] is not numeric as expected.");
		TestReporter.assertTrue(Regex.match("[0-9]+", book.getTravelPlanSegmentId()), "The reservation number ["+book.getTravelPlanSegmentId()+"] is not numeric as expected.");
		TPS_ID=book.getTravelPlanSegmentId();
		
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

	@Test(groups = {"api", "regression", "dining", "eventDiningService"})
	public void testBookWith4Adults(){
		hh = new HouseHold("4 Adults");
		Book book = new Book(environment, "NoComponentsNoAddOns");
		book.setParty(hh);
		book.sendRequest();
		TestReporter.logAPI(!book.getResponseStatusCode().contains("200"), book.getFaultString() ,book);
		TestReporter.assertTrue(Regex.match("[0-9]+", book.getTravelPlanId()), "The travel plan ID ["+book.getTravelPlanId()+"] is not numeric as expected.");
		TestReporter.assertTrue(Regex.match("[0-9]+", book.getTravelPlanSegmentId()), "The reservation number ["+book.getTravelPlanSegmentId()+"] is not numeric as expected.");
		TPS_ID=book.getTravelPlanSegmentId();
		
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

	@Test(groups = {"api", "regression", "dining", "eventDiningService"})
	public void testBookWith2Adults2Child(){
		hh = new HouseHold("2 Adults 2 Child");
		Book book = new Book(environment, "NoComponentsNoAddOns");
		book.setParty(hh);
		book.sendRequest();
		TestReporter.logAPI(!book.getResponseStatusCode().contains("200"), book.getFaultString() ,book);
		TestReporter.assertTrue(Regex.match("[0-9]+", book.getTravelPlanId()), "The travel plan ID ["+book.getTravelPlanId()+"] is not numeric as expected.");
		TestReporter.assertTrue(Regex.match("[0-9]+", book.getTravelPlanSegmentId()), "The reservation number ["+book.getTravelPlanSegmentId()+"] is not numeric as expected.");
		TPS_ID=book.getTravelPlanSegmentId();
		
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

	@Test(groups = {"api", "regression", "dining", "eventDiningService"})
	public void testBookWith4Adults2Child2Infant(){
		hh = new HouseHold("4 Adults 2 Child 2 Infant");
		Book book = new Book(environment, "NoComponentsNoAddOns");
		book.setParty(hh);
		book.sendRequest();
		TestReporter.logAPI(!book.getResponseStatusCode().contains("200"), book.getFaultString() ,book);
		TestReporter.assertTrue(Regex.match("[0-9]+", book.getTravelPlanId()), "The travel plan ID ["+book.getTravelPlanId()+"] is not numeric as expected.");
		TestReporter.assertTrue(Regex.match("[0-9]+", book.getTravelPlanSegmentId()), "The reservation number ["+book.getTravelPlanSegmentId()+"] is not numeric as expected.");
		TPS_ID=book.getTravelPlanSegmentId();
		
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
	

	@Test(groups = {"api", "regression", "dining", "eventDiningService"})
	public void testBookWith12InParty(){
		hh = new HouseHold(12);
		Book book = new Book(environment, "NoComponentsNoAddOns");
		book.setParty(hh);
		book.sendRequest();
		TestReporter.logAPI(!book.getResponseStatusCode().contains("200"), book.getFaultString() ,book);
		TestReporter.assertTrue(Regex.match("[0-9]+", book.getTravelPlanId()), "The travel plan ID ["+book.getTravelPlanId()+"] is not numeric as expected.");
		TestReporter.assertTrue(Regex.match("[0-9]+", book.getTravelPlanSegmentId()), "The reservation number ["+book.getTravelPlanSegmentId()+"] is not numeric as expected.");
		TPS_ID=book.getTravelPlanSegmentId();
		
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

	@Test(groups = {"api", "regression", "dining", "eventDiningService", "it4", "s138180" })
	public void testAddAllergy(){
		hh = new HouseHold("1 Adult");
		Book book = new Book(environment, "NoComponentsNoAddOns");
		book.setParty(hh);
		book.setAllergies("Egg","1");
		book.sendRequest();
		TestReporter.logAPI(!book.getResponseStatusCode().contains("200"), book.getFaultString() ,book);
		TestReporter.assertTrue(Regex.match("[0-9]+", book.getTravelPlanId()), "The travel plan ID ["+book.getTravelPlanId()+"] is not numeric as expected.");
		TestReporter.assertTrue(Regex.match("[0-9]+", book.getTravelPlanSegmentId()), "The reservation number ["+book.getTravelPlanSegmentId()+"] is not numeric as expected.");
		TPS_ID=book.getTravelPlanSegmentId();
		
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
	

	@Test(groups = {"api", "regression", "dining", "eventDiningService", "it4", "s138180" })
	public void testAddTwoAllergies(){
		hh = new HouseHold("1 Adult");
		Book book = new Book(environment, "NoComponentsNoAddOns");
		book.setParty(hh);
		book.setAllergies("Egg","1");
		book.setAllergies("Corn","2");		
		book.sendRequest();
		
		TestReporter.logAPI(!book.getResponseStatusCode().contains("200"), book.getFaultString() ,book);
		TestReporter.assertTrue(Regex.match("[0-9]+", book.getTravelPlanId()), "The travel plan ID ["+book.getTravelPlanId()+"] is not numeric as expected.");
		TestReporter.assertTrue(Regex.match("[0-9]+", book.getTravelPlanSegmentId()), "The reservation number ["+book.getTravelPlanSegmentId()+"] is not numeric as expected.");
		TPS_ID=book.getTravelPlanSegmentId();
		
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
	

	@Test(groups = {"api", "regression", "dining", "eventDiningService", "it4", "s138180" })
	public void testAddAllAllergies(){
		hh = new HouseHold(1);
		Book book = new Book(environment, "NoComponentsNoAddOns");
		book.setParty(hh);
		
		RetrieveAllergies retrieveAllergies = new RetrieveAllergies(environment);
		retrieveAllergies.sendRequest();

		int index = 1;
		for(String allergy : retrieveAllergies.getAllergies().values()){
			book.setAllergies(allergy,String.valueOf(index));
			index++;
		}
		
		book.sendRequest();
		TestReporter.logAPI(!book.getResponseStatusCode().contains("200"), book.getFaultString() ,book);
		TestReporter.assertTrue(Regex.match("[0-9]+", book.getTravelPlanId()), "The travel plan ID ["+book.getTravelPlanId()+"] is not numeric as expected.");
		TestReporter.assertTrue(Regex.match("[0-9]+", book.getTravelPlanSegmentId()), "The reservation number ["+book.getTravelPlanSegmentId()+"] is not numeric as expected.");
		TPS_ID=book.getTravelPlanSegmentId();
		
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