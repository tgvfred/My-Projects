package com.disney.composite.api.diningBatch;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.testng.SkipException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.applicationError.DiningErrorCode;
import com.disney.api.soapServices.applicationError.LiloSystemErrorCode;
import com.disney.api.soapServices.core.BaseSoapCommands;
import com.disney.api.soapServices.scheduledEventsServicePort.operations.Checkout;
import com.disney.api.soapServices.scheduledEventsServicePort.operations.RetrieveReservationsForAutoCheckout;
import com.disney.composite.BaseTest;
import com.disney.utils.Environment;
import com.disney.utils.Randomness;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.database.LogItems;

public class AutoCheckout extends BaseTest{
	private String date = Randomness.generateCurrentXMLDatetime(1);
	private Map<String, String> reservations = new HashMap<String, String>();
	private String reservation;
	private Checkout checkout;
	private LogItems logValidItems;
	private String invalidNumber = "1";
	
	@Override
	@BeforeMethod(alwaysRun=true)
	@Parameters("environment")
	public void setup(@Optional String environment){
		this.environment = environment;
		RetrieveReservationsForAutoCheckout retrieve = new RetrieveReservationsForAutoCheckout(environment, "ForAcctCntrSE_WDW");
		retrieve.setProcessDate(date);
		retrieve.sendRequest();
		TestReporter.logAPI(!retrieve.getResponseStatusCode().equals("200"), "An error occurred retrieving reservations for auto checkout: " + retrieve.getFaultString(), retrieve);
		if(reservation == null)reservations = retrieve.getAllReservationNumbers();
		TestReporter.assertTrue(reservations.size() > 0, "No reservations were returned for the date ["+date+"]");
	}
	
	@Test(groups = {"api", "regression", "dining", "batch"})
	public void testAutoCheckout(){
		Iterator<String> it = reservations.values().iterator();
		reservation = it.next().toString();
		checkout = new Checkout(environment);
		checkout.setTravelPlanSegmentId(reservation);
		checkout.sendRequest();
		TestReporter.logAPI(!checkout.getResponseStatusCode().equals("200"), "An error occurred checking out reservation ["+reservation+"]: " + checkout.getFaultString(), checkout);
		TestReporter.assertTrue(checkout.isSuccessfullyCheckedOut(), "Verify the reservation is successfully checked out.");
		TestReporter.assertEquals(reservation, checkout.getReservationNumber(), "Verify the actual reservation number ["+checkout.getReservationNumber()+"] matches the expected reservation number ["+reservation+"].");

		logValidItems = new LogItems();
		logValidItems.addItem("TravelPlanServiceCrossReferenceV3SEI", "updateOrder", false);
		logValidItems.addItem("PartyIF", "retrieveParty", false);
		logValidItems.addItem("ScheduledEventsServiceIF", "checkout", false);
		
		validateLogs(checkout, logValidItems, 10000);
	}	

	@Test(groups = {"api", "regression", "dining", "batch", "negative"}, dependsOnMethods="testAutoCheckout")
	public void testAutoCheckout_InvalidReservationStatus_PastVisit(){
		if(checkout == null) checkout = new Checkout(environment); 
		checkout = new Checkout(environment);
		checkout.setTravelPlanSegmentId(reservation);
		checkout.sendRequest();	
		validateApplicationError(checkout, DiningErrorCode.INVALID_TRAVEL_STATUS);
		TestReporter.logAPI(!checkout.getFaultString().contains("Travel Status is invalid  : INVALID RESERVATION STATUS.CANNOT CHANGE THE STATUS TO PAST VISIT!"), checkout.getFaultString() ,checkout);

		logValidItems = new LogItems();
		logValidItems.addItem("ScheduledEventsServiceIF", "checkout", true);
		validateLogs(checkout, logValidItems, 10000);
		
		logValidItems = new LogItems();
		logValidItems.addItem("TravelPlanServiceCrossReferenceV3SEI", "updateOrder", false);
		logValidItems.addItem("PartyIF", "retrieveParty", false);
		validateNotInLogs(checkout, logValidItems);
	}	

	@Test(groups = {"api", "regression", "dining", "batch", "negative"})
	public void testAutoCheckout_InvalidReservationNumber(){
		Checkout checkout = new Checkout(environment);
		checkout.setTravelPlanSegmentId(invalidNumber);
		checkout.sendRequest();	
		validateApplicationError(checkout, DiningErrorCode.RECORD_NOT_FOUND_EXCEPTION);
		TestReporter.logAPI(!checkout.getFaultString().contains("RECORD NOT FOUND : NO RESERVATION FOUND WITH 1"), checkout.getFaultString() ,checkout);

		logValidItems = new LogItems();
		logValidItems.addItem("ScheduledEventsServiceIF", "checkout", true);
		validateLogs(checkout, logValidItems, 10000);
		
		logValidItems = new LogItems();
		logValidItems.addItem("TravelPlanServiceCrossReferenceV3SEI", "updateOrder", false);
		logValidItems.addItem("PartyIF", "retrieveParty", false);
		validateNotInLogs(checkout, logValidItems);
	}	

	@Test(groups = {"api", "regression", "dining", "batch", "negative"})
	public void testAutoCheckout_MissingTravelPlanSegmentId(){
		Checkout checkout = new Checkout(environment);
		checkout.setTravelPlanSegmentId(BaseSoapCommands.REMOVE_NODE.toString());
		checkout.sendRequest();	
		TestReporter.logAPI(!checkout.getFaultString().contains("RECORD NOT FOUND : NO RESERVATION FOUND WITH 0"), checkout.getFaultString() ,checkout);
	}
	
	@Test(groups = {"api", "regression", "dining", "batch", "negative"})
	public void testAutoCheckout_MissingCheckoutRequestNode(){
		Checkout checkout = new Checkout(environment);
		checkout.setRequestNodeValueByXPath("/Envelope/Body/checkout/checkoutRequest", BaseSoapCommands.REMOVE_NODE.toString());
		checkout.sendRequest();	
		TestReporter.logAPI(!checkout.getFaultString().contains("Unexpected Error occurred : checkout : java.lang.NullPointerException"), checkout.getFaultString() ,checkout);
	}
	@Test(groups = {"api", "regression", "dining", "batch", "negative"})
	public void testAutoCheckout_MissingProcessDate(){
		if(Environment.getEnvironmentName(environment).equalsIgnoreCase("bashful_cm"))
			throw new SkipException("This test is not valid to run in latest since 'DEV3 is not in EBR where as other env are in EBR'");
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
	@Test(groups = {"api", "regression", "dining", "batch", "negative"})
	public void testAutoCheckout_InvalidDateEqualCondition(){
		if(Environment.getEnvironmentName(environment).equalsIgnoreCase("bashful_cm"))
			throw new SkipException("This test is not valid to run in latest since 'DEV3 is not in EBR where as other env are in EBR'");
		TestReporter.logStep("Retrieve Reservations for Auto Checkout");
		RetrieveReservationsForAutoCheckout retrieveReservationForAutoCheckout = new RetrieveReservationsForAutoCheckout(environment, "ForAcctCntrSE_WDW");
		retrieveReservationForAutoCheckout.setDateEqualCondition_Negative("==");
		retrieveReservationForAutoCheckout.sendRequest();
		validateApplicationError(retrieveReservationForAutoCheckout, LiloSystemErrorCode.UNEXPECTED_ERROR);
		TestReporter.logAPI(!retrieveReservationForAutoCheckout.getFaultString().contains("Unexpected Error occurred : retrieveReservationsForAutoCheckout : org.hibernate.exception.SQLGrammarException: could not extract ResultSet : org.hibernate.exception.SQLGrammarException: could not extract ResultSet; nested exception is javax.persistence.PersistenceException: org.hibernate.exception.SQLGrammarException: could not extract ResultSet"), retrieveReservationForAutoCheckout.getFaultString(), retrieveReservationForAutoCheckout);
		
		LogItems logItems = new LogItems();
		logItems.addItem("ScheduledEventsServiceIF", "retrieveReservationsForAutoCheckout", true);	
		validateLogs(retrieveReservationForAutoCheckout, logItems);
	}
	@Test(groups = {"api", "regression", "dining", "batch", "negative"})
	public void testAutoCheckout_MissingDateEqualCondition(){
		if(Environment.getEnvironmentName(environment).equalsIgnoreCase("bashful_cm"))
			throw new SkipException("This test is not valid to run in latest since 'DEV3 is not in EBR where as other env are in EBR'");
		TestReporter.logStep("Retrieve Reservations for Auto Checkout");
		RetrieveReservationsForAutoCheckout retrieveReservationForAutoCheckout = new RetrieveReservationsForAutoCheckout(environment, "ForAcctCntrSE_WDW");
		retrieveReservationForAutoCheckout.setDateEqualCondition_Negative(BaseSoapCommands.REMOVE_NODE.toString());
		retrieveReservationForAutoCheckout.sendRequest();
		validateApplicationError(retrieveReservationForAutoCheckout, LiloSystemErrorCode.UNEXPECTED_ERROR);
		TestReporter.logAPI(!retrieveReservationForAutoCheckout.getFaultString().contains("Unexpected Error occurred : retrieveReservationsForAutoCheckout : org.hibernate.exception.SQLGrammarException: could not extract ResultSet : org.hibernate.exception.SQLGrammarException: could not extract ResultSet; nested exception is javax.persistence.PersistenceException: org.hibernate.exception.SQLGrammarException: could not extract ResultSet"), retrieveReservationForAutoCheckout.getFaultString(), retrieveReservationForAutoCheckout);
		
		LogItems logItems = new LogItems();
		logItems.addItem("ScheduledEventsServiceIF", "retrieveReservationsForAutoCheckout", true);	
		validateLogs(retrieveReservationForAutoCheckout, logItems);
	}
}