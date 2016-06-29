package com.disney.composite.api.showDiningService.arrived;

import org.testng.annotations.Test;

import com.disney.api.soapServices.showDiningService.operations.Arrived;
import com.disney.api.soapServices.showDiningService.operations.Book;
import com.disney.api.soapServices.showDiningService.operations.Cancel;
import com.disney.composite.BaseTest;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.database.LogItems;
import com.disney.utils.dataFactory.staging.bookSEReservation.ScheduledEventReservation;

public class TestArrived_Negative_InvalidReservationStatus extends BaseTest{

	@Test(groups = {"api", "regression", "dining", "showDiningService"})
	public void cancelledReservation() {
		TestReporter.logScenario("Test Setting Cancelled Reservation to Arrived");
		Book book = book();
		
		Cancel cancel = new Cancel(environment, "CancelDiningEvent");
		cancel.setTravelPlanSegmentId(book.getTravelPlanSegmentId());
		cancel.sendRequest();
		
		arrived(book);
	}

	@Test(groups = {"api", "regression", "dining", "showDiningService"})
	public void arrivedReservation() {
		TestReporter.logScenario("Test Setting Arrivd Reservation to Arrived");
		Book book = book();
		
		Arrived arrived = new Arrived(environment, "ContactCenter");
		arrived.setReservatinoNumber(book.getTravelPlanSegmentId());
		arrived.sendRequest();
		
		arrived(book);
	}
	
	private Book book(){
		Book book = new Book(environment, ScheduledEventReservation.ONECOMPONENTSNOADDONS);
		book.setParty(hh);
		book.sendRequest();
		return book;
	}
	
	private void arrived(Book book){		
		Arrived arrived = new Arrived(environment, "ContactCenter");
		arrived.setReservatinoNumber(book.getTravelPlanSegmentId());
		arrived.sendRequest();
		TestReporter.logAPI(!arrived.getFaultString().contains(" Travel Status is invalid  : INVALID RESERVATION STATUS.CANNOT CHANGE THE STATUS TO ARRIVED!"), arrived.getFaultString() ,arrived);

		LogItems logValidItems = new LogItems();
		logValidItems.addItem("ShowDiningServiceIF", "arrived", true);
		validateLogs(arrived, logValidItems);
		
		LogItems logInvalidItems = new LogItems();
		logInvalidItems.addItem("TravelPlanServiceCrossReferenceV3SEI", "updateOrder", false);
		logInvalidItems.addItem("ChargeGroupIF", "checkIn", false);
		logInvalidItems.addItem("TravelPlanServiceCrossReferenceV3", "updateOrder", false);
		logInvalidItems.addItem("PartyIF", "retrieveParty", false);
		validateNotInLogs(arrived, logInvalidItems);		
	}
}