package com.disney.composite.api.RestServices.folio.chargeAccount.chargeAccount.updatePin;


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
import com.disney.api.restServices.folio.chargeAccountService.chargeAccount.updatePin.request.UpdatePinRequest;
import com.disney.api.restServices.folio.chargeAccountService.chargeAccount.updatePin.response.UpdatePinResponse;
import com.disney.utils.TestReporter;

@SuppressWarnings("unused")
public class TestUpdatePin {
private String environment = "Bashful";
private CreateResponse caCreate = null;	
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
	@Test(groups={"api","rest", "regression", "folio", "chargeAccountV2", "updatePin"})
	public void testupdatePin()throws IOException{
		TestReporter.setDebugLevel(1);
		TestReporter.setDebugLevel(TestReporter.DEBUG);
		
		//Creating ChargeAccount.updatePin request
		UpdatePinRequest request = new UpdatePinRequest();

		//Adding Charge Accounts to look for
		request.addChargeAccountId("2938");
		//Update value for Pin
		request.setPinNumber("1369");
	
	RestResponse response= Rest.folio(environment).chargeAccountService().chargeAccount().updatePin().sendPutRequest(request);
	TestReporter.assertTrue(response.getStatusCode() == 200, "Validate status code returned ["+response.getStatusCode()+"] was [200]");
	TestReporter.assertTrue(response.getResponse().contains("Success"), "Update Pin status of was [Success]");
		
	}

}
