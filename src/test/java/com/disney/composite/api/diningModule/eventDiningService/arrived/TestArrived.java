package com.disney.composite.api.diningModule.eventDiningService.arrived;

import org.testng.annotations.Test;

import com.disney.api.soapServices.diningModule.eventDiningService.operations.Arrived;
import com.disney.api.soapServices.diningModule.eventDiningService.operations.Book;
import com.disney.composite.BaseTest;
import com.disney.utils.Randomness;
import com.disney.utils.Regex;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.database.LogItems;
import com.disney.utils.dataFactory.guestFactory.HouseHold;
import com.disney.utils.dataFactory.staging.bookSEReservation.EventDiningReservation;
import com.disney.utils.dataFactory.staging.bookSEReservation.ScheduledEventReservation;

public class TestArrived  extends BaseTest{
	// Defining global variables
	protected String TPS_ID = null;
	protected ScheduledEventReservation res = null;

	@Test(groups = {"api", "regression", "dining", "eventDiningService"})
	public void testArrived(){
		ScheduledEventReservation res = null;
		hh = new HouseHold(1);
		res = new EventDiningReservation(this.environment, hh);
		res.setServiceStartDate(Randomness.generateCurrentXMLDatetime(Randomness.randomNumberBetween(15, 45)));
		res.book( ScheduledEventReservation.NOCOMPONENTSNOADDONS);
		
		
		Arrived arrived = new Arrived(this.environment,"Main");
		arrived.setReservationNumber(res.getConfirmationNumber());
		arrived.sendRequest();
		TestReporter.logAPI(!arrived.getResponseStatusCode().contains("200"), arrived.getFaultString() ,arrived);
		TestReporter.logAPI(!arrived.getArrivalStatus().equals("SUCCESS"), "The response ["+arrived.getArrivalStatus()+"] was not 'SUCCESS' as expected.", arrived);
		

		LogItems logItems = new LogItems();
		logItems.addItem("ChargeGroupIF", "checkIn", false);
		logItems.addItem("EventDiningServiceIF", "arrived", false);
		logItems.addItem("TravelPlanServiceCrossReferenceV3", "updateOrder", false);
		logItems.addItem("TravelPlanServiceCrossReferenceV3SEI", "updateOrder", false);
		validateLogs(arrived, logItems);

	}
	
	@Test(groups = {"api", "regression", "dining", "eventDiningService"})
	public void testArrived_DLR(){
		Book book = new Book(environment, "DLRTableServiceOneChild");
		book.setParty(hh);
		book.setServiceStartDateTime(Randomness.generateCurrentXMLDatetime(Randomness.randomNumberBetween(15, 45)));
		book.sendRequest();
		
		TestReporter.logAPI(!book.getResponseStatusCode().contains("200"), book.getFaultString() ,book);
		TestReporter.assertTrue(Regex.match("[0-9]+", book.getTravelPlanId()), "The travel plan ID ["+book.getTravelPlanId()+"] is numeric as expected.");
		TestReporter.assertTrue(Regex.match("[0-9]+", book.getTravelPlanSegmentId()), "The reservation number ["+book.getTravelPlanSegmentId()+"] is numeric as expected.");
		
		
		Arrived arrived = new Arrived(this.environment,"Main");
		arrived.setReservationNumber(book.getTravelPlanSegmentId());
		arrived.sendRequest();
		TestReporter.logAPI(!arrived.getResponseStatusCode().contains("200"), arrived.getFaultString() ,arrived);
		TestReporter.logAPI(!arrived.getArrivalStatus().equals("SUCCESS"), "The response ["+arrived.getArrivalStatus()+"] was not 'SUCCESS' as expected.", arrived);
		

		LogItems logItems = new LogItems();
		logItems.addItem("ChargeGroupIF", "checkIn", false);
		logItems.addItem("EventDiningServiceIF", "arrived", false);
		logItems.addItem("TravelPlanServiceCrossReferenceV3", "updateOrder", false);
		logItems.addItem("TravelPlanServiceCrossReferenceV3SEI", "updateOrder", false);
		validateLogs(arrived, logItems);

	}
	
	@Test(groups = {"api", "regression", "dining", "eventDiningService", "debug"})
	public void testArriveTrueDiningReservation(){
		Book book = new Book(environment, ScheduledEventReservation.NOCOMPONENTSNOADDONS);
		book.setParty(new HouseHold(1));
		book.setServiceStartDateTime(Randomness.generateCurrentXMLDate(Randomness.randomNumberBetween(20, 50)));
		book.setReservableResourceId("BA054CBB-D573-C672-BE95-173042178DBE");
		book.addDetailsByFacilityNameAndProductName("The Hollywood Brown Derby", "Brown Derby Lunch F! 1st Show");
		book.addSpecialEventByProductName("Fantasmic! Viewing 1st Show");
		book.sendRequest();
		
		TestReporter.logAPI(!book.getResponseStatusCode().contains("200"), "An error occurred bookingan event dining reservation: " + book.getFaultString() ,book);
		TestReporter.assertTrue(Regex.match("[0-9]+", book.getTravelPlanId()), "The travel plan ID ["+book.getTravelPlanId()+"] is numeric as expected.");
		TestReporter.assertTrue(Regex.match("[0-9]+", book.getTravelPlanSegmentId()), "The reservation number ["+book.getTravelPlanSegmentId()+"] is numeric as expected.");
		TPS_ID=book.getTravelPlanSegmentId();
		
		Arrived arrived = new Arrived(this.environment,"Main");
		arrived.setReservationNumber(book.getTravelPlanSegmentId());
		arrived.sendRequest();
		TestReporter.logAPI(!arrived.getResponseStatusCode().contains("200"), arrived.getFaultString() ,arrived);
		TestReporter.logAPI(!arrived.getArrivalStatus().equals("SUCCESS"), "The response ["+arrived.getArrivalStatus()+"] was not 'SUCCESS' as expected.", arrived);
			
	}
}
