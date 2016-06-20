
package com.disney.composite.api.accommodationSalesServicePort;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationSalesServicePort.operations.BookWithPrice;
import com.disney.utils.TestReporter;

public class TestBookWithPrice {
private String environment = "";
@BeforeMethod(alwaysRun = true)
	@Parameters({  "environment" })
	public void setup(String environment) {
		this.environment = environment;
	
	}
		
	@Test(groups={"api", "regression", "accommodation", "accommodationSalesService", "BookWithPrice"})
	public void testBookWithPrice_MainFlow(){
		BookWithPrice BookWithPrice = new BookWithPrice(environment, "Main" );
		BookWithPrice.sendRequest();
		//System.out.println(BookWithPrice.getRequest());
		//System.out.println(BookWithPrice.getResponse());
		TestReporter.assertEquals(BookWithPrice.getResponseStatusCode(), "200", "The response code was not 200");
		TestReporter.assertNotNull(BookWithPrice.getGuestId(), "The response contains a GroupID");
		TestReporter.assertNotNull(BookWithPrice.getReservationType(), "The response contains a Reservation Type");
	}
}
