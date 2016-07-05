package com.disney.composite.api.showDiningService.noShow;

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
import com.disney.test.utils.Randomness;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.database.LogItems;
import com.disney.utils.dataFactory.guestFactory.HouseHold;
import com.disney.utils.dataFactory.staging.bookSEReservation.ScheduledEventReservation;

public class TestNoShow_Negative extends BaseTest{
	protected ThreadLocal<Book> book = new ThreadLocal<Book>();
	protected ThreadLocal<NoShow> noShow = new ThreadLocal<NoShow>();
	protected ThreadLocal<LogItems> logValidItems = new ThreadLocal<LogItems>();
	protected ThreadLocal<String[]> expectedLogs = new ThreadLocal<String[]>();
	
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
		noShow.set(new NoShow(environment, "ContactCenter"));
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
	public void invalidCommunicationChannel() {
		TestReporter.logScenario("Invalid Communications Channel");
		noShow.get().setReservatinoNumber(book.get().getTravelPlanSegmentId());
		noShow.get().setCommunicationsChannel(Randomness.randomString(4));
		sendRequestAndValidateFaultString("communication Channel is required : null");
	}
	@Test(groups = {"api", "regression", "dining", "showDiningService", "negative"})
	public void invalidReservationNumber() {
		String number = Randomness.randomNumber(4);
		TestReporter.logScenario("Invalid Reservation Number");
		noShow.get().setReservatinoNumber(number);
		sendRequestAndValidateFaultString("RECORD NOT FOUND : NO RESERVATION FOUND WITH "+number);
	}	
	@Test(groups = {"api", "regression", "dining", "showDiningService", "negative"})
	public void invalidSalesChannel() {
		TestReporter.logScenario("Invalid Sales Channel");
		noShow.get().setReservatinoNumber(book.get().getTravelPlanSegmentId());
		noShow.get().setSalesChannel(Randomness.randomString(4));
		sendRequestAndValidateFaultString("Sales Channel is required : null");
	}	
	@Test(groups = {"api", "regression", "dining", "showDiningService", "negative"})
	public void missingCommunicationChannel() {
		TestReporter.logScenario("Missing Communications Channel");
		noShow.get().setReservatinoNumber(book.get().getTravelPlanSegmentId());
		noShow.get().setCommunicationsChannel(BaseSoapCommands.REMOVE_NODE.toString());
		sendRequestAndValidateFaultString("communication Channel is required : null");
	}
	@Test(groups = {"api", "regression", "dining", "showDiningService", "negative"})
	public void missingReservationNumber() {
		TestReporter.logScenario("Missing Reservation Number");
		noShow.get().setReservatinoNumber(BaseSoapCommands.REMOVE_NODE.toString());
		sendRequestAndValidateFaultString("RECORD NOT FOUND : NO RESERVATION FOUND WITH 0");
	}
	@Test(groups = {"api", "regression", "dining", "showDiningService", "negative"})
	public void missingSalesChannel() {
		TestReporter.logScenario("Missing Sales Channel");
		noShow.set(new NoShow(environment, "ContactCenter"));
		noShow.get().setReservatinoNumber(book.get().getTravelPlanSegmentId());
		noShow.get().setSalesChannel(BaseSoapCommands.REMOVE_NODE.toString());
		sendRequestAndValidateFaultString("Sales Channel is required : null");
	}
	@Test(groups = {"api", "regression", "dining", "showDiningService", "negative"})
	public void noShow() {
		TestReporter.logScenario("No Show");
		noShow.get().setReservatinoNumber(book.get().getTravelPlanSegmentId());
		noShow.get().sendRequest();
		TestReporter.logAPI(!noShow.get().getResponseStatusCode().equals("200"), "An error occurred setting the reservation to 'No Show'", noShow.get());
		
		noShow.set(new NoShow(environment, "ContactCenter"));
		noShow.get().setReservatinoNumber(book.get().getTravelPlanSegmentId());
		sendRequestAndValidateFaultString("Travel Status is invalid  : INVALID RESERVATION STATUS.CANNOT CHANGE THE STATUS TO NO-SHOW!");
	}
	@Test(groups = {"api", "regression", "dining", "showDiningService", "negative"})
	public void arrivedReservation() {
		TestReporter.logScenario("Arrived");
		Arrived arrived = new Arrived(environment, "ContactCenter");
		arrived.setReservationNumber(book.get().getTravelPlanSegmentId());
		arrived.sendRequest();
		TestReporter.logAPI(!arrived.getResponseStatusCode().equals("200"), "An error occurred setting the reservation to 'Arrived'", arrived);
		
		noShow.get().setReservatinoNumber(book.get().getTravelPlanSegmentId());
		sendRequestAndValidateFaultString("Travel Status is invalid  : INVALID RESERVATION STATUS.CANNOT CHANGE THE STATUS TO NO-SHOW!");
	}
	@Test(groups = {"api", "regression", "dining", "showDiningService", "negative"})
	public void cancelledReservation() {
		TestReporter.logScenario("Arrived");
		Cancel cancel = new Cancel(environment, "CancelDiningEvent");
		cancel.setTravelPlanSegmentId(book.get().getTravelPlanSegmentId());
		cancel.sendRequest();
		TestReporter.logAPI(!cancel.getResponseStatusCode().equals("200"), "An error occurred cancelling the reservation", cancel);
		
		noShow.get().setReservatinoNumber(book.get().getTravelPlanSegmentId());
		sendRequestAndValidateFaultString("Travel Status is invalid  : INVALID RESERVATION STATUS.CANNOT CHANGE THE STATUS TO NO-SHOW!");
	}
	
    private void sendRequestAndValidateFaultString(String fault){
    	noShow.get().sendRequest();
    	TestReporter.logAPI(!noShow.get().getFaultString().contains(fault), noShow.get().getFaultString(), noShow.get());
		logItems();
    }
	
	private void logItems(){
		logValidItems.get().addItem("ShowDiningServiceIF", "noShow", true);
		validateLogs(noShow.get(), logValidItems.get());
		
		LogItems logInvalidItems = new LogItems();
		logInvalidItems.addItem("FolioServiceIF", "retrieveAccountingTransactions", false);
		logInvalidItems.addItem("GuestLinkServiceV1SEI", "createEntitlementReference", false);
		logInvalidItems.addItem("TravelPlanServiceCrossReferenceV3SEI", "updateOrder", false);
		logInvalidItems.addItem("ChargeGroupIF", "processNoShow", false);
		logInvalidItems.addItem("PartyIF", "retrieveParty", false);
		logInvalidItems.addItem("PartyIF", "retrievePartyBasicInformation", false);
		logInvalidItems.addItem("AccommodationInventoryRequestComponentServiceIF", "retrieveAssignmentOwner", false);
		logInvalidItems.addItem("AccommodationInventoryRequestComponentServiceIF", "releaseInventory", false);
		logInvalidItems.addItem("PackagingService", "getProducts", false);
		logInvalidItems.addItem("PricingService", "getCancellationCharges", false);
		logInvalidItems.addItem("GuestLinkServiceV1", "createEntitlementReference", false);
		logInvalidItems.addItem("TravelPlanServiceCrossReferenceV3", "updateOrder", false);
		logInvalidItems.addItem("UpdateInventory", "updateInventory", false);
		logInvalidItems.addItem("ShowDiningServiceIF", "retrieve", false);
		validateNotInLogs(noShow.get(), logInvalidItems);
	}
	
	
	
	
	

}
