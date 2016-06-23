package com.disney.composite.api.scheduledEventsServicePort;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.scheduledEventsServicePort.operations.RetrieveSalesChannels;
import com.disney.utils.TestReporter;

public class TestRetrieveSalesChannels {
	// Defining global variables
	protected String testName = null;
	protected String environment = null;
	
	@BeforeMethod(alwaysRun = true)
	@Parameters({ "environment" })
	public void setup(String environment) {this.environment = environment;}

	@Test(groups = {"api", "regression", "dining", "scheduledEventsServicePort"})
	public void testRetrieveSalesChannels(){
		TestReporter.logStep("Retrieve Sales Channels");
		RetrieveSalesChannels retrieveSalesChannels = new RetrieveSalesChannels(environment);
		retrieveSalesChannels.sendRequest();
		TestReporter.logAPI(!retrieveSalesChannels.getResponseStatusCode().equals("200"), "An error occurred during retrieval.", retrieveSalesChannels);
		TestReporter.assertGreaterThanZero(retrieveSalesChannels.getNumberOfSalesChannels());
	}
}