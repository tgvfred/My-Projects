package com.disney.composite.api.tpsoModule.travelPlanSalesOrderServiceV1;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.tpsoModule.travelPlanSalesOrderServiceV1.operations.RetrieveServiceVersion;
import com.disney.utils.TestReporter;

public class TestRetrieveServiceVersion {
	private String environment;
	
	@BeforeMethod(alwaysRun = true)
	@Parameters({ "environment" })
	public void setup(String environment) {
		this.environment = environment;
	}
	
	@Test(groups = {"api", "regression", "tpso", "travelPlanSalesOrderServiceV1"})
	public void testRetrieveServiceVersion(){
		RetrieveServiceVersion version = new RetrieveServiceVersion(environment, "Main");
		version.sendRequest();
		TestReporter.assertEquals(version.getResponseStatusCode(), "200", "An error occurred while getting enumerations.\nRequest:\n"+version.getRequest()+"\nResonse:\n"+version.getResponse());
	}
}