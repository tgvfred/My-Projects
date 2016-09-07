package com.disney.composite.api.diningModule.seatedEventsComponentService.retrieve;

import org.testng.annotations.Test;

import com.disney.api.soapServices.applicationError.ApplicationErrorCode;
import com.disney.api.soapServices.applicationError.DiningErrorCode;
import com.disney.api.soapServices.core.BaseSoapCommands;
import com.disney.api.soapServices.diningModule.seatedEventsComponentService.operations.Retrieve;
import com.disney.composite.BaseTest;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.database.LogItems;

public class TestRetrieve_Negative extends BaseTest{

	@Test(groups = {"api", "regression", "dining", "seatedEventsComponentService", "negative"})
	public void invalidReservationNumber(){
		String resNumber = "11111";
		TestReporter.logStep("Retrieve Seated Events Component Service");
		Retrieve retrieve = new Retrieve(environment, "Main");
		retrieve.setReservationNumber(resNumber);
		sendRequestAndValidateLogs(retrieve, "RECORD NOT FOUND : NO RESERVATION FOUND !", DiningErrorCode.RECORD_NOT_FOUND_EXCEPTION);
	}
	
	@Test(groups = {"api", "regression", "dining", "seatedEventsComponentService"})
	public void missingReservationNumber(){
		TestReporter.logStep("Retrieve Seated Events Component Service");
		Retrieve retrieve = new Retrieve(environment, "Main");
		retrieve.setReservationNumber(BaseSoapCommands.REMOVE_NODE.toString());
		sendRequestAndValidateLogs(retrieve, "RECORD NOT FOUND : NO RESERVATION FOUND !", DiningErrorCode.RECORD_NOT_FOUND_EXCEPTION);
	}
	
	private void sendRequestAndValidateLogs(Retrieve retrieve, String faultString, ApplicationErrorCode errorCode){
		retrieve.sendRequest();
		TestReporter.logAPI(!retrieve.getFaultString().contains(faultString), retrieve.getFaultString(), retrieve);
		validateApplicationError(retrieve, errorCode);
		
		LogItems logValidItems = new LogItems();
		logValidItems.addItem("SeatedEventsComponentService", "retrieve", true);
		validateLogs(retrieve, logValidItems, 10000);
		
		LogItems logInvalidItems = new LogItems();	
		logInvalidItems.addItem("PartyIF", "retrieveParty", false);
		validateNotInLogs(retrieve, logInvalidItems);
	}
}