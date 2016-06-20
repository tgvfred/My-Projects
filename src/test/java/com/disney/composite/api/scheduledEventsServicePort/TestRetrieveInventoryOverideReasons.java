package com.disney.composite.api.scheduledEventsServicePort;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.scheduledEventsServicePort.operations.RetrieveInventoryOverideReasons;
import com.disney.utils.TestReporter;

public class TestRetrieveInventoryOverideReasons {
	// Defining global variables
	protected ThreadLocal<String> testName = new ThreadLocal<String>();
	protected ThreadLocal<String> environment = new ThreadLocal<String>();
	
	@BeforeMethod(alwaysRun = true)
	@Parameters({ "environment" })
	public void setup(String environment) {this.environment.set(environment);}

	@Test(groups = {"api", "regression", "dining", "scheduledEventsServicePort"})
	public void testRetrieveInventoryOverideReasons(){
		TestReporter.logStep("Retrieve Inventory Overide Reasons");
		RetrieveInventoryOverideReasons retrieveInventoryOverideReasons = new RetrieveInventoryOverideReasons(environment.get());
		retrieveInventoryOverideReasons.sendRequest();
		TestReporter.logAPI(!retrieveInventoryOverideReasons.getResponseStatusCode().equals("200"), "An error occurred during retrieval.", retrieveInventoryOverideReasons);
		TestReporter.assertGreaterThanZero(retrieveInventoryOverideReasons.getNumberOfInventoryOverideReasons());
	}
}