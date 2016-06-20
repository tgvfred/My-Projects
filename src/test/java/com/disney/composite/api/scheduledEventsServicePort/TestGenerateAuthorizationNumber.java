package com.disney.composite.api.scheduledEventsServicePort;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.scheduledEventsServicePort.operations.GenerateAuthorizationNumber;
import com.disney.utils.TestReporter;

public class TestGenerateAuthorizationNumber {
	// Defining global variables
	protected ThreadLocal<String> testName = new ThreadLocal<String>();
	protected ThreadLocal<String> environment = new ThreadLocal<String>();
	
	@BeforeMethod(alwaysRun = true)
	@Parameters({ "environment" })
	public void setup(String environment) {this.environment.set(environment);}

	@Test(groups = {"api", "regression", "dining", "scheduledEventsServicePort"})
	public void testGenerateAuthorizationNumber(){
		TestReporter.logStep("Generate Authrization Number");
		GenerateAuthorizationNumber generateAuthorizationNumber = new GenerateAuthorizationNumber(environment.get());
		generateAuthorizationNumber.sendRequest();
		TestReporter.logAPI(!generateAuthorizationNumber.getResponseStatusCode().equals("200"), "An error occurred during retrieval.", generateAuthorizationNumber);
		TestReporter.assertTrue(!generateAuthorizationNumber.getAuthorizationNumber().isEmpty(), "Authorization Number not generated.");
		TestReporter.log("Authorization Number: " + generateAuthorizationNumber.getAuthorizationNumber());
	}
}