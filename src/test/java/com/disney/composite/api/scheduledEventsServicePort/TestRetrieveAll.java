package com.disney.composite.api.scheduledEventsServicePort;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.scheduledEventsServicePort.operations.RetrieveAll;
import com.disney.utils.TestReporter;

public class TestRetrieveAll {
	// Defining global variables
	protected ThreadLocal<String> testName = new ThreadLocal<String>();
	protected ThreadLocal<String> environment = new ThreadLocal<String>();
	
	@BeforeMethod(alwaysRun = true)
	@Parameters({ "environment" })
	public void setup(String environment) {this.environment.set(environment);}

	@Test(groups = {"api", "regression", "dining", "scheduledEventsServicePort"})
	public void testRetrieveAll(){
		TestReporter.logStep("Retrieve All");
		RetrieveAll retrieveAll = new RetrieveAll(environment.get());
		retrieveAll.sendRequest();
		TestReporter.logAPI(!retrieveAll.getResponseStatusCode().equals("200"), "An error occurred during retrieval.", retrieveAll);
		TestReporter.assertGreaterThanZero(retrieveAll.getNumberOfAllergies());
	}
}