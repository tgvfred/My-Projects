package com.disney.api.soapServices.accommodationModule.helpers;

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

        String sql = "select a.TRVL_STS_NM TP_STATUS, a.TPS_CNCL_NB CANCEL_NUMBER, c.TRVL_STS_NM TC_STATUS"
                + " from res_mgmt.tps a"
                + " join res_mgmt.tc_grp b on a.tps_id = b.tps_id"
                + " join res_mgmt.tc c on b.tc_grp_nb = c.tc_grp_nb"
                + " where a.tps_id ='" + tps + "'";
        Database db = new OracleDatabase(env, Database.DREAMS);
        Recordset rs = new Recordset(db.getResultSet(sql));

        // tpStatus = rs.getValue("TRVL_STS_NM TP_STATUS");

        // TestReporter.assertTrue(rs.getValue("TP_STATUS", 2) != "REINSTATE", "The reservation is [" + rs.getValue("TP_STATUS", 2) + "] and is not reinstated");

        for (int i = 1; i < rs.getRowCount(); i++) {
            if (rs.getValue("TPS_CNCL_NB CANCEL_NUMBER", i) != "0") {

                TestReporter.assertTrue(rs.getValue("CANCEL_NUMBER", i) != "0", "The cancellation number is [" + rs.getValue("CANCEL_NUMBER", i) + "]");
                TestReporter.assertTrue(rs.getValue("TP_STATUS", i) != "REINSTATE", "The reservation is [" + rs.getValue("TP_STATUS", i) + "] and is not reinstated");
            }

        }
    }

    public void reservationHistory(String tp, String env) {
        String sql = "select a. RES_HIST_PROC_DS EVENT"
                + " from res_mgmt.res_hist a"
                + " where a.tp_id='" + tp + "'";
        Database db = new OracleDatabase(env, Database.DREAMS);
        Recordset rs = new Recordset(db.getResultSet(sql));

        for (int j = 0; j <= rs.getRowCount(); j++) {
            if (rs.getValue("RES_HIST_PROC_DS EVENT", j) == "Auto Cancelled") {
                TestReporter.assertTrue(rs.getValue("RES_HIST_PROC_DS EVENT", j) == "Auto Cancelled", "The reservation history is [" + rs.getValue("RES_HIST_PROC_DS EVENT", j) + "]");

            }
        }
    }

    public void rimRecordNotConsumed(String tcg, String env) {
        String sql = "select*"
                + " from res_mgmt.tc a"
                + " join rsrc_inv.RSRC_ASGN_OWNR b"
                + " on a.ASGN_OWN_ID=b.ASGN_OWNR_ID"
                + " join rsrc_inv.RSRC_ASGN_REQ c on b.ASGN_OWNR_ID=c.ASGN_OWNR_ID"
                + " where a.tc_grp_nb='" + tcg + "'";

        Database db = new OracleDatabase(env, Database.DREAMS);
        Recordset rs = new Recordset(db.getResultSet(sql));
        TestReporter.assertTrue(rs.getRowCount() == 0, "The Rim Inventory was not consumed since the row count is [" + rs.getRowCount() + "].");

    }

    public void rimRecordConsumed(String tcg, String env) {
        String sql = "select*"
                + " from res_mgmt.tc a"
                + " join rsrc_inv.RSRC_ASGN_OWNR b"
                + " on a.ASGN_OWN_ID=b.ASGN_OWNR_ID"
                + " join rsrc_inv.RSRC_ASGN_REQ c on b.ASGN_OWNR_ID=c.ASGN_OWNR_ID"
                + " where a.tc_grp_nb='" + tcg + "'";

        Database db = new OracleDatabase(env, Database.DREAMS);
        Recordset rs = new Recordset(db.getResultSet(sql));
        rs.print();
        TestReporter.assertTrue(rs.getRowCount() >= 1, "The Rim Inventory was consumed since the row count is [" + rs.getRowCount() + "].");

    }

    public void chargeGroupStatus(String tp, String tps, String tcg, String env, String status) {
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
        rs.print();
        for (int i = 1; i < rs.getRowCount(); i++) {

            if ((rs.getValue("CHRG_GRP_STS_NM", i) == status)) {
                TestReporter.assertTrue(rs.getValue("CHRG_GRP_STS_NM", i) == status, "The charge group status is [" + rs.getValue("CHRG_GRP_STS_NM", i) + "].");
            }
        }
    }

    public void chargeItemsNotActive(String tcg, String env) {
        String sql = "select d.*"
                + " from folio.EXTNL_REF a"
                + " left outer join folio.CHRG_GRP_EXTNL_REF b on a.EXTNL_REF_ID = b.EXTNL_REF_ID"
                + " left outer join folio.CHRG_GRP c on b.CHRG_GRP_ID = c.CHRG_GRP_ID"
                + " left outer join folio.CHRG d on c.CHRG_GRP_ID = d.CHRG_GRP_ID"
                + " left outer join folio.CHRG_ITEM e on d.CHRG_ID = e.CHRG_ID"
                + " where a.EXTNL_REF_VAL in ('" + tcg + "')";

        Database db = new OracleDatabase(env, Database.DREAMS);
        Recordset rs = new Recordset(db.getResultSet(sql));
        for (int i = 1; i < rs.getRowCount(); i++) {

            TestReporter.assertTrue(rs.getValue("CHRG_ACTV_IN", i) == "N", "The charge groups are not active and set to [" + rs.getValue("CHRG_ACTV_IN", i) + "]");

        }
    }

    public void chargeItemsActive(String tcg, String env) {
        String sql = "select d.*"
                + " from folio.EXTNL_REF a"
                + " left outer join folio.CHRG_GRP_EXTNL_REF b on a.EXTNL_REF_ID = b.EXTNL_REF_ID"
                + " left outer join folio.CHRG_GRP c on b.CHRG_GRP_ID = c.CHRG_GRP_ID"
                + " left outer join folio.CHRG d on c.CHRG_GRP_ID = d.CHRG_GRP_ID"
                + " left outer join folio.CHRG_ITEM e on d.CHRG_ID = e.CHRG_ID"
                + " where a.EXTNL_REF_VAL in ('" + tcg + "')";

        Database db = new OracleDatabase(env, Database.DREAMS);
        Recordset rs = new Recordset(db.getResultSet(sql));
        for (int i = 1; i < rs.getRowCount(); i++) {

            TestReporter.assertTrue(rs.getValue("CHRG_ACTV_IN", i) == "Y", "The charge groups are active and set to [" + rs.getValue("CHRG_ACTV_IN", i) + "]");

        }
    }

    public void folioItems(String tp, String env) {
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
        // rs.print();

        for (int i = 1; i < rs.getRowCount(); i++) {

            if (rs.getValue("JDO_CLS_NM", i) == "AdditionalChargeItem") {
                TestReporter.assertTrue(rs.getValue("FOLIO_ITEM_AM", i) != "NULL", "The folio item amount is [" + rs.getValue("FOLIO_ITEM_AM", i) + "]");

            }
        }
    }

    public void tpv3Status(String env) {

        String sql = "select b.SLS_ORD_ITEM_STS_NM TPV3_STATUS"
                + " from sales_tp.sls_ord a"
                + " join sales_tp.sls_ord_item b on a.sls_ord = b.sls_ord";
        Database db = new OracleDatabase(env, Database.SALESTP);
        Recordset rs = new Recordset(db.getResultSet(sql));

        TestReporter.assertTrue(rs.getValue("TPV3_STATUS", 1) != "", "The TPV3 status is [" + rs.getValue("TPV3_STATUS", 1) + "]");

    }

    public void tcReason(String tcg, String env) {
        String sql = "select b.TC_RSN_TYP_NM,b.TC_RSN_TX, b.TC_RSN_CNTCT_NM"
                + " from res_mgmt.tc a"
                + " join res_mgmt."
                + " tc_rsn b"
                + "  on a.tc_id="
                + "  b.tc_id"
                + " where a.tc_grp_nb='" + tcg + "'";

        Database db = new OracleDatabase(env, Database.DREAMS);
        Recordset rs = new Recordset(db.getResultSet(sql));
    }
}
