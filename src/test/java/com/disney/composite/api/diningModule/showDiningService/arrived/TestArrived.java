package com.disney.composite.api.diningModule.showDiningService.arrived;

import org.testng.annotations.AfterMethod;
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

public class TestArrived extends BaseTest{
	protected Book book;
	
	@AfterMethod(alwaysRun=true)
	public void teardown(){
		try{
			Cancel cancel = new Cancel(environment, "CancelDiningEvent");
			cancel.setTravelPlanSegmentId(book.getTravelPlanSegmentId());
			cancel.sendRequest();
		}catch(Exception e){}
	}
	
	@Test(groups = {"api", "regression", "dining", "showDiningService"})
	public void testArrived() {
		TestReporter.logStep("Book an show dining reservation.");
		book = new Book(environment, ScheduledEventReservation.ONECOMPONENTSNOADDONS);
		book.setParty(hh);
		book.sendRequest();
		TestReporter.logAPI(!book.getResponseStatusCode().equals("200"), "An error occurred during booking: " + book.getFaultString(), book);

		TestReporter.logStep("Update an show dining reservation to [Arrived].");
		Arrived arrived = new Arrived(environment, "ContactCenter");
		arrived.setReservationNumber(book.getTravelPlanSegmentId());
		arrived.sendRequest();
		TestReporter.logAPI(!arrived.getResponseStatusCode().equals("200"), "An error occurred updating an show dining service reservation to [Arrived]: " + arrived.getFaultString(), arrived);
		TestReporter.assertEquals(arrived.getResponseStatus(), "SUCCESS", "Arrived status ["+arrived.getResponseStatus()+"] was not 'SUCCESS' as expected.");
		
		LogItems logValidItems = new LogItems();
		logValidItems.addItem("ShowDiningServiceIF", "arrived", false);
//		logValidItems.addItem("TravelPlanServiceCrossReferenceV3SEI", "updateOrder", false);
		logValidItems.addItem("ChargeGroupIF", "checkIn", false);
		logValidItems.addItem("TravelPlanServiceCrossReferenceV3", "updateOrder", false);
		logValidItems.addItem("PartyIF", "retrieveParty", false);
		validateLogs(arrived, logValidItems, 10000);
	}

	@Test(groups = {"api", "regression", "dining", "showDiningService"})
	public void testArrived_DLR(){
		Book book = new Book(environment, "DLRDinnerShowOneAdultOneChild");
		book.setParty(hh);
		book.setServiceStartDateTime(Randomness.generateCurrentXMLDate(1));
		book.sendRequest();
		
		TestReporter.logAPI(!book.getResponseStatusCode().contains("200"), book.getFaultString() ,book);
		TestReporter.assertTrue(Regex.match("[0-9]+", book.getTravelPlanId()), "The travel plan ID ["+book.getTravelPlanId()+"] is numeric as expected.");
		TestReporter.assertTrue(Regex.match("[0-9]+", book.getTravelPlanSegmentId()), "The reservation number ["+book.getTravelPlanSegmentId()+"] is numeric as expected.");
		
		
		Arrived arrived = new Arrived(this.environment,"ContactCenter");
		arrived.setReservationNumber(book.getTravelPlanSegmentId());
		arrived.sendRequest();
		TestReporter.logAPI(!arrived.getResponseStatusCode().contains("200"), arrived.getFaultString() ,arrived);
		TestReporter.logAPI(!arrived.getResponseStatus().equals("SUCCESS"), "The response ["+arrived.getResponseStatus()+"] was not 'SUCCESS' as expected.", arrived);
		

		LogItems logItems = new LogItems();
		logItems.addItem("ChargeGroupIF", "checkIn", false);
		logItems.addItem("EventDiningServiceIF", "arrived", false);
		logItems.addItem("TravelPlanServiceCrossReferenceV3", "updateOrder", false);
		logItems.addItem("TravelPlanServiceCrossReferenceV3SEI", "updateOrder", false);
		validateLogs(arrived, logItems);

	}
	
}