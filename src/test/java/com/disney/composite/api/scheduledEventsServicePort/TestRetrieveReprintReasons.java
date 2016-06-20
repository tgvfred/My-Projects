package com.disney.composite.api.scheduledEventsServicePort;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.scheduledEventsServicePort.operations.RetrieveReprintReasons;
import com.disney.utils.TestReporter;

public class TestRetrieveReprintReasons {
	// Defining global variables
	protected ThreadLocal<String> testName = new ThreadLocal<String>();
	protected ThreadLocal<String> environment = new ThreadLocal<String>();
	
	@BeforeMethod(alwaysRun = true)
	@Parameters({ "environment" })
	public void setup(String environment) {this.environment.set(environment);}

	@Test(groups = {"api", "regression", "dining", "scheduledEventsServicePort"})
	public void testRetrieveReprintReasons(){
		TestReporter.logStep("Retrieve Reprint Reasons");
		RetrieveReprintReasons retrieveReprintReasons = new RetrieveReprintReasons(environment.get());
		retrieveReprintReasons.sendRequest();
		TestReporter.logAPI(!retrieveReprintReasons.getResponseStatusCode().equals("200"), "An error occurred during retrieval.", retrieveReprintReasons);
		TestReporter.assertGreaterThanZero(retrieveReprintReasons.getNumberOfReprintReasons());
	}
}