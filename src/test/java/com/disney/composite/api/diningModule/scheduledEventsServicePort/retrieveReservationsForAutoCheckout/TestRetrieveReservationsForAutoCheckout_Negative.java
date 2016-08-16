package com.disney.composite.api.diningModule.scheduledEventsServicePort.retrieveReservationsForAutoCheckout;

import org.testng.SkipException;
import org.testng.annotations.Test;

import com.disney.api.soapServices.applicationError.LiloSystemErrorCode;
import com.disney.api.soapServices.core.BaseSoapCommands;
import com.disney.api.soapServices.diningModule.scheduledEventsServicePort.operations.RetrieveReservationsForAutoCheckout;
import com.disney.composite.BaseTest;
import com.disney.utils.Environment;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.database.LogItems;

public class TestRetrieveReservationsForAutoCheckout_Negative extends BaseTest{

	@Test(groups = {"api", "regression", "dining", "scheduledEventsServicePort", "negative"})
	public void testMissingProcessDate(){
		if(Environment.getEnvironmentName(environment).equalsIgnoreCase("bashful_cm"))
			throw new SkipException("This test is not valid to run in latest since 'DEV3 is not in ESB where as other env are in EBR'");
		TestReporter.logStep("Retrieve Reservations for Auto Checkout");
		RetrieveReservationsForAutoCheckout retrieveReservationForAutoCheckout = new RetrieveReservationsForAutoCheckout(environment, "ForAcctCntrSE_WDW");
		retrieveReservationForAutoCheckout.setProcessDate(BaseSoapCommands.REMOVE_NODE.toString());
		retrieveReservationForAutoCheckout.sendRequest();
		TestReporter.logAPI(!retrieveReservationForAutoCheckout.getFaultString().contains("Unexpected Error occurred : retrieveReservationsForAutoCheckout : java.lang.NullPointerException"), retrieveReservationForAutoCheckout.getFaultString(), retrieveReservationForAutoCheckout);
		validateApplicationError(retrieveReservationForAutoCheckout, LiloSystemErrorCode.UNEXPECTED_ERROR);
		
		LogItems logItems = new LogItems();
		logItems.addItem("ScheduledEventsServiceIF", "retrieveReservationsForAutoCheckout", true);	
		validateLogs(retrieveReservationForAutoCheckout, logItems);
	}
	
	@Test(groups = {"api", "regression", "dining", "scheduledEventsServicePort", "negative"})
	public void testInvalidDateEqualCondition(){
		if(Environment.getEnvironmentName(environment).equalsIgnoreCase("bashful_cm"))
			throw new SkipException("This test is not valid to run in latest since 'DEV3 is not in ESB where as other env are in EBR'");
		TestReporter.logStep("Retrieve Reservations for Auto Checkout");
		RetrieveReservationsForAutoCheckout retrieveReservationForAutoCheckout = new RetrieveReservationsForAutoCheckout(environment, "ForAcctCntrSE_WDW");
		retrieveReservationForAutoCheckout.setDateEqualCondition_Negative("==");
		retrieveReservationForAutoCheckout.sendRequest();
		TestReporter.logAPI(!retrieveReservationForAutoCheckout.getFaultString().contains("Unexpected Error occurred : retrieveReservationsForAutoCheckout : org.hibernate.exception.SQLGrammarException: could not extract ResultSet : org.hibernate.exception.SQLGrammarException: could not extract ResultSet; nested exception is javax.persistence.PersistenceException: org.hibernate.exception.SQLGrammarException: could not extract ResultSet"), retrieveReservationForAutoCheckout.getFaultString(), retrieveReservationForAutoCheckout);
		validateApplicationError(retrieveReservationForAutoCheckout, LiloSystemErrorCode.UNEXPECTED_ERROR);
		
		LogItems logItems = new LogItems();
		logItems.addItem("ScheduledEventsServiceIF", "retrieveReservationsForAutoCheckout", true);	
		validateLogs(retrieveReservationForAutoCheckout, logItems);
	}
	@Test(groups = {"api", "regression", "dining", "scheduledEventsServicePort", "negative"})
	public void testMissingDateEqualCondition(){
		if(Environment.getEnvironmentName(environment).equalsIgnoreCase("bashful_cm"))
			throw new SkipException("This test is not valid to run in latest since 'DEV3 is not in ESB where as other env are in EBR'");
		TestReporter.logStep("Retrieve Reservations for Auto Checkout");
		RetrieveReservationsForAutoCheckout retrieveReservationForAutoCheckout = new RetrieveReservationsForAutoCheckout(environment, "ForAcctCntrSE_WDW");
		retrieveReservationForAutoCheckout.setDateEqualCondition_Negative(BaseSoapCommands.REMOVE_NODE.toString());
		retrieveReservationForAutoCheckout.sendRequest();
		TestReporter.logAPI(!retrieveReservationForAutoCheckout.getFaultString().contains("Unexpected Error occurred : retrieveReservationsForAutoCheckout : org.hibernate.exception.SQLGrammarException: could not extract ResultSet : org.hibernate.exception.SQLGrammarException: could not extract ResultSet; nested exception is javax.persistence.PersistenceException: org.hibernate.exception.SQLGrammarException: could not extract ResultSet"), retrieveReservationForAutoCheckout.getFaultString(), retrieveReservationForAutoCheckout);
		validateApplicationError(retrieveReservationForAutoCheckout, LiloSystemErrorCode.UNEXPECTED_ERROR);
		
		LogItems logItems = new LogItems();
		logItems.addItem("ScheduledEventsServiceIF", "retrieveReservationsForAutoCheckout", true);	
		validateLogs(retrieveReservationForAutoCheckout, logItems);
	}	
}