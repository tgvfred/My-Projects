package com.disney.composite.api.diningBatch;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.applicationError.DiningErrorCode;
import com.disney.api.soapServices.scheduledEventsServicePort.operations.Checkout;
import com.disney.api.soapServices.scheduledEventsServicePort.operations.RetrieveReservationsForAutoCheckout;
import com.disney.composite.BaseTest;
import com.disney.utils.Randomness;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.database.LogItems;

public class AutoCheckout extends BaseTest{
	private String date = Randomness.generateCurrentXMLDatetime(1);
	private Map<String, String> reservations = new HashMap<String, String>();
	private String reservation;
	
	@Override
	@BeforeClass(alwaysRun=true)
	@Parameters("environment")
	public void setup(@Optional String environment){
		this.environment = environment;
		RetrieveReservationsForAutoCheckout retrieve = new RetrieveReservationsForAutoCheckout(environment, "ForAcctCntrSE_WDW");
		retrieve.setProcessDate(date);
		retrieve.sendRequest();
		TestReporter.logAPI(!retrieve.getResponseStatusCode().equals("200"), "An error occurred retrieving reservations for auto checkout: " + retrieve.getFaultString(), retrieve);
		reservations = retrieve.getAllReservationNumbers();
		TestReporter.assertTrue(reservations.size() > 0, "No reservations were returned for the date ["+date+"]");
	}
	
	@Test(groups = {"api", "regression", "dining", "batch"})
	public void autoCheckout(){
		Iterator<String> it = reservations.values().iterator();
		reservation = it.next().toString();
		Checkout checkout = new Checkout(environment);
		checkout.setTravelPlanSegmentId(reservation);
		checkout.sendRequest();
		TestReporter.logAPI(!checkout.getResponseStatusCode().equals("200"), "An error occurred checking out reservation ["+reservation+"]: " + checkout.getFaultString(), checkout);
		TestReporter.assertTrue(checkout.isSuccessfullyCheckedOut(), "Verify the reservation is successfully checked out.");
		TestReporter.assertEquals(reservation, checkout.getReservationNumber(), "Verify the actual reservation number ["+checkout.getReservationNumber()+"] matches the expected reservation number ["+reservation+"].");

		LogItems logValidItems = new LogItems();
		logValidItems.addItem("GuestLinkServiceV1SEI", "createEntitlementReference", false);
		logValidItems.addItem("TravelPlanServiceCrossReferenceV3SEI", "updateOrder", false);
//		logValidItems.addItem("ChargeGroupIF", "checkout", false);
		logValidItems.addItem("PartyIF", "retrieveParty", false);
//		logValidItems.addItem("PartyIF", "retrievePartyBasicInformation", false);
		logValidItems.addItem("FolioServiceIF", "retrieveAccountingTransactions", false);
		logValidItems.addItem("ScheduledEventsServiceIF", "checkout", false);
		logValidItems.addItem("AccommodationInventoryRequestComponentServiceIF", "retrieveAssignmentOwner", false);
		logValidItems.addItem("EventDiningServiceIF", "retrieve", false);
		
		validateLogs(checkout, logValidItems, 10000);
		
		checkout.sendRequest();
		validateApplicationError(checkout, DiningErrorCode.INVALID_TRAVEL_STATUS);
		TestReporter.logAPI(!checkout.getFaultString().contains("INVALID RESERVATION STATUS.CANNOT CHANGE THE STATUS TO ARRIVED"), checkout.getFaultString() ,checkout);

		logValidItems = new LogItems();
		logValidItems.addItem("ScheduledEventsServiceIF", "checkout", true);
		validateLogs(checkout, logValidItems);
	}
}