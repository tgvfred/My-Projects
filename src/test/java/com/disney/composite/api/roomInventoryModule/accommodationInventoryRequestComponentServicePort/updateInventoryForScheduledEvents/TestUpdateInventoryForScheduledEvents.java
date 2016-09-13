package com.disney.composite.api.roomInventoryModule.accommodationInventoryRequestComponentServicePort.updateInventoryForScheduledEvents;

import org.testng.annotations.Test;

import com.disney.api.BaseTest;
import com.disney.api.soapServices.core.BaseSoapCommands;
import com.disney.api.soapServices.roomInventoryModule.accommodationInventoryRequestComponentServicePort.operations.UpdateInventoryForScheduledEvents;
import com.disney.test.utils.Randomness;
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

		Database db = new OracleDatabase(environment, Database.DREAMS);
		Recordset rsBaseInfo = new Recordset(db.getResultSet(Dreams.getReservationInfoByTpsId(res.getConfirmationNumber()) + " AND PROD_TYP_NM = 'RESERVABLE_RESOURCE_COMPONENT'"));
		Recordset rsResourceId= new Recordset(db.getResultSet(Dreams.getTcReservableResourceID(rsBaseInfo.getValue("TC_ID"))));
		
		Database availDb = new OracleDatabase(environment, Database.AVAIL_SE);
		Recordset rsInventory = new Recordset(availDb.getResultSet(AvailSE.getReservableResourceByFacilityAndDate(res.getFacilityId(), res.getServiceStartDate())));
		if (rsInventory.getRowCount() == 0) {
			rsInventory = new Recordset(availDb.getResultSet(AvailSE.getResourceAvailibleTimesById(rsResourceId.getValue("Resource_ID"))));
		}
		
		String tcId = rsBaseInfo.getValue("TC_ID");
		UpdateInventoryForScheduledEvents update = new UpdateInventoryForScheduledEvents(environment, "MinimalInfo");
		update.setAssignmentRequestDetailsDate(rsInventory.getValue("Start_Date").replace(".0", ""));
		update.setAssignmentOwnerId(BaseSoapCommands.REMOVE_NODE.toString());
		update.setFacilityId(res.getFacilityId());
		update.setOwnerDetailsBookingDate(rsInventory.getValue("Start_Date").replace(".0", ""));
		update.setOwnerDetailsOwnerName(res.party().primaryGuest().getFullName());
		update.setOwnerDetailsOwnerReferenceNumber(tcId);
		update.setOwnerDetailsTravelPlanId(Randomness.randomNumber(12));
		update.setOwnerDetailsTravelPlanSegmentId(Randomness.randomNumber(12));
		update.setOwnerDetailsPeriodStartDate(rsInventory.getValue("Start_Date").replace(".0", ""));
		update.setOwnerDetailsPeriodEndDate(rsInventory.getValue("Start_Date").replace(".0", ""));
		update.setPeriodStartDate(rsInventory.getValue("Start_Date").replace(".0", ""));
		update.setPeriodEndDate(rsInventory.getValue("Start_Date").replace(".0", ""));
		update.setResourceTypeCode(rsInventory.getValue("Resource_ID"));
		update.setTpId(Randomness.randomNumber(12));
		update.setUpdateInventoryTypeInfoReservableResourceId(rsInventory.getValue("Resource_ID"));
		update.setUpdateInventoryTypeInfoOldServiceStartDate(rsInventory.getValue("Start_Date").replace(".0", ""));
		update.setUpdateInventoryTypeInfoNewServiceStartDate(rsInventory.getValue("Start_Date").replace(".0", ""));
		update.setUpdateInventoryTypeInfoNewDuration("1");
		update.sendRequest();
		
		TestReporter.logAPI(!update.getResponseStatusCode().contains("200"), update.getFaultString() ,update);
		TestReporter.assertTrue(Regex.match("[0-9]+", update.getNewAssignmentOwnerId()), "The new Assignement Owner ID ["+update.getNewAssignmentOwnerId()+"] is numeric as expected.");

		TestReporter.assertTrue(update.getNewAssignmentOwnerId().length()==9, "The new Assignement Owner ID ["+update.getNewAssignmentOwnerId()+"] is 9 characters in length.");
		TestReporter.assertTrue(Regex.match("[0-9]+", update.getResponseTravelComponentId()), "The returned Travel Component Id ["+update.getResponseTravelComponentId()+"] is numeric as expected.");
		
		
		LogItems logItems = new LogItems();	
		logItems.addItem("PartyIF", "searchGuestIDByNameAndLocator", false);		
		//validateLogs(update, logItems);
		return update;
	}
}