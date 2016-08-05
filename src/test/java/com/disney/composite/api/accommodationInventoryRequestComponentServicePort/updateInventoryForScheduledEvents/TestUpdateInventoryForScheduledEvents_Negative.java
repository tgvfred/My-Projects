package com.disney.composite.api.accommodationInventoryRequestComponentServicePort.updateInventoryForScheduledEvents;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationInventoryRequestComponentServicePort.operations.UpdateInventoryForScheduledEvents;
import com.disney.api.soapServices.core.BaseSoapCommands;
import com.disney.composite.BaseTest;
import com.disney.test.utils.Randomness;
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

public class TestUpdateInventoryForScheduledEvents_Negative extends BaseTest{
	// Defining global variables
	protected String TPS_ID = null;
	ScheduledEventReservation res =null;

	Database db = null;
	Database availDb = null;
	@Override
	@BeforeClass
	@Parameters("environment")
	public void setup(@Optional String environment){
		this.environment = environment;
		hh = new HouseHold(1);
		res = new EventDiningReservation(this.environment, hh);
		res.book(ScheduledEventReservation.NOCOMPONENTSNOADDONS);
		db = new OracleDatabase(environment, Database.DREAMS);
		availDb = new OracleDatabase(environment, Database.AVAIL_SE);
	}
	
	@Test(groups = {"api", "regression", "resourceInventory", "accommodationInventoryRequestComponentServicePort", "negative"})
	public void testMissingAssignmentOwnerType(){
		UpdateInventoryForScheduledEvents update = update();
		update.setAssignmentOwnerType(BaseSoapCommands.REMOVE_NODE.toString());
		update.sendRequest();
		TestReporter.logAPI(!update.getServiceExceptionApplicationFaultCode().contains("4040"), update.getServiceExceptionApplicationFaultCode() ,update);
		TestReporter.logAPI(!update.getServiceExceptionApplicationFaultModule().contains("LILO RIM"), update.getServiceExceptionApplicationFaultModule() ,update);
		TestReporter.logAPI(!update.getServiceExceptionApplicationFaultMessage().contains("UPDATE INVENTORYV FOR SCHEDULED EVENTS ERROR"), update.getServiceExceptionApplicationFaultMessage() ,update);
		TestReporter.logAPI(!update.getServiceExceptionErrorMessage().contains("Assignment owner Type is missing"), update.getServiceExceptionErrorMessage() ,update);
		
		LogItems logItems = new LogItems();			
		//validateLogs(update, logItems);
	}
	
	@Test(groups = {"api", "regression", "resourceInventory", "accommodationInventoryRequestComponentServicePort", "negative"})
	public void testInvalidAssignmentOwnerType(){
		UpdateInventoryForScheduledEvents update = update();
		update.setAssignmentOwnerType("TRAVEL_COMPONENT");
		update.sendRequest();
		TestReporter.logAPI(!update.getServiceExceptionApplicationFaultCode().contains("4040"), update.getServiceExceptionApplicationFaultCode() ,update);
		TestReporter.logAPI(!update.getServiceExceptionApplicationFaultModule().contains("LILO RIM"), update.getServiceExceptionApplicationFaultModule() ,update);
		TestReporter.logAPI(!update.getServiceExceptionApplicationFaultMessage().contains("UPDATE INVENTORYV FOR SCHEDULED EVENTS ERROR"), update.getServiceExceptionApplicationFaultMessage() ,update);
		TestReporter.logAPI(!update.getServiceExceptionErrorMessage().contains("Assignment owner Type is missing"), update.getServiceExceptionErrorMessage() ,update);
		
		LogItems logItems = new LogItems();			
		//validateLogs(update, logItems);
	}

	@Test(groups = {"api", "regression", "resourceInventory", "accommodationInventoryRequestComponentServicePort", "negative"})
	public void testMissingAssignmentRequestDetailsDate(){
		UpdateInventoryForScheduledEvents update = update();
		update.setAssignmentRequestDetailsDate(BaseSoapCommands.REMOVE_NODE.toString());
		update.sendRequest();
		TestReporter.logAPI(!update.getServiceExceptionApplicationFaultCode().contains("4040"), update.getServiceExceptionApplicationFaultCode() ,update);
		TestReporter.logAPI(!update.getServiceExceptionApplicationFaultModule().contains("LILO RIM"), update.getServiceExceptionApplicationFaultModule() ,update);
		TestReporter.logAPI(!update.getServiceExceptionApplicationFaultMessage().contains("UPDATE INVENTORYV FOR SCHEDULED EVENTS ERROR"), update.getServiceExceptionApplicationFaultMessage() ,update);
		TestReporter.logAPI(!update.getServiceExceptionErrorMessage().contains("Assignment Request Date is missing"), update.getServiceExceptionErrorMessage() ,update);
		
		LogItems logItems = new LogItems();			
		//validateLogs(update, logItems);
	}
	
	@Test(groups = {"api", "regression", "resourceInventory", "accommodationInventoryRequestComponentServicePort", "negative"})
	public void testMissingFacilityId(){
		UpdateInventoryForScheduledEvents update = update();
		update.setFacilityId(BaseSoapCommands.REMOVE_NODE.toString());
		update.sendRequest();
		TestReporter.logAPI(!update.getServiceExceptionApplicationFaultCode().contains("4040"), update.getServiceExceptionApplicationFaultCode() ,update);
		TestReporter.logAPI(!update.getServiceExceptionApplicationFaultModule().contains("LILO RIM"), update.getServiceExceptionApplicationFaultModule() ,update);
		TestReporter.logAPI(!update.getServiceExceptionApplicationFaultMessage().contains("UPDATE INVENTORYV FOR SCHEDULED EVENTS ERROR"), update.getServiceExceptionApplicationFaultMessage() ,update);
		TestReporter.logAPI(!update.getServiceExceptionErrorMessage().contains("Facility Id is missing"), update.getServiceExceptionErrorMessage() ,update);
		
		LogItems logItems = new LogItems();			
		//validateLogs(update, logItems);
	}
	
	@Test(groups = {"api", "regression", "resourceInventory", "accommodationInventoryRequestComponentServicePort", "negative"})
	public void testMissingIsGuaranteedFlag(){
		UpdateInventoryForScheduledEvents update = update();
		update.setIsGuaranteed(BaseSoapCommands.REMOVE_NODE.toString());
		update.sendRequest();
		TestReporter.logAPI(!update.getServiceExceptionApplicationFaultCode().contains("4040"), update.getServiceExceptionApplicationFaultCode() ,update);
		TestReporter.logAPI(!update.getServiceExceptionApplicationFaultModule().contains("LILO RIM"), update.getServiceExceptionApplicationFaultModule() ,update);
		TestReporter.logAPI(!update.getServiceExceptionApplicationFaultMessage().contains("UPDATE INVENTORYV FOR SCHEDULED EVENTS ERROR"), update.getServiceExceptionApplicationFaultMessage() ,update);
		TestReporter.logAPI(!update.getServiceExceptionErrorMessage().contains("Gauranteed Flag is missing"), update.getServiceExceptionErrorMessage() ,update);
		
		LogItems logItems = new LogItems();			
		//validateLogs(update, logItems);
	}
	
	@Test(groups = {"api", "regression", "resourceInventory", "accommodationInventoryRequestComponentServicePort", "negative"})
	public void testMissingIsVIPFlag(){
		UpdateInventoryForScheduledEvents update = update();
		update.setIsVip(BaseSoapCommands.REMOVE_NODE.toString());
		update.sendRequest();
		TestReporter.logAPI(!update.getServiceExceptionApplicationFaultCode().contains("4040"), update.getServiceExceptionApplicationFaultCode() ,update);
		TestReporter.logAPI(!update.getServiceExceptionApplicationFaultModule().contains("LILO RIM"), update.getServiceExceptionApplicationFaultModule() ,update);
		TestReporter.logAPI(!update.getServiceExceptionApplicationFaultMessage().contains("UPDATE INVENTORYV FOR SCHEDULED EVENTS ERROR"), update.getServiceExceptionApplicationFaultMessage() ,update);
		TestReporter.logAPI(!update.getServiceExceptionErrorMessage().contains("VIP Flag is missing"), update.getServiceExceptionErrorMessage() ,update);
		
		LogItems logItems = new LogItems();			
		//validateLogs(update, logItems);
	}
	
	@Test(groups = {"api", "regression", "resourceInventory", "accommodationInventoryRequestComponentServicePort", "negative"})
	public void testMissingOwnerDetailsBookingDate(){
		UpdateInventoryForScheduledEvents update = update();
		update.setOwnerDetailsBookingDate(BaseSoapCommands.REMOVE_NODE.toString());
		update.sendRequest();
		TestReporter.logAPI(!update.getServiceExceptionApplicationFaultCode().contains("4040"), update.getServiceExceptionApplicationFaultCode() ,update);
		TestReporter.logAPI(!update.getServiceExceptionApplicationFaultModule().contains("LILO RIM"), update.getServiceExceptionApplicationFaultModule() ,update);
		TestReporter.logAPI(!update.getServiceExceptionApplicationFaultMessage().contains("UPDATE INVENTORYV FOR SCHEDULED EVENTS ERROR"), update.getServiceExceptionApplicationFaultMessage() ,update);
		TestReporter.logAPI(!update.getServiceExceptionErrorMessage().contains("Booking Date is missing"), update.getServiceExceptionErrorMessage() ,update);
		
		LogItems logItems = new LogItems();			
		//validateLogs(update, logItems);
	}
	
	@Test(groups = {"api", "regression", "resourceInventory", "accommodationInventoryRequestComponentServicePort", "negative"})
	public void testMissingOwnerDetailsTcId(){
		UpdateInventoryForScheduledEvents update = update();
		update.setOwnerDetailsOwnerReferenceNumber(BaseSoapCommands.REMOVE_NODE.toString());
		update.sendRequest();
		TestReporter.logAPI(!update.getServiceExceptionApplicationFaultCode().contains("4040"), update.getServiceExceptionApplicationFaultCode() ,update);
		TestReporter.logAPI(!update.getServiceExceptionApplicationFaultModule().contains("LILO RIM"), update.getServiceExceptionApplicationFaultModule() ,update);
		TestReporter.logAPI(!update.getServiceExceptionApplicationFaultMessage().contains("UPDATE INVENTORYV FOR SCHEDULED EVENTS ERROR"), update.getServiceExceptionApplicationFaultMessage() ,update);
		TestReporter.logAPI(!update.getServiceExceptionErrorMessage().contains("TC Id is missing"), update.getServiceExceptionErrorMessage() ,update);
		
		LogItems logItems = new LogItems();			
		//validateLogs(update, logItems);
	}

	
	@Test(groups = {"api", "regression", "resourceInventory", "accommodationInventoryRequestComponentServicePort", "negative"})
	public void testMissingOwnerDetailsTpType(){
		UpdateInventoryForScheduledEvents update = update();
		update.setRequestNodeValueByXPath("/Envelope/Body/updateInventoryForScheduledEvents/request/newAssignmentOwners/ownerDetails/parentOwnerReferences[1]/referenceNumber", BaseSoapCommands.REMOVE_NODE.toString());
		update.sendRequest();
		TestReporter.logAPI(!update.getServiceExceptionApplicationFaultCode().contains("4040"), update.getServiceExceptionApplicationFaultCode() ,update);
		TestReporter.logAPI(!update.getServiceExceptionApplicationFaultModule().contains("LILO RIM"), update.getServiceExceptionApplicationFaultModule() ,update);
		TestReporter.logAPI(!update.getServiceExceptionApplicationFaultMessage().contains("UPDATE INVENTORYV FOR SCHEDULED EVENTS ERROR"), update.getServiceExceptionApplicationFaultMessage() ,update);
		TestReporter.logAPI(!update.getServiceExceptionErrorMessage().contains("TP Id is missing"), update.getServiceExceptionErrorMessage() ,update);
		
		LogItems logItems = new LogItems();			
		//validateLogs(update, logItems);
	}
	
	@Test(groups = {"api", "regression", "resourceInventory", "accommodationInventoryRequestComponentServicePort", "negative"})
	public void testMissingOwnerDetailsTpId(){
		UpdateInventoryForScheduledEvents update = update();
		update.setOwnerDetailsTravelPlanId(BaseSoapCommands.REMOVE_NODE.toString());
		update.sendRequest();
		TestReporter.logAPI(!update.getServiceExceptionApplicationFaultCode().contains("4040"), update.getServiceExceptionApplicationFaultCode() ,update);
		TestReporter.logAPI(!update.getServiceExceptionApplicationFaultModule().contains("LILO RIM"), update.getServiceExceptionApplicationFaultModule() ,update);
		TestReporter.logAPI(!update.getServiceExceptionApplicationFaultMessage().contains("UPDATE INVENTORYV FOR SCHEDULED EVENTS ERROR"), update.getServiceExceptionApplicationFaultMessage() ,update);
		TestReporter.logAPI(!update.getServiceExceptionErrorMessage().contains("TP Id is missing"), update.getServiceExceptionErrorMessage() ,update);
		
		LogItems logItems = new LogItems();			
		//validateLogs(update, logItems);
	}
	
	@Test(groups = {"api", "regression", "resourceInventory", "accommodationInventoryRequestComponentServicePort", "negative"})
	public void testMissingOwnerDetailsTpsType(){
		UpdateInventoryForScheduledEvents update = update();
		update.setRequestNodeValueByXPath("/Envelope/Body/updateInventoryForScheduledEvents/request/newAssignmentOwners/ownerDetails/parentOwnerReferences[2]/referenceNumber", BaseSoapCommands.REMOVE_NODE.toString());
		update.sendRequest();
		TestReporter.logAPI(!update.getServiceExceptionApplicationFaultCode().contains("4040"), update.getServiceExceptionApplicationFaultCode() ,update);
		TestReporter.logAPI(!update.getServiceExceptionApplicationFaultModule().contains("LILO RIM"), update.getServiceExceptionApplicationFaultModule() ,update);
		TestReporter.logAPI(!update.getServiceExceptionApplicationFaultMessage().contains("UPDATE INVENTORYV FOR SCHEDULED EVENTS ERROR"), update.getServiceExceptionApplicationFaultMessage() ,update);
		TestReporter.logAPI(!update.getServiceExceptionErrorMessage().contains("TPS Id is missing"), update.getServiceExceptionErrorMessage() ,update);
		
		LogItems logItems = new LogItems();			
		//validateLogs(update, logItems);
	}
	
	@Test(groups = {"api", "regression", "resourceInventory", "accommodationInventoryRequestComponentServicePort", "negative"})
	public void testMissingOwnerDetailsTpsId(){
		UpdateInventoryForScheduledEvents update = update();
		update.setOwnerDetailsTravelPlanSegmentId(BaseSoapCommands.REMOVE_NODE.toString());
		update.sendRequest();
		TestReporter.logAPI(!update.getServiceExceptionApplicationFaultCode().contains("4040"), update.getServiceExceptionApplicationFaultCode() ,update);
		TestReporter.logAPI(!update.getServiceExceptionApplicationFaultModule().contains("LILO RIM"), update.getServiceExceptionApplicationFaultModule() ,update);
		TestReporter.logAPI(!update.getServiceExceptionApplicationFaultMessage().contains("UPDATE INVENTORYV FOR SCHEDULED EVENTS ERROR"), update.getServiceExceptionApplicationFaultMessage() ,update);
		TestReporter.logAPI(!update.getServiceExceptionErrorMessage().contains("TPS Id is missing"), update.getServiceExceptionErrorMessage() ,update);
		
		LogItems logItems = new LogItems();			
		//validateLogs(update, logItems);
	}
	
	@Test(groups = {"api", "regression", "resourceInventory", "accommodationInventoryRequestComponentServicePort", "negative"})
	public void testMissingPeriod(){
		UpdateInventoryForScheduledEvents update = update();
		update.setRequestNodeValueByXPath("/Envelope/Body/updateInventoryForScheduledEvents/request/newAssignmentOwners/period", BaseSoapCommands.REMOVE_NODE.toString());
		update.sendRequest();
		TestReporter.logAPI(!update.getServiceExceptionApplicationFaultCode().contains("4040"), update.getServiceExceptionApplicationFaultCode() ,update);
		TestReporter.logAPI(!update.getServiceExceptionApplicationFaultModule().contains("LILO RIM"), update.getServiceExceptionApplicationFaultModule() ,update);
		TestReporter.logAPI(!update.getServiceExceptionApplicationFaultMessage().contains("UPDATE INVENTORYV FOR SCHEDULED EVENTS ERROR"), update.getServiceExceptionApplicationFaultMessage() ,update);
		TestReporter.logAPI(!update.getServiceExceptionErrorMessage().contains("Period is missing"), update.getServiceExceptionErrorMessage() ,update);
		
		LogItems logItems = new LogItems();			
		//validateLogs(update, logItems);
	}

	@Test(groups = {"api", "regression", "resourceInventory", "accommodationInventoryRequestComponentServicePort", "negative"})
	public void testMissingResourceTypeCode(){
		UpdateInventoryForScheduledEvents update = update();
		update.setResourceTypeCode(BaseSoapCommands.REMOVE_NODE.toString());
		update.sendRequest();
		TestReporter.logAPI(!update.getServiceExceptionApplicationFaultCode().contains("4040"), update.getServiceExceptionApplicationFaultCode() ,update);
		TestReporter.logAPI(!update.getServiceExceptionApplicationFaultModule().contains("LILO RIM"), update.getServiceExceptionApplicationFaultModule() ,update);
		TestReporter.logAPI(!update.getServiceExceptionApplicationFaultMessage().contains("UPDATE INVENTORYV FOR SCHEDULED EVENTS ERROR"), update.getServiceExceptionApplicationFaultMessage() ,update);
		TestReporter.logAPI(!update.getServiceExceptionErrorMessage().contains("Resource Type Code is missing"), update.getServiceExceptionErrorMessage() ,update);
		
		LogItems logItems = new LogItems();			
		//validateLogs(update, logItems);
	}

	@Test(groups = {"api", "regression", "resourceInventory", "accommodationInventoryRequestComponentServicePort", "negative"})
	public void testMissingWholeSaleIndicator(){
		UpdateInventoryForScheduledEvents update = update();
		update.setWholesaleIndicator(BaseSoapCommands.REMOVE_NODE.toString());
		update.sendRequest();
		TestReporter.logAPI(!update.getServiceExceptionApplicationFaultCode().contains("4040"), update.getServiceExceptionApplicationFaultCode() ,update);
		TestReporter.logAPI(!update.getServiceExceptionApplicationFaultModule().contains("LILO RIM"), update.getServiceExceptionApplicationFaultModule() ,update);
		TestReporter.logAPI(!update.getServiceExceptionApplicationFaultMessage().contains("UPDATE INVENTORYV FOR SCHEDULED EVENTS ERROR"), update.getServiceExceptionApplicationFaultMessage() ,update);
		TestReporter.logAPI(!update.getServiceExceptionErrorMessage().contains("Whole sale indicator is missing"), update.getServiceExceptionErrorMessage() ,update);
		
		LogItems logItems = new LogItems();			
		//validateLogs(update, logItems);
	}
	
	private UpdateInventoryForScheduledEvents update(){
		

		Recordset rsBaseInfo = new Recordset(db.getResultSet(Dreams.getReservationInfoByTpsId(res.getConfirmationNumber()) + " AND PROD_TYP_NM = 'RESERVABLE_RESOURCE_COMPONENT'"));
		Recordset rsResourceId= new Recordset(db.getResultSet(Dreams.getTcReservableResourceID(rsBaseInfo.getValue("TC_ID"))));
		
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
		
		return update;
	}
}