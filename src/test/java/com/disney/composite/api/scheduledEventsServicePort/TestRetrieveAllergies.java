package com.disney.composite.api.scheduledEventsServicePort;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.scheduledEventsServicePort.operations.RetrieveAllergies;
import com.disney.utils.TestReporter;

public class TestRetrieveAllergies {
	// Defining global variables
	protected ThreadLocal<String> testName = new ThreadLocal<String>();
	protected ThreadLocal<String> environment = new ThreadLocal<String>();
	
	@BeforeMethod(alwaysRun = true)
	@Parameters({ "environment" })
	public void setup(String environment) {this.environment.set(environment);}

	@Test(groups = {"api", "regression", "dining", "scheduledEventsServicePort"})
	public void testRetrieveAllergies(){
		TestReporter.logStep("Retrieve Allergies");
		RetrieveAllergies retrieveAllergies = new RetrieveAllergies(environment.get());
		retrieveAllergies.sendRequest();
		TestReporter.logAPI(!retrieveAllergies.getResponseStatusCode().equals("200"), "An error occurred during retrieval.", retrieveAllergies);
		TestReporter.assertGreaterThanZero(retrieveAllergies.getNumberOfAllergies());
	}
}