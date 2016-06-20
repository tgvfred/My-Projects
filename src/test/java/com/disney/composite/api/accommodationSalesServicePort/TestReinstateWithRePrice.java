package com.disney.composite.api.accommodationSalesServicePort;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationSalesServicePort.operations.Book;
import com.disney.api.soapServices.accommodationSalesServicePort.operations.Cancel;
import com.disney.api.soapServices.accommodationSalesServicePort.operations.ReinstateWithRePrice;
import com.disney.utils.TestReporter;
import com.disney.utils.date.DateTimeConversion;

public class TestReinstateWithRePrice {
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
			
		/*@Test(groups={"api", "regression", "accommodation", "accommodationSalesService", "Cancel"})
		public void testCancel_MainFlow(){
			
			Cancel cancel = new Cancel(environment, "Main");
			cancel.setCancelDate(DateTimeConversion.ConvertToDateYYYYMMDD("0"));
			cancel.setTravelComponentGroupingId(book.getTravelComponentGroupingId());
			cancel.sendRequest();
			System.out.println(cancel.getRequest());
			System.out.println(cancel.getResponse());
			TestReporter.assertEquals(cancel.getResponseStatusCode(), "200", "The response code was not 200");
		}*/
		
		@Test(groups={"api", "regression", "accommodation", "accommodationSalesService", "reinstateWithRePrice"})
		        
		public void TestReinstateWithRePrice_MainFlow(){
			
			ReinstateWithRePrice ReinstateWithRePrice = new ReinstateWithRePrice(environment, "Main");
			ReinstateWithRePrice.setTravelComponentGroupingId(book.getTravelComponentGroupingId());
			ReinstateWithRePrice.setTravelComponentId(book.getTravelComponentId());
			ReinstateWithRePrice.setTravelPlanSegmentId(book.getTravelPlanSegmentId());
			ReinstateWithRePrice.sendRequest();
			System.out.println(ReinstateWithRePrice.getRequest());
			System.out.println(ReinstateWithRePrice.getResponse());
			TestReporter.assertEquals(ReinstateWithRePrice.getResponseStatusCode(), "200", "The response code was not 200");
		}
}