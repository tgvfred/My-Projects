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

    // Response

    public String getBookingDate() {
        return getResponseNodeValueByXPath("/Envelope/Body/searchAccommodationsForShareResponse/return/unSharedRoomDetail/bookingDate");
    }
}
