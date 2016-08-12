package com.disney.composite.api.accommodationModule.accommodationSalesServicePort;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.CalculateSharedRates;
import com.disney.utils.TestReporter;

public class TestCalculateSharedRates {
	private String environment = "";
	
	@BeforeMethod(alwaysRun = true)
	@Parameters({  "environment" })
	public void setup(String environment){this.environment = environment;}
			
	@Test(groups={"api", "regression", "accommodation", "accommodationSalesService", "calculateSharedRates"})
	public void testCalculateSharedRates_MainFlow(){
		TestReporter.logScenario("Test Calculate Shared Rates");
		CalculateSharedRates calculateSharedRates = new CalculateSharedRates(environment, "Main");		
		calculateSharedRates.sendRequest();
		TestReporter.logAPI(!calculateSharedRates.getResponseStatusCode().equals("200"), "An error occurred calculating shared rates", calculateSharedRates);
		TestReporter.assertNotNull(calculateSharedRates.getpackageCode(), "The response contains a PackageCode");
		TestReporter.assertNotNull(calculateSharedRates.getguaranteeStatus(), "The response contains a Guarantee Status");
	}
}
