package com.disney.composite.api.accommodationSalesServicePort;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationSalesServicePort.operations.Book;
import com.disney.api.soapServices.accommodationSalesServicePort.operations.RetrieveSummary;
import com.disney.utils.TestReporter;

public class TestRetrieveSummary {
	private String environment = "";
	Book book = null;
	@BeforeTest(alwaysRun = true)
	@Parameters({"environment" })
	public void setup(String environment) {
		this.environment = environment;
		book= new Book(environment, "bookRoomOnly2Adults2ChildrenWithoutTickets" );
		book.sendRequest();
		
		System.out.println(book.getRequest());
		System.out.println(book.getResponse());
}
		
	@Test(groups={"api", "regression", "accommodation", "accommodationSalesService", "RetrieveSummary"})
	public void testRetrieveSummary_MainFlow(){
		
		RetrieveSummary RetrieveSummary = new RetrieveSummary(environment, "Retrieve Summay" );
		RetrieveSummary.setRequestTravelComponentGroupingId(book.getTravelComponentGroupingId());
		RetrieveSummary.sendRequest();
		System.out.println(RetrieveSummary.getRequest());
		System.out.println(RetrieveSummary.getResponse());
		TestReporter.assertEquals(RetrieveSummary.getResponseStatusCode(), "200", "The response code was not 200");
	}
}
