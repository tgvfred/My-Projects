package com.disney.api.soapServices.accommodationModule.helpers;

import com.disney.AutomationException;
import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.ReplaceAllForTravelPlanSegment;
import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.Retrieve;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.database.Database;
import com.disney.utils.dataFactory.database.Recordset;
import com.disney.utils.dataFactory.database.databaseImpl.OracleDatabase;

public class RetrieveHelper {

    public void baseValidation(ReplaceAllForTravelPlanSegment book, Retrieve retrieve) {

        String guestfirstName = book.getResponseNodeValueByXPath("Envelope/Body/replaceAllForTravelPlanSegmentResponse/response/roomDetails/roomReservationDetail/guestReferenceDetails/guest/firstName");

        String guestlastName = book.getResponseNodeValueByXPath("Envelope/Body/replaceAllForTravelPlanSegmentResponse/response/roomDetails/roomReservationDetail/guestReferenceDetails/guest/lastName");

        String guestPhone = book.getResponseNodeValueByXPath("Envelope/Body/replaceAllForTravelPlanSegmentResponse/response/roomDetails/roomReservationDetail/guestReferenceDetails/guest/phoneDetails/number");

        String guestAddress = book.getResponseNodeValueByXPath("Envelope/Body/replaceAllForTravelPlanSegmentResponse/response/roomDetails/roomReservationDetail/guestReferenceDetails/guest/addressDetails/addressLine1");

        String guestEmail = book.getResponseNodeValueByXPath("Envelope/Body/replaceAllForTravelPlanSegmentResponse/response/roomDetails/roomReservationDetail/guestReferenceDetails/guest/emailDetails/address");

        String startPeriod = book.getResponseNodeValueByXPath("Envelope/Body/replaceAllForTravelPlanSegmentResponse/response/roomDetails/resortPeriod/startDate");

        String endPeriod = book.getResponseNodeValueByXPath("Envelope/Body/replaceAllForTravelPlanSegmentResponse/response/roomDetails/resortPeriod/endDate");

        String guestPartyId = book.getResponseNodeValueByXPath("Envelope/Body/replaceAllForTravelPlanSegmentResponse/response/roomDetails/roomReservationDetail/guestReferenceDetails/guest/partyId");

        String guestGuestId = book.getResponseNodeValueByXPath("Envelope/Body/replaceAllForTravelPlanSegmentResponse/response/roomDetails/roomReservationDetail/guestReferenceDetails/guest/guestId");

        // String pfirstName = book.getResponseNodeValueByXPath("Envelope/Body/replaceAllForTravelPlanSegmentResponse/response/roomDetails/roomReservationDetail/guestReferenceDetails/guest/guestId");
        //
        // String plastName = book.getResponseNodeValueByXPath("Envelope/Body/replaceAllForTravelPlanSegmentResponse/response/roomDetails/roomReservationDetail/guestReferenceDetails/guest/guestId");
        //
        // String pAddress = book.getResponseNodeValueByXPath("Envelope/Body/replaceAllForTravelPlanSegmentResponse/response/roomDetails/roomReservationDetail/guestReferenceDetails/guest/guestId");
        //
        // String pEmail = book.getResponseNodeValueByXPath("Envelope/Body/replaceAllForTravelPlanSegmentResponse/response/roomDetails/roomReservationDetail/guestReferenceDetails/guest/guestId");
        //
        // String pPartyId = book.getResponseNodeValueByXPath("Envelope/Body/replaceAllForTravelPlanSegmentResponse/response/roomDetails/roomReservationDetail/guestReferenceDetails/guest/guestId");
        //
        // String pGuestId = book.getResponseNodeValueByXPath("Envelope/Body/replaceAllForTravelPlanSegmentResponse/response/roomDetails/roomReservationDetail/guestReferenceDetails/guest/guestId");
        //
        // String roomReadyNotificationInformation = book.getResponseNodeValueByXPath("Envelope/Body/replaceAllForTravelPlanSegmentResponse/response/roomDetails/travelStatus");

        String travelStatus = book.getResponseNodeValueByXPath("Envelope/Body/replaceAllForTravelPlanSegmentResponse/response/roomDetails/travelStatus");

        TestReporter.assertEquals(guestfirstName, retrieve.getFirstName(), "Verify the first name [" + retrieve.getFirstName() + "] matches the expected [" + guestfirstName + "]");
        TestReporter.assertEquals(guestlastName, retrieve.getLastName(), "Verify the last name [" + retrieve.getLastName() + "] matches the expected [" + guestlastName + "]");
        TestReporter.assertEquals(guestPhone, retrieve.getPhone(), "Verify the guest phone [" + retrieve.getPhone() + "] matches the expected [" + guestPhone + "]");
        TestReporter.assertEquals(guestAddress, retrieve.getAddress(), "Verify the guest address [" + retrieve.getAddress() + "] matches the expected [" + guestAddress + "]");
        TestReporter.assertEquals(guestEmail, retrieve.getEmail(), "Verify the email [" + retrieve.getEmail() + "] matches the expected [" + guestEmail + "]");

        TestReporter.assertEquals(startPeriod, retrieve.getPeriodSD(), "Verify the period start date [" + retrieve.getPeriodSD() + "] matches the expected [" + startPeriod + "]");
        TestReporter.assertEquals(endPeriod, retrieve.getPeriodED(), "Verify the period end date [" + retrieve.getPeriodED() + "] matches the expected [" + endPeriod + "]");
        TestReporter.assertNotNull(retrieve.getPartyId(), "Verify the party id is in the response [" + retrieve.getPartyId() + "].");

        TestReporter.assertNotNull(retrieve.getPPFirstName(), "Verify the primary party first name is in response [" + retrieve.getPPFirstName() + "].");
        TestReporter.assertNotNull(retrieve.getPPLastName(), "Verify the primary party last name  is in response [" + retrieve.getPPLastName() + "].");
        TestReporter.assertNotNull(retrieve.getPPPhone(), "Verify the primary party phone is in response [" + retrieve.getPPPhone() + "].");
        TestReporter.assertNotNull(retrieve.getPPAddress(), "Verify the primary party address is in response [" + retrieve.getPPAddress() + "].");
        TestReporter.assertNotNull(retrieve.getPPEmail(), "Verify the primary party email is in response [" + retrieve.getPPEmail() + "].");

        TestReporter.assertNotNull(retrieve.getRoomReadyNotificationInfoTP(), "Verify the room ready notification information travel plan id is in response [" + retrieve.getPartyId() + "].");

        TestReporter.assertNotNull(retrieve.getRoomReadyNotificationInfoRequired(), "Verify the room ready notification information required in response [" + retrieve.getPartyId() + "].");

        TestReporter.assertEquals(guestGuestId, retrieve.getGuestId(), "Verify the guest id [" + retrieve.getGuestId() + "] matches the expected [" + guestGuestId + "]");

        TestReporter.assertEquals(travelStatus, retrieve.getTravelStatus(), "Verify the travel status [" + retrieve.getTravelStatus() + "] matches the expected [" + travelStatus + "]");

    }

    public void ticketValidation(Retrieve retrieve) {

        TestReporter.assertNotNull(retrieve.getTicketStatus(), "Verify the ticket details status is in response [" + retrieve.getTicketStatus() + "].");

        TestReporter.assertNotNull(retrieve.getTicketGuestId(), "Verify the ticket details guest id is in response [" + retrieve.getTicketGuestId() + "].");

        TestReporter.assertNotNull(retrieve.getTicketAgeType(), "Verify the ticket details age type is in the response [" + retrieve.getTicketAgeType() + "].");

        TestReporter.assertNotNull(retrieve.getTicketComponentId(), "Verify the ticket details component id is in the response[" + retrieve.getTicketComponentId() + "].");

        TestReporter.assertNotNull(retrieve.getTicketCode(), "Verify the ticket details ticket code is in the response [" + retrieve.getTicketCode() + "].");

    }

    public void sqlGuest_PartyId(String env, String tp) {

        String sql = "select a.TXN_PTY_ID GUEST_ID, b.TXN_PTY_EXTNL_REF_VAL PARTY_ID"
                + " from res_mgmt.tp_pty a"
                + " join guest.TXN_PTY_EXTNL_REF b on a.TXN_PTY_ID = b.TXN_PTY_ID"
                + " where a.tp_id = '" + tp + "'";

        Database db = new OracleDatabase(env, Database.DREAMS);
        Recordset rs = new Recordset(db.getResultSet(sql));

        if (rs.getRowCount() == 0) {
            throw new AutomationException("No records were returned. SQL: [" + sql + "].");
        }

        TestReporter.assertTrue(!rs.getValue("GUEST_ID", 1).equals(""), "The guest id  is [" + rs.getValue("GUEST_ID", 1) + "]");

        TestReporter.assertTrue(!rs.getValue("PARTY_ID", 1).equals(""), "The party id  is [" + rs.getValue("PARTY_ID", 1) + "]");
    }

    public void sqlConfirmationDetails(String env, String tps) {
        String sql = "select *"
                + " from res_mgmt.tps_cnfirm_rcpnt a"
                + " where a.tps_id = '" + tps + "'";

        Database db = new OracleDatabase(env, Database.DREAMS);
        Recordset rs = new Recordset(db.getResultSet(sql));
        rs.print();
        if (rs.getRowCount() == 0) {
            throw new AutomationException("No records were returned. SQL: [" + sql + "].");
        }

        TestReporter.assertTrue(!rs.getValue("GUEST_ID", 1).equals(""), "The guest id  is [" + rs.getValue("GUEST_ID", 1) + "]");

        TestReporter.assertTrue(!rs.getValue("PARTY_ID", 1).equals(""), "The party id  is [" + rs.getValue("PARTY_ID", 1) + "]");
    }

    public void sqlAdmissionComponentDetails(String env, String tcg) {
        String sql = "select b.ADM_TC_ID, b.ATS_TKT_CD"
                + " from res_mgmt.tc a"
                + " join res_mgmt.adm_cmpnt b on a.tc_id = b.adm_tc_id"
                + " where a.tc_grp_nb = '" + tcg + "'"
                + " and a.tc_typ_nm = 'AdmissionComponent'";

        Database db = new OracleDatabase(env, Database.DREAMS);
        Recordset rs = new Recordset(db.getResultSet(sql));

        if (rs.getRowCount() == 0) {
            throw new AutomationException("No records were returned. SQL: [" + sql + "].");
        }

        TestReporter.assertTrue(!rs.getValue("ADM_TC_ID", 1).equals(""), "The admission tc id  is [" + rs.getValue("ADM_TC_ID", 1) + "]");

        TestReporter.assertTrue(!rs.getValue("ATS_TKT_CD", 1).equals(""), "The ticket cd  is [" + rs.getValue("ATS_TKT_CD", 1) + "]");
    }

    public void sqlTPSDetails(String env, String tps) {
        String sql = "select *"
                + " from res_mgmt.tps a"
                + " where a.tps_id ='" + tps + "'";

        Database db = new OracleDatabase(env, Database.DREAMS);
        Recordset rs = new Recordset(db.getResultSet(sql));
        rs.print();
        if (rs.getRowCount() == 0) {
            throw new AutomationException("No records were returned. SQL: [" + sql + "].");
        }

        TestReporter.assertTrue(!rs.getValue("GUEST_ID", 1).equals(""), "The guest id  is [" + rs.getValue("GUEST_ID", 1) + "]");

        TestReporter.assertTrue(!rs.getValue("PARTY_ID", 1).equals(""), "The party id  is [" + rs.getValue("PARTY_ID", 1) + "]");
    }

    public void sqlTPSConfirmationDetails(String env, String tps) {
        String sql = "SELECT *"
                + " FROM RES_MGMT.TPS_CNFIRM_RCPNT a"
                + " LEFT OUTER JOIN GUEST.TXN_PTY_EML_LCTR c ON a.LCTR_ID = c.TXN_PTY_EML_LCTR_ID"
                + " WHERE TPS_ID = '" + tps + "'";

        Database db = new OracleDatabase(env, Database.DREAMS);
        Recordset rs = new Recordset(db.getResultSet(sql));
        rs.print();
        if (rs.getRowCount() == 0) {
            throw new AutomationException("No records were returned. SQL: [" + sql + "].");
        }

        TestReporter.assertTrue(!rs.getValue("GUEST_ID", 1).equals(""), "The guest id  is [" + rs.getValue("GUEST_ID", 1) + "]");

        TestReporter.assertTrue(!rs.getValue("PARTY_ID", 1).equals(""), "The party id  is [" + rs.getValue("PARTY_ID", 1) + "]");
    }

    public void sqlPrimaryTPLinkedDVCReservation(String env, String tp) {
        String sql = "select *"
                + " from res_mgmt.tp a"
                + " join res_mgmt.tps b on a.tp_id = b.tp_id"
                + " where a.tp_id = '" + tp + "'";

        Database db = new OracleDatabase(env, Database.DREAMS);
        Recordset rs = new Recordset(db.getResultSet(sql));
        rs.print();
        if (rs.getRowCount() == 0) {
            throw new AutomationException("No records were returned. SQL: [" + sql + "].");
        }

        TestReporter.assertTrue(!rs.getValue("GUEST_ID", 1).equals(""), "The guest id  is [" + rs.getValue("GUEST_ID", 1) + "]");

        TestReporter.assertTrue(!rs.getValue("PARTY_ID", 1).equals(""), "The party id  is [" + rs.getValue("PARTY_ID", 1) + "]");
    }

}
