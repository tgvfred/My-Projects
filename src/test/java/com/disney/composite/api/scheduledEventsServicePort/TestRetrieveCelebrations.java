package com.disney.composite.api.scheduledEventsServicePort;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.scheduledEventsServicePort.operations.RetrieveCelebrations;
import com.disney.utils.TestReporter;

public class TestRetrieveCelebrations {
	// Defining global variables
	protected ThreadLocal<String> testName = new ThreadLocal<String>();
	protected ThreadLocal<String> environment = new ThreadLocal<String>();
	
	@BeforeMethod(alwaysRun = true)
	@Parameters({ "environment" })
	public void setup(String environment) {this.environment.set(environment);}

	@Test(groups = {"api", "regression", "dining", "scheduledEventsServicePort"})
	public void testRetrieveCelebrations(){
		TestReporter.logStep("Retrieve Celebrations");
		RetrieveCelebrations retrieveCelebrations = new RetrieveCelebrations(environment.get());
		retrieveCelebrations.sendRequest();
		TestReporter.logAPI(!retrieveCelebrations.getResponseStatusCode().equals("200"), "An error occurred during retrieval.", retrieveCelebrations);
		TestReporter.assertGreaterThanZero(retrieveCelebrations.getNumberOfCelebrations());
	}
}