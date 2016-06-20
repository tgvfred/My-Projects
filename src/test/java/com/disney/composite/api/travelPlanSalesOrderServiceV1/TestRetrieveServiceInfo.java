package com.disney.composite.api.travelPlanSalesOrderServiceV1;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.travelPlanSalesOrderServiceV1.operations.RetrieveServiceInfo;
import com.disney.utils.TestReporter;

public class TestRetrieveServiceInfo {
	private String environment;
	
	@BeforeMethod(alwaysRun = true)
	@Parameters({ "environment" })
	public void setup(String environment) {
		this.environment = environment;
	}
	
	@Test(groups = {"api", "regression", "tpso", "travelPlanSalesOrderServiceV1"})
	public void testRetrieveServiceInfo(){
		RetrieveServiceInfo version = new RetrieveServiceInfo(environment, "Main");
		version.sendRequest();
		TestReporter.assertEquals(version.getResponseStatusCode(), "200", "An error occurred while getting enumerations.\nRequest:\n"+version.getRequest()+"\nResonse:\n"+version.getResponse());
	}
}