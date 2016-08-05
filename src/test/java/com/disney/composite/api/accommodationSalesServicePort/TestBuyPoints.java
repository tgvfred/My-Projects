package com.disney.composite.api.accommodationSalesServicePort;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationSalesServicePort.operations.BuyPoints;
import com.disney.api.soapServices.accommodationSalesServicePort.operations.Cancel;
import com.disney.test.utils.Randomness;
import com.disney.utils.TestReporter;

public class TestBuyPoints {
	private String environment = "";
	private BuyPoints BuyPoints;
	
	@BeforeClass(alwaysRun = true)
	@Parameters({  "environment" })
	public void setup(String environment) {this.environment = environment;}
	
	@AfterMethod(alwaysRun=true)
	public void teardown(){
		try{
			if(BuyPoints != null){
				if(BuyPoints.getTravelPlanId() != null){
					if(!BuyPoints.getTravelPlanId().isEmpty()){
						Cancel cancel = new Cancel(environment, "Main");
						cancel.setCancelDate(Randomness.generateCurrentXMLDate(0));
						cancel.setTravelComponentGroupingId(BuyPoints.getTravelComponentGroupingId());
						cancel.sendRequest();
					}
				}
			}
		}catch(Exception e){}
	}
	
	@Test(groups={"api", "regression", "accommodation", "accommodationSalesService", "BuyPoints"})
	public void testBuyPoints_MainFlow(){
		TestReporter.logScenario("Test Buy Points");
		BuyPoints = new BuyPoints(environment, "Main" );
		BuyPoints.sendRequest();
		TestReporter.logAPI(!BuyPoints.getResponseStatusCode().equals("200"), "An error occurred buying points", BuyPoints);
	    TestReporter.log("Travel Plan ID: " + BuyPoints.getTravelPlanId());
		TestReporter.assertNotNull(BuyPoints.getTravelPlanID(), "The response contains a Travel Plan ID");
		TestReporter.assertNotNull(BuyPoints.gettravelComponentId(), "The response contains a Travel Component Id Value");
	}
}