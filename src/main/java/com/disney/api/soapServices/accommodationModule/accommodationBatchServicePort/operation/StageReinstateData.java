package com.disney.api.soapServices.accommodationModule.accommodationBatchServicePort.operation;

import com.disney.api.soapServices.accommodationModule.accommodationBatchServicePort.AccommodationBatchServicePort;
import com.disney.utils.XMLTools;

public class StageReinstateData extends AccommodationBatchServicePort {
    private String defaultProcessName = MASS_REINSTATE;
    private String processName;

    public StageReinstateData(String environment, String scenario) {
        super(environment);

        // Generate a request from a project xml file
        setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("stageReinstateData")));
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
        setRequestNodeValueByXPath("/Envelope/Body/stageReinstateData/request/processName", value);
    }

    public void setReinstateWithPrice(String value) {
        setRequestNodeValueByXPath("/Envelope/Body/stageReinstateData/request/massReinstateRequestDetail/reInstateWithPrice", value);
    }

    public void setReinstateContactName(String value) {
        setRequestNodeValueByXPath("/Envelope/Body/stageReinstateData/request/massReinstateRequestDetail/reInstateContactName", value);
    }

    public void setReinstateReason(String value) {
        setRequestNodeValueByXPath("/Envelope/Body/stageReinstateData/request/massReinstateRequestDetail/reInstateReason", value);
    }

    public void setIsFeeWaived(String value) {
        setRequestNodeValueByXPath("/Envelope/Body/stageReinstateData/request/massReinstateRequestDetail/isFeeWaived", value);
    }

    public void setTcg(String value) {
        setRequestNodeValueByXPath("/Envelope/Body/stageReinstateData/request/massReinstateRequestDetail/travelComponentGroupingId", value);
    }

    public void setTpId(String value) {
        setRequestNodeValueByXPath("/Envelope/Body/stageReinstateData/request/massReinstateRequestDetail/travelPlanId", value);
    }

    // public void setSecondTcg(String value) {
    // setRequestNodeValueByXPath("/Envelope/Body/stageMassReinstateTransactional/request[2]/massReinstateRequestDetail/travelComponentGroupingId", value);
    // }
    //
    // public void setSecondTpId(String value) {
    // setRequestNodeValueByXPath("/Envelope/Body/stageMassReinstateTransactional/request[2]/massReinstateRequestDetail/travelPlanId", value);
    // }

    public String getResponseProcessId() {
        return getResponseNodeValueByXPath("//processId");
    }

    public String getResponseProcessCode() {
        return getResponseNodeValueByXPath("//processCode");
    }

}
