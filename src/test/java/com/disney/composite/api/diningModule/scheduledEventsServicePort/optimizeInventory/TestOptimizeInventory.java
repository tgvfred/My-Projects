package com.disney.composite.api.diningModule.scheduledEventsServicePort.optimizeInventory;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.diningModule.scheduledEventsServicePort.operations.OptimizeInventory;
import com.disney.composite.BaseTest;
import com.disney.utils.Randomness;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.database.Database;
import com.disney.utils.dataFactory.database.LogItems;
import com.disney.utils.dataFactory.database.Recordset;
import com.disney.utils.dataFactory.database.databaseImpl.OracleDatabase;
import com.disney.utils.dataFactory.database.sqlStorage.AvailSE;
import com.disney.utils.dataFactory.database.sqlStorage.Dreams;
import com.disney.utils.dataFactory.guestFactory.HouseHold;
import com.disney.utils.dataFactory.staging.bookSEReservation.EventDiningReservation;
import com.disney.utils.dataFactory.staging.bookSEReservation.ScheduledEventReservation;

public class TestOptimizeInventory extends BaseTest{
	private ScheduledEventReservation res;
	private String inventoryGot;
	private String inventoryWant;
	private Recordset rsBaseInfo;
	private Recordset rsResourceId;
	private Database availDb;
	private Recordset rsInventory;
	private String date = Randomness.generateCurrentXMLDate(1);
	private Database db;
	
//	@Override
//	@BeforeMethod(alwaysRun = true)
//	@Parameters("environment")
//	public void setup(@Optional String environment){
//		this.environment = environment;
//		hh = new HouseHold(1);
//		res = new EventDiningReservation(environment, hh);		
//		res.book(ScheduledEventReservation.NOCOMPONENTSNOADDONS);
//		
//		// Retrieve the 'inventoryGot' value 
//		db = new OracleDatabase(environment, Database.DREAMS);
//		rsBaseInfo = new Recordset(db.getResultSet(Dreams.getReservationInfoByTpsId(res.getConfirmationNumber()) + " AND PROD_TYP_NM = 'RESERVABLE_RESOURCE_COMPONENT'"));
//		rsResourceId= new Recordset(db.getResultSet(Dreams.getTcReservableResourceID(rsBaseInfo.getValue("TC_ID"))));
//		inventoryGot = rsResourceId.getValue("RSRC_INVTRY_TYP_CD");
//		
//		// Retrieve the 'inventoryWant' value
//		availDb = new OracleDatabase(environment, Database.AVAIL_SE);
//		rsInventory = new Recordset(availDb.getResultSet(AvailSE.getReservableResourceByFacilityAndDate(res.getFacilityId(), date)));
//		inventoryWant = rsInventory.getValue("Resource_ID");
//	}
	
	@AfterMethod(alwaysRun=true)
	public void teardown(){
		try{
			res.cancel();
		}catch(Exception e){}
	}
	
	@Test(groups = {"api", "regression", "dining", "scheduledEventsServicePort"})
	public void testOptimizeInventory(){
		preReq();
		OptimizeInventory optimize = new OptimizeInventory(environment, "Main");
		optimize.setTravelPlanSegmentId(res.getConfirmationNumber());
		optimize.setPartySize(String.valueOf(hh.getAllGuests().size()));
		optimize.setPartymIxAdultCount(hh.numberOfAdults());
		optimize.setPartyMixChildCount(hh.numberOfChildren());
		optimize.setPartyMixInfantCount(hh.numberOfInfants());
		optimize.setInventoryGot(inventoryGot);
		optimize.setInventoryWant(inventoryWant);		
		optimize.sendRequest();
		TestReporter.logAPI(!optimize.getResponseStatusCode().equals("200"), "An error occurred optimizing inventory: " + optimize.getFaultString(), optimize);
		TestReporter.assertTrue(optimize.isSuccessful(), "Verify that the invetory optimization was successful.");

		LogItems logValidItems = new LogItems();
		logValidItems.addItem("PartyIF", "retrieveParty", false);
		logValidItems.addItem("AccommodationInventoryRequestComponentServiceIF", "updateInventoryForScheduledEvents", false);
		logValidItems.addItem("AccommodationInventoryRequestComponentServiceIF", "retrieveAssignmentOwner", false);
		logValidItems.addItem("ScheduledEventsServiceIF", "optimizeInventory", false);
		logValidItems.addItem("UpdateInventory", "updateInventory", false);
		validateLogs(optimize, logValidItems);
	}
	
	private void preReq(){
		res = new EventDiningReservation(environment, hh);		
		res.book(ScheduledEventReservation.NOCOMPONENTSNOADDONS);
		
		// Retrieve the 'inventoryGot' value 
		db = new OracleDatabase(environment, Database.DREAMS);
		rsBaseInfo = new Recordset(db.getResultSet(Dreams.getReservationInfoByTpsId(res.getConfirmationNumber()) + " AND PROD_TYP_NM = 'RESERVABLE_RESOURCE_COMPONENT'"));
		rsResourceId= new Recordset(db.getResultSet(Dreams.getTcReservableResourceID(rsBaseInfo.getValue("TC_ID"))));
		inventoryGot = rsResourceId.getValue("RSRC_INVTRY_TYP_CD");
		
		// Retrieve the 'inventoryWant' value
		availDb = new OracleDatabase(environment, Database.AVAIL_SE);
		rsInventory = new Recordset(availDb.getResultSet(AvailSE.getReservableResourceByFacilityAndDate(res.getFacilityId(), date)));
		inventoryWant = rsInventory.getValue("Resource_ID");
	}
}