package com.disney.composite.api.diningModule.scheduledEventsBatchService;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.testng.SkipException;
import org.testng.annotations.Test;

import com.disney.api.soapServices.applicationError.DiningErrorCode;
import com.disney.api.soapServices.applicationError.LiloSystemErrorCode;
import com.disney.api.soapServices.core.BaseSoapCommands;
import com.disney.api.soapServices.diningModule.scheduledEventsBatchService.operations.Checkout;
import com.disney.api.soapServices.diningModule.scheduledEventsBatchService.operations.RetrieveReservationsForAutoCheckout;
import com.disney.composite.BaseTest;
import com.disney.utils.Randomness;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.database.LogItems;

public class TestAutoCheckout extends BaseTest{
	private String date;
	private Map<String, String> reservations = new HashMap<String, String>();
	private String reservation;
	private Checkout checkout;
	private String invalidNumber = "1";
	private int maxDaysOut = 45;
	
	@Test(groups = {"api", "regression", "dining", "scheduledEventsBatchService"})
	public void testRetrieveReservationsForAutoCheckout(){
		TestReporter.logScenario("RetrieveReservationsForAutoCheckout");
		RetrieveReservationsForAutoCheckout retrieve = new RetrieveReservationsForAutoCheckout(environment, "ForAcctCntrSE_WDW");
		int daysOut = 0;
		do{
			daysOut++;
			date = Randomness.generateCurrentXMLDatetime(daysOut);
			retrieve.setProcessDate(date);
			retrieve.sendRequest();
			TestReporter.logAPI(!retrieve.getResponseStatusCode().equals("200"), "An error occurred retrieving reservations for auto checkout: " + retrieve.getFaultString(), retrieve);
			reservations = retrieve.getAllReservationNumbers();
		}while(reservations.size() == 0 && daysOut <= maxDaysOut);
		TestReporter.assertTrue(reservations.size() > 0, "No reservations were returned for the date ["+date+"]");
		
		LogItems logValidItems = new LogItems();
		logValidItems.addItem("ScheduledEventsServiceIF", "retrieveReservationsForAutoCheckout", false);			
		validateLogs(checkout, logValidItems, 10000);
	}
	
	@Test(groups = {"api", "regression", "dining", "scheduledEventsBatchService"}, dependsOnMethods="testRetrieveReservationsForAutoCheckout")
	public void testAutoCheckout(){
		TestReporter.logScenario("Checkout");
		if(reservations.size() == 0) throw new SkipException("No reservations were returned for the date ["+date+"].");
		Iterator<String> it = reservations.values().iterator();
		reservation = it.next().toString();
		checkout = new Checkout(environment);
		checkout.setTravelPlanSegmentId(reservation);
		checkout.sendRequest();
		TestReporter.logAPI(!checkout.getResponseStatusCode().equals("200"), "An error occurred checking out reservation ["+reservation+"]: " + checkout.getFaultString(), checkout);
		TestReporter.assertTrue(checkout.isSuccessfullyCheckedOut(), "Verify the reservation is successfully checked out.");
		TestReporter.assertEquals(reservation, checkout.getReservationNumber(), "Verify the actual reservation number ["+checkout.getReservationNumber()+"] matches the expected reservation number ["+reservation+"].");

		LogItems logValidItems = new LogItems();
		logValidItems.addItem("TravelPlanServiceCrossReferenceV3SEI", "updateOrder", false);
		logValidItems.addItem("PartyIF", "retrieveParty", false);
		logValidItems.addItem("ScheduledEventsServiceIF", "checkout", false);
		
		validateLogs(checkout, logValidItems, 10000);
	}	

	@Test(groups = {"api", "regression", "dining", "scheduledEventsBatchService", "negative"}, dependsOnMethods="testAutoCheckout")
	public void testAutoCheckout_InvalidReservationStatus_PastVisit(){
		TestReporter.logScenario("Checkout_InvalidReservationStatus_PastVisit");
		if(checkout == null) checkout = new Checkout(environment); 
		checkout = new Checkout(environment);
		checkout.setTravelPlanSegmentId(reservation);
		checkout.sendRequest();	
		validateApplicationError(checkout, DiningErrorCode.INVALID_TRAVEL_STATUS);
		TestReporter.logAPI(!checkout.getFaultString().contains("Travel Status is invalid  : INVALID RESERVATION STATUS.CANNOT CHANGE THE STATUS TO PAST VISIT!"), checkout.getFaultString() ,checkout);

		LogItems logValidItems = new LogItems();
		logValidItems.addItem("ScheduledEventsServiceIF", "checkout", true);
		validateLogs(checkout, logValidItems, 10000);
		
		logValidItems = new LogItems();
		logValidItems.addItem("TravelPlanServiceCrossReferenceV3SEI", "updateOrder", false);
		logValidItems.addItem("PartyIF", "retrieveParty", false);
		validateNotInLogs(checkout, logValidItems);
	}	

	@Test(groups = {"api", "regression", "dining", "scheduledEventsBatchService", "negative"})
	public void testAutoCheckout_InvalidReservationNumber(){
		TestReporter.logScenario("Checkout_InvalidReservationNumber");
		Checkout checkout = new Checkout(environment);
		checkout.setTravelPlanSegmentId(invalidNumber);
		checkout.sendRequest();	
		validateApplicationError(checkout, DiningErrorCode.RECORD_NOT_FOUND_EXCEPTION);
		TestReporter.logAPI(!checkout.getFaultString().contains("RECORD NOT FOUND : NO RESERVATION FOUND WITH 1"), checkout.getFaultString() ,checkout);

		LogItems logValidItems = new LogItems();
		logValidItems.addItem("ScheduledEventsServiceIF", "checkout", true);
		validateLogs(checkout, logValidItems, 10000);
		
		logValidItems = new LogItems();
		logValidItems.addItem("TravelPlanServiceCrossReferenceV3SEI", "updateOrder", false);
		logValidItems.addItem("PartyIF", "retrieveParty", false);
		validateNotInLogs(checkout, logValidItems);
	}	

	@Test(groups = {"api", "regression", "dining", "scheduledEventsBatchService", "negative"})
	public void testAutoCheckout_MissingTravelPlanSegmentId(){
		TestReporter.logScenario("Checkout_MissingTravelPlanSegmentId");
		Checkout checkout = new Checkout(environment);
		checkout.setTravelPlanSegmentId(BaseSoapCommands.REMOVE_NODE.toString());
		checkout.sendRequest();	
		TestReporter.logAPI(!checkout.getFaultString().contains("RECORD NOT FOUND : NO RESERVATION FOUND WITH 0"), checkout.getFaultString() ,checkout);
	}
	
	@Test(groups = {"api", "regression", "dining", "scheduledEventsBatchService", "negative"})
	public void testAutoCheckout_MissingCheckoutRequestNode(){
		TestReporter.logScenario("Checkout_MissingCheckoutRequestNode");
		Checkout checkout = new Checkout(environment);
		checkout.setRequestNodeValueByXPath("/Envelope/Body/checkout/checkoutRequest", BaseSoapCommands.REMOVE_NODE.toString());
		checkout.sendRequest();	
		TestReporter.logAPI(!checkout.getFaultString().contains("Unexpected Error occurred : checkout : java.lang.NullPointerException"), checkout.getFaultString() ,checkout);
	}
	@Test(groups = {"api", "regression", "dining", "scheduledEventsBatchService", "negative"})
	public void testAutoCheckout_MissingProcessDate(){
		TestReporter.logScenario("RetrieveReservationsForAutoCheckout_MissingProcessDate");
		TestReporter.logStep("Retrieve Reservations for Auto Checkout");
		RetrieveReservationsForAutoCheckout retrieveReservationForAutoCheckout = new RetrieveReservationsForAutoCheckout(environment, "ForAcctCntrSE_WDW");
		retrieveReservationForAutoCheckout.setProcessDate(BaseSoapCommands.REMOVE_NODE.toString());
		retrieveReservationForAutoCheckout.sendRequest();
		validateApplicationError(retrieveReservationForAutoCheckout, LiloSystemErrorCode.UNEXPECTED_ERROR);
		TestReporter.logAPI(!retrieveReservationForAutoCheckout.getFaultString().contains("Unexpected Error occurred : retrieveReservationsForAutoCheckout : java.lang.NullPointerException"), retrieveReservationForAutoCheckout.getFaultString(), retrieveReservationForAutoCheckout);
		
		LogItems logItems = new LogItems();
		logItems.addItem("ScheduledEventsServiceIF", "retrieveReservationsForAutoCheckout", true);	
		validateLogs(retrieveReservationForAutoCheckout, logItems);
	}
	@Test(groups = {"api", "regression", "dining", "scheduledEventsBatchService", "negative"})
	public void testAutoCheckout_InvalidDateEqualCondition(){
		TestReporter.logScenario("RetrieveReservationsForAutoCheckout_InvalidDateEqualCondition");
		TestReporter.logStep("Retrieve Reservations for Auto Checkout");
		RetrieveReservationsForAutoCheckout retrieveReservationForAutoCheckout = new RetrieveReservationsForAutoCheckout(environment, "ForAcctCntrSE_WDW");
		retrieveReservationForAutoCheckout.setDateEqualCondition_Negative("==");
		retrieveReservationForAutoCheckout.sendRequest();
		validateApplicationError(retrieveReservationForAutoCheckout, LiloSystemErrorCode.UNEXPECTED_ERROR);
		TestReporter.logAPI(!retrieveReservationForAutoCheckout.getFaultString().contains("org.hibernate.exception.SQLGrammarException"), retrieveReservationForAutoCheckout.getFaultString(), retrieveReservationForAutoCheckout);
		
		LogItems logItems = new LogItems();
		logItems.addItem("ScheduledEventsServiceIF", "retrieveReservationsForAutoCheckout", true);	
		validateLogs(retrieveReservationForAutoCheckout, logItems);
	}
	@Test(groups = {"api", "regression", "dining", "scheduledEventsBatchService", "negative"})
	public void testAutoCheckout_MissingDateEqualCondition(){
		TestReporter.logScenario("RetrieveReservationsForAutoCheckout_MissingDateEqualCondition");
		TestReporter.logStep("Retrieve Reservations for Auto Checkout");
		RetrieveReservationsForAutoCheckout retrieveReservationForAutoCheckout = new RetrieveReservationsForAutoCheckout(environment, "ForAcctCntrSE_WDW");
		retrieveReservationForAutoCheckout.setDateEqualCondition_Negative(BaseSoapCommands.REMOVE_NODE.toString());
		retrieveReservationForAutoCheckout.sendRequest();
		validateApplicationError(retrieveReservationForAutoCheckout, LiloSystemErrorCode.UNEXPECTED_ERROR);
		TestReporter.logAPI(!retrieveReservationForAutoCheckout.getFaultString().contains("org.hibernate.exception.SQLGrammarException"), retrieveReservationForAutoCheckout.getFaultString(), retrieveReservationForAutoCheckout);
		
		LogItems logItems = new LogItems();
		logItems.addItem("ScheduledEventsServiceIF", "retrieveReservationsForAutoCheckout", true);	
		validateLogs(retrieveReservationForAutoCheckout, logItems);
	}
}