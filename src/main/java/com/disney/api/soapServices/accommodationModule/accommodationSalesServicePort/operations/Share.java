package com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations;

import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.AccommodationSalesServicePort;
import com.disney.api.soapServices.core.BaseSoapCommands;
import com.disney.utils.XMLTools;

public class Share extends AccommodationSalesServicePort {

    int Shared = 1;

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

    public void addSharedComponent() {
        String baseXpath = "/Envelope/Body/share/request";
        setRequestNodeValueByXPath(baseXpath, BaseSoapCommands.ADD_NODE.commandAppend("sharedComponents"));

        baseXpath = "/Envelope/Body/share/request/sharedComponents[" + (Shared + 1) + "]";
        setRequestNodeValueByXPath(baseXpath, BaseSoapCommands.ADD_NODE.commandAppend("travelComponentGroupingId"));

        Shared++;
    }

    public void setTravelComponentGroupingId(String value) {
        setRequestNodeValueByXPath("/Envelope/Body/share/request/sharedComponents/travelComponentGroupingId", value);
    }

    public void setSecondTravelComponentGroupingId(String value) {
        setRequestNodeValueByXPath("/Envelope/Body/share/request/sharedComponents[2]/travelComponentGroupingId", value);
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

    public String getSecondTravelComponentGroupingId() {
        return getResponseNodeValueByXPath("/Envelope/Body/shareResponse/shareChainDetails/shareRoomDetails[2]/sharedRoomDetail/travelComponentGroupingId");
    }

    public String getTravelComponentId() {
        return getResponseNodeValueByXPath("/Envelope/Body/shareResponse/shareChainDetails/shareRoomDetails/sharedRoomDetail/travelComponentId");
    }

    public String getSecondTravelComponentId() {
        return getResponseNodeValueByXPath("/Envelope/Body/shareResponse/shareChainDetails/shareRoomDetails[2]/sharedRoomDetail/travelComponentId");
    }

    public String getTravelPlanSegmentId() {
        return getResponseNodeValueByXPath("/Envelope/Body/shareResponse/shareChainDetails/shareRoomDetails/travelPlanSegmentId");
    }

    public String getSecondTravelPlanSegmentId() {
        return getResponseNodeValueByXPath("/Envelope/Body/shareResponse/shareChainDetails/shareRoomDetails[2]/travelPlanSegmentId");
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