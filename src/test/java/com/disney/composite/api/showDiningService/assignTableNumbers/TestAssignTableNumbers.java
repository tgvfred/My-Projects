package com.disney.composite.api.showDiningService.assignTableNumbers;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import com.disney.api.soapServices.showDiningService.operations.AssignTableNumbers;
import com.disney.api.soapServices.showDiningService.operations.Book;
import com.disney.api.soapServices.showDiningService.operations.Cancel;
import com.disney.composite.BaseTest;
import com.disney.utils.Randomness;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.database.LogItems;
import com.disney.utils.dataFactory.staging.bookSEReservation.ScheduledEventReservation;

public class TestAssignTableNumbers extends BaseTest{
	private String tableNumber = String.valueOf(Randomness.randomNumberBetween(1, 99));
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
	public void testAssignedTableNumber() {
		TestReporter.logStep("Book a show dining reservation.");
		book = new Book(environment, ScheduledEventReservation.ONECOMPONENTSNOADDONS);
		book.setParty(hh);
		book.sendRequest();
		TestReporter.logAPI(!book.getResponseStatusCode().equals("200"), "An error occurred during booking", book);
		
		TestReporter.logStep("Assign Table Number");
		AssignTableNumbers assign = new AssignTableNumbers(environment, "Main");
		assign.setPartySize(String.valueOf(hh.getAllGuests().size()));
		assign.setTravelPlanSegmentId(book.getTravelPlanSegmentId());
		assign.setTableNumber(tableNumber);
		assign.sendRequest();
		TestReporter.logAPI(!assign.getResponseStatusCode().equals("200"), "An error occurred while assigning table numbers to the reservation.", assign);
		TestReporter.assertEquals(assign.getStatus(), "SUCCESS", "The status ["+assign.getStatus()+"] was not 'SUCCESS' as expected.");
		
		LogItems logValidItems = new LogItems();
		logValidItems.addItem("ShowDiningServiceIF", "assignTableNumbers", true);
		logValidItems.addItem("UpdateInventory", "updateInventory", false);
		logValidItems.addItem("AccommodationInventoryRequestComponentServiceIF", "updateInventoryForScheduledEvents", false);
		logValidItems.addItem("AccommodationInventoryRequestComponentServiceIF", "retrieveAssignmentOwner", false);
		logValidItems.addItem("PartyIF", "retrieveParty", false);
		validateLogs(assign, logValidItems);
	}
}
