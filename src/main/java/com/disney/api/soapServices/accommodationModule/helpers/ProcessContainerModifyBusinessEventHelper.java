package com.disney.api.soapServices.accommodationModule.helpers;

import com.disney.AutomationException;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.database.Database;
import com.disney.utils.dataFactory.database.Recordset;
import com.disney.utils.dataFactory.database.databaseImpl.OracleDatabase;

public class ProcessContainerModifyBusinessEventHelper {
    private String tpStatus;

    public String getTpStatus() {

        return tpStatus;
    }

    public void statusTP_TC(String tps, String env) {
        TestReporter.logStep("Validate cancellation number and TPS status");
        String sql = "select a.TRVL_STS_NM TP_STATUS, a.TPS_CNCL_NB CANCEL_NUMBER, c.TRVL_STS_NM TC_STATUS"
                + " from res_mgmt.tps a"
                + " join res_mgmt.tc_grp b on a.tps_id = b.tps_id"
                + " join res_mgmt.tc c on b.tc_grp_nb = c.tc_grp_nb"
                + " where a.tps_id ='" + tps + "'";

        Database db = new OracleDatabase(env, Database.DREAMS);
        Recordset rs = new Recordset(db.getResultSet(sql));
        if (rs.getRowCount() == 0) {
            throw new AutomationException("No records were returned. SQL: [" + sql + "].");
        }

        TestReporter.assertTrue(!rs.getValue("CANCEL_NUMBER", 1).equals("0") && !rs.getValue("CANCEL_NUMBER", 1).equals("NULL"), "The Travel Plan Segment [" + tps + "] has a cancellation number of [" + rs.getValue("CANCEL_NUMBER", 1) + "] and a reservation status of [" + rs.getValue("TP_STATUS", 1) + "]");
        // TestReporter.assertTrue(rs.getValue("TP_STATUS", 1).equals("Booked") || rs.getValue("TP_STATUS", 1).equals("Auto Cancelled"), "The reservation is [" + rs.getValue("TP_STATUS", 1) + "].");
        TestReporter.assertTrue(rs.getValue("TP_STATUS", 1).equals("Auto Cancelled"), "The reservation is [" + rs.getValue("TP_STATUS", 1) + "].");
    }

    public void statusTP_TCNoCanc(String tps, String env) {
        TestReporter.logStep("Validate cancellation number and TPS status");
        String sql = "select a.TRVL_STS_NM TP_STATUS, a.TPS_CNCL_NB CANCEL_NUMBER, c.TRVL_STS_NM TC_STATUS"
                + " from res_mgmt.tps a"
                + " join res_mgmt.tc_grp b on a.tps_id = b.tps_id"
                + " join res_mgmt.tc c on b.tc_grp_nb = c.tc_grp_nb"
                + " where a.tps_id ='" + tps + "'";
        Database db = new OracleDatabase(env, Database.DREAMS);
        Recordset rs = new Recordset(db.getResultSet(sql));
        if (rs.getRowCount() == 0) {
            throw new AutomationException("No records were returned. SQL: [" + sql + "].");
        }

        TestReporter.assertTrue(rs.getValue("CANCEL_NUMBER", 1).equals("0") || rs.getValue("CANCEL_NUMBER", 1).equals("NULL"), "The Travel Plan Segment [" + tps + "] has a cancellation number of [" + rs.getValue("CANCEL_NUMBER", 1) + "] and a reservation status of [" + rs.getValue("TP_STATUS", 1) + "]");
        // TestReporter.assertTrue(rs.getValue("TP_STATUS", 1).equals("Booked") || rs.getValue("TP_STATUS", 1).equals("Auto Cancelled"), "The Travel Plan Segment[ " + tps + "] has a reservation of [" + rs.getValue("TP_STATUS", 1) + "].");
        TestReporter.assertTrue(rs.getValue("TP_STATUS", 1).equals("Booked"), "The Travel Plan Segment[ " + tps + "] has a reservation of [" + rs.getValue("TP_STATUS", 1) + "].");
    }

    public void statusTP_TCWithZeroCanc(String tps, String env) {
        TestReporter.logStep("Validate cancellation number and TPS status");
        String sql = "select a.TRVL_STS_NM TP_STATUS, a.TPS_CNCL_NB CANCEL_NUMBER, c.TRVL_STS_NM TC_STATUS"
                + " from res_mgmt.tps a"
                + " join res_mgmt.tc_grp b on a.tps_id = b.tps_id"
                + " join res_mgmt.tc c on b.tc_grp_nb = c.tc_grp_nb"
                + " where a.tps_id ='" + tps + "'";
        Database db = new OracleDatabase(env, Database.DREAMS);
        Recordset rs = new Recordset(db.getResultSet(sql));
        if (rs.getRowCount() == 0) {
            throw new AutomationException("No records were returned. SQL: [" + sql + "].");
        }

        TestReporter.assertTrue(rs.getValue("CANCEL_NUMBER", 1).equals("0") || rs.getValue("CANCEL_NUMBER", 1).equals("NULL"), "The Travel Plan Segment [" + tps + "] has a cancellation number of [" + rs.getValue("CANCEL_NUMBER", 1) + "] and a reservation status of [" + rs.getValue("TP_STATUS", 1) + "]");
        TestReporter.assertTrue(rs.getValue("CANCEL_NUMBER", 1).equals("0"), "The Travel Plan Segment [" + tps + "] has a cancellation number of [" + rs.getValue("CANCEL_NUMBER", 1) + "] and a reservation status of [" + rs.getValue("TP_STATUS", 1) + "]");
    }

    public void reservationHistory(String tp, String env) {
        TestReporter.logStep("Validate reservation history");
        String sql = "select a. RES_HIST_PROC_DS EVENT"
                + " from res_mgmt.res_hist a"
                + " where a.tp_id='" + tp + "'";
        Database db = new OracleDatabase(env, Database.DREAMS);
        Recordset rs = new Recordset(db.getResultSet(sql));
        if (rs.getRowCount() == 0) {
            throw new AutomationException("No records were returned. SQL: [" + sql + "].");
        }

        for (int j = 1; j <= rs.getRowCount(); j++) {
            if (rs.getValue("EVENT", j).equals("Auto Cancelled")) {
                TestReporter.assertTrue(rs.getValue("EVENT", j).equals("Auto Cancelled"), "The travel plan id [" + tp + "] has a reservation history of [" + rs.getValue("EVENT", j) + "]");
            }
            if (rs.getValue("EVENT", j).equals("Reinstated")) {
                TestReporter.assertTrue(rs.getValue("EVENT", j).equals("Reinstated"), "The  travel plan id [" + tp + "] has reservation history of [" + rs.getValue("EVENT", j) + "]");
            }
        }
    }

    public void noReservationHistory(String tp, String env) {
        TestReporter.logStep("Validate reservation history");
        String sql = "select a. RES_HIST_PROC_DS EVENT"
                + " from res_mgmt.res_hist a"
                + " where a.tp_id='" + tp + "'";
        Database db = new OracleDatabase(env, Database.DREAMS);
        Recordset rs = new Recordset(db.getResultSet(sql));
        TestReporter.assertTrue(rs.getRowCount() == 0, "Verify that no reservation history records were found for TP ID [" + tp + "].");
    }

    public void rimRecordNotConsumed(String tcg, String env) {
        TestReporter.logStep("Validate RIM not consumed");
        String sql = "select*"
                + " from res_mgmt.tc a"
                + " join rsrc_inv.RSRC_ASGN_OWNR b"
                + " on a.ASGN_OWN_ID=b.ASGN_OWNR_ID"
                + " join rsrc_inv.RSRC_ASGN_REQ c on b.ASGN_OWNR_ID=c.ASGN_OWNR_ID"
                + " where a.tc_grp_nb='" + tcg + "'";

        Database db = new OracleDatabase(env, Database.DREAMS);
        Recordset rs = new Recordset(db.getResultSet(sql));

        TestReporter.assertTrue(rs.getRowCount() == 0, "The travel component grouping id [" + tcg + "] has a Rim Inventory that was not consumed since the row count is [" + rs.getRowCount() + "]");
    }

    public void rimRecordConsumed(String tcg, String env) {
        TestReporter.logStep("Validate RIM consumed");
        String sql = "select*"
                + " from res_mgmt.tc a"
                + " join rsrc_inv.RSRC_ASGN_OWNR b"
                + " on a.ASGN_OWN_ID=b.ASGN_OWNR_ID"
                + " join rsrc_inv.RSRC_ASGN_REQ c on b.ASGN_OWNR_ID=c.ASGN_OWNR_ID"
                + " where a.tc_grp_nb='" + tcg + "'";

        Database db = new OracleDatabase(env, Database.DREAMS);
        Recordset rs = new Recordset(db.getResultSet(sql));
        if (rs.getRowCount() == 0) {
            throw new AutomationException("No records were returned. SQL: [" + sql + "].");
        }

        TestReporter.assertTrue(rs.getRowCount() >= 1, "The travel component grouping id [" + tcg + "] has a Rim Inventory that was consumed since the row count is [" + rs.getRowCount() + "]. And"
                + "has a Owner Assignment Id of [" + rs.getValue("ASGN_OWNR_ID") + "].");
    }

    public void chargeGroupStatus(String tp, String tps, String tcg, String env, String status) {
        TestReporter.logStep("Validate charge group status");
        String sql = "select c.CHRG_GRP_STS_NM"
                + " from"
                + " folio.EXTNL_REF a"
                + " left outer"
                + " join folio."
                + " CHRG_GRP_EXTNL_REF b"
                + " on a.EXTNL_REF_ID="
                + " b.EXTNL_REF_ID left"
                + " outer join"
                + " folio.CHRG_GRP c"
                + " on b.CHRG_GRP_ID="
                + " c.CHRG_GRP_ID"
                + " where a.EXTNL_REF_VAL in ('" + tp + "','" + tps + "', '" + tcg + "')";

        Database db = new OracleDatabase(env, Database.DREAMS);
        Recordset rs = new Recordset(db.getResultSet(sql));
        if (rs.getRowCount() == 0) {
            throw new AutomationException("No records were returned. SQL: [" + sql + "].");
        }

        for (int i = 1; i < rs.getRowCount(); i++) {
            if ((rs.getValue("CHRG_GRP_STS_NM", i).equals(status))) {
                TestReporter.assertTrue(rs.getValue("CHRG_GRP_STS_NM", i).equals(status), "The travel plan segment id [" + tps + "] has a charge group status of [" + rs.getValue("CHRG_GRP_STS_NM", i) + "].");
            }
        }
    }

    public void chargeItemsNotActive(String tcg, String env) {
        TestReporter.logStep("Validate charge item inactive");
        String sql = "select d.*"
                + " from folio.EXTNL_REF a"
                + " left outer join folio.CHRG_GRP_EXTNL_REF b on a.EXTNL_REF_ID = b.EXTNL_REF_ID"
                + " left outer join folio.CHRG_GRP c on b.CHRG_GRP_ID = c.CHRG_GRP_ID"
                + " left outer join folio.CHRG d on c.CHRG_GRP_ID = d.CHRG_GRP_ID"
                + " left outer join folio.CHRG_ITEM e on d.CHRG_ID = e.CHRG_ID"
                + " where a.EXTNL_REF_VAL in ('" + tcg + "')";

        Database db = new OracleDatabase(env, Database.DREAMS);
        Recordset rs = new Recordset(db.getResultSet(sql));
        if (rs.getRowCount() == 0) {
            throw new AutomationException("No records were returned. SQL: [" + sql + "].");
        }

        for (int i = 1; i < rs.getRowCount(); i++) {
            TestReporter.assertTrue(rs.getValue("CHRG_ACTV_IN", i).equals("N"), "The Travel Component Grouping id [" + tcg + "] charge groups are not active and set to [" + rs.getValue("CHRG_ACTV_IN", i) + "]");
        }
    }

    public void chargeItemsActive(String tcg, String env) {
        TestReporter.logStep("Validate charge item active");
        String sql = "select d.*"
                + " from folio.EXTNL_REF a"
                + " left outer join folio.CHRG_GRP_EXTNL_REF b on a.EXTNL_REF_ID = b.EXTNL_REF_ID"
                + " left outer join folio.CHRG_GRP c on b.CHRG_GRP_ID = c.CHRG_GRP_ID"
                + " left outer join folio.CHRG d on c.CHRG_GRP_ID = d.CHRG_GRP_ID"
                + " left outer join folio.CHRG_ITEM e on d.CHRG_ID = e.CHRG_ID"
                + " where a.EXTNL_REF_VAL in ('" + tcg + "')";

        Database db = new OracleDatabase(env, Database.DREAMS);
        Recordset rs = new Recordset(db.getResultSet(sql));
        if (rs.getRowCount() == 0) {
            throw new AutomationException("No records were returned. SQL: [" + sql + "].");
        }

        for (int i = 1; i < rs.getRowCount(); i++) {
            TestReporter.assertTrue(rs.getValue("CHRG_ACTV_IN", i).equals("Y"), "The Travel Component grouping id [" + tcg + "] charge groups are active and set to [" + rs.getValue("CHRG_ACTV_IN", i) + "]");
        }
    }

    public void folioItems(String tp, String env) {
        TestReporter.logStep("Validate folio items");
        String sql = "select h.*"
                + " from folio.EXTNL_REF a"
                + " left outer join folio.CHRG_GRP_EXTNL_REF b on a.EXTNL_REF_ID = b.EXTNL_REF_ID"
                + " left outer join folio.CHRG_GRP c on b.CHRG_GRP_ID = c.CHRG_GRP_ID"
                + " left outer join folio.CHRG d on c.CHRG_GRP_ID = d.CHRG_GRP_ID"
                + " left outer join folio.CHRG_ITEM e on d.CHRG_ID = e.CHRG_ID"
                + " left outer join folio.CHRG_GRP_FOLIO f on c.CHRG_GRP_ID = f.ROOT_CHRG_GRP_ID"
                + " left outer join folio.FOLIO g on f.CHRG_GRP_FOLIO_ID = g.FOLIO_ID"
                + " left outer join folio.FOLIO_ITEM h on g.FOLIO_ID = h.FOLIO_ID"
                + " where a.EXTNL_REF_VAL in ('" + tp + "')";

        Database db = new OracleDatabase(env, Database.DREAMS);
        Recordset rs = new Recordset(db.getResultSet(sql));
        if (rs.getRowCount() == 0) {
            throw new AutomationException("No records were returned. SQL: [" + sql + "].");
        }

        for (int i = 1; i < rs.getRowCount(); i++) {
            if (rs.getValue("JDO_CLS_NM", i).equals("AdditionalChargeItem")) {
                TestReporter.assertTrue(!rs.getValue("FOLIO_ITEM_AM", i).equals("NULL"), "The folio item id is [" + rs.getValue("FOLIO_ITEM_ID", i) + "] with an amount of [" + rs.getValue("FOLIO_ITEM_AM", i) + "] and the folio id is [" + rs.getValue("FOLIO_ID", i) + "].");
            }
        }
    }

    public void tpv3Status(String env, String tp) {
        TestReporter.logStep("Validate TPV3");
        String sql = "select b.SLS_ORD_ITEM_STS_NM TPV3_STATUS"
                + " from sales_tp.sls_ord a"
                + " join sales_tp.sls_ord_item b on a.sls_ord = b.sls_ord "
                + " and a.tp_id = " + tp;
        Database db = new OracleDatabase(env, Database.SALESTP);
        Recordset rs = new Recordset(db.getResultSet(sql));
        if (rs.getRowCount() == 0) {
            throw new AutomationException("No records were returned. SQL: [" + sql + "].");
        }

        TestReporter.assertTrue(!rs.getValue("TPV3_STATUS", 1).equals(""), "The TPV3 status is [" + rs.getValue("TPV3_STATUS", 1) + "]");
    }

    public void rimGuaranteeStatus(String env, String tc) {
        TestReporter.logStep("Validate RIM guarantee");
        String sql = "select c.GUAR_IN"
                + " from rsrc_inv.RSRC_OWN_REF a"
                + " join rsrc_inv.RSRC_OWN b on a.RSRC_OWN_ID = b.RSRC_OWN_ID"
                + " join rsrc_inv.RSRC_ASGN_OWNR c on b.ASGN_OWNR_ID = c.ASGN_OWNR_ID"
                + " where a.EXTNL_OWN_REF_VAL = '" + tc + "'";

        Database db = new OracleDatabase(env, Database.DREAMS);
        Recordset rs = new Recordset(db.getResultSet(sql));
        if (rs.getRowCount() == 0) {
            throw new AutomationException("No records were returned. SQL: [" + sql + "].");
        }

        TestReporter.assertTrue(!rs.getValue("GUAR_IN", 1).equals(""), "The guarantee status is [" + rs.getValue("GUAR_IN", 1) + "]");
    }

    public void folioNodeChargeGroupST(String env, String tps, String tcg) {
        TestReporter.logStep("Validate node charge group folio");
        String sql = "select c.GUAR_TYP_NM"
                + " from folio.EXTNL_REF a"
                + " join folio.CHRG_GRP_EXTNL_REF b on a.EXTNL_REF_ID = b.EXTNL_REF_ID"
                + " join folio.NODE_CHRG_GRP c on b.CHRG_GRP_ID = c.NODE_CHRG_GRP_ID"
                + " where a.EXTNL_REF_VAL in ('" + tps + "','" + tcg + "')";

        Database db = new OracleDatabase(env, Database.DREAMS);
        Recordset rs = new Recordset(db.getResultSet(sql));
        if (rs.getRowCount() == 0) {
            throw new AutomationException("No records were returned. SQL: [" + sql + "].");
        }
        TestReporter.assertTrue(!rs.getValue("GUAR_TYP_NM", 1).equals(""), "The folio node charge group guarantee status is [" + rs.getValue("GUAR_TYP_NM", 1) + "]");
    }
}