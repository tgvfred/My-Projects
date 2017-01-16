package com.disney.composite.api.accommodationModule.soapServices.accommodationSalesServicePort;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.Book;
import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.RetrieveSummary;
import com.disney.utils.TestReporter;

public class TestRetrieveSummary {
	private String environment = "";
	Book book = null;
	@BeforeClass(alwaysRun = true)
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
		TestReporter.logAPI(!RetrieveSummary.getResponseStatusCode().equals("200"), "An error occurred retrieving the summary for the travel component grouping ["+book.getTravelComponentGroupingId()+"]", RetrieveSummary);
	}
}
