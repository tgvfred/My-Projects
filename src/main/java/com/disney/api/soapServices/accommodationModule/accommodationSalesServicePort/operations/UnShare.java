package com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations;

import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.AccommodationSalesServicePort;
import com.disney.utils.XMLTools;

public class UnShare extends AccommodationSalesServicePort {

    public UnShare(String environment, String scenario) {
        super(environment);
        // Generate a request from a project xml file
        setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("unShare")));
        // setRequestNodeValueByXPath(getTestScenario("/services/accommodationSalesServicePort/UnShare/UnShareInputs.xls", scenario));
        setRequestNodeValueByXPath(getTestScenario(getService(), getOperation(), scenario));
        removeComments();
        removeWhiteSpace();
        generateServiceContext();
    }

    public UnShare(String environment) {
        super(environment);
        // Generate a request from a project xml file
        setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("unShare")));

        generateServiceContext();
        removeComments();
        removeWhiteSpace();
    }

    public void setTravelComponentGroupingId(String value) {
        setRequestNodeValueByXPath("/Envelope/Body/unShare/request/unSharedComponents/travelComponentGroupingId", value);
    }

    public void setLocationId(String value) {
        setRequestNodeValueByXPath("/Envelope/Body/unShare/request/locationId", value);
    }

    public String getTravelPlanSegmentId() {
        return getResponseNodeValueByXPath("/Envelope/Body/unShareResponse/shareChainDetails/shareRoomDetails/travelPlanSegmentId");
    }

    public String getTravelComponentId() {
        return getResponseNodeValueByXPath("/Envelope/Body/unShareResponse/shareChainDetails/shareRoomDetails/sharedRoomDetail/travelComponentId");
    }

    public String getTravelComponentGroupingId() {
        return getResponseNodeValueByXPath("/Envelope/Body/unShareResponse/shareChainDetails/shareRoomDetails/sharedRoomDetail/travelComponentGroupingId");
    }

    public String getTravelComponentId_unSharedRoomDetail() {
        return getResponseNodeValueByXPath("/Envelope/Body/unShareResponse/shareChainDetails/shareRoomDetails/unSharedRoomDetail/travelComponentId");
    }

    public String getTravelComponentGroupingId_unSharedRoomDetail() {
        return getResponseNodeValueByXPath("/Envelope/Body/unShareResponse/shareChainDetails/shareRoomDetails/unSharedRoomDetail/travelComponentGroupingId");
    }

    public String getBookingDate() {
        return getResponseNodeValueByXPath("/Envelope/Body/unShareResponse/shareChainDetails/shareRoomDetails/sharedRoomDetail/bookingDate");
    }

    public String getTravelStatus() {
        return getResponseNodeValueByXPath("/Envelope/Body/unShareResponse/shareChainDetails/shareRoomDetails/sharedRoomDetail/travelStatus");
    }

    public String getLocationId() {
        return getResponseNodeValueByXPath("/Envelope/Body/unShareResponse/shareChainDetails/shareRoomDetails/sharedRoomDetail/locationId");
    }

    public String getBookingDate_unSharedRoomDetail() {
        return getResponseNodeValueByXPath("/Envelope/Body/unShareResponse/shareChainDetails/shareRoomDetails/unSharedRoomDetail/bookingDate");
    }

    public String getTravelStatus_unSharedRoomDetail() {
        return getResponseNodeValueByXPath("/Envelope/Body/unShareResponse/shareChainDetails/shareRoomDetails/unSharedRoomDetail/travelStatus");
    }

    public String getLocationId_unSharedRoomDetail() {
        return getResponseNodeValueByXPath("/Envelope/Body/unShareResponse/shareChainDetails/shareRoomDetails/unSharedRoomDetail/locationId");
    }
}