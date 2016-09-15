package com.disney.composite.api.RestServices.folio.chargeAccount.chargeAccount.retrieveGuests;

import org.testng.annotations.Test;
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
import com.disney.api.restServices.folio.chargeAccountService.chargeAccount.retrieveGuests.request.RetrieveGuestsRequest;
import com.disney.api.restServices.folio.chargeAccountService.chargeAccount.retrieveGuests.request.objects.ExternalReferenceTO;
import com.disney.utils.TestReporter;

@SuppressWarnings("unused")
public class TestRetrieveGuests_Negative {
private String environment;
	
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
	
	
	@Test(groups={"api","rest", "regression", "folio", "chargeAccountV2", "retrieveGuests"})
	public void testretrieveGuests_Negative_NullPointer()throws IOException{
		TestReporter.setDebugLevel(TestReporter.INFO);
		//Send Get request			
		RestResponse response= Rest.folio(environment).chargeAccountService().chargeAccount().retrieveGuests().sendGetRequest("DREAMS_TP","462143359419" , "2");
		TestReporter.assertTrue(response.getStatusCode() == 500, "Validate status code returned ["+response.getStatusCode()+"] was [500]");
		TestReporter.assertTrue(response.getResponse().contains("errorMessage\":\"General Error"),"errorMessage\":\"General Error, the correct error is received");
	}
	@Test(groups={"api","rest", "regression", "folio", "chargeAccountV2", "retrieveGuests"})
	public void testretrieveGuests_Negative_NoAcctCenter()throws IOException{
		TestReporter.setDebugLevel(TestReporter.INFO);
		//Submit Get request			
		RestResponse response= Rest.folio(environment).chargeAccountService().chargeAccount().retrieveGuests().sendGetRequest("DREAMS_TP","462143359419" , "");
		TestReporter.assertTrue(response.getStatusCode() == 500, "Validate status code returned ["+response.getStatusCode()+"] was [500]");
		TestReporter.assertTrue(response.getResponse().contains("errorMessage\":\"General Error"),"errorMessage\":\"General Error, the correct error is received");
	}
	
	@Test(groups={"api","rest", "regression", "folio", "chargeAccountV2", "retrieveGuests"})
	public void testretrieveGuests_Negative_NoAuthorization()throws IOException{
		TestReporter.setDebugLevel(TestReporter.INFO);
		//Submit get without token			
		RestResponse response= Rest.folio(environment).chargeAccountService().chargeAccount().retrieveGuests().sendGetRequestWithMissingAuthToken("DREAMS_TP","462143359419" , "2");
		TestReporter.assertTrue(response.getStatusCode() == 401, "Validate status code returned ["+response.getStatusCode()+"] was [401]");
	}
}
