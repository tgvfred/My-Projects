package com.disney.composite.api.showDiningService.modify;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import com.disney.api.soapServices.showDiningService.operations.Book;
import com.disney.api.soapServices.showDiningService.operations.Cancel;
import com.disney.api.soapServices.showDiningService.operations.Modify;
import com.disney.composite.BaseTest;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.guestFactory.HouseHold;
import com.disney.utils.dataFactory.staging.bookSEReservation.ScheduledEventReservation;

/**
 * This class contains all fields, methods and classes required to test
 * 
 * @author Venkatesh A
 * @version 1/11/2016 Venkatesh A - Original
 */

public class TestModify extends BaseTest{
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
	public void testModify() {
		TestReporter.logStep("Book a show dining reservation.");
		book = new Book(environment, ScheduledEventReservation.ONECOMPONENTSNOADDONS);
		book.setParty(hh);
		book.sendRequest();
		TestReporter.logAPI(!book.getResponseStatusCode().equals("200"), "An error occurred during booking", book);
		
		TestReporter.logScenario("Modify an existing reservation.");
		hh = new HouseHold(hh.getAllGuests().size() + 1);
		Modify modify = new Modify(environment, ScheduledEventReservation.ONECOMPONENTSNOADDONS);
		modify.setTravelPlanId(book.getTravelPlanId());
		modify.setReservationnumber(book.getTravelPlanSegmentId());
		modify.setParty(hh);
		modify.sendRequest();
		TestReporter.logAPI(!modify.getResponseStatusCode().equals("200"), "An error occurred during modification", modify);
		TestReporter.assertEquals(modify.getResponseStatus(), "SUCCESS", "The status ["+modify.getResponseStatus()+"] was not 'SUCCESS' as expected.");
	}
}
