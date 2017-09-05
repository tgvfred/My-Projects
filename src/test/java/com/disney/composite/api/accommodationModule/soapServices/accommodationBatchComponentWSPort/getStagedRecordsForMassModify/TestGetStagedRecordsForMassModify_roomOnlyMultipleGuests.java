package com.disney.composite.api.accommodationModule.soapServices.accommodationBatchComponentWSPort.getStagedRecordsForMassModify;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationModule.accommodationBatchComponentWSPort.operation.GetStagedRecordsForMassModify;
import com.disney.api.soapServices.accommodationModule.accommodationBatchComponentWSPort.operation.StageMassModifyTransactional;
import com.disney.api.soapServices.accommodationModule.helpers.AccommodationBaseTest;
import com.disney.api.soapServices.accommodationModule.helpers.GetStagedRecordsForMassModifyHelper;
import com.disney.utils.Environment;
import com.disney.utils.Randomness;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.database.Database;
import com.disney.utils.dataFactory.database.Recordset;
import com.disney.utils.dataFactory.database.databaseImpl.OracleDatabase;

public class TestGetStagedRecordsForMassModify_roomOnlyMultipleGuests extends AccommodationBaseTest {
    private String assignOwner = null;

    @Override
    @BeforeMethod(alwaysRun = true)
    @Parameters("environment")
    public void setup(String environment) {
        setEnvironment(environment);
        isComo.set("false");
        setValues();
        setDaysOut(0);
        setNights(1);
        setArrivalDate(getDaysOut());
        setDepartureDate(getNights());
        setAddGuest(true);
        bookReservation();
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
    }

    @Test(groups = { "api", "regression", "getStagedRecordsForMassModify", "accommodation" })
    public void testGetStagedRecordsForMassModify_roomOnlyMultipleGuests() {
        String processName = "MASS_MODIFY";
        String tcId = getBook().getTravelComponentId();
        String tpsId = getBook().getTravelPlanSegmentId();
        String tcgId = getBook().getTravelComponentGroupingId();
        String firstName = getHouseHold().primaryGuest().getFirstName();
        String lastName = getHouseHold().primaryGuest().getLastName();
        String confirmationIndicator = "true";
        String adultTicket = "true";
        String hardTicketedEvent = "false";
        String baseAdmissionProductId = "2640488";
        String componentId = "0";
        String dayCount = "2";
        String guestReferenceAge = "2" + Randomness.randomNumber(1);
        String ageType = "ADULT";
        String ticketDetailGuestFirstName = getHouseHold().primaryGuest().getFirstName();
        String ticketDetailGuestLastName = getHouseHold().primaryGuest().getLastName();
        String partofPackage = "false";

        StageMassModifyTransactional stage = new StageMassModifyTransactional(environment, "MainProcLst");
        stage.setProcessName(processName);
        stage.setMassModifyRoomDetailTcId(tcId);
        stage.setMassModifyRoomDetailTpsId(tpsId);
        stage.setMassModifyRoomDetailTcgID(tcgId);
        stage.setMassModifyRoomDetailPrimaryGuestDetailFirstName(firstName);
        stage.setMassModifyRoomDetailPrimaryGuestDetailLastName(lastName);
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

        TestReporter.logStep("Retrieve staged record for Mass Modify");
        GetStagedRecordsForMassModify mod = new GetStagedRecordsForMassModify(environment);
        mod.setProcessDataId(rs.getValue("GRP_RES_PROC_RUN_ID"));
        mod.sendRequest();

        TestReporter.logAPI(!mod.getResponseStatusCode().equals("200"), "Error sending request", mod);

        TestReporter.softAssertEquals(mod.getFirstName(), firstName, "Verify that the retrieved first name [" + mod.getFirstName() + "] matches the expected [" + firstName + "]");
        TestReporter.softAssertEquals(mod.getLastName(), lastName, "Verify that the retrieved last name [" + mod.getLastName() + "] matches the expected [" + lastName + "]");
        TestReporter.softAssertEquals(mod.getTicketGuestFirstName(), ticketDetailGuestFirstName, "Verify that the retrieved ticket guest first name [" + mod.getTicketGuestFirstName() + "] matches the expected [" + ticketDetailGuestFirstName + "]");
        TestReporter.softAssertEquals(mod.getTicketGuestLastName(), ticketDetailGuestLastName, "Verify that the retrieved ticket guest last name [" + mod.getTicketGuestLastName() + "] matches the expected [" + ticketDetailGuestLastName + "]");
        TestReporter.softAssertEquals(mod.getNumberOfResponseNodesByXPath("//roomDetail/roomReservationDetail/guestReferenceDetails"), 2, "Verify that the number of response guests [" + mod.getNumberOfResponseNodesByXPath("//roomDetail/roomReservationDetail/guestReferenceDetails") + "] is that which is expected [2]");
        TestReporter.assertAll();

        validations();
    }

    private void validations() {
        GetStagedRecordsForMassModifyHelper helper = new GetStagedRecordsForMassModifyHelper(Environment.getBaseEnvironmentName(getEnvironment()), this);
        helper.validateNumberOfTcs(1);
        helper.validateReservationDetails("NULL", "NULL", "NULL", "N", getArrivalDate(), getDepartureDate(), assignOwner, false);
    }
}
