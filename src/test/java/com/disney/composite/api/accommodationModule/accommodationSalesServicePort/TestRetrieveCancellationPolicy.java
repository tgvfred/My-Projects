package com.disney.composite.api.accommodationModule.accommodationSalesServicePort;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.Book;
import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.Cancel;
import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.RetrieveCancellationPolicy;
import com.disney.utils.Randomness;
import com.disney.utils.TestReporter;

public class TestRetrieveCancellationPolicy {
	private String environment = "";
    private Book book = null;
    
    @BeforeMethod(alwaysRun = true)
	@Parameters({  "environment" })
	public void setup(String environment) {
		this.environment = environment;
		book= new Book(environment, "bookRoomOnly2Adults2ChildrenWithoutTickets" );
		book.sendRequest();
	}
	
	@AfterMethod(alwaysRun=true)
	public void teardown(){
		try{
			if(book != null){
				if(book.getTravelPlanSegmentId() != null){
					if(!book.getTravelPlanSegmentId().isEmpty()){
						Cancel cancel = new Cancel(environment, "Main");
						cancel.setCancelDate(Randomness.generateCurrentXMLDate(0));
						cancel.setTravelComponentGroupingId(book.getTravelComponentGroupingId());
						cancel.sendRequest();
					}
				}
			}
		}catch(Exception e){}
	}
	
	@Test(groups={"api", "regression", "accommodation", "accommodationSalesService", "retrieveCancellationPolicy"})
	public void testretrieveCancellationPolicy_MainFlow(){
		TestReporter.logScenario("Test Retrieve Cancellation Policy");
		RetrieveCancellationPolicy RetrieveCancellationPolicy= new RetrieveCancellationPolicy(environment, "Main" );
		RetrieveCancellationPolicy.setTravelComponentGroupingId(book.getTravelComponentGroupingId());
		RetrieveCancellationPolicy.setTravelPlanSegmentId(book.getTravelPlanSegmentId());
		RetrieveCancellationPolicy.sendRequest();
		TestReporter.logAPI(!RetrieveCancellationPolicy.getResponseStatusCode().equals("200"), "An error occurred retrieving cancellation policy", RetrieveCancellationPolicy);
	    TestReporter.log("Travel Plan ID: " + book.getTravelPlanId());
		TestReporter.assertNotNull(RetrieveCancellationPolicy.getcancelFee(), "The response contains a cancel Fee");
	}
}