package com.disney.composite.api.travelPlanSalesOrderServiceV1;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.travelPlanSalesOrderServiceV1.operations.RetrieveServiceOptions;
import com.disney.utils.Randomness;
import com.disney.utils.TestReporter;

public class TestRetrieveServiceOptions {
	private String environment;
	private String serviceOption;
	
	@BeforeMethod(alwaysRun = true)
	@Parameters({ "environment" })
	public void setup(String environment) {
		this.environment = environment;
	}
	
	@Test(groups = {"api", "regression", "tpso", "travelPlanSalesOrderServiceV1"})
	public void testRetrieveServiceOptions(){
		RetrieveServiceOptions options = new RetrieveServiceOptions(environment, "Main");
		serviceOption = Randomness.randomString(8);
		options.setServiceOption(serviceOption);
		options.sendRequest();
		TestReporter.assertEquals(options.getResponseStatusCode(), "200", "An error occurred while getting service options.\nRequest:\n"+options.getRequest()+"\nResonse:\n"+options.getResponse());
		TestReporter.assertEquals(serviceOption, options.getServiceOption(), "The service option in the response ["+options.getServiceOption()+"] did not match the expected service option ["+serviceOption+"].");
	}
}