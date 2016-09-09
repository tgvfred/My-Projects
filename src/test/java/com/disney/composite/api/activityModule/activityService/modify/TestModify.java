package com.disney.composite.api.activityModule.activityService.modify;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import com.disney.api.BaseTest;
import com.disney.api.soapServices.ServiceConstants;
import com.disney.api.soapServices.activityModule.activityServicePort.operations.Book;
import com.disney.api.soapServices.activityModule.activityServicePort.operations.Cancel;
import com.disney.api.soapServices.activityModule.activityServicePort.operations.Modify;
import com.disney.utils.Regex;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.database.LogItems;
import com.disney.utils.dataFactory.guestFactory.HouseHold;
import com.disney.utils.dataFactory.staging.bookSEReservation.ActivityEventReservation;
import com.disney.utils.dataFactory.staging.bookSEReservation.ScheduledEventReservation;

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
		ScheduledEventReservation originalRes = bookOriginalRes();
		Modify modify = new Modify(this.environment, ScheduledEventReservation.ONECOMPONENTSNOADDONS);
		originalRes.cancel();
		sendRequestAndValidateLogs(originalRes, modify, hh);
		originalRes.retrieve();
		TestReporter.logAPI(!originalRes.getStatus().equals("Booked"), "Reservation status was not [Booked] instead [" + originalRes.getStatus() + "]", modify);
	}
	@Test(groups = {"api", "regression", "activity", "activityService"})
	public void testModify(){
		TestReporter.logScenario("1 Adult");
		HouseHold newParty = new HouseHold(1);
		ScheduledEventReservation originalRes = bookOriginalRes();
		Modify modify = new Modify(this.environment, "NoComponentsNoAddOns");
		sendRequestAndValidateLogs(originalRes, modify,newParty);
	}

	@Test(groups = {"api", "regression", "activity", "activityService"})
	public void testModifyTo2Adults(){
		TestReporter.logScenario("2 Adults");
		HouseHold newParty = new HouseHold("2 Adults");
		ScheduledEventReservation originalRes = bookOriginalRes();
		Modify modify = new Modify(this.environment, "NoComponentsNoAddOns");
		sendRequestAndValidateLogs(originalRes, modify,newParty);
	}

	@Test(groups = {"api", "regression", "activity", "activityService"})
	public void testModifyTo4Adults(){
		TestReporter.logScenario("4 Adults");
		HouseHold newParty = new HouseHold("4 Adults");
		ScheduledEventReservation originalRes = bookOriginalRes();
		Modify modify = new Modify(this.environment, "NoComponentsNoAddOns");
		sendRequestAndValidateLogs(originalRes, modify,newParty);
	}	

	@Test(groups = {"api", "regression", "activity", "activityService"})
	public void testModifyTo2Adults2Child(){
		TestReporter.logScenario("2 Adults, 2 Children");
		HouseHold newParty = new HouseHold("2 Adults 2 Child");
		ScheduledEventReservation originalRes = bookOriginalRes();
		Modify modify = new Modify(this.environment, "NoComponentsNoAddOns");
		sendRequestAndValidateLogs(originalRes, modify,newParty);
	}	

	@Test(groups = {"api", "regression", "activity", "activityService"})
	public void testModifyTo4Adults2Child2Infant(){
		TestReporter.logScenario("4 Adults, 2 Children, 2 Infants");
		HouseHold newParty = new HouseHold("4 Adults 2 Child 2 Infant");
		ScheduledEventReservation originalRes = bookOriginalRes();
		Modify modify = new Modify(this.environment, "NoComponentsNoAddOns");
		sendRequestAndValidateLogs(originalRes, modify,newParty);
	}	

	@Test(groups = {"api", "regression", "activity", "activityService"})
	public void testModifyTo12Adults(){
		TestReporter.logScenario("12 Adults");
		HouseHold newParty = new HouseHold(12);
		ScheduledEventReservation originalRes = bookOriginalRes();

		Modify modify = new Modify(this.environment, "NoComponentsNoAddOns");
		sendRequestAndValidateLogs(originalRes, modify,newParty);
	}

	@Test(groups = {"api", "regression", "activity", "activityService"})
	public void testModify_DLR(){
		hh = new HouseHold(1);
		hh.primaryGuest().setAge("10");
		Book book = new Book(environment, "NoComponentsNoAddOns");
		book.setParty(hh);
		book.setFacilityId("2135034");
		book.setProductId("162265");
		book.setSourceAccountingCenter("5005");
		book.setProductType("RecreationActivityProduct");
		book.setReservableResourceId("CAF43AD9-459F-7044-E043-9906F4D17044");
		book.sendRequest();

		TPS_ID.set(book.getTravelPlanSegmentId());
		TestReporter.logAPI(!book.getResponseStatusCode().contains("200"), book.getFaultString() ,book);
		TestReporter.assertTrue(Regex.match("[0-9]+", book.getTravelPlanId()), "The travel plan ID ["+book.getTravelPlanId()+"] is numeric as expected.");
		TestReporter.assertTrue(Regex.match("[0-9]+", book.getTravelPlanSegmentId()), "The reservation number ["+book.getTravelPlanSegmentId()+"] is numeric as expected.");
		
		Modify modify = new Modify(this.environment, "NoComponentsNoAddOns");
		modify.setParty(hh);
		modify.setReservationNumber(book.getTravelPlanSegmentId());
		modify.setTravelPlanId(book.getTravelPlanId());
		modify.setFacilityId(book.getRequestFacilityId());
		modify.setServiceStartDate(book.getRequestServiceStartDate());
		modify.setServicePeriodId(book.getRequestServicePeriodId());
		modify.setSourceAccountingCenter("5005");
		modify.setProductType("RecreationActivityProduct");
		modify.setReservableResourceIdNew("CAF43AD9-459F-7044-E043-9906F4D17044");
		modify.sendRequest();
		TestReporter.logAPI(!modify.getStatus().equals("SUCCESS"),"The Response status was not SUCCESS as expected", modify);		

		LogItems logItems = new LogItems();
		logItems.addItem("ChargeGroupIF", "modifyGuestContainerChargeGroup", false);
		logItems.addItem("ChargeGroupIF", "modifyRootChargeGroup", false);
		logItems.addItem("tableDiningServiceIF", "modify", false);
		logItems.addItem("TravelPlanServiceCrossReferenceV3", "updateOrder", false);
		logItems.addItem("TravelPlanServiceCrossReferenceV3SEI", "updateOrder", false);
		validateLogs(modify, logItems, 10000);
	}

	@Test(groups = {"api", "regression", "activity", "activityService"})
	public void testReinstate_DLR(){
		hh = new HouseHold(1);
		hh.primaryGuest().setAge("10");
		Book book = new Book(environment, "NoComponentsNoAddOns");
		book.setParty(hh);
		book.setFacilityId("2135034");
		book.setProductId("162265");
		book.setSourceAccountingCenter("5005");
		book.setProductType("RecreationActivityProduct");
		book.setReservableResourceIdForError("CAF43AD9-459F-7044-E043-9906F4D17044");
		book.sendRequest();

		TPS_ID.set(book.getTravelPlanSegmentId());
		TestReporter.logAPI(!book.getResponseStatusCode().contains("200"), book.getFaultString() ,book);
		TestReporter.assertTrue(Regex.match("[0-9]+", book.getTravelPlanId()), "The travel plan ID ["+book.getTravelPlanId()+"] is numeric as expected.");
		TestReporter.assertTrue(Regex.match("[0-9]+", book.getTravelPlanSegmentId()), "The reservation number ["+book.getTravelPlanSegmentId()+"] is numeric as expected.");
		
		Cancel cancel = new Cancel(environment, "CancelDiningEvent");
		cancel.setReservationNumber(book.getTravelPlanSegmentId());
		cancel.sendRequest();
		TestReporter.logAPI(!cancel.getResponseStatusCode().equals("200"), "An error occurred during cancellation.", cancel);
		
		Modify modify = new Modify(this.environment, "NoComponentsNoAddOns");
		modify.setParty(hh);
		modify.setReservationNumber(book.getTravelPlanSegmentId());
		modify.setTravelPlanId(book.getTravelPlanId());
		modify.setFacilityId(book.getRequestFacilityId());
		modify.setServiceStartDate(book.getRequestServiceStartDate());
		modify.setServicePeriodId(book.getRequestServicePeriodId());
		modify.setSourceAccountingCenter("5005");
		modify.setProductType("RecreationActivityProduct");
		modify.setReservableResourceIdNew("CAF43AD9-459F-7044-E043-9906F4D17044");
		modify.sendRequest();
		TestReporter.logAPI(!modify.getStatus().equals("SUCCESS"),"The Response status was not SUCCESS as expected", modify);		

		LogItems logItems = new LogItems();
		logItems.addItem("ChargeGroupIF", "modifyGuestContainerChargeGroup", false);
		logItems.addItem("ChargeGroupIF", "modifyRootChargeGroup", false);
		logItems.addItem("tableDiningServiceIF", "modify", false);
		logItems.addItem("TravelPlanServiceCrossReferenceV3", "updateOrder", false);
		logItems.addItem("TravelPlanServiceCrossReferenceV3SEI", "updateOrder", false);
		validateLogs(modify, logItems, 10000);
	}
	@Test(groups = {"api", "regression", "dining", "eventDiningService"})
	public void testModifyWithComments(){
		ScheduledEventReservation originalRes = new ActivityEventReservation(this.environment, hh);
		originalRes.setFacilityName("Bay Lake Pool");
		originalRes.setProductName("Cabana - CO - 4 Hour Rental");
		originalRes.book(ScheduledEventReservation.NOCOMPONENTSNOADDONS);
		TPS_ID.set(originalRes.getConfirmationNumber());
		
		Modify modify = new Modify(this.environment, "NoComponentsNoAddOns");
		modify.setComments("Internal Comments", "Internal");
		sendRequestAndValidateLogs(originalRes, modify, hh);
	}
	@Test(groups = {"api", "regression", "dining", "eventDiningService"})
	public void testModifyWithMultipleComments(){
		ScheduledEventReservation originalRes = new ActivityEventReservation(this.environment, hh);
		originalRes.setFacilityName("Bay Lake Pool");
		originalRes.setProductName("Cabana - CO - 4 Hour Rental");
		originalRes.book(ScheduledEventReservation.NOCOMPONENTSNOADDONS);
		TPS_ID.set(originalRes.getConfirmationNumber());
		
		
		Modify modify = new Modify(this.environment, "NoComponentsNoAddOns");
		modify.setComments("Internal Comments", "Internal");
		modify.setComments("More Internal Comments", "External");
		sendRequestAndValidateLogs(originalRes, modify, hh);
	}
	
	@Test(groups = {"api", "regression", "dining", "eventDiningService"})
	public void testModifyWithGuestRequests(){
		ScheduledEventReservation originalRes = new ActivityEventReservation(this.environment, hh);
		originalRes.setFacilityName("Bay Lake Pool");
		originalRes.setProductName("Cabana - CO - 4 Hour Rental");
		originalRes.book(ScheduledEventReservation.NOCOMPONENTSNOADDONS);
		TPS_ID.set(originalRes.getConfirmationNumber());
		
		Modify modify = new Modify(this.environment, "NoComponentsNoAddOns");
		modify.setProfileDetailIdAndType(ServiceConstants.SeGuestRequests.BOOSTER_SEAT_ID, "GuestRequest");
		sendRequestAndValidateLogs(originalRes, modify, hh);
	}
	
	@Test(groups = {"api", "regression", "dining", "eventDiningService"})
	public void testModifyWithMultipleGuestRequests(){
		ScheduledEventReservation originalRes = new ActivityEventReservation(this.environment, hh);
		originalRes.setFacilityName("Bay Lake Pool");
		originalRes.setProductName("Cabana - CO - 4 Hour Rental");
		originalRes.book(ScheduledEventReservation.NOCOMPONENTSNOADDONS);
		TPS_ID.set(originalRes.getConfirmationNumber());
		
		Modify modify = new Modify(this.environment, "NoComponentsNoAddOns");
		modify.setProfileDetailIdAndType(ServiceConstants.SeGuestRequests.BOOSTER_SEAT_ID, "GuestRequest");
		modify.setProfileDetailIdAndType(ServiceConstants.SeGuestRequests.REQUEST_TWO_HIGH_CHAIRS_ID, "SecondGuestRequest");
		sendRequestAndValidateLogs(originalRes, modify, hh);
	}
	
	@Test(groups = {"api", "regression", "dining", "eventDiningService"})
	public void testModifyWithGuestSpecialNeeds(){
		ScheduledEventReservation originalRes = new ActivityEventReservation(this.environment, hh);
		originalRes.setFacilityName("Bay Lake Pool");
		originalRes.setProductName("Cabana - CO - 4 Hour Rental");
		originalRes.book(ScheduledEventReservation.NOCOMPONENTSNOADDONS);
		TPS_ID.set(originalRes.getConfirmationNumber());
				
		Modify modify = new Modify(this.environment, "NoComponentsNoAddOns");
		modify.setProfileDetailIdAndType(ServiceConstants.SeSpecialNeeds.HEARING_LOSS_ID, "SeSpecialNeed");
		sendRequestAndValidateLogs(originalRes, modify, hh);
	}
	
	@Test(groups = {"api", "regression", "dining", "eventDiningService"})
	public void testModifyWithMultipleSpecialNeeds(){
		ScheduledEventReservation originalRes = new ActivityEventReservation(this.environment, hh);
		originalRes.setFacilityName("Bay Lake Pool");
		originalRes.setProductName("Cabana - CO - 4 Hour Rental");
		originalRes.book(ScheduledEventReservation.NOCOMPONENTSNOADDONS);
		TPS_ID.set(originalRes.getConfirmationNumber());
		
		Modify modify = new Modify(this.environment, "NoComponentsNoAddOns");
		modify.setProfileDetailIdAndType(ServiceConstants.SeSpecialNeeds.HEARING_LOSS_ID, "SeSpecialNeed");
		modify.setProfileDetailIdAndType(ServiceConstants.SeSpecialNeeds.LIMITED_MOBILITY_ID, "SeSpecialNeed");
		modify.setProfileDetailIdAndType(ServiceConstants.SeSpecialNeeds.OXYGEN_TANK_USE_ID, "SeSpecialNeed");
		sendRequestAndValidateLogs(originalRes, modify, hh);
	}
	@Test(groups = {"api", "regression", "dining", "eventDiningService"})
	public void testModifyAddTaxExcempt(){
		ScheduledEventReservation originalRes = new ActivityEventReservation(this.environment, hh);
		originalRes.setFacilityName("Bay Lake Pool");
		originalRes.setProductName("Cabana - CO - 4 Hour Rental");
		originalRes.book(ScheduledEventReservation.NOCOMPONENTSNOADDONS);
		TPS_ID.set(originalRes.getConfirmationNumber());
		Modify modify = new Modify(this.environment, "NoComponentsNoAddOns");
		modify.setTaxExemptDetails("123456789", "Military");
		sendRequestAndValidateLogs(originalRes, modify, hh);
	}
	private ScheduledEventReservation bookOriginalRes(){
		ScheduledEventReservation originalRes = new ActivityEventReservation(this.environment, hh);
		originalRes.setFacilityName("Bay Lake Pool");
		originalRes.setProductName("Cabana - CO - 4 Hour Rental");
		originalRes.book(ScheduledEventReservation.NOCOMPONENTSNOADDONS);
		TPS_ID.set(originalRes.getConfirmationNumber());
		return originalRes;
	}
	
	private void sendRequestAndValidateLogs(ScheduledEventReservation localRes, Modify modify, HouseHold party){
		modify.setReservationNumber(localRes.getConfirmationNumber());
		modify.setTravelPlanId(localRes.getTravelPlanId());
		modify.setParty(party);
		modify.setFacilityId(localRes.getFacilityId());	
		modify.setProductId(localRes.getProductId());
		modify.setProductType(localRes.getProductType());		
		modify.setServiceStartDate(localRes.getServiceStartDate());
		modify.setServicePeriodId(localRes.getServicePeriodId());
		modify.setProductId(localRes.getProductId());
		modify.sendRequest();
		TestReporter.logAPI(!modify.getResponseStatusCode().equals("200"),"An error occurred during modification: " + modify.getFaultString(), modify);
		TestReporter.logAPI(!modify.getStatus().equals("SUCCESS"),"The Response status was not SUCCESS as expected: " + modify.getFaultString(), modify);		

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