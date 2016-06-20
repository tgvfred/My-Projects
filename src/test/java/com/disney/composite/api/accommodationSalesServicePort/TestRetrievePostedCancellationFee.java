package com.disney.composite.api.accommodationSalesServicePort;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import com.disney.api.soapServices.accommodationSalesServicePort.operations.Book;
import com.disney.api.soapServices.accommodationSalesServicePort.operations.RetrievePostedCancellationFee;
import com.disney.utils.TestReporter;

public class TestRetrievePostedCancellationFee {
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
		
	@Test(groups={"api", "regression", "accommodation", "accommodationSalesService", "RetrievePostedCancellationFee"})
	public void testRetrievePostedCancellationFee_MainFlow(){
		
		RetrievePostedCancellationFee RetrievePostedCancellationFee = new RetrievePostedCancellationFee(environment, "Main" );
		RetrievePostedCancellationFee.setid(book.getTravelPlanSegmentId());
		RetrievePostedCancellationFee.sendRequest();
		System.out.println(RetrievePostedCancellationFee.getRequest());
		System.out.println(RetrievePostedCancellationFee.getResponse());
		TestReporter.assertEquals(RetrievePostedCancellationFee.getResponseStatusCode(), "200", "The response code was not 200");
		TestReporter.assertNotNull(RetrievePostedCancellationFee.getWaived(),  "The response contains a Waived");
		TestReporter.assertNotNull(RetrievePostedCancellationFee.getOverridden(),  "The response contains a Overridden");
	
	
	}
	
	
	
}
