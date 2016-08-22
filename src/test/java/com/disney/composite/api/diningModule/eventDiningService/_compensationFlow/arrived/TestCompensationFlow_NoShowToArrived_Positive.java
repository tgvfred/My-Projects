package com.disney.composite.api.diningModule.eventDiningService._compensationFlow.arrived;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.diningModule.eventDiningService.operations.Arrived;
import com.disney.api.soapServices.diningModule.eventDiningService.operations.Book;
import com.disney.api.soapServices.diningModule.eventDiningService.operations.Cancel;
import com.disney.api.soapServices.diningModule.eventDiningService.operations.NoShow;
import com.disney.api.soapServices.diningModule.eventDiningService.operations.Retrieve;
import com.disney.composite.BaseTest;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.guestFactory.HouseHold;
import com.disney.utils.dataFactory.staging.bookSEReservation.ScheduledEventReservation;

public class TestCompensationFlow_NoShowToArrived_Positive extends BaseTest{
	private Book book;
	private String reservableResourceId;
	private String dateTime;
	
	@Override
	@BeforeMethod(alwaysRun = true)
	@Parameters("environment")
	public void setup(@Optional String environment){
		this.environment = environment;
		hh = new HouseHold(1);
		book = new Book(environment, ScheduledEventReservation.NOCOMPONENTSNOADDONS);
		book.setParty(hh);
		book.sendRequest();
		TestReporter.logAPI(!book.getResponseStatusCode().equals("200"), "An error occurred during booking: " + book.getFaultString(), book);
		reservableResourceId = book.getReservableResourceId();
		dateTime = book.getDateTime();
		
		NoShow noShow = new NoShow(environment, "Main");
		noShow.setReservationNumber(book.getTravelPlanSegmentId());
		noShow.sendRequest();
		TestReporter.logAPI(!noShow.getResponseStatusCode().equals("200"), "An error occurred setting the reservation to 'NoShow': " + noShow.getFaultString(), noShow);
	}
	
	@AfterMethod(alwaysRun=true)
	public void teardown(){
		try{
			Cancel cancel = new Cancel(environment, "CancelDiningEvent");
			cancel.setReservationNumber(book.getTravelPlanSegmentId());
			cancel.sendRequest();
		}catch(Exception e){}
	}
	
	@Test(groups = {"api", "regression", "dining", "eventDiningService", "compensation"})
	public void testCompensationFlow_NoShowToArrived_Positive(){
		Arrived arrived = new Arrived(environment, "Main");
		arrived.setReservationNumber(book.getTravelPlanSegmentId());
		arrived.sendRequest(reservableResourceId, dateTime);
		TestReporter.logAPI(!arrived.getResponseStatusCode().equals("200"), "An error occurred setting the reservation to 'Arrived'", arrived);
		TestReporter.logAPI(!arrived.getArrivalStatus().equals("SUCCESS"), "The response ["+arrived.getArrivalStatus()+"] was not 'SUCCESS' as expected.", arrived);
		TestReporter.assertTrue(Integer.parseInt(arrived.getInventoryCountBefore()) == Integer.parseInt(arrived.getInventoryCountAfter()), "Verify the booked inventory count ["+arrived.getInventoryCountAfter()+"] for reservable resource ID ["+reservableResourceId+"] equals the count prior to setting the reservation to 'Arrived' ["+arrived.getInventoryCountBefore()+"]");
		
		Retrieve retrieve = new Retrieve(environment, "RetrieveDiningEvent");
		retrieve.setReservationNumber(book.getTravelPlanSegmentId());
		retrieve.sendRequest();
		TestReporter.assertEquals(retrieve.getStatus(), "Arrived", "Verify the reservation status ["+retrieve.getStatus()+"] is [Arrived] as expected.");
	}
}