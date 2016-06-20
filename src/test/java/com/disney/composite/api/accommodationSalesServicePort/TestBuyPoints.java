package com.disney.composite.api.accommodationSalesServicePort;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationSalesServicePort.operations.BuyPoints;
import com.disney.utils.TestReporter;

public class TestBuyPoints {
private String environment = "";
	
	@BeforeTest(alwaysRun = true)
	@Parameters({  "environment" })
	public void setup(String environment) {
		this.environment = environment;
	
	}
		
	@Test(groups={"api", "regression", "accommodation", "accommodationSalesService", "BuyPoints"})
	public void testBuyPoints_MainFlow(){
		
		BuyPoints BuyPoints = new BuyPoints(environment, "Main" );
		BuyPoints.sendRequest();
		System.out.println(BuyPoints.getRequest());
		System.out.println(BuyPoints.getResponse());
		TestReporter.assertEquals(BuyPoints.getResponseStatusCode(), "200", "The response code was not 200");
		TestReporter.assertNotNull(BuyPoints.getTravelPlanID(), "The response contains a Travel Plan ID");
		TestReporter.assertNotNull(BuyPoints.gettravelComponentId(), "The response contains a Travel Component Id Value");
	}
}
