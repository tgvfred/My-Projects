package com.disney.api.soapServices.accommodationModule.accommodationBatchComponentWSPort.operation;

import com.disney.api.soapServices.accommodationModule.accommodationBatchComponentWSPort.AccommodationBatchComponentWSPort;
import com.disney.utils.XMLTools;

public class StageMassCancelTransactional extends AccommodationBatchComponentWSPort {
    private String defaultProcessName = MASS_CANCEL;
    private String processName;

    public StageMassCancelTransactional(String environment, String scenario) {
        super(environment);

        // Generate a request from a project xml file
        setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("stageMassCancelTransactional")));
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
        setRequestNodeValueByXPath("//massCancelAccommodationRequestDetails/cancelDate", value);
    }

    public void setCancelReasonCode(String value) {
        setRequestNodeValueByXPath("//massCancelAccommodationRequestDetails/cancelReasonCode", value);
    }

    public void setIsOverridden(String value) {
        setRequestNodeValueByXPath("//massCancelAccommodationRequestDetails/isOverridden", value);
    }

    public void setIsWaived(String value) {
        setRequestNodeValueByXPath("//massCancelAccommodationRequestDetails/isWaived", value);
    }

    public void setOVerridenCancelFEe(String value) {
        setRequestNodeValueByXPath("//massCancelAccommodationRequestDetails/overriddenCancelFee", value);
    }

    public void setTCg(String value) {
        setRequestNodeValueByXPath("//massCancelAccommodationRequestDetails/travelComponentGroupingId", value);
    }

    public String getResponseProcessId() {
        return getResponseNodeValueByXPath("//processId");
    }

    public String getResponseProcessCode() {
        return getResponseNodeValueByXPath("//processCode");
    }

}
