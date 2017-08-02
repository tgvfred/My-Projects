package com.disney.api.soapServices.accommodationModule.helpers;

import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.database.Database;
import com.disney.utils.dataFactory.database.Recordset;
import com.disney.utils.dataFactory.database.databaseImpl.OracleDatabase;

public class ShareHelper {
    private String environment;
    private String tpId;
    private String tpsId;
    private String tcgId;
    private String tcId;

    public String getEnvironment() {
        return environment;
    }

    public void setEnvironment(String environment) {
        this.environment = environment;
    }

    public String getTpId() {
        return tpId;
    }

    public void setTpId(String tpId) {
        this.tpId = tpId;
    }

    public String getTpsId() {
        return tpsId;
    }

    public void setTpsId(String tpsId) {
        this.tpsId = tpsId;
    }

    public String getTcgId() {
        return tcgId;
    }

    public void setTcgId(String tcgId) {
        this.tcgId = tcgId;
    }

    public String getTcId() {
        return tcId;
    }

    public void setTcId(String tcId) {
        this.tcId = tcId;
    }

    public void validateReservationHistory(int numExpectedRecords, String reservationHistoryId, String resLocation, String creationDate, String updateDate, String bookingDate, String arrivalDate, String departureDate) {
        TestReporter.logStep("Verify reservation history");

        String sql = "select * from res_mgmt.res_hist a where a.tps_id = '" + getTpsId() + "'";
        Database db = new OracleDatabase(environment, Database.DREAMS);
        Recordset rs = new Recordset(db.getResultSet(sql));

        TestReporter.softAssertEquals(rs.getRowCount(), numExpectedRecords, "Verify that the number of records [" + rs.getRowCount() + "] is that which is expcetd [" + numExpectedRecords + "].");

        for (int i = 1; i <= rs.getRowCount(); i++) {
            if (rs.getValue("RES_HIST_PROC_DS", i).equals("Shared")) {
                TestReporter.assertEquals(rs.getValue("RES_HIST_ID", i), reservationHistoryId, "Verify the reservation history id [" + rs.getValue("RES_HIST_ID", i) + "] matches the reservation history in the DB [" + reservationHistoryId + "]");
                TestReporter.assertEquals(rs.getValue("TP_ID", i), tpId, "Verify the tp id [" + rs.getValue("TP_ID", i) + "] matches the tpId in the DB [" + tpId + "]");
                TestReporter.assertEquals(rs.getValue("RES_HIST_LOC_NM", i), resLocation, "Verify the reservation history location name [" + rs.getValue("RES_HIST_LOC_NM", i) + "] matches the reservation history location name in the db [" + resLocation + "]");
                TestReporter.assertEquals(rs.getValue("TPS_ID", i), tpsId, "Verify the tpsId [" + rs.getValue("TPS_ID", i) + "] matches the tpsId in the db [" + tpsId + "]");
                TestReporter.assertEquals(rs.getValue("TC_GRP_NM", i), tcgId, "Verify the tcgId [" + rs.getValue("TC_GRP_NM", i) + "] matches the tcgId in the db [" + tcgId + "]");
                TestReporter.assertEquals(rs.getValue("TC_ID", i), tcId, "Verify the tcId [" + rs.getValue("TC_ID", i) + "] matches the tcId in the db [" + tcId + "]");
                TestReporter.assertEquals(rs.getValue("RES_HIST_PROC_DS", i), "Shared", "Verify the reservation history process [" + rs.getValue("RES_HIST_PROC_DS", i) + "] matches the reservation history process in the db [Shared]");
                TestReporter.assertEquals(rs.getValue("CREATE_DTS", i).split(" ")[0], creationDate, "Verify the creation date [" + rs.getValue("CREATE_DTS", i).split(" ")[0] + "] matches the creation date in the db [" + creationDate + "]");
                TestReporter.assertEquals(rs.getValue("UPDT_DTS", i).split(" ")[0], updateDate, "Verify the update date [" + rs.getValue("UPDT_DTS", i).split(" ")[0] + "] matches the update date in the db [" + updateDate + "]");
                TestReporter.softAssertTrue(tpId.contains(rs.getValue("RES_HIST_TX")), "Verify the TCG id [" + rs.getValue("RES_HIST_TX") + "] is contained in the reservation history text in the db [" + tcId + "].");
                TestReporter.softAssertTrue(bookingDate.contains(rs.getValue("RES_HIST_TX")), "Verify the booking date [" + rs.getValue("RES_HIST_TX") + "] is contained in the reservation history text in the db [" + bookingDate + "].");
                TestReporter.softAssertTrue(arrivalDate.contains(rs.getValue("RES_HIST_TX")), "Verify the arrival date [" + rs.getValue("RES_HIST_TX") + "] is contained in the reservation history text in the db [" + arrivalDate + "].");
                TestReporter.softAssertTrue(departureDate.contains(rs.getValue("RES_HIST_TX")), "Verify the departure date [" + rs.getValue("RES_HIST_TX") + "] is contained in the reservation history text in the db [" + departureDate + "].");

            }
            TestReporter.assertAll();
        }

    }

    public void validateShareInFlag(int numExpectedRecords2) {
        TestReporter.logStep("Verify share in flag");

        String sql = "select c.* from res_mgmt.tc_grp a join res_mgmt.tc b on a.tc_grp_nb = b.tc_grp_nb join res_mgmt.acm_cmpnt c on b.tc_id = c.acm_tc_id where a.tc_grp_nb = '" + getTcgId() + "'";
        Database db = new OracleDatabase(environment, Database.DREAMS);
        Recordset rs = new Recordset(db.getResultSet(sql));

        TestReporter.softAssertEquals(rs.getRowCount(), numExpectedRecords2, "Verify that the number of records [" + rs.getRowCount() + "] is that which is expcetd [" + numExpectedRecords2 + "].");

        for (int i = 1; i <= rs.getRowCount(); i++) {
            if (rs.getValue("RES_HIST_PROC_DS", i).equals("Shared")) {
                TestReporter.assertEquals(rs.getValue("SHR_IN", i), "Y", "Verify the room has been shared [" + rs.getValue("SHR_IN", i) + "] in the DB [Y]");
            }
            TestReporter.assertAll();
        }
    }

    public void validateAssignmentOwnerIdChanges(int numExpectedRecords3) {
        TestReporter.logStep("Verify assignment owner id changes");

        String sql = "select c.* from res_mgmt.tc_grp a join res_mgmt.tc b on a.tc_grp_nb = b.tc_grp_nb join res_mgmt.acm_cmpnt c on b.tc_id = c.acm_tc_id where a.tc_grp_nb = '" + getTcgId() + "'";
        Database db = new OracleDatabase(environment, Database.DREAMS);
        Recordset rs = new Recordset(db.getResultSet(sql));

        TestReporter.softAssertEquals(rs.getRowCount(), numExpectedRecords3, "Verify that the number of records [" + rs.getRowCount() + "] is that which is expcetd [" + numExpectedRecords2 + "].");

    }
}
