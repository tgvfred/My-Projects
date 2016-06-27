package com.disney.composite.api.accommodationSalesServicePort;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationSalesServicePort.operations.CalculateSharedRates;
import com.disney.utils.TestReporter;

public class TestCalculateSharedRates {
private String environment = "";
	
@BeforeMethod(alwaysRun = true)
	@Parameters({  "environment" })
	public void setup(String environment) {
		this.environment = environment;
	
	}
			
	@Test(groups={"api", "regression", "accommodation", "accommodationSalesService", "calculateSharedRates"})
	public void testCalculateSharedRates_MainFlow(){
		
		CalculateSharedRates calculateSharedRates = new CalculateSharedRates(environment, "Main");
		
		calculateSharedRates.sendRequest();
		//System.out.println(calculateSharedRates.getRequest());
		//System.out.println(calculateSharedRates.getResponse());
		TestReporter.assertEquals(calculateSharedRates.getResponseStatusCode(), "200", "The response code was not 200");
		TestReporter.assertNotNull(calculateSharedRates.getpackageCode(), "The response contains a PackageCode");
		TestReporter.assertNotNull(calculateSharedRates.getguaranteeStatus(), "The response contains a Guarantee Status");
	}
}
