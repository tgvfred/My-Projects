package com.disney.composite.api.accommodationModule.accommodationSalesServicePort;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.Book;
import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.Cancel;
import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.RetrievePackageAndRackRates;
import com.disney.test.utils.Randomness;
import com.disney.utils.TestReporter;

public class TestRetrievePackageAndRackRates {
	private String environment = "";
    private Book book = null;
    
    @BeforeMethod(alwaysRun = true)
	@Parameters({  "environment" })
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
		
	@Test(groups={"api", "regression", "accommodation", "accommodationSalesService", "retrievePackageAndRackRates"})
	public void testRetrievePackageAndRackRates_MainFlow(){
		TestReporter.logScenario("Test Retrieving Package and Rack Rates");
	    RetrievePackageAndRackRates RetrievePackageAndRackRates= new RetrievePackageAndRackRates(environment, "Main" );
	    RetrievePackageAndRackRates.setTravelPlanSegementId(book.getTravelPlanSegmentId());
	    RetrievePackageAndRackRates.setaccomComponentId(book.getTravelComponentId());
	    RetrievePackageAndRackRates.sendRequest();
		TestReporter.logAPI(!RetrievePackageAndRackRates.getResponseStatusCode().equals("200"), "An error occurred retrieving package and rack rates", RetrievePackageAndRackRates);
	    TestReporter.log("Travel Plan ID: " + book.getTravelPlanId());
		TestReporter.assertNotNull(RetrievePackageAndRackRates.getdate(),  "The response contains a date");
		TestReporter.assertNotNull(RetrievePackageAndRackRates.getpackagePrice(),  "The response contains a package Price");
}
}