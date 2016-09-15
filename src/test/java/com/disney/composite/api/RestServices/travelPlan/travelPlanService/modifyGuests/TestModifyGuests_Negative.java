package com.disney.composite.api.RestServices.travelPlan.travelPlanService.modifyGuests;

import java.io.IOException;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.restServices.Rest;
import com.disney.api.restServices.core.RestResponse;
import com.disney.api.restServices.travelPlan.travelPlanService.modifyGuests.request.ModifyGuestsRequest;
import com.disney.utils.TestReporter;

@SuppressWarnings("unused")
public class TestModifyGuests_Negative {
private String environment;
	
	/**
	 * This will always be used as is. TestNG will pass in the Environment used
	 * @param environment - Valid environments for active testing are bashful, sleepy and grumpy
	 */
	@BeforeMethod(alwaysRun = true)
	@Parameters({  "environment" })
	public void setup(@Optional String environment) {
		this.environment = environment;
		//this.environment = "Bashful";
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
	@Test(groups={"api","rest", "regression","negative", "travelPlan", "travelPlanService", "modifyGuests"})
	public void testmodifyGuests_Negative_NoAuthorization () throws IOException{
		// set log levels for debugging
		TestReporter.setDebugLevel(TestReporter.INFO);
				
		//create new request file
		ModifyGuestsRequest request = new ModifyGuestsRequest();
				
		
				
		//Submit request
		RestResponse response= Rest.travelPlan(environment).travelPlanService().modifyGuests().sendPutRequestWithMissingAuthToken(request);
		TestReporter.assertTrue(response.getStatusCode() == 401, "Validate status code returned ["+response.getStatusCode()+"] was [401]");
	}
	@Test(groups={"api","rest", "regression","negative", "travelPlan", "travelPlanService", "modifyGuests"})
	public void testmodifyGuests_Negative_NoGuestId () throws IOException{
		// set log levels for debugging
		TestReporter.setDebugLevel(TestReporter.INFO);
				
		//create new request file
		ModifyGuestsRequest request = new ModifyGuestsRequest();
				
		//Add needed data for submission
		request.getGuestReferenceDetail().get(0).getGuest().setGuestId("");
		
				
		//Submit request
		RestResponse response= Rest.travelPlan(environment).travelPlanService().modifyGuests().sendPutRequest(request);
		TestReporter.assertTrue(response.getStatusCode() == 500, "Validate status code returned ["+response.getStatusCode()+"] was [500]");
		TestReporter.assertTrue(response.getResponse().contains("errorMessage\":\"org.codehaus.jackson.map.JsonMappingException: Problem deserializing property 'guestId'"), "errorMessage\":\"org.codehaus.jackson.map.JsonMappingException: Problem deserializing property 'guestId'");
	}
	@Test(groups={"api","rest", "regression","negative", "travelPlan", "travelPlanService", "modifyGuests"})
	public void testmodifyGuests_Negative_NoLocatorID () throws IOException{
		// set log levels for debugging
		TestReporter.setDebugLevel(TestReporter.INFO);
				
		//create new request file
		ModifyGuestsRequest request = new ModifyGuestsRequest();
				
		//Add needed data for submission
		request.getGuestReferenceDetail().get(0).getGuest().getAddressDetails().get(0).setLocatorId("");
		
				
		//Submit request
		RestResponse response= Rest.travelPlan(environment).travelPlanService().modifyGuests().sendPutRequest(request);
		TestReporter.assertTrue(response.getStatusCode() == 500, "Validate status code returned ["+response.getStatusCode()+"] was [500]");
		TestReporter.assertTrue(response.getResponse().contains("errorMessage\":\"org.codehaus.jackson.map.JsonMappingException: Problem deserializing property 'locatorId'"), "errorMessage\":\"org.codehaus.jackson.map.JsonMappingException: Problem deserializing property 'locatorId'");
	}
	@Test(groups={"api","rest", "regression","negative", "travelPlan", "travelPlanService", "modifyGuests"})
	public void testmodifyGuests_Negative_NoGuestLocatorID () throws IOException{
		// set log levels for debugging
		TestReporter.setDebugLevel(TestReporter.INFO);
				
		//create new request file
		ModifyGuestsRequest request = new ModifyGuestsRequest();
				
		//Add needed data for submission
		request.getGuestReferenceDetail().get(0).getGuest().getAddressDetails().get(0).setGuestLocatorId("");
		
				
		//Submit request
		RestResponse response= Rest.travelPlan(environment).travelPlanService().modifyGuests().sendPutRequest(request);
		TestReporter.assertTrue(response.getStatusCode() == 500, "Validate status code returned ["+response.getStatusCode()+"] was [500]");
		TestReporter.assertTrue(response.getResponse().contains("errorMessage\":\"org.codehaus.jackson.map.JsonMappingException: Problem deserializing property 'guestLocatorId'"), "errorMessage\":\"org.codehaus.jackson.map.JsonMappingException: Problem deserializing property 'guestLocatorId'");
	}
}
