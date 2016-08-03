package com.disney.composite.api.scheduledEventsServicePort.massCancelSE;

import org.testng.annotations.Test;

import com.disney.api.soapServices.core.BaseSoapCommands;
import com.disney.api.soapServices.scheduledEventsServicePort.operations.MassCancelSE;
import com.disney.composite.BaseTest;
import com.disney.test.utils.TestReporter;
import com.disney.utils.dataFactory.database.LogItems;

public class TestMassCancelSE extends BaseTest{	
	@Test(groups = {"api", "regression", "dining", "scheduledEventsServicePort"})
	public void missingTravelPlanSegment(){
		TestReporter.logScenario("Missing Travel Plan Segment");
		MassCancelSE massCancelSE = new MassCancelSE(environment);
		massCancelSE.setTravelPlanSegmentId(BaseSoapCommands.REMOVE_NODE.toString());
		massCancelSE.sendRequest();
		
		LogItems logValidItems = new LogItems();
		logValidItems.addItem("ScheduledEventsServiceIF", "massCancelSE", false);
		validateLogs(massCancelSE, logValidItems);
	}
	@Test(groups = {"api", "regression", "dining", "scheduledEventsServicePort"})
	public void noData(){
		TestReporter.logScenario("No Data");
		MassCancelSE massCancelSE = new MassCancelSE(environment);
		massCancelSE.setTravelPlanSegmentId(BaseSoapCommands.REMOVE_NODE.toString());
		massCancelSE.setReasonText(BaseSoapCommands.REMOVE_NODE.toString());
		massCancelSE.sendRequest();
		
		LogItems logValidItems = new LogItems();
		logValidItems.addItem("ScheduledEventsServiceIF", "massCancelSE", false);
		validateLogs(massCancelSE, logValidItems);
	}
}
