package com.disney.composite.api.accommodationInventoryRequestComponentServicePort.updateInventoryForScheduledEvents;

import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationInventoryRequestComponentServicePort.operations.UpdateInventoryForScheduledEvents;
import com.disney.api.soapServices.core.BaseSoapCommands;
import com.disney.composite.BaseTest;
import com.disney.test.utils.Randomness;
import com.disney.test.utils.Sleeper;
import com.disney.utils.Regex;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.database.Database;
import com.disney.utils.dataFactory.database.LogItems;
import com.disney.utils.dataFactory.database.Recordset;
import com.disney.utils.dataFactory.database.databaseImpl.OracleDatabase;
import com.disney.utils.dataFactory.database.sqlStorage.AvailSE;
import com.disney.utils.dataFactory.database.sqlStorage.Dreams;
import com.disney.utils.dataFactory.staging.bookSEReservation.ActivityEventReservation;
import com.disney.utils.dataFactory.staging.bookSEReservation.EventDiningReservation;
import com.disney.utils.dataFactory.staging.bookSEReservation.ScheduledEventReservation;
import com.disney.utils.dataFactory.staging.bookSEReservation.ShowDiningReservation;
import com.disney.utils.dataFactory.staging.bookSEReservation.TableServiceDiningReservation;

public class TestUpdateInventoryForScheduledEvents extends BaseTest{
	// Defining global variables
	protected String TPS_ID = null;
	
	@Test(groups = {"api", "regression", "resourceInventory", "accommodationInventoryRequestComponentServicePort"})
	public void testUpdatingWithEventDiningRes(){
		ScheduledEventReservation res = new EventDiningReservation(environment, hh);
		res.book(ScheduledEventReservation.NOCOMPONENTSNOADDONS);
		update(res);
	}

	@Test(groups = {"api", "regression", "resourceInventory", "accommodationInventoryRequestComponentServicePort"})
	public void testUpdatingWithShowDiningRes(){
		ScheduledEventReservation res = new ShowDiningReservation(environment, hh);
		res.book(ScheduledEventReservation.ONECOMPONENTSNOADDONS);
		update(res);
	}
	
	@Test(groups = {"api", "regression", "resourceInventory", "accommodationInventoryRequestComponentServicePort"})
	public void testUpdatingWithTableServiceDiningRes(){
		ScheduledEventReservation res = new TableServiceDiningReservation(environment, hh);
		res.book(ScheduledEventReservation.NOCOMPONENTSNOADDONS);	
		update(res);
	}
	
	//@Test(groups = {"api", "regression", "resourceInventory", "accommodationInventoryRequestComponentServicePort"})
	public void testUpdatingWithActivityEventRes(){
		ScheduledEventReservation res = new ActivityEventReservation(environment, hh);
		res.book(ScheduledEventReservation.NOCOMPONENTSNOADDONS);
		update(res);
	}
	
	private UpdateInventoryForScheduledEvents update(ScheduledEventReservation res){

		TestReporter.setDebugLevel(1);
		Database db = new OracleDatabase(environment, Database.DREAMS);
		Recordset rsBaseInfo = new Recordset(db.getResultSet(Dreams.getReservationInfoByTpsId(res.getConfirmationNumber()) + " AND PROD_TYP_NM = 'RESERVABLE_RESOURCE_COMPONENT'"));
		Recordset rsResourceId= new Recordset(db.getResultSet(Dreams.getTcReservableResourceID(rsBaseInfo.getValue("TC_ID"))));
		
		Database availDb = new OracleDatabase(environment, Database.AVAIL_SE);
		Recordset rsInventory = new Recordset(availDb.getResultSet(AvailSE.getResourceAvailibleTimesByIdAndDate(rsResourceId.getValue("RSRC_INVTRY_TYP_CD"), res.getServiceStartDate())));
		if (rsInventory.getRowCount() == 0) {
			rsInventory = new Recordset(availDb.getResultSet(AvailSE.getResourceAvailibleTimesById(rsResourceId.getValue("RSRC_INVTRY_TYP_CD"))));
		}
		
		String tcId = rsBaseInfo.getValue("TC_ID");
		UpdateInventoryForScheduledEvents update = new UpdateInventoryForScheduledEvents(environment, "MinimalInfo");
		update.setAssignmentRequestDetailsDate(rsInventory.getValue("START_DATE"));
		update.setAssignmentOwnerId(BaseSoapCommands.REMOVE_NODE.toString());
		update.setFacilityId(rsInventory.getValue("FACILITY_ID"));
		update.setOwnerDetailsBookingDate(res.getServiceStartDate());
		update.setOwnerDetailsOwnerName(res.party().primaryGuest().getFullName());
		update.setOwnerDetailsOwnerReferenceNumber(tcId);
		update.setOwnerDetailsTravelPlanId(Randomness.randomNumber(12));
		update.setOwnerDetailsTravelPlanSegmentId(Randomness.randomNumber(12));
		update.setOwnerDetailsPeriodStartDate(rsInventory.getValue("START_DATE"));
		update.setOwnerDetailsPeriodEndDate(rsInventory.getValue("START_DATE"));
		update.setPeriodStartDate(rsInventory.getValue("START_DATE"));
		update.setPeriodEndDate(rsInventory.getValue("START_DATE"));
		update.setResourceTypeCode(rsResourceId.getValue("RSRC_INVTRY_TYP_CD"));
		update.setTpId(Randomness.randomNumber(12));
		update.setUpdateInventoryTypeInfoReservableResourceId(rsResourceId.getValue("RSRC_INVTRY_TYP_CD"));
		update.setUpdateInventoryTypeInfoOldServiceStartDate(rsInventory.getValue("START_DATE"));
		update.setUpdateInventoryTypeInfoNewServiceStartDate(rsInventory.getValue("START_DATE"));
		update.setUpdateInventoryTypeInfoNewDuration("1");
		update.sendRequest();
		
		TestReporter.logAPI(!update.getResponseStatusCode().contains("200"), update.getFaultString() ,update);
		TestReporter.assertTrue(Regex.match("[0-9]+", update.getNewAssignmentOwnerId()), "The new Assignement Owner ID ["+update.getNewAssignmentOwnerId()+"] is numeric as expected.");
		TestReporter.assertTrue(Regex.match("[0-9]+", update.getResponseTravelComponentId()), "The returned Travel Component Id ["+update.getResponseTravelComponentId()+"] is numeric as expected.");
		
		
		LogItems logItems = new LogItems();	
		logItems.addItem("PartyIF", "searchGuestIDByNameAndLocator", false);		
		//validateLogs(update, logItems);
		return update;
	}
}