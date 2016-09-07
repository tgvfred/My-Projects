package com.disney.composite.api.diningModule.scheduledEventsServicePort.retrieveSpecialEvents;

import java.util.Map;

import org.testng.annotations.Test;

import com.disney.api.soapServices.diningModule.scheduledEventsServicePort.operations.RetrieveSpecialEvents;
import com.disney.composite.BaseTest;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.database.LogItems;

public class TestRetrieveSpecialEvents extends BaseTest{

	@Test(groups = {"api", "regression", "dining", "scheduledEventsServicePort"})
	public void testRetrieveSpecialEvents(){
		TestReporter.logStep("Retrieve Special Events");
		RetrieveSpecialEvents retrieveSpecialEvents = new RetrieveSpecialEvents(environment);
		retrieveSpecialEvents.sendRequest();
		TestReporter.logAPI(!retrieveSpecialEvents.getResponseStatusCode().equals("200"), "An error occurred during retrieval: " + retrieveSpecialEvents.getFaultString(), retrieveSpecialEvents);
		TestReporter.assertTrue(retrieveSpecialEvents.getNumberOfSpecialEvents() > 0, "Verify special events are returned.");
		TestReporter.assertTrue(retrieveSpecialEvents.getSpecialEventsCodes().size() == retrieveSpecialEvents.getNumberOfSpecialEvents(), "Verify the number of special event codes is ["+retrieveSpecialEvents.getNumberOfSpecialEvents()+"].");
		reportValues("Special Events", retrieveSpecialEvents.getNumberOfSpecialEvents(), retrieveSpecialEvents.getSpecialEventsCodes());
		TestReporter.assertTrue(retrieveSpecialEvents.getSpecialEventsDescriptions().size() == retrieveSpecialEvents.getNumberOfSpecialEvents(), "Verify the number of special event descriptions is ["+retrieveSpecialEvents.getNumberOfSpecialEvents()+"].");
		reportValues("Special Events", retrieveSpecialEvents.getNumberOfSpecialEvents(), retrieveSpecialEvents.getSpecialEventsDescriptions());
		TestReporter.assertTrue(retrieveSpecialEvents.getSpecialEventsIds().size() == retrieveSpecialEvents.getNumberOfSpecialEvents(), "Verify the number of special event ids is ["+retrieveSpecialEvents.getNumberOfSpecialEvents()+"].");
		reportValues("Special Events", retrieveSpecialEvents.getNumberOfSpecialEvents(), retrieveSpecialEvents.getSpecialEventsIds());
		
		LogItems logItems = new LogItems();
		logItems.addItem("ScheduledEventsServiceIF", "retrieveSpecialEvents", false);
		logItems.addItem("ProfileComponentServiceIF", "retrieveProfilesByRoutingType", false);	
		validateLogs(retrieveSpecialEvents, logItems);
	}
	
	private void reportValues(String valueName, int size, Map<String, String> values){
		for(int i = 0; i < size; i++){
			TestReporter.log(valueName + ": " + values.get(String.valueOf(i)));
		}
	}
}