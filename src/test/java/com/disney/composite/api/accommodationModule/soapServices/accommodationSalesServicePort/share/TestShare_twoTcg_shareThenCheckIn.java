package com.disney.composite.api.accommodationModule.soapServices.accommodationSalesServicePort.share;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationModule.accommodationFulfillmentServicePort.operations.CheckIn;
import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.Share;
import com.disney.api.soapServices.accommodationModule.helpers.AccommodationBaseTest;
import com.disney.api.soapServices.accommodationModule.helpers.ShareHelper;
import com.disney.api.soapServices.core.BaseSoapCommands;
import com.disney.utils.Environment;
import com.disney.utils.Randomness;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.database.Database;
import com.disney.utils.dataFactory.database.Recordset;
import com.disney.utils.dataFactory.database.SQLValidationException;
import com.disney.utils.dataFactory.database.databaseImpl.OracleDatabase;

public class TestShare_twoTcg_shareThenCheckIn extends AccommodationBaseTest {
    private Share share;
    private String firstOwnerId;
    private String secondOwnerId;
    private String firstTCG;
    private String firstTC;
    private String firstTPS;

    @BeforeMethod(alwaysRun = true)
    @Parameters("environment")
    public void setup(String environment) {
        Environment.getBaseEnvironmentName(environment);
        setEnvironment(environment);
        isComo.set("false");
        daysOut.set(0);
        nights.set(1);
        arrivalDate.set(Randomness.generateCurrentXMLDate(getDaysOut()));
        departureDate.set(Randomness.generateCurrentXMLDate(getDaysOut() + getNights()));
        setValues();
        bookReservation();

        firstTCG = getBook().getTravelComponentGroupingId();
        firstTC = getBook().getTravelComponentId();
        firstTPS = getBook().getTravelPlanSegmentId();
        captureFirstOwnerId();

        bookReservation();
        TestReporter.logAPI(!getBook().getResponseStatusCode().equals("200"), "Verify that no error occurred booking a reservation: " + getBook().getFaultString(), getBook());
    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesService", "share" })
    public void testShare_twoTcg_shareThenCheckIn() {
        captureSecondOwnerId();

        // verify that the owner id's for the first and second tcg do not match.
        TestReporter.softAssertTrue(firstOwnerId != secondOwnerId, "Verify the assignment owner Ids for each TCG [" + firstOwnerId + "] do not match [" + secondOwnerId + "].");

        share = new Share(environment, "Main_twoTcg");
        share.setTravelComponentGroupingId(firstTCG);
        share.addSharedComponent();
        share.setSecondTravelComponentGroupingId(getBook().getTravelComponentGroupingId());
        share.sendRequest();
        TestReporter.logAPI(!share.getResponseStatusCode().equals("200"), "Verify that no error occurred while sharing a room " + share.getFaultString(), share);

        // check in the first res
        CheckIn checkIn = new CheckIn(environment, "Main");
        checkIn.setTravelComponentGroupingId(firstTCG);
        checkIn.setRequestNodeValueByXPath("/Envelope/Body/checkIn/request/checkInGuestDetails/guestId", BaseSoapCommands.REMOVE_NODE.toString());
        checkIn.sendRequest();
        TestReporter.logAPI(!share.getResponseStatusCode().equals("200"), "Verify that no error occurred while checking in a reservation " + share.getFaultString(), share);

        // check in the second res
        checkIn.setTravelComponentGroupingId(getBook().getTravelComponentGroupingId());
        checkIn.sendRequest();
        TestReporter.logAPI(!share.getResponseStatusCode().equals("200"), "Verify that no error occurred while checking in the second reservation " + share.getFaultString(), share);
        validateResponse();
        validations();
    }

    public void validateResponse() {
        TestReporter.logStep("Validate data in the response node.");
        String tpsId = share.getTravelPlanSegmentId();
        String tpsId2 = share.getSecondTravelPlanSegmentId();
        String tcgId = share.getTravelComponentGroupingId();
        String tcgId2 = share.getSecondTravelComponentGroupingId();
        String tcId = share.getTravelComponentId();
        String tcId2 = share.getSecondTravelComponentId();
        String bookingDate = share.getBookingDate();
        String travelStatus = share.getTravelStatus();

        TestReporter.softAssertEquals(firstTPS, tpsId, "Verify that the response returns the tpsID [" + firstTPS + "] that which is expected [" + tpsId + "].");
        TestReporter.softAssertEquals(getBook().getTravelPlanSegmentId(), tpsId2, "Verify that the response returns the tpsID [" + getBook().getTravelPlanSegmentId() + "] that which is expected [" + tpsId2 + "].");

        TestReporter.softAssertEquals(firstTCG, tcgId, "Verify that the response returns the tcgId [" + firstTCG + "] that which is expected [" + tcgId + "].");
        TestReporter.softAssertEquals(getBook().getTravelComponentGroupingId(), tcgId2, "Verify that the response returns the tcgId [" + getBook().getTravelComponentGroupingId() + "] that which is expected [" + tcgId2 + "].");

        TestReporter.softAssertEquals(firstTC, tcId, "Verify that the response returns the tcId [" + firstTC + "] that which is expected [" + tcId + "].");
        TestReporter.softAssertEquals(getBook().getTravelComponentId(), tcId2, "Verify that the response returns the tcId [" + getBook().getTravelComponentId() + "] that which is expected [" + tcId2 + "].");

        TestReporter.softAssertEquals(Randomness.generateCurrentXMLDate(), bookingDate.substring(0, 10), "Verify that the booking date [" + Randomness.generateCurrentXMLDate() + "] that which is expected [" + bookingDate.substring(0, 10) + "].");
        TestReporter.softAssertEquals(travelStatus, "Booked", "Verify that the response returns the travel status [" + travelStatus + "] that which is expected [Booked].");
        TestReporter.assertAll();
    }

    public void validations() {
        ShareHelper helper = new ShareHelper(getEnvironment());

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