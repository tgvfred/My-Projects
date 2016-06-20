package com.disney.composite.api.accommodationSalesServicePort;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationSalesServicePort.operations.Book;
import com.disney.api.soapServices.accommodationSalesServicePort.operations.ReplaceAllForTravelPlanSegment;
import com.disney.utils.TestReporter;

public class TestReplaceAllForTravelPlanSegment {
	private String environment = "";
    Book book = null;
    @BeforeMethod(alwaysRun = true)
	@Parameters({"environment" })
	public void setup(String environment) {
		this.environment = environment;
		book= new Book(environment, "bookRoomOnly2Adults2ChildrenWithoutTickets" );
		book.sendRequest();
		//System.out.println(book.getRequest());
		//System.out.println(book.getResponse());
	}
	
	@Test(groups={"api", "regression", "accommodation", "accommodationSalesService", "replaceAllForTravelPlanSegment"})
	public void testReplaceAllForTravelPlanSegment_MainFlow(){
		ReplaceAllForTravelPlanSegment ReplaceAllForTravelPlanSegment = new ReplaceAllForTravelPlanSegment(environment, "Main" );
		ReplaceAllForTravelPlanSegment.setTravelPlanSegementId(book.getTravelPlanSegmentId());
		ReplaceAllForTravelPlanSegment.setTravelComponentId(book.getTravelComponentId());
		ReplaceAllForTravelPlanSegment.setTravelComponentGroupingId(book.getTravelComponentGroupingId());
		ReplaceAllForTravelPlanSegment.setTravelPlanId(book.getTravelPlanId());
		ReplaceAllForTravelPlanSegment.sendRequest();
	    //System.out.println(ReplaceAllForTravelPlanSegment.getRequest());
	    //System.out.println(ReplaceAllForTravelPlanSegment.getResponse());
		TestReporter.assertEquals(ReplaceAllForTravelPlanSegment.getResponseStatusCode(), "200", "The response code was not 200");
		TestReporter.assertNotNull(ReplaceAllForTravelPlanSegment.getTravelComponentGroupingId(), "The response contains a Travel Component Grouping ID");
		TestReporter.assertNotNull(ReplaceAllForTravelPlanSegment.getTravelComponentId(),  "The response contains a Travel Component ID");
	}
}