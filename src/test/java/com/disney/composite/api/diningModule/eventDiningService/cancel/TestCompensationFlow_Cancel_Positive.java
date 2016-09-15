package com.disney.composite.api.diningModule.eventDiningService.cancel;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.BaseTest;
import com.disney.api.soapServices.diningModule.eventDiningService.operations.Book;
import com.disney.api.soapServices.diningModule.eventDiningService.operations.Cancel;
import com.disney.utils.Regex;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.database.Database;
import com.disney.utils.dataFactory.database.Recordset;
import com.disney.utils.dataFactory.database.databaseImpl.OracleDatabase;
import com.disney.utils.dataFactory.database.sqlStorage.Dreams;
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
		book.setFacilityId("90001833");
		book.addDetailsByProductName("Spirit of Aloha-Cat 1-2nd Show");
		book.setValidateInventory(true);
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
		
		Database db = new OracleDatabase(environment, "Dreams");
		Recordset rs = new Recordset(db.getResultSet(Dreams.getReservationInfoByTpsId(book.getTravelPlanSegmentId())));
		TestReporter.assertEquals(rs.getValue("TPS_TRAVEL_STATUS"), "Cancelled", "Verify that the travel plan segment status ["+rs.getValue("TPS_TRAVEL_STATUS")+"] is [Cancelled] as expected.");
	}
}