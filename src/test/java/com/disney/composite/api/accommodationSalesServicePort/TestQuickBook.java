package com.disney.composite.api.accommodationSalesServicePort;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationSalesServicePort.operations.Quickbook;
//import com.disney.api.soapServices.accommodationSalesServicePort.operations.RetrieveComments;
import com.disney.utils.TestReporter;

public class TestQuickBook {
	private String environment = "";
	
	@BeforeMethod(alwaysRun = true)
	@Parameters({  "environment" })
	public void setup(String environment) {
		this.environment = environment;
	
	}
		
	@Test(groups={"api", "regression", "accommodation", "accommodationSalesService", "quickbook"})
	public void testQuickbook_Main(){
		Quickbook quickbook = new Quickbook(environment, "Main" );
		quickbook.sendRequest();
		TestReporter.assertEquals(quickbook.getResponseStatusCode(), "200", "The response code was not 200");
		TestReporter.assertNotNull(quickbook.getTravelPlanId(), "The response contains a Travel Plan ID");
	}
	
	@Test(groups={"api", "regression", "accommodation", "accommodationSalesService", "quickbook"})
	public void testQuickbook_UIBooking(){
		Quickbook quickbook = new Quickbook(environment, "UI Booking" );
		quickbook.sendRequest();
		TestReporter.assertEquals(quickbook.getResponseStatusCode(), "200", "The response code was not 200");
		TestReporter.assertNotNull(quickbook.getTravelPlanId(), "The response contains a Travel Plan ID");
	}
	
	@Test(groups={"api", "regression", "accommodation", "accommodationSalesService", "quickbook"})
	public void testQuickbook_2Adults1Child(){
		Quickbook quickbook = new Quickbook(environment, "2Adults1Child" );
		quickbook.sendRequest();
		TestReporter.assertEquals(quickbook.getResponseStatusCode(), "200", "The response code was not 200");
		TestReporter.assertNotNull(quickbook.getTravelPlanId(), "The response contains a Travel Plan ID");
	}
	
	@Test(groups={"api", "regression", "accommodation", "accommodationSalesService", "quickbook"})
	public void testQuickbook_BookingWithAddress(){
		Quickbook quickbook = new Quickbook(environment, "Booking with Address" );
		quickbook.sendRequest();
		TestReporter.assertEquals(quickbook.getResponseStatusCode(), "200", "The response code was not 200");
		TestReporter.assertNotNull(quickbook.getTravelPlanId(), "The response contains a Travel Plan ID");
	}
	
}
