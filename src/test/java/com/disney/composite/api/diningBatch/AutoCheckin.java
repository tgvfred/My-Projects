package com.disney.composite.api.diningBatch;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.w3c.dom.NodeList;

import com.disney.api.soapServices.applicationError.DiningErrorCode;
import com.disney.api.soapServices.core.BaseSoapCommands;
import com.disney.api.soapServices.scheduledEventsComponentService.operations.AutoArrived;
import com.disney.api.soapServices.scheduledEventsComponentService.operations.RetrieveTravelPlanSegmentsForAutoArrival;
import com.disney.composite.BaseTest;
import com.disney.utils.Randomness;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.database.LogItems;

public class AutoCheckin extends BaseTest{
	private String date = Randomness.generateCurrentXMLDatetime(1);
	private NodeList reservations;
	private String reservation;
	private String sourceAccountingCenter = "3";
	private AutoArrived aa;
	private LogItems logValidItems;
	
	@Override
	@BeforeMethod(alwaysRun=true)
	@Parameters("environment")
	public void setup(@Optional String environment){
		this.environment = environment;
		RetrieveTravelPlanSegmentsForAutoArrival  retrieve = new RetrieveTravelPlanSegmentsForAutoArrival(environment, "Main");
		retrieve.setProcessDate(date);
		retrieve.setSourceAccountingCenter(sourceAccountingCenter);
		retrieve.sendRequest();
		TestReporter.logAPI(!retrieve.getResponseStatusCode().equals("200"), "An error occurred retrieving reservations for auto arrival: " + retrieve.getFaultString(), retrieve);
		if(reservation == null)reservations = retrieve.getAllReservationNumbers();
		TestReporter.assertTrue(reservations.getLength() > 0, "No reservations were returned for the date ["+date+"]");
	}
	
	@Test(groups = {"api", "regression", "dining", "batch"})
	public void testAutoArrived(){
		reservation = reservations.item(0).getTextContent();
		aa = new AutoArrived(environment, "Main");
		aa.setTpsId(reservation);
		aa.sendRequest();
		TestReporter.logAPI(!aa.getResponseStatusCode().equals("200"), "An error occurred setting a reservation to AutoArrived", aa);
		
		logValidItems = new LogItems();
		logValidItems.addItem("FolioServiceIF", "retrieveAccountingTransactions", false);
		logValidItems.addItem("GuestLinkServiceV1SEI", "createEntitlementReference", false);
		logValidItems.addItem("TravelPlanServiceCrossReferenceV3SEI", "updateOrderItem", false);
		logValidItems.addItem("ChargeGroupIF", "checkIn", false);
		logValidItems.addItem("PartyIF", "retrievePartyBasicInformation", false);
		logValidItems.addItem("PartyIF", "retrieveParty", false);
		logValidItems.addItem("AccommodationInventoryRequestComponentServiceIF", "retrieveAssignmentOwner", false);
		logValidItems.addItem("EventDiningServiceIF", "retrieve", false);
		logValidItems.addItem("PackagingService", "getProducts", false);
		logValidItems.addItem("PricingService", "priceComponents", false);
		logValidItems.addItem("ScheduledEventsComponentServiceIF", "autoArrived", false);
		validateLogs(aa, logValidItems, 10000);
	}
	
	@Test(groups = {"api", "regression", "dining", "batch"}, dependsOnMethods="autoArrived")
	public void testAutoArrived_InvalidReservationStatus_Arrived(){		
		aa.sendRequest();
		validateApplicationError(aa, DiningErrorCode.INVALID_TRAVEL_STATUS);
		TestReporter.logAPI(!aa.getFaultString().contains("INVALID RESERVATION STATUS.CANNOT CHANGE THE STATUS TO ARRIVED"), aa.getFaultString() ,aa);

		logValidItems = new LogItems();
		logValidItems.addItem("ScheduledEventsComponentServiceIF", "aa", true);
		validateLogs(aa, logValidItems);
	}
	
	@Test(groups = {"api", "regression", "dining", "batch"})
	public void testAutoArrived_InvalidReservationNumber(){		
		AutoArrived aa = new AutoArrived(environment, "Main");
		aa.setTpsId("1");
		aa.sendRequest();
		validateApplicationError(aa, DiningErrorCode.RECORD_NOT_FOUND_EXCEPTION);
		TestReporter.logAPI(!aa.getFaultString().contains("RECORD NOT FOUND : NO RESERVATION FOUND WITH 1"), aa.getFaultString() ,aa);

		logValidItems = new LogItems();
		logValidItems.addItem("ScheduledEventsComponentServiceIF", "aa", true);
		validateLogs(aa, logValidItems);
	}
	
	@Test(groups = {"api", "regression", "dining", "batch"})
	public void testAutoArrived_MissingTpsId(){		
		AutoArrived aa = new AutoArrived(environment, "Main");
		aa.setTpsId(BaseSoapCommands.REMOVE_NODE.toString());
		aa.sendRequest();
		TestReporter.logAPI(!aa.getFaultString().contains("INVALID RESERVATION STATUS.CANNOT CHANGE THE STATUS TO ARRIVED"), aa.getFaultString() ,aa);

		logValidItems = new LogItems();
		logValidItems.addItem("ScheduledEventsComponentServiceIF", "aa", false);
		validateLogs(aa, logValidItems);
	}
}