package com.disney.composite.api.accommodationSalesServicePort;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationSalesServicePort.operations.Book;
import com.disney.api.soapServices.accommodationSalesServicePort.operations.Cancel;
import com.disney.api.soapServices.accommodationSalesServicePort.operations.Reinstate;
import com.disney.utils.TestReporter;
import com.disney.utils.date.DateTimeConversion;

public class TestReinstate {
	private String environment = "";
	private Book book = null;
	
	@BeforeClass(alwaysRun = true)
	@Parameters({"environment" })
	public void setup(String environment) {
		this.environment = environment;
		book= new Book(environment, "bookRoomOnly2Adults2ChildrenWithoutTickets" );
		book.sendRequest();
		
		Cancel cancel = new Cancel(environment, "Main");
		cancel.setCancelDate(DateTimeConversion.ConvertToDateYYYYMMDD("0"));
		cancel.setTravelComponentGroupingId(book.getTravelComponentGroupingId());
		cancel.sendRequest();
	}
	
	@Test(groups={"api", "regression", "accommodation", "accommodationSalesService", "Reinstate"})
	public void testReinstate_MainFlow(){
		TestReporter.logScenario("Test Reinstate");
	    TestReporter.log("Travel Plan ID: " + book.getTravelPlanId());
		Reinstate Reinstate = new Reinstate(environment, "Main");
		Reinstate.setTravelComponentGroupingId(book.getTravelComponentGroupingId());
		Reinstate.setTravelComponentId(book.getTravelComponentId());
		Reinstate.setTravelPlanSegmentId(book.getTravelPlanSegmentId());
		Reinstate.sendRequest();
		TestReporter.logAPI(!Reinstate.getResponseStatusCode().equals("200"), "An error occurred reinstating the reservation with travel plan segment ID["+book.getTravelPlanSegmentId()+"]", Reinstate);
	}	
}