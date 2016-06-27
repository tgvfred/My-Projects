package com.disney.composite.api.accommodationSalesServicePort;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationSalesServicePort.operations.Book;
import com.disney.api.soapServices.accommodationSalesServicePort.operations.CalculateUnsharedRates;
import com.disney.utils.TestReporter;

public class TestCalculateUnsharedRates {
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
	
	@Test(groups={"api", "regression", "accommodation", "accommodationSalesService", "calculateUnsharedRates"})
	public void testCalculateUnsharedRates_MainFlow(){
		CalculateUnsharedRates CalculateUnsharedRates = new CalculateUnsharedRates(environment, "Main" );
		CalculateUnsharedRates.sendRequest();
	    //System.out.println(CalculateUnsharedRates.getRequest());
	    //System.out.println(CalculateUnsharedRates.getResponse());
		TestReporter.assertEquals(CalculateUnsharedRates.getResponseStatusCode(), "200", "The response code was not 200");
		TestReporter.assertNotNull(CalculateUnsharedRates.getinventoryStatus(), "The response contains a Inventory Status");
		TestReporter.assertNotNull(CalculateUnsharedRates.getfreezeId(),  "The response contains a Freeze Id");
}
}