package com.disney.composite.api.accommodationModule.soapServices.accommodationBatchComponentWSPort.getStagedRecordsForMassModify;

import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationModule.accommodationBatchComponentWSPort.operation.GetStagedRecordsForMassModify;
import com.disney.api.soapServices.accommodationModule.accommodationBatchComponentWSPort.operation.StageMassModifyTransactional;
import com.disney.api.soapServices.accommodationModule.helpers.AccommodationBaseTest;
import com.disney.api.soapServices.accommodationModule.helpers.GetStagedRecordsForMassModifyHelper;
import com.disney.utils.Environment;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.database.Database;
import com.disney.utils.dataFactory.database.Recordset;
import com.disney.utils.dataFactory.database.databaseImpl.OracleDatabase;

public class TestGetStagedRecordsForMassModify_roomOnly_ModifyInventoryOverride extends AccommodationBaseTest {
    private String assignOwner = null;

    @Test(groups = { "api", "regression", "getStagedRecordsForMassModify", "accommodation" })
    public void testGetStagedRecordsForMassModify_roomOnly_ModifyInventoryOverride() {
        String sql = "select a.TRVL_AGCY_PTY_ID, a.TRVL_AGT_PTY_ID, a.TPS_SECUR_VL, a.TPS_GUAR_IN, a.TPS_ARVL_DT, a.TPS_DPRT_DT, c.TC_STRT_DTS, c.TC_END_DTS, c.ASGN_OWN_ID "
                + "from res_mgmt.tps a "
                + "join res_mgmt.tc_grp b on a.tps_id = b.tps_id "
                + "join res_mgmt.tc c on b.tc_grp_nb = c.tc_grp_nb "
                + "where a.tps_id = " + getBook().getTravelPlanSegmentId();
        Database db = new OracleDatabase(environment, Database.DREAMS);
        Recordset rs = new Recordset(db.getResultSet(sql));
        do {
            if (!rs.getValue("ASGN_OWN_ID").equals("NULL")) {
                assignOwner = rs.getValue("ASGN_OWN_ID");
            }
            rs.moveNext();
        } while (rs.hasNext());

        String processName = "MASS_MODIFY";
        String tcId = getBook().getTravelComponentId();
        String tpsId = getBook().getTravelPlanSegmentId();
        String tcgId = getBook().getTravelComponentGroupingId();
        String startDate = getArrivalDate();
        String endDate = getDepartureDate();
        String packageCode = getPackageCode();
        String firstName = getHouseHold().primaryGuest().getFirstName();
        String lastName = getHouseHold().primaryGuest().getLastName();
        String resortCode = getResortCode();
        String roomType = getRoomTypeCode();
        String partyId = "0";
        String doNotMail = "true";
        String doNotPhone = "true";
        String preferredLanguage = "eng";
        String inventoryReasonCode = "RIN8";
        String inventoryContactName = "Inventory Contact";
        String guestId = getBook().getGuestId();
        String active = "true";

        StageMassModifyTransactional stage = new StageMassModifyTransactional(environment, "MainProcLst");
        stage.setProcessName(processName);
        stage.setMassModifyRoomDetailTcgID(tcgId);
        stage.setMassModifyRoomDetailTcId(tcId);
        stage.setMassModifyRoomDetailTpsId(tpsId);
        stage.setMassModifyRoomDetailPeriodEndDates(endDate);
        stage.setMassModifyRoomDetailPeriodStartDate(startDate);
        stage.setMassModifyRoomDetailPackageCode(packageCode);
        stage.setMassModifyRoomDetailResortCode(resortCode);
        stage.setMassModifyRoomDetailRoomType(roomType);
        stage.setInventoryReasonCode(inventoryReasonCode);
        stage.setInventoryReasonContactName(inventoryContactName);
        stage.setMassModifyRoomDetailPrimaryGuestDetailFirstName(firstName);
        stage.setMassModifyRoomDetailPrimaryGuestDetailLastName(lastName);
        stage.setMassModifyRoomDetailPrimaryGuestDetailPartyId(partyId);
        stage.setMassModifyRoomDetailPrimaryGuestDetailDoNotMail(doNotMail);
        stage.setMassModifyRoomDetailPrimaryGuestDetailDoNotPhone(doNotPhone);
        stage.setMassModifyRoomDetailPrimaryGuestDetailPreferredLanguage(preferredLanguage);
        stage.setMassModifyRoomDetailPrimaryGuestDetailGuestId(guestId);
        stage.setMassModifyRoomDetailPrimaryGuestDetailACtive(active);
        stage.sendRequest();

        TestReporter.logAPI(!stage.getResponseStatusCode().equals("200"), "Error sending request", stage);

        sql = "select a.GRP_RES_PROC_RUN_ID " +
                " from res_mgmt.GRP_RES_PROC_RUN a " +
                " where a. GRP_RES_PROC_ID = " + stage.getResponseProcessId() + " ";

        db = new OracleDatabase(environment, Database.DREAMS);
        rs = new Recordset(db.getResultSet(sql));

        TestReporter.logStep("Retrieve staged record for Mass Modify");
        GetStagedRecordsForMassModify mod = new GetStagedRecordsForMassModify(environment);
        mod.setProcessDataId(rs.getValue("GRP_RES_PROC_RUN_ID"));
        mod.sendRequest();

        TestReporter.logAPI(!mod.getResponseStatusCode().equals("200"), "Error sending request", mod);

        TestReporter.softAssertEquals(mod.getFirstName(), firstName, "Verify that the retrieved first name [" + mod.getFirstName() + "] matches the expected [" + firstName + "]");
        TestReporter.softAssertEquals(mod.getLastName(), lastName, "Verify that the retrieved last name [" + mod.getLastName() + "] matches the expected [" + lastName + "]");
        TestReporter.softAssertEquals(mod.getStartDate(), startDate + "T00:00:00", "Verify that the retrieved start date [" + mod.getStartDate() + "] matches the expected [" + startDate + "T00:00:00" + "]");
        TestReporter.softAssertEquals(mod.getEndDate(), endDate + "T00:00:00", "Verify that the retrieved end date [" + mod.getEndDate() + "] matches the expected [" + endDate + "T00:00:00" + "]");
        TestReporter.softAssertEquals(mod.getPackageCode(), packageCode, "Verify that the retrieved package code [" + mod.getPackageCode() + "] matches the expected [" + packageCode + "]");
        TestReporter.softAssertEquals(mod.getPreferredLanguage(), preferredLanguage, "Verify that the retrieved preferred language [" + mod.getPreferredLanguage() + "] matches the expected [" + preferredLanguage + "]");
        TestReporter.softAssertEquals(mod.getTcgId(), tcgId, "Verify that the retrieved TCG ID [" + mod.getTcgId() + "] matches the expected [" + tcgId + "]");
        TestReporter.softAssertEquals(mod.getTcId(), tcId, "Verify that the retrieved TC ID [" + mod.getTcId() + "] matches the expected [" + tcId + "]");
        TestReporter.softAssertEquals(mod.getTpsId(), tpsId, "Verify that the retrieved TPS ID [" + mod.getTpsId() + "] matches the expected [" + tpsId + "]");
        TestReporter.softAssertEquals(mod.getInventoryReasonCode(), inventoryReasonCode, "Verify that the retrieved inventory reason code [" + mod.getInventoryReasonCode() + "] matches the expected [" + inventoryReasonCode + "]");
        TestReporter.softAssertEquals(mod.getInventoryContactName(), inventoryContactName, "Verify that the retrieved inventory contact name [" + mod.getInventoryContactName() + "] matches the expected [" + inventoryContactName + "]");
        TestReporter.softAssertEquals(mod.getGuestId(), guestId, "Verify that the retrieved Guest ID [" + mod.getGuestId() + "] matches the expected [" + guestId + "]");
        TestReporter.assertAll();

        validations();
    }

    private void validations() {
        GetStagedRecordsForMassModifyHelper helper = new GetStagedRecordsForMassModifyHelper(Environment.getBaseEnvironmentName(getEnvironment()), this);
        helper.validateNumberOfTcs(1);
        helper.validateReservationDetails("NULL", "NULL", "NULL", "N", getArrivalDate(), getDepartureDate(), assignOwner, false);
    }
}
