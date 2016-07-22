package com.disney.composite.api.scheduledEventsServicePort.retrieveCommentTypes;

import java.util.Map;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.scheduledEventsServicePort.operations.RetrieveCommentTypes;
import com.disney.composite.BaseTest;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.database.LogItems;

public class TestRetrieveCommentTypes extends BaseTest{
	// Defining global variables
	protected String testName = null;
	protected String environment = null;
	
	@BeforeMethod(alwaysRun = true)
	@Parameters({ "environment" })
	public void setup(String environment) {this.environment = environment;}

	@Test(groups = {"api", "regression", "dining", "scheduledEventsServicePort"})
	public void testRetrieveCommentTypes(){
		TestReporter.logStep("Retrieve Comment Types");
		RetrieveCommentTypes retrieveCommentTypes = new RetrieveCommentTypes(environment);
		retrieveCommentTypes.sendRequest();
		TestReporter.logAPI(!retrieveCommentTypes.getResponseStatusCode().equals("200"), "An error occurred during retrieval.", retrieveCommentTypes);
		TestReporter.assertTrue(retrieveCommentTypes.getNumberOfCommentTypes() > 0, "Verify comment types are returned.");
		TestReporter.assertTrue(retrieveCommentTypes.getCommentTypes().size() == retrieveCommentTypes.getNumberOfCommentTypes(), "Verify the number of comment types is ["+retrieveCommentTypes.getNumberOfCommentTypes()+"].");
		reportValues("Comment Types", retrieveCommentTypes.getNumberOfCommentTypes(), retrieveCommentTypes.getCommentTypes());
		
		LogItems logItems = new LogItems();
		logItems.addItem("ScheduledEventsServiceIF", "retrieveCommentTypes", false);	
		validateLogs(retrieveCommentTypes, logItems);
	}
	
	private void reportValues(String valueName, int size, Map<String, String> values){
		for(int i = 0; i < size; i++){
			TestReporter.log(valueName + ": " + values.get(String.valueOf(i)));
		}
	}
}