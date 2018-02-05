package com.disney.composite.api.accommodationModule.soapServices.accommodationSalesComponentServicePort.overrideAccommodationRates;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationModule.accommodationSalesComponentService.operations.OverrideAccommodationRatesRequest;
import com.disney.api.soapServices.accommodationModule.helpers.AccommodationBaseTest;
import com.disney.utils.Environment;
import com.disney.utils.Sleeper;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.database.Database;
import com.disney.utils.dataFactory.database.Recordset;
import com.disney.utils.dataFactory.database.databaseImpl.OracleDatabase;

public class TestOverrideAccommodationRates_RO_OneAdultOneNight extends AccommodationBaseTest {
    Map<String, String> allPairs = new HashMap<String, String>();
    Map<String, String> allPairs2 = new HashMap<String, String>();
    Map<String, String> allPairs3 = new HashMap<String, String>();
    Map<String, String> allPairs4 = new HashMap<String, String>();
    Map<String, String> allPairs5 = new HashMap<String, String>();
    Map<String, String> allPairs6 = new HashMap<String, String>();

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
    public void testOverrideAccommodationRates_RO_OneAdultOneNight() {
        String tp_id1 = getBook().getTravelPlanId();
        String tp_id2;
        String tcg_id = getBook().getTravelComponentGroupingId();

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

        int numberOfCharges = rs.getRowCount();
        int numberOfFolioItemsBR = rs.getRowCount();
        int numberOfChargeItemsBR = rs.getRowCount();
        String locationId = rs.getValue("WRK_LOC_ID", 4);
        TestReporter.assertTrue(numberOfCharges >= 1, "The number of charges is " + numberOfCharges);

        // creates and sends request
        OverrideAccommodationRatesRequest oar = new OverrideAccommodationRatesRequest(environment, "Main");

        oar.setTpsID(getBook().getTravelPlanSegmentId());
        oar.setTcgId(tcg_id);
        oar.setBasePrice("4");
        oar.setRackRateRate("1.0");
        oar.setDate(getArrivalDate());
        oar.setRackRateDate(getArrivalDate());
        oar.setOverrideReason("RTPRTSIZE");
        oar.setLocationId(locationId);

        oar.sendRequest();
        Sleeper.sleep(5000);

        // record set after request
        Recordset rs5 = new Recordset(db.getResultSet(sql1));
        Recordset rs6 = new Recordset(db.getResultSet(sql2));
        Recordset rs7 = new Recordset(db.getResultSet(sql3));
        Recordset rs8 = new Recordset(db.getResultSet(sql5));

        int numberOfChargeItemsAR = rs5.getRowCount();
        int numberOfFolioItemsAR = rs6.getRowCount();

        TestReporter.logAPI(!oar.getResponseStatusCode().equals("200"), "An error occurred getting override Accomodation Rates: " + oar.getFaultString(), oar);
        TestReporter.assertEquals(oar.getTcgID(), getBook().getTravelComponentGroupingId(), "The response Travel Component Grouping id [" + oar.getTcgID() + "] matches Travel Component Grouping id in the request [" + getBook().getTravelComponentGroupingId() + "].");
        TestReporter.assertEquals(oar.getTcID(), getBook().getTravelComponentId(), "The response Travel Plan Segment id [" + oar.getTcID() + "] matches Travel Plan Segment id in the request [" + getBook().getTravelComponentId() + "].");

        int column1Pos = rs.getColumnIndex("CHRG_ITEM_ID");
        int column2Pos = rs.getColumnIndex("CHRG_ITEM_AM");

        int column3Pos = rs5.getColumnIndex("CHRG_ITEM_ID");
        int column4Pos = rs5.getColumnIndex("CHRG_ITEM_AM");

        int column5Pos = rs.getColumnIndex("CHRG_ID");
        int column6Pos = rs.getColumnIndex("CHRG_AM");

        int column7Pos = rs5.getColumnIndex("CHRG_ID");
        int column8Pos = rs5.getColumnIndex("CHRG_AM");

        int column9Pos = rs2.getColumnIndex("FOLIO_ITEM_ID");
        int column10Pos = rs2.getColumnIndex("FOLIO_ITEM_AM");

        int column11Pos = rs6.getColumnIndex("FOLIO_ITEM_ID");
        int column12Pos = rs6.getColumnIndex("FOLIO_ITEM_AM");

        for (int i = 1; i <= rs.getRowCount(); i++) {

            String value = rs.getValue(column1Pos, i);
            String value2 = rs.getValue(column2Pos, i);
            String value3 = rs5.getValue(column3Pos, i);
            String value4 = rs5.getValue(column4Pos, i);
            String value5 = rs.getValue(column5Pos, i);
            String value6 = rs.getValue(column6Pos, i);
            String value7 = rs5.getValue(column7Pos, i);
            String value8 = rs5.getValue(column8Pos, i);

            allPairs.put(value, value2);
            allPairs2.put(value3, value4);
            allPairs3.put(value5, value6);
            allPairs4.put(value7, value8);

        }

        for (int w = 1; w <= rs2.getRowCount(); w++) {

            String value9 = rs2.getValue(column9Pos, w);
            String value10 = rs2.getValue(column10Pos, w);
            String value11 = rs6.getValue(column11Pos, w);
            String value12 = rs6.getValue(column12Pos, w);

            allPairs5.put(value9, value10);
            allPairs6.put(value11, value12);
        }

        Object[][] objKeyValue = new Object[allPairs.size()][2];
        int p = 0;
        for (String key : allPairs.keySet()) {
            objKeyValue[p][0] = key;
            objKeyValue[p][1] = allPairs.get(key);
            p++;
        }

        Object[][] objKeyValue2 = new Object[allPairs2.size()][2];
        int p2 = 0;
        for (String key : allPairs2.keySet()) {
            objKeyValue2[p2][0] = key;
            objKeyValue2[p2][1] = allPairs2.get(key);
            p2++;
        }

        Object[][] objKeyValue3 = new Object[allPairs3.size()][2];
        int p3 = 0;
        for (String key : allPairs3.keySet()) {
            objKeyValue3[p3][0] = key;
            objKeyValue3[p3][1] = allPairs3.get(key);
            p3++;
        }

        Object[][] objKeyValue4 = new Object[allPairs4.size()][2];
        int p4 = 0;
        for (String key : allPairs4.keySet()) {
            objKeyValue4[p4][0] = key;
            objKeyValue4[p4][1] = allPairs4.get(key);
            p4++;
        }

        Object[][] objKeyValue5 = new Object[allPairs5.size()][2];
        int p5 = 0;
        for (String key : allPairs5.keySet()) {
            objKeyValue5[p5][0] = key;
            objKeyValue5[p5][1] = allPairs5.get(key);
            p5++;
        }

        Object[][] objKeyValue6 = new Object[allPairs6.size()][2];
        int p6 = 0;
        for (String key : allPairs6.keySet()) {
            objKeyValue6[p6][0] = key;
            objKeyValue6[p6][1] = allPairs6.get(key);
            p6++;
        }

        Collection<String> l = allPairs.values();
        Collection<String> l2 = allPairs2.values();
        Collection<String> l3 = allPairs3.values();
        Collection<String> l4 = allPairs4.values();
        Collection<String> l5 = allPairs5.values();
        Collection<String> l6 = allPairs6.values();

        // sql1
        // captures number of charge items, charge amount, charge id and charge item amount
        for (int o = 0; o <= rs.getRowCount() - 1; o++) {

            TestReporter.assertTrue(!l.toArray()[o].equals(l2.toArray()[o]), "In row [" + o + "] the  old charge item amount is [" + l.toArray()[o] + "] has been updated to [" + l2.toArray()[o] + "]. ");
            TestReporter.assertTrue(!allPairs.keySet().toArray()[o].equals(allPairs2.keySet().toArray()[o]), "In row [" + o + "] the  old charge item id is [" + allPairs.keySet().toArray()[o] + "] has been updated to [" + allPairs2.keySet().toArray()[o] + "]. ");

        }
        TestReporter.assertTrue(!l3.toArray()[0].equals(l4.toArray()[0]), "In all the rows the old charge amount is [" + l3.toArray()[0] + "] has been updated to [" + l4.toArray()[0] + "]. ");
        TestReporter.assertTrue(!allPairs3.keySet().toArray()[0].equals(allPairs4.keySet().toArray()[0]), "In all the rows the old charge id is [" + allPairs3.keySet().toArray()[0] + "] has been updated to [" + allPairs4.keySet().toArray()[0] + "]. ");
        TestReporter.assertEquals(numberOfChargeItemsBR, numberOfChargeItemsAR, "The number of charge items before the request is [" + numberOfChargeItemsBR + "] and after the request is [" + numberOfChargeItemsAR + "].");

        // sql2
        // captures the number of folio items, folio item id, and folio item amount
        for (int o = 0; o <= rs2.getRowCount() - 1; o++) {

            TestReporter.assertTrue(!l5.toArray()[o].equals(l6.toArray()[o]), "In row [" + o + "] the  old folio item id is [" + l5.toArray()[o] + "] has been updated to [" + l6.toArray()[o] + "]. ");
            TestReporter.assertTrue(!allPairs5.keySet().toArray()[o].equals(allPairs6.keySet().toArray()[o]), "In row [" + o + "] the  old folio item amount is [" + allPairs5.keySet().toArray()[o] + "] has been updated to [" + allPairs6.keySet().toArray()[o] + "]. ");

        }

        TestReporter.assertEquals(numberOfFolioItemsBR, numberOfFolioItemsAR, "The number of folio items before the request is [" + numberOfFolioItemsBR + "]and after the request is [" + numberOfFolioItemsAR + "].");

        // sql3
        // Grabs the TC_RSN_NM in the sql7 after the request is sent
        TestReporter.assertTrue(!rs7.getValue("TC_RSN_NM", 1).toString().equals(""), "The TC_RSN_TYP_NM before the request is empty and after the request has a TC_RSN_TYP_NM of [" + rs7.getValue("TC_RSN_TYP_NM", 1).toString() + "].");

        // sql5
        // grabs the reservation history before and after the request
        for (int j = 1; j <= rs4.getRowCount(); j++) {
            if (!rs4.getValue("RES_HIST_PROC_DS", j).equals("Rate Overridden")) {
                TestReporter.assertTrue(!rs4.getValue("RES_HIST_PROC_DS", j).equals("Rate Overridden"), "The reservation history before the request record is created is set to [" + rs4.getValue("RES_HIST_PROC_DS", j) + "].");
            }

        }

        for (int i = 1; i <= rs8.getRowCount(); i++) {

            if (rs8.getValue("RES_HIST_PROC_DS", i).equals("Rate Overridden")) {
                TestReporter.assertTrue(rs8.getValue("RES_HIST_PROC_DS", i).equals("Rate Overridden"), "The reservation history after the request record is created is set to [" + rs8.getValue("RES_HIST_PROC_DS", i) + "].");
            }

        }

        // old vs. new

        if (Environment.isSpecialEnvironment(getEnvironment())) {

            OverrideAccommodationRatesRequest clone = (OverrideAccommodationRatesRequest) oar.clone();
            clone.setEnvironment(Environment.getBaseEnvironmentName(getEnvironment()));

            int tries = 0;
            int maxTries = 40;
            boolean success = false;
            tries = 0;
            maxTries = 40;
            success = false;
            do {
                Sleeper.sleep(500);
                clone.sendRequest();
                if (oar.getResponseStatusCode().equals("200")) {
                    success = true;
                } else {
                    tries++;
                }
            } while (tries < maxTries && !success);
            if (!clone.getResponseStatusCode().equals("200")) {
                TestReporter.logAPI(!clone.getResponseStatusCode().equals("200"),
                        "Error was returned: " + clone.getFaultString(), clone);
            }
            clone.addExcludedBaselineXpathValidations("/Envelope/Header");

            clone.addExcludedXpathValidations("/Envelope/Body/overrideAccommodationRatesResponse/return/externalReferences");

            clone.addExcludedXpathValidations("/Envelope/Body/overrideAccommodationRatesResponse/return/externalReferences/externalReferenceNumber");
            clone.addExcludedXpathValidations("/Envelope/Body/overrideAccommodationRatesResponse/return/externalReferences/externalReferenceSource[text()='Accovia'");

            TestReporter.assertTrue(clone.validateResponseNodeQuantity(oar, true), "Validating Response Comparison");
            // }

        }
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