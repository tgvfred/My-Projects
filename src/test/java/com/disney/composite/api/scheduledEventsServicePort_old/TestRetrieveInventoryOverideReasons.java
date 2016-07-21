package com.disney.composite.api.scheduledEventsServicePort_old;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.scheduledEventsServicePort.operations.RetrieveInventoryOverideReasons;
import com.disney.utils.TestReporter;

public class TestRetrieveInventoryOverideReasons {
	// Defining global variables
	protected String testName = null;
	protected String environment = null;
	
	@BeforeMethod(alwaysRun = true)
	@Parameters({ "environment" })
	public void setup(String environment) {this.environment = environment;}

	@Test(groups = {"api", "regression", "dining", "scheduledEventsServicePort"})
	public void testRetrieveInventoryOverideReasons(){
		TestReporter.logStep("Retrieve Inventory Overide Reasons");
		RetrieveInventoryOverideReasons retrieveInventoryOverideReasons = new RetrieveInventoryOverideReasons(environment);
		retrieveInventoryOverideReasons.sendRequest();
		TestReporter.logAPI(!retrieveInventoryOverideReasons.getResponseStatusCode().equals("200"), "An error occurred during retrieval.", retrieveInventoryOverideReasons);
		TestReporter.assertGreaterThanZero(retrieveInventoryOverideReasons.getNumberOfInventoryOverideReasons());
	}
}