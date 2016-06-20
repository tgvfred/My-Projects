package com.disney.composite.api.accommodationSalesServicePort;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationSalesServicePort.operations.Book;
import com.disney.api.soapServices.accommodationSalesServicePort.operations.Cancel;
import com.disney.api.soapServices.accommodationSalesServicePort.operations.Reinstate;
import com.disney.api.soapServices.accommodationSalesServicePort.operations.Retrieve;
import com.disney.utils.TestReporter;
import com.disney.utils.date.DateTimeConversion;

public class TestReinstate {
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
		
	@Test(groups={"api", "regression", "accommodation", "accommodationSalesService", "Cancel"})
	public void testCancel_MainFlow(){
		
		Cancel cancel = new Cancel(environment, "Main");
		cancel.setCancelDate(DateTimeConversion.ConvertToDateYYYYMMDD("0"));
		cancel.setTravelComponentGroupingId(book.getTravelComponentGroupingId());
		cancel.sendRequest();
		System.out.println(cancel.getRequest());
		System.out.println(cancel.getResponse());
		TestReporter.assertEquals(cancel.getResponseStatusCode(), "200", "The response code was not 200");
	}
	
	@Test(groups={"api", "regression", "accommodation", "accommodationSalesService", "Reinstate"},
			dependsOnMethods = "testCancel_MainFlow")
	public void testReinstate_MainFlow(){
		
		Reinstate Reinstate = new Reinstate(environment, "Main");
		Reinstate.setTravelComponentGroupingId(book.getTravelComponentGroupingId());
		Reinstate.setTravelComponentId(book.getTravelComponentId());
		Reinstate.setTravelPlanSegmentId(book.getTravelPlanSegmentId());
		Reinstate.sendRequest();
		System.out.println(Reinstate.getRequest());
		System.out.println(Reinstate.getResponse());
		TestReporter.assertEquals(Reinstate.getResponseStatusCode(), "200", "The response code was not 200");
	}
	
	
	
	
}
