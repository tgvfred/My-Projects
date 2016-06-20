package com.disney.composite.api.scheduledEventsServicePort;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.scheduledEventsServicePort.operations.RetrieveCancelReasons;
import com.disney.utils.TestReporter;

public class TestRetrieveCancelReasons {
	// Defining global variables
	protected ThreadLocal<String> testName = new ThreadLocal<String>();
	protected ThreadLocal<String> environment = new ThreadLocal<String>();
	
	@BeforeMethod(alwaysRun = true)
	@Parameters({ "environment" })
	public void setup(String environment) {this.environment.set(environment);}

	@Test(groups = {"api", "regression", "dining", "scheduledEventsServicePort"})
	public void testRetrieveCancelReasons(){
		TestReporter.logStep("Retrieve Cancel Reasons");
		RetrieveCancelReasons retrieveCancelReasons = new RetrieveCancelReasons(environment.get());
		retrieveCancelReasons.sendRequest();
		TestReporter.logAPI(!retrieveCancelReasons.getResponseStatusCode().equals("200"), "An error occurred during retrieval.", retrieveCancelReasons);
		TestReporter.assertGreaterThanZero(retrieveCancelReasons.getNumberOfCancelReasons());
	}
}