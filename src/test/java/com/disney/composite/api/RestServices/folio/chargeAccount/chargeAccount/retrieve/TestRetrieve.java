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
public class TestRetrieve {
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
		//Create new request
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
	@Test(groups={"api","rest", "regression", "folio", "chargeAccountV2", "retrieve"})
	public void testretrieve_MultipleChargeAcct() throws IOException{
		TestReporter.setDebugLevel(TestReporter.DEBUG);
		//Creating ChargeAccount.retrieve request
		RetrieveRequest request = new RetrieveRequest();

		//Adding Charge Accounts to look for
		request.addChargeAccountId(caChargeAccount1);
		request.addChargeAccountId(caChargeAccount2);
		

		//Sending request and validating response
		RestResponse response= Rest.folio(environment).chargeAccountService().chargeAccount().retrieve().sendPutRequest(request);
		
		RetreiveResponse[] retreiveResponse = response.mapJSONToObject(RetreiveResponse[].class);
			for(RetreiveResponse chargeAccount:retreiveResponse){
				System.out.println("Charge Account Id:"+ chargeAccount.getRootChargeAccountResponse().getCommonChargeAccountResponse().getId());
				System.out.println("Charge Account Status:"+ chargeAccount.getRootChargeAccountResponse().getCommonChargeAccountResponse().getStatus());
				GuestInfoTO guest = chargeAccount.getRootChargeAccountResponse().getCommonChargeAccountResponse().getGuestInfoTO().get(0);
				System.out.println("Charge Account Guest Name: "+ guest.getFirstName()+ "" + guest.getLastName());
				System.out.println();
			}
			TestReporter.assertTrue(response.getStatusCode() == 200, "Validate status code returned ["+response.getStatusCode()+"] was [200]");	
	}
	
	@Test(groups={"api","rest", "regression", "folio", "chargeAccountV2", "retrieve"})
	public void testretrieve_SingleChargeAcct() throws IOException{
		TestReporter.setDebugLevel(TestReporter.DEBUG);
		//Creating ChargeAccount.retrieve request
		RetrieveRequest request = new RetrieveRequest();

		//Adding Charge Accounts to look for
		request.addChargeAccountId(caChargeAccount1);
		
		//Sending request and validating response
		RestResponse response= Rest.folio(environment).chargeAccountService().chargeAccount().retrieve().sendPutRequest(request);
		
		RetreiveResponse[] retreiveResponse = response.mapJSONToObject(RetreiveResponse[].class);
			for(RetreiveResponse chargeAccount:retreiveResponse){
				System.out.println("Charge Account Id:"+ chargeAccount.getRootChargeAccountResponse().getCommonChargeAccountResponse().getId());
				System.out.println("Charge Account Status:"+ chargeAccount.getRootChargeAccountResponse().getCommonChargeAccountResponse().getStatus());
				GuestInfoTO guest = chargeAccount.getRootChargeAccountResponse().getCommonChargeAccountResponse().getGuestInfoTO().get(0);
				System.out.println("Charge Account Guest Name: "+ guest.getFirstName()+ "" + guest.getLastName());
				System.out.println();
			}
			TestReporter.assertTrue(response.getStatusCode() == 200, "Validate status code returned ["+response.getStatusCode()+"] was [200]");	
	}
}
