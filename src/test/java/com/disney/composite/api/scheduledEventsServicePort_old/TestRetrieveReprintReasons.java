package com.disney.composite.api.scheduledEventsServicePort_old;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.scheduledEventsServicePort.operations.RetrieveReprintReasons;
import com.disney.utils.TestReporter;

public class TestRetrieveReprintReasons {
	// Defining global variables
	protected String testName = null;
	protected String environment = null;
	
	@BeforeMethod(alwaysRun = true)
	@Parameters({ "environment" })
	public void setup(String environment) {this.environment = environment;}

	@Test(groups = {"api", "regression", "dining", "scheduledEventsServicePort"})
	public void testRetrieveReprintReasons(){
		TestReporter.logStep("Retrieve Reprint Reasons");
		RetrieveReprintReasons retrieveReprintReasons = new RetrieveReprintReasons(environment);
		retrieveReprintReasons.sendRequest();
		TestReporter.logAPI(!retrieveReprintReasons.getResponseStatusCode().equals("200"), "An error occurred during retrieval.", retrieveReprintReasons);
		TestReporter.assertGreaterThanZero(retrieveReprintReasons.getNumberOfReprintReasons());
	}
}