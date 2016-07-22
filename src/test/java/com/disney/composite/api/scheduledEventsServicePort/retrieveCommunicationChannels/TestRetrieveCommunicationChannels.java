package com.disney.composite.api.scheduledEventsServicePort.retrieveCommunicationChannels;

import java.util.Map;

import org.testng.annotations.Test;

import com.disney.api.soapServices.scheduledEventsServicePort.operations.RetrieveCommunicationChannels;
import com.disney.composite.BaseTest;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.database.LogItems;

public class TestRetrieveCommunicationChannels extends BaseTest{

	@Test(groups = {"api", "regression", "dining", "scheduledEventsServicePort"})
	public void testRetrieveCommunicationChannels(){
		TestReporter.logStep("Retrieve Communication Channels");
		RetrieveCommunicationChannels retrieveCommunicationChannels = new RetrieveCommunicationChannels(environment);
		retrieveCommunicationChannels.sendRequest();
		TestReporter.logAPI(!retrieveCommunicationChannels.getResponseStatusCode().equals("200"), "An error occurred during retrieval.", retrieveCommunicationChannels);
		TestReporter.assertTrue(retrieveCommunicationChannels.getNumberOfCommunicationChannels() > 0, "Verify communication channels are returned.");
		TestReporter.assertTrue(retrieveCommunicationChannels.getCommunicationChannels().size() == retrieveCommunicationChannels.getNumberOfCommunicationChannels(), "Verify the number of change reason codes is ["+retrieveCommunicationChannels.getNumberOfCommunicationChannels()+"].");
		reportValues("Communication Channels", retrieveCommunicationChannels.getNumberOfCommunicationChannels(), retrieveCommunicationChannels.getCommunicationChannels());
		
		LogItems logItems = new LogItems();
		logItems.addItem("ScheduledEventsServiceIF", "retrieveCommunicationChannels", false);	
		validateLogs(retrieveCommunicationChannels, logItems);
	}
	
	private void reportValues(String valueName, int size, Map<String, String> values){
		for(int i = 0; i < size; i++){
			TestReporter.log(valueName + ": " + values.get(String.valueOf(i)));
		}
	}
}