package com.disney.composite.api.accommodationModule.soapServices.accommodationBatchComponentWSPort.getStagedRecordsForReinstate;

import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationModule.accommodationBatchComponentWSPort.operation.GetStagedRecordsForReinstate;
import com.disney.api.soapServices.accommodationModule.accommodationBatchServicePort.operation.StageReinstateData;
import com.disney.api.soapServices.accommodationModule.helpers.AccommodationBaseTest;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.database.Database;
import com.disney.utils.dataFactory.database.Recordset;
import com.disney.utils.dataFactory.database.databaseImpl.OracleDatabase;

public class TestGetStagedRecordsForReinstate_oneReservation extends AccommodationBaseTest {

    private GetStagedRecordsForReinstate stageReinstate;
    private StageReinstateData stage;

    @Test(groups = { "api", "regression", "getStagedRecordsForReinstate", "accommodation" })
    public void Test_GetStagedRecordsForReinstate_oneReservation() {

        // stage reinstate transactions.
        stage = new StageReinstateData(environment, "Main");
        stage.setTcg(getBook().getTravelComponentGroupingId());
        // stage.setTpId(getBook().getTravelPlanId());
        stage.sendRequest();
        TestReporter.logAPI(!stage.getResponseStatusCode().equals("200"), "Verify that no error occurred staging records for reinstate: " + stage.getFaultString(), stage);

        // pull the group reservation process run id from the database.
        TestReporter.logStep("Pull process reservation run Id from database");
        String sql = "select b.GRP_RES_PROC_ID, b.GRP_RES_PROC_RUN_ID, b.GRP_RES_PROC_RUN_STS_NM from res_mgmt.GRP_RES_PROC a join res_mgmt.GRP_RES_PROC_RUN b on a.GRP_RES_PROC_ID = b.GRP_RES_PROC_ID where a.GRP_RES_PROC_ID in '" + stage.getResponseProcessId() + "'";
        Database db = new OracleDatabase(environment, Database.DREAMS);
        Recordset rs = new Recordset(db.getResultSet(sql));

        // get staged records for reinstate.
        stageReinstate = new GetStagedRecordsForReinstate(environment, "Main");
        stageReinstate.setProcessDataId(rs.getValue("GRP_RES_PROC_RUN_ID"));
        stageReinstate.sendRequest();
        TestReporter.logAPI(!stageReinstate.getResponseStatusCode().equals("200"), "Verify that no error occurred getting staged records for reinstate: " + stageReinstate.getFaultString(), stageReinstate);
        validateResponseReturnNode();
        validations();

        // if (Environment.isSpecialEnvironment(environment)) {
        // GetStagedRecordsForReinstate clone = (GetStagedRecordsForReinstate) stageReinstate.clone();
        // clone.setEnvironment(Environment.getBaseEnvironmentName(environment));
        // clone.sendRequest();
        // if (!clone.getResponseStatusCode().equals("200")) {
        // TestReporter.logAPI(!clone.getResponseStatusCode().equals("200"), "Error was returned", clone);
        // }
        // clone.addExcludedBaselineAttributeValidations("@xsi:nil");
        // clone.addExcludedBaselineAttributeValidations("@xsi:type");
        // clone.addExcludedBaselineXpathValidations("/Envelope/Header");
        // TestReporter.assertTrue(clone.validateResponseNodeQuantity(stageReinstate, true), "Validating Response Comparison");
        // }
    }

    public void validateResponseReturnNode() {
        TestReporter.logStep("Validate the response node has one 'return' node.");
        int numExpectedNodes = 1;
        int returnNodes = stageReinstate.getNumberOfResponseNodesByXPath("/Envelope/Body/getStagedRecordsForReinstateResponse/return");
        TestReporter.softAssertEquals(returnNodes, numExpectedNodes, "Verify that the response returns the number of 'return' nodes [" + returnNodes + "] is that which is expected [" + numExpectedNodes + "].");
        TestReporter.assertAll();
    }

    public void validations() {

        String communicationChannel = stageReinstate.getResponseNodeValueByXPath("/Envelope/Body/getStagedRecordsForReinstateResponse/return/reinstateRequest/communicationchannel");
        String overrideFreeze = stageReinstate.getResponseNodeValueByXPath("/Envelope/Body/getStagedRecordsForReinstateResponse/return/reinstateRequest/roomdetails/overideFreeze");
        String rsrReservation = stageReinstate.getResponseNodeValueByXPath("/Envelope/Body/getStagedRecordsForReinstateResponse/return/reinstateRequest/roomdetails/rsrReservation");
        String tcgId = stageReinstate.getResponseNodeValueByXPath("/Envelope/Body/getStagedRecordsForReinstateResponse/return/reinstateRequest/roomdetails/travelComponentGroupingId");
        String shared = stageReinstate.getResponseNodeValueByXPath("/Envelope/Body/getStagedRecordsForReinstateResponse/return/reinstateRequest/roomdetails/shared");
        String salesChannel = stageReinstate.getResponseNodeValueByXPath("/Envelope/Body/getStagedRecordsForReinstateResponse/return/reinstateRequest/saleschannel");
        String tpsId = stageReinstate.getResponseNodeValueByXPath("/Envelope/Body/getStagedRecordsForReinstateResponse/return/reinstateRequest/travelPlanSegmentId");
        String reinstateReasonCode = stageReinstate.getResponseNodeValueByXPath("/Envelope/Body/getStagedRecordsForReinstateResponse/return/reinstateRequest/reinstateReasonCode");
        String isCancelFeeWaived = stageReinstate.getResponseNodeValueByXPath("/Envelope/Body/getStagedRecordsForReinstateResponse/return/reinstateRequest/isCancelFeeWaived");
        String contactName = stageReinstate.getResponseNodeValueByXPath("/Envelope/Body/getStagedRecordsForReinstateResponse/return/reinstateRequest/contactName");

        TestReporter.softAssertEquals(communicationChannel, "Guest Facing", "Verify that the response returns the communication channel [" + communicationChannel + "] that is expected [Guest Facing].");
        TestReporter.softAssertEquals(overrideFreeze, "false", "Verify that the response returns the override freeze [" + overrideFreeze + "] that is expected [false].");
        TestReporter.softAssertEquals(rsrReservation, "false", "Verify that the response returns the rsrReservation [" + rsrReservation + "] that is expected [false].");
        TestReporter.softAssertEquals(tcgId, getBook().getTravelComponentGroupingId(), "Verify that the response returns the tcgId [" + tcgId + "] that is expected [" + getBook().getTravelComponentGroupingId() + "].");
        TestReporter.softAssertEquals(shared, "false", "Verify that the response returns the shared status [" + shared + "] that is expected [false].");
        TestReporter.softAssertEquals(salesChannel, "Consumer Direct", "Verify that the response returns the sales channel [" + salesChannel + "] that is expected [Consumer Direct].");
        TestReporter.softAssertEquals(tpsId, getBook().getTravelPlanSegmentId(), "Verify that the response returns the tpsId [" + tpsId + "] that is expected [" + getBook().getTravelPlanSegmentId() + "].");
        TestReporter.softAssertEquals(reinstateReasonCode, "RIN8", "Verify that the response returns the reinstate reason code [" + reinstateReasonCode + "] that is expected [RIN8].");
        TestReporter.softAssertEquals(isCancelFeeWaived, "false", "Verify that the response returns the cancel fee waived status [" + isCancelFeeWaived + "] that is expected [false].");
        TestReporter.softAssertEquals(contactName, "Reinstate Contact", "Verify that the response returns the contact name [" + contactName + "] that is expected [Reinstate Contact].");

        TestReporter.assertAll();

    }
}
