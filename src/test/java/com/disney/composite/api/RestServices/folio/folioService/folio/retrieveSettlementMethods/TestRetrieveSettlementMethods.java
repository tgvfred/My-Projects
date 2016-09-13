package com.disney.composite.api.RestServices.folio.folioService.folio.retrieveSettlementMethods;

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
import com.disney.api.restServices.folio.folioService.folio.retrieveSettlementMethods.request.RetrieveSettlementMethodsRequest;
import com.disney.api.restServices.folio.folioService.folio.retrieveSettlementMethods.response.RetrieveSettlementMethodsResponse;
import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.Book;
import com.disney.utils.TestReporter;

	@SuppressWarnings("unused")
	public class TestRetrieveSettlementMethods {
	private String environment;
	private Book book = null;
	private String TPId;
	private String folioId;
		
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
		
		
		@Test(groups={"api","rest", "regression", "folio", "folioService", "retrieveSettlementMethods"})
		public void testretrieveSettlementMethods_ByTravelPlan()throws IOException{
			TestReporter.setDebugLevel(1);
			TestReporter.setDebugLevel(TestReporter.DEBUG);
			//Submit get with TP only
			RestResponse response= Rest.folio(environment).folioService().folio().retrieveSettlementMethods().sendGetRequest("DREAMS_TP",TPId,"","","true");
			TestReporter.assertTrue(response.getStatusCode() == 200, "Validate status code returned ["+response.getStatusCode()+"] was [200]");
		}
		@Test(groups={"api","rest", "regression", "folio", "folioService", "retrieveSettlementMethods"})
		public void testretrieveSettlementMethods_ByTPAndFolioType()throws IOException{
			TestReporter.setDebugLevel(1);
			TestReporter.setDebugLevel(TestReporter.DEBUG);
			//Submit get with TP only
			RestResponse response= Rest.folio(environment).folioService().folio().retrieveSettlementMethods().sendGetRequest("DREAMS_TP",TPId,"INDIVIDUAL","","true");
			TestReporter.assertTrue(response.getStatusCode() == 200, "Validate status code returned ["+response.getStatusCode()+"] was [200]");
		}
		@Test(groups={"api","rest", "regression", "folio", "folioService", "retrieveSettlementMethods"})
		public void testretrieveSettlementMethods_ByFolioId()throws IOException{
			TestReporter.setDebugLevel(1);
			TestReporter.setDebugLevel(TestReporter.DEBUG);
			//Submit get with TP only
			RestResponse response= Rest.folio(environment).folioService().folio().retrieveSettlementMethods().sendGetRequest("DREAMS_TP",TPId,"INDIVIDUAL","","true");
			//Get the returned FolioID
			RetrieveSettlementMethodsResponse [] retrieveSettlementMethodsResponse = response.mapJSONToObject(RetrieveSettlementMethodsResponse[].class);
			for( RetrieveSettlementMethodsResponse folio:retrieveSettlementMethodsResponse){
				folioId = Long.toString(folio.getFolioID());
				
			}
			
			//Submit get with TP only
			RestResponse response2= Rest.folio(environment).folioService().folio().retrieveSettlementMethods().sendGetRequest("","","INDIVIDUAL",folioId,"true");			
			TestReporter.assertTrue(response2.getStatusCode() == 200, "Validate status code returned ["+response.getStatusCode()+"] was [200]");
		}
}
