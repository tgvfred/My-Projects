package com.disney.composite.api.diningModule.showDiningService.modify;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.ServiceConstants;
import com.disney.api.soapServices.diningModule.showDiningService.operations.Book;
import com.disney.api.soapServices.diningModule.showDiningService.operations.Cancel;
import com.disney.api.soapServices.diningModule.showDiningService.operations.Modify;
import com.disney.composite.BaseTest;
import com.disney.utils.Randomness;
import com.disney.utils.Regex;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.database.LogItems;
import com.disney.utils.dataFactory.guestFactory.Guest;
import com.disney.utils.dataFactory.guestFactory.HouseHold;
import com.disney.utils.dataFactory.staging.bookSEReservation.EventDiningReservation;
import com.disney.utils.dataFactory.staging.bookSEReservation.ScheduledEventReservation;
import com.disney.utils.dataFactory.staging.bookSEReservation.ShowDiningReservation;

/**
 * This class contains all fields, methods and classes required to test
 * 
 * @author Venkatesh A
 * @version 1/11/2016 Venkatesh A - Original
 */

public class TestModify extends BaseTest{
	protected HouseHold hh = null;
	protected ThreadLocal<String> TPS_ID = new ThreadLocal<String>();
	
	@Override
	@BeforeMethod(alwaysRun=true)
	@Parameters("environment")
	public void setup(@Optional String environment){
		this.environment = environment;
		hh = new HouseHold(1);
	}
	
	@AfterMethod(alwaysRun = true)
	public void teardown(){
		try{
			Cancel cancel = new Cancel(environment, "CancelDiningEvent");
			cancel.setTravelPlanSegmentId(TPS_ID.get());
			cancel.sendRequest();
		}catch(Exception e){}
	}

	@Test(groups = {"api", "regression", "dining", "showDiningService"})
	public void testModify() {
		TestReporter.logScenario("Modify an existing reservation by increasing the party mix by 1 adult");
		HouseHold hh2 = new HouseHold(hh.getAllGuests().size() + 1);
		sendRequestAndvalidateLogs(book(), hh2);
	}	

	@Test(groups = {"api", "regression", "dining", "showDiningService"})
	public void testModify_DLR(){
		Book book = new Book(environment, "DLRDinnerShowOneAdultOneChild");
		book.setServiceStartDateTime(Randomness.generateCurrentXMLDate(1));
		
		book.sendRequest();

		TPS_ID.set(book.getTravelPlanSegmentId());
		TestReporter.logAPI(!book.getResponseStatusCode().contains("200"), book.getFaultString() ,book);
		TestReporter.assertTrue(Regex.match("[0-9]+", book.getTravelPlanId()), "The travel plan ID ["+book.getTravelPlanId()+"] is numeric as expected.");
		TestReporter.assertTrue(Regex.match("[0-9]+", book.getTravelPlanSegmentId()), "The reservation number ["+book.getTravelPlanSegmentId()+"] is numeric as expected.");
		
		Modify modify = new Modify(this.environment, "DLRDinnerShowOneAdultOneChild");
		modify.setReservationNumber(book.getTravelPlanSegmentId());
		modify.setTravelPlanId(book.getTravelPlanId());
		modify.setFacilityId(book.getRequestFacilityId());
		modify.setServiceStartDate(book.getRequestServiceStartDate());
		modify.setServicePeriodId(book.getRequestServicePeriodId());
		modify.setProductId(book.getRequestProductId());
		modify.sendRequest();
		TestReporter.logAPI(!modify.getResponseStatus().equals("SUCCESS"),"The Response status was not SUCCESS as expected", modify);
		

		LogItems logItems = new LogItems();
		logItems.addItem("ChargeGroupIF", "modifyGuestContainerChargeGroup", false);
		logItems.addItem("ChargeGroupIF", "modifyRootChargeGroup", false);
		logItems.addItem("ShowDiningServiceIF", "modify", false);
		logItems.addItem("TravelPlanServiceCrossReferenceV3", "updateOrder", false);
		logItems.addItem("TravelPlanServiceCrossReferenceV3SEI", "updateOrder", false);
		validateLogs(modify, logItems, 10000);
	}
	

	@Test(groups = {"api", "regression", "dining", "eventDiningService"})
	public void testReinstate_DLR(){
		Book book = new Book(environment, "DLRDinnerShowOneAdultOneChild");
		book.setParty(hh);
		book.setServiceStartDateTime(Randomness.generateCurrentXMLDate(1));
		book.sendRequest();
		TPS_ID.set(book.getTravelPlanSegmentId());
		
		Modify modify = new Modify(this.environment, "DLRDinnerShowOneAdultOneChild");
		modify.setReservationNumber(book.getTravelPlanSegmentId());
		modify.setTravelPlanId(book.getTravelPlanId());
		modify.setFacilityId(book.getRequestFacilityId());
		modify.setServiceStartDate(book.getRequestServiceStartDate());
		modify.setServicePeriodId(book.getRequestServicePeriodId());
		modify.setProductId(book.getRequestProductId());
		
		Cancel cancel = new Cancel(environment, "CancelDiningEvent");
		cancel.setTravelPlanSegmentId(book.getTravelPlanSegmentId());
		cancel.sendRequest();
		TestReporter.logAPI(!cancel.getResponseStatusCode().contains("200"), cancel.getFaultString() ,cancel);
		TestReporter.assertTrue(Regex.match("[0-9]+", cancel.getCancellationConfirmationNumber()), "The cancellation number ["+cancel.getCancellationConfirmationNumber()+"] was not numeric as expected.");

		
		modify.sendRequest();
		TestReporter.logAPI(!modify.getResponseStatus().equals("SUCCESS"),"The Response status was not SUCCESS as expected", modify);
		
	}
	@Test(groups = {"api", "regression", "dining", "showDiningService"})
	public void testReinstate(){
		ScheduledEventReservation res2 = new ShowDiningReservation(this.environment, new HouseHold(1));
		res2.book(ScheduledEventReservation.ONECOMPONENTSNOADDONS);
		TPS_ID.set(res2.getConfirmationNumber());
		
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
		TPS_ID.set(res2.getConfirmationNumber());
	}
	
	@Test(groups = {"api", "regression", "dining", "showDiningService"})
	public void testModifyWith2Adults(){
		TestReporter.logStep("Book a show dining reservation with 2 adults.");
		HouseHold hh2 = new HouseHold("2 Adults");
		sendRequestAndvalidateLogs(book(), hh2);
	}
	@Test(groups = {"api", "regression", "dining", "showDiningService"})
	public void testModifyWith4Adults(){
		TestReporter.logStep("Book a show dining reservation with 4 adults.");
		HouseHold hh2 = new HouseHold("4 Adults");
		sendRequestAndvalidateLogs(book(), hh2);
	}
	@Test(groups = {"api", "regression", "dining", "showDiningService"})
	public void testModifyWith2Adults2Child(){
		TestReporter.logStep("Book a show dining reservation with 2 adults and 2 children.");
		HouseHold hh2 = new HouseHold("2 Adults 2 Child");
		sendRequestAndvalidateLogs(book(), hh2);
	}
	@Test(groups = {"api", "regression", "dining", "showDiningService"})
	public void testModifyWith12InParty(){
		TestReporter.logStep("Book a show dining reservation with 12 adults.");
		HouseHold hh2 = new HouseHold(12);
		sendRequestAndvalidateLogs(book(), hh2);
	}
	
	@Test(groups = {"api", "regression", "dining", "showDiningService"})
	public void testModifyWithComments(){
		ScheduledEventReservation res2 = new ShowDiningReservation(this.environment, new HouseHold(1));
		res2.setFacilityName("Pioneer Hall");
		res2.setProductName("Hoop-Dee-Doo-Cat 2-1st Show");
		res2.book(ScheduledEventReservation.NOCOMPONENTSNOADDONS);
		TPS_ID.set(res2.getConfirmationNumber());
		
		Modify modify = new Modify(this.environment, "ONEComponentsNoAddOns");
		modify.setReservationNumber(res2.getConfirmationNumber());
		modify.setTravelPlanId(res2.getTravelPlanId());
		modify.setParty(res2.party());
		modify.setFacilityId(res2.getFacilityId());
		modify.setServiceStartDate(res2.getServiceStartDate());
		modify.setServicePeriodId(res2.getServicePeriodId());
		modify.setProductId(res2.getProductId());
		modify.setProductId(res2.getProductId());
		modify.addInternalComments("Internal Comments", "Internal");
		modify.setReservableResourceId(res2.getReservableResourceId());
		modify.sendRequest();
		TestReporter.logAPI(!modify.getResponseStatus().equals("SUCCESS"),"The Response status was not SUCCESS as expected", modify);		

		LogItems logItems = new LogItems();
		logItems.addItem("ChargeGroupIF", "modifyGuestContainerChargeGroup", false);
		logItems.addItem("ChargeGroupIF", "modifyRootChargeGroup", false);
		logItems.addItem("ShowDiningServiceIF", "modify", false);
		logItems.addItem("GuestServiceV1", "create", false);
		logItems.addItem("PartyIF", "updateExternalPartyAndLocatorId", false);
		logItems.addItem("TravelPlanServiceCrossReferenceV3", "updateOrder", false);
		logItems.addItem("TravelPlanServiceCrossReferenceV3SEI", "updateOrder", false);
		validateLogs(modify, logItems, 5000);
	}
	@Test(groups = {"api", "regression", "dining", "showDiningService"})
	public void testModifyWithMultipleComments(){
		ScheduledEventReservation res2 = new ShowDiningReservation(this.environment, new HouseHold(1));
		res2.setFacilityName("Pioneer Hall");
		res2.setProductName("Hoop-Dee-Doo-Cat 2-1st Show");
		res2.book(ScheduledEventReservation.NOCOMPONENTSNOADDONS);
		TPS_ID.set(res2.getConfirmationNumber());
		
		Modify modify = new Modify(this.environment, "ONEComponentsNoAddOns");
		modify.setReservationNumber(res2.getConfirmationNumber());
		modify.setTravelPlanId(res2.getTravelPlanId());
		modify.setParty(res2.party());
		modify.setFacilityId(res2.getFacilityId());
		modify.setServiceStartDate(res2.getServiceStartDate());
		modify.setServicePeriodId(res2.getServicePeriodId());
		modify.setProductId(res2.getProductId());
		modify.setProductId(res2.getProductId());
		modify.addInternalComments("Internal Comments", "Internal");
		modify.addInternalComments("More Internal Comments", "External");
		modify.setReservableResourceId(res2.getReservableResourceId());
		modify.sendRequest();
		TestReporter.logAPI(!modify.getResponseStatus().equals("SUCCESS"),"The Response status was not SUCCESS as expected", modify);		

		LogItems logItems = new LogItems();
		logItems.addItem("ChargeGroupIF", "modifyGuestContainerChargeGroup", false);
		logItems.addItem("ChargeGroupIF", "modifyRootChargeGroup", false);
		logItems.addItem("ShowDiningServiceIF", "modify", false);
		logItems.addItem("GuestServiceV1", "create", false);
		logItems.addItem("PartyIF", "updateExternalPartyAndLocatorId", false);
		logItems.addItem("TravelPlanServiceCrossReferenceV3", "updateOrder", false);
		logItems.addItem("TravelPlanServiceCrossReferenceV3SEI", "updateOrder", false);
		validateLogs(modify, logItems, 5000);
	}
	
	@Test(groups = {"api", "regression", "dining", "showDiningService"})
	public void testModifyWithGuestRequests(){
		ScheduledEventReservation res2 = new ShowDiningReservation(this.environment, new HouseHold(1));
		res2.setFacilityName("Pioneer Hall");
		res2.setProductName("Hoop-Dee-Doo-Cat 2-1st Show");
		res2.book(ScheduledEventReservation.NOCOMPONENTSNOADDONS);
		TPS_ID.set(res2.getConfirmationNumber());
		
		Modify modify = new Modify(this.environment, "ONEComponentsNoAddOns");
		modify.setReservationNumber(res2.getConfirmationNumber());
		modify.setTravelPlanId(res2.getTravelPlanId());
		modify.setParty(res2.party());
		modify.setFacilityId(res2.getFacilityId());
		modify.setServiceStartDate(res2.getServiceStartDate());
		modify.setServicePeriodId(res2.getServicePeriodId());
		modify.setProductId(res2.getProductId());
		modify.setProductId(res2.getProductId());
		modify.setProfileDetailIdAndType(ServiceConstants.SeGuestRequests.BOOSTER_SEAT_ID, "GuestRequest");
		modify.setReservableResourceId(res2.getReservableResourceId());
		modify.sendRequest();
		TestReporter.logAPI(!modify.getResponseStatus().equals("SUCCESS"),"The Response status was not SUCCESS as expected", modify);		

		LogItems logItems = new LogItems();
		logItems.addItem("ChargeGroupIF", "modifyGuestContainerChargeGroup", false);
		logItems.addItem("ChargeGroupIF", "modifyRootChargeGroup", false);
		logItems.addItem("ShowDiningServiceIF", "modify", false);
		logItems.addItem("GuestServiceV1", "create", false);
		logItems.addItem("PartyIF", "updateExternalPartyAndLocatorId", false);
		logItems.addItem("TravelPlanServiceCrossReferenceV3", "updateOrder", false);
		logItems.addItem("TravelPlanServiceCrossReferenceV3SEI", "updateOrder", false);
		validateLogs(modify, logItems, 5000);
	}
	
	@Test(groups = {"api", "regression", "dining", "showDiningService"})
	public void testModifyWithMultipleGuestRequests(){
		ScheduledEventReservation res2 = new ShowDiningReservation(this.environment, new HouseHold(1));
		res2.setFacilityName("Pioneer Hall");
		res2.setProductName("Hoop-Dee-Doo-Cat 2-1st Show");
		res2.book(ScheduledEventReservation.NOCOMPONENTSNOADDONS);
		TPS_ID.set(res2.getConfirmationNumber());
		
		Modify modify = new Modify(this.environment, "ONEComponentsNoAddOns");
		modify.setReservationNumber(res2.getConfirmationNumber());
		modify.setTravelPlanId(res2.getTravelPlanId());
		modify.setParty(res2.party());
		modify.setFacilityId(res2.getFacilityId());
		modify.setServiceStartDate(res2.getServiceStartDate());
		modify.setServicePeriodId(res2.getServicePeriodId());
		modify.setProductId(res2.getProductId());
		modify.setProductId(res2.getProductId());
		modify.setProfileDetailIdAndType(ServiceConstants.SeGuestRequests.BOOSTER_SEAT_ID, "GuestRequest");
		modify.setProfileDetailIdAndType(ServiceConstants.SeGuestRequests.REQUEST_TWO_HIGH_CHAIRS_ID, "SecondGuestRequest");
		modify.setReservableResourceId(res2.getReservableResourceId());
		modify.sendRequest();
		TestReporter.logAPI(!modify.getResponseStatus().equals("SUCCESS"),"The Response status was not SUCCESS as expected", modify);		

		LogItems logItems = new LogItems();
		logItems.addItem("ChargeGroupIF", "modifyGuestContainerChargeGroup", false);
		logItems.addItem("ChargeGroupIF", "modifyRootChargeGroup", false);
		logItems.addItem("ShowDiningServiceIF", "modify", false);
		logItems.addItem("GuestServiceV1", "create", false);
		logItems.addItem("PartyIF", "updateExternalPartyAndLocatorId", false);
		logItems.addItem("TravelPlanServiceCrossReferenceV3", "updateOrder", false);
		logItems.addItem("TravelPlanServiceCrossReferenceV3SEI", "updateOrder", false);
		validateLogs(modify, logItems, 5000);
	}
	
	@Test(groups = {"api", "regression", "dining", "showDiningService"})
	public void testModifyWithGuestSpecialNeeds(){
		ScheduledEventReservation res2 = new ShowDiningReservation(this.environment, new HouseHold(1));
		res2.setFacilityName("Pioneer Hall");
		res2.setProductName("Hoop-Dee-Doo-Cat 2-1st Show");
		res2.book(ScheduledEventReservation.NOCOMPONENTSNOADDONS);
		TPS_ID.set(res2.getConfirmationNumber());
		
		Modify modify = new Modify(this.environment, "ONEComponentsNoAddOns");
		modify.setReservationNumber(res2.getConfirmationNumber());
		modify.setTravelPlanId(res2.getTravelPlanId());
		modify.setParty(res2.party());
		modify.setFacilityId(res2.getFacilityId());
		modify.setServiceStartDate(res2.getServiceStartDate());
		modify.setServicePeriodId(res2.getServicePeriodId());
		modify.setProductId(res2.getProductId());
		modify.setProfileDetailIdAndType(ServiceConstants.SeSpecialNeeds.HEARING_LOSS_ID, "SeSpecialNeed");
		modify.setReservableResourceId(res2.getReservableResourceId());
		modify.sendRequest();
		TestReporter.logAPI(!modify.getResponseStatus().equals("SUCCESS"),"The Response status was not SUCCESS as expected", modify);		

		LogItems logItems = new LogItems();
		logItems.addItem("ChargeGroupIF", "modifyGuestContainerChargeGroup", false);
		logItems.addItem("ChargeGroupIF", "modifyRootChargeGroup", false);
		logItems.addItem("ShowDiningServiceIF", "modify", false);
		logItems.addItem("GuestServiceV1", "create", false);
		logItems.addItem("PartyIF", "updateExternalPartyAndLocatorId", false);
		logItems.addItem("TravelPlanServiceCrossReferenceV3", "updateOrder", false);
		logItems.addItem("TravelPlanServiceCrossReferenceV3SEI", "updateOrder", false);
		validateLogs(modify, logItems, 5000);
	}
	
	@Test(groups = {"api", "regression", "dining", "showDiningService"})
	public void testModifyWithMultipleSpecialNeeds(){
		ScheduledEventReservation res2 = new ShowDiningReservation(this.environment, new HouseHold(1));
		res2.setFacilityName("Pioneer Hall");
		res2.setProductName("Hoop-Dee-Doo-Cat 2-1st Show");
		res2.book(ScheduledEventReservation.NOCOMPONENTSNOADDONS);
		TPS_ID.set(res2.getConfirmationNumber());
		
		Modify modify = new Modify(this.environment, "ONEComponentsNoAddOns");
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
		modify.sendRequest();
		TestReporter.logAPI(!modify.getResponseStatus().equals("SUCCESS"),"The Response status was not SUCCESS as expected", modify);		

		LogItems logItems = new LogItems();
		logItems.addItem("ChargeGroupIF", "modifyGuestContainerChargeGroup", false);
		logItems.addItem("ChargeGroupIF", "modifyRootChargeGroup", false);
		logItems.addItem("ShowDiningServiceIF", "modify", false);
		logItems.addItem("GuestServiceV1", "create", false);
		logItems.addItem("PartyIF", "updateExternalPartyAndLocatorId", false);
		logItems.addItem("TravelPlanServiceCrossReferenceV3", "updateOrder", false);
		logItems.addItem("TravelPlanServiceCrossReferenceV3SEI", "updateOrder", false);
		validateLogs(modify, logItems, 5000);
	}
	
	@Test(groups = {"api", "regression", "dining", "showDiningService"})
	public void testModifyAddTaxExcempt(){

		ScheduledEventReservation res2 = new ShowDiningReservation(this.environment, new HouseHold(1));
		res2.setFacilityName("Pioneer Hall");
		res2.setProductName("Hoop-Dee-Doo-Cat 2-1st Show");
		res2.book(ScheduledEventReservation.NOCOMPONENTSNOADDONS);
		TPS_ID.set(res2.getConfirmationNumber());
		
		Modify modify = new Modify(this.environment, "NoComponentsNoAddOns");
		modify.setReservationNumber(res2.getConfirmationNumber());
		modify.setTravelPlanId(res2.getTravelPlanId());
		modify.setParty(res2.party());
		modify.setServiceStartDate(res2.getServiceStartDate());
		modify.setServicePeriodId(res2.getServicePeriodId());
		modify.addDetailsByFacilityNameAndProductName("Pioneer Hall", "Hoop-Dee-Doo-Cat 2-1st Show");
		modify.setTaxExemptDetails("123456789", "Military");
		modify.setReservableResourceId(res2.getReservableResourceId());
		modify.sendRequest();
		TestReporter.logAPI(!modify.getResponseStatus().equals("SUCCESS"),"The Response status was not SUCCESS as expected", modify);		

		LogItems logItems = new LogItems();
		logItems.addItem("ChargeGroupIF", "modifyGuestContainerChargeGroup", false);
		logItems.addItem("ChargeGroupIF", "modifyRootChargeGroup", false);
		logItems.addItem("ShowDiningServiceIF", "modify", false);
		logItems.addItem("GuestServiceV1", "create", false);
		logItems.addItem("PartyIF", "updateExternalPartyAndLocatorId", false);
		logItems.addItem("TravelPlanServiceCrossReferenceV3", "updateOrder", false);
		logItems.addItem("TravelPlanServiceCrossReferenceV3SEI", "updateOrder", false);
		validateLogs(modify, logItems, 5000);
	}
	@Test(groups = {"api", "regression", "dining", "showDiningService", "it4", "s138180" })
	public void testModifyAddGuest(){
		Book book = new Book(environment, ScheduledEventReservation.ONECOMPONENTSNOADDONS);
		book.setParty(hh);		
		book.sendRequest();
		TPS_ID.set(book.getTravelPlanSegmentId());
		
		TestReporter.logAPI(!book.getResponseStatusCode().equals("200"), "An error occurred booking a show dining reservation: " + book.getFaultString(), book);
		Modify modify = new Modify(this.environment, ScheduledEventReservation.ONECOMPONENTSNOADDONS);
		modify.setReservationNumber(book.getTravelPlanSegmentId());
		modify.setTravelPlanId(book.getTravelPlanId());
		hh.addGuest(new Guest());
		modify.setParty(hh);
		modify.setFacilityId(book.getRequestFacilityId());
		modify.setServiceStartDate(book.getRequestServiceStartDate());
		modify.setServicePeriodId(book.getRequestServicePeriodId());
		modify.setProductId(book.getRequestProductId());
		modify.setAllergies("Egg");
		modify.sendRequest();
		TestReporter.logAPI(!modify.getResponseStatus().equals("SUCCESS"),"The Response status was not SUCCESS as expected", modify);
		

		LogItems logItems = new LogItems();
		logItems.addItem("ChargeGroupIF", "modifyGuestContainerChargeGroup", false);
		logItems.addItem("ChargeGroupIF", "modifyRootChargeGroup", false);
		logItems.addItem("ShowDiningServiceIF", "modify", false);
		logItems.addItem("TravelPlanServiceCrossReferenceV3", "updateOrder", false);
		logItems.addItem("TravelPlanServiceCrossReferenceV3SEI", "updateOrder", false);
		validateLogs(modify, logItems, 10000);
	}
	@Test(groups = {"api", "regression", "dining", "showDiningService", "it4", "s138180" })
	public void testModifyAddAllergy(){
		Book book = new Book(environment, ScheduledEventReservation.ONECOMPONENTSNOADDONS);
		book.setParty(hh);		
		book.sendRequest();
		TPS_ID.set(book.getTravelPlanSegmentId());
		TestReporter.logAPI(!book.getResponseStatusCode().equals("200"), "An error occurred booking a show dining reservation: " + book.getFaultString(), book);
		Modify modify = new Modify(this.environment, ScheduledEventReservation.ONECOMPONENTSNOADDONS);
		modify.setReservationNumber(book.getTravelPlanSegmentId());
		modify.setTravelPlanId(book.getTravelPlanId());
		modify.setParty(hh);
		modify.setFacilityId(book.getRequestFacilityId());
		modify.setServiceStartDate(book.getRequestServiceStartDate());
		modify.setServicePeriodId(book.getRequestServicePeriodId());
		modify.setProductId(book.getRequestProductId());
		modify.setAllergies("Egg");
		modify.sendRequest();
		TestReporter.logAPI(!modify.getResponseStatus().equals("SUCCESS"),"The Response status was not SUCCESS as expected", modify);
		

		LogItems logItems = new LogItems();
		logItems.addItem("ChargeGroupIF", "modifyGuestContainerChargeGroup", false);
		logItems.addItem("ChargeGroupIF", "modifyRootChargeGroup", false);
		logItems.addItem("ShowDiningServiceIF", "modify", false);
		logItems.addItem("TravelPlanServiceCrossReferenceV3", "updateOrder", false);
		logItems.addItem("TravelPlanServiceCrossReferenceV3SEI", "updateOrder", false);
		validateLogs(modify, logItems, 10000);
	}

	@Test(groups = {"api", "regression", "dining", "showDiningService", "it4", "s138180" })
	public void testModifyAddAdditionalAllergy(){
		Book book = new Book(environment, ScheduledEventReservation.ONECOMPONENTSNOADDONS);
		book.setParty(hh);		
		book.setAllergies("Egg");
		book.sendRequest();
		TPS_ID.set(book.getTravelPlanSegmentId());
		TestReporter.logAPI(!book.getResponseStatusCode().equals("200"), "An error occurred booking a show dining reservation: " + book.getFaultString(), book);
		Modify modify = new Modify(this.environment, ScheduledEventReservation.ONECOMPONENTSNOADDONS);
		modify.setReservationNumber(book.getTravelPlanSegmentId());
		modify.setTravelPlanId(book.getTravelPlanId());
		modify.setParty(hh);
		modify.setFacilityId(book.getRequestFacilityId());
		modify.setServiceStartDate(book.getRequestServiceStartDate());
		modify.setServicePeriodId(book.getRequestServicePeriodId());
		modify.setProductId(book.getRequestProductId());
		modify.setAllergies("Egg");
		modify.setAllergies("Corn");
		modify.sendRequest();
		TestReporter.logAPI(!modify.getResponseStatus().equals("SUCCESS"),"The Response status was not SUCCESS as expected", modify);
		

		LogItems logItems = new LogItems();
		logItems.addItem("ChargeGroupIF", "modifyGuestContainerChargeGroup", false);
		logItems.addItem("ChargeGroupIF", "modifyRootChargeGroup", false);
		logItems.addItem("ShowDiningServiceIF", "modify", false);
		logItems.addItem("TravelPlanServiceCrossReferenceV3", "updateOrder", false);
		logItems.addItem("TravelPlanServiceCrossReferenceV3SEI", "updateOrder", false);
		validateLogs(modify, logItems, 10000);
	}
	
	@Test(groups = {"api", "regression", "dining", "showDiningService", "it4", "s138180" })
	public void testModifyRemoveAllergy(){
		Book book = new Book(environment, ScheduledEventReservation.ONECOMPONENTSNOADDONS);
		book.setParty(hh);		
		book.setAllergies("Egg");
		book.sendRequest();
		TPS_ID.set(book.getTravelPlanSegmentId());
		
		TestReporter.logAPI(!book.getResponseStatusCode().equals("200"), "An error occurred booking a show dining reservation: " + book.getFaultString(), book);
		Modify modify = new Modify(this.environment, ScheduledEventReservation.ONECOMPONENTSNOADDONS);
		modify.setReservationNumber(book.getTravelPlanSegmentId());
		modify.setTravelPlanId(book.getTravelPlanId());
		modify.setParty(hh);
		modify.setFacilityId(book.getRequestFacilityId());
		modify.setServiceStartDate(book.getRequestServiceStartDate());
		modify.setServicePeriodId(book.getRequestServicePeriodId());
		modify.setProductId(book.getRequestProductId());
		modify.sendRequest();
		TestReporter.logAPI(!modify.getResponseStatus().equals("SUCCESS"),"The Response status was not SUCCESS as expected", modify);
		

		LogItems logItems = new LogItems();
		logItems.addItem("ChargeGroupIF", "modifyGuestContainerChargeGroup", false);
		logItems.addItem("ChargeGroupIF", "modifyRootChargeGroup", false);
		logItems.addItem("ShowDiningServiceIF", "modify", false);
		validateLogs(modify, logItems, 10000);
	}
	
	private Book book(){
		Book book = new Book(environment, ScheduledEventReservation.ONECOMPONENTSNOADDONS);
		book.setParty(hh);
		book.sendRequest();
		TestReporter.logAPI(!book.getResponseStatusCode().equals("200"), "An error occurred during booking: " + book.getFaultString(), book);
		TPS_ID.set(book.getTravelPlanSegmentId());
		return book;
	}
	
	private void sendRequestAndvalidateLogs(Book book, HouseHold hh){
		TestReporter.log("Reservation Number: " + TPS_ID.get());
		Modify modify = new Modify(environment, ScheduledEventReservation.ONECOMPONENTSNOADDONS);
		modify.setTravelPlanId(book.getTravelPlanId());
		modify.setReservationNumber(TPS_ID.get());
		modify.setParty(hh);
		modify.sendRequest();
		TestReporter.logAPI(!modify.getResponseStatusCode().equals("200"), "An error occurred during modification: " + modify.getFaultString(), modify);
		TestReporter.assertEquals(modify.getResponseStatus(), "SUCCESS", "The status ["+modify.getResponseStatus()+"] was not 'SUCCESS' as expected.");
		
		LogItems logValidItems = new LogItems();
		logValidItems.addItem("ShowDiningServiceIF", "modify", false);
//		logValidItems.addItem("TravelPlanServiceCrossReferenceV3SEI", "updateOrder", false);
		logValidItems.addItem("ChargeGroupIF", "modifyGuestContainedChargeGroup", false);
		logValidItems.addItem("ChargeGroupIF", "modifyGuestContainerChargeGroup", false);
		logValidItems.addItem("PartyIF", "createAndRetrieveParty", false);
		logValidItems.addItem("PartyIF", "retrieveParties", false);
		logValidItems.addItem("PartyIF", "retrieveParty", false);
		logValidItems.addItem("AccommodationInventoryRequestComponentServiceIF", "updateInventoryForScheduledEvents", false);
		logValidItems.addItem("AccommodationInventoryRequestComponentServiceIF", "retrieveAssignmentOwner", false);
		logValidItems.addItem("FacilityMasterServiceSEI", "findFacilityByEnterpriseID", false);
		logValidItems.addItem("PackagingService", "getProducts", false);
		logValidItems.addItem("TravelPlanServiceCrossReferenceV3", "updateOrder", false);
		logValidItems.addItem("UpdateInventory", "updateInventory", false);
		validateLogs(modify, logValidItems, 10000);
	}
}
