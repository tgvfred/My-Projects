package com.disney.composite.api.diningModule.eventDiningService.modify;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.ServiceConstants;
import com.disney.api.soapServices.SoapException;
import com.disney.api.soapServices.diningModule.eventDiningService.operations.Book;
import com.disney.api.soapServices.diningModule.eventDiningService.operations.Cancel;
import com.disney.api.soapServices.diningModule.eventDiningService.operations.Modify;
import com.disney.composite.BaseTest;
import com.disney.test.utils.Sleeper;
import com.disney.utils.Randomness;
import com.disney.utils.Regex;
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
		res.setServiceStartDate(Randomness.generateCurrentXMLDatetime(Randomness.randomNumberBetween(30, 90)));
		try{
			res.book(ScheduledEventReservation.NOCOMPONENTSNOADDONS);
		}catch (SoapException se){
			res.book(ScheduledEventReservation.NOCOMPONENTSNOADDONS);
		}
	}
	
	@AfterClass(alwaysRun = true)
	public void cleanup(){
		try{
			res.cancel();
		}catch(Exception e ){}
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
		modify.setReservableResourceId(res.getReservableResourceId());
		modify.sendRequest(res.getReservableResourceId(), res.getServiceStartDate());
		if(modify.getResponse().toLowerCase().contains("unique constraint")){
			Sleeper.sleep(Randomness.randomNumberBetween(1, 5) * 1000);
			modify.sendRequest();
		}
		TestReporter.logAPI(!modify.getResponseStatus().equals("SUCCESS"),"The Response status was not SUCCESS as expected: " + modify.getFaultString(), modify);
		

		LogItems logItems = new LogItems();
		logItems.addItem("ChargeGroupIF", "modifyGuestContainerChargeGroup", false);
		logItems.addItem("ChargeGroupIF", "modifyRootChargeGroup", false);
		logItems.addItem("EventDiningServiceIF", "modify", false);
		logItems.addItem("TravelPlanServiceCrossReferenceV3", "updateOrder", false);
		logItems.addItem("TravelPlanServiceCrossReferenceV3SEI", "updateOrder", false);
		validateLogs(modify, logItems, 5000);
	}

	@Test(groups = {"api", "regression", "dining", "eventDiningService"})
	public void testModify_DLR(){
		Book book = new Book(environment, "DLRTableServiceOneChild");
		book.setParty(hh);
		book.setServiceStartDateTime(Randomness.generateCurrentXMLDate(Randomness.randomNumberBetween(15, 45)));
		book.sendRequest();
		Modify modify = new Modify(this.environment, "DLRRemoveAddOnOneChild");
		modify.setReservationNumber(book.getTravelPlanSegmentId());
		modify.setTravelPlanId(book.getTravelPlanId());
		modify.setFacilityId(book.getRequestFacilityId());
		modify.setServiceStartDate(book.getRequestServiceStartDate());
		modify.setServicePeriodId(book.getRequestServicePeriodId());
		modify.setProductId(book.getRequestProductId());
		modify.sendRequest(book.getReservableResourceId(), book.getRequestServiceStartDate());
		if(modify.getResponse().toLowerCase().contains("unique constraint")){
			Sleeper.sleep(Randomness.randomNumberBetween(1, 5) * 1000);
			modify.sendRequest();
		}
		TestReporter.logAPI(!modify.getResponseStatus().equals("SUCCESS"),"The Response status was not SUCCESS as expected: " + modify.getFaultString(), modify);
		

		LogItems logItems = new LogItems();
		logItems.addItem("ChargeGroupIF", "modifyGuestContainerChargeGroup", false);
		logItems.addItem("ChargeGroupIF", "modifyRootChargeGroup", false);
		logItems.addItem("EventDiningServiceIF", "modify", false);
		logItems.addItem("TravelPlanServiceCrossReferenceV3", "updateOrder", false);
		logItems.addItem("TravelPlanServiceCrossReferenceV3SEI", "updateOrder", false);
		validateLogs(modify, logItems, 5000);
	}
	
	@Test(groups = {"api", "regression", "dining", "eventDiningService"})
	public void testReinstate(){
		ScheduledEventReservation res2 = new EventDiningReservation(this.environment, new HouseHold(1));
		res2.book(ScheduledEventReservation.NOCOMPONENTSNOADDONS);
		String rrid = res2.getReservableResourceId();
		String dateTime = res2.getServiceStartDate();
		Modify modify = new Modify(this.environment, "NoComponentsNoAddOns");
		modify.setReservationNumber(res2.getConfirmationNumber());
		modify.setTravelPlanId(res2.getTravelPlanId());
		modify.setParty(res2.party());
		modify.setFacilityId(res2.getFacilityId());
		modify.setServiceStartDate(res2.getServiceStartDate());
		modify.setServicePeriodId(res2.getServicePeriodId());
		modify.setProductId(res2.getProductId());
		res2.cancel();
		modify.sendRequest(rrid, dateTime);
		TestReporter.logAPI(!modify.getResponseStatus().equals("SUCCESS"),"The Response status was not SUCCESS as expected: " + modify.getFaultString(), modify);
		res2.retrieve();
		TestReporter.logAPI(!res2.getStatus().equals("Booked"), "Reservation status was not [Booked] instead [" + res2.getStatus() + "]", modify);
		try{res2.cancel();}
		catch(Exception e){}
	}


	@Test(groups = {"api", "regression", "dining", "eventDiningService"})
	public void testReinstate_DLR(){
		Book book = new Book(environment, "DLRTableServiceOneChild");
		book.setParty(hh);
		book.setServiceStartDateTime(Randomness.generateCurrentXMLDate(1));
		book.sendRequest();
		Modify modify = new Modify(this.environment, "DLRRemoveAddOnOneChild");
		modify.setReservationNumber(book.getTravelPlanSegmentId());
		modify.setTravelPlanId(book.getTravelPlanId());
		modify.setFacilityId(book.getRequestFacilityId());
		modify.setServiceStartDate(book.getRequestServiceStartDate());
		modify.setServicePeriodId(book.getRequestServicePeriodId());
		modify.setProductId(book.getRequestProductId());
		
		Cancel cancel = new Cancel(environment, "CancelDiningEvent");
		cancel.setReservationNumber(book.getTravelPlanSegmentId());
		cancel.sendRequest();
		TestReporter.logAPI(!cancel.getResponseStatusCode().contains("200"), cancel.getFaultString() ,cancel);
		TestReporter.assertTrue(Regex.match("[0-9]+", cancel.getCancellationConfirmationNumber()), "The cancellation number ["+cancel.getCancellationConfirmationNumber()+"] was not numeric as expected.");

		modify.sendRequest(book.getReservableResourceId(), book.getRequestServiceStartDate());
		TestReporter.logAPI(!modify.getResponseStatus().equals("SUCCESS"),"The Response status was not SUCCESS as expected: " + modify.getFaultString(), modify);
		
	}
	
	@Test
	public void testModifyTrueDiningReservation(){
		Book book = new Book(environment, ScheduledEventReservation.NOCOMPONENTSNOADDONS);
		HouseHold party =new HouseHold(1);
		book.setParty(party);
		book.setServiceStartDateTime(Randomness.generateCurrentXMLDate(Randomness.randomNumberBetween(15, 45)));
		book.setReservableResourceId("BA054CBB-D573-C672-BE95-173042178DBE");
		book.addDetailsByFacilityNameAndProductName("The Hollywood Brown Derby", "Brown Derby Lunch F! 1st Show");
		book.addSpecialEventByProductName("Fantasmic! Viewing 1st Show");
		book.sendRequest();
		
		TestReporter.logAPI(!book.getResponseStatusCode().contains("200"), "An error occurred bookingan event dining reservation: " + book.getFaultString() ,book);
		TestReporter.assertTrue(Regex.match("[0-9]+", book.getTravelPlanId()), "The travel plan ID ["+book.getTravelPlanId()+"] is numeric as expected.");
		TestReporter.assertTrue(Regex.match("[0-9]+", book.getTravelPlanSegmentId()), "The reservation number ["+book.getTravelPlanSegmentId()+"] is numeric as expected.");
		Modify modify = new Modify(this.environment, "NoComponentsNoAddOns");
		modify.setParty(party);
		modify.setReservationNumber(book.getTravelPlanSegmentId());
		modify.setReservableResourceId(book.getRequestReservableResourceId());
		modify.setTravelPlanId(book.getTravelPlanId());
		modify.setFacilityId(book.getRequestFacilityId());
		modify.setServiceStartDate(book.getRequestServiceStartDate());
		modify.setServicePeriodId(book.getRequestServicePeriodId());
		modify.setProductId(book.getRequestProductId());
		modify.setEnterpriseProductId(book.getRequestEnterpriseProductId());
		modify.addSpecialEventByProductName("Fantasmic! Viewing 1st Show", "4cafc352-62d4-4e88-98f3-16474db25aa7");;
		modify.sendRequest(book.getReservableResourceId(), book.getRequestServiceStartDate());
		TestReporter.logAPI(!modify.getResponseStatus().equals("SUCCESS"),"The Response status was not SUCCESS as expected: " + modify.getFaultString(), modify);
	}
	@Test(groups = {"api", "regression", "dining", "eventDiningService"})
	public void testModifyWithComments(){
		ScheduledEventReservation res2 = new EventDiningReservation(this.environment, new HouseHold(1));
		res2.setFacilityName("Biergarten Restaurant");
		res2.setProductName("Biergarten Lunch");
		res2.book(ScheduledEventReservation.NOCOMPONENTSNOADDONS);
		
		Modify modify = new Modify(this.environment, "NoComponentsNoAddOns");
		modify.setReservationNumber(res2.getConfirmationNumber());
		modify.setTravelPlanId(res2.getTravelPlanId());
		modify.setParty(res2.party());
		modify.setFacilityId(res2.getFacilityId());
		modify.setServiceStartDate(res2.getServiceStartDate());
		modify.setServicePeriodId(res2.getServicePeriodId());
		modify.setProductId(res2.getProductId());
		modify.addInternalComments("Internal Comments", "Internal");
		modify.sendRequest(res2.getReservableResourceId(), res2.getServiceStartDate());
		TestReporter.logAPI(!modify.getResponseStatus().equals("SUCCESS"),"The Response status was not SUCCESS as expected: " + modify.getFaultString(), modify);		

		LogItems logItems = new LogItems();
		logItems.addItem("ChargeGroupIF", "modifyGuestContainerChargeGroup", false);
		logItems.addItem("ChargeGroupIF", "modifyRootChargeGroup", false);
		logItems.addItem("EventDiningServiceIF", "modify", false);
		logItems.addItem("GuestServiceV1", "create", false);
		logItems.addItem("PartyIF", "updateExternalPartyAndLocatorId", false);
		logItems.addItem("TravelPlanServiceCrossReferenceV3", "updateOrder", false);
		logItems.addItem("TravelPlanServiceCrossReferenceV3SEI", "updateOrder", false);
		validateLogs(modify, logItems, 5000);
		res2.cancel();
	}
	@Test(groups = {"api", "regression", "dining", "eventDiningService"})
	public void testModifyWithMultipleComments(){
		ScheduledEventReservation res2 = new EventDiningReservation(this.environment, new HouseHold(1));
		res2.setFacilityName("Biergarten Restaurant");
		res2.setProductName("Biergarten Lunch");
		res2.book(ScheduledEventReservation.NOCOMPONENTSNOADDONS);
		
		Modify modify = new Modify(this.environment, "NoComponentsNoAddOns");
		modify.setReservationNumber(res2.getConfirmationNumber());
		modify.setTravelPlanId(res2.getTravelPlanId());
		modify.setParty(res2.party());
		modify.setFacilityId(res2.getFacilityId());
		modify.setServiceStartDate(res2.getServiceStartDate());
		modify.setServicePeriodId(res2.getServicePeriodId());
		modify.setProductId(res2.getProductId());
		modify.addInternalComments("Internal Comments", "Internal");
		modify.addInternalComments("More Internal Comments", "External");
		modify.sendRequest(res2.getReservableResourceId(), res2.getServiceStartDate());
		TestReporter.logAPI(!modify.getResponseStatus().equals("SUCCESS"),"The Response status was not SUCCESS as expected: " + modify.getFaultString(), modify);		

		LogItems logItems = new LogItems();
		logItems.addItem("ChargeGroupIF", "modifyGuestContainerChargeGroup", false);
		logItems.addItem("ChargeGroupIF", "modifyRootChargeGroup", false);
		logItems.addItem("EventDiningServiceIF", "modify", false);
		logItems.addItem("GuestServiceV1", "create", false);
		logItems.addItem("PartyIF", "updateExternalPartyAndLocatorId", false);
		logItems.addItem("TravelPlanServiceCrossReferenceV3", "updateOrder", false);
		logItems.addItem("TravelPlanServiceCrossReferenceV3SEI", "updateOrder", false);
		validateLogs(modify, logItems, 5000);
		res2.cancel();
	}
	
	@Test(groups = {"api", "regression", "dining", "eventDiningService"})
	public void testModifyWithGuestRequests(){
		ScheduledEventReservation res2 = new EventDiningReservation(this.environment, new HouseHold(1));
		res2.setFacilityName("Biergarten Restaurant");
		res2.setProductName("Biergarten Lunch");
		res2.book(ScheduledEventReservation.NOCOMPONENTSNOADDONS);
		
		Modify modify = new Modify(this.environment, "NoComponentsNoAddOns");
		modify.setReservationNumber(res2.getConfirmationNumber());
		modify.setTravelPlanId(res2.getTravelPlanId());
		modify.setParty(res2.party());
		modify.setFacilityId(res2.getFacilityId());
		modify.setServiceStartDate(res2.getServiceStartDate());
		modify.setServicePeriodId(res2.getServicePeriodId());
		modify.setProductId(res2.getProductId());
		modify.setProfileDetailIdAndType(ServiceConstants.SeGuestRequests.BOOSTER_SEAT_ID, "GuestRequest");
		modify.sendRequest(res2.getReservableResourceId(), res2.getServiceStartDate());
		TestReporter.logAPI(!modify.getResponseStatus().equals("SUCCESS"),"The Response status was not SUCCESS as expected: " + modify.getFaultString(), modify);		

		LogItems logItems = new LogItems();
		logItems.addItem("ChargeGroupIF", "modifyGuestContainerChargeGroup", false);
		logItems.addItem("ChargeGroupIF", "modifyRootChargeGroup", false);
		logItems.addItem("EventDiningServiceIF", "modify", false);
		logItems.addItem("GuestServiceV1", "create", false);
		logItems.addItem("PartyIF", "updateExternalPartyAndLocatorId", false);
		logItems.addItem("TravelPlanServiceCrossReferenceV3", "updateOrder", false);
		logItems.addItem("TravelPlanServiceCrossReferenceV3SEI", "updateOrder", false);
		validateLogs(modify, logItems, 5000);
		res2.cancel();
	}
	
	@Test(groups = {"api", "regression", "dining", "eventDiningService"})
	public void testModifyWithMultipleGuestRequests(){
		ScheduledEventReservation res2 = new EventDiningReservation(this.environment, new HouseHold(1));
		res2.setFacilityName("Biergarten Restaurant");
		res2.setProductName("Biergarten Lunch");
		res2.book(ScheduledEventReservation.NOCOMPONENTSNOADDONS);
		
		Modify modify = new Modify(this.environment, "NoComponentsNoAddOns");
		modify.setReservationNumber(res2.getConfirmationNumber());
		modify.setTravelPlanId(res2.getTravelPlanId());
		modify.setParty(res2.party());
		modify.setFacilityId(res2.getFacilityId());
		modify.setServiceStartDate(res2.getServiceStartDate());
		modify.setServicePeriodId(res2.getServicePeriodId());
		modify.setProductId(res2.getProductId());
		modify.setProfileDetailIdAndType(ServiceConstants.SeGuestRequests.BOOSTER_SEAT_ID, "GuestRequest");
		modify.setProfileDetailIdAndType(ServiceConstants.SeGuestRequests.REQUEST_TWO_HIGH_CHAIRS_ID, "SecondGuestRequest");
		modify.sendRequest(res2.getReservableResourceId(), res2.getServiceStartDate());
		TestReporter.logAPI(!modify.getResponseStatus().equals("SUCCESS"),"The Response status was not SUCCESS as expected: " + modify.getFaultString(), modify);		

		LogItems logItems = new LogItems();
		logItems.addItem("ChargeGroupIF", "modifyGuestContainerChargeGroup", false);
		logItems.addItem("ChargeGroupIF", "modifyRootChargeGroup", false);
		logItems.addItem("EventDiningServiceIF", "modify", false);
		logItems.addItem("GuestServiceV1", "create", false);
		logItems.addItem("PartyIF", "updateExternalPartyAndLocatorId", false);
		logItems.addItem("TravelPlanServiceCrossReferenceV3", "updateOrder", false);
		logItems.addItem("TravelPlanServiceCrossReferenceV3SEI", "updateOrder", false);
		validateLogs(modify, logItems, 5000);
		res2.cancel();
	}
	
	@Test(groups = {"api", "regression", "dining", "eventDiningService"})
	public void testModifyWithGuestSpecialNeeds(){
		ScheduledEventReservation res2 = new EventDiningReservation(this.environment, new HouseHold(1));
		res2.setFacilityName("Biergarten Restaurant");
		res2.setProductName("Biergarten Lunch");
		res2.book(ScheduledEventReservation.NOCOMPONENTSNOADDONS);
		
		Modify modify = new Modify(this.environment, "NoComponentsNoAddOns");
		modify.setReservationNumber(res2.getConfirmationNumber());
		modify.setTravelPlanId(res2.getTravelPlanId());
		modify.setParty(res2.party());
		modify.setFacilityId(res2.getFacilityId());
		modify.setServiceStartDate(res2.getServiceStartDate());
		modify.setServicePeriodId(res2.getServicePeriodId());
		modify.setProductId(res2.getProductId());
		modify.setProfileDetailIdAndType(ServiceConstants.SeSpecialNeeds.HEARING_LOSS_ID, "SeSpecialNeed");
		modify.setReservableResourceId(res2.getReservableResourceId());
		modify.sendRequest(res2.getReservableResourceId(), res2.getServiceStartDate());
		TestReporter.logAPI(!modify.getResponseStatus().equals("SUCCESS"),"The Response status was not SUCCESS as expected: " + modify.getFaultString(), modify);		

		LogItems logItems = new LogItems();
		logItems.addItem("ChargeGroupIF", "modifyGuestContainerChargeGroup", false);
		logItems.addItem("ChargeGroupIF", "modifyRootChargeGroup", false);
		logItems.addItem("EventDiningServiceIF", "modify", false);
		logItems.addItem("GuestServiceV1", "create", false);
		logItems.addItem("PartyIF", "updateExternalPartyAndLocatorId", false);
		logItems.addItem("TravelPlanServiceCrossReferenceV3", "updateOrder", false);
		logItems.addItem("TravelPlanServiceCrossReferenceV3SEI", "updateOrder", false);
		validateLogs(modify, logItems, 5000);
		res2.cancel();
	}
	
	@Test(groups = {"api", "regression", "dining", "eventDiningService"})
	public void testModifyWithMultipleSpecialNeeds(){
		ScheduledEventReservation res2 = new EventDiningReservation(this.environment, new HouseHold(1));
		res2.setFacilityName("Biergarten Restaurant");
		res2.setProductName("Biergarten Lunch");
		res2.book(ScheduledEventReservation.NOCOMPONENTSNOADDONS);
		
		Modify modify = new Modify(this.environment, "NoComponentsNoAddOns");
		modify.setReservationNumber(res2.getConfirmationNumber());
		modify.setTravelPlanId(res2.getTravelPlanId());
		modify.setParty(res2.party());
		modify.setFacilityId(res2.getFacilityId());
		modify.setServiceStartDate(res2.getServiceStartDate());
		modify.setServicePeriodId(res2.getServicePeriodId());
		modify.setProductId(res2.getProductId());
		modify.setProfileDetailIdAndType(ServiceConstants.SeSpecialNeeds.HEARING_LOSS_ID, "SeSpecialNeed");
		modify.setProfileDetailIdAndType(ServiceConstants.SeSpecialNeeds.LIMITED_MOBILITY_ID, "SeSpecialNeed");
		modify.setProfileDetailIdAndType(ServiceConstants.SeSpecialNeeds.OXYGEN_TANK_USE_ID, "SeSpecialNeed");
		modify.setReservableResourceId(res2.getReservableResourceId());
		modify.sendRequest(res2.getReservableResourceId(), res2.getServiceStartDate());
		TestReporter.logAPI(!modify.getResponseStatus().equals("SUCCESS"),"The Response status was not SUCCESS as expected: " + modify.getFaultString(), modify);		

		LogItems logItems = new LogItems();
		logItems.addItem("ChargeGroupIF", "modifyGuestContainerChargeGroup", false);
		logItems.addItem("ChargeGroupIF", "modifyRootChargeGroup", false);
		logItems.addItem("EventDiningServiceIF", "modify", false);
		logItems.addItem("GuestServiceV1", "create", false);
		logItems.addItem("PartyIF", "updateExternalPartyAndLocatorId", false);
		logItems.addItem("TravelPlanServiceCrossReferenceV3", "updateOrder", false);
		logItems.addItem("TravelPlanServiceCrossReferenceV3SEI", "updateOrder", false);
		validateLogs(modify, logItems, 5000);
		res2.cancel();
	}
	@Test(groups = {"api", "regression", "dining", "eventDiningService"})
	public void testModifyAddTaxExcempt(){
		ScheduledEventReservation res2 = new EventDiningReservation(this.environment, new HouseHold(1));
		res2.setFacilityName("Biergarten Restaurant");
		res2.setProductName("Biergarten Lunch");
		res2.book(ScheduledEventReservation.NOCOMPONENTSNOADDONS);
		
		Modify modify = new Modify(this.environment, "NoComponentsNoAddOns");
		modify.setReservationNumber(res2.getConfirmationNumber());
		modify.setTravelPlanId(res2.getTravelPlanId());
		modify.setParty(res2.party());
		modify.setFacilityId(res2.getFacilityId());
		modify.setServiceStartDate(res2.getServiceStartDate());
		modify.setServicePeriodId(res2.getServicePeriodId());
		modify.setProductId(res2.getProductId());
		modify.setTaxExemptDetails("1234689", "Military");
		modify.setReservableResourceId(res2.getReservableResourceId());
		modify.sendRequest(res2.getReservableResourceId(), res2.getServiceStartDate());
		TestReporter.logAPI(!modify.getResponseStatus().equals("SUCCESS"),"The Response status was not SUCCESS as expected: " + modify.getFaultString(), modify);		

		LogItems logItems = new LogItems();
		logItems.addItem("ChargeGroupIF", "modifyGuestContainerChargeGroup", false);
		logItems.addItem("ChargeGroupIF", "modifyRootChargeGroup", false);
		logItems.addItem("EventDiningServiceIF", "modify", false);
		logItems.addItem("GuestServiceV1", "create", false);
		logItems.addItem("PartyIF", "updateExternalPartyAndLocatorId", false);
		logItems.addItem("TravelPlanServiceCrossReferenceV3", "updateOrder", false);
		logItems.addItem("TravelPlanServiceCrossReferenceV3SEI", "updateOrder", false);
		validateLogs(modify, logItems, 5000);
		res2.cancel();
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
		modify.sendRequest(originalRes.getReservableResourceId(), originalRes.getServiceStartDate());
		TestReporter.logAPI(!modify.getResponseStatus().equals("SUCCESS"),"The Response status was not SUCCESS as expected: " + modify.getFaultString(), modify);
		

		LogItems logItems = new LogItems();
		logItems.addItem("ChargeGroupIF", "modifyGuestContainerChargeGroup", false);
		logItems.addItem("ChargeGroupIF", "modifyRootChargeGroup", false);
		logItems.addItem("EventDiningServiceIF", "modify", false);
		logItems.addItem("GuestServiceV1", "create", false);
		logItems.addItem("PartyIF", "updateExternalPartyAndLocatorId", false);
		logItems.addItem("TravelPlanServiceCrossReferenceV3", "updateOrder", false);
		logItems.addItem("TravelPlanServiceCrossReferenceV3SEI", "updateOrder", false);
		validateLogs(modify, logItems, 5000);
		originalRes.cancel();
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
		modify.sendRequest(originalRes.getReservableResourceId(), originalRes.getServiceStartDate());
		if(modify.getResponse().toLowerCase().contains("unique constraint")){
			Sleeper.sleep(Randomness.randomNumberBetween(1, 5) * 1000);
			modify.sendRequest();
		}
		TestReporter.logAPI(!modify.getResponseStatus().equals("SUCCESS"),"The Response status was not SUCCESS as expected: " + modify.getFaultString(), modify);
		

		LogItems logItems = new LogItems();
		logItems.addItem("ChargeGroupIF", "modifyGuestContainerChargeGroup", false);
		logItems.addItem("ChargeGroupIF", "modifyRootChargeGroup", false);
		logItems.addItem("EventDiningServiceIF", "modify", false);
		logItems.addItem("GuestServiceV1", "create", false);
		logItems.addItem("PartyIF", "updateExternalPartyAndLocatorId", false);
		logItems.addItem("TravelPlanServiceCrossReferenceV3", "updateOrder", false);
		logItems.addItem("TravelPlanServiceCrossReferenceV3SEI", "updateOrder", false);
		validateLogs(modify, logItems, 5000);
		originalRes.cancel();
	}
	

	@Test(groups = {"api", "regression", "dining", "eventDiningService"})
	public void testModifyTo2Adults2Child(){
		HouseHold newParty = new HouseHold("2 Adults 2 Child");
		ScheduledEventReservation originalRes = new EventDiningReservation(this.environment, hh);
		originalRes.setServiceStartDate(Randomness.generateCurrentXMLDatetime(Randomness.randomNumberBetween(15, 45)));
		originalRes.book(ScheduledEventReservation.NOCOMPONENTSNOADDONS);
		Modify modify = new Modify(this.environment, "NoComponentsNoAddOns");
		modify.setReservationNumber(originalRes.getConfirmationNumber());
		modify.setTravelPlanId(originalRes.getTravelPlanId());
		modify.setParty(newParty);
		modify.setFacilityId(originalRes.getFacilityId());
		modify.setServiceStartDate(originalRes.getServiceStartDate());
		modify.setServicePeriodId(originalRes.getServicePeriodId());
		modify.setProductId(originalRes.getProductId());
		modify.sendRequest(originalRes.getReservableResourceId(), originalRes.getServiceStartDate());
		if(modify.getResponse().toLowerCase().contains("unique constraint")){
			Sleeper.sleep(Randomness.randomNumberBetween(1, 5) * 1000);
			modify.sendRequest();
		}
		TestReporter.logAPI(!modify.getResponseStatus().equals("SUCCESS"),"The Response status was not SUCCESS as expected: " + modify.getFaultString(), modify);
		

		LogItems logItems = new LogItems();
		logItems.addItem("ChargeGroupIF", "modifyGuestContainerChargeGroup", false);
		logItems.addItem("ChargeGroupIF", "modifyRootChargeGroup", false);
		logItems.addItem("EventDiningServiceIF", "modify", false);
		logItems.addItem("GuestServiceV1", "create", false);
		logItems.addItem("PartyIF", "updateExternalPartyAndLocatorId", false);
		logItems.addItem("TravelPlanServiceCrossReferenceV3", "updateOrder", false);
		logItems.addItem("TravelPlanServiceCrossReferenceV3SEI", "updateOrder", false);
		validateLogs(modify, logItems, 5000);
		originalRes.cancel();
	}
	

	@Test(groups = {"api", "regression", "dining", "eventDiningService"})
	public void testModifyTo4Adults2Child2Infant(){
		HouseHold newParty = new HouseHold("4 Adults 2 Child 2 Infant");
		ScheduledEventReservation originalRes = new EventDiningReservation(this.environment, hh);
		originalRes.setServiceStartDate(Randomness.generateCurrentXMLDatetime(Randomness.randomNumberBetween(15, 45)));		
		originalRes.book(ScheduledEventReservation.NOCOMPONENTSNOADDONS);
		Modify modify = new Modify(this.environment, "NoComponentsNoAddOns");
		modify.setReservationNumber(originalRes.getConfirmationNumber());
		modify.setTravelPlanId(originalRes.getTravelPlanId());
		modify.setParty(newParty);
		modify.setFacilityId(originalRes.getFacilityId());
		modify.setServiceStartDate(originalRes.getServiceStartDate());
		modify.setServicePeriodId(originalRes.getServicePeriodId());
		modify.setProductId(originalRes.getProductId());
		modify.sendRequest(originalRes.getReservableResourceId(), originalRes.getServiceStartDate());
		if(modify.getResponse().toLowerCase().contains("unique constraint")){
			Sleeper.sleep(Randomness.randomNumberBetween(1, 5) * 1000);
			modify.sendRequest();
		}
		TestReporter.logAPI(!modify.getResponseStatus().equals("SUCCESS"),"The Response status was not SUCCESS as expected: " + modify.getFaultString(), modify);
		

		LogItems logItems = new LogItems();
		logItems.addItem("ChargeGroupIF", "modifyGuestContainerChargeGroup", false);
		logItems.addItem("ChargeGroupIF", "modifyRootChargeGroup", false);
		logItems.addItem("EventDiningServiceIF", "modify", false);
		logItems.addItem("GuestServiceV1", "create", false);
		logItems.addItem("PartyIF", "updateExternalPartyAndLocatorId", false);
		logItems.addItem("TravelPlanServiceCrossReferenceV3", "updateOrder", false);
		logItems.addItem("TravelPlanServiceCrossReferenceV3SEI", "updateOrder", false);
		validateLogs(modify, logItems, 5000);
		originalRes.cancel();
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
		modify.sendRequest(originalRes.getReservableResourceId(), originalRes.getServiceStartDate());
		if(modify.getResponse().toLowerCase().contains("unique constraint")){
			Sleeper.sleep(Randomness.randomNumberBetween(1, 5) * 1000);
			modify.sendRequest();
		}
		TestReporter.logAPI(!modify.getResponseStatus().equals("SUCCESS"),"The Response status was not SUCCESS as expected: " + modify.getFaultString(), modify);
		

		LogItems logItems = new LogItems();
		logItems.addItem("ChargeGroupIF", "modifyGuestContainerChargeGroup", false);
		logItems.addItem("ChargeGroupIF", "modifyRootChargeGroup", false);
		logItems.addItem("EventDiningServiceIF", "modify", false);
		logItems.addItem("GuestServiceV1", "create", false);
		logItems.addItem("PartyIF", "updateExternalPartyAndLocatorId", false);
		logItems.addItem("TravelPlanServiceCrossReferenceV3", "updateOrder", false);
		logItems.addItem("TravelPlanServiceCrossReferenceV3SEI", "updateOrder", false);
		validateLogs(modify, logItems, 5000);
		originalRes.cancel();
	}
	

	@Test(groups = {"api", "regression", "dining", "eventDiningService", "it4", "s138180" })
	public void testModifyAddAllergy(){
		Book book = new Book(environment, "NoComponentsNoAddOns");
		book.setParty(hh);		
		book.addDetailsByFacilityNameAndProductName("Biergarten Restaurant", "Biergarten Lunch");
		book.sendRequest();
		Modify modify = new Modify(this.environment, "NoComponentsNoAddOns");
		modify.setReservationNumber(book.getTravelPlanSegmentId());
		modify.setTravelPlanId(book.getTravelPlanId());
		modify.setParty(hh);
		modify.setServiceStartDateTime(book.getRequestServiceStartDate());
		modify.addDetailsByFacilityNameAndProductName("Biergarten Restaurant", "Biergarten Dinner");
		modify.setAllergies("Egg");
		modify.sendRequest(book.getReservableResourceId(), book.getRequestServiceStartDate());
		if(modify.getResponse().toLowerCase().contains("unique constraint")){
			Sleeper.sleep(Randomness.randomNumberBetween(1, 5) * 1000);
			modify.sendRequest();
		}
		TestReporter.logAPI(!modify.getResponseStatus().equals("SUCCESS"),"The Response status was not SUCCESS as expected: " + modify.getFaultString(), modify);
		

		LogItems logItems = new LogItems();
		logItems.addItem("ChargeGroupIF", "modifyGuestContainerChargeGroup", false);
		logItems.addItem("ChargeGroupIF", "modifyRootChargeGroup", false);
		logItems.addItem("EventDiningServiceIF", "modify", false);
		logItems.addItem("TravelPlanServiceCrossReferenceV3", "updateOrder", false);
		logItems.addItem("TravelPlanServiceCrossReferenceV3SEI", "updateOrder", false);
		validateLogs(modify, logItems, 5000);
	}

	@Test(groups = {"api", "regression", "dining", "eventDiningService", "it4", "s138180" })
	public void testModifyAddAdditionalAllergy(){
		Book book = new Book(environment, "NoComponentsNoAddOns");
		book.setParty(hh);		
		book.setAllergies("Egg");
		book.setServiceStartDateTime(Randomness.generateCurrentXMLDatetime(Randomness.randomNumberBetween(15, 45)));	
		book.sendRequest();
		Modify modify = new Modify(this.environment, "NoComponentsNoAddOns");
		modify.setReservationNumber(book.getTravelPlanSegmentId());
		modify.setTravelPlanId(book.getTravelPlanId());
		modify.setParty(hh);
		modify.setFacilityId(book.getRequestFacilityId());
		modify.setServiceStartDate(book.getRequestServiceStartDate());
		modify.setServicePeriodId(book.getRequestServicePeriodId());
		modify.setProductId(book.getRequestProductId());
		modify.setAllergies("Egg");
		modify.setAllergies("Corn");
		modify.sendRequest(book.getReservableResourceId(), book.getRequestServiceStartDate());
		if(modify.getResponse().toLowerCase().contains("unique constraint")){
			Sleeper.sleep(Randomness.randomNumberBetween(1, 5) * 1000);
			modify.sendRequest();
		}
		TestReporter.logAPI(!modify.getResponseStatus().equals("SUCCESS"),"The Response status was not SUCCESS as expected: " + modify.getFaultString(), modify);
		

		LogItems logItems = new LogItems();
		logItems.addItem("ChargeGroupIF", "modifyGuestContainerChargeGroup", false);
		logItems.addItem("ChargeGroupIF", "modifyRootChargeGroup", false);
		logItems.addItem("EventDiningServiceIF", "modify", false);
		logItems.addItem("TravelPlanServiceCrossReferenceV3", "updateOrder", false);
//		logItems.addItem("TravelPlanServiceCrossReferenceV3SEI", "updateOrder", false);
		validateLogs(modify, logItems, 5000);
	}
	
	@Test(groups = {"api", "regression", "dining", "eventDiningService", "it4", "s138180" })
	public void testModifyRemoveAllergy(){
		Book book = new Book(environment, "NoComponentsNoAddOns");
		book.setParty(hh);		
		book.setAllergies("Egg");
		book.sendRequest();
		TestReporter.logAPI(!book.getResponseStatusCode().equals("200"), "An error occurred during the prereq booking: " + book.getFaultString(), book);
		Modify modify = new Modify(this.environment, "NoComponentsNoAddOns");
		modify.setReservationNumber(book.getTravelPlanSegmentId());
		modify.setTravelPlanId(book.getTravelPlanId());
		modify.setParty(hh);
		modify.setFacilityId(book.getRequestFacilityId());
		modify.setServiceStartDate(book.getRequestServiceStartDate());
		modify.setServicePeriodId(book.getRequestServicePeriodId());
		modify.setProductId(book.getRequestProductId());
		modify.sendRequest(book.getReservableResourceId(), book.getRequestServiceStartDate());
		if(modify.getResponse().toLowerCase().contains("unique constraint")){
			Sleeper.sleep(Randomness.randomNumberBetween(1, 5) * 1000);
			modify.sendRequest();
		}
		TestReporter.logAPI(!modify.getResponseStatus().equals("SUCCESS"),"The Response status was not SUCCESS as expected: " + modify.getFaultString(), modify);
		

		LogItems logItems = new LogItems();
		logItems.addItem("ChargeGroupIF", "modifyGuestContainerChargeGroup", false);
		logItems.addItem("ChargeGroupIF", "modifyRootChargeGroup", false);
		logItems.addItem("EventDiningServiceIF", "modify", false);
		logItems.addItem("TravelPlanServiceCrossReferenceV3", "updateOrder", false);
		logItems.addItem("TravelPlanServiceCrossReferenceV3SEI", "updateOrder", false);
		validateLogs(modify, logItems, 5000);
	}
	
}
