package com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations;

import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.AccommodationSalesServicePort;
import com.disney.utils.XMLTools;

public class RetrieveShareChain extends AccommodationSalesServicePort {
    public RetrieveShareChain(String environment, String scenario) {
        super(environment);

        // Generate a request from a project xml file
        setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("retrieveShareChain")));
        generateServiceContext();
        // setRequestNodeValueByXPath(getTestScenario(getService(), getOperation(), scenario));
        removeComments();
        removeWhiteSpace();
    }

    public void setTravelComponentGroupingId(String tcg_id) {
        setRequestNodeValueByXPath("/Envelope/Body/retrieveShareChain/request/travelComponentGroupingId", tcg_id);
    }

    public String gettravelComponentId() {
        return getResponseNodeValueByXPath("/Envelope/Body/retrieveShareChainResponse/shareRoomDetails/sharedRoomDetail/travelComponentId");
    }

    public String gettravelPlanSegmentId() {
        return getResponseNodeValueByXPath("/Envelope/Body/retrieveShareChainResponse/shareRoomDetails/travelPlanSegmentId");
    }

    // Getters
    public String getBookingDate() {
        return getResponseNodeValueByXPath("/Envelope/Body/retrieveShareChainResponse/shareRoomDetails/sharedRoomDetail/bookingDate");
    }

    public String getModificationDate() {
        return getResponseNodeValueByXPath("/Envelope/Body/retrieveShareChainResponse/shareRoomDetails/sharedRoomDetail/modificationDate");
    }

    public String getPackageCode() {
        return getResponseNodeValueByXPath("/Envelope/Body/retrieveShareChainResponse/shareRoomDetails/sharedRoomDetail/packageCode");
    }

    public String roomTypeCode() {
        return getResponseNodeValueByXPath("/Envelope/Body/retrieveShareChainResponse/shareRoomDetails/sharedRoomDetail/roomTypeCode");
    }

    public String getTravelComponentGroupingId() {
        return getResponseNodeValueByXPath("/Envelope/Body/retrieveShareChainResponse/shareRoomDetails/sharedRoomDetail/travelComponentGroupingId");
    }

    public String getTravelComponentId() {
        return getResponseNodeValueByXPath("/Envelope/Body/retrieveShareChainResponse/shareRoomDetails/sharedRoomDetail/travelComponentId");
    }

    public String getTravelStatus() {
        return getResponseNodeValueByXPath("/Envelope/Body/retrieveShareChainResponse/shareRoomDetails/sharedRoomDetail/travelStatus");
    }

    public String getLocationId() {
        return getResponseNodeValueByXPath("/Envelope/Body/retrieveShareChainResponse/shareRoomDetails/sharedRoomDetail/locationId");
    }

    public String getTravelPlanSegmentId() {
        return getResponseNodeValueByXPath("/Envelope/Body/retrieveShareChainResponse/shareRoomDetails/sharedRoomDetail/travelPlanSegmentId");
    }

    public String getResortPeriodEndDate() {
        return getResponseNodeValueByXPath("/Envelope/Body/retrieveShareChainResponse/shareRoomDetails/sharedRoomDetail/resortPeriod/endDate");
    }

    public String getResortPeriodStartDate() {
        return getResponseNodeValueByXPath("/Envelope/Body/retrieveShareChainResponse/shareRoomDetails/sharedRoomDetail/resortPeriod/startDate");
    }

    public String getRoomGuestFirstName() {
        return getResponseNodeValueByXPath("/Envelope/Body/retrieveShareChainResponse/shareRoomDetails/sharedRoomDetail/roomReservationDetail/guestReferenceDetails/guest/firstName");
    }

    public String getRoomGuestLastName() {
        return getResponseNodeValueByXPath("/Envelope/Body/retrieveShareChainResponse/shareRoomDetails/sharedRoomDetail/roomReservationDetail/guestReferenceDetails/guest/firstName");
    }

    public String getRoomGuestMiddleName() {
        return getResponseNodeValueByXPath("/Envelope/Body/retrieveShareChainResponse/shareRoomDetails/sharedRoomDetail/roomReservationDetail/guestReferenceDetails/guest/middleName");
    }

    public String getGuestFirstName() {
        return getResponseNodeValueByXPath("/Envelope/Body/retrieveShareChainResponse/shareRoomDetails/guestDetail/firstName");
    }

    public String getGuestLastName() {
        return getResponseNodeValueByXPath("/Envelope/Body/retrieveShareChainResponse/shareRoomDetails/guestDetail/lastName");
    }

    public String getGuestMiddleName() {
        return getResponseNodeValueByXPath("/Envelope/Body/retrieveShareChainResponse/shareRoomDetails/guestDetail/middleName");
    }

    public String getMultipleGuestFirstName(int shareIndex, int guestIndex) {
        return getResponseNodeValueByXPath("/Envelope/Body/retrieveShareChainResponse/shareRoomDetails[" + shareIndex + "]/sharedRoomDetail/roomReservationDetail//guestReferenceDetails[" + guestIndex + "]/guest/firstName");
    }

    public String getMultipleGuestLastName(int shareIndex, int guestIndex) {
        return getResponseNodeValueByXPath("/Envelope/Body/retrieveShareChainResponse/shareRoomDetails[" + shareIndex + "]/sharedRoomDetail/roomReservationDetail//guestReferenceDetails[" + guestIndex + "]/guest/lastName");
    }

    public String getMultipleGuestMiddleName(int shareIndex, int guestIndex) {
        return getResponseNodeValueByXPath("/Envelope/Body/retrieveShareChainResponse/shareRoomDetails[" + shareIndex + "]/sharedRoomDetail/roomReservationDetail//guestReferenceDetails[" + guestIndex + "]/guest/middleName");
    }

    public String getPhoneNumber() {
        return getResponseNodeValueByXPath("/Envelope/Body/retrieveShareChainResponse/shareRoomDetails/guestDetail/phoneDetails/number");
    }

    public String getAddressLine1() {
        return getResponseNodeValueByXPath("/Envelope/Body/retrieveShareChainResponse/shareRoomDetails/guestDetail/addressDetails/addressLine1");
    }

    public String getCity() {
        return getResponseNodeValueByXPath("/Envelope/Body/retrieveShareChainResponse/shareRoomDetails/guestDetail/addressDetails/city");
    }

    public String getCountry() {
        return getResponseNodeValueByXPath("/Envelope/Body/retrieveShareChainResponse/shareRoomDetails/guestDetail/addressDetails/country");
    }

    public String getState() {
        return getResponseNodeValueByXPath("/Envelope/Body/retrieveShareChainResponse/shareRoomDetails/guestDetail/addressDetails/state");
    }

    public String getRegionName() {
        return getResponseNodeValueByXPath("/Envelope/Body/retrieveShareChainResponse/shareRoomDetails/guestDetail/addressDetails/regionName");
    }

    public String getPostalCode() {
        return getResponseNodeValueByXPath("/Envelope/Body/retrieveShareChainResponse/shareRoomDetails/guestDetail/addressDetails/postalCode");
    }

    public String getEmailAddress() {
        return getResponseNodeValueByXPath("/Envelope/Body/retrieveShareChainResponse/shareRoomDetails/guestDetail/emailDetails/address");
    }

    public String getPrefferedLanguage() {
        return getResponseNodeValueByXPath("/Envelope/Body/retrieveShareChainResponse/shareRoomDetails/guestDetail/preferredLanguage");
    }

    // Guest detail getters

    public String getGuestAge() {
        return getResponseNodeValueByXPath("/Envelope/Body/retrieveShareChainResponse/shareRoomDetails/sharedRoomDetail/roomReservationDetail/guestReferenceDetails/age");
    }

    public String getGuestAgeType() {
        return getResponseNodeValueByXPath("/Envelope/Body/retrieveShareChainResponse/shareRoomDetails/sharedRoomDetail/roomReservationDetail/guestReferenceDetails/ageType");
    }

}