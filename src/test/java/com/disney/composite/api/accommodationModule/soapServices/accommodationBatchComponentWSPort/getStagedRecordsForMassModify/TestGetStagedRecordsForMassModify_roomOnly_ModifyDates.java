package com.disney.composite.api.accommodationModule.soapServices.accommodationBatchComponentWSPort.getStagedRecordsForMassModify;

import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationModule.accommodationBatchComponentWSPort.operation.GetStagedRecordsForMassModify;
import com.disney.api.soapServices.accommodationModule.accommodationBatchComponentWSPort.operation.StageMassModifyTransactional;
import com.disney.api.soapServices.accommodationModule.helpers.AccommodationBaseTest;
import com.disney.api.soapServices.core.BaseSoapCommands;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.database.Database;
import com.disney.utils.dataFactory.database.Recordset;
import com.disney.utils.dataFactory.database.databaseImpl.OracleDatabase;

public class TestGetStagedRecordsForMassModify_roomOnly_ModifyDates extends AccommodationBaseTest {
    String processName;
    String tcId;
    String tpsId;
    String tcgId;
    String startDate;
    String endDate;
    String packageCode;
    String resortCode;
    String roomType;
    String firstName;
    String lastName;
    String partyId;
    String doNotMail;
    String doNotPhone;
    String preferredLanguage;
    String inventoryReasonCode;
    String inventoryContactName;
    String guestId;

    @Test(groups = { "api", "regression", "getStagedRecordsForMassModify", "accommodation" })
    public void testGetStagedRecordsForMassModify_roomOnly_ModifyDates() {

        processName = "MASS_MODIFY";
        tcId = getBook().getTravelComponentId();
        tpsId = getBook().getTravelPlanSegmentId();
        tcgId = getBook().getTravelComponentGroupingId();
        startDate = getArrivalDate();
        endDate = getDepartureDate();
        packageCode = getPackageCode();
        resortCode = getResortCode();
        roomType = getRoomTypeCode();
        firstName = getHouseHold().primaryGuest().getFirstName();
        lastName = getHouseHold().primaryGuest().getLastName();
        partyId = "0";
        doNotMail = "true";
        doNotPhone = "true";
        preferredLanguage = "eng";
        inventoryReasonCode = "RIN8";
        inventoryContactName = "Inventory Contact";
        guestId = getBook().getGuestId();

        StageMassModifyTransactional stage = new StageMassModifyTransactional(environment, "MainProcLst");
        stage.setProcessName(processName);
        stage.setMassModifyRoomDetailTcId(tcId);
        stage.setMassModifyRoomDetailTpsId(tpsId);
        stage.setMassModifyRoomDetailTcgID(tcgId);
        stage.setMassModifyRoomDetailPeriodStartDate(startDate);
        stage.setMassModifyRoomDetailPeriodEndDates(endDate);
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
        stage.setMassModifyRoomDetailPrimaryGuestDetailACtive(BaseSoapCommands.REMOVE_NODE.toString());
        stage.setMassModifyRoomDetailConfirmationIndicator(BaseSoapCommands.REMOVE_NODE.toString());
        stage.sendRequest();

        TestReporter.logAPI(!stage.getResponseStatusCode().equals("200"), "Error sending request", stage);

        String originalProcessId = getProcessIdFromDatabase(stage);

        TestReporter.logStep("Retrieve staged record for Mass Modify");
        GetStagedRecordsForMassModify mod = new GetStagedRecordsForMassModify(environment);
        mod.setProcessDataId(originalProcessId);
        mod.sendRequest();

        TestReporter.logAPI(!mod.getResponseStatusCode().equals("200"), "Error sending request", mod);
        validateResponse(mod, startDate, endDate);

        // String modifiedStartDate = getArrivalDate();
        // String modifiedEndDate = getDepartureDate();

        startDate = getArrivalDate();
        endDate = getDepartureDate();

        // TestReporter.logStep("Verify that the modified dates differ from the original dates");
        // TestReporter.softAssertFalse(modifiedStartDate.equals(startDate), "Verify that the modified start date [" + modifiedStartDate + "] is different from the original [" + startDate + "]");
        // TestReporter.softAssertFalse(modifiedEndDate.equals(endDate), "Verify that the modified end date [" + modifiedEndDate + "] is different from the original [" + endDate + "]");
        // TestReporter.assertAll();

        // enter modified dates
        stage.setProcessName(processName);
        stage.setMassModifyRoomDetailTcId(tcId);
        stage.setMassModifyRoomDetailTpsId(tpsId);
        stage.setMassModifyRoomDetailTcgID(tcgId);
        stage.setMassModifyRoomDetailPeriodStartDate(startDate);// (modifiedStartDate);
        stage.setMassModifyRoomDetailPeriodEndDates(endDate);// (modifiedEndDate);
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
        stage.sendRequest();

        TestReporter.logAPI(!stage.getResponseStatusCode().equals("200"), "Error sending request", stage);
        String newProcessId = getProcessIdFromDatabase(stage);

        TestReporter.assertEquals(originalProcessId, newProcessId, "Verify that that the original process Id [" + originalProcessId + "] matches the new process Id [" + newProcessId + "]");
        validateResponse(mod, startDate, endDate); // (modifiedStartDate, modifiedEndDate);
    }

    private String getProcessIdFromDatabase(StageMassModifyTransactional stage) {

        String sql = "select a.GRP_RES_PROC_RUN_ID " +
                " from res_mgmt.GRP_RES_PROC_RUN a " +
                " where a. GRP_RES_PROC_ID = " + stage.getResponseProcessId() + " ";

        Database db = new OracleDatabase(environment, Database.DREAMS);
        Recordset rs = new Recordset(db.getResultSet(sql));
        rs.print();

        return rs.getValue("GRP_RES_PROC_RUN_ID");

    }

    private void validateResponse(GetStagedRecordsForMassModify mod, String startDate, String endDate) {

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
        TestReporter.softAssertEquals(mod.getGuestId(), guestId, "Verify that the retrieved Guest ID [" + mod.getGuestId() + "] matches the expected [" + guestId + "]");
        TestReporter.assertAll();
    }
}
