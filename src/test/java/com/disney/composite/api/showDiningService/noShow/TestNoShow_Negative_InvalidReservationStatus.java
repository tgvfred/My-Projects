package com.disney.composite.api.showDiningService.noShow;

import org.testng.annotations.Test;

import com.disney.api.soapServices.showDiningService.operations.Arrived;
import com.disney.api.soapServices.showDiningService.operations.Book;
import com.disney.api.soapServices.showDiningService.operations.Cancel;
import com.disney.api.soapServices.showDiningService.operations.NoShow;
import com.disney.composite.BaseTest;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.database.LogItems;
import com.disney.utils.dataFactory.staging.bookSEReservation.ScheduledEventReservation;

public class TestNoShow_Negative_InvalidReservationStatus extends BaseTest{

	@Test(groups = {"api", "regression", "dining", "showDiningService"})
	public void cancelledReservation() {
		TestReporter.logScenario("Test Setting Cancelled Reservation to Arrived");
		Book book = book();
		
		Cancel cancel = new Cancel(environment, "CancelDiningEvent");
		cancel.setTravelPlanSegmentId(book.getTravelPlanSegmentId());
		cancel.sendRequest();
		
		noShow(book);
	}

	@Test(groups = {"api", "regression", "dining", "showDiningService"})
	public void arrivedReservation() {
		TestReporter.logScenario("Test Setting Arrivd Reservation to Arrived");
		Book book = book();
		
		Arrived arrived = new Arrived(environment, "ContactCenter");
		arrived.setReservatinoNumber(book.getTravelPlanSegmentId());
		arrived.sendRequest();
		
		noShow(book);
	}

	@Test(groups = {"api", "regression", "dining", "showDiningService"})
	public void noShowReservation() {
		TestReporter.logScenario("Test Setting Arrivd Reservation to Arrived");
		Book book = book();		

		NoShow noshow = new NoShow(environment, "ContactCenter");
		noshow.setReservatinoNumber(book.getTravelPlanSegmentId());
		noshow.sendRequest();
		
		noShow(book);
	}
	
	private Book book(){
		Book book = new Book(environment, ScheduledEventReservation.ONECOMPONENTSNOADDONS);
		book.setParty(hh);
		book.sendRequest();
		return book;
	}
	
	private void noShow(Book book){	
		NoShow noshow = new NoShow(environment, "ContactCenter");
		noshow.setReservatinoNumber(book.getTravelPlanSegmentId());
		noshow.sendRequest();
		TestReporter.logAPI(!noshow.getFaultString().contains(" Travel Status is invalid  : INVALID RESERVATION STATUS.CANNOT CHANGE THE STATUS TO NO-SHOW!"), noshow.getFaultString() ,noshow);

		LogItems logValidItems = new LogItems();
		logValidItems.addItem("ShowDiningServiceIF", "noShow", true);
		validateLogs(noshow, logValidItems);
		
		LogItems logInvalidItems = new LogItems();
		logInvalidItems.addItem("TravelPlanServiceCrossReferenceV3SEI", "updateOrder", false);
		logInvalidItems.addItem("ChargeGroupIF", "processNoShow", false);
		logInvalidItems.addItem("AccommodationInventoryRequestComponentServiceIF", "releaseInventory", false);
		logInvalidItems.addItem("PartyIF", "retrieveParty", false);
		logInvalidItems.addItem("PricingService", "getCancellationCharges", false);
		logInvalidItems.addItem("TravelPlanServiceCrossReferenceV3", "updateOrder", false);
		logInvalidItems.addItem("UpdateInventory", "updateInventory", false);
		validateNotInLogs(noshow, logInvalidItems);		
	}
}