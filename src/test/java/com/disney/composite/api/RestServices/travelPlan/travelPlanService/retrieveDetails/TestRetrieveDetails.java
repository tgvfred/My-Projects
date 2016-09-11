package com.disney.composite.api.RestServices.travelPlan.travelPlanService.retrieveDetails;

import org.testng.annotations.Test;
import org.testng.AssertJUnit;
import org.testng.AssertJUnit;
import static org.testng.AssertJUnit.assertTrue;
import java.io.IOException;
import org.testng.annotations.Test;
import static org.testng.AssertJUnit.assertTrue;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import com.disney.api.restServices.Rest;
import com.disney.api.restServices.core.RestResponse;
import com.disney.api.restServices.folio.chargeAccountService.chargeAccount.create.response.CreateResponse;
import com.disney.utils.TestReporter;
import com.disney.api.restServices.travelPlan.travelPlanService.retrieveDetails.request.RetrieveDetailsRequest;
import com.disney.api.restServices.travelPlan.travelPlanService.retrieveDetails.response.RetrieveDetailsResponse;
import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.Book;

@SuppressWarnings("unused")
public class TestRetrieveDetails {
private String environment = "Bashful";
private String TPId;
private String TPSId; 
	
	/**
	 * This will always be used as is. TestNG will pass in the Environment used
	 * @param environment - Valid environments for active testing are bashful, sleepy and grumpy
	 */
	@BeforeMethod(alwaysRun = true)
	@Parameters({  "environment" })
	public void setup(@Optional String environment) {
		//this.environment = environment;
		this.environment = "Bashful";
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
	@Test(groups={"api","rest", "regression", "travelPlan", "travelPlanService", "retrieveDetails"})
	public void testretrieveDetails_ByTravelPlan () throws IOException{
		// set log levels for debugging
		TestReporter.setDebugLevel(1);
		TestReporter.setDebugLevel(TestReporter.DEBUG);
		
		RestResponse response= Rest.travelPlan(environment).travelPlanService().retrieveDetails().sendGetRequest("", "", TPId, "", "");
		TestReporter.assertTrue(response.getStatusCode() == 200, "Validate status code returned ["+response.getStatusCode()+"] was [200]");
		RetrieveDetailsResponse [] RetrieveDetailsResponse = response.mapJSONToObject(RetrieveDetailsResponse[].class);
		System.out.println(RetrieveDetailsResponse);
		TestReporter.assertTrue(response.getResponse().contains(TPId),"The response file returned the proper travel plan id "+TPId);
		TestReporter.assertTrue(response.getResponse().contains(TPSId),"The response file returned the proper travel plan segment id "+TPSId);
	}
	@Test(groups={"api","rest", "regression", "travelPlan", "travelPlanService", "retrieveDetails"})
	public void testretrieveDetails_ByTravelPlanSegment () throws IOException{
		// set log levels for debugging
		TestReporter.setDebugLevel(1);
		TestReporter.setDebugLevel(TestReporter.DEBUG);
		
		RestResponse response= Rest.travelPlan(environment).travelPlanService().retrieveDetails().sendGetRequest("", "", "", TPSId , "");
		TestReporter.assertTrue(response.getStatusCode() == 200, "Validate status code returned ["+response.getStatusCode()+"] was [200]");
		RetrieveDetailsResponse [] RetrieveDetailsResponse = response.mapJSONToObject(RetrieveDetailsResponse[].class);
		System.out.println(RetrieveDetailsResponse);
		TestReporter.assertTrue(response.getResponse().contains(TPId),"The response file returned the proper travel plan id "+TPId);
		TestReporter.assertTrue(response.getResponse().contains(TPSId),"The response file returned the proper travel plan segment id "+TPSId);

	}
	@Test(groups={"api","rest", "regression", "travelPlan", "travelPlanService", "retrieveDetails"})
	public void testretrieveDetails_ByTravelPlanAndTravelPlanSegment () throws IOException{
		// set log levels for debugging
		TestReporter.setDebugLevel(1);
		TestReporter.setDebugLevel(TestReporter.DEBUG);
		
		RestResponse response= Rest.travelPlan(environment).travelPlanService().retrieveDetails().sendGetRequest("", "", TPId, TPSId , "");
		TestReporter.assertTrue(response.getStatusCode() == 200, "Validate status code returned ["+response.getStatusCode()+"] was [200]");
		RetrieveDetailsResponse [] RetrieveDetailsResponse = response.mapJSONToObject(RetrieveDetailsResponse[].class);
		System.out.println(RetrieveDetailsResponse);
		TestReporter.assertTrue(response.getResponse().contains(TPId),"The response file returned the proper travel plan id "+TPId);
		TestReporter.assertTrue(response.getResponse().contains(TPSId),"The response file returned the proper travel plan segment id "+TPSId);

	}
}
