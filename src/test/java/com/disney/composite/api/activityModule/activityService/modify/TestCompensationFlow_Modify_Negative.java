package com.disney.composite.api.activityModule.activityService.modify;

import org.testng.SkipException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.BaseTest;
import com.disney.api.soapServices.activityModule.activityServicePort.operations.Book;
import com.disney.api.soapServices.activityModule.activityServicePort.operations.Cancel;
import com.disney.api.soapServices.activityModule.activityServicePort.operations.Modify;
import com.disney.test.utils.Randomness;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.database.Database;
import com.disney.utils.dataFactory.database.Recordset;
import com.disney.utils.dataFactory.database.databaseImpl.OracleDatabase;
import com.disney.utils.dataFactory.database.sqlStorage.Dreams;
import com.disney.utils.dataFactory.guestFactory.HouseHold;
import com.disney.utils.dataFactory.staging.bookSEReservation.ScheduledEventReservation;

public class TestCompensationFlow_Modify_Negative extends BaseTest{
	private ThreadLocal<Book> book = new ThreadLocal<Book>();
	private String facilityId = "210507";
	
	@Override
	@BeforeMethod(alwaysRun = true)
	@Parameters("environment")
	public void setup(@Optional String environment){
		this.environment = environment;
		hh = new HouseHold(1);
		hh.primaryGuest().setAge("9");
		book.set(new Book(environment, ScheduledEventReservation.NOCOMPONENTSNOADDONS));
		book.get().setParty(hh);
		book.get().setFacilityId(facilityId);
		book.get().sendRequest();
		TestReporter.logAPI(!book.get().getResponseStatusCode().equals("200"), "An error occurred during booking: " + book.get().getFaultString(), book.get());
		
		Cancel cancel = new Cancel(environment, "CancelDiningEvent");
		cancel.setReservationNumber(book.get().getTravelPlanSegmentId());
		cancel.sendRequest();
		TestReporter.logAPI(!cancel.getResponseStatusCode().equals("200"), "An error occurred during cancelling: " + cancel.getFaultString(), cancel);
	}
	
	@AfterMethod(alwaysRun=true)
	public void teardown(){
		try{
			Cancel cancel = new Cancel(environment, "CancelDiningEvent");
			cancel.setReservationNumber(book.get().getTravelPlanSegmentId());
			cancel.sendRequest();
		}catch(Exception e){}
	}

	@Test(groups = {"api", "regression", "activity", "activityService", "negtive", "compensation"})
	public void TestCompensationFlow_Modify_Negative_RIMFail(){
		Modify modify = new Modify(environment, ScheduledEventReservation.NOCOMPONENTSNOADDONS);
		modify.setTravelPlanId(book.get().getTravelPlanId());
		modify.setReservationNumber(book.get().getTravelPlanSegmentId());
		modify.setParty(hh);
		modify.setFacilityId(facilityId);
		modify.setFreezeIdForError(Randomness.randomAlphaNumeric(36));
		modify.setReservableResourceId(book.get().getReservableResourceId(), true);
		modify.setServiceStartDate(Randomness.generateCurrentXMLDate(30));
		modify.setExistingRRID(book.get().getReservableResourceId());
		modify.setExistingStartDateTime(book.get().getStartTime());
		modify.sendRequest();
		TestReporter.logAPI(!modify.getResponse().contains("RELEASE INVENTORY REQUEST IS INVALID"), modify.getFaultString(), modify);
		TestReporter.assertTrue(Integer.parseInt(modify.getExistingInventoryCountBefore()) == Integer.parseInt(modify.getExistingInventoryCountAfter()), "Verify the existing booked inventory count ["+modify.getExistingInventoryCountBefore()+"] decrements from the value prior to modifying ["+modify.getExistingInventoryCountAfter()+"].");
		Database db = new OracleDatabase(environment, "Dreams");
		Recordset rs = new Recordset(db.getResultSet(Dreams.getReservationInfoByTpsId(book.get().getTravelPlanSegmentId())));
		TestReporter.assertEquals(rs.getValue("TPS_TRAVEL_STATUS"), "Cancelled", "Verify that the travel plan segment status ["+rs.getValue("TPS_TRAVEL_STATUS")+"] is [Cancelled] as expected.");
	}
}