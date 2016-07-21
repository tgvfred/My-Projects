package com.disney.composite.api.scheduledEventsServicePort_old;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.scheduledEventsServicePort.operations.RetrieveMassCancelReasons;
import com.disney.utils.TestReporter;

public class TestRetrieveMassCancelReasons {
	// Defining global variables
	protected String testName = null;
	protected String environment = null;
	
	@BeforeMethod(alwaysRun = true)
	@Parameters({ "environment" })
	public void setup(String environment) {this.environment = environment;}

	@Test(groups = {"api", "regression", "dining", "scheduledEventsServicePort"})
	public void testRetrieveMassCancelReasons(){
		TestReporter.logStep("Retrieve Mass Cancel Reasons");
		RetrieveMassCancelReasons retrieveMassCancelReasons = new RetrieveMassCancelReasons(environment);
		retrieveMassCancelReasons.sendRequest();
		TestReporter.logAPI(!retrieveMassCancelReasons.getResponseStatusCode().equals("200"), "An error occurred during retrieval.", retrieveMassCancelReasons);
		TestReporter.assertGreaterThanZero(retrieveMassCancelReasons.getNumberOfMassCancelReasons());
	}
}