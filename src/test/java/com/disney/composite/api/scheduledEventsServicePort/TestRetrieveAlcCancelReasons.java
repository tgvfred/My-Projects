package com.disney.composite.api.scheduledEventsServicePort;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.scheduledEventsServicePort.operations.RetrieveAlaCarteCancelReasons;
import com.disney.utils.TestReporter;

public class TestRetrieveAlcCancelReasons {
	// Defining global variables
	protected String testName = null;
	protected String environment = null;
	
	@BeforeMethod(alwaysRun = true)
	@Parameters({ "environment" })
	public void setup(String environment) {this.environment = environment;}

	@Test(groups = {"api", "regression", "dining", "scheduledEventsServicePort"})
	public void testRetrieveAlcCancelReasons(){
		TestReporter.logStep("Retrieve Ala Carte Cancel Reasons");
		RetrieveAlaCarteCancelReasons retrieveAlcCancelReasons = new RetrieveAlaCarteCancelReasons(environment);
		retrieveAlcCancelReasons.sendRequest();
		TestReporter.logAPI(!retrieveAlcCancelReasons.getResponseStatusCode().equals("200"), "An error occurred during retrieval.", retrieveAlcCancelReasons);
		TestReporter.assertGreaterThanZero(retrieveAlcCancelReasons.getNumberOfAlcCancelReasons());
	}
}