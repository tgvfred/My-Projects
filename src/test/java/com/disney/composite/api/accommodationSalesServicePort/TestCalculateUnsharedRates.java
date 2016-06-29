package com.disney.composite.api.accommodationSalesServicePort;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationSalesServicePort.operations.Book;
import com.disney.api.soapServices.accommodationSalesServicePort.operations.CalculateUnsharedRates;
import com.disney.utils.TestReporter;

public class TestCalculateUnsharedRates {
	private String environment = "";
    private Book book = null;
    
    @BeforeMethod(alwaysRun = true)
	@Parameters({"environment" })
	public void setup(String environment) {
		this.environment = environment;
		book= new Book(environment, "bookRoomOnly2Adults2ChildrenWithoutTickets" );
		book.sendRequest();
	}
	
	@Test(groups={"api", "regression", "accommodation", "accommodationSalesService", "calculateUnsharedRates"})
	public void testCalculateUnsharedRates_MainFlow(){
		TestReporter.logScenario("Test Calculate Unshared Rates");
		CalculateUnsharedRates CalculateUnsharedRates = new CalculateUnsharedRates(environment, "Main" );
		CalculateUnsharedRates.sendRequest();
		TestReporter.logAPI(!CalculateUnsharedRates.getResponseStatusCode().equals("200"), "An error occurred calculating unshared rates.", CalculateUnsharedRates);
		TestReporter.assertNotNull(CalculateUnsharedRates.getinventoryStatus(), "The response contains a Inventory Status");
		TestReporter.assertNotNull(CalculateUnsharedRates.getfreezeId(),  "The response contains a Freeze Id");
}
}