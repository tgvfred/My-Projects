package com.disney.composite.api.RestServices.folio.folioService.folio.retrieveSettlementMethods;

import java.io.IOException;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.restServices.Rest;
import com.disney.api.restServices.core.RestResponse;
import com.disney.api.restServices.folio.chargeAccountService.chargeAccount.create.request.CreateRequest;
import com.disney.api.restServices.folio.chargeAccountService.chargeAccount.create.response.CreateResponse;
import com.disney.api.restServices.folio.folioService.folio.retrieveSettlementMethods.request.RetrieveSettlementMethodsRequest;
import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.Book;
import com.disney.utils.TestReporter;

@SuppressWarnings("unused")
public class TestRetrieveSettlementMethods_Negative {
	private String environment = "Bashful";
	private Book book = null;
	private String TPId;
	private String TPSId;
	
	/**
	 * This will always be used as is. TestNG will pass in the Environment used
	 * @param environment - Valid environments for active testing are bashful, sleepy and grumpy
	 */
	@BeforeMethod(alwaysRun = true)
	@Parameters({  "environment" })
	public void setup(@Optional String environment) {
		//this.environment = environment;
		this.environment = "Bashful";
		
		//generate accommodation booking
		Book book = new Book(this.environment, "bookRoomOnly2Adults2ChildrenWithoutTickets" );
		book.sendRequest();
			
		//Create new request
		CreateRequest request = new CreateRequest();
		request.getChargeAccountRequests().get(0).getRootChargeAccountRequest().getChargeAccountCommonRequest().getChargeAccountPaymentMethodDetail().get(0).getKttwPaymentDetail().setReservationTxnGuestId(book.getGuestId());
		request.getChargeAccountRequests().get(0).getRootChargeAccountRequest().getChargeAccountCommonRequest().getGuestInfoTO().get(0).setFirstName(book.getPrimaryGuestFirstName());
		request.getChargeAccountRequests().get(0).getRootChargeAccountRequest().getChargeAccountCommonRequest().getGuestInfoTO().get(0).setLastName(book.getPrimaryGuestLastName());
		request.getChargeAccountRequests().get(0).getRootChargeAccountRequest().getChargeAccountCommonRequest().getGuestInfoTO().get(0).setTxnGuestId(book.getGuestId());
		
		TPId = book.getTravelPlanId();
		//Submit new chargeAccount Request
		RestResponse response= Rest.folio(this.environment).chargeAccountService().chargeAccount().create().sendPostRequest(request);
		CreateResponse[] createResponse = response.mapJSONToObject(CreateResponse[].class);
		TestReporter.assertTrue(response.getStatusCode() == 200, "Validate status code returned ["+response.getStatusCode()+"] was [200]");
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
	
	
	@Test(groups={"api","rest", "regression", "folio", "folioService","negative", "retrieveSettlementMethods"})
	public void testretrieveSettlementMethods_Negative_NoAuthorization()throws IOException{
		TestReporter.setDebugLevel(1);
		TestReporter.setDebugLevel(TestReporter.DEBUG);
		
		RestResponse response= Rest.folio(environment).folioService().folio().retrieveSettlementMethods().sendGetRequestWithMissingAuthToken("DREAMS_TP","462243403661","INDIVIDUAL","138847433","true");
		TestReporter.assertTrue(response.getStatusCode() == 401, "Validate status code returned ["+response.getStatusCode()+"] was [401]");
	}
	@Test(groups={"api","rest", "regression", "folio", "folioService","negative", "retrieveSettlementMethods"})
	public void testretrieveSettlementMethods_Negative_NoExtRefName()throws IOException{
		TestReporter.setDebugLevel(1);
		TestReporter.setDebugLevel(TestReporter.DEBUG);
		
		
		RestResponse response= Rest.folio(environment).folioService().folio().retrieveSettlementMethods().sendGetRequest("",TPId,"","","true");
		TestReporter.assertTrue(response.getStatusCode() == 500, "Validate status code returned ["+response.getStatusCode()+"] was [500]");
		TestReporter.assertTrue(response.getResponse().contains("Invalid ChargeGroup ExternalReference-ExternalReferenceTO[Reference Name = "), "Invalid ChargeGroup ExternalReference-ExternalReferenceTO[Reference Name = ] was received");
	}	
	@Test(groups={"api","rest", "regression", "folio", "folioService","negative", "retrieveSettlementMethods"})
	public void testretrieveSettlementMethods_Negative_NoExtRefValue()throws IOException{
		TestReporter.setDebugLevel(1);
		TestReporter.setDebugLevel(TestReporter.DEBUG);
		
		RestResponse response= Rest.folio(environment).folioService().folio().retrieveSettlementMethods().sendGetRequest("DREAMS_TP","","","","true");
		TestReporter.assertTrue(response.getStatusCode() == 500, "Validate status code returned ["+response.getStatusCode()+"] was [500]");
		TestReporter.assertTrue(response.getResponse().contains("Invalid input fields. : Invalid ChargeGroup ExternalReference-ExternalReferenceTO[Reference Name = "), "Invalid input fields. : Invalid ChargeGroup ExternalReference-ExternalReferenceTO[Reference Value = ] was received");
	}
	@Test(groups={"api","rest", "regression", "folio", "folioService","negative", "retrieveSettlementMethods"})
	public void testretrieveSettlementMethods_Negative_InvalidFolioNum()throws IOException{
		TestReporter.setDebugLevel(1);
		TestReporter.setDebugLevel(TestReporter.DEBUG);
		
		RestResponse response= Rest.folio(environment).folioService().folio().retrieveSettlementMethods().sendGetRequest("","","INDIVIDUAL","138","true");
		TestReporter.assertTrue(response.getStatusCode() == 500, "Validate status code returned ["+response.getStatusCode()+"] was [500]");
		TestReporter.assertTrue(response.getResponse().contains("errorMessage\":\"No Folio found : Folio with the ID cannot be found. folioID"), "errorMessage\":\"No Folio found : Folio with the ID cannot be found. folioID");
	}
}
