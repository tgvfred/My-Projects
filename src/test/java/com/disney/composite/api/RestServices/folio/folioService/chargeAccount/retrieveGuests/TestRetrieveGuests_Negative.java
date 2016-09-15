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
import com.disney.api.restServices.folio.chargeAccountService.chargeAccount.create.request.CreateRequest;
import com.disney.api.restServices.folio.chargeAccountService.chargeAccount.create.response.CreateResponse;
import com.disney.api.restServices.folio.folioService.chargeAccount.retrieveGuests.request.RetrieveGuestsRequest;
import com.disney.api.restServices.folio.folioService.chargeAccount.retrieveGuests.request.objects.ExternalReferenceTO;
import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.Book;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.guestFactory.Guest;

@SuppressWarnings("unused")
public class TestRetrieveGuests_Negative {
private String environment;
private String TPId;
	
	/**
	 * This will always be used as is. TestNG will pass in the Environment used
	 * @param environment - Valid environments for active testing are bashful, sleepy and grumpy
	 */
	@BeforeMethod(alwaysRun = true)
	@Parameters({  "environment" })
	public void setup(@Optional String environment) {
		this.environment = environment;
		
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
	
	@Test(groups={"api","rest", "regression", "folio","negative", "folioService", "retrieveGuests"})
	public void testretrieveGuests_Negative_NoAuthorization()throws IOException{
		TestReporter.setDebugLevel(TestReporter.INFO);
		
	
		RestResponse response= Rest.folio(environment).folioService().chargeAccount().retrieveGuests().sendGetRequestWithMissingAuthToken("DREAMS_TP", TPId, "2");
		TestReporter.assertTrue(response.getStatusCode() == 401, "Validate status code returned ["+response.getStatusCode()+"] was [401]");
	}	
	@Test(groups={"api","rest", "regression", "folio","negative", "folioService", "retrieveGuests"})
	public void testretrieveGuests_Negative_BlankReferenceName()throws IOException{
		TestReporter.setDebugLevel(TestReporter.INFO);
		
		
		RestResponse response= Rest.folio(environment).folioService().chargeAccount().retrieveGuests().sendGetRequest("", TPId, "2");
		TestReporter.assertTrue(response.getStatusCode() == 500, "Validate status code returned ["+response.getStatusCode()+"] was [500]");
		TestReporter.assertTrue(response.getResponse().contains("Invalid input fields. : Invalid ChargeGroup ExternalReference-ExternalReferenceTO"), "Invalid input fields. : Invalid ChargeGroup ExternalReference-ExternalReferenceTO[Reference Name =  ] was received");

	}
	
	@Test(groups={"api","rest", "regression", "folio", "negative","folioService", "retrieveGuests"})
	public void testretrieveGuests_Negative_BlankReferenceNumber()throws IOException{
		TestReporter.setDebugLevel(TestReporter.INFO);
		RestResponse response= Rest.folio(environment).folioService().chargeAccount().retrieveGuests().sendGetRequest("DREAMS_TP", "", "2");
		TestReporter.assertTrue(response.getStatusCode() == 500, "Validate status code returned ["+response.getStatusCode()+"] was [500]");
		TestReporter.assertTrue(response.getResponse().contains("Invalid input fields. : Invalid ChargeGroup ExternalReference-ExternalReferenceTO"), "Invalid input fields. : Invalid ChargeGroup ExternalReference-ExternalReferenceTO");

	}
	@Test(groups={"api","rest", "regression", "folio", "negative","folioService", "retrieveGuests"})
	public void testretrieveGuests_Negative_InvalidSourceAcctCenter()throws IOException{
		TestReporter.setDebugLevel(TestReporter.INFO);
		RestResponse response= Rest.folio(environment).folioService().chargeAccount().retrieveGuests().sendGetRequest("DREAMS_TP", TPId, "23");
		TestReporter.assertTrue(response.getStatusCode() == 500, "Validate status code returned ["+response.getStatusCode()+"] was [500]");
		TestReporter.assertTrue(response.getResponse().contains("Invalid Charge Group reference : Root/Node reference provided is invalid - sourceAccountingCenterID"), "Invalid Charge Group reference : Root/Node reference provided is invalid - sourceAccountingCenterID");

	}
	@Test(groups={"api","rest", "regression", "folio", "negative","folioService", "retrieveGuests"})
	public void testretrieveGuests_Negative_InvalidReferenceNumber()throws IOException{
		TestReporter.setDebugLevel(TestReporter.INFO);
		RestResponse response= Rest.folio(environment).folioService().chargeAccount().retrieveGuests().sendGetRequest("DREAMS_TP", "432516262626", "2");
		TestReporter.assertTrue(response.getStatusCode() == 500, "Validate status code returned ["+response.getStatusCode()+"] was [500]");
		TestReporter.assertTrue(response.getResponse().contains("Invalid Charge Group reference : Root/Node reference provided is invalid"), "Invalid Charge Group reference : Root/Node reference provided is invalid");

	}
}
