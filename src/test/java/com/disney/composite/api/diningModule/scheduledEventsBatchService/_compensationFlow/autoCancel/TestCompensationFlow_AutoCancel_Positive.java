package com.disney.composite.api.diningModule.scheduledEventsBatchService._compensationFlow.autoCancel;

import org.testng.SkipException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.diningModule.scheduledEventsBatchService.operations.AutoCancel;
import com.disney.api.soapServices.diningModule.showDiningService.operations.Retrieve;
import com.disney.api.soapServices.folioModule.chargeGroup.operations.RetrieveNonGuaranteedGuestChargeGroups;
import com.disney.composite.BaseTest;
import com.disney.test.utils.Randomness;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.database.Database;
import com.disney.utils.dataFactory.database.LogItems;
import com.disney.utils.dataFactory.database.Recordset;
import com.disney.utils.dataFactory.database.databaseImpl.OracleDatabase;
import com.disney.utils.dataFactory.database.sqlStorage.Dreams;

public class TestCompensationFlow_AutoCancel_Positive extends BaseTest{
	private String date = Randomness.generateCurrentXMLDate(150);
	private String sourceAccountingCenter = "3";
	private RetrieveNonGuaranteedGuestChargeGroups retrieve;
	private String expected_TCG;
	private String actual_TCG;
	private String rrId;
	private String startDateTime;
	
	@Override
	@BeforeMethod(alwaysRun = true)
	@Parameters("environment")
	public void setup(@Optional String environment){
		this.environment = environment;
		TestReporter.logScenario("RetrieveNonGuaranteedGuestChargeGroups");
		retrieve = new RetrieveNonGuaranteedGuestChargeGroups(environment);
		retrieve.setRunDate(date);
		retrieve.setSourceAccountingCenter(sourceAccountingCenter);
		retrieve.sendRequest();
		TestReporter.logAPI(!retrieve.getResponseStatusCode().equals("200"), "An error occurred retrieving non-guaranteed guest charge groups: " + retrieve.getFaultString(), retrieve);
		
		LogItems logValidItems = new LogItems();
		logValidItems.addItem("ChargeGroupIF", "retrieveNonGuaranteedGuestChargeGroups", false);
		validateLogs(retrieve, logValidItems, 10000);
	}
	
	@Test(groups = {"api", "regression", "dining", "scheduledEventsBatchService", "compensation"})
	public void testCompensationFlow_AutoCancel_Positive(){	
		TestReporter.logScenario("AutoCancel");	
		
		String ids = "";
		for(String id : retrieve.getAllReservations().values()){
			ids += id + ", ";
		}
		
		ids = ids.substring(0,ids.lastIndexOf(","));
		String sql = "SELECT tcg.TPS_ID, tcg.TC_GRP_NB, tps.TPS_ARVL_DT, tcg.TC_GRP_TYP_NM FROM RES_MGMT.TPS, RES_MGMT.TC_GRP tcg WHERE TPS.TPS_ID = tcg.TPS_ID and tc_grp_nb"
				+ " in( "+ ids + ") and tcg.CREATE_USR_ID_CD = 'AutoJUnit.us' and tps.TPS_ARVL_DT like ('%:00')";
		Database db = new OracleDatabase(environment, Database.DREAMS);
		Recordset rs = new Recordset(db.getResultSet(sql));
		
		if(rs.getRowCount() == 0)
			throw new SkipException("No automated CoMo reservations were returned by RetrieveNonGuaranteedGuestChargeGroups for the date ["+date+"] and source accounting center ["+sourceAccountingCenter+"].");
		
		for(int i = 1; i <= rs.getRowCount(); i++){
			expected_TCG = rs.getValue("TC_GRP_NB", i);
			if(retrieveResourceReservationId(rs.getValue("TC_GRP_TYP_NM"))){
				AutoCancel cancel = new AutoCancel(environment, "Main");
				cancel.setTravelComponentGroupingId(expected_TCG);
				cancel.sendRequest(rrId, startDateTime);
				TestReporter.logAPI(!cancel.getResponseStatusCode().equals("200"), "An error occurred checking out a reservation: " + cancel.getFaultString(), cancel);
				actual_TCG = cancel.getTravelComponentGroupIdUsingTPS(cancel.getTravelPlanSegmentId());
				TestReporter.assertEquals(expected_TCG, actual_TCG, "Verify that the actual travel component grouping number ["+actual_TCG+"] matches the expected travel component grouping number ["+expected_TCG+"].");
				TestReporter.assertEquals(cancel.getTravelStatus(), "Auto Cancelled", "Verify that the actual resevation status ["+cancel.getTravelStatus()+"] matches the expected reservation status [Auto Cancelled].");
				TestReporter.assertTrue(Integer.parseInt(cancel.getInventoryCountAfter()) < Integer.parseInt(cancel.getInventoryCountBefore()), "Verify the booked inventory count ["+cancel.getInventoryCountBefore()+"] has decremented from the previous value ["+cancel.getInventoryCountAfter()+"].");
				break;
			}
		}
	}
	
	private boolean retrieveResourceReservationId(String tcgType){
		Database db = new OracleDatabase(environment, Database.DREAMS);
		Recordset rs = new Recordset(db.getResultSet(Dreams.getTpsByTcg(expected_TCG)));
		boolean tcgTypeFound = true;
		
		switch (tcgType.toUpperCase()) {
		case "SHOWDINING":
			Retrieve retrieveShow = new Retrieve(environment, "RetrieveDiningEvent"); 
			retrieveShow.setReservationNumber(rs.getValue("TPS_ID"));
			retrieveShow.sendRequest();
			rrId = retrieveShow.getReservableResourceId();
			tcgTypeFound = !retrieveShow.getServiceStartDate().contains("T00:00:00");
			if(tcgTypeFound) startDateTime = retrieveShow.getServiceStartDate().replace("T", " ");
			break;
		case "TABLESERVICEDINING":
			com.disney.api.soapServices.diningModule.tableServiceDiningServicePort.operations.Retrieve retrieveTable = new com.disney.api.soapServices.diningModule.tableServiceDiningServicePort.operations.Retrieve(environment, "Main");
			retrieveTable.setReservationNumber(rs.getValue("TPS_ID"));
			retrieveTable.sendRequest();
			rrId = retrieveTable.getReservableResourceId();
			tcgTypeFound = !retrieveTable.getServiceStartDate().contains("T00:00:00");
			if(tcgTypeFound) startDateTime = retrieveTable.getServiceStartDate().replace("T", " ");
			break;
		case "EVENTDINING":
			com.disney.api.soapServices.diningModule.eventDiningService.operations.Retrieve retrieveEvent = new com.disney.api.soapServices.diningModule.eventDiningService.operations.Retrieve(environment, "RetrieveDiningEvent");
			retrieveEvent.setReservationNumber(rs.getValue("TPS_ID"));
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
