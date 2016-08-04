package com.disney.composite.api.diningBatch;

import org.testng.annotations.Test;
import org.w3c.dom.NodeList;

import com.disney.api.soapServices.applicationError.DiningErrorCode;
import com.disney.api.soapServices.applicationError.LiloSystemErrorCode;
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
	
	@Test(groups = {"api", "regression", "dining", "batch"})
	public void testRetrieveTravelPlanSegmentsForAutoArrival(){
		RetrieveTravelPlanSegmentsForAutoArrival  retrieve = new RetrieveTravelPlanSegmentsForAutoArrival(environment, "Main");
		retrieve.setProcessDate(date);
		retrieve.setSourceAccountingCenter(sourceAccountingCenter);
		retrieve.sendRequest();
		TestReporter.logAPI(!retrieve.getResponseStatusCode().equals("200"), "An error occurred retrieving reservations for auto arrival: " + retrieve.getFaultString(), retrieve);
		if(reservation == null)reservations = retrieve.getAllReservationNumbers();
		TestReporter.assertTrue(reservations.getLength() > 0, "No reservations were returned for the date ["+date+"]");
		
		LogItems logValidItems = new LogItems();
		logValidItems.addItem("ScheduledEventsComponentServiceIF", "retrieveTravelPlanSegmentsForAutoArrival", false);
		validateLogs(retrieve, logValidItems, 10000);
	}
	
	@Test(groups = {"api", "regression", "dining", "batch"}, dependsOnMethods="testRetrieveTravelPlanSegmentsForAutoArrival")
	public void testAutoArrived(){
		reservation = reservations.item(0).getTextContent();
		AutoArrived aa = new AutoArrived(environment, "Main");
		aa.setTpsId(reservation);
		aa.sendRequest();
		TestReporter.logAPI(!aa.getResponseStatusCode().equals("200"), "An error occurred setting a reservation to AutoArrived: " + aa.getFaultString(), aa);
		
		LogItems logValidItems = new LogItems();
		logValidItems.addItem("TravelPlanServiceCrossReferenceV3SEI", "updateOrderItem", false);
		logValidItems.addItem("ChargeGroupIF", "checkIn", false);
		logValidItems.addItem("ScheduledEventsComponentServiceIF", "autoArrived", false);
		validateLogs(aa, logValidItems, 10000);
	}
	
	@Test(groups = {"api", "regression", "dining", "batch", "negative"}, dependsOnMethods="testAutoArrived")
	public void testAutoArrived_InvalidReservationStatus_Arrived(){	
		AutoArrived aa = new AutoArrived(environment, "Main");
		aa.setTpsId(reservation);	
		aa.sendRequest();
		validateApplicationError(aa, DiningErrorCode.INVALID_TRAVEL_STATUS);
		TestReporter.logAPI(!aa.getFaultString().contains("INVALID RESERVATION STATUS.CANNOT CHANGE THE STATUS TO ARRIVED"), aa.getFaultString() ,aa);

		LogItems logValidItems = new LogItems();
		logValidItems.addItem("ScheduledEventsComponentServiceIF", "autoArrived", true);
		validateLogs(aa, logValidItems, 10000);
		
		logValidItems = new LogItems();
		logValidItems.addItem("TravelPlanServiceCrossReferenceV3SEI", "updateOrderItem", false);
		logValidItems.addItem("ChargeGroupIF", "checkIn", false);
		validateNotInLogs(aa, logValidItems);
	}
	
	@Test(groups = {"api", "regression", "dining", "batch", "negative"})
	public void testAutoArrived_InvalidReservationNumber(){		
		AutoArrived aa = new AutoArrived(environment, "Main");
		aa.setTpsId("1");
		aa.sendRequest();
		validateApplicationError(aa, DiningErrorCode.RECORD_NOT_FOUND_EXCEPTION);
		TestReporter.logAPI(!aa.getFaultString().contains("RECORD NOT FOUND : NO RESERVATION FOUND WITH 1"), aa.getFaultString() ,aa);

		LogItems logValidItems = new LogItems();
		logValidItems.addItem("ScheduledEventsComponentServiceIF", "autoArrived", true);
		validateLogs(aa, logValidItems);
		
		logValidItems = new LogItems();
		logValidItems.addItem("TravelPlanServiceCrossReferenceV3SEI", "updateOrderItem", false);
		logValidItems.addItem("ChargeGroupIF", "checkIn", false);
		validateNotInLogs(aa, logValidItems);
	}
	
	@Test(groups = {"api", "regression", "dining", "batch"})
	public void testAutoArrived_MissingTpsId(){		
		AutoArrived aa = new AutoArrived(environment, "Main");
		aa.setTpsId(BaseSoapCommands.REMOVE_NODE.toString());
		aa.sendRequest();
		TestReporter.logAPI(!aa.getResponseStatusCode().equals("200"), "An error occurred setting a reservation to AutoArrived", aa);

		LogItems logValidItems = new LogItems();
		logValidItems.addItem("ScheduledEventsComponentServiceIF", "autoArrived", false);
		validateLogs(aa, logValidItems);
	}	
	
	@Test(groups = {"api", "regression", "dining", "batch"})
	public void testAutoArrived_MissingProcessDate(){
		RetrieveTravelPlanSegmentsForAutoArrival  retrieve = new RetrieveTravelPlanSegmentsForAutoArrival(environment, "Main");
		retrieve.setProcessDate(BaseSoapCommands.REMOVE_NODE.toString());
		retrieve.setSourceAccountingCenter(sourceAccountingCenter);
		retrieve.sendRequest();
		TestReporter.logAPI(!retrieve.getResponseStatusCode().equals("200"), "An error occurred retrieving reservations for auto arrival: " + retrieve.getFaultString(), retrieve);
		if(reservation == null)reservations = retrieve.getAllReservationNumbers();
		TestReporter.assertTrue(reservations.getLength() > 0, "No reservations were returned for the date ["+date+"]");
		
		LogItems logValidItems = new LogItems();
		logValidItems.addItem("ScheduledEventsComponentServiceIF", "retrieveTravelPlanSegmentsForAutoArrival", false);
		validateLogs(retrieve, logValidItems, 10000);		
	}
	@Test(groups = {"api", "regression", "dining", "batch"})
	public void testAutoArrived_InvalidSourceAccountingCenter(){
		RetrieveTravelPlanSegmentsForAutoArrival  retrieve = new RetrieveTravelPlanSegmentsForAutoArrival(environment, "Main");
		retrieve.setProcessDate(date);
		retrieve.setSourceAccountingCenter("1");
		retrieve.sendRequest();
		TestReporter.logAPI(!retrieve.getResponseStatusCode().equals("200"), "An error occurred retrieving reservations for auto arrival: " + retrieve.getFaultString(), retrieve);
		TestReporter.assertTrue(retrieve.getNumberOfReservations() == 0, "Reservations were unexpectedly returned for the source accounting center ["+sourceAccountingCenter+"]");
		
		LogItems logValidItems = new LogItems();
		logValidItems.addItem("ScheduledEventsComponentServiceIF", "retrieveTravelPlanSegmentsForAutoArrival", false);
		validateLogs(retrieve, logValidItems, 10000);
	}
	@Test(groups = {"api", "regression", "dining", "batch", "negative"})
	public void testAutoArrived_MissingSourceAccountingCenter(){
		RetrieveTravelPlanSegmentsForAutoArrival  retrieve = new RetrieveTravelPlanSegmentsForAutoArrival(environment, "Main");
		retrieve.setProcessDate(date);
		retrieve.setSourceAccountingCenter(BaseSoapCommands.REMOVE_NODE.toString());
		retrieve.sendRequest();
		validateApplicationError(retrieve, LiloSystemErrorCode.UNEXPECTED_ERROR);
		TestReporter.logAPI(!retrieve.getFaultString().contains("Unexpected Error occurred : retrieveTravelPlanSegmentsForAutoArrival : java.lang.NullPointerException"), retrieve.getFaultString() , retrieve);
		
		LogItems logValidItems = new LogItems();
		logValidItems.addItem("ScheduledEventsComponentServiceIF", "retrieveTravelPlanSegmentsForAutoArrival", true);
		validateLogs(retrieve, logValidItems, 10000);
	}
	@Test(groups = {"api", "regression", "dining", "batch", "negative"})
	public void testAutoArrived_NoData(){
		RetrieveTravelPlanSegmentsForAutoArrival  retrieve = new RetrieveTravelPlanSegmentsForAutoArrival(environment, "Main");
		retrieve.setProcessDate(BaseSoapCommands.REMOVE_NODE.toString());
		retrieve.setSourceAccountingCenter(BaseSoapCommands.REMOVE_NODE.toString());
		retrieve.sendRequest();
		validateApplicationError(retrieve, LiloSystemErrorCode.UNEXPECTED_ERROR);
		TestReporter.logAPI(!retrieve.getFaultString().contains("Unexpected Error occurred : retrieveTravelPlanSegmentsForAutoArrival : java.lang.NullPointerException"), retrieve.getFaultString() , retrieve);
		
		LogItems logValidItems = new LogItems();
		logValidItems.addItem("ScheduledEventsComponentServiceIF", "retrieveTravelPlanSegmentsForAutoArrival", true);
		validateLogs(retrieve, logValidItems, 10000);
	}

}