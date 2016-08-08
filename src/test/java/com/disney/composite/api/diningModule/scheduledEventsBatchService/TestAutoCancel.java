package com.disney.composite.api.diningModule.scheduledEventsBatchService;

import org.testng.SkipException;
import org.testng.annotations.Test;

import com.disney.api.soapServices.applicationError.DiningErrorCode;
import com.disney.api.soapServices.applicationError.LiloSystemErrorCode;
import com.disney.api.soapServices.chargeGroup.operations.RetrieveNonGuaranteedGuestChargeGroups;
import com.disney.api.soapServices.core.BaseSoapCommands;
import com.disney.api.soapServices.diningModule.scheduledEventsBatchService.operations.AutoCancel;
import com.disney.composite.BaseTest;
import com.disney.test.utils.Randomness;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.database.LogItems;

public class TestAutoCancel extends BaseTest{
	private String date = Randomness.generateCurrentXMLDate(45);
	private String sourceAccountingCenter = "3";
	private RetrieveNonGuaranteedGuestChargeGroups retrieve;
	private String expected_TCG;
	private String actual_TCG;
	
	@Test(groups = {"api", "regression", "dining", "scheduledEventsBatchService", "negative"})
	public void testRetrieveNonGuaranteedGuestChargeGroups(){
		TestReporter.logScenario("RetrieveNonGuaranteedGuestChargeGroups");
		retrieve = new RetrieveNonGuaranteedGuestChargeGroups(environment);
		retrieve.setRunDate(date);
		retrieve.setSourceAccountingCenter(sourceAccountingCenter);
		retrieve.sendRequest();
		TestReporter.logAPI(!retrieve.getResponseStatusCode().equals("200"), "An error occurred retrieving non-guaranteed guest charge groups: " + retrieve.getFaultString(), retrieve);
		
		LogItems logValidItems = new LogItems();
		logValidItems.addItem("ChargeGroupIF", "retrieveNonGuaranteedGuestChargeGroups", false);
		validateLogs(retrieve, logValidItems, 10000);
	}
	
	@Test(groups = {"api", "regression", "dining", "scheduledEventsBatchService", "negative"}, dependsOnMethods="testRetrieveNonGuaranteedGuestChargeGroups")
	public void testAutoCancel(){	
		TestReporter.logScenario("AutoCancel");	
		if(retrieve.getAllReservations().size() == 0)
			throw new SkipException("No reservations were returned by RetrieveNonGuaranteedGuestChargeGroups for the date ["+date+"] and source accounting center ["+sourceAccountingCenter+"].");
		expected_TCG = retrieve.getAllReservations().get("1");
		
		
		AutoCancel cancel = new AutoCancel(environment, "Main");
		cancel.setTravelComponentGroupingId(expected_TCG);
		cancel.sendRequest();
		actual_TCG = cancel.getTravelComponentGroupIdUsingTPS(cancel.getTravelPlanSegmentId());
		TestReporter.logAPI(!cancel.getResponseStatusCode().equals("200"), "An error occurred checking out a reservation: " + cancel.getFaultString(), cancel);
		TestReporter.assertEquals(expected_TCG, actual_TCG, "Verify that the actual travel component grouping number ["+actual_TCG+"] matches the expected travel component grouping number ["+expected_TCG+"].");
		TestReporter.assertEquals(cancel.getTravelStatus(), "Auto Cancelled", "Verify that the actual resevation status ["+cancel.getTravelStatus()+"] matches the expected reservation status [Auto Cancelled].");

		LogItems logValidItems = new LogItems();
		logValidItems.addItem("ScheduledEventsComponentServiceIF", "autoCancel", false);
		logValidItems.addItem("ChargeGroupIF", "updateChargeGroupStatus", false);
		logValidItems.addItem("ChargeGroupIF", "cancelChargeGroups", false);
		logValidItems.addItem("UpdateInventory", "updateInventory", false);
		validateLogs(cancel, logValidItems, 10000);
	}
	@Test(groups = {"api", "regression", "dining", "scheduledEventsBatchService", "negative"}, dependsOnMethods="testAutoCancel")
	public void testAutoCancel_InvalidReservationStatus(){
		TestReporter.logScenario("AutoCancel_InvalidReservationStatus");
		if(expected_TCG == null)expected_TCG = retrieve.getAllReservations().get("1");
		AutoCancel cancel = new AutoCancel(environment, "Main");
		cancel.setTravelComponentGroupingId(expected_TCG);
		cancel.sendRequest();
		validateApplicationError(cancel, DiningErrorCode.INVALID_TRAVEL_STATUS);
		TestReporter.logAPI(!cancel.getFaultString().contains("INVALID RESERVATION STATUS"), cancel.getFaultString(), cancel);

		LogItems logValidItems = new LogItems();
		logValidItems.addItem("ScheduledEventsComponentServiceIF", "autoCancel", true);
		validateLogs(cancel, logValidItems);
		
		LogItems logInvalidItems = new LogItems();
		logInvalidItems.addItem("ChargeGroupIF", "updateChargeGroupStatus", false);
		logInvalidItems.addItem("ChargeGroupIF", "cancelChargeGroups", false);
		logInvalidItems.addItem("UpdateInventory", "updateInventory", false);
		validateNotInLogs(cancel, logInvalidItems);
	}
	@Test(groups = {"api", "regression", "dining", "scheduledEventsBatchService"})
	public void testAutoCancel_MissingRunDate(){
		TestReporter.logScenario("RetrieveNonGuaranteedGuestChargeGroups_MissingRunDate");
		RetrieveNonGuaranteedGuestChargeGroups retrieve = new RetrieveNonGuaranteedGuestChargeGroups(environment);
		retrieve.setRunDate(BaseSoapCommands.REMOVE_NODE.toString());
		retrieve.setSourceAccountingCenter(sourceAccountingCenter);
		retrieve.sendRequest();
		TestReporter.logAPI(!retrieve.getResponseStatusCode().equals("200"), "An error occurred checking out a reservation: " + retrieve.getFaultString(), retrieve);
		
		LogItems logValidItems = new LogItems();
		logValidItems.addItem("ChargeGroupIF", "retrieveNonGuaranteedGuestChargeGroups", false);
		validateLogs(retrieve, logValidItems, 10000);
	}
	@Test(groups = {"api", "regression", "dining", "scheduledEventsBatchService", "negative"})
	public void testAutoCancel_MissingSourceAccountingCenter(){
		TestReporter.logScenario("RetrieveNonGuaranteedGuestChargeGroups_MissingSourceAccountingCenter");
		RetrieveNonGuaranteedGuestChargeGroups retrieve = new RetrieveNonGuaranteedGuestChargeGroups(environment);
		retrieve.setRunDate(date);
		retrieve.setSourceAccountingCenter(BaseSoapCommands.REMOVE_NODE.toString());
		retrieve.sendRequest();

		validateApplicationError(retrieve, LiloSystemErrorCode.UNEXPECTED_ERROR);
		TestReporter.logAPI(!retrieve.getFaultString().contains("Unexpected Error occurred : retrieveNonGuaranteedGuestChargeGroups : java.lang.NullPointerException"), retrieve.getFaultString(), retrieve);

		LogItems logValidItems = new LogItems();
		logValidItems.addItem("ChargeGroupIF", "retrieveNonGuaranteedGuestChargeGroups", true);
		validateLogs(retrieve, logValidItems);
	}
	@Test(groups = {"api", "regression", "dining", "scheduledEventsBatchService"})
	public void testAutoCancel_NoData(){
		TestReporter.logScenario("RetrieveNonGuaranteedGuestChargeGroups_NoData");
		RetrieveNonGuaranteedGuestChargeGroups retrieve = new RetrieveNonGuaranteedGuestChargeGroups(environment);
		retrieve.setRunDate(BaseSoapCommands.REMOVE_NODE.toString());
		retrieve.setSourceAccountingCenter(BaseSoapCommands.REMOVE_NODE.toString());
		retrieve.sendRequest();
		TestReporter.logAPI(!retrieve.getResponseStatusCode().equals("200"), "An error occurred checking out a reservation: " + retrieve.getFaultString(), retrieve);
		
		LogItems logValidItems = new LogItems();
		logValidItems.addItem("ChargeGroupIF", "retrieveNonGuaranteedGuestChargeGroups", true);		
		validateLogs(retrieve, logValidItems, 10000);
	}	
	@Test(groups = {"api", "regression", "dining", "scheduledEventsBatchService", "negative"})
	public void testAutoCancel_InvalidTcg(){
		TestReporter.logScenario("AutoCancel_InvalidTcg");
		AutoCancel cancel = new AutoCancel(environment, "Main");
		cancel.setTravelComponentGroupingId("1");
		cancel.sendRequest();

		validateApplicationError(cancel, DiningErrorCode.TCG_NOT_FOUND);
		TestReporter.logAPI(!cancel.getFaultString().contains("Travel Component Grouping not found : Cannot find Travel Component Grouping with ID : 1"), cancel.getFaultString(), cancel);
		LogItems logValidItems = new LogItems();
		logValidItems.addItem("ScheduledEventsComponentServiceIF", "autoCancel", true);
		validateLogs(cancel, logValidItems, 10000);
	}
	@Test(groups = {"api", "regression", "dining", "scheduledEventsBatchService", "negative"})
	public void testAutoCancel_MissingTcg(){
		TestReporter.logScenario("AutoCancel_MissingTcg");
		AutoCancel cancel = new AutoCancel(environment, "Main");
		cancel.setTravelComponentGroupingId(BaseSoapCommands.REMOVE_NODE.toString());
		cancel.sendRequest();

		validateApplicationError(cancel, DiningErrorCode.MISSING_REQUIRED_PARAM_EXCEPTION);
		TestReporter.logAPI(!cancel.getFaultString().contains("Required parameters are missing : Invalid TravelComponentGroupingId : 0"), cancel.getFaultString(), cancel);
		LogItems logValidItems = new LogItems();
		logValidItems.addItem("ScheduledEventsComponentServiceIF", "autoCancel", true);
		validateLogs(cancel, logValidItems, 10000);
	}
}
