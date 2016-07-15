package com.disney.composite.api.showDiningService.cancel;

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

public class TestCancel extends BaseTest{
	protected HouseHold hh = null;
	
	@Override
	@BeforeMethod(alwaysRun=true)
	@Parameters("environment")
	public void setup(@Optional String environment){
		this.environment = environment;
		hh = new HouseHold(1);
	}

	@Test(groups = {"api", "regression", "dining", "showDiningService"})
	public void testCancel() {
		TestReporter.logStep("Book a show dining reservation.");
		Book book = new Book(environment, ScheduledEventReservation.ONECOMPONENTSNOADDONS);
		book.setParty(hh);
		book.sendRequest();
		TestReporter.logAPI(!book.getResponseStatusCode().equals("200"), "An error occurred during booking", book);
		
		TestReporter.logStep("Cancel an existing reservation.");
		Cancel cancel = new Cancel(environment, "CancelDiningEvent");
		cancel.setTravelPlanSegmentId(book.getTravelPlanSegmentId());
		cancel.sendRequest();
		TestReporter.logAPI(!cancel.getResponseStatusCode().equals("200"), "An error occurred during cancellation", cancel);
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
		logValidItems.addItem("ravelPlanServiceCrossReferenceV3SEI", "cancelOrder", false);
		logValidItems.addItem("UpdateInventory", "updateInventory", false);
		validateLogs(cancel, logValidItems, 10000);
	}
}