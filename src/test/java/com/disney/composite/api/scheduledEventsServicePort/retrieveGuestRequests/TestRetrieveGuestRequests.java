package com.disney.composite.api.scheduledEventsServicePort.retrieveGuestRequests;

import java.util.Map;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.scheduledEventsServicePort.operations.RetrieveGuestRequests;
import com.disney.composite.BaseTest;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.database.LogItems;

public class TestRetrieveGuestRequests extends BaseTest{
	// Defining global variables
	protected String testName = null;
	
	@BeforeMethod(alwaysRun = true)
	@Parameters({ "environment" })
	public void setup(String environment) {this.environment = environment;}

	@Test(groups = {"api", "regression", "dining", "scheduledEventsServicePort"})
	public void testRetrieveGuestRequests(){
		TestReporter.logStep("Retrieve Guest Request");
		RetrieveGuestRequests retrieveGuestRequests = new RetrieveGuestRequests(environment);
		retrieveGuestRequests.sendRequest();
		TestReporter.logAPI(!retrieveGuestRequests.getResponseStatusCode().equals("200"), "An error occurred during retrieval: " + retrieveGuestRequests.getFaultString(), retrieveGuestRequests);
		TestReporter.assertTrue(retrieveGuestRequests.getNumberOfGuestRequests() > 0, "Verify guest requests are returned.");
		TestReporter.assertTrue(retrieveGuestRequests.getGuestRequestCodes().size() == retrieveGuestRequests.getNumberOfGuestRequests(), "Verify the number of guest request codes is ["+retrieveGuestRequests.getNumberOfGuestRequests()+"].");
		reportValues("Guest Requests", retrieveGuestRequests.getNumberOfGuestRequests(), retrieveGuestRequests.getGuestRequestCodes());
		TestReporter.assertTrue(retrieveGuestRequests.getGuestRequestDescriptions().size() == retrieveGuestRequests.getNumberOfGuestRequests(), "Verify the number of guest request descriptions is ["+retrieveGuestRequests.getNumberOfGuestRequests()+"].");
		reportValues("Guest Requests", retrieveGuestRequests.getNumberOfGuestRequests(), retrieveGuestRequests.getGuestRequestDescriptions());
		
		LogItems logItems = new LogItems();
		logItems.addItem("ScheduledEventsServiceIF", "retrieveGuestRequests", false);
		logItems.addItem("ProfileComponentServiceIF", "retrieveProfilesByRoutingType", false);	
		validateLogs(retrieveGuestRequests, logItems);
	}
	
	private void reportValues(String valueName, int size, Map<String, String> values){
		for(int i = 0; i < size; i++){
			TestReporter.log(valueName + ": " + values.get(String.valueOf(i)));
		}
	}
}