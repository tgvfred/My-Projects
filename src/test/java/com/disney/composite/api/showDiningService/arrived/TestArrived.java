package com.disney.composite.api.showDiningService.arrived;

import org.testng.annotations.Test;

import com.disney.api.soapServices.showDiningService.operations.Arrived;
import com.disney.api.soapServices.showDiningService.operations.Book;
import com.disney.composite.BaseTest;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.database.LogItems;
import com.disney.utils.dataFactory.staging.bookSEReservation.ScheduledEventReservation;

public class TestArrived extends BaseTest{
	
	@Test(groups = {"api", "regression", "dining", "showDiningService"})
	public void testArrived() {
		TestReporter.logStep("Book an show dining reservation.");
		Book book = new Book(environment, ScheduledEventReservation.ONECOMPONENTSNOADDONS);
		book.setParty(hh);
		book.sendRequest();
		TestReporter.logAPI(!book.getResponseStatusCode().equals("200"), "An error occurred during booking", book);

		TestReporter.logStep("Update an show dining reservation to [Arrived].");
		Arrived arrived = new Arrived(environment, "ContactCenter");
		arrived.setReservationNumber(book.getTravelPlanSegmentId());
		arrived.sendRequest();
		TestReporter.logAPI(!arrived.getResponseStatusCode().equals("200"), "An error occurred updating an show dining service reservation to [Arrived]", arrived);
		TestReporter.assertEquals(arrived.getResponseStatus(), "SUCCESS", "Arrived status ["+arrived.getResponseStatus()+"] was not 'SUCCESS' as expected.");
		
		LogItems logValidItems = new LogItems();
		logValidItems.addItem("ShowDiningServiceIF", "arrived", true);
		logValidItems.addItem("TravelPlanServiceCrossReferenceV3SEI", "updateOrder", true);
		logValidItems.addItem("ChargeGroupIF", "checkIn", true);
		logValidItems.addItem("TravelPlanServiceCrossReferenceV3", "updateOrder", true);
		logValidItems.addItem("PartyIF", "retrieveParty", true);
		validateLogs(arrived, logValidItems);
	}
}