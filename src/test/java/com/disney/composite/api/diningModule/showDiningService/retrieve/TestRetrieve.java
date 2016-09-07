package com.disney.composite.api.diningModule.showDiningService.retrieve;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import com.disney.api.soapServices.core.BaseSoapCommands;
import com.disney.api.soapServices.core.exceptions.XPathNotFoundException;
import com.disney.api.soapServices.diningModule.showDiningService.operations.Book;
import com.disney.api.soapServices.diningModule.showDiningService.operations.Cancel;
import com.disney.api.soapServices.diningModule.showDiningService.operations.Modify;
import com.disney.api.soapServices.diningModule.showDiningService.operations.Retrieve;
import com.disney.composite.BaseTest;
import com.disney.utils.Regex;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.database.LogItems;
import com.disney.utils.dataFactory.guestFactory.HouseHold;
import com.disney.utils.dataFactory.staging.bookSEReservation.ScheduledEventReservation;

public class TestRetrieve extends BaseTest{
	private Book book;
	
	@AfterMethod(alwaysRun=true)
	public void teardown(){
		try{
			Cancel cancel = new Cancel(environment, "CancelDiningEvent");
			cancel.setTravelPlanSegmentId(book.getTravelPlanSegmentId());
			cancel.sendRequest();
		}catch(Exception e){}
	}
	
	@Test(groups = {"api", "regression", "dining", "showDiningService", "negative"})
	public void testRetrieve(){
		TestReporter.logStep("Book a show dining reservation.");
		book = new Book(environment, ScheduledEventReservation.ONECOMPONENTSNOADDONS);
		book.setParty(hh);
		book.sendRequest();
		TestReporter.logAPI(!book.getResponseStatusCode().equals("200"), "An error occurred during booking: " + book.getFaultString(), book);		
		
		TestReporter.logStep("Retrieve a show dining reservation.");
		Retrieve retrieve = new Retrieve(environment, "RetrieveDiningEvent");
		retrieve.setReservationNumber(book.getTravelPlanSegmentId());
		retrieve.sendRequest();
		TestReporter.logAPI(!retrieve.getResponseStatusCode().equals("200"), "An error occurred retrieving an show dining service reservation", retrieve);
		TestReporter.assertEquals(hh.getAllGuests().size(), retrieve.getNumberOfGuests(), "The number of guests ["+retrieve.getNumberOfGuests()+"] did not match the expected number of guests ["+hh.getAllGuests().size()+"].");
		logItems(retrieve);
	}
	@Test(groups = {"api", "regression", "dining", "eventDiningService", "it4", "s138180" })
	public void testReservationWithAllergy(){
		hh = new HouseHold("1 Adult");
		Book book = new Book(environment, ScheduledEventReservation.ONECOMPONENTSNOADDONS);
		book.setParty(hh);
		book.setAllergies("Egg");
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
		Book book = new Book(environment, ScheduledEventReservation.ONECOMPONENTSNOADDONS);
		book.setParty(hh);		
		book.setAllergies("Egg");
		book.sendRequest();
		TestReporter.logAPI(!book.getResponseStatusCode().contains("200"), book.getFaultString() ,book);
		
		Modify modify = new Modify(this.environment, ScheduledEventReservation.ONECOMPONENTSNOADDONS);
		modify.setReservationNumber(book.getTravelPlanSegmentId());
		modify.setTravelPlanId(book.getTravelPlanId());
		modify.setParty(hh);
		modify.setFacilityId(book.getRequestFacilityId());
		modify.setServiceStartDate(book.getRequestServiceStartDate());
		modify.setServicePeriodId(book.getRequestServicePeriodId());
		modify.setProductId(book.getRequestProductId());
		modify.setAllergies(BaseSoapCommands.REMOVE_NODE.toString());
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
	
	
	private void logItems(Retrieve retrieve){
		LogItems logValidItems = new LogItems();
		logValidItems.addItem("ShowDiningServiceIF", "retrieve", true);
		logValidItems.addItem("FolioServiceIF", "retrieveAccountingTransactions", false);
		logValidItems.addItem("PartyIF", "retrieveParty", false);
		logValidItems.addItem("PartyIF", "retrievePartyBasicInformation", false);
		logValidItems.addItem("AccommodationInventoryRequestComponentServiceIF", "retrieveAssignmentOwner", false);
		logValidItems.addItem("PackagingService", "getProducts", false);
		validateLogs(retrieve, logValidItems, 10000);
	}
}