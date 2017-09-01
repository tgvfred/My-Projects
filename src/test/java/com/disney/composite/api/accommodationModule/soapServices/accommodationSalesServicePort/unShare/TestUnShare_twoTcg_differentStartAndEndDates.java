package com.disney.composite.api.accommodationModule.soapServices.accommodationSalesServicePort.unShare;

import org.testng.SkipException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.Share;
import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.UnShare;
import com.disney.api.soapServices.accommodationModule.helpers.AccommodationBaseTest;
import com.disney.api.soapServices.accommodationModule.helpers.UnShareHelper;
import com.disney.utils.Environment;
import com.disney.utils.Randomness;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.database.Database;
import com.disney.utils.dataFactory.database.Recordset;
import com.disney.utils.dataFactory.database.databaseImpl.OracleDatabase;

public class TestUnShare_twoTcg_differentStartAndEndDates extends AccommodationBaseTest {

    private UnShare unshare;
    private Share share;
    String firstOwnerId;
    String secondOwnerId;
    String firstTCG;
    String ownerIdOne;
    String ownerIdTwo;

    @Override
    @BeforeMethod(alwaysRun = true)
    @Parameters("environment")
    public void setup(String environment) {
        setEnvironment(environment);
        setDaysOut(0);
        setNights(2);
        setArrivalDate(getDaysOut());
        setDepartureDate(getNights());
        setValues(getEnvironment());
        isComo.set("true");
        setSendRequest(false);
        bookReservation();
        getBook().setEnvironment(Environment.getBaseEnvironmentName(environment));
        getBook().sendRequest();
        TestReporter.logAPI(!getBook().getResponseStatusCode().equals("200"), "Verify that no error occurred booking a reservation: " + getBook().getFaultString(), getBook());
        firstTCG = getBook().getTravelComponentGroupingId();
        captureFirstOwnerId();

        // book second reservation.
        setDaysOut(1);
        setNights(2);
        setArrivalDate(getDaysOut());
        setDepartureDate(getNights());
        isComo.set("true");
        setSendRequest(false);
        bookReservation();
        getBook().setEnvironment(Environment.getBaseEnvironmentName(environment));
        getBook().sendRequest();
        TestReporter.logAPI(!getBook().getResponseStatusCode().equals("200"), "Verify that no error occurred booking a reservation: " + getBook().getFaultString(), getBook());
        captureSecondOwnerId();
    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesService", "unShare" })
    public void Test_unShare_twoTcgs_differentStartAndEndDates() {

        if (Environment.isSpecialEnvironment(environment)) {
            if (true) {
                throw new SkipException("Folio Fix in Progress, for now operation not supported.");
            }
        }
        share = new Share(environment, "Main_oneTcg");
        share.setTravelComponentGroupingId(firstTCG);
        share.addSharedComponent();
        share.setSecondTravelComponentGroupingId(getBook().getTravelComponentGroupingId());
        share.sendRequest();
        TestReporter.logAPI(!share.getResponseStatusCode().equals("200"), "Verify that no error occurred while sharing a room " + share.getFaultString(), share);

        unshare = new UnShare(environment, "Main");
        unshare.setTravelComponentGroupingId(firstTCG);
        unshare.sendRequest();
        TestReporter.logAPI(!unshare.getResponseStatusCode().equals("200"), "Verify that no error occurred while sharing a room " + unshare.getFaultString(), unshare);

        // verify that the owner id's for the first and second tcg do not match.
        TestReporter.softAssertTrue(firstOwnerId != secondOwnerId, "Verify the assignment owner Ids for each TCG [" + firstOwnerId + "] do not match [" + secondOwnerId + "].");

        // unshare the second reservation.
        unshare = new UnShare(environment, "Main");
        unshare.setTravelComponentGroupingId(getBook().getTravelComponentGroupingId());
        unshare.sendRequest();
        TestReporter.logAPI(!unshare.getResponseStatusCode().equals("200"), "Verify that no error occurred while sharing a room " + unshare.getFaultString(), unshare);
        validateResponse();
        validations();

        if (Environment.isSpecialEnvironment(environment)) {
            UnShare clone = (UnShare) unshare.clone();
            clone.setEnvironment(Environment.getBaseEnvironmentName(environment));
            clone.sendRequest();
            if (!clone.getResponseStatusCode().equals("200")) {
                TestReporter.logAPI(!clone.getResponseStatusCode().equals("200"), "Error was returned", clone);
            }
            clone.addExcludedBaselineAttributeValidations("@xsi:nil");
            clone.addExcludedBaselineAttributeValidations("@xsi:type");
            clone.addExcludedBaselineXpathValidations("/Envelope/Header");
            TestReporter.assertTrue(clone.validateResponseNodeQuantity(unshare, true), "Validating Response Comparison");

        }

    }

    public void validateResponse() {
        TestReporter.logStep("Validate data in the response node.");
        String tpsId = unshare.getTravelPlanSegmentId();
        String tcgId = unshare.getTravelComponentGroupingId();
        String tcId = unshare.getTravelComponentId();
        String bookingDate = unshare.getBookingDate();
        String travelStatus = unshare.getTravelStatus();

        TestReporter.softAssertEquals(getBook().getTravelPlanSegmentId(), tpsId, "Verify that the response returns the tpsID [" + getBook().getTravelPlanSegmentId() + "] that which is expected [" + tpsId + "].");
        TestReporter.softAssertEquals(getBook().getTravelComponentGroupingId(), tcgId, "Verify that the response returns the tcgId [" + getBook().getTravelComponentGroupingId() + "] that which is expected [" + tcgId + "].");
        TestReporter.softAssertEquals(getBook().getTravelComponentId(), tcId, "Verify that the response returns the tcId [" + getBook().getTravelComponentId() + "] that which is expected [" + tcId + "].");
        TestReporter.softAssertEquals(Randomness.generateCurrentXMLDate(), bookingDate.substring(0, 10), "Verify that the booking date [" + Randomness.generateCurrentXMLDate() + "] that which is expected [" + bookingDate.substring(0, 10) + "].");
        TestReporter.softAssertEquals(travelStatus, "Booked", "Verify that the response returns the travel status [" + travelStatus + "] that which is expected [Booked].");
        TestReporter.assertAll();

    }

    public void validations() {
        UnShareHelper helper = new UnShareHelper(getEnvironment());

        int numExpectedRecords = 4;
        helper.validateReservationHistory(numExpectedRecords, getBook().getTravelPlanSegmentId());

        int numExpectedRecords2 = 1;
        helper.validateShareInFlag(numExpectedRecords2, getBook().getTravelComponentGroupingId());

        helper.validateMultipleOwnerIds(firstTCG, getBook().getTravelComponentGroupingId());

    }

    public void captureFirstOwnerId() {

        String sql = "select a.* from res_mgmt.tc a join rsrc_inv.RSRC_ASGN_OWNR b on a.ASGN_OWN_ID = b.ASGN_OWNR_ID join rsrc_inv.RSRC_ASGN_REQ c on b.ASGN_OWNR_ID = c.ASGN_OWNR_ID where a.tc_grp_nb = '" + firstTCG + "'";
        Database db = new OracleDatabase(environment, Database.DREAMS);
        Recordset rs = new Recordset(db.getResultSet(sql));

        firstOwnerId = rs.getValue("ASGN_OWN_ID");

    }

    public void captureSecondOwnerId() {

        String sql = "select a.* from res_mgmt.tc a join rsrc_inv.RSRC_ASGN_OWNR b on a.ASGN_OWN_ID = b.ASGN_OWNR_ID join rsrc_inv.RSRC_ASGN_REQ c on b.ASGN_OWNR_ID = c.ASGN_OWNR_ID where a.tc_grp_nb = '" + getBook().getTravelComponentGroupingId() + "'";
        Database db = new OracleDatabase(environment, Database.DREAMS);
        Recordset rs = new Recordset(db.getResultSet(sql));

        secondOwnerId = rs.getValue("ASGN_OWN_ID");

    }

}
