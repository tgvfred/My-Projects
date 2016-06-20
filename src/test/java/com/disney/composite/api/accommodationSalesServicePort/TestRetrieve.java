package com.disney.composite.api.accommodationSalesServicePort;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationSalesServicePort.operations.Quickbook;
import com.disney.api.soapServices.accommodationSalesServicePort.operations.Retrieve;
import com.disney.utils.TestReporter;

public class TestRetrieve {
	private String environment = "";
	Quickbook quickbook = null;
	@BeforeMethod(alwaysRun = true)
	@Parameters({  "environment" })
	public void setup(String environment) {
		this.environment = environment;
		quickbook= new Quickbook(environment, "Main" );
		quickbook.sendRequest();
		
	}
		
	@Test(groups={"api", "regression", "accommodation", "accommodationSalesService", "retrieve"})
	public void testRetrieve_MainFlow(){
		Retrieve retrieve = new Retrieve(environment, "Main" );
		retrieve.setTravelPlanId(quickbook.getTravelPlanId());
		retrieve.sendRequest();
		TestReporter.assertEquals(retrieve.getResponseStatusCode(), "200", "The response code was not 200");
		TestReporter.assertNotNull(retrieve.getTravelPlanSegmentId(), "The response contains a Travel Plan Segment ID");
	}
}
