package com.disney.composite.api.accommodationModule.soapServices.accommodationSalesServicePort.retrieveResortReservations;

import static com.disney.api.soapServices.accommodationModule.applicationError.AccommodationErrorCode.INVALID_SEARCH_CRITERIA;
import static com.disney.api.soapServices.accommodationModule.applicationError.AccommodationErrorCode.NO_TP_DATA_FOUND;

import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

import org.testng.annotations.Test;

import com.disney.api.BaseTest;
import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.RetrieveResortReservations;
import com.disney.api.soapServices.applicationError.ApplicationErrorCode;
import com.disney.api.soapServices.core.BaseSoapCommands;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.database.Database;
import com.disney.utils.dataFactory.database.Recordset;
import com.disney.utils.dataFactory.database.databaseImpl.OracleDatabase;

public class TestRetrieveResortReservations_Negative extends BaseTest {
    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesService", "retrieveResortReservations", "negative" })
    public void testRetrieveResortReservations_NullReservationNumber() {
        TestReporter.logScenario("Test - Retrieve Resort Reservations - Null Reservation Number");
        sendRequestAndValidateApplicationError(BaseSoapCommands.REMOVE_NODE.toString(), INVALID_SEARCH_CRITERIA);
    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesService", "retrieveResortReservations", "negative" })
    public void testRetrieveResortReservations_DiningReservation() {
        TestReporter.logScenario("Test - Retrieve Resort Reservations - Dining Reservation");
        String tpsID = getTPSIdForQuery("select b.tps_id from res_mgmt.tps a "
                + "join res_mgmt.tc_grp b on a.tps_id = b.tps_id "
                + "join res_mgmt.tc c on b.tc_grp_nb = c.tc_grp_nb "
                + "and a.TRVL_STS_NM not in ('Auto Cancelled','Cancelled','Past Visit', 'No Show') "
                + "and c.TRVL_STS_NM not in ('Auto Cancelled','Cancelled','Past Visit', 'No Show', 'DF Checked Out') "
                + "and b.tc_grp_typ_nm = 'SHOWDINING' "
                + "and c.tc_typ_nm = 'ComponentTravelComponent' "
                + "and c.prod_typ_nm = 'DiningProduct' "
                + "where rownum < 100"
                + "order by dbms_random.value");
        sendRequestAndValidateApplicationError(tpsID, NO_TP_DATA_FOUND);
    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesService", "retrieveResortReservations", "negative" })
    public void testRetrieveResortReservations_Cancelled() {
        TestReporter.logScenario("Test - Retrieve Resort Reservations - Cancelled");
        String tpsID = getTPSIdForQuery(accommodationComponentQueryBuilder("Cancelled"));
        sendRequestAndValidateApplicationError(tpsID, NO_TP_DATA_FOUND);
    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesService", "retrieveResortReservations", "negative" })
    public void testRetrieveResortReservations_Arrived() {
        TestReporter.logScenario("Test - Retrieve Resort Reservations - Arrived");
        String tpsID = getTPSIdForQuery(arrivedQueryBuilder("Arrived"));
        sendRequestAndValidateApplicationError(tpsID, NO_TP_DATA_FOUND);
    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesService", "retrieveResortReservations", "negative" })
    public void testRetrieveResortReservations_AutoArrived() {
        TestReporter.logScenario("Test - Retrieve Resort Reservations - Auto Arrived");
        String tpsID = getTPSIdForQuery(arrivedQueryBuilder("Auto Arrived"));
        sendRequestAndValidateApplicationError(tpsID, NO_TP_DATA_FOUND);
    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesService", "retrieveResortReservations", "negative" })
    public void testRetrieveResortReservations_NoShow() {
        TestReporter.logScenario("Test - Retrieve Resort Reservations - No Show");
        String tpsID = getTPSIdForQuery(accommodationComponentQueryBuilder("No Show"));
        sendRequestAndValidateApplicationError(tpsID, NO_TP_DATA_FOUND);
    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesService", "retrieveResortReservations", "negative" })
    public void testRetrieveResortReservations_PastVisit() {
        TestReporter.logScenario("Test - Retrieve Resort Reservations - Past Visit");
        String tpsID = getTPSIdForQuery(accommodationComponentQueryBuilder("Past Visit"));
        sendRequestAndValidateApplicationError(tpsID, NO_TP_DATA_FOUND);
    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesService", "retrieveResortReservations", "negative" })
    public void testRetrieveResortReservations_AutoCancelled() {
        TestReporter.logScenario("Test - Retrieve Resort Reservations - Auto Cancelled");
        String tpsID = getTPSIdForQuery(accommodationComponentQueryBuilder("Auto Cancelled"));
        sendRequestAndValidateApplicationError(tpsID, NO_TP_DATA_FOUND);
    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesService", "retrieveResortReservations", "negative" })
    public void testRetrieveResortReservations_NotArrived() {
        TestReporter.logScenario("Test - Retrieve Resort Reservations - Not Arrived");
        String query = "select tps_id, COUNT(CASE WHEN TRVL_STS_NM = 'Not Arrived' THEN 0 ELSE 1 END) from "
                + "(select b.tps_id, c.trvl_sts_nm from res_mgmt.tps a "
                + "join res_mgmt.tc_grp b on a.tps_id = b.tps_id "
                + "join res_mgmt.tc c on b.tc_grp_nb = c.tc_grp_nb "
                + "where a.TRVL_STS_NM = 'Not Arrived' "
                + "and c.tc_typ_nm = 'AccommodationComponent' "
                + "and rownum < 100) "
                + "group by tps_id "
                + "order by COUNT(CASE WHEN TRVL_STS_NM = 'Not Arrived' THEN 0 ELSE 1 END) asc, dbms_random.value";

        Recordset results = new Recordset(new OracleDatabase(environment, Database.DREAMS).getResultSet(query));
        TestReporter.assertTrue(results.getRowCount() > 0 && Arrays.asList(results.getArray()[0]).contains("TPS_ID"), "The SQL Query returned a TPS ID");
        String tpsID = results.getValue("TPS_ID");

        sendRequestAndValidateApplicationError(tpsID, NO_TP_DATA_FOUND);
    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesService", "retrieveResortReservations", "negative" })
    public void testRetrieveResortReservations_CheckingIn() {
        TestReporter.logScenario("Test - Retrieve Resort Reservations - Checking In");
        String tpsID = getTPSIdForQuery(accommodationComponentQueryBuilder("Checking In"));
        sendRequestAndValidateApplicationError(tpsID, NO_TP_DATA_FOUND);
    }

    /*
     * Utility Functions
     */
    private String getTPSIdForQuery(String query) {
        Recordset results = new Recordset(new OracleDatabase(environment, Database.DREAMS).getResultSet(query));
        TestReporter.assertTrue(results.getRowCount() > 0 && Arrays.asList(results.getArray()[0]).contains("TPS_ID"), "The SQL Query returned a TPS ID");
        return results.getValue("TPS_ID", ThreadLocalRandom.current().nextInt(results.getRowCount()) + 1);
    }

    private String arrivedQueryBuilder(String status) {
        return "select b.tps_id from res_mgmt.tps a "
                + "join res_mgmt.tc_grp b on a.tps_id = b.tps_id "
                + "join res_mgmt.tc c on b.tc_grp_nb = c.tc_grp_nb "
                + "and a.TRVL_STS_NM = '" + status + "' "
                + "where rownum < 100 "
                + "order by dbms_random.value";
    }

    private String accommodationComponentQueryBuilder(String status) {
        return "select b.tps_id from res_mgmt.tps a "
                + "join res_mgmt.tc_grp b on a.tps_id = b.tps_id "
                + "join res_mgmt.tc c on b.tc_grp_nb = c.tc_grp_nb "
                + "and a.TRVL_STS_NM = '" + status + "' "
                + "and c.tc_typ_nm = 'AccommodationComponent' "
                + "where rownum < 100 "
                + "order by dbms_random.value";
    }

    private void sendRequestAndValidateApplicationError(String tpsID, ApplicationErrorCode error) {
        RetrieveResortReservations retrieveResortReservations = new RetrieveResortReservations(environment);
        retrieveResortReservations.setReservationNumber(tpsID);
        retrieveResortReservations.sendRequest();

        TestReporter.logAPI(retrieveResortReservations.getResponseStatusCode().equals("200"), "The response was incorrectly successful.", retrieveResortReservations);
        validateApplicationError(retrieveResortReservations, error);
    }
}
