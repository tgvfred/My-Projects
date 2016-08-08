package com.disney.composite.api.accommodationModule.accommodationSalesServicePort;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.Book;
import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.Cancel;
import com.disney.utils.TestReporter;
import com.disney.utils.date.DateTimeConversion;

public class TestCancel {
	private String environment = "";
	private Book book = null;
	
	@BeforeMethod(alwaysRun = true)
	@Parameters({"environment" })
	public void setup(String environment) {
		this.environment = environment;
		book= new Book(environment, "bookRoomOnly2Adults2ChildrenWithoutTickets" );
		book.sendRequest();
	}
		
	@Test(groups={"api", "regression", "accommodation", "accommodationSalesService", "Cancel"})
	public void testCancel_MainFlow(){
		TestReporter.logScenario("Test Cancel");
	    TestReporter.log("Travel Plan ID: " + book.getTravelPlanId());
		Cancel cancel = new Cancel(environment, "Main");
		cancel.setCancelDate(DateTimeConversion.ConvertToDateYYYYMMDD("0"));
		cancel.setTravelComponentGroupingId(book.getTravelComponentGroupingId());
		cancel.sendRequest();
		TestReporter.logAPI(!cancel.getResponseStatusCode().equals("200"), "An error occurred cancelling the reservation.", cancel);
		TestReporter.assertNotNull(cancel.getCancellationNumber(), "The response contains a cancellation number");
	}
}
