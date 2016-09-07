package com.disney.composite.api.RestServices.travelPlan.travelPlanService.retrieveDetails;

import java.io.IOException;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.restServices.Rest;
import com.disney.api.restServices.core.RestResponse;
import com.disney.api.restServices.travelPlan.travelPlanService.retrieveDetails.request.RetrieveDetailsRequest;
import com.disney.utils.TestReporter;

@SuppressWarnings("unused")

public class TestRetrieveDetails_Negative {
private String environment = "Development";
	
	/**
	 * This will always be used as is. TestNG will pass in the Environment used
	 * @param environment - Valid environments for active testing are bashful, sleepy and grumpy
	 */
	@BeforeMethod(alwaysRun = true)
	@Parameters({  "environment" })
	public void setup(@Optional String environment) {
		//this.environment = environment;
		this.environment = "Development";
	}
	
	/**
	 * This is a sample template 	
	 * Expected updates
	 * 		- serviceClusterName - The cluster of services this service falls under (ie. Accommodation, Folio, RIM, GoMaster)
	 * 		- serviceName - name of the service
	 * 		- operationName - name of the operation
	 * 		- OperationName - name of the operation
	 * 		- DataScenario - data scenario used, data sheets can contain multiple scenarios.
	 */
	@Test(groups={"api","rest", "regression", "travelPlan", "travelPlanService", "retrieveDetails"})
	public void testretrieveDetails_Negative_NoAuthorization () throws IOException{
		// set log levels for debugging
		TestReporter.setDebugLevel(1);
		TestReporter.setDebugLevel(TestReporter.DEBUG);
		
		//create new request file
		RetrieveDetailsRequest request = new RetrieveDetailsRequest();
		
		//Add needed data for submission
		request.setTravelPlanSegmentId("462291037410");
		request.setRetrieveComments("false");
		request.setRetrieveRoom("true");
		
		RestResponse response= Rest.travelPlan("Development").travelPlanService().retrieveDetails().sendPutRequestWithMissingAuthToken(request);
		TestReporter.assertTrue(response.getStatusCode() == 401, "Validate status code returned ["+response.getStatusCode()+"] was [401]");
		}
}
