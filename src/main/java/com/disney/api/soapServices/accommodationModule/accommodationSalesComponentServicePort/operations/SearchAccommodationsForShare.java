package com.disney.api.soapServices.accommodationModule.accommodationSalesComponentServicePort.operations;

import com.disney.api.soapServices.accommodationModule.accommodationSalesComponentServicePort.AccommodationSalesComponentServicePort;
import com.disney.utils.XMLTools;

public class SearchAccommodationsForShare extends AccommodationSalesComponentServicePort {

    public SearchAccommodationsForShare(String environment, String scenario) {
        super(environment);

        // Generate a request from a project xml file
        setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("searchAccommodationsForShare")));

        setRequestNodeValueByXPath(getTestScenario(getService(), getOperation(), scenario));
        removeComments();
        removeWhiteSpace();
        generateServiceContext();
    }

    // Request
    public void setRequest(String value) {
        setRequestNodeValueByXPath("/Envelope/Body/searchAccommodationsForShare/request", value);
    }

    public void setTravelPlanViewTO(String value) {
        setRequestNodeValueByXPath("/Envelope/Body/searchAccommodationsForShare/request/travelPlanViewTO", value);
    }

    public void setArrivalDate(String value) {
        setRequestNodeValueByXPath("/Envelope/Body/searchAccommodationsForShare/request/travelPlanViewTO/arrivalDate", value);
    }

    public void setDepatureDate(String value) {
        setRequestNodeValueByXPath("/Envelope/Body/searchAccommodationsForShare/request/travelPlanViewTO/depatureDate", value);
    }

    public void setGuestFirstName(String value) {
        setRequestNodeValueByXPath("/Envelope/Body/searchAccommodationsForShare/request/travelPlanViewTO/guestFirstName", value);
    }

    public void setGuestLastName(String value) {
        setRequestNodeValueByXPath("/Envelope/Body/searchAccommodationsForShare/request/travelPlanViewTO/guestLastName", value);
    }

    public void setResortCode(String value) {
        setRequestNodeValueByXPath("/Envelope/Body/searchAccommodationsForShare/request/travelPlanViewTO/resortCode", value);
    }

    public void setAccomFacTOResortCode(String value) {
        setRequestNodeValueByXPath("Envelope/Body/searchAccommodationsForShare/request/travelPlanViewTO/roomType/accommodationFacilityTO/resortCode", value);
    }

    public void setRoomTypeCode(String value) {
        setRequestNodeValueByXPath("/Envelope/Body/searchAccommodationsForShare/request/travelPlanViewTO/roomType/roomTypeCode", value);
    }

    public void setTravelPlanId(String value) {
        setRequestNodeValueByXPath("/Envelope/Body/searchAccommodationsForShare/request/travelPlanViewTO/travelPlanId", value);
    }

    public void setTravelPlanSegmentId(String value) {
        setRequestNodeValueByXPath("/Envelope/Body/searchAccommodationsForShare/request/travelPlanViewTO/travelPlanSegmentId", value);
    }

    public void setExternalReferenceNumber(String value) {
        setRequestNodeValueByXPath("/Envelope/Body/searchAccommodationsForShare/request/travelPlanViewTO/externalReferenceDetail/externalReferenceNumber", value);
    }

    public void setExternalReferenceSource(String value) {
        setRequestNodeValueByXPath("/Envelope/Body/searchAccommodationsForShare/request/travelPlanViewTO/externalReferenceDetail/externalReferenceSource", value);
    }

    public void setPostalCode(String value) {
        setRequestNodeValueByXPath("/Envelope/Body/searchAccommodationsForShare/request/travelPlanViewTO/postalCode", value);
    }

    // Response

    public String getBookingDate() {
        return getResponseNodeValueByXPath("/Envelope/Body/searchAccommodationsForShareResponse/return/unSharedRoomDetail/bookingDate");
    }

    public String getResortCode() {
        return getResponseNodeValueByXPath("/Envelope/Body/searchAccommodationsForShareResponse/return/unSharedRoomDetail/resortCode");
    }

    public String getRoomTypeCode() {
        return getResponseNodeValueByXPath("/Envelope/Body/searchAccommodationsForShareResponse/return/unSharedRoomDetail/roomTypeCode");
    }

    public String getTravelComponentGroupingId() {
        return getResponseNodeValueByXPath(" /Envelope/Body/searchAccommodationsForShareResponse/return/unSharedRoomDetail/travelComponentGroupingId");
    }

    public String getTravelComponentId() {
        return getResponseNodeValueByXPath("/Envelope/Body/searchAccommodationsForShareResponse/return/unSharedRoomDetail/travelComponentId");
    }

    public String getFirstName() {
        return getResponseNodeValueByXPath("/Envelope/Body/searchAccommodationsForShareResponse/return/guestDetail/firstName");
    }

    public String getLastName() {
        return getResponseNodeValueByXPath("/Envelope/Body/searchAccommodationsForShareResponse/return/guestDetail/lastName");
    }

    public String getTravelStatus() {
        return getResponseNodeValueByXPath("/Envelope/Body/searchAccommodationsForShareResponse/return/unSharedRoomDetail/travelStatus");
    }

    public String getTravelPlanSegmentId() {
        return getResponseNodeValueByXPath("/Envelope/Body/searchAccommodationsForShareResponse/return/travelPlanSegmentId");
    }

    public String getEndDate() {
        return getResponseNodeValueByXPath("/Envelope/Body/searchAccommodationsForShareResponse/return/unSharedRoomDetail/resortPeriod/endDate");
    }

    public String getStartDate() {
        return getResponseNodeValueByXPath("/Envelope/Body/searchAccommodationsForShareResponse/return/unSharedRoomDetail/resortPeriod/startDate");
    }

}
