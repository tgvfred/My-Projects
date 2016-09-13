package com.disney.composite.api.RestServices.profile.profileService.retrieveProfiles;

import java.io.IOException;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.restServices.Rest;
import com.disney.api.restServices.core.RestResponse;
import com.disney.utils.TestReporter;


@SuppressWarnings("unused")
public class TestRetrieveProfiles_Negative {	
private String environment;
private String TPId;
private String TPSId; 
		
	/**
	* This will always be used as is. TestNG will pass in the Environment used
	* @param environment - Valid environments for active testing are bashful, sleepy and grumpy
	*/
	@BeforeMethod(alwaysRun = true)
	@Parameters({  "environment" })
	public void setup(@Optional String environment) {
		this.environment = environment;
		
				
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
	@Test(groups={"api","rest", "regression", "profile", "profileservice", "retrieveProfiles"})
	public void testretrieveProfiles_Negative_NoAuthorization () throws IOException{
		// set log levels for debugging
		TestReporter.setDebugLevel(1);
		TestReporter.setDebugLevel(TestReporter.DEBUG);
			
		RestResponse response = Rest.profile(environment).profileService().retrieveProfiles().sendGetRequestWithMissingAuthToken("80010390", "YC", "2366375", "true", "false", "COMMENT");
		TestReporter.assertTrue(response.getStatusCode() == 401, "Validate status code returned ["+response.getStatusCode()+"] was [401");
	}
	@Test(groups={"api","rest", "regression", "profile", "profileservice", "retrieveProfiles"})
	public void testretrieveProfiles_Negative_NoFacilityRoomTypeCdProductorProfileType () throws IOException{
		// set log levels for debugging
		TestReporter.setDebugLevel(1);
		TestReporter.setDebugLevel(TestReporter.DEBUG);
			
		RestResponse response = Rest.profile(environment).profileService().retrieveProfiles().sendGetRequest("", "", "", "true", "false", "");
		TestReporter.assertTrue(response.getStatusCode() == 500, "Validate status code returned ["+response.getStatusCode()+"] was [500]");
		TestReporter.assertTrue(response.getResponse().contains("Conflicting getter definitions for property "),"Proper error was received: Conflicting getter definitions for property");
	}
	
}
