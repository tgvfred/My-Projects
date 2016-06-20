package com.disney.composite.api.accommodationSalesServicePort;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationSalesServicePort.operations.Book;
import com.disney.api.soapServices.accommodationSalesServicePort.operations.RetrieveShareChain;
import com.disney.utils.TestReporter;

public class TestRetrieveShareChain {
	private String environment = "";
    Book book = null;
    @BeforeMethod(alwaysRun = true)
	@Parameters({"environment" })
	public void setup(String environment) {
		this.environment = environment;
		book= new Book(environment, "bookRoomOnly2Adults2ChildrenWithoutTickets" );
		book.sendRequest();
		//System.out.println(book.getResponse());
	}
	
	@Test(groups={"api", "regression", "accommodation", "accommodationSalesService", "retrieveShareChain"})
	public void testRetrieveShareChain_MainFlow(){
		RetrieveShareChain RetrieveShareChain = new RetrieveShareChain(environment, "" );
		RetrieveShareChain.setTravelComponentGroupingId(book.getTravelComponentGroupingId());
		RetrieveShareChain.sendRequest();
	    //System.out.println(RetrieveShareChain.getRequest());
	    //System.out.println(RetrieveShareChain.getResponse());
		TestReporter.assertEquals(RetrieveShareChain.getResponseStatusCode(), "200", "The response code was not 200");
		TestReporter.assertNotNull(RetrieveShareChain.gettravelComponentId(), "The response contains a Travel Component Id");
		TestReporter.assertNotNull(RetrieveShareChain.gettravelPlanSegmentId(),  "The response contains a Travel Plan Segment Id");
	}
}