package com.disney.composite.api.accommodationSalesServicePort;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationSalesServicePort.operations.Book;
import com.disney.api.soapServices.accommodationSalesServicePort.operations.RetrieveforShare;
import com.disney.utils.TestReporter;

public class TestRetrieveforShare {
	private String environment = "";
	Book book = null;
	@BeforeMethod(alwaysRun = true)
	@Parameters({"environment" })
	public void setup(String environment) {
		this.environment = environment;
		book= new Book(environment, "bookRoomOnly2Adults2ChildrenWithoutTickets" );
		book.sendRequest();
		
		//System.out.println(book.getRequest());
		//System.out.println(book.getResponse());
	}
		
	@Test(groups={"api", "regression", "accommodation", "accommodationSalesService", "retrieveForShare"})
	public void testRetrieveforShare_MainFlow(){
		
		RetrieveforShare retrieveforShare = new RetrieveforShare(environment, "Main" );
		retrieveforShare.settravelComponentGroupingId(book.getTravelComponentGroupingId());
		retrieveforShare.settravelPlanSegmentId(book.getTravelPlanSegmentId());
		retrieveforShare.sendRequest();
		//System.out.println(retrieveforShare.getRequest());
		//System.out.println(retrieveforShare.getResponse());
		TestReporter.assertEquals(retrieveforShare.getResponseStatusCode(), "200", "The response code was not 200");
	}
}
