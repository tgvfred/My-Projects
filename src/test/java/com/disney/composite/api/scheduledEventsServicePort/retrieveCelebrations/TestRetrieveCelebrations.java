package com.disney.composite.api.scheduledEventsServicePort.retrieveCelebrations;

import java.util.Map;

import org.testng.annotations.Test;

import com.disney.api.soapServices.scheduledEventsServicePort.operations.RetrieveCelebrations;
import com.disney.composite.BaseTest;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.database.LogItems;

public class TestRetrieveCelebrations extends BaseTest{

	@Test(groups = {"api", "regression", "dining", "scheduledEventsServicePort"})
	public void testRetrieveCelebrations(){
		TestReporter.logStep("Retrieve Celebrations");
		RetrieveCelebrations retrieveCelebrations = new RetrieveCelebrations(environment);
		retrieveCelebrations.sendRequest();
		TestReporter.logAPI(!retrieveCelebrations.getResponseStatusCode().equals("200"), "An error occurred during retrieval: " + retrieveCelebrations.getFaultString(), retrieveCelebrations);
		
		TestReporter.assertTrue(retrieveCelebrations.getNumberOfCelebrations() > 0, "Verify Celebrations are returned.");
		TestReporter.assertTrue(retrieveCelebrations.getCelebrationsCodes().size() == retrieveCelebrations.getNumberOfCelebrations(), "Verify the number of Celebrations codes is ["+retrieveCelebrations.getNumberOfCelebrations()+"].");
		reportValues("Celebrations", retrieveCelebrations.getNumberOfCelebrations(), retrieveCelebrations.getCelebrationsCodes());
		TestReporter.assertTrue(retrieveCelebrations.getCelebrationsDescriptions().size() == retrieveCelebrations.getNumberOfCelebrations(), "Verify the number of Celebration descriptions is ["+retrieveCelebrations.getNumberOfCelebrations()+"].");
		reportValues("Celebrations", retrieveCelebrations.getNumberOfCelebrations(), retrieveCelebrations.getCelebrationsDescriptions());
		TestReporter.assertTrue(retrieveCelebrations.getCelebrationsIds().size() == retrieveCelebrations.getNumberOfCelebrations(), "Verify the number of Celebration ids is ["+retrieveCelebrations.getNumberOfCelebrations()+"].");
		reportValues("Celebrations", retrieveCelebrations.getNumberOfCelebrations(), retrieveCelebrations.getCelebrationsIds());
		
		LogItems logItems = new LogItems();
		logItems.addItem("ScheduledEventsServiceIF", "retrieveCelebrations", false);
		logItems.addItem("ProfileComponentServiceIF", "retrieveProfilesByRoutingType", false);		
		validateLogs(retrieveCelebrations, logItems);
	}
	
	private void reportValues(String valueName, int size, Map<String, String> values){
		for(int i = 0; i < size; i++){
			TestReporter.log(valueName + ": " + values.get(String.valueOf(i)));
		}
	}
}