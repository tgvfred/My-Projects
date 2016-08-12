package com.disney.composite.api.accommodationModule.accommodationSalesServicePort;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.Book;
import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.RetrieveCancellationFee;
import com.disney.utils.TestReporter;

public class TestRetrieveCancellationFee {
	private String environment = "";
    private Book book = null;
    
    @BeforeMethod(alwaysRun = true)
	@Parameters({  "environment" })
	public void setup(String environment) {
		this.environment = environment;
		book= new Book(environment, "bookRoomOnly2Adults2ChildrenWithoutTickets" );
		book.setResortCode("1E"); //Comment out if your want to use the default resort
		book.setRoomTypeCode("EK"); //Comment out if your want to use the default room type
		book.sendRequest();
	}
		
	@Test(groups={"api", "regression", "accommodation", "accommodationSalesService", "RetrieveCancellationFee", "debug"})
	public void testRetrieveCancellationFee_MainFlow(){
		TestReporter.logScenario("Test Retrieve Cancellation Fee");
		RetrieveCancellationFee RetrieveCancellationFee = new RetrieveCancellationFee(environment, "Main" );
		RetrieveCancellationFee.setTravelPlanSegmentID(book.getTravelPlanSegmentId());
		RetrieveCancellationFee.sendRequest();
		TestReporter.logAPI(!RetrieveCancellationFee.getResponseStatusCode().equals("200"), "An error occurred retrieving cancellation fee", RetrieveCancellationFee);
	    TestReporter.log("Travel Plan ID: " + book.getTravelPlanId());
		TestReporter.assertNotNull(RetrieveCancellationFee.getRevenueID(), "The response contains a Revenue ID");
		TestReporter.assertNotNull(RetrieveCancellationFee.getName(), "The response contains Name");
	}
}