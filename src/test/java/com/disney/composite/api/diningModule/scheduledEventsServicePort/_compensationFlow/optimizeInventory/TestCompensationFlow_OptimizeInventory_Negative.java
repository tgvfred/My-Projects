package com.disney.composite.api.diningModule.scheduledEventsServicePort._compensationFlow.optimizeInventory;

import org.testng.SkipException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import com.disney.api.soapServices.diningModule.showDiningService.operations.Retrieve;
import com.disney.composite.BaseTest;
import com.disney.utils.Randomness;
import com.disney.utils.dataFactory.database.Database;
import com.disney.utils.dataFactory.database.Recordset;
import com.disney.utils.dataFactory.database.databaseImpl.OracleDatabase;
import com.disney.utils.dataFactory.database.sqlStorage.AvailSE;
import com.disney.utils.dataFactory.database.sqlStorage.Dreams;
import com.disney.utils.dataFactory.staging.bookSEReservation.EventDiningReservation;
import com.disney.utils.dataFactory.staging.bookSEReservation.ScheduledEventReservation;

public class TestCompensationFlow_OptimizeInventory_Negative extends BaseTest{
	private ScheduledEventReservation res;
	private String inventoryGot;
	private String inventoryWant;
	private Recordset rsBaseInfo;
	private Recordset rsResourceId;
	private Database availDb;
	private Recordset rsInventory;
	private String date = Randomness.generateCurrentXMLDate(1);
	private Database db;
	private String startDateTime;
	
	@AfterMethod(alwaysRun=true)
	public void teardown(){
		try{
			res.cancel();
		}catch(Exception e){}
	}
	@Test(groups = {"api", "regression", "dining", "scheduledEventsServicePort", "negative", "compensation"})
	public void TestCompensationFlow_OptimizeInventory_Negative_RIMFail(){
//		TestReporter.setDebugLevel(1);
//		preReq();
		throw new SkipException("The testing solution for this scenario has not been determined.");
	}
	@Test(groups = {"api", "regression", "dining", "scheduledEventsServicePort", "negative", "compensation"})
	public void TestCompensationFlow_OptimizeInventory_Negative_DineFail(){
//		TestReporter.setDebugLevel(1);
//		preReq();
		throw new SkipException("The testing solution for this scenario has not been determined.");
	}
	@Test(groups = {"api", "regression", "dining", "scheduledEventsServicePort", "negative", "compensation"})
	public void TestCompensationFlow_OptimizeInventory_Negative_FolioFail(){
//		TestReporter.setDebugLevel(1);
//		preReq();
		throw new SkipException("The testing solution for this scenario has not been determined.");
	}
	
	private void preReq(){
		res = new EventDiningReservation(environment, hh);		
		res.book(ScheduledEventReservation.NOCOMPONENTSNOADDONS);
		startDateTime(retrieveTcgType());
		
		// Retrieve the 'inventoryGot' value 
		db = new OracleDatabase(environment, Database.DREAMS);
		rsBaseInfo = new Recordset(db.getResultSet(Dreams.getReservationInfoByTpsId(res.getConfirmationNumber()) + " AND PROD_TYP_NM = 'RESERVABLE_RESOURCE_COMPONENT'"));
		rsResourceId= new Recordset(db.getResultSet(Dreams.getTcReservableResourceID(rsBaseInfo.getValue("TC_ID"))));
		inventoryGot = rsResourceId.getValue("RSRC_INVTRY_TYP_CD");
		
		// Retrieve the 'inventoryWant' value
		availDb = new OracleDatabase(environment, Database.AVAIL_SE);
		rsInventory = new Recordset(availDb.getResultSet(AvailSE.getReservableResourceByFacilityAndDateTime(res.getFacilityId(), startDateTime)));
		inventoryWant = rsInventory.getValue("Resource_ID");
	}
	
	private String retrieveTcgType(){
		Database db = new OracleDatabase(environment, Database.DREAMS);
		Recordset rs = new Recordset(db.getResultSet(Dreams.getReservationInfoByTpsId(res.getConfirmationNumber())));
		return rs.getValue("TC_GRP_TYP_NM");
	}
	
	private boolean startDateTime(String tcgType){
		boolean tcgTypeFound = true;
		
		switch (tcgType.toUpperCase()) {
		case "SHOWDINING":
			Retrieve retrieveShow = new Retrieve(environment, "RetrieveDiningEvent"); 
			retrieveShow.setReservationNumber(res.getConfirmationNumber());
			retrieveShow.sendRequest();
			tcgTypeFound = !retrieveShow.getServiceStartDate().contains("T00:00:00");
			if(tcgTypeFound) startDateTime = retrieveShow.getServiceStartDate().replace("T", " ");
			break;
		case "TABLESERVICEDINING":
			com.disney.api.soapServices.diningModule.tableServiceDiningServicePort.operations.Retrieve retrieveTable = new com.disney.api.soapServices.diningModule.tableServiceDiningServicePort.operations.Retrieve(environment, "Main");
			retrieveTable.setReservationNumber(res.getConfirmationNumber());
			retrieveTable.sendRequest();
			tcgTypeFound = !retrieveTable.getServiceStartDate().contains("T00:00:00");
			if(tcgTypeFound) startDateTime = retrieveTable.getServiceStartDate().replace("T", " ");
			break;
		case "EVENTDINING":
			com.disney.api.soapServices.diningModule.eventDiningService.operations.Retrieve retrieveEvent = new com.disney.api.soapServices.diningModule.eventDiningService.operations.Retrieve(environment, "RetrieveDiningEvent");
			retrieveEvent.setReservationNumber(res.getConfirmationNumber());
			retrieveEvent.sendRequest();
			tcgTypeFound = !retrieveEvent.getServiceStartDate().contains("T00:00:00");
			if(tcgTypeFound) startDateTime = retrieveEvent.getServiceStartDate().replace("T", " ");
			break;
		default:
			tcgTypeFound = false;
			break;
		}
		return tcgTypeFound;
	}
}