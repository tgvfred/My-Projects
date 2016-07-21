package com.disney.composite.api.scheduledEventsServicePort_old;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.scheduledEventsServicePort.operations.RetrieveDiningReservations;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.guestFactory.HouseHold;
import com.disney.utils.dataFactory.staging.bookSEReservation.EventDiningReservation;
import com.disney.utils.dataFactory.staging.bookSEReservation.ScheduledEventReservation;

public class TestRetrieveDiningReservations {
	// Defining global variables
	protected String testName = null;
	protected String environment = null;
	private ScheduledEventReservation book = null;
	
	@BeforeMethod(alwaysRun = true)
	@Parameters({ "environment" })
	public void preReq_BookReservation(String environment) {
		this.environment = environment;

		book = new EventDiningReservation(environment);
		book.setParty(new HouseHold(1));
		book.book("BookGuaranteedTS");
		book.arrived();
	}
	
	@Test(groups = {"api", "regression", "dining", "scheduledEventsServicePort"})
	public void testRetrieveDiningReservations(){
		TestReporter.logStep("Retrieve Dining Reservations");
		RetrieveDiningReservations retrieve = new RetrieveDiningReservations(environment, "Main");
		retrieve.setFacilityId("1");
		retrieve.sendRequest();
		TestReporter.logAPI(!retrieve.getResponseStatusCode().equals("200"), "An error occurred during retrieval.", retrieve);
		TestReporter.assertTrue(retrieve.getDiningReservations().getLength() > 0, "No dining reservations were returned.");
	}
}