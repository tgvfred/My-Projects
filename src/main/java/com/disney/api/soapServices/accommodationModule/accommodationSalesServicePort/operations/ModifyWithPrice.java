package com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations;

import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.AccommodationSalesServicePort;
import com.disney.utils.XMLTools;

/**
 * @deprecated deprecated in 8.5
 * @author phlej001
 *
 */
@Deprecated
public class ModifyWithPrice extends AccommodationSalesServicePort {
    public ModifyWithPrice(String environment, String scenario) {
        super(environment);
        // Generate a request from a project xml file
        setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("modifyWithPrice")));
        generateServiceContext();
        setRequestNodeValueByXPath(getTestScenario(getService(), getOperation(), scenario));
        removeComments();
        removeWhiteSpace();
    }

    public void setTravelComponentGroupingId(String tcg_id) {
        setRequestNodeValueByXPath("/Envelope/Body/modifyWithPrice/request/roomDetail/travelComponentGroupingId", tcg_id);
    }

    public void setTravelComponentId(String tc_id) {
        setRequestNodeValueByXPath("/Envelope/Body/modifyWithPrice/request/roomDetail/travelComponentId", tc_id);
    }

    public void setTravelPlanId(String tp_id) {
        setRequestNodeValueByXPath("/Envelope/Body/modifyWithPrice/request/travelPlanId", tp_id);
    }

    public void setTravelPlanSegementId(String tps_id) {
        setRequestNodeValueByXPath("/Envelope/Body/modifyWithPrice/request/travelPlanSegmentId", tps_id);
    }

    public String getTravelComponentGroupingId() {
        return getResponseNodeValueByXPath("/Envelope/Body/modifyWithPriceResponse/response/roomDetails/travelComponentGroupingId");
    }

    public String getTravelComponentId() {
        return getResponseNodeValueByXPath("/Envelope/Body/modifyWithPriceResponse/response/roomDetails/travelComponentId");
    }
}