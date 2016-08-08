package com.disney.composite.api.diningModule.showDiningService.reprintTicket;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.diningModule.showDiningService.operations.Book;
import com.disney.api.soapServices.diningModule.showDiningService.operations.Cancel;
import com.disney.api.soapServices.diningModule.showDiningService.operations.PrintTicket;
import com.disney.api.soapServices.diningModule.showDiningService.operations.ReprintTicket;
import com.disney.composite.BaseTest;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.database.LogItems;
import com.disney.utils.dataFactory.guestFactory.HouseHold;
import com.disney.utils.dataFactory.staging.bookSEReservation.ScheduledEventReservation;

public class TestReprintTicket extends BaseTest{
	private Book book;
	protected HouseHold hh = null;
	
	@Override
	@BeforeMethod(alwaysRun=true)
	@Parameters("environment")
	public void setup(@Optional String environment){
		this.environment = environment;
		hh = new HouseHold(1);
	}
	
	@AfterMethod(alwaysRun = true)
	public void teardown(){
		try{
			if(book != null)
				if(!book.getTravelPlanSegmentId().isEmpty()){
					Cancel cancel = new Cancel(environment, "CancelDiningEvent");
					cancel.setTravelPlanSegmentId(book.getTravelPlanSegmentId());
					cancel.sendRequest();
				}
		}catch(Exception e){}
	}

	@Test(groups = {"api", "regression", "dining", "showDiningService"})
	public void testReprintTicket() {
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

		TestReporter.logStep("RePrint Ticket");
		ReprintTicket reprint = new ReprintTicket(environment, "Main");
		reprint = new ReprintTicket(environment, "Main");
		reprint.setReservationNumber(book.getTravelPlanSegmentId());
		reprint.setReprintReasonId("1");
		reprint.sendRequest();
		TestReporter.logAPI(!reprint.getResponseStatusCode().equals("200"), "An error occurred while reprinting a ticket.", reprint);
		TestReporter.assertEquals(reprint.getStatus(), "SUCCESS", "The status ["+reprint.getStatus()+"] was not 'SUCCESS' as expected.");
		logItems(reprint);
	}
	
	private void logItems(ReprintTicket reprint){
		LogItems logValidItems = new LogItems();
		logValidItems.addItem("ShowDiningServiceIF", "reprintTicket", false);
		validateLogs(reprint, logValidItems, 10000);
	}
}