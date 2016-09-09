package com.disney.composite.api.diningModule.eventDiningService.noShow;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.BaseTest;
import com.disney.api.soapServices.diningModule.eventDiningService.operations.NoShow;
import com.disney.api.soapServices.applicationError.DiningErrorCode;
import com.disney.api.soapServices.core.BaseSoapCommands;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.database.LogItems;
import com.disney.utils.dataFactory.guestFactory.HouseHold;
import com.disney.utils.dataFactory.staging.bookSEReservation.EventDiningReservation;
import com.disney.utils.dataFactory.staging.bookSEReservation.ScheduledEventReservation;

public class TestNoShow_Negative extends BaseTest{
	// Defining global variables
	protected String TPS_ID = null;
	protected ScheduledEventReservation res = null;
	
	
	@Override
	@BeforeClass(alwaysRun = true)
	@Parameters({ "environment" })
	public void setup(@Optional String environment){
		this.environment = environment;
		hh = new HouseHold(1);
		res = new EventDiningReservation(this.environment, hh);
		res.book(ScheduledEventReservation.NOCOMPONENTSNOADDONS);
	}

	@Test(groups = {"api", "regression", "dining", "eventDiningService", "negative"})
	public void testMissingReservationNumber(){
		NoShow noShow = new NoShow(environment, "Main");
		noShow.setReservationNumber(BaseSoapCommands.REMOVE_NODE.toString());
		noShow.sendRequest();
		validateApplicationError(noShow, DiningErrorCode.RECORD_NOT_FOUND_EXCEPTION);
		TestReporter.logAPI(!noShow.getFaultString().contains("RECORD NOT FOUND : NO RESERVATION FOUND WITH 0"), noShow.getFaultString() ,noShow);

		LogItems logItems = new LogItems();
		logItems.addItem("EventDiningServiceIF", "noShow", true);
		validateLogs(noShow, logItems);		
		
		LogItems logInvalidItems = new LogItems();
		logInvalidItems.addItem("ChargeGroupIF", "processNoShow", false);
		logInvalidItems.addItem("PricingService", "getCancellationCharges", false);	
		logInvalidItems.addItem("TravelPlanServiceCrossReferenceV3", "updateOrder", false);
		logInvalidItems.addItem("TravelPlanServiceCrossReferenceV3SEI", "updateOrder", false);
		logInvalidItems.addItem("UpdateInventory", "updateInventory", false);
		validateNotInLogs(noShow, logInvalidItems);
	}	

	@Test(groups = {"api", "regression", "dining", "eventDiningService", "negative"})
	public void testInvalidReservationNumber(){
		NoShow noShow = new NoShow(environment, "Main");
		noShow.setReservationNumber("123456789011");
		noShow.sendRequest();

		validateApplicationError(noShow, DiningErrorCode.RECORD_NOT_FOUND_EXCEPTION);
		TestReporter.logAPI(!noShow.getFaultString().contains("RECORD NOT FOUND : NO RESERVATION FOUND WITH 123456789011"), noShow.getFaultString() ,noShow);

		LogItems logItems = new LogItems();
		logItems.addItem("EventDiningServiceIF", "noShow", true);
		validateLogs(noShow, logItems);		
		
		LogItems logInvalidItems = new LogItems();
		logInvalidItems.addItem("ChargeGroupIF", "processNoShow", false);
		logInvalidItems.addItem("PricingService", "getCancellationCharges", false);	
		logInvalidItems.addItem("TravelPlanServiceCrossReferenceV3", "updateOrder", false);
		logInvalidItems.addItem("TravelPlanServiceCrossReferenceV3SEI", "updateOrder", false);
		logInvalidItems.addItem("UpdateInventory", "updateInventory", false);
		validateNotInLogs(noShow, logInvalidItems);
	}

	@Test(groups = {"api", "regression", "dining", "eventDiningService", "negative"})
	public void testMissingSalesChannel(){
		NoShow noShow = new NoShow(environment, "Main");
		noShow.setReservationNumber(res.getConfirmationNumber());		
		noShow.setSalesChannel(BaseSoapCommands.REMOVE_NODE.toString());
		noShow.sendRequest();

		validateApplicationError(noShow, DiningErrorCode.SALES_CHANNEL_REQUIRED);
		TestReporter.logAPI(!noShow.getFaultString().contains("Sales Channel is required : null"), noShow.getFaultString() ,noShow);

		LogItems logItems = new LogItems();
		logItems.addItem("EventDiningServiceIF", "noShow", true);
		validateLogs(noShow, logItems);		
		
		LogItems logInvalidItems = new LogItems();
		logInvalidItems.addItem("ChargeGroupIF", "processNoShow", false);
		logInvalidItems.addItem("PricingService", "getCancellationCharges", false);	
		logInvalidItems.addItem("TravelPlanServiceCrossReferenceV3", "updateOrder", false);
		logInvalidItems.addItem("TravelPlanServiceCrossReferenceV3SEI", "updateOrder", false);
		logInvalidItems.addItem("UpdateInventory", "updateInventory", false);
		validateNotInLogs(noShow, logInvalidItems);
	}

	@Test(groups = {"api", "regression", "dining", "eventDiningService", "negative"})
	public void testInvalidSalesChannel(){
		NoShow noShow = new NoShow(environment, "Main");
		noShow.setReservationNumber(res.getConfirmationNumber());		
		noShow.setSalesChannel("Blah");
		noShow.sendRequest();

		validateApplicationError(noShow, DiningErrorCode.SALES_CHANNEL_REQUIRED);
		TestReporter.logAPI(!noShow.getFaultString().contains("Sales Channel is required : null"), noShow.getFaultString() ,noShow);

		LogItems logItems = new LogItems();
		logItems.addItem("EventDiningServiceIF", "noShow", true);
		validateLogs(noShow, logItems);		
		
		LogItems logInvalidItems = new LogItems();
		logInvalidItems.addItem("ChargeGroupIF", "processNoShow", false);
		logInvalidItems.addItem("PricingService", "getCancellationCharges", false);	
		logInvalidItems.addItem("TravelPlanServiceCrossReferenceV3", "updateOrder", false);
		logInvalidItems.addItem("TravelPlanServiceCrossReferenceV3SEI", "updateOrder", false);
		logInvalidItems.addItem("UpdateInventory", "updateInventory", false);
		validateNotInLogs(noShow, logInvalidItems);
	}
	
	@Test(groups = {"api", "regression", "dining", "eventDiningService", "negative"})
	public void testMissingCommunicationChannel(){
			NoShow noShow = new NoShow(environment, "Main");
			noShow.setReservationNumber(res.getConfirmationNumber());		
			noShow.setCommunicationChannel(BaseSoapCommands.REMOVE_NODE.toString());
			noShow.sendRequest();

			validateApplicationError(noShow, DiningErrorCode.COMMUNICATION_CHANNEL_REQUIRED);
			TestReporter.logAPI(!noShow.getFaultString().contains("communication Channel is required : null"), noShow.getFaultString() ,noShow);

			LogItems logItems = new LogItems();
			logItems.addItem("EventDiningServiceIF", "noShow", true);
			validateLogs(noShow, logItems);		
			
			LogItems logInvalidItems = new LogItems();
			logInvalidItems.addItem("ChargeGroupIF", "processNoShow", false);
			logInvalidItems.addItem("PricingService", "getCancellationCharges", false);	
			logInvalidItems.addItem("TravelPlanServiceCrossReferenceV3", "updateOrder", false);
			logInvalidItems.addItem("TravelPlanServiceCrossReferenceV3SEI", "updateOrder", false);
			logInvalidItems.addItem("UpdateInventory", "updateInventory", false);
			validateNotInLogs(noShow, logInvalidItems);
		}

	@Test(groups = {"api", "regression", "dining", "eventDiningService", "negative"})
	public void testInvalidCommunicationChannel(){
			NoShow noShow = new NoShow(environment, "Main");
			noShow.setReservationNumber(res.getConfirmationNumber());		
			noShow.setCommunicationChannel("Blah");
			noShow.sendRequest();

			validateApplicationError(noShow, DiningErrorCode.COMMUNICATION_CHANNEL_REQUIRED);
			TestReporter.logAPI(!noShow.getFaultString().contains("communication Channel is required : null"), noShow.getFaultString() ,noShow);

			LogItems logItems = new LogItems();
			logItems.addItem("EventDiningServiceIF", "noShow", true);
			validateLogs(noShow, logItems);		
			
			LogItems logInvalidItems = new LogItems();
			logInvalidItems.addItem("ChargeGroupIF", "processNoShow", false);
			logInvalidItems.addItem("PricingService", "getCancellationCharges", false);	
			logInvalidItems.addItem("TravelPlanServiceCrossReferenceV3", "updateOrder", false);
			logInvalidItems.addItem("TravelPlanServiceCrossReferenceV3SEI", "updateOrder", false);
			logInvalidItems.addItem("UpdateInventory", "updateInventory", false);
			validateNotInLogs(noShow, logInvalidItems);
	}

	@Test(groups = {"api", "regression", "dining", "eventDiningService", "negative"})
	public void cancelledReservation(){
		ScheduledEventReservation res2 = new EventDiningReservation(this.environment, hh);
		res2.book(ScheduledEventReservation.NOCOMPONENTSNOADDONS);
		res2.cancel();
		NoShow noShow = new NoShow(environment, "Main");
		noShow.setReservationNumber(res2.getConfirmationNumber());		
		noShow.sendRequest();

		validateApplicationError(noShow, DiningErrorCode.INVALID_TRAVEL_STATUS);
		TestReporter.logAPI(!noShow.getFaultString().contains("Travel Status is invalid  : INVALID RESERVATION STATUS.CANNOT CHANGE THE STATUS TO NO-SHOW!"), noShow.getFaultString() ,noShow);

		LogItems logItems = new LogItems();
		logItems.addItem("EventDiningServiceIF", "noShow", true);
		validateLogs(noShow, logItems);		
		
		LogItems logInvalidItems = new LogItems();
		logInvalidItems.addItem("ChargeGroupIF", "processNoShow", false);
		logInvalidItems.addItem("PricingService", "getCancellationCharges", false);	
		logInvalidItems.addItem("TravelPlanServiceCrossReferenceV3", "updateOrder", false);
		logInvalidItems.addItem("TravelPlanServiceCrossReferenceV3SEI", "updateOrder", false);
		logInvalidItems.addItem("UpdateInventory", "updateInventory", false);
		validateNotInLogs(noShow, logInvalidItems);
	}
	
	@Test(groups = {"api", "regression", "dining", "eventDiningService", "negative"})
	public void arrivedReservation(){
		ScheduledEventReservation res2 = new EventDiningReservation(this.environment, hh);
		res2.book(ScheduledEventReservation.NOCOMPONENTSNOADDONS);
		res2.arrived();
		NoShow noShow = new NoShow(environment, "Main");
		noShow.setReservationNumber(res2.getConfirmationNumber());		
		noShow.sendRequest();

		validateApplicationError(noShow, DiningErrorCode.INVALID_TRAVEL_STATUS);
		TestReporter.logAPI(!noShow.getFaultString().contains("Travel Status is invalid  : INVALID RESERVATION STATUS.CANNOT CHANGE THE STATUS TO NO-SHOW!"), noShow.getFaultString() ,noShow);

		LogItems logItems = new LogItems();
		logItems.addItem("EventDiningServiceIF", "noShow", true);
		validateLogs(noShow, logItems);		
		
		LogItems logInvalidItems = new LogItems();
		logInvalidItems.addItem("ChargeGroupIF", "processNoShow", false);
		logInvalidItems.addItem("PricingService", "getCancellationCharges", false);	
		logInvalidItems.addItem("TravelPlanServiceCrossReferenceV3", "updateOrder", false);
		logInvalidItems.addItem("TravelPlanServiceCrossReferenceV3SEI", "updateOrder", false);
		logInvalidItems.addItem("UpdateInventory", "updateInventory", false);
		validateNotInLogs(noShow, logInvalidItems);
	}
}
