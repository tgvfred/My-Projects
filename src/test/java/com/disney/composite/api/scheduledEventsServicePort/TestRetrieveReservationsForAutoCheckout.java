package com.disney.composite.api.scheduledEventsServicePort;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.scheduledEventsServicePort.operations.RetrieveReservationsForAutoCheckout;
import com.disney.utils.TestReporter;

public class TestRetrieveReservationsForAutoCheckout {
	// Defining global variables
	protected ThreadLocal<String> testName = new ThreadLocal<String>();
	protected ThreadLocal<String> environment = new ThreadLocal<String>();
	
	@BeforeMethod(alwaysRun = true)
	@Parameters({ "environment" })
	public void setup(String environment) {this.environment.set(environment);}

	@Test(groups = {"api", "regression", "dining", "scheduledEventsServicePort"})
	public void testRetrieveReservationForAutoCheckout(){
		TestReporter.logStep("Retrieve Reservations for Auto Checkout");
		RetrieveReservationsForAutoCheckout retrieveReservationForAutoCheckout = new RetrieveReservationsForAutoCheckout(environment.get(), "ForAcctCntrSE_WDW");
		retrieveReservationForAutoCheckout.setDateEqualCondition("!=");
		retrieveReservationForAutoCheckout.sendRequest();
		TestReporter.logAPI(!retrieveReservationForAutoCheckout.getResponseStatusCode().equals("200"), "An error occurred during retrieval.", retrieveReservationForAutoCheckout);
		TestReporter.assertGreaterThanZero(retrieveReservationForAutoCheckout.getNumberOfReservations());
	}
}