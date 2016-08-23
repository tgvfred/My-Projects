package com.disney.composite.api.activityModule.activityService.modify;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import com.disney.api.soapServices.activityModule.activityServicePort.operations.Cancel;
import com.disney.api.soapServices.activityModule.activityServicePort.operations.Modify;
import com.disney.composite.BaseTest;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.database.LogItems;
import com.disney.utils.dataFactory.guestFactory.HouseHold;
import com.disney.utils.dataFactory.staging.bookSEReservation.ActivityEventReservation;
import com.disney.utils.dataFactory.staging.bookSEReservation.ScheduledEventReservation;
import com.disney.utils.dataFactory.staging.bookSEReservation.ShowDiningReservation;

public class TestModify extends BaseTest{
	protected ThreadLocal<String> TPS_ID = new ThreadLocal<String>();

	@AfterMethod(alwaysRun=true)
	public void setup(){
		try{
			Cancel cancel = new Cancel(environment, "CancelDiningEvent");
			cancel.setReservationNumber(TPS_ID.get());
			cancel.sendRequest();
		}catch(Exception e){}
	}
	@Test(groups = {"api", "regression", "dining", "activityService"})
	public void testReinstate(){
		ScheduledEventReservation res2 = bookOriginalRes();
		Modify modify = new Modify(this.environment, ScheduledEventReservation.ONECOMPONENTSNOADDONS);
		modify.setReservationNumber(res2.getConfirmationNumber());
		modify.setTravelPlanId(res2.getTravelPlanId());
		modify.setParty(res2.party());
		modify.setFacilityId(res2.getFacilityId());
		modify.setServiceStartDate(res2.getServiceStartDate());
		modify.setServicePeriodId(res2.getServicePeriodId());
		modify.setProductId(res2.getProductId());
		res2.cancel();
		modify.sendRequest();
		res2.retrieve();
		TestReporter.logAPI(!res2.getStatus().equals("Booked"), "Reservation status was not [Booked] instead [" + res2.getStatus() + "]", modify);
	}
	@Test(groups = {"api", "regression", "activity", "activityService"})
	public void testModify(){
		TestReporter.logScenario("1 Adult");
		HouseHold newParty = new HouseHold(1);
		ScheduledEventReservation originalRes = bookOriginalRes();
		sendRequestAndValidateLogs(originalRes, newParty);
	}

	@Test(groups = {"api", "regression", "activity", "activityService"})
	public void testModifyTo2Adults(){
		TestReporter.logScenario("2 Adults");
		HouseHold newParty = new HouseHold("2 Adults");
		ScheduledEventReservation originalRes = bookOriginalRes();
		sendRequestAndValidateLogs(originalRes, newParty);
	}

	@Test(groups = {"api", "regression", "activity", "activityService"})
	public void testModifyTo4Adults(){
		TestReporter.logScenario("4 Adults");
		HouseHold newParty = new HouseHold("4 Adults");
		ScheduledEventReservation originalRes = bookOriginalRes();
		sendRequestAndValidateLogs(originalRes, newParty);
	}	

	@Test(groups = {"api", "regression", "activity", "activityService"})
	public void testModifyTo2Adults2Child(){
		TestReporter.logScenario("2 Adults, 2 Children");
		HouseHold newParty = new HouseHold("2 Adults 2 Child");
		ScheduledEventReservation originalRes = bookOriginalRes();
		sendRequestAndValidateLogs(originalRes, newParty);
	}	

	@Test(groups = {"api", "regression", "activity", "activityService"})
	public void testModifyTo4Adults2Child2Infant(){
		TestReporter.logScenario("4 Adults, 2 Children, 2 Infants");
		HouseHold newParty = new HouseHold("4 Adults 2 Child 2 Infant");
		ScheduledEventReservation originalRes = bookOriginalRes();
		sendRequestAndValidateLogs(originalRes, newParty);
	}	

	@Test(groups = {"api", "regression", "activity", "activityService"})
	public void testModifyTo12Adults(){
		TestReporter.logScenario("12 Adults");
		HouseHold newParty = new HouseHold(12);
		ScheduledEventReservation originalRes = bookOriginalRes();
		sendRequestAndValidateLogs(originalRes, newParty);
	}
	
	private ScheduledEventReservation bookOriginalRes(){
		ScheduledEventReservation originalRes = new ActivityEventReservation(this.environment, hh);
		originalRes.setFacilityId("80008044");
		originalRes.setProductId("53972");
		originalRes.setProductType("RecreationActivityProduct");				
		originalRes.book(ScheduledEventReservation.NOCOMPONENTSNOADDONS);
		TPS_ID.set(originalRes.getConfirmationNumber());
		return originalRes;
	}
	
	private void sendRequestAndValidateLogs(ScheduledEventReservation localRes, HouseHold party){
		Modify modify = new Modify(this.environment, "NoComponentsNoAddOns");
		modify.setReservationNumber(localRes.getConfirmationNumber());
		modify.setTravelPlanId(localRes.getTravelPlanId());
		modify.setParty(party);
		modify.setFacilityId(localRes.getFacilityId());		
		modify.setFacilityId(localRes.getFacilityId());
		modify.setProductId(localRes.getProductId());
		modify.setProductType(localRes.getProductType());		
		modify.setServiceStartDate(localRes.getServiceStartDate());
		modify.setServicePeriodId(localRes.getServicePeriodId());
		modify.setProductId(localRes.getProductId());
		modify.sendRequest();
		TestReporter.logAPI(!modify.getStatus().equals("SUCCESS"),"The Response status was not SUCCESS as expected", modify);		

		LogItems logItems = new LogItems();
		logItems.addItem("ChargeGroupIF", "modifyGuestContainerChargeGroup", false);
		logItems.addItem("ChargeGroupIF", "modifyRootChargeGroup", false);
		logItems.addItem("ActivityServiceIF", "modify", false);
		logItems.addItem("TravelPlanServiceCrossReferenceV3", "updateOrder", false);
		logItems.addItem("TravelPlanServiceCrossReferenceV3SEI", "updateOrder", false);
		logItems.addItem("FolioServiceIF", "retrieveAccountingTransactions", false);
		logItems.addItem("PartyIF", "retrieveParty", false);
		logItems.addItem("PartyIF", "retrieveParties", false);
		logItems.addItem("PartyIF", "retrievePartyBasicInformation", false);
		logItems.addItem("PartyIF", "createAndRetrieveParty", false);
		logItems.addItem("PartyIF", "updateExternalPartyAndLocatorId", false);
		logItems.addItem("AccommodationInventoryRequestComponentServiceIF", "retrieveAssignmentOwner", false);
		logItems.addItem("ActivityServiceIF", "retrieve", false);
		logItems.addItem("PackagingService", "getProducts", false);
		logItems.addItem("PricingService", "priceComponents", false);
		logItems.addItem("GuestServiceV1", "create", false);
		validateLogs(modify, logItems, 8000);
	}
}