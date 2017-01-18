package com.disney.composite.api.accommodationModule.soapServices.accommodationSalesServicePort;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.Book;
import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.Cancel;
import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.Modify;
import com.disney.utils.Randomness;
import com.disney.utils.TestReporter;

public class TestModify {
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
		
	@Test(groups={"api", "regression", "accommodation", "accommodationSalesService", "modify"})
	public void testModify_MainFlow(){
		TestReporter.logScenario("Test Modify Reservation");
		Modify Modify = new Modify(environment, "Main" );
		Modify.setTravelPlanId(book.getTravelPlanId());
		Modify.setTravelComponentGroupingId(book.getTravelComponentGroupingId());
		Modify.setTravelPlanSegmentId(book.getTravelPlanSegmentId());
		Modify.setTravelComponentId(book.getTravelComponentId());
		Modify.sendRequest();
		TestReporter.logAPI(!Modify.getResponseStatusCode().equals("200"), "An error occurred modifying the reservation", Modify);
		TestReporter.assertNotNull(Modify.getPackageCode(), "The response contains a Travel PackageCode");
		TestReporter.assertNotNull(Modify.getResortCode(), "The response contains a Travel ResortCode");
	}
}
