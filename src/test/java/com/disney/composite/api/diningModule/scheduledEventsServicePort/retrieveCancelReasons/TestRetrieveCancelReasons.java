package com.disney.composite.api.diningModule.scheduledEventsServicePort.retrieveCancelReasons;

import java.util.Map;

import org.testng.annotations.Test;

import com.disney.api.soapServices.diningModule.scheduledEventsServicePort.operations.RetrieveCancelReasons;
import com.disney.composite.BaseTest;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.database.LogItems;

public class TestRetrieveCancelReasons extends BaseTest{

	@Test(groups = {"api", "regression", "dining", "scheduledEventsServicePort"})
	public void testRetrieveCancelReasons(){
		TestReporter.logStep("Retrieve Cancel Reasons");
		RetrieveCancelReasons retrieveCancelReasons = new RetrieveCancelReasons(environment);
		retrieveCancelReasons.sendRequest();
		TestReporter.logAPI(!retrieveCancelReasons.getResponseStatusCode().equals("200"), "An error occurred during retrieval.", retrieveCancelReasons);
		
		TestReporter.assertTrue(retrieveCancelReasons.getNumberOfCancelReasons() > 0, "Verify cancel reasons are returned.");
		TestReporter.assertTrue(retrieveCancelReasons.getCancelReasonCodes().size() == retrieveCancelReasons.getNumberOfCancelReasons(), "Verify the number of cancel reason codes is ["+retrieveCancelReasons.getNumberOfCancelReasons()+"].");
		reportValues("Cancel Reason Codes", retrieveCancelReasons.getNumberOfCancelReasons(), retrieveCancelReasons.getCancelReasonCodes());
		TestReporter.assertTrue(retrieveCancelReasons.getCancelReasonDescriptions().size() == retrieveCancelReasons.getNumberOfCancelReasons(), "Verify the number of cancel reason descriptions is ["+retrieveCancelReasons.getNumberOfCancelReasons()+"].");
		reportValues("Cancel Reason Descriptions", retrieveCancelReasons.getNumberOfCancelReasons(), retrieveCancelReasons.getCancelReasonDescriptions());
		TestReporter.assertTrue(retrieveCancelReasons.getCancelReasonIds().size() == retrieveCancelReasons.getNumberOfCancelReasons(), "Verify the number of cancel reason ids is ["+retrieveCancelReasons.getNumberOfCancelReasons()+"].");
		reportValues("Cancel Reason IDs", retrieveCancelReasons.getNumberOfCancelReasons(), retrieveCancelReasons.getCancelReasonIds());
		
		LogItems logItems = new LogItems();
		logItems.addItem("ScheduledEventsServiceIF", "retrieveCancelReasons", false);	
		validateLogs(retrieveCancelReasons, logItems);
	}
	
	private void reportValues(String valueName, int size, Map<String, String> values){
		for(int i = 0; i < size; i++){
			TestReporter.log(valueName + ": " + values.get(String.valueOf(i)));
		}
	}
}