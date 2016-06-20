package com.disney.composite.api.scheduledEventsServicePort;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.scheduledEventsServicePort.operations.RetrieveSalesChannels;
import com.disney.utils.TestReporter;

public class TestRetrieveSalesChannels {
	// Defining global variables
	protected ThreadLocal<String> testName = new ThreadLocal<String>();
	protected ThreadLocal<String> environment = new ThreadLocal<String>();
	
	@BeforeMethod(alwaysRun = true)
	@Parameters({ "environment" })
	public void setup(String environment) {this.environment.set(environment);}

	@Test(groups = {"api", "regression", "dining", "scheduledEventsServicePort"})
	public void testRetrieveSalesChannels(){
		TestReporter.logStep("Retrieve Sales Channels");
		RetrieveSalesChannels retrieveSalesChannels = new RetrieveSalesChannels(environment.get());
		retrieveSalesChannels.sendRequest();
		TestReporter.logAPI(!retrieveSalesChannels.getResponseStatusCode().equals("200"), "An error occurred during retrieval.", retrieveSalesChannels);
		TestReporter.assertGreaterThanZero(retrieveSalesChannels.getNumberOfSalesChannels());
	}
}