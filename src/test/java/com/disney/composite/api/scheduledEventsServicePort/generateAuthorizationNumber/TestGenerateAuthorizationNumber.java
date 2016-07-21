package com.disney.composite.api.scheduledEventsServicePort.generateAuthorizationNumber;

import org.testng.annotations.Test;

import com.disney.api.soapServices.scheduledEventsServicePort.operations.GenerateAuthorizationNumber;
import com.disney.composite.BaseTest;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.database.LogItems;

public class TestGenerateAuthorizationNumber extends BaseTest{

	@Test(groups = {"api", "regression", "dining", "scheduledEventsServicePort"})
	public void testGenerateAuthorizationNumber(){
		TestReporter.logStep("Generate Authrization Number");
		GenerateAuthorizationNumber generateAuthorizationNumber = new GenerateAuthorizationNumber(environment);
		generateAuthorizationNumber.sendRequest();
		TestReporter.logAPI(!generateAuthorizationNumber.getResponseStatusCode().equals("200"), "An error occurred during retrieval.", generateAuthorizationNumber);
		TestReporter.assertTrue(!generateAuthorizationNumber.getAuthorizationNumber().isEmpty(), "Verify that an authorization number was generated.");
		TestReporter.log("Authorization Number: " + generateAuthorizationNumber.getAuthorizationNumber());
		
		LogItems logItems = new LogItems();
		logItems.addItem("ScheduledEventsServiceIF", "generateAuthorizationNumber", false);	
		validateLogs(generateAuthorizationNumber, logItems);
	}
}