package com.disney.composite.api.RestServices.guestLink.guestLinkService.LinkedTransGuestIdByTransGuestId;

import java.io.IOException;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.restServices.Rest;
import com.disney.api.restServices.core.RestResponse;
import com.disney.api.restServices.folio.chargeAccountService.chargeAccount.create.request.CreateRequest;
import com.disney.api.restServices.folio.chargeAccountService.chargeAccount.create.response.CreateResponse;
import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.Book;
import com.disney.utils.TestReporter;

@SuppressWarnings("unused")
public class TestLinkedTransGuestIdByTransGuestId {
private String environment;
private String TPId;
private String GuestId1;
private String GuestId2;
private String GuestId3;
private String GuestId4;
	/**
	 * This will always be used as is. TestNG will pass in the Environment used
	 * @param environment - Valid environments for active testing are bashful, sleepy and grumpy
	 */
	@BeforeMethod(alwaysRun = true)
	@Parameters({  "environment" })
	public void setup(@Optional String environment) {
		TestReporter.setDebugLevel(TestReporter.INFO);
		this.environment = environment;
		//this.environment = "Bashful";
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
		GuestId1 = book.getGuestId();
		GuestId2 = book.getResponseNodeValueByXPath("/Envelope/Body/bookResponse/bookResponse/roomDetails[1]/roomReservationDetail/guestReferenceDetails[2]/guest/guestId");
		GuestId3 = book.getResponseNodeValueByXPath("/Envelope/Body/bookResponse/bookResponse/roomDetails[1]/roomReservationDetail/guestReferenceDetails[3]/guest/guestId");
		GuestId4 = book.getResponseNodeValueByXPath("/Envelope/Body/bookResponse/bookResponse/roomDetails[1]/roomReservationDetail/guestReferenceDetails[4]/guest/guestId");
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


	@Test(groups={"api","rest", "regression", "guestlink", "guestLinkService", "linkedTransGuestByTransGuestId"})
	public void testlinkedTransGuestIdByTransGuestId_SingleGuestId()throws IOException{
		TestReporter.setDebugLevel(TestReporter.INFO);	
	
		RestResponse response= Rest.guestLink(environment).guestLinkService().LinkedTransGuestIdByTransGuestId().sendGetRequest("TRANSACTION_GUEST_ID", GuestId1);
		TestReporter.assertTrue(response.getStatusCode() == 200, "Validate status code returned ["+response.getStatusCode()+"] was [200]");
		TestReporter.assertTrue(response.getResponse().contains(GuestId1), "The response file returns the guest id of "+GuestId1);
	}
	@Test(groups={"api","rest", "regression", "guestlink", "guestLinkService", "linkedTransGuestByTransGuestId"})
	public void testlinkedTransGuestIdByTransGuestId_TwoGuestIds()throws IOException{
		TestReporter.setDebugLevel(TestReporter.INFO);	
	
		RestResponse response= Rest.guestLink(environment).guestLinkService().LinkedTransGuestIdByTransGuestId().sendGetRequest("TRANSACTION_GUEST_ID", GuestId1+","+GuestId2);
		TestReporter.assertTrue(response.getStatusCode() == 200, "Validate status code returned ["+response.getStatusCode()+"] was [200]");
		TestReporter.assertTrue(response.getResponse().contains(GuestId1), "The response file returns the guest id of "+GuestId1);
	}
	@Test(groups={"api","rest", "regression", "guestlink", "guestLinkService", "linkedTransGuestByTransGuestId"})
	public void testlinkedTransGuestIdByTransGuestId_ThreeGuestIds()throws IOException{
		TestReporter.setDebugLevel(TestReporter.INFO);	
	
		RestResponse response= Rest.guestLink(environment).guestLinkService().LinkedTransGuestIdByTransGuestId().sendGetRequest("TRANSACTION_GUEST_ID", GuestId1+","+GuestId2+","+GuestId3);
		TestReporter.assertTrue(response.getStatusCode() == 200, "Validate status code returned ["+response.getStatusCode()+"] was [200]");
		TestReporter.assertTrue(response.getResponse().contains(GuestId1), "The response file returns the guest id of "+GuestId1);
	}
	@Test(groups={"api","rest", "regression", "guestlink", "guestLinkService", "linkedTransGuestByTransGuestId"})
	public void testlinkedTransGuestIdByTransGuestId_FourGuestIds()throws IOException{
		TestReporter.setDebugLevel(TestReporter.INFO);	
	
		RestResponse response= Rest.guestLink(environment).guestLinkService().LinkedTransGuestIdByTransGuestId().sendGetRequest("TRANSACTION_GUEST_ID", GuestId1+","+GuestId2+","+GuestId3+","+GuestId4);
		TestReporter.assertTrue(response.getStatusCode() == 200, "Validate status code returned ["+response.getStatusCode()+"] was [200]");
		TestReporter.assertTrue(response.getResponse().contains(GuestId1), "The response file returns the guest id of "+GuestId1);
	}
}
