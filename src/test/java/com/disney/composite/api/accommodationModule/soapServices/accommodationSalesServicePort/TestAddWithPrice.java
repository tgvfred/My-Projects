package com.disney.composite.api.accommodationModule.soapServices.accommodationSalesServicePort;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.AddWithPrice;
import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.Book;
import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.Cancel;
import com.disney.utils.Randomness;
import com.disney.utils.TestReporter;

public class TestAddWithPrice {
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
	
	@Test(groups={"api", "regression", "accommodation", "accommodationSalesService", "addWithPrice"})
	public void testAddWithPrice_MainFlow(){
		TestReporter.logScenario("Test Add with Price");
		AddWithPrice AddWithPrice = new AddWithPrice(environment, "Main" );
		AddWithPrice.setTravelPlanSegementId(book.getTravelPlanSegmentId());
		AddWithPrice.setTravelComponentId(book.getTravelComponentId());
		AddWithPrice.setTravelComponentGroupingId(book.getTravelComponentGroupingId());
		AddWithPrice.setTravelPlanId(book.getTravelPlanId());
	    AddWithPrice.sendRequest();
	    TestReporter.logAPI(!AddWithPrice.getResponseStatusCode().equals("200"), "An error occurred adding with price", AddWithPrice);
	    TestReporter.log("Travel Plan ID: " + book.getTravelPlanId());
		TestReporter.assertNotNull(AddWithPrice.getTravelComponentGroupingId(), "The response contains a Travel Component Grouping ID");
		TestReporter.assertNotNull(AddWithPrice.getTravelComponentId(),  "The response contains a Travel Component ID");
	}
}