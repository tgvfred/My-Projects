package com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations;

import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.AccommodationSalesServicePort;
import com.disney.utils.XMLTools;

public class RetrieveShareChain extends AccommodationSalesServicePort {

    public RetrieveShareChain(String environment) {
        super(environment);
        // Generate a request from a project xml file
        setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("retrieveShareChain")));
        generateServiceContext();
        removeComments();
        removeWhiteSpace();
    }

    public void setTravelComponentGroupingId(String tcg_id) {
        setRequestNodeValueByXPath("//travelComponentGroupingId", tcg_id);
    }

    public String getTravelComponentId() {
        return getResponseNodeValueByXPath("/Envelope/Body/retrieveShareChainResponse/shareRoomDetails/sharedRoomDetail/travelComponentId");
    }

    public String getTravelPlanSegmentId() {
        return getResponseNodeValueByXPath("/Envelope/Body/retrieveShareChainResponse/shareRoomDetails/travelPlanSegmentId");
    }
}