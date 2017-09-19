package com.disney.composite.api.accommodationModule.soapServices.accommodationSalesComponentServicePort.overrideAccommodationRates;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationModule.accommodationSalesComponentService.operations.OverrideAccommodationRatesRequest;
import com.disney.api.soapServices.accommodationModule.helpers.AccommodationBaseTest;
import com.disney.api.soapServices.accommodationModule.helpers.CheckInHelper;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.database.Database;
import com.disney.utils.dataFactory.database.Recordset;
import com.disney.utils.dataFactory.database.databaseImpl.OracleDatabase;

public class TestOverrideAccommodationRates_approvedCharges extends AccommodationBaseTest {
    private CheckInHelper helper;

    @Override
    @BeforeMethod(alwaysRun = true)
    @Parameters("environment")
    public void setup(String environment) {
        setEnvironment(environment);
        setDaysOut(0);
        setNights(1);
        setArrivalDate(getDaysOut());
        setDepartureDate(getDaysOut() + getNights());
        setValues(getEnvironment());

        isComo.set("false");
        bookReservation();
    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesComponentService", "overrideAccommodationRates" })
    public void testOverrideAccommodationRates_approvedCharges() {

        String tp_id1 = getBook().getTravelPlanId();
        String tp_id2;
        String tcg_id = getBook().getTravelComponentGroupingId();
        // System.out.println(key);
        // System.out.println(value);

        // Book room only booking (1 night, 1 adult)
        // Capture charge ids, charge amounts, charge items ids, charge item amounts, folio item ids, folio item amounts (to be used in a later validation)
        // Override the rate for the one night

        String sql1 = " select a.EXTNL_REF_VAL, a.EXTNL_SRC_NM, b.CHRG_GRP_ID, d.*, e.*"
                + " from folio.EXTNL_REF a"
                + " left outer join folio.CHRG_GRP_EXTNL_REF b on a.EXTNL_REF_ID = b.EXTNL_REF_ID"
                + " left outer join folio.CHRG_GRP c on b.CHRG_GRP_ID = c.CHRG_GRP_ID"
                + " left outer join folio.CHRG d on c.CHRG_GRP_ID = d.CHRG_GRP_ID"
                + " left outer join folio.CHRG_ITEM e on d.CHRG_ID = e.CHRG_ID"
                + " where a.EXTNL_REF_VAL in ("
                + " (select to_char(b.tc_grp_nb)"
                + " from res_mgmt.tps a"
                + " join res_mgmt.tc_grp b on a.tps_id = b.tps_id"
                + " where a.tp_id = '" + tp_id1 + "'))";

        String sql2 = " select a.EXTNL_REF_VAL, a.EXTNL_SRC_NM, b.CHRG_GRP_ID, i.*"
                + " from folio.EXTNL_REF a"
                + " left outer join folio.CHRG_GRP_EXTNL_REF b on a.EXTNL_REF_ID = b.EXTNL_REF_ID"
                + " left outer join folio.CHRG_GRP c on b.CHRG_GRP_ID = c.CHRG_GRP_ID"
                + " left outer join folio.CHRG_GRP_FOLIO g on c.CHRG_GRP_ID = g.ROOT_CHRG_GRP_ID"
                + " left outer join folio.FOLIO h on g.CHRG_GRP_FOLIO_ID = h.FOLIO_ID"
                + " left outer join folio.FOLIO_ITEM i on h.FOLIO_ID = i.FOLIO_ID"
                + " where a.EXTNL_REF_VAL in ("
                + " (select to_char(a.tp_id)"
                + " from res_mgmt.tps a"
                + " where a.tp_id = '" + tp_id1 + "'))";

        String sql3 = " select a.TC_RSN_TYP_NM, b.LGCY_RSN_CD, b.TC_RSN_NM"
                + " from res_mgmt.tc_rsn a"
                + " join res_mgmt.prdf_tc_rsn b on a.PRDF_TC_RSN_ID = b.PRDF_TC_RSN_ID"
                + " where a.tc_id in ("
                + " select x.tc_id"
                + " from res_mgmt.tc x"
                + " where x.tc_grp_nb = '" + tcg_id + "')";

        String sql5 = " select unique(a.RES_HIST_PROC_DS)"
                + " from res_mgmt.res_hist a"
                + " WHERE TP_ID ='" + tp_id1 + "'";
        Database db = new OracleDatabase(environment, Database.DREAMS);
        Recordset rs = new Recordset(db.getResultSet(sql1));
        Recordset rs2 = new Recordset(db.getResultSet(sql2));
        Recordset rs3 = new Recordset(db.getResultSet(sql3));
        Recordset rs4 = new Recordset(db.getResultSet(sql5));

        rs.print();
        rs2.print();
        rs3.print();
        rs4.print();

        // SQL1
        int numberOfCharges = rs.getRowCount();
        int numberOfFolioItems = rs.getRowCount();
        int numberOfChargeItems = rs.getRowCount();
        String old_chargeID = rs.getValue("CHRG_ID", 4);
        String old_chargeAmount = rs.getValue("CHRG_AM", 4);

        String old_chargeItemID4 = rs.getValue("CHRG_ITEM_ID", 4);
        // int numberOfChargeItems = rs.getRowCount();

        String oldchargeItemAmount4 = rs.getValue("CHRG_ITEM_AM", 4);

        // SQL2

        String old_folioItemID = rs2.getValue("FOLIO_ITEM_ID", 4);
        String old_folioItemAmount = rs2.getValue("FOLIO_ITEM_AM", 4);

        String basePriceSql = rs.getValue("CHRG_ITEM_AM", 4);
        String locationId = rs.getValue("WRK_LOC_ID", 4);
        TestReporter.assertTrue(numberOfCharges >= 1, "The number of charges is " + numberOfCharges);

        //

        helper = new CheckInHelper(getEnvironment(), getBook());
        helper.checkIn(getLocationId(), getDaysOut(), getNights(), getFacilityId());

        OverrideAccommodationRatesRequest oar = new OverrideAccommodationRatesRequest(environment, "Main");

        oar.setTpsID(getBook().getTravelPlanSegmentId());
        oar.setTcgId(tcg_id);
        oar.setOverridden("true");
        oar.setBasePrice("8");
        oar.setRackRateRate("8");
        oar.setDate(getArrivalDate());
        oar.setRackRateDate(getArrivalDate());
        oar.setOverrideReason("RTOTHER");
        oar.setLocationId(locationId);

        oar.sendRequest();
        System.out.println(oar.getRequest());
        Recordset rs5 = new Recordset(db.getResultSet(sql1));
        Recordset rs6 = new Recordset(db.getResultSet(sql2));
        Recordset rs7 = new Recordset(db.getResultSet(sql3));
        Recordset rs8 = new Recordset(db.getResultSet(sql5));
        System.out.println(tcg_id);
        // System.out.println(tp_id1);
        rs5.print();
        rs6.print();
        // rs7.print();
        // rs8.print();

        TestReporter.logAPI(!oar.getResponseStatusCode().equals("200"), "An error occurred getting override Accomodation Rates: " + oar.getFaultString(), oar);
        TestReporter.assertEquals(oar.getTcgID(), getBook().getTravelComponentGroupingId(), "The response Travel Component Grouping id [" + oar.getTcgID() + "] matches Travel Component Grouping id in the request [" + getBook().getTravelComponentGroupingId() + "].");
        TestReporter.assertEquals(oar.getTcID(), getBook().getTravelComponentId(), "The response Travel Plan Segment id [" + oar.getTcID() + "] matches Travel Plan Segment id in the request [" + getBook().getTravelComponentId() + "].");

        // validates the different table values have changed
        TestReporter.assertTrue(rs5.getValue("RECOG_STS_NM", 1).equals("APPROVED"), "The column RECOG_STS_NM went from [" + rs.getValue("RECOG_STS_NM", 1).toString() + "] to [" + rs5.getValue("RECOG_STS_NM", 1).toString() + "].");
        TestReporter.assertTrue(rs.getValue("CHRG_ACTV_IN", 1).toString().equals("Y"), "The CHRG_ACTV_IN column BEFORE the override request is [" + rs.getValue("CHRG_ACTV_IN", 1).toString() + "].");
        TestReporter.assertTrue(rs5.getValue("CHRG_ACTV_IN", 1).toString().equals("N"), "The CHRG_ACTV_IN column AFTER the override request is [" + rs5.getValue("CHRG_ACTV_IN", 1).toString() + "].");
        TestReporter.assertTrue(rs5.getValue("CHRG_PST_ST_NM", 1).toString().equals("Earned"), "The CHRG_PST_ST_NM column after the override request is [" + rs5.getValue("CHRG_PST_ST_NM", 1).toString() + "].");
        TestReporter.assertTrue(rs5.getValue("FOLIO_TXN_TYP_CD", 1).toString().equals("SALE"), "The FOLIO_TXN_TYP_CD column after the override request is [" + rs5.getValue("FOLIO_TXN_TYP_CD", 1).toString() + "].");
        TestReporter.assertAll();

        // sql1
        // captures the charge items, charge amount, charge id, and charge item amount
        // TestReporter.assertTrue(numberOfChargeItems == rs5.getRowCount(), "The number of charge items [" + numberOfChargeItems + "].");
        TestReporter.assertTrue(!old_chargeAmount.equals(rs5.getValue("CHRG_AM", 4).toString()), "The old charge [" + old_chargeAmount + "] has been updated to [" + rs5.getValue("CHRG_AM", 4).toString() + "]. ");
        TestReporter.assertTrue(!old_chargeID.equals(rs5.getValue("CHRG_ID", 4).toString()), "The old charge item [" + old_chargeID + " ] has been updated to [" + rs5.getValue("CHRG_ID", 4).toString() + "]. ");
        TestReporter.assertTrue(!oldchargeItemAmount4.equals(rs5.getValue("CHRG_ITEM_AM", 4).toString()), "The charge Item amount [ " + oldchargeItemAmount4 + " ] has been updated to [" + rs5.getValue("CHRG_ITEM_AM", 4) + "].");
        TestReporter.assertAll();

        // sql2
        // captures the folio items, folio item ids, and folio item amount
        // TestReporter.assertTrue(numberOfFolioItems == rs6.getRowCount(), "The number of folio items [" + numberOfFolioItems + "].");
        TestReporter.assertTrue(!old_folioItemID.equals(rs6.getValue("FOLIO_ITEM_ID", 4).toString()), "The Folio item id [" + old_folioItemID + "] has been updated to [" + rs6.getValue("FOLIO_ITEM_ID", 4).toString() + "]. ");
        TestReporter.assertTrue(!old_folioItemAmount.equals(rs6.getValue("FOLIO_ITEM_AM", 4).toString()), "The Folio Item amount [" + old_folioItemAmount + "] has been updated to [" + rs6.getValue("FOLIO_ITEM_AM", 4).toString() + "].");
        TestReporter.assertAll();

        // sql3
        // Grabs the TC_RSN_NM in the sql7 after the request is sent
        TestReporter.assertTrue(!rs7.getValue("TC_RSN_NM", 1).toString().equals(""), "The TC_RSN_TYP_NM before the request is empty and after the request has a TC_RSN_TYP_NM of [" + rs7.getValue("TC_RSN_TYP_NM", 1).toString() + "].");

        // sql5
        // Grabs the reservation history created before and after the request is sent
        for (int j = 1; j <= rs4.getRowCount(); j++) {
            if (!rs4.getValue("RES_HIST_PROC_DS", j).equals("Rate Overridden")) {
                TestReporter.assertTrue(!rs4.getValue("RES_HIST_PROC_DS", j).equals("Rate Overridden"), "The reservation history before the request record is created is set to " + rs4.getValue("RES_HIST_PROC_DS", j));
            }

        }

        for (int i = 1; i <= rs8.getRowCount(); i++) {

            if (rs8.getValue("RES_HIST_PROC_DS", i).equals("Rate Overridden")) {
                TestReporter.assertTrue(rs8.getValue("RES_HIST_PROC_DS", i).equals("Rate Overridden"), "The reservation history record created is set to " + rs8.getValue("RES_HIST_PROC_DS", i));
            }

        }
    }
}
