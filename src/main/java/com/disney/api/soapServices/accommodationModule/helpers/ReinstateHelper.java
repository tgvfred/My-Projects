package com.disney.api.soapServices.accommodationModule.helpers;

import java.util.Map;

import com.disney.utils.Environment;
import com.disney.utils.Sleeper;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.database.Database;
import com.disney.utils.dataFactory.database.Recordset;
import com.disney.utils.dataFactory.database.SQLValidationException;
import com.disney.utils.dataFactory.database.databaseImpl.OracleDatabase;

public class ReinstateHelper {
    private String environment;
    private String tpID;
    private String tcID;
    private String tpsID;
    private String tcgID;
    private Boolean validateAssignOwner;

    public ReinstateHelper(String environment, String tpID, String tpsID, String tcgID, String tcID) {
        this.environment = environment;
        this.tpID = tpID;
        this.tpsID = tpsID;
        this.tcgID = tcgID;
        this.tcID = tcID;

    }

    public void validateActiveChargeGroup(int numExpectedRecords) {
        TestReporter.logStep("Verify Charge Group");
        String sql = "select distinct c.* from folio.EXTNL_REF a left outer join folio.CHRG_GRP_EXTNL_REF b on a.EXTNL_REF_ID = b.EXTNL_REF_ID left outer join folio.CHRG_GRP c on b.CHRG_GRP_ID = c.CHRG_GRP_ID left outer join folio.CHRG d on c.CHRG_GRP_ID = d.CHRG_GRP_ID left outer join folio.CHRG_ITEM e on d.CHRG_ID = e.CHRG_ID left outer join folio.CHRG_GRP_FOLIO f on c.CHRG_GRP_ID = f.ROOT_CHRG_GRP_ID left outer join folio.FOLIO g on f.CHRG_GRP_FOLIO_ID = g.FOLIO_ID left outer join folio.FOLIO_ITEM h on g.FOLIO_ID = h.FOLIO_ID where a.EXTNL_REF_VAL in ('" + tpID + "','" + tpsID + "','" + tcgID + "')";

        Database db = new OracleDatabase(Environment.getBaseEnvironmentName(environment), Database.DREAMS);
        Recordset rs = new Recordset(db.getResultSet(sql));

        if (rs.getRowCount() == 0) {
            throw new SQLValidationException("No charges found for tp ID [ " + tpID + " ]", sql);
        }

        // Verify that the actual number of records is that which is expected

        // TestReporter.softAssertEquals(rs.getRowCount(), numExpectedRecords, "Verify that the number of records [" + rs.getRowCount() + "] is that which is expected [" + numExpectedRecords + "].");
        // Iterate over all records and verify the charge group status and
        // charge group active indicator
        do {
            TestReporter.softAssertEquals(rs.getValue("CHRG_GRP_ACTV_IN"), "Y", "Verify the charge group active indicator [" + rs.getValue("CHRG_GRP_ACTV_IN") + "] matches the charge group status in the DB [Y]");
            TestReporter.softAssertEquals(rs.getValue("CHRG_GRP_STS_NM"), "UnEarned", "Verify the charge group status [" + rs.getValue("CHRG_GRP_STS_NM") + "] matches the charge group status in the DB [UnEarned]");
            rs.moveNext();
        } while (rs.hasNext());
        TestReporter.assertAll();
    }

    public String validateCharges(int numExpectedRecords2, String workLocation) {
        TestReporter.logStep("Verify Charges");
        String cancelledChargeId = null;
        String sql = "select d.* from folio.EXTNL_REF a left outer join folio.CHRG_GRP_EXTNL_REF b on a.EXTNL_REF_ID = b.EXTNL_REF_ID left outer join folio.CHRG_GRP c on b.CHRG_GRP_ID = c.CHRG_GRP_ID left outer join folio.CHRG d on c.CHRG_GRP_ID = d.CHRG_GRP_ID left outer join folio.CHRG_ITEM e on d.CHRG_ID = e.CHRG_ID left outer join folio.CHRG_GRP_FOLIO f on c.CHRG_GRP_ID = f.ROOT_CHRG_GRP_ID left outer join folio.FOLIO g on f.CHRG_GRP_FOLIO_ID = g.FOLIO_ID left outer join folio.FOLIO_ITEM h on g.FOLIO_ID = h.FOLIO_ID where a.EXTNL_REF_VAL in ('" + tpID + "','" + tpsID + "','" + tcgID + "') and d.CHRG_ID is not null";
        Database db = new OracleDatabase(Environment.getBaseEnvironmentName(environment), Database.DREAMS);
        Recordset rs = new Recordset(db.getResultSet(sql));

        int numCancellationFees = 1;
        int numAccommodationCharges = numExpectedRecords2 - numCancellationFees;
        int numActiveCharge = numAccommodationCharges / 2;
        int numInActiveCharge = numActiveCharge;
        int activeCounter = 0;
        int inActiveCounter = 0;

        if (rs.getRowCount() == 0) {
            throw new SQLValidationException("No charges found for tp ID [ " + tpID + " ]", sql);
        }

        // Verify that the actual number of records is that which is expected
        // //TestReporter.softAssertEquals(rs.getRowCount(), numExpectedRecords2, "Verify that the number of records [" + rs.getRowCount() + "] is that which is expected [" + numExpectedRecords2 + "].");
        // Iterate over all records and verify the charge group status and
        // charge group active indicator
        for (int i = 1; i <= rs.getRowCount(); i++) {
            // if (rs.getValue("WRK_LOC_ID").equals(workLocation)) {
            // TestReporter.softAssertEquals(rs.getValue("CHRG_ACTV_IN"), "Y", "Verify the charge group active indicator [" + rs.getValue("CHRG_ACTV_IN") + "]
            // matches the charge group status in the DB [Y]");
            // } else {
            // cancelledChargeId = rs.getValue("CHRG_ID");
            // TestReporter.softAssertEquals(rs.getValue("CHRG_ACTV_IN"), "N", "Verify the charge group active indicator [" + rs.getValue("CHRG_ACTV_IN") + "]
            // matches the charge group status in the DB [N]");
            // }
            if (rs.getValue("REV_CLS_NM", i).equals("Cancellation Fee")) {
                cancelledChargeId = rs.getValue("CHRG_ID", i);
            } else if (rs.getValue("CHRG_ACTV_IN", i).equals("Y")) {
                activeCounter++;
            } else {
                inActiveCounter++;
            }
        }
        TestReporter.softAssertEquals(activeCounter, numActiveCharge, "Verify that the number of active charges [" + activeCounter + "] is that which is expected [" + numActiveCharge + "].");
        TestReporter.softAssertEquals(inActiveCounter, numInActiveCharge, "Verify that the inactive charges [" + inActiveCounter + "] is that which is expected [" + numInActiveCharge + "].");
        TestReporter.assertAll();
        return cancelledChargeId;
    }

    public String validateCharges(int numExpectedRecords2, String workLocation, int numActiveCharge, int numInActiveCharge) {
        TestReporter.logStep("Verify Charges");
        String cancelledChargeId = null;
        String sql = "select d.* from folio.EXTNL_REF a left outer join folio.CHRG_GRP_EXTNL_REF b on a.EXTNL_REF_ID = b.EXTNL_REF_ID left outer join folio.CHRG_GRP c on b.CHRG_GRP_ID = c.CHRG_GRP_ID left outer join folio.CHRG d on c.CHRG_GRP_ID = d.CHRG_GRP_ID left outer join folio.CHRG_ITEM e on d.CHRG_ID = e.CHRG_ID left outer join folio.CHRG_GRP_FOLIO f on c.CHRG_GRP_ID = f.ROOT_CHRG_GRP_ID left outer join folio.FOLIO g on f.CHRG_GRP_FOLIO_ID = g.FOLIO_ID left outer join folio.FOLIO_ITEM h on g.FOLIO_ID = h.FOLIO_ID where a.EXTNL_REF_VAL in ('" + tpID + "','" + tpsID + "','" + tcgID + "') and d.CHRG_ID is not null";
        Database db = new OracleDatabase(Environment.getBaseEnvironmentName(environment), Database.DREAMS);
        Recordset rs = new Recordset(db.getResultSet(sql));

        int activeCounter = 0;
        int inActiveCounter = 0;

        if (rs.getRowCount() == 0) {
            throw new SQLValidationException("No charges found for tp ID [ " + tpID + " ]", sql);
        }

        // Verify that the actual number of records is that which is expected
        //// TestReporter.softAssertEquals(rs.getRowCount(), numExpectedRecords2, "Verify that the number of records [" + rs.getRowCount() + "] is that which is expected [" + numExpectedRecords2 + "].");
        // Iterate over all records and verify the charge group status and
        // charge group active indicator
        for (int i = 1; i <= rs.getRowCount(); i++) {
            // if (rs.getValue("WRK_LOC_ID").equals(workLocation)) {
            // TestReporter.softAssertEquals(rs.getValue("CHRG_ACTV_IN"), "Y", "Verify the charge group active indicator [" + rs.getValue("CHRG_ACTV_IN") + "]
            // matches the charge group status in the DB [Y]");
            // } else {
            // cancelledChargeId = rs.getValue("CHRG_ID");
            // TestReporter.softAssertEquals(rs.getValue("CHRG_ACTV_IN"), "N", "Verify the charge group active indicator [" + rs.getValue("CHRG_ACTV_IN") + "]
            // matches the charge group status in the DB [N]");
            // }
            if (rs.getValue("REV_CLS_NM", i).equals("Cancellation Fee")) {
                cancelledChargeId = rs.getValue("CHRG_ID", i);
            } else if (rs.getValue("CHRG_ACTV_IN", i).equals("Y")) {
                activeCounter++;
            } else {
                inActiveCounter++;
            }
        }
        TestReporter.softAssertEquals(activeCounter, numActiveCharge, "Verify that the number of active charges [" + activeCounter + "] is that which is expected [" + numActiveCharge + "].");
        TestReporter.softAssertEquals(inActiveCounter, numInActiveCharge, "Verify that the inactive charges [" + inActiveCounter + "] is that which is expected [" + numInActiveCharge + "].");
        TestReporter.assertAll();
        return cancelledChargeId;
    }

    public void validateChargeItem(String cancelledChargeId, int numExpectedRecords3) {
        TestReporter.logStep("Verify Charge Items");
        String sql = "select e.* from folio.EXTNL_REF a left outer join folio.CHRG_GRP_EXTNL_REF b on a.EXTNL_REF_ID = b.EXTNL_REF_ID left outer join folio.CHRG_GRP c on b.CHRG_GRP_ID = c.CHRG_GRP_ID left outer join folio.CHRG d on c.CHRG_GRP_ID = d.CHRG_GRP_ID left outer join folio.CHRG_ITEM e on d.CHRG_ID = e.CHRG_ID left outer join folio.CHRG_GRP_FOLIO f on c.CHRG_GRP_ID = f.ROOT_CHRG_GRP_ID left outer join folio.FOLIO g on f.CHRG_GRP_FOLIO_ID = g.FOLIO_ID left outer join folio.FOLIO_ITEM h on g.FOLIO_ID = h.FOLIO_ID where a.EXTNL_REF_VAL in ('" + tpID + "','" + tpsID + "','" + tcgID + "') and e.CHRG_ID is not null";
        Database db = new OracleDatabase(Environment.getBaseEnvironmentName(environment), Database.DREAMS);
        Recordset rs = new Recordset(db.getResultSet(sql));

        int numCancellationFees = 1;
        int numAccommodationCharges = numExpectedRecords3 - numCancellationFees;
        int numActiveCharge = numAccommodationCharges / 2;
        int numInActiveCharge = numActiveCharge;
        int activeCounter = 0;
        int inActiveCounter = 0;

        if (rs.getRowCount() == 0) {
            throw new SQLValidationException("No charges items found for tp ID [ " + tpID + " ]", sql);
        }

        // Verify that the actual number of records is that which is expected
        // //TestReporter.softAssertEquals(rs.getRowCount(), numExpectedRecords3, "Verify that the number of records [" + rs.getRowCount() + "] is that which is expected [" + numExpectedRecords3 + "].");
        // Iterate over all records and verify the charge group status and
        // charge group active indicator
        for (int i = 1; i <= rs.getRowCount(); i++) {
            if (rs.getValue("CHRG_ID", i).equals(cancelledChargeId)) {
                TestReporter.assertEquals(rs.getValue("CHRG_ITEM_INACTV_DTS", i), "NULL", "Verify the charge item inactive indicator for the cancellation charge [" + rs.getValue("CHRG_ITEM_INACTV_DTS", i) + "] matches the charge item inactive in the DB [NULL]");
            } else if (rs.getValue("CHRG_ITEM_INACTV_DTS", i).equals("NULL")) {
                activeCounter++;
            } else {
                inActiveCounter++;
            }
        }
        TestReporter.softAssertEquals(activeCounter, numActiveCharge, "Verify that the number of active charge items [" + activeCounter + "] is that which is expected [" + numActiveCharge + "].");
        TestReporter.softAssertEquals(inActiveCounter, numInActiveCharge, "Verify that the inactive charge items [" + inActiveCounter + "] is that which is expected [" + numInActiveCharge + "].");
        TestReporter.assertAll();
    }

    public void validateChargeItem(String cancelledChargeId, int numExpectedRecords3, int numActiveCharge, int numInActiveCharge) {
        TestReporter.logStep("Verify Charge Items");
        String sql = "select e.* from folio.EXTNL_REF a left outer join folio.CHRG_GRP_EXTNL_REF b on a.EXTNL_REF_ID = b.EXTNL_REF_ID left outer join folio.CHRG_GRP c on b.CHRG_GRP_ID = c.CHRG_GRP_ID left outer join folio.CHRG d on c.CHRG_GRP_ID = d.CHRG_GRP_ID left outer join folio.CHRG_ITEM e on d.CHRG_ID = e.CHRG_ID left outer join folio.CHRG_GRP_FOLIO f on c.CHRG_GRP_ID = f.ROOT_CHRG_GRP_ID left outer join folio.FOLIO g on f.CHRG_GRP_FOLIO_ID = g.FOLIO_ID left outer join folio.FOLIO_ITEM h on g.FOLIO_ID = h.FOLIO_ID where a.EXTNL_REF_VAL in ('" + tpID + "','" + tpsID + "','" + tcgID + "') and e.CHRG_ID is not null";
        Database db = new OracleDatabase(Environment.getBaseEnvironmentName(environment), Database.DREAMS);
        Recordset rs = new Recordset(db.getResultSet(sql));

        int activeCounter = 0;
        int inActiveCounter = 0;

        if (rs.getRowCount() == 0) {
            throw new SQLValidationException("No charges items found for tp ID [ " + tpID + " ]", sql);
        }

        // Verify that the actual number of records is that which is expected
        // //TestReporter.softAssertEquals(rs.getRowCount(), numExpectedRecords3, "Verify that the number of records [" + rs.getRowCount() + "] is that which is expected [" + numExpectedRecords3 + "].");
        // Iterate over all records and verify the charge group status and
        // charge group active indicator
        for (int i = 1; i <= rs.getRowCount(); i++) {
            if (rs.getValue("CHRG_ID", i).equals(cancelledChargeId)) {
                TestReporter.assertEquals(rs.getValue("CHRG_ITEM_INACTV_DTS", i), "NULL", "Verify the charge item inactive indicator for the cancellation charge [" + rs.getValue("CHRG_ITEM_INACTV_DTS", i) + "] matches the charge item inactive in the DB [NULL]");
            } else if (rs.getValue("CHRG_ITEM_INACTV_DTS", i).equals("NULL")) {
                activeCounter++;
            } else {
                inActiveCounter++;
            }
        }
        TestReporter.softAssertEquals(activeCounter, numActiveCharge, "Verify that the number of active charge items [" + activeCounter + "] is that which is expected [" + numActiveCharge + "].");
        TestReporter.softAssertEquals(inActiveCounter, numInActiveCharge, "Verify that the inactive charge items [" + inActiveCounter + "] is that which is expected [" + numInActiveCharge + "].");
        TestReporter.assertAll();
    }

    public void validateFolioStatus(int numExpectedRecords4) {
        TestReporter.logStep("Verify Folio Status");
        String sql = "select f.* from folio.EXTNL_REF a left outer join folio.CHRG_GRP_EXTNL_REF b on a.EXTNL_REF_ID = b.EXTNL_REF_ID left outer join folio.CHRG_GRP c on b.CHRG_GRP_ID = c.CHRG_GRP_ID left outer join folio.CHRG d on c.CHRG_GRP_ID = d.CHRG_GRP_ID left outer join folio.CHRG_ITEM e on d.CHRG_ID = e.CHRG_ID left outer join folio.CHRG_GRP_FOLIO f on c.CHRG_GRP_ID = f.ROOT_CHRG_GRP_ID left outer join folio.FOLIO g on f.CHRG_GRP_FOLIO_ID = g.FOLIO_ID left outer join folio.FOLIO_ITEM h on g.FOLIO_ID = h.FOLIO_ID where a.EXTNL_REF_VAL in ('" + tpID + "','" + tpsID + "','" + tcgID + "') and f.CHRG_GRP_FOLIO_ID is not null";
        Database db = new OracleDatabase(Environment.getBaseEnvironmentName(environment), Database.DREAMS);
        Recordset rs = new Recordset(db.getResultSet(sql));

        if (rs.getRowCount() == 0) {
            throw new SQLValidationException("No folio status found for tp ID [ " + tpID + " ]", sql);
        }

        // Verify that the actual number of records is that which is expected
        // //TestReporter.softAssertEquals(rs.getRowCount(), numExpectedRecords4, "Verify that the number of records [" + rs.getRowCount() + "] is that which is expected [" + numExpectedRecords4 + "].");
        // Iterate over all records and verify the charge group status and
        // charge group active indicator

        if (rs.getRowCount() == 0) {
            throw new SQLValidationException("No folio status found for tp ID [ " + tpID + " ]", sql);
        }
        do {
            TestReporter.assertEquals(rs.getValue("FOLIO_ACTV_IN"), "Y", "Verify the charge folio active indicator [" + rs.getValue("FOLIO_ACTV_IN") + "] matches the charge group status in the DB [Y]");
            TestReporter.assertEquals(rs.getValue("FOLIO_STS_NM"), "UnEarned", "Verify the folio status status [" + rs.getValue("FOLIO_STS_NM") + "] matches the folio status" + " in the DB [UnEarned]");
            rs.moveNext();
        } while (rs.hasNext());

        TestReporter.assertAll();
    }

    public void validateFolioData(int numExpectedRecords5) {
        TestReporter.logStep("Verify Folio Row Count");
        String sql = "select g.* from folio.EXTNL_REF a left outer join folio.CHRG_GRP_EXTNL_REF b on a.EXTNL_REF_ID = b.EXTNL_REF_ID left outer join folio.CHRG_GRP c on b.CHRG_GRP_ID = c.CHRG_GRP_ID left outer join folio.CHRG d on c.CHRG_GRP_ID = d.CHRG_GRP_ID left outer join folio.CHRG_ITEM e on d.CHRG_ID = e.CHRG_ID left outer join folio.CHRG_GRP_FOLIO f on c.CHRG_GRP_ID = f.ROOT_CHRG_GRP_ID left outer join folio.FOLIO g on f.CHRG_GRP_FOLIO_ID = g.FOLIO_ID left outer join folio.FOLIO_ITEM h on g.FOLIO_ID = h.FOLIO_ID where a.EXTNL_REF_VAL in ('" + tpID + "','" + tpsID + "','" + tcgID + "') and h.FOLIO_ID is not null";
        Database db = new OracleDatabase(Environment.getBaseEnvironmentName(environment), Database.DREAMS);
        Recordset rs = new Recordset(db.getResultSet(sql));

        // Verify that the actual number of records is that which is expected
        // //TestReporter.softAssertEquals(rs.getRowCount(), numExpectedRecords5, "Verify that the number of records [" + rs.getRowCount() + "] is that which is expected [" + numExpectedRecords5 + "].");
        TestReporter.assertAll();
    }

    public void validateFolioItemData(int numExpectedRecords6) {
        TestReporter.logStep("Verify Folio Item Data");
        String sql = "select g.* from folio.EXTNL_REF a left outer join folio.CHRG_GRP_EXTNL_REF b on a.EXTNL_REF_ID = b.EXTNL_REF_ID left outer join folio.CHRG_GRP c on b.CHRG_GRP_ID = c.CHRG_GRP_ID left outer join folio.CHRG d on c.CHRG_GRP_ID = d.CHRG_GRP_ID left outer join folio.CHRG_ITEM e on d.CHRG_ID = e.CHRG_ID left outer join folio.CHRG_GRP_FOLIO f on c.CHRG_GRP_ID = f.ROOT_CHRG_GRP_ID left outer join folio.FOLIO g on f.CHRG_GRP_FOLIO_ID = g.FOLIO_ID left outer join folio.FOLIO_ITEM h on g.FOLIO_ID = h.FOLIO_ID where a.EXTNL_REF_VAL in ('" + tpID + "','" + tpsID + "','" + tcgID + "') and g.FOLIO_ID is not null";
        Database db = new OracleDatabase(Environment.getBaseEnvironmentName(environment), Database.DREAMS);
        Recordset rs = new Recordset(db.getResultSet(sql));

        // Verify that the actual number of records is that which is expected
        // //TestReporter.softAssertEquals(rs.getRowCount(), numExpectedRecords6, "Verify that the number of records [" + rs.getRowCount() + "] is that which is expected [" + numExpectedRecords6 + "].");
        TestReporter.assertAll();
    }

    public void validateTCReasons(int numExpectedRecords7, String tcId, String TCReasonType, String TCReasonTX, String TCReasonCntctNm, String LegacyReasonCode) {
        TestReporter.logStep("Verify the TC reasons");
        String sql = "select a.TC_RSN_TYP_NM, a.TC_ID, a.TC_RSN_TX, a.TC_RSN_CNTCT_NM, d.LGCY_RSN_CD from res_mgmt.TC_RSN a left join res_mgmt.prdf_tc_rsn d  on a.PRDF_TC_RSN_ID = d.PRDF_TC_RSN_ID where a.TC_ID in ( select c.tc_id from res_mgmt.TPS a left join res_mgmt.TC_GRP b on a.tps_id = b.tps_id left join res_mgmt.TC c on b.tc_grp_nb = c.tc_grp_nb where a.TP_ID = '" + tpID + "')";
        Database db = new OracleDatabase(Environment.getBaseEnvironmentName(environment), Database.DREAMS);
        Recordset rs = new Recordset(db.getResultSet(sql));
        boolean reasonFound = false;

        if (rs.getRowCount() == 0) {
            throw new SQLValidationException("No TC reasons found for tp ID [ " + tpID + " ]", sql);
        }

        // Verify that the actual number of records is that which is expected
        // //TestReporter.softAssertEquals(rs.getRowCount(), numExpectedRecords7, "Verify that the number of records [" + rs.getRowCount() + "] is that which is expected [" + numExpectedRecords7 + "].");
        do {
            if (rs.getValue("TC_RSN_TYP_NM").equals(TCReasonType)) {
                reasonFound = true;
                TestReporter.softAssertEquals(rs.getValue("TC_RSN_TYP_NM"), TCReasonType, "Verify the charge item inactive indicator [" + rs.getValue("TC_RSN_TYP_NM") + "] matches the charge item inactive in the DB [" + TCReasonType + "].");
                TestReporter.softAssertEquals(rs.getValue("TC_ID"), tcId, "Verify the TC id [" + rs.getValue("TC_ID") + "] matches the charge group status in the DB [" + tcId + "].");
                TestReporter.softAssertEquals(rs.getValue("TC_RSN_TX"), TCReasonTX, "Verify the TC reason text [" + rs.getValue("TC_RSN_TX") + "] matches the TC reason text in the DB [" + TCReasonTX + "]");
                TestReporter.softAssertEquals(rs.getValue("TC_RSN_CNTCT_NM"), TCReasonCntctNm, "Verify the TC reason contact name indicator [" + rs.getValue("TC_RSN_CNTCT_NM") + "] matches the TC reason contact name in the DB [" + TCReasonCntctNm + "]");
                TestReporter.softAssertEquals(rs.getValue("LGCY_RSN_CD"), LegacyReasonCode, "Verify the legacy reason code [" + rs.getValue("LGCY_RSN_CD") + "] matches the legacy reason code in the DB [" + LegacyReasonCode + "].");
            }
            rs.moveNext();
        } while (rs.hasNext());
        TestReporter.softAssertTrue(reasonFound, "Verify that the TC reason [" + TCReasonType + "] was found.");
        TestReporter.assertAll();
    }

    public void validateTCReasons(int numExpectedRecords7, Map<String, String> tcIds, String TCReasonType, String TCReasonTX, String TCReasonCntctNm, String LegacyReasonCode) {
        TestReporter.logStep("Verify the TC reasons");
        String sql = "select a.TC_RSN_TYP_NM, a.TC_ID, a.TC_RSN_TX, a.TC_RSN_CNTCT_NM, d.LGCY_RSN_CD from res_mgmt.TC_RSN a left join res_mgmt.prdf_tc_rsn d  on a.PRDF_TC_RSN_ID = d.PRDF_TC_RSN_ID where a.TC_ID in ( select c.tc_id from res_mgmt.TPS a left join res_mgmt.TC_GRP b on a.tps_id = b.tps_id left join res_mgmt.TC c on b.tc_grp_nb = c.tc_grp_nb where a.TP_ID = '" + tpID + "')";
        Database db = new OracleDatabase(Environment.getBaseEnvironmentName(environment), Database.DREAMS);
        Recordset rs = new Recordset(db.getResultSet(sql));

        if (rs.getRowCount() == 0) {
            throw new SQLValidationException("No TC reasons found for tp ID [ " + tpID + " ]", sql);
        }

        // // Verify that the actual number of records is that which is expected
        //// TestReporter.softAssertEquals(rs.getRowCount(), numExpectedRecords7, "Verify that the number of records [" + rs.getRowCount() + "] is that which is expected [" + numExpectedRecords7 + "].");
        do {
            if (rs.getValue("TC_RSN_TYP_NM").equals(TCReasonType)) {
                TestReporter.softAssertEquals(rs.getValue("TC_RSN_TYP_NM"), TCReasonType, "Verify the charge item inactive indicator [" + rs.getValue("TC_RSN_TYP_NM") + "] matches the charge item inactive in the DB [" + TCReasonType + "].");
                TestReporter.softAssertTrue(tcIds.containsKey(rs.getValue("TC_ID")), "Verify the TC id [" + rs.getValue("TC_ID") + "] is contained in the list of expected TC IDs [" + tcIds + "].");
                TestReporter.softAssertEquals(rs.getValue("TC_RSN_TX"), TCReasonTX, "Verify the TC reason text [" + rs.getValue("TC_RSN_TX") + "] matches the TC reason text in the DB [" + TCReasonTX + "]");
                TestReporter.softAssertEquals(rs.getValue("TC_RSN_CNTCT_NM"), TCReasonCntctNm, "Verify the TC reason contact name indicator [" + rs.getValue("TC_RSN_CNTCT_NM") + "] matches the TC reason contact name in the DB [" + TCReasonCntctNm + "]");
                TestReporter.softAssertEquals(rs.getValue("LGCY_RSN_CD"), LegacyReasonCode, "Verify the legacy reason code [" + rs.getValue("LGCY_RSN_CD") + "] matches the legacy reason code in the DB [" + LegacyReasonCode + "].");
            }
            rs.moveNext();
        } while (rs.hasNext());
        TestReporter.assertAll();
    }

    public void validateReservationHistory(int numExpectedRecords8) {
        TestReporter.logStep("Verify the reservation history");
        String sql = "select * from res_mgmt.res_hist a where a.tp_id = '" + tpID + "'";
        Database db = new OracleDatabase(Environment.getBaseEnvironmentName(environment), Database.DREAMS);
        Recordset rs = new Recordset(db.getResultSet(sql));

        if (rs.getRowCount() == 0) {
            throw new SQLValidationException("No reservation found for found for tp ID [ " + tpID + " ]", sql);
        }

        // Verify that the actual number of records is that which is expected
        // TestReporter.softAssertEquals(rs.getRowCount(), numExpectedRecords8, "Verify that the number of records [" + rs.getRowCount() + "] is that which is expected [" + numExpectedRecords8 + "].");

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

        // if (rs.getValue("RES_HIST_PROC_DS").equalsIgnoreCase("Cancelled")) {
        // TestReporter.softAssertTrue(cancelFound, "Verify that a cancel record was found for TP ID [" + tpID + "].");
        // } else if (rs.getValue("RES_HIST_PROC_DS").equalsIgnoreCase("Auto Cancelled")) {
        // TestReporter.softAssertTrue(autoCancelFound, "Verify that an auto cancel record was found for TP ID [" + tpID + "].");
        // }
        TestReporter.softAssertTrue(reinstateFound, "Verify that a reinstate record was found for TP ID [" + tpID + "].");
        TestReporter.assertAll();
    }

    public void validateReservationHistoryMultAccomm(int numExpectedRecords8, String tcId) {
        TestReporter.logStep("Verify the reservation history");
        String sql = "select * from res_mgmt.res_hist a where a.tp_id = '" + tpID + "'";
        Database db = new OracleDatabase(Environment.getBaseEnvironmentName(environment), Database.DREAMS);
        Recordset rs = new Recordset(db.getResultSet(sql));

        if (rs.getRowCount() == 0) {
            throw new SQLValidationException("No reservation history found for tp ID [ " + tpID + " ]", sql);
        }

        // Verify that the actual number of records is that which is expected
        // TestReporter.softAssertEquals(rs.getRowCount(), numExpectedRecords8, "Verify that the number of records [" + rs.getRowCount() + "] is that which is expected [" + numExpectedRecords8 + "].");

        boolean bookFound = false;
        boolean cancelFound = false;
        boolean reinstateFound = false;
        do {
            if (rs.getValue("RES_HIST_PROC_DS").equalsIgnoreCase("Booked")) {
                bookFound = true;
            } else if (rs.getValue("RES_HIST_PROC_DS").equalsIgnoreCase("Cancelled")) {
                cancelFound = true;
            } else if (rs.getValue("RES_HIST_PROC_DS").equalsIgnoreCase("Reinstated")) {
                reinstateFound = true;
            }
            // TestReporter.softAssertEquals(rs.getValue("TC_ID"), tcId, "Verify the TC id [" + rs.getValue("TC_ID") + "] matches the TC id in the DB [" + tcId
            // + "].");
            TestReporter.softAssertEquals(rs.getValue("TPS_ID"), tpsID, "Verify the TPS id [" + rs.getValue("TPS_ID") + "] matches the TPS id in the DB [" + tpsID + "].");
            TestReporter.softAssertEquals(rs.getValue("TP_ID"), tpID, "Verify the TP id [" + rs.getValue("TP_ID") + "] matches the TP id in the DB [" + tpID + "].");
            // TestReporter.softAssertEquals(rs.getValue("TC_GRP_NM"), tcgID, "Verify the TCG id [" + rs.getValue("TC_GRP_NM") + "] matches the TCG in the DB ["
            // + tcgID + "].");
            rs.moveNext();
        } while (rs.hasNext());
        TestReporter.softAssertTrue(bookFound, "Verify that a book record was found for TP ID [" + tpID + "].");
        TestReporter.softAssertTrue(cancelFound, "Verify that a cancel record was found for TP ID [" + tpID + "].");
        TestReporter.softAssertTrue(reinstateFound, "Verify that a reinstate record was found for TP ID [" + tpID + "].");
        TestReporter.assertAll();
    }

    public void validateReservationHistory(int numExpectedRecords8, Map<String, String> tcIds, Map<String, String> tcgIds) {
        TestReporter.logStep("Verify the reservation history");
        String sql = "select * from res_mgmt.res_hist a where a.tp_id = '" + tpID + "'";
        Database db = new OracleDatabase(Environment.getBaseEnvironmentName(environment), Database.DREAMS);
        Recordset rs = new Recordset(db.getResultSet(sql));

        if (rs.getRowCount() == 0) {
            throw new SQLValidationException("No reservation history found for tp ID [ " + tpID + " ]", sql);
        }

        // Verify that the actual number of records is that which is expected
        // TestReporter.softAssertEquals(rs.getRowCount(), numExpectedRecords8, "Verify that the number of records [" + rs.getRowCount() + "] is that which is expected [" + numExpectedRecords8 + "].");

        int bookFound = 0;
        int cancelFound = 0;
        int reinstateFound = 0;
        do {
            if (rs.getValue("RES_HIST_PROC_DS").equalsIgnoreCase("Booked")) {
                bookFound++;
            } else if (rs.getValue("RES_HIST_PROC_DS").equalsIgnoreCase("Cancelled")) {
                cancelFound++;
            } else if (rs.getValue("RES_HIST_PROC_DS").equalsIgnoreCase("Reinstated")) {
                reinstateFound++;
            }
            TestReporter.softAssertTrue(tcIds.containsKey(rs.getValue("TC_ID")), "Verify the TC id [" + rs.getValue("TC_ID") + "] is contained in the list of expected TC IDs [" + tcIds + "].");
            TestReporter.softAssertEquals(rs.getValue("TPS_ID"), tpsID, "Verify the TPS id [" + rs.getValue("TPS_ID") + "] matches the TPS id in the DB [" + tpsID + "].");
            TestReporter.softAssertEquals(rs.getValue("TP_ID"), tpID, "Verify the TP id [" + rs.getValue("TP_ID") + "] matches the TP id in the DB [" + tpID + "].");
            TestReporter.softAssertTrue(tcgIds.containsKey(rs.getValue("TC_GRP_NM")), "Verify the TCG id [" + rs.getValue("TC_GRP_NM") + "] is contained in the list of expected TCG IDs [" + tcgIds + "].");
            rs.moveNext();
        } while (rs.hasNext());
        TestReporter.softAssertTrue(bookFound == tcgIds.size(), "Verify that the number of booked records [" + bookFound + "] was that which was expected [" + tcgIds.size() + "].");
        TestReporter.softAssertTrue(cancelFound == tcgIds.size(), "Verify that the number of cancelled records [" + cancelFound + "] was that which was expected [" + tcgIds.size() + "].");
        TestReporter.softAssertTrue(reinstateFound == tcgIds.size(), "Verify that the number of reinstated records [" + reinstateFound + "] was that which was expected [" + tcgIds.size() + "].");
        TestReporter.assertAll();
    }

    public void validateRIM(int numExpectedRecords9, String roomTypeCode) {
        TestReporter.logStep("Verify the RIM");
        String sql = "select d.OWNR_STS_NM, e.RSRC_INVTRY_TYP_CD ROOM_TYPE_CODE from res_mgmt.tps a join res_mgmt.tc_grp b on a.tps_id = b.tps_id join res_mgmt.tc c on b.tc_grp_nb = c.tc_grp_nb join rsrc_inv.RSRC_ASGN_OWNR d  on c.ASGN_OWN_ID = d.ASGN_OWNR_ID join rsrc_inv.RSRC_INVTRY_TYP e on d.RSRC_INVTRY_TYP_ID = e.RSRC_INVTRY_TYP_ID where a.tp_id = '" + tpID + "'";
        Database db = new OracleDatabase(Environment.getBaseEnvironmentName(environment), Database.DREAMS);
        Recordset rs = new Recordset(db.getResultSet(sql));

        if (rs.getRowCount() == 0) {
            throw new SQLValidationException("No RIM results found for tp ID [ " + tpID + " ]", sql);
        }

        // TestReporter.softAssertEquals(rs.getRowCount(), numExpectedRecords9, "Verify that the number of records [" + rs.getRowCount() + "] is that which is expected [" + numExpectedRecords9 + "].");

        do {
            TestReporter.softAssertEquals(rs.getValue("OWNR_STS_NM"), "PENDING", "Verify the owner status [" + rs.getValue("OWNR_STS_NM") + "] matches the owner status in the DB [Pending]");
            TestReporter.softAssertEquals(rs.getValue("ROOM_TYPE_CODE"), roomTypeCode, "Verify the room type code [" + rs.getValue("ROOM_TYPE_CODE") + "] matches the room type code in the DB [" + roomTypeCode + "].");
            rs.moveNext();
        } while (rs.hasNext());

        TestReporter.assertAll();
    }

    public void validateTPV3Records(int numExpectedRecords10, String tpStartDate, String tpEndDate) {
        TestReporter.logStep("Verify the TPV3 records");
        Sleeper.sleep(5000);
        String sql = "select * from sales_tp.tp a where a.tp_id = '" + tpID + "'";
        Database db = new OracleDatabase(Environment.getBaseEnvironmentName(environment), Database.SALESTP);
        Recordset rs = null;

        int tries = 0;
        int maxTries = 60;
        boolean success = false;
        do {
            Sleeper.sleep(1000);
            rs = new Recordset(db.getResultSet(sql));
            if (rs.getRowCount() == numExpectedRecords10) {
                success = true;
            }
            tries++;
        } while (tries < maxTries && !success);

        if (rs.getRowCount() == 0) {
            throw new SQLValidationException("No TPV3 records found for tp ID [ " + tpID + " ]", sql);
        }

        // TestReporter.softAssertEquals(rs.getRowCount(), numExpectedRecords10, "Verify that the number of records [" + rs.getRowCount() + "] is that which is expected [" + numExpectedRecords10 + "].");

        do {
            TestReporter.softAssertEquals(rs.getValue("TP_ID"), tpID, "Verify the TP id [" + rs.getValue("TP_ID") + "] matches the TP id in the DB [" + tpID + "].");
            TestReporter.softAssertEquals(rs.getValue("TP_STRT_DT").split(" ")[0], tpStartDate, "Verify the start date [" + rs.getValue("TP_STRT_DT").split(" ")[0] + "] matches the start date in the DB [" + tpStartDate + "].");
            TestReporter.softAssertEquals(rs.getValue("TP_END_DT").split(" ")[0], tpEndDate, "Verify the end date [" + rs.getValue("TP_END_DT").split(" ")[0] + "] matches the end date in the DB [" + tpEndDate + "].");
            rs.moveNext();
        } while (rs.hasNext());

        TestReporter.assertAll();

    }

    public void validateTPV3Records(int numExpectedRecords10, String tpStartDate, Map<String, String> tpEndDates) {
        TestReporter.logStep("Verify the TPV3 records");
        Sleeper.sleep(5000);
        String sql = "select * from sales_tp.tp a where a.tp_id = '" + tpID + "'";
        Database db = new OracleDatabase(Environment.getBaseEnvironmentName(environment), Database.SALESTP);
        Recordset rs = null;

        int tries = 0;
        int maxTries = 60;
        boolean success = false;
        do {
            Sleeper.sleep(1000);
            rs = new Recordset(db.getResultSet(sql));
            if (rs.getRowCount() == numExpectedRecords10) {
                success = true;
            }
            tries++;
        } while (tries < maxTries && !success);

        if (rs.getRowCount() == 0) {
            throw new SQLValidationException("No TPV3 records found for tp ID [ " + tpID + " ]", sql);
        }

        // TestReporter.softAssertEquals(rs.getRowCount(), numExpectedRecords10, "Verify that the number of records [" + rs.getRowCount() + "] is that which is expected [" + numExpectedRecords10 + "].");

        do {
            TestReporter.softAssertEquals(rs.getValue("TP_ID"), tpID, "Verify the TP id [" + rs.getValue("TP_ID") + "] matches the TP id in the DB [" + tpID + "].");
            TestReporter.softAssertEquals(rs.getValue("TP_STRT_DT").split(" ")[0], tpStartDate, "Verify the start date [" + rs.getValue("TP_STRT_DT").split(" ")[0] + "] matches the start date in the DB [" + tpStartDate + "].");
            TestReporter.softAssertTrue(tpEndDates.containsKey(rs.getValue("TP_END_DT").split(" ")[0]), "Verify the end date [" + rs.getValue("TP_END_DT").split(" ")[0] + "] is contained in the list of expected end dates [" + tpEndDates + "].");
            rs.moveNext();
        } while (rs.hasNext());

        TestReporter.assertAll();

    }

    public String validateTPV3SalesOrder(int numExpectedRecords11, String slsOrderArrivalDate, String slsOrderDepartDate) {
        TestReporter.logStep("Verify the TPV3 sales order");
        String salesOrder = null;
        String sql = "select * from sales_tp.sls_ord a where a.tp_id = '" + tpID + "'";
        Database db = new OracleDatabase(Environment.getBaseEnvironmentName(environment), Database.SALESTP);
        Recordset rs = null;

        int tries = 0;
        int maxTries = 60;
        boolean success = false;
        do {
            Sleeper.sleep(1000);
            rs = new Recordset(db.getResultSet(sql));
            if (rs.getValue("SLS_ORD_STS_NM").equals("Booked")) {
                success = true;
            }
            tries++;
        } while (tries < maxTries && !success);

        if (rs.getRowCount() == 0) {
            throw new SQLValidationException("No TPV3 sales order found for tp ID [ " + tpID + " ]", sql);
        }

        // TestReporter.softAssertEquals(rs.getRowCount(), numExpectedRecords11, "Verify that the number of records [" + rs.getRowCount() + "] is that which is expected [" + numExpectedRecords11 + "].");

        do {
            salesOrder = rs.getValue("SLS_ORD");
            TestReporter.softAssertEquals(rs.getValue("TP_ID"), tpID, "Verify the TP id [" + rs.getValue("TP_ID") + "] matches the TP id in the DB [" + tpID + "].");
            TestReporter.softAssertEquals(rs.getValue("SLS_ORD_STS_NM"), "Booked", "Verify the sales order status [" + rs.getValue("SLS_ORD_STS_NM") + "] matches the sales order status in the DB [Booked]");
            TestReporter.softAssertEquals(rs.getValue("SLS_ORD_ARVL_DT").split(" ")[0], slsOrderArrivalDate, "Verify the arrival date [" + rs.getValue("SLS_ORD_ARVL_DT").split(" ")[0] + "] matches the arrival date in the DB [" + slsOrderArrivalDate + "].");
            TestReporter.softAssertEquals(rs.getValue("SLS_ORD_DPRT_DT").split(" ")[0], slsOrderDepartDate, "Verify the departure date [" + rs.getValue("SLS_ORD_DPRT_DT").split(" ")[0] + "] matches the departure date in the DB [" + slsOrderDepartDate + "].");
            rs.moveNext();
        } while (rs.hasNext());

        TestReporter.assertAll();
        return salesOrder;

    }

    public String validateTPV3SalesOrderAccomm(int numExpectedRecords11, String slsOrderArrivalDate, String slsOrderDepartDate) {
        TestReporter.logStep("Verify the TPV3 sales order");
        String salesOrder = null;
        String sql = "select * from sales_tp.sls_ord a where a.tp_id = '" + tpID + "'";
        Database db = new OracleDatabase(Environment.getBaseEnvironmentName(environment), Database.SALESTP);
        Recordset rs = null;

        int tries = 0;
        int maxTries = 60;
        boolean success = false;
        do {
            Sleeper.sleep(1000);
            rs = new Recordset(db.getResultSet(sql));
            if (rs.getValue("SLS_ORD_STS_NM").equals("Booked")) {
                success = true;
            }
            tries++;
        } while (tries < maxTries && !success);

        if (rs.getRowCount() == 0) {
            throw new SQLValidationException("No TPV3 sales order found for tp ID [ " + tpID + " ]", sql);
        }

        // TestReporter.softAssertEquals(rs.getRowCount(), numExpectedRecords11, "Verify that the number of records [" + rs.getRowCount() + "] is that which is expected [" + numExpectedRecords11 + "].");

        do {
            salesOrder = rs.getValue("SLS_ORD");
            TestReporter.softAssertEquals(rs.getValue("TP_ID"), tpID, "Verify the TP id [" + rs.getValue("TP_ID") + "] matches the TP id in the DB [" + tpID + "].");
            TestReporter.softAssertEquals(rs.getValue("SLS_ORD_STS_NM"), "Booked", "Verify the sales order status [" + rs.getValue("SLS_ORD_STS_NM") + "] matches the sales order status in the DB [Booked]");
            TestReporter.softAssertEquals(rs.getValue("SLS_ORD_ARVL_DT").split(" ")[0], slsOrderArrivalDate, "Verify the arrival date [" + rs.getValue("SLS_ORD_ARVL_DT").split(" ")[0] + "] matches the arrival date in the DB [" + slsOrderArrivalDate + "].");
            TestReporter.softAssertEquals(rs.getValue("SLS_ORD_DPRT_DT").split(" ")[0], slsOrderDepartDate, "Verify the departure date [" + rs.getValue("SLS_ORD_DPRT_DT").split(" ")[0] + "] matches the departure date in the DB [" + slsOrderDepartDate + "].");
            rs.moveNext();
        } while (rs.hasNext());

        TestReporter.assertAll();
        return salesOrder;

    }

    public void validateTPSReservationStatus(int numExpectedRecords12, String tpsCancelDate, String travelStatus, String tpsCancelNumber, String tpsArrivalDate, String tpsDepartureDate) {
        TestReporter.logStep("Verify the TPS reservation status");
        String sql = "select a.* from res_mgmt.tps a where a.tp_id = '" + tpID + "'";
        Database db = new OracleDatabase(Environment.getBaseEnvironmentName(environment), Database.DREAMS);

        Recordset rs = new Recordset(db.getResultSet(sql));

        if (rs.getRowCount() == 0) {
            throw new SQLValidationException("No TPS found for tp ID [ " + tpID + " ]", sql);
        }

        // TestReporter.softAssertEquals(rs.getRowCount(), numExpectedRecords12, "Verify that the number of records [" + rs.getRowCount() + "] is that which is expected [" + numExpectedRecords12 + "].");

        do {
            TestReporter.softAssertEquals(rs.getValue("TPS_ID"), tpsID, "Verify the TPS id [" + rs.getValue("TPS_ID") + "] matches the TPS id in the DB [" + tpsID + "].");
            TestReporter.softAssertEquals(rs.getValue("TRVL_STS_NM"), travelStatus, "Verify the travel status [" + rs.getValue("TRVL_STS_NM") + "] matches the travel status in the DB [" + travelStatus + "].");
            TestReporter.softAssertEquals(rs.getValue("TPS_CNCL_NB"), tpsCancelNumber, "Verify the TPS cancel number [" + rs.getValue("TPS_CNCL_NB") + "] matches the TPS cancel number in the DB [" + tpsCancelNumber + "].");
            TestReporter.softAssertEquals(rs.getValue("TPS_CNCL_DTS").split(" ")[0], tpsCancelDate, "Verify the cancel date [" + rs.getValue("TPS_CNCL_DTS").split(" ")[0] + "] matches the cancel date in the DB [" + tpsCancelDate + "].");
            TestReporter.softAssertEquals(rs.getValue("TPS_ARVL_DT").split(" ")[0], tpsArrivalDate, "Verify the arrival date [" + rs.getValue("TPS_ARVL_DT").split(" ")[0] + "] matches the arrival date in the DB [" + tpsArrivalDate + "].");
            TestReporter.softAssertEquals(rs.getValue("TPS_DPRT_DT").split(" ")[0], tpsDepartureDate, "Verify the departure date [" + rs.getValue("TPS_DPRT_DT").split(" ")[0] + "] matches the departure date in the DB [" + tpsDepartureDate + "].");
            rs.moveNext();
        } while (rs.hasNext());

        TestReporter.assertAll();

    }

    public void validateTCGReservationStatus(int numExpectedRecords13, String tcgID) {
        TestReporter.logStep("Verify the TCG reservation status");
        String sql = "select b.* from res_mgmt.tps a left outer join res_mgmt.tc_grp b on a.tps_id = b.tps_id where a.tp_id = '" + tpID + "'";
        Database db = new OracleDatabase(Environment.getBaseEnvironmentName(environment), Database.DREAMS);
        Recordset rs = new Recordset(db.getResultSet(sql));

        if (rs.getRowCount() == 0) {
            throw new SQLValidationException("No TCG reservation found for tp ID [ " + tpID + " ]", sql);
        }

        // TestReporter.softAssertEquals(rs.getRowCount(), numExpectedRecords13, "Verify that the number of records [" + rs.getRowCount() + "] is that which is expected [" + numExpectedRecords13 + "].");

        do {
            TestReporter.softAssertEquals(rs.getValue("TPS_ID"), tpsID, "Verify the TPS id [" + rs.getValue("TPS_ID") + "] matches the TPS id in the DB [" + tpsID + "].");
            TestReporter.softAssertEquals(rs.getValue("TC_GRP_NB"), tcgID, "Verify the TCG id [" + rs.getValue("TC_GRP_NB") + "] matches the TCG id in the DB [" + tcgID + "].");
            rs.moveNext();
        } while (rs.hasNext());

        TestReporter.assertAll();
    }

    public void validateTCGReservationStatus(int numExpectedRecords13, Map<String, String> tcgIds) {
        TestReporter.logStep("Verify the TCG reservation status");
        String sql = "select b.* from res_mgmt.tps a left outer join res_mgmt.tc_grp b on a.tps_id = b.tps_id where a.tp_id = '" + tpID + "'";
        Database db = new OracleDatabase(Environment.getBaseEnvironmentName(environment), Database.DREAMS);
        Recordset rs = new Recordset(db.getResultSet(sql));

        if (rs.getRowCount() == 0) {
            throw new SQLValidationException("No TCG reservation found for tp ID [ " + tpID + " ]", sql);
        }

        // TestReporter.softAssertEquals(rs.getRowCount(), numExpectedRecords13, "Verify that the number of records [" + rs.getRowCount() + "] is that which is expected [" + numExpectedRecords13 + "].");

        do {
            TestReporter.softAssertEquals(rs.getValue("TPS_ID"), tpsID, "Verify the TPS id [" + rs.getValue("TPS_ID") + "] matches the TPS id in the DB [" + tpsID + "].");
            TestReporter.softAssertTrue(tcgIds.containsKey(rs.getValue("TC_GRP_NB")), "Verify the TCG id [" + rs.getValue("TC_GRP_NB") + "] is contained in the expected list of TCGs [" + tcgIds + "].");
            rs.moveNext();
        } while (rs.hasNext());

        TestReporter.assertAll();
    }

    public void validateTCReservationStatus(int numExpectedRecords14, String tcID, String tcStartDate, String tcEndDate, String salesChannelId, String travelStatus, String facilityId, String tcGroupNumber) {
        TestReporter.logStep("Verify the TC reservation status");
        String sql = "select c.* from res_mgmt.tps a left outer join res_mgmt.tc_grp b on a.tps_id = b.tps_id left outer join res_mgmt.tc c on b.tc_grp_nb = c.tc_grp_nb where a.tp_id = '" + tpID + "'";
        Database db = new OracleDatabase(Environment.getBaseEnvironmentName(environment), Database.DREAMS);
        Recordset rs = null;

        int tries = 0;
        int maxTries = 20;
        boolean success = false;
        do {
            Sleeper.sleep(1000);
            rs = new Recordset(db.getResultSet(sql));
            do {
                if (rs.getValue("TC_TYP_NM").equalsIgnoreCase("AccommodationComponent")) {
                    if (rs.getValue("FAC_ID").equals(facilityId) && !rs.getValue("ASGN_OWN_ID").equals("NULL")) {
                        success = true;
                    }
                }
                tries++;
                rs.moveNext();
            } while (rs.hasNext());
        } while (tries < maxTries && !success);

        if (rs.getRowCount() == 0) {
            throw new SQLValidationException("No TC reservation found for tp ID [ " + tpID + " ]", sql);
        }

        // TestReporter.softAssertEquals(rs.getRowCount(), numExpectedRecords14, "Verify that the number of records [" + rs.getRowCount() + "] is that which is expected [" + numExpectedRecords14 + "].");

        rs.moveFirst();
        do {
            TestReporter.softAssertEquals(rs.getValue("TC_GRP_NB"), tcGroupNumber, "Verify the TC group number [" + rs.getValue("TC_GRP_NB") + "] matches the TC group number in the DB [" + tcGroupNumber + "].");
            TestReporter.softAssertEquals(rs.getValue("TC_STRT_DTS").split(" ")[0], tcStartDate, "Verify the start date [" + rs.getValue("TC_STRT_DTS").split(" ")[0] + "] matches the start date in the DB [" + tcStartDate + "].");
            TestReporter.softAssertEquals(rs.getValue("TC_END_DTS").split(" ")[0], tcEndDate, "Verify the departure date [" + rs.getValue("TC_END_DTS").split(" ")[0] + "] matches the departure date in the DB [" + tcEndDate + "].");
            TestReporter.softAssertEquals(rs.getValue("SLS_CHAN_ID"), salesChannelId, "Verify the sales channel id [" + rs.getValue("SLS_CHAN_ID") + "] matches the sales channel id in the DB [" + salesChannelId + "].");
            TestReporter.softAssertEquals(rs.getValue("TRVL_STS_NM"), travelStatus, "Verify the travel status [" + rs.getValue("TRVL_STS_NM") + "] matches the travel status in the DB [" + travelStatus + "].");
            if (rs.getValue("TC_TYP_NM").equalsIgnoreCase("AccommodationComponent")) {
                TestReporter.softAssertEquals(rs.getValue("TC_ID"), tcID, "Verify the TC id [" + rs.getValue("TC_ID") + "] matches the TC id in the DB [" + tcID + "].");
                TestReporter.softAssertEquals(rs.getValue("FAC_ID"), facilityId, "Verify the facility ID [" + rs.getValue("FAC_ID") + "] matches the facility ID in the DB [" + facilityId + "].");
                TestReporter.softAssertTrue(!rs.getValue("ASGN_OWN_ID").equals("NULL"), "Verify the asgnOwnId [" + rs.getValue("ASGN_OWN_ID") + "] is not [NULL].");
            } else {
                TestReporter.softAssertTrue(rs.getValue("FAC_ID").equals("0"), "Verify the facility ID [" + rs.getValue("FAC_ID") + "] is [0].");
                TestReporter.softAssertTrue(rs.getValue("ASGN_OWN_ID").equals("0"), "Verify the asgnOwnId [" + rs.getValue("ASGN_OWN_ID") + "] is [0].");

            }
            rs.moveNext();
        } while (rs.hasNext());

        TestReporter.assertAll();
    }

    public void validateTCReservationStatusForTCG(int numExpectedRecords14, String tcID, String tcStartDate, String tcEndDate, String salesChannelId, String travelStatus, String facilityId, String tcGroupNumber) {
        TestReporter.logStep("Verify the TC reservation status");
        String sql = "select c.* from res_mgmt.tps a left outer join res_mgmt.tc_grp b on a.tps_id = b.tps_id left outer join res_mgmt.tc c on b.tc_grp_nb = c.tc_grp_nb where a.tp_id = '" + tpID + "' and c.tc_grp_nb = " + tcGroupNumber;
        Database db = new OracleDatabase(Environment.getBaseEnvironmentName(environment), Database.DREAMS);
        Recordset rs = null;

        int tries = 0;
        int maxTries = 20;
        boolean success = false;
        do {
            Sleeper.sleep(1000);
            rs = new Recordset(db.getResultSet(sql));
            do {
                if (rs.getValue("TC_TYP_NM").equalsIgnoreCase("AccommodationComponent")) {
                    if (rs.getValue("FAC_ID").equals(facilityId) && !rs.getValue("ASGN_OWN_ID").equals("NULL")) {
                        success = true;
                    }
                }
                tries++;
                rs.moveNext();
            } while (rs.hasNext());
        } while (tries < maxTries && !success);

        if (rs.getRowCount() == 0) {
            throw new SQLValidationException("No TC reservation found for tp ID [ " + tpID + " ]", sql);
        }

        // TestReporter.softAssertEquals(rs.getRowCount(), numExpectedRecords14, "Verify that the number of records [" + rs.getRowCount() + "] is that which is expected [" + numExpectedRecords14 + "].");

        rs.moveFirst();
        do {
            TestReporter.log(rs.getValue("TC_TYP_NM") + " : " + rs.getValue("TC_ID"));
            TestReporter.softAssertEquals(rs.getValue("TC_GRP_NB"), tcGroupNumber, "Verify the TC group number [" + rs.getValue("TC_GRP_NB") + "] matches the TC group number in the DB [" + tcGroupNumber + "].");
            TestReporter.softAssertEquals(rs.getValue("TC_STRT_DTS").split(" ")[0], tcStartDate, "Verify the start date [" + rs.getValue("TC_STRT_DTS").split(" ")[0] + "] matches the start date in the DB [" + tcStartDate + "].");
            TestReporter.softAssertEquals(rs.getValue("TC_END_DTS").split(" ")[0], tcEndDate, "Verify the departure date [" + rs.getValue("TC_END_DTS").split(" ")[0] + "] matches the departure date in the DB [" + tcEndDate + "].");
            TestReporter.softAssertEquals(rs.getValue("SLS_CHAN_ID"), salesChannelId, "Verify the sales channel id [" + rs.getValue("SLS_CHAN_ID") + "] matches the sales channel id in the DB [" + salesChannelId + "].");
            TestReporter.softAssertEquals(rs.getValue("TRVL_STS_NM"), travelStatus, "Verify the travel status [" + rs.getValue("TRVL_STS_NM") + "] matches the travel status in the DB [" + travelStatus + "].");
            if (rs.getValue("TC_TYP_NM").equalsIgnoreCase("AccommodationComponent")) {
                TestReporter.softAssertEquals(rs.getValue("TC_ID"), tcID, "Verify the TC id [" + rs.getValue("TC_ID") + "] matches the TC id in the DB [" + tcID + "].");
                TestReporter.softAssertEquals(rs.getValue("FAC_ID"), facilityId, "Verify the facility ID [" + rs.getValue("FAC_ID") + "] matches the facility ID in the DB [" + facilityId + "].");
                TestReporter.softAssertTrue(!rs.getValue("ASGN_OWN_ID").equals("NULL"), "Verify the asgnOwnId [" + rs.getValue("ASGN_OWN_ID") + "] is not [NULL].");
            } else {
                TestReporter.softAssertTrue(rs.getValue("FAC_ID").equals("0"), "Verify the facility ID [" + rs.getValue("FAC_ID") + "] is [0].");
                TestReporter.softAssertTrue(rs.getValue("ASGN_OWN_ID").equals("0"), "Verify the asgnOwnId [" + rs.getValue("ASGN_OWN_ID") + "] is [0].");

            }
            rs.moveNext();
        } while (rs.hasNext());

        TestReporter.assertAll();
    }

    public void validateTCReservationStatusForTCGFacId(int numExpectedRecords14, String tcID, String tcStartDate, String tcEndDate, String salesChannelId, String travelStatus, String facilityId, String tcGroupNumber) {
        TestReporter.logStep("Verify the TC reservation status");
        String sql = "select c.* from res_mgmt.tps a left outer join res_mgmt.tc_grp b on a.tps_id = b.tps_id left outer join res_mgmt.tc c on b.tc_grp_nb = c.tc_grp_nb where a.tp_id = '" + tpID + "' and c.tc_grp_nb = " + tcGroupNumber;
        Database db = new OracleDatabase(Environment.getBaseEnvironmentName(environment), Database.DREAMS);
        Recordset rs = null;

        int tries = 0;
        int maxTries = 20;
        boolean success = false;
        do {
            Sleeper.sleep(1000);
            rs = new Recordset(db.getResultSet(sql));
            do {
                if (rs.getValue("TC_TYP_NM").equalsIgnoreCase("AccommodationComponent")) {
                    if (rs.getValue("FAC_ID").equals(facilityId) && !rs.getValue("ASGN_OWN_ID").equals("NULL")) {
                        success = true;
                    }
                }
                tries++;
                rs.moveNext();
            } while (rs.hasNext());
        } while (tries < maxTries && !success);

        if (rs.getRowCount() == 0) {
            throw new SQLValidationException("No TC reservation found for tp ID [ " + tpID + " ]", sql);
        }

        // TestReporter.softAssertEquals(rs.getRowCount(), numExpectedRecords14, "Verify that the number of records [" + rs.getRowCount() + "] is that which is expected [" + numExpectedRecords14 + "].");

        rs.moveFirst();
        do {
            TestReporter.log(rs.getValue("TC_TYP_NM") + " : " + rs.getValue("TC_ID"));
            TestReporter.softAssertEquals(rs.getValue("TC_GRP_NB"), tcGroupNumber, "Verify the TC group number [" + rs.getValue("TC_GRP_NB") + "] matches the TC group number in the DB [" + tcGroupNumber + "].");
            TestReporter.softAssertEquals(rs.getValue("TC_STRT_DTS").split(" ")[0], tcStartDate, "Verify the start date [" + rs.getValue("TC_STRT_DTS").split(" ")[0] + "] matches the start date in the DB [" + tcStartDate + "].");
            TestReporter.softAssertEquals(rs.getValue("TC_END_DTS").split(" ")[0], tcEndDate, "Verify the departure date [" + rs.getValue("TC_END_DTS").split(" ")[0] + "] matches the departure date in the DB [" + tcEndDate + "].");
            TestReporter.softAssertEquals(rs.getValue("TRVL_STS_NM"), travelStatus, "Verify the travel status [" + rs.getValue("TRVL_STS_NM") + "] matches the travel status in the DB [" + travelStatus + "].");
            if (rs.getValue("TC_TYP_NM").equalsIgnoreCase("AccommodationComponent")) {
                TestReporter.softAssertEquals(rs.getValue("TC_ID"), tcID, "Verify the TC id [" + rs.getValue("TC_ID") + "] matches the TC id in the DB [" + tcID + "].");
                TestReporter.softAssertEquals(rs.getValue("FAC_ID"), facilityId, "Verify the facility ID [" + rs.getValue("FAC_ID") + "] matches the facility ID in the DB [" + facilityId + "].");
                TestReporter.softAssertEquals(rs.getValue("SLS_CHAN_ID"), salesChannelId, "Verify the sales channel id [" + rs.getValue("SLS_CHAN_ID") + "] matches the sales channel id in the DB [" + salesChannelId + "].");
            } else {
                if (rs.getValue("FAC_ID").equals("0")) {
                    TestReporter.softAssertTrue(rs.getValue("FAC_ID").equals("0"), "Verify the facility ID [" + rs.getValue("FAC_ID") + "] is [0].");
                } else if (rs.getValue("FAC_ID").equals("NULL")) {
                    TestReporter.softAssertTrue(rs.getValue("FAC_ID").equals("NULL"), "Verify the facility ID [" + rs.getValue("FAC_ID") + "] is [NULL].");
                } else {
                    TestReporter.softAssertEquals(rs.getValue("FAC_ID"), facilityId, "Verify the facility ID [" + rs.getValue("FAC_ID") + "] matches the facility ID in the DB [" + facilityId + "].");
                }
            }
            rs.moveNext();
        } while (rs.hasNext());

        TestReporter.assertAll();
    }

    public void validateTCReservationStatusForTCGFacId2(int numExpectedRecords14, String tcID, String tcStartDate, String tcEndDate, String salesChannelId, String travelStatus, String facilityId, String tcGroupNumber) {
        TestReporter.logStep("Verify the TC reservation status");
        String sql = "select c.* from res_mgmt.tps a left outer join res_mgmt.tc_grp b on a.tps_id = b.tps_id left outer join res_mgmt.tc c on b.tc_grp_nb = c.tc_grp_nb where a.tp_id = '" + tpID + "' and c.tc_grp_nb = " + tcGroupNumber;
        Database db = new OracleDatabase(Environment.getBaseEnvironmentName(environment), Database.DREAMS);
        Recordset rs = null;

        int tries = 0;
        int maxTries = 20;
        boolean success = false;
        do {
            Sleeper.sleep(1000);
            rs = new Recordset(db.getResultSet(sql));
            do {
                if (rs.getValue("TC_TYP_NM").equalsIgnoreCase("AccommodationComponent")) {
                    if (rs.getValue("FAC_ID").equals(facilityId) && !rs.getValue("ASGN_OWN_ID").equals("NULL")) {
                        success = true;
                    }
                }
                tries++;
                rs.moveNext();
            } while (rs.hasNext());
        } while (tries < maxTries && !success);

        if (rs.getRowCount() == 0) {
            throw new SQLValidationException("No TC reservation found for tp ID [ " + tpID + " ]", sql);
        }

        // TestReporter.softAssertEquals(rs.getRowCount(), numExpectedRecords14, "Verify that the number of records [" + rs.getRowCount() + "] is that which is expected [" + numExpectedRecords14 + "].");

        rs.moveFirst();
        do {
            TestReporter.log(rs.getValue("TC_TYP_NM") + " : " + rs.getValue("TC_ID"));
            TestReporter.softAssertEquals(rs.getValue("TC_GRP_NB"), tcGroupNumber, "Verify the TC group number [" + rs.getValue("TC_GRP_NB") + "] matches the TC group number in the DB [" + tcGroupNumber + "].");
            TestReporter.softAssertEquals(rs.getValue("TC_STRT_DTS").split(" ")[0], tcStartDate, "Verify the start date [" + rs.getValue("TC_STRT_DTS").split(" ")[0] + "] matches the start date in the DB [" + tcStartDate + "].");
            TestReporter.softAssertEquals(rs.getValue("TC_END_DTS").split(" ")[0], tcEndDate, "Verify the departure date [" + rs.getValue("TC_END_DTS").split(" ")[0] + "] matches the departure date in the DB [" + tcEndDate + "].");
            TestReporter.softAssertEquals(rs.getValue("TRVL_STS_NM"), travelStatus, "Verify the travel status [" + rs.getValue("TRVL_STS_NM") + "] matches the travel status in the DB [" + travelStatus + "].");
            TestReporter.softAssertEquals(rs.getValue("SLS_CHAN_ID"), salesChannelId, "Verify the sales channel id [" + rs.getValue("SLS_CHAN_ID") + "] matches the sales channel id in the DB [" + salesChannelId + "].");
            if (rs.getValue("TC_TYP_NM").equalsIgnoreCase("AccommodationComponent")) {
                TestReporter.softAssertEquals(rs.getValue("TC_ID"), tcID, "Verify the TC id [" + rs.getValue("TC_ID") + "] matches the TC id in the DB [" + tcID + "].");
                TestReporter.softAssertEquals(rs.getValue("FAC_ID"), facilityId, "Verify the facility ID [" + rs.getValue("FAC_ID") + "] matches the facility ID in the DB [" + facilityId + "].");
                TestReporter.softAssertTrue(!rs.getValue("ASGN_OWN_ID").equals("NULL"), "Verify the asgnOwnId [" + rs.getValue("ASGN_OWN_ID") + "] is not [NULL].");
                TestReporter.softAssertEquals(rs.getValue("SLS_CHAN_ID"), salesChannelId, "Verify the sales channel id [" + rs.getValue("SLS_CHAN_ID") + "] matches the sales channel id in the DB [" + salesChannelId + "].");
            } else {
                if (rs.getValue("FAC_ID").equals("0")) {
                    TestReporter.softAssertTrue(rs.getValue("FAC_ID").equals("0"), "Verify the facility ID [" + rs.getValue("FAC_ID") + "] is [0].");
                } else if (rs.getValue("FAC_ID").equals("NULL")) {
                    TestReporter.softAssertTrue(rs.getValue("FAC_ID").equals("NULL"), "Verify the facility ID [" + rs.getValue("FAC_ID") + "] is [NULL].");
                } else {
                    TestReporter.softAssertEquals(rs.getValue("FAC_ID"), facilityId, "Verify the facility ID [" + rs.getValue("FAC_ID") + "] matches the facility ID in the DB [" + facilityId + "].");
                }
                TestReporter.softAssertTrue(rs.getValue("ASGN_OWN_ID").equals("0"), "Verify the asgnOwnId [" + rs.getValue("ASGN_OWN_ID") + "] is [0].");
                // TestReporter.softAssertEquals(rs.getValue("SLS_CHAN_ID"), "3", "Verify the sales channel id [" + rs.getValue("SLS_CHAN_ID") + "] matches the
                // sales channel id in the DB [3].");
            }
            rs.moveNext();
        } while (rs.hasNext());

        TestReporter.assertAll();
    }

    public void validateTCReservationStatusForTCGFacId3(int numExpectedRecords14, String tcID, String tcStartDate, String tcEndDate, String salesChannelId, String travelStatus, String facilityId, String tcGroupNumber) {
        TestReporter.logStep("Verify the TC reservation status");
        String sql = "select c.* from res_mgmt.tps a left outer join res_mgmt.tc_grp b on a.tps_id = b.tps_id left outer join res_mgmt.tc c on b.tc_grp_nb = c.tc_grp_nb where a.tp_id = '" + tpID + "' and c.tc_grp_nb = " + tcGroupNumber;
        Database db = new OracleDatabase(Environment.getBaseEnvironmentName(environment), Database.DREAMS);
        Recordset rs = null;

        int tries = 0;
        int maxTries = 20;
        boolean success = false;
        do {
            Sleeper.sleep(1000);
            rs = new Recordset(db.getResultSet(sql));
            do {
                if (rs.getValue("TC_TYP_NM").equalsIgnoreCase("AccommodationComponent")) {
                    if (rs.getValue("FAC_ID").equals(facilityId) && !rs.getValue("ASGN_OWN_ID").equals("NULL")) {
                        success = true;
                    }
                }
                tries++;
                rs.moveNext();
            } while (rs.hasNext());
        } while (tries < maxTries && !success);

        if (rs.getRowCount() == 0) {
            throw new SQLValidationException("No TC reservation status found for tp ID [ " + tpID + " ]", sql);
        }

        // TestReporter.softAssertEquals(rs.getRowCount(), numExpectedRecords14, "Verify that the number of records [" + rs.getRowCount() + "] is that which is expected [" + numExpectedRecords14 + "].");

        rs.moveFirst();
        do {
            TestReporter.log(rs.getValue("TC_TYP_NM") + " : " + rs.getValue("TC_ID"));
            TestReporter.softAssertEquals(rs.getValue("TC_GRP_NB"), tcGroupNumber, "Verify the TC group number [" + rs.getValue("TC_GRP_NB") + "] matches the TC group number in the DB [" + tcGroupNumber + "].");
            TestReporter.softAssertEquals(rs.getValue("TC_STRT_DTS").split(" ")[0], tcStartDate, "Verify the start date [" + rs.getValue("TC_STRT_DTS").split(" ")[0] + "] matches the start date in the DB [" + tcStartDate + "].");
            TestReporter.softAssertEquals(rs.getValue("TC_END_DTS").split(" ")[0], tcEndDate, "Verify the departure date [" + rs.getValue("TC_END_DTS").split(" ")[0] + "] matches the departure date in the DB [" + tcEndDate + "].");
            TestReporter.softAssertEquals(rs.getValue("TRVL_STS_NM"), travelStatus, "Verify the travel status [" + rs.getValue("TRVL_STS_NM") + "] matches the travel status in the DB [" + travelStatus + "].");
            TestReporter.softAssertEquals(rs.getValue("SLS_CHAN_ID"), salesChannelId, "Verify the sales channel id [" + rs.getValue("SLS_CHAN_ID") + "] matches the sales channel id in the DB [" + salesChannelId + "].");
            if (rs.getValue("TC_TYP_NM").equalsIgnoreCase("AccommodationComponent")) {
                TestReporter.softAssertEquals(rs.getValue("TC_ID"), tcID, "Verify the TC id [" + rs.getValue("TC_ID") + "] matches the TC id in the DB [" + tcID + "].");
                TestReporter.softAssertEquals(rs.getValue("FAC_ID"), facilityId, "Verify the facility ID [" + rs.getValue("FAC_ID") + "] matches the facility ID in the DB [" + facilityId + "].");
                if (Environment.isSpecialEnvironment(environment)) {
                    TestReporter.softAssertTrue(!rs.getValue("ASGN_OWN_ID").equals("0"), "Verify the asgnOwnId [" + rs.getValue("ASGN_OWN_ID") + "] is not [0].");
                } else {
                    TestReporter.softAssertTrue(!rs.getValue("ASGN_OWN_ID").equals("NULL"), "Verify the asgnOwnId [" + rs.getValue("ASGN_OWN_ID") + "] is not [NULL].");
                }
                TestReporter.softAssertEquals(rs.getValue("SLS_CHAN_ID"), salesChannelId, "Verify the sales channel id [" + rs.getValue("SLS_CHAN_ID") + "] matches the sales channel id in the DB [" + salesChannelId + "].");
            } else {
                if (rs.getValue("FAC_ID").equals("0")) {
                    TestReporter.softAssertTrue(rs.getValue("FAC_ID").equals("0"), "Verify the facility ID [" + rs.getValue("FAC_ID") + "] is [0].");
                } else if (rs.getValue("FAC_ID").equals("NULL")) {
                    TestReporter.softAssertTrue(rs.getValue("FAC_ID").equals("NULL"), "Verify the facility ID [" + rs.getValue("FAC_ID") + "] is [NULL].");
                } else {
                    TestReporter.softAssertEquals(rs.getValue("FAC_ID"), facilityId, "Verify the facility ID [" + rs.getValue("FAC_ID") + "] matches the facility ID in the DB [" + facilityId + "].");
                }
                if (Environment.isSpecialEnvironment(environment)) {
                    TestReporter.softAssertTrue(rs.getValue("ASGN_OWN_ID").equals("0"), "Verify the asgnOwnId [" + rs.getValue("ASGN_OWN_ID") + "] is [0].");
                } else {
                    TestReporter.softAssertTrue(rs.getValue("ASGN_OWN_ID").equals("NULL"), "Verify the asgnOwnId [" + rs.getValue("ASGN_OWN_ID") + "] is [NULL].");
                }
            }
            rs.moveNext();
        } while (rs.hasNext());

        TestReporter.assertAll();
    }

    public void validateTCReservationStatusForTCGFacId4(int numExpectedRecords14, String tcID, String tcStartDate, String tcEndDate, String salesChannelId, String travelStatus, String facilityId, String tcGroupNumber) {
        TestReporter.logStep("Verify the TC reservation status");
        String sql = "select c.* from res_mgmt.tps a left outer join res_mgmt.tc_grp b on a.tps_id = b.tps_id left outer join res_mgmt.tc c on b.tc_grp_nb = c.tc_grp_nb where a.tp_id = '" + tpID + "' and c.tc_grp_nb = " + tcGroupNumber;
        Database db = new OracleDatabase(Environment.getBaseEnvironmentName(environment), Database.DREAMS);
        Recordset rs = null;

        int tries = 0;
        int maxTries = 20;
        boolean success = false;
        do {
            Sleeper.sleep(1000);
            rs = new Recordset(db.getResultSet(sql));
            do {
                if (rs.getValue("TC_TYP_NM").equalsIgnoreCase("AccommodationComponent")) {
                    if (rs.getValue("FAC_ID").equals(facilityId) && !rs.getValue("ASGN_OWN_ID").equals("NULL")) {
                        success = true;
                    }
                }
                tries++;
                rs.moveNext();
            } while (rs.hasNext());
        } while (tries < maxTries && !success);

        if (rs.getRowCount() == 0) {
            throw new SQLValidationException("No TC reservation found for tp ID [ " + tpID + " ]", sql);
        }

        // TestReporter.softAssertEquals(rs.getRowCount(), numExpectedRecords14, "Verify that the number of records [" + rs.getRowCount() + "] is that which is expected [" + numExpectedRecords14 + "].");

        rs.moveFirst();
        do {
            TestReporter.log(rs.getValue("TC_TYP_NM") + " : " + rs.getValue("TC_ID"));
            TestReporter.softAssertEquals(rs.getValue("TC_GRP_NB"), tcGroupNumber, "Verify the TC group number [" + rs.getValue("TC_GRP_NB") + "] matches the TC group number in the DB [" + tcGroupNumber + "].");
            TestReporter.softAssertEquals(rs.getValue("TC_STRT_DTS").split(" ")[0], tcStartDate, "Verify the start date [" + rs.getValue("TC_STRT_DTS").split(" ")[0] + "] matches the start date in the DB [" + tcStartDate + "].");
            TestReporter.softAssertEquals(rs.getValue("TC_END_DTS").split(" ")[0], tcEndDate, "Verify the departure date [" + rs.getValue("TC_END_DTS").split(" ")[0] + "] matches the departure date in the DB [" + tcEndDate + "].");
            TestReporter.softAssertEquals(rs.getValue("TRVL_STS_NM"), travelStatus, "Verify the travel status [" + rs.getValue("TRVL_STS_NM") + "] matches the travel status in the DB [" + travelStatus + "].");
            TestReporter.softAssertEquals(rs.getValue("SLS_CHAN_ID"), salesChannelId, "Verify the sales channel id [" + rs.getValue("SLS_CHAN_ID") + "] matches the sales channel id in the DB [" + salesChannelId + "].");
            if (rs.getValue("TC_TYP_NM").equalsIgnoreCase("AccommodationComponent")) {
                TestReporter.softAssertEquals(rs.getValue("TC_ID"), tcID, "Verify the TC id [" + rs.getValue("TC_ID") + "] matches the TC id in the DB [" + tcID + "].");
                TestReporter.softAssertEquals(rs.getValue("FAC_ID"), facilityId, "Verify the facility ID [" + rs.getValue("FAC_ID") + "] matches the facility ID in the DB [" + facilityId + "].");
                // TestReporter.softAssertTrue(!rs.getValue("ASGN_OWN_ID").equals("NULL"), "Verify the asgnOwnId [" + rs.getValue("ASGN_OWN_ID") + "] is not [NULL].");

                if (Environment.isSpecialEnvironment(environment)) {
                    TestReporter.softAssertTrue(!rs.getValue("ASGN_OWN_ID").equals("0"), "Verify the asgnOwnId [" + rs.getValue("ASGN_OWN_ID") + "] is not [0].");
                } else {
                    TestReporter.softAssertTrue(!rs.getValue("ASGN_OWN_ID").equals("NULL"), "Verify the asgnOwnId [" + rs.getValue("ASGN_OWN_ID") + "] is not [NULL].");
                }
                TestReporter.softAssertEquals(rs.getValue("SLS_CHAN_ID"), salesChannelId, "Verify the sales channel id [" + rs.getValue("SLS_CHAN_ID") + "] matches the sales channel id in the DB [" + salesChannelId + "].");
            } else {
                if (rs.getValue("FAC_ID").equals("0")) {
                    TestReporter.softAssertTrue(rs.getValue("FAC_ID").equals("0"), "Verify the facility ID [" + rs.getValue("FAC_ID") + "] is [0].");
                } else if (rs.getValue("FAC_ID").equals("NULL")) {
                    TestReporter.softAssertTrue(rs.getValue("FAC_ID").equals("NULL"), "Verify the facility ID [" + rs.getValue("FAC_ID") + "] is [NULL].");
                } else {
                    TestReporter.softAssertEquals(rs.getValue("FAC_ID"), facilityId, "Verify the facility ID [" + rs.getValue("FAC_ID") + "] matches the facility ID in the DB [" + facilityId + "].");
                }
                // TestReporter.softAssertTrue(rs.getValue("ASGN_OWN_ID").equals("NULL"), "Verify the asgnOwnId [" + rs.getValue("ASGN_OWN_ID") + "] is [NULL].");

                if (Environment.isSpecialEnvironment(environment)) {
                    TestReporter.softAssertTrue(rs.getValue("ASGN_OWN_ID").equals("0"), "Verify the asgnOwnId [" + rs.getValue("ASGN_OWN_ID") + "] is [0].");
                } else {
                    TestReporter.softAssertTrue(rs.getValue("ASGN_OWN_ID").equals("NULL"), "Verify the asgnOwnId [" + rs.getValue("ASGN_OWN_ID") + "] is [NULL].");
                }
            }
            rs.moveNext();
        } while (rs.hasNext());

        TestReporter.assertAll();
    }

    public void validateTCReservationStatusForTCGs(int numExpectedRecords14, String tcID, String tcStartDate, String tcEndDate, String salesChannelId, String travelStatus, String facilityId, String tcGroupNumber) {
        TestReporter.logStep("Verify the TC reservation status");
        String sql = "select c.* from res_mgmt.tps a left outer join res_mgmt.tc_grp b on a.tps_id = b.tps_id left outer join res_mgmt.tc c on b.tc_grp_nb = c.tc_grp_nb where a.tp_id = '" + tpID + "' and c.tc_grp_nb = " + tcGroupNumber;
        Database db = new OracleDatabase(Environment.getBaseEnvironmentName(environment), Database.DREAMS);
        Recordset rs = null;

        int tries = 0;
        int maxTries = 20;
        boolean success = false;
        do {
            Sleeper.sleep(1000);
            rs = new Recordset(db.getResultSet(sql));
            do {
                if (rs.getValue("TC_TYP_NM").equalsIgnoreCase("AccommodationComponent")) {
                    if (rs.getValue("FAC_ID").equals(facilityId) && !rs.getValue("ASGN_OWN_ID").equals("NULL")) {
                        success = true;
                    }
                }
                tries++;
                rs.moveNext();
            } while (rs.hasNext());
        } while (tries < maxTries && !success);

        if (rs.getRowCount() == 0) {
            throw new SQLValidationException("No TC reservation found for tp ID [ " + tpID + " ]", sql);
        }

        // TestReporter.softAssertEquals(rs.getRowCount(), numExpectedRecords14, "Verify that the number of records [" + rs.getRowCount() + "] is that which is expected [" + numExpectedRecords14 + "].");

        rs.moveFirst();
        do {
            TestReporter.softAssertEquals(rs.getValue("TC_GRP_NB"), tcGroupNumber, "Verify the TC group number [" + rs.getValue("TC_GRP_NB") + "] matches the TC group number in the DB [" + tcGroupNumber + "].");
            TestReporter.softAssertEquals(rs.getValue("TC_STRT_DTS").split(" ")[0], tcStartDate, "Verify the start date [" + rs.getValue("TC_STRT_DTS").split(" ")[0] + "] matches the start date in the DB [" + tcStartDate + "].");
            TestReporter.softAssertEquals(rs.getValue("TC_END_DTS").split(" ")[0], tcEndDate, "Verify the departure date [" + rs.getValue("TC_END_DTS").split(" ")[0] + "] matches the departure date in the DB [" + tcEndDate + "].");
            TestReporter.softAssertEquals(rs.getValue("SLS_CHAN_ID"), salesChannelId, "Verify the sales channel id [" + rs.getValue("SLS_CHAN_ID") + "] matches the sales channel id in the DB [" + salesChannelId + "].");
            TestReporter.softAssertEquals(rs.getValue("TRVL_STS_NM"), travelStatus, "Verify the travel status [" + rs.getValue("TRVL_STS_NM") + "] matches the travel status in the DB [" + travelStatus + "].");
            if (rs.getValue("TC_TYP_NM").equalsIgnoreCase("AccommodationComponent")) {
                TestReporter.softAssertEquals(rs.getValue("TC_ID"), tcID, "Verify the TC id [" + rs.getValue("TC_ID") + "] matches the TC id in the DB [" + tcID + "].");
                TestReporter.softAssertEquals(rs.getValue("FAC_ID"), facilityId, "Verify the facility ID [" + rs.getValue("FAC_ID") + "] matches the facility ID in the DB [" + facilityId + "].");
                TestReporter.softAssertTrue(!rs.getValue("ASGN_OWN_ID").equals("NULL"), "Verify the asgnOwnId [" + rs.getValue("ASGN_OWN_ID") + "] is not [NULL].");
            } else {
                TestReporter.softAssertTrue(rs.getValue("FAC_ID").equals("NULL"), "Verify the facility ID [" + rs.getValue("FAC_ID") + "] is [NULL].");
                TestReporter.softAssertTrue(rs.getValue("ASGN_OWN_ID").equals("NULL"), "Verify the asgnOwnId [" + rs.getValue("ASGN_OWN_ID") + "] is [NULL].");

            }
            rs.moveNext();
        } while (rs.hasNext());

        TestReporter.assertAll();
    }

    public void validateTCReservationStatusCancelledAdmission(int numExpectedRecords14, String tcID, String tcStartDate, String tcEndDate, String salesChannelId, String travelStatus, String facilityId, String tcGroupNumber) {
        TestReporter.logStep("Verify the TC reservation status");
        String sql = "select c.* from res_mgmt.tps a left outer join res_mgmt.tc_grp b on a.tps_id = b.tps_id left outer join res_mgmt.tc c on b.tc_grp_nb = c.tc_grp_nb where a.tp_id = '" + tpID + "'";
        Database db = new OracleDatabase(Environment.getBaseEnvironmentName(environment), Database.DREAMS);
        Recordset rs = new Recordset(db.getResultSet(sql));

        if (rs.getRowCount() == 0) {
            throw new SQLValidationException("No TC reservation found for tp ID [ " + tpID + " ]", sql);
        }

        // TestReporter.softAssertEquals(rs.getRowCount(), numExpectedRecords14, "Verify that the number of records [" + rs.getRowCount() + "] is that which is expected [" + numExpectedRecords14 + "].");

        do {
            TestReporter.softAssertEquals(rs.getValue("TC_GRP_NB"), tcGroupNumber, "Verify the TC group number [" + rs.getValue("TC_GRP_NB") + "] matches the TC group number in the DB [" + tcGroupNumber + "].");
            TestReporter.softAssertEquals(rs.getValue("TC_STRT_DTS").split(" ")[0], tcStartDate, "Verify the start date [" + rs.getValue("TC_STRT_DTS").split(" ")[0] + "] matches the start date in the DB [" + tcStartDate + "].");
            TestReporter.softAssertEquals(rs.getValue("TC_END_DTS").split(" ")[0], tcEndDate, "Verify the departure date [" + rs.getValue("TC_END_DTS").split(" ")[0] + "] matches the departure date in the DB [" + tcEndDate + "].");
            if (rs.getValue("TC_TYP_NM").equalsIgnoreCase("AccommodationComponent")) {
                TestReporter.softAssertEquals(rs.getValue("TC_ID"), tcID, "Verify the TC id [" + rs.getValue("TC_ID") + "] matches the TC id in the DB [" + tcID + "].");
                TestReporter.softAssertEquals(rs.getValue("FAC_ID"), facilityId, "Verify the facility ID [" + rs.getValue("FAC_ID") + "] matches the facility ID in the DB [" + facilityId + "].");
                TestReporter.softAssertTrue(!rs.getValue("ASGN_OWN_ID").equals("NULL"), "Verify the asgnOwnId [" + rs.getValue("ASGN_OWN_ID") + "] is not [NULL].");
            }
            rs.moveNext();
        } while (rs.hasNext());

        TestReporter.assertAll();
    }

    public void validateTCReservationStatus(int numExpectedRecords14, Map<String, String> tcIds, String tcStartDate, String tcEndDate, String salesChannelId, String travelStatus, String facilityId, Map<String, String> tcgIds) {
        TestReporter.logStep("Verify the TC reservation status");
        String sql = "select c.* from res_mgmt.tps a left outer join res_mgmt.tc_grp b on a.tps_id = b.tps_id left outer join res_mgmt.tc c on b.tc_grp_nb = c.tc_grp_nb where a.tp_id = '" + tpID + "'";
        Database db = new OracleDatabase(Environment.getBaseEnvironmentName(environment), Database.DREAMS);
        Recordset rs = new Recordset(db.getResultSet(sql));

        if (rs.getRowCount() == 0) {
            throw new SQLValidationException("No TC reservation found for tp ID [ " + tpID + " ]", sql);
        }

        // TestReporter.softAssertEquals(rs.getRowCount(), numExpectedRecords14, "Verify that the number of records [" + rs.getRowCount() + "] is that which is expected [" + numExpectedRecords14 + "].");

        do {
            TestReporter.softAssertTrue(tcgIds.containsKey(rs.getValue("TC_GRP_NB")), "Verify the TC group number [" + rs.getValue("TC_GRP_NB") + "] is contained in the list of expected TCGs [" + tcgIds + "].");
            TestReporter.softAssertEquals(rs.getValue("TC_STRT_DTS").split(" ")[0], tcStartDate, "Verify the start date [" + rs.getValue("TC_STRT_DTS").split(" ")[0] + "] matches the start date in the DB [" + tcStartDate + "].");
            TestReporter.softAssertEquals(rs.getValue("TC_END_DTS").split(" ")[0], tcEndDate, "Verify the departure date [" + rs.getValue("TC_END_DTS").split(" ")[0] + "] matches the departure date in the DB [" + tcEndDate + "].");
            TestReporter.softAssertEquals(rs.getValue("SLS_CHAN_ID"), salesChannelId, "Verify the sales channel id [" + rs.getValue("SLS_CHAN_ID") + "] matches the sales channel id in the DB [" + salesChannelId + "].");
            TestReporter.softAssertEquals(rs.getValue("TRVL_STS_NM"), travelStatus, "Verify the travel status [" + rs.getValue("TRVL_STS_NM") + "] matches the travel status in the DB [" + travelStatus + "].");
            if (rs.getValue("TC_TYP_NM").equalsIgnoreCase("AccommodationComponent")) {
                TestReporter.softAssertTrue(tcIds.containsKey(rs.getValue("TC_ID")), "Verify the TC id [" + rs.getValue("TC_ID") + "] is contained in the list of expected TC ids [" + tcIds + "].");
                TestReporter.softAssertEquals(rs.getValue("FAC_ID"), facilityId, "Verify the facility ID [" + rs.getValue("FAC_ID") + "] matches the facility ID in the DB [" + facilityId + "].");
                TestReporter.softAssertTrue(!rs.getValue("ASGN_OWN_ID").equals("NULL"), "Verify the asgnOwnId [" + rs.getValue("ASGN_OWN_ID") + "] is not [NULL].");
            } else {
                TestReporter.softAssertTrue(rs.getValue("FAC_ID").equals("0"), "Verify the facility ID [" + rs.getValue("FAC_ID") + "] is [0].");
                TestReporter.softAssertTrue(rs.getValue("ASGN_OWN_ID").equals("0"), "Verify the asgnOwnId [" + rs.getValue("ASGN_OWN_ID") + "] is [0].");

            }
            rs.moveNext();
        } while (rs.hasNext());

        TestReporter.assertAll();
    }

    public void validateTCFee(Boolean feeExpected, int numFeesExpected) {
        TestReporter.logStep("Verify the TC fee");
        String sql = "select b.* from res_mgmt.tc a join res_mgmt.tc_fee b on a.tc_id = b.tc_id where a.tc_grp_nb = '" + tcgID + "'";
        Database db = new OracleDatabase(Environment.getBaseEnvironmentName(environment), Database.DREAMS);
        Recordset rs = new Recordset(db.getResultSet(sql));

        if (feeExpected) {
            TestReporter.assertTrue(rs.getRowCount() == numFeesExpected, "Verify that the number of TC fees [" + rs.getRowCount() + "] is that which is expected [" + numFeesExpected + "].");
        } else {
            TestReporter.assertTrue(rs.getRowCount() == 0, "Verify that no TC fees were found.");
        }

    }

}
