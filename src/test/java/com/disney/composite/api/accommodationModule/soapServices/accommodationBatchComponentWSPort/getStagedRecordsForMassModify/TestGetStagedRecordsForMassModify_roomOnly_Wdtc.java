package com.disney.composite.api.accommodationModule.soapServices.accommodationBatchComponentWSPort.getStagedRecordsForMassModify;

import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationModule.accommodationBatchComponentWSPort.operation.GetStagedRecordsForMassModify;
import com.disney.api.soapServices.accommodationModule.accommodationBatchServicePort.operation.StageModifyData;
import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.ReplaceAllForTravelPlanSegment;
import com.disney.api.soapServices.accommodationModule.helpers.AccommodationBaseTest;
import com.disney.api.soapServices.accommodationModule.helpers.GetStagedRecordsForMassModifyHelper;
import com.disney.api.soapServices.core.BaseSoapCommands;
import com.disney.utils.Environment;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.database.Database;
import com.disney.utils.dataFactory.database.Recordset;
import com.disney.utils.dataFactory.database.databaseImpl.OracleDatabase;

public class TestGetStagedRecordsForMassModify_roomOnly_Wdtc extends AccommodationBaseTest {
    private String assignOwner = null;
    private ReplaceAllForTravelPlanSegment book;

    @Test(groups = { "api", "regression", "getStagedRecordsForMassModify", "accommodation" })
    public void testGetStagedRecordsForMassModify_roomOnly_Wdtc() {
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
        String guestId = getBook().getGuestId();
        book = getBook();

        setSendRequest(false);
        setIsWdtcBooking(true);
        bookReservation();

        String startDate = getArrivalDate();
        String endDate = getDepartureDate();
        String packageCode = getPackageCode();
        String resortCode = getResortCode();
        String roomType = getRoomTypeCode();
        String blockCode = "01825";
        String firstName = getHouseHold().primaryGuest().getFirstName();
        String lastName = getHouseHold().primaryGuest().getLastName();
        String partyId = "0";
        String doNotMail = "true";
        String doNotPhone = "true";
        String preferredLanguage = "eng";
        String inventoryReasonCode = "RIN8";
        String inventoryContactName = "Inventory Contact";

        StageModifyData stage = new StageModifyData(environment, "MainProcLst");
        stage.setProcessName(processName);
        stage.setMassModifyRoomDetailTcId(tcId);
        stage.setMassModifyRoomDetailTpsId(tpsId);
        stage.setMassModifyRoomDetailTcgID(tcgId);
        stage.setMassModifyRoomDetailPeriodStartDate(startDate);
        stage.setMassModifyRoomDetailPeriodEndDates(endDate);
        stage.setMassModifyRoomDetailPackageCode(packageCode);
        stage.setMassModifyRoomDetailResortCode(resortCode);
        stage.setMassModifyRoomDetailRoomType(roomType);
        stage.setMassModifyRoomDetailBlockCode(blockCode);
        stage.setInventoryReasonCode(inventoryReasonCode);
        stage.setInventoryReasonContactName(inventoryContactName);
        stage.setMassModifyRoomDetailPrimaryGuestDetailFirstName(firstName);
        stage.setMassModifyRoomDetailPrimaryGuestDetailLastName(lastName);
        stage.setMassModifyRoomDetailPrimaryGuestDetailPartyId(partyId);
        stage.setMassModifyRoomDetailPrimaryGuestDetailDoNotMail(doNotMail);
        stage.setMassModifyRoomDetailPrimaryGuestDetailDoNotPhone(doNotPhone);
        stage.setMassModifyRoomDetailPrimaryGuestDetailPreferredLanguage(preferredLanguage);
        stage.setMassModifyRoomDetailPrimaryGuestDetailGuestId(guestId);
        stage.setMassModifyRoomDetailPrimaryGuestDetailACtive(BaseSoapCommands.REMOVE_NODE.toString());
        stage.setMassModifyRoomDetailConfirmationIndicator(BaseSoapCommands.REMOVE_NODE.toString());
        stage.sendRequest();

        TestReporter.logAPI(!stage.getResponseStatusCode().equals("200"), "Error sending request: " + stage.getFaultString(), stage);

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
        TestReporter.softAssertEquals(mod.getResortCode(), resortCode, "Verify that the retrieved resort code [" + mod.getResortCode() + "] matches the expected [" + resortCode + "]");
        TestReporter.softAssertEquals(mod.getRoomType(), roomType, "Verify that the retrieved room type [" + mod.getRoomType() + "] matches the expected [" + roomType + "]");
        TestReporter.softAssertEquals(mod.getBlockCode(), blockCode, "Verify that the retrieved block code [" + mod.getBlockCode() + "] matches the expected [" + blockCode + "]");
        TestReporter.softAssertEquals(mod.getGuestId(), guestId, "Verify that the retrieved Guest ID [" + mod.getGuestId() + "] matches the expected [" + guestId + "]");
        TestReporter.assertAll();

        setBook(book);
        validations();
    }

    private void validations() {
        GetStagedRecordsForMassModifyHelper helper = new GetStagedRecordsForMassModifyHelper(Environment.getBaseEnvironmentName(getEnvironment()), this);
        helper.validateNumberOfTcs(1);
        helper.validateReservationDetails("NULL", "NULL", "NULL", "N", getArrivalDate(), getDepartureDate(), assignOwner, false);
    }
}
