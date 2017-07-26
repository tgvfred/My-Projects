package com.disney.composite.api.accommodationModule.soapServices.accommodationSalesComponentServicePort.cancel;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationModule.accommodationSalesComponentServicePort.operations.Cancel;
import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.Add;
import com.disney.api.soapServices.accommodationModule.helpers.AccommodationBaseTest;
import com.disney.api.soapServices.accommodationModule.helpers.AddAccommodationHelper;
import com.disney.api.soapServices.accommodationModule.helpers.CheckInHelper;
import com.disney.api.soapServices.core.BaseSoapCommands;
import com.disney.api.soapServices.core.BaseSoapService;
import com.disney.api.soapServices.pricingModule.packagingService.operations.FindMiscPackages;
import com.disney.api.soapServices.pricingModule.packagingService.operations.FindTicketPriceGridByPackage;
import com.disney.api.soapServices.pricingModule.packagingService.operations.GetTicketProducts;
import com.disney.api.soapServices.tpsoModule.travelPlanSalesOrderServiceV1.operations.AddBundle;
import com.disney.utils.Environment;
import com.disney.utils.PackageCodes;
import com.disney.utils.Randomness;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.database.Database;
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
        validateSpecialEnvironment(cancel);
    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesComponentServicePort", "cancel" })
    public void testCancel_CheckingIn_tcgOnly() {
        checkingIn();

        TestReporter.logScenario("Test - Cancel - Checking In TCG Only");

        Cancel cancel = new Cancel(environment);
        cancel.setTravelComponentGroupingId(getBook().getTravelComponentGroupingId());
        sendRequestAndValidateSoapResponse(cancel);
        validateSpecialEnvironment(cancel);
    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesComponentServicePort", "cancel" })
    public void testCancel_addAccommodation_cancelOne_tcgOnly() {
        addAccommodation();

        TestReporter.logScenario("Test - Cancel - Add Accommodation Cancel One TCG Only");

        Cancel cancel = new Cancel(environment);
        cancel.setTravelComponentGroupingId(getBook().getTravelComponentGroupingId());
        sendRequestAndValidateSoapResponse(cancel);
        validateSpecialEnvironment(cancel);
    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesComponentServicePort", "cancel" })
    public void testCancel_addAccommodation_checkInOne_cancelOne_tcgOnly() {
        checkIn();
        AddAccommodationHelper helper = addAccommodation();

        TestReporter.logScenario("Test - Cancel - Add Accommodation Check In One Cancel One TCG Only");

        Cancel cancel = new Cancel(environment);
        cancel.setTravelComponentGroupingId(helper.getTcgId());
        sendRequestAndValidateSoapResponse(cancel);
        validateSpecialEnvironment(cancel);
    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesComponentServicePort", "cancel" })
    public void testCancel_addBundle_cancelAccommodation_tcgOnly() {
        addBundle();

        TestReporter.logScenario("Test - Cancel - Add Bundle Cancel Accommodation TCG Only");

        Cancel cancel = buildRequestForDefaultBook();
        sendRequestAndValidateSoapResponse(cancel);
        validateSpecialEnvironment(cancel);
    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesComponentServicePort", "cancel" })
    public void testCancel_Guaranteed() {
        groupBooking();

        TestReporter.logScenario("Test - Cancel - Guaranteed");

        Cancel cancel = buildRequestForDefaultBook();
        sendRequestAndValidateSoapResponse(cancel);
        validateSpecialEnvironment(cancel);
    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesComponentServicePort", "cancel" })
    public void testCancel_groupBookingWithTickets() {
        groupBooking();
        addTickets();

        TestReporter.logScenario("Test - Cancel - Group Booking With Tickets");

        Cancel cancel = buildRequestForDefaultBook();
        sendRequestAndValidateSoapResponse(cancel);
        validateSpecialEnvironment(cancel);
    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesComponentServicePort", "cancel" })
    public void testCancel_cancellationFeeWaived() {
        TestReporter.logScenario("Test - Cancel - Cancellation Fee Waived");

        Cancel cancel = buildRequestForDefaultBook();
        cancel.setWaived("true");
        sendRequestAndValidateSoapResponse(cancel);
        validateSpecialEnvironment(cancel);
    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesComponentServicePort", "cancel" })
    public void testCancel_cancellationFeeNotWaived() {
        TestReporter.logScenario("Test - Cancel - Cancellation Fee Not Waived");

        Cancel cancel = buildRequestForDefaultBook();
        cancel.setWaived("false");
        sendRequestAndValidateSoapResponse(cancel);
        validateSpecialEnvironment(cancel);
    }

    /*
     * Utility Functions
     */
    private AddAccommodationHelper addAccommodation() {
        AddAccommodationHelper helper = new AddAccommodationHelper(environment, getBook());
        Add addAccomm = helper.addAccommodation(getResortCode(), getRoomTypeCode(), getPackageCode(), getDaysOut(), getNights(), getLocationId());
        addAccomm.sendRequest();
        TestReporter.assertEquals(addAccomm.getResponseStatusCode(), "200", "The accommodation addition precondition succeeded.");
        return helper;
    }

    private void addBundle() {
        AddBundle add = new AddBundle(environment, "Main");
        add.setGuestsGuestNameFirstName(getHouseHold().primaryGuest().getFirstName());
        add.setGuestsGuestNameLastName(getHouseHold().primaryGuest().getLastName());
        add.setGuestsGuestReferenceId(getGuestId());
        add.setGuestsId(getGuestId());
        add.setPackageBundleRequestsBookDate(Randomness.generateCurrentXMLDate());
        add.setPackageBundleRequestsContactName(getHouseHold().primaryGuest().getFirstName() + " " + getHouseHold().primaryGuest().getLastName());
        add.setPackageBundleRequestsEndDate(Randomness.generateCurrentXMLDate(getDaysOut() + 2) + "T00:00:00");
        add.setPackageBundleRequestsSalesOrderItemGuestsGUestReferenceId(getGuestId());
        add.setPackageBundleRequestsStartDate(Randomness.generateCurrentXMLDate(getDaysOut() + 1) + "T00:00:00");
        add.setTravelPlanId(getBook().getTravelPlanId());
        add.retrieveSalesOrderId(getBook().getTravelPlanId());
        add.setSalesOrderId(add.getBundleSalesOrderIds()[0]);

        FindMiscPackages find = new FindMiscPackages(environment, "MinimalInfo");
        find.setArrivalDate(Randomness.generateCurrentXMLDate(getDaysOut()));
        find.setBookDate(Randomness.generateCurrentXMLDate());
        find.sendRequest();
        TestReporter.assertTrue(find.getResponseStatusCode().equals("200"), "Verify that no error occurred adding a bundle to TP ID [" + getTpId() + "]: " + add.getFaultString());
        add.setPackageBundleRequestsCode(find.getPackageCode());
        add.sendRequest();
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
        // getBook().setResortAreaStartDate(getArrivalDate());
        // getBook().setResortAreaEndDate(getDepartureDate());

        FindTicketPriceGridByPackage find = new FindTicketPriceGridByPackage(environment);
        find.setPackageCode(packageCode);
        find.sendRequest();
        TestReporter.assertTrue(find.getResponseStatusCode().equals("200"), "Verify that no error occurred finding tickets for package code [" + packageCode + "].");
        // getBook().setRoomDetailsTicketGroup(BaseSoapCommands.ADD_NODE.toString());
        // getBook().setRoomDetailsTicketGroup(find.getTicketGroupName());
        trySetRequestNodeValueByXPath(getBook(), "//replaceAllForTravelPlanSegment/request/roomDetails/ticketGroup", find.getTicketGroupName());
        // ticketGroupName = find.getTicketGroupName();

        GetTicketProducts get = new GetTicketProducts(environment, "Main");
        get.setTicketGroupName(find.getTicketGroupName());
        get.sendRequest();
        TestReporter.assertTrue(get.getResponseStatusCode().equals("200"), "Verify that no error occurred finding ticket products for ticket group name [" + find.getTicketGroupName() + "].");
        String admissionProductId = get.getAdmissionProductIdByTicketDescription("2 Day Base Ticket");
        // getBook().setTicketDetailsBaseAdmissionProductId(admissionProductId);
        // getBook().setTicketDetailsCode(admissionProductId);

        createHouseHold();

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

        if (getBook().getResponse().contains("Error Invoking Pricing Service")) {
            // System.out.println();
        }
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
        // Add second validation here
    }

    private void validateSpecialEnvironment(Cancel cancel) {
        if (Environment.isSpecialEnvironment(environment) || isComo.equals("true")) {
            Cancel cancelBaseLine = (Cancel) cancel.clone();
            cancelBaseLine.setEnvironment(Environment.getBaseEnvironmentName(environment));
            cancelBaseLine.sendRequest();
            TestReporter.assertTrue(cancel.validateResponseNodeQuantity(cancelBaseLine), "Response Node Validation Result");
        }
    }
}
