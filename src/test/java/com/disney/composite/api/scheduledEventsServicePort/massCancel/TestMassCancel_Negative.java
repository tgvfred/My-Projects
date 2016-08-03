package com.disney.composite.api.scheduledEventsServicePort.massCancel;

import org.testng.annotations.Test;

import com.disney.api.soapServices.applicationError.DiningErrorCode;
import com.disney.api.soapServices.applicationError.LiloSystemErrorCode;
import com.disney.api.soapServices.core.BaseSoapCommands;
import com.disney.api.soapServices.scheduledEventsServicePort.operations.MassCancel;
import com.disney.composite.BaseTest;
import com.disney.test.utils.Randomness;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.database.LogItems;

public class TestMassCancel_Negative extends BaseTest{
	
	@Test(groups = {"api", "regression", "dining", "scheduledEventsServicePort", "negative"})
	public void noData(){
		MassCancel massCancel = new MassCancel(environment);
		massCancel.setCommunicationsChannel(BaseSoapCommands.REMOVE_NODE.toString());
		massCancel.setFacilityId(BaseSoapCommands.REMOVE_NODE.toString());
		massCancel.setMassCancelReasonId(BaseSoapCommands.REMOVE_NODE.toString());
		massCancel.setProductChannelId(BaseSoapCommands.REMOVE_NODE.toString());
		massCancel.setSalesChannel(BaseSoapCommands.REMOVE_NODE.toString());
		massCancel.setServiceDate(BaseSoapCommands.REMOVE_NODE.toString());
		massCancel.setServiceWindowEnd(BaseSoapCommands.REMOVE_NODE.toString());
		massCancel.setServiceWindowStart(BaseSoapCommands.REMOVE_NODE.toString());
		massCancel.setSourceAccountingCenter(BaseSoapCommands.REMOVE_NODE.toString());
		massCancel.sendRequest();
		validateApplicationError(massCancel, LiloSystemErrorCode.UNEXPECTED_ERROR);
		TestReporter.logAPI(!massCancel.getFaultString().contains("Unexpected Error occurred : massCancel : java.lang.NullPointerException"), massCancel.getFaultString(), massCancel);
		
		LogItems logValidItems = new LogItems();
		logValidItems.addItem("ScheduledEventsServiceIF", "massCancel", true);
		validateLogs(massCancel, logValidItems);
		
//		LogItems logInvalidItems = new LogItems();	
//		logInvalidItems.addItem("GuestServiceV1", "create", false);
//		logInvalidItems.addItem("PartyIF", "updateExternalPartyAndLocatorId", false);
//		validateNotInLogs(massCancel, logInvalidItems);
	}
	@Test(groups = {"api", "regression", "dining", "scheduledEventsServicePort", "negative"})
	public void missingMassCancelRequest(){
		MassCancel massCancel = new MassCancel(environment);
		massCancel.setRequestNodeValueByXPath("/Envelope/Body/massCancel/massCancelRequest", BaseSoapCommands.REMOVE_NODE.toString());
		massCancel.sendRequest();
		validateApplicationError(massCancel, DiningErrorCode.INVALID_SEARCH_CRITERIA);
		TestReporter.logAPI(!massCancel.getFaultString().contains("Search Criteria is Invalid : INVALID SEARCH CRITERIA"), massCancel.getFaultString(), massCancel);
		
		LogItems logValidItems = new LogItems();
		logValidItems.addItem("ScheduledEventsServiceIF", "massCancel", true);
		validateLogs(massCancel, logValidItems);
		
//		LogItems logInvalidItems = new LogItems();	
//		logInvalidItems.addItem("GuestServiceV1", "create", false);
//		logInvalidItems.addItem("PartyIF", "updateExternalPartyAndLocatorId", false);
//		validateNotInLogs(massCancel, logInvalidItems);
	}
	@Test(groups = {"api", "regression", "dining", "scheduledEventsServicePort", "negative"})
	public void facilityIdAndMissingServiceWindowStart(){
		MassCancel massCancel = new MassCancel(environment);
		massCancel.setCommunicationsChannel(BaseSoapCommands.REMOVE_NODE.toString());
		massCancel.setFacilityId("354081");
		massCancel.setMassCancelReasonId(BaseSoapCommands.REMOVE_NODE.toString());
		massCancel.setProductChannelId(BaseSoapCommands.REMOVE_NODE.toString());
		massCancel.setSalesChannel(BaseSoapCommands.REMOVE_NODE.toString());
		massCancel.setServiceDate(BaseSoapCommands.REMOVE_NODE.toString());
		massCancel.setServiceWindowEnd(BaseSoapCommands.REMOVE_NODE.toString());
		massCancel.setServiceWindowStart(Randomness.generateCurrentXMLDatetime(0));
		massCancel.setSourceAccountingCenter(BaseSoapCommands.REMOVE_NODE.toString());
		massCancel.sendRequest();
		validateApplicationError(massCancel, DiningErrorCode.INVALID_SEARCH_CRITERIA);
		TestReporter.logAPI(!massCancel.getFaultString().contains("Search Criteria is Invalid : INVALID SEARCH CRITERIA"), massCancel.getFaultString(), massCancel);
		
		LogItems logValidItems = new LogItems();
		logValidItems.addItem("ScheduledEventsServiceIF", "massCancel", true);
		validateLogs(massCancel, logValidItems);
		
//		LogItems logInvalidItems = new LogItems();	
//		logInvalidItems.addItem("GuestServiceV1", "create", false);
//		logInvalidItems.addItem("PartyIF", "updateExternalPartyAndLocatorId", false);
//		validateNotInLogs(massCancel, logInvalidItems);
	}
	@Test(groups = {"api", "regression", "dining", "scheduledEventsServicePort", "negative"})
	public void facilityIdAndMissingServiceWindowEnd(){
		MassCancel massCancel = new MassCancel(environment);
		massCancel.setCommunicationsChannel(BaseSoapCommands.REMOVE_NODE.toString());
		massCancel.setFacilityId("354081");
		massCancel.setMassCancelReasonId(BaseSoapCommands.REMOVE_NODE.toString());
		massCancel.setProductChannelId(BaseSoapCommands.REMOVE_NODE.toString());
		massCancel.setSalesChannel(BaseSoapCommands.REMOVE_NODE.toString());
		massCancel.setServiceDate(BaseSoapCommands.REMOVE_NODE.toString());
		massCancel.setServiceWindowEnd(Randomness.generateCurrentXMLDatetime(0));
		massCancel.setServiceWindowStart(BaseSoapCommands.REMOVE_NODE.toString());
		massCancel.setSourceAccountingCenter(BaseSoapCommands.REMOVE_NODE.toString());
		massCancel.sendRequest();
		validateApplicationError(massCancel, DiningErrorCode.INVALID_SEARCH_CRITERIA);
		TestReporter.logAPI(!massCancel.getFaultString().contains("Search Criteria is Invalid : INVALID SEARCH CRITERIA"), massCancel.getFaultString(), massCancel);
		
		LogItems logValidItems = new LogItems();
		logValidItems.addItem("ScheduledEventsServiceIF", "massCancel", true);
		validateLogs(massCancel, logValidItems);
		
//		LogItems logInvalidItems = new LogItems();	
//		logInvalidItems.addItem("GuestServiceV1", "create", false);
//		logInvalidItems.addItem("PartyIF", "updateExternalPartyAndLocatorId", false);
//		validateNotInLogs(massCancel, logInvalidItems);
	}
}
