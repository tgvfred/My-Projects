package com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations;

import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.AccommodationSalesServicePort;
import com.disney.utils.XMLTools;

/**
 * @deprecated deprecated in 8.5
 * @author phlej001
 *
 */
@Deprecated
public class AddWithPrice extends AccommodationSalesServicePort {
    public AddWithPrice(String environment, String scenario) {
        super(environment);
        // Generate a request from a project xml file
        setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("addWithPrice")));
        generateServiceContext();
        setRequestNodeValueByXPath(getTestScenario(getService(), getOperation(), scenario));
        removeComments();
        removeWhiteSpace();
    }

    public void setTravelComponentGroupingId(String tcg_id) {
        setRequestNodeValueByXPath("/Envelope/Body/addWithPrice/request/roomDetail/travelComponentGroupingId", tcg_id);
    }

    public void setTravelComponentId(String tc_id) {
        setRequestNodeValueByXPath("/Envelope/Body/addWithPrice/request/roomDetail/travelComponentId", tc_id);
    }

    public void setTravelPlanId(String tp_id) {
        setRequestNodeValueByXPath("/Envelope/Body/addWithPrice/request/travelPlanId", tp_id);
    }

    public void setTravelPlanSegementId(String tps_id) {
        setRequestNodeValueByXPath("/Envelope/Body/addWithPrice/request/travelPlanSegmentId", tps_id);
    }

    public String getTravelComponentGroupingId() {
        return getResponseNodeValueByXPath("/Envelope/Body/addWithPriceResponse/addResponse/roomDetails/travelComponentGroupingId");
    }

    public String getTravelComponentId() {
        return getResponseNodeValueByXPath("/Envelope/Body/addWithPriceResponse/addResponse/roomDetails/travelComponentId");
    }
}