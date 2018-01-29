package com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations;

import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.AccommodationSalesServicePort;
import com.disney.utils.XMLTools;

/**
 * @deprecated deprecated in 8.5
 * @author phlej001
 *
 */
@Deprecated
public class ReinstateWithRePrice extends AccommodationSalesServicePort {

    public ReinstateWithRePrice(String environment, String scenario) {
        super(environment);

        // Generate a request from a project xml file
        setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("reinstateWithRePrice")));
        generateServiceContext();
        setRequestNodeValueByXPath(getTestScenario(getService(), getOperation(), scenario));
        removeComments();
        removeWhiteSpace();
    }

    public void setTravelComponentId(String Tcp_ID) {
        setRequestNodeValueByXPath("/Envelope/Body/reinstateWithRePrice/request/roomdetails/travelComponentId", Tcp_ID);
    }

    public void setTravelComponentGroupingId(String Tcg_ID) {
        setRequestNodeValueByXPath("/Envelope/Body/reinstateWithRePrice/request/roomdetails/travelComponentGroupingId", Tcg_ID);
    }

    public void setTravelPlanSegmentId(String Tps_ID) {
        setRequestNodeValueByXPath("/Envelope/Body/reinstateWithRePrice/request/travelPlanSegmentId", Tps_ID);

    }
}