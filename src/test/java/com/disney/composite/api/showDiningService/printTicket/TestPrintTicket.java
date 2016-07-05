package com.disney.composite.api.showDiningService.printTicket;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import com.disney.api.soapServices.showDiningService.operations.Book;
import com.disney.api.soapServices.showDiningService.operations.Cancel;
import com.disney.api.soapServices.showDiningService.operations.PrintTicket;
import com.disney.composite.BaseTest;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.staging.bookSEReservation.ScheduledEventReservation;

public class TestPrintTicket extends BaseTest{
	private Book book;
	
	@AfterMethod
	public void teardown(){
		if(book != null)
			if(!book.getTravelPlanSegmentId().isEmpty()){
				Cancel cancel = new Cancel(environment, "CancelDiningEvent");
				cancel.setTravelPlanSegmentId(book.getTravelPlanSegmentId());
				cancel.sendRequest();
			}
	}

	@Test(groups = {"api", "regression", "dining", "showDiningService"})
	public void testPrintTicket() {
		TestReporter.logStep("Book a show dining reservation.");
		book = new Book(environment, ScheduledEventReservation.ONECOMPONENTSNOADDONS);
		book.setParty(hh);
		book.sendRequest();
		TestReporter.logAPI(!book.getResponseStatusCode().equals("200"), "An error occurred during booking", book);
		
		TestReporter.logStep("Print Ticket for Show Dining Reservation");
		PrintTicket print = new PrintTicket(environment, "Main");
		print = new PrintTicket(environment, "Main");
		print.setReservationnumber(book.getTravelPlanSegmentId());
		print.sendRequest();
		TestReporter.logAPI(!print.getResponseStatusCode().equals("200"), "An error occurred while printing a ticket.", print);
		TestReporter.assertEquals(print.getStatus(), "SUCCESS", "The status ["+print.getStatus()+"] was not 'SUCCESS' as expected.");
	}
}