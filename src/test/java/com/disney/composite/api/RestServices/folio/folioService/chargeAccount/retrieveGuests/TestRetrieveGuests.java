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
public class TestRetrieveGuests {
private String environment = "Bashful";
private String TPSId;
	/**
	 * This will always be used as is. TestNG will pass in the Environment used
	 * @param environment - Valid environments for active testing are bashful, sleepy and grumpy
	 */
	@BeforeMethod(alwaysRun = true)
	@Parameters({  "environment" })
	public void setup(@Optional String environment) {
		TestReporter.setDebugLevel(1);
		TestReporter.setDebugLevel(TestReporter.DEBUG);
		//this.environment = environment;
		this.environment = "Bashful";
		//generate accommodation booking
			Book book = new Book(this.environment, "bookRoomOnly2Adults2ChildrenWithoutTickets" );
			book.sendRequest();
				
			//Create new request
			CreateRequest request = new CreateRequest();
			//request.getChargeAccountRequests().get(0).getRootChargeAccountRequest().getChargeAccountCommonRequest().getGuestInfoTO().get(0).setFirstName(book.getPrimaryGuestFirstName());
			//request.getChargeAccountRequests().get(0).getRootChargeAccountRequest().getChargeAccountCommonRequest().getGuestInfoTO().get(0).setLastName(book.getPrimaryGuestLastName());
			//request.getChargeAccountRequests().get(0).getRootChargeAccountRequest().getChargeAccountCommonRequest().getGuestInfoTO().get(0).setTxnGuestId(book.getGuestId());
			//request/.getChargeAccountRequests().get(0).getRootChargeAccountRequest().getChargeAccountCommonRequest().getGuestInfoTO().get(0).getExternalReference().get(0).setReferenceName("DPMSBooking");
			//request.getChargeAccountRequests().get(0).getRootChargeAccountRequest().getChargeAccountCommonRequest().getGuestInfoTO().get(0).getExternalReference().get(0).setReferenceValue(book.getTravelPlanSegmentId());
			TPSId = book.getTravelPlanSegmentId();
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
	
	
	@Test(groups={"api","rest", "regression", "folio", "folioService", "retrieveGuests"})
	public void testretrieveGuests()throws IOException{
		TestReporter.setDebugLevel(1);
		TestReporter.setDebugLevel(TestReporter.DEBUG);	
		
		RestResponse response= Rest.folio(environment).folioService().chargeAccount().retrieveGuests().sendGetRequest("SWID","{c64a010f-0029-4ee2-8bf5-7749c792b843}","2");
		TestReporter.assertTrue(response.getStatusCode() == 200, "Validate status code returned ["+response.getStatusCode()+"] was [200]");

	}
}
