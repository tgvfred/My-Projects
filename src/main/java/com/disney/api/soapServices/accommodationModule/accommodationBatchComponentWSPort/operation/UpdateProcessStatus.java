package com.disney.api.soapServices.accommodationModule.accommodationBatchComponentWSPort.operation;

import com.disney.api.soapServices.accommodationModule.accommodationBatchComponentWSPort.AccommodationBatchComponentWSPort;
import com.disney.api.soapServices.core.BaseSoapCommands;
import com.disney.api.soapServices.core.exceptions.XPathNotFoundException;
import com.disney.utils.XMLTools;

public class UpdateProcessStatus extends AccommodationBatchComponentWSPort {

    public UpdateProcessStatus(String environment) {
        super(environment);

        // Generate a request from a project xml file
        setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("updateProcessStatus")));
        removeComments();
        removeWhiteSpace();
        generateServiceContext();
    }

    public UpdateProcessStatus(String environment, String scenario) {
        super(environment);

        // Generate a request from a project xml file
        setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("updateProcessStatus")));
        generateServiceContext();
        setRequestNodeValueByXPath(getTestScenario(getService(), getOperation(), scenario));
        removeComments();
        removeWhiteSpace();
    }

    // Setters

    public void setProcessDataId(String value) {
        try {
            setRequestNodeValueByXPath("/Envelope/Body/updateProcessStatus/processDataId", value);
        } catch (XPathNotFoundException e) {
            setRequestNodeValueByXPath("/Envelope/Body/updateProcessStatus", BaseSoapCommands.ADD_NODE.commandAppend("processDataId"));
            setRequestNodeValueByXPath("/Envelope/Body/updateProcessStatus/processDataId", value);
        }
    }

    // Sample RQ look different from the RQ in SoapUI
    public void setProcessDataIdList(String value) {
        try {
            setRequestNodeValueByXPath("/Envelope/Body/updateProcessStatus/processDataIdList", value);
        } catch (XPathNotFoundException e) {
            setRequestNodeValueByXPath("/Envelope/Body/updateProcessStatus", BaseSoapCommands.ADD_NODE.commandAppend("processDataIdList"));
            setRequestNodeValueByXPath("/Envelope/Body/updateProcessStatus/processDataIdList", value);
        }
    }

    public void setProcessType(String value) {
        try {
            setRequestNodeValueByXPath("/Envelope/Body/updateProcessStatus/processType", value);
        } catch (XPathNotFoundException e) {
            setRequestNodeValueByXPath("/Envelope/Body/updateProcessStatus", BaseSoapCommands.ADD_NODE.commandAppend("processType"));
            setRequestNodeValueByXPath("/Envelope/Body/updateProcessStatus/processType", value);
        }
    }

    public void setProcessingStatus(String value) {
        try {
            setRequestNodeValueByXPath("/Envelope/Body/updateProcessStatus/processingStatus", value);
        } catch (XPathNotFoundException e) {
            setRequestNodeValueByXPath("/Envelope/Body/updateProcessStatus", BaseSoapCommands.ADD_NODE.commandAppend("processingStatus"));
            setRequestNodeValueByXPath("/Envelope/Body/updateProcessStatus/processingStatus", value);
        }
    }

    public void setErrorMessage(String value) {
        try {
            setRequestNodeValueByXPath("/Envelope/Body/updateProcessStatus/errorMessage", value);
        } catch (XPathNotFoundException e) {
            setRequestNodeValueByXPath("/Envelope/Body/updateProcessStatus", BaseSoapCommands.ADD_NODE.commandAppend("errorMessage"));
            setRequestNodeValueByXPath("/Envelope/Body/updateProcessStatus/errorMessage", value);
        }
    }

    public void setTPSId(String value) {
        try {
            setRequestNodeValueByXPath("/Envelope/Body/updateProcessStatus/tpsId", value);
        } catch (XPathNotFoundException e) {
            setRequestNodeValueByXPath("/Envelope/Body/updateProcessStatus", BaseSoapCommands.ADD_NODE.commandAppend("tpsId"));
            setRequestNodeValueByXPath("/Envelope/Body/updateProcessStatus/tpsId", value);
        }
    }

    // Getters

}
