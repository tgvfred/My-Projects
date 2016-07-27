package com.disney.composite.api.scheduledEventsServicePort.retrieveInventoryOverideReasons;

import java.util.Map;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.scheduledEventsServicePort.operations.RetrieveInventoryOverideReasons;
import com.disney.composite.BaseTest;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.database.LogItems;

public class TestRetrieveInventoryOverideReasons extends BaseTest{
	// Defining global variables
	protected String testName = null;
	
	@BeforeMethod(alwaysRun = true)
	@Parameters({ "environment" })
	public void setup(String environment) {this.environment = environment;}

	@Test(groups = {"api", "regression", "dining", "scheduledEventsServicePort"})
	public void testRetrieveInventoryOverideReasons(){
		TestReporter.logStep("Retrieve Inventory Overide Reasons");
		RetrieveInventoryOverideReasons retrieveInventoryOverideReasons = new RetrieveInventoryOverideReasons(environment);
		retrieveInventoryOverideReasons.sendRequest();
		TestReporter.logAPI(!retrieveInventoryOverideReasons.getResponseStatusCode().equals("200"), "An error occurred during retrieval.", retrieveInventoryOverideReasons);
		TestReporter.assertTrue(retrieveInventoryOverideReasons.getNumberOfInventoryOverideReasons() > 0, "Verify inventory overide reason codes are returned.");
		TestReporter.assertTrue(retrieveInventoryOverideReasons.getInventoryOverideReasonsCodes().size() == retrieveInventoryOverideReasons.getNumberOfInventoryOverideReasons(), "Verify the number of overide inventory reason codes is ["+retrieveInventoryOverideReasons.getNumberOfInventoryOverideReasons()+"].");
		reportValues("Inventory Overide Reasons", retrieveInventoryOverideReasons.getNumberOfInventoryOverideReasons(), retrieveInventoryOverideReasons.getInventoryOverideReasonsCodes());
		TestReporter.assertTrue(retrieveInventoryOverideReasons.getInventoryOverideReasonsDescriptions().size() == retrieveInventoryOverideReasons.getNumberOfInventoryOverideReasons(), "Verify the number of overide inventory reason descriptions is ["+retrieveInventoryOverideReasons.getNumberOfInventoryOverideReasons()+"].");
		reportValues("Inventory Overide Reasons", retrieveInventoryOverideReasons.getNumberOfInventoryOverideReasons(), retrieveInventoryOverideReasons.getInventoryOverideReasonsDescriptions());
		TestReporter.assertTrue(retrieveInventoryOverideReasons.getInventoryOverideReasonsIds().size() == retrieveInventoryOverideReasons.getNumberOfInventoryOverideReasons(), "Verify the number of overide inventory reason ids is ["+retrieveInventoryOverideReasons.getNumberOfInventoryOverideReasons()+"].");
		reportValues("Inventory Overide Reasons", retrieveInventoryOverideReasons.getNumberOfInventoryOverideReasons(), retrieveInventoryOverideReasons.getInventoryOverideReasonsIds());
		
		LogItems logItems = new LogItems();
		logItems.addItem("ScheduledEventsServiceIF", "retrieveInventoryOverideReasons", false);	
		validateLogs(retrieveInventoryOverideReasons, logItems);
	}
	
	private void reportValues(String valueName, int size, Map<String, String> values){
		for(int i = 0; i < size; i++){
			TestReporter.log(valueName + ": " + values.get(String.valueOf(i)));
		}
	}
}