package com.disney.composite.api.accommodationSalesServicePort;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationSalesServicePort.operations.Book;
import com.disney.api.soapServices.accommodationSalesServicePort.operations.RetrieveCancellationPolicy;
import com.disney.utils.TestReporter;

public class TestRetrieveCancellationPolicy {
	private String environment = "";
    Book book = null;
    @BeforeMethod(alwaysRun = true)
	@Parameters({  "environment" })
	public void setup(String environment) {
		this.environment = environment;
		book= new Book(environment, "bookRoomOnly2Adults2ChildrenWithoutTickets" );
		book.sendRequest();
	    //System.out.println(book.getResponse());
	}
	
	@Test(groups={"api", "regression", "accommodation", "accommodationSalesService", "retrieveCancellationPolicy"})
	public void testretrieveCancellationPolicy_MainFlow(){
		RetrieveCancellationPolicy RetrieveCancellationPolicy= new RetrieveCancellationPolicy(environment, "Main" );
		RetrieveCancellationPolicy.setTravelComponentGroupingId(book.getTravelComponentGroupingId());
		RetrieveCancellationPolicy.setTravelPlanSegmentId(book.getTravelPlanSegmentId());
		RetrieveCancellationPolicy.sendRequest();
	    //System.out.println(RetrieveCancellationPolicy.getRequest());
	    //System.out.println(RetrieveCancellationPolicy.getResponse());
		TestReporter.assertEquals(RetrieveCancellationPolicy.getResponseStatusCode(), "200", "The response code was not 200");
		TestReporter.assertNotNull(RetrieveCancellationPolicy.getcancelFee(), "The response contains a cancel Fee");
	}
}