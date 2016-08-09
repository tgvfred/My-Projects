package com.disney.composite.api.accommodationModule.accommodationSalesServicePort;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.Book;
import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.BookForShare;
import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.Cancel;
import com.disney.test.utils.Randomness;
import com.disney.utils.TestReporter;

public class TestBookForShare {
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
		
	@Test(groups={"api", "regression", "accommodation", "accommodationSalesService", "bookForShare"})
	public void testBookForShare_MainFlow(){
		TestReporter.logScenario("Test Book for Share");
	    BookForShare BookForShare = new BookForShare(environment, "Main" );
	    BookForShare.sendRequest();
	    TestReporter.logAPI(!BookForShare.getResponseStatusCode().equals("200"), "An error occurred booking a reservation for share.", BookForShare);
	    TestReporter.log("Travel Plan ID: " + book.getTravelPlanId());
		TestReporter.assertNotNull(BookForShare.getlocationId(),  "The response contains a Location Id");
		TestReporter.assertNotNull(BookForShare.getshared(),  "The response contains a Shared");
	}
}