package com.disney.composite.api.RestServices.travelPlan.travelPlanService.retrieveDetails;

import java.io.IOException;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.restServices.Rest;
import com.disney.api.restServices.core.RestResponse;
import com.disney.api.restServices.travelPlan.travelPlanService.retrieveDetails.request.RetrieveDetailsRequest;
import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.Book;
import com.disney.utils.TestReporter;

@SuppressWarnings("unused")

public class TestRetrieveDetails_Negative {
private String environment;
private String TPSId;
private String TPId;
	
	/**
	 * This will always be used as is. TestNG will pass in the Environment used
	 * @param environment - Valid environments for active testing are bashful, sleepy and grumpy
	 */
	@BeforeMethod(alwaysRun = true)
	@Parameters({  "environment" })
	public void setup(@Optional String environment) {
		this.environment = environment;
		
		//generate accommodation booking
		Book book = new Book(this.environment, "bookRoomOnly2Adults2ChildrenWithoutTickets" );
		book.sendRequest();
		TPId = book.getTravelPlanId();
		TPSId = book.getTravelPlanSegmentId();
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
	@Test(groups={"api","rest", "regression", "negative", "travelPlan", "travelPlanService", "retrieveDetails"})
	public void testretrieveDetails_Negative_NoAuthorization () throws IOException{
		// set log levels for debugging
		TestReporter.setDebugLevel(TestReporter.INFO);
		
		RestResponse response= Rest.travelPlan(environment).travelPlanService().retrieveDetails().sendGetRequestWithMissingAuthToken(TPId, TPSId, "true", "true", "true", "true", "true", "true", "true", "true", "true");
		TestReporter.assertTrue(response.getStatusCode() == 401, "Validate status code returned ["+response.getStatusCode()+"] was [401]");
		}
	
	@Test(groups={"api","rest", "regression", "negative","travelPlan", "travelPlanService", "retrieveDetails"})
	public void testretrieveDetails_Negative_InvalidTravelPlan () throws IOException{
		// set log levels for debugging
		TestReporter.setDebugLevel(TestReporter.INFO);
		
		RestResponse response= Rest.travelPlan(environment).travelPlanService().retrieveDetails().sendGetRequest(TPSId, "", "true", "true", "true", "true", "true", "true", "true", "true", "true");
		TestReporter.assertTrue(response.getStatusCode() == 500, "Validate status code returned ["+response.getStatusCode()+"] was [500]");
		TestReporter.assertTrue(response.getResponse().contains("Travel Plan Segment Not Found : Invalid criteria. No data found"), "Travel Plan Segment Not Found : Invalid criteria. No data found");	
		}
	@Test(groups={"api","rest", "regression", "negative","travelPlan", "travelPlanService", "retrieveDetails"})
	public void testretrieveDetails_Negative_InvalidTravelPlanSegemnt () throws IOException{
		// set log levels for debugging
		TestReporter.setDebugLevel(TestReporter.INFO);
		
		RestResponse response= Rest.travelPlan(environment).travelPlanService().retrieveDetails().sendGetRequest("", TPId, "true", "true", "true", "true", "true", "true", "true", "true", "true");
		TestReporter.assertTrue(response.getStatusCode() == 500, "Validate status code returned ["+response.getStatusCode()+"] was [500]");
		TestReporter.assertTrue(response.getResponse().contains("Travel Plan Segment Not Found : Invalid criteria. No data found"), "Travel Plan Segment Not Found : Invalid criteria. No data found");	
		}
}
