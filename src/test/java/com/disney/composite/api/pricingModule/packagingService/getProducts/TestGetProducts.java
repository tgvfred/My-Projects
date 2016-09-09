package com.disney.composite.api.pricingModule.packagingService.getProducts;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.BaseTest;
import com.disney.api.soapServices.pricingModule.packagingService.getProducts.GetProducts;
import com.disney.utils.TestReporter;

public class TestGetProducts extends BaseTest{
	
	@Test(groups={"api", "presmoke", "regression", "pricing", "packagingService", "getProducts"})
	public void Presmoke_testGetOptions_MainFlow(){
		TestReporter.logScenario("Test Get Products");
		GetProducts getProducts= new GetProducts("Virtual", "Main" );
		getProducts.sendRequest();
		TestReporter.logAPI(!getProducts.getResponseStatusCode().equals("200"), "An error occurred getting options", getProducts);
		TestReporter.assertNotNull(getProducts.getProductDescription(), "The response contains a Product description");
	}
}
