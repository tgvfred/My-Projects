package com.disney.composite.api.accommodationSalesServicePort;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationSalesServicePort.operations.GetOptionsByFilter;
import com.disney.utils.TestReporter;

public class TestGetOptionsFilter {
private String environment = "";
	
@BeforeMethod(alwaysRun = true)
	@Parameters({  "environment" })
	public void setup(String environment) {
		this.environment = environment;
	
	}
		
	@Test(groups={"api", "regression", "accommodation", "accommodationSalesService", "GetOptionsByFilter"})
	public void testGetOptionsByFilterByFilter_Role(){
		
		GetOptionsByFilter GetOptionsByFilter = new GetOptionsByFilter(environment, "getOptionsByFilter_Role" );
		GetOptionsByFilter.sendRequest();
		//System.out.println(GetOptionsByFilter.getRequest());
		//System.out.println(GetOptionsByFilter.getResponse());
		TestReporter.assertEquals(GetOptionsByFilter.getResponseStatusCode(), "200", "The response code was not 200");
		TestReporter.assertNotNull(GetOptionsByFilter.getoptionKey(), "The response contains a option Key");
		TestReporter.assertNotNull(GetOptionsByFilter.getoptionValue(), "The response contains a option Value");
	}
	
	@Test(groups={"api", "regression", "accommodation", "accommodationSalesService", "GetOptionsByFilter"})
	public void testGetOptionsByFilterByFilter_LANGUAGE(){
		
		GetOptionsByFilter GetOptionsByFilter = new GetOptionsByFilter(environment, "getOptionsByFilter_LANGUAGE" );
		GetOptionsByFilter.sendRequest();
		//System.out.println(GetOptionsByFilter.getRequest());
		//System.out.println(GetOptionsByFilter.getResponse());
		TestReporter.assertEquals(GetOptionsByFilter.getResponseStatusCode(), "200", "The response code was not 200");
		TestReporter.assertNotNull(GetOptionsByFilter.getoptionKey(), "The response contains a option Key");
		TestReporter.assertNotNull(GetOptionsByFilter.getoptionValue(), "The response contains a option Value");
	}
}
