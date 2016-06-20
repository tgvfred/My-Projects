package com.disney.composite.api.accommodationSalesServicePort;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationSalesServicePort.operations.RetrieveProductPurchaseData;
import com.disney.utils.TestReporter;

public class TestRetrieveProductPurchaseData {
	private String environment = "";
	
	@BeforeMethod(alwaysRun = true)
		@Parameters({  "environment" })
		public void setup(String environment) {
			this.environment = environment;
		
		}
				
		@Test(groups={"api", "regression", "accommodation", "accommodationSalesService", "retrieveProductPurchaseData"})
		public void testRetrieveProductPurchaseData_MainFlow(){
			RetrieveProductPurchaseData RetrieveProductPurchaseData = new RetrieveProductPurchaseData(environment, "Main");
			RetrieveProductPurchaseData.sendRequest();
			//System.out.println(RetrieveProductPurchaseData.getRequest());
			//System.out.println(RetrieveProductPurchaseData.getResponse());
			TestReporter.assertEquals(RetrieveProductPurchaseData.getResponseStatusCode(), "200", "The response code was not 200");
			TestReporter.assertNotNull(RetrieveProductPurchaseData.getproductId(), "The response contains a Product Id");
			TestReporter.assertNotNull(RetrieveProductPurchaseData.getproductTypeName(), "The response contains a Product Type Name");
		}
}
