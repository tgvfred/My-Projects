package com.disney.composite.api.accommodationSalesServicePort;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationSalesServicePort.operations.Book;
import com.disney.api.soapServices.accommodationSalesServicePort.operations.CancelWithRefund;
import com.disney.utils.TestReporter;

public class TestCancelWithRefund {
	private String environment = "";
	Book book = null;
	@BeforeMethod(alwaysRun = true)
	@Parameters({"environment" })
	public void setup(String environment) {
		this.environment = environment;
		book= new Book(environment, "bookWithoutTickets" );
		book.sendRequest();
		
		//System.out.println(book.getRequest());
		//System.out.println(book.getResponse());
	}
		
	@Test(groups={"api", "regression", "accommodation", "accommodationSalesService", "CancelWithRefund"})
	public void testCancelWithRefund_MainFlow(){
		
		CancelWithRefund CancelWithRefund = new CancelWithRefund(environment, "Main" );
		CancelWithRefund.setTravelComponentGroupingId(book.getTravelComponentGroupingId());
		CancelWithRefund.sendRequest();
		//System.out.println(CancelWithRefund.getRequest());
		//System.out.println(CancelWithRefund.getResponse());
		TestReporter.assertEquals(CancelWithRefund.getResponseStatusCode(), "200", "The response code was not 200");
		TestReporter.assertNotNull(CancelWithRefund.getTravelPlanSegmentId(), "The response contains a Travel Plan Segment ID");
		TestReporter.assertNotNull(CancelWithRefund.getTravelPlanId(), "The response contains a Travel Plan ID");
	
	}
}
