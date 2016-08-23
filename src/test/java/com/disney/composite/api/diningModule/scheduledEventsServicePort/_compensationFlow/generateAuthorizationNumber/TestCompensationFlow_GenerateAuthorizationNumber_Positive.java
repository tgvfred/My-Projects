package com.disney.composite.api.diningModule.scheduledEventsServicePort._compensationFlow.generateAuthorizationNumber;

import org.testng.annotations.Test;

import com.disney.api.soapServices.diningModule.scheduledEventsServicePort.operations.GenerateAuthorizationNumber;
import com.disney.composite.BaseTest;
import com.disney.utils.TestReporter;

public class TestCompensationFlow_GenerateAuthorizationNumber_Positive extends BaseTest{

	@Test(groups = {"api", "regression", "dining", "scheduledEventsServicePort", "compensation"})
	public void testCompensationFlow_GenerateAuthorizationNumber_Positive(){
		TestReporter.logStep("Generate Authrization Number");
		GenerateAuthorizationNumber generateAuthorizationNumber = new GenerateAuthorizationNumber(environment);
		generateAuthorizationNumber.sendRequest();
		TestReporter.logAPI(!generateAuthorizationNumber.getResponseStatusCode().equals("200"), "An error occurred during retrieval: " + generateAuthorizationNumber.getFaultString(), generateAuthorizationNumber);
		TestReporter.assertTrue(!generateAuthorizationNumber.getAuthorizationNumber().isEmpty(), "Verify that an authorization number was generated.");
		TestReporter.log("Authorization Number: " + generateAuthorizationNumber.getAuthorizationNumber());
	}
}