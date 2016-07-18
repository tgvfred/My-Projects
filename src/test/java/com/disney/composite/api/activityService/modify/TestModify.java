package com.disney.composite.api.activityService.modify;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.activityServicePort.operations.Modify;
import com.disney.composite.BaseTest;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.database.LogItems;
import com.disney.utils.dataFactory.guestFactory.HouseHold;
import com.disney.utils.dataFactory.staging.bookSEReservation.ActivityEventReservation;
import com.disney.utils.dataFactory.staging.bookSEReservation.ScheduledEventReservation;

public class TestModify extends BaseTest{
	// Defining global variables
	protected ThreadLocal<ScheduledEventReservation> res = new ThreadLocal<ScheduledEventReservation>();
	protected ThreadLocal<String> TPS_ID = new ThreadLocal<String>();
	HouseHold hh = null;
	
	@BeforeMethod(alwaysRun = true)
	@Parameters({ "environment" })
	public void setup(@Optional String environment){
		this.environment = environment;
		hh = new HouseHold(1);
		res.set(new ActivityEventReservation(this.environment, hh));
		res.get().setFacilityId("80008044");
		res.get().setProductId("53972");
		res.get().setProductType("RecreationActivityProduct");
		res.get().book(ScheduledEventReservation.NOCOMPONENTSNOADDONS);
		TPS_ID.set(res.get().getConfirmationNumber());
	}	

	@AfterMethod(alwaysRun=true)
	public void setup(){
		try{
			if(res.get() != null)
				if(!res.get().getConfirmationNumber().isEmpty())
					res.get().cancel();
		}catch(Exception e){}
	}
	
	@Test(groups = {"api", "regression", "activity", "activityService"})
	public void testModify(){
		HouseHold newParty = new HouseHold(1);
		Modify modify = new Modify(this.environment, "NoComponentsNoAddOns");
		modify.setReservationNumber(res.get().getConfirmationNumber());
		modify.setTravelPlanId(res.get().getTravelPlanId());
		modify.setParty(newParty);		
		modify.setFacilityId(res.get().getFacilityId());		
		modify.setProductId(res.get().getProductId());
		modify.setProductType(res.get().getProductType());		
		modify.setServiceStartDate(res.get().getServiceStartDate());
		modify.setServicePeriodId(res.get().getServicePeriodId());
		modify.setProductId(res.get().getProductId());
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
		validateLogs(modify, logItems);
	}
	

	@Test(groups = {"api", "regression", "activity", "activityService"})
	public void testModifyTo2Adults(){
		HouseHold newParty = new HouseHold("2 Adults");
		ScheduledEventReservation originalRes = new ActivityEventReservation(this.environment, hh);	
		originalRes.setFacilityId("80008044");
		originalRes.setProductId("53972");
		originalRes.setProductType("RecreationActivityProduct");	
		originalRes.book(ScheduledEventReservation.NOCOMPONENTSNOADDONS);
		TPS_ID.set(originalRes.getConfirmationNumber());
		Modify modify = new Modify(this.environment, "NoComponentsNoAddOns");
		modify.setReservationNumber(originalRes.getConfirmationNumber());
		modify.setTravelPlanId(originalRes.getTravelPlanId());
		modify.setParty(newParty);		
		modify.setFacilityId(originalRes.getFacilityId());
		modify.setProductId(originalRes.getFacilityId());
		modify.setProductType(originalRes.getProductType());		
		modify.setServiceStartDate(originalRes.getServiceStartDate());
		modify.setServicePeriodId(originalRes.getServicePeriodId());
		modify.setProductId(originalRes.getProductId());
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
		validateLogs(modify, logItems);
	}

	@Test(groups = {"api", "regression", "activity", "activityService"})
	public void testModifyTo4Adults(){
		HouseHold newParty = new HouseHold("4 Adults");
		ScheduledEventReservation originalRes = new ActivityEventReservation(this.environment, hh);	
		originalRes.setFacilityId("80008044");
		originalRes.setProductId("53972");
		originalRes.setProductType("RecreationActivityProduct");		
		originalRes.book(ScheduledEventReservation.NOCOMPONENTSNOADDONS);
		TPS_ID.set(originalRes.getConfirmationNumber());
		Modify modify = new Modify(this.environment, "NoComponentsNoAddOns");
		modify.setReservationNumber(originalRes.getConfirmationNumber());
		modify.setTravelPlanId(originalRes.getTravelPlanId());
		modify.setParty(newParty);		
		modify.setFacilityId(originalRes.getFacilityId());
		modify.setProductId(originalRes.getProductId());
		modify.setProductType(originalRes.getProductType());		
		modify.setServiceStartDate(originalRes.getServiceStartDate());
		modify.setServicePeriodId(originalRes.getServicePeriodId());
		modify.setProductId(originalRes.getProductId());
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
		validateLogs(modify, logItems);
	}	

	@Test(groups = {"api", "regression", "activity", "activityService"})
	public void testModifyTo2Adults2Child(){
		HouseHold newParty = new HouseHold("2 Adults 2 Child");
		ScheduledEventReservation originalRes = new ActivityEventReservation(this.environment, hh);	
		originalRes.setFacilityId("80008044");
		originalRes.setProductId("53972");
		originalRes.setProductType("RecreationActivityProduct");			
		originalRes.book(ScheduledEventReservation.NOCOMPONENTSNOADDONS);
		TPS_ID.set(originalRes.getConfirmationNumber());
		Modify modify = new Modify(this.environment, "NoComponentsNoAddOns");
		modify.setReservationNumber(originalRes.getConfirmationNumber());
		modify.setTravelPlanId(originalRes.getTravelPlanId());
		modify.setParty(newParty);		
		modify.setFacilityId(originalRes.getFacilityId());
		modify.setProductId(originalRes.getProductId());
		modify.setProductType(originalRes.getProductType());		
		modify.setServiceStartDate(originalRes.getServiceStartDate());
		modify.setServicePeriodId(originalRes.getServicePeriodId());
		modify.setProductId(originalRes.getProductId());
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
		validateLogs(modify, logItems);
	}	

	@Test(groups = {"api", "regression", "activity", "activityService"})
	public void testModifyTo4Adults2Child2Infant(){
		HouseHold newParty = new HouseHold("4 Adults 2 Child 2 Infant");
		ScheduledEventReservation originalRes = new ActivityEventReservation(this.environment, hh);
		originalRes.setFacilityId("80008044");
		originalRes.setProductId("53972");
		originalRes.setProductType("RecreationActivityProduct");				
		originalRes.book(ScheduledEventReservation.NOCOMPONENTSNOADDONS);
		TPS_ID.set(originalRes.getConfirmationNumber());
		Modify modify = new Modify(this.environment, "NoComponentsNoAddOns");
		modify.setReservationNumber(originalRes.getConfirmationNumber());
		modify.setTravelPlanId(originalRes.getTravelPlanId());
		modify.setParty(newParty);
		modify.setFacilityId(originalRes.getFacilityId());		
		modify.setFacilityId(originalRes.getFacilityId());
		modify.setProductId(originalRes.getProductId());
		modify.setProductType(originalRes.getProductType());		
		modify.setServiceStartDate(originalRes.getServiceStartDate());
		modify.setServicePeriodId(originalRes.getServicePeriodId());
		modify.setProductId(originalRes.getProductId());
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
		validateLogs(modify, logItems);
	}	

	@Test(groups = {"api", "regression", "activity", "activityService"})
	public void testModifyTo12Adults(){
		HouseHold newParty = new HouseHold(12);
		ScheduledEventReservation originalRes = new ActivityEventReservation(this.environment, hh);	
		originalRes.setFacilityId("80008044");
		originalRes.setProductId("53972");
		originalRes.setProductType("RecreationActivityProduct");			
		originalRes.book(ScheduledEventReservation.NOCOMPONENTSNOADDONS);
		TPS_ID.set(originalRes.getConfirmationNumber());
		Modify modify = new Modify(this.environment, "NoComponentsNoAddOns");
		modify.setReservationNumber(originalRes.getConfirmationNumber());
		modify.setTravelPlanId(originalRes.getTravelPlanId());
		modify.setParty(newParty);
		modify.setFacilityId(originalRes.getFacilityId());		
		modify.setFacilityId(originalRes.getFacilityId());
		modify.setProductId(originalRes.getProductId());
		modify.setProductType(originalRes.getProductType());		
		modify.setServiceStartDate(originalRes.getServiceStartDate());
		modify.setServicePeriodId(originalRes.getServicePeriodId());
		modify.setProductId(originalRes.getProductId());
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
		validateLogs(modify, logItems);
	}
}