package com.disney.composite.api.accommodationSalesServicePort;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationSalesServicePort.operations.GetOptions;
import com.disney.utils.TestReporter;

public class TestGetOptions {
private String environment = "";
	
@BeforeMethod(alwaysRun = true)
	@Parameters({  "environment" })
	public void setup(String environment) {
		this.environment = environment;
	
	}
		
	@Test(groups={"api", "regression", "accommodation", "accommodationSalesService", "getOptions"})
	public void testGetOptions_MainFlow(){
		
		GetOptions GetOptions = new GetOptions(environment, "Main" );
		GetOptions.sendRequest();
		//System.out.println(GetOptions.getRequest());
		//System.out.println(GetOptions.getResponse());
		TestReporter.assertEquals(GetOptions.getResponseStatusCode(), "200", "The response code was not 200");
		TestReporter.assertNotNull(GetOptions.getoptionKey(), "The response contains a option Key");
		TestReporter.assertNotNull(GetOptions.getoptionValue(), "The response contains a option Value");
	}
}
