package com.disney.composite.api.accommodationSalesServicePort;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationSalesServicePort.operations.GetOptionDetail;
import com.disney.utils.TestReporter;

public class TestGetOptionDetail {
private String environment = "";
	
@BeforeMethod(alwaysRun = true)
	@Parameters({  "environment" })
	public void setup(String environment) {
		this.environment = environment;
	
	}
		
	@Test(groups={"api", "regression", "accommodation", "accommodationSalesService", "GetOptionDetail"})
	public void testGetOptionDetail_MainFlow(){
		
		GetOptionDetail GetOptionDetail = new GetOptionDetail(environment, "Main" );
		GetOptionDetail.sendRequest();
		//System.out.println(GetOptionDetail.getRequest());
		//System.out.println(GetOptionDetail.getResponse());
		TestReporter.assertEquals(GetOptionDetail.getResponseStatusCode(), "200", "The response code was not 200");
	}
}
