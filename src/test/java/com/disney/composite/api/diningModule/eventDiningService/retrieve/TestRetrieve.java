package com.disney.composite.api.diningModule.eventDiningService.retrieve;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.diningModule.eventDiningService.operations.Book;
import com.disney.api.soapServices.diningModule.eventDiningService.operations.Modify;
import com.disney.api.soapServices.diningModule.eventDiningService.operations.Retrieve;
import com.disney.api.soapServices.core.BaseSoapCommands;
import com.disney.api.soapServices.core.exceptions.XPathNotFoundException;
import com.disney.composite.BaseTest;
import com.disney.utils.Regex;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.database.LogItems;
import com.disney.utils.dataFactory.guestFactory.HouseHold;

public class TestRetrieve extends BaseTest{
	// Defining global variables
	protected String TPS_ID = null;
	Book book = null;
	
	@Override
	@BeforeClass(alwaysRun = true)
	@Parameters({ "environment" })
	public void setup(@Optional String environment){
		this.environment = environment;
		hh = new HouseHold(1);
		book = new Book(this.environment, "NoComponentsNoAddOns");
		book.setParty(hh);
		book.sendRequest();
		TestReporter.logAPI(!book.getResponseStatusCode().equals("200"), "An error occurred during booking: " + book.getFaultString(), book);
	}
	@Test(groups = {"api", "regression", "dining", "eventDiningService"})
	public void testRetrieve(){
		Retrieve retrieve = new Retrieve(this.environment, "RetrieveDiningEvent");
		retrieve.setReservationNumber(book.getTravelPlanSegmentId());
		retrieve.sendRequest();
		TestReporter.logAPI(!retrieve.getResponseStatusCode().equals("200"), retrieve.getFaultString(), retrieve);
		TestReporter.logAPI(!Regex.match("[0-9]+", retrieve.getPartyId()), "The Party Id ["+retrieve.getPartyId()+"] was not numeric as expected.",retrieve);
		LogItems logItems = new LogItems();
		
		logItems.addItem("AccommodationInventoryRequestComponentServiceIF", "retrieveAssignmentOwner", false);
		logItems.addItem("EventDiningServiceIF", "retrieve", false);
		logItems.addItem("FolioServiceIF", "retrieveAccountingTransactions", false);
		logItems.addItem("PartyIF", "retrieveParty", false);
		logItems.addItem("PackagingService", "getProducts", false);
		logItems.addItem("PartyIF", "retrievePartyBasicInformation", false);
		logItems.addItem("PricingService", "priceComponents", false);
		validateLogs(retrieve, logItems);
	}
	
	@Test(groups = {"api", "regression", "dining", "eventDiningService", "it4", "s138180" })
	public void testReservationWithAllergy(){
		hh = new HouseHold("1 Adult");
		Book book = new Book(environment, "NoComponentsNoAddOns");
		book.setParty(hh);
		book.setAllergies("Egg","1");
		book.sendRequest();
		TestReporter.logAPI(!book.getResponseStatusCode().contains("200"), book.getFaultString() ,book);
		
		Retrieve retrieve = new Retrieve(this.environment, "RetrieveDiningEvent");		
		retrieve.setReservationNumber(book.getTravelPlanSegmentId());
		retrieve.sendRequest();
		TestReporter.logAPI(!retrieve.getResponseStatusCode().equals("200"), retrieve.getFaultString(), retrieve);
		TestReporter.logAPI(!Regex.match("[0-9]+", retrieve.getPartyId()), "The Party Id ["+retrieve.getPartyId()+"] was not numeric as expected.",retrieve);
		String allergy = ""; 
		try{
			allergy = retrieve.getAllergies("1");
		}catch(XPathNotFoundException xpe){
			TestReporter.logAPI(true, "No Allergies were returned in the response", retrieve);
		}
		TestReporter.logAPI(!retrieve.getAllergies("1").equals("Egg"), "The found ["+retrieve.getAllergies("1") + "] was not [Egg] as expected.",retrieve);
	}
	
	@Test(groups = {"api", "regression", "dining", "eventDiningService", "it4", "s138180" })
	public void testReservationWithRemovedAllergy(){
		Book book = new Book(environment, "NoComponentsNoAddOns");
		book.setParty(hh);		
		book.setAllergies("Egg", "1");
		book.sendRequest();
		TestReporter.logAPI(!book.getResponseStatusCode().contains("200"), book.getFaultString() ,book);
		
		Modify modify = new Modify(this.environment, "NoComponentsNoAddOns");
		modify.setReservationNumber(book.getTravelPlanSegmentId());
		modify.setTravelPlanId(book.getTravelPlanId());
		modify.setParty(hh);
		modify.setFacilityId(book.getRequestFacilityId());
		modify.setServiceStartDate(book.getRequestServiceStartDate());
		modify.setServicePeriodId(book.getRequestServicePeriodId());
		modify.setProductId(book.getRequestProductId());
		modify.setAllergies(BaseSoapCommands.REMOVE_NODE.toString(), "1");
		modify.sendRequest();
		TestReporter.logAPI(!modify.getResponseStatusCode().contains("200"), modify.getFaultString() ,modify);
		
		Retrieve retrieve = new Retrieve(this.environment, "RetrieveDiningEvent");		
		retrieve.setReservationNumber(book.getTravelPlanSegmentId());
		retrieve.sendRequest();
		TestReporter.logAPI(!retrieve.getResponseStatusCode().equals("200"), retrieve.getFaultString(), retrieve);
		TestReporter.logAPI(!Regex.match("[0-9]+", retrieve.getPartyId()), "The Party Id ["+retrieve.getPartyId()+"] was not numeric as expected.",retrieve);
		String allergy = ""; 
		try{
			allergy = retrieve.getAllergies("1");
			TestReporter.logAPI(true, "Allergies were returned in the response when not expected", retrieve);
		}catch(XPathNotFoundException throwAway){
		// Passing - Do nothing	
		}
	}
	
}
