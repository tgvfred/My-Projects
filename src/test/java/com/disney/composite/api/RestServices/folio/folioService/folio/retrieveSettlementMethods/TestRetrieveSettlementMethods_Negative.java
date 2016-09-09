package com.disney.composite.api.RestServices.folio.folioService.folio.retrieveSettlementMethods;

import java.io.IOException;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.restServices.Rest;
import com.disney.api.restServices.core.RestResponse;
import com.disney.api.restServices.folio.folioService.folio.retrieveSettlementMethods.request.RetrieveSettlementMethodsRequest;
import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.Book;
import com.disney.utils.TestReporter;

@SuppressWarnings("unused")
public class TestRetrieveSettlementMethods_Negative {
	private String environment = "Bashful";
	private Book book = null;
	
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
		book= new Book(environment, "bookRoomOnly2Adults2ChildrenWithoutTickets" );
		book.sendRequest();
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
		
		//create the json message
		RetrieveSettlementMethodsRequest request = new RetrieveSettlementMethodsRequest();
		//Adding data for the different nodes
		//Added External Reference Type
		request.getFolioIdentifierTO().getExternalReferenceTO().setReferenceName("DREAMS_TP");
		request.getFolioIdentifierTO().getExternalReferenceTO().setReferenceValue(book.getTravelPlanId());
		//Add Folio Type
		request.getFolioIdentifierTO().setFolioType("INDIVIDUAL");
		RestResponse response= Rest.folio(environment).folioService().folio().retrieveSettlementMethods().sendPutRequestWithMissingAuthToken(request);
		TestReporter.assertTrue(response.getStatusCode() == 401, "Validate status code returned ["+response.getStatusCode()+"] was [401]");
	}
	@Test(groups={"api","rest", "regression", "folio", "folioService","negative", "retrieveSettlementMethods"})
	public void testretrieveSettlementMethods_Negative_NoExtRefName()throws IOException{
		TestReporter.setDebugLevel(1);
		TestReporter.setDebugLevel(TestReporter.DEBUG);
		
		//create the json message
		RetrieveSettlementMethodsRequest request = new RetrieveSettlementMethodsRequest();
		//Adding data for the different nodes
		//Added External Reference Type
		request.getFolioIdentifierTO().getExternalReferenceTO().setReferenceName("");
		request.getFolioIdentifierTO().getExternalReferenceTO().setReferenceValue(book.getTravelPlanId());
		//Add Folio Type
		request.getFolioIdentifierTO().setFolioType("INDIVIDUAL");
		RestResponse response= Rest.folio(environment).folioService().folio().retrieveSettlementMethods().sendPutRequestWithMissingAuthToken(request);
		TestReporter.assertTrue(response.getStatusCode() == 500, "Validate status code returned ["+response.getStatusCode()+"] was [500]");
		TestReporter.assertTrue(response.getResponse().contains("Invalid ChargeGroup ExternalReference-ExternalReferenceTO[Reference Name = "), "Invalid ChargeGroup ExternalReference-ExternalReferenceTO[Reference Name = ] was received");
	}	
	@Test(groups={"api","rest", "regression", "folio", "folioService","negative", "retrieveSettlementMethods"})
	public void testretrieveSettlementMethods_Negative_NoExtRefValue()throws IOException{
		TestReporter.setDebugLevel(1);
		TestReporter.setDebugLevel(TestReporter.DEBUG);
		
		//create the json message
		RetrieveSettlementMethodsRequest request = new RetrieveSettlementMethodsRequest();
		//Adding data for the different nodes
		//Added External Reference Type
		request.getFolioIdentifierTO().getExternalReferenceTO().setReferenceName("DREAMS_TP");
		request.getFolioIdentifierTO().getExternalReferenceTO().setReferenceValue("");
		//Add Folio Type
		request.getFolioIdentifierTO().setFolioType("INDIVIDUAL");
		RestResponse response= Rest.folio(environment).folioService().folio().retrieveSettlementMethods().sendPutRequestWithMissingAuthToken(request);
		TestReporter.assertTrue(response.getStatusCode() == 500, "Validate status code returned ["+response.getStatusCode()+"] was [500]");
		TestReporter.assertTrue(response.getResponse().contains("Invalid input fields. : Invalid ChargeGroup ExternalReference-ExternalReferenceTO[Reference Name = "), "Invalid input fields. : Invalid ChargeGroup ExternalReference-ExternalReferenceTO[Reference Value = ] was received");
	}
	@Test(groups={"api","rest", "regression", "folio", "folioService","negative", "retrieveSettlementMethods"})
	public void testretrieveSettlementMethods_Negative_InvalidFolioType()throws IOException{
		TestReporter.setDebugLevel(1);
		TestReporter.setDebugLevel(TestReporter.DEBUG);
		
		//create the json message
		RetrieveSettlementMethodsRequest request = new RetrieveSettlementMethodsRequest();
		//Adding data for the different nodes
		//Added External Reference Type
		request.getFolioIdentifierTO().getExternalReferenceTO().setReferenceName("DREAMS_TP");
		request.getFolioIdentifierTO().getExternalReferenceTO().setReferenceValue(book.getTravelPlanId());
		//Add Folio Type
		request.getFolioIdentifierTO().setFolioType("IND");
		RestResponse response= Rest.folio(environment).folioService().folio().retrieveSettlementMethods().sendPutRequestWithMissingAuthToken(request);
		TestReporter.assertTrue(response.getStatusCode() == 500, "Validate status code returned ["+response.getStatusCode()+"] was [500]");
		TestReporter.assertTrue(response.getResponse().contains("FolioType from String value 'IND': value not one of declared Enum instance names"), "FolioType from String value 'IND': value not one of declared Enum instance names");
	}
	@Test(groups={"api","rest", "regression", "folio", "folioService","negative", "retrieveSettlementMethods"})
	public void testretrieveSettlementMethods_Negative_MissingFolioType()throws IOException{
		TestReporter.setDebugLevel(1);
		TestReporter.setDebugLevel(TestReporter.DEBUG);
		
		//create the json message
		RetrieveSettlementMethodsRequest request = new RetrieveSettlementMethodsRequest();
		//Adding data for the different nodes
		//Added External Reference Type
		request.getFolioIdentifierTO().getExternalReferenceTO().setReferenceName("DREAMS_TP");
		request.getFolioIdentifierTO().getExternalReferenceTO().setReferenceValue(book.getTravelPlanId());
		//Add Folio Type
		request.getFolioIdentifierTO().setFolioType("");
		RestResponse response= Rest.folio(environment).folioService().folio().retrieveSettlementMethods().sendPutRequestWithMissingAuthToken(request);
		TestReporter.assertTrue(response.getStatusCode() == 500, "Validate status code returned ["+response.getStatusCode()+"] was [500]");
		TestReporter.assertTrue(response.getResponse().contains("FolioType from String value '': value not one of declared Enum instance names"), "FolioType from String value '': value not one of declared Enum instance names");
	}
	
}
