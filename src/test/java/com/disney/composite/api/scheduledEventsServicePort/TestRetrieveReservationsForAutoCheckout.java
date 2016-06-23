package com.disney.composite.api.scheduledEventsServicePort;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.scheduledEventsServicePort.operations.RetrieveReservationsForAutoCheckout;
import com.disney.utils.TestReporter;

public class TestRetrieveReservationsForAutoCheckout {
	// Defining global variables
	protected String testName = null;
	protected String environment = null;
	
	@BeforeMethod(alwaysRun = true)
	@Parameters({ "environment" })
	public void setup(String environment) {this.environment = environment;}

	@Test(groups = {"api", "regression", "dining", "scheduledEventsServicePort"})
	public void testRetrieveReservationForAutoCheckout(){
		TestReporter.logStep("Retrieve Reservations for Auto Checkout");
		RetrieveReservationsForAutoCheckout retrieveReservationForAutoCheckout = new RetrieveReservationsForAutoCheckout(environment, "ForAcctCntrSE_WDW");
		retrieveReservationForAutoCheckout.setDateEqualCondition("!=");
		retrieveReservationForAutoCheckout.sendRequest();
		TestReporter.logAPI(!retrieveReservationForAutoCheckout.getResponseStatusCode().equals("200"), "An error occurred during retrieval.", retrieveReservationForAutoCheckout);
		TestReporter.assertGreaterThanZero(retrieveReservationForAutoCheckout.getNumberOfReservations());
	}
}