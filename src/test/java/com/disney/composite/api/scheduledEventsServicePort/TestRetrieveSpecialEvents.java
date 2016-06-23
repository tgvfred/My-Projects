package com.disney.composite.api.scheduledEventsServicePort;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.scheduledEventsServicePort.operations.RetrieveSpecialEvents;
import com.disney.utils.TestReporter;

public class TestRetrieveSpecialEvents {
	// Defining global variables
	protected String testName = null;
	protected String environment = null;
	
	@BeforeMethod(alwaysRun = true)
	@Parameters({ "environment" })
	public void setup(String environment) {this.environment = environment;}

	@Test(groups = {"api", "regression", "dining", "scheduledEventsServicePort"})
	public void testRetrieveSpecialEvents(){
		TestReporter.logStep("Retrieve Special Events");
		RetrieveSpecialEvents retrieveSpecialEvents = new RetrieveSpecialEvents(environment);
		retrieveSpecialEvents.sendRequest();
		TestReporter.logAPI(!retrieveSpecialEvents.getResponseStatusCode().equals("200"), "An error occurred during retrieval.", retrieveSpecialEvents);
		TestReporter.assertGreaterThanZero(retrieveSpecialEvents.getNumberOfSpecialEvents());
	}
}