package com.disney.composite.api.diningModule.scheduledEventsServicePort.retrieveMassCancelReasons;

import java.util.Map;

import org.testng.annotations.Test;

import com.disney.api.soapServices.diningModule.scheduledEventsServicePort.operations.RetrieveMassCancelReasons;
import com.disney.composite.BaseTest;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.database.LogItems;

public class TestRetrieveMassCancelReasons extends BaseTest{

	@Test(groups = {"api", "regression", "dining", "scheduledEventsServicePort"})
	public void testRetrieveMassCancelReasons(){
		TestReporter.logStep("Retrieve Mass Cancel Reasons");
		RetrieveMassCancelReasons retrieveMassCancelReasons = new RetrieveMassCancelReasons(environment);
		retrieveMassCancelReasons.sendRequest();
		TestReporter.logAPI(!retrieveMassCancelReasons.getResponseStatusCode().equals("200"), "An error occurred during retrieval: " + retrieveMassCancelReasons.getFaultString(), retrieveMassCancelReasons);
		TestReporter.assertTrue(retrieveMassCancelReasons.getNumberOfMassCancelReasons() > 0, "Verify mass cancel reasons are returned.");
		TestReporter.assertTrue(retrieveMassCancelReasons.getMassCancelCodes().size() == retrieveMassCancelReasons.getNumberOfMassCancelReasons(), "Verify the number of mass cancel reason codes is ["+retrieveMassCancelReasons.getNumberOfMassCancelReasons()+"].");
		reportValues("Mass Cancel Reasons", retrieveMassCancelReasons.getNumberOfMassCancelReasons(), retrieveMassCancelReasons.getMassCancelCodes());
		TestReporter.assertTrue(retrieveMassCancelReasons.getMassCancelDescriptions().size() == retrieveMassCancelReasons.getNumberOfMassCancelReasons(), "Verify the number of mass cancel reason descriptions is ["+retrieveMassCancelReasons.getNumberOfMassCancelReasons()+"].");
		reportValues("Mass Cancel Reasons", retrieveMassCancelReasons.getNumberOfMassCancelReasons(), retrieveMassCancelReasons.getMassCancelDescriptions());
		TestReporter.assertTrue(retrieveMassCancelReasons.getMassCancelIds().size() == retrieveMassCancelReasons.getNumberOfMassCancelReasons(), "Verify the number of mass cancel reason ids is ["+retrieveMassCancelReasons.getNumberOfMassCancelReasons()+"].");
		reportValues("Mass Cancel Reasons", retrieveMassCancelReasons.getNumberOfMassCancelReasons(), retrieveMassCancelReasons.getMassCancelIds());
		
		LogItems logItems = new LogItems();
		logItems.addItem("ScheduledEventsServiceIF", "retrieveMassCancelReasons", false);	
		validateLogs(retrieveMassCancelReasons, logItems);
	}
	
	private void reportValues(String valueName, int size, Map<String, String> values){
		for(int i = 0; i < size; i++){
			TestReporter.log(valueName + ": " + values.get(String.valueOf(i)));
		}
	}
}