package com.disney.api.soapServices.accommodationModule.accommodationBatchComponentWSPort.operation;

import static com.disney.api.soapServices.accommodationModule.helpers.AccommodationBaseTest.isValid;

import com.disney.api.soapServices.accommodationModule.accommodationBatchComponentWSPort.AccommodationBatchComponentWSPort;
import com.disney.api.soapServices.core.BaseSoapCommands;
import com.disney.utils.XMLTools;

public class GetStagedRecordsForReinstate extends AccommodationBatchComponentWSPort {

    public GetStagedRecordsForReinstate(String environment, String scenario) {
        super(environment);

        // Generate a request from a project xml file
        setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("getStagedRecordsForReinstate")));
        generateServiceContext();
        setRequestNodeValueByXPath(getTestScenario(getService(), getOperation(), scenario));
        removeComments();
        removeWhiteSpace();

    }

    public void setProcessDataId(String value) {
        setRequestNodeValueByXPath(
                "/Envelope/Body/getStagedRecordsForReinstate/processDataId", value);
    }

    private void setNewProcessDataId(String baseXpath, String value) {
        if (isValid(value)) {
            if (baseXpath.contains("processDataId")) {
                setRequestNodeValueByXPath(baseXpath, value);
            } else {
                setRequestNodeValueByXPath(baseXpath + "processDataId", value);
            }
        }
    }

    public void addProcessDataId(String newProcessDataId) {
        int existingNodes = getNumberOfRequestNodesByXPath("/Envelope/Body/getStagedRecordsForReinstate/processDataId");
        String baseXpath = "/Envelope/Body/getStagedRecordsForReinstate";
        setRequestNodeValueByXPath(baseXpath, BaseSoapCommands.ADD_NODE.commandAppend("processDataId"));
        existingNodes++;
        baseXpath = "/Envelope/Body/getStagedRecordsForReinstate/processDataId[" + existingNodes + "]";
        setNewProcessDataId(baseXpath, newProcessDataId);
    }
}