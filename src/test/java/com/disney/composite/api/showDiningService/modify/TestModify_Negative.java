package com.disney.composite.api.showDiningService.modify;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.core.BaseSoapCommands;
import com.disney.api.soapServices.showDiningService.operations.Arrived;
import com.disney.api.soapServices.showDiningService.operations.Book;
import com.disney.api.soapServices.showDiningService.operations.Cancel;
import com.disney.api.soapServices.showDiningService.operations.Modify;
import com.disney.api.soapServices.showDiningService.operations.NoShow;
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
	protected ThreadLocal<LogItems> logValidItems = new ThreadLocal<LogItems>();
	protected ThreadLocal<String> TPS_ID = new ThreadLocal<String>();
	
	@BeforeTest(alwaysRun=true)
	@Parameters("environment")
	public void testSetup(@Optional String environment){
		this.environment = environment;
		hh = new HouseHold(1);
		logValidItems.set(new LogItems());
	}
	
	@Override
	@BeforeMethod(alwaysRun=true)
	@Parameters("environment")
	public void setup(@Optional String environment){
		logValidItems.set(new LogItems());
	}
	
	@AfterMethod
	public void teardown(){
		if(TPS_ID.get() != null)
			if(!TPS_ID.get().isEmpty()){
				Cancel cancel = new Cancel(environment, "CancelDiningEvent");
				cancel.setTravelPlanSegmentId(TPS_ID.get());
				cancel.sendRequest();
			}
	}	
	
	@Test(groups = {"api", "regression", "dining", "showDiningService", "negative"})
	public void invalidAuthorizationNumber(){		
		TestReporter.logScenario("Invalid Authorization Number");
		Modify modify = modify(book());
		modify.setAuthorizationNumber("1");
		sendRequestAndValidateLogs(modify, "INVALID AUTHORIZATION CODE !! : INVALID AUTHORIZATION CODE !");
	}
	@Test(groups = {"api", "regression", "dining", "showDiningService", "negative"})
	public void invalidBookDateExceeds180DaysInFuture(){		
		TestReporter.logScenario("Booking Date Exceeds 180 Days");
		Modify modify = modify(book());
		modify.setServiceStartDateTime(Randomness.generateCurrentXMLDatetime(181));
		sendRequestAndValidateLogs(modify, "RESManagement suggests to stop this reservation : Day Guest cannot book a Dining Reservation beyond 180 days from booking date");
	}
	@Test(groups = {"api", "regression", "dining", "showDiningService", "negative"})
	public void invalidBookDateInPast(){		
		TestReporter.logScenario("Booking Date In The Past");
		Modify modify = modify(book());
		modify.setServiceStartDateTime(Randomness.generateCurrentXMLDatetime(-1));
		sendRequestAndValidateLogs(modify, "RESManagement suggests to stop this reservation : Book Date is greater than Service date");
	}
	@Test(groups = {"api", "regression", "dining", "showDiningService", "negative"})
	public void invalidCommunicationChannel(){	
		TestReporter.logScenario("Invalid Communications Channel");
		Modify modify = modify(book());
		modify.setCommunicationsChannel("abcd");
		sendRequestAndValidateLogs(modify, "communication Channel is required : null");
	}
	@Test(groups = {"api", "regression", "dining", "showDiningService", "negative"})
	public void invalidFacilityId(){	
		TestReporter.logScenario("Invalid Facility ID");
		Modify modify = modify(book());
		modify.setFacilityId("-1");
		sendRequestAndValidateLogs(modify, "FACILITY ID/NAME IS REQUIRED! : FACILITY ID IS REQUIRED!");
	}
	@Test(groups = {"api", "regression", "dining", "showDiningService", "negative"})
	public void invalidPrimaryGuestCountry(){	
		TestReporter.logScenario("Invalid Primary Guest Country");
		Modify modify = modify(book());
		modify.setPrimaryGuestCountry("abcd");
		sendRequestAndValidateLogs(modify, "Create Party Error : Please enter valid country code");
	}
	@Test(groups = {"api", "regression", "dining", "showDiningService", "negative"})
	public void invalidPrimaryGuestTitle(){
		TestReporter.logScenario("Invalid Primary Guest Title");
		Modify modify = modify(book());
		modify.setPrimaryGuestTitle("abcd");
		sendRequestAndValidateLogs(modify, "Salutation is invalid : Salutation abcd is invalid");
	}
	@Test(groups = {"api", "regression", "dining", "showDiningService", "negative"})
	public void invalidProductId(){
		TestReporter.logScenario("Invalid Product ID");
		Modify modify = modify(book());
		modify.setProductId("-1");
		sendRequestAndValidateLogs(modify, "PRODUCT ID IS REQUIRED !! : DREAMS/ENTERPRISE PRODUCT ID IS REQUIRED!!");
	}
	@Test(groups = {"api", "regression", "dining", "showDiningService", "negative"})
	public void invalidSalesChannel(){
		TestReporter.logScenario("Invalid Sales Channel");
		Modify modify = modify(book());
		modify.setSalesChannel("abcd");
		sendRequestAndValidateLogs(modify, "Sales Channel is required : null");
	}
	@Test(groups = {"api", "regression", "dining", "showDiningService", "negative"})
	public void invalidReservationNumber(){
		String number = "-1";
		TestReporter.logScenario("Invalid Reservation Number");
		Modify modify = modify(book());
		modify.setReservationNumber(number);
		sendRequestAndValidateLogs(modify, "RECORD NOT FOUND : NO RESERVATION FOUND WITH "+number);
	}
	@Test(groups = {"api", "regression", "dining", "showDiningService", "negative"})
	public void missingCommunicationChannel(){
		TestReporter.logScenario("Missing Communication Channel");
		Modify modify = modify(book());
		modify.setCommunicationsChannel(BaseSoapCommands.REMOVE_NODE.toString());
		sendRequestAndValidateLogs(modify, "communication Channel is required : null");
	}
	@Test(groups = {"api", "regression", "dining", "showDiningService", "negative"})
	public void missingFacilityId(){
		TestReporter.logScenario("Missing Facility ID");
		Modify modify = modify(book());
		modify.setFacilityId(BaseSoapCommands.REMOVE_NODE.toString());
		sendRequestAndValidateLogs(modify, "FACILITY ID/NAME IS REQUIRED! : FACILITY ID IS REQUIRED!");
	}
	@Test(groups = {"api", "regression", "dining", "showDiningService", "negative"})
	public void missingPartyRoleAgeType(){
		TestReporter.logScenario("Missing Party Role Age Type");
		Modify modify = modify(book());
		modify.setPartyRoleAgeType(BaseSoapCommands.REMOVE_NODE.toString());
		sendRequestAndValidateLogs(modify, "Age Type is required : AGE TYPE IS REQUIRED.");
	}
	@Test(groups = {"api", "regression", "dining", "showDiningService", "negative"})
	public void missingPartyRoles(){
		TestReporter.logScenario("Missing Party Role");
		Modify modify = modify(book());
		modify.setRequestNodeValueByXPath("/Envelope/Body/modify/modifyShowDiningRequest/dinnerShowPackage/partyRoles", BaseSoapCommands.REMOVE_NODE.toString());
		sendRequestAndValidateLogs(modify, "Invalid PartyMix. Please send valid partymix : INVALID PARTY SIZE.");
	}
	@Test(groups = {"api", "regression", "dining", "showDiningService", "negative"})
	public void missingPrimaryGuest(){
		TestReporter.logScenario("Missing Primary Guest");
		Modify modify = modify(book());
		modify.setRequestNodeValueByXPath("/Envelope/Body/modify/modifyShowDiningRequest/primaryGuest", BaseSoapCommands.REMOVE_NODE.toString());
		sendRequestAndValidateLogs(modify, "Travel Plan Guest is required : TRAVEL PLAN GUEST REQUIRED");
	}
	@Test(groups = {"api", "regression", "dining", "showDiningService", "negative"})
	public void missingPrimaryGuestFirstName(){
		TestReporter.logScenario("Missing Primary Guest First Name");
		Modify modify = modify(book());
		modify.setPrimaryGuestFirstName(BaseSoapCommands.REMOVE_NODE.toString());
		sendRequestAndValidateLogs(modify, "Travel Plan Guest is required : TRAVEL PLAN GUEST REQUIRED");
	}
	@Test(groups = {"api", "regression", "dining", "showDiningService", "negative"})
	public void missingPrimaryGuestLastName(){
		TestReporter.logScenario("Missing Primary Guest Last Name");
		Modify modify = modify(book());
		modify.setPrimaryGuestLastName(BaseSoapCommands.REMOVE_NODE.toString());
		sendRequestAndValidateLogs(modify, "Travel Plan Guest is required : TRAVEL PLAN GUEST REQUIRED");
	}
	@Test(groups = {"api", "regression", "dining", "showDiningService", "negative"})
	public void missingProductId(){
		TestReporter.logScenario("Missing Product ID");
		Modify modify = modify(book());
		modify.setProductId(BaseSoapCommands.REMOVE_NODE.toString());
		sendRequestAndValidateLogs(modify, "PRODUCT ID IS REQUIRED !! : DREAMS/ENTERPRISE PRODUCT ID IS REQUIRED!!");
	}
	@Test(groups = {"api", "regression", "dining", "showDiningService", "negative"})
	public void missingProductType(){
		TestReporter.logScenario("Missing Product Type");
		Modify modify = modify(book());
		modify.setProductType(BaseSoapCommands.REMOVE_NODE.toString());
		sendRequestAndValidateLogs(modify, "PRODUCT TYPE NAME IS REQUIRED!! : PRODUCT TYPE NAME IS REQUIRED!!");
	}
	@Test(groups = {"api", "regression", "dining", "showDiningService", "negative"})
	public void missingReservableResourceID(){
		TestReporter.logScenario("Missing Reservable Resource ID");
		Modify modify = modify(book());
		modify.setReservableResourceId(BaseSoapCommands.REMOVE_NODE.toString());
		logValidItems.get().addItem("ChargeGroupIF", "modifyGuestContainerChargeGroup", false);	
		sendRequestAndValidateLogs(modify, "RESERVABLE RESOURCE ID IS REQUIRED! : RESERVABLE RESOURCE ID IS REQUIRED!");
	}
	@Test(groups = {"api", "regression", "dining", "showDiningService", "negative"})
	public void missingSalesChannel(){
		TestReporter.logScenario("Missing Sales Channel");
		Modify modify = modify(book());
		modify.setSalesChannel(BaseSoapCommands.REMOVE_NODE.toString());
		sendRequestAndValidateLogs(modify, "Sales Channel is required : null");
	}
	@Test(groups = {"api", "regression", "dining", "showDiningService", "negative"})
	public void missingServicePeriodId(){
		TestReporter.logScenario("Missing Service Period ID");
		Modify modify = modify(book());
		modify.setServicePeriodId(BaseSoapCommands.REMOVE_NODE.toString());
		sendRequestAndValidateLogs(modify, "ENTERPRISE SERVICE PERIOD ID IS REQUIRED.! : ENTERPRISE SERVICE PERIOD ID IS REQUIRED.");
	}
	@Test(groups = {"api", "regression", "dining", "showDiningService", "negative"})
	public void missingServiceStartDate(){
		TestReporter.logScenario("Missing Service Start Date");
		Modify modify = modify(book());
		modify.setServiceStartDateTime(BaseSoapCommands.REMOVE_NODE.toString());
		sendRequestAndValidateLogs(modify, "INVALID  SERVICE START DATE!! : INVALID SERVICE START DATE!!");
	}
	@Test(groups = {"api", "regression", "dining", "showDiningService", "negative"})
	public void arrivedReservation(){
		Book book = new Book(environment, ScheduledEventReservation.ONECOMPONENTSNOADDONS);
		book.setParty(hh);
		book.sendRequest();
		TestReporter.logAPI(!book.getResponseStatusCode().equals("200"), "An error occurred during booking", book);
		
		Arrived arrived = new Arrived(environment, "ContactCenter");
		arrived.setReservationNumber(book.getTravelPlanSegmentId());
		arrived.sendRequest();
		TestReporter.logAPI(!arrived.getResponseStatusCode().equals("200"), "An error occurred setting the reservation to [Arrived]", arrived);
		
		TestReporter.logScenario("Arrived Reservation Status");
		Modify modify = modify(book);
		sendRequestAndValidateLogs(modify, "Travel Status is invalid  : INVALID RESERVATION STATUS.");
	}
	@Test(groups = {"api", "regression", "dining", "showDiningService", "negative"})
	public void noShowReservation(){
		Book book = new Book(environment, ScheduledEventReservation.ONECOMPONENTSNOADDONS);
		book.setParty(hh);
		book.sendRequest();
		TestReporter.logAPI(!book.getResponseStatusCode().equals("200"), "An error occurred during booking", book);
		
		NoShow noShow = new NoShow(environment, "ContactCenter");
		noShow.setReservationNumber(book.getTravelPlanSegmentId());
		noShow.sendRequest();
		TestReporter.logAPI(!noShow.getResponseStatusCode().equals("200"), "An error occurred setting the reservation to [NoShow]", noShow);
		
		TestReporter.logScenario("NoShow Reservation Status");
		Modify modify = modify(book);
		sendRequestAndValidateLogs(modify, "Travel Status is invalid  : INVALID RESERVATION STATUS.");
	}
	
	private Book book(){
		Book book = new Book(environment, ScheduledEventReservation.ONECOMPONENTSNOADDONS);
		book.setParty(hh);
		book.sendRequest();
		TestReporter.logAPI(!book.getResponseStatusCode().equals("200"), "An error occurred during booking", book);
		TPS_ID.set(book.getTravelPlanSegmentId());
		return book;
	}
	
	private Modify modify(Book book){
		Modify modify = new Modify(environment, ScheduledEventReservation.ONECOMPONENTSNOADDONS);
		modify.setParty(hh);
		modify.setTravelPlanId(book.getTravelPlanId());
		modify.setReservationNumber(book.getTravelPlanSegmentId());
		return modify;
	}
	
	private void sendRequestAndValidateLogs(Modify modify, String faultString){
		modify.sendRequest();
    	TestReporter.logAPI(!modify.getFaultString().contains(faultString), modify.getFaultString(), modify);
		logItems(modify);		
	}
	
	private void logItems(Modify modify){
		logValidItems.get().addItem("ShowDiningServiceIF", "modify", true);		
		validateLogs(modify, logValidItems.get(), 10000);		
		
		LogItems logInvalidItems = new LogItems();
		logInvalidItems.addItem("FolioServiceIF", "retrieveAccountingTransactions", false);
		logInvalidItems.addItem("TravelPlanServiceCrossReferenceV3SEI", "updateOrder", false);
		logInvalidItems.addItem("ChargeGroupIF", "modifyGuestContainedChargeGroup", false);
		logInvalidItems.addItem("AccommodationInventoryRequestComponentServiceIF", "updateInventoryForScheduledEvents", false);
		logInvalidItems.addItem("TravelPlanServiceCrossReferenceV3", "updateOrder", false);
		logInvalidItems.addItem("UpdateInventory", "updateInventory", false);
		validateNotInLogs(modify, logInvalidItems);
	}
}
