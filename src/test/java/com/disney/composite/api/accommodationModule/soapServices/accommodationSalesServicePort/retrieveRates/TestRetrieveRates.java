package com.disney.composite.api.accommodationModule.soapServices.accommodationSalesServicePort.retrieveRates;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.Book;
import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.Cancel;
import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.RetrieveRates;
import com.disney.utils.Randomness;
import com.disney.utils.TestReporter;

public class TestRetrieveRates {
    private String environment = "";
    private Book book = null;

    @BeforeMethod(alwaysRun = true)
<<<<<<< HEAD:src/test/java/com/disney/composite/api/accommodationModule/soapServices/accommodationSalesServicePort/retrieveRates/TestRetrieveRates.java
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
		
	@Test(groups={"api", "regression", "accommodation", "accommodationSalesService", "retrieveRates"})
	public void testRetrieveRates_MainFlow(){
		TestReporter.logScenario("Test Retrieve Rates");
		RetrieveRates RetrieveRates = new RetrieveRates(environment, "retrieveRates" );
		RetrieveRates.setTravelComponentGroupingId(book.getTravelComponentGroupingId());
		RetrieveRates.sendRequest();
		TestReporter.logAPI(!RetrieveRates.getResponseStatusCode().equals("200"), "An error occurred retrieving rates", RetrieveRates);
	    TestReporter.log("Travel Plan ID: " + book.getTravelPlanId());
		TestReporter.assertNotNull(RetrieveRates.getroomTypeCode(), "The response contains a Room Type Code");
		TestReporter.assertNotNull(RetrieveRates.getadditionalChargeOverridden(), "The response contains a Additional Charge Overridden");
	}
	@Test(groups={"api", "regression", "accommodation", "accommodationSalesService", "retrieveRates"})
	public void TestRetrieveRates_roomOnly_oneNight(){
		
	}
	@Test(groups={"api", "regression", "accommodation", "accommodationSalesService", "retrieveRates"})
	public void TestRetrieveRates_roomOnly_twoNights(){
		
	}
	@Test(groups={"api", "regression", "accommodation", "accommodationSalesService", "retrieveRates"})
	public void TestRetrieveRates_showDiningReservation(){
		
	}
	@Test(groups={"api", "regression", "accommodation", "accommodationSalesService", "retrieveRates"})
	public void TestRetrieveRates_roomOnly_checkedIn(){
		
	}
	
=======
    @Parameters({ "environment" })
    public void setup(String environment) {
        this.environment = environment;
        book = new Book(environment, "bookRoomOnly2Adults2ChildrenWithoutTickets");
        book.sendRequest();
    }

    @AfterMethod(alwaysRun = true)
    public void teardown() {
        try {
            if (book != null) {
                if (book.getTravelPlanSegmentId() != null) {
                    if (!book.getTravelPlanSegmentId().isEmpty()) {
                        Cancel cancel = new Cancel(environment, "Main");
                        cancel.setCancelDate(Randomness.generateCurrentXMLDate(0));
                        cancel.setTravelComponentGroupingId(book.getTravelComponentGroupingId());
                        cancel.sendRequest();
                    }
                }
            }
        } catch (Exception e) {
        }
    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesService", "retrieveRates", "example" })
    public void testRetrieveRates_MainFlow() {
        TestReporter.logScenario("Test Retrieve Rates");
        RetrieveRates RetrieveRates = new RetrieveRates(environment, "retrieveRates");
        RetrieveRates.setTravelComponentGroupingId(book.getTravelComponentGroupingId());
        RetrieveRates.sendRequest();
        TestReporter.logAPI(!RetrieveRates.getResponseStatusCode().equals("200"), "An error occurred retrieving rates", RetrieveRates);
        TestReporter.log("Travel Plan ID: " + book.getTravelPlanId());
        TestReporter.assertNotNull(RetrieveRates.getroomTypeCode(), "The response contains a Room Type Code");
        TestReporter.assertNotNull(RetrieveRates.getadditionalChargeOverridden(), "The response contains a Additional Charge Overridden");
    }
>>>>>>> 3355aa155f32d01c6ada4313d785ad8fa54a0e34:src/test/java/com/disney/composite/api/accommodationModule/soapServices/accommodationSalesServicePort/TestRetrieveRates.java
}