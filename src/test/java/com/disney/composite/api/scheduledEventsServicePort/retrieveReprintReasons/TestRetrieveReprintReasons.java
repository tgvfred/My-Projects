package com.disney.composite.api.scheduledEventsServicePort.retrieveReprintReasons;

import java.util.Map;

import org.testng.annotations.Test;

import com.disney.api.soapServices.scheduledEventsServicePort.operations.RetrieveReprintReasons;
import com.disney.composite.BaseTest;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.database.LogItems;

public class TestRetrieveReprintReasons extends BaseTest{

	@Test(groups = {"api", "regression", "dining", "scheduledEventsServicePort"})
	public void testRetrieveReprintReasons(){
		TestReporter.logStep("Retrieve Reprint Reasons");
		RetrieveReprintReasons retrieveReprintReasons = new RetrieveReprintReasons(environment);
		retrieveReprintReasons.sendRequest();
		TestReporter.logAPI(!retrieveReprintReasons.getResponseStatusCode().equals("200"), "An error occurred during retrieval.", retrieveReprintReasons);
		TestReporter.assertTrue(retrieveReprintReasons.getNumberOfReprintReasons() > 0, "Verify reprint reasons are returned.");
		TestReporter.assertTrue(retrieveReprintReasons.getReprintReasonCodes().size() == retrieveReprintReasons.getNumberOfReprintReasons(), "Verify the number of reprint reason codes is ["+retrieveReprintReasons.getNumberOfReprintReasons()+"].");
		reportValues("Reprint Reasons", retrieveReprintReasons.getNumberOfReprintReasons(), retrieveReprintReasons.getReprintReasonCodes());
		TestReporter.assertTrue(retrieveReprintReasons.getReprintReasonDescriptions().size() == retrieveReprintReasons.getNumberOfReprintReasons(), "Verify the number of reprint reason descriptions is ["+retrieveReprintReasons.getNumberOfReprintReasons()+"].");
		reportValues("Reprint Reasons", retrieveReprintReasons.getNumberOfReprintReasons(), retrieveReprintReasons.getReprintReasonDescriptions());
		TestReporter.assertTrue(retrieveReprintReasons.getReprintReasonIds().size() == retrieveReprintReasons.getNumberOfReprintReasons(), "Verify the number of reprint reason ids is ["+retrieveReprintReasons.getNumberOfReprintReasons()+"].");
		reportValues("Reprint Reasons", retrieveReprintReasons.getNumberOfReprintReasons(), retrieveReprintReasons.getReprintReasonIds());
		
		LogItems logItems = new LogItems();
		logItems.addItem("ScheduledEventsServiceIF", "retrieveReprintReasons", false);	
		validateLogs(retrieveReprintReasons, logItems);
	}
	
	private void reportValues(String valueName, int size, Map<String, String> values){
		for(int i = 0; i < size; i++){
			TestReporter.log(valueName + ": " + values.get(String.valueOf(i)));
		}
	}
}