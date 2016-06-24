package com.disney.composite.api.accommodationSalesServicePort;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationSalesServicePort.operations.Book;
import com.disney.api.soapServices.accommodationSalesServicePort.operations.Cancel;
import com.disney.utils.TestReporter;
import com.disney.utils.date.DateTimeConversion;

public class TestCancel {
	private String environment = "";
	Book book = null;
	@BeforeMethod(alwaysRun = true)
	@Parameters({"environment" })
	public void setup(String environment) {
		this.environment = environment;
		book= new Book(environment, "bookRoomOnly2Adults2ChildrenWithoutTickets" );
		book.sendRequest();
		
		System.out.println(book.getRequest());
		System.out.println(book.getResponse());
	}
		
	@Test(groups={"api", "regression", "accommodation", "accommodationSalesService", "Cancel"})
	public void testCancel_MainFlow(){
		Cancel cancel = new Cancel(environment, "Main");
		cancel.setCancelDate(DateTimeConversion.ConvertToDateYYYYMMDD("0"));
		cancel.setTravelComponentGroupingId(book.getTravelComponentGroupingId());
		cancel.sendRequest();
		
		System.out.println(cancel.getRequest());
		System.out.println(cancel.getResponse());
		TestReporter.assertEquals(cancel.getResponseStatusCode(), "200", "The response code was not 200");
		TestReporter.assertNotNull(cancel.getCancellationNumber(), "The response contains a cancellation number");
	}
}
