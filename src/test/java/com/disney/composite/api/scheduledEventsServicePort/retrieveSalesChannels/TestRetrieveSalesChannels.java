package com.disney.composite.api.scheduledEventsServicePort.retrieveSalesChannels;

import java.util.Map;

import org.testng.annotations.Test;

import com.disney.api.soapServices.scheduledEventsServicePort.operations.RetrieveSalesChannels;
import com.disney.composite.BaseTest;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.database.LogItems;

public class TestRetrieveSalesChannels extends BaseTest{

	@Test(groups = {"api", "regression", "dining", "scheduledEventsServicePort"})
	public void testRetrieveSalesChannels(){
		TestReporter.logStep("Retrieve Sales Channels");
		RetrieveSalesChannels retrieveSalesChannels = new RetrieveSalesChannels(environment);
		retrieveSalesChannels.sendRequest();
		TestReporter.logAPI(!retrieveSalesChannels.getResponseStatusCode().equals("200"), "An error occurred during retrieval.", retrieveSalesChannels);
		TestReporter.assertTrue(retrieveSalesChannels.getNumberOfSalesChannels() > 0, "Verify sales channels are returned.");
		TestReporter.assertTrue(retrieveSalesChannels.getSalesChannels().size() == retrieveSalesChannels.getNumberOfSalesChannels(), "Verify the number of sales channels is ["+retrieveSalesChannels.getNumberOfSalesChannels()+"].");
		reportValues("Sales Channels", retrieveSalesChannels.getNumberOfSalesChannels(), retrieveSalesChannels.getSalesChannels());
		
		LogItems logItems = new LogItems();
		logItems.addItem("ScheduledEventsServiceIF", "retrieveSalesChannels", false);	
		validateLogs(retrieveSalesChannels, logItems);
	}
	
	private void reportValues(String valueName, int size, Map<String, String> values){
		for(int i = 0; i < size; i++){
			TestReporter.log(valueName + ": " + values.get(String.valueOf(i)));
		}
	}
}