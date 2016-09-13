package com.disney.composite.api.RestServices.profile.profileService.retrieveProfiles;

import java.io.IOException;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import java.io.IOException;
import org.testng.annotations.Test;
import static org.testng.AssertJUnit.assertTrue;
import com.disney.api.restServices.Rest;
import com.disney.api.restServices.core.RestResponse;
import com.disney.api.restServices.profile.profileService.retrieveProfiles.retrieveProfiles;
import com.disney.utils.TestReporter;


@SuppressWarnings("unused")
public class TestRetrieveProfiles {
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
	public void testretrieveProfiles_Allfields () throws IOException{
		// set log levels for debugging
		TestReporter.setDebugLevel(1);
		TestReporter.setDebugLevel(TestReporter.DEBUG);
		
		RestResponse response = Rest.profile(environment).profileService().retrieveProfiles().sendGetRequest("80010390", "YC", "2366375", "true", "false", "COMMENT");
		TestReporter.assertTrue(response.getStatusCode() == 200, "Validate status code returned ["+response.getStatusCode()+"] was [200]");
		}
	@Test(groups={"api","rest", "regression", "profile", "profileservice", "retrieveProfiles"})
	public void testretrieveProfiles_IncludeInactiveProfilesTrue () throws IOException{
		// set log levels for debugging
		TestReporter.setDebugLevel(1);
		TestReporter.setDebugLevel(TestReporter.DEBUG);
		
		RestResponse response = Rest.profile(environment).profileService().retrieveProfiles().sendGetRequest("80010390", "YC", "2366375", "true", "true", "COMMENT");
		TestReporter.assertTrue(response.getStatusCode() == 200, "Validate status code returned ["+response.getStatusCode()+"] was [200]");
		}
	@Test(groups={"api","rest", "regression", "profile", "profileservice", "retrieveProfiles"})
	public void testretrieveProfiles_retrieveOnlyDefaultProfilesFalse () throws IOException{
		// set log levels for debugging
		TestReporter.setDebugLevel(1);
		TestReporter.setDebugLevel(TestReporter.DEBUG);
		
		RestResponse response = Rest.profile(environment).profileService().retrieveProfiles().sendGetRequest("80010390", "YC", "2366375", "false", "false", "COMMENT");
		TestReporter.assertTrue(response.getStatusCode() == 200, "Validate status code returned ["+response.getStatusCode()+"] was [200]");
		}
	@Test(groups={"api","rest", "regression", "profile", "profileservice", "retrieveProfiles"})
	public void testretrieveProfiles_ProfileTypeMessage () throws IOException{
		// set log levels for debugging
		TestReporter.setDebugLevel(1);
		TestReporter.setDebugLevel(TestReporter.DEBUG);
		
		RestResponse response = Rest.profile(environment).profileService().retrieveProfiles().sendGetRequest("80010390", "YC", "2366375", "true", "false", "MESSAGE");
		TestReporter.assertTrue(response.getStatusCode() == 200, "Validate status code returned ["+response.getStatusCode()+"] was [200]");
		}
	@Test(groups={"api","rest", "regression", "profile", "profileservice", "retrieveProfiles"})
	public void testretrieveProfiles_ProfileTypeService () throws IOException{
		// set log levels for debugging
		TestReporter.setDebugLevel(1);
		TestReporter.setDebugLevel(TestReporter.DEBUG);
		
		RestResponse response = Rest.profile(environment).profileService().retrieveProfiles().sendGetRequest("80010390", "YC", "2366375", "true", "false", "SERVICE");
		TestReporter.assertTrue(response.getStatusCode() == 200, "Validate status code returned ["+response.getStatusCode()+"] was [200]");
		}
	@Test(groups={"api","rest", "regression", "profile", "profileservice", "retrieveProfiles"})
	public void testretrieveProfiles_ProfileTypeAttribute () throws IOException{
		// set log levels for debugging
		TestReporter.setDebugLevel(1);
		TestReporter.setDebugLevel(TestReporter.DEBUG);
		
		RestResponse response = Rest.profile(environment).profileService().retrieveProfiles().sendGetRequest("80010390", "YC", "2366375", "true", "false", "ATTRIBUTE");
		TestReporter.assertTrue(response.getStatusCode() == 200, "Validate status code returned ["+response.getStatusCode()+"] was [200]");
		}
	@Test(groups={"api","rest", "regression", "profile", "profileservice", "retrieveProfiles"})
	public void testretrieveProfiles_ProfileTypeAlert () throws IOException{
		// set log levels for debugging
		TestReporter.setDebugLevel(1);
		TestReporter.setDebugLevel(TestReporter.DEBUG);
		
		RestResponse response = Rest.profile(environment).profileService().retrieveProfiles().sendGetRequest("80010390", "YC", "2366375", "true", "false", "ALERT");
		TestReporter.assertTrue(response.getStatusCode() == 200, "Validate status code returned ["+response.getStatusCode()+"] was [200]");
		}
	@Test(groups={"api","rest", "regression", "profile", "profileservice", "retrieveProfiles"})
	public void testretrieveProfiles_ProfileTypeGRAND_GATHERING_TYPE () throws IOException{
		// set log levels for debugging
		TestReporter.setDebugLevel(1);
		TestReporter.setDebugLevel(TestReporter.DEBUG);
		
		RestResponse response = Rest.profile(environment).profileService().retrieveProfiles().sendGetRequest("80010390", "YC", "2366375", "true", "false", "GRAND_GATHERING_TYPE");
		TestReporter.assertTrue(response.getStatusCode() == 200, "Validate status code returned ["+response.getStatusCode()+"] was [200]");
		}
}
