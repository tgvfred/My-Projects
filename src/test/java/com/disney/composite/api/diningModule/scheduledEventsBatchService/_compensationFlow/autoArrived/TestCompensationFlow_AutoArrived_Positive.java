package com.disney.composite.api.diningModule.scheduledEventsBatchService._compensationFlow.autoArrived;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.w3c.dom.NodeList;

import com.disney.api.soapServices.diningModule.scheduledEventsBatchService.operations.AutoArrived;
import com.disney.api.soapServices.diningModule.scheduledEventsBatchService.operations.RetrieveTravelPlanSegmentsForAutoArrival;
import com.disney.api.soapServices.diningModule.showDiningService.operations.Retrieve;
import com.disney.composite.BaseTest;
import com.disney.utils.Randomness;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.database.Database;
import com.disney.utils.dataFactory.database.Recordset;
import com.disney.utils.dataFactory.database.databaseImpl.OracleDatabase;
import com.disney.utils.dataFactory.database.sqlStorage.Dreams;

public class TestCompensationFlow_AutoArrived_Positive extends BaseTest{
	private String date = Randomness.generateCurrentXMLDatetime(1);
	private NodeList reservations;
	private String reservation;
	private String sourceAccountingCenter = "3";
	private String rrId;
	private String startDateTime;

	@Override
	@BeforeMethod(alwaysRun = true)
	@Parameters("environment")
	public void setup(@Optional String environment){
		this.environment = environment;
		TestReporter.logScenario("RetrieveTravelPlanSegmentsForAutoArrival");
		RetrieveTravelPlanSegmentsForAutoArrival  retrieve = new RetrieveTravelPlanSegmentsForAutoArrival(environment, "Main");
		retrieve.setProcessDate(date);
		retrieve.setSourceAccountingCenter(sourceAccountingCenter);
		retrieve.sendRequest();
		TestReporter.logAPI(!retrieve.getResponseStatusCode().equals("200"), "An error occurred retrieving reservations for auto arrival: " + retrieve.getFaultString(), retrieve);
		if(reservation == null)reservations = retrieve.getAllReservationNumbers();
		TestReporter.assertTrue(reservations.getLength() > 0, "No reservations were returned for the date ["+date+"]");
	}

	@Test(groups = {"api", "regression", "dining", "scheduledEventsBatchService", "compensation"})
	public void testCompensationFlow_AutoArrived_Positive(){
		TestReporter.logScenario("AutoArrived");
		
		AutoArrived aa = null;
		for(int i = 0; i < reservations.getLength(); i++){
			reservation = reservations.item(i).getTextContent();
			if(retrieveResourceReservationId(retrieveTcgType(reservation))) break;
		}	
		aa = new AutoArrived(environment, "Main");
		aa.setTpsId(reservation);	
		aa.sendRequest(rrId, startDateTime);
		TestReporter.logAPI(!aa.getResponseStatusCode().equals("200"), "An error occurred setting a reservation to AutoArrived: " + aa.getFaultString(), aa);
		TestReporter.assertEquals(aa.getInventoryCountAfter(), aa.getInventoryCountBefore(), "Verify that the booked inventory count ["+aa.getInventoryCountAfter()+"] did not change from teh previous value ["+aa.getInventoryCountBefore()+"].");
	}
	
	private String retrieveTcgType(String tps){
		Database db = new OracleDatabase(environment, Database.DREAMS);
		Recordset rs = new Recordset(db.getResultSet(Dreams.getReservationInfoByTpsId(tps)));
		return rs.getValue("TC_GRP_TYP_NM");
	}
	
	private boolean retrieveResourceReservationId(String tcgType){
		boolean tcgTypeFound = true;
		
		switch (tcgType.toUpperCase()) {
		case "SHOWDINING":
			Retrieve retrieveShow = new Retrieve(environment, "RetrieveDiningEvent"); 
			retrieveShow.setReservationNumber(reservation);
			retrieveShow.sendRequest();
			rrId = retrieveShow.getReservableResourceId();
			tcgTypeFound = !retrieveShow.getServiceStartDate().contains("T00:00:00");
			if(tcgTypeFound) startDateTime = retrieveShow.getServiceStartDate().replace("T", " ");
			break;
		case "TABLESERVICEDINING":
			com.disney.api.soapServices.diningModule.tableServiceDiningServicePort.operations.Retrieve retrieveTable = new com.disney.api.soapServices.diningModule.tableServiceDiningServicePort.operations.Retrieve(environment, "Main");
			retrieveTable.setReservationNumber(reservation);
			retrieveTable.sendRequest();
			rrId = retrieveTable.getReservableResourceId();
			tcgTypeFound = !retrieveTable.getServiceStartDate().contains("T00:00:00");
			if(tcgTypeFound) startDateTime = retrieveTable.getServiceStartDate().replace("T", " ");
			break;
		case "EVENTDINING":
			com.disney.api.soapServices.diningModule.eventDiningService.operations.Retrieve retrieveEvent = new com.disney.api.soapServices.diningModule.eventDiningService.operations.Retrieve(environment, "RetrieveDiningEvent");
			retrieveEvent.setReservationNumber(reservation);
			retrieveEvent.sendRequest();
			rrId = retrieveEvent.getReservableResourceId();
			tcgTypeFound = !retrieveEvent.getServiceStartDate().contains("T00:00:00");
			if(tcgTypeFound) startDateTime = retrieveEvent.getServiceStartDate().replace("T", " ");
			break;
		default:
			tcgTypeFound = false;
			break;
		}
		return tcgTypeFound;
	}
}
