package com.disney.composite.api.RestServices.folio.chargeAccount.chargeAccount.retrieve;

import org.testng.annotations.Test;
import org.testng.AssertJUnit;
import java.io.IOException;
import org.testng.annotations.Test;
import static org.testng.AssertJUnit.assertTrue;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import com.disney.api.restServices.Rest;
import com.disney.api.restServices.core.RestResponse;
import com.disney.api.restServices.folio.chargeAccountService.chargeAccount.create.request.CreateRequest;
import com.disney.api.restServices.folio.chargeAccountService.chargeAccount.create.response.CreateResponse;
import com.disney.api.restServices.folio.chargeAccountService.chargeAccount.retrieve.request.RetrieveRequest;
import com.disney.api.restServices.folio.chargeAccountService.chargeAccount.retrieve.response.RetreiveResponse;
import com.disney.api.restServices.folio.chargeAccountService.chargeAccount.retrieve.response.objects.GuestInfoTO;
import com.disney.utils.TestReporter;

@Test
@SuppressWarnings("unused")
public class TestRetrieve_Negative {
	private String environment = "Bashful";
	private String caChargeAccount1;
	private String caChargeAccount2;
	
	/**
	 * This will always be used as is. TestNG will pass in the Environment used
	 * @param environment - Valid environments for active testing are bashful, sleepy and grumpy
	 */
	@BeforeMethod(alwaysRun = true)
	@Parameters({  "environment" })
	public void setup(@Optional String environment) {
		//this.environment = environment;
		this.environment = "Bashful";
		//Create new request
		CreateRequest request = new CreateRequest();
		//Submit new chargeAccount Request
		RestResponse response= Rest.folio(this.environment).chargeAccountService().chargeAccount().create().sendPostRequest(request);
		CreateResponse[] createResponse = response.mapJSONToObject(CreateResponse[].class);
		for(CreateResponse chargeAccount:createResponse){
			caChargeAccount1 = chargeAccount.getRootChargeAccountCreateResponse().getChargeAccountId();
		}
		//Create 2nd new request
		CreateRequest request2 = new CreateRequest();
		//Submit new chargeAccount Request
		RestResponse response2= Rest.folio(this.environment).chargeAccountService().chargeAccount().create().sendPostRequest(request2);
		CreateResponse[] createResponse2 = response2.mapJSONToObject(CreateResponse[].class);
		for(CreateResponse chargeAccount:createResponse2){
			caChargeAccount2 = chargeAccount.getRootChargeAccountCreateResponse().getChargeAccountId();
		}
	}
	
	/**
	 * This is a sample template 	
	 * Expected updates
	 * 		- serviceClusterName - The cluster of services this service falls under (ie. Accommodation, Folio, RIM, GoMaster)
	 * 		- serviceName - name of the service
	 * 		- operationName - name of the operation
	 * 		- OperationName - name of the operation
	 * 		- DataScenario - data scenario used, data sheets can contain multiple scenarios.
	 * @throws IOException 
	 */
	@Test(groups={"api","rest", "regression", "negative", "folio", "chargeAccountV2", "retrieve"})
	public void testretrieve_Negatvie_NoAuthorization() throws IOException{
		TestReporter.setDebugLevel(TestReporter.DEBUG);
		//Creating ChargeAccount.retrieve request
		RetrieveRequest request = new RetrieveRequest();

		//Adding Charge Accounts to look for
		request.addChargeAccountId("99999");
		
		//Sending request and validating response
		RestResponse response= Rest.folio(environment).chargeAccountService().chargeAccount().retrieve().sendPutRequestWithMissingAuthToken(request);
		TestReporter.assertTrue(response.getStatusCode() == 401, "Validate status code returned ["+response.getStatusCode()+"] was [401]");	
	}
	
	@Test(groups={"api","rest", "regression", "negative", "folio", "chargeAccountV2", "retrieve"})
	public void testretrieve_Negatvie_InvalidChargeAcct() throws IOException{
		TestReporter.setDebugLevel(TestReporter.DEBUG);
		//Creating ChargeAccount.retrieve request
		RetrieveRequest request = new RetrieveRequest();

		//Adding Charge Accounts to look for
		request.addChargeAccountId("99999");
		
		//Sending request and validating response
		RestResponse response= Rest.folio(environment).chargeAccountService().chargeAccount().retrieve().sendPutRequest(request);
		
		RetreiveResponse[] retreiveResponse = response.mapJSONToObject(RetreiveResponse[].class);
		TestReporter.assertTrue(response.getStatusCode() == 500, "Validate status code returned ["+response.getStatusCode()+"] was [500]");
		TestReporter.assertTrue(response.getResponse().contains("Charge account not found. : ChargeAccount Not Found"), "Charge account not found. : ChargeAccount Not Found");	
	}
	
	@Test(groups={"api","rest", "regression", "negative", "folio", "chargeAccountV2", "retrieve"})
	public void testretrieve_Negative_BlankChargeAcct() throws IOException{
		TestReporter.setDebugLevel(TestReporter.DEBUG);
		//Creating ChargeAccount.retrieve request
		RetrieveRequest request = new RetrieveRequest();

		//Adding Charge Accounts to look for
		request.addChargeAccountId("");
		
		//Sending request and validating response
		RestResponse response= Rest.folio(environment).chargeAccountService().chargeAccount().retrieve().sendPutRequest(request);
		
		RetreiveResponse[] retreiveResponse = response.mapJSONToObject(RetreiveResponse[].class);
		TestReporter.assertTrue(response.getStatusCode() == 500, "Validate status code returned ["+response.getStatusCode()+"] was [500]");
		TestReporter.assertTrue(response.getResponse().contains("Charge account not found. : ChargeAccount Not Found"), "Charge account not found. : ChargeAccount Not Found");

	}
	
	@Test(groups={"api","rest", "regression", "negative", "folio", "chargeAccountV2", "retrieve"})
	public void testretrieve_Negative_2ndChargeAcctBlank() throws IOException{
		TestReporter.setDebugLevel(TestReporter.DEBUG);
		//Creating ChargeAccount.retrieve request
		RetrieveRequest request = new RetrieveRequest();

		//Adding Charge Accounts to look for
		request.addChargeAccountId(caChargeAccount1);
		request.addChargeAccountId("");
		
		//Sending request and validating response
		RestResponse response= Rest.folio(environment).chargeAccountService().chargeAccount().retrieve().sendPutRequest(request);
		
		RetreiveResponse[] retreiveResponse = response.mapJSONToObject(RetreiveResponse[].class);
		TestReporter.assertTrue(response.getStatusCode() == 200, "Validate status code returned ["+response.getStatusCode()+"] was [500]");
		TestReporter.assertTrue(response.getResponse().contains("\"nodeChargeAccountResponse\":null"), "nodeChargeAccountResponse:null");

	}
	
	@Test(groups={"api","rest", "regression", "negative", "folio", "chargeAccountV2", "retrieve"})
	public void testretrieve_Negative_2ndChargeAcctInvalid() throws IOException{
		TestReporter.setDebugLevel(TestReporter.DEBUG);
		//Creating ChargeAccount.retrieve request
		RetrieveRequest request = new RetrieveRequest();

		//Adding Charge Accounts to look for
		request.addChargeAccountId(caChargeAccount1);
		request.addChargeAccountId("99999");
		
		//Sending request and validating response
		RestResponse response= Rest.folio(environment).chargeAccountService().chargeAccount().retrieve().sendPutRequest(request);
		
		RetreiveResponse[] retreiveResponse = response.mapJSONToObject(RetreiveResponse[].class);
		TestReporter.assertTrue(response.getStatusCode() == 200, "Validate status code returned ["+response.getStatusCode()+"] was [500]");
		TestReporter.assertTrue(response.getResponse().contains("\"nodeChargeAccountResponse\":null"), "nodeChargeAccountResponse:null");

	}
}