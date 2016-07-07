package com.disney.composite.api.showDiningService.modify;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.showDiningService.operations.Book;
import com.disney.api.soapServices.showDiningService.operations.Cancel;
import com.disney.api.soapServices.showDiningService.operations.Modify;
import com.disney.composite.BaseTest;
import com.disney.utils.Randomness;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.database.LogItems;
import com.disney.utils.dataFactory.guestFactory.HouseHold;
import com.disney.utils.dataFactory.staging.bookSEReservation.ScheduledEventReservation;

/**
 * This class contains all fields, methods and classes required to test
 * 
 * @author Venkatesh A
 * @version 1/11/2016 Venkatesh A - Original
 */

public class TestModify_Negative extends BaseTest{
	protected HouseHold hh = null;
	protected Book book;
	
	@Override
	@BeforeTest(alwaysRun=true)
	@Parameters("environment")
	public void setup(@Optional String environment){
		this.environment = environment;
		hh = new HouseHold(1);
		book = new Book(environment, ScheduledEventReservation.ONECOMPONENTSNOADDONS);
		book.setParty(hh);
		book.sendRequest();
		TestReporter.logAPI(!book.getResponseStatusCode().equals("200"), "An error occurred during booking", book);
	}
	
	@AfterMethod
	public void teardown(){
		if(book != null)
			if(!book.getTravelPlanSegmentId().isEmpty()){
				Cancel cancel = new Cancel(environment, "CancelDiningEvent");
				cancel.setTravelPlanSegmentId(book.getTravelPlanSegmentId());
				cancel.sendRequest();
			}
	}	
	
//	@Test(groups = {"api", "regression", "dining", "showDiningService"})
	public void invalidAuthorizationNumber(){		
		TestReporter.logScenario("Invalid Authorization Number");
		Modify modify = modify(book);
		modify.setAuthorizationNumber("1");
		sendRequestAndValidateLogs(modify, "INVALID AUTHORIZATION CODE !! : INVALID AUTHORIZATION CODE !");
	}
//	@Test(groups = {"api", "regression", "dining", "showDiningService"})
	public void invalidBookDateExceeds180DaysInFuture(){		
		TestReporter.logScenario("Booking Date Exceeds 180 Days");
		Modify modify = modify(book);
		modify.setServiceStartDateTime(Randomness.generateCurrentXMLDatetime(181));
		sendRequestAndValidateLogs(modify, "RESManagement suggests to stop this reservation : Day Guest cannot book a Dining Reservation beyond 180 days from booking date");
	}
//	@Test(groups = {"api", "regression", "dining", "showDiningService"})
	public void invalidBookDateInPast(){		
		TestReporter.logScenario("Booking Date In The Past");
		Modify modify = modify(book);
		modify.setServiceStartDateTime(Randomness.generateCurrentXMLDatetime(-1));
		sendRequestAndValidateLogs(modify, "RESManagement suggests to stop this reservation : Book Date is greater than Service date");
	}
//	@Test(groups = {"api", "regression", "dining", "showDiningService"})
	public void invalidCommunicationChannel(){	
		TestReporter.logScenario("Invalid Communications Channel");
		Modify modify = modify(book);
		modify.setCommunicationsChannel("abcd");
		sendRequestAndValidateLogs(modify, "communication Channel is required : null");
	}
//	@Test(groups = {"api", "regression", "dining", "showDiningService"})
	public void invalidFacilityId(){	
		TestReporter.logScenario("Invalid Facility ID");
		Modify modify = modify(book);
		modify.setFacilityId("-1");
		sendRequestAndValidateLogs(modify, "FACILITY ID/NAME IS REQUIRED! : FACILITY ID IS REQUIRED!");
	}
//	@Test(groups = {"api", "regression", "dining", "showDiningService"})
	public void invalidPrimaryGuestCountry(){	
		TestReporter.logScenario("Invalid Primary Guest Country");
		Modify modify = modify(book);
		modify.setPrimaryGuestCountry("abcd");
		sendRequestAndValidateLogs(modify, "Create Party Error : Please enter valid country code");
	}
//	@Test(groups = {"api", "regression", "dining", "showDiningService"})
	public void invalidPrimaryGuestTitle(){
		TestReporter.logScenario("Invalid Primary Guest Title");
		Modify modify = modify(book);
		modify.setPrimaryGuestTitle("abcd");
		sendRequestAndValidateLogs(modify, "Salutation is invalid : Salutation abcd is invalid");
	}
//	@Test(groups = {"api", "regression", "dining", "showDiningService"})
	public void invalidProductId(){
		TestReporter.logScenario("Invalid Product ID");
		Modify modify = modify(book);
		modify.setProductId("-1");
		sendRequestAndValidateLogs(modify, "PRODUCT ID IS REQUIRED !! : DREAMS/ENTERPRISE PRODUCT ID IS REQUIRED!!");
	}
	@Test(groups = {"api", "regression", "dining", "showDiningService"})
	public void invalidSalesChannel(){
		TestReporter.logScenario("Invalid Sales Channel");
		Modify modify = modify(book);
		modify.setSalesChannel("abcd");
		sendRequestAndValidateLogs(modify, "Sales Channel is required : null");
	}
//	@Test(groups = {"api", "regression", "dining", "showDiningService"})
//	public void invalidTravelPlanId(){
//		
//	}
//	@Test(groups = {"api", "regression", "dining", "showDiningService"})
//	public void invalidTravelPlanIdFormat(){
//		
//	}
//	@Test(groups = {"api", "regression", "dining", "showDiningService"})
//	public void invalidReservationNumber(){
//		
//	}
//	@Test(groups = {"api", "regression", "dining", "showDiningService"})
//	public void invalidReservationNumberFormat(){
//		
//	}
//	@Test(groups = {"api", "regression", "dining", "showDiningService"})
//	public void missingCommunicationChannel(){
//		
//	}
//	@Test(groups = {"api", "regression", "dining", "showDiningService"})
//	public void missingFacilityId(){
//		
//	}
//	@Test(groups = {"api", "regression", "dining", "showDiningService"})
//	public void missingFreeze(){
//		
//	}
//	@Test(groups = {"api", "regression", "dining", "showDiningService"})
//	public void missingPartyRoleAgeType(){
//		
//	}
//	@Test(groups = {"api", "regression", "dining", "showDiningService"})
//	public void missingPartyRoles(){
//		
//	}
//	@Test(groups = {"api", "regression", "dining", "showDiningService"})
//	public void missingPrimaryGuest(){
//		
//	}
//	@Test(groups = {"api", "regression", "dining", "showDiningService"})
//	public void missingPrimaryGuestFirstName(){
//		
//	}
//	@Test(groups = {"api", "regression", "dining", "showDiningService"})
//	public void missingPrimaryGuestLastName(){
//		
//	}
//	@Test(groups = {"api", "regression", "dining", "showDiningService"})
//	public void missingProductId(){
//		
//	}
//	@Test(groups = {"api", "regression", "dining", "showDiningService"})
//	public void missingProductType(){
//		
//	}
//	@Test(groups = {"api", "regression", "dining", "showDiningService"})
//	public void missingReservableResourceID(){
//		
//	}
//	@Test(groups = {"api", "regression", "dining", "showDiningService"})
//	public void missingSalesChannel(){
//		
//	}
//	@Test(groups = {"api", "regression", "dining", "showDiningService"})
//	public void missingServicePeriodId(){
//		
//	}
//	@Test(groups = {"api", "regression", "dining", "showDiningService"})
//	public void missingServiceStartDate(){
//		
//	}
//	@Test(groups = {"api", "regression", "dining", "showDiningService"})
//	public void missingSourceAccountingCenter(){
//		
//	}
//	@Test(groups = {"api", "regression", "dining", "showDiningService"})
//	public void arrivedReservation(){
//		
//	}
//	@Test(groups = {"api", "regression", "dining", "showDiningService"})
//	public void noShowReservation(){
//		
//	}
	
	private Modify modify(Book book){
		Modify modify = new Modify(environment, ScheduledEventReservation.ONECOMPONENTSNOADDONS);
		modify.setParty(hh);
		modify.setTravelPlanId(book.getTravelPlanId());
		modify.setReservationnumber(book.getTravelPlanSegmentId());
		return modify;
	}
	
	private void sendRequestAndValidateLogs(Modify modify, String faultString){
		modify.sendRequest();
    	TestReporter.logAPI(!modify.getFaultString().contains(faultString), modify.getFaultString(), modify);
		logItems(modify);		
	}
	
	private void logItems(Modify modify){
		LogItems logValidItems = new LogItems();
		logValidItems.addItem("ShowDiningServiceIF", "modify", true);		
		validateLogs(modify, logValidItems);
		
		
		LogItems logInvalidItems = new LogItems();
		logInvalidItems.addItem("FolioServiceIF", "retrieveAccountingTransactions", false);
		logInvalidItems.addItem("TravelPlanServiceCrossReferenceV3SEI", "updateOrder", false);
		logInvalidItems.addItem("ChargeGroupIF", "modifyGuestContainedChargeGroup", false);
		logInvalidItems.addItem("ChargeGroupIF", "modifyGuestContainerChargeGroup", false);
		logInvalidItems.addItem("AccommodationInventoryRequestComponentServiceIF", "updateInventoryForScheduledEvents", false);
		logInvalidItems.addItem("AccommodationInventoryRequestComponentServiceIF", "retrieveAssignmentOwner", false);
		logInvalidItems.addItem("TravelPlanServiceCrossReferenceV3", "updateOrder", false);
		logInvalidItems.addItem("UpdateInventory", "updateInventory", false);
		validateNotInLogs(modify, logInvalidItems);
	}
}
