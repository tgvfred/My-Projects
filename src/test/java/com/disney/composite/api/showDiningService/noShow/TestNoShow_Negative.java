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
	protected ThreadLocal<LogItems> logValidItems = new ThreadLocal<LogItems>();
	protected ThreadLocal<String[]> expectedLogs = new ThreadLocal<String[]>();
	protected ThreadLocal<String> TPS_ID = new ThreadLocal<String>();
	
	@Override
	@BeforeMethod(alwaysRun = true)
	@Parameters({ "environment" })
	public void setup(@Optional String environment){
		this.environment = environment;
		hh = new HouseHold(1);
		logValidItems.set(new LogItems());
	}
	
	@AfterMethod(alwaysRun = true)
	public void teardown(){
		if(TPS_ID.get() != null)
			if(!TPS_ID.get().isEmpty()){
				Cancel cancel = new Cancel(environment, "CancelDiningEvent");
				cancel.setTravelPlanSegmentId(TPS_ID.get());
				cancel.sendRequest();
			}
	}

	@Test(groups = {"api", "regression", "dining", "showDiningService", "negative"})
	public void invalidCommunicationChannel() {
		TestReporter.logScenario("Invalid Communications Channel");
		NoShow noShow = new NoShow(environment, "ContactCenter");
		noShow.setReservationNumber(book().getTravelPlanSegmentId());
		noShow.setCommunicationsChannel(Randomness.randomString(4));
		sendRequestAndValidateFaultString("communication Channel is required : null", noShow);
	}
	@Test(groups = {"api", "regression", "dining", "showDiningService", "negative"})
	public void invalidReservationNumber() {
		String number = Randomness.randomNumber(4);
		TestReporter.logScenario("Invalid Reservation Number");
		NoShow noShow = new NoShow(environment, "ContactCenter");
		noShow.setReservationNumber(number);
		sendRequestAndValidateFaultString("RECORD NOT FOUND : NO RESERVATION FOUND WITH "+number, noShow);
	}	
	@Test(groups = {"api", "regression", "dining", "showDiningService", "negative"})
	public void invalidSalesChannel() {
		TestReporter.logScenario("Invalid Sales Channel");
		NoShow noShow = new NoShow(environment, "ContactCenter");
		noShow.setReservationNumber(book().getTravelPlanSegmentId());
		noShow.setSalesChannel(Randomness.randomString(4));
		sendRequestAndValidateFaultString("Sales Channel is required : null", noShow);
	}	
	@Test(groups = {"api", "regression", "dining", "showDiningService", "negative"})
	public void missingCommunicationChannel() {
		TestReporter.logScenario("Missing Communications Channel");
		NoShow noShow = new NoShow(environment, "ContactCenter");
		noShow.setReservationNumber(book().getTravelPlanSegmentId());
		noShow.setCommunicationsChannel(BaseSoapCommands.REMOVE_NODE.toString());
		sendRequestAndValidateFaultString("communication Channel is required : null", noShow);
	}
	@Test(groups = {"api", "regression", "dining", "showDiningService", "negative"})
	public void missingReservationNumber() {
		TestReporter.logScenario("Missing Reservation Number");
		NoShow noShow = new NoShow(environment, "ContactCenter");
		noShow.setReservationNumber(BaseSoapCommands.REMOVE_NODE.toString());
		sendRequestAndValidateFaultString("RECORD NOT FOUND : NO RESERVATION FOUND WITH 0", noShow);
	}
	@Test(groups = {"api", "regression", "dining", "showDiningService", "negative"})
	public void missingSalesChannel() {
		TestReporter.logScenario("Missing Sales Channel");
		NoShow noShow = new NoShow(environment, "ContactCenter");
		noShow.setReservationNumber(book().getTravelPlanSegmentId());
		noShow.setSalesChannel(BaseSoapCommands.REMOVE_NODE.toString());
		sendRequestAndValidateFaultString("Sales Channel is required : null", noShow);
	}
	@Test(groups = {"api", "regression", "dining", "showDiningService", "negative"})
	public void noShow() {
		TestReporter.logScenario("No Show");
		Book book = book();
		NoShow noShow = new NoShow(environment, "ContactCenter");
		noShow.setReservationNumber(book.getTravelPlanSegmentId());
		noShow.sendRequest();
		TestReporter.logAPI(!noShow.getResponseStatusCode().equals("200"), "An error occurred setting the reservation to 'No Show'", noShow);

		noShow = new NoShow(environment, "ContactCenter");
		noShow.setReservationNumber(book.getTravelPlanSegmentId());
		sendRequestAndValidateFaultString("Travel Status is invalid  : INVALID RESERVATION STATUS.CANNOT CHANGE THE STATUS TO NO-SHOW!", noShow);
	}
	@Test(groups = {"api", "regression", "dining", "showDiningService", "negative"})
	public void arrivedReservation() {
		TestReporter.logScenario("Arrived");
		Book book = book();
		NoShow noShow = new NoShow(environment, "ContactCenter");
		Arrived arrived = new Arrived(environment, "ContactCenter");
		arrived.setReservationNumber(book.getTravelPlanSegmentId());
		arrived.sendRequest();
		TestReporter.logAPI(!arrived.getResponseStatusCode().equals("200"), "An error occurred setting the reservation to 'Arrived'", arrived);
		
		noShow.setReservationNumber(book.getTravelPlanSegmentId());
		sendRequestAndValidateFaultString("Travel Status is invalid  : INVALID RESERVATION STATUS.CANNOT CHANGE THE STATUS TO NO-SHOW!", noShow);
	}
	@Test(groups = {"api", "regression", "dining", "showDiningService", "negative"})
	public void cancelledReservation() {
		TestReporter.logScenario("Arrived");
		Book book = book();
		NoShow noShow = new NoShow(environment, "ContactCenter");
		Cancel cancel = new Cancel(environment, "CancelDiningEvent");
		cancel.setTravelPlanSegmentId(book.getTravelPlanSegmentId());
		cancel.sendRequest();
		TestReporter.logAPI(!cancel.getResponseStatusCode().equals("200"), "An error occurred cancelling the reservation", cancel);
		
		noShow.setReservationNumber(book.getTravelPlanSegmentId());
		sendRequestAndValidateFaultString("Travel Status is invalid  : INVALID RESERVATION STATUS.CANNOT CHANGE THE STATUS TO NO-SHOW!", noShow);
	}
	
	private Book book(){
		Book book = new Book(this.environment, ScheduledEventReservation.ONECOMPONENTSNOADDONS);
		book.setParty(hh);
		book.sendRequest();
		TestReporter.logAPI(!book.getResponseStatusCode().equals("200"), "An error occurred booking a show dining event", book);
		TPS_ID.set(book.getTravelPlanSegmentId());
		return book;
	}
	
    private void sendRequestAndValidateFaultString(String fault, NoShow noShow){
    	noShow.sendRequest();
    	TestReporter.logAPI(!noShow.getFaultString().contains(fault), noShow.getFaultString(), noShow);
		logItems(noShow);
    }
	
	private void logItems(NoShow noShow){
		logValidItems.get().addItem("ShowDiningServiceIF", "noShow", true);
		validateLogs(noShow, logValidItems.get(), 10000);
		
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
		validateNotInLogs(noShow, logInvalidItems);
	}
}
