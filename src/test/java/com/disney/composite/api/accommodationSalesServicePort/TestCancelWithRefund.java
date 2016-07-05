package com.disney.composite.api.accommodationSalesServicePort;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationSalesServicePort.operations.Book;
import com.disney.api.soapServices.accommodationSalesServicePort.operations.Cancel;
import com.disney.api.soapServices.accommodationSalesServicePort.operations.CancelWithRefund;
import com.disney.test.utils.Randomness;
import com.disney.utils.TestReporter;

public class TestCancelWithRefund {
	private String environment = "";
	private Book book = null;
	
	@BeforeMethod(alwaysRun = true)
	@Parameters({"environment" })
	public void setup(String environment) {
		this.environment = environment;
		book= new Book(environment, "bookWithoutTickets" );
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
		
	@Test(groups={"api", "regression", "accommodation", "accommodationSalesService", "CancelWithRefund"})
	public void testCancelWithRefund_MainFlow(){
		TestReporter.logScenario("Test Cancel with Refund");
	    TestReporter.log("Travel Plan ID: " + book.getTravelPlanId());
		CancelWithRefund CancelWithRefund = new CancelWithRefund(environment, "Main" );
		CancelWithRefund.setTravelComponentGroupingId(book.getTravelComponentGroupingId());
		CancelWithRefund.sendRequest();
		TestReporter.logAPI(!CancelWithRefund.getResponseStatusCode().equals("200"), "An error occurred cancelling with refund", CancelWithRefund);
		TestReporter.assertNotNull(CancelWithRefund.getTravelPlanSegmentId(), "The response contains a Travel Plan Segment ID");
		TestReporter.assertNotNull(CancelWithRefund.getTravelPlanId(), "The response contains a Travel Plan ID");
	
	}
}
