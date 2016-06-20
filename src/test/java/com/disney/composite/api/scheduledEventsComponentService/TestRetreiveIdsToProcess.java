package com.disney.composite.api.scheduledEventsComponentService;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationBatchServicePort.operation.RetrieveProcessSummary;
import com.disney.api.soapServices.core.exceptions.XPathNotFoundException;
import com.disney.api.soapServices.scheduledEventsComponentService.operations.RetreiveIdsToProcess;
import com.disney.utils.Randomness;
import com.disney.utils.TestReporter;

public class TestRetreiveIdsToProcess {
	private String environment;
	private RetrieveProcessSummary retrieve;
	private RetreiveIdsToProcess ids;
	private String toDate = Randomness.generateCurrentXMLDate(5);
	private String fromDate = Randomness.generateCurrentXMLDate(-15);
	private String firstProcessId;
	private String firstProcessType;
	private String[][] idsAndTypes; 
	
	@BeforeMethod(alwaysRun = true)
	@Parameters("environment")
	public void before(String environment){
		this.environment = environment;
	}
	
	@Test(groups = {"api", "regression", "dining", "ScheduledEventsComponentService"})
	public void testRetreiveIdsToProcess(){
		TestReporter.logStep("Retrieve Process Summary");
		retrieve = new RetrieveProcessSummary(environment, "Main");
		retrieve.setFromDate(fromDate);
		retrieve.setToDate(toDate);
		retrieve.sendRequest();
		retrieve.getProcessSummaryDetails();
		retrieve.getProcessIds();
		retrieve.getProcessTypes();
		TestReporter.logAPI(!retrieve.getResponseStatusCode().equals("200"), "An error occurred retrieve the process summary from ["+fromDate+"] to ["+toDate+"]", retrieve);
//		TestReporter.assertEquals(retrieve.getResponseStatusCode(), "200", "An error occurred retrieve the process summary from ["+fromDate+"] to ["+toDate+"].\nRequest:\n"+retrieve.getRequest()+"\nResponse:\n"+retrieve.getResponse());

		TestReporter.logStep("Retrieve IDs To Process");
		ids = new RetreiveIdsToProcess(environment, "Main");
		idsAndTypes = retrieve.getProcessIdsAndTypes();
		for(int i = 0; i < idsAndTypes.length; i++){
			firstProcessId = idsAndTypes[i][0];
			firstProcessType = idsAndTypes[i][1];
			ids.setProcessId(firstProcessId);
			ids.sendRequest();
			try{
				ids.getProcessType();
				break;
			}catch(XPathNotFoundException e){}
		}
		TestReporter.logAPI(!ids.getResponseStatusCode().equals("200"), "An error occurred retrieve ids to process with process id ["+retrieve.getFirstProcessId(), ids);
//		TestReporter.assertEquals(ids.getResponseStatusCode(), "200", "An error occurred retrieve ids to process with process id ["+retrieve.getFirstProcessId()+".\nRequest:\n"+ids.getRequest()+"\nResponse:\n"+ids.getResponse());
		if(retrieve.getProcessIds().getLength() > 0)TestReporter.assertEquals(firstProcessType, ids.getProcessType().trim(), "The expected type ["+firstProcessType+"] did not match the actual type ["+ids.getProcessType().trim()+"].");
	}
}
