package com.disney.api.soapServices.accommodationModule.accommodationBatchComponentWSPort.operation;

import com.disney.api.soapServices.accommodationModule.accommodationBatchComponentWSPort.AccommodationBatchComponentWSPort;
import com.disney.utils.XMLTools;

public class StageRemoveGroupTransactional extends AccommodationBatchComponentWSPort {
    private String defaultProcessName = REMOVEGROUP;
    private String processName;

    public StageRemoveGroupTransactional(String environment) {
        super(environment);

        // Generate a request from a project xml file
        setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("stageRemoveGroupTransactional")));
        removeComments();
        removeWhiteSpace();
        generateServiceContext();
        setProcessName(getLocalDefaultProcessName());
    }

    public StageRemoveGroupTransactional(String environment, String scenario) {
        super(environment);

        // Generate a request from a project xml file
        setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("stageRemoveGroupTransactional")));
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

    public void setTcg(String value) {
        setRequestNodeValueByXPath("//travelComponentGroupNoList", value);
    }

    public String getResponseProcessId() {
        return getResponseNodeValueByXPath("//processId");
    }

    public String getResponseProcessCode() {
        return getResponseNodeValueByXPath("//processCode");
    }

}
