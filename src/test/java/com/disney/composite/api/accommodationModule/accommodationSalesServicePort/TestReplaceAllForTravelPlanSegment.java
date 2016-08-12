package com.disney.composite.api.accommodationModule.accommodationSalesServicePort;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.Book;
import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.Cancel;
import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.ReplaceAllForTravelPlanSegment;
import com.disney.test.utils.Randomness;
import com.disney.utils.TestReporter;

public class TestReplaceAllForTravelPlanSegment {
	private String environment = "";
    private Book book = null;
    
    @BeforeMethod(alwaysRun = true)
	@Parameters({"environment" })
	public void setup(String environment) {
		this.environment = environment;
		book= new Book(environment, "bookRoomOnly2Adults2ChildrenWithoutTickets" );
		book.sendRequest();
	}
	
	@AfterMethod(alwaysRun=true)
	public void teardown(){
		try{
			if(book != null){
				if(book.getTravelPlanSegmentId() != null){
					if(!book.getTravelPlanSegmentId().isEmpty()){
						Cancel cancel = new Cancel(environment, "Main");
						cancel.setCancelDate(Randomness.generateCurrentXMLDate(0));
						cancel.setTravelComponentGroupingId(book.getTravelComponentGroupingId());
						cancel.sendRequest();
					}
				}
			}
		}catch(Exception e){}
	}
	
	@Test(groups={"api", "regression", "accommodation", "accommodationSalesService", "replaceAllForTravelPlanSegment"})
	public void testReplaceAllForTravelPlanSegment_MainFlow(){
		TestReporter.logScenario("Test Replce All for Travel Plan Segment");
		ReplaceAllForTravelPlanSegment ReplaceAllForTravelPlanSegment = new ReplaceAllForTravelPlanSegment(environment, "Main" );
		ReplaceAllForTravelPlanSegment.setTravelPlanSegementId(book.getTravelPlanSegmentId());
		ReplaceAllForTravelPlanSegment.setTravelComponentId(book.getTravelComponentId());
		ReplaceAllForTravelPlanSegment.setTravelComponentGroupingId(book.getTravelComponentGroupingId());
		ReplaceAllForTravelPlanSegment.setTravelPlanId(book.getTravelPlanId());
		ReplaceAllForTravelPlanSegment.sendRequest();
		TestReporter.logAPI(!ReplaceAllForTravelPlanSegment.getResponseStatusCode().equals("200"), "An error occurred replacing all for travel plan segment", ReplaceAllForTravelPlanSegment);
	    TestReporter.log("Travel Plan ID: " + book.getTravelPlanId());
		TestReporter.assertNotNull(ReplaceAllForTravelPlanSegment.getTravelComponentGroupingId(), "The response contains a Travel Component Grouping ID");
		TestReporter.assertNotNull(ReplaceAllForTravelPlanSegment.getTravelComponentId(),  "The response contains a Travel Component ID");
	}
}