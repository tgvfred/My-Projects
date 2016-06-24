package com.disney.composite.api.accommodationSalesServicePort;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationSalesServicePort.operations.Book;
import com.disney.api.soapServices.accommodationSalesServicePort.operations.ModifyWithPrice;
import com.disney.utils.TestReporter;

public class TestModifyWithPrice {
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
	
	@Test(groups={"api", "regression", "accommodation", "accommodationSalesService", "modifyWithPrice"})
	public void ModifyWithPrice_MainFlow(){
		ModifyWithPrice ModifyWithPrice = new ModifyWithPrice(environment, "Main" );
		ModifyWithPrice.setTravelPlanSegementId(book.getTravelPlanSegmentId());
		ModifyWithPrice.setTravelComponentId(book.getTravelComponentId());
		ModifyWithPrice.setTravelComponentGroupingId(book.getTravelComponentGroupingId());
		ModifyWithPrice.setTravelPlanId(book.getTravelPlanId());
		ModifyWithPrice.sendRequest();
	    //System.out.println(ModifyWithPrice.getRequest());
	    //System.out.println(ModifyWithPrice.getResponse());
		TestReporter.assertEquals(ModifyWithPrice.getResponseStatusCode(), "200", "The response code was not 200");
		TestReporter.assertNotNull(ModifyWithPrice.getTravelComponentGroupingId(), "The response contains a Travel Component Grouping ID");
		TestReporter.assertNotNull(ModifyWithPrice.getTravelComponentId(),  "The response contains a Travel Component ID");
	}
}