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
import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.Book;
import com.disney.utils.TestReporter;

@Test
@SuppressWarnings("unused")
public class TestRetrieve {
	private String environment;
	private String caChargeAccount1;
	private String externalRefName;
	private String externalRefValue;
	private String caChargeAccount2;
	
	/**
	 * This will always be used as is. TestNG will pass in the Environment used
	 * @param environment - Valid environments for active testing are bashful, sleepy and grumpy
	 * @param book 
	 */
	@BeforeMethod(alwaysRun = true)
	@Parameters({  "environment" })
	public void setup(@Optional String environment) {
		this.environment = environment;
		
		//generate accommodation booking
		
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
	@Test(groups={"api","rest", "regression", "folio", "chargeAccountV2", "retrieve"})
	public void testretrieve_GetChargeAcctId() throws IOException{
		TestReporter.setDebugLevel(TestReporter.INFO);
		//Submit file
		RestResponse response= Rest.folio(environment).chargeAccountService().chargeAccount().retrieve().sendGetRequest("", "", caChargeAccount1);
		TestReporter.assertTrue(response.getStatusCode() == 200, "Validate status code returned ["+response.getStatusCode()+"] was [200]");
		TestReporter.assertTrue(response.getResponse().contains(caChargeAccount1), "Retrieve returned the proper response for ChargeAccount Id of"+caChargeAccount1);	
	}
	@Test(groups={"api","rest", "regression", "folio", "chargeAccountV2", "retrieve"})
	public void testretrieve_GetExternalRefValue() throws IOException{
		TestReporter.setDebugLevel(TestReporter.INFO);
		//Submit file
		RestResponse response= Rest.folio(environment).chargeAccountService().chargeAccount().retrieve().sendGetRequest("", "87395260", "");
		TestReporter.assertTrue(response.getStatusCode() == 200, "Validate status code returned ["+response.getStatusCode()+"] was [200]");
		TestReporter.assertTrue(response.getResponse().contains("87395260"), "Retrieve returned the proper response for the external reference value provided 87395260");
	}
	@Test(groups={"api","rest", "regression", "folio", "chargeAccountV2", "retrieve"})
	public void testretrieve_GetExternalRefNameValue() throws IOException{
		TestReporter.setDebugLevel(TestReporter.INFO);
		//Submit file
		RestResponse response= Rest.folio(environment).chargeAccountService().chargeAccount().retrieve().sendGetRequest(externalRefName, externalRefValue, "");
		TestReporter.assertTrue(response.getStatusCode() == 200, "Validate status code returned ["+response.getStatusCode()+"] was [200]");
		TestReporter.assertTrue(response.getResponse().contains(caChargeAccount1), "Retrieve returned the proper response for ChargeAccount Id of"+caChargeAccount1);
	}
}
