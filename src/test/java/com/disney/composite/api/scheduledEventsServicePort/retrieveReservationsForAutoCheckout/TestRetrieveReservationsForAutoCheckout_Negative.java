package com.disney.composite.api.scheduledEventsServicePort.retrieveReservationsForAutoCheckout;

import org.testng.annotations.Test;

import com.disney.api.soapServices.applicationError.LiloSystemErrorCode;
import com.disney.api.soapServices.core.BaseSoapCommands;
import com.disney.api.soapServices.scheduledEventsServicePort.operations.RetrieveReservationsForAutoCheckout;
import com.disney.composite.BaseTest;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.database.LogItems;

public class TestRetrieveReservationsForAutoCheckout_Negative extends BaseTest{

	@Test(groups = {"api", "regression", "dining", "scheduledEventsServicePort", "negative"})
	public void testMissingProcessDate(){
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
	
	@Test(groups = {"api", "regression", "dining", "scheduledEventsServicePort", "negative"})
	public void testInvalidDateEqualCondition(){
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
	@Test(groups = {"api", "regression", "dining", "scheduledEventsServicePort", "negative"})
	public void testMissingDateEqualCondition(){
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