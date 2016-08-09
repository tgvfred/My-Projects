package com.disney.composite.api.accommodationModule.accommodationSalesServicePort;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.Cancel;
import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.Quickbook;
import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.Retrieve;
import com.disney.test.utils.Randomness;
//import com.disney.api.soapServices.accommodationSalesServicePort.operations.RetrieveComments;
import com.disney.utils.TestReporter;

public class TestQuickBook {
	private String environment = "";
	private ThreadLocal<Quickbook> quickbook = new ThreadLocal<Quickbook>();
	
	@BeforeMethod(alwaysRun = true)
	@Parameters({  "environment" })
	public void setup(String environment) {
		TestReporter.setDebugLevel(Integer.parseInt(System.getenv("debugLevel")));
		this.environment = environment;}
	
	@AfterMethod(alwaysRun=true)
	public void teardown(){
		try{
			if(quickbook != null){
				if(quickbook.get().getTravelPlanId() != null){
					if(!quickbook.get().getTravelPlanId().isEmpty()){
						Retrieve retrieve = new Retrieve(environment, "ByTP_ID");
						retrieve.setTravelPlanId(quickbook.get().getTravelPlanId());
						retrieve.sendRequest();
						
						Cancel cancel = new Cancel(environment, "Main");
						cancel.setCancelDate(Randomness.generateCurrentXMLDate(0));
						cancel.setTravelComponentGroupingId(retrieve.getTravelComponentGroupingId());
						cancel.sendRequest();
					}
				}
			}
		}catch(Exception e){}
	}
	
	@Test(groups={"api", "regression", "accommodation", "accommodationSalesService", "quickbook"})
	public void testQuickbook_UIBooking(){
		TestReporter.logScenario("Test Quickbook Main UI Booking Scenario");
		quickbook.set(new Quickbook(environment, "UI Booking" ));
		quickbook.get().sendRequest();
		TestReporter.logAPI(!quickbook.get().getResponseStatusCode().equals("200"), "An error occurred booking a quickbook for the UI scenario", quickbook.get());
	    TestReporter.log("Travel Plan ID: " + quickbook.get().getTravelPlanId());
		TestReporter.assertNotNull(quickbook.get().getTravelPlanId(), "The response contains a Travel Plan ID");
	}
	
	@Test(groups={"api", "regression", "accommodation", "accommodationSalesService", "quickbook"})
	public void testQuickbook_2Adults1Child(){
		TestReporter.logScenario("Test Quickbook For 2 Adults And 1 Child");
		quickbook.set(new Quickbook(environment, "2Adults1Child" ));
		quickbook.get().sendRequest();
		TestReporter.logAPI(!quickbook.get().getResponseStatusCode().equals("200"), "An error occurred booking a quickbook for 2 adults and 1 child", quickbook.get());
	    TestReporter.log("Travel Plan ID: " + quickbook.get().getTravelPlanId());
		TestReporter.assertNotNull(quickbook.get().getTravelPlanId(), "The response contains a Travel Plan ID");
	}
	
	@Test(groups={"api", "regression", "accommodation", "accommodationSalesService", "quickbook"})
	public void testQuickbook_BookingWithAddress(){
		TestReporter.logScenario("Test Quickbook With Address");
		quickbook.set(new Quickbook(environment, "Booking with Address" ));
		quickbook.get().sendRequest();
		TestReporter.logAPI(!quickbook.get().getResponseStatusCode().equals("200"), "An error occurred booking a quickbook with address", quickbook.get());
	    TestReporter.log("Travel Plan ID: " + quickbook.get().getTravelPlanId());
		TestReporter.assertNotNull(quickbook.get().getTravelPlanId(), "The response contains a Travel Plan ID");
	}	
}