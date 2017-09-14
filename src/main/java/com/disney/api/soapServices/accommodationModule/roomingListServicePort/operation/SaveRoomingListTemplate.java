package com.disney.api.soapServices.accommodationModule.roomingListServicePort.operation;

import com.disney.api.soapServices.accommodationModule.roomingListServicePort.RoomingListServicePort;
import com.disney.api.soapServices.core.BaseSoapCommands;
import com.disney.api.soapServices.core.exceptions.XPathNotFoundException;
import com.disney.utils.XMLTools;

public class SaveRoomingListTemplate extends RoomingListServicePort {

    public SaveRoomingListTemplate(String environment) {
        super(environment);

        // Generate a request from a project xml file
        setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("saveRoomingListTemplate")));
        removeComments();
        removeWhiteSpace();
        generateServiceContext();
    }

    public SaveRoomingListTemplate(String environment, String scenario) {
        super(environment);

        // Generate a request from a project xml file
        setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("saveRoomingListTemplate")));
        generateServiceContext();
        setRequestNodeValueByXPath(getTestScenario(getService(), getOperation(), scenario));
        removeComments();
        removeWhiteSpace();
    }

    // Setters

    public void setTemplateName(String value) {
        try {
            setRequestNodeValueByXPath("/Envelope/Body/saveRoomingListTemplate/request/templateTO/templateName", value);
        } catch (XPathNotFoundException e) {
            setRequestNodeValueByXPath("/Envelope/Body/saveRoomingListTemplate/request/templateTO", BaseSoapCommands.ADD_NODE.commandAppend("templateName"));
            setRequestNodeValueByXPath("/Envelope/Body/saveRoomingListTemplate/request/templateTO/templateName", value);
        }
    }

    public void setCreatedUpdatedBy(String value) {
        try {
            setRequestNodeValueByXPath("/Envelope/Body/saveRoomingListTemplate/request/templateTO/createdUpdatedBy", value);
        } catch (XPathNotFoundException e) {
            setRequestNodeValueByXPath("/Envelope/Body/saveRoomingListTemplate/request/templateTO", BaseSoapCommands.ADD_NODE.commandAppend("createdUpdatedBy"));
            setRequestNodeValueByXPath("/Envelope/Body/saveRoomingListTemplate/request/templateTO/createdUpdatedBy", value);
        }
    }

    public void setLastUsedDate(String value) {
        try {
            setRequestNodeValueByXPath("/Envelope/Body/saveRoomingListTemplate/request/templateTO/lastUsedDate", value);
        } catch (XPathNotFoundException e) {
            setRequestNodeValueByXPath("/Envelope/Body/saveRoomingListTemplate/request/templateTO", BaseSoapCommands.ADD_NODE.commandAppend("lastUsedDate"));
            setRequestNodeValueByXPath("/Envelope/Body/saveRoomingListTemplate/request/templateTO/lastUsedDate", value);
        }
    }

    public void setOrderNumber(String value) {
        try {
            setRequestNodeValueByXPath("/Envelope/Body/saveRoomingListTemplate/request/templateTO/templateFieldList/orderNumber", value);
        } catch (XPathNotFoundException e) {
            setRequestNodeValueByXPath("/Envelope/Body/saveRoomingListTemplate/request/templateTO/templateFieldList", BaseSoapCommands.ADD_NODE.commandAppend("orderNumber"));
            setRequestNodeValueByXPath("/Envelope/Body/saveRoomingListTemplate/request/templateTO/templateFieldList/orderNumber", value);
        }
    }

    public void setFieldSequenceNumber(String value) {
        try {
            setRequestNodeValueByXPath("/Envelope/Body/saveRoomingListTemplate/request/templateTO/templateFieldList/fieldSequenceNumber", value);
        } catch (XPathNotFoundException e) {
            setRequestNodeValueByXPath("/Envelope/Body/saveRoomingListTemplate/request/templateTO/templateFieldList", BaseSoapCommands.ADD_NODE.commandAppend("fieldSequenceNumber"));
            setRequestNodeValueByXPath("/Envelope/Body/saveRoomingListTemplate/request/templateTO/templateFieldList/fieldSequenceNumber", value);
        }
    }

    public void setFieldId(String value) {
        try {
            setRequestNodeValueByXPath("/Envelope/Body/saveRoomingListTemplate/request/templateTO/templateFieldList/fieldTO/fieldId", value);
        } catch (XPathNotFoundException e) {
            setRequestNodeValueByXPath("/Envelope/Body/saveRoomingListTemplate/request/templateTO/templateFieldList/fieldTO", BaseSoapCommands.ADD_NODE.commandAppend("fieldId"));
            setRequestNodeValueByXPath("/Envelope/Body/saveRoomingListTemplate/request/templateTO/templateFieldList/fieldTO/fieldId", value);
        }
    }

    public void setIsRepeatable(String value) {
        try {
            setRequestNodeValueByXPath("/Envelope/Body/saveRoomingListTemplate/request/templateTO/templateFieldList/fieldTO/isRepeatable", value);
        } catch (XPathNotFoundException e) {
            setRequestNodeValueByXPath("/Envelope/Body/saveRoomingListTemplate/request/templateTO/templateFieldList/fieldTO", BaseSoapCommands.ADD_NODE.commandAppend("isRepeatable"));
            setRequestNodeValueByXPath("/Envelope/Body/saveRoomingListTemplate/request/templateTO/templateFieldList/fieldTO/isRepeatable", value);
        }
    }

    public void setDefaultFieldIndicator(String value) {
        try {
            setRequestNodeValueByXPath("/Envelope/Body/saveRoomingListTemplate/request/templateTO/templateFieldList/fieldTO/defaultFieldIndicator", value);
        } catch (XPathNotFoundException e) {
            setRequestNodeValueByXPath("/Envelope/Body/saveRoomingListTemplate/request/templateTO/templateFieldList/fieldTO", BaseSoapCommands.ADD_NODE.commandAppend("defaultFieldIndicator"));
            setRequestNodeValueByXPath("/Envelope/Body/saveRoomingListTemplate/request/templateTO/templateFieldList/fieldTO/defaultFieldIndicator", value);
        }
    }

    public void setFieldName(String value) {
        try {
            setRequestNodeValueByXPath("/Envelope/Body/saveRoomingListTemplate/request/templateTO/templateFieldList/fieldTO/fieldName", value);
        } catch (XPathNotFoundException e) {
            setRequestNodeValueByXPath("/Envelope/Body/saveRoomingListTemplate/request/templateTO/templateFieldList/fieldTO", BaseSoapCommands.ADD_NODE.commandAppend("fieldName"));
            setRequestNodeValueByXPath("/Envelope/Body/saveRoomingListTemplate/request/templateTO/templateFieldList/fieldTO/fieldName", value);
        }
    }

    public void setFieldDataType(String value) {
        try {
            setRequestNodeValueByXPath("/Envelope/Body/saveRoomingListTemplate/request/templateTO/templateFieldList/fieldTO/fieldDataType", value);
        } catch (XPathNotFoundException e) {
            setRequestNodeValueByXPath("/Envelope/Body/saveRoomingListTemplate/request/templateTO/templateFieldList/fieldTO", BaseSoapCommands.ADD_NODE.commandAppend("fieldDataType"));
            setRequestNodeValueByXPath("/Envelope/Body/saveRoomingListTemplate/request/templateTO/templateFieldList/fieldTO/fieldDataType", value);
        }
    }
}
