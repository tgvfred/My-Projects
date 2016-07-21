package com.disney.composite.api.scheduledEventsServicePort_old;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.scheduledEventsServicePort.operations.RetrieveCancelReasons;
import com.disney.utils.TestReporter;

public class TestRetrieveCancelReasons {
	// Defining global variables
	protected String testName = null;
	protected String environment = null;
	
	@BeforeMethod(alwaysRun = true)
	@Parameters({ "environment" })
	public void setup(String environment) {this.environment = environment;}

	@Test(groups = {"api", "regression", "dining", "scheduledEventsServicePort"})
	public void testRetrieveCancelReasons(){
		TestReporter.logStep("Retrieve Cancel Reasons");
		RetrieveCancelReasons retrieveCancelReasons = new RetrieveCancelReasons(environment);
		retrieveCancelReasons.sendRequest();
		TestReporter.logAPI(!retrieveCancelReasons.getResponseStatusCode().equals("200"), "An error occurred during retrieval.", retrieveCancelReasons);
		TestReporter.assertGreaterThanZero(retrieveCancelReasons.getNumberOfCancelReasons());
	}
}