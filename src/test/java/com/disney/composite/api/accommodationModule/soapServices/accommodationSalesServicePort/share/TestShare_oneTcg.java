package com.disney.composite.api.accommodationModule.soapServices.accommodationSalesServicePort.share;

import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.Share;
import com.disney.api.soapServices.accommodationModule.helpers.AccommodationBaseTest;
import com.disney.utils.Environment;
import com.disney.utils.TestReporter;

public class TestShare_oneTcg extends AccommodationBaseTest {

    private Share share;

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesService", "share" })
    public void Test_Share_oneTcg() {
        share = new Share(environment, "Main_oneTcg");
        share.setTravelComponentGroupingId(getBook().getTravelComponentGroupingId());
        share.sendRequest();
        TestReporter.logAPI(!share.getResponseStatusCode().equals("200"), "Verify that no error occurred while sharing a room " + share.getFaultString(), share);

        if (Environment.isSpecialEnvironment(environment)) {
            Share clone = (Share) share.clone();
            clone.setEnvironment(Environment.getBaseEnvironmentName(environment));
            clone.sendRequest();
            if (!clone.getResponseStatusCode().equals("200")) {
                TestReporter.logAPI(!clone.getResponseStatusCode().equals("200"), "Error was returned", clone);
            }
            clone.addExcludedBaselineAttributeValidations("@xsi:nil");
            clone.addExcludedBaselineAttributeValidations("@xsi:type");
            clone.addExcludedBaselineXpathValidations("/Envelope/Header");
            TestReporter.assertTrue(clone.validateResponseNodeQuantity(share, true), "Validating Response Comparison");
            validateResponse();
        }
    }

    public void validateResponse() {
        TestReporter.logStep("Validate date in the response node.");
        String tpsId = share.getTravelPlanSegmentId();
        String tcgId = share.getTravelComponentGroupingId();
        String tcId = share.getTravelComponentId();
        String bookingDate = share.getBookingDate();

        TestReporter.softAssertEquals(getBook().getTravelPlanSegmentId(), tpsId, "Verify that the response returns the tpsID [" + getBook().getTravelPlanSegmentId() + "] that which is expected [" + tpsId + "].");
        TestReporter.softAssertEquals(getBook().getTravelComponentGroupingId(), tcgId, "Verify that the response returns the tcgId [" + getBook().getTravelComponentGroupingId() + "] that which is expected [" + tcgId + "].");
        TestReporter.softAssertEquals(getBook().getTravelComponentId(), tcId, "Verify that the response returns the tcId [" + getBook().getTravelComponentId() + "] that which is expected [" + tcId + "].");
        TestReporter.softAssertEquals("", bookingDate, "Verify that the response returns the tcId [" + getBook().getTravelComponentId() + "] that which is expected [" + tcId + "].");

        TestReporter.assertAll();
    }
}

// // Validate Reservation History for Shared Record
// String sql = "select * " +
// "from res_mgmt.res_hist a " +
// " where a.tps_id = " + tpsID;
//
// Database db = new OracleDatabase(environment, Database.DREAMS);
// Recordset rs = new Recordset(db.getResultSet(sql));
// rs.print();
// String reservationHistoryID = rs.getValue("RES_HIST_ID", 1);
// TestReporter.assertNotNull(reservationHistoryID, "No Reservation Hisotry found for this record");
//
// // Lets look for a Shared Flag
// sql = "select * from res_mgmt.acm_cmpnt a " +
// "INNER JOIN res_mgmt.res_hist b on a.acm_tc_id = b.tc_id " +
// "where tps_id = " + tpsID;
// rs = new Recordset(db.getResultSet(sql));
// rs.print();
// String shared = rs.getValue("SHR_IN", 1);
// TestReporter.assertEquals(shared, "Y", "Shared flag on record one is not set. TPSID: " + tpsID);
// shared = rs.getValue("SHR_IN", 2);
// TestReporter.assertEquals(shared, "Y", "Shared flag on record one is not set. TPSID: " + tpsID);
//
// // Validate Share Period
// // TODO - waiting for info for determining share period
//
// // Validate assignmentOwnerId changes for 'second' tcg
// // TODO -
//
// // Validate group guarantee for node charge group of tcg
// sql = "select d.GUAR_TYP_NM " +
// "from folio.EXTNL_REF a " +
// "left outer join folio.CHRG_GRP_EXTNL_REF b on a.EXTNL_REF_ID = b.EXTNL_REF_ID " +
// "left outer join folio.CHRG_GRP c on b.CHRG_GRP_ID = c.CHRG_GRP_ID " +
// "left outer join folio.NODE_CHRG_GRP d on c.CHRG_GRP_ID = d.NODE_CHRG_GRP_ID " +
// "where a.EXTNL_REF_VAL in ('472092173062')";
//
// rs = new Recordset(db.getResultSet(sql));
// rs.print();
// String guarantee = rs.getValue("GUAR_TYP_NM", 1);
//
// TestReporter.assertEquals(guarantee, "GROUP_GUARANTEED", "Group Guaranteed not set for Node Charge of Group: " + tcg);
//
//
