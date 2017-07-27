package com.disney.api.soapServices.accommodationModule.accommodationBatchComponentWSPort.operation;

import com.disney.api.soapServices.accommodationModule.accommodationBatchComponentWSPort.AccommodationBatchComponentWSPort;
import com.disney.api.soapServices.core.BaseSoapCommands;
import com.disney.api.soapServices.core.exceptions.XPathNotFoundException;
import com.disney.utils.XMLTools;

public class GetStagedRecordsForRemoveGroup extends AccommodationBatchComponentWSPort {
    public GetStagedRecordsForRemoveGroup(String environment) {
        super(environment);

        setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("getStagedRecordsForRemoveGroup")));
        removeComments();
        removeWhiteSpace();
        generateServiceContext();
    }

    /*
     * Request setters
     */
    public void setProcessDataID(String value) {
        trySetRequestNodeValueByXPath("/Envelope/Body/getStagedRecordsForRemoveGroup/processDataId", value);
    }

    /*
     * Response getters
     */

    /*
     * Utility functions
     */
    private String tryGetResponseNodeValueByXPath(String xpath) {
        try {
            return getResponseNodeValueByXPath(xpath);
        } catch (XPathNotFoundException e) {
            return null;
        }
    }

    private void trySetRequestNodeValueByXPath(String xpath, String value) {
        if (getNumberOfRequestNodesByXPath(xpath) > 0) {
            setRequestNodeValueByXPath(xpath, value.isEmpty() ? BaseSoapCommands.REMOVE_NODE.toString() : value);
        } else if (!value.isEmpty()) {
            resolveMissingPath(xpath);
            setRequestNodeValueByXPath(xpath, value);
        }
    }

    private void resolveMissingPath(String xpath) {
        int lastindex = xpath.lastIndexOf('/');
        String parentxpath = xpath.substring(0, lastindex);
        String node = xpath.substring(lastindex + 1, xpath.length()).split("\\[")[0];
        if (getNumberOfRequestNodesByXPath(parentxpath) == 0) {
            resolveMissingPath(parentxpath);
        }
        setRequestNodeValueByXPath(parentxpath, BaseSoapCommands.ADD_NODE.commandAppend(node));
    }
}
