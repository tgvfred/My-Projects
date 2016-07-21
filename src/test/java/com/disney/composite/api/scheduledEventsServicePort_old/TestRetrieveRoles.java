package com.disney.composite.api.scheduledEventsServicePort_old;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.scheduledEventsServicePort.operations.RetrieveRoles;
import com.disney.utils.TestReporter;

public class TestRetrieveRoles {
	// Defining global variables
	protected String testName = null;
	protected String environment = null;
	
	@BeforeMethod(alwaysRun = true)
	@Parameters({ "environment" })
	public void setup(String environment) {this.environment = environment;}

	@Test(groups = {"api", "regression", "dining", "scheduledEventsServicePort"})
	public void testRetrieveRoles(){
		TestReporter.logStep("Retrieve Roles");
		RetrieveRoles retrieveRoles = new RetrieveRoles(environment);
		retrieveRoles.sendRequest();
		TestReporter.logAPI(!retrieveRoles.getResponseStatusCode().equals("200"), "An error occurred during retrieval.", retrieveRoles);
//		TestReporter.assertEquals(retrieveRoles.getResponseStatusCode(), "200", "An error occurred during retrieval.\nRequest:\n"+retrieveRoles.getRequest()+"\nResponse:\n"+retrieveRoles.getResponse());
		TestReporter.assertGreaterThanZero(retrieveRoles.getNumberOfRoles());
	}
}