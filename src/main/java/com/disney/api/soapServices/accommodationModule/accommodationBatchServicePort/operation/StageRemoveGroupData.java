package com.disney.api.soapServices.accommodationModule.accommodationBatchServicePort.operation;

import com.disney.api.soapServices.accommodationModule.accommodationBatchServicePort.AccommodationBatchServicePort;
import com.disney.utils.XMLTools;

public class StageRemoveGroupData extends AccommodationBatchServicePort {
    private String defaultProcessName = REMOVEGROUP;
    private String processName;

    public StageRemoveGroupData(String environment) {
        super(environment);

        // Generate a request from a project xml file
        setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("stageRemoveGroupData")));
        removeComments();
        removeWhiteSpace();
        generateServiceContext();
        setProcessName(getLocalDefaultProcessName());
    }

    public StageRemoveGroupData(String environment, String scenario) {
        super(environment);

        // Generate a request from a project xml file
        setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("stageRemoveGroupData")));
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
        setRequestNodeValueByXPath("/Envelope/Body/stageRemoveGroupData/request/processName", value);
    }

    public void setTcg(String value) {
        setRequestNodeValueByXPath("/Envelope/Body/stageRemoveGroupData/request/travelComponentGroupNoList", value);
    }

    public String getResponseProcessId() {
        return getResponseNodeValueByXPath("//processId");
    }

    public String getResponseProcessCode() {
        return getResponseNodeValueByXPath("//processCode");
    }

}
