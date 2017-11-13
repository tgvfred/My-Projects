package com.disney.composite.api.accommodationModule.soapServices.accommodationSalesServicePort.retrieveResortReservations;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.RetrieveResortReservations;
import com.disney.api.soapServices.accommodationModule.helpers.AccommodationBaseTest;
import com.disney.utils.Environment;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.database.Database;
import com.disney.utils.dataFactory.database.Recordset;
import com.disney.utils.dataFactory.database.databaseImpl.OracleDatabase;

public class TestRetrieveResortReservations_Positive extends AccommodationBaseTest {
    private static OracleDatabase db;

    @BeforeClass
    @Parameters("environment")
    public void beforeClass(String environment) {
        db = new OracleDatabase(environment, Database.DREAMS);
    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesService", "retrieveResortReservations", "smoke" })
    public void testRetrieveResortReservations_RoomOnlyReservation() {
        TestReporter.logScenario("Test - Retrieve Resort Reservations - Room Only Reservation");
        String tpsID = getTPSIdForQuery("select b.tps_id from res_mgmt.tps a "
                + "join res_mgmt.tc_grp b on a.tps_id = b.tps_id "
                + "join res_mgmt.tc c on b.tc_grp_nb = c.tc_grp_nb "
                + "join res_mgmt.acm_cmpnt d on c.tc_id = d.acm_tc_id "
                + "and a.TRVL_STS_NM not in ('Auto Cancelled','Cancelled','Past Visit', 'No Show') "
                + "and c.TRVL_STS_NM not in ('Auto Cancelled','Cancelled','Past Visit', 'No Show', 'DF Checked Out', 'Not Arrived') "
                + "and c.TC_TYP_NM = 'AccommodationComponent' "
                + "and c.blk_cd is null "
                + "where rownum <= 100 "
                + "order by dbms_random.value");

        RetrieveResortReservations retrieveResortReservations = buildAndSendRequestAndValidateSoapResponse(tpsID);
        TestReporter.assertEquals("Room Only", retrieveResortReservations.getPackagePlanType(), "The package plan type was 'Room Only' as expected.");
        validateSpecialEnvironment(retrieveResortReservations);
    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesService", "retrieveResortReservations" })
    public void testRetrieveResortReservations_GroupReservation() {
        setEnvironment(environment);
        setDaysOut(0);
        setNights(1);
        setArrivalDate(getDaysOut());
        setDepartureDate(getDaysOut() + getNights());
        setValues(getEnvironment());
        setIsWdtcBooking(true);
        bookReservation();

        TestReporter.logScenario("Test - Retrieve Resort Reservations - Group Reservation");

        RetrieveResortReservations retrieveResortReservations = buildAndSendRequestAndValidateSoapResponse(getBook().getTravelPlanSegmentId());
        validateSpecialEnvironment(retrieveResortReservations);
    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesService", "retrieveResortReservations", "debug" })
    public void testRetrieveResortReservations_MultipleGuests() {
        setEnvironment(environment);
        setDaysOut(0);
        setNights(1);
        setArrivalDate(getDaysOut());
        setDepartureDate(getDaysOut() + getNights());
        setValues(getEnvironment());
        setIsWdtcBooking(true);
        setAddNewGuest(true);
        bookReservation();

        TestReporter.logScenario("Test - Retrieve Resort Reservations - Multiple Guests");

        RetrieveResortReservations retrieveResortReservations = buildAndSendRequestAndValidateSoapResponse(getBook().getTravelPlanSegmentId());
        TestReporter.assertTrue(retrieveResortReservations.getNumberOfPartyRoles() > 1, "There is more than one guest in the resort reservation.");
        validateSpecialEnvironment(retrieveResortReservations);
    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesService", "retrieveResortReservations" })
    public void testRetrieveResortReservations_NonZeroVip() {
        TestReporter.logScenario("Test - Retrieve Resort Reservations - Non Zero Vip");
        String tpsID = getTPSIdForQuery("select b.tps_id from res_mgmt.tps a "
                + "join res_mgmt.tc_grp b on a.tps_id = b.tps_id "
                + "join res_mgmt.tc c on b.tc_grp_nb = c.tc_grp_nb "
                + "join res_mgmt.acm_cmpnt d on c.tc_id = d.acm_tc_id "
                + "and a.TRVL_STS_NM not in ('Auto Cancelled','Cancelled','Past Visit', 'No Show') "
                + "and c.TRVL_STS_NM not in ('Auto Cancelled','Cancelled','Past Visit', 'No Show', 'DF Checked Out', 'Not Arrived') "
                + "and c.TC_TYP_NM = 'AccommodationComponent' "
                + "and VIP_LVL_NM != '0' "
                + "where rownum <= 100 "
                + "order by dbms_random.value");

        RetrieveResortReservations retrieveResortReservations = buildAndSendRequestAndValidateSoapResponse(tpsID);
        TestReporter.assertGreaterThanZero(Integer.parseInt(retrieveResortReservations.getVipLevel()));
        validateSpecialEnvironment(retrieveResortReservations);
    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesService", "retrieveResortReservations" })
    public void testRetrieveResortReservations_CheckedIn() {
        TestReporter.logScenario("Test - Retrieve Resort Reservations - Checked In");
        String tpsID = getTPSIdForQuery("select b.tps_id from res_mgmt.tps a "
                + "join res_mgmt.tc_grp b on a.tps_id = b.tps_id "
                + "join res_mgmt.tc c on b.tc_grp_nb = c.tc_grp_nb "
                + "join res_mgmt.acm_cmpnt d on c.tc_id = d.acm_tc_id "
                + "and a.TRVL_STS_NM = 'Checked In' "
                + "and c.TRVL_STS_NM not in ('Auto Cancelled','Cancelled','Past Visit', 'No Show', 'DF Checked Out', 'Not Arrived') "
                + "and c.tc_typ_nm = 'AccommodationComponent' "
                + "where rownum < 100 "
                + "order by dbms_random.value ");

        RetrieveResortReservations retrieveResortReservations = buildAndSendRequestAndValidateSoapResponse(tpsID);

        Recordset results = new Recordset(db.getResultSet("SELECT TRVL_STS_NM FROM RES_MGMT.TC "
                + "WHERE TC_TYP_NM = 'AccommodationComponent' "
                + "AND TC_GRP_NB = " + retrieveResortReservations.getTravelComponentGroupingId()));

        String expectedStatus = "Checked In";
        for (results.moveFirst(); results.hasNext(); results.moveNext()) {
            if (results.getValue("TRVL_STS_NM") != "Checked In") {
                expectedStatus = "Booked";
                break;
            }
        }

        TestReporter.assertEquals(expectedStatus, retrieveResortReservations.getReservationStatus(), "The reservation status was '" + expectedStatus + "' as expected.");
        validateSpecialEnvironment(retrieveResortReservations);
    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesService", "retrieveResortReservations" })
    public void testRetrieveResortReservations_Booked() {
        TestReporter.logScenario("Test - Retrieve Resort Reservations - Booked");
        String tpsID = getTPSIdForQuery("select b.tps_id from res_mgmt.tps a "
                + "join res_mgmt.tc_grp b on a.tps_id = b.tps_id "
                + "join res_mgmt.tc c on b.tc_grp_nb = c.tc_grp_nb "
                + "join res_mgmt.acm_cmpnt d on c.tc_id = d.acm_tc_id "
                + "and a.TRVL_STS_NM = 'Booked' "
                + "and c.tc_typ_nm = 'AccommodationComponent' "
                + "where rownum < 100 "
                + "order by dbms_random.value");

        RetrieveResortReservations retrieveResortReservations = buildAndSendRequestAndValidateSoapResponse(tpsID);
        TestReporter.assertEquals("Booked", retrieveResortReservations.getReservationStatus(), "The reservation status was 'Booked' as expected.");
        validateSpecialEnvironment(retrieveResortReservations);
    }

    /*
     * Utility Functions
     */
    private String getTPSIdForQuery(String query) {
        Recordset results = new Recordset(db.getResultSet(query));
        TestReporter.assertTrue(results.getRowCount() > 0 && Arrays.asList(results.getArray()[0]).contains("TPS_ID"), "The SQL Query returned a TPS ID");
        return results.getValue("TPS_ID", ThreadLocalRandom.current().nextInt(results.getRowCount()) + 1);
    }

    private RetrieveResortReservations buildAndSendRequestAndValidateSoapResponse(String tpsID) {
        // Build and send request
        RetrieveResortReservations retrieveResortReservations = new RetrieveResortReservations(environment);
        retrieveResortReservations.setReservationNumber(tpsID);
        retrieveResortReservations.sendRequest();

        // Response validation
        TestReporter.logAPI(!retrieveResortReservations.getResponseStatusCode().equals("200"), "The response was not successful: " + retrieveResortReservations.getFaultString(), retrieveResortReservations);
        TestReporter.assertEquals(tpsID, retrieveResortReservations.getTravelPlanSegmentId(), "The response contains the correct TPS ID.");

        // Database validation
        Recordset results;
        results = new Recordset(db.getResultSet("SELECT COUNT(*) FROM RES_MGMT.TPS a "
                + "JOIN RES_MGMT.TC_GRP b ON a.TPS_ID = b.TPS_ID "
                + "WHERE a.TPS_ID = " + tpsID + " "
                + "AND a.TP_ID = " + retrieveResortReservations.getTravelPlanId() + " "
                + "AND a.TPS_ARVL_DT = '" + retrieveResortReservations.getResortStartDate() + "' "
                + "AND a.TPS_DPRT_DT = '" + retrieveResortReservations.getResortEndDate() + "' "
                + "AND b.TC_GRP_NB = " + retrieveResortReservations.getTravelComponentGroupingId()));
        TestReporter.assertTrue(results.getRowCount() > 0 && Integer.parseInt(results.getValue(1, 1)) > 0, "Response data was found in the database.");

        // Guest validation
        results = new Recordset(db.getResultSet("SELECT a.IDVL_FST_NM, a.IDVL_LST_NM, a.TXN_IDVL_PTY_ID FROM GUEST.TXN_IDVL_PTY a "
                + "JOIN RES_MGMT.TC_GST b ON a.TXN_IDVL_PTY_ID = b.TXN_IDVL_PTY_ID "
                + "JOIN RES_MGMT.TC c ON b.TC_ID = c.TC_ID "
                + "WHERE c.TC_GRP_NB = " + retrieveResortReservations.getTravelComponentGroupingId()));

        Map<Integer, String> mappedResults = new HashMap<>();
        for (results.moveFirst(); results.hasNext(); results.moveNext()) {
            Integer ID = Integer.parseInt(results.getValue("TXN_IDVL_PTY_ID").trim());
            String name = (results.getValue("IDVL_FST_NM").trim() + " " + results.getValue("IDVL_LST_NM").trim()).toUpperCase();
            mappedResults.put(ID, name);
        }
        TestReporter.assertEquals(mappedResults, retrieveResortReservations.getPartyRoleGuests(), "The guests in the response matched the guests in the database.");

        results = new Recordset(db.getResultSet("SELECT a.TXN_IDVL_PTY_ID FROM GUEST.TXN_IDVL_PTY a "
                + "JOIN RES_MGMT.TP_PTY b ON a.TXN_IDVL_PTY_ID = b.TXN_PTY_ID "
                + "WHERE b.PRMY_PTY_IN = 'Y' "
                + "AND b.TP_ID = " + retrieveResortReservations.getTravelPlanId()));
        TestReporter.assertEquals(results.getValue("TXN_IDVL_PTY_ID"), retrieveResortReservations.getPrimaryGuest().getKey().toString(), "The primary guest in the response matched the one in the database.");

        // Room Type validation
        results = new Recordset(db.getResultSet("select c.RSRC_INVTRY_TYP_CD from res_mgmt.tc a "
                + "join rsrc_inv.RSRC_ASGN_OWNR b on a.ASGN_OWN_ID = b.ASGN_OWNR_ID "
                + "join rsrc_inv.RSRC_INVTRY_TYP c on b.RSRC_INVTRY_TYP_ID = c.RSRC_INVTRY_TYP_ID "
                + "where a.tc_grp_nb = " + retrieveResortReservations.getTravelComponentGroupingId()));
        TestReporter.assertTrue(results.getRowCount() > 0 && retrieveResortReservations.getRoomTypeCode().equals(results.getValue("RSRC_INVTRY_TYP_CD")), "The room type code in the response matched the one in the database.");

        // Package Product ID validation
        results = new Recordset(db.getResultSet("select plan_typ_nm from pricing.pkg where pkg_id = " + retrieveResortReservations.getPackageProductId()));
        TestReporter.assertEquals(results.getValue("plan_typ_nm"), retrieveResortReservations.getPackagePlanType(), "The Package Product ID in the response matched the one in the database.");

        return retrieveResortReservations;
    }

    private void validateSpecialEnvironment(RetrieveResortReservations retrieveResortReservations) {
        if (Environment.isSpecialEnvironment(environment) || isComo.equals("true")) {
            RetrieveResortReservations retrieveResortReservationsBaseLine = (RetrieveResortReservations) retrieveResortReservations.clone();
            retrieveResortReservationsBaseLine.setEnvironment(Environment.getBaseEnvironmentName(environment));
            retrieveResortReservationsBaseLine.sendRequest();

            retrieveResortReservations.addExcludedXpathValidations("/Envelope/Body/retrieveResortReservationsResponse/resortReservations/partyRoles/role");
            TestReporter.assertTrue(retrieveResortReservations.validateResponseNodeQuantity(retrieveResortReservationsBaseLine, true), "Response Node Validation Result");
        }
    }
}