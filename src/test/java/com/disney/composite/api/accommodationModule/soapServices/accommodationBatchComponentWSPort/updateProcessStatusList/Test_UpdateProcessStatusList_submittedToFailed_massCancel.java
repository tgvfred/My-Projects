package com.disney.composite.api.accommodationModule.soapServices.accommodationBatchComponentWSPort.updateProcessStatusList;

import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationModule.accommodationBatchComponentWSPort.operation.UpdateProcessStatusList;
import com.disney.api.soapServices.accommodationModule.accommodationBatchServicePort.operation.StageCancelData;
import com.disney.api.soapServices.accommodationModule.helpers.AccommodationBaseTest;
import com.disney.api.soapServices.accommodationModule.helpers.UpdateProcessStatusListHelper;
import com.disney.utils.Environment;
import com.disney.utils.Randomness;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.database.Database;
import com.disney.utils.dataFactory.database.Recordset;
import com.disney.utils.dataFactory.database.databaseImpl.OracleDatabase;

public class Test_UpdateProcessStatusList_submittedToFailed_massCancel extends AccommodationBaseTest {

    private String errID;
    private String errNM;
    private String errCD;
    private String errDS;

    @Test(groups = { "api", "regression", "accommodation", "accommodationBatchComponentWS", "UpdateProcessStatusList" })
    public void testUpdateProcessStatusList_submittedToFailed_massCancel() {

        UpdateProcessStatusListHelper helper = new UpdateProcessStatusListHelper(environment);

        StageCancelData cancel = new StageCancelData(Environment.getBaseEnvironmentName(environment), "Main");
        cancel.setCancelContactName("Cancel Name");
        cancel.setCancelDate("2017-17-07");
        cancel.setCancelReasonCode("AIR");
        cancel.setIsOverridden("false");
        cancel.setIsWaived("false");
        cancel.setOVerridenCancelFEe("0");
        cancel.setTCg(getBook().getTravelComponentGroupingId());
        cancel.sendRequest();
        TestReporter.logAPI(!cancel.getResponseStatusCode().equals("200"), "An error occurred retrieving the summary for the travel component grouping [" + getBook().getTravelComponentGroupingId() + "]", cancel);

        UpdateProcessStatusList update = new UpdateProcessStatusList(environment, "Main");

        update.setProcessDataIdList(helper.retrieveProcRunId(cancel.getResponseProcessId()));
        update.setProcessType("MASS_CANCEL");
        update.setProcessingStatus("FAILED");
        update.setErrorMessage("WHOOPSIE");
        update.sendRequest();
        TestReporter.logAPI(!update.getResponseStatusCode().equals("200"), "An error occurred retrieving the summary for the travel component grouping [" + getBook().getTravelComponentGroupingId() + "]", update);

        // Validations
        helper.validationOverall(helper.retrieveProcRunId(cancel.getResponseProcessId()), "FAILED", Randomness.generateCurrentDatetime().substring(0, 10));

        helper.validationMassCancel(helper.retrieveProcRunId(cancel.getResponseProcessId()), getBook().getTravelPlanId(), getBook().getTravelComponentGroupingId());

        // Test Case Only Validations
        // Validation only works in Old Service
        // validation(helper.retrieveProcRunId(cancel.getResponseProcessId()));

    }

    // Validations specific for this test case
    public void validation(String procRunId) {

        String sql = "select a.GRP_RES_PROC_RUN_ERR_ID, a.GRP_RES_PROC_RUN_ID, a.GRP_RES_PROC_RUN_ERR_TYP_NM, a.GRP_RES_PROC_RUN_ERR_CD, a.GRP_RES_PROC_RUN_ERR_DS "
                + "from res_mgmt.GRES_PROC_RUN_ERR a "
                + "where a.GRP_RES_PROC_RUN_ID = '" + procRunId + "'";

        Database db = new OracleDatabase(Environment.getBaseEnvironmentName(environment), Database.DREAMS);
        Recordset rs = new Recordset(db.getResultSet(sql));

        errID = rs.getValue("GRP_RES_PROC_RUN_ERR_ID");
        errNM = rs.getValue("GRP_RES_PROC_RUN_ERR_TYP_NM");
        errCD = rs.getValue("GRP_RES_PROC_RUN_ERR_CD");
        errDS = rs.getValue("GRP_RES_PROC_RUN_ERR_DS");

        TestReporter.logStep("Test Case Specific Validations:");
        TestReporter.logStep("Validates that a record is returned");
        TestReporter.assertTrue(!(errID.equals(null)), "Error ID is found! [" + errID + "]");

        TestReporter.logStep("Validates the various columns");
        TestReporter.assertEquals(errNM, "Error", "Verify the Error Name [Error] matches the Error Name found"
                + " in the DB [" + errNM + "]");

        TestReporter.assertEquals(errCD, "NOTKNOWN", "Verify the Error Code [NOTKNOWN] matches the Error Code found"
                + " in the DB [" + errCD + "]");

        TestReporter.assertEquals(errDS, "WHOOPSIE", "Verify the Error Description [WHOOPSIE] matches the Error Description found"
                + " in the DB [" + errDS + "]");
    }

}
