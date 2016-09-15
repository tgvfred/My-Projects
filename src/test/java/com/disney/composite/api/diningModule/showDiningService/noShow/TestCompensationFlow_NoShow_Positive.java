package com.disney.composite.api.diningModule.showDiningService.noShow;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.BaseTest;
import com.disney.api.soapServices.diningModule.showDiningService.operations.Book;
import com.disney.api.soapServices.diningModule.showDiningService.operations.Cancel;
import com.disney.api.soapServices.diningModule.showDiningService.operations.NoShow;
import com.disney.utils.Regex;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.database.Database;
import com.disney.utils.dataFactory.database.Recordset;
import com.disney.utils.dataFactory.database.databaseImpl.OracleDatabase;
import com.disney.utils.dataFactory.database.sqlStorage.Dreams;
import com.disney.utils.dataFactory.guestFactory.HouseHold;
import com.disney.utils.dataFactory.staging.bookSEReservation.ScheduledEventReservation;

public class TestCompensationFlow_NoShow_Positive extends BaseTest{
	private Book book;
	private String reservableResourceId;
	private String dateTime;
	
	@Override
	@BeforeMethod(alwaysRun = true)
	@Parameters("environment")
	public void setup(@Optional String environment){
		this.environment = environment;
		hh = new HouseHold(1);
		book = new Book(environment, ScheduledEventReservation.ONECOMPONENTSNOADDONS);
		book.setParty(hh);
		book.sendRequest();
		TestReporter.logAPI(!book.getResponseStatusCode().equals("200"), "An error occurred during booking: " + book.getFaultString(), book);
		reservableResourceId = book.getReservableResourceId();
		dateTime = book.getDateTime();
	}
	
	@AfterMethod(alwaysRun=true)
	public void teardown(){
		try{
			Cancel cancel = new Cancel(environment, "CancelDiningEvent");
			cancel.setTravelPlanSegmentId(book.getTravelPlanSegmentId());
			cancel.sendRequest();
		}catch(Exception e){}
	}

	@Test(groups = {"api", "regression", "dining", "showDiningService", "compensation"})
	public void testCompensationFlow_NoShow_Positive(){
		NoShow noShow = new NoShow(environment, "ContactCenter");
		noShow.setReservationNumber(book.getTravelPlanSegmentId());
		noShow.sendRequest(reservableResourceId, dateTime);
		TestReporter.logAPI(!noShow.getResponseStatusCode().equals("200"), "An error occurred setting the reservation to 'NoShow'", noShow);
		TestReporter.assertTrue(Regex.match("[0-9]+", noShow.getCancellationConfirmationNumber()), "Verify the concellation number ["+noShow.getCancellationConfirmationNumber()+"] is numeric.");
		TestReporter.assertTrue(Integer.parseInt(noShow.getInventoryCountAfter()) < Integer.parseInt(noShow.getInventoryCountBefore()), "Verify the booked inventory count ["+noShow.getInventoryCountAfter()+"] decrements from the previous value ["+noShow.getInventoryCountBefore()+"].");
		
		Database db = new OracleDatabase(environment, "Dreams");
		Recordset rs = new Recordset(db.getResultSet(Dreams.getReservationInfoByTpsId(book.getTravelPlanSegmentId())));
		TestReporter.assertEquals(rs.getValue("TPS_TRAVEL_STATUS"), "No Show", "Verify that the travel plan segment status ["+rs.getValue("TPS_TRAVEL_STATUS")+"] is [No Show] as expected.");
	}
}
