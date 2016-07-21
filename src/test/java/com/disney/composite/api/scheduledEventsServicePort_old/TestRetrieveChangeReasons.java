package com.disney.composite.api.scheduledEventsServicePort_old;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.scheduledEventsServicePort.operations.RetrieveChangeReasons;
import com.disney.utils.TestReporter;

public class TestRetrieveChangeReasons {
	// Defining global variables
	protected String testName = null;
	protected String environment = null;
	
	@BeforeMethod(alwaysRun = true)
	@Parameters({ "environment" })
	public void setup(String environment) {this.environment = environment;}

	@Test(groups = {"api", "regression", "dining", "scheduledEventsServicePort"})
	public void testRetrieveChangeReasons(){
		TestReporter.logStep("Retrieve Change Reasons");
		RetrieveChangeReasons retrieveChangeReasons = new RetrieveChangeReasons(environment);
		retrieveChangeReasons.sendRequest();
		TestReporter.logAPI(!retrieveChangeReasons.getResponseStatusCode().equals("200"), "An error occurred during retrieval.", retrieveChangeReasons);
		TestReporter.assertGreaterThanZero(retrieveChangeReasons.getNumberOfChangeReasons());
	}
}