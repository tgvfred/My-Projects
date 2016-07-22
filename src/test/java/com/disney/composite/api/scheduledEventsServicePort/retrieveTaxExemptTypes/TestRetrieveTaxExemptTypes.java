package com.disney.composite.api.scheduledEventsServicePort.retrieveTaxExemptTypes;

import java.util.Map;

import org.testng.annotations.Test;

import com.disney.api.soapServices.scheduledEventsServicePort.operations.RetrieveTaxExemptTypes;
import com.disney.composite.BaseTest;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.database.LogItems;

public class TestRetrieveTaxExemptTypes extends BaseTest{

	@Test(groups = {"api", "regression", "dining", "scheduledEventsServicePort"})
	public void testRetrieveTaxExemptTypes(){
		TestReporter.logStep("Retrieve Tax Exempt Types");
		RetrieveTaxExemptTypes retrieveTaxExemptTypes = new RetrieveTaxExemptTypes(environment);
		retrieveTaxExemptTypes.sendRequest();
		TestReporter.logAPI(!retrieveTaxExemptTypes.getResponseStatusCode().equals("200"), "An error occurred during retrieval.", retrieveTaxExemptTypes);
		TestReporter.assertTrue(retrieveTaxExemptTypes.getNumberOfTaxExemptTypes() > 0, "Verify tax exempt types are returned.");
		TestReporter.assertTrue(retrieveTaxExemptTypes.getTaxExemptTypes().size() == retrieveTaxExemptTypes.getNumberOfTaxExemptTypes(), "Verify the number of tax exempt types is ["+retrieveTaxExemptTypes.getNumberOfTaxExemptTypes()+"].");
		reportValues("Tax Exempt Types", retrieveTaxExemptTypes.getNumberOfTaxExemptTypes(), retrieveTaxExemptTypes.getTaxExemptTypes());
		
		LogItems logItems = new LogItems();
		logItems.addItem("ScheduledEventsServiceIF", "retrieveTaxExemptTypes", false);
		logItems.addItem("FolioConfigurationServiceIF", "getOptions", false);	
		validateLogs(retrieveTaxExemptTypes, logItems);
	}
	
	private void reportValues(String valueName, int size, Map<String, String> values){
		for(int i = 0; i < size; i++){
			TestReporter.log(valueName + ": " + values.get(String.valueOf(i)));
		}
	}
}