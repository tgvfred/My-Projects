package com.disney.composite.api.cardAuthorizationServiceV2;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;





import com.disney.api.soapServices.accommodationSalesServicePort.operations.AddWithPrice;
import com.disney.api.soapServices.accommodationSalesServicePort.operations.Book;
import com.disney.api.soapServices.cardAuthorizationServiceV2.operations.RequestCreditCardRRN;
import com.disney.utils.TestReporter;

public class TestRequestCreditCardRRN {
	private String environment = "";
	@BeforeMethod(alwaysRun = true)
	//@Parameters({"environment" })
	//public void setup(String environment) {
	public void setup() {
		this.environment = "Grumpy";
	}
		
	@Test(groups={"api", "regression", "accommodation", "accommodationSalesService", "addWithPrice"})
	public void testRequestCreditCardRRN_MainFlow(){
		RequestCreditCardRRN rrn = new RequestCreditCardRRN(environment, "Main");
		System.out.println(rrn.getRequest());
		rrn.sendRequest();
		System.out.println(rrn.getResponse());
		TestReporter.assertEquals(rrn.getResponseStatusCode(), "200", "The response code was not 200");
		TestReporter.assertEquals(rrn.getStatusCode(), "APPROVED", "The response code was not 200");
		TestReporter.assertNotNull(rrn.getRetrievalReferenceNumber(), "The response contains a RRN");
		TestReporter.assertNotNull(rrn.getRetrievalReferenceNumberKey(),  "The response contains a Travel Component ID");
	}
}