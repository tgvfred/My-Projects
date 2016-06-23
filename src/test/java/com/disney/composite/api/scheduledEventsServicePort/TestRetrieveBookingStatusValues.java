package com.disney.composite.api.scheduledEventsServicePort;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.scheduledEventsServicePort.operations.RetrieveBookingStatusValues;
import com.disney.utils.TestReporter;

public class TestRetrieveBookingStatusValues {
	// Defining global variables
	protected String testName = null;
	protected String environment = null;
	
	@BeforeMethod(alwaysRun = true)
	@Parameters({ "environment" })
	public void setup(String environment) {this.environment = environment;}

	@Test(groups = {"api", "regression", "dining", "scheduledEventsServicePort"})
	public void testRetrieveBookingStatusValues(){
		TestReporter.logStep("Retrieve Booking Status Values");
		RetrieveBookingStatusValues retrieveBookingStatusValues = new RetrieveBookingStatusValues(environment);
		retrieveBookingStatusValues.sendRequest();
		TestReporter.logAPI(!retrieveBookingStatusValues.getResponseStatusCode().equals("200"), "An error occurred during retrieval.", retrieveBookingStatusValues);
		TestReporter.assertGreaterThanZero(retrieveBookingStatusValues.getNumberOfBookingStatusValues());
	}
}