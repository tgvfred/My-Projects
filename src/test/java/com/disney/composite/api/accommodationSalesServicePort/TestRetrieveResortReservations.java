package com.disney.composite.api.accommodationSalesServicePort;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationSalesServicePort.operations.Book;
import com.disney.api.soapServices.accommodationSalesServicePort.operations.RetrieveResortReservations;
import com.disney.utils.TestReporter;

public class TestRetrieveResortReservations {
	private String environment = "";
    Book book = null;
    @BeforeMethod(alwaysRun = true)
	@Parameters({  "environment" })
	public void setup(String environment) {
		this.environment = environment;
		book= new Book(environment, "bookRoomOnly2Adults2ChildrenWithoutTickets" );
		//System.out.println(book.getRequest());
		book.sendRequest();
	    //System.out.println(book.getResponse());
	}
	
	@Test(groups={"api", "regression", "accommodation", "accommodationSalesService", "retrieveResortReservations"})
	public void testretrieveResortReservations_MainFlow(){
		RetrieveResortReservations RetrieveResortReservations= new RetrieveResortReservations(environment, "Main" );
		RetrieveResortReservations.setreservationNumber(book.getTravelPlanSegmentId());
		RetrieveResortReservations.sendRequest();
	    //System.out.println(RetrieveResortReservations.getRequest());
	    //System.out.println(RetrieveResortReservations.getResponse());
		TestReporter.assertEquals(RetrieveResortReservations.getResponseStatusCode(), "200", "The response code was not 200");
		TestReporter.assertNotNull(RetrieveResortReservations.getTravelPlanSegmentId(), "The response contains a Travel Plan Segment Id");
		TestReporter.assertNotNull(RetrieveResortReservations.getpurposeOfVisit(), "The response contains a purpose Of Visit");
	}
}