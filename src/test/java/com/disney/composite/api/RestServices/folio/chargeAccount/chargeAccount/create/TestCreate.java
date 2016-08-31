package com.disney.composite.api.RestServices.folio.chargeAccount.chargeAccount.create;

import org.testng.annotations.Test;
import org.testng.annotations.Test;
import static org.testng.AssertJUnit.assertTrue;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import com.disney.api.restServices.Rest;
import com.disney.api.restServices.core.RestResponse;
import com.disney.utils.TestReporter;

import com.disney.api.restServices.Rest;
import com.disney.api.restServices.core.RestResponse;
import com.disney.utils.TestReporter;
import com.disney.api.restServices.folio.chargeAccountService.chargeAccount.create.request.CreateRequest;

@SuppressWarnings("unused")

public class TestCreate {
private String environment = "Bashful";
	
	/**
	 * This will always be used as is. TestNG will pass in the Environment used
	 * @param environment - Valid environments for active testing are bashful, sleepy and grumpy
	 */
	
	@BeforeMethod(alwaysRun = true)
	@Parameters({  "environment" })
	public void setup(@Optional String environment) {
	//this.environment = environment;
	this.environment = "Bashful";
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
	@Test(groups={"api","rest", "regression", "folio", "chargeAccountV2", "create"})
	public void testcreate(){
		TestReporter.setDebugLevel(TestReporter.DEBUG);
		
		//Create new request
		CreateRequest request = new CreateRequest();
		// Charge Account Type
		request.getChargeAccountRequests();
				
		RestResponse response= Rest.folio(environment).chargeAccountService().chargeAccount().create().sendPostRequest(request);
		TestReporter.assertTrue(response.getStatusCode() == 200, "Validate status code returned ["+response.getStatusCode()+"] was [200]");
	}
}
