package com.disney.composite.api.diningModule.scheduledEventsServicePort._compensationFlow.optimizeInventory;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import com.disney.api.soapServices.diningModule.scheduledEventsServicePort.operations.OptimizeInventory;
import com.disney.api.soapServices.diningModule.showDiningService.operations.Retrieve;
import com.disney.composite.BaseTest;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.database.Database;
import com.disney.utils.dataFactory.database.Recordset;
import com.disney.utils.dataFactory.database.databaseImpl.OracleDatabase;
import com.disney.utils.dataFactory.database.sqlStorage.AvailSE;
import com.disney.utils.dataFactory.database.sqlStorage.Dreams;
import com.disney.utils.dataFactory.staging.bookSEReservation.EventDiningReservation;
import com.disney.utils.dataFactory.staging.bookSEReservation.ScheduledEventReservation;

public class TestCompensationFlow_OptimizeInventory_Positive extends BaseTest{
	private ScheduledEventReservation res;
	private String inventoryGot;
	private String inventoryWant;
	private Recordset rsBaseInfo;
	private Recordset rsResourceId;
	private Database availDb;
	private Recordset rsInventory;
	private Database db;
	private String startDateTime;
	
	@AfterMethod(alwaysRun=true)
	public void teardown(){
		try{
			res.cancel();
		}catch(Exception e){}
	}
	
	@Test(groups = {"api", "regression", "dining", "scheduledEventsServicePort", "compensation"})
	public void testCompensationFlow_OptimizeInventory_Positive(){
		preReq();
		OptimizeInventory optimize = new OptimizeInventory(environment, "Main");
		optimize.setTravelPlanSegmentId(res.getConfirmationNumber());
		optimize.setPartySize(String.valueOf(hh.getAllGuests().size()));
		optimize.setPartymIxAdultCount(hh.numberOfAdults());
		optimize.setPartyMixChildCount(hh.numberOfChildren());
		optimize.setPartyMixInfantCount(hh.numberOfInfants());
		optimize.setInventoryGot(inventoryGot);
		optimize.setInventoryWant(inventoryWant);
		optimize.setFreezeId(inventoryWant, startDateTime);
		optimize.sendRequest(inventoryWant, startDateTime, inventoryGot);
		TestReporter.logAPI(!optimize.getResponseStatusCode().equals("200"), "An error occurred optimizing inventory: " + optimize.getFaultString(), optimize);
		TestReporter.assertTrue(optimize.isSuccessful(), "Verify that the invetory optimization was successful.");
		
		if(inventoryGot.equals(inventoryWant)){
			TestReporter.assertTrue(Integer.parseInt(optimize.getExistingInventoryCountAfter()) == Integer.parseInt(optimize.getExistingInventoryCountBefore()), 
					"Verify the booked inventory count ["+optimize.getExistingInventoryCountAfter()+"] for the old reservable reservation ID ["+inventoryGot+"] equals the previous value ["+optimize.getExistingInventoryCountBefore()+"].");
		}else{
			TestReporter.assertTrue(Integer.parseInt(optimize.getInventoryCountAfter()) > Integer.parseInt(optimize.getInventoryCountBefore()), 
					"Verify the booked inventory count ["+optimize.getInventoryCountAfter()+"] for the new reservable reservation ID ["+inventoryWant+"] is incremented from the previous value ["+optimize.getInventoryCountBefore()+"].");
			TestReporter.assertTrue(Integer.parseInt(optimize.getExistingInventoryCountAfter()) < Integer.parseInt(optimize.getExistingInventoryCountBefore()), 
					"Verify the booked inventory count ["+optimize.getExistingInventoryCountAfter()+"] for the old reservable reservation ID ["+inventoryGot+"] is decremented from the previous value ["+optimize.getExistingInventoryCountBefore()+"].");
		}
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
		if(tcgTypeFound){
			if(!startDateTime.substring(startDateTime.lastIndexOf(":")).equals("00")){
				startDateTime = startDateTime.substring(0, startDateTime.length()-2);
				startDateTime = startDateTime + "00";
			}
		}
		return tcgTypeFound;
	}
}