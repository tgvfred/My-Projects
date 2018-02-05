package com.disney.composite.api.accommodationModule.soapServices.accommodationSalesComponentServicePort.cancel;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationModule.accommodationSalesComponentService.operations.Cancel;
import com.disney.api.soapServices.accommodationModule.helpers.AccommodationBaseTest;
import com.disney.api.soapServices.accommodationModule.helpers.CancelHelper;
import com.disney.api.soapServices.accommodationModule.helpers.CheckInHelper;
import com.disney.utils.Environment;
import com.disney.utils.Sleeper;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.database.Database;
import com.disney.utils.dataFactory.database.Recordset;
import com.disney.utils.dataFactory.database.databaseImpl.OracleDatabase;

public class TestCancel_Positive extends AccommodationBaseTest {
    private static Database db;

    @Parameters("environment")
    @BeforeClass(alwaysRun = true)
    public static void beforeClass(String environment) {
        db = new OracleDatabase(environment, Database.DREAMS);
    }

    @Override
    @BeforeMethod(alwaysRun = true)
    @Parameters("environment")
    public void setup(String environment) {
        setEnvironment(environment);
        setDaysOut(30);
        setNights(1);
        setArrivalDate(getDaysOut());
        setDepartureDate(getDaysOut() + getNights());
        setValues(getEnvironment());
        isComo.set("false");
        bookReservation();
        Sleeper.sleep(5000);
    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesComponentServicePort", "cancel", "tpv3" })
    public void testCancel_Booked_tcgOnly_RoomOnly() {
        TestReporter.logScenario("Test - Cancel - TCG Only Room Only");

        Cancel cancel = buildRequestForDefaultBook();
        sendRequestAndValidateSoapResponse(cancel);
    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesComponentServicePort", "cancel", "tpv3" })
    public void testCancel_CheckingIn_tcgOnly() {
        checkingIn();

        TestReporter.logScenario("Test - Cancel - Checking In TCG Only");

        Cancel cancel = buildRequestForDefaultBook();
        sendRequestAndValidateSoapResponse(cancel);
    }

    @SuppressWarnings("static-access")
    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesComponentServicePort", "cancel", "tpv3" })
    public void testCancel_addAccommodation_cancelOne_tcgOnly() {
        String tpId = getBook().getTravelPlanId();
        String tpsId = getBook().getTravelPlanSegmentId();
        AccommodationBaseTest base = new AccommodationBaseTest();
        base.setEnvironment(getEnvironment());
        base.setDaysOut(30);
        base.setNights(1);
        base.setArrivalDate(getDaysOut());
        base.setDepartureDate(getNights());
        base.setValues(getFacilityId(), getRoomTypeCode(), getLocationId());
        setSendRequest(false);
        base.bookReservation();
        base.getBook().setTravelPlanId(tpId);
        base.getBook().setTravelPlanSegementId(tpsId);
        base.getBook().sendRequest();
        TestReporter.logAPI(!base.getBook().getResponseStatusCode().equals("200"), "Verify that no error occurred booking a second reservation: " + base.getBook().getFaultString(), base.getBook());

        TestReporter.logScenario("Test - Cancel - Add Accommodation Cancel One TCG Only");

        Cancel cancel = buildRequestForDefaultBook();
        sendRequestAndValidateSoapResponse(cancel);
        verifyNotCancelled(base.getBook().getTravelComponentGroupingId());
    }

    @SuppressWarnings("static-access")
    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesComponentServicePort", "cancel", "tpv3" })
    public void testCancel_addAccommodation_checkInOne_cancelOne_tcgOnly() {
        setDaysOut(30);
        setNights(1);
        setArrivalDate(getDaysOut());
        setDepartureDate(getDaysOut() + getNights());
        setValues(getEnvironment());
        isComo.set("false");
        bookReservation();
        checkIn();
        String tpId = getBook().getTravelPlanId();
        String tpsId = getBook().getTravelPlanSegmentId();
        AccommodationBaseTest base = new AccommodationBaseTest();
        base.setEnvironment(getEnvironment());
        base.setDaysOut(0);
        base.setNights(1);
        base.setArrivalDate(getDaysOut());
        base.setDepartureDate(getNights());
        base.setValues(getFacilityId(), getRoomTypeCode(), getLocationId());
        setSendRequest(false);
        base.bookReservation();
        base.getBook().setTravelPlanId(tpId);
        base.getBook().setTravelPlanSegementId(tpsId);
        base.getBook().sendRequest();
        TestReporter.logAPI(!base.getBook().getResponseStatusCode().equals("200"), "Verify that no error occurred booking a second reservation: " + base.getBook().getFaultString(), base.getBook());

        TestReporter.logScenario("Test - Cancel - Add Accommodation Check In One Cancel One TCG Only");
        Sleeper.sleep(5000);
        Cancel cancel = new Cancel(environment);
        cancel.setTravelComponentGroupingId(base.getBook().getTravelComponentGroupingId());
        sendRequestAndValidateSoapResponse(cancel);
        verifyNotCancelled(getBook().getTravelComponentGroupingId());
    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesComponentServicePort", "cancel", "tpv3" })
    public void testCancel_addBundle_cancelAccommodation_tcgOnly() {
        addBundle();

        TestReporter.logScenario("Test - Cancel - Add Bundle Cancel Accommodation TCG Only");

        Cancel cancel = buildRequestForDefaultBook();
        sendRequestAndValidateSoapResponse(cancel);
        verifyNotCancelled(getBundleTcg());
    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesComponentServicePort", "cancel" })
    public void testCancel_Guaranteed() {
        groupBooking();

        TestReporter.logScenario("Test - Cancel - Guaranteed");

        Cancel cancel = buildRequestForDefaultBook();
        sendRequestAndValidateSoapResponse(cancel);
    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesComponentServicePort", "cancel" })
    public void testCancel_cancellationFeeWaived() {
        TestReporter.logScenario("Test - Cancel - Cancellation Fee Waived");

        Cancel cancel = buildRequestForDefaultBook();
        cancel.setWaived("true");
        sendRequestAndValidateSoapResponse(cancel);
        verifyCancellationFee(cancel.getTravelPlanId(), true);
    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesComponentServicePort", "cancel" })
    public void testCancel_cancellationFeeNotWaived() {
        checkingIn();

        TestReporter.logScenario("Test - Cancel - Cancellation Fee Not Waived");

        Cancel cancel = buildRequestForDefaultBook();
        cancel.setWaived("false");
        sendRequestAndValidateSoapResponse(cancel);
        verifyCancellationFee(cancel.getTravelPlanId(), false);
    }

    /*
     * Utility Functions
     */
    // private AddAccommodationHelper addAccommodation() {
    // AddAccommodationHelper helper = new AddAccommodationHelper(Environment.getBaseEnvironmentName(environment), getBook());
    // helper.addAccommodation(getResortCode(), getRoomTypeCode(), getPackageCode(), getDaysOut(), getNights(), getLocationId());
    // return helper;
    // }

    private void addBundle() {
        setIsWdtcBooking(false);
        setValues(getEnvironment());
        setIsBundle(true);
        setSkipDeposit(true);
        bookReservation();
    }

    private void checkingIn() {
        setEnvironment(environment);
        setDaysOut(0);
        setNights(1);
        setArrivalDate(getDaysOut());
        setDepartureDate(getDaysOut() + getNights());
        setValues();
        bookReservation();

        CheckInHelper helper = new CheckInHelper(getEnvironment(), getBook());
        helper.checkingIn(getLocationId(), getDaysOut(), getNights(), getFacilityId());
    }

    private void checkIn() {
        setEnvironment(environment);
        setDaysOut(0);
        setNights(1);
        setArrivalDate(getDaysOut());
        setDepartureDate(getDaysOut() + getNights());
        setValues();
        bookReservation();

        CheckInHelper helper = new CheckInHelper(getEnvironment(), getBook());
        helper.checkIn(getLocationId(), getDaysOut(), getNights(), getFacilityId());
    }

    private void groupBooking() {
        setDaysOut(0);
        setNights(1);
        setArrivalDate(getDaysOut());
        setDepartureDate(getDaysOut() + getNights());
        setValues();
        setIsWdtcBooking(true);
        setAddNewGuest(true);
        bookReservation();
    }

    private Cancel buildRequestForDefaultBook() {
        Cancel cancel = new Cancel(environment);
        cancel.setTravelComponentGroupingId(getBook().getTravelComponentGroupingId());
        return cancel;
    }

    private void sendRequestAndValidateSoapResponse(Cancel cancel) {
        cancel.sendRequest();
        TestReporter.logAPI(!cancel.getResponseStatusCode().equals("200"), "The cancel was not successful: " + cancel.getFaultString(), cancel);
        String tcg = cancel.getRequestNodeValueByXPath("/Envelope/Body/cancel/request/travelComponentGroupingId");
        Recordset results = new Recordset(db.getResultSet("SELECT a.TPS_ID, b.TC_ID FROM RES_MGMT.TC_GRP a "
                + "JOIN RES_MGMT.TC b ON a.TC_GRP_NB = b.TC_GRP_NB "
                + "WHERE a.TC_GRP_NB = " + tcg + " "
                + "AND b.TC_TYP_NM = 'AccommodationComponent'"));
        TestReporter.assertGreaterThanZero(results.getRowCount());
        String tpsID = results.getValue("TPS_ID");
        String tcID = results.getValue("TC_ID");
        String tpID = null;
        try {
            tpID = cancel.getTravelPlanId();
            TestReporter.assertTrue(Long.parseLong(cancel.getTravelPlanId()) > 0, "The Travel Plan ID in the response was greater than zero.");
        } catch (NumberFormatException e) {
            TestReporter.assertTrue(false, "The Travel Plan ID in the response was a valid number.");
        }

        CancelHelper cancelHelper = new CancelHelper(Environment.getBaseEnvironmentName(environment), tpID);
        cancelHelper.verifyChargeGroupsCancelled();
        cancelHelper.verifyCancellationIsFoundInResHistory(tpsID, tcg, tcID);
        cancelHelper.verifyInventoryReleased(tcg);
        cancelHelper.verifyTcStatusByTcg(tcg, "Cancelled");
        cancelHelper.verifyExchangeFeeFound(false);
        cancelHelper.verifyTPV3GuestRecordCreated(tpID, getHouseHold().primaryGuest());
        cancelHelper.verifyTPV3RecordCreated(tpID);
        cancelHelper.verifyTPV3SalesOrderRecordCreated(tpID);
        TestReporter.assertAll();
    }

    private void verifyNotCancelled(String tcg) {
        TestReporter.logStep("Verify TC Status By TCG");
        Recordset rs = new Recordset(db.getResultSet("select count(*) count "
                + "from res_mgmt.tc_grp a, res_mgmt.tc b "
                + "where a.tc_grp_nb = b.tc_grp_nb "
                + "and a.tc_grp_nb = '" + tcg + "' "
                + "and b.TRVL_STS_NM = 'Cancelled'"));
        TestReporter.assertEquals(rs.getValue("count"), "0", "The TCs were not cancelled.");
    }

    private void verifyCancellationFee(String tpID, boolean waived) {
        TestReporter.logStep("Verify cancellation fee was created in Folio");
        Recordset results = new Recordset(db.getResultSet(" select e.* " +
                " from FOLIO.CHRG_GRP_FOLIO a  " +
                " left outer join FOLIO.FOLIO b on b.FOLIO_ID= a.CHRG_GRP_FOLIO_ID " +
                " left outer join FOLIO.FOLIO_ITEM c on c.FOLIO_ID= b.FOLIO_ID " +
                " left outer join FOLIO.CHRG_ITEM d on d.CHRG_ITEM_ID = c.FOLIO_ITEM_ID " +
                " left outer join FOLIO.CHRG e on e.CHRG_ID = d.CHRG_ID " +
                " left outer join FOLIO.PMT f on f.FOLIO_ITEM_ID = c.FOLIO_ITEM_ID " +
                " left outer join FOLIO.CHRG_GRP g on g.CHRG_GRP_ID = a.ROOT_CHRG_GRP_ID " +
                " left outer join FOLIO.CHRG_GRP_EXTNL_REF h on h.CHRG_GRP_ID=g.CHRG_GRP_ID " +
                " left outer join FOLIO.EXTNL_REF i on i.EXTNL_REF_ID=h.EXTNL_REF_ID " +
                " left outer join FOLIO.prod_chrg t on t.chrg_id=d.chrg_id " +
                " left outer join FOLIO.chrg_mkt_pkg u on u.chrg_mkt_pkg_id=t.chrg_mkt_pkg_Id  " +
                " where i.EXTNL_REF_VAL ='" + tpID + "' " +
                " AND e.CHRG_ID is not null" +
                " and CHRG_TYP_NM = 'Fee Charge'"));

        if (waived) {
            TestReporter.assertTrue(results.getRowCount() == 0, "There were no cancellation fees.");
        } else {
            TestReporter.assertGreaterThanZero(results.getRowCount());
            TestReporter.softAssertEquals(results.getValue("RECOG_STS_NM"), "APPROVED", "Validate value for RECOG_STS_NM [ " + results.getValue("RECOG_STS_NM") + " ] is [ APPROVED ] as expected");
            TestReporter.softAssertEquals(results.getValue("REV_CLS_NM"), "Cancellation Fee", "Validate value for REV_CLS_NM [ " + results.getValue("RECOG_STS_NM") + " ] is [ Cancellation Fee ] as expected");
            TestReporter.softAssertEquals(results.getValue("CHRG_ACTV_IN"), "Y", "Validate value for RECOG_STS_NM [ " + results.getValue("CHRG_ACTV_IN") + " ] is [ Y ] as expected");
            TestReporter.softAssertEquals(results.getValue("CHRG_DS"), "Cancellation Fee", "Validate value for RECOG_STS_NM [ " + results.getValue("CHRG_DS") + " ] is [ Cancellation Fee ] as expected");
            TestReporter.assertAll();
        }
    }
}
