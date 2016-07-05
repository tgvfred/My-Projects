package com.disney.composite.api.showDiningService.reprintTicket;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.core.BaseSoapCommands;
import com.disney.api.soapServices.showDiningService.operations.Book;
import com.disney.api.soapServices.showDiningService.operations.Cancel;
import com.disney.api.soapServices.showDiningService.operations.PrintTicket;
import com.disney.api.soapServices.showDiningService.operations.ReprintTicket;
import com.disney.composite.BaseTest;
import com.disney.utils.Randomness;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.database.LogItems;
import com.disney.utils.dataFactory.guestFactory.HouseHold;
import com.disney.utils.dataFactory.staging.bookSEReservation.ScheduledEventReservation;

public class TestReprintTicket_Negative extends BaseTest{
	protected ThreadLocal<Book> book = new ThreadLocal<Book>();
	protected ThreadLocal<PrintTicket> print = new ThreadLocal<PrintTicket>();
	protected ThreadLocal<ReprintTicket> reprint = new ThreadLocal<ReprintTicket>();
	protected ThreadLocal<LogItems> logValidItems = new ThreadLocal<LogItems>();
	
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
		print.set(new PrintTicket(environment, "Main"));
		print.get().setReservationnumber(book.get().getTravelPlanSegmentId());
		print.get().sendRequest();
		reprint.set(new ReprintTicket(environment, "Main"));
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
	public void invalidReservationNumber() {
		TestReporter.logScenario("Invalid Reservation Number");
		String number = Randomness.randomNumber(4);
		reprint.get().setReservationNumber(number);
		sendRequestAndValidateFaultString("RECORD NOT FOUND : NO RESERVATION FOUND WITH "+number);
	}

	@Test(groups = {"api", "regression", "dining", "showDiningService", "negative"})
	public void invalidSalesChannel() {
		TestReporter.logScenario("Invalid Sales Channel");
		reprint.get().setSalesChannel(Randomness.randomString(4));
		sendRequestAndValidateFaultString("Sales Channel is required : null");
	}

	@Test(groups = {"api", "regression", "dining", "showDiningService", "negative"})
	public void invalidCommunicationChannel() {
		TestReporter.logScenario("Invalid Communications Channel");
		reprint.get().setCommunicationChannel(Randomness.randomString(4));
		sendRequestAndValidateFaultString("communication Channel is required : null");
	}

	@Test(groups = {"api", "regression", "dining", "showDiningService", "negative"})
	public void invalidReprintReason() {
		String id = "-1";
		TestReporter.logScenario("Invalid Reprint Reason ID");
		reprint.get().setReprintReasonId(id);
		sendRequestAndValidateFaultString("INVALID REASON ID! : INVALID REPRINT REASON#"+id);
	}

	@Test(groups = {"api", "regression", "dining", "showDiningService", "negative"})
	public void missingReservationNumber() {
		TestReporter.logScenario("Missing Reservation Number");
		reprint.get().setReservationNumber(BaseSoapCommands.REMOVE_NODE.toString());
		sendRequestAndValidateFaultString("RECORD NOT FOUND : NO RESERVATION FOUND WITH 0");
	}

	@Test(groups = {"api", "regression", "dining", "showDiningService", "negative"})
	public void missingSalesChannel() {
		TestReporter.logScenario("Invalid Sales Channel");
		reprint.get().setSalesChannel(BaseSoapCommands.REMOVE_NODE.toString());
		sendRequestAndValidateFaultString("Sales Channel is required : null");
	}

	@Test(groups = {"api", "regression", "dining", "showDiningService", "negative"})
	public void missingCommunicationChannel() {
		TestReporter.logScenario("Invalid Communications Channel");
		reprint.get().setCommunicationChannel(BaseSoapCommands.REMOVE_NODE.toString());
		sendRequestAndValidateFaultString("communication Channel is required : null");
	}

	@Test(groups = {"api", "regression", "dining", "showDiningService", "negative"})
	public void missingReprintReason() {
		TestReporter.logScenario("Invalid Reprint Reason ID");
		reprint.get().setReprintReasonId(BaseSoapCommands.REMOVE_NODE.toString());
		sendRequestAndValidateFaultString("INVALID REASON ID! : INVALID REPRINT REASON#0");
	}	

    private void sendRequestAndValidateFaultString(String fault){
    	reprint.get().sendRequest();
    	TestReporter.logAPI(!reprint.get().getFaultString().contains(fault), reprint.get().getFaultString(), reprint.get());
		logItems();
    }
	
	private void logItems(){
		logValidItems.get().addItem("ShowDiningServiceIF", "reprintTicket", true);
		validateLogs(reprint.get(), logValidItems.get());
		
		LogItems logInvalidItems = new LogItems();
		logInvalidItems.addItem("FolioServiceIF", "retrieveAccountingTransactions", false);
		logInvalidItems.addItem("GuestLinkServiceV1SEI", "createEntitlementReference", false);
		logInvalidItems.addItem("PartyIF", "retrieveParty", false);
		logInvalidItems.addItem("PartyIF", "retrievePartyBasicInformation", false);
		logInvalidItems.addItem("AccommodationInventoryRequestComponentServiceIF", "retrieveAssignmentOwner", false);
		logInvalidItems.addItem("PackagingService", "getProducts", false);
		logInvalidItems.addItem("GuestLinkServiceV1", "createEntitlementReference", false);
		logInvalidItems.addItem("ShowDiningServiceIF", "retrieve", false);
		logInvalidItems.addItem("ShowDiningServiceIF", "printTicket", false);
		validateNotInLogs(reprint.get(), logInvalidItems);
	}
}