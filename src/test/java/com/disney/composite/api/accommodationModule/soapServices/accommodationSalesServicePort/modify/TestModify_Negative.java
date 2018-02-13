package com.disney.composite.api.accommodationModule.soapServices.accommodationSalesServicePort.modify;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.Modify;
import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.ReplaceAllForTravelPlanSegment;
import com.disney.api.soapServices.accommodationModule.applicationError.AccommodationErrorCode;
import com.disney.api.soapServices.accommodationModule.helpers.AccommodationBaseTest;
import com.disney.api.soapServices.applicationError.ApplicationErrorCode;
import com.disney.api.soapServices.core.BaseSoapCommands;
import com.disney.utils.Randomness;
import com.disney.utils.TestReporter;

public class TestModify_Negative extends AccommodationBaseTest {
    private String tpId = null;
    private String tpsId = null;
    private String tcgId = null;
    private String tcId = null;
    private static ReplaceAllForTravelPlanSegment book;

    @Override
    @BeforeMethod(alwaysRun = true)
    @Parameters("environment")
    public void setup(String environment) {
        AccommodationBaseTest.environment = environment;
    }

    @BeforeClass(alwaysRun = true)
    @Parameters("environment")
    public void beforeClass(String environment) {
        setEnvironment(environment);
        setDaysOut(30);
        setNights(1);
        setArrivalDate(getDaysOut());
        setDepartureDate(getNights());
        setValues(getEnvironment());
        setIsWdtcBooking(true);
        setMywPackageCode(true);
        bookReservation();
        tpId = getBook().getTravelPlanId();
        tpsId = getBook().getTravelPlanSegmentId();
        tcgId = getBook().getTravelComponentGroupingId();
        tcId = getBook().getTravelComponentId();
        setSendRequest(false);
        bookReservation();
        getBook().setTravelPlanId(tpId);
        getBook().setTravelPlanSegementId(tpsId);
        getBook().setTravelComponentGroupingId(tcgId);
        getBook().setTravelComponentId(tcId);
        setTpId(tpId);
        setTpsId(tpsId);
        setTcgId(tcgId);
        setTcId(tcId);
        book = getBook();
    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesService", "modify", "negative" })
    public void testModify_NullRequest() {
        String errorMessage = "Accommodations not found : Invalid TravelPlanSegment";
        Modify modify = new Modify(book);
        modify.setRequestNodeValueByXPath("/Envelope/Body/modify/request", BaseSoapCommands.REMOVE_NODE.toString());
        modify.sendRequest();
        validateError(modify, AccommodationErrorCode.ACCOMMODATIONS_NOT_FOUND, errorMessage);
    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesService", "modify", "negative" })
    public void testModify_NullRoomDetail() {
        String errorMessage = "Accommodations not found : Invalid TravelPlanSegment";
        Modify modify = new Modify(book);
        modify.setRequestNodeValueByXPath("/Envelope/Body/modify/request/roomDetail", BaseSoapCommands.REMOVE_NODE.toString());
        modify.sendRequest();
        validateError(modify, AccommodationErrorCode.ACCOMMODATIONS_NOT_FOUND, errorMessage);
    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesService", "modify", "negative" })
    public void testModify_NullTravelComponentGroupingID() {
        String errorMessage = "Accommodations not found : Invalid TravelPlanSegment";
        Modify modify = new Modify(book);
        modify.setRequestNodeValueByXPath("/Envelope/Body/modify/request/roomDetail/travelComponentGroupingId", BaseSoapCommands.REMOVE_NODE.toString());
        modify.sendRequest();
        validateError(modify, AccommodationErrorCode.ACCOMMODATIONS_NOT_FOUND, errorMessage);
    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesService", "modify", "negative" })
    public void testModify_NullTravelPlanSegmentID() {
        String errorMessage = "Accommodations not found : Invalid TravelPlanSegment";
        Modify modify = new Modify(book);
        modify.setRequestNodeValueByXPath("/Envelope/Body/modify/request/travelPlanSegmentId", BaseSoapCommands.REMOVE_NODE.toString());
        modify.sendRequest();
        validateError(modify, AccommodationErrorCode.ACCOMMODATIONS_NOT_FOUND, errorMessage);
    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesService", "modify", "negative" })
    public void testModify_TravelComponentGroupingIdEquals0() {
        String errorMessage = "Accommodations not found : Invalid TravelPlanSegment";
        Modify modify = new Modify(book);
        modify.setRequestNodeValueByXPath("/Envelope/Body/modify/request/roomDetail/travelComponentGroupingId", "0");
        modify.sendRequest();
        validateError(modify, AccommodationErrorCode.ACCOMMODATIONS_NOT_FOUND, errorMessage);
    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesService", "modify", "negative" })
    public void testModify_TravelPlanSegmentIdEquals0() {
        String errorMessage = "Accommodations not found : Invalid TravelPlanSegment";
        Modify modify = new Modify(book);
        modify.setRequestNodeValueByXPath("/Envelope/Body/modify/request/travelPlanSegmentId", "0");
        modify.sendRequest();
        validateError(modify, AccommodationErrorCode.ACCOMMODATIONS_NOT_FOUND, errorMessage);
    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesService", "modify", "negative" })
    public void testModify_NullGuestDetail() {
        String errorMessage = "Travel Plan Guest Details not provided";
        Modify modify = new Modify(book);
        modify.setRequestNodeValueByXPath("/Envelope/Body/modify/request/travelPlanGuest", BaseSoapCommands.REMOVE_NODE.toString());
        modify.sendRequest();
        validateError(modify, AccommodationErrorCode.TRAVEL_PLAN_GUEST_REQUIRED, errorMessage);
    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesService", "modify", "negative" })
    public void testModify_NullGuestDetailFirstName() {
        String errorMessage = "Travel Plan Guest Details not provided";
        Modify modify = new Modify(book);
        modify.setRequestNodeValueByXPath("/Envelope/Body/modify/request/travelPlanGuest/firstName", BaseSoapCommands.REMOVE_NODE.toString());
        modify.sendRequest();
        validateError(modify, AccommodationErrorCode.TRAVEL_PLAN_GUEST_REQUIRED, errorMessage);
    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesService", "modify", "negative" })
    public void testModify_NullGuestDetailLastName() {
        String errorMessage = "Travel Plan Guest Details not provided";
        Modify modify = new Modify(book);
        modify.setRequestNodeValueByXPath("/Envelope/Body/modify/request/travelPlanGuest/lastName", BaseSoapCommands.REMOVE_NODE.toString());
        modify.sendRequest();
        validateError(modify, AccommodationErrorCode.TRAVEL_PLAN_GUEST_REQUIRED, errorMessage);
    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesService", "modify", "negative" })
    public void testModify_NullRoomDetailResortCode() {
        String errorMessage = "ResortCode can not be Null OR Blank";
        Modify modify = new Modify(book);
        modify.setRequestNodeValueByXPath("/Envelope/Body/modify/request/roomDetail/resortCode", BaseSoapCommands.REMOVE_NODE.toString());
        modify.sendRequest();
        validateError(modify, AccommodationErrorCode.INVALID_RESORT_CODE, errorMessage);
    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesService", "modify", "negative" })
    public void testModify_NullRoomDetailRoomType() {
        String errorMessage = "RoomType Code can not be Null OR Blank";
        Modify modify = new Modify(book);
        modify.setRequestNodeValueByXPath("/Envelope/Body/modify/request/roomDetail/roomTypeCode", BaseSoapCommands.REMOVE_NODE.toString());
        modify.sendRequest();
        validateError(modify, AccommodationErrorCode.INVALID_ROOM_TYPE, errorMessage);
    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesService", "modify", "negative" })
    public void testModify_NullRoomDetailPackageCode() {
        String errorMessage = "PackageCode can not be null or blank";
        Modify modify = new Modify(book);
        modify.setRequestNodeValueByXPath("/Envelope/Body/modify/request/roomDetail/packageCode", BaseSoapCommands.REMOVE_NODE.toString());
        modify.sendRequest();
        validateError(modify, AccommodationErrorCode.INVALID_PACKAGE_CODE, errorMessage);
    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesService", "modify", "negative" })
    public void testModify_NullRoomDetailGuestReferenceDetails() {
        String errorMessage = "There should be at least one Guest Detail";
        Modify modify = new Modify(book);
        modify.setRequestNodeValueByXPath("/Envelope/Body/modify/request/roomDetail/roomReservationDetail/guestReferenceDetails", BaseSoapCommands.REMOVE_NODE.toString());
        modify.sendRequest();
        validateError(modify, AccommodationErrorCode.APPLICATION_EXCEPTION, errorMessage);
    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesService", "modify", "negative" })
    public void testModify_NullRoomDetailGuestReferenceDetailsGuest() {
        String errorMessage = "There should be at least one Guest Detail";
        Modify modify = new Modify(book);
        modify.setRequestNodeValueByXPath("/Envelope/Body/modify/request/roomDetail/roomReservationDetail/guestReferenceDetails/guest", BaseSoapCommands.REMOVE_NODE.toString());
        modify.sendRequest();
        validateError(modify, AccommodationErrorCode.APPLICATION_EXCEPTION, errorMessage);
    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesService", "modify", "negative" })
    public void testModify_NullRoomDetailResortPeriod() {
        String errorMessage = "ResortPeriod or resort period start date or end date can not be null";
        Modify modify = new Modify(book);
        modify.setRequestNodeValueByXPath("/Envelope/Body/modify/request/roomDetail/resortPeriod", BaseSoapCommands.REMOVE_NODE.toString());
        modify.sendRequest();
        validateError(modify, AccommodationErrorCode.INVALID_RESORT_PERIOD, errorMessage);
    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesService", "modify", "negative" })
    public void testModify_NullRoomDetailResortPeriodStartDate() {
        String errorMessage = "ResortPeriod or resort period start date or end date can not be null";
        Modify modify = new Modify(book);
        modify.setRequestNodeValueByXPath("/Envelope/Body/modify/request/roomDetail/resortPeriod/startDate", BaseSoapCommands.REMOVE_NODE.toString());
        modify.sendRequest();
        validateError(modify, AccommodationErrorCode.INVALID_RESORT_PERIOD, errorMessage);
    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesService", "modify", "negative" })
    public void testModify_NullRoomDetailResortPeriodEndDate() {
        String errorMessage = "ResortPeriod or resort period start date or end date can not be null";
        Modify modify = new Modify(book);
        modify.setRequestNodeValueByXPath("/Envelope/Body/modify/request/roomDetail/resortPeriod/endDate", BaseSoapCommands.REMOVE_NODE.toString());
        modify.sendRequest();
        validateError(modify, AccommodationErrorCode.INVALID_RESORT_PERIOD, errorMessage);
    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesService", "modify", "negative" })
    public void testModify_RoomDetailResortPeriodEndDateBeforeStartDate() {
        String errorMessage = "ResortPeriod start date should be less than end date";
        Modify modify = new Modify(book);
        modify.setRequestNodeValueByXPath("/Envelope/Body/modify/request/roomDetail/resortPeriod/startDate", Randomness.generateCurrentXMLDate(1));
        modify.setRequestNodeValueByXPath("/Envelope/Body/modify/request/roomDetail/resortPeriod/endDate", Randomness.generateCurrentXMLDate(0));
        modify.sendRequest();
        validateError(modify, AccommodationErrorCode.INVALID_RESORT_PERIOD, errorMessage);
    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesService", "modify", "negative" })
    public void testModify_RoomDetailResortPeriodGreaterThan30Days() {
        String errorMessage = "Reservation duration can not be more than 30 days";
        Modify modify = new Modify(book);
        modify.setRequestNodeValueByXPath("/Envelope/Body/modify/request/roomDetail/resortPeriod/startDate", Randomness.generateCurrentXMLDate(1));
        modify.setRequestNodeValueByXPath("/Envelope/Body/modify/request/roomDetail/resortPeriod/endDate", Randomness.generateCurrentXMLDate(45));
        modify.sendRequest();
        validateError(modify, AccommodationErrorCode.INVALID_RESORT_PERIOD, errorMessage);
    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesService", "modify", "negative" })
    public void testModify_NullExternalReferenceDetailExternalReferenceCodeAndSource() {
        String errorMessage = "EXTERNAL_REFERENCE_SOURCE_OR_EXTERNAL_REFERENCE_CODE_REQUIRED";
        Modify modify = new Modify(book);
        modify.setRequestNodeValueByXPath("/Envelope/Body/modify/request/externalReference/externalReferenceCode", BaseSoapCommands.REMOVE_NODE.toString());
        modify.sendRequest();
        validateError(modify, AccommodationErrorCode.EXTERNAL_REFERENCE_SOURCE_OR_EXTERNAL_REFERENCE_CODE_REQUIRED, errorMessage);
    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesService", "modify", "negative" })
    public void testModify_NullRoomDetailExternalReferenceDetailExternalReferenceSourceAndCode() {
        String errorMessage = "EXTERNAL_REFERENCE_SOURCE_OR_EXTERNAL_REFERENCE_CODE_REQUIRED";
        Modify modify = new Modify(book);
        modify.setRequestNodeValueByXPath("/Envelope/Body/modify/request/roomDetail/externalReferences/externalReferenceCode", BaseSoapCommands.REMOVE_NODE.toString());
        modify.sendRequest();
        validateError(modify, AccommodationErrorCode.EXTERNAL_REFERENCE_SOURCE_OR_EXTERNAL_REFERENCE_CODE_REQUIRED, errorMessage);
    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesService", "modify", "negative" })
    public void testModify_NullGatheringDetailGatheringInfo() {
        String errorMessage = "Not a valid Gathering Detail Information";
        Modify modify = new Modify(book);
        modify.setRequestNodeValueByXPath("/Envelope/Body/modify/request", BaseSoapCommands.ADD_NODE.commandAppend("gatheringDetail"));
        modify.sendRequest();
        validateError(modify, AccommodationErrorCode.NOT_VALID_GATHERING_DETAIL, errorMessage);
    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesService", "modify", "negative" })
    public void testModify_InvalidCommunicationChannel() {
        String errorMessage = "Communication Channel is required";
        Modify modify = new Modify(book);
        modify.setRequestNodeValueByXPath("/Envelope/Body/modify/request/communicationChannel", "Blah");
        modify.sendRequest();
        validateError(modify, AccommodationErrorCode.INVALID_REQUEST, errorMessage);
    }

    private void validateError(Modify modify, ApplicationErrorCode error, String errorMessage) {
        TestReporter.logAPI(!modify.getFaultString().trim().toLowerCase().contains(errorMessage.trim().toLowerCase()), "Validate expected error message [ " + errorMessage + " ] is returned in response [ " + modify.getFaultString() + " ]", modify);
        validateApplicationError(modify, error);
    }
}
