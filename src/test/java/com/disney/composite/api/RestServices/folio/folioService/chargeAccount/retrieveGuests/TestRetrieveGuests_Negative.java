package com.disney.composite.api.RestServices.folio.folioService.chargeAccount.retrieveGuests;

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
import com.disney.api.restServices.folio.folioService.chargeAccount.retrieveGuests.request.RetrieveGuestsRequest;
import com.disney.api.restServices.folio.folioService.chargeAccount.retrieveGuests.request.objects.ExternalReferenceTO;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.guestFactory.Guest;

@SuppressWarnings("unused")
public class TestRetrieveGuests_Negative {
private String environment = "Bashful";
private String OdsID;
	
	/**
	 * This will always be used as is. TestNG will pass in the Environment used
	 * @param environment - Valid environments for active testing are bashful, sleepy and grumpy
	 */
	@BeforeMethod(alwaysRun = true)
	@Parameters({  "environment" })
	public void setup(@Optional String environment) {
		//this.environment = environment;
		this.environment = "Bashful";
		Guest guest = new Guest();
		guest.sendToApi(this.environment);
		OdsID = guest.getOdsId();
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
	
	@Test(groups={"api","rest", "regression", "folio","negative", "folioService", "retrieveGuests"})
	public void testretrieveGuests_Negative_NoAuthorization()throws IOException{
		TestReporter.setDebugLevel(1);
		TestReporter.setDebugLevel(TestReporter.DEBUG);
		
		//create the json message
		RetrieveGuestsRequest request = new RetrieveGuestsRequest();
		
		//Adding data for the different nodes
		//Added External Reference Type
		request.addExternalReferenceTO();

		//Add External Reference value
		
		
		//Add SourceAccountingCenter
		request.setSourceAccountingCenter("2");
		RestResponse response= Rest.folio(environment).folioService().chargeAccount().retrieveGuests().sendPutRequestWithMissingAuthToken(request);
		TestReporter.assertTrue(response.getStatusCode() == 401, "Validate status code returned ["+response.getStatusCode()+"] was [401]");
	}	
	@Test(groups={"api","rest", "regression", "folio","negative", "folioService", "retrieveGuests"})
	public void testretrieveGuests_Negative_BlankReferenceName()throws IOException{
		TestReporter.setDebugLevel(1);
		TestReporter.setDebugLevel(TestReporter.DEBUG);
		
		//create the json message
		RetrieveGuestsRequest request = new RetrieveGuestsRequest();
		
		//Adding data for the different nodes
		//Added External Reference Type
		request.addExternalReferenceTO();
		request.getExternalReferenceTO().setReferenceName("");
		request.getExternalReferenceTO().setReferenceValue(OdsID);
		//Add External Reference value
		
		
		//Add SourceAccountingCenter
		request.setSourceAccountingCenter("2");
		RestResponse response= Rest.folio(environment).folioService().chargeAccount().retrieveGuests().sendPutRequest(request);
		TestReporter.assertTrue(response.getStatusCode() == 500, "Validate status code returned ["+response.getStatusCode()+"] was [500]");
		TestReporter.assertTrue(response.getResponse().contains("Invalid input fields. : Invalid ChargeGroup ExternalReference-ExternalReferenceTO[Reference Name =  "), "Invalid input fields. : Invalid ChargeGroup ExternalReference-ExternalReferenceTO[Reference Name =  ] was received");

	}
	
	@Test(groups={"api","rest", "regression", "folio", "negative","folioService", "retrieveGuests"})
	public void testretrieveGuests_Negative_BlankReferenceNumber()throws IOException{
		TestReporter.setDebugLevel(1);
		TestReporter.setDebugLevel(TestReporter.DEBUG);
		
		//create the json message
		RetrieveGuestsRequest request = new RetrieveGuestsRequest();
		
		//Adding data for the different nodes
		//Added External Reference Type
		request.addExternalReferenceTO();
		request.getExternalReferenceTO().setReferenceValue("");
		//Add External Reference value
		
		
		//Add SourceAccountingCenter
		request.setSourceAccountingCenter("2");
		RestResponse response= Rest.folio(environment).folioService().chargeAccount().retrieveGuests().sendPutRequest(request);
		TestReporter.assertTrue(response.getStatusCode() == 500, "Validate status code returned ["+response.getStatusCode()+"] was [500]");
		TestReporter.assertTrue(response.getResponse().contains("Invalid input fields. : Invalid ChargeGroup ExternalReference-ExternalReferenceTO"), "Invalid input fields. : Invalid ChargeGroup ExternalReference-ExternalReferenceTO[Reference Value = ");

	}
}
