package com.disney.composite.api.accommodationModule.soapServices.accommodationSalesServicePort;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.Book;
import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.RetrievePostedCancellationFee;
import com.disney.utils.TestReporter;

public class TestRetrievePostedCancellationFee {
	private String environment = "";
	private Book book = null;
	
	@BeforeClass(alwaysRun = true)
	@Parameters({"environment" })
	public void setup(String environment) {
		this.environment = environment;
		book= new Book(environment, "bookRoomOnly2Adults2ChildrenWithoutTickets" );
		book.sendRequest();
	}
		
	@Test(groups={"api", "regression", "accommodation", "accommodationSalesService", "RetrievePostedCancellationFee"})
	public void testRetrievePostedCancellationFee_MainFlow(){
		TestReporter.logScenario("Test Retrieve Posted Cancellation Fee");
		RetrievePostedCancellationFee RetrievePostedCancellationFee = new RetrievePostedCancellationFee(environment, "Main" );
		RetrievePostedCancellationFee.setid(book.getTravelPlanSegmentId());
		RetrievePostedCancellationFee.sendRequest();
		TestReporter.logAPI(!RetrievePostedCancellationFee.getResponseStatusCode().equals("200"), "An error occurred retrieving posted cancellation fees", RetrievePostedCancellationFee);
	    TestReporter.log("Travel Plan ID: " + book.getTravelPlanId());
		TestReporter.assertNotNull(RetrievePostedCancellationFee.getWaived(),  "The response contains a Waived");
		TestReporter.assertNotNull(RetrievePostedCancellationFee.getOverridden(),  "The response contains a Overridden");	
	}	
}