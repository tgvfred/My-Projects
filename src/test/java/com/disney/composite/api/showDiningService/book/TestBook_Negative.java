package com.disney.composite.api.showDiningService.book;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.core.BaseSoapCommands;
import com.disney.api.soapServices.showDiningService.operations.Book;
import com.disney.composite.BaseTest;
import com.disney.test.utils.Randomness;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.database.LogItems;
import com.disney.utils.dataFactory.guestFactory.HouseHold;
import com.disney.utils.dataFactory.staging.bookSEReservation.ScheduledEventReservation;

public class TestBook_Negative  extends BaseTest{
	protected ThreadLocal<LogItems> logValidItems = new ThreadLocal<LogItems>();
	protected String[] defaultExpectedLogs = {"PartyIF;createAndRetrieveParty",
			"FacilityMasterServiceSEI;findFacilityByEnterpriseID",
			"PackagingService;getProducts"};
	protected ThreadLocal<String[]> expectedLogs = new ThreadLocal<String[]>();
	
	@Override
	@BeforeMethod(alwaysRun = true)
	@Parameters({ "environment" })
	public void setup(@Optional String environment){
		this.environment = environment;
		hh = new HouseHold(1);
		logValidItems.set(new LogItems());
	}
	
	@Test(groups = {"api", "regression", "dining", "showDiningService", "negative"})
	public void invalidAuthorizationNumber(){
		TestReporter.logScenario("Invalid Authorization Number");
		Book book = book();
		book.setAuthorizationNumber("1");
		sendRequestAndValidateFaultString("INVALID AUTHORIZATION CODE !! : INVALID AUTHORIZATION CODE !", book);
	}	
	@Test(groups = {"api", "regression", "dining", "showDiningService", "negative"})
	public void invalidBookDateExceeds180DaysInFuture(){
		TestReporter.logScenario("Booking Date Exceeds 180 Days in the Future");
		Book book = book();
		book.setServiceStartDateTime(Randomness.generateCurrentXMLDate(181));
		sendRequestAndValidateFaultString("RESManagement suggests to stop this reservation : Day Guest cannot book a Dining Reservation beyond 180 days from booking date", book);
	}
	@Test(groups = {"api", "regression", "dining", "showDiningService", "negative"})
	public void invalidBookDateInPast(){
		TestReporter.logScenario("Booking Date in the Past");
		Book book = book();
		book.setServiceStartDateTime(Randomness.generateCurrentXMLDate(-1));
		sendRequestAndValidateFaultString("RESManagement suggests to stop this reservation : Book Date is greater than Service date", book);
	}
	@Test(groups = {"api", "regression", "dining", "showDiningService", "negative"})
	public void invalidCommunicationChannel(){
		expectedLogs.set(new String[1]);
		TestReporter.logScenario("Invalid Communications Channel");
		Book book = book();
		book.setCommunicationChannel(Randomness.randomString(4));
		sendRequestAndValidateFaultString("communication Channel is required : null", book);
	}
	@Test(groups = {"api", "regression", "dining", "showDiningService", "negative"})
	public void invalidFacilityId(){
		expectedLogs.set(new String[1]);
		TestReporter.logScenario("Invalid Facility ID");
		Book book = book();
		book.setFacilityId("-1");
		sendRequestAndValidateFaultString("FACILITY ID/NAME IS REQUIRED! : FACILITY ID IS REQUIRED!", book);
	}	
	@Test(groups = {"api", "regression", "dining", "showDiningService", "negative"})
	public void invalidPrimaryGuestCountry(){
		TestReporter.logScenario("Invalid Primary Guest Country");
		Book book = book();
		book.setPrimaryGuestCountry("INVALID");
		sendRequestAndValidateFaultString("Create Party Error : Please enter valid country code", book);
	}
	@Test(groups = {"api", "regression", "dining", "showDiningService", "negative"})
	public void invalidPrimaryGuestTitle(){
		String title = "INVALID";
		TestReporter.logScenario("Invalid Primary Guest Title");
		Book book = book();
		book.setPrimaryGuestTitle(title);
		sendRequestAndValidateFaultString("Salutation is invalid : Salutation "+title+" is invalid", book);
	}	
	@Test(groups = {"api", "regression", "dining", "showDiningService", "negative"})
	public void invalidProductId(){
		expectedLogs.set(new String[1]);
		expectedLogs.get()[0] = "FacilityMasterServiceSEI;findFacilityByEnterpriseID";
		TestReporter.logScenario("Invalid Product ID");
		Book book = book();
		book.setProductId("-1");
		sendRequestAndValidateFaultString("PRODUCT ID IS REQUIRED !! : DREAMS/ENTERPRISE PRODUCT ID IS REQUIRED!!", book);
	}	
	@Test(groups = {"api", "regression", "dining", "showDiningService", "negative"})
	public void invalidSalesChannel(){
		expectedLogs.set(new String[1]);
		TestReporter.logScenario("Invalid Sales Channel");
		Book book = book();
		book.setSalesChannel("INVAlID");
		sendRequestAndValidateFaultString("Sales Channel is required : null", book);
	}
	@Test(groups = {"api", "regression", "dining", "showDiningService", "negative"})
	public void missingCommunicationChannel(){
		expectedLogs.set(new String[1]);
		TestReporter.logScenario("Missing Communications Channel");
		Book book = book();
		book.setCommunicationChannel(BaseSoapCommands.REMOVE_NODE.toString());
		sendRequestAndValidateFaultString("communication Channel is required : null", book);
	}
	@Test(groups = {"api", "regression", "dining", "showDiningService", "negative"})
	public void missingFacilityId(){
		expectedLogs.set(new String[1]);
		TestReporter.logScenario("Missing Facility ID");
		Book book = book();
		book.setFacilityId(BaseSoapCommands.REMOVE_NODE.toString());
		sendRequestAndValidateFaultString("FACILITY ID/NAME IS REQUIRED! : FACILITY ID IS REQUIRED!", book);
	}
	@Test(groups = {"api", "regression", "dining", "showDiningService", "negative"})
	public void missingFreeze(){
		expectedLogs.set(new String[2]);
		expectedLogs.get()[0] = "FacilityMasterServiceSEI;findFacilityByEnterpriseID";
		expectedLogs.get()[1] = "PackagingService;getProducts";
		TestReporter.logScenario("Missing Freeze ID");
		Book book = book();
		book.setFreezeId(BaseSoapCommands.REMOVE_NODE.toString());
		sendRequestAndValidateFaultString("Freeze Id is required : FREEZE ID IS REQUIRED", book);
	}
	@Test(groups = {"api", "regression", "dining", "showDiningService", "negative"})
	public void missingPartyRoleAgeType(){
		expectedLogs.set(new String[1]);
		expectedLogs.get()[0] = "FacilityMasterServiceSEI;findFacilityByEnterpriseID";
		TestReporter.logScenario("Missing Party Role Age Type");
		Book book = book();
		book.setPartyRoleAgeType(BaseSoapCommands.REMOVE_NODE.toString());
		sendRequestAndValidateFaultString("Age Type is required : AGE TYPE IS REQUIRED.", book);
	}
	@Test(groups = {"api", "regression", "dining", "showDiningService", "negative"})
	public void missingPartyRoles(){
		expectedLogs.set(new String[1]);
		expectedLogs.get()[0] = "FacilityMasterServiceSEI;findFacilityByEnterpriseID";
		TestReporter.logScenario("Missing Party Roles");
		Book book = book();
		book.setRequestNodeValueByXPath("/Envelope/Body/book/bookShowDiningRequest/dinnerShowPackage/partyRoles", BaseSoapCommands.REMOVE_NODE.toString());
		sendRequestAndValidateFaultString("Invalid PartyMix. Please send valid partymix : INVALID PARTY SIZE.", book);
	}
	@Test(groups = {"api", "regression", "dining", "showDiningService", "negative"})
	public void missingPrimaryGuest(){
		expectedLogs.set(new String[2]);
		expectedLogs.get()[0] = "FacilityMasterServiceSEI;findFacilityByEnterpriseID";
		expectedLogs.get()[1] = "PackagingService;getProducts";
		TestReporter.logScenario("Missing Primary Guest");
		Book book = book();
		book.setRequestNodeValueByXPath("/Envelope/Body/book/bookShowDiningRequest/primaryGuest", BaseSoapCommands.REMOVE_NODE.toString());
		sendRequestAndValidateFaultString("Travel Plan Guest is required : TRAVEL PLAN GUEST REQUIRED", book);
	}
	@Test(groups = {"api", "regression", "dining", "showDiningService", "negative"})
	public void missingPrimaryGuestFirstName(){
		expectedLogs.set(new String[2]);
		expectedLogs.get()[0] = "FacilityMasterServiceSEI;findFacilityByEnterpriseID";
		expectedLogs.get()[1] = "PackagingService;getProducts";
		TestReporter.logScenario("Missing Primary Guest First Name");
		Book book = book();
		book.setPrimaryGuestFirstName(BaseSoapCommands.REMOVE_NODE.toString());
		sendRequestAndValidateFaultString("Travel Plan Guest is required : TRAVEL PLAN GUEST REQUIRED", book);
	}
	@Test(groups = {"api", "regression", "dining", "showDiningService", "negative"})
	public void missingPrimaryGuestLastName(){
		expectedLogs.set(new String[2]);
		expectedLogs.get()[0] = "FacilityMasterServiceSEI;findFacilityByEnterpriseID";
		expectedLogs.get()[1] = "PackagingService;getProducts";
		TestReporter.logScenario("Missing Primary Guest Last Name");
		Book book = book();
		book.setPrimaryGuestLastName(BaseSoapCommands.REMOVE_NODE.toString());
		sendRequestAndValidateFaultString("Travel Plan Guest is required : TRAVEL PLAN GUEST REQUIRED", book);
	}
	@Test(groups = {"api", "regression", "dining", "showDiningService", "negative"})
	public void missingProductId(){
		expectedLogs.set(new String[1]);
		expectedLogs.get()[0] = "FacilityMasterServiceSEI;findFacilityByEnterpriseID";
		TestReporter.logScenario("Missing Product ID");
		Book book = book();
		book.setProductId(BaseSoapCommands.REMOVE_NODE.toString());
		sendRequestAndValidateFaultString("PRODUCT ID IS REQUIRED !! : DREAMS/ENTERPRISE PRODUCT ID IS REQUIRED!!", book);
	}
	@Test(groups = {"api", "regression", "dining", "showDiningService", "negative"})
	public void missingProductType(){
		expectedLogs.set(new String[1]);
		expectedLogs.get()[0] = "FacilityMasterServiceSEI;findFacilityByEnterpriseID";
		TestReporter.logScenario("Missing Product Type");
		Book book = book();
		book.setProductType(BaseSoapCommands.REMOVE_NODE.toString());
		sendRequestAndValidateFaultString("PRODUCT TYPE NAME IS REQUIRED!! : PRODUCT TYPE NAME IS REQUIRED!!", book);
	}
	@Test(groups = {"api", "regression", "dining", "showDiningService", "negative"})
	public void missingReservableResourceID(){
		expectedLogs.set(new String[2]);
		expectedLogs.get()[0] = "FacilityMasterServiceSEI;findFacilityByEnterpriseID";
		expectedLogs.get()[1] = "PackagingService;getProducts";
		TestReporter.logScenario("Missing Reservable Resource ID");
		Book book = book();
		book.setReservableResourceId(BaseSoapCommands.REMOVE_NODE.toString());
		sendRequestAndValidateFaultString("RESERVABLE RESOURCE ID IS REQUIRED! : RESERVABLE RESOURCE ID IS REQUIRED!", book);
	}
	@Test(groups = {"api", "regression", "dining", "showDiningService", "negative"})
	public void missingSalesChannel(){
		expectedLogs.set(new String[1]);
		TestReporter.logScenario("Missing Sales Channel");
		Book book = book();
		book.setSalesChannel(BaseSoapCommands.REMOVE_NODE.toString());
		sendRequestAndValidateFaultString("Sales Channel is required : null", book);
	}
	@Test(groups = {"api", "regression", "dining", "showDiningService", "negative"})
	public void missingServicePeriodId(){
		expectedLogs.set(new String[1]);
		expectedLogs.get()[0] = "FacilityMasterServiceSEI;findFacilityByEnterpriseID";
		TestReporter.logScenario("Missing Service Period ID");
		Book book = book();
		book.setServicePeriosId(BaseSoapCommands.REMOVE_NODE.toString());
		sendRequestAndValidateFaultString("ENTERPRISE SERVICE PERIOD ID IS REQUIRED.! : ENTERPRISE SERVICE PERIOD ID IS REQUIRED.", book);
	}
	@Test(groups = {"api", "regression", "dining", "showDiningService", "negative"})
	public void missingServiceStartDate(){
		expectedLogs.set(new String[1]);
		expectedLogs.get()[0] = "FacilityMasterServiceSEI;findFacilityByEnterpriseID";
		TestReporter.logScenario("Missing Service Start Date");
		Book book = book();
		book.setServiceStartDateTime(BaseSoapCommands.REMOVE_NODE.toString());
		sendRequestAndValidateFaultString("INVALID  SERVICE START DATE!! : INVALID SERVICE START DATE!!", book);
	}
	@Test(groups = {"api", "regression", "dining", "showDiningService", "negative"})
	public void missingSourceAccountingCenter(){
		expectedLogs.set(new String[1]);
		TestReporter.logScenario("Missing Source Accounting Center");
		Book book = book();
		book.setSourceAccountingCenter(BaseSoapCommands.REMOVE_NODE.toString());
		sendRequestAndValidateFaultString("SOURCER ACCOUNTING CENTER IS REQUIRED! : SOURCER ACCOUNTING CENTER IS REQUIRED!", book);
	}
	@Test(groups = {"api", "regression", "dining", "showDiningService", "negative"})
	public void missingComponent(){
		expectedLogs.set(new String[2]);
		expectedLogs.get()[0] = "FacilityMasterServiceSEI;findFacilityByEnterpriseID";
		expectedLogs.get()[1] = "PackagingService;getProducts";
		TestReporter.logScenario("Missing Components");
		Book book = book();
		book.setRequestNodeValueByXPath("/Envelope/Body/book/bookShowDiningRequest/dinnerShowPackage/componentPrices", BaseSoapCommands.REMOVE_NODE.toString());
		sendRequestAndValidateFaultString("COMPONENT PRICE IS REQUIRED! :  For Product : "+ book.getRequestProductId(), book);		
	}
	
	private Book book(){
		Book book = new Book(this.environment, ScheduledEventReservation.ONECOMPONENTSNOADDONS);
		book.setParty(hh);
		return book;
	}
	
    private void sendRequestAndValidateFaultString(String fault, Book book){
    	book.sendRequest();
    	TestReporter.logAPI(!book.getFaultString().contains(fault), book.getFaultString() ,book);
		logItems(book);
    }
    
    private void addLogItems(){
    	String[] items;
    	items = (expectedLogs.get() == null) ? defaultExpectedLogs : expectedLogs.get();
    	String service;
    	String operation;
    	for(String item: items){
    		if(item != null){
        		service = item.split(";")[0];
        		operation = item.split(";")[1];
        		logValidItems.get().addItem(service, operation, true);
    		}
    	}
    }
	
	private void logItems(Book book){
		logValidItems.get().addItem("ShowDiningServiceIF", "book", true);
		addLogItems();
		validateLogs(book, logValidItems.get(), 10000);
		
		LogItems logInvalidItems = new LogItems();
		logInvalidItems.addItem("FolioServiceIF", "retrieveAccountingTransactions", false);
		logInvalidItems.addItem("GuestLinkServiceV1SEI", "createEntitlementReference", false);
		logInvalidItems.addItem("TravelPlanServiceV3SEI", "create", false);
		logInvalidItems.addItem("PartyIF", "retrieveParty", false);
		logInvalidItems.addItem("PartyIF", "createChargeGroupAndPostCharges", false);
		logInvalidItems.addItem("PartyIF", "retrievePartyBasicInformation", false);
		logInvalidItems.addItem("AccommodationInventoryRequestComponentServiceIF", "createInventory", false);
		logInvalidItems.addItem("AccommodationInventoryRequestComponentServiceIF", "retrieveAssignmentOwner", false);
		logInvalidItems.addItem("GuestLinkServiceV1", "createEntitlementReference", false);
		logInvalidItems.addItem("GuestServiceV1", "create", false);
		logInvalidItems.addItem("PartyIF", "updateExternalPartyAndLocatorId", false);
		logInvalidItems.addItem("TravelPlanServiceV3", "create", false);
		logInvalidItems.addItem("UpdateInventory", "updateInventory", false);
		logInvalidItems.addItem("ShowDiningServiceIF", "retrieve", false);
		validateNotInLogs(book, logInvalidItems);
	}
}