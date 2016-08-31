package com.disney.composite.api.diningModule.tableServiceDiningService.modify;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.ServiceConstants;
import com.disney.api.soapServices.diningModule.tableServiceDiningServicePort.operations.Book;
import com.disney.api.soapServices.diningModule.tableServiceDiningServicePort.operations.Cancel;
import com.disney.api.soapServices.diningModule.tableServiceDiningServicePort.operations.Modify;
import com.disney.composite.BaseTest;
import com.disney.utils.Randomness;
import com.disney.utils.Regex;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.database.LogItems;
import com.disney.utils.dataFactory.guestFactory.HouseHold;
import com.disney.utils.dataFactory.staging.bookSEReservation.ScheduledEventReservation;
import com.disney.utils.dataFactory.staging.bookSEReservation.ShowDiningReservation;
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
	public void testModify_DLR(){
		Book book = new Book(environment, "DLRTableServiceOneChild");
		hh = new HouseHold(1);
		hh.primaryGuest().setAge("10");
		book.setParty(hh);
		book.sendRequest();

		TPS_ID.set(book.getTravelPlanSegmentId());
		TestReporter.logAPI(!book.getResponseStatusCode().contains("200"), book.getFaultString() ,book);
		TestReporter.assertTrue(Regex.match("[0-9]+", book.getTravelPlanId()), "The travel plan ID ["+book.getTravelPlanId()+"] is numeric as expected.");
		TestReporter.assertTrue(Regex.match("[0-9]+", book.getTravelPlanSegmentId()), "The reservation number ["+book.getTravelPlanSegmentId()+"] is numeric as expected.");
		
		Modify modify = new Modify(this.environment, "DLRTableServiceOneChild");
		modify.setParty(hh);
		modify.setReservationNumber(book.getTravelPlanSegmentId());
		modify.setTravelPlanId(book.getTravelPlanId());
		modify.setFacilityId(book.getRequestFacilityId());
		modify.setServiceStartDate(book.getRequestServiceStartDate());
		modify.setServicePeriodId(book.getRequestServicePeriodId());
		modify.sendRequest();
		TestReporter.logAPI(!modify.getResponseStatus().equals("SUCCESS"),"The Response status was not SUCCESS as expected", modify);		

		LogItems logItems = new LogItems();
		logItems.addItem("ChargeGroupIF", "modifyGuestContainerChargeGroup", false);
		logItems.addItem("ChargeGroupIF", "modifyRootChargeGroup", false);
		logItems.addItem("tableDiningServiceIF", "modify", false);
		logItems.addItem("TravelPlanServiceCrossReferenceV3", "updateOrder", false);
		logItems.addItem("TravelPlanServiceCrossReferenceV3SEI", "updateOrder", false);
		validateLogs(modify, logItems, 10000);
	}

	@Test(groups = {"api", "regression", "dining", "tableDiningService"})
	public void testReinstate_DLR(){
		Book book = new Book(environment, "DLRTableServiceOneChild");
		hh = new HouseHold(1);
		hh.primaryGuest().setAge("10");
		book.setParty(hh);
		book.sendRequest();

		TPS_ID.set(book.getTravelPlanSegmentId());
		TestReporter.logAPI(!book.getResponseStatusCode().contains("200"), book.getFaultString() ,book);
		TestReporter.assertTrue(Regex.match("[0-9]+", book.getTravelPlanId()), "The travel plan ID ["+book.getTravelPlanId()+"] is numeric as expected.");
		TestReporter.assertTrue(Regex.match("[0-9]+", book.getTravelPlanSegmentId()), "The reservation number ["+book.getTravelPlanSegmentId()+"] is numeric as expected.");
		
		Cancel cancel = new Cancel(environment, "Main");
		cancel.setReservationNumber(book.getTravelPlanSegmentId());
		cancel.sendRequest();
		TestReporter.logAPI(!cancel.getResponseStatusCode().contains("200"), cancel.getFaultString() ,cancel);
		
		Modify modify = new Modify(this.environment, "DLRTableServiceOneChild");
		modify.setParty(hh);
		modify.setReservationNumber(book.getTravelPlanSegmentId());
		modify.setTravelPlanId(book.getTravelPlanId());
		modify.setFacilityId(book.getRequestFacilityId());
		modify.setServiceStartDate(book.getRequestServiceStartDate());
		modify.setServicePeriodId(book.getRequestServicePeriodId());
		modify.sendRequest();
		TestReporter.logAPI(!modify.getResponseStatus().equals("SUCCESS"),"The Response status was not SUCCESS as expected", modify);
		

		LogItems logItems = new LogItems();
		logItems.addItem("ChargeGroupIF", "modifyGuestContainerChargeGroup", false);
		logItems.addItem("ChargeGroupIF", "modifyRootChargeGroup", false);
		logItems.addItem("tableDiningServiceIF", "modify", false);
		logItems.addItem("TravelPlanServiceCrossReferenceV3", "updateOrder", false);
		logItems.addItem("TravelPlanServiceCrossReferenceV3SEI", "updateOrder", false);
		validateLogs(modify, logItems, 10000);
	}
	@Test(groups = {"api", "regression", "dining", "tableDiningService"})
	public void testReinstate(){
		ScheduledEventReservation res2 = new TableServiceDiningReservation(this.environment, new HouseHold(1));
		res2.book(ScheduledEventReservation.NOCOMPONENTSNOADDONS);
		Modify modify = new Modify(this.environment, ScheduledEventReservation.NOCOMPONENTSNOADDONS);
		modify.setReservationNumber(res2.getConfirmationNumber());
		modify.setTravelPlanId(res2.getTravelPlanId());
		modify.setParty(res2.party());
		modify.setFacilityId(res2.getFacilityId());
		modify.setServiceStartDate(res2.getServiceStartDate());
		modify.setServicePeriodId(res2.getServicePeriodId());
		res2.cancel();
		modify.sendRequest();
		res2.retrieve();
		TestReporter.logAPI(!res2.getStatus().equals("Booked"), "Reservation status was not [Booked] instead [" + res2.getStatus() + "]", modify);
		TPS_ID.set(res2.getConfirmationNumber());
	}
	@Test(groups = {"api", "regression", "dining", "tableDiningService"})
	public void testModifyWithComments(){
		ScheduledEventReservation res2 = new TableServiceDiningReservation(this.environment, new HouseHold(1));
		res2.book(ScheduledEventReservation.NOCOMPONENTSNOADDONS);
		TPS_ID.set(res2.getConfirmationNumber());
		
		Modify modify = new Modify(this.environment, "NoComponentsNoAddOns");
		modify.setReservationNumber(res2.getConfirmationNumber());
		modify.setTravelPlanId(res2.getTravelPlanId());
		modify.setParty(res2.party());
		modify.setFacilityId(res2.getFacilityId());
		modify.setServiceStartDate(res2.getServiceStartDate());
		modify.setServicePeriodId(res2.getServicePeriodId());
		modify.setComments("Internal Comments", "Internal");
		modify.setReservableResourceId(res2.getReservableResourceId());
		modify.sendRequest();
		TestReporter.logAPI(!modify.getResponseStatus().equals("SUCCESS"),"The Response status was not SUCCESS as expected", modify);		

		LogItems logItems = new LogItems();
		logItems.addItem("ChargeGroupIF", "modifyGuestContainerChargeGroup", false);
		logItems.addItem("ChargeGroupIF", "modifyRootChargeGroup", false);
		logItems.addItem("tableDiningServiceIF", "modify", false);
		logItems.addItem("GuestServiceV1", "create", false);
		logItems.addItem("PartyIF", "updateExternalPartyAndLocatorId", false);
		logItems.addItem("TravelPlanServiceCrossReferenceV3", "updateOrder", false);
		logItems.addItem("TravelPlanServiceCrossReferenceV3SEI", "updateOrder", false);
		validateLogs(modify, logItems, 5000);
	}
	@Test(groups = {"api", "regression", "dining", "tableDiningService"})
	public void testModifyWithMultipleComments(){
		ScheduledEventReservation res2 = new TableServiceDiningReservation(this.environment, new HouseHold(1));
		res2.book(ScheduledEventReservation.NOCOMPONENTSNOADDONS);
		TPS_ID.set(res2.getConfirmationNumber());
		
		Modify modify = new Modify(this.environment, "NoComponentsNoAddOns");
		modify.setReservationNumber(res2.getConfirmationNumber());
		modify.setTravelPlanId(res2.getTravelPlanId());
		modify.setParty(res2.party());
		modify.setFacilityId(res2.getFacilityId());
		modify.setServiceStartDate(res2.getServiceStartDate());
		modify.setServicePeriodId(res2.getServicePeriodId());
		modify.setComments("Internal Comments", "Internal");
		modify.setComments("More Internal Comments", "External");
		modify.setReservableResourceId(res2.getReservableResourceId());
		modify.sendRequest();
		TestReporter.logAPI(!modify.getResponseStatus().equals("SUCCESS"),"The Response status was not SUCCESS as expected", modify);		

		LogItems logItems = new LogItems();
		logItems.addItem("ChargeGroupIF", "modifyGuestContainerChargeGroup", false);
		logItems.addItem("ChargeGroupIF", "modifyRootChargeGroup", false);
		logItems.addItem("tableDiningServiceIF", "modify", false);
		logItems.addItem("GuestServiceV1", "create", false);
		logItems.addItem("PartyIF", "updateExternalPartyAndLocatorId", false);
		logItems.addItem("TravelPlanServiceCrossReferenceV3", "updateOrder", false);
		logItems.addItem("TravelPlanServiceCrossReferenceV3SEI", "updateOrder", false);
		validateLogs(modify, logItems, 5000);
	}
	
	@Test(groups = {"api", "regression", "dining", "tableDiningService"})
	public void testModifyWithGuestRequests(){
		ScheduledEventReservation res2 = new TableServiceDiningReservation(this.environment, new HouseHold(1));
		res2.book(ScheduledEventReservation.NOCOMPONENTSNOADDONS);
		TPS_ID.set(res2.getConfirmationNumber());
		
		Modify modify = new Modify(this.environment, "NoComponentsNoAddOns");
		modify.setReservationNumber(res2.getConfirmationNumber());
		modify.setTravelPlanId(res2.getTravelPlanId());
		modify.setParty(res2.party());
		modify.setFacilityId(res2.getFacilityId());
		modify.setServiceStartDate(res2.getServiceStartDate());
		modify.setServicePeriodId(res2.getServicePeriodId());
		modify.setProfileDetailIdAndType(ServiceConstants.SeGuestRequests.BOOSTER_SEAT_ID, "GuestRequest");
		modify.setReservableResourceId(res2.getReservableResourceId());
		modify.sendRequest();
		TestReporter.logAPI(!modify.getResponseStatus().equals("SUCCESS"),"The Response status was not SUCCESS as expected", modify);		

		LogItems logItems = new LogItems();
		logItems.addItem("ChargeGroupIF", "modifyGuestContainerChargeGroup", false);
		logItems.addItem("ChargeGroupIF", "modifyRootChargeGroup", false);
		logItems.addItem("tableDiningServiceIF", "modify", false);
		logItems.addItem("GuestServiceV1", "create", false);
		logItems.addItem("PartyIF", "updateExternalPartyAndLocatorId", false);
		logItems.addItem("TravelPlanServiceCrossReferenceV3", "updateOrder", false);
		logItems.addItem("TravelPlanServiceCrossReferenceV3SEI", "updateOrder", false);
		validateLogs(modify, logItems, 5000);
	}
	
	@Test(groups = {"api", "regression", "dining", "tableDiningService"})
	public void testModifyWithMultipleGuestRequests(){
		ScheduledEventReservation res2 = new TableServiceDiningReservation(this.environment, new HouseHold(1));
		res2.book(ScheduledEventReservation.NOCOMPONENTSNOADDONS);
		TPS_ID.set(res2.getConfirmationNumber());
		
		Modify modify = new Modify(this.environment, "NoComponentsNoAddOns");
		modify.setReservationNumber(res2.getConfirmationNumber());
		modify.setTravelPlanId(res2.getTravelPlanId());
		modify.setParty(res2.party());
		modify.setFacilityId(res2.getFacilityId());
		modify.setServiceStartDate(res2.getServiceStartDate());
		modify.setServicePeriodId(res2.getServicePeriodId());
		modify.setProfileDetailIdAndType(ServiceConstants.SeGuestRequests.BOOSTER_SEAT_ID, "GuestRequest");
		modify.setProfileDetailIdAndType(ServiceConstants.SeGuestRequests.REQUEST_TWO_HIGH_CHAIRS_ID, "SecondGuestRequest");
		modify.setReservableResourceId(res2.getReservableResourceId());
		modify.sendRequest();
		TestReporter.logAPI(!modify.getResponseStatus().equals("SUCCESS"),"The Response status was not SUCCESS as expected", modify);		

		LogItems logItems = new LogItems();
		logItems.addItem("ChargeGroupIF", "modifyGuestContainerChargeGroup", false);
		logItems.addItem("ChargeGroupIF", "modifyRootChargeGroup", false);
		logItems.addItem("tableDiningServiceIF", "modify", false);
		logItems.addItem("GuestServiceV1", "create", false);
		logItems.addItem("PartyIF", "updateExternalPartyAndLocatorId", false);
		logItems.addItem("TravelPlanServiceCrossReferenceV3", "updateOrder", false);
		logItems.addItem("TravelPlanServiceCrossReferenceV3SEI", "updateOrder", false);
		validateLogs(modify, logItems, 5000);
	}
	
	@Test(groups = {"api", "regression", "dining", "tableDiningService"})
	public void testModifyWithGuestSpecialNeeds(){
		ScheduledEventReservation res2 = new TableServiceDiningReservation(this.environment, new HouseHold(1));
		res2.book(ScheduledEventReservation.NOCOMPONENTSNOADDONS);
		TPS_ID.set(res2.getConfirmationNumber());
		
		Modify modify = new Modify(this.environment, "NoComponentsNoAddOns");
		modify.setReservationNumber(res2.getConfirmationNumber());
		modify.setTravelPlanId(res2.getTravelPlanId());
		modify.setParty(res2.party());
		modify.setFacilityId(res2.getFacilityId());
		modify.setServiceStartDate(res2.getServiceStartDate());
		modify.setServicePeriodId(res2.getServicePeriodId());
		modify.setProfileDetailIdAndType(ServiceConstants.SeSpecialNeeds.HEARING_LOSS_ID, "SeSpecialNeed");
		modify.setReservableResourceId(res2.getReservableResourceId());
		modify.sendRequest();
		TestReporter.logAPI(!modify.getResponseStatus().equals("SUCCESS"),"The Response status was not SUCCESS as expected", modify);		

		LogItems logItems = new LogItems();
		logItems.addItem("ChargeGroupIF", "modifyGuestContainerChargeGroup", false);
		logItems.addItem("ChargeGroupIF", "modifyRootChargeGroup", false);
		logItems.addItem("tableDiningServiceIF", "modify", false);
		logItems.addItem("GuestServiceV1", "create", false);
		logItems.addItem("PartyIF", "updateExternalPartyAndLocatorId", false);
		logItems.addItem("TravelPlanServiceCrossReferenceV3", "updateOrder", false);
		logItems.addItem("TravelPlanServiceCrossReferenceV3SEI", "updateOrder", false);
		validateLogs(modify, logItems, 5000);
	}
	
	@Test(groups = {"api", "regression", "dining", "tableDiningService"})
	public void testModifyWithMultipleSpecialNeeds(){
		ScheduledEventReservation res2 = new TableServiceDiningReservation(this.environment, new HouseHold(1));
		res2.book(ScheduledEventReservation.NOCOMPONENTSNOADDONS);
		TPS_ID.set(res2.getConfirmationNumber());
		
		Modify modify = new Modify(this.environment, "NoComponentsNoAddOns");
		modify.setReservationNumber(res2.getConfirmationNumber());
		modify.setTravelPlanId(res2.getTravelPlanId());
		modify.setParty(res2.party());
		modify.setFacilityId(res2.getFacilityId());
		modify.setServiceStartDate(res2.getServiceStartDate());
		modify.setServicePeriodId(res2.getServicePeriodId());
		modify.setProfileDetailIdAndType(ServiceConstants.SeSpecialNeeds.HEARING_LOSS_ID, "SeSpecialNeed");
		modify.setProfileDetailIdAndType(ServiceConstants.SeSpecialNeeds.LIMITED_MOBILITY_ID, "SeSpecialNeed");
		modify.setProfileDetailIdAndType(ServiceConstants.SeSpecialNeeds.OXYGEN_TANK_USE_ID, "SeSpecialNeed");
		modify.setReservableResourceId(res2.getReservableResourceId());
		modify.sendRequest();
		TestReporter.logAPI(!modify.getResponseStatus().equals("SUCCESS"),"The Response status was not SUCCESS as expected", modify);		

		LogItems logItems = new LogItems();
		logItems.addItem("ChargeGroupIF", "modifyGuestContainerChargeGroup", false);
		logItems.addItem("ChargeGroupIF", "modifyRootChargeGroup", false);
		logItems.addItem("tableDiningServiceIF", "modify", false);
		logItems.addItem("GuestServiceV1", "create", false);
		logItems.addItem("PartyIF", "updateExternalPartyAndLocatorId", false);
		logItems.addItem("TravelPlanServiceCrossReferenceV3", "updateOrder", false);
		logItems.addItem("TravelPlanServiceCrossReferenceV3SEI", "updateOrder", false);
		validateLogs(modify, logItems, 5000);
	}
	
	@Test(groups = {"api", "regression", "dining", "tableDiningService"})
	public void testModifyAddTaxExempt(){

		ScheduledEventReservation res2 = new TableServiceDiningReservation(this.environment, new HouseHold(1));
		res2.book(ScheduledEventReservation.NOCOMPONENTSNOADDONS);
		TPS_ID.set(res2.getConfirmationNumber());
		
		Modify modify = new Modify(this.environment, "NoComponentsNoAddOns");
		modify.setReservationNumber(res2.getConfirmationNumber());
		modify.setTravelPlanId(res2.getTravelPlanId());
		modify.setParty(res2.party());
		modify.setServiceStartDate(res2.getServiceStartDate());
		modify.setServicePeriodId(res2.getServicePeriodId());
		modify.setReservableResourceId(res2.getReservableResourceId());
		modify.sendRequest();
		TestReporter.logAPI(!modify.getResponseStatus().equals("SUCCESS"),"The Response status was not SUCCESS as expected", modify);		

		LogItems logItems = new LogItems();
		logItems.addItem("ChargeGroupIF", "modifyGuestContainerChargeGroup", false);
		logItems.addItem("ChargeGroupIF", "modifyRootChargeGroup", false);
		logItems.addItem("tableDiningServiceIF", "modify", false);
		logItems.addItem("GuestServiceV1", "create", false);
		logItems.addItem("PartyIF", "updateExternalPartyAndLocatorId", false);
		logItems.addItem("TravelPlanServiceCrossReferenceV3", "updateOrder", false);
		logItems.addItem("TravelPlanServiceCrossReferenceV3SEI", "updateOrder", false);
		validateLogs(modify, logItems, 5000);
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
	@Test(groups = {"api", "regression", "dining", "tableDiningService", "it4", "s138180" })
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
		//modify.setProductId(book.getRequestProductId());
		modify.setAllergies("Egg", "1");
		modify.sendRequest();
		TestReporter.logAPI(!modify.getResponseStatus().equals("SUCCESS"),"The Response status was not SUCCESS as expected", modify);
		
	}

	@Test(groups = {"api", "regression", "dining", "tableDiningService", "it4", "s138180" })
	public void testModifyAddAdditionalAllergy(){
		Book book = new Book(environment, "NoComponentsNoAddOns");
		book.setParty(hh);		
		book.setAllergies("Egg");
		book.sendRequest();
		Modify modify = new Modify(this.environment, "NoComponentsNoAddOns");
		modify.setReservationNumber(book.getTravelPlanSegmentId());
		modify.setTravelPlanId(book.getTravelPlanId());
		modify.setParty(hh);
		modify.setFacilityId(book.getRequestFacilityId());
		modify.setServiceStartDate(book.getRequestServiceStartDate());
		modify.setServicePeriodId(book.getRequestServicePeriodId());
		//modify.setProductId(book.getRequestProductId());
		modify.setAllergies("Egg", "1");
		modify.setAllergies("Corn", "2");
		modify.sendRequest();
		TestReporter.logAPI(!modify.getResponseStatus().equals("SUCCESS"),"The Response status was not SUCCESS as expected", modify);
		
	}
	
	@Test(groups = {"api", "regression", "dining", "tableDiningService", "it4", "s138180" })
	public void testModifyRemoveAllergy(){
		Book book = new Book(environment, "NoComponentsNoAddOns");
		book.setParty(hh);		
		book.setAllergies("Egg");
		book.sendRequest();
		Modify modify = new Modify(this.environment, "NoComponentsNoAddOns");
		modify.setReservationNumber(book.getTravelPlanSegmentId());
		modify.setTravelPlanId(book.getTravelPlanId());
		modify.setParty(hh);
		modify.setFacilityId(book.getRequestFacilityId());
		modify.setServiceStartDate(book.getRequestServiceStartDate());
		modify.setServicePeriodId(book.getRequestServicePeriodId());
		//modify.setProductId(book.getRequestProductId());
		modify.sendRequest();
		TestReporter.logAPI(!modify.getResponseStatus().equals("SUCCESS"),"The Response status was not SUCCESS as expected", modify);
		
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
//		logItems.addItem("GuestLinkServiceV1SEI", "createEntitlementReference", false);
		logItems.addItem("PartyIF", "createAndRetrieveParty", false);
		logItems.addItem("PartyIF", "retrieveParties", false);
		logItems.addItem("PartyIF", "retrieveParty", false);
		logItems.addItem("AccommodationInventoryRequestComponentServiceIF", "retrieveAssignmentOwner", false);
		logItems.addItem("AccommodationInventoryRequestComponentServiceIF", "updateInventoryForScheduledEvents", false);
		logItems.addItem("FacilityMasterServiceSEI", "findFacilityByEnterpriseID", false);
//		logItems.addItem("GuestLinkServiceV1", "createEntitlementReference", false);
		logItems.addItem("GuestServiceV1", "create", false);
		logItems.addItem("PartyIF", "updateExternalPartyAndLocatorId", false);
		logItems.addItem("UpdateInventory", "updateInventory", false);
		validateLogs(modify, logItems);
	}
}
