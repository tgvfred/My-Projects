package com.disney.composite.api.scheduledEventsServicePort_old;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.scheduledEventsServicePort.operations.RetrieveGuestRequests;
import com.disney.utils.TestReporter;

public class TestRetrieveGuestRequests {
	// Defining global variables
	protected String testName = null;
	protected String environment = null;
	
	@BeforeMethod(alwaysRun = true)
	@Parameters({ "environment" })
	public void setup(String environment) {this.environment = environment;}

	@Test(groups = {"api", "regression", "dining", "scheduledEventsServicePort"})
	public void testRetrieveGuestRequests(){
		TestReporter.logStep("Retrieve Guest Request");
		RetrieveGuestRequests retrieveGuestRequests = new RetrieveGuestRequests(environment);
		retrieveGuestRequests.sendRequest();
		TestReporter.logAPI(!retrieveGuestRequests.getResponseStatusCode().equals("200"), "An error occurred during retrieval.", retrieveGuestRequests);
		TestReporter.assertGreaterThanZero(retrieveGuestRequests.getNumberOfGuestRequests());
	}
}