package com.disney.composite.api.diningModule.scheduledEventsServicePort.retrieveSpecialNeeds;

import java.util.Map;

import org.testng.annotations.Test;

import com.disney.api.BaseTest;
import com.disney.api.soapServices.diningModule.scheduledEventsServicePort.operations.RetrieveSpecialNeeds;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.database.LogItems;

public class TestRetrieveSpecialNeeds extends BaseTest{

	@Test(groups = {"api", "regression", "dining", "scheduledEventsServicePort"})
	public void testRetrieveSpecialNeeds(){
		TestReporter.logStep("Retrieve Special Needs");
		RetrieveSpecialNeeds retrieveSpecialNeeds = new RetrieveSpecialNeeds(environment);
		retrieveSpecialNeeds.sendRequest();
		TestReporter.logAPI(!retrieveSpecialNeeds.getResponseStatusCode().equals("200"), "An error occurred during retrieval: " + retrieveSpecialNeeds.getFaultString(), retrieveSpecialNeeds);
		TestReporter.assertTrue(retrieveSpecialNeeds.getNumberOfSpecialNeeds() > 0, "Verify special events are returned.");
		TestReporter.assertTrue(retrieveSpecialNeeds.getSpecialNeedsCodes().size() == retrieveSpecialNeeds.getNumberOfSpecialNeeds(), "Verify the number of special needs codes is ["+retrieveSpecialNeeds.getNumberOfSpecialNeeds()+"].");
		reportValues("Special Needs", retrieveSpecialNeeds.getNumberOfSpecialNeeds(), retrieveSpecialNeeds.getSpecialNeedsCodes());
		TestReporter.assertTrue(retrieveSpecialNeeds.getSpecialNeedsDescriptions().size() == retrieveSpecialNeeds.getNumberOfSpecialNeeds(), "Verify the number of special needs descriptions is ["+retrieveSpecialNeeds.getNumberOfSpecialNeeds()+"].");
		reportValues("Special Needs", retrieveSpecialNeeds.getNumberOfSpecialNeeds(), retrieveSpecialNeeds.getSpecialNeedsDescriptions());
		TestReporter.assertTrue(retrieveSpecialNeeds.getSpecialNeedsIds().size() == retrieveSpecialNeeds.getNumberOfSpecialNeeds(), "Verify the number of special needs ids is ["+retrieveSpecialNeeds.getNumberOfSpecialNeeds()+"].");
		reportValues("Special Needs", retrieveSpecialNeeds.getNumberOfSpecialNeeds(), retrieveSpecialNeeds.getSpecialNeedsIds());
		
		LogItems logItems = new LogItems();
		logItems.addItem("ScheduledEventsServiceIF", "retrieveSpecialNeeds", false);
		logItems.addItem("ProfileComponentServiceIF", "retrieveProfilesByRoutingType", false);	
		validateLogs(retrieveSpecialNeeds, logItems);
	}
	
	private void reportValues(String valueName, int size, Map<String, String> values){
		for(int i = 0; i < size; i++){
			TestReporter.log(valueName + ": " + values.get(String.valueOf(i)));
		}
	}
}