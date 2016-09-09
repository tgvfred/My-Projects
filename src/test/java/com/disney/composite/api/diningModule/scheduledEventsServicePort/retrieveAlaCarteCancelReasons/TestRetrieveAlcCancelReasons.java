package com.disney.composite.api.diningModule.scheduledEventsServicePort.retrieveAlaCarteCancelReasons;

import java.util.Map;

import org.testng.annotations.Test;

import com.disney.api.BaseTest;
import com.disney.api.soapServices.diningModule.scheduledEventsServicePort.operations.RetrieveAlaCarteCancelReasons;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.database.LogItems;

public class TestRetrieveAlcCancelReasons extends BaseTest{
	@Test(groups = {"api", "regression", "dining", "scheduledEventsServicePort"})
	public void testRetrieveAlaCarteCancelReasons(){
		TestReporter.logStep("Retrieve Ala Carte Cancel Reasons");
		RetrieveAlaCarteCancelReasons retrieveAlcCancelReasons = new RetrieveAlaCarteCancelReasons(environment);
		retrieveAlcCancelReasons.sendRequest();
		TestReporter.logAPI(!retrieveAlcCancelReasons.getResponseStatusCode().equals("200"), "An error occurred during retrieval: " + retrieveAlcCancelReasons.getFaultString(), retrieveAlcCancelReasons);
		TestReporter.assertGreaterThanZero(retrieveAlcCancelReasons.getNumberOfAlcCancelReasons());
		
		LogItems logItems = new LogItems();
		logItems.addItem("ScheduledEventsServiceIF", "retrieveAlaCarteCancelReasons", false);	
		validateLogs(retrieveAlcCancelReasons, logItems);
		
		reportAlcCAncelReasons(retrieveAlcCancelReasons);
	}
	
	private void reportAlcCAncelReasons(RetrieveAlaCarteCancelReasons retrieve){
		int numberOfReasons = retrieve.getNumberOfAlcCancelReasons();
		Map<String, String> codes = retrieve.getAllOptionCodes();
		Map<String, String> descriptions = retrieve.getAllOptionDescriptions();
		Map<String, String> ids = retrieve.getAllOptionIds();
		for(int i = 1; i <= numberOfReasons; i++){
			TestReporter.log("Code: " +codes.get(String.valueOf(String.valueOf(i)))
					+" Description: " +descriptions.get(String.valueOf(String.valueOf(i)))
					+" ID: " + ids.get(String.valueOf(String.valueOf(i))));
		}
	}
}