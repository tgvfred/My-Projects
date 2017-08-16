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

public class TestGetStagedRecordsForMassModify_WdtcToRoomOnly extends AccommodationBaseTest {

    @Test(groups = { "api", "regression", "getStagedRecordsForMassModify", "accommodation" })
    public void testGetStagedRecordsForMassModify_WdtcToRoomOnly() {

        String processName = "MASS_MODIFY";
        String tcId = getBook().getTravelComponentId();
        String tpsId = getBook().getTravelPlanSegmentId();
        String tcgId = getBook().getTravelComponentGroupingId();
        String startDate = getArrivalDate();
        String endDate = getDepartureDate();
        String packageCode = getPackageCode();
        String firstName = getHouseHold().primaryGuest().getFirstName();
        String lastName = getHouseHold().primaryGuest().getLastName();
        String partyId = "0";
        String doNotMail = "true";
        String doNotPhone = "true";
        String preferredLanguage = "eng";
        String resortCode = getResortCode();
        String roomType = getRoomTypeCode();
        String inventoryReasonCode = "RIN8";
        String inventoryContactName = "Inventory Contact";
        String guestId = getBook().getGuestId();
        String active = "true";
        String confirmationIndicator = "true";
        String adultTicket = "true";
        String hardTicketedEvent = "false";
        String baseAdmissionProductId = "2640488";
        String componentId = "0";
        String dayCount = "2";
        String guestReferenceAge = "22";
        String ageType = "ADULT";
        String ticketDetailGuestFirstName = getHouseHold().primaryGuest().getFirstName();
        String ticketDetailGuestLastName = getHouseHold().primaryGuest().getLastName();
        String partofPackage = "false";

        // WDTC to room only
        StageMassModifyTransactional stage = new StageMassModifyTransactional(environment, "MainProcLst");
        stage.setProcessName(processName);
        stage.setMassModifyRoomDetailTcId(tcId);
        stage.setMassModifyRoomDetailTpsId(tpsId);
        stage.setMassModifyRoomDetailTcgID(tcgId);
        stage.setMassModifyRoomDetailPeriodStartDate(startDate);
        stage.setMassModifyRoomDetailPeriodEndDates(endDate);
        stage.setMassModifyRoomDetailPackageCode(packageCode);
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

        TestReporter.logStep("Verify that the request has been stored in the database");

        String sql = "select a.GRP_RES_PROC_RUN_ID " +
                " from res_mgmt.GRP_RES_PROC_RUN a " +
                " where a. GRP_RES_PROC_ID = " + stage.getResponseProcessId() + " ";

        Database db = new OracleDatabase(environment, Database.DREAMS);
        Recordset rs = new Recordset(db.getResultSet(sql));
        rs.print();

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
        TestReporter.softAssertEquals(mod.getResortCode(), resortCode, "Verify that the retrieved resort code [" + mod.getResortCode() + "] matches the expected [" + resortCode + "]");
        TestReporter.softAssertEquals(mod.getTcgId(), tcgId, "Verify that the retrieved TCG ID [" + mod.getTcgId() + "] matches the expected [" + tcgId + "]");
        TestReporter.softAssertEquals(mod.getTcId(), tcId, "Verify that the retrieved TC ID [" + mod.getTcId() + "] matches the expected [" + tcId + "]");
        TestReporter.softAssertEquals(mod.getTpsId(), tpsId, "Verify that the retrieved TPS ID [" + mod.getTpsId() + "] matches the expected [" + tpsId + "]");
        TestReporter.softAssertEquals(mod.getGuestId(), guestId, "Verify that the retrieved Guest ID [" + mod.getGuestId() + "] matches the expected [" + guestId + "]");
        TestReporter.assertAll();

        // Inventory Override Reason
        // StageMassModifyTransactional stage = new StageMassModifyTransactional(environment, "MainProcLst");
        // stage.setProcessName(processName);
        // stage.setMassModifyRoomDetailTcgID(tcgId);
        // stage.setMassModifyRoomDetailTcId(tcId);
        // stage.setMassModifyRoomDetailTpsId(tpsId);
        // stage.setMassModifyRoomDetailPeriodEndDates(endDate);
        // stage.setMassModifyRoomDetailPeriodStartDate(startDate);
        // stage.setMassModifyRoomDetailPackageCode(packageCode);
        // stage.setMassModifyRoomDetailResortCode(resortCode);
        // stage.setMassModifyRoomDetailRoomType(roomType);
        // stage.setMassModifyRoomDetailPrimaryGuestDetailFirstName(firstName);
        // stage.setMassModifyRoomDetailPrimaryGuestDetailLastName(lastName);
        // stage.setMassModifyRoomDetailPrimaryGuestDetailPartyId(partyId);
        // stage.setMassModifyRoomDetailPrimaryGuestDetailDoNotMail(doNotMail);
        // stage.setMassModifyRoomDetailPrimaryGuestDetailDoNotPhone(doNotPhone);
        // stage.setMassModifyRoomDetailPrimaryGuestDetailPreferredLanguage(preferredLanguage);
        // stage.setMassModifyRoomDetailPrimaryGuestDetailGuestId(guestId);
        // stage.setMassModifyRoomDetailPrimaryGuestDetailACtive(active);
        // stage.sendRequest();

        // Staging with Tickets
        // StageMassModifyTransactional stage = new StageMassModifyTransactional(environment, "MainProcLst");
        // stage.setProcessName(processName);
        // stage.setMassModifyRoomDetailTcId(tcId);
        // stage.setMassModifyRoomDetailTpsId(tpsId);
        // stage.setMassModifyRoomDetailTcgID(tcgId);
        // stage.setMassModifyRoomDetailPeriodEndDates(endDate);
        // stage.setMassModifyRoomDetailPeriodStartDate(startDate);
        // stage.setMassModifyRoomDetailPackageCode(packageCode);
        // stage.setMassModifyRoomDetailResortCode(resortCode);
        // stage.setMassModifyRoomDetailRoomType(roomType);
        // stage.setInventoryReasonCode(inventoryReasonCode);
        // stage.setRequestNodeValueByXPath("/Envelope/Body/stageMassModifyTransactional/massModifyRequest/massModifyRoomDetail/inventoryContactName", inventoryContactName);
        // stage.setMassModifyRoomDetailPrimaryGuestDetailFirstName(firstName);
        // stage.setMassModifyRoomDetailPrimaryGuestDetailLastName(lastName);
        // stage.setMassModifyRoomDetailPrimaryGuestDetailPartyId(partyId);
        // stage.setMassModifyRoomDetailPrimaryGuestDetailDoNotMail(doNotMail);
        // stage.setMassModifyRoomDetailPrimaryGuestDetailDoNotPhone(doNotPhone);
        // stage.setMassModifyRoomDetailPrimaryGuestDetailPreferredLanguage(preferredLanguage);
        // stage.setMassModifyRoomDetailPrimaryGuestDetailGuestId(guestId);
        // stage.setMassModifyRoomDetailPrimaryGuestDetailACtive(active);
        // stage.setMassModifyRoomDetailConfirmationIndicator(confirmationIndicator);
        // stage.setRequestNodeValueByXPath("/Envelope/Body/stageMassModifyTransactional/massModifyRequest/massModifyRoomDetail/ticketdetail/adultTicket", adultTicket);
        // stage.setRequestNodeValueByXPath("/Envelope/Body/stageMassModifyTransactional/massModifyRequest/massModifyRoomDetail/ticketdetail/hardTicketedEvent", hardTicketedEvent);
        // stage.setRequestNodeValueByXPath("/Envelope/Body/stageMassModifyTransactional/massModifyRequest/massModifyRoomDetail/ticketdetail/baseAdmissionProductId", baseAdmissionProductId);
        // stage.setRequestNodeValueByXPath("/Envelope/Body/stageMassModifyTransactional/massModifyRequest/massModifyRoomDetail/ticketdetail/code", baseAdmissionProductId);
        // stage.setRequestNodeValueByXPath("/Envelope/Body/stageMassModifyTransactional/massModifyRequest/massModifyRoomDetail/ticketdetail/componentId", componentId);
        // stage.setRequestNodeValueByXPath("/Envelope/Body/stageMassModifyTransactional/massModifyRequest/massModifyRoomDetail/ticketdetail/dayCount", dayCount);
        // stage.setRequestNodeValueByXPath("/Envelope/Body/stageMassModifyTransactional/massModifyRequest/massModifyRoomDetail/ticketdetail/guestReference/age", guestReferenceAge);
        // stage.setRequestNodeValueByXPath("/Envelope/Body/stageMassModifyTransactional/massModifyRequest/massModifyRoomDetail/ticketdetail/guestReference/ageType", ageType);
        // stage.setRequestNodeValueByXPath("/Envelope/Body/stageMassModifyTransactional/massModifyRequest/massModifyRoomDetail/ticketdetail/guestReference/guest/firstName", ticketDetailGuestFirstName);
        // stage.setRequestNodeValueByXPath("/Envelope/Body/stageMassModifyTransactional/massModifyRequest/massModifyRoomDetail/ticketdetail/guestReference/guest/lastName", ticketDetailGuestLastName);
        // stage.setRequestNodeValueByXPath("/Envelope/Body/stageMassModifyTransactional/massModifyRequest/massModifyRoomDetail/ticketdetail/partOfPackage", partofPackage);
        // stage.sendRequest();

    }
}
