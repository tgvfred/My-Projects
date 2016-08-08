package com.disney.composite.api.diningModule.scheduledEventsServicePort.retrieveChangeReasons;

import java.util.Map;

import org.testng.annotations.Test;

import com.disney.api.soapServices.diningModule.scheduledEventsServicePort.operations.RetrieveChangeReasons;
import com.disney.composite.BaseTest;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.database.LogItems;

public class TestRetrieveChangeReasons extends BaseTest{

	@Test(groups = {"api", "regression", "dining", "scheduledEventsServicePort"})
	public void testRetrieveChangeReasons(){
		TestReporter.logStep("Retrieve Change Reasons");
		RetrieveChangeReasons retrieveChangeReasons = new RetrieveChangeReasons(environment);
		retrieveChangeReasons.sendRequest();
		TestReporter.logAPI(!retrieveChangeReasons.getResponseStatusCode().equals("200"), "An error occurred during retrieval.", retrieveChangeReasons);
		TestReporter.assertTrue(retrieveChangeReasons.getNumberOfChangeReasons() > 0, "Verify change reasons are returned.");
		TestReporter.assertTrue(retrieveChangeReasons.getChangeReasonCodes().size() == retrieveChangeReasons.getNumberOfChangeReasons(), "Verify the number of change reason codes is ["+retrieveChangeReasons.getNumberOfChangeReasons()+"].");
		reportValues("Change Reason Codes", retrieveChangeReasons.getNumberOfChangeReasons(), retrieveChangeReasons.getChangeReasonCodes());
		TestReporter.assertTrue(retrieveChangeReasons.getChangeReasonDescriptions().size() == retrieveChangeReasons.getNumberOfChangeReasons(), "Verify the number of change reason descriptions is ["+retrieveChangeReasons.getNumberOfChangeReasons()+"].");
		reportValues("Change Reason Description", retrieveChangeReasons.getNumberOfChangeReasons(), retrieveChangeReasons.getChangeReasonDescriptions());
		
		LogItems logItems = new LogItems();
		logItems.addItem("ScheduledEventsServiceIF", "retrieveChangeReasons", false);	
		validateLogs(retrieveChangeReasons, logItems);
	}
	
	private void reportValues(String valueName, int size, Map<String, String> values){
		for(int i = 0; i < size; i++){
			TestReporter.log(valueName + ": " + values.get(String.valueOf(i)));
		}
	}
}