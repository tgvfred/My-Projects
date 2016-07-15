package com.disney.composite.api.scheduledEventsServicePort;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.scheduledEventsServicePort.operations.RetrieveCommentTypes;
import com.disney.utils.TestReporter;

public class TestRetrieveCommentTypes {
	// Defining global variables
	protected String testName = null;
	protected String environment = null;
	
	@BeforeMethod(alwaysRun = true)
	@Parameters({ "environment" })
	public void setup(String environment) {this.environment = environment;}

	@Test(groups = {"api", "regression", "dining", "scheduledEventsServicePort"})
	public void testRetrieveCommentTypes(){
		TestReporter.logStep("Retrieve Comment Types");
		RetrieveCommentTypes retrieveCommentTypes = new RetrieveCommentTypes(environment);
		retrieveCommentTypes.sendRequest();
		TestReporter.logAPI(!retrieveCommentTypes.getResponseStatusCode().equals("200"), "An error occurred during retrieval.", retrieveCommentTypes);
		TestReporter.assertGreaterThanZero(retrieveCommentTypes.getNumberOfCommentTypes());
	}
}