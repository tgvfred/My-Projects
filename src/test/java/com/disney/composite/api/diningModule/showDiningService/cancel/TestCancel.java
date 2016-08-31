package com.disney.composite.api.diningModule.showDiningService.cancel;

import org.testng.annotations.Test;

import com.disney.api.soapServices.diningModule.showDiningService.operations.Arrived;
import com.disney.api.soapServices.diningModule.showDiningService.operations.Book;
import com.disney.api.soapServices.diningModule.showDiningService.operations.Cancel;
import com.disney.composite.BaseTest;
import com.disney.utils.Randomness;
import com.disney.utils.Regex;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.database.LogItems;
import com.disney.utils.dataFactory.staging.bookSEReservation.ScheduledEventReservation;

public class TestCancel extends BaseTest{

	@Test(groups = {"api", "regression", "dining", "showDiningService"})
	public void testCancel() {
		TestReporter.logStep("Book a show dining reservation.");
		Book book = new Book(environment, ScheduledEventReservation.ONECOMPONENTSNOADDONS);
		book.setParty(hh);
		book.sendRequest();
		TestReporter.logAPI(!book.getResponseStatusCode().equals("200"), "An error occurred during booking: " + book.getFaultString(), book);
		
		TestReporter.logStep("Cancel an existing reservation.");
		Cancel cancel = new Cancel(environment, "CancelDiningEvent");
		cancel.setTravelPlanSegmentId(book.getTravelPlanSegmentId());
		cancel.sendRequest();
		TestReporter.logAPI(!cancel.getResponseStatusCode().equals("200"), "An error occurred during cancellation: " + cancel.getFaultString(), cancel);
		TestReporter.assertTrue(Regex.match("[0-9]+", cancel.getCancellationConfirmationNumber()), "The cancellation number ["+cancel.getCancellationConfirmationNumber()+"] was not numeric as expected.");
		
		logItems(cancel);
	}

	@Test(groups = {"api", "regression", "dining", "showDiningService"})
	public void testCancel_DLR(){
		Book book = new Book(environment, "DLRDinnerShowOneAdultOneChild");
		book.setParty(hh);
		book.setServiceStartDateTime(Randomness.generateCurrentXMLDate(1));
		book.sendRequest();
		
		TestReporter.logAPI(!book.getResponseStatusCode().contains("200"), book.getFaultString() ,book);
		TestReporter.assertTrue(Regex.match("[0-9]+", book.getTravelPlanId()), "The travel plan ID ["+book.getTravelPlanId()+"] is numeric as expected.");
		TestReporter.assertTrue(Regex.match("[0-9]+", book.getTravelPlanSegmentId()), "The reservation number ["+book.getTravelPlanSegmentId()+"] is numeric as expected.");
		
		TestReporter.logStep("Cancel an existing reservation.");
		Cancel cancel = new Cancel(environment, "CancelDiningEvent");
		cancel.setTravelPlanSegmentId(book.getTravelPlanSegmentId());
		cancel.sendRequest();
		TestReporter.logAPI(!cancel.getResponseStatusCode().equals("200"), "An error occurred during cancellation: " + cancel.getFaultString(), cancel);
		TestReporter.assertTrue(Regex.match("[0-9]+", cancel.getCancellationConfirmationNumber()), "The cancellation number ["+cancel.getCancellationConfirmationNumber()+"] was not numeric as expected.");
		
		logItems(cancel);
	}
	private void logItems(Cancel cancel){
		LogItems logValidItems = new LogItems();
		logValidItems.addItem("ShowDiningServiceIF", "cancel", false);
		logValidItems.addItem("TravelPlanServiceCrossReferenceV3", "cancelOrder", false);
		logValidItems.addItem("ChargeGroupIF", "cancelChargeGroups", false);
		logValidItems.addItem("PartyIF", "retrieveParty", false);
		logValidItems.addItem("AccommodationInventoryRequestComponentServiceIF", "releaseInventory", false);
//		logValidItems.addItem("ravelPlanServiceCrossReferenceV3SEI", "cancelOrder", false);
		logValidItems.addItem("UpdateInventory", "updateInventory", false);
		validateLogs(cancel, logValidItems, 10000);
	}
}