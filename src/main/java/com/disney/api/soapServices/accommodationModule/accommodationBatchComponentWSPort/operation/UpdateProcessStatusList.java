package com.disney.api.soapServices.accommodationModule.accommodationBatchComponentWSPort.operation;

import com.disney.api.soapServices.accommodationModule.accommodationBatchComponentWSPort.AccommodationBatchComponentWSPort;
import com.disney.api.soapServices.core.BaseSoapCommands;
import com.disney.api.soapServices.core.exceptions.XPathNotFoundException;
import com.disney.utils.XMLTools;

public class UpdateProcessStatusList extends AccommodationBatchComponentWSPort {

    public UpdateProcessStatusList(String environment) {
        super(environment);

        // Generate a request from a project xml file
        setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("updateProcessStatusList")));
        removeComments();
        removeWhiteSpace();
        generateServiceContext();
    }

    public UpdateProcessStatusList(String environment, String scenario) {
        super(environment);

        // Generate a request from a project xml file
        setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("updateProcessStatusList")));
        generateServiceContext();
        setRequestNodeValueByXPath(getTestScenario(getService(), getOperation(), scenario));
        removeComments();
        removeWhiteSpace();
    }

    // Setters

    public void setProcessDataIdList(String value) {
        setRequestNodeValueByXPath("/Envelope/Body/updateProcessStatusList/processDataIdList", value);
    }

    public void setProcessDataIdList2(String value) {
        try {
            setRequestNodeValueByXPath("/Envelope/Body/updateProcessStatusList/processDataIdList[2]", value);
        } catch (XPathNotFoundException e) {
            setRequestNodeValueByXPath("/Envelope/Body/updateProcessStatusList", BaseSoapCommands.ADD_NODE.commandAppend("processDataIdList[2]"));
            setRequestNodeValueByXPath("/Envelope/Body/updateProcessStatusList/processDataIdList[2]", value);
        }
    }

    public void setProcessType(String value) {
        try {
            setRequestNodeValueByXPath("/Envelope/Body/updateProcessStatusList/processType", value);
        } catch (XPathNotFoundException e) {
            setRequestNodeValueByXPath("/Envelope/Body/updateProcessStatusList", BaseSoapCommands.ADD_NODE.commandAppend("processType"));
            setRequestNodeValueByXPath("/Envelope/Body/updateProcessStatusList/processType", value);
        }
    }

    public void setProcessingStatus(String value) {
        try {
            setRequestNodeValueByXPath("/Envelope/Body/updateProcessStatusList/processingStatus", value);
        } catch (XPathNotFoundException e) {
            setRequestNodeValueByXPath("/Envelope/Body/updateProcessStatusList", BaseSoapCommands.ADD_NODE.commandAppend("processingStatus"));
            setRequestNodeValueByXPath("/Envelope/Body/updateProcessStatusList/processingStatus", value);
        }
    }

    public void setErrorMessage(String value) {
        try {
            setRequestNodeValueByXPath("/Envelope/Body/updateProcessStatusList/errorMessage", value);
        } catch (XPathNotFoundException e) {
            setRequestNodeValueByXPath("/Envelope/Body/updateProcessStatusList", BaseSoapCommands.ADD_NODE.commandAppend("errorMessage"));
            setRequestNodeValueByXPath("/Envelope/Body/updateProcessStatusList/errorMessage", value);
        }
    }

    public void setTPSId(String value) {
        try {
            setRequestNodeValueByXPath("/Envelope/Body/updateProcessStatusList/tpsId", value);
        } catch (XPathNotFoundException e) {
            setRequestNodeValueByXPath("/Envelope/Body/updateProcessStatusList", BaseSoapCommands.ADD_NODE.commandAppend("tpsId"));
            setRequestNodeValueByXPath("/Envelope/Body/updateProcessStatusList/tpsId", value);
        }
    }

    // Getters

}
