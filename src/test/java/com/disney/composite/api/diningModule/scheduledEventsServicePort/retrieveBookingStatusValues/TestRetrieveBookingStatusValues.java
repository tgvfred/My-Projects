package com.disney.composite.api.diningModule.scheduledEventsServicePort.retrieveBookingStatusValues;

import java.util.Map;

import org.testng.annotations.Test;

import com.disney.api.soapServices.diningModule.scheduledEventsServicePort.operations.RetrieveBookingStatusValues;
import com.disney.composite.BaseTest;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.database.LogItems;

public class TestRetrieveBookingStatusValues extends BaseTest{

	@Test(groups = {"api", "regression", "dining", "scheduledEventsServicePort"})
	public void testRetrieveBookingStatusValues(){
		TestReporter.logStep("Retrieve Booking Status Values");
		RetrieveBookingStatusValues retrieveBookingStatusValues = new RetrieveBookingStatusValues(environment);
		retrieveBookingStatusValues.sendRequest();
		TestReporter.logAPI(!retrieveBookingStatusValues.getResponseStatusCode().equals("200"), "An error occurred during retrieval.", retrieveBookingStatusValues);
		TestReporter.assertTrue(retrieveBookingStatusValues.getNumberOfBookingStatusValues() > 0, "Verify booking status values are returned.");
		TestReporter.assertTrue(retrieveBookingStatusValues.getBookingStatusValues().size() == retrieveBookingStatusValues.getNumberOfBookingStatusValues(), "Verify the number of booking status values is ["+retrieveBookingStatusValues.getNumberOfBookingStatusValues()+"].");
		
		LogItems logItems = new LogItems();
		logItems.addItem("ScheduledEventsServiceIF", "retrieveBookingStatusValues", false);	
		validateLogs(retrieveBookingStatusValues, logItems);
		
		reportBookingStatusValues(retrieveBookingStatusValues);
	}
	
	private void reportBookingStatusValues(RetrieveBookingStatusValues retrieve){
		int numberOfBookingStatusValues = retrieve.getNumberOfBookingStatusValues();
		Map<String, String> bookingStatusValues = retrieve.getBookingStatusValues();
		for(int i = 0; i < numberOfBookingStatusValues; i++){
			TestReporter.log("Booking Status Values: " +bookingStatusValues.get(String.valueOf(String.valueOf(i))));
		}
	}
}