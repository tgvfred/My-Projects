package com.disney.composite.api.diningModule.eventDiningService.modify;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.BaseTest;
import com.disney.api.soapServices.diningModule.eventDiningService.operations.Book;
import com.disney.api.soapServices.diningModule.eventDiningService.operations.Cancel;
import com.disney.api.soapServices.diningModule.eventDiningService.operations.Modify;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.database.Database;
import com.disney.utils.dataFactory.database.Recordset;
import com.disney.utils.dataFactory.database.databaseImpl.OracleDatabase;
import com.disney.utils.dataFactory.database.sqlStorage.Dreams;
import com.disney.utils.dataFactory.guestFactory.HouseHold;
import com.disney.utils.dataFactory.staging.bookSEReservation.ScheduledEventReservation;

public class TestCompensationFlow_ModifyReinstate_Positive extends BaseTest{
	private Book book;
	protected String startDate;
	protected String startTime;
	
	@Override
	@BeforeMethod(alwaysRun = true)
	@Parameters("environment")
	public void setup(@Optional String environment){
		this.environment = environment;
		hh = new HouseHold(1);
		book = new Book(environment, ScheduledEventReservation.NOCOMPONENTSNOADDONS);
		book.setParty(hh);
		book.setFacilityId("90001833");
		book.addDetailsByProductName("Spirit of Aloha-Cat 2-1st Show");
		book.setValidateInventory(true);
		book.sendRequest();
		TestReporter.logAPI(!book.getResponseStatusCode().equals("200"), "An error occurred during booking: " + book.getFaultString(), book);
		
		Cancel cancel = new Cancel(environment, "CancelDiningEvent");
		cancel.setReservationNumber(book.getTravelPlanSegmentId());
		cancel.sendRequest();
		TestReporter.logAPI(!cancel.getResponseStatusCode().equals("200"), "An error occurred during cancelling: " + cancel.getFaultString(), cancel);
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
	public void testCompensationFlow_ModifyReinstate_Positive(){
		Modify modify = new Modify(environment, ScheduledEventReservation.NOCOMPONENTSNOADDONS);
		modify.setTravelPlanId(book.getTravelPlanId());
		modify.setReservationNumber(book.getTravelPlanSegmentId());
		modify.setParty(hh);
		modify.setReservableResourceId(book.getReservableResourceId(), true);
		modify.setServiceStartDate(book.getRequestServiceStartDate());
		modify.setExistingRRID(book.getReservableResourceId());
		modify.setExistingStartDateTime(book.getRequestServiceStartDate());
		modify.setFacilityId("90001833");
		modify.addDetailsByProductName("Spirit of Aloha-Cat 2-1st Show");
		modify.setValidateInventory(true);
		modify.sendRequest();
		TestReporter.logAPI(!modify.getResponseStatusCode().equals("200"), "An error occurred modifying reservation ["+book.getTravelPlanSegmentId()+"]:" + modify.getFaultString(), modify);
		TestReporter.assertEquals(modify.getResponseStatus(), "SUCCESS", "Verify that the modification status ["+modify.getResponseStatus()+"] is [SUCCESS].");
		TestReporter.assertTrue(Integer.parseInt(modify.getInventoryCountBefore()) < Integer.parseInt(modify.getInventoryCountAfter()), "Verify the booked inventory count ["+modify.getInventoryCountBefore()+"] increments from the value prior to modifying ["+modify.getInventoryCountAfter()+"].");
		Database db = new OracleDatabase(environment, "Dreams");
		Recordset rs = new Recordset(db.getResultSet(Dreams.getReservationInfoByTpsId(book.getTravelPlanSegmentId())));
		TestReporter.assertEquals(rs.getValue("TPS_TRAVEL_STATUS"), "Booked", "Verify that the travel plan segment status ["+rs.getValue("TPS_TRAVEL_STATUS")+"] is [Booked] as expected.");
	}
}