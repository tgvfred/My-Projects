package com.disney.composite.api.accommodationModule.soapServices.accommodationSalesComponentServicePort.overrideAccommodationRates;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationModule.accommodationSalesComponentService.operations.OverrideAccommodationRatesRequest;
import com.disney.api.soapServices.accommodationModule.helpers.AccommodationBaseTest;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.database.Database;
import com.disney.utils.dataFactory.database.Recordset;
import com.disney.utils.dataFactory.database.databaseImpl.OracleDatabase;

public class TestOverrideAccommodationRates_RO_OneAdultOneNight extends AccommodationBaseTest {

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

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesService", "overrideAccommodationRates" })
    public void testOverrideAccommodationRates_RO_OneAdultOneNight() {
        String tp_id1 = getBook().getTravelPlanId();
        String tp_id2;
        String tcg_id;
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
                + " where a.tp_id = '" + tp_id1 + "')  )";
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
                + " where a.tp_id = '[[TP_ID]]'))";

        String sql3 = " select a.TC_RSN_TYP_NM, b.LGCY_RSN_CD, b.TC_RSN_NM"
                + " from res_mgmt.tc_rsn a"
                + " join res_mgmt.prdf_tc_rsn b on a.PRDF_TC_RSN_ID = b.PRDF_TC_RSN_ID"
                + " where a.tc_id in ("
                + " select x.tc_id"
                + " from res_mgmt.tc x"
                + " where x.tc_grp_nb = '[[TCG_ID]]')";

        String sql5 = " select unique(a.RES_HIST_PROC_DS)"
                + " from res_mgmt.res_hist a";

        Database db = new OracleDatabase(environment, Database.DREAMS);
        Recordset rs = new Recordset(db.getResultSet(sql1));
        // rs.getValue("CHRG_GRP_ID");
        rs.print();
        int numberOfCharges = rs.getRowCount();
        String old_charge = rs.getValue("CHRG_AM", 4);
        // int chargeAmount;

        // int numberOfChargeItems=
        // int old_new_chargeItem;
        // int old_new_chargeItemAmount;
        String basePriceSql = rs.getValue("CHRG_ITEM_AM", 4);
        String locationId = rs.getValue("WRK_LOC_ID", 4);
        TestReporter.assertTrue(numberOfCharges >= 1, "The number of charges is " + numberOfCharges);

        //
        OverrideAccommodationRatesRequest oar = new OverrideAccommodationRatesRequest(environment, "Main");

        oar.setTpsID(getBook().getTravelPlanSegmentId());
        oar.setTcgId(getBook().getTravelComponentGroupingId());
        oar.setBasePrice(basePriceSql);
        oar.setRackRateRate("1.0");
        oar.setOverrideReason("RTPRTSIZE");
        oar.setLocationId(locationId);

        oar.sendRequest();

        System.out.println(oar.getRequest());
        System.out.println(oar.getResponse());
        TestReporter.logAPI(!oar.getResponseStatusCode().equals("200"), "An error occurred getting override Accomodation Rates: " + oar.getFaultString(), oar);
        TestReporter.assertEquals(oar.getTcgID(), getBook().getTravelComponentGroupingId(), "The response Travel Component Grouping id [" + oar.getTcgID() + "] matches Travel Component Grouping id in the request [" + getBook().getTravelComponentGroupingId() + "].");
        TestReporter.assertEquals(oar.getTcID(), getBook().getTravelComponentId(), "The response Travel Plan Segment id [" + oar.getTcID() + "] matches Travel Plan Segment id in the request [" + getBook().getTravelComponentId() + "].");
        TestReporter.assertTrue(!old_charge.equals(rs.getValue("CHRG_AM", 4).toString()), "The old charge [" + old_charge + "] has been updated [" + rs.getValue("CHRG_AM", 4).toString() + "] ");

        TestReporter.assertAll();

        rs.print();
    }

}
// Validate response data
// Verify the number of charges (SQL 1)
// Verify the old charge is replaced with a new charge (SQL 1)
// Verify the charge amount is different from the old amount (SQL 1)
// Veriy the number of charge items (SQL 1)
// Verify old charge items are replace with new charge items (SQL1)
// Verify the charge item amounts are different from the old amounts (SQL 1)
// Verify that the charge item amount for the "Base" charge item is that which is passed into the request (SQL 1)
// Verify the location ID (WRK_LOC_ID) is that which is passed into the RQ (SQL 1)

// Veriy the number of folio items (SQL 2)
// Verify old folio items are replace with new folio items (SQL2)
// Validate old vs new responses
// Verify TC reason data (SQL 3)
// Verify reservation history record created (SQL5) for "Rate Overridden"