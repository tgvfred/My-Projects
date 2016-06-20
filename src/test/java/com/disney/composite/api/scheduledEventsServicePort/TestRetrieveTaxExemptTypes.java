package com.disney.composite.api.scheduledEventsServicePort;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.scheduledEventsServicePort.operations.RetrieveTaxExemptTypes;
import com.disney.utils.TestReporter;

public class TestRetrieveTaxExemptTypes {
	// Defining global variables
	protected ThreadLocal<String> testName = new ThreadLocal<String>();
	protected ThreadLocal<String> environment = new ThreadLocal<String>();
	
	@BeforeMethod(alwaysRun = true)
	@Parameters({ "environment" })
	public void setup(String environment) {this.environment.set(environment);}

	@Test(groups = {"api", "regression", "dining", "scheduledEventsServicePort"})
	public void testRetrieveTaxExemptTypes(){
		TestReporter.logStep("Retrieve Tax Exempt Types");
		RetrieveTaxExemptTypes retrieveTaxExemptTypes = new RetrieveTaxExemptTypes(environment.get());
		retrieveTaxExemptTypes.sendRequest();
		TestReporter.logAPI(!retrieveTaxExemptTypes.getResponseStatusCode().equals("200"), "An error occurred during retrieval.", retrieveTaxExemptTypes);
		TestReporter.assertGreaterThanZero(retrieveTaxExemptTypes.getNumberOfTaxExemptTypes());
	}
}