package com.disney.composite.api.accommodationModule.soapServices.accommodationBatchComponentWSPort.getStagedRecordsForCancel;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationModule.accommodationBatchComponentWSPort.operation.GetStagedRecordsForCancel;
import com.disney.api.soapServices.accommodationModule.accommodationBatchServicePort.operation.StageCancelData;
import com.disney.api.soapServices.accommodationModule.helpers.AccommodationBaseTest;
import com.disney.api.soapServices.core.BaseSoapCommands;
import com.disney.utils.Environment;
import com.disney.utils.Randomness;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.database.Database;
import com.disney.utils.dataFactory.database.Recordset;
import com.disney.utils.dataFactory.database.databaseImpl.OracleDatabase;

public class TestGetStagedRecordsForCancel_OneTcg extends AccommodationBaseTest {

    private String processId = null;
    private String contactName = "Cancel Name";
    private String reasonCode = "AIR";
    private String isOverridden = "false";
    private String isWaived = "false";
    private String cancelFee = "0";

    @Override
    @BeforeMethod(alwaysRun = true)
    @Parameters("environment")
    public void setup(String environment) {
        // TestReporter.setDebugLevel(TestReporter.INFO); //Uncomment this line
        // to invoke lower levels of reporting
        setEnvironment(environment);
        isComo.set("false");
        daysOut.set(0);
        nights.set(1);
        arrivalDate.set(Randomness.generateCurrentXMLDate(getDaysOut()));
        departureDate.set(Randomness.generateCurrentXMLDate(getDaysOut() + getNights()));

        setIsWdtcBooking(false);
        setValues();
        bookReservation();

        StageCancelData stageMassCancel = new StageCancelData(environment, "Main");
        stageMassCancel.setCancelContactName(contactName);
        stageMassCancel.setCancelDate(Randomness.generateCurrentXMLDate());
        stageMassCancel.setCancelReasonCode(reasonCode);
        // These nodes are not valid in the RQ when ran in _CM
        // stageMassCancel.setIsOverridden(isOverridden);
        // stageMassCancel.setIsWaived(isWaived);
        stageMassCancel.setRequestNodeValueByXPath("/Envelope/Body/stageCancelData/request/massCancelAccommodationRequestDetails/isOverridden", BaseSoapCommands.REMOVE_NODE.toString());
        stageMassCancel.setRequestNodeValueByXPath("/Envelope/Body/stageCancelData/request/massCancelAccommodationRequestDetails/isWaived", BaseSoapCommands.REMOVE_NODE.toString());
        stageMassCancel.setOVerridenCancelFEe(cancelFee);
        stageMassCancel.setTCg(getBook().getTravelComponentGroupingId());
        stageMassCancel.setRequestNodeValueByXPath("/Envelope/Body/stageCancelData/request/massCancelAccommodationRequestDetails/externalReferenceDetail", BaseSoapCommands.REMOVE_NODE.toString());
        stageMassCancel.sendRequest();
        TestReporter.logAPI(!stageMassCancel.getResponseStatusCode().equals("200"), "An error occurred with StageCancelData request: " + stageMassCancel.getFaultString(), stageMassCancel);

        processId = stageMassCancel.getResponseProcessId();

    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationBatchComponentWSPort", "getStagedRecordsForCancel" })
    public void testGetStagedRecordsForCancel_OneTcg() {

        // Uses group res process ID to get the group res process run ID
        String sql = "select b.GRP_RES_PROC_ID, b.GRP_RES_PROC_RUN_ID, b.GRP_RES_PROC_RUN_STS_NM "
                + "from res_mgmt.GRP_RES_PROC a "
                + "join res_mgmt.GRP_RES_PROC_RUN b on a.GRP_RES_PROC_ID = b.GRP_RES_PROC_ID "
                + "where a.GRP_RES_PROC_ID in ('" + processId + "')";

        Database db = new OracleDatabase(environment, Database.DREAMS);
        Recordset rs = new Recordset(db.getResultSet(sql));

        String runID = rs.getValue("GRP_RES_PROC_RUN_ID");

        GetStagedRecordsForCancel getStaged = new GetStagedRecordsForCancel(environment, "Main");
        getStaged.setProcessDataId(runID);
        getStaged.sendRequest();
        TestReporter.logAPI(!getStaged.getResponseStatusCode().equals("200"), "An error occurred getting staged records for cancel.", getStaged);

        TestReporter.softAssertEquals(getStaged.getCancelContactName(), contactName, "Verify the cancel contact name [" + getStaged.getCancelContactName() + "] "
                + "brought back in the GetStagedRecordsForCancel response matches the value [" + contactName + "] used in the StageMassCancelTransactional request");
        TestReporter.softAssertEquals(getStaged.getCancelDate().substring(0, 10), Randomness.generateCurrentXMLDate(), "Verify the cancel date [" + getStaged.getCancelDate() + "] "
                + "brought back in the GetStagedRecordsForCancel response matches the value [" + Randomness.generateCurrentXMLDate() + "] used in the StageMassCancelTransactional request");
        TestReporter.softAssertEquals(getStaged.getCancelReasonCode(), reasonCode, "Verify the cancel reason code [" + getStaged.getCancelReasonCode() + "] "
                + "brought back in the GetStagedRecordsForCancel response matches the value [" + reasonCode + "] used in the StageMassCancelTransactional request");
        TestReporter.softAssertEquals(getStaged.getOverridden(), isOverridden, "Verify the isOverridden value [" + getStaged.getOverridden() + "] "
                + "brought back in the GetStagedRecordsForCancel response matches the value [" + isOverridden + "] used in the StageMassCancelTransactional request");
        TestReporter.softAssertEquals(getStaged.getWaived(), isWaived, "Verify the isWaived value [" + getStaged.getWaived() + "] "
                + "brought back in the GetStagedRecordsForCancel response matches the value [" + isWaived + "] used in the StageMassCancelTransactional request");
        TestReporter.softAssertEquals(getStaged.getOverriddenCancelFee().substring(0, 1), cancelFee, "Verify the overriddenCancelFee [" + getStaged.getOverriddenCancelFee() + "] "
                + "brought back in the GetStagedRecordsForCancel response matches the value [" + cancelFee + "] used in the StageMassCancelTransactional request");
        TestReporter.softAssertEquals(getStaged.getTravelComponentGroupingId(), getBook().getTravelComponentGroupingId(), "Verify the TcgId [" + getStaged.getTravelComponentGroupingId() + "] "
                + "brought back in the GetStagedRecordsForCancel response matches the value [" + getBook().getTravelComponentGroupingId() + "] used in the StageMassCancelTransactional request");

        TestReporter.assertAll();
        validateDBProcessData(processId);
        validateACM_CNCL_PROC_RUN(runID, getStaged);

        if (Environment.isSpecialEnvironment(environment)) {
            GetStagedRecordsForCancel clone = (GetStagedRecordsForCancel) getStaged.clone();
            clone.setEnvironment(Environment.getBaseEnvironmentName(environment));
            clone.sendRequest();
            if (!clone.getResponseStatusCode().equals("200")) {
                TestReporter.logAPI(!clone.getResponseStatusCode().equals("200"), "Error was returned", clone);
            }
            clone.addExcludedBaselineAttributeValidations("@xsi:nil");
            clone.addExcludedBaselineAttributeValidations("@xsi:type");
            clone.addExcludedBaselineXpathValidations("/Envelope/Header");
            TestReporter.assertTrue(clone.validateResponseNodeQuantity(getStaged, true), "Validating Response Comparison");
        }

    }

    private void validateDBProcessData(String processId) {

        String sql = "select * "
                + "from res_mgmt.GRP_RES_PROC "
                + "WHERE GRP_RES_PROC_ID = '" + processId + "'";

        Database db = new OracleDatabase(environment, Database.DREAMS);
        Recordset rs = new Recordset(db.getResultSet(sql));

        String processName = rs.getValue("GRP_RES_PROC_NM");

        TestReporter.assertEquals(processName, "MASS_CANCEL", "Verify that the process name: [" + processName + "] from res_mgmt.GRP_RES_PROC provided by the stageMassCancelTransactional process ID "
                + "matches the expected value: [MASS_CANCEL] ");
        TestReporter.assertEquals(rs.getRowCount(), 1, "Validate one record in the DB for the processId in the response");

    }

    private void validateACM_CNCL_PROC_RUN(String runID, GetStagedRecordsForCancel getStaged) {

        String sql = "select * "
                + "from ACM_CNCL_PROC_RUN "
                + "WHERE GRP_RES_PROC_RUN_ID = '" + runID + "'";

        Database db = new OracleDatabase(environment, Database.DREAMS);
        Recordset rs = new Recordset(db.getResultSet(sql));

        String tpID = rs.getValue("TP_ID");
        String tcgId = rs.getValue("TC_GRP_NB");
        String waived = rs.getValue("WV_CNCL_FEE_IN");
        String cancelName = rs.getValue("CNCL_CNTCT_NM");
        String cancelReason = rs.getValue("CNCL_RSN_TX");

        TestReporter.softAssertEquals(getStaged.getCancelContactName(), cancelName, "Verify the cancel contact name [" + getStaged.getCancelContactName() + "] "
                + "brought back in the GetStagedRecordsForCancel response matches the value [" + cancelName + "] found in ACM_CNCL_PROC_RUN database using the "
                + "GRP_RES_PROC_RUN_ID");
        TestReporter.softAssertEquals(getStaged.getCancelReasonCode(), cancelReason, "Verify the cancel reason code [" + getStaged.getCancelReasonCode() + "] "
                + "brought back in the GetStagedRecordsForCancel response matches the value [" + cancelReason + "] found in ACM_CNCL_PROC_RUN database using the "
                + "GRP_RES_PROC_RUN_ID");
        TestReporter.softAssertEquals("N", waived, "Verify the isWaived value is N as expected");
        TestReporter.softAssertEquals(getStaged.getTravelComponentGroupingId(), tcgId, "Verify the TcgID [" + getStaged.getTravelComponentGroupingId() + "] "
                + "brought back in the GetStagedRecordsForCancel response matches the value [" + tcgId + "] found in ACM_CNCL_PROC_RUN database using the "
                + "GRP_RES_PROC_RUN_ID");
        TestReporter.softAssertEquals(getBook().getTravelPlanId(), tpID, "Verify the TpID [" + getBook().getTravelPlanId() + "] "
                + "brought back in the book response matches the value [" + tpID + "] found in ACM_CNCL_PROC_RUN database using the "
                + "GRP_RES_PROC_RUN_ID");
        TestReporter.assertAll();

    }

}
