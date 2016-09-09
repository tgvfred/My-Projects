package com.disney.composite.api.diningModule.scheduledEventsServicePort.retrieveRoles;

import java.util.Map;

import org.testng.annotations.Test;

import com.disney.api.BaseTest;
import com.disney.api.soapServices.diningModule.scheduledEventsServicePort.operations.RetrieveRoles;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.database.LogItems;

public class TestRetrieveRoles extends BaseTest{

	@Test(groups = {"api", "regression", "dining", "scheduledEventsServicePort"})
	public void testRetrieveRoles(){
		TestReporter.logStep("Retrieve Roles");
		RetrieveRoles retrieveRoles = new RetrieveRoles(environment);
		retrieveRoles.sendRequest();
		TestReporter.logAPI(!retrieveRoles.getResponseStatusCode().equals("200"), "An error occurred during retrieval: " + retrieveRoles.getFaultString(), retrieveRoles);
		TestReporter.assertTrue(retrieveRoles.getNumberOfRoles() > 0, "Verify roles are returned.");
		TestReporter.assertTrue(retrieveRoles.getRoles().size() == retrieveRoles.getNumberOfRoles(), "Verify the number of roles is ["+retrieveRoles.getNumberOfRoles()+"].");
		reportValues("Roles", retrieveRoles.getNumberOfRoles(), retrieveRoles.getRoles());
		
		LogItems logItems = new LogItems();
		logItems.addItem("ScheduledEventsServiceIF", "retrieveRoles", false);
		logItems.addItem("PartyIF", "getOptions", false);	
		validateLogs(retrieveRoles, logItems);
	}
	
	private void reportValues(String valueName, int size, Map<String, String> values){
		for(int i = 0; i < size; i++){
			TestReporter.log(valueName + ": " + values.get(String.valueOf(i)));
		}
	}
}