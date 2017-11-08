package com.disney.api.soapServices.accommodationModule.helpers;

import com.disney.utils.Environment;
import com.disney.utils.Sleeper;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.database.Database;
import com.disney.utils.dataFactory.database.Recordset;
import com.disney.utils.dataFactory.database.SQLValidationException;
import com.disney.utils.dataFactory.database.databaseImpl.OracleDatabase;

public class AutoReinstateHelper {
    private String environment;
    private String tpID;
    private String tcID;
    private String tpsID;
    private String tcgID;

    public AutoReinstateHelper(String environment, String tpID, String tpsID, String tcgID, String tcID) {
        this.environment = environment;
        this.tpID = tpID;
        this.tpsID = tpsID;
        this.tcgID = tcgID;
        this.tcID = tcID;

    }

    public void validateReservationBookedStatus() {
        TestReporter.logStep("Verify reservation status is 'Booked'");
        String sql = "select * from res_mgmt.res_hist a where a.tp_id = '" + tpID + "'";

        Database db = new OracleDatabase(Environment.getBaseEnvironmentName(environment), Database.DREAMS);
        Recordset rs = new Recordset(db.getResultSet(sql));

        if (rs.getRowCount() == 0) {
            throw new SQLValidationException("No charges found for tp ID [ " + tpID + " ]", sql);
        }

        boolean bookFound = false;
        boolean cancelFound = false;
        boolean reinstateFound = false;
        boolean autoCancelFound = false;
        do {
            if (rs.getValue("RES_HIST_PROC_DS").equalsIgnoreCase("Booked")) {
                bookFound = true;
            } else if (rs.getValue("RES_HIST_PROC_DS").equalsIgnoreCase("Cancelled")) {
                cancelFound = true;
                TestReporter.softAssertTrue(cancelFound, "Verify that a cancel record was found for TP ID [" + tpID + "].");
            } else if (rs.getValue("RES_HIST_PROC_DS").equalsIgnoreCase("Auto Cancelled")) {
                autoCancelFound = true;
                TestReporter.softAssertTrue(autoCancelFound, "Verify that an auto cancel record was found for TP ID [" + tpID + "].");
            } else if (rs.getValue("RES_HIST_PROC_DS").equalsIgnoreCase("Reinstated")) {
                reinstateFound = true;
            }
            TestReporter.softAssertEquals(rs.getValue("TC_ID"), tcID, "Verify the TC id [" + rs.getValue("TC_ID") + "] matches the TC id in the DB [" + tcID + "].");
            TestReporter.softAssertEquals(rs.getValue("TPS_ID"), tpsID, "Verify the TPS id [" + rs.getValue("TPS_ID") + "] matches the TPS id in the DB [" + tpsID + "].");
            TestReporter.softAssertEquals(rs.getValue("TP_ID"), tpID, "Verify the TP id [" + rs.getValue("TP_ID") + "] matches the TP id in the DB [" + tpID + "].");
            TestReporter.softAssertEquals(rs.getValue("TC_GRP_NM"), tcgID, "Verify the TCG id [" + rs.getValue("TC_GRP_NM") + "] matches the TCG in the DB [" + tcgID + "].");
            rs.moveNext();
        } while (rs.hasNext());
        TestReporter.softAssertTrue(bookFound, "Verify that a book record was found for TP ID [" + tpID + "].");

    }

    public void validateCancellationNumber() {
        TestReporter.logStep("Verify cancellation number is 0");
        String sql = "select a.TRVL_STS_NM TP_STATUS, a.TPS_CNCL_NB CANCEL_NUMBER, c.TRVL_STS_NM TC_STATUS "
                + "from res_mgmt.tps a "
                + "join res_mgmt.tc_grp b on a.tps_id = b.tps_id "
                + "join res_mgmt.tc c on b.tc_grp_nb = c.tc_grp_nb "
                + "where a.tps_id = '" + tpsID + "'";

        Database db = new OracleDatabase(Environment.getBaseEnvironmentName(environment), Database.DREAMS);
        Recordset rs = new Recordset(db.getResultSet(sql));

        if (rs.getRowCount() == 0) {
            throw new SQLValidationException("No charges found for tp ID [ " + tpsID + " ]", sql);
        }

        do {
            if (rs.getValue("CANCEL_NUMBER").equals("0")) {
                TestReporter.softAssertEquals(rs.getValue("CANCEL_NUMBER"), "0", "Verify the cancel number [" + rs.getValue("CANCEL_NUMBER") + "] matches the cancel number in the DB [0].");

            }
            rs.moveNext();
        } while (rs.hasNext());
    }

    public void validateCancellationNumberFirstTPS(String firstTPS) {
        TestReporter.logStep("Verify cancellation number is 0");
        String sql = "select a.TRVL_STS_NM TP_STATUS, a.TPS_CNCL_NB CANCEL_NUMBER, c.TRVL_STS_NM TC_STATUS "
                + "from res_mgmt.tps a "
                + "join res_mgmt.tc_grp b on a.tps_id = b.tps_id "
                + "join res_mgmt.tc c on b.tc_grp_nb = c.tc_grp_nb "
                + "where a.tps_id = '" + firstTPS + "'";

        Database db = new OracleDatabase(Environment.getBaseEnvironmentName(environment), Database.DREAMS);
        Recordset rs = new Recordset(db.getResultSet(sql));

        if (rs.getRowCount() == 0) {
            throw new SQLValidationException("No charges found for tp ID [ " + tpsID + " ]", sql);
        }

        do {
            if (rs.getValue("CANCEL_NUMBER").equals("0")) {
                TestReporter.softAssertEquals(rs.getValue("CANCEL_NUMBER"), "0", "Verify the cancel number [" + rs.getValue("CANCEL_NUMBER") + "] matches the cancel number in the DB [0].");

            }
            rs.moveNext();
        } while (rs.hasNext());
    }

    public void validateCancellationNumberTwoTPS(String firstTPS) {
        TestReporter.logStep("Verify cancellation number is 0");
        String sql = "select a.TRVL_STS_NM TP_STATUS, a.TPS_CNCL_NB CANCEL_NUMBER, c.TRVL_STS_NM TC_STATUS "
                + "from res_mgmt.tps a "
                + "join res_mgmt.tc_grp b on a.tps_id = b.tps_id "
                + "join res_mgmt.tc c on b.tc_grp_nb = c.tc_grp_nb "
                + "where a.tps_id = '" + firstTPS + "'";

        Database db = new OracleDatabase(Environment.getBaseEnvironmentName(environment), Database.DREAMS);
        Recordset rs = new Recordset(db.getResultSet(sql));

        if (rs.getRowCount() == 0) {
            throw new SQLValidationException("No charges found for tp ID [ " + tpsID + " ]", sql);
        }

        do {
            if (rs.getValue("CANCEL_NUMBER").equals("0")) {
                TestReporter.softAssertEquals(rs.getValue("CANCEL_NUMBER"), "0", "Verify the cancel number [" + rs.getValue("CANCEL_NUMBER") + "] matches the cancel number in the DB [0].");

            }
            rs.moveNext();
        } while (rs.hasNext());

        String sql2 = "select a.TRVL_STS_NM TP_STATUS, a.TPS_CNCL_NB CANCEL_NUMBER, c.TRVL_STS_NM TC_STATUS "
                + "from res_mgmt.tps a "
                + "join res_mgmt.tc_grp b on a.tps_id = b.tps_id "
                + "join res_mgmt.tc c on b.tc_grp_nb = c.tc_grp_nb "
                + "where a.tps_id = '" + tpsID + "'";

        Database db2 = new OracleDatabase(Environment.getBaseEnvironmentName(environment), Database.DREAMS);
        Recordset rs2 = new Recordset(db2.getResultSet(sql2));

        if (rs2.getRowCount() == 0) {
            throw new SQLValidationException("No charges found for tp ID [ " + tpsID + " ]", sql2);
        }

        do {
            if (rs2.getValue("CANCEL_NUMBER").equals("0")) {
                TestReporter.softAssertEquals(rs2.getValue("CANCEL_NUMBER"), "0", "Verify the cancel number [" + rs2.getValue("CANCEL_NUMBER") + "] matches the cancel number in the DB [0].");

            }
            rs2.moveNext();
        } while (rs2.hasNext());
    }

    public void validateReinstateRecord() {
        TestReporter.logStep("Verify reinstate record");
        String sql = "select * from res_mgmt.res_hist a where a.tp_id = '" + tpID + "'";

        Database db = new OracleDatabase(Environment.getBaseEnvironmentName(environment), Database.DREAMS);
        Recordset rs = new Recordset(db.getResultSet(sql));

        if (rs.getRowCount() == 0) {
            throw new SQLValidationException("No charges found for tp ID [ " + tpID + " ]", sql);
        }

        do {
            if (rs.getValue("RES_HIST_PROC_DS").equalsIgnoreCase("Reinstated")) {
                TestReporter.softAssertEquals(rs.getValue("RES_HIST_PROC_DS"), "Reinstated", "Verify the status  [" + rs.getValue("RES_HIST_PROC_DS") + "] matches in the DB [Reinstated].");
                TestReporter.softAssertEquals(rs.getValue("TC_ID"), tcID, "Verify the TC id [" + rs.getValue("TC_ID") + "] matches the TC id in the DB [" + tcID + "].");
                TestReporter.softAssertEquals(rs.getValue("TPS_ID"), tpsID, "Verify the TPS id [" + rs.getValue("TPS_ID") + "] matches the TPS id in the DB [" + tpsID + "].");
                TestReporter.softAssertEquals(rs.getValue("TP_ID"), tpID, "Verify the TP id [" + rs.getValue("TP_ID") + "] matches the TP id in the DB [" + tpID + "].");
                TestReporter.softAssertEquals(rs.getValue("TC_GRP_NM"), tcgID, "Verify the TCG id [" + rs.getValue("TC_GRP_NM") + "] matches the TCG in the DB [" + tcgID + "].");
            }
            rs.moveNext();
        } while (rs.hasNext());
    }

    public void validateBookedRecord() {
        TestReporter.logStep("Verify one booked record");
        String sql = "select * from res_mgmt.res_hist a where a.tp_id = '" + tpID + "'";

        Database db = new OracleDatabase(Environment.getBaseEnvironmentName(environment), Database.DREAMS);
        Recordset rs = new Recordset(db.getResultSet(sql));

        if (rs.getRowCount() == 0) {
            throw new SQLValidationException("No charges found for tp ID [ " + tpID + " ]", sql);
        }

        do {
            if (rs.getValue("RES_HIST_PROC_DS").equalsIgnoreCase("Booked")) {
                TestReporter.softAssertEquals(rs.getValue("RES_HIST_PROC_DS"), "Booked", "Verify the status  [" + rs.getValue("RES_HIST_PROC_DS") + "] matches in the DB [Booked].");
                TestReporter.softAssertEquals(rs.getValue("TC_ID"), tcID, "Verify the TC id [" + rs.getValue("TC_ID") + "] matches the TC id in the DB [" + tcID + "].");
                TestReporter.softAssertEquals(rs.getValue("TPS_ID"), tpsID, "Verify the TPS id [" + rs.getValue("TPS_ID") + "] matches the TPS id in the DB [" + tpsID + "].");
                TestReporter.softAssertEquals(rs.getValue("TP_ID"), tpID, "Verify the TP id [" + rs.getValue("TP_ID") + "] matches the TP id in the DB [" + tpID + "].");
                TestReporter.softAssertEquals(rs.getValue("TC_GRP_NM"), tcgID, "Verify the TCG id [" + rs.getValue("TC_GRP_NM") + "] matches the TCG in the DB [" + tcgID + "].");
            }
            rs.moveNext();
        } while (rs.hasNext());
    }

    public void validateBookedRecords(String firstTP, String firstTC, String firstTPS, String firstTCG) {
        TestReporter.logStep("Verify two booked record");
        String sql = "select * from res_mgmt.res_hist a where a.tp_id = '" + firstTP + "'";

        Database db = new OracleDatabase(Environment.getBaseEnvironmentName(environment), Database.DREAMS);
        Recordset rs = new Recordset(db.getResultSet(sql));

        if (rs.getRowCount() == 0) {
            throw new SQLValidationException("No charges found for tp ID [ " + tpID + " ]", sql);
        }

        do {
            if (rs.getValue("RES_HIST_PROC_DS").equalsIgnoreCase("Booked")) {
                TestReporter.softAssertEquals(rs.getValue("RES_HIST_PROC_DS"), "Booked", "Verify the status  [" + rs.getValue("RES_HIST_PROC_DS") + "] matches in the DB [Booked].");
                TestReporter.softAssertEquals(rs.getValue("TC_ID"), firstTC, "Verify the TC id [" + rs.getValue("TC_ID") + "] matches the TC id in the DB [" + firstTC + "].");
                TestReporter.softAssertEquals(rs.getValue("TPS_ID"), firstTPS, "Verify the TPS id [" + rs.getValue("TPS_ID") + "] matches the TPS id in the DB [" + firstTPS + "].");
                TestReporter.softAssertEquals(rs.getValue("TP_ID"), firstTP, "Verify the TP id [" + rs.getValue("TP_ID") + "] matches the TP id in the DB [" + firstTP + "].");
                TestReporter.softAssertEquals(rs.getValue("TC_GRP_NM"), firstTCG, "Verify the TCG id [" + rs.getValue("TC_GRP_NM") + "] matches the TCG in the DB [" + firstTCG + "].");
            }
            rs.moveNext();
        } while (rs.hasNext());

        String sql2 = "select * from res_mgmt.res_hist a where a.tp_id = '" + tpID + "'";

        Database db2 = new OracleDatabase(Environment.getBaseEnvironmentName(environment), Database.DREAMS);
        Recordset rs2 = new Recordset(db2.getResultSet(sql2));

        if (rs2.getRowCount() == 0) {
            throw new SQLValidationException("No charges found for tp ID [ " + tpID + " ]", sql2);
        }

        do {
            if (rs2.getValue("RES_HIST_PROC_DS").equalsIgnoreCase("Booked")) {
                TestReporter.softAssertEquals(rs2.getValue("RES_HIST_PROC_DS"), "Booked", "Verify the status  [" + rs2.getValue("RES_HIST_PROC_DS") + "] matches in the DB [Booked].");
                TestReporter.softAssertEquals(rs2.getValue("TC_ID"), tcID, "Verify the TC id [" + rs2.getValue("TC_ID") + "] matches the TC id in the DB [" + tcID + "].");
                TestReporter.softAssertEquals(rs2.getValue("TPS_ID"), tpsID, "Verify the TPS id [" + rs2.getValue("TPS_ID") + "] matches the TPS id in the DB [" + tpsID + "].");
                TestReporter.softAssertEquals(rs2.getValue("TP_ID"), tpID, "Verify the TP id [" + rs2.getValue("TP_ID") + "] matches the TP id in the DB [" + tpID + "].");
                TestReporter.softAssertEquals(rs2.getValue("TC_GRP_NM"), tcgID, "Verify the TCG id [" + rs2.getValue("TC_GRP_NM") + "] matches the TCG in the DB [" + tcgID + "].");
            }
            rs.moveNext();
        } while (rs.hasNext());
    }

    public void validateRIMInventory() {
        TestReporter.logStep("Verify RIM inventory consumed");
        String sql = "select * "
                + "from res_mgmt.tc a "
                + "join rsrc_inv.RSRC_ASGN_OWNR b on a.ASGN_OWN_ID = b.ASGN_OWNR_ID "
                + "join rsrc_inv.RSRC_ASGN_REQ c on b.ASGN_OWNR_ID = c.ASGN_OWNR_ID "
                + "where a.tc_grp_nb = '" + tcgID + "'";

        Database db = new OracleDatabase(Environment.getBaseEnvironmentName(environment), Database.DREAMS);
        Recordset rs = new Recordset(db.getResultSet(sql));

        if (rs.getRowCount() == 0) {
            throw new SQLValidationException("No charges found for tp ID [ " + tcgID + " ]", sql);
        }

        TestReporter.softAssertEquals(rs.getValue("TC_ID"), tcID, "Verify the TC id [" + rs.getValue("TC_ID") + "] matches the TC id in the DB [" + tcID + "].");
        TestReporter.softAssertEquals(rs.getValue("TC_GRP_NB"), tcgID, "Verify the TCG id [" + rs.getValue("TC_GRP_NM") + "] matches the TCG in the DB [" + tcgID + "].");
        TestReporter.softAssertEquals(rs.getValue("SLS_CHAN_ID"), "1", "Verify the sales channel id [" + rs.getValue("SLS_CHAN_ID") + "] matches the sales channel id in the DB [1].");
        TestReporter.softAssertEquals(rs.getValue("TRVL_STS_NM"), "Booked", "Verify the travel status name [" + rs.getValue("TRVL_STS_NM") + "] matches the travel status name in the DB [Booked].");
        TestReporter.softAssertEquals(rs.getValue("TC_CHRG_IN"), "Y", "Verify the TC charge [" + rs.getValue("TC_CHRG_IN") + "] matches the TC charge in the DB [Y].");
        TestReporter.assertAll();
    }

    public void validateRIMInventoryReinstatedTCG(String firstTCG, String firstTC) {
        TestReporter.logStep("Verify RIM inventory consumed by reinstate TCG");
        String sql = "select * "
                + "from res_mgmt.tc a "
                + "join rsrc_inv.RSRC_ASGN_OWNR b on a.ASGN_OWN_ID = b.ASGN_OWNR_ID "
                + "join rsrc_inv.RSRC_ASGN_REQ c on b.ASGN_OWNR_ID = c.ASGN_OWNR_ID "
                + "where a.tc_grp_nb = '" + firstTCG + "'";

        Database db = new OracleDatabase(Environment.getBaseEnvironmentName(environment), Database.DREAMS);
        Recordset rs = new Recordset(db.getResultSet(sql));

        if (rs.getRowCount() == 0) {
            throw new SQLValidationException("No charges found for tp ID [ " + tcgID + " ]", sql);
        }

        TestReporter.softAssertEquals(rs.getValue("TC_ID"), firstTC, "Verify the TC id [" + rs.getValue("TC_ID") + "] matches the TC id in the DB [" + firstTC + "].");
        TestReporter.softAssertEquals(rs.getValue("TC_GRP_NB"), firstTCG, "Verify the TCG id [" + rs.getValue("TC_GRP_NM") + "] matches the TCG in the DB [" + firstTCG + "].");
        TestReporter.softAssertEquals(rs.getValue("SLS_CHAN_ID"), "1", "Verify the sales channel id [" + rs.getValue("SLS_CHAN_ID") + "] matches the sales channel id in the DB [1].");
        TestReporter.softAssertEquals(rs.getValue("TRVL_STS_NM"), "Booked", "Verify the travel status name [" + rs.getValue("TRVL_STS_NM") + "] matches the travel status name in the DB [Booked].");
        TestReporter.softAssertEquals(rs.getValue("TC_CHRG_IN"), "Y", "Verify the TC charge [" + rs.getValue("TC_CHRG_IN") + "] matches the TC charge in the DB [Y].");
        TestReporter.assertAll();
    }

    public void validateRIMInventoryTwoTCGs(String firstTCG, String firstTC) {
        TestReporter.logStep("Verify RIM inventory consumed by both reinstated TCGs");
        String sql = "select * "
                + "from res_mgmt.tc a "
                + "join rsrc_inv.RSRC_ASGN_OWNR b on a.ASGN_OWN_ID = b.ASGN_OWNR_ID "
                + "join rsrc_inv.RSRC_ASGN_REQ c on b.ASGN_OWNR_ID = c.ASGN_OWNR_ID "
                + "where a.tc_grp_nb = '" + firstTCG + "'";

        Database db = new OracleDatabase(Environment.getBaseEnvironmentName(environment), Database.DREAMS);
        Recordset rs = new Recordset(db.getResultSet(sql));

        if (rs.getRowCount() == 0) {
            throw new SQLValidationException("No charges found for tp ID [ " + firstTCG + " ]", sql);
        }

        TestReporter.softAssertEquals(rs.getValue("TC_ID"), firstTC, "Verify the TC id [" + rs.getValue("TC_ID") + "] matches the TC id in the DB [" + firstTC + "].");
        TestReporter.softAssertEquals(rs.getValue("TC_GRP_NB"), firstTCG, "Verify the TCG id [" + rs.getValue("TC_GRP_NM") + "] matches the TCG in the DB [" + firstTCG + "].");
        TestReporter.softAssertEquals(rs.getValue("SLS_CHAN_ID"), "1", "Verify the sales channel id [" + rs.getValue("SLS_CHAN_ID") + "] matches the sales channel id in the DB [1].");
        TestReporter.softAssertEquals(rs.getValue("TRVL_STS_NM"), "Booked", "Verify the travel status name [" + rs.getValue("TRVL_STS_NM") + "] matches the travel status name in the DB [Booked].");
        TestReporter.softAssertEquals(rs.getValue("TC_CHRG_IN"), "Y", "Verify the TC charge [" + rs.getValue("TC_CHRG_IN") + "] matches the TC charge in the DB [Y].");
        TestReporter.assertAll();

        String sql2 = "select * "
                + "from res_mgmt.tc a "
                + "join rsrc_inv.RSRC_ASGN_OWNR b on a.ASGN_OWN_ID = b.ASGN_OWNR_ID "
                + "join rsrc_inv.RSRC_ASGN_REQ c on b.ASGN_OWNR_ID = c.ASGN_OWNR_ID "
                + "where a.tc_grp_nb = '" + tcgID + "'";

        Database db2 = new OracleDatabase(Environment.getBaseEnvironmentName(environment), Database.DREAMS);
        Recordset rs2 = new Recordset(db2.getResultSet(sql2));

        if (rs2.getRowCount() == 0) {
            throw new SQLValidationException("No charges found for tp ID [ " + tcgID + " ]", sql2);
        }

        TestReporter.softAssertEquals(rs2.getValue("TC_ID"), tcID, "Verify the TC id [" + rs2.getValue("TC_ID") + "] matches the TC id in the DB [" + tcID + "].");
        TestReporter.softAssertEquals(rs2.getValue("TC_GRP_NB"), tcgID, "Verify the TCG id [" + rs2.getValue("TC_GRP_NM") + "] matches the TCG in the DB [" + tcgID + "].");
        TestReporter.softAssertEquals(rs2.getValue("SLS_CHAN_ID"), "1", "Verify the sales channel id [" + rs2.getValue("SLS_CHAN_ID") + "] matches the sales channel id in the DB [1].");
        TestReporter.softAssertEquals(rs2.getValue("TRVL_STS_NM"), "Booked", "Verify the travel status name [" + rs2.getValue("TRVL_STS_NM") + "] matches the travel status name in the DB [Booked].");
        TestReporter.softAssertEquals(rs2.getValue("TC_CHRG_IN"), "Y", "Verify the TC charge [" + rs2.getValue("TC_CHRG_IN") + "] matches the TC charge in the DB [Y].");
        TestReporter.assertAll();
    }

    public void validateChargeGroups() {
        TestReporter.logStep("Verify charge groups are active");
        String sql = "select c.CHRG_GRP_STS_NM "
                + "from folio.EXTNL_REF a "
                + "left outer join folio.CHRG_GRP_EXTNL_REF b on a.EXTNL_REF_ID = b.EXTNL_REF_ID "
                + "left outer join folio.CHRG_GRP c on b.CHRG_GRP_ID = c.CHRG_GRP_ID "
                + "where a.EXTNL_REF_VAL in ('" + tpID + "', '" + tpsID + "', '" + tcgID + "')";

        Database db = new OracleDatabase(Environment.getBaseEnvironmentName(environment), Database.DREAMS);
        Recordset rs = new Recordset(db.getResultSet(sql));

        if (rs.getRowCount() == 0) {
            throw new SQLValidationException("No charges found for tp ID [ " + tcgID + " ]", sql);
        }

        do {
            TestReporter.softAssertEquals(rs.getValue("CHRG_GRP_STS_NM"), "UnEarned", "Verify the status  [" + rs.getValue("CHRG_GRP_STS_NM") + "] matches in the DB [UnEarned].");

            rs.moveNext();
        } while (rs.hasNext());
        TestReporter.assertAll();
    }

    public void validateTwoBookedChargeGroups(String firstTP, String firstTPS, String firstTCG) {
        TestReporter.logStep("Verify charge groups are active for the reinstated TCG and cancelled for the cancelled TCG");
        String sql = "select c.CHRG_GRP_STS_NM "
                + "from folio.EXTNL_REF a "
                + "left outer join folio.CHRG_GRP_EXTNL_REF b on a.EXTNL_REF_ID = b.EXTNL_REF_ID "
                + "left outer join folio.CHRG_GRP c on b.CHRG_GRP_ID = c.CHRG_GRP_ID "
                + "where a.EXTNL_REF_VAL in ('" + firstTP + "', '" + firstTPS + "', '" + firstTCG + "')";

        Database db = new OracleDatabase(Environment.getBaseEnvironmentName(environment), Database.DREAMS);
        Recordset rs = new Recordset(db.getResultSet(sql));

        if (rs.getRowCount() == 0) {
            throw new SQLValidationException("No charges found for tp ID [ " + firstTCG + " ]", sql);
        }

        do {
            TestReporter.softAssertEquals(rs.getValue("CHRG_GRP_STS_NM"), "UnEarned", "Verify the status  [" + rs.getValue("CHRG_GRP_STS_NM") + "] matches in the DB [UnEarned].");

            rs.moveNext();
        } while (rs.hasNext());
        TestReporter.assertAll();

        String sql2 = "select c.CHRG_GRP_STS_NM "
                + "from folio.EXTNL_REF a "
                + "left outer join folio.CHRG_GRP_EXTNL_REF b on a.EXTNL_REF_ID = b.EXTNL_REF_ID "
                + "left outer join folio.CHRG_GRP c on b.CHRG_GRP_ID = c.CHRG_GRP_ID "
                + "where a.EXTNL_REF_VAL in ('" + tpID + "', '" + tpsID + "', '" + tcgID + "')";

        Database db2 = new OracleDatabase(Environment.getBaseEnvironmentName(environment), Database.DREAMS);
        Recordset rs2 = new Recordset(db2.getResultSet(sql2));

        if (rs2.getRowCount() == 0) {
            throw new SQLValidationException("No charges found for tp ID [ " + tcgID + " ]", sql2);
        }

        do {
            TestReporter.softAssertEquals(rs2.getValue("CHRG_GRP_STS_NM"), "Cancelled", "Verify the status  [" + rs2.getValue("CHRG_GRP_STS_NM") + "] matches in the DB [Cancelled].");

            rs2.moveNext();
        } while (rs2.hasNext());
        TestReporter.assertAll();
    }

    public void validateTwoBookedChargeGroupsBothUnEarned(String firstTP, String firstTPS, String firstTCG) {
        TestReporter.logStep("Verify charge groups are active for both reinstated TCG's");
        String sql = "select c.CHRG_GRP_STS_NM "
                + "from folio.EXTNL_REF a "
                + "left outer join folio.CHRG_GRP_EXTNL_REF b on a.EXTNL_REF_ID = b.EXTNL_REF_ID "
                + "left outer join folio.CHRG_GRP c on b.CHRG_GRP_ID = c.CHRG_GRP_ID "
                + "where a.EXTNL_REF_VAL in ('" + firstTP + "', '" + firstTPS + "', '" + firstTCG + "')";

        Database db = new OracleDatabase(Environment.getBaseEnvironmentName(environment), Database.DREAMS);
        Recordset rs = new Recordset(db.getResultSet(sql));

        if (rs.getRowCount() == 0) {
            throw new SQLValidationException("No charges found for tp ID [ " + firstTCG + " ]", sql);
        }

        do {
            TestReporter.softAssertEquals(rs.getValue("CHRG_GRP_STS_NM"), "UnEarned", "Verify the status  [" + rs.getValue("CHRG_GRP_STS_NM") + "] matches in the DB [UnEarned].");

            rs.moveNext();
        } while (rs.hasNext());
        TestReporter.assertAll();

        String sql2 = "select c.CHRG_GRP_STS_NM "
                + "from folio.EXTNL_REF a "
                + "left outer join folio.CHRG_GRP_EXTNL_REF b on a.EXTNL_REF_ID = b.EXTNL_REF_ID "
                + "left outer join folio.CHRG_GRP c on b.CHRG_GRP_ID = c.CHRG_GRP_ID "
                + "where a.EXTNL_REF_VAL in ('" + tpID + "', '" + tpsID + "', '" + tcgID + "')";

        Database db2 = new OracleDatabase(Environment.getBaseEnvironmentName(environment), Database.DREAMS);
        Recordset rs2 = new Recordset(db2.getResultSet(sql2));

        if (rs2.getRowCount() == 0) {
            throw new SQLValidationException("No charges found for tp ID [ " + tcgID + " ]", sql2);
        }

        do {
            TestReporter.softAssertEquals(rs2.getValue("CHRG_GRP_STS_NM"), "UnEarned", "Verify the status  [" + rs2.getValue("CHRG_GRP_STS_NM") + "] matches in the DB [UnEarned].");

            rs2.moveNext();
        } while (rs2.hasNext());
        TestReporter.assertAll();
    }

    public void validateChargeItems() {
        TestReporter.logStep("Verify charge items");
        String sql = "select d.* "
                + "from folio.EXTNL_REF a "
                + "left outer join folio.CHRG_GRP_EXTNL_REF b on a.EXTNL_REF_ID = b.EXTNL_REF_ID "
                + "left outer join folio.CHRG_GRP c on b.CHRG_GRP_ID = c.CHRG_GRP_ID "
                + "left outer join folio.CHRG d on c.CHRG_GRP_ID = d.CHRG_GRP_ID "
                + "left outer join folio.CHRG_ITEM e on d.CHRG_ID = e.CHRG_ID "
                + "where a.EXTNL_REF_VAL in '" + tcgID + "'";
        Database db = new OracleDatabase(Environment.getBaseEnvironmentName(environment), Database.DREAMS);
        Recordset rs = new Recordset(db.getResultSet(sql));

        if (rs.getRowCount() == 0) {
            throw new SQLValidationException("No charges found for tp ID [ " + tcgID + " ]", sql);
        }

        do {
            if (rs.getValue("CHRG_TYP_NM").equalsIgnoreCase("Fee Charge")) {
                TestReporter.softAssertEquals(rs.getValue("CHRG_PST_ST_NM"), "UnEarned", "Verify the status  [" + rs.getValue("CHRG_PST_ST_NM") + "] matches in the DB [UnEarned].");
                TestReporter.softAssertEquals(rs.getValue("RECOG_STS_NM"), "APPROVED", "Verify the recognition status  [" + rs.getValue("RECOG_STS_NM") + "] matches in the DB [APPROVED].");
                TestReporter.softAssertEquals(rs.getValue("CHRG_ACTV_IN"), "Y", "Verify the charge is active [" + rs.getValue("CHRG_ACTV_IN") + "] matches in the DB [Y].");

            } else if (rs.getValue("CHRG_TYP_NM").equalsIgnoreCase("Product Charge")) {
                TestReporter.softAssertEquals(rs.getValue("CHRG_PST_ST_NM"), "UnEarned", "Verify the status  [" + rs.getValue("CHRG_PST_ST_NM") + "] matches in the DB [UnEarned].");
                TestReporter.softAssertEquals(rs.getValue("RECOG_STS_NM"), "PENDING", "Verify the status  [" + rs.getValue("RECOG_STS_NM") + "] matches in the DB [PENDING].");
                TestReporter.softAssertEquals(rs.getValue("CHRG_ACTV_IN"), "Y", "Verify the charge is active [" + rs.getValue("CHRG_ACTV_IN") + "] matches in the DB [Y].");

            }

            rs.moveNext();
        } while (rs.hasNext());
        TestReporter.assertAll();

    }

    public void validateChargeItemsTwoTCG(String firstTCG) {
        TestReporter.logStep("Verify charge items for reinstated and cancelled tcg");
        String sql = "select d.* "
                + "from folio.EXTNL_REF a "
                + "left outer join folio.CHRG_GRP_EXTNL_REF b on a.EXTNL_REF_ID = b.EXTNL_REF_ID "
                + "left outer join folio.CHRG_GRP c on b.CHRG_GRP_ID = c.CHRG_GRP_ID "
                + "left outer join folio.CHRG d on c.CHRG_GRP_ID = d.CHRG_GRP_ID "
                + "left outer join folio.CHRG_ITEM e on d.CHRG_ID = e.CHRG_ID "
                + "where a.EXTNL_REF_VAL in '" + firstTCG + "'";
        Database db = new OracleDatabase(Environment.getBaseEnvironmentName(environment), Database.DREAMS);
        Recordset rs = new Recordset(db.getResultSet(sql));

        if (rs.getRowCount() == 0) {
            throw new SQLValidationException("No charges found for tp ID [ " + firstTCG + " ]", sql);
        }

        do {
            if (rs.getValue("CHRG_TYP_NM").equalsIgnoreCase("Fee Charge")) {
                TestReporter.softAssertEquals(rs.getValue("CHRG_PST_ST_NM"), "UnEarned", "Verify the status  [" + rs.getValue("CHRG_PST_ST_NM") + "] matches in the DB [UnEarned].");
                TestReporter.softAssertEquals(rs.getValue("RECOG_STS_NM"), "APPROVED", "Verify the recognition status  [" + rs.getValue("RECOG_STS_NM") + "] matches in the DB [APPROVED].");
                TestReporter.softAssertEquals(rs.getValue("CHRG_ACTV_IN"), "Y", "Verify the charge is active [" + rs.getValue("CHRG_ACTV_IN") + "] matches in the DB [Y].");

            } else if (rs.getValue("CHRG_TYP_NM").equalsIgnoreCase("Product Charge")) {
                TestReporter.softAssertEquals(rs.getValue("CHRG_PST_ST_NM"), "UnEarned", "Verify the status  [" + rs.getValue("CHRG_PST_ST_NM") + "] matches in the DB [UnEarned].");
                TestReporter.softAssertEquals(rs.getValue("RECOG_STS_NM"), "PENDING", "Verify the status  [" + rs.getValue("RECOG_STS_NM") + "] matches in the DB [PENDING].");
                TestReporter.softAssertEquals(rs.getValue("CHRG_ACTV_IN"), "Y", "Verify the charge is active [" + rs.getValue("CHRG_ACTV_IN") + "] matches in the DB [Y].");

            }

            rs.moveNext();
        } while (rs.hasNext());
        TestReporter.assertAll();

        // validate second tcg
        String sql2 = "select d.* "
                + "from folio.EXTNL_REF a "
                + "left outer join folio.CHRG_GRP_EXTNL_REF b on a.EXTNL_REF_ID = b.EXTNL_REF_ID "
                + "left outer join folio.CHRG_GRP c on b.CHRG_GRP_ID = c.CHRG_GRP_ID "
                + "left outer join folio.CHRG d on c.CHRG_GRP_ID = d.CHRG_GRP_ID "
                + "left outer join folio.CHRG_ITEM e on d.CHRG_ID = e.CHRG_ID "
                + "where a.EXTNL_REF_VAL in '" + tcgID + "'";
        Database db2 = new OracleDatabase(Environment.getBaseEnvironmentName(environment), Database.DREAMS);
        Recordset rs2 = new Recordset(db2.getResultSet(sql2));

        if (rs2.getRowCount() == 0) {
            throw new SQLValidationException("No charges found for tp ID [ " + tcgID + " ]", sql2);
        }

        do {
            if (rs2.getValue("CHRG_TYP_NM").equalsIgnoreCase("Fee Charge")) {
                TestReporter.softAssertEquals(rs2.getValue("CHRG_PST_ST_NM"), "UnEarned", "Verify the status  [" + rs2.getValue("CHRG_PST_ST_NM") + "] matches in the DB [Cancelled].");
                TestReporter.softAssertEquals(rs2.getValue("RECOG_STS_NM"), "APPROVED", "Verify the recognition status  [" + rs2.getValue("RECOG_STS_NM") + "] matches in the DB [APPROVED].");
                TestReporter.softAssertEquals(rs2.getValue("CHRG_ACTV_IN"), "Y", "Verify the charge is active [" + rs2.getValue("CHRG_ACTV_IN") + "] matches in the DB [Y].");

            } else if (rs2.getValue("CHRG_TYP_NM").equalsIgnoreCase("Product Charge")) {
                TestReporter.softAssertEquals(rs2.getValue("CHRG_PST_ST_NM"), "UnEarned", "Verify the status  [" + rs2.getValue("CHRG_PST_ST_NM") + "] matches in the DB [UnEarned].");
                TestReporter.softAssertEquals(rs2.getValue("RECOG_STS_NM"), "PENDING", "Verify the status  [" + rs2.getValue("RECOG_STS_NM") + "] matches in the DB [PENDING].");
                TestReporter.softAssertEquals(rs2.getValue("CHRG_ACTV_IN"), "N", "Verify the charge is active [" + rs2.getValue("CHRG_ACTV_IN") + "] matches in the DB [Y].");

            }

            rs.moveNext();
        } while (rs.hasNext());
        TestReporter.assertAll();

    }

    public void validateChargeItemsTwoTCGs(String firstTCG) {
        TestReporter.logStep("Verify charge items for reinstated and cancelled tcg");
        String sql = "select d.* "
                + "from folio.EXTNL_REF a "
                + "left outer join folio.CHRG_GRP_EXTNL_REF b on a.EXTNL_REF_ID = b.EXTNL_REF_ID "
                + "left outer join folio.CHRG_GRP c on b.CHRG_GRP_ID = c.CHRG_GRP_ID "
                + "left outer join folio.CHRG d on c.CHRG_GRP_ID = d.CHRG_GRP_ID "
                + "left outer join folio.CHRG_ITEM e on d.CHRG_ID = e.CHRG_ID "
                + "where a.EXTNL_REF_VAL in '" + firstTCG + "'";
        Database db = new OracleDatabase(Environment.getBaseEnvironmentName(environment), Database.DREAMS);
        Recordset rs = new Recordset(db.getResultSet(sql));

        if (rs.getRowCount() == 0) {
            throw new SQLValidationException("No charges found for tp ID [ " + firstTCG + " ]", sql);
        }

        do {
            if (rs.getValue("CHRG_TYP_NM").equalsIgnoreCase("Fee Charge")) {
                TestReporter.softAssertEquals(rs.getValue("CHRG_PST_ST_NM"), "UnEarned", "Verify the status  [" + rs.getValue("CHRG_PST_ST_NM") + "] matches in the DB [UnEarned].");
                TestReporter.softAssertEquals(rs.getValue("RECOG_STS_NM"), "APPROVED", "Verify the recognition status  [" + rs.getValue("RECOG_STS_NM") + "] matches in the DB [APPROVED].");
                TestReporter.softAssertEquals(rs.getValue("CHRG_ACTV_IN"), "Y", "Verify the charge is active [" + rs.getValue("CHRG_ACTV_IN") + "] matches in the DB [Y].");

            } else if (rs.getValue("CHRG_TYP_NM").equalsIgnoreCase("Product Charge")) {
                TestReporter.softAssertEquals(rs.getValue("CHRG_PST_ST_NM"), "UnEarned", "Verify the status  [" + rs.getValue("CHRG_PST_ST_NM") + "] matches in the DB [UnEarned].");
                TestReporter.softAssertEquals(rs.getValue("RECOG_STS_NM"), "PENDING", "Verify the status  [" + rs.getValue("RECOG_STS_NM") + "] matches in the DB [PENDING].");
                TestReporter.softAssertEquals(rs.getValue("CHRG_ACTV_IN"), "Y", "Verify the charge is active [" + rs.getValue("CHRG_ACTV_IN") + "] matches in the DB [Y].");

            }

            rs.moveNext();
        } while (rs.hasNext());
        TestReporter.assertAll();

        // validate second tcg
        String sql2 = "select d.* "
                + "from folio.EXTNL_REF a "
                + "left outer join folio.CHRG_GRP_EXTNL_REF b on a.EXTNL_REF_ID = b.EXTNL_REF_ID "
                + "left outer join folio.CHRG_GRP c on b.CHRG_GRP_ID = c.CHRG_GRP_ID "
                + "left outer join folio.CHRG d on c.CHRG_GRP_ID = d.CHRG_GRP_ID "
                + "left outer join folio.CHRG_ITEM e on d.CHRG_ID = e.CHRG_ID "
                + "where a.EXTNL_REF_VAL in '" + tcgID + "'";
        Database db2 = new OracleDatabase(Environment.getBaseEnvironmentName(environment), Database.DREAMS);
        Recordset rs2 = new Recordset(db2.getResultSet(sql2));

        if (rs2.getRowCount() == 0) {
            throw new SQLValidationException("No charges found for tp ID [ " + tcgID + " ]", sql2);
        }

        do {
            if (rs2.getValue("CHRG_TYP_NM").equalsIgnoreCase("Fee Charge")) {
                TestReporter.softAssertEquals(rs2.getValue("CHRG_PST_ST_NM"), "UnEarned", "Verify the status  [" + rs2.getValue("CHRG_PST_ST_NM") + "] matches in the DB [Cancelled].");
                TestReporter.softAssertEquals(rs2.getValue("RECOG_STS_NM"), "APPROVED", "Verify the recognition status  [" + rs2.getValue("RECOG_STS_NM") + "] matches in the DB [APPROVED].");
                TestReporter.softAssertEquals(rs2.getValue("CHRG_ACTV_IN"), "Y", "Verify the charge is active [" + rs2.getValue("CHRG_ACTV_IN") + "] matches in the DB [Y].");

            } else if (rs2.getValue("CHRG_TYP_NM").equalsIgnoreCase("Product Charge")) {
                TestReporter.softAssertEquals(rs2.getValue("CHRG_PST_ST_NM"), "UnEarned", "Verify the status  [" + rs2.getValue("CHRG_PST_ST_NM") + "] matches in the DB [UnEarned].");
                TestReporter.softAssertEquals(rs2.getValue("RECOG_STS_NM"), "PENDING", "Verify the status  [" + rs2.getValue("RECOG_STS_NM") + "] matches in the DB [PENDING].");
                TestReporter.softAssertEquals(rs2.getValue("CHRG_ACTV_IN"), "Y", "Verify the charge is active [" + rs2.getValue("CHRG_ACTV_IN") + "] matches in the DB [Y].");

            }

            rs.moveNext();
        } while (rs.hasNext());
        TestReporter.assertAll();

    }

    public void validateFolioItems(int numExpectedRecords) {
        TestReporter.logStep("Verify folio items");
        String sql = "select h.* "
                + "from folio.EXTNL_REF a "
                + "left outer join folio.CHRG_GRP_EXTNL_REF b on a.EXTNL_REF_ID = b.EXTNL_REF_ID "
                + "left outer join folio.CHRG_GRP c on b.CHRG_GRP_ID = c.CHRG_GRP_ID "
                + "left outer join folio.CHRG d on c.CHRG_GRP_ID = d.CHRG_GRP_ID "
                + "left outer join folio.CHRG_ITEM e on d.CHRG_ID = e.CHRG_ID "
                + "left outer join folio.CHRG_GRP_FOLIO f on c.CHRG_GRP_ID = f.ROOT_CHRG_GRP_ID "
                + "left outer join folio.FOLIO g on f.CHRG_GRP_FOLIO_ID = g.FOLIO_ID "
                + "left outer join folio.FOLIO_ITEM h on g.FOLIO_ID = h.FOLIO_ID "
                + "where a.EXTNL_REF_VAL in '" + tpID + "'";

        Database db = new OracleDatabase(Environment.getBaseEnvironmentName(environment), Database.DREAMS);
        Recordset rs = new Recordset(db.getResultSet(sql));

        if (rs.getRowCount() == 0) {
            throw new SQLValidationException("No charges found for tp ID [ " + tcgID + " ]", sql);
        }

        // Verify that the actual number of records is that which is expected
        TestReporter.softAssertEquals(rs.getRowCount(), numExpectedRecords, "Verify that the number of records [" + rs.getRowCount() + "] is that which is expected [" + numExpectedRecords + "].");
        TestReporter.assertAll();
    }

    public void validateTPV3Data() {
        TestReporter.logStep("Verify TPV3 is in Booked status");
        String sql = "select b.SLS_ORD_ITEM_STS_NM TPV3_STATUS "
                + "from sales_tp.sls_ord a "
                + "join sales_tp.sls_ord_item b on a.sls_ord = b.sls_ord "
                + "where a.tp_id = " + tpID;

        Database db = new OracleDatabase(Environment.getBaseEnvironmentName(environment), Database.SALESTP);
        Recordset rs = null;

        int tries = 0;
        int maxTries = 35;
        boolean success = false;
        do {
            Sleeper.sleep(1000);
            rs = new Recordset(db.getResultSet(sql));
            tries++;
            if (rs.getRowCount() > 0 && rs.getValue("TPV3_STATUS").equalsIgnoreCase("Booked")) {
                success = true;
            }
        } while (tries < maxTries && success);

        if (rs.getRowCount() == 0) {
            throw new SQLValidationException("No charges found for tp ID [ " + tcgID + " ]", sql);
        }

        do {
            TestReporter.softAssertEquals(rs.getValue("TPV3_STATUS"), "Booked", "Verify the status  [" + rs.getValue("TPV3_STATUS") + "] matches in the DB [Booked].");
            rs.moveNext();
        } while (rs.hasNext());
        TestReporter.assertAll();
    }

    public void validateComponentsBooked() {
        TestReporter.logStep("Verify all components are booked");
        String sql = "select a.TRVL_STS_NM TP_STATUS, a.TPS_CNCL_NB CANCEL_NUMBER, c.TRVL_STS_NM TC_STATUS "
                + "from res_mgmt.tps a "
                + "join res_mgmt.tc_grp b on a.tps_id = b.tps_id "
                + "join res_mgmt.tc c on b.tc_grp_nb = c.tc_grp_nb "
                + "where a.tps_id = '" + tpsID + "'";

        Database db = new OracleDatabase(Environment.getBaseEnvironmentName(environment), Database.DREAMS);
        Recordset rs = new Recordset(db.getResultSet(sql));

        if (rs.getRowCount() == 0) {
            throw new SQLValidationException("No charges found for tp ID [ " + tcgID + " ]", sql);
        }

        do {
            TestReporter.softAssertEquals(rs.getValue("TP_STATUS"), "Booked", "Verify TP status [" + rs.getValue("TP_STATUS") + "] matches in the DB [Booked].");
            TestReporter.softAssertEquals(rs.getValue("TC_STATUS"), "Booked", "Verify TC status [" + rs.getValue("TC_STATUS") + "] matches in the DB [Booked].");
            rs.moveNext();
        } while (rs.hasNext());
        TestReporter.assertAll();
    }

    public void validateAdmissionComponent() {
        TestReporter.logStep("Verify admissionComponent is booked");
        String sql = "select a.TRVL_STS_NM TP_STATUS, a.TPS_CNCL_NB CANCEL_NUMBER, c.TRVL_STS_NM TC_STATUS, c.TC_TYP_NM "
                + "from res_mgmt.tps a "
                + "join res_mgmt.tc_grp b on a.tps_id = b.tps_id "
                + "join res_mgmt.tc c on b.tc_grp_nb = c.tc_grp_nb "
                + "where a.tps_id = '" + tpsID + "'";

        Database db = new OracleDatabase(Environment.getBaseEnvironmentName(environment), Database.DREAMS);
        Recordset rs = new Recordset(db.getResultSet(sql));

        if (rs.getRowCount() == 0) {
            throw new SQLValidationException("No charges found for tp ID [ " + tpsID + " ]", sql);
        }

        do {

            if (rs.getValue("TC_TYP_NM").equalsIgnoreCase("AdmissionComponent")) {
                TestReporter.softAssertEquals(rs.getValue("TP_STATUS"), "Booked", "Verify TP status [" + rs.getValue("TP_STATUS") + "] matches in the DB [Booked].");
                TestReporter.softAssertEquals(rs.getValue("TC_STATUS"), "Booked", "Verify TC status [" + rs.getValue("TC_STATUS") + "] matches in the DB [Booked].");

            }
            rs.moveNext();
        } while (rs.hasNext());
        TestReporter.assertAll();

    }

    public void validateCancelFeeWaived() {
        TestReporter.logStep("Verify that there is no cancel fee.");
        String sql = "select d.*, e.* "
                + "from folio.EXTNL_REF a "
                + "left outer join folio.CHRG_GRP_EXTNL_REF b on a.EXTNL_REF_ID = b.EXTNL_REF_ID "
                + "left outer join folio.CHRG_GRP c on b.CHRG_GRP_ID = c.CHRG_GRP_ID "
                + "left outer join folio.CHRG d on c.CHRG_GRP_ID = d.CHRG_GRP_ID "
                + "left outer join folio.CHRG_ITEM e on d.CHRG_ID = e.CHRG_ID "
                + "left outer join folio.NODE_CHRG_GRP l on c.CHRG_GRP_ID = l.NODE_CHRG_GRP_ID "
                + "where a.EXTNL_REF_VAL = '" + tcgID + "'";

        Database db = new OracleDatabase(Environment.getBaseEnvironmentName(environment), Database.DREAMS);
        Recordset rs = new Recordset(db.getResultSet(sql));

        if (rs.getRowCount() == 0) {
            throw new SQLValidationException("No charges found for tp ID [ " + tcgID + " ]", sql);
        }

        do {
            TestReporter.softAssertTrue(rs.getValue("CHRG_TYP_NM") != "Fee Charge", "Verify that the charge type name [" + rs.getValue("CHRG_TYP_NM") + "] does not equal [Fee Charge], cancallation fee was waived.");
            TestReporter.softAssertTrue(rs.getValue("CHRG_DS") != "Cancellation Fee", "Verify that the charge type [" + rs.getValue("CHRG_DS") + "] does not equal [Cancellation Fee], cancallation fee was waived.");
            rs.moveNext();
        } while (rs.hasNext());
        TestReporter.assertAll();

    }

    public void validateCancelFeeNotWaived() {
        TestReporter.logStep("Verify that there is a cancel fee.");
        String sql = "select d.*, e.* "
                + "from folio.EXTNL_REF a "
                + "left outer join folio.CHRG_GRP_EXTNL_REF b on a.EXTNL_REF_ID = b.EXTNL_REF_ID "
                + "left outer join folio.CHRG_GRP c on b.CHRG_GRP_ID = c.CHRG_GRP_ID "
                + "left outer join folio.CHRG d on c.CHRG_GRP_ID = d.CHRG_GRP_ID "
                + "left outer join folio.CHRG_ITEM e on d.CHRG_ID = e.CHRG_ID "
                + "left outer join folio.NODE_CHRG_GRP l on c.CHRG_GRP_ID = l.NODE_CHRG_GRP_ID "
                + "where a.EXTNL_REF_VAL = '" + tcgID + "'";

        Database db = new OracleDatabase(Environment.getBaseEnvironmentName(environment), Database.DREAMS);
        Recordset rs = new Recordset(db.getResultSet(sql));

        if (rs.getRowCount() == 0) {
            throw new SQLValidationException("No charges found for tp ID [ " + tcgID + " ]", sql);
        }

        do {

            if (rs.getValue("CHRG_TYP_NM").equals("Fee Charge")) {
                TestReporter.softAssertEquals(rs.getValue("CHRG_TYP_NM"), "Fee Charge", "Verify that the charge type name [" + rs.getValue("CHRG_TYP_NM") + "] matches what is expected [Fee Charge]");
                TestReporter.softAssertEquals(rs.getValue("CHRG_DS"), "Cancellation Fee", "Verify that the charge type [" + rs.getValue("CHRG_DS") + "] matches what is expected [Cancellation Fee]");
            }
            rs.moveNext();
        } while (rs.hasNext());
        TestReporter.assertAll();

    }

    public void validateReinstatedTravelComponent(String firstTPS) {
        TestReporter.logStep("Verify the reinstated travel component is in booked status.");
        String sql = "select a.TRVL_STS_NM TP_STATUS, a.TPS_CNCL_NB CANCEL_NUMBER, c.TRVL_STS_NM TC_STATUS, c.TC_TYP_NM "
                + "from res_mgmt.tps a "
                + "join res_mgmt.tc_grp b on a.tps_id = b.tps_id "
                + "join res_mgmt.tc c on b.tc_grp_nb = c.tc_grp_nb "
                + "where a.tps_id = '" + firstTPS + "'";

        Database db = new OracleDatabase(Environment.getBaseEnvironmentName(environment), Database.DREAMS);
        Recordset rs = new Recordset(db.getResultSet(sql));

        if (rs.getRowCount() == 0) {
            throw new SQLValidationException("No charges found for tp ID [ " + tpsID + " ]", sql);
        }

        do {

            TestReporter.softAssertEquals(rs.getValue("TP_STATUS"), "Booked", "Verify TP status [" + rs.getValue("TP_STATUS") + "] matches in the DB [Booked].");
            TestReporter.softAssertEquals(rs.getValue("TC_STATUS"), "Booked", "Verify TC status [" + rs.getValue("TC_STATUS") + "] matches in the DB [Booked].");

            rs.moveNext();
        } while (rs.hasNext());
        TestReporter.assertAll();

    }

    public void validateAllReinstatedTravelComponents(String firstTPS) {
        TestReporter.logStep("Verify both reinstated travel components are in booked status.");
        String sql = "select a.TRVL_STS_NM TP_STATUS, a.TPS_CNCL_NB CANCEL_NUMBER, c.TRVL_STS_NM TC_STATUS, c.TC_TYP_NM "
                + "from res_mgmt.tps a "
                + "join res_mgmt.tc_grp b on a.tps_id = b.tps_id "
                + "join res_mgmt.tc c on b.tc_grp_nb = c.tc_grp_nb "
                + "where a.tps_id = '" + firstTPS + "'";

        Database db = new OracleDatabase(Environment.getBaseEnvironmentName(environment), Database.DREAMS);
        Recordset rs = new Recordset(db.getResultSet(sql));

        if (rs.getRowCount() == 0) {
            throw new SQLValidationException("No charges found for tp ID [ " + tpsID + " ]", sql);
        }

        do {

            TestReporter.softAssertEquals(rs.getValue("TP_STATUS"), "Booked", "Verify TP status [" + rs.getValue("TP_STATUS") + "] matches in the DB [Booked].");
            TestReporter.softAssertEquals(rs.getValue("TC_STATUS"), "Booked", "Verify TC status [" + rs.getValue("TC_STATUS") + "] matches in the DB [Booked].");

            rs.moveNext();
        } while (rs.hasNext());
        TestReporter.assertAll();

        String sql2 = "select a.TRVL_STS_NM TP_STATUS, a.TPS_CNCL_NB CANCEL_NUMBER, c.TRVL_STS_NM TC_STATUS, c.TC_TYP_NM "
                + "from res_mgmt.tps a "
                + "join res_mgmt.tc_grp b on a.tps_id = b.tps_id "
                + "join res_mgmt.tc c on b.tc_grp_nb = c.tc_grp_nb "
                + "where a.tps_id = '" + tpsID + "'";

        Database db2 = new OracleDatabase(Environment.getBaseEnvironmentName(environment), Database.DREAMS);
        Recordset rs2 = new Recordset(db2.getResultSet(sql2));

        if (rs2.getRowCount() == 0) {
            throw new SQLValidationException("No charges found for tp ID [ " + tpsID + " ]", sql2);
        }

        do {

            TestReporter.softAssertEquals(rs2.getValue("TP_STATUS"), "Booked", "Verify TP status [" + rs2.getValue("TP_STATUS") + "] matches in the DB [Booked].");
            TestReporter.softAssertEquals(rs2.getValue("TC_STATUS"), "Booked", "Verify TC status [" + rs2.getValue("TC_STATUS") + "] matches in the DB [Booked].");

            rs2.moveNext();
        } while (rs2.hasNext());
        TestReporter.assertAll();

    }

    public void validateCancelledTravelComponent() {
        TestReporter.logStep("Verify the cancelled travel component is in a cancelled status.");
        String sql = "select a.TRVL_STS_NM TP_STATUS, a.TPS_CNCL_NB CANCEL_NUMBER, c.TRVL_STS_NM TC_STATUS, c.TC_TYP_NM "
                + "from res_mgmt.tps a "
                + "join res_mgmt.tc_grp b on a.tps_id = b.tps_id "
                + "join res_mgmt.tc c on b.tc_grp_nb = c.tc_grp_nb "
                + "where a.tps_id = '" + tpsID + "'";

        Database db = new OracleDatabase(Environment.getBaseEnvironmentName(environment), Database.DREAMS);
        Recordset rs = new Recordset(db.getResultSet(sql));

        if (rs.getRowCount() == 0) {
            throw new SQLValidationException("No charges found for tp ID [ " + tpsID + " ]", sql);
        }

        do {
            TestReporter.softAssertEquals(rs.getValue("TP_STATUS"), "Cancelled", "Verify TP status [" + rs.getValue("TP_STATUS") + "] matches in the DB [Cancelled].");
            TestReporter.softAssertEquals(rs.getValue("TC_STATUS"), "Cancelled", "Verify TC status [" + rs.getValue("TC_STATUS") + "] matches in the DB [Cancelled].");
            rs.moveNext();
        } while (rs.hasNext());
        TestReporter.assertAll();

    }

}
