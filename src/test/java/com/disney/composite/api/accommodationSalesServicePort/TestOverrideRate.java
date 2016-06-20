package com.disney.composite.api.accommodationSalesServicePort;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationSalesServicePort.operations.Book;
import com.disney.api.soapServices.accommodationSalesServicePort.operations.OverrideRate;
import com.disney.utils.TestReporter;

public class TestOverrideRate {
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
		
	@Test(groups={"api", "regression", "accommodation", "accommodationSalesService", "overrideRate"})
	public void testOverrideRate_MainFlow(){
		OverrideRate OverrideRate= new OverrideRate(environment, "Main" );
		OverrideRate.setTravelPlanSegementId(book.getTravelPlanSegmentId());
		OverrideRate.setTravelComponentGroupingId(book.getTravelComponentGroupingId());
		OverrideRate.sendRequest();
	    //System.out.println(OverrideRate.getRequest());
	    //System.out.println(OverrideRate.getResponse());
		TestReporter.assertEquals(OverrideRate.getResponseStatusCode(), "200", "The response code was not 200");
		TestReporter.assertNotNull(OverrideRate.gettravelComponentId(),  "The response contains a Travel Component Id");
		TestReporter.assertNotNull(OverrideRate.gettravelComponentGroupingId(),  "The response contains a TravelComponent GroupingI d");
	   }
	}