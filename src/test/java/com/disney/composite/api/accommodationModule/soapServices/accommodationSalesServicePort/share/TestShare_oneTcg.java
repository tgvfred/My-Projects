package com.disney.composite.api.accommodationModule.soapServices.accommodationSalesServicePort.share;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.Share;
import com.disney.api.soapServices.accommodationModule.helpers.AccommodationBaseTest;
import com.disney.api.soapServices.accommodationModule.helpers.ShareHelper;
import com.disney.utils.Environment;
import com.disney.utils.Randomness;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.database.Database;
import com.disney.utils.dataFactory.database.Recordset;
import com.disney.utils.dataFactory.database.databaseImpl.OracleDatabase;

public class TestShare_oneTcg extends AccommodationBaseTest {

    private Share share;
    String assignOwnerId;

    @BeforeMethod(alwaysRun = true)
    @Parameters("environment")
    public void setup(String environment) {
        // TestReporter.setDebugLevel(TestReporter.INFO); //Uncomment this line
        // to invoke lower levels of reporting
        Environment.getBaseEnvironmentName(environment);
        setEnvironment(environment);
        isComo.set("false");
        setDaysOut(0);
        setArrivalDate(getDaysOut());
        setNights(2);
        setDepartureDate(getNights());
        setValues();
        bookReservation();

    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesService", "share" })
    public void Test_Share_oneTcg() {

        // if (Environment.isSpecialEnvironment(environment)) {
        // if (true) {
        // throw new SkipException("Folio Fix in Progress, for now operation not supported.");
        // }
        // }
        captureOwnerId();
        share = new Share(environment, "Main_oneTcg");
        share.setTravelComponentGroupingId(getBook().getTravelComponentGroupingId());
        share.sendRequest();
        TestReporter.logAPI(!share.getResponseStatusCode().equals("200"), "Verify that no error occurred while sharing a room " + share.getFaultString(), share);
        validateResponse();
        validations();

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
        TestReporter.softAssertEquals(Randomness.generateCurrentXMLDate(), bookingDate.substring(0, 10), "Verify that the booking date [" + Randomness.generateCurrentXMLDate() + "] that which is expected [" + bookingDate.substring(0, 10) + "].");
        TestReporter.softAssertEquals(travelStatus, "Booked", "Verify that the response returns the travel status [" + travelStatus + "] that which is expected [Booked].");
        TestReporter.assertAll();

    }

    public void validations() {
        ShareHelper helper = new ShareHelper(getEnvironment());

        int numExpectedRecords = 2;
        helper.validateReservationHistory(numExpectedRecords, getBook().getTravelPlanSegmentId());

        int numExpectedRecords2 = 1;
        helper.validateShareInFlag(numExpectedRecords2, getBook().getTravelComponentGroupingId());

        int numExpectedRecords3 = 2;
        helper.validateAssignmentOwnerIdChanges(numExpectedRecords3, assignOwnerId, getBook().getTravelComponentGroupingId());

        int numExpectedRecords4 = 8;
        helper.validateFolioGuaranteeType(numExpectedRecords4, getBook().getTravelComponentGroupingId());
    }

    public void captureOwnerId() {

        String sql = "select a.* from res_mgmt.tc a join rsrc_inv.RSRC_ASGN_OWNR b on a.ASGN_OWN_ID = b.ASGN_OWNR_ID join rsrc_inv.RSRC_ASGN_REQ c on b.ASGN_OWNR_ID = c.ASGN_OWNR_ID where a.tc_grp_nb = '" + getBook().getTravelComponentGroupingId() + "'";
        Database db = new OracleDatabase(environment, Database.DREAMS);
        Recordset rs = new Recordset(db.getResultSet(sql));

        assignOwnerId = rs.getValue("ASGN_OWN_ID");

    }
}
