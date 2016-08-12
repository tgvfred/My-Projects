package com.disney.composite.api.diningModule.eventDiningService.modify;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.diningModule.eventDiningService.operations.Book;
import com.disney.api.soapServices.diningModule.eventDiningService.operations.Modify;
import com.disney.composite.BaseTest;
import com.disney.test.utils.Sleeper;
import com.disney.utils.Randomness;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.database.LogItems;
import com.disney.utils.dataFactory.guestFactory.HouseHold;
import com.disney.utils.dataFactory.staging.bookSEReservation.EventDiningReservation;
import com.disney.utils.dataFactory.staging.bookSEReservation.ScheduledEventReservation;

public class TestModify extends BaseTest{
	// Defining global variables
	protected ScheduledEventReservation res = null;
	HouseHold hh = null;
	
	@BeforeClass(alwaysRun = true)
	@Parameters({ "environment" })
	public void setup(@Optional String environment){
		this.environment = environment;
		hh = new HouseHold(1);
		res = new EventDiningReservation(this.environment, hh);
		res.book(ScheduledEventReservation.NOCOMPONENTSNOADDONS);
	}
	

	@Test(groups = {"api", "regression", "dining", "eventDiningService"})
	public void testModify(){
		Modify modify = new Modify(this.environment, "NoComponentsNoAddOns");
		modify.setReservationNumber(res.getConfirmationNumber());
		modify.setTravelPlanId(res.getTravelPlanId());
		modify.setParty(res.party());
		modify.setFacilityId(res.getFacilityId());
		modify.setServiceStartDate(res.getServiceStartDate());
		modify.setServicePeriodId(res.getServicePeriodId());
		modify.setProductId(res.getProductId());
		modify.sendRequest();
		if(modify.getResponse().toLowerCase().contains("unique constraint")){
			Sleeper.sleep(Randomness.randomNumberBetween(1, 5) * 1000);
			modify.sendRequest();
		}
		TestReporter.logAPI(!modify.getResponseStatus().equals("SUCCESS"),"The Response status was not SUCCESS as expected", modify);
		

		LogItems logItems = new LogItems();
		logItems.addItem("ChargeGroupIF", "modifyGuestContainerChargeGroup", false);
		logItems.addItem("ChargeGroupIF", "modifyRootChargeGroup", false);
		logItems.addItem("EventDiningServiceIF", "modify", false);
		logItems.addItem("TravelPlanServiceCrossReferenceV3", "updateOrder", false);
		logItems.addItem("TravelPlanServiceCrossReferenceV3SEI", "updateOrder", false);
		validateLogs(modify, logItems);
	}
	

	@Test(groups = {"api", "regression", "dining", "eventDiningService"})
	public void testModifyTo2Adults(){
		HouseHold newParty = new HouseHold("2 Adults");
		ScheduledEventReservation originalRes = new EventDiningReservation(this.environment, hh);		
		originalRes.book(ScheduledEventReservation.NOCOMPONENTSNOADDONS);
		Modify modify = new Modify(this.environment, "NoComponentsNoAddOns");
		modify.setReservationNumber(originalRes.getConfirmationNumber());
		modify.setTravelPlanId(originalRes.getTravelPlanId());
		modify.setParty(newParty);
		modify.setFacilityId(originalRes.getFacilityId());
		modify.setServiceStartDate(originalRes.getServiceStartDate());
		modify.setServicePeriodId(originalRes.getServicePeriodId());
		modify.setProductId(originalRes.getProductId());
		modify.sendRequest();
		if(modify.getResponse().toLowerCase().contains("unique constraint")){
			Sleeper.sleep(Randomness.randomNumberBetween(1, 5) * 1000);
			modify.sendRequest();
		}
		TestReporter.logAPI(!modify.getResponseStatus().equals("SUCCESS"),"The Response status was not SUCCESS as expected", modify);
		

		LogItems logItems = new LogItems();
		logItems.addItem("ChargeGroupIF", "modifyGuestContainerChargeGroup", false);
		logItems.addItem("ChargeGroupIF", "modifyRootChargeGroup", false);
		logItems.addItem("EventDiningServiceIF", "modify", false);
		logItems.addItem("GuestServiceV1", "create", false);
		logItems.addItem("PartyIF", "updateExternalPartyAndLocatorId", false);
		logItems.addItem("TravelPlanServiceCrossReferenceV3", "updateOrder", false);
		logItems.addItem("TravelPlanServiceCrossReferenceV3SEI", "updateOrder", false);
		validateLogs(modify, logItems);
	}

	@Test(groups = {"api", "regression", "dining", "eventDiningService"})
	public void testModifyTo4Adults(){
		HouseHold newParty = new HouseHold("4 Adults");
		ScheduledEventReservation originalRes = new EventDiningReservation(this.environment, hh);		
		originalRes.book(ScheduledEventReservation.NOCOMPONENTSNOADDONS);
		Modify modify = new Modify(this.environment, "NoComponentsNoAddOns");
		modify.setReservationNumber(originalRes.getConfirmationNumber());
		modify.setTravelPlanId(originalRes.getTravelPlanId());
		modify.setParty(newParty);
		modify.setFacilityId(originalRes.getFacilityId());
		modify.setServiceStartDate(originalRes.getServiceStartDate());
		modify.setServicePeriodId(originalRes.getServicePeriodId());
		modify.setProductId(originalRes.getProductId());
		modify.sendRequest();
		if(modify.getResponse().toLowerCase().contains("unique constraint")){
			Sleeper.sleep(Randomness.randomNumberBetween(1, 5) * 1000);
			modify.sendRequest();
		}
		TestReporter.logAPI(!modify.getResponseStatus().equals("SUCCESS"),"The Response status was not SUCCESS as expected", modify);
		

		LogItems logItems = new LogItems();
		logItems.addItem("ChargeGroupIF", "modifyGuestContainerChargeGroup", false);
		logItems.addItem("ChargeGroupIF", "modifyRootChargeGroup", false);
		logItems.addItem("EventDiningServiceIF", "modify", false);
		logItems.addItem("GuestServiceV1", "create", false);
		logItems.addItem("PartyIF", "updateExternalPartyAndLocatorId", false);
		logItems.addItem("TravelPlanServiceCrossReferenceV3", "updateOrder", false);
		logItems.addItem("TravelPlanServiceCrossReferenceV3SEI", "updateOrder", false);
		validateLogs(modify, logItems);
	}
	

	@Test(groups = {"api", "regression", "dining", "eventDiningService"})
	public void testModifyTo2Adults2Child(){
		HouseHold newParty = new HouseHold("2 Adults 2 Child");
		ScheduledEventReservation originalRes = new EventDiningReservation(this.environment, hh);		
		originalRes.book(ScheduledEventReservation.NOCOMPONENTSNOADDONS);
		Modify modify = new Modify(this.environment, "NoComponentsNoAddOns");
		modify.setReservationNumber(originalRes.getConfirmationNumber());
		modify.setTravelPlanId(originalRes.getTravelPlanId());
		modify.setParty(newParty);
		modify.setFacilityId(originalRes.getFacilityId());
		modify.setServiceStartDate(originalRes.getServiceStartDate());
		modify.setServicePeriodId(originalRes.getServicePeriodId());
		modify.setProductId(originalRes.getProductId());
		modify.sendRequest();
		if(modify.getResponse().toLowerCase().contains("unique constraint")){
			Sleeper.sleep(Randomness.randomNumberBetween(1, 5) * 1000);
			modify.sendRequest();
		}
		TestReporter.logAPI(!modify.getResponseStatus().equals("SUCCESS"),"The Response status was not SUCCESS as expected", modify);
		

		LogItems logItems = new LogItems();
		logItems.addItem("ChargeGroupIF", "modifyGuestContainerChargeGroup", false);
		logItems.addItem("ChargeGroupIF", "modifyRootChargeGroup", false);
		logItems.addItem("EventDiningServiceIF", "modify", false);
		logItems.addItem("GuestServiceV1", "create", false);
		logItems.addItem("PartyIF", "updateExternalPartyAndLocatorId", false);
		logItems.addItem("TravelPlanServiceCrossReferenceV3", "updateOrder", false);
		logItems.addItem("TravelPlanServiceCrossReferenceV3SEI", "updateOrder", false);
		validateLogs(modify, logItems);
	}
	

	@Test(groups = {"api", "regression", "dining", "eventDiningService"})
	public void testModifyTo4Adults2Child2Infant(){
		HouseHold newParty = new HouseHold("4 Adults 2 Child 2 Infant");
		ScheduledEventReservation originalRes = new EventDiningReservation(this.environment, hh);		
		originalRes.book(ScheduledEventReservation.NOCOMPONENTSNOADDONS);
		Modify modify = new Modify(this.environment, "NoComponentsNoAddOns");
		modify.setReservationNumber(originalRes.getConfirmationNumber());
		modify.setTravelPlanId(originalRes.getTravelPlanId());
		modify.setParty(newParty);
		modify.setFacilityId(originalRes.getFacilityId());
		modify.setServiceStartDate(originalRes.getServiceStartDate());
		modify.setServicePeriodId(originalRes.getServicePeriodId());
		modify.setProductId(originalRes.getProductId());
		modify.sendRequest();
		if(modify.getResponse().toLowerCase().contains("unique constraint")){
			Sleeper.sleep(Randomness.randomNumberBetween(1, 5) * 1000);
			modify.sendRequest();
		}
		TestReporter.logAPI(!modify.getResponseStatus().equals("SUCCESS"),"The Response status was not SUCCESS as expected", modify);
		

		LogItems logItems = new LogItems();
		logItems.addItem("ChargeGroupIF", "modifyGuestContainerChargeGroup", false);
		logItems.addItem("ChargeGroupIF", "modifyRootChargeGroup", false);
		logItems.addItem("EventDiningServiceIF", "modify", false);
		logItems.addItem("GuestServiceV1", "create", false);
		logItems.addItem("PartyIF", "updateExternalPartyAndLocatorId", false);
		logItems.addItem("TravelPlanServiceCrossReferenceV3", "updateOrder", false);
		logItems.addItem("TravelPlanServiceCrossReferenceV3SEI", "updateOrder", false);
		validateLogs(modify, logItems);
	}
	

	@Test(groups = {"api", "regression", "dining", "eventDiningService"})
	public void testModifyTo12Adults(){
		HouseHold newParty = new HouseHold(12);
		ScheduledEventReservation originalRes = new EventDiningReservation(this.environment, hh);		
		originalRes.book(ScheduledEventReservation.NOCOMPONENTSNOADDONS);
		Modify modify = new Modify(this.environment, "NoComponentsNoAddOns");
		modify.setReservationNumber(originalRes.getConfirmationNumber());
		modify.setTravelPlanId(originalRes.getTravelPlanId());
		modify.setParty(newParty);
		modify.setFacilityId(originalRes.getFacilityId());
		modify.setServiceStartDate(originalRes.getServiceStartDate());
		modify.setServicePeriodId(originalRes.getServicePeriodId());
		modify.setProductId(originalRes.getProductId());
		modify.sendRequest();
		if(modify.getResponse().toLowerCase().contains("unique constraint")){
			Sleeper.sleep(Randomness.randomNumberBetween(1, 5) * 1000);
			modify.sendRequest();
		}
		TestReporter.logAPI(!modify.getResponseStatus().equals("SUCCESS"),"The Response status was not SUCCESS as expected", modify);
		

		LogItems logItems = new LogItems();
		logItems.addItem("ChargeGroupIF", "modifyGuestContainerChargeGroup", false);
		logItems.addItem("ChargeGroupIF", "modifyRootChargeGroup", false);
		logItems.addItem("EventDiningServiceIF", "modify", false);
		logItems.addItem("GuestServiceV1", "create", false);
		logItems.addItem("PartyIF", "updateExternalPartyAndLocatorId", false);
		logItems.addItem("TravelPlanServiceCrossReferenceV3", "updateOrder", false);
		logItems.addItem("TravelPlanServiceCrossReferenceV3SEI", "updateOrder", false);
		validateLogs(modify, logItems);
	}
	

	@Test(groups = {"api", "regression", "dining", "eventDiningService", "it4", "s138180" })
	public void testModifyAddAllergy(){
		Book book = new Book(environment, "NoComponentsNoAddOns");
		book.setParty(hh);		
		book.sendRequest();
		Modify modify = new Modify(this.environment, "NoComponentsNoAddOns");
		modify.setReservationNumber(book.getTravelPlanSegmentId());
		modify.setTravelPlanId(book.getTravelPlanId());
		modify.setParty(hh);
		modify.setFacilityId(book.getRequestFacilityId());
		modify.setServiceStartDate(book.getRequestServiceStartDate());
		modify.setServicePeriodId(book.getRequestServicePeriodId());
		modify.setProductId(book.getRequestProductId());
		modify.setAllergies("Egg", "1");
		modify.sendRequest();
		if(modify.getResponse().toLowerCase().contains("unique constraint")){
			Sleeper.sleep(Randomness.randomNumberBetween(1, 5) * 1000);
			modify.sendRequest();
		}
		TestReporter.logAPI(!modify.getResponseStatus().equals("SUCCESS"),"The Response status was not SUCCESS as expected", modify);
		

		LogItems logItems = new LogItems();
		logItems.addItem("ChargeGroupIF", "modifyGuestContainerChargeGroup", false);
		logItems.addItem("ChargeGroupIF", "modifyRootChargeGroup", false);
		logItems.addItem("EventDiningServiceIF", "modify", false);
		logItems.addItem("TravelPlanServiceCrossReferenceV3", "updateOrder", false);
		logItems.addItem("TravelPlanServiceCrossReferenceV3SEI", "updateOrder", false);
		validateLogs(modify, logItems);
	}

	@Test(groups = {"api", "regression", "dining", "eventDiningService", "it4", "s138180" })
	public void testModifyAddAdditionalAllergy(){
		Book book = new Book(environment, "NoComponentsNoAddOns");
		book.setParty(hh);		
		book.setAllergies("Egg", "1");
		book.sendRequest();
		Modify modify = new Modify(this.environment, "NoComponentsNoAddOns");
		modify.setReservationNumber(book.getTravelPlanSegmentId());
		modify.setTravelPlanId(book.getTravelPlanId());
		modify.setParty(hh);
		modify.setFacilityId(book.getRequestFacilityId());
		modify.setServiceStartDate(book.getRequestServiceStartDate());
		modify.setServicePeriodId(book.getRequestServicePeriodId());
		modify.setProductId(book.getRequestProductId());
		modify.setAllergies("Egg", "1");
		modify.setAllergies("Corn", "2");
		modify.sendRequest();
		if(modify.getResponse().toLowerCase().contains("unique constraint")){
			Sleeper.sleep(Randomness.randomNumberBetween(1, 5) * 1000);
			modify.sendRequest();
		}
		TestReporter.logAPI(!modify.getResponseStatus().equals("SUCCESS"),"The Response status was not SUCCESS as expected", modify);
		

		LogItems logItems = new LogItems();
		logItems.addItem("ChargeGroupIF", "modifyGuestContainerChargeGroup", false);
		logItems.addItem("ChargeGroupIF", "modifyRootChargeGroup", false);
		logItems.addItem("EventDiningServiceIF", "modify", false);
		logItems.addItem("TravelPlanServiceCrossReferenceV3", "updateOrder", false);
//		logItems.addItem("TravelPlanServiceCrossReferenceV3SEI", "updateOrder", false);
		validateLogs(modify, logItems);
	}
	
	@Test(groups = {"api", "regression", "dining", "eventDiningService", "it4", "s138180" })
	public void testModifyRemoveAllergy(){
		Book book = new Book(environment, "NoComponentsNoAddOns");
		book.setParty(hh);		
		book.setAllergies("Egg", "1");
		book.sendRequest();
		Modify modify = new Modify(this.environment, "NoComponentsNoAddOns");
		modify.setReservationNumber(book.getTravelPlanSegmentId());
		modify.setTravelPlanId(book.getTravelPlanId());
		modify.setParty(hh);
		modify.setFacilityId(book.getRequestFacilityId());
		modify.setServiceStartDate(book.getRequestServiceStartDate());
		modify.setServicePeriodId(book.getRequestServicePeriodId());
		modify.setProductId(book.getRequestProductId());
		modify.sendRequest();
		if(modify.getResponse().toLowerCase().contains("unique constraint")){
			Sleeper.sleep(Randomness.randomNumberBetween(1, 5) * 1000);
			modify.sendRequest();
		}
		TestReporter.logAPI(!modify.getResponseStatus().equals("SUCCESS"),"The Response status was not SUCCESS as expected", modify);
		

		LogItems logItems = new LogItems();
		logItems.addItem("ChargeGroupIF", "modifyGuestContainerChargeGroup", false);
		logItems.addItem("ChargeGroupIF", "modifyRootChargeGroup", false);
		logItems.addItem("EventDiningServiceIF", "modify", false);
		logItems.addItem("TravelPlanServiceCrossReferenceV3", "updateOrder", false);
		logItems.addItem("TravelPlanServiceCrossReferenceV3SEI", "updateOrder", false);
		validateLogs(modify, logItems);
	}
	
}
