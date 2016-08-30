package com.disney.composite.api.diningModule.eventDiningService._compensationFlow.modify;

import org.testng.SkipException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.applicationError.DiningErrorCode;
import com.disney.api.soapServices.diningModule.eventDiningService.operations.Book;
import com.disney.api.soapServices.diningModule.eventDiningService.operations.Cancel;
import com.disney.api.soapServices.diningModule.eventDiningService.operations.Modify;
import com.disney.composite.BaseTest;
import com.disney.utils.Randomness;
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
		book.set(new Book(environment, ScheduledEventReservation.NOCOMPONENTSNOADDONS));
		book.get().setParty(hh);
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

	@Test(groups = {"api", "regression", "dining", "eventDiningService", "compensation"})
	public void TestCompensationFlow_ModifyReinstate_Negative_RIMFail(){
		Modify modify = new Modify(environment, ScheduledEventReservation.NOCOMPONENTSNOADDONS);
		modify.setTravelPlanId(book.get().getTravelPlanId());
		modify.setReservationNumber(book.get().getTravelPlanSegmentId());
		modify.setParty(hh);
		modify.setReservableResourceId(book.get().getReservableResourceId(), true);
		modify.setServiceStartDate(book.get().getRequestServiceStartDate());
		modify.setExistingRRID(book.get().getReservableResourceId());
		modify.setExistingStartDateTime(book.get().getRequestServiceStartDate());
		modify.setFreezeIdForError(Randomness.randomAlphaNumeric(36));
		modify.sendRequest();
		TestReporter.logAPI(!modify.getResponse().contains("RELEASE INVENTORY REQUEST IS INVALID"), "An error occurred modifying reservation ["+book.get().getTravelPlanSegmentId()+"]:" + modify.getFaultString(), modify);
		validateApplicationError(modify, DiningErrorCode.INVENTORY_MANAGEMENT_SERVICE_FAILURE);
		TestReporter.assertTrue(Integer.parseInt(modify.getInventoryCountBefore()) == Integer.parseInt(modify.getInventoryCountAfter()), "Verify the booked inventory count ["+modify.getInventoryCountBefore()+"] increments from the value prior to modifying ["+modify.getInventoryCountAfter()+"].");
		TestReporter.assertTrue(Integer.parseInt(modify.getExistingInventoryCountBefore()) == Integer.parseInt(modify.getExistingInventoryCountAfter()), "Verify the existing booked inventory count ["+modify.getExistingInventoryCountBefore()+"] remains the same as the value prior to modifying ["+modify.getExistingInventoryCountAfter()+"].");
		Database db = new OracleDatabase(environment, "Dreams");
		Recordset rs = new Recordset(db.getResultSet(Dreams.getReservationInfoByTpsId(book.get().getTravelPlanSegmentId())));
		TestReporter.assertEquals(rs.getValue("TPS_TRAVEL_STATUS"), "Cancelled", "Verify that the travel plan segment status ["+rs.getValue("TPS_TRAVEL_STATUS")+"] is [Cancelled] as expected.");
	}

	@Test(groups = {"api", "regression", "dining", "eventDiningService", "compensation"})
	public void TestCompensationFlow_ModifyReinstate_Negative_DineFail(){
		throw new SkipException("The testing solution for this scenario has not been determined.");
	}

	@Test(groups = {"api", "regression", "dining", "eventDiningService", "compensation"})
	public void TestCompensationFlow_ModifyReinstate_Negative_FolioFail(){
		throw new SkipException("The testing solution for this scenario has not been determined.");
//		Modify modify = new Modify(environment, ScheduledEventReservation.NOCOMPONENTSNOADDONS);
//		modify.setTravelPlanId(book.get().getTravelPlanId());
//		modify.setReservationNumber(book.get().getTravelPlanSegmentId());
//		modify.setParty(hh);
//		modify.setReservableResourceId(book.get().getReservableResourceId(), true);
//		modify.setServiceStartDate(book.get().getRequestServiceStartDate());
//		modify.setExistingRRID(book.get().getReservableResourceId());
//		modify.setExistingStartDateTime(book.get().getStartTime());
//		modify.setFacilityId("90002032");
//		modify.addDetailsByProductName("Hoop-Dee-Doo-Cat 2-1st Show");
//		
//		int taxesNodes = XMLTools.getNodeList(XMLTools.loadXML(modify.getRequest()), "/Envelope/Body/modify/modifyEventDiningRequest/eventDiningPackage/componentPrices/unitPrices/taxes/revenueType").getLength();
//		for(int i = 1; i <= taxesNodes; i++){
//			try{
//				modify.setRequestNodeValueByXPath("/Envelope/Body/modify/modifyEventDiningRequest/eventDiningPackage/componentPrices["+String.valueOf(i)+"]/unitPrices/baseCharge", BaseSoapCommands.REMOVE_NODE.toString());
//			}catch(XPathNotFoundException e){
//				System.out.println();
//			}
//		}
//		
//		
//		modify.sendRequest();
//		TestReporter.logAPI(!modify.getResponse().contains("Invalid input fields"), "An error occurred modifying reservation ["+book.get().getTravelPlanSegmentId()+"]:" + modify.getFaultString(), modify);
//		validateApplicationError(modify, DiningErrorCode.FOLIO_MANAGEMENT_SERVICE_FAILURE);
//		TestReporter.assertTrue(Integer.parseInt(modify.getInventoryCountBefore()) == Integer.parseInt(modify.getInventoryCountAfter()), "Verify the existing booked inventory count ["+modify.getInventoryCountBefore()+"] remains the same as the value prior to modifying ["+modify.getInventoryCountAfter()+"].");
//		TestReporter.assertTrue(Integer.parseInt(modify.getExistingInventoryCountBefore()) == Integer.parseInt(modify.getExistingInventoryCountAfter()), "Verify the existing booked inventory count ["+modify.getExistingInventoryCountBefore()+"] remains the same as the value prior to modifying ["+modify.getExistingInventoryCountAfter()+"].");
//		Database db = new OracleDatabase(environment, "Dreams");
//		Recordset rs = new Recordset(db.getResultSet(Dreams.getReservationInfoByTpsId(book.get().getTravelPlanSegmentId())));
//		TestReporter.assertEquals(rs.getValue("TPS_TRAVEL_STATUS"), "Booked", "Verify that the travel plan segment status ["+rs.getValue("TPS_TRAVEL_STATUS")+"] is [Booked] as expected.");
	}
}