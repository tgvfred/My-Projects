package com.disney.composite.api.scheduledEventsServicePort;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.scheduledEventsServicePort.operations.SearchByAgency;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.guestFactory.HouseHold;
import com.disney.utils.dataFactory.staging.bookDiningReservation.EventDiningReservation;
import com.disney.utils.dataFactory.staging.bookDiningReservation.ScheduledEventReservation;

public class TestSearchByAgency {
	// Defining global variables
	protected ThreadLocal<String> testName = new ThreadLocal<String>();
	protected ThreadLocal<String> environment = new ThreadLocal<String>();
	private ScheduledEventReservation book = null;
	
	@BeforeMethod(alwaysRun = true)
	@Parameters({ "environment" })
	public void preReq_BookReservation(String environment) {
		this.environment.set(environment);

		book = new EventDiningReservation(environment);
		book.setParty(new HouseHold(1));
		book.addTravelAgency();
		book.book("BookGuaranteedTS");
	}
	
	@AfterMethod(alwaysRun=true)
	public void cancelReservation(){
		book.cancel();
	}

	@Test(groups = {"api", "regression", "dining", "scheduledEventsServicePort"})
	public void testSearchByAgency(){
		TestReporter.logStep("Search By Agency");
		SearchByAgency searchByAgency = new SearchByAgency(environment.get(), "OnlyAgency");
		searchByAgency.sendRequest();
		TestReporter.logAPI(!searchByAgency.getResponseStatusCode().equals("200"), "An error occurred during retrieval.", searchByAgency);
		TestReporter.assertGreaterThanZero(searchByAgency.getNumberOfReservation());
	}
}