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
	import com.disney.api.restServices.folio.folioService.folio.retrieveGuests.request.RetrieveGuestsRequest;
	import com.disney.api.restServices.folio.folioService.folio.retrieveGuests.request.objects.ExternalReferenceTO;
	import com.disney.api.restServices.folio.folioService.folio.retrieveSettlementMethods.request.RetrieveSettlementMethodsRequest;
	import com.disney.utils.TestReporter;

	@SuppressWarnings("unused")
	public class TestRetrieveSettlementMethods {
	private String environment = "Bashful";
		
		/**
		 * This will always be used as is. TestNG will pass in the Environment used
		 * @param environment - Valid environments for active testing are bashful, sleepy and grumpy
		 */
		@BeforeMethod(alwaysRun = true)
		@Parameters({  "environment" })
		public void setup(@Optional String environment) {
			//this.environment = environment;
			this.environment = "Bashful";
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
		public void testretrieveSettlementMethods()throws IOException{
			TestReporter.setDebugLevel(1);
			TestReporter.setDebugLevel(TestReporter.DEBUG);
			
			//create the json message
			RetrieveSettlementMethodsRequest request = new RetrieveSettlementMethodsRequest();
			//Adding data for the different nodes
			//Added External Reference Type
			request.getFolioIdentifierTO().getExternalReferenceTO().setReferenceName("DREAMS_TP");
			request.getFolioIdentifierTO().getExternalReferenceTO().setReferenceValue("462243403661");
			//Add Folio Type
			request.getFolioIdentifierTO().setFolioType("INDIVIDUAL");
			RestResponse response= Rest.folio(environment).folioService().folio().retrieveSettlementMethods().sendPutRequest(request);
			TestReporter.assertTrue(response.getStatusCode() == 200, "Validate status code returned ["+response.getStatusCode()+"] was [200]");
		}	
}
