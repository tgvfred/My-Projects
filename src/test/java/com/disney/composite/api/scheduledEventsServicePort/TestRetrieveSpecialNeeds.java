package com.disney.composite.api.scheduledEventsServicePort;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.scheduledEventsServicePort.operations.RetrieveSpecialNeeds;
import com.disney.utils.TestReporter;

public class TestRetrieveSpecialNeeds {
	// Defining global variables
	protected ThreadLocal<String> testName = new ThreadLocal<String>();
	protected ThreadLocal<String> environment = new ThreadLocal<String>();
	
	@BeforeMethod(alwaysRun = true)
	@Parameters({ "environment" })
	public void setup(String environment) {this.environment.set(environment);}

	@Test(groups = {"api", "regression", "dining", "scheduledEventsServicePort"})
	public void testRetrieveSpecialNeeds(){
		TestReporter.logStep("Retrieve Special Needs");
		RetrieveSpecialNeeds retrieveSpecialNeeds = new RetrieveSpecialNeeds(environment.get());
		retrieveSpecialNeeds.sendRequest();
		TestReporter.logAPI(!retrieveSpecialNeeds.getResponseStatusCode().equals("200"), "An error occurred during retrieval.", retrieveSpecialNeeds);
		TestReporter.assertGreaterThanZero(retrieveSpecialNeeds.getNumberOfSpecialNeeds());
	}
}