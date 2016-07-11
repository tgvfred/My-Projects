package com.disney.composite.api.showDiningService.reprintTicket;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.core.BaseSoapCommands;
import com.disney.api.soapServices.showDiningService.operations.ReprintTicket;
import com.disney.composite.BaseTest;
import com.disney.utils.Randomness;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.database.LogItems;
import com.disney.utils.dataFactory.guestFactory.HouseHold;

public class TestReprintTicket_Negative extends BaseTest{
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
		String number = Randomness.randomNumber(4);
		ReprintTicket reprint = new ReprintTicket(environment, "Main");
		reprint.setReservationNumber(number);
		sendRequestAndValidateFaultString("RECORD NOT FOUND : NO RESERVATION FOUND WITH "+number, reprint);
	}

	@Test(groups = {"api", "regression", "dining", "showDiningService", "negative"})
	public void invalidSalesChannel() {
		TestReporter.logScenario("Invalid Sales Channel");
		ReprintTicket reprint = new ReprintTicket(environment, "Main");
		reprint.setSalesChannel(Randomness.randomString(4));
		sendRequestAndValidateFaultString("Sales Channel is required : null", reprint);
	}

	@Test(groups = {"api", "regression", "dining", "showDiningService", "negative"})
	public void invalidCommunicationChannel() {
		TestReporter.logScenario("Invalid Communications Channel");
		ReprintTicket reprint = new ReprintTicket(environment, "Main");
		reprint.setCommunicationChannel(Randomness.randomString(4));
		sendRequestAndValidateFaultString("communication Channel is required : null", reprint);
	}

	@Test(groups = {"api", "regression", "dining", "showDiningService", "negative"})
	public void invalidReprintReason() {
		String id = "-1";
		TestReporter.logScenario("Invalid Reprint Reason ID");
		ReprintTicket reprint = new ReprintTicket(environment, "Main");
		reprint.setReprintReasonId(id);
		sendRequestAndValidateFaultString("INVALID REASON ID! : INVALID REPRINT REASON#"+id, reprint);
	}

	@Test(groups = {"api", "regression", "dining", "showDiningService", "negative"})
	public void missingReservationNumber() {
		TestReporter.logScenario("Missing Reservation Number");
		ReprintTicket reprint = new ReprintTicket(environment, "Main");
		reprint.setReservationNumber(BaseSoapCommands.REMOVE_NODE.toString());
		sendRequestAndValidateFaultString("RECORD NOT FOUND : NO RESERVATION FOUND WITH 0", reprint);
	}

	@Test(groups = {"api", "regression", "dining", "showDiningService", "negative"})
	public void missingSalesChannel() {
		TestReporter.logScenario("Invalid Sales Channel");
		ReprintTicket reprint = new ReprintTicket(environment, "Main");
		reprint.setSalesChannel(BaseSoapCommands.REMOVE_NODE.toString());
		sendRequestAndValidateFaultString("Sales Channel is required : null", reprint);
	}

	@Test(groups = {"api", "regression", "dining", "showDiningService", "negative"})
	public void missingCommunicationChannel() {
		TestReporter.logScenario("Invalid Communications Channel");
		ReprintTicket reprint = new ReprintTicket(environment, "Main");
		reprint.setCommunicationChannel(BaseSoapCommands.REMOVE_NODE.toString());
		sendRequestAndValidateFaultString("communication Channel is required : null", reprint);
	}

	@Test(groups = {"api", "regression", "dining", "showDiningService", "negative"})
	public void missingReprintReason() {
		TestReporter.logScenario("Invalid Reprint Reason ID");
		ReprintTicket reprint = new ReprintTicket(environment, "Main");
		reprint.setReprintReasonId(BaseSoapCommands.REMOVE_NODE.toString());
		sendRequestAndValidateFaultString("INVALID REASON ID! : INVALID REPRINT REASON#0", reprint);
	}	

    private void sendRequestAndValidateFaultString(String fault, ReprintTicket reprint){
    	reprint.sendRequest();
    	TestReporter.logAPI(!reprint.getFaultString().contains(fault), reprint.getFaultString(), reprint);
		logItems(reprint);
    }
	
	private void logItems(ReprintTicket reprint){
		logValidItems.get().addItem("ShowDiningServiceIF", "reprintTicket", true);
		validateLogs(reprint, logValidItems.get(), 10000);
		
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
		validateNotInLogs(reprint, logInvalidItems);
	}
}