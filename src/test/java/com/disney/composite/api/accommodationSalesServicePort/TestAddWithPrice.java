package com.disney.composite.api.accommodationSalesServicePort;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;




import com.disney.api.soapServices.accommodationSalesServicePort.operations.AddWithPrice;
import com.disney.api.soapServices.accommodationSalesServicePort.operations.Book;
import com.disney.utils.TestReporter;

public class TestAddWithPrice {
	private String environment = "";
	Book book = null;
	@BeforeMethod(alwaysRun = true)
	@Parameters({"environment" })
	public void setup(String environment) {
		this.environment = environment;
		book= new Book(environment, "bookRoomOnly2Adults2ChildrenWithoutTickets" );
		book.sendRequest();
		//System.out.println(book.getResponse());
	}
		
	@Test(groups={"api", "regression", "accommodation", "accommodationSalesService", "addWithPrice"})
	public void testAddWithPrice_MainFlow(){
		AddWithPrice AddWithPrice = new AddWithPrice(environment, "Main" );
		AddWithPrice.setTravelPlanSegementId(book.getTravelPlanSegmentId());
		AddWithPrice.setTravelComponentId(book.getTravelComponentId());
		AddWithPrice.setTravelComponentGroupingId(book.getTravelComponentGroupingId());
		AddWithPrice.setTravelPlanId(book.getTravelPlanId());
	    AddWithPrice.sendRequest();
		//System.out.println(AddWithPrice.getRequest());
		//System.out.println(AddWithPrice.getResponse());
		TestReporter.assertEquals(AddWithPrice.getResponseStatusCode(), "200", "The response code was not 200");
		TestReporter.assertNotNull(AddWithPrice.getTravelComponentGroupingId(), "The response contains a Travel Component Grouping ID");
		TestReporter.assertNotNull(AddWithPrice.getTravelComponentId(),  "The response contains a Travel Component ID");
	}
}