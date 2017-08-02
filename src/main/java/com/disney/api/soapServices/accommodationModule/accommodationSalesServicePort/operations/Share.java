package com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations;

import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.AccommodationSalesServicePort;
import com.disney.utils.XMLTools;

public class Share extends AccommodationSalesServicePort {
    public Share(String environment, String scenario) {
        super(environment);
        // Generate a request from a project xml file
        setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("share")));
        setRequestNodeValueByXPath(getTestScenario(getService(), getOperation(), scenario));
        generateServiceContext();
        removeComments();
        removeWhiteSpace();
    }

    public Share(String environment) {
        super(environment);
        // Generate a request from a project xml file
        setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("share")));

        generateServiceContext();
        removeComments();
        removeWhiteSpace();
    }

    public void setTravelComponentGroupingId(String value) {
        setRequestNodeValueByXPath("/Envelope/Body/share/request/sharedComponents/travelComponentGroupingId", value);
    }

    public void setSecondTravelComponentGroupingId(String value) {
        setRequestNodeValueByXPath("/Envelope/Body/share/request/sharedComponents/travelComponentGroupingId[2]", value);
    }

    public void setRoomNumber(String value) {
        setRequestNodeValueByXPath("/Envelope/Body/share/request/roomNumber", value);
    }

    public void setLocationId(String value) {
        setRequestNodeValueByXPath("/Envelope/Body/share/request/locationId", value);
    }

    public String getTravelComponentGroupingId() {
        return getResponseNodeValueByXPath("/Envelope/Body/shareResponse/shareChainDetails/shareRoomDetails/sharedRoomDetail/travelComponentGroupingId");
    }

    public String getTravelComponentId() {
        return getResponseNodeValueByXPath("/Envelope/Body/shareResponse/shareChainDetails/shareRoomDetails/sharedRoomDetail/travelComponentId");
    }

    public String getTravelPlanSegmentId() {
        return getResponseNodeValueByXPath("/Envelope/Body/shareResponse/shareChainDetails/shareRoomDetails/travelPlanSegmentId");
    }

    public String getBookingDate() {
        return getResponseNodeValueByXPath("/Envelope/Body/shareResponse/shareChainDetails/shareRoomDetails/sharedRoomDetail/bookingDate");
    }

    public String getTravelStatus() {
        return getResponseNodeValueByXPath("/Envelope/Body/shareResponse/shareChainDetails/shareRoomDetails/sharedRoomDetail/travelStatus");
    }

    public String getLocationId() {
        return getResponseNodeValueByXPath("/Envelope/Body/shareResponse/shareChainDetails/shareRoomDetails/sharedRoomDetail/locationId");
    }

}