package com.disney.api.soapServices.accommodationModule.accommodationSalesComponentService.operations;

import com.disney.api.soapServices.accommodationModule.accommodationSalesComponentService.AccommodationSalesComponentService;
import com.disney.utils.XMLTools;

public class SearchAccommodationsForShare extends AccommodationSalesComponentService {

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

    public void setTravelStatus(String value) {
        setRequestNodeValueByXPath("/Envelope/Body/searchAccommodationsForShare/request/travelPlanViewTO/travelStatus", value);
    }

    // Response

    public String getBookingDate(String index) {
        return getResponseNodeValueByXPath("/Envelope/Body/searchAccommodationsForShareResponse/return[" + index + "]/unSharedRoomDetail/bookingDate");
    }

    public String getResortCode(String index) {
        return getResponseNodeValueByXPath("/Envelope/Body/searchAccommodationsForShareResponse/return[" + index + "]/unSharedRoomDetail/resortCode");
    }

    public String getRoomTypeCode(String index) {
        return getResponseNodeValueByXPath("/Envelope/Body/searchAccommodationsForShareResponse/return[" + index + "]/unSharedRoomDetail/roomTypeCode");
    }

    public String getTravelComponentGroupingId(String index) {
        return getResponseNodeValueByXPath(" /Envelope/Body/searchAccommodationsForShareResponse/return[" + index + "]/unSharedRoomDetail/travelComponentGroupingId");
    }

    public String getTravelComponentId(String index) {
        return getResponseNodeValueByXPath("/Envelope/Body/searchAccommodationsForShareResponse/return[" + index + "]/unSharedRoomDetail/travelComponentId");
    }

    public String getFirstName(String index) {
        return getResponseNodeValueByXPath("/Envelope/Body/searchAccommodationsForShareResponse/return[" + index + "]/guestDetail/firstName");
    }

    public String getLastName(String index) {
        return getResponseNodeValueByXPath("/Envelope/Body/searchAccommodationsForShareResponse/return[" + index + "]/guestDetail/lastName");
    }

    public String getTravelStatus(String index) {
        return getResponseNodeValueByXPath("/Envelope/Body/searchAccommodationsForShareResponse/return[" + index + "]/unSharedRoomDetail/travelStatus");
    }

    public String getTravelPlanSegmentId(String index) {
        return getResponseNodeValueByXPath("/Envelope/Body/searchAccommodationsForShareResponse/return[" + index + "]/travelPlanSegmentId");
    }

    public String getEndDate(String index) {
        return getResponseNodeValueByXPath("/Envelope/Body/searchAccommodationsForShareResponse/return[" + index + "]/unSharedRoomDetail/resortPeriod/endDate");
    }

    public String getStartDate(String index) {
        return getResponseNodeValueByXPath("/Envelope/Body/searchAccommodationsForShareResponse/return[" + index + "]/unSharedRoomDetail/resortPeriod/startDate");
    }

}
