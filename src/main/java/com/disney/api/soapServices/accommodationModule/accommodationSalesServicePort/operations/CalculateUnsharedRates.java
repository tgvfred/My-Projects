package com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations;

import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.AccommodationSalesServicePort;
import com.disney.utils.XMLTools;

public class CalculateUnsharedRates extends AccommodationSalesServicePort {
    public CalculateUnsharedRates(String environment, String scenario) {
        super(environment);
        // Generate a request from a project xml file
        setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("calculateUnsharedRates")));
        setRequestNodeValueByXPath(getTestScenario(getService(), getOperation(), scenario));
        removeComments();
        removeWhiteSpace();
        generateServiceContext();
    }

    // setters
    public void setUnsharedChainUnsharedRoomDetailTCGId(String value) {
        setRequestNodeValueByXPath("/Envelope/Body/calculateUnsharedRates/request/unSharedChain/shareRoomDetails/unSharedRoomDetail/travelComponentGroupingId", value);
    }

    public void setUnsharedChainUnsharedRoomDetailTCId(String value) {
        setRequestNodeValueByXPath("/Envelope/Body/calculateUnsharedRates/request/unSharedChain/shareRoomDetails/unSharedRoomDetail/travelComponentId", value);
    }

    public void setUnsharedChainSharedRoomDetailTCGId(String value) {
        setRequestNodeValueByXPath("/Envelope/Body/calculateUnsharedRates/request/unSharedChain/shareRoomDetails/sharedRoomDetail/travelComponentGroupingId", value);
    }

    public void setUnsharedChainSharedRoomDetailTCId(String value) {
        setRequestNodeValueByXPath("/Envelope/Body/calculateUnsharedRates/request/unSharedChain/shareRoomDetails/sharedRoomDetail/travelComponentId", value);
    }

    public void setUnsharedChainShareRoomDetailsTPSId(String value) {
        setRequestNodeValueByXPath("/Envelope/Body/calculateUnsharedRates/request/unSharedChain/shareRoomDetails/travelPlanSegmentId", value);
    }

    public void setUnsharedAccommodationUnShareRoomDetailsTCGId(String value) {
        setRequestNodeValueByXPath("/Envelope/Body/calculateUnsharedRates/request/unsharedAccomadation/unSharedRoomDetail/travelComponentGroupingId", value);
    }

    public void setUnsharedAccommodationUnShareRoomDetailsTCId(String value) {
        setRequestNodeValueByXPath("/Envelope/Body/calculateUnsharedRates/request/unsharedAccomadation/unSharedRoomDetail/travelComponentId", value);
    }

    public void setUnsharedAccommodationTPSd(String value) {
        setRequestNodeValueByXPath("/Envelope/Body/calculateUnsharedRates/request/unsharedAccomadation/travelPlanSegmentId", value);
    }

    // getters
    public String getfreezeId() {
        return getResponseNodeValueByXPath("/Envelope/Body/calculateUnsharedRatesResponse/splitRateWithTotalTO/shareChains/shareRoomDetails/unSharedRoomDetail/freezeId");
    }

    public String getinventoryStatus() {
        return getResponseNodeValueByXPath("/Envelope/Body/calculateUnsharedRatesResponse/splitRateWithTotalTO/shareChains/shareRoomDetails/unSharedRoomDetail/inventoryStatus");
    }
}