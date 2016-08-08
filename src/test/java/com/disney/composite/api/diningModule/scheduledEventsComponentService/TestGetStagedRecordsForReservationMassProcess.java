package com.disney.composite.api.diningModule.scheduledEventsComponentService;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.w3c.dom.NodeList;

import com.disney.AutomationException;
import com.disney.api.soapServices.accommodationModule.accommodationBatchServicePort.operation.RetrieveProcessSummary;
import com.disney.api.soapServices.diningModule.scheduledEventsComponentService.operations.GetStagedRecordsForReservationMassProcess;
import com.disney.api.soapServices.diningModule.scheduledEventsComponentService.operations.RetreiveIdsToProcess;
import com.disney.utils.Randomness;
import com.disney.utils.Regex;
import com.disney.utils.TestReporter;

public class TestGetStagedRecordsForReservationMassProcess {
	private String environment;
	private RetrieveProcessSummary retrieve;
	private RetreiveIdsToProcess ids;
	private GetStagedRecordsForReservationMassProcess records;
	private String toDate = Randomness.generateCurrentXMLDate(5);
	private String fromDate = Randomness.generateCurrentXMLDate(-90);
	private NodeList processIds;
	private String processDataId;
	private int intIds;
	
	@BeforeMethod(alwaysRun = true)
	@Parameters("environment")
	public void before(String environment){
		this.environment = environment;
	}
	
	@Test(groups = {"api", "regression", "dining", "ScheduledEventsComponentService"})
	public void testGetStagedRecordsForReservationMassProcess(){
		retrieve = new RetrieveProcessSummary(environment, "Main");
		retrieve.setFromDate(fromDate);
		retrieve.setToDate(toDate);
		retrieve.sendRequest();
		retrieve.getProcessSummaryDetails();
		retrieve.getProcessIds();
		retrieve.getProcessTypes();
		TestReporter.logAPI(!retrieve.getResponseStatusCode().equals("200"), "An error occurred retrieve the process summary from ["+fromDate+"] to ["+toDate+"]", retrieve);
		processIds = retrieve.getProcessIds();
		intIds = processIds.getLength();
		TestReporter.log("IDs Returned: " + intIds);
		for(int i = 0; i < processIds.getLength(); i++ ){
			processDataId = null;
			ids = new RetreiveIdsToProcess(environment, "Main");
			ids.setProcessId(processIds.item(i).getTextContent());
			ids.sendRequest();
			TestReporter.logAPI(!ids.getResponseStatusCode().equals("200"), "An error occurred retrieve ids to process with process id ["+retrieve.getFirstProcessId(), ids);
			try{	
				processDataId = ids.getProcessDataIdList();
				if(processDataId != null){
					records = new GetStagedRecordsForReservationMassProcess(environment, "Main");
					records.setProcessDataId(processDataId);
					records.sendRequest();
					TestReporter.logAPI(!records.getResponseStatusCode().equals("200"), "An error occurred retrieving records for process data list id ["+processDataId+"].", records);
					break;
				}
			}
			catch(AssertionError | AutomationException e){}
		}
		TestReporter.assertTrue(Regex.match("[0-9]+", records.getTravelPlanSegmentId()), "The TPS ID ["+records.getTravelPlanSegmentId()+"] was not numeric as expected.");			
	}
}
