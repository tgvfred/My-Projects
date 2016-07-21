package com.disney.composite.api.scheduledEventsServicePort_old;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.scheduledEventsServicePort.operations.RetrieveCommunicationChannels;
import com.disney.utils.TestReporter;

public class TestRetrieveCommunicationChannels {
	// Defining global variables
	protected String testName = null;
	protected String environment = null;
	
	@BeforeMethod(alwaysRun = true)
	@Parameters({ "environment" })
	public void setup(String environment) {this.environment = environment;}

	@Test(groups = {"api", "regression", "dining", "scheduledEventsServicePort"})
	public void testRetrieveCommunicationChannels(){
		TestReporter.logStep("Retrieve Communication Channels");
		RetrieveCommunicationChannels retrieveCommunicationChannels = new RetrieveCommunicationChannels(environment);
		retrieveCommunicationChannels.sendRequest();
		TestReporter.logAPI(!retrieveCommunicationChannels.getResponseStatusCode().equals("200"), "An error occurred during retrieval.", retrieveCommunicationChannels);
		TestReporter.assertGreaterThanZero(retrieveCommunicationChannels.getNumberOfCommunicationChannels());
	}
}