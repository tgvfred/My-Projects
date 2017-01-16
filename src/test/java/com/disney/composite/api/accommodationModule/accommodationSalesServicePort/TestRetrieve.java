package com.disney.composite.api.accommodationModule.accommodationSalesServicePort;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.Cancel;
import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.Quickbook;
import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.Retrieve;
import com.disney.utils.Randomness;
import com.disney.utils.TestReporter;

public class TestRetrieve {
	private String environment = "";
	private Quickbook quickbook = null;
	private Retrieve retrieve;
	
	@BeforeMethod(alwaysRun = true)
	@Parameters({  "environment" })
	public void setup(String environment) {
		this.environment = environment;
		quickbook= new Quickbook(environment, "UI Booking" );
		quickbook.sendRequest();	
	}
	
	@AfterMethod(alwaysRun=true)
	public void teardown(){
		try{
			if(retrieve != null){
				if(retrieve.getTravelPlanSegmentId() != null){
					if(!retrieve.getTravelPlanSegmentId().isEmpty()){
						Cancel cancel = new Cancel(environment, "Main");
						cancel.setCancelDate(Randomness.generateCurrentXMLDate(0));
						cancel.setTravelComponentGroupingId(retrieve.getTravelComponentGroupingId());
						cancel.sendRequest();
					}
				}
			}
		}catch(Exception e){}
	}
		
	@Test(groups={"api", "regression", "accommodation", "accommodationSalesService", "retrieve"})
	public void testRetrieve_MainFlow(){
		TestReporter.logScenario("Test Retrieve");
		retrieve = new Retrieve(environment, "Main" );
		retrieve.setTravelPlanId(quickbook.getTravelPlanId());
		retrieve.sendRequest();
		TestReporter.logAPI(!retrieve.getResponseStatusCode().equals("200"), "An error occurred retrieving the reservation", retrieve);
	    TestReporter.log("Travel Plan ID: " + quickbook.getTravelPlanId());
		TestReporter.assertNotNull(retrieve.getTravelPlanSegmentId(), "The response contains a Travel Plan Segment ID");
	}
}
