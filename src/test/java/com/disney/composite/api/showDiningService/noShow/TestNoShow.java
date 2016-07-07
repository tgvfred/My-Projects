package com.disney.composite.api.showDiningService.noShow;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.showDiningService.operations.Book;
import com.disney.api.soapServices.showDiningService.operations.NoShow;
import com.disney.composite.BaseTest;
import com.disney.utils.Regex;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.database.LogItems;
import com.disney.utils.dataFactory.guestFactory.HouseHold;
import com.disney.utils.dataFactory.staging.bookSEReservation.ScheduledEventReservation;

public class TestNoShow extends BaseTest{
	
	@Override
	@BeforeMethod(alwaysRun=true)
	@Parameters("environment")
	public void setup(@Optional String environment){
		this.environment = environment;
		hh = new HouseHold(1);
	}

	@Test(groups = {"api", "regression", "dining", "showDiningService"})
	public void testNoShow() {
		TestReporter.logStep("Book a show dining reservation.");
		Book book = new Book(environment, ScheduledEventReservation.ONECOMPONENTSNOADDONS);
		book.setParty(hh);
		book.sendRequest();
		TestReporter.logAPI(!book.getResponseStatusCode().equals("200"), "An error occurred during booking", book);

		TestReporter.logStep("Update a show dining reservation to [No Show].");
		NoShow noShow = new NoShow(environment, "GuestFacing");
		noShow.setReservatinoNumber(book.getTravelPlanSegmentId());
		noShow.sendRequest();
		TestReporter.logAPI(!noShow.getResponseStatusCode().equals("200"), "An error occurred updating an show dining service reservation to [No Show]", noShow);
		TestReporter.assertTrue(Regex.match("[0-9]+", noShow.getCancellationConfirmationNumber()), "The cancellation number ["+noShow.getCancellationConfirmationNumber()+"] was not numeric as expected.");
		logItems(noShow);
	}
	
	private void logItems(NoShow noShow){
		LogItems logValidItems = new LogItems();
		logValidItems.addItem("ShowDiningServiceIF", "noShow", false);
		logValidItems.addItem("TravelPlanServiceCrossReferenceV3SEI", "updateOrder", false);
		logValidItems.addItem("ChargeGroupIF", "processNoShow", false);
		logValidItems.addItem("PartyIF", "retrieveParty", false);
		logValidItems.addItem("AccommodationInventoryRequestComponentServiceIF", "releaseInventory", false);
		logValidItems.addItem("PricingService", "getCancellationCharges", false);
		logValidItems.addItem("TravelPlanServiceCrossReferenceV3", "updateOrder", false);
		logValidItems.addItem("UpdateInventory", "updateInventory", false);
		validateLogs(noShow, logValidItems);
	}
}
