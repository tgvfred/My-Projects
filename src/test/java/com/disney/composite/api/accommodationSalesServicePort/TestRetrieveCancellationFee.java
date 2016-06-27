package com.disney.composite.api.accommodationSalesServicePort;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationSalesServicePort.operations.Book;
import com.disney.api.soapServices.accommodationSalesServicePort.operations.RetrieveCancellationFee;
import com.disney.utils.TestReporter;

public class TestRetrieveCancellationFee {
	private String environment = "";
    Book book = null;
    @BeforeMethod(alwaysRun = true)
	@Parameters({  "environment" })
	public void setup(String environment) {
		this.environment = environment;
		book= new Book(environment, "bookRoomOnly2Adults2ChildrenWithoutTickets" );
		book.setResortCode("1E"); //Comment out if your want to use the default resort
		book.setRoomTypeCode("EK"); //Comment out if your want to use the default room type
		book.sendRequest();
		System.out.println(book.getRequest());
		System.out.println(book.getResponse());
	}
		
	@Test(groups={"api", "regression", "accommodation", "accommodationSalesService", "RetrieveCancellationFee", "debug"})
	public void testRetrieveCancellationFee_MainFlow(){
		RetrieveCancellationFee RetrieveCancellationFee = new RetrieveCancellationFee(environment, "Main" );
		RetrieveCancellationFee.setTravelPlanSegmentID(book.getTravelPlanSegmentId());
		RetrieveCancellationFee.sendRequest();
		System.out.println(RetrieveCancellationFee.getRequest());
		System.out.println(RetrieveCancellationFee.getResponse());
		TestReporter.assertEquals(RetrieveCancellationFee.getResponseStatusCode(), "200", "The response code was not 200");
		TestReporter.assertNotNull(RetrieveCancellationFee.getRevenueID(), "The response contains a Revenue ID");
		TestReporter.assertNotNull(RetrieveCancellationFee.getName(), "The response contains Name");
	}
}