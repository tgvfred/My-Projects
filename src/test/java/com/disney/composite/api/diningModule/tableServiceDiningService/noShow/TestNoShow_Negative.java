package com.disney.composite.api.diningModule.tableServiceDiningService.noShow;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.BaseTest;
import com.disney.api.soapServices.applicationError.ApplicationErrorCode;
import com.disney.api.soapServices.applicationError.DiningErrorCode;
import com.disney.api.soapServices.core.BaseSoapCommands;
import com.disney.api.soapServices.diningModule.tableServiceDiningServicePort.operations.NoShow;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.database.LogItems;
import com.disney.utils.dataFactory.guestFactory.HouseHold;
import com.disney.utils.dataFactory.staging.bookSEReservation.ScheduledEventReservation;
import com.disney.utils.dataFactory.staging.bookSEReservation.TableServiceDiningReservation;

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
		res = new TableServiceDiningReservation(this.environment, hh);
		res.book(ScheduledEventReservation.NOCOMPONENTSNOADDONS);
	}

	@AfterTest(alwaysRun=true)
	public void teardown(){
		try{
			if(res.getConfirmationNumber() != null)
				if(!res.getConfirmationNumber().isEmpty())
					res.cancel();
		}catch(Exception e){}
	}
	
	@Test(groups = {"api", "regression", "dining", "tableServiceDiningService", "negative"})
	public void testMissingReservationNumber(){
		TestReporter.logScenario("Missing Reservation");
		NoShow noShow = new NoShow(environment, "Main");
		noShow.setReservationNumber(BaseSoapCommands.REMOVE_NODE.toString());
		sendRequestAndValidateLogs(noShow, DiningErrorCode.RECORD_NOT_FOUND_EXCEPTION, "RECORD NOT FOUND : NO RESERVATION FOUND WITH 0");
	}	

	@Test(groups = {"api", "regression", "dining", "tableServiceDiningService", "negative"})
	public void testInvalidReservationNumber(){
		TestReporter.logScenario("Invalid Reservation Number");
		NoShow noShow = new NoShow(environment, "Main");
		noShow.setReservationNumber("123456789011");
		sendRequestAndValidateLogs(noShow, DiningErrorCode.RECORD_NOT_FOUND_EXCEPTION, "RECORD NOT FOUND : NO RESERVATION FOUND WITH 123456789011");
	}

	@Test(groups = {"api", "regression", "dining", "tableServiceDiningService", "negative"})
	public void testMissingSalesChannel(){
		TestReporter.logScenario("Missing Sales Channel");
		NoShow noShow = new NoShow(environment, "Main");
		noShow.setReservationNumber(res.getConfirmationNumber());		
		noShow.setSalesChannel(BaseSoapCommands.REMOVE_NODE.toString());
		sendRequestAndValidateLogs(noShow, DiningErrorCode.SALES_CHANNEL_REQUIRED, "Sales Channel is required : null");
	}

	@Test(groups = {"api", "regression", "dining", "tableServiceDiningService", "negative"})
	public void testInvalidSalesChannel(){
		TestReporter.logScenario("Invalid Sales Channel");
		NoShow noShow = new NoShow(environment, "Main");
		noShow.setReservationNumber(res.getConfirmationNumber());		
		noShow.setSalesChannel("Blah");
		sendRequestAndValidateLogs(noShow, DiningErrorCode.SALES_CHANNEL_REQUIRED, "Sales Channel is required : null");
	}
	
	@Test(groups = {"api", "regression", "dining", "tableServiceDiningService", "negative"})
	public void testMissingCommunicationChannel(){
		TestReporter.logScenario("Missing Communications Channel");
		NoShow noShow = new NoShow(environment, "Main");
		noShow.setReservationNumber(res.getConfirmationNumber());		
		noShow.setCommunicationChannel(BaseSoapCommands.REMOVE_NODE.toString());
		sendRequestAndValidateLogs(noShow, DiningErrorCode.COMMUNICATION_CHANNEL_REQUIRED, "communication Channel is required : null");
	}

	@Test(groups = {"api", "regression", "dining", "tableServiceDiningService", "negative"})
	public void testInvalidCommunicationChannel(){
		TestReporter.logScenario("Invalid Communications Channel");
		NoShow noShow = new NoShow(environment, "Main");
		noShow.setReservationNumber(res.getConfirmationNumber());		
		noShow.setCommunicationChannel("Blah");
		sendRequestAndValidateLogs(noShow, DiningErrorCode.COMMUNICATION_CHANNEL_REQUIRED, "communication Channel is required : null");
	}

	@Test(groups = {"api", "regression", "dining", "tableServiceDiningService", "negative"})
	public void cancelledReservation(){
		TestReporter.logScenario("Cancelled Reservation");
		ScheduledEventReservation res2 = new TableServiceDiningReservation(this.environment, hh);
		res2.book(ScheduledEventReservation.NOCOMPONENTSNOADDONS);
		res2.cancel();
		NoShow noShow = new NoShow(environment, "Main");
		noShow.setReservationNumber(res2.getConfirmationNumber());
		sendRequestAndValidateLogs(noShow, DiningErrorCode.INVALID_TRAVEL_STATUS, "Travel Status is invalid  : INVALID RESERVATION STATUS.CANNOT CHANGE THE STATUS TO NO-SHOW!");
	}
	
	@Test(groups = {"api", "regression", "dining", "tableServiceDiningService", "negative"})
	public void arrivedReservation(){
		TestReporter.logScenario("Arrived Reservation");
		ScheduledEventReservation res2 = new TableServiceDiningReservation(this.environment, hh);
		res2.book(ScheduledEventReservation.NOCOMPONENTSNOADDONS);
		res2.arrived();
		NoShow noShow = new NoShow(environment, "Main");
		noShow.setReservationNumber(res2.getConfirmationNumber());
		sendRequestAndValidateLogs(noShow, DiningErrorCode.INVALID_TRAVEL_STATUS, "Travel Status is invalid  : INVALID RESERVATION STATUS.CANNOT CHANGE THE STATUS TO NO-SHOW!");
	}
	
	private void sendRequestAndValidateLogs(NoShow noShow, ApplicationErrorCode error, String faultString){
		noShow.sendRequest();

		validateApplicationError(noShow, error);
		TestReporter.logAPI(!noShow.getFaultString().contains(faultString), noShow.getFaultString() ,noShow);

		LogItems logItems = new LogItems();
		logItems.addItem("TableServiceDiningServiceIF", "noShow", true);
		validateLogs(noShow, logItems);		
		
		LogItems logInvalidItems = new LogItems();
		logInvalidItems.addItem("ChargeGroupIF", "processNoShow", false);
		logInvalidItems.addItem("TravelPlanServiceCrossReferenceV3", "updateOrder", false);
		logInvalidItems.addItem("TravelPlanServiceCrossReferenceV3SEI", "updateOrder", false);
		logInvalidItems.addItem("UpdateInventory", "updateInventory", false);	
		logInvalidItems.addItem("PartyIF", "retrieveParty", false);
		logInvalidItems.addItem("AccommodationInventoryRequestComponentServiceIF", "releaseInventory", false);
		logInvalidItems.addItem("GuestLinkServiceV1", "createEntitlementReference", false);
		logInvalidItems.addItem("GuestLinkServiceV1SEI", "createEntitlementReference", false);
		validateNotInLogs(noShow, logInvalidItems);
	}
}