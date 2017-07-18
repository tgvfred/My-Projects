package com.disney.api.soapServices.accommodationModule.accommodationBatchComponentWSPort.operation;

import com.disney.api.soapServices.accommodationModule.accommodationBatchComponentWSPort.AccommodationBatchComponentWSPort;
import com.disney.utils.XMLTools;

public class StageMassReinstateTransactional extends AccommodationBatchComponentWSPort {
    private String defaultProcessName = MASS_REINSTATE;
    private String processName;

    public StageMassReinstateTransactional(String environment, String scenario) {
        super(environment);

        // Generate a request from a project xml file
        setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("stageMassReinstateTransactional")));
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

    public void setReinstateWithPrice(String value) {
        setRequestNodeValueByXPath("//reInstateWithPrice", value);
    }

    public void setReinstateContactName(String value) {
        setRequestNodeValueByXPath("//reInstateContactName", value);
    }

    public void setReinstateReason(String value) {
        setRequestNodeValueByXPath("//reInstateReason", value);
    }

    public void setIsFeeWaived(String value) {
        setRequestNodeValueByXPath("//isFeeWaived", value);
    }

    public void setTcg(String value) {
        setRequestNodeValueByXPath("//travelComponentGroupingId", value);
    }

    public void setTpId(String value) {
        setRequestNodeValueByXPath("//travelPlanId", value);
    }

    public String getResponseProcessId() {
        return getResponseNodeValueByXPath("//processId");
    }

    public String getResponseProcessCode() {
        return getResponseNodeValueByXPath("//processCode");
    }

}
