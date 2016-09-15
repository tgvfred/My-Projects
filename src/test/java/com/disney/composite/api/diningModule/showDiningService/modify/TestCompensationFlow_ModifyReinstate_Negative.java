package com.disney.composite.api.diningModule.showDiningService.modify;

import org.testng.SkipException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.BaseTest;
import com.disney.api.soapServices.applicationError.DiningErrorCode;
import com.disney.api.soapServices.core.BaseSoapCommands;
import com.disney.api.soapServices.diningModule.showDiningService.operations.Book;
import com.disney.api.soapServices.diningModule.showDiningService.operations.Cancel;
import com.disney.api.soapServices.diningModule.showDiningService.operations.Modify;
import com.disney.test.utils.Randomness;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.database.Database;
import com.disney.utils.dataFactory.database.Recordset;
import com.disney.utils.dataFactory.database.databaseImpl.OracleDatabase;
import com.disney.utils.dataFactory.database.sqlStorage.Dreams;
import com.disney.utils.dataFactory.guestFactory.HouseHold;
import com.disney.utils.dataFactory.staging.bookSEReservation.ScheduledEventReservation;

public class TestCompensationFlow_ModifyReinstate_Negative extends BaseTest{
	private ThreadLocal<Book> book = new ThreadLocal<Book>();
	protected String startDate;
	protected String startTime;
	
	@Override
	@BeforeMethod(alwaysRun = true)
	@Parameters("environment")
	public void setup(@Optional String environment){
		this.environment = environment;
		hh = new HouseHold(1);
		book.set(new Book(environment, ScheduledEventReservation.ONECOMPONENTSNOADDONS));
		book.get().setParty(hh);
		book.get().sendRequest();
		TestReporter.logAPI(!book.get().getResponseStatusCode().equals("200"), "An error occurred during booking: " + book.get().getFaultString(), book.get());
		
		Cancel cancel = new Cancel(environment, "CancelDiningEvent");
		cancel.setTravelPlanSegmentId(book.get().getTravelPlanSegmentId());
		cancel.sendRequest();
		TestReporter.logAPI(!cancel.getResponseStatusCode().equals("200"), "An error occurred during cancelling: " + cancel.getFaultString(), cancel);
	}
	
	@AfterMethod(alwaysRun=true)
	public void teardown(){
		try{
			Cancel cancel = new Cancel(environment, "CancelDiningEvent");
			cancel.setTravelPlanSegmentId(book.get().getTravelPlanSegmentId());
			cancel.sendRequest();
		}catch(Exception e){}
	}

	@Test(groups = {"api", "regression", "dining", "showDiningService", "compensation", "negative"})
	public void TestCompensationFlow_ModifyReinstate_Negative_RIMFail(){
		Modify modify = new Modify(environment, ScheduledEventReservation.ONECOMPONENTSNOADDONS);
		modify.setTravelPlanId(book.get().getTravelPlanId());
		modify.setReservationNumber(book.get().getTravelPlanSegmentId());
		modify.setParty(hh);
		modify.setReservableResourceId(book.get().getReservableResourceId(), true);
		modify.setServiceStartDate(Randomness.generateCurrentXMLDate(30));
		modify.setExistingRRID(book.get().getReservableResourceId());
		modify.setExistingStartDateTime(book.get().getStartTime());
		modify.setFreezeIdForError(Randomness.randomAlphaNumeric(36));
		modify.setValidateInventory(true);
		modify.sendRequest();
		TestReporter.logAPI(!modify.getResponse().contains("RELEASE INVENTORY REQUEST IS INVALID"), modify.getFaultString() ,modify);
		TestReporter.assertTrue(Integer.parseInt(modify.getInventoryCountBefore()) == Integer.parseInt(modify.getInventoryCountAfter()), "Verify the booked inventory count ["+modify.getInventoryCountAfter()+"] for reservable resource ID ["+modify.getReservableResourceId()+"] does not increment from the count prior to booking ["+modify.getInventoryCountBefore()+"]");
		Database db = new OracleDatabase(environment, "Dreams");
		Recordset rs = new Recordset(db.getResultSet(Dreams.getReservationInfoByTpsId(book.get().getTravelPlanSegmentId())));
		TestReporter.assertEquals(rs.getValue("TPS_TRAVEL_STATUS"), "Cancelled", "Verify that the travel plan segment status ["+rs.getValue("TPS_TRAVEL_STATUS")+"] is [Cancelled] as expected.");
	}

}