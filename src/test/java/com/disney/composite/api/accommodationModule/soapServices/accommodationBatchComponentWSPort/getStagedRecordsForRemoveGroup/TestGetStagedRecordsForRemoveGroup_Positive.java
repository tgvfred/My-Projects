package com.disney.composite.api.accommodationModule.soapServices.accommodationBatchComponentWSPort.getStagedRecordsForRemoveGroup;

import java.util.LinkedHashMap;

import org.testng.SkipException;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationModule.accommodationBatchComponentWSPort.operation.GetStagedRecordsForRemoveGroup;
import com.disney.api.soapServices.accommodationModule.accommodationBatchServicePort.operation.StageRemoveGroupData;
import com.disney.api.soapServices.accommodationModule.helpers.AccommodationBaseTest;
import com.disney.api.soapServices.core.BaseSoapCommands;
import com.disney.utils.Environment;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.database.Database;
import com.disney.utils.dataFactory.database.Recordset;
import com.disney.utils.dataFactory.database.databaseImpl.OracleDatabase;

public class TestGetStagedRecordsForRemoveGroup_Positive extends AccommodationBaseTest {
    private static Database db, recdb;

    @Parameters("environment")
    @BeforeClass(alwaysRun = true)
    public void beforeClass(String environment) {
        db = new OracleDatabase(environment, Database.DREAMS);
        recdb = new OracleDatabase(environment, Database.RECOMMENDER);
    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesService", "getStagedRecordsForRemoveGroup" })
    public void testGetStagedRecordsForRemoveGroup_nullProcessDataId() {
        TestReporter.logScenario("Test - Get Staged Records For Remove Group - Null Process Data ID");

        GetStagedRecordsForRemoveGroup getStagedRecordsForRemoveGroup = buildAndSendRequestAndValidateResponse("");
        validateSpecialEnvironment(getStagedRecordsForRemoveGroup);
    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesService", "getStagedRecordsForRemoveGroup" })
    public void testGetStagedRecordsForRemoveGroup_oneReservation() {
        createGroupBooking();
        String processID = getProcessDataID();

        TestReporter.logScenario("Test - Get Staged Records For Remove Group - One Reservation");

        GetStagedRecordsForRemoveGroup getStagedRecordsForRemoveGroup = buildAndSendRequestAndValidateResponse(processID);
        validateSpecialEnvironment(getStagedRecordsForRemoveGroup);
    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesService", "getStagedRecordsForRemoveGroup", "debug" })
    public void TestGetStagedRecordsForRemoveGroup_twoReservations() {

        if (Environment.isSpecialEnvironment(environment)) {
            if (true) {
                throw new SkipException("RS doesn't bring back multiple Records.");
            }
        }
        createGroupBooking();
        String firstTcg = getBook().getTravelComponentGroupingId();
        createGroupBooking();
        String secondTcg = getBook().getTravelComponentGroupingId();
        LinkedHashMap<Integer, String> procRunIds = getProcessDataID(firstTcg, secondTcg);

        TestReporter.logScenario("Test - Get Staged Records For Remove Group - Two Reservations");

        GetStagedRecordsForRemoveGroup getStagedRecordsForRemoveGroup = buildAndSendRequestAndValidateResponse(procRunIds);
        validateSpecialEnvironment(getStagedRecordsForRemoveGroup);
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

        TestReporter.assertEquals(stageRemoveGroupData.getResponseStatusCode(), "200", "The stage remove group transaction precondition succeeded.");

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
        stageRemoveGroupData.setRequestNodeValueByXPath("/Envelope/Body/stageRemoveGroupTransactional/request", BaseSoapCommands.ADD_NODE.commandAppend("travelComponentGroupNoList"));
        stageRemoveGroupData.setRequestNodeValueByXPath("/Envelope/Body/stageRemoveGroupTransactional/request/travelComponentGroupNoList[2]", secondTcg);
        stageRemoveGroupData.sendRequest();

        TestReporter.assertEquals(stageRemoveGroupData.getResponseStatusCode(), "200", "The stage remove group transaction precondition succeeded.");

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

        TestReporter.logAPI(!getStagedRecordsForRemoveGroup.getResponseStatusCode().equals("200"), "The request was not successful.", getStagedRecordsForRemoveGroup);
        if (pdID.isEmpty()) {
            TestReporter.assertEquals(getStagedRecordsForRemoveGroup.getNumberOfResponseNodesByXPath("//return"), 0, "There was no information returned in the response.");
        } else {
            try {
                Recordset results = new Recordset(recdb.getResultSet("select a.PLAN_TYPE from pma_wdw.pkg a"
                        + " where a.pkg_cd = '" + getStagedRecordsForRemoveGroup.getRoomPackageCode() + "'"));

                TestReporter.softAssertEquals(getExternalRefNumber(), getStagedRecordsForRemoveGroup.getRoomExternalReferenceNumber(), "The External Reference Number in the response was equal to the one in the request.");
                TestReporter.softAssertEquals(getBook().getTravelPlanSegmentId(), getStagedRecordsForRemoveGroup.getTravelPlanSegmentID(), "The TPS ID in the response was equal to the one in the request.");
                TestReporter.softAssertEquals(getBook().getTravelComponentGroupingId(), getStagedRecordsForRemoveGroup.getRoomTravelComponentGroupingID(), "The TCG ID in the response was equal to the one in the request.");
                TestReporter.softAssertEquals(getBook().getTravelComponentId(), getStagedRecordsForRemoveGroup.getRoomTravelComponentID(), "The TC ID in the response was equal to the one in the request.");
                TestReporter.softAssertEquals(getResortCode(), getStagedRecordsForRemoveGroup.getRoomResortCode(), "The Resort Code in the response was equal to the one in the request.");
                TestReporter.softAssertEquals(getRoomTypeCode(), getStagedRecordsForRemoveGroup.getRoomTypeCode(), "The Room Type Code in the response was equal to the one in the request.");
                if (TestReporter.softAssertTrue(results.getRowCount() > 0, "The Package Code Plan Type was found in the database.")) {
                    TestReporter.softAssertEquals(results.getValue("PLAN_TYPE"), "Room Only", "The Package Code in the response was not a group booking.");
                }
            } finally {
                TestReporter.assertAll();
            }
        }
        return getStagedRecordsForRemoveGroup;
    }

    private GetStagedRecordsForRemoveGroup buildAndSendRequestAndValidateResponse(LinkedHashMap<Integer, String> procRunIds) {

        GetStagedRecordsForRemoveGroup getStagedRecordsForRemoveGroup = new GetStagedRecordsForRemoveGroup(environment);
        getStagedRecordsForRemoveGroup.setProcessDataID(procRunIds.get(0));
        getStagedRecordsForRemoveGroup.setRequestNodeValueByXPath("Envelope/Body/getStagedRecordsForRemoveGroup", BaseSoapCommands.ADD_NODE.commandAppend("processDataId"));
        getStagedRecordsForRemoveGroup.setRequestNodeValueByXPath("Envelope/Body/getStagedRecordsForRemoveGroup/processDataId[2]", procRunIds.get(1));
        getStagedRecordsForRemoveGroup.sendRequest();

        TestReporter.logAPI(!getStagedRecordsForRemoveGroup.getResponseStatusCode().equals("200"), "The request was not successful.", getStagedRecordsForRemoveGroup);
        TestReporter.softAssertTrue(getStagedRecordsForRemoveGroup.getNumberOfResponseNodesByXPath("/Envelope/Body/getStagedRecordsForRemoveGroupResponse/return") == 2, "Verify that 2 return nodes are returned.");
        try {
            Recordset results = new Recordset(recdb.getResultSet("select a.PLAN_TYPE from pma_wdw.pkg a"
                    + " where a.pkg_cd = '" + getStagedRecordsForRemoveGroup.getRoomPackageCode() + "'"));

            TestReporter.softAssertEquals(getExternalRefNumber(), getStagedRecordsForRemoveGroup.getRoomExternalReferenceNumber(), "The External Reference Number in the response was equal to the one in the request.");
            TestReporter.softAssertEquals(getBook().getTravelPlanSegmentId(), getStagedRecordsForRemoveGroup.getTravelPlanSegmentID(), "The TPS ID in the response was equal to the one in the request.");
            TestReporter.softAssertEquals(getBook().getTravelComponentGroupingId(), getStagedRecordsForRemoveGroup.getRoomTravelComponentGroupingID(), "The TCG ID in the response was equal to the one in the request.");
            TestReporter.softAssertEquals(getBook().getTravelComponentId(), getStagedRecordsForRemoveGroup.getRoomTravelComponentID(), "The TC ID in the response was equal to the one in the request.");
            TestReporter.softAssertEquals(getResortCode(), getStagedRecordsForRemoveGroup.getRoomResortCode(), "The Resort Code in the response was equal to the one in the request.");
            TestReporter.softAssertEquals(getRoomTypeCode(), getStagedRecordsForRemoveGroup.getRoomTypeCode(), "The Room Type Code in the response was equal to the one in the request.");
            if (TestReporter.softAssertTrue(results.getRowCount() > 0, "The Package Code Plan Type was found in the database.")) {
                TestReporter.softAssertEquals(results.getValue("PLAN_TYPE"), "Room Only", "The Package Code in the response was not a group booking.");
            }
        } finally {
            TestReporter.assertAll();
        }
        return getStagedRecordsForRemoveGroup;
    }

    private void validateSpecialEnvironment(GetStagedRecordsForRemoveGroup getStagedRecordsForRemoveGroup) {
        if (Environment.isSpecialEnvironment(environment) || isComo.equals("true")) {
            GetStagedRecordsForRemoveGroup getStagedRecordsForRemoveGroupBaseLine = (GetStagedRecordsForRemoveGroup) getStagedRecordsForRemoveGroup.clone();
            getStagedRecordsForRemoveGroupBaseLine.setEnvironment(Environment.getBaseEnvironmentName(environment));
            getStagedRecordsForRemoveGroupBaseLine.sendRequest();
            TestReporter.assertTrue(getStagedRecordsForRemoveGroup.validateResponseNodeQuantity(getStagedRecordsForRemoveGroupBaseLine), "Response Node Validation Result");
        }
    }
}