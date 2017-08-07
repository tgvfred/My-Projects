package com.disney.composite.api.accommodationModule.soapServices.accommodationBatchComponentWSPort.getStagedRecordsForReinstate;

import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationModule.accommodationBatchComponentWSPort.operation.GetStagedRecordsForReinstate;
import com.disney.api.soapServices.accommodationModule.accommodationBatchComponentWSPort.operation.StageMassReinstateTransactional;
import com.disney.api.soapServices.accommodationModule.helpers.AccommodationBaseTest;
import com.disney.utils.Environment;
import com.disney.utils.Randomness;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.database.Database;
import com.disney.utils.dataFactory.database.Recordset;
import com.disney.utils.dataFactory.database.databaseImpl.OracleDatabase;

public class TestGetStagedRecordsForReinstate_twoReservations_oneRequest extends AccommodationBaseTest {

    private GetStagedRecordsForReinstate stageReinstate;
    private StageMassReinstateTransactional stage;
    private String firstResTCG;
    private String firstResTP;
    private String firstResTPS;

    public void setupData(String environment) {
        // TestReporter.setDebugLevel(TestReporter.INFO); //Uncomment this line
        // to invoke lower levels of reporting
        setEnvironment(environment);

        // grab the TCG and TPS from the first reservation.
        firstResTCG = getBook().getTravelComponentGroupingId();
        firstResTP = getBook().getTravelPlanSegmentId();
        firstResTPS = getBook().getTravelPlanSegmentId();

        daysOut.set(Randomness.randomNumberBetween(1, 12));
        nights.set(Randomness.randomNumberBetween(1, 3));
        arrivalDate.set(Randomness.generateCurrentXMLDate(getDaysOut()));
        departureDate.set(Randomness.generateCurrentXMLDate(getDaysOut() + getNights()));

        setIsWdtcBooking(false);
        setValues();
        bookReservation();

    }

    @Test(groups = { "api", "regression", "getStagedRecordsForReinstate", "accommodation" })
    public void Test_GetStagedRecordsForReinstate_twoReservations_oneRequest() {
        setupData(environment);

        // First invocation to stage reinstate transactions.
        stage = new StageMassReinstateTransactional(environment, "Main_TwoReservations");
        stage.setTcg(firstResTCG);
        stage.setTpId(firstResTP);
        stage.sendRequest();
        TestReporter.logAPI(!stage.getResponseStatusCode().equals("200"), "Verify that no error occurred staging records for reinstate: " + stage.getFaultString(), stage);

        // pull the group reservation process run id from the database.
        TestReporter.logStep("Pull process reservation run Id from database");
        String sql = "select b.GRP_RES_PROC_ID, b.GRP_RES_PROC_RUN_ID, b.GRP_RES_PROC_RUN_STS_NM from res_mgmt.GRP_RES_PROC a join res_mgmt.GRP_RES_PROC_RUN b on a.GRP_RES_PROC_ID = b.GRP_RES_PROC_ID where a.GRP_RES_PROC_ID in '" + stage.getResponseProcessId() + "'";
        Database db = new OracleDatabase(environment, Database.DREAMS);
        Recordset rs = new Recordset(db.getResultSet(sql));

        // second invocation to stage reinstate transactions.
        stage.setTcg(getBook().getTravelComponentGroupingId());
        stage.setTpId(getBook().getTravelPlanId());
        stage.sendRequest();
        TestReporter.logAPI(!stage.getResponseStatusCode().equals("200"), "Verify that no error occurred staging records for reinstate: " + stage.getFaultString(), stage);

        // pull the second group reservation process run id from the database.
        TestReporter.logStep("Pull process reservation run Id from database");
        String sql2 = "select b.GRP_RES_PROC_ID, b.GRP_RES_PROC_RUN_ID, b.GRP_RES_PROC_RUN_STS_NM from res_mgmt.GRP_RES_PROC a join res_mgmt.GRP_RES_PROC_RUN b on a.GRP_RES_PROC_ID = b.GRP_RES_PROC_ID where a.GRP_RES_PROC_ID in '" + stage.getResponseProcessId() + "'";
        Database db2 = new OracleDatabase(environment, Database.DREAMS);
        Recordset rs2 = new Recordset(db2.getResultSet(sql2));

        // get staged records for reinstate.
        stageReinstate = new GetStagedRecordsForReinstate(environment, "Main_twoReservations");
        stageReinstate.setProcessDataId(rs.getValue("GRP_RES_PROC_RUN_ID"));
        stageReinstate.addProcessDataId(rs2.getValue("GRP_RES_PROC_RUN_ID"));

        stageReinstate.sendRequest();
        TestReporter.logAPI(!stageReinstate.getResponseStatusCode().equals("200"), "Verify that no error occurred getting staged records for reinstate: " + stageReinstate.getFaultString(), stageReinstate);
        validateResponseReturnNode();
        validations();

        if (Environment.isSpecialEnvironment(environment)) {
            GetStagedRecordsForReinstate clone = (GetStagedRecordsForReinstate) stageReinstate.clone();
            clone.setEnvironment(Environment.getBaseEnvironmentName(environment));
            clone.sendRequest();
            if (!clone.getResponseStatusCode().equals("200")) {
                TestReporter.logAPI(!clone.getResponseStatusCode().equals("200"), "Error was returned", clone);
            }
            clone.addExcludedBaselineAttributeValidations("@xsi:nil");
            clone.addExcludedBaselineAttributeValidations("@xsi:type");
            clone.addExcludedBaselineXpathValidations("/Envelope/Header");
            TestReporter.assertTrue(clone.validateResponseNodeQuantity(stageReinstate, true), "Validating Response Comparison");
        }
    }

    public void validateResponseReturnNode() {
        TestReporter.logStep("Validate the response node has one 'return' node.");
        int numExpectedNodes = 2;
        int returnNodes = stageReinstate.getNumberOfResponseNodesByXPath("/Envelope/Body/getStagedRecordsForReinstateResponse/return");
        TestReporter.assertEquals(returnNodes, numExpectedNodes, "Verify that the response returns the number of 'return' nodes [" + returnNodes + "] is that which is expected [" + numExpectedNodes + "].");
    }

    public void validations() {

        int returnNodes = stageReinstate.getNumberOfResponseNodesByXPath("/Envelope/Body/getStagedRecordsForReinstateResponse/return");

        for (int i = 1; i < returnNodes; i++) {

            String communicationChannel = stageReinstate.getResponseNodeValueByXPath("/Envelope/Body/getStagedRecordsForReinstateResponse/return[" + i + "]/communicationchannel");
            String overrideFreeze = stageReinstate.getResponseNodeValueByXPath("/Envelope/Body/getStagedRecordsForReinstateResponse/return[" + i + "]/roomdetails/overideFreeze");
            String rsrReservation = stageReinstate.getResponseNodeValueByXPath("/Envelope/Body/getStagedRecordsForReinstateResponse/return[" + i + "]/roomdetails/rsrReservation");
            String tcgId = stageReinstate.getResponseNodeValueByXPath("/Envelope/Body/getStagedRecordsForReinstateResponse/return[" + i + "]/roomdetails/travelComponentGroupingId");
            String shared = stageReinstate.getResponseNodeValueByXPath("/Envelope/Body/getStagedRecordsForReinstateResponse/return[" + i + "]/roomdetails/shared");
            String salesChannel = stageReinstate.getResponseNodeValueByXPath("/Envelope/Body/getStagedRecordsForReinstateResponse/return[" + i + "]/saleschannel");
            String tpsId = stageReinstate.getResponseNodeValueByXPath("/Envelope/Body/getStagedRecordsForReinstateResponse/return[" + i + "]/travelPlanSegmentId");
            String reinstateReasonCode = stageReinstate.getResponseNodeValueByXPath("/Envelope/Body/getStagedRecordsForReinstateResponse/return[" + i + "]/reinstateReasonCode");
            String isCancelFeeWaived = stageReinstate.getResponseNodeValueByXPath("/Envelope/Body/getStagedRecordsForReinstateResponse/return[" + i + "]/isCancelFeeWaived");
            String contactName = stageReinstate.getResponseNodeValueByXPath("/Envelope/Body/getStagedRecordsForReinstateResponse/return[" + i + "]/contactName");

            TestReporter.softAssertEquals(communicationChannel, "Guest Facing", "Verify that the response returns the communication channel [" + communicationChannel + "] that is expected [Guest Facing].");
            TestReporter.softAssertEquals(overrideFreeze, "false", "Verify that the response returns the override freeze [" + overrideFreeze + "] that is expected [false].");
            TestReporter.softAssertEquals(rsrReservation, "false", "Verify that the response returns the rsrReservation [" + rsrReservation + "] that is expected [false].");

            if (tcgId.equals(firstResTCG)) {
                TestReporter.softAssertEquals(tcgId, firstResTCG, "Verify that the response returns the tcgId [" + tcgId + "] that is expected [" + firstResTCG + "].");
                TestReporter.softAssertEquals(tpsId, firstResTPS, "Verify that the response returns the tpsId [" + tpsId + "] that is expected [" + firstResTPS + "].");
            } else {
                TestReporter.softAssertEquals(tcgId, getBook().getTravelComponentGroupingId(), "Verify that the response returns the tcgId [" + tcgId + "] that is expected [" + getBook().getTravelComponentGroupingId() + "].");
                TestReporter.softAssertEquals(tpsId, getBook().getTravelPlanSegmentId(), "Verify that the response returns the tpsId [" + tpsId + "] that is expected [" + getBook().getTravelPlanSegmentId() + "].");

            }
            TestReporter.softAssertEquals(shared, "false", "Verify that the response returns the shared status [" + shared + "] that is expected [false].");
            TestReporter.softAssertEquals(salesChannel, "Consumer Direct", "Verify that the response returns the sales channel [" + salesChannel + "] that is expected [Consumer Direct].");
            TestReporter.softAssertEquals(reinstateReasonCode, "Reinstate Contact", "Verify that the response returns the reinstate reason code [" + reinstateReasonCode + "] that is expected [Reinstate Contact].");
            TestReporter.softAssertEquals(isCancelFeeWaived, "false", "Verify that the response returns the cancel fee waived status [" + isCancelFeeWaived + "] that is expected [false].");
            TestReporter.softAssertEquals(contactName, "Reinstate Contact", "Verify that the response returns the contact name [" + contactName + "] that is expected [Reinstate Contact].");
        }

        TestReporter.assertAll();

    }

}