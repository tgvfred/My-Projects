package com.disney.composite.api.accommodationModule.soapServices.accommodationBatchComponentWSPort.getStagedRecordsForRemoveGroup;

import java.util.LinkedHashMap;
import java.util.LinkedList;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationModule.accommodationBatchComponentWSPort.operation.GetStagedRecordsForRemoveGroup;
import com.disney.api.soapServices.accommodationModule.accommodationBatchServicePort.operation.StageRemoveGroupData;
import com.disney.api.soapServices.accommodationModule.applicationError.AccommodationBatchErrorCode;
import com.disney.api.soapServices.accommodationModule.helpers.AccommodationBaseTest;
import com.disney.api.soapServices.core.BaseSoapCommands;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.database.Database;
import com.disney.utils.dataFactory.database.Recordset;
import com.disney.utils.dataFactory.database.databaseImpl.OracleDatabase;

public class TestGetStagedRecordsForRemoveGroup_Positive extends AccommodationBaseTest {
    private static Database db;

    @Parameters("environment")
    @BeforeClass(alwaysRun = true)
    public void beforeClass(String environment) {
        db = new OracleDatabase(environment, Database.DREAMS);
    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesService", "getStagedRecordsForRemoveGroup", "negative" })
    public void testGetStagedRecordsForRemoveGroup_nullProcessDataId() {
        TestReporter.logScenario("Test - Get Staged Records For Remove Group - Null Process Data ID");

        GetStagedRecordsForRemoveGroup getStagedRecordsForRemoveGroup = new GetStagedRecordsForRemoveGroup(environment);
        getStagedRecordsForRemoveGroup.setProcessDataID("");
        getStagedRecordsForRemoveGroup.sendRequest();
        String faultString = "Invalid Request : Invalid Request";
        TestReporter.assertEquals(getStagedRecordsForRemoveGroup.getFaultString(), faultString, "Verify that the fault string [" + getStagedRecordsForRemoveGroup.getFaultString() + "] is that which is expected [" + faultString + "].");

        validateApplicationError(getStagedRecordsForRemoveGroup, AccommodationBatchErrorCode.INVALID_REQUEST);
    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesService", "getStagedRecordsForRemoveGroup" })
    public void testGetStagedRecordsForRemoveGroup_oneReservation() {
        createGroupBooking();
        String processID = getProcessDataID();

        TestReporter.logScenario("Test - Get Staged Records For Remove Group - One Reservation");

        buildAndSendRequestAndValidateResponse(processID);
    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesService", "getStagedRecordsForRemoveGroup", "debug" })
    public void TestGetStagedRecordsForRemoveGroup_twoReservations() {
        createGroupBooking();
        String firstTcg = getBook().getTravelComponentGroupingId();
        createGroupBooking();
        String secondTcg = getBook().getTravelComponentGroupingId();
        LinkedHashMap<Integer, String> procRunIds = getProcessDataID(firstTcg, secondTcg);

        TestReporter.logScenario("Test - Get Staged Records For Remove Group - Two Reservations");
        LinkedList<String> tcgs = new LinkedList<>();
        tcgs.add(firstTcg);
        tcgs.add(secondTcg);
        buildAndSendRequestAndValidateResponse(procRunIds, tcgs);
    }

    /*
     * Utility functions
     */
    private void createGroupBooking() {
        setDaysOut(0);
        setNights(1);
        setArrivalDate(getDaysOut());
        setDepartureDate(getDaysOut() + getNights());
        setValues();
        setIsWdtcBooking(true);
        setAddNewGuest(true);
        bookReservation();
    }

    private String getProcessDataID() {
        StageRemoveGroupData stageRemoveGroupData = new StageRemoveGroupData(environment);
        stageRemoveGroupData.setRequestNodeValueByXPath("//processId", BaseSoapCommands.REMOVE_NODE.toString());
        stageRemoveGroupData.setTcg(getBook().getTravelComponentGroupingId());
        stageRemoveGroupData.sendRequest();

        TestReporter.assertEquals(stageRemoveGroupData.getResponseStatusCode(), "200", "The stage remove group transaction precondition succeeded: " + stageRemoveGroupData.getFaultString());

        String sql = "select b.GRP_RES_PROC_RUN_ID"
                + " from res_mgmt.GRP_RES_PROC a"
                + " join res_mgmt.GRP_RES_PROC_RUN b on a.GRP_RES_PROC_ID = b.GRP_RES_PROC_ID"
                + " where a.GRP_RES_PROC_ID = " + stageRemoveGroupData.getResponseProcessId();

        Recordset results = new Recordset(db.getResultSet(sql));
        TestReporter.assertGreaterThanZero(results.getRowCount());

        return results.getValue("GRP_RES_PROC_RUN_ID");
    }

    private LinkedHashMap<Integer, String> getProcessDataID(String firstTcg, String secondTcg) {
        StageRemoveGroupData stageRemoveGroupData = new StageRemoveGroupData(environment);
        stageRemoveGroupData.setRequestNodeValueByXPath("//processId", BaseSoapCommands.REMOVE_NODE.toString());
        stageRemoveGroupData.setTcg(firstTcg);
        stageRemoveGroupData.setRequestNodeValueByXPath("/Envelope/Body/stageRemoveGroupData/request", BaseSoapCommands.ADD_NODE.commandAppend("travelComponentGroupNoList"));
        stageRemoveGroupData.setRequestNodeValueByXPath("/Envelope/Body/stageRemoveGroupData/request/travelComponentGroupNoList[2]", secondTcg);
        stageRemoveGroupData.sendRequest();

        TestReporter.assertEquals(stageRemoveGroupData.getResponseStatusCode(), "200", "The stage remove group transaction precondition succeeded: " + stageRemoveGroupData.getFaultString());

        String sql = "select b.GRP_RES_PROC_RUN_ID"
                + " from res_mgmt.GRP_RES_PROC a"
                + " join res_mgmt.GRP_RES_PROC_RUN b on a.GRP_RES_PROC_ID = b.GRP_RES_PROC_ID"
                + " where a.GRP_RES_PROC_ID = " + stageRemoveGroupData.getResponseProcessId();

        Recordset results = new Recordset(db.getResultSet(sql));
        TestReporter.assertGreaterThanZero(results.getRowCount());

        LinkedHashMap<Integer, String> procRunIds = new LinkedHashMap<>();
        int index = 0;
        do {
            procRunIds.put(index, results.getValue("GRP_RES_PROC_RUN_ID"));
            results.moveNext();
            index++;
        } while (results.hasNext());
        return procRunIds;
    }

    private GetStagedRecordsForRemoveGroup buildAndSendRequestAndValidateResponse(String pdID) {
        GetStagedRecordsForRemoveGroup getStagedRecordsForRemoveGroup = new GetStagedRecordsForRemoveGroup(environment);
        getStagedRecordsForRemoveGroup.setProcessDataID(pdID);
        getStagedRecordsForRemoveGroup.sendRequest();

        TestReporter.logAPI(!getStagedRecordsForRemoveGroup.getResponseStatusCode().equals("200"), "The request was not successful: " + getStagedRecordsForRemoveGroup.getFaultString(), getStagedRecordsForRemoveGroup);
        if (pdID.isEmpty()) {
            TestReporter.assertEquals(getStagedRecordsForRemoveGroup.getNumberOfResponseNodesByXPath("//return"), 0, "There was no information returned in the response.");
        } else {
            try {
                TestReporter.softAssertEquals(getBook().getTravelComponentGroupingId(), getStagedRecordsForRemoveGroup.getRoomTravelComponentGroupingID(), "The TCG ID in the response was equal to the one in the request.");
            } finally {
                TestReporter.assertAll();
            }
        }
        return getStagedRecordsForRemoveGroup;
    }

    private GetStagedRecordsForRemoveGroup buildAndSendRequestAndValidateResponse(LinkedHashMap<Integer, String> procRunIds, LinkedList<String> tcgs) {

        GetStagedRecordsForRemoveGroup getStagedRecordsForRemoveGroup = new GetStagedRecordsForRemoveGroup(environment);
        getStagedRecordsForRemoveGroup.setProcessDataID(procRunIds.get(0));
        getStagedRecordsForRemoveGroup.setRequestNodeValueByXPath("Envelope/Body/getStagedRecordsForRemoveGroup", BaseSoapCommands.ADD_NODE.commandAppend("processDataId"));
        getStagedRecordsForRemoveGroup.setRequestNodeValueByXPath("Envelope/Body/getStagedRecordsForRemoveGroup/processDataId[2]", procRunIds.get(1));
        getStagedRecordsForRemoveGroup.sendRequest();

        TestReporter.logAPI(!getStagedRecordsForRemoveGroup.getResponseStatusCode().equals("200"), "The request was not successful.", getStagedRecordsForRemoveGroup);
        TestReporter.softAssertTrue(getStagedRecordsForRemoveGroup.getNumberOfResponseNodesByXPath("/Envelope/Body/getStagedRecordsForRemoveGroupResponse/return") == 2, "Verify that 1 return node are returned.");

        TestReporter.softAssertTrue(tcgs.contains(getStagedRecordsForRemoveGroup.getRoomTravelComponentGroupingID("1")), "Verify that the TCG [" + getStagedRecordsForRemoveGroup.getRoomTravelComponentGroupingID("1") + "] is contained in the response [" + tcgs + "].");
        TestReporter.softAssertTrue(tcgs.contains(getStagedRecordsForRemoveGroup.getRoomTravelComponentGroupingID("2")), "Verify that the TCG [" + getStagedRecordsForRemoveGroup.getRoomTravelComponentGroupingID("2") + "] is contained in the response [" + tcgs + "].");

        TestReporter.assertAll();
        return getStagedRecordsForRemoveGroup;
    }
}