package com.disney.composite.api.accommodationSalesServicePort;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationSalesServicePort.operations.Book;
import com.disney.api.soapServices.accommodationSalesServicePort.operations.RetrievePackageAndRackRates;
import com.disney.utils.TestReporter;

public class TestRetrievePackageAndRackRates {
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
		
	@Test(groups={"api", "regression", "accommodation", "accommodationSalesService", "retrievePackageAndRackRates"})
	public void testRetrievePackageAndRackRates_MainFlow(){
	    RetrievePackageAndRackRates RetrievePackageAndRackRates= new RetrievePackageAndRackRates(environment, "Main" );
	    RetrievePackageAndRackRates.setTravelPlanSegementId(book.getTravelPlanSegmentId());
	    RetrievePackageAndRackRates.setaccomComponentId(book.getTravelComponentId());
	    RetrievePackageAndRackRates.sendRequest();
	    //System.out.println(RetrievePackageAndRackRates.getRequest());
	    //System.out.println(RetrievePackageAndRackRates.getResponse());
		TestReporter.assertEquals(RetrievePackageAndRackRates.getResponseStatusCode(), "200", "The response code was not 200");
		TestReporter.assertNotNull(RetrievePackageAndRackRates.getdate(),  "The response contains a date");
		TestReporter.assertNotNull(RetrievePackageAndRackRates.getpackagePrice(),  "The response contains a package Price");
}
}