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
	private String environment;
	private String caChargeAccount1;
	private String externalRefName;
	private String externalRefValue;
	private String caChargeAccount2;
	
	/**
	 * This will always be used as is. TestNG will pass in the Environment used
	 * @param environment - Valid environments for active testing are bashful, sleepy and grumpy
	 */
	@BeforeMethod(alwaysRun = true)
	@Parameters({  "environment" })
	public void setup(@Optional String environment) {
		this.environment = environment;
		
		//Create new request
		CreateRequest request = new CreateRequest();
		externalRefName = request.getChargeAccountRequests().get(0).getRootChargeAccountRequest().getChargeAccountCommonRequest().getGuestInfoTO().get(0).getExternalReference().get(0).getReferenceName();
		externalRefValue= request.getChargeAccountRequests().get(0).getRootChargeAccountRequest().getChargeAccountCommonRequest().getGuestInfoTO().get(0).getExternalReference().get(0).getReferenceValue();	
		//Submit new chargeAccount Request
		RestResponse response= Rest.folio(this.environment).chargeAccountService().chargeAccount().create().sendPostRequest(request);
		CreateResponse[] createResponse = response.mapJSONToObject(CreateResponse[].class);
		for(CreateResponse chargeAccount:createResponse){	
		caChargeAccount1 = chargeAccount.getRootChargeAccountCreateResponse().getChargeAccountId();
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
		TestReporter.setDebugLevel(TestReporter.INFO);
		//Sending request and validating response
		RestResponse response= Rest.folio(environment).chargeAccountService().chargeAccount().retrieve().sendGetRequestWithMissingAuthToken("", "", caChargeAccount1);
		TestReporter.assertTrue(response.getStatusCode() == 401, "Validate status code returned ["+response.getStatusCode()+"] was [401]");		
	}
	@Test(groups={"api","rest", "regression", "negative", "folio", "chargeAccountV2", "retrieve"})
	public void testretrieve_Negatvie_InvalidRefName() throws IOException{
		TestReporter.setDebugLevel(TestReporter.INFO);
		//Sending request and validating response
		RestResponse response= Rest.folio(environment).chargeAccountService().chargeAccount().retrieve().sendGetRequest("DREAMS_TP", "", "");
		TestReporter.assertTrue(response.getStatusCode() == 500, "Validate status code returned ["+response.getStatusCode()+"] was [500]");		
		TestReporter.assertTrue(response.getResponse().contains("Unexpected Error occurred : retrieve : null"), "Unexpected Error occurred : retrieve : null");
	}
	@Test(groups={"api","rest", "regression", "negative", "folio", "chargeAccountV2", "retrieve"})
	public void testretrieve_Negatvie_InvalidRefValue() throws IOException{
		TestReporter.setDebugLevel(TestReporter.INFO);
		//Sending request and validating response
		RestResponse response= Rest.folio(environment).chargeAccountService().chargeAccount().retrieve().sendGetRequest("", "HiThisIsATest123", "");
		TestReporter.assertTrue(response.getStatusCode() == 500, "Validate status code returned ["+response.getStatusCode()+"] was [500]");		
		TestReporter.assertTrue(response.getResponse().contains("Unexpected Error occurred : retrieve : For input string:"), "Unexpected Error occurred : retrieve : For input string:");
	}
	@Test(groups={"api","rest", "regression", "negative", "folio", "chargeAccountV2", "retrieve"})
	public void testretrieve_Negatvie_InvalidChargeAcct() throws IOException{
		TestReporter.setDebugLevel(TestReporter.INFO);
		//Sending request and validating response
		RestResponse response= Rest.folio(environment).chargeAccountService().chargeAccount().retrieve().sendGetRequest("", "", "99999");
		TestReporter.assertTrue(response.getStatusCode() == 500, "Validate status code returned ["+response.getStatusCode()+"] was [500]");
		TestReporter.assertTrue(response.getResponse().contains("Charge account not found. : ChargeAccount Not Found"), "Charge account not found. : ChargeAccount Not Found");	
	}
}