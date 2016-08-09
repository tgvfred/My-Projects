package com.disney.composite.api.diningModule.scheduledEventsServicePort.retrieveReservationsForAutoCheckout;

import java.util.Collection;

import org.testng.SkipException;
import org.testng.annotations.Test;

import com.disney.api.soapServices.core.BaseSoapCommands;
import com.disney.api.soapServices.diningModule.scheduledEventsServicePort.operations.RetrieveReservationsForAutoCheckout;
import com.disney.composite.BaseTest;
import com.disney.utils.Environment;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.database.LogItems;

public class TestRetrieveReservationsForAutoCheckout extends BaseTest{

	@Test(groups = {"api", "regression", "dining", "scheduledEventsServicePort"})
	public void testRetrieveReservationForAutoCheckout(){
//		if(Environment.getEnvironmentName(environment).equalsIgnoreCase("bashful_cm"))
//			throw new SkipException("This test is not valid to run in latest since 'DEV3 is not in EBR where as other env are in EBR'");
		TestReporter.logStep("Retrieve Reservations for Auto Checkout");
		RetrieveReservationsForAutoCheckout retrieveReservationForAutoCheckout = new RetrieveReservationsForAutoCheckout(environment, "ForAcctCntrSE_WDW");
		retrieveReservationForAutoCheckout.setDateEqualCondition("!=");
		sendRequestAndValidateLogs(retrieveReservationForAutoCheckout, true);
	}	
	@Test(groups = {"api", "regression", "dining", "scheduledEventsServicePort"})
	public void testInvalidSourceAccountingCenter(){
//		if(Environment.getEnvironmentName(environment).equalsIgnoreCase("bashful_cm"))
//			throw new SkipException("This test is not valid to run in latest since 'DEV3 is not in EBR where as other env are in EBR'");
		TestReporter.logStep("Retrieve Reservations for Auto Checkout");
		RetrieveReservationsForAutoCheckout retrieveReservationForAutoCheckout = new RetrieveReservationsForAutoCheckout(environment, "ForAcctCntrSE_WDW");
		retrieveReservationForAutoCheckout.setDateEqualCondition("!=");
		retrieveReservationForAutoCheckout.setSourceAccountingCenter("99");
		sendRequestAndValidateLogs(retrieveReservationForAutoCheckout, false);
	}
	@Test(groups = {"api", "regression", "dining", "scheduledEventsServicePort"})
	public void testMissingSourceAccountingCenter(){
//		if(Environment.getEnvironmentName(environment).equalsIgnoreCase("bashful_cm"))
//			throw new SkipException("This test is not valid to run in latest since 'DEV3 is not in EBR where as other env are in EBR'");
		TestReporter.logStep("Retrieve Reservations for Auto Checkout");
		RetrieveReservationsForAutoCheckout retrieveReservationForAutoCheckout = new RetrieveReservationsForAutoCheckout(environment, "ForAcctCntrSE_WDW");
		retrieveReservationForAutoCheckout.setDateEqualCondition("!=");
		retrieveReservationForAutoCheckout.setSourceAccountingCenter(BaseSoapCommands.REMOVE_NODE.toString());
		sendRequestAndValidateLogs(retrieveReservationForAutoCheckout, true);
	}
	@Test(groups = {"api", "regression", "dining", "scheduledEventsServicePort"})
	public void testDateInThePast(){
//		if(Environment.getEnvironmentName(environment).equalsIgnoreCase("bashful_cm"))
//			throw new SkipException("This test is not valid to run in latest since 'DEV3 is not in EBR where as other env are in EBR'");
		TestReporter.logStep("Retrieve Reservations for Auto Checkout");
		RetrieveReservationsForAutoCheckout retrieveReservationForAutoCheckout = new RetrieveReservationsForAutoCheckout(environment, "ForAcctCntrSE_WDW");
		retrieveReservationForAutoCheckout.setDateEqualCondition("=");
		retrieveReservationForAutoCheckout.setProcessDateDaysOut("-730");
		sendRequestAndValidateLogs(retrieveReservationForAutoCheckout, false);
	}

	private void sendRequestAndValidateLogs(RetrieveReservationsForAutoCheckout retrieveReservationForAutoCheckout, boolean reservationsReturned){
		retrieveReservationForAutoCheckout.sendRequest();
		TestReporter.logAPI(!retrieveReservationForAutoCheckout.getResponseStatusCode().equals("200"), "An error occurred during retrieval: " + retrieveReservationForAutoCheckout.getFaultString(), retrieveReservationForAutoCheckout);
		if(reservationsReturned){
			TestReporter.assertGreaterThanZero(retrieveReservationForAutoCheckout.getNumberOfReservations());

			Collection<String> reservations = retrieveReservationForAutoCheckout.getAllReservationNumbers().values();
			for(String reservation : reservations){
				TestReporter.log("Reservation: " + reservation);
			}
		}
		else{TestReporter.assertTrue(retrieveReservationForAutoCheckout.getNumberOfReservations() == 0, "Verify that the number of reservations is zero.");}
		
		LogItems logItems = new LogItems();
		logItems.addItem("ScheduledEventsServiceIF", "retrieveReservationsForAutoCheckout", false);	
		validateLogs(retrieveReservationForAutoCheckout, logItems);
	}
}