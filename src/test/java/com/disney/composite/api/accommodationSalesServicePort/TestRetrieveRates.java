package com.disney.composite.api.accommodationSalesServicePort;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationSalesServicePort.operations.Book;
import com.disney.api.soapServices.accommodationSalesServicePort.operations.RetrieveRates;
import com.disney.utils.TestReporter;

public class TestRetrieveRates {
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
		
	@Test(groups={"api", "regression", "accommodation", "accommodationSalesService", "retrieveRates"})
	public void testRetrieveRates_MainFlow(){
		RetrieveRates RetrieveRates = new RetrieveRates(environment, "retrieveRates" );
		RetrieveRates.setTravelComponentGroupingId(book.getTravelComponentGroupingId());
		RetrieveRates.sendRequest();
		//System.out.println(RetrieveRates.getRequest());
		//System.out.println(RetrieveRates.getResponse());
		TestReporter.assertEquals(RetrieveRates.getResponseStatusCode(), "200", "The response code was not 200");
		TestReporter.assertNotNull(RetrieveRates.getroomTypeCode(), "The response contains a Room Type Code");
		TestReporter.assertNotNull(RetrieveRates.getadditionalChargeOverridden(), "The response contains a Additional Charge Overridden");
	}
}