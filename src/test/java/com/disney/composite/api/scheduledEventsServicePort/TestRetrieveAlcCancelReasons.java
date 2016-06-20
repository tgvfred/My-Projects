package com.disney.composite.api.scheduledEventsServicePort;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.scheduledEventsServicePort.operations.RetrieveAlaCarteCancelReasons;
import com.disney.utils.TestReporter;

public class TestRetrieveAlcCancelReasons {
	// Defining global variables
	protected ThreadLocal<String> testName = new ThreadLocal<String>();
	protected ThreadLocal<String> environment = new ThreadLocal<String>();
	
	@BeforeMethod(alwaysRun = true)
	@Parameters({ "environment" })
	public void setup(String environment) {this.environment.set(environment);}

	@Test(groups = {"api", "regression", "dining", "scheduledEventsServicePort"})
	public void testRetrieveAlcCancelReasons(){
		TestReporter.logStep("Retrieve Ala Carte Cancel Reasons");
		RetrieveAlaCarteCancelReasons retrieveAlcCancelReasons = new RetrieveAlaCarteCancelReasons(environment.get());
		retrieveAlcCancelReasons.sendRequest();
		TestReporter.logAPI(!retrieveAlcCancelReasons.getResponseStatusCode().equals("200"), "An error occurred during retrieval.", retrieveAlcCancelReasons);
		TestReporter.assertGreaterThanZero(retrieveAlcCancelReasons.getNumberOfAlcCancelReasons());
	}
}