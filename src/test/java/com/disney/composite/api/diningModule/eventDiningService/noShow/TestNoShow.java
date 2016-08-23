package com.disney.composite.api.diningModule.eventDiningService.noShow;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.diningModule.eventDiningService.operations.NoShow;
import com.disney.composite.BaseTest;
import com.disney.utils.Regex;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.database.LogItems;
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
		hh = new HouseHold(1);
		ScheduledEventReservation res = new EventDiningReservation(this.environment, hh);
		res.book("DLRTableServiceOneChild");
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
}
