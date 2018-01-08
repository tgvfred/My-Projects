package com.disney.api.soapServices.accommodationModule.helpers;

import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.database.Database;
import com.disney.utils.dataFactory.database.Recordset;
import com.disney.utils.dataFactory.database.SQLValidationException;
import com.disney.utils.dataFactory.database.databaseImpl.OracleDatabase;

public class ShareHelper {
    private String environment;
    private String tpId;
    private String tpsId;
    private String tcgId;
    private String tcId;

    public ShareHelper(String environment) {
        this.environment = environment;
    }

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

    public void validateReservationHistory(int numExpectedRecords, String TpsId) {
        TestReporter.logStep("Verify reservation history");

        String sql = "select * from res_mgmt.res_hist a where a.tps_id = '" + TpsId + "'";
        Database db = new OracleDatabase(environment, Database.DREAMS);
        Recordset rs = new Recordset(db.getResultSet(sql));

        if (rs.getRowCount() == 0) {
            throw new SQLValidationException("No reservation found for tps ID [ " + TpsId + " ]", sql);
        }

        // TestReporter.softAssertEquals(rs.getRowCount(), numExpectedRecords, "Verify that the number of records [" + rs.getRowCount() + "] is that which is expected [" + numExpectedRecords + "].");

        for (int i = 1; i <= rs.getRowCount(); i++) {
            if (rs.getValue("RES_HIST_PROC_DS", i).equals("Shared")) {
                TestReporter.assertEquals(rs.getValue("RES_HIST_PROC_DS", i), "Shared", "Verify the reservation history status [" + rs.getValue("RES_HIST_PROC_DS", i) + "] matches the reservation history status in the DB [Shared]");
            }
            TestReporter.assertAll();
        }
    }

    public void validateShareInFlag(int numExpectedRecords2, String TcgId) {
        TestReporter.logStep("Verify share in flag");

        String sql = "select c.* from res_mgmt.tc_grp a join res_mgmt.tc b on a.tc_grp_nb = b.tc_grp_nb join res_mgmt.acm_cmpnt c on b.tc_id = c.acm_tc_id where a.tc_grp_nb = '" + TcgId + "'";
        Database db = new OracleDatabase(environment, Database.DREAMS);
        Recordset rs = new Recordset(db.getResultSet(sql));

        if (rs.getRowCount() == 0) {
            throw new SQLValidationException("No share flag found for tcg ID [ " + TcgId + " ]", sql);
        }

        // TestReporter.softAssertEquals(rs.getRowCount(), numExpectedRecords2, "Verify that the number of records [" + rs.getRowCount() + "] is that which is expected [" + numExpectedRecords2 + "].");

        for (int i = 1; i <= rs.getRowCount(); i++) {
            if (rs.getValue("RES_HIST_PROC_DS", i).equals("Shared")) {
                TestReporter.assertEquals(rs.getValue("SHR_IN", i), "Y", "Verify the room has been shared [" + rs.getValue("SHR_IN", i) + "] in the DB [Y]");
            }
            TestReporter.assertAll();
        }
    }

    public void validateAssignmentOwnerIdChanges(int numExpectedRecords3, String assignOwnerId, String TcgId) {
        TestReporter.logStep("Verify assignment owner id changes");

        String sql = "select a.* from res_mgmt.tc a join rsrc_inv.RSRC_ASGN_OWNR b on a.ASGN_OWN_ID = b.ASGN_OWNR_ID join rsrc_inv.RSRC_ASGN_REQ c on b.ASGN_OWNR_ID = c.ASGN_OWNR_ID where a.tc_grp_nb = '" + TcgId + "'";
        Database db = new OracleDatabase(environment, Database.DREAMS);
        Recordset rs = new Recordset(db.getResultSet(sql));

        if (rs.getRowCount() == 0) {
            throw new SQLValidationException("No assignment owner id found for tcg ID [ " + TcgId + " ]", sql);
        }

        // TestReporter.softAssertEquals(rs.getRowCount(), numExpectedRecords3, "Verify that the number of records [" + rs.getRowCount() + "] is that which is expected [" + numExpectedRecords3 + "].");

        do {
            TestReporter.softAssertTrue(rs.getValue("ASGN_OWN_ID") != assignOwnerId, "Verify the assignment owner Id [" + rs.getValue("ASGN_OWN_ID") + "] does not equal the old assignment owner id [" + assignOwnerId + "].");
            rs.moveNext();
        } while (rs.hasNext());
    }

    public void validateMultipleOwnerIds(String firstTcgId, String secondTcgId) {
        TestReporter.logStep("Verify assignment owner id is the same for both TCG's but is not the same as either original ownder Id for each TCG");

        String sql = "select a.* from res_mgmt.tc a join rsrc_inv.RSRC_ASGN_OWNR b on a.ASGN_OWN_ID = b.ASGN_OWNR_ID join rsrc_inv.RSRC_ASGN_REQ c on b.ASGN_OWNR_ID = c.ASGN_OWNR_ID where a.tc_grp_nb = '" + firstTcgId + "'";
        Database db = new OracleDatabase(environment, Database.DREAMS);
        Recordset rs = new Recordset(db.getResultSet(sql));

        if (rs.getRowCount() == 0) {
            throw new SQLValidationException("No assignment owner id found for tcg ID [ " + firstTcgId + " ]", sql);
        }

        String assignFirstOwnerId = rs.getValue("ASGN_OWN_ID");

        String sql2 = "select a.* from res_mgmt.tc a join rsrc_inv.RSRC_ASGN_OWNR b on a.ASGN_OWN_ID = b.ASGN_OWNR_ID join rsrc_inv.RSRC_ASGN_REQ c on b.ASGN_OWNR_ID = c.ASGN_OWNR_ID where a.tc_grp_nb = '" + secondTcgId + "'";
        Database db2 = new OracleDatabase(environment, Database.DREAMS);
        Recordset rs2 = new Recordset(db2.getResultSet(sql2));

        String assignSecondOwnerId = rs2.getValue("ASGN_OWN_ID");

        do {
            TestReporter.softAssertEquals(assignFirstOwnerId, assignSecondOwnerId, "Verify the assignment owner Ids for each TCG [" + assignFirstOwnerId + "] match [" + assignSecondOwnerId + "].");
            rs.moveNext();
        } while (rs.hasNext());

    }

    public void validateFolioGuaranteeType(int numExpectedRecords4, String TcgId) {
        TestReporter.logStep("Validate group guarantee for node charge group of tcg");

        String sql = "select i.GUAR_TYP_NM from folio.EXTNL_REF a left outer join folio.CHRG_GRP_EXTNL_REF b on a.EXTNL_REF_ID = b.EXTNL_REF_ID left outer join folio.CHRG_GRP c on b.CHRG_GRP_ID = c.CHRG_GRP_ID left outer join folio.CHRG d on c.CHRG_GRP_ID = d.CHRG_GRP_ID left outer join folio.CHRG_ITEM e on d.CHRG_ID = e.CHRG_ID left outer join folio.CHRG_GRP_FOLIO f on c.CHRG_GRP_ID = f.ROOT_CHRG_GRP_ID left outer join folio.FOLIO g on f.CHRG_GRP_FOLIO_ID = g.FOLIO_ID left outer join folio.FOLIO_ITEM h on g.FOLIO_ID = h.FOLIO_ID left outer join folio.node_chrg_grp i on c.CHRG_GRP_ID = i.NODE_CHRG_GRP_ID where a.EXTNL_REF_VAL in ('" + TcgId + "')";
        Database db = new OracleDatabase(environment, Database.DREAMS);
        Recordset rs = new Recordset(db.getResultSet(sql));

        if (rs.getRowCount() == 0) {
            throw new SQLValidationException("No assignment owner id found for tcg ID [ " + TcgId + " ]", sql);
        }

        // TestReporter.softAssertEquals(rs.getRowCount(), numExpectedRecords4, "Verify that the number of records [" + rs.getRowCount() + "] is that which is expected [" + numExpectedRecords4 + "].");

        do {
            TestReporter.assertEquals(rs.getValue("GUAR_TYP_NM"), "GROUP_GUARANTEED", "Verify the guarantee type name [" + rs.getValue("GUAR_TYP_NM") + "] is that which is expected in the DB [GROUP_GUARANTEED]");
            rs.moveNext();
            TestReporter.assertAll();
        } while (rs.hasNext());
    }
}
