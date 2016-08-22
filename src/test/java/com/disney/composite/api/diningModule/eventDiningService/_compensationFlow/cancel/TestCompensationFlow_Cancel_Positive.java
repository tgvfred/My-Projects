package com.disney.composite.api.diningModule.eventDiningService._compensationFlow.cancel;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.diningModule.eventDiningService.operations.Book;
import com.disney.api.soapServices.diningModule.eventDiningService.operations.Cancel;
import com.disney.api.soapServices.diningModule.eventDiningService.operations.Retrieve;
import com.disney.composite.BaseTest;
import com.disney.utils.Regex;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.guestFactory.HouseHold;
import com.disney.utils.dataFactory.staging.bookSEReservation.ScheduledEventReservation;

public class TestCompensationFlow_Cancel_Positive extends BaseTest{
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
	}
	
	@Test(groups = {"api", "regression", "dining", "eventDiningService", "compensation"})
	public void testCompensationFlow_Cancel_Positive(){
		Cancel cancel = new Cancel(environment, "CancelDiningEvent");
		cancel.setReservationNumber(book.getTravelPlanSegmentId());
		cancel.sendRequest(reservableResourceId, dateTime);
		TestReporter.logAPI(!cancel.getResponseStatusCode().equals("200"), "An error occurred cancelling the reservation.", cancel);
		TestReporter.logAPI(!Regex.match("[0-9]+", cancel.getCancellationConfirmationNumber()), "Verify the cancellation confirmation number ["+cancel.getCancellationConfirmationNumber()+"] is numeric as expected.", cancel);		
		TestReporter.assertTrue(Integer.parseInt(cancel.getInventoryCountBefore()) > Integer.parseInt(cancel.getInventoryCountAfter()), "Verify the booked inventory count ["+cancel.getInventoryCountAfter()+"] for reservable resource ID ["+reservableResourceId+"] decrements from the count prior to cancelling ["+cancel.getInventoryCountBefore()+"]");
		
		Retrieve retrieve = new Retrieve(environment, "RetrieveDiningEvent");
		retrieve.setReservationNumber(book.getTravelPlanSegmentId());
		retrieve.sendRequest();
		TestReporter.logAPI(!retrieve.getResponseStatusCode().equals("200"), "An error occurred retrieving reservation number ["+book.getTravelPlanSegmentId()+"].", retrieve);
		TestReporter.assertEquals(retrieve.getStatus(), "Cancelled", "Verify the reservation status ["+retrieve.getStatus()+"] is [Cancelled] as expected.");
	}
}