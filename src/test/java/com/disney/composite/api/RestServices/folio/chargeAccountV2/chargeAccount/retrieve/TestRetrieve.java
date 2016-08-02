package com.disney.composite.api.RestServices.folio.chargeAccountV2.chargeAccount.retrieve;

import org.testng.annotations.Test;
import org.testng.AssertJUnit;
import static org.testng.AssertJUnit.assertTrue;
import org.testng.annotations.Test;
import static org.testng.AssertJUnit.assertTrue;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import com.disney.api.restServices.Rest;
import com.disney.api.restServices.core.RestResponse;
import com.disney.utils.TestReporter;

@SuppressWarnings("unused")
public class TestRetrieve {
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
	@Test(groups={"api", "regression", "serviceClusterName", "serviceName", "operationName"})
	public void testretrieve(){
		String json = "{\"chargeAccountIdentifiers\": [{\"chargeAccountId\": \"2364\"},"+
				   " {\"chargeAccountId\": \"2365\"}]}";		
RestResponse response= Rest.folio("Development").chargeAccountServiceV2().chargeAccount().retrieve().sendPutRequest(json);
TestReporter.assertTrue(response.getStatusCode() == 200, "Validate status code returned ["+response.getStatusCode()+"] was [200]");

	}
	
}
