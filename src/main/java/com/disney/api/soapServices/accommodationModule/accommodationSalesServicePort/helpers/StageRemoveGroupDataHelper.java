package com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.helpers;

import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.ReplaceAllForTravelPlanSegment;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.database.Database;
import com.disney.utils.dataFactory.database.Recordset;
import com.disney.utils.dataFactory.database.databaseImpl.OracleDatabase;
import com.disney.utils.dataFactory.guestFactory.HouseHold;

public class StageRemoveGroupDataHelper {

    // SQL2: Retrieve res mgmt info
    public static void validateResMgmtInfo(ReplaceAllForTravelPlanSegment book) {
        String sql = "select c.BLK_CD, c.JDO_SEQ_NB " +
                " from res_mgmt.TPS a " +
                " join res_mgmt.TC_GRP b on a.TPS_ID = b.TPS_ID " +
                " join res_mgmt.TC c on b.TC_GRP_NB = c.TC_GRP_NB " +
                " join res_mgmt.ACM_CMPNT d on c.TC_ID = d.ACM_TC_ID " +
                " where a.TPS_ID = " + book.getTravelPlanSegmentId();

        Database db = new OracleDatabase(book.getEnvironment(), Database.DREAMS);
        Recordset rs = new Recordset(db.getResultSet(sql));
        TestReporter.softAssertTrue(rs.getValue("BLK_CD").equals("NULL"), "The BLOCK CODE in the query is [" + rs.getValue("BLK_CD") + "] matches [NULL]");
        TestReporter.assertAll();
    }

    // SQL3: Retrieve res history info
    public static void validateResHistoryInfo(ReplaceAllForTravelPlanSegment book) {
        String sql = "select * from res_mgmt.RES_HIST a where a.TP_ID = " + book.getTravelComponentGroupingId();
        Database db = new OracleDatabase(book.getEnvironment(), Database.DREAMS);
        Recordset rs = new Recordset(db.getResultSet(sql));
        for (rs.moveFirst(); rs.hasNext(); rs.moveNext()) {
            if (rs.getValue("RES_HIST_PROC_DS").equals("MODIFIED") || rs.getValue("RES_HIST_PROC_DS").equals("Rate Overridden")) {
                TestReporter.softAssertTrue(rs.getValue("RES_HIST_PROC_DS").equals("MODIFIED") || rs.getValue("RES_HIST_PROC_DS").equals("Rate Overridden"), "The record is " + rs.getValue("RES_HIST_PROC_DS"));
            }
        }
        TestReporter.assertAll();
    }

    // SQL4: Retrieve TC guest info
    public static void validateTcGuestInfo(ReplaceAllForTravelPlanSegment book) {
        String sql = "select b.TC_GST_ID " +
                " from res_mgmt.tc a " +
                " join res_mgmt.TC_GST b on a.TC_ID = b.TC_ID " +
                " where a.TC_GRP_NB = " + book.getTravelComponentGroupingId() +
                " and b.TXN_IDVL_PTY_ID is not null ";
        Database db = new OracleDatabase(book.getEnvironment(), Database.DREAMS);
        Recordset rs = new Recordset(db.getResultSet(sql));

        TestReporter.softAssertTrue(!rs.getValue("TC_GST_ID").equals(book.getGuestId()), "The tc guest id in the query is [" + rs.getValue("TC_GST_ID") + "] does not  match the booking guest id [" + book.getGuestId() + "]");
        TestReporter.assertAll();
    }

    // SQL5: Retrieve TP pty info
    public static void validateTPPartyInfo(ReplaceAllForTravelPlanSegment book, HouseHold hh) {
        String sql = "select b.*, d.*, e.*, f.* " +
                " from res_mgmt.TP_PTY a " +
                " join guest.TXN_PTY_EXTNL_REF b on a.TXN_PTY_ID = b.TXN_PTY_ID " +
                " join guest.TXN_PTY_LCTR c on b.TXN_PTY_ID = c.TXN_PTY_ID " +
                " left outer join guest.TXN_PTY_ADDR_LCTR d on c.TXN_PTY_LCTR_ID = d.TXN_PTY_ADDR_LCTR_ID " +
                " left outer join guest.TXN_PTY_EML_LCTR e on c.TXN_PTY_LCTR_ID = e.TXN_PTY_EML_LCTR_ID " +
                " left outer join guest.TXN_PTY_PHN_LCTR f on c.TXN_PTY_LCTR_ID = f.TXN_PTY_PHN_LCTR_ID " +
                " where a.TP_ID = " + book.getTravelPlanId();
        Database db = new OracleDatabase(book.getEnvironment(), Database.DREAMS);
        Recordset rs = new Recordset(db.getResultSet(sql));
        TestReporter.softAssertTrue(rs.getValue("TXN_PTY_ID").equals(book.getGuestId()), "The party id in the query is [" + rs.getValue("TXN_PTY_ID") + "] matches the booking party id [" + book.getGuestId() + "]");
        TestReporter.softAssertTrue(hh.primaryGuest().primaryAddress().getAddress1().equals(rs.getValue("ADDR_LN_1_TX", 1)), "The booking address is [" + hh.primaryGuest().primaryAddress().getAddress1() + "] matches the query address [" + rs.getValue("ADDR_LN_1_TX", 1) + "]");
        TestReporter.softAssertTrue(hh.primaryGuest().primaryEmail().getEmail().equals(rs.getValue("TXN_PTY_EML_ADDR_TX", 2)), "The booking email address is [" + hh.primaryGuest().primaryEmail().getEmail() + "] matches the email address in the query [" + rs.getValue("TXN_PTY_EML_ADDR_TX", 2) + "]");
        TestReporter.softAssertTrue(hh.primaryGuest().primaryPhone().getNumber().equals(rs.getValue("PHN_NB", 3)), "The booking phone number is [" + hh.primaryGuest().primaryPhone().getNumber() + "] matches the phone number in the query [" + rs.getValue("PHN_NB", 3) + "]");
        TestReporter.assertAll();
    }

    // SQL6: Retrieve RIM info
    public static void validateRIMInfo(ReplaceAllForTravelPlanSegment book) {
        String sql = "select d.WHSL_IN, d.BLK_CD, d.JDO_SEQ_NB " +
                " from res_mgmt.tc a " +
                " join rsrc_inv.RSRC_OWN_REF b on to_char(a.tc_id) = b.EXTNL_OWN_REF_VAL " +
                " join rsrc_inv.RSRC_OWN c on b.RSRC_OWN_ID = c.RSRC_OWN_ID " +
                " join rsrc_inv.RSRC_ASGN_OWNR d on c.ASGN_OWNR_ID = d.ASGN_OWNR_ID " +
                " join rsrc_inv.RSRC_ASGN_REQ e on d.ASGN_OWNR_ID = e.ASGN_OWNR_ID " +
                " where a.TC_GRP_NB =" + book.getTravelComponentGroupingId();
        Database db = new OracleDatabase(book.getEnvironment(), Database.DREAMS);
        Recordset rs = new Recordset(db.getResultSet(sql));
        TestReporter.softAssertTrue(rs.getValue("WHSL_IN").equals("N"), "The whsl indicator is [" + rs.getValue("WHSL_IN") + "] matches  [N]");
        TestReporter.softAssertTrue(rs.getValue("BLK_CD").equals("NULL"), "The block code indicator is [" + rs.getValue("BLK_CD") + "] matches [NULL]");
        TestReporter.assertAll();
    }

    // SQL7: Retrieve Folio - Root Charge Group info (TP)
    public static void validateFolioRootInfo(ReplaceAllForTravelPlanSegment book) {
        String sql = " select f.CHRG_ALLOC_ID, f.CHRG_ALLOC_TYP_NM, f.CHRG_ALLOC_INACTV_DT, i.FOLIO_ITEM_ID, i.FOLIO_ID, i.FOLIO_ITEM_AM " +
                " from folio.EXTNL_REF a " +
                " left outer join folio.CHRG_GRP_EXTNL_REF b on a.EXTNL_REF_ID = b.EXTNL_REF_ID " +
                " left outer join folio.CHRG_GRP c on b.CHRG_GRP_ID = c.CHRG_GRP_ID " +
                " left outer join folio.CHRG_ALLOC f on b.CHRG_GRP_ID = f.OWN_CHRG_GRP_ID " +
                " left outer join folio.CHRG_GRP_FOLIO g on c.CHRG_GRP_ID = g.ROOT_CHRG_GRP_ID " +
                " left outer join folio.FOLIO h on g.CHRG_GRP_FOLIO_ID = h.FOLIO_ID " +
                " left outer join folio.FOLIO_ITEM i on h.FOLIO_ID = i.FOLIO_ID " +
                " left outer join folio.ROOT_CHRG_GRP j on c.chrg_grp_id = j.root_chrg_grp_id " +
                " where a.EXTNL_REF_VAL = '" + book.getTravelPlanId() + "'";
        Database db = new OracleDatabase(book.getEnvironment(), Database.DREAMS);
        Recordset rs = new Recordset(db.getResultSet(sql));
        TestReporter.softAssertTrue(!rs.getValue("FOLIO_ID").equals(""), "The folio id is [" + rs.getValue("FOLIO_ID") + "]");
        TestReporter.softAssertTrue(!rs.getValue("FOLIO_ITEM_AM").equals(""), "The folio item amount is [" + rs.getValue("FOLIO_ITEM_AM") + "]");
        for (rs.moveFirst(); rs.hasNext(); rs.moveNext()) {
            if (rs.getValue("CHRG_ALLOC_TYP_NM") == "Template") {

                TestReporter.softAssertTrue(rs.getValue("CHRG_ALLOC_INACTV_DT") != "NULL", "The charge allocation inactive date is [" + rs.getValue("CHRG_ALLOC_INACTV_DT") + "] when the charge allocation type nm is [" + rs.getValue("CHRG_ALLOC_TYP_NM") + "].");
                TestReporter.softAssertTrue(!rs.getValue("FOLIO_ITEM_ID").equals(""), "The Folio item id is [" + rs.getValue("FOLIO_ITEM_ID") + "]");
            }
        }
        TestReporter.assertAll();
    }

    // SQL8: Retrieve Folio - Node Charge Group info (TCG)
    public static void validateFolioNodeInfo(ReplaceAllForTravelPlanSegment book) {
        String sql = "select c.JDO_SEQ_NB, d.CHRG_ID, d.REV_CLS_NM, e.CHRG_ITEM_ID, e.CHRG_ITEM_AM, l.BILL_CHRG_GRP_ID, l.GUAR_TYP_NM " +
                " from folio.EXTNL_REF a " +
                " left outer join folio.CHRG_GRP_EXTNL_REF b on a.EXTNL_REF_ID = b.EXTNL_REF_ID " +
                " left outer join folio.CHRG_GRP c on b.CHRG_GRP_ID = c.CHRG_GRP_ID " +
                " left outer join folio.CHRG d on c.CHRG_GRP_ID = d.CHRG_GRP_ID " +
                " left outer join folio.CHRG_ITEM e on d.CHRG_ID = e.CHRG_ID " +
                " left outer join folio.NODE_CHRG_GRP l on c.CHRG_GRP_ID = l.NODE_CHRG_GRP_ID " +
                " where a.EXTNL_REF_VAL = '" + book.getTravelComponentGroupingId() + "'";

        Database db = new OracleDatabase(book.getEnvironment(), Database.DREAMS);
        Recordset rs = new Recordset(db.getResultSet(sql));

        for (rs.moveFirst(); rs.hasNext(); rs.moveNext()) {
            if (rs.getValue("REV_CLS_NM") == "Accommodation") {
                TestReporter.softAssertTrue(!rs.getValue("CHRG_ID").equals(""), "The charge id is [" + rs.getValue("CHRG_ID") + "] ");
                TestReporter.softAssertTrue(rs.getValue("REV_CLS_NM").equals("Accommodation"), "The rev cls name is [" + rs.getValue("REV_CLS_NM") + "] ");
                TestReporter.softAssertTrue(!rs.getValue("CHRG_ITEM_ID").equals(""), "The charge item id is [" + rs.getValue("CHRG_ITEM_ID") + "] ");
                TestReporter.softAssertTrue(!rs.getValue("CHRG_ITEM_AM").equals(""), "The charge item amount is [" + rs.getValue("CHRG_ITEM_AM") + "] ");
                TestReporter.softAssertTrue(rs.getValue("BILL_CHRG_GRP_ID").equals("NULL"), "The bill charge group id is [" + rs.getValue("BILL_CHRG_GRP_ID") + "] ");
                TestReporter.softAssertTrue(rs.getValue("GUAR_TYP_NM").equals("NONE"), "The guar type name is [" + rs.getValue("GUAR_TYP_NM") + "] ");
            }
        }
        TestReporter.assertAll();
    }

}