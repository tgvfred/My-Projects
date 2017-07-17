package com.disney.composite.api.accommodationModule.soapServices.accommodationSalesServicePort.retrieveResortReservations;

import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.BaseTest;
import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.RetrieveResortReservations;
import com.disney.utils.Environment;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.database.Database;
import com.disney.utils.dataFactory.database.Recordset;
import com.disney.utils.dataFactory.database.databaseImpl.OracleDatabase;

public class TestRetrieveResortReservations_Positive extends BaseTest {
    private static OracleDatabase db;

    @BeforeClass
    @Parameters("environment")
    public void beforeClass(String environment) {
        db = new OracleDatabase(this.environment = environment, Database.DREAMS);
    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesService", "retrieveResortReservations", "smoke" })
    public void testRetrieveResortReservations_Smoke() {
        TestReporter.logScenario("Test - Retrieve Resort Reservations - Smoke");
        validateSpecialEnvironment(buildAndSendRequestAndValidateSoapResponse("470181081988"));
    }

    @Test(dependsOnMethods = "testRetrieveResortReservations_Smoke", groups = { "api", "regression", "accommodation", "accommodationSalesService", "retrieveResortReservations" })
    public void testRetrieveResortReservations_RoomOnlyReservation() {
        TestReporter.logScenario("Test - Retrieve Resort Reservations - Room Only Reservation");
        String tpsID = getTPSIdForQuery("select b.tps_id from res_mgmt.tps a "
                + "join res_mgmt.tc_grp b on a.tps_id = b.tps_id "
                + "join res_mgmt.tc c on b.tc_grp_nb = c.tc_grp_nb "
                + "and a.TRVL_STS_NM not in ('Auto Cancelled','Cancelled','Past Visit', 'No Show') "
                + "and c.TRVL_STS_NM not in ('Auto Cancelled','Cancelled','Past Visit', 'No Show', 'DF Checked Out', 'Not Arrived') "
                + "and c.TC_TYP_NM = 'AccommodationComponent' "
                + "and c.blk_cd is null "
                + "and a.tps_secur_vl != 'DVC' "
                + "where rownum < 100"
                + "order by dbms_random.value");

        RetrieveResortReservations retrieveResortReservations = buildAndSendRequestAndValidateSoapResponse(tpsID);
        TestReporter.assertEquals("Room Only", retrieveResortReservations.getPackagePlanType(), "The package plan type was 'Room Only' as expected.");
        validateSpecialEnvironment(retrieveResortReservations);
    }

    @Test(dependsOnMethods = "testRetrieveResortReservations_Smoke", groups = { "api", "regression", "accommodation", "accommodationSalesService", "retrieveResortReservations" })
    public void testRetrieveResortReservations_GroupReservation() {
        TestReporter.logScenario("Test - Retrieve Resort Reservations - Group Reservation");
        String tpsID = getTPSIdForQuery("select b.tps_id from res_mgmt.tps a "
                + "join res_mgmt.tc_grp b on a.tps_id = b.tps_id "
                + "join res_mgmt.tc c on b.tc_grp_nb = c.tc_grp_nb "
                + "and a.TRVL_STS_NM not in ('Auto Cancelled','Cancelled','Past Visit', 'No Show') "
                + "and c.TRVL_STS_NM not in ('Auto Cancelled','Cancelled','Past Visit', 'No Show', 'DF Checked Out', 'Not Arrived') "
                + "and c.TC_TYP_NM = 'AccommodationComponent' "
                + "and c.blk_cd is not null "
                + "and a.tps_secur_vl != 'DVC' "
                + "where rownum < 100"
                + "order by dbms_random.value");

        RetrieveResortReservations retrieveResortReservations = buildAndSendRequestAndValidateSoapResponse(tpsID);
        validateSpecialEnvironment(retrieveResortReservations);
    }

    @Test(dependsOnMethods = "testRetrieveResortReservations_Smoke", groups = { "api", "regression", "accommodation", "accommodationSalesService", "retrieveResortReservations" })
    public void testRetrieveResortReservations_MultipleGuests() {
        TestReporter.logScenario("Test - Retrieve Resort Reservations - Multiple Guests");
        String tpsID = getTPSIdForQuery("select z.tps_id "
                + "from res_mgmt.tps z "
                + "where z.tp_id in "
                + "(select a.tp_id "
                + "from res_mgmt.tps a "
                + "join res_mgmt.tc_grp b on a.tps_id = b.tps_id "
                + "join res_mgmt.tc c on b.tc_grp_nb = c.tc_grp_nb "
                + "join res_mgmt.tp_pty d on a.tp_id = d.tp_id "
                + "join guest.TXN_PTY_EXTNL_REF e on d.TXN_PTY_ID = e.TXN_PTY_ID "
                + "where a.TRVL_STS_NM = 'Booked' "
                + "and c.TRVL_STS_NM = 'Booked' "
                + "and c.TC_TYP_NM = 'AccommodationComponent' "
                + "and c.blk_cd is null "
                + "group by a.tp_id "
                + "having count(*) >=2) "
                + "and rownum = 1");

        RetrieveResortReservations retrieveResortReservations = buildAndSendRequestAndValidateSoapResponse(tpsID);
        TestReporter.assertTrue(retrieveResortReservations.getNumberOfPartyRoles() > 1, "There is more than one guest in the resort reservation.");
        validateSpecialEnvironment(retrieveResortReservations);
    }

    @Test(dependsOnMethods = "testRetrieveResortReservations_Smoke", groups = { "api", "regression", "accommodation", "accommodationSalesService", "retrieveResortReservations" })
    public void testRetrieveResortReservations_NonZeroVip() {
        TestReporter.logScenario("Test - Retrieve Resort Reservations - Non Zero Vip");
        String tpsID = getTPSIdForQuery("select b.tps_id from res_mgmt.tps a "
                + "join res_mgmt.tc_grp b on a.tps_id = b.tps_id "
                + "join res_mgmt.tc c on b.tc_grp_nb = c.tc_grp_nb "
                + "and a.TRVL_STS_NM not in ('Auto Cancelled','Cancelled','Past Visit', 'No Show') "
                + "and c.TRVL_STS_NM not in ('Auto Cancelled','Cancelled','Past Visit', 'No Show', 'DF Checked Out', 'Not Arrived') "
                + "and c.TC_TYP_NM = 'AccommodationComponent' "
                + "and VIP_LVL_NM != '0' "
                + "where rownum < 100"
                + "order by dbms_random.value");

        RetrieveResortReservations retrieveResortReservations = buildAndSendRequestAndValidateSoapResponse(tpsID);
        TestReporter.assertGreaterThanZero(Integer.parseInt(retrieveResortReservations.getVipLevel()));
        validateSpecialEnvironment(retrieveResortReservations);
    }

    @Test(dependsOnMethods = "testRetrieveResortReservations_Smoke", groups = { "api", "regression", "accommodation", "accommodationSalesService", "retrieveResortReservations" })
    public void testRetrieveResortReservations_CheckedIn() {
        TestReporter.logScenario("Test - Retrieve Resort Reservations - Checked In");
        String tpsID = getTPSIdForQuery("select b.tps_id from res_mgmt.tps a "
                + "join res_mgmt.tc_grp b on a.tps_id = b.tps_id "
                + "join res_mgmt.tc c on b.tc_grp_nb = c.tc_grp_nb "
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

    @Test(dependsOnMethods = "testRetrieveResortReservations_Smoke", groups = { "api", "regression", "accommodation", "accommodationSalesService", "retrieveResortReservations" })
    public void testRetrieveResortReservations_Booked() {
        TestReporter.logScenario("Test - Retrieve Resort Reservations - Booked");
        String tpsID = getTPSIdForQuery("select b.tps_id from res_mgmt.tps a "
                + "join res_mgmt.tc_grp b on a.tps_id = b.tps_id "
                + "join res_mgmt.tc c on b.tc_grp_nb = c.tc_grp_nb "
                + "and a.TRVL_STS_NM = 'Booked' "
                + "and c.tc_typ_nm = 'AccommodationComponent' "
                + "where rownum < 100"
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
        TestReporter.logAPI(!retrieveResortReservations.getResponseStatusCode().equals("200"), "The response was not successful.", retrieveResortReservations);
        TestReporter.assertEquals(tpsID, retrieveResortReservations.getTravelPlanSegmentId(), "The response contains the correct TPS ID.");

        // Database validation
        Recordset results = new Recordset(db.getResultSet("SELECT COUNT(*) FROM RES_MGMT.TPS a "
                + "JOIN RES_MGMT.TC_GRP b ON a.TPS_ID = b.TPS_ID "
                + "WHERE a.TPS_ID = " + tpsID + " "
                + "AND a.TP_ID = " + retrieveResortReservations.getTravelPlanId() + " "
                + "AND a.TPS_ARVL_DT = '" + retrieveResortReservations.getResortStartDate() + "' "
                + "AND a.TPS_DPRT_DT = '" + retrieveResortReservations.getResortEndDate() + "' "
                + "AND b.TC_GRP_NB = " + retrieveResortReservations.getTravelComponentGroupingId()));
        TestReporter.assertTrue(results.getRowCount() > 0 && Integer.parseInt(results.getValue(1, 1)) > 0, "Response data was found in the database.");

        return retrieveResortReservations;
    }

    private void validateSpecialEnvironment(RetrieveResortReservations retrieveResortReservations) {
        if (Environment.isSpecialEnvironment(environment) || isComo.equals("true")) {
            RetrieveResortReservations retrieveResortReservationsBaseLine = (RetrieveResortReservations) retrieveResortReservations.clone();
            retrieveResortReservationsBaseLine.setEnvironment(Environment.getBaseEnvironmentName(environment));
            retrieveResortReservationsBaseLine.sendRequest();
            TestReporter.assertTrue(retrieveResortReservations.validateResponseNodeQuantity(retrieveResortReservationsBaseLine, true), "Response Node Validation Result");
        }
    }
}