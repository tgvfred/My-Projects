
package com.disney.composite.api.accommodationModule.soapServices.accommodationSalesServicePort;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.BookWithPrice;
import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.Cancel;
import com.disney.utils.Randomness;
import com.disney.utils.TestReporter;

public class TestBookWithPrice {
	private String environment = "";
	private BookWithPrice BookWithPrice;
	
	@BeforeMethod(alwaysRun = true)
	@Parameters({"environment"})
	public void setup(String environment) {this.environment = environment;}
	
	@AfterMethod(alwaysRun=true)
	public void teardown(){
		try{
			if(BookWithPrice != null){
				if(BookWithPrice.getTravelPlanSegmentId() != null){
					if(!BookWithPrice.getTravelPlanSegmentId().isEmpty()){
						Cancel cancel = new Cancel(environment, "Main");
						cancel.setCancelDate(Randomness.generateCurrentXMLDate(0));
						cancel.setTravelComponentGroupingId(BookWithPrice.getTravelComponentGroupingId());
						cancel.sendRequest();
					}
				}
			}
		}catch(Exception e){}
	}
		
	@Test(groups={"api", "regression", "accommodation", "accommodationSalesService", "BookWithPrice", "debug"})
	public void testBookWithPrice_MainFlow(){
		TestReporter.logScenario("Test Book with Price");
		BookWithPrice = new BookWithPrice(environment, "Main" );
		BookWithPrice.sendRequest();
		TestReporter.logAPI(!BookWithPrice.getResponseStatusCode().equals("200"), "An error occurred booking with price", BookWithPrice);
	    TestReporter.log("Travel Plan ID: " + BookWithPrice.getTravelPlanId());
		TestReporter.assertNotNull(BookWithPrice.getGuestId(), "The response contains a GroupID");
		TestReporter.assertNotNull(BookWithPrice.getReservationType(), "The response contains a Reservation Type");
	}
}