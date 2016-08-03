package com.disney.composite.api.scheduledEventsServicePort.retrieveDiningReservations;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.disney.api.soapServices.scheduledEventsServicePort.operations.RetrieveDiningReservations;
import com.disney.composite.BaseTest;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.database.LogItems;

public class TestRetrieveDiningReservations extends BaseTest{	
	@DataProvider(name = "dataProvider", parallel=true)
	public Object[][] dataProvider(){
		return new Object[][] {{"354261"}, {"90001724"}, {"178451"}, {"90001744"}, {"354279"}, {"90002012"}, {"90001778"}, {"90001786"},
				{"349059"}, {"348023"}, {"15525573"}, {"90001798"}, {"90001819"}, {"90001833"}, {"90001865"}, {"17364609"}, {"90001879"},
				{"90001951"}, {"90001296"}, {"354372"}, {"354378"}, {"90001983"}, {"90001985"}, {"90002606"}, {"90002006"}, {"343233"}, {"90002032"},
				{"90002041"}, {"354414"}, {"90002053"}, {"90002044"}, {"354432"}, {"90001873"}, {"90002440"}, {"354435"}, {"17564465"}, {"90002068"},
				{"90002086"}, {"90002084"}, {"90002100"}, {"293704"}, {"90002114"}, {"90002122"}, {"90002126"}, {"90002124"}, {"90002156"},
				{"17720675"}, {"17134590"}, {"354468"}, {"354474"}, {"221625"}, {"90002660"}, {"247904"}, {"90002402"}, {"364242"}, {"90002237"},
				{"90002245"}, {"90002670"}, {"98575"}, {"266442"}, {"7813806"}, {"90002274"}, {"221626"}, {"90002668"}, {"90002678"}, {"354528"},
				{"17346592"}, {"90001473"}, {"90002287"}, {"293563"}, {"90002686"}, {"16526362"}, {"220739"}, {"15525574"}, {"90002336"}, {"90002376"},
				{"354555"}, {"23170"}, {"90002412"}, {"215686"}};
	}
	
	@Test(groups = {"api", "regression", "dining", "scheduledEventsServicePort"}, dataProvider = "dataProvider")
	public void testRetrieveDiningReservations(String facilityId){
		TestReporter.logScenario("Facility ID: " + facilityId);
		RetrieveDiningReservations retrieve = new RetrieveDiningReservations(environment, "Main");
		retrieve.setFacilityId(facilityId);
		retrieve.sendRequest();
		TestReporter.logAPI(!retrieve.getResponseStatusCode().equals("200"), "An error occurred retrieving dining reservations. " + retrieve.getFaultString(), retrieve);
		TestReporter.logStep("["+retrieve.getNumberOfReservations()+"] reservations were returned.");
		
		LogItems logItems = new LogItems();
		logItems.addItem("ScheduledEventsServiceIF", "retrieveDiningReservations", false);		
		validateLogs(retrieve, logItems, 10000);
	}
}