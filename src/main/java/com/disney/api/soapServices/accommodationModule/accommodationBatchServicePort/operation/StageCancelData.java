package com.disney.api.soapServices.accommodationModule.accommodationBatchServicePort.operation;

import com.disney.api.soapServices.accommodationModule.accommodationBatchServicePort.AccommodationBatchServicePort;
import com.disney.utils.XMLTools;

public class StageCancelData extends AccommodationBatchServicePort {
    private String defaultProcessName = MASS_CANCEL;
    private String processName;

    public StageCancelData(String environment, String scenario) {
        super(environment);

        // Generate a request from a project xml file
        setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("stageCancelData")));
        generateServiceContext();
        setRequestNodeValueByXPath(getTestScenario(getService(), getOperation(), scenario));
        removeComments();
        removeWhiteSpace();
        setProcessName(getLocalDefaultProcessName());
    }

    public String getLocalProcessName() {
        return processName;
    }

    public void setLocalProcessName(String processName) {
        this.processName = processName;
    }

    public String getLocalDefaultProcessName() {
        return defaultProcessName;
    }

    public void setProcessName(String value) {
        setRequestNodeValueByXPath("//processName", value);
    }

    public void setCancelDate(String value) {
        setRequestNodeValueByXPath("/Envelope/Body/stageCancelData/request/massCancelAccommodationRequestDetails/cancelDate", value);
    }

    public void setCancelReasonCode(String value) {
        setRequestNodeValueByXPath("/Envelope/Body/stageCancelData/request/massCancelAccommodationRequestDetails/cancelReasonCode", value);
    }

    public void setIsOverridden(String value) {
        setRequestNodeValueByXPath("/Envelope/Body/stageCancelData/request/massCancelAccommodationRequestDetails/isOverridden", value);
    }

    public void setIsWaived(String value) {
        setRequestNodeValueByXPath("/Envelope/Body/stageCancelData/request/massCancelAccommodationRequestDetails/isWaived", value);
    }

    public void setOVerridenCancelFEe(String value) {
        setRequestNodeValueByXPath("/Envelope/Body/stageCancelData/request/massCancelAccommodationRequestDetails/overriddenCancelFee", value);
    }

    public void setTCg(String value) {
        setRequestNodeValueByXPath("/Envelope/Body/stageCancelData/request/massCancelAccommodationRequestDetails/travelComponentGroupingId", value);
    }

    public void setCancelContactName(String value) {
        setRequestNodeValueByXPath("/Envelope/Body/stageCancelData/request/massCancelAccommodationRequestDetails/cancelContactName", value);
    }

    public String getResponseProcessId() {
        return getResponseNodeValueByXPath("//processId");
    }

    public String getResponseProcessCode() {
        return getResponseNodeValueByXPath("//processCode");
    }

}
