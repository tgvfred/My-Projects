package com.disney.composite.api.accommodationModule.soapServices.accommodationBatchWSPort.getStagedRecordsForReinstate;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationModule.accommodationBatchComponentWSPort.operation.GetStagedRecordsForReinstate;
import com.disney.api.soapServices.accommodationModule.accommodationBatchComponentWSPort.operation.StageMassReinstateTransactional;
import com.disney.api.soapServices.accommodationModule.helpers.AccommodationBaseTest;
import com.disney.api.soapServices.travelPlanSegmentModule.travelPlanSegmentServicePort.operations.RetrieveComment;
import com.disney.utils.Environment;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.database.Database;
import com.disney.utils.dataFactory.database.Recordset;
import com.disney.utils.dataFactory.database.databaseImpl.OracleDatabase;

public class TestGetStagedRecordsForReinstate_oneReservation extends AccommodationBaseTest {

    private GetStagedRecordsForReinstate stageReinstate;
    private StageMassReinstateTransactional stage;

    @BeforeMethod(alwaysRun = true)
    @Parameters("environment")

    @Test(groups = { "api", "regression", "getStagedRecordsForReinstate", "accommodation" })
    public void Test_GetStagedRecordsForReinstate_oneReservation() {

        // stage reinstate transactions.
        stage = new StageMassReinstateTransactional(environment, "Main");
        stage.setTcg(getBook().getTravelComponentGroupingId());
        stage.setTpId(getBook().getTravelPlanId());
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

        if (Environment.isSpecialEnvironment(environment)) {
            RetrieveComment clone = (RetrieveComment) stageReinstate.clone();
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
        int numExpectedNodes = 1;
        int returnNodes = stageReinstate.getNumberOfResponseNodesByXPath("/Envelope/Body/getStagedRecordsForReinstateResponse/return");
        TestReporter.softAssertEquals(returnNodes, numExpectedNodes, "Verify that the response returns the number of 'return' nodes [" + returnNodes + "] is that which is expected [" + numExpectedNodes + "].");

    }
}
