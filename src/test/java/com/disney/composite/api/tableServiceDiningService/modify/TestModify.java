package com.disney.composite.api.tableServiceDiningService.modify;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.tableServiceDiningServicePort.operations.Cancel;
import com.disney.api.soapServices.tableServiceDiningServicePort.operations.Modify;
import com.disney.composite.BaseTest;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.database.LogItems;
import com.disney.utils.dataFactory.guestFactory.HouseHold;
import com.disney.utils.dataFactory.staging.bookSEReservation.ScheduledEventReservation;
import com.disney.utils.dataFactory.staging.bookSEReservation.TableServiceDiningReservation;

public class TestModify extends BaseTest{
	// Defining global variables
	HouseHold hh = null;
	protected ThreadLocal<String> TPS_ID = new ThreadLocal<String>();
	private ScheduledEventReservation res;
	@Override
	@BeforeClass(alwaysRun = true)
	@Parameters({ "environment" })
	public void setup(@Optional String environment){
		this.environment = environment;
		hh = new HouseHold(1);
		res = new TableServiceDiningReservation(this.environment, hh);
		res.book(ScheduledEventReservation.NOCOMPONENTSNOADDONS);
	}
	
	@AfterMethod(alwaysRun=true)
	public void teardown(){
		try{
			if(TPS_ID.get() != null)
				if(!TPS_ID.get().isEmpty()){
					Cancel cancel = new Cancel(environment, "Main");
					cancel.setReservationNumber(TPS_ID.get());
					cancel.sendRequest();
				}
		}catch(Exception e){}
	}

	@Test(groups = {"api", "regression", "dining", "tableDiningService"})
	public void testModify(){
		TestReporter.logScenario("1 Adult");
		TPS_ID.set(res.getConfirmationNumber());
		HouseHold newParty = new HouseHold(1);
		modifyAndValidateLogs(newParty,res);
	}
	@Test(groups = {"api", "regression", "dining", "tableDiningService"})
	public void testModifyTo2Adults(){
		TestReporter.logScenario("2 Adults");
		HouseHold newParty = new HouseHold("2 Adults");
		modifyAndValidateLogs(newParty,res);
	}
	@Test(groups = {"api", "regression", "dining", "tableDiningService"})
	public void testModifyTo4Adults(){
		TestReporter.logScenario("4 Adults");
		HouseHold newParty = new HouseHold("4 Adults");
		modifyAndValidateLogs(newParty,res);
	}
	@Test(groups = {"api", "regression", "dining", "tableDiningService"})
	public void testModifyTo2Adults2Child(){
		TestReporter.logScenario("2 Adults, 2 Children");
		HouseHold newParty = new HouseHold("2 Adults 2 Child");
		modifyAndValidateLogs(newParty,res);
	}
	@Test(groups = {"api", "regression", "dining", "tableDiningService"})
	public void testModifyTo4Adults2Child2Infant(){
		TestReporter.logScenario("4 Adults, 2 Children, 2 Infants");
		HouseHold newParty = new HouseHold("4 Adults 2 Child 2 Infant");
		modifyAndValidateLogs(newParty,res);
	}
	@Test(groups = {"api", "regression", "dining", "tableDiningService"})
	public void testModifyTo12Adults(){
		TestReporter.logScenario("12 Adults");
		HouseHold newParty = new HouseHold(12);
		modifyAndValidateLogs(newParty,res);
	}
	
	private void modifyAndValidateLogs(HouseHold newParty, ScheduledEventReservation res){
		Modify modify = new Modify(this.environment, "NoComponentsNoAddOns");
		modify.setReservationNumber(res.getConfirmationNumber());
		modify.setTravelPlanId(res.getTravelPlanId());
		modify.setParty(newParty);
		modify.setFacilityId(res.getFacilityId());
		modify.setServiceStartDate(res.getServiceStartDate());
		modify.setServicePeriodId(res.getServicePeriodId());
		modify.sendRequest();
		TestReporter.logAPI(!modify.getResponseStatusCode().equals("200"), "An error occurred duringmodification.", modify);
		TestReporter.logAPI(!modify.getResponseStatus().equals("SUCCESS"),"The Response status was not SUCCESS as expected", modify);
		
		LogItems logItems = new LogItems();
		logItems.addItem("ChargeGroupIF", "modifyGuestContainerChargeGroup", false);
		logItems.addItem("ChargeGroupIF", "modifyRootChargeGroup", false);
		logItems.addItem("TableServiceDiningServiceIF", "modify", false);
		logItems.addItem("GuestServiceV1", "create", false);
		logItems.addItem("PartyIF", "updateExternalPartyAndLocatorId", false);
		logItems.addItem("TravelPlanServiceCrossReferenceV3", "updateOrder", false);
		logItems.addItem("TravelPlanServiceCrossReferenceV3SEI", "updateOrder", false);
		logItems.addItem("GuestLinkServiceV1SEI", "createEntitlementReference", false);
		logItems.addItem("PartyIF", "createAndRetrieveParty", false);
		logItems.addItem("PartyIF", "retrieveParties", false);
		logItems.addItem("PartyIF", "retrieveParty", false);
		logItems.addItem("AccommodationInventoryRequestComponentServiceIF", "retrieveAssignmentOwner", false);
		logItems.addItem("AccommodationInventoryRequestComponentServiceIF", "updateInventoryForScheduledEvents", false);
		logItems.addItem("FacilityMasterServiceSEI", "findFacilityByEnterpriseID", false);
		logItems.addItem("GuestLinkServiceV1", "createEntitlementReference", false);
		logItems.addItem("GuestServiceV1", "create", false);
		logItems.addItem("PartyIF", "updateExternalPartyAndLocatorId", false);
		logItems.addItem("UpdateInventory", "updateInventory", false);
		validateLogs(modify, logItems);
	}
}
