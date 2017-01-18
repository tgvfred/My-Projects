package com.disney.composite.api.accommodationModule.soapServices.accommodationSalesServicePort;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.Book;
import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.Share;
import com.disney.utils.TestReporter;

public class TestShare {
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
		
	@Test(groups={"api", "regression", "accommodation", "accommodationSalesService", "share", "debug"})
	public void testShare_MainFlow(){
		Share Share = new Share(environment, "Main" );
		Share.setTravelComponentGroupingId(book.getTravelComponentGroupingId());
		Share.sendRequest();
		//System.out.println(Share.getRequest());
		//System.out.println(Share.getResponse());
		TestReporter.assertEquals(Share.getResponseStatusCode(), "200", "The response code was not 200");
		TestReporter.assertNotNull(Share.getTravelComponentGroupingId(),  "The response contains a Travel Component Grouping Id");
		TestReporter.assertNotNull(Share.getTravelComponentId(),  "The response contains a Travel Component Id");
	}
}