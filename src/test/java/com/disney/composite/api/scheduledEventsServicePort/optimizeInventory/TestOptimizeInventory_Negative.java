package com.disney.composite.api.scheduledEventsServicePort.optimizeInventory;

import org.testng.annotations.Test;

import com.disney.api.soapServices.applicationError.ApplicationErrorCode;
import com.disney.api.soapServices.applicationError.DiningErrorCode;
import com.disney.api.soapServices.core.BaseSoapCommands;
import com.disney.api.soapServices.scheduledEventsServicePort.operations.OptimizeInventory;
import com.disney.composite.BaseTest;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.database.LogItems;

public class TestOptimizeInventory_Negative extends BaseTest{
	private String invalidValue = "INVALID";
	
	@Test(groups = {"api", "regression", "dining", "scheduledEventsServicePort", "negative"})
	public void missingInventoryGot(){
		OptimizeInventory optimize = new OptimizeInventory(environment, "Main");
		optimize.setInventoryGot(BaseSoapCommands.REMOVE_NODE.toString());
		sendRequestValidateLogs(optimize, "INVALID REQUEST ! : INVENTORY GOT or WANT IS MISSING. CANNOT OPTIMIZE !", DiningErrorCode.INVALID_REQUEST);
	}
	@Test(groups = {"api", "regression", "dining", "scheduledEventsServicePort", "negative"})
	public void missingInventoryWant(){
		OptimizeInventory optimize = new OptimizeInventory(environment, "Main");
		optimize.setInventoryWant(BaseSoapCommands.REMOVE_NODE.toString());
		sendRequestValidateLogs(optimize, "INVALID REQUEST ! : INVENTORY GOT or WANT IS MISSING. CANNOT OPTIMIZE !", DiningErrorCode.INVALID_REQUEST);
	}
	@Test(groups = {"api", "regression", "dining", "scheduledEventsServicePort", "negative"})
	public void invalidSalesChannel(){
		OptimizeInventory optimize = new OptimizeInventory(environment, "Main");
		optimize.setSalesChannel(invalidValue);
		sendRequestValidateLogs(optimize, "Sales Channel is required : null", DiningErrorCode.SALES_CHANNEL_REQUIRED);
	}
	@Test(groups = {"api", "regression", "dining", "scheduledEventsServicePort", "negative"})
	public void missingSalesChannel(){
		OptimizeInventory optimize = new OptimizeInventory(environment, "Main");
		optimize.setSalesChannel(BaseSoapCommands.REMOVE_NODE.toString());
		sendRequestValidateLogs(optimize, "Sales Channel is required : null", DiningErrorCode.SALES_CHANNEL_REQUIRED);
	}
	@Test(groups = {"api", "regression", "dining", "scheduledEventsServicePort", "negative"})
	public void invalidCommunicationsChannel(){
		OptimizeInventory optimize = new OptimizeInventory(environment, "Main");
		optimize.setCommunicationChannel(invalidValue);
		sendRequestValidateLogs(optimize, "communication Channel is required : null", DiningErrorCode.COMMUNICATION_CHANNEL_REQUIRED);
	}
	@Test(groups = {"api", "regression", "dining", "scheduledEventsServicePort", "negative"})
	public void missingCommunicationsChannel(){
		OptimizeInventory optimize = new OptimizeInventory(environment, "Main");
		optimize.setCommunicationChannel(BaseSoapCommands.REMOVE_NODE.toString());
		sendRequestValidateLogs(optimize, "communication Channel is required : null", DiningErrorCode.COMMUNICATION_CHANNEL_REQUIRED);
	}
	
	private void sendRequestValidateLogs(OptimizeInventory optimize, String falutString, ApplicationErrorCode errorCode){
		optimize.sendRequest();
		validateApplicationError(optimize, errorCode);
		TestReporter.logAPI(!optimize.getFaultString().contains(falutString), optimize.getFaultString(), optimize);

		LogItems logValidItems = new LogItems();
		logValidItems.addItem("ScheduledEventsServiceIF", "optimizeInventory", true);
		validateLogs(optimize, logValidItems);
		
		LogItems logInvalidItems = new LogItems();	
		logInvalidItems.addItem("AccommodationInventoryRequestComponentServiceIF", "updateInventoryForScheduledEvents", false);
		logInvalidItems.addItem("AccommodationInventoryRequestComponentServiceIF", "retrieveAssignmentOwner", false);
		logInvalidItems.addItem("UpdateInventory", "updateInventory", false);
		validateNotInLogs(optimize, logInvalidItems);
	}
}