package com.disney.composite.api.showDiningService.cancel;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

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
	protected ThreadLocal<Book> book = new ThreadLocal<Book>();
	protected ThreadLocal<LogItems> logValidItems = new ThreadLocal<LogItems>();
	protected ThreadLocal<String[]> expectedLogs = new ThreadLocal<String[]>();
	protected ThreadLocal<Cancel> cancel = new ThreadLocal<Cancel>();
	
	@Override
	@BeforeMethod(alwaysRun = true)
	@Parameters({ "environment" })
	public void setup(@Optional String environment){
		this.environment = environment;
		hh = new HouseHold(1);
		book.set(new Book(this.environment, ScheduledEventReservation.ONECOMPONENTSNOADDONS));
		book.get().setParty(hh);
		book.get().sendRequest();
		logValidItems.set(new LogItems());
	}
	
	@AfterMethod
	public void teardown(){
		try{
			Cancel cancel = new Cancel(environment, "CancelDiningEvent");
			cancel.setTravelPlanSegmentId(book.get().getTravelPlanSegmentId());
			cancel.sendRequest();
		}catch(Exception e){}
	}
	
	@Test(groups = {"api", "regression", "dining", "showDiningService", "negative"})
	public void invalidReservationNumber(){
		TestReporter.logScenario("Invalid Reservation Number");
		String number = String.valueOf(Randomness.randomNumberBetween(1, 999));
		cancel.set(new Cancel(environment, "CancelDiningEvent"));
		cancel.get().setTravelPlanSegmentId(number);
		sendRequestAndValidateFaultString("RECORD NOT FOUND : NO RESERVATION FOUND WITH "+number);
	}
	@Test(groups = {"api", "regression", "dining", "showDiningService", "negative"})
	public void missingReservationNumber(){
		TestReporter.logScenario("Missing Reservation Number");
		cancel.set(new Cancel(environment, "CancelDiningEvent"));
		cancel.get().setTravelPlanSegmentId(BaseSoapCommands.REMOVE_NODE.toString());
		sendRequestAndValidateFaultString("RECORD NOT FOUND : NO RESERVATION FOUND WITH 0");
	}
	@Test(groups = {"api", "regression", "dining", "showDiningService", "negative"})
	public void arrivedReservation(){
		TestReporter.logScenario("Arrived Reservation");
		Arrived arrived = new Arrived(environment, "ContactCenter");
		arrived.setReservationNumber(book.get().getTravelPlanSegmentId());
		arrived.sendRequest();
		TestReporter.logAPI(!arrived.getResponseStatusCode().equals("200"), "An error occurred setting the reservation to 'Arrived'", arrived);
		
		cancel.set(new Cancel(environment, "CancelDiningEvent"));
		cancel.get().setTravelPlanSegmentId(book.get().getTravelPlanSegmentId());
		sendRequestAndValidateFaultString("Travel Status is invalid  : INVALID RESERVATION STATUS.");
	}
	@Test(groups = {"api", "regression", "dining", "showDiningService", "negative"})
	public void cancelledReservation(){
		TestReporter.logScenario("Cancelled Reservation");
		cancel.set(new Cancel(environment, "CancelDiningEvent"));
		cancel.get().setTravelPlanSegmentId(book.get().getTravelPlanSegmentId());
		cancel.get().sendRequest();
		TestReporter.logAPI(!cancel.get().getResponseStatusCode().equals("200"), "An error occurred cancelling the reservation the first time.", cancel.get());
		
		cancel.set(new Cancel(environment, "CancelDiningEvent"));
		cancel.get().setTravelPlanSegmentId(book.get().getTravelPlanSegmentId());
		sendRequestAndValidateFaultString("Travel Status is invalid  : INVALID RESERVATION STATUS.");
	}
	@Test(groups = {"api", "regression", "dining", "showDiningService", "negative"})
	public void noShowReservation(){
		TestReporter.logScenario("No Show Reservation");
		NoShow noShow = new NoShow(environment, "ContactCenter");
		noShow.setReservatinoNumber(book.get().getTravelPlanSegmentId());
		noShow.sendRequest();
		
		cancel.set(new Cancel(environment, "CancelDiningEvent"));
		cancel.get().setTravelPlanSegmentId(book.get().getTravelPlanSegmentId());
		sendRequestAndValidateFaultString("Travel Status is invalid  : INVALID RESERVATION STATUS.");
	}
	
    private void sendRequestAndValidateFaultString(String fault){
    	cancel.get().sendRequest();
    	TestReporter.logAPI(!cancel.get().getFaultString().contains(fault), cancel.get().getFaultString() ,cancel.get());
		logItems();
    }
	
	private void logItems(){
		logValidItems.get().addItem("ShowDiningServiceIF", "cancel", true);
		validateLogs(cancel.get(), logValidItems.get());
		
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
		validateNotInLogs(cancel.get(), logInvalidItems);
	}
}