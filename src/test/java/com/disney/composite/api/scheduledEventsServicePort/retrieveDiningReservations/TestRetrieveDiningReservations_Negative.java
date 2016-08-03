package com.disney.composite.api.scheduledEventsServicePort.retrieveDiningReservations;

import org.testng.annotations.Test;

import com.disney.api.soapServices.applicationError.LiloSystemErrorCode;
import com.disney.api.soapServices.core.BaseSoapCommands;
import com.disney.api.soapServices.scheduledEventsServicePort.operations.RetrieveDiningReservations;
import com.disney.composite.BaseTest;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.database.LogItems;

public class TestRetrieveDiningReservations_Negative extends BaseTest{		
	
	@Test(groups = {"api", "regression", "dining", "scheduledEventsServicePort", "negative"})
	public void missingRetrieveDiningReservationsRequest(){

		RetrieveDiningReservations retrieve = new RetrieveDiningReservations(environment, "Main");
		retrieve.setRequestNodeValueByXPath("/Envelope/Body/retrieveDiningReservations/retrieveDiningReservationsRequest", BaseSoapCommands.REMOVE_NODE.toString());
		retrieve.sendRequest();
		validateApplicationError(retrieve, LiloSystemErrorCode.UNEXPECTED_ERROR);
		TestReporter.logAPI(!retrieve.getFaultString().contains("Unexpected Error occurred : retrieveDiningReservations : java.lang.NullPointerException"), retrieve.getFaultString(), retrieve);
		
		LogItems logValidItems = new LogItems();
		logValidItems.addItem("ScheduledEventsServiceIF", "retrieveDiningReservations", true);
		validateLogs(retrieve, logValidItems);
	}

}