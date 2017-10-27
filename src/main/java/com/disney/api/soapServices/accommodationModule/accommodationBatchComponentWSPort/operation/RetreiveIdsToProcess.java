package com.disney.api.soapServices.accommodationModule.accommodationBatchComponentWSPort.operation;

import com.disney.api.soapServices.accommodationModule.accommodationBatchComponentWSPort.AccommodationBatchComponentWSPort;
import com.disney.api.soapServices.core.exceptions.XPathNotFoundException;
import com.disney.utils.XMLTools;

public class RetreiveIdsToProcess extends AccommodationBatchComponentWSPort {

    public RetreiveIdsToProcess(String environment, String scenario) {
        super(environment);
        // Generate a request from a project xml file
        setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("retreiveIdsToProcess")));
        generateServiceContext();
        // setRequestNodeValueByXPath(getService(), getOperation());
        removeComments();
        removeWhiteSpace();
    }

    // Setters
    public void setProcessId(String value) {
        setRequestNodeValueByXPath("/Envelope/Body/retreiveIdsToProcess/processId", value);
    }

    // Getters
    int index = getNumberOfRequestNodesByXPath("/Envelope/Body/retreiveIdsToProcessResp/retreiveIdsToProcessResponse") + 1;

    public String getRetreiveIdsToProcessResponse(String index) {
        try {
            return getResponseNodeValueByXPath("/Envelope/Body/retreiveIdsToProcessResp/retreiveIdsToProcessResponse[" + index + "]");
        } catch (XPathNotFoundException e) {
            return null;
        }
    }

    public Integer getRetreiveIdsToProcessResponse() {
        return getNumberOfResponseNodesByXPath("/Envelope/Body/retreiveIdsToProcessResp/retreiveIdsToProcessResponse");
    }

    public String getProcessDataIdList() {
        return getResponseNodeValueByXPath("/Envelope/Body/retreiveIdsToProcessResp/retreiveIdsToProcessResponse/processDataIdList");
    }

    public String getProcessType() {
        return getResponseNodeValueByXPath("/Envelope/Body/retreiveIdsToProcessResp/retreiveIdsToProcessResponse/processType");
    }

    public String getProcessId() {
        return getResponseNodeValueByXPath("/Envelope/Body/retreiveIdsToProcess/processId");
    }

}
