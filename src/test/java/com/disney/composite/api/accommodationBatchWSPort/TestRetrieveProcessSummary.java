package com.disney.composite.api.accommodationBatchWSPort;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationBatchServicePort.operation.RetrieveProcessSummary;
import com.disney.utils.Randomness;
import com.disney.utils.Regex;
import com.disney.utils.TestReporter;

public class TestRetrieveProcessSummary {
	private String environment;
	private RetrieveProcessSummary retrieve;
	private String toDate = Randomness.generateCurrentXMLDate(5);
	private String fromDate = Randomness.generateCurrentXMLDate(-15);
	
	@BeforeMethod(alwaysRun = true)
	@Parameters("environment")
	public void before(String environment){
		this.environment = environment;
	}
	
	@Test(groups = {"api", "regression", "Accommodation", "AccommodationBatchService"})
	public void testRetrieveProcessSummary(){
		retrieve = new RetrieveProcessSummary(environment, "Main");
		retrieve.setFromDate(fromDate);
		retrieve.setToDate(toDate);
		retrieve.sendRequest();
		TestReporter.assertEquals(retrieve.getResponseStatusCode(), "200", "An error occurred retrieve the process summary from ["+fromDate+"] to ["+toDate+"].\nRequest:\n"+retrieve.getRequest()+"\nResponse:\n"+retrieve.getResponse());
		TestReporter.assertTrue(retrieve.getProcessSummaryDetails().getLength() > 0, "No process summary details were returned");
		TestReporter.assertTrue(retrieve.getProcessIds().getLength() > 0, "No process ids were returned");
		TestReporter.assertTrue(retrieve.getProcessTypes().getLength() > 0, "No process types were returned");
		TestReporter.assertTrue(new Regex().match("[0-9]+", retrieve.getFirstProcessId()), "The first process ID ["+retrieve.getFirstProcessId()+"] was not numeric as expected.");
		TestReporter.assertTrue(new Regex().match("[A-Za-z]+", retrieve.getFirstProcessType().replace("_", "")), "The first process type ["+retrieve.getFirstProcessType()+"] was not alphabetic as expected.");
	}
}
