package com.disney.composite.api.scheduledEventsServicePort_old;

import org.testng.annotations.Test;

import com.disney.api.soapServices.scheduledEventsServicePort.operations.RetrieveAllergies;
import com.disney.composite.BaseTest;
import com.disney.utils.TestReporter;

public class TestRetrieveAllergies extends BaseTest{

	@Test(groups = {"api", "regression", "dining", "scheduledEventsServicePort"})
	public void testRetrieveAllergies(){
		TestReporter.logStep("Retrieve Allergies");
		RetrieveAllergies retrieveAllergies = new RetrieveAllergies(environment);
		retrieveAllergies.sendRequest();
		TestReporter.logAPI(!retrieveAllergies.getResponseStatusCode().equals("200"), "An error occurred during retrieval.", retrieveAllergies);
		TestReporter.assertGreaterThanZero(retrieveAllergies.getNumberOfAllergies());
	}
}