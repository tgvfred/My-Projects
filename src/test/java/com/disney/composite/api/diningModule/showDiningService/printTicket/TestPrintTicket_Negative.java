package com.disney.composite.api.diningModule.showDiningService.printTicket;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.applicationError.ApplicationErrorCode;
import com.disney.api.soapServices.applicationError.DiningErrorCode;
import com.disney.api.soapServices.core.BaseSoapCommands;
import com.disney.api.soapServices.diningModule.showDiningService.operations.PrintTicket;
import com.disney.composite.BaseTest;
import com.disney.utils.Randomness;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.database.LogItems;
import com.disney.utils.dataFactory.guestFactory.HouseHold;

public class TestPrintTicket_Negative extends BaseTest{
	protected ThreadLocal<LogItems> logValidItems = new ThreadLocal<LogItems>();
	
	@Override
	@BeforeMethod(alwaysRun = true)
	@Parameters({ "environment" })
	public void setup(@Optional String environment){
		this.environment = environment;
		hh = new HouseHold(1);
		logValidItems.set(new LogItems());
	}

	@Test(groups = {"api", "regression", "dining", "showDiningService", "negative"})
	public void invalidReservationNumber() {
		TestReporter.logScenario("Invalid Reservation Number");
		String number = "1234";
		PrintTicket print = new PrintTicket(environment, "Main");
		print.setReservationnumber(number);
		sendRequestAndValidateFaultString("RECORD NOT FOUND : NO RESERVATION FOUND WITH "+number, DiningErrorCode.RECORD_NOT_FOUND_EXCEPTION, print);
	}
	@Test(groups = {"api", "regression", "dining", "showDiningService", "negative"})
	public void invalidSalesChannel() {
		TestReporter.logScenario("Invalid Sales Channel");
		PrintTicket print = new PrintTicket(environment, "Main");
		print.setSalesChannel(Randomness.randomString(4));
		sendRequestAndValidateFaultString("Sales Channel is required : null", DiningErrorCode.SALES_CHANNEL_REQUIRED, print);
	}

	@Test(groups = {"api", "regression", "dining", "showDiningService", "negative"})
	public void invalidCommunicationChannel() {
		TestReporter.logScenario("Invalid Communications Channel");
		PrintTicket print = new PrintTicket(environment, "Main");
		print.setCommunicationChannel(Randomness.randomString(4));
		sendRequestAndValidateFaultString("communication Channel is required : null", DiningErrorCode.COMMUNICATION_CHANNEL_REQUIRED, print);
	}

	@Test(groups = {"api", "regression", "dining", "showDiningService", "negative"})
	public void missingReservationNumber() {
		TestReporter.logScenario("Missing Reservation Number");
		PrintTicket print = new PrintTicket(environment, "Main");
		print.setReservationnumber(BaseSoapCommands.REMOVE_NODE.toString());
		sendRequestAndValidateFaultString("RECORD NOT FOUND : NO RESERVATION FOUND WITH 0", DiningErrorCode.RECORD_NOT_FOUND_EXCEPTION, print);
	}

	@Test(groups = {"api", "regression", "dining", "showDiningService", "negative"})
	public void missingSalesChannel() {
		TestReporter.logScenario("Invalid Sales Channel");
		PrintTicket print = new PrintTicket(environment, "Main");
		print.setSalesChannel(BaseSoapCommands.REMOVE_NODE.toString());
		sendRequestAndValidateFaultString("Sales Channel is required : null", DiningErrorCode.SALES_CHANNEL_REQUIRED, print);
	}

	@Test(groups = {"api", "regression", "dining", "showDiningService", "negative"})
	public void missingCommunicationChannel() {
		TestReporter.logScenario("Invalid Communications Channel");
		PrintTicket print = new PrintTicket(environment, "Main");
		print.setCommunicationChannel(BaseSoapCommands.REMOVE_NODE.toString());
		sendRequestAndValidateFaultString("communication Channel is required : null", DiningErrorCode.COMMUNICATION_CHANNEL_REQUIRED, print);
	}
	
    private void sendRequestAndValidateFaultString(String fault, ApplicationErrorCode error, PrintTicket print){
    	print.sendRequest();
		validateApplicationError(print, error);
    	TestReporter.logAPI(!print.getFaultString().contains(fault), print.getFaultString(), print);
		logItems(print);
    }
	
	private void logItems(PrintTicket print){
		logValidItems.get().addItem("ShowDiningServiceIF", "printTicket", true);
		validateLogs(print, logValidItems.get(), 10000);
		
		LogItems logInvalidItems = new LogItems();
		logInvalidItems.addItem("FolioServiceIF", "retrieveAccountingTransactions", false);
		logInvalidItems.addItem("GuestLinkServiceV1SEI", "createEntitlementReference", false);
		logInvalidItems.addItem("PartyIF", "retrieveParty", false);
		logInvalidItems.addItem("PartyIF", "retrievePartyBasicInformation", false);
		logInvalidItems.addItem("AccommodationInventoryRequestComponentServiceIF", "retrieveAssignmentOwner", false);
		logInvalidItems.addItem("PackagingService", "getProducts", false);
		logInvalidItems.addItem("GuestLinkServiceV1", "createEntitlementReference", false);
		logInvalidItems.addItem("ShowDiningServiceIF", "retrieve", false);
		validateNotInLogs(print, logInvalidItems);
	}
}