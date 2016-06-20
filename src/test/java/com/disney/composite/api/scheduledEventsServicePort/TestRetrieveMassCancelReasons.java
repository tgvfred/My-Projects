package com.disney.composite.api.scheduledEventsServicePort;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.scheduledEventsServicePort.operations.RetrieveMassCancelReasons;
import com.disney.utils.TestReporter;

public class TestRetrieveMassCancelReasons {
	// Defining global variables
	protected ThreadLocal<String> testName = new ThreadLocal<String>();
	protected ThreadLocal<String> environment = new ThreadLocal<String>();
	
	@BeforeMethod(alwaysRun = true)
	@Parameters({ "environment" })
	public void setup(String environment) {this.environment.set(environment);}

	@Test(groups = {"api", "regression", "dining", "scheduledEventsServicePort"})
	public void testRetrieveMassCancelReasons(){
		TestReporter.logStep("Retrieve Mass Cancel Reasons");
		RetrieveMassCancelReasons retrieveMassCancelReasons = new RetrieveMassCancelReasons(environment.get());
		retrieveMassCancelReasons.sendRequest();
		TestReporter.logAPI(!retrieveMassCancelReasons.getResponseStatusCode().equals("200"), "An error occurred during retrieval.", retrieveMassCancelReasons);
		TestReporter.assertGreaterThanZero(retrieveMassCancelReasons.getNumberOfMassCancelReasons());
	}
}