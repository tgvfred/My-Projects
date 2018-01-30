package com.disney.composite.api.accommodationModule.soapServices.accommodationSalesServicePort.unShare;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.Share;
import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.UnShare;
import com.disney.api.soapServices.accommodationModule.helpers.AccommodationBaseTest;
import com.disney.api.soapServices.accommodationModule.helpers.UnShareHelper;
import com.disney.utils.Environment;
import com.disney.utils.Randomness;
import com.disney.utils.Sleeper;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.database.Database;
import com.disney.utils.dataFactory.database.Recordset;
import com.disney.utils.dataFactory.database.SQLValidationException;
import com.disney.utils.dataFactory.database.databaseImpl.OracleDatabase;

public class TestUnShare_twoTcg extends AccommodationBaseTest {
    private UnShare unshare;
    private Share share;
    private String firstOwnerId;
    private String secondOwnerId;
    private String firstTCG;
    private String firstTPS;
    private String firstTC;
    private String firstLocationId;

    @Override
    @BeforeMethod(alwaysRun = true)
    @Parameters("environment")
    public void setup(String environment) {
        setEnvironment(environment);
        setDaysOut(40);
        setNights(1);
        setArrivalDate(getDaysOut());
        setDepartureDate(getNights());
        setValues(getEnvironment());
        isComo.set("true");
        setSendRequest(false);
        bookReservation();
        getBook().sendRequest();
        TestReporter.logAPI(!getBook().getResponseStatusCode().equals("200"), "Verify that no error occurred booking a reservation: " + getBook().getFaultString(), getBook());
        firstTCG = getBook().getTravelComponentGroupingId();
        firstTPS = getBook().getTravelPlanSegmentId();
        firstTC = getBook().getTravelComponentId();
        captureFirstOwnerId();
        firstLocationId = getLocationId();

        // book second reservation.
        setDaysOut(40);
        setNights(2);
        setArrivalDate(getDaysOut());
        setDepartureDate(getNights());
        isComo.set("true");
        setSendRequest(false);
        bookReservation();
        getBook().sendRequest();
        TestReporter.logAPI(!getBook().getResponseStatusCode().equals("200"), "Verify that no error occurred booking a reservation: " + getBook().getFaultString(), getBook());
        captureSecondOwnerId();
    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesService", "unShare" })
    public void Test_unShare_twoTcgs() {
        share = new Share(environment, "Main_oneTcg");
        share.setTravelComponentGroupingId(firstTCG);
        share.addSharedComponent();
        share.setSecondTravelComponentGroupingId(getBook().getTravelComponentGroupingId());

        // Add a wait to avoid async issues
        Sleeper.sleep(5000);

        share.sendRequest();
        TestReporter.logAPI(!share.getResponseStatusCode().equals("200"), "Verify that no error occurred while sharing a room " + share.getFaultString(), share);

        unshare = new UnShare(environment, "Main");
        unshare.setTravelComponentGroupingId(firstTCG);
        unshare.setLocationId(firstLocationId);

        // Add a wait to avoid async issues
        Sleeper.sleep(5000);

        unshare.sendRequest();
        TestReporter.logAPI(!unshare.getResponseStatusCode().equals("200"), "Verify that no error occurred while sharing a room " + unshare.getFaultString(), unshare);

        // verify that the owner id's for the first and second tcg do not match.
        TestReporter.softAssertTrue(firstOwnerId != secondOwnerId, "Verify the assignment owner Ids for each TCG [" + firstOwnerId + "] do not match [" + secondOwnerId + "].");

        validateResponse();
        validations();

        share.sendRequest();
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
        String tpsId = unshare.getShareRoomDetailsTravelPlanSegmentId("1", "1");
        String tcgId = unshare.getSharedRoomDetailsTravelComponentGroupingId("1", "1");
        String tcId = unshare.getSharedRoomDetailsTravelComponentId("1", "1");
        String bookingDate = unshare.getBookingDate();
        String travelStatus = unshare.getTravelStatus();

        TestReporter.softAssertEquals(getBook().getTravelPlanSegmentId(), tpsId, "Verify that the response returns the tpsID [" + getBook().getTravelPlanSegmentId() + "] that which is expected [" + tpsId + "].");
        TestReporter.softAssertEquals(getBook().getTravelComponentGroupingId(), tcgId, "Verify that the response returns the tcgId [" + getBook().getTravelComponentGroupingId() + "] that which is expected [" + tcgId + "].");
        TestReporter.softAssertEquals(getBook().getTravelComponentId(), tcId, "Verify that the response returns the tcId [" + getBook().getTravelComponentId() + "] that which is expected [" + tcId + "].");
        TestReporter.softAssertEquals(Randomness.generateCurrentXMLDate(), bookingDate.substring(0, 10), "Verify that the booking date [" + Randomness.generateCurrentXMLDate() + "] that which is expected [" + bookingDate.substring(0, 10) + "].");
        TestReporter.softAssertEquals(travelStatus, "Booked", "Verify that the response returns the travel status [" + travelStatus + "] that which is expected [Booked].");

        tpsId = unshare.getShareRoomDetailsTravelPlanSegmentId("2", "1");
        tcgId = unshare.getUnSharedRoomDetailsTravelComponentGroupingId("2", "1");
        tcId = unshare.getUnSharedRoomDetailsTravelComponentId("2", "1");
        bookingDate = unshare.getBookingDate();
        travelStatus = unshare.getTravelStatus();
        TestReporter.softAssertEquals(firstTPS, tpsId, "Verify that the response returns the tpsID [" + firstTPS + "] that which is expected [" + tpsId + "].");
        TestReporter.softAssertEquals(firstTCG, tcgId, "Verify that the response returns the tcgId [" + firstTCG + "] that which is expected [" + tcgId + "].");
        TestReporter.softAssertEquals(firstTC, tcId, "Verify that the response returns the tcId [" + firstTC + "] that which is expected [" + tcId + "].");
        TestReporter.softAssertEquals(Randomness.generateCurrentXMLDate(), bookingDate.substring(0, 10), "Verify that the booking date [" + Randomness.generateCurrentXMLDate() + "] that which is expected [" + bookingDate.substring(0, 10) + "].");
        TestReporter.softAssertEquals(travelStatus, "Booked", "Verify that the response returns the travel status [" + travelStatus + "] that which is expected [Booked].");

        TestReporter.assertAll();
    }

    public void validations() {
        UnShareHelper helper = new UnShareHelper(getEnvironment());

        int numExpectedRecords = 3;
        helper.validateReservationHistory(numExpectedRecords, getBook().getTravelPlanSegmentId());

        int numExpectedRecords2 = 1;
        helper.validateShareInFlag(numExpectedRecords2, getBook().getTravelComponentGroupingId());

        helper.validateMultipleOwnerIds(firstTCG, getBook().getTravelComponentGroupingId());
    }

    public void captureFirstOwnerId() {
        String sql = "select a.* from res_mgmt.tc a join rsrc_inv.RSRC_ASGN_OWNR b on a.ASGN_OWN_ID = b.ASGN_OWNR_ID join rsrc_inv.RSRC_ASGN_REQ c on b.ASGN_OWNR_ID = c.ASGN_OWNR_ID where a.tc_grp_nb = '" + firstTCG + "'";
        Database db = new OracleDatabase(environment, Database.DREAMS);
        Recordset rs = new Recordset(db.getResultSet(sql));

        if (rs.getRowCount() == 0) {
            throw new SQLValidationException("No owner id found for tcg ID [ " + firstTCG + " ]", sql);
        }

        firstOwnerId = rs.getValue("ASGN_OWN_ID");
    }

    public void captureSecondOwnerId() {
        String sql = "select a.* from res_mgmt.tc a join rsrc_inv.RSRC_ASGN_OWNR b on a.ASGN_OWN_ID = b.ASGN_OWNR_ID join rsrc_inv.RSRC_ASGN_REQ c on b.ASGN_OWNR_ID = c.ASGN_OWNR_ID where a.tc_grp_nb = '" + getBook().getTravelComponentGroupingId() + "'";
        Database db = new OracleDatabase(environment, Database.DREAMS);
        Recordset rs = new Recordset(db.getResultSet(sql));

        if (rs.getRowCount() == 0) {
            throw new SQLValidationException("No owner id found for tcg ID [ " + getBook().getTravelComponentGroupingId() + " ]", sql);
        }

        secondOwnerId = rs.getValue("ASGN_OWN_ID");
    }
}