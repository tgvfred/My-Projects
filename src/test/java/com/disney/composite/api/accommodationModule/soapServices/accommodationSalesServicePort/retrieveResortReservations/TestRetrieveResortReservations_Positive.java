package com.disney.composite.api.accommodationModule.soapServices.accommodationSalesServicePort.retrieveResortReservations;

import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

import org.testng.annotations.Test;

import com.disney.api.BaseTest;
import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.RetrieveResortReservations;
import com.disney.utils.Environment;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.database.Database;
import com.disney.utils.dataFactory.database.Recordset;
import com.disney.utils.dataFactory.database.databaseImpl.OracleDatabase;

public class TestRetrieveResortReservations_Positive extends BaseTest {
    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesService", "retrieveResortReservations", "smoke" })
    public void testRetrieveResortReservations_Smoke() {
        TestReporter.logScenario("Test - Retrieve Resort Reservations - Smoke");
        validateSpecialEnvironment(sendRequestAndValidateSoapResponse("470181081988"));
    }

    @Test(dependsOnMethods = "testRetrieveResortReservations_Smoke", groups = { "api", "regression", "accommodation", "accommodationSalesService", "retrieveResortReservations" })
    public void testRetrieveResortReservations_RoomOnlyReservation() {
        TestReporter.logScenario("Test - Retrieve Resort Reservations - Room Only Reservation");
        String tpsID = getTPSIdForQuery("select b.tps_id "
                + "from res_mgmt.tps a "
                + "join res_mgmt.tc_grp b on a.tps_id = b.tps_id "
                + "join res_mgmt.tc c on b.tc_grp_nb = c.tc_grp_nb "
                + "and a.TRVL_STS_NM not in ('Auto Cancelled','Cancelled','Past Visit', 'No Show') "
                + "and c.TRVL_STS_NM not in ('Auto Cancelled','Cancelled','Past Visit', 'No Show', 'DF Checked Out') "
                + "and c.TC_TYP_NM = 'AccommodationComponent' "
                + "and c.blk_cd is null "
                + "and a.tps_secur_vl != 'DVC' "
                + "where rownum < 100"
                + "order by dbms_random.value");

        validateSpecialEnvironment(sendRequestAndValidateSoapResponse(tpsID));
    }

    @Test(dependsOnMethods = "testRetrieveResortReservations_Smoke", groups = { "api", "regression", "accommodation", "accommodationSalesService", "retrieveResortReservations" })
    public void testRetrieveResortReservations_GroupReservation() {
        TestReporter.logScenario("Test - Retrieve Resort Reservations - Group Reservation");
        String tpsID = getTPSIdForQuery("select b.tps_id "
                + "from res_mgmt.tps a "
                + "join res_mgmt.tc_grp b on a.tps_id = b.tps_id "
                + "join res_mgmt.tc c on b.tc_grp_nb = c.tc_grp_nb "
                + "and a.TRVL_STS_NM not in ('Auto Cancelled','Cancelled','Past Visit', 'No Show') "
                + "and c.TRVL_STS_NM not in ('Auto Cancelled','Cancelled','Past Visit', 'No Show', 'DF Checked Out') "
                + "and c.TC_TYP_NM = 'AccommodationComponent' "
                + "and c.blk_cd is not null "
                + "and a.tps_secur_vl != 'DVC' "
                + "where rownum < 100"
                + "order by dbms_random.value");

        validateSpecialEnvironment(sendRequestAndValidateSoapResponse(tpsID));
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
                + "where a.TRVL_STS_NM = 'Booked' "
                + "and c.TRVL_STS_NM = 'Booked' "
                + "and c.TC_TYP_NM = 'AccommodationComponent' "
                + "and c.blk_cd is null "
                + "group by a.tp_id "
                + "having count(*) >=2) "
                + "and rownum = 1");

        RetrieveResortReservations retrieveResortReservations = sendRequestAndValidateSoapResponse(tpsID);
        TestReporter.assertTrue(retrieveResortReservations.getNumberOfPartyRoles() > 1, "There is more than one guest in the response.");
        validateSpecialEnvironment(retrieveResortReservations);
    }

    @Test(dependsOnMethods = "testRetrieveResortReservations_Smoke", groups = { "api", "regression", "accommodation", "accommodationSalesService", "retrieveResortReservations" })
    public void testRetrieveResortReservations_NonZeroVip() {
        TestReporter.logScenario("Test - Retrieve Resort Reservations - Non Zero Vip");
        String tpsID = getTPSIdForQuery("select b.tps_id "
                + "from res_mgmt.tps a "
                + "join res_mgmt.tc_grp b on a.tps_id = b.tps_id "
                + "join res_mgmt.tc c on b.tc_grp_nb = c.tc_grp_nb "
                + "and a.TRVL_STS_NM not in ('Auto Cancelled','Cancelled','Past Visit', 'No Show') "
                + "and c.TRVL_STS_NM not in ('Auto Cancelled','Cancelled','Past Visit', 'No Show', 'DF Checked Out') "
                + "and c.TC_TYP_NM = 'AccommodationComponent' "
                + "and VIP_LVL_NM != '0' "
                + "where rownum < 100"
                + "order by dbms_random.value");

        validateSpecialEnvironment(sendRequestAndValidateSoapResponse(tpsID));
    }

    @Test(dependsOnMethods = "testRetrieveResortReservations_Smoke", groups = { "api", "regression", "accommodation", "accommodationSalesService", "retrieveResortReservations" })
    public void testRetrieveResortReservations_CheckedIn() {
        TestReporter.logScenario("Test - Retrieve Resort Reservations - Checked In");
        String tpsID = getTPSIdForQuery("select b.tps_id "
                + "from res_mgmt.tps a "
                + "join res_mgmt.tc_grp b on a.tps_id = b.tps_id "
                + "join res_mgmt.tc c on b.tc_grp_nb = c.tc_grp_nb "
                + "and a.TRVL_STS_NM = 'Checked In' "
                + "and c.tc_typ_nm = 'AccommodationComponent' "
                + "where rownum < 100"
                + "order by dbms_random.value");

        validateSpecialEnvironment(sendRequestAndValidateSoapResponse(tpsID));
    }

    @Test(dependsOnMethods = "testRetrieveResortReservations_Smoke", groups = { "api", "regression", "accommodation", "accommodationSalesService", "retrieveResortReservations" })
    public void testRetrieveResortReservations_Booked() {
        TestReporter.logScenario("Test - Retrieve Resort Reservations - Booked");
        String tpsID = getTPSIdForQuery("select b.tps_id "
                + "from res_mgmt.tps a "
                + "join res_mgmt.tc_grp b on a.tps_id = b.tps_id "
                + "join res_mgmt.tc c on b.tc_grp_nb = c.tc_grp_nb "
                + "and a.TRVL_STS_NM = 'Booked' "
                + "and c.tc_typ_nm = 'AccommodationComponent' "
                + "where rownum < 100"
                + "order by dbms_random.value");

        validateSpecialEnvironment(sendRequestAndValidateSoapResponse(tpsID));
    }

    /*
     * Utility Functions
     */
    private String getTPSIdForQuery(String query) {
        Recordset results = new Recordset(new OracleDatabase(environment, Database.DREAMS).getResultSet(query));
        TestReporter.assertTrue(results.getRowCount() > 0 && Arrays.asList(results.getArray()[0]).contains("TPS_ID"), "The SQL Query returned a TPS ID");
        return results.getValue("TPS_ID", ThreadLocalRandom.current().nextInt(results.getRowCount()) + 1);
    }

    private RetrieveResortReservations sendRequestAndValidateSoapResponse(String tpsID) {
        RetrieveResortReservations retrieveResortReservations = new RetrieveResortReservations(environment);
        retrieveResortReservations.setReservationNumber(tpsID);
        retrieveResortReservations.sendRequest();

        TestReporter.logAPI(!retrieveResortReservations.getResponseStatusCode().equals("200"), "The response was not successful.", retrieveResortReservations);
        TestReporter.assertEquals(tpsID, retrieveResortReservations.getTravelPlanSegmentId(), "The response contains the correct TPS ID.");
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