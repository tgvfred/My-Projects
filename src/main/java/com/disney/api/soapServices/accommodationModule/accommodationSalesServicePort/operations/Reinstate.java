
package com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations;

import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.AccommodationSalesServicePort;
import com.disney.utils.XMLTools;

public class Reinstate extends AccommodationSalesServicePort {

    public Reinstate(String environment, String scenario) {
        super(environment);

        // Generate a request from a project xml file
        setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("reinstate")));
        generateServiceContext();
        setRequestNodeValueByXPath(getTestScenario(getService(), getOperation(), scenario));
        removeComments();
        removeWhiteSpace();
    }

    public Reinstate(String environment) {
        super(environment);
        // Generate a request from a project xml file
        setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("reinstate")));
        generateServiceContext();
        removeComments();
        removeWhiteSpace();
    }

    public void setTravelComponentId(String Tcp_ID) {
        setRequestNodeValueByXPath("/Envelope/Body/reinstate/request/roomdetails/travelComponentId", Tcp_ID);
    }

    public void setTravelComponentGroupingId(String Tcg_ID) {
        setRequestNodeValueByXPath("/Envelope/Body/reinstate/request/roomdetails/travelComponentGroupingId", Tcg_ID);
    }

    public void setTravelPlanSegmentId(String Tps_ID) {
        setRequestNodeValueByXPath("/Envelope/Body/reinstate/request/travelPlanSegmentId", Tps_ID);
    }

    public void setCommChannel(String value) {
        setRequestNodeValueByXPath("/Envelope/Body/reinstate/request/communicationchannel", value);
    }

    public void setSalesChannel(String value) {
        setRequestNodeValueByXPath("/Envelope/Body/reinstate/request/saleschannel", value);
    }

    public void setReinstateReasonCode(String value) {
        setRequestNodeValueByXPath("/Envelope/Body/reinstate/request/reinstateReasonCode", value);
    }

    public void setIsCancelFeeWaived(String value) {
        setRequestNodeValueByXPath("/Envelope/Body/reinstate/request/isCancelFeeWaived", value);
    }

    public void setLocation(String value) {
        setRequestNodeValueByXPath("/Envelope/Body/reinstate/request/location", value);
    }

    public void setInventoryOverrideReason(String value) {
        setRequestNodeValueByXPath("/Envelope/Body/reinstate/request/roomdetails/inventoryOverrideReason", value);
    }

    public void setInventoryOverrideContactName(String value) {
        setRequestNodeValueByXPath("/Envelope/Body/reinstate/request/roomdetails/inventoryOverrideContactName", value);
    }

}
