package com.disney.composite.api.scheduledEventsServicePort;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.scheduledEventsServicePort.operations.RetrieveCommunicationChannels;
import com.disney.utils.TestReporter;

public class TestRetrieveCommunicationChannels {
	// Defining global variables
	protected ThreadLocal<String> testName = new ThreadLocal<String>();
	protected ThreadLocal<String> environment = new ThreadLocal<String>();
	
	@BeforeMethod(alwaysRun = true)
	@Parameters({ "environment" })
	public void setup(String environment) {this.environment.set(environment);}

	@Test(groups = {"api", "regression", "dining", "scheduledEventsServicePort"})
	public void testRetrieveCommunicationChannels(){
		TestReporter.logStep("Retrieve Communication Channels");
		RetrieveCommunicationChannels retrieveCommunicationChannels = new RetrieveCommunicationChannels(environment.get());
		retrieveCommunicationChannels.sendRequest();
		TestReporter.logAPI(!retrieveCommunicationChannels.getResponseStatusCode().equals("200"), "An error occurred during retrieval.", retrieveCommunicationChannels);
		TestReporter.assertGreaterThanZero(retrieveCommunicationChannels.getNumberOfCommunicationChannels());
	}
}