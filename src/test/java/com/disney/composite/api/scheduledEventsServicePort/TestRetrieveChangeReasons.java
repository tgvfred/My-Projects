package com.disney.composite.api.scheduledEventsServicePort;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.scheduledEventsServicePort.operations.RetrieveChangeReasons;
import com.disney.utils.TestReporter;

public class TestRetrieveChangeReasons {
	// Defining global variables
	protected ThreadLocal<String> testName = new ThreadLocal<String>();
	protected ThreadLocal<String> environment = new ThreadLocal<String>();
	
	@BeforeMethod(alwaysRun = true)
	@Parameters({ "environment" })
	public void setup(String environment) {this.environment.set(environment);}

	@Test(groups = {"api", "regression", "dining", "scheduledEventsServicePort"})
	public void testRetrieveChangeReasons(){
		TestReporter.logStep("Retrieve Change Reasons");
		RetrieveChangeReasons retrieveChangeReasons = new RetrieveChangeReasons(environment.get());
		retrieveChangeReasons.sendRequest();
		TestReporter.logAPI(!retrieveChangeReasons.getResponseStatusCode().equals("200"), "An error occurred during retrieval.", retrieveChangeReasons);
		TestReporter.assertGreaterThanZero(retrieveChangeReasons.getNumberOfChangeReasons());
	}
}