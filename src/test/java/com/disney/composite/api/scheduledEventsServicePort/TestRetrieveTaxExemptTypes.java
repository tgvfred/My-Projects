package com.disney.composite.api.scheduledEventsServicePort;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.scheduledEventsServicePort.operations.RetrieveTaxExemptTypes;
import com.disney.utils.TestReporter;

public class TestRetrieveTaxExemptTypes {
	// Defining global variables
	protected String testName = null;
	protected String environment = null;
	
	@BeforeMethod(alwaysRun = true)
	@Parameters({ "environment" })
	public void setup(String environment) {this.environment = environment;}

	@Test(groups = {"api", "regression", "dining", "scheduledEventsServicePort"})
	public void testRetrieveTaxExemptTypes(){
		TestReporter.logStep("Retrieve Tax Exempt Types");
		RetrieveTaxExemptTypes retrieveTaxExemptTypes = new RetrieveTaxExemptTypes(environment);
		retrieveTaxExemptTypes.sendRequest();
		TestReporter.logAPI(!retrieveTaxExemptTypes.getResponseStatusCode().equals("200"), "An error occurred during retrieval.", retrieveTaxExemptTypes);
		TestReporter.assertGreaterThanZero(retrieveTaxExemptTypes.getNumberOfTaxExemptTypes());
	}
}