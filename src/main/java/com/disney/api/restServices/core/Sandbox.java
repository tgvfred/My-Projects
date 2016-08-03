package com.disney.api.restServices.core;

import org.junit.Test;

import com.disney.api.restServices.Rest;
import com.disney.api.restServices.folio.chargeAccountServiceV2.chargeAccount.retrieve.request.RetrieveRequest;
import com.disney.api.restServices.folio.chargeAccountServiceV2.chargeAccount.retrieve.response.RetrieveResponse;
import com.disney.api.restServices.folio.chargeAccountServiceV2.chargeAccount.retrieve.response.objects.GuestInfoTO;
import com.disney.api.restServices.folio.chargeAccountServiceV2.chargeAccount.retrieve.response.objects.RootChargeAccountResponse;
import com.disney.utils.TestReporter;

public class Sandbox {

	@Test
	public void test() {
		TestReporter.setDebugLevel(1);
		
		//Creating ChargeAccount.retrieve request
		RetrieveRequest request = new RetrieveRequest();
		
		//Adding Charge Accounts to look for
		request.addChargeAccountId("2364");
		request.addChargeAccountId("2365");
		request.addChargeAccountId("2366");
		request.addChargeAccountId("2367");
		request.addChargeAccountId("2368");
		request.addChargeAccountId("2369");
		
		//Sending request and validating response
		RestResponse response= Rest.folio("Development").chargeAccountServiceV2().chargeAccount().retrieve().sendPutRequest(request);		
		TestReporter.assertTrue(response.getStatusCode() == 200, "Validate status code returned ["+response.getStatusCode()+"] was [200]");
		
		//Navigating response json to get specific data
		RetrieveResponse[] retrieveResponse = response.mapJSONToObject(RetrieveResponse[].class);
		for(RetrieveResponse chargeAccount : retrieveResponse){
			for(RootChargeAccountResponse rootCA : chargeAccount.getRootChargeAccountResponse()){
				System.out.println("Charge Account Id: " + rootCA.getCommonChargeAccountResponse().getId());
				System.out.println("Charge Account Status: " + rootCA.getCommonChargeAccountResponse().getStatus());
				GuestInfoTO guest = rootCA.getCommonChargeAccountResponse().getGuestInfoTO().get(0);
				System.out.println("Charge Account Guest Name: " + guest.getFirstName() + " " + guest.getLastName());
				System.out.println();
			}
		}
	}
}
