package com.disney.composite.api.accommodationSalesServicePort;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationSalesServicePort.operations.Book;
import com.disney.api.soapServices.accommodationSalesServicePort.operations.Cancel;
import com.disney.api.soapServices.accommodationSalesServicePort.operations.ReinstateWithRePrice;
import com.disney.test.utils.Randomness;
import com.disney.utils.TestReporter;

public class TestReinstateWithRePrice {
	    private String environment = "";
		private Book book = null;
		
		@BeforeClass(alwaysRun = true)
		@Parameters({"environment" })
		public void setup(String environment) {
			this.environment = environment;
			book= new Book(environment, "bookRoomOnly2Adults2ChildrenWithoutTickets" );
			book.sendRequest();
			
			Cancel cancel = new Cancel(environment, "Main");
			cancel.setCancelDate(Randomness.generateCurrentXMLDate(0));
			cancel.setTravelComponentGroupingId(book.getTravelComponentGroupingId());
			cancel.sendRequest();
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
		
		@Test(groups={"api", "regression", "accommodation", "accommodationSalesService", "reinstateWithRePrice"})		        
		public void TestReinstateWithRePrice_MainFlow(){	
			TestReporter.logScenario("Test Reinstate with Reprice");
			ReinstateWithRePrice ReinstateWithRePrice = new ReinstateWithRePrice(environment, "Main");
			ReinstateWithRePrice.setTravelComponentGroupingId(book.getTravelComponentGroupingId());
			ReinstateWithRePrice.setTravelComponentId(book.getTravelComponentId());
			ReinstateWithRePrice.setTravelPlanSegmentId(book.getTravelPlanSegmentId());
			ReinstateWithRePrice.sendRequest();
			TestReporter.logAPI(!ReinstateWithRePrice.getResponseStatusCode().equals("200"), "An error occurred reinstating with reprice", ReinstateWithRePrice);
		    TestReporter.log("Travel Plan ID: " + book.getTravelPlanId());
		}
}