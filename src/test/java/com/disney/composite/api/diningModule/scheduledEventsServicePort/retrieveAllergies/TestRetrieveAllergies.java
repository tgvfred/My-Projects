package com.disney.composite.api.diningModule.scheduledEventsServicePort.retrieveAllergies;

import java.util.Map;

import org.testng.annotations.Test;

import com.disney.api.soapServices.diningModule.scheduledEventsServicePort.operations.RetrieveAllergies;
import com.disney.composite.BaseTest;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.database.LogItems;

public class TestRetrieveAllergies extends BaseTest{

	@Test(groups = {"api", "regression", "dining", "scheduledEventsServicePort"})
	public void testRetrieveAllergies(){
		TestReporter.logStep("Retrieve Allergies");
		RetrieveAllergies retrieveAllergies = new RetrieveAllergies(environment);
		retrieveAllergies.sendRequest();
		TestReporter.logAPI(!retrieveAllergies.getResponseStatusCode().equals("200"), "An error occurred during retrieval: " + retrieveAllergies.getFaultString(), retrieveAllergies);
		TestReporter.assertTrue(retrieveAllergies.getNumberOfAllergies() > 0, "Verify allergies are returned.");
		TestReporter.assertTrue(retrieveAllergies.getAllergies().size() == retrieveAllergies.getNumberOfAllergies(), "Verify the number of allergies is ["+retrieveAllergies.getNumberOfAllergies()+"].");
		LogItems logItems = new LogItems();
		
		logItems.addItem("ScheduledEventsServiceIF", "retrieveAllergies", false);	
		validateLogs(retrieveAllergies, logItems);
		
		reportAllergies(retrieveAllergies);
	}
	
	private void reportAllergies(RetrieveAllergies retrieve){
		int numberOfAllergies = retrieve.getNumberOfAllergies();
		Map<String, String> allergies = retrieve.getAllergies();
		for(int i = 0; i < numberOfAllergies; i++){
			TestReporter.log("Allergies: " +allergies.get(String.valueOf(String.valueOf(i))));
		}
	}
}