package com.disney.composite.api.accommodationModule.soapServices.accommodationSalesServicePort;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.utils.TestReporter;

@SuppressWarnings("unused")
public class TestRetrieveTravelPlanMediaCustomization {
	private String environment = "";
	@BeforeMethod(alwaysRun = true)
    @Parameters({  "environment" })
	public void setup(String environment) {
	this.environment = environment;
	}
	
//	@Test(groups={"api", "regression", "accommodation", "accommodationSalesService", "retrieveTravelPlanMediaCustomization"})
//	public void testretrieveTravelPlanMediaCustomization_MainFlow(){
//		RetrieveTravelPlanMediaCustomization RetrieveTravelPlanMediaCustomization = new RetrieveTravelPlanMediaCustomization(environment, "Main" );
//		RetrieveTravelPlanMediaCustomization.sendRequest();
//		//System.out.println(RetrieveTravelPlanMediaCustomization.getRequest());
//		//System.out.println(RetrieveTravelPlanMediaCustomization.getResponse());
//		TestReporter.assertEquals(RetrieveTravelPlanMediaCustomization.getResponseStatusCode(), "200", "The response code was not 200");
//		TestReporter.assertNotNull(RetrieveTravelPlanMediaCustomization.getisFacilityEligible(), "The response contains a Is Facility Eligible");
//	}
}