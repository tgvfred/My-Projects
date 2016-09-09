package com.disney.composite.api.diningModule.eventDiningService.noShow;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.BaseTest;
import com.disney.api.soapServices.diningModule.eventDiningService.operations.Arrived;
import com.disney.api.soapServices.diningModule.eventDiningService.operations.Book;
import com.disney.api.soapServices.diningModule.eventDiningService.operations.NoShow;
import com.disney.api.soapServices.folioModule.folioServicePort.operations.CreateSettlementMethod;
import com.disney.utils.Randomness;
import com.disney.utils.Regex;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.database.LogItems;
import com.disney.utils.dataFactory.folioInterface.FolioInterfaceSettlement;
import com.disney.utils.dataFactory.guestFactory.HouseHold;
import com.disney.utils.dataFactory.staging.bookSEReservation.EventDiningReservation;
import com.disney.utils.dataFactory.staging.bookSEReservation.ScheduledEventReservation;

public class TestNoShow extends BaseTest{


	@Test(groups = {"api", "regression", "dining", "eventDiningService"})
	public void testNoShow(){
		hh = new HouseHold(1);
		ScheduledEventReservation res = new EventDiningReservation(this.environment, hh);
		res.book(ScheduledEventReservation.NOCOMPONENTSNOADDONS);
		NoShow noShow = new NoShow(environment, "Main");
		noShow.setReservationNumber(res.getConfirmationNumber());
		noShow.sendRequest();
		
		TestReporter.logAPI(!noShow.getResponseStatusCode().contains("200"), noShow.getFaultString() ,noShow);
		TestReporter.assertTrue(Regex.match("[0-9]+", noShow.getCancellationNumber()), "The cancellation number ["+noShow.getCancellationNumber()+"] was not numeric as expected.");		

		LogItems logItems = new LogItems();
		logItems.addItem("ChargeGroupIF", "processNoShow", false);
		logItems.addItem("EventDiningServiceIF", "noShow", false);
		logItems.addItem("PricingService", "getCancellationCharges", false);
		logItems.addItem("TravelPlanServiceCrossReferenceV3", "updateOrder", false);
		logItems.addItem("TravelPlanServiceCrossReferenceV3SEI", "updateOrder", false);
		logItems.addItem("UpdateInventory", "updateInventory", false);
		validateLogs(noShow, logItems);
	}

	@Test(groups = {"api", "regression", "dining", "eventDiningService"})
	public void testNoShow_DLR(){
		Book book = new Book(environment, "DLRTableServiceOneChild");
		book.setParty(hh);
		book.setServiceStartDateTime(Randomness.generateCurrentXMLDate(1));
		book.sendRequest();
		
		TestReporter.logAPI(!book.getResponseStatusCode().contains("200"), book.getFaultString() ,book);
		TestReporter.assertTrue(Regex.match("[0-9]+", book.getTravelPlanId()), "The travel plan ID ["+book.getTravelPlanId()+"] is numeric as expected.");
		TestReporter.assertTrue(Regex.match("[0-9]+", book.getTravelPlanSegmentId()), "The reservation number ["+book.getTravelPlanSegmentId()+"] is numeric as expected.");
		
		NoShow noShow = new NoShow(environment, "Main");
		noShow.setReservationNumber(book.getTravelPlanSegmentId());
		noShow.sendRequest();
		
		TestReporter.logAPI(!noShow.getResponseStatusCode().contains("200"), noShow.getFaultString() ,noShow);
		TestReporter.assertTrue(Regex.match("[0-9]+", noShow.getCancellationNumber()), "The cancellation number ["+noShow.getCancellationNumber()+"] was not numeric as expected.");		

		LogItems logItems = new LogItems();
		logItems.addItem("ChargeGroupIF", "processNoShow", false);
		logItems.addItem("EventDiningServiceIF", "noShow", false);
		logItems.addItem("PricingService", "getCancellationCharges", false);
		logItems.addItem("TravelPlanServiceCrossReferenceV3", "updateOrder", false);
		logItems.addItem("TravelPlanServiceCrossReferenceV3SEI", "updateOrder", false);
		logItems.addItem("UpdateInventory", "updateInventory", false);
		validateLogs(noShow, logItems);
	}
	
	@Test(groups = {"api", "regression", "dining", "eventDiningService"})
	public void testNoShowTrueDiningReservation(){
		Book book = new Book(environment, ScheduledEventReservation.NOCOMPONENTSNOADDONS);
		book.setParty(new HouseHold(1));
		book.setServiceStartDateTime(Randomness.generateCurrentXMLDate(Randomness.randomNumberBetween(15, 25)));
		book.setReservableResourceId("BA054CBB-D573-C672-BE95-173042178DBE");
		book.addDetailsByFacilityNameAndProductName("The Hollywood Brown Derby", "Brown Derby Lunch F! 1st Show");
		book.addSpecialEventByProductName("Fantasmic! Viewing 1st Show");
		book.sendRequest();  //Debug: could not freeze
		
		FolioInterfaceSettlement folio = new FolioInterfaceSettlement(environment.replace("_CM", ""), book.getTravelPlanId());
		folio.createSettlementMethod("Pay total amount due with valid visa with incidentals");
		
		NoShow noShow = new NoShow(environment, "Main");
		noShow.setReservationNumber(book.getTravelPlanSegmentId());
		noShow.sendRequest();
		
		TestReporter.logAPI(!noShow.getResponseStatusCode().contains("200"), noShow.getFaultString() ,noShow);
		TestReporter.assertTrue(Regex.match("[0-9]+", noShow.getCancellationNumber()), "The cancellation number ["+noShow.getCancellationNumber()+"] was not numeric as expected.");		

		LogItems logItems = new LogItems();
		logItems.addItem("ChargeGroupIF", "processNoShow", false);
		logItems.addItem("EventDiningServiceIF", "noShow", false);
		logItems.addItem("PricingService", "getCancellationCharges", false);
		logItems.addItem("TravelPlanServiceCrossReferenceV3", "updateOrder", false);
		logItems.addItem("TravelPlanServiceCrossReferenceV3SEI", "updateOrder", false);
		logItems.addItem("UpdateInventory", "updateInventory", false);
		validateLogs(noShow, logItems);
	}
}
