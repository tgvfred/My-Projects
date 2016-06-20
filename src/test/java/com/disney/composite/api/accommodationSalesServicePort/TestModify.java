package com.disney.composite.api.accommodationSalesServicePort;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationSalesServicePort.operations.Book;
import com.disney.api.soapServices.accommodationSalesServicePort.operations.Modify;
import com.disney.utils.TestReporter;

public class TestModify {
	private String environment = "";
	Book book = null;
	@BeforeMethod(alwaysRun = true)
	@Parameters({"environment" })
	public void setup(String environment) {
		this.environment = environment;
		book= new Book(environment, "bookRoomOnly2Adults2ChildrenWithoutTickets" );
		book.sendRequest();
	/*	System.out.println(book.getRequest());
		System.out.println(book.getResponse());
		*/}
		
		
	@Test(groups={"api", "regression", "accommodation", "accommodationSalesService", "modify"})
	public void testModify_MainFlow(){
		Modify Modify = new Modify(environment, "Main" );
		Modify.setTravelPlanId(book.getTravelPlanId());
		Modify.setTravelComponentGroupingId(book.getTravelComponentGroupingId());
		Modify.setTravelPlanSegmentId(book.getTravelPlanSegmentId());
		Modify.setTravelComponentId(book.getTravelComponentId());
		Modify.sendRequest();
		/*System.out.println(Modify.getRequest());
		System.out.println(Modify.getResponse());
		*/
		TestReporter.assertEquals(Modify.getResponseStatusCode(), "200", "The response code was not 200");
		TestReporter.assertNotNull(Modify.getPackageCode(), "The response contains a Travel PackageCode");
		TestReporter.assertNotNull(Modify.getResortCode(), "The response contains a Travel ResortCode");
	}
}
