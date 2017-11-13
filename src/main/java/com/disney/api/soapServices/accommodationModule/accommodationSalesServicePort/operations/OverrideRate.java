package com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations;

import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.AccommodationSalesServicePort;
import com.disney.utils.XMLTools;

public class OverrideRate extends AccommodationSalesServicePort {
    public OverrideRate(String environment, String scenario) {
        super(environment);

        // Generate a request from a project xml file
        setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("overrideRate")));
        generateServiceContext();
        setRequestNodeValueByXPath(getTestScenario(getService(), getOperation(), scenario));
        removeComments();
        removeWhiteSpace();
    }

    public void setTravelComponentGroupingId(String tcg_id) {
        setRequestNodeValueByXPath("/Envelope/Body/overrideRate/request/travelComponentGroupingId", tcg_id);
    }

    public void setTravelPlanSegementId(String tps_id) {
        setRequestNodeValueByXPath("/Envelope/Body/overrideRate/request/travelPlanSegmentId", tps_id);
    }

    public String gettravelComponentGroupingId() {
        return getResponseNodeValueByXPath("/Envelope/Body/overrideRateResponse/roomDetails/travelComponentGroupingId");
    }

    public String gettravelComponentId() {
        return getResponseNodeValueByXPath("/Envelope/Body/overrideRateResponse/roomDetails/travelComponentId");
    }
}