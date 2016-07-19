package com.disney.composite.api.showDiningService.cancel;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.applicationError.ApplicationErrorCode;
import com.disney.api.soapServices.applicationError.DiningErrorCode;
import com.disney.api.soapServices.core.BaseSoapCommands;
import com.disney.api.soapServices.showDiningService.operations.Arrived;
import com.disney.api.soapServices.showDiningService.operations.Book;
import com.disney.api.soapServices.showDiningService.operations.Cancel;
import com.disney.api.soapServices.showDiningService.operations.NoShow;
import com.disney.composite.BaseTest;
import com.disney.utils.Randomness;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.database.LogItems;
import com.disney.utils.dataFactory.guestFactory.HouseHold;
import com.disney.utils.dataFactory.staging.bookSEReservation.ScheduledEventReservation;

public class TestCancel_Negative extends BaseTest{
	protected ThreadLocal<LogItems> logValidItems = new ThreadLocal<LogItems>();
	protected ThreadLocal<String[]> expectedLogs = new ThreadLocal<String[]>();
	protected ThreadLocal<String> TPS_ID = new ThreadLocal<String>();
	
	@BeforeTest(alwaysRun = true)
	@Parameters({ "environment" })
	public void testSetup(@Optional String environment){
		this.environment = environment;
		hh = new HouseHold(1);
	}
	
	@Override
	@BeforeMethod(alwaysRun=true)
	@Parameters({ "environment" })
	public void setup(@Optional String environment){
		logValidItems.set(new LogItems());
	}
	
	@AfterTest(alwaysRun=true)
	public void teardown(){
		try{
			if(TPS_ID.get() != null)
				if(!TPS_ID.get().isEmpty()){
					Cancel cancel = new Cancel(environment, "CancelDiningEvent");
					cancel.setTravelPlanSegmentId(TPS_ID.get());
					cancel.sendRequest();
				}
		}catch(Exception e){}
	}
	
	@Test(groups = {"api", "regression", "dining", "showDiningService", "negative"})
	public void invalidReservationNumber(){
		TestReporter.logScenario("Invalid Reservation Number");
		String number = String.valueOf(Randomness.randomNumberBetween(1, 999));
		Cancel cancel = new Cancel(environment, "CancelDiningEvent");
		cancel.setTravelPlanSegmentId(number);
		sendRequestAndValidateFaultString("RECORD NOT FOUND : NO RESERVATION FOUND WITH "+number, DiningErrorCode.RECORD_NOT_FOUND_EXCEPTION, cancel);
	}
	@Test(groups = {"api", "regression", "dining", "showDiningService", "negative"})
	public void missingReservationNumber(){
		TestReporter.logScenario("Missing Reservation Number");
		Cancel cancel = new Cancel(environment, "CancelDiningEvent");
		cancel.setTravelPlanSegmentId(BaseSoapCommands.REMOVE_NODE.toString());
		sendRequestAndValidateFaultString("RECORD NOT FOUND : NO RESERVATION FOUND WITH 0", DiningErrorCode.RECORD_NOT_FOUND_EXCEPTION, cancel);
	}
	@Test(groups = {"api", "regression", "dining", "showDiningService", "negative"})
	public void arrivedReservation(){
		TestReporter.logScenario("Arrived Reservation");
		Book book = book();
		Arrived arrived = new Arrived(environment, "ContactCenter");
		arrived.setReservationNumber(book.getTravelPlanSegmentId());
		arrived.sendRequest();
		TestReporter.logAPI(!arrived.getResponseStatusCode().equals("200"), "An error occurred setting the reservation to 'Arrived'", arrived);
		
		Cancel cancel = new Cancel(environment, "CancelDiningEvent");
		cancel.setTravelPlanSegmentId(book.getTravelPlanSegmentId());
		sendRequestAndValidateFaultString("Travel Status is invalid  : INVALID RESERVATION STATUS.", DiningErrorCode.INVALID_TRAVEL_STATUS, cancel);
	}
	@Test(groups = {"api", "regression", "dining", "showDiningService", "negative"})
	public void cancelledReservation(){
		TestReporter.logScenario("Cancelled Reservation");
		Book book = book();
		Cancel cancel = new Cancel(environment, "CancelDiningEvent");
		cancel.setTravelPlanSegmentId(book.getTravelPlanSegmentId());
		cancel.sendRequest();
		TestReporter.logAPI(!cancel.getResponseStatusCode().equals("200"), "An error occurred cancelling the reservation the first time.", cancel);
		
		cancel = new Cancel(environment, "CancelDiningEvent");
		cancel.setTravelPlanSegmentId(book.getTravelPlanSegmentId());
		sendRequestAndValidateFaultString("Travel Status is invalid  : INVALID RESERVATION STATUS.", DiningErrorCode.INVALID_TRAVEL_STATUS, cancel);
	}
	@Test(groups = {"api", "regression", "dining", "showDiningService", "negative"})
	public void noShowReservation(){
		TestReporter.logScenario("No Show Reservation");
		Book book = book();
		NoShow noShow = new NoShow(environment, "ContactCenter");
		noShow.setReservationNumber(book.getTravelPlanSegmentId());
		noShow.sendRequest();
		
		Cancel cancel = new Cancel(environment, "CancelDiningEvent");
		cancel.setTravelPlanSegmentId(book.getTravelPlanSegmentId());
		sendRequestAndValidateFaultString("Travel Status is invalid  : INVALID RESERVATION STATUS.", DiningErrorCode.INVALID_TRAVEL_STATUS, cancel);
	}
	
	private Book book(){
		Book book = new Book(this.environment, ScheduledEventReservation.ONECOMPONENTSNOADDONS);
		book.setParty(hh);
		book.sendRequest();
		TestReporter.logAPI(!book.getResponseStatusCode().equals("200"), "An error occurred booking a show dining reservation.", book);
		TPS_ID.set(book.getTravelPlanSegmentId());
		return book;
	}
	
    private void sendRequestAndValidateFaultString(String fault, ApplicationErrorCode error, Cancel cancel){
    	cancel.sendRequest();
		validateApplicationError(cancel, error);
    	TestReporter.logAPI(!cancel.getFaultString().contains(fault), cancel.getFaultString() ,cancel);
		logItems(cancel);
    }
	
	private void logItems(Cancel cancel){
		logValidItems.get().addItem("ShowDiningServiceIF", "cancel", true);
		validateLogs(cancel, logValidItems.get(), 10000);
		
		LogItems logInvalidItems = new LogItems();
		logInvalidItems.addItem("FolioServiceIF", "retrieveAccountingTransactions", false);
		logInvalidItems.addItem("GuestLinkServiceV1SEI", "createEntitlementReference", false);
		logInvalidItems.addItem("TravelPlanServiceV3SEI", "cancelOrder", false);
		logInvalidItems.addItem("ChargeGroupIF", "getTaxExempt", false);
		logInvalidItems.addItem("ChargeGroupIF", "cancelChargeGroups", false);
		logInvalidItems.addItem("PartyIF", "retrieveParty", false);
		logInvalidItems.addItem("PartyIF", "retrievePartyBasicInformation", false);
		logInvalidItems.addItem("AccommodationInventoryRequestComponentServiceIF", "releaseInventory", false);
		logInvalidItems.addItem("AccommodationInventoryRequestComponentServiceIF", "retrieveAssignmentOwner", false);
		logInvalidItems.addItem("PackagingService", "getProducts", false);	
		logInvalidItems.addItem("GuestLinkServiceV1", "createEntitlementReference", false);
		logInvalidItems.addItem("TravelPlanServiceCrossReferenceV3", "cancelOrder", false);
		logInvalidItems.addItem("UpdateInventory", "updateInventory", false);
		logInvalidItems.addItem("ShowDiningServiceIF", "retrieve", false);
		validateNotInLogs(cancel, logInvalidItems);
	}
}