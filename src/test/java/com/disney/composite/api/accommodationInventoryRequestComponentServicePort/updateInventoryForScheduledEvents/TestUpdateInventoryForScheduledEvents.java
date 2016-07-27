package com.disney.composite.api.accommodationInventoryRequestComponentServicePort.updateInventoryForScheduledEvents;

import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationInventoryRequestComponentServicePort.operations.UpdateInventoryForScheduledEvents;
import com.disney.api.soapServices.core.BaseSoapCommands;
import com.disney.composite.BaseTest;
import com.disney.utils.Regex;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.database.Database;
import com.disney.utils.dataFactory.database.LogItems;
import com.disney.utils.dataFactory.database.Recordset;
import com.disney.utils.dataFactory.database.databaseImpl.OracleDatabase;
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
		String tcId = rsBaseInfo.getValue("TC_ID");
		String assignmentOwnerId = rsBaseInfo.getValue("TC_ASGN_OWN_ID");
		UpdateInventoryForScheduledEvents update = new UpdateInventoryForScheduledEvents("Development", "MinimalInfo");
		update.setAssignmentRequestDetailsDate(res.getServiceStartDate());
		update.setAssignmentOwnerId(assignmentOwnerId);
		update.setFacilityId(res.getFacilityId());
		update.setOwnerDetailsBookingDate(res.getServiceStartDate());
		update.setOwnerDetailsOwnerName(res.party().primaryGuest().getFullName());
		update.setOwnerDetailsOwnerReferenceNumber(tcId);
		update.setOwnerDetailsTravelPlanId(res.getTravelPlanId());
		update.setOwnerDetailsTravelPlanSegmentId(res.getConfirmationNumber());
		update.setOwnerDetailsPeriodStartDate(res.getServiceStartDate());
		update.setOwnerDetailsPeriodEndDate(res.getServiceStartDate());
		update.setPeriodStartDate(res.getServiceStartDate());
		update.setResourceTypeCode(rsResourceId.getValue("RSRC_INVTRY_TYP_CD"));
		update.setTpId(res.getTravelPlanId());
		update.setUpdateInventoryTypeInfoReservableResourceId(rsResourceId.getValue("RSRC_INVTRY_TYP_CD"));
		update.setUpdateInventoryTypeInfoOldServiceStartDate(res.getServiceStartDate());
		update.setUpdateInventoryTypeInfoNewServiceStartDate(BaseSoapCommands.GET_DATE_TIME.commandAppend("2"));
		update.setUpdateInventoryTypeInfoNewDuration("1");
		update.sendRequest();
		
		TestReporter.logAPI(!update.getResponseStatusCode().contains("200"), update.getFaultString() ,update);
		TestReporter.assertTrue(Regex.match("[0-9]+", update.getNewAssignmentOwnerId()), "The new Assignement Owner ID ["+update.getNewAssignmentOwnerId()+"] is numeric as expected.");
		TestReporter.assertTrue(Regex.match("[0-9]+", update.getResponseTravelComponentId()), "The returned Travel Component Id ["+update.getResponseTravelComponentId()+"] is numeric as expected.");
		TestReporter.assertTrue(Long.parseLong(update.getNewAssignmentOwnerId()) > Long.parseLong(assignmentOwnerId), "The returned Assignment Owner Id [" + update.getNewAssignmentOwnerId() + "] is expected to be larger than prior Assignment ID [" + assignmentOwnerId +"]" );
		TestReporter.assertTrue(tcId.equals(update.getResponseTravelComponentId()) , "The returned Travel Component Id [" + update.getResponseTravelComponentId() + "] is expected to be equal to Travel Component ID sent in request  [" + tcId+"]" );
		
		
		LogItems logItems = new LogItems();	
		logItems.addItem("PartyIF", "searchGuestIDByNameAndLocator", false);		
		//validateLogs(update, logItems);
		return update;
	}
}