package com.disney.composite.api.RestServices.folio.paymentv2.foliopaymentV2;

import java.io.IOException;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.restServices.Rest;
import com.disney.api.restServices.core.RestResponse;
import com.disney.api.restServices.folio.PaymentV2.folioPaymentV2.establishSettlementMethod.request.EstablishSettlementMethodRequest;
import com.disney.api.restServices.travelPlan.travelPlanService.modifyGuests.request.ModifyGuestsRequest;
import com.disney.utils.TestReporter;

@SuppressWarnings("unused")
public class TestEstablishSettlementMethod {
private String environment = "Development";
	
	/**
	 * This will always be used as is. TestNG will pass in the Environment used
	 * @param environment - Valid environments for active testing are bashful, sleepy and grumpy
	 */
	@BeforeMethod(alwaysRun = true)
	@Parameters({  "environment" })
	public void setup(@Optional String environment) {
		//this.environment = environment;
		this.environment = "Development";
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
	@Test(groups={"api","rest", "regression", "folio", "paymentV2", "establishSettlementMethod"})
	public void testestablishSettlementMethod () throws IOException{
		// set log levels for debugging
		TestReporter.setDebugLevel(1);
		TestReporter.setDebugLevel(TestReporter.DEBUG);
				
		//create new request file
		EstablishSettlementMethodRequest request = new EstablishSettlementMethodRequest();
		
		//Create request
		
		//submit request
		
	}
}
