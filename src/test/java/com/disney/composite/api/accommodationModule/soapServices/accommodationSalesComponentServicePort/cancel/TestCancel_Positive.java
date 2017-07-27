package com.disney.composite.api.accommodationModule.soapServices.accommodationSalesComponentServicePort.cancel;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationModule.accommodationSalesComponentServicePort.operations.Cancel;
import com.disney.api.soapServices.accommodationModule.helpers.AccommodationBaseTest;
import com.disney.api.soapServices.accommodationModule.helpers.AddAccommodationHelper;
import com.disney.api.soapServices.accommodationModule.helpers.CancelHelper;
import com.disney.api.soapServices.accommodationModule.helpers.CheckInHelper;
import com.disney.api.soapServices.core.BaseSoapCommands;
import com.disney.api.soapServices.core.BaseSoapService;
import com.disney.api.soapServices.pricingModule.packagingService.operations.FindTicketPriceGridByPackage;
import com.disney.api.soapServices.pricingModule.packagingService.operations.GetTicketProducts;
import com.disney.utils.Environment;
import com.disney.utils.PackageCodes;
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

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesComponentServicePort", "cancel" })
    public void testCancel_Booked_tcgOnly_RoomOnly() {
        TestReporter.logScenario("Test - Cancel - TCG Only Room Only");

        Cancel cancel = new Cancel(environment);
        cancel.setTravelComponentGroupingId(getBook().getTravelComponentGroupingId());
        sendRequestAndValidateSoapResponse(cancel);
    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesComponentServicePort", "cancel" })
    public void testCancel_CheckingIn_tcgOnly() {
        checkingIn();

        TestReporter.logScenario("Test - Cancel - Checking In TCG Only");

        Cancel cancel = buildRequestForDefaultBook();
        sendRequestAndValidateSoapResponse(cancel);
    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesComponentServicePort", "cancel" })
    public void testCancel_addAccommodation_cancelOne_tcgOnly() {
        AddAccommodationHelper helper = addAccommodation();

        TestReporter.logScenario("Test - Cancel - Add Accommodation Cancel One TCG Only");

        Cancel cancel = buildRequestForDefaultBook();
        sendRequestAndValidateSoapResponse(cancel);
        verifyNotCancelled(helper.getTcgId());
    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesComponentServicePort", "cancel" })
    public void testCancel_addAccommodation_checkInOne_cancelOne_tcgOnly() {
        checkIn();
        AddAccommodationHelper helper = addAccommodation();

        TestReporter.logScenario("Test - Cancel - Add Accommodation Check In One Cancel One TCG Only");

        Cancel cancel = new Cancel(environment);
        cancel.setTravelComponentGroupingId(helper.getTcgId());
        sendRequestAndValidateSoapResponse(cancel);
        verifyNotCancelled(getBook().getTravelComponentGroupingId());
    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesComponentServicePort", "cancel" })
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
    public void testCancel_groupBookingWithTickets() {
        groupBooking();
        addTickets();

        TestReporter.logScenario("Test - Cancel - Group Booking With Tickets");

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
        TestReporter.logScenario("Test - Cancel - Cancellation Fee Not Waived");

        Cancel cancel = buildRequestForDefaultBook();
        cancel.setWaived("false");
        sendRequestAndValidateSoapResponse(cancel);
        verifyCancellationFee(cancel.getTravelPlanId(), false);
    }

    /*
     * Utility Functions
     */
    private AddAccommodationHelper addAccommodation() {
        AddAccommodationHelper helper = new AddAccommodationHelper(environment, getBook());
        helper.addAccommodation(getResortCode(), getRoomTypeCode(), getPackageCode(), getDaysOut(), getNights(), getLocationId());
        return helper;
    }

    private void addBundle() {
        setIsWdtcBooking(false);
        setValues(getEnvironment());
        setIsBundle(true);
        setSkipDeposit(true);
        bookReservation();
    }

    private void addTickets() {
        getBook().setRoomDetailsBlockCode("01825");
        setArrivalDate(45);
        setDepartureDate(1);

        boolean success = false;
        String packageCode = null;
        PackageCodes pkg = new PackageCodes();
        for (int i = 0; i < 100 && !success; i++) {
            try {
                packageCode = pkg.retrievePackageCode(environment, String.valueOf(getDaysOut()),
                        getLocationId(), "WDW PKG", "*WDTC", getResortCode(), getRoomTypeCode(), "R MYW Pkg + Deluxe Dining");
                success = true;
            } catch (AssertionError e) {
                setValues();
            }
        }
        TestReporter.assertTrue(success, "The package code was found successfully.");
        getBook().setRoomDetailsPackageCode(packageCode);
        getBook().setRoomDetailsResortCode(getResortCode());
        getBook().setRoomDetailsRoomTypeCode(getRoomTypeCode());
        getBook().setAreaPeriodStartDate(getArrivalDate());
        getBook().setAreaPeriodEndDate(getDepartureDate());

        FindTicketPriceGridByPackage find = new FindTicketPriceGridByPackage(environment);
        find.setPackageCode(packageCode);
        find.sendRequest();
        TestReporter.assertTrue(find.getResponseStatusCode().equals("200"), "Verify that no error occurred finding tickets for package code [" + packageCode + "].");
        trySetRequestNodeValueByXPath(getBook(), "//replaceAllForTravelPlanSegment/request/roomDetails/ticketGroup", find.getTicketGroupName());

        GetTicketProducts get = new GetTicketProducts(environment, "Main");
        get.setTicketGroupName(find.getTicketGroupName());
        get.sendRequest();
        TestReporter.assertTrue(get.getResponseStatusCode().equals("200"), "Verify that no error occurred finding ticket products for ticket group name [" + find.getTicketGroupName() + "].");
        String admissionProductId = get.getAdmissionProductIdByTicketDescription("2 Day Base Ticket");
        trySetRequestNodeValueByXPath(getBook(), "/Envelope/Body/replaceAllForTravelPlanSegment/request/roomReservationRequest/roomDetails/ticketDetails/baseAdmissionProductId", admissionProductId);
        trySetRequestNodeValueByXPath(getBook(), "/Envelope/Body/replaceAllForTravelPlanSegment/request/roomReservationRequest/roomDetails/ticketDetails/code", admissionProductId);

        trySetRequestNodeValueByXPath(getBook(), "/Envelope/Body/replaceAllForTravelPlanSegment/request/roomReservationRequest/roomDetails/roomReservationDetail/guestReferenceDetails/guest/firstName", getHouseHold().primaryGuest().getFirstName());
        trySetRequestNodeValueByXPath(getBook(), "/Envelope/Body/replaceAllForTravelPlanSegment/request/roomReservationRequest/roomDetails/roomReservationDetail/guestReferenceDetails/guest/lastName", getHouseHold().primaryGuest().getLastName());
        trySetRequestNodeValueByXPath(getBook(), "/Envelope/Body/replaceAllForTravelPlanSegment/request/roomReservationRequest/roomDetails/roomReservationDetail/guestReferenceDetails/guest/addressDetails/addressLine1", getHouseHold().primaryGuest().primaryAddress().getAddress1());
        trySetRequestNodeValueByXPath(getBook(), "/Envelope/Body/replaceAllForTravelPlanSegment/request/roomReservationRequest/roomDetails/roomReservationDetail/guestReferenceDetails/guest/addressDetails/city", getHouseHold().primaryGuest().primaryAddress().getCity());
        trySetRequestNodeValueByXPath(getBook(), "/Envelope/Body/replaceAllForTravelPlanSegment/request/roomReservationRequest/roomDetails/roomReservationDetail/guestReferenceDetails/guest/addressDetails/country", getHouseHold().primaryGuest().getAllAddresses().get(0).getCountry());
        trySetRequestNodeValueByXPath(getBook(), "/Envelope/Body/replaceAllForTravelPlanSegment/request/roomReservationRequest/roomDetails/roomReservationDetail/guestReferenceDetails/guest/addressDetails/postalCode", getHouseHold().primaryGuest().primaryAddress().getZipCode());
        trySetRequestNodeValueByXPath(getBook(), "/Envelope/Body/replaceAllForTravelPlanSegment/request/roomReservationRequest/roomDetails/roomReservationDetail/guestReferenceDetails/guest/addressDetails/state", getHouseHold().primaryGuest().primaryAddress().getState());

        trySetRequestNodeValueByXPath(getBook(), "/Envelope/Body/replaceAllForTravelPlanSegment/request/roomReservationRequest/roomDetails/ticketDetails/guestReference/guest/firstName", getHouseHold().primaryGuest().getFirstName());
        trySetRequestNodeValueByXPath(getBook(), "/Envelope/Body/replaceAllForTravelPlanSegment/request/roomReservationRequest/roomDetails/ticketDetails/guestReference/guest/lastName", getHouseHold().primaryGuest().getLastName());
        trySetRequestNodeValueByXPath(getBook(), "/Envelope/Body/replaceAllForTravelPlanSegment/request/roomReservationRequest/roomDetails/ticketDetails/guestReference/guest/addressDetails/addressLine1", getHouseHold().primaryGuest().primaryAddress().getAddress1());
        trySetRequestNodeValueByXPath(getBook(), "/Envelope/Body/replaceAllForTravelPlanSegment/request/roomReservationRequest/roomDetails/ticketDetails/guestReference/guest/addressDetails/city", getHouseHold().primaryGuest().primaryAddress().getCity());
        trySetRequestNodeValueByXPath(getBook(), "/Envelope/Body/replaceAllForTravelPlanSegment/request/roomReservationRequest/roomDetails/ticketDetails/guestReference/guest/addressDetails/country", getHouseHold().primaryGuest().getAllAddresses().get(0).getCountry());
        trySetRequestNodeValueByXPath(getBook(), "/Envelope/Body/replaceAllForTravelPlanSegment/request/roomReservationRequest/roomDetails/ticketDetails/guestReference/guest/addressDetails/postalCode", getHouseHold().primaryGuest().primaryAddress().getZipCode());
        trySetRequestNodeValueByXPath(getBook(), "/Envelope/Body/replaceAllForTravelPlanSegment/request/roomReservationRequest/roomDetails/ticketDetails/guestReference/guest/addressDetails/state", getHouseHold().primaryGuest().primaryAddress().getState());
        trySetRequestNodeValueByXPath(getBook(), "/Envelope/Body/replaceAllForTravelPlanSegment/request/roomReservationRequest/roomDetails/ticketDetails/guestReference/guest/phoneDetails/number", getHouseHold().primaryGuest().primaryPhone().getNumber());
        trySetRequestNodeValueByXPath(getBook(), "/Envelope/Body/replaceAllForTravelPlanSegment/request/roomReservationRequest/roomDetails/ticketDetails/guestReference/guest/emailDetails/address", getHouseHold().primaryGuest().primaryEmail().getEmail());

        trySetRequestNodeValueByXPath(getBook(), "/Envelope/Body/replaceAllForTravelPlanSegment/request/roomReservationRequest/travelPlanGuest/firstName", getHouseHold().primaryGuest().getFirstName());
        trySetRequestNodeValueByXPath(getBook(), "/Envelope/Body/replaceAllForTravelPlanSegment/request/roomReservationRequest/travelPlanGuest/lastName", getHouseHold().primaryGuest().getLastName());
        trySetRequestNodeValueByXPath(getBook(), "/Envelope/Body/replaceAllForTravelPlanSegment/request/roomReservationRequest/travelPlanGuest/addressDetails/addressLine1", getHouseHold().primaryGuest().primaryAddress().getAddress1());
        trySetRequestNodeValueByXPath(getBook(), "/Envelope/Body/replaceAllForTravelPlanSegment/request/roomReservationRequest/travelPlanGuest/addressDetails/city", getHouseHold().primaryGuest().primaryAddress().getCity());
        trySetRequestNodeValueByXPath(getBook(), "/Envelope/Body/replaceAllForTravelPlanSegment/request/roomReservationRequest/travelPlanGuest/addressDetails/country", getHouseHold().primaryGuest().getAllAddresses().get(0).getCountry());
        trySetRequestNodeValueByXPath(getBook(), "/Envelope/Body/replaceAllForTravelPlanSegment/request/roomReservationRequest/travelPlanGuest/addressDetails/postalCode", getHouseHold().primaryGuest().primaryAddress().getZipCode());
        trySetRequestNodeValueByXPath(getBook(), "/Envelope/Body/replaceAllForTravelPlanSegment/request/roomReservationRequest/travelPlanGuest/addressDetails/state", getHouseHold().primaryGuest().primaryAddress().getState());
        trySetRequestNodeValueByXPath(getBook(), "/Envelope/Body/replaceAllForTravelPlanSegment/request/roomReservationRequest/travelPlanGuest/phoneDetails/number", getHouseHold().primaryGuest().primaryPhone().getNumber());
        trySetRequestNodeValueByXPath(getBook(), "/Envelope/Body/replaceAllForTravelPlanSegment/request/roomReservationRequest/travelPlanGuest/emailDetails/address", getHouseHold().primaryGuest().primaryEmail().getEmail());

        TestReporter.assertTrue(getBook().getResponseStatusCode().equals("200"), "Verify that no error occurred booking a prereq reservation: " + getBook().getFaultString());
        setTpId(getBook().getTravelPlanId());
        retrieveReservation();
    }

    private void trySetRequestNodeValueByXPath(BaseSoapService bss, String xpath, String value) {
        if (bss.getNumberOfRequestNodesByXPath(xpath) > 0) {
            bss.setRequestNodeValueByXPath(xpath, value.isEmpty() ? BaseSoapCommands.REMOVE_NODE.toString() : value);
        } else if (!value.isEmpty()) {
            resolveMissingPath(bss, xpath);
            bss.setRequestNodeValueByXPath(xpath, value);
        }
    }

    private void resolveMissingPath(BaseSoapService bss, String xpath) {
        int lastindex = xpath.lastIndexOf('/');
        String parentxpath = xpath.substring(0, lastindex);
        String node = xpath.substring(lastindex + 1, xpath.length()).split("\\[")[0];
        if (bss.getNumberOfRequestNodesByXPath(parentxpath) == 0) {
            resolveMissingPath(bss, parentxpath);
        }
        bss.setRequestNodeValueByXPath(parentxpath, BaseSoapCommands.ADD_NODE.commandAppend(node));
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
        TestReporter.logAPI(!cancel.getResponseStatusCode().equals("200"), "The cancel was not successful.", cancel);
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
