package com.disney.composite.api.accommodationModule.soapServices.accommodationBatchComponentWSPort.getStagedRecordsForMassModify;

import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationModule.accommodationBatchComponentWSPort.operation.GetStagedRecordsForMassModify;
import com.disney.api.soapServices.accommodationModule.accommodationBatchComponentWSPort.operation.StageMassModifyTransactional;
import com.disney.api.soapServices.accommodationModule.helpers.AccommodationBaseTest;
import com.disney.utils.Randomness;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.database.Database;
import com.disney.utils.dataFactory.database.Recordset;
import com.disney.utils.dataFactory.database.databaseImpl.OracleDatabase;

public class TestGetStagedRecordsForMassModify_roomOnlyMultipleGuests extends AccommodationBaseTest {

    @Test(groups = { "api", "regression", "getStagedRecordsForMassModify", "accommodation" })
    public void testGetStagedRecordsForMassModify_roomOnlyMultipleGuests() {

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
        String guestReferenceAge = "2" + Randomness.randomNumber(1);
        String ageType = "ADULT";
        String ticketDetailGuestFirstName = Randomness.randomAlphaNumeric(4);
        String ticketDetailGuestLastName = Randomness.randomAlphaNumeric(4);
        String partofPackage = "false";

        StageMassModifyTransactional stage = new StageMassModifyTransactional(environment, "MainProcLst");
        stage.setProcessName(processName);
        stage.setMassModifyRoomDetailTcId(tcId);
        stage.setMassModifyRoomDetailTpsId(tpsId);
        stage.setMassModifyRoomDetailTcgID(tcgId);
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
        stage.setMassModifyRoomDetailConfirmationIndicator(confirmationIndicator);
        stage.setAdultTicket(adultTicket);
        stage.setHardTicketedEvent(hardTicketedEvent);
        stage.setBaseAdmissionProductId(baseAdmissionProductId);
        stage.setCode(baseAdmissionProductId);
        stage.setComponentId(componentId);
        stage.setDayCount(dayCount);
        stage.setAge(guestReferenceAge);
        stage.setTicketGuestFirstName(ticketDetailGuestFirstName);
        stage.setTicketGuestLastName(ticketDetailGuestLastName);
        stage.setComponentId(componentId);
        stage.setDayCount(dayCount);
        stage.setAgeType(ageType);
        stage.setPartOfPackage(partofPackage);
        stage.sendRequest();

        TestReporter.logAPI(!stage.getResponseStatusCode().equals("200"), "Error sending request", stage);

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
        TestReporter.softAssertEquals(mod.getTicketGuestFirstName(), ticketDetailGuestFirstName, "Verify that the retrieved ticket guest first name [" + mod.getTicketGuestFirstName() + "] matches the expected [" + ticketDetailGuestFirstName + "]");
        TestReporter.softAssertEquals(mod.getTicketGuestLastName(), ticketDetailGuestLastName, "Verify that the retrieved ticket guest last name [" + mod.getTicketGuestLastName() + "] matches the expected [" + ticketDetailGuestLastName + "]");
        TestReporter.assertAll();

    }
}
