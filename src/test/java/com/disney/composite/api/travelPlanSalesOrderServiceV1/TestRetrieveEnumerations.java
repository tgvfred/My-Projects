package com.disney.composite.api.travelPlanSalesOrderServiceV1;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.travelPlanSalesOrderServiceV1.operations.RetrieveEnumerations;
import com.disney.utils.TestReporter;

public class TestRetrieveEnumerations {
	private String environment;
	
	@BeforeMethod(alwaysRun = true)
	@Parameters({ "environment" })
	public void setup(String environment) {
		this.environment = environment;
	}
	
	@Test(groups = {"api", "regression", "tpso", "travelPlanSalesOrderServiceV1"})
	public void testRetrieveEnumerations(){
		RetrieveEnumerations enums = new RetrieveEnumerations(environment, "Main");
		enums.sendRequest();
		TestReporter.assertEquals(enums.getResponseStatusCode(), "200", "An error occurred while getting enumerations.\nRequest:\n"+enums.getRequest()+"\nResonse:\n"+enums.getResponse());
	}
}