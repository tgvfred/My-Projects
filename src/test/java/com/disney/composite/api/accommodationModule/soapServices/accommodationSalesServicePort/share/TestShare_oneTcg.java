package com.disney.composite.api.accommodationModule.soapServices.accommodationSalesServicePort.share;

import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.Share;
import com.disney.api.soapServices.accommodationModule.helpers.AccommodationBaseTest;
import com.disney.api.soapServices.accommodationModule.helpers.ShareHelper;
import com.disney.utils.Environment;
import com.disney.utils.Randomness;
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
            validations();
        }
    }

    public void validateResponse() {
        TestReporter.logStep("Validate date in the response node.");
        String tpsId = share.getTravelPlanSegmentId();
        String tcgId = share.getTravelComponentGroupingId();
        String tcId = share.getTravelComponentId();
        String bookingDate = share.getBookingDate();
        String travelStatus = share.getTravelStatus();

        TestReporter.softAssertEquals(getBook().getTravelPlanSegmentId(), tpsId, "Verify that the response returns the tpsID [" + getBook().getTravelPlanSegmentId() + "] that which is expected [" + tpsId + "].");
        TestReporter.softAssertEquals(getBook().getTravelComponentGroupingId(), tcgId, "Verify that the response returns the tcgId [" + getBook().getTravelComponentGroupingId() + "] that which is expected [" + tcgId + "].");
        TestReporter.softAssertEquals(getBook().getTravelComponentId(), tcId, "Verify that the response returns the tcId [" + getBook().getTravelComponentId() + "] that which is expected [" + tcId + "].");
        TestReporter.softAssertEquals(Randomness.generateCurrentXMLDate(), bookingDate, "Verify that the booking date [" + Randomness.generateCurrentXMLDate() + "] that which is expected [" + bookingDate + "].");
        TestReporter.softAssertEquals(travelStatus, "Booked", "Verify that the response returns the travel status [" + getBook().getTravelComponentId() + "] that which is expected [Booked].");
        TestReporter.assertAll();

    }

    public void validations() {
        ShareHelper helper = new ShareHelper();

        int numExpectedRecords = 2;
        helper.validateReservationHistory(numExpectedRecords, reservationHistoryId, share.getLocationId(), Randomness.generateCurrentXMLDate(), Randomness.generateCurrentXMLDate(), Randomness.generateCurrentXMLDate(), arrivalDate, departureDate);

        int numExpectedRecords2 = 1;
        helper.validateShareInFlag(numExpectedRecords2);
    }
}

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
