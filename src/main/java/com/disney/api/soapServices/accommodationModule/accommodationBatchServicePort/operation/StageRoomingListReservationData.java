package com.disney.api.soapServices.accommodationModule.accommodationBatchServicePort.operation;

import com.disney.api.soapServices.accommodationModule.accommodationBatchServicePort.AccommodationBatchServicePort;
import com.disney.api.soapServices.core.BaseSoapCommands;
import com.disney.api.soapServices.core.exceptions.XPathNotFoundException;
import com.disney.utils.XMLTools;

public class StageRoomingListReservationData extends AccommodationBatchServicePort {

    public StageRoomingListReservationData(String environment) {
        super(environment);

        // Generate a request from a project xml file
        setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("stageRoomingListReservationData")));
        removeComments();
        removeWhiteSpace();
        generateServiceContext();
    }

    public StageRoomingListReservationData(String environment, String scenario) {
        super(environment);

        // Generate a request from a project xml file
        setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("stageRoomingListReservationData")));
        generateServiceContext();
        setRequestNodeValueByXPath(getTestScenario(getService(), getOperation(), scenario));
        removeComments();
        removeWhiteSpace();
    }

    // Setters

    public void setProcessName(String value) {
        try {
            setRequestNodeValueByXPath("/Envelope/Body/stageRoomingListReservationData/request/processName", value);
        } catch (XPathNotFoundException e) {
            setRequestNodeValueByXPath("/Envelope/Body/stageRoomingListReservationData/request", BaseSoapCommands.ADD_NODE.commandAppend("processName"));
            setRequestNodeValueByXPath("/Envelope/Body/stageRoomingListReservationData/request/processName", value);
        }
    }

    public void setTemplateId(String value) {
        try {
            setRequestNodeValueByXPath("/Envelope/Body/stageRoomingListReservationData/request/templateId", value);
        } catch (XPathNotFoundException e) {
            setRequestNodeValueByXPath("/Envelope/Body/stageRoomingListReservationData/request", BaseSoapCommands.ADD_NODE.commandAppend("templateId"));
            setRequestNodeValueByXPath("/Envelope/Body/stageRoomingListReservationData/request/templateId", value);
        }
    }

    public void setReservationNumber(String value) {
        try {
            setRequestNodeValueByXPath("/Envelope/Body/stageRoomingListReservationData/request/roomingListReservationTOList/reservationNumber", value);
        } catch (XPathNotFoundException e) {
            setRequestNodeValueByXPath("/Envelope/Body/stageRoomingListReservationData/request/roomingListReservationTOList", BaseSoapCommands.ADD_NODE.commandAppend("reservationNumber"));
            setRequestNodeValueByXPath("/Envelope/Body/stageRoomingListReservationData/request/roomingListReservationTOList/reservationNumber", value);
        }
    }

    public void setProcessingDate(String value) {
        try {
            setRequestNodeValueByXPath("/Envelope/Body/stageRoomingListReservationData/request/roomingListReservationTOList/processingDate", value);
        } catch (XPathNotFoundException e) {
            setRequestNodeValueByXPath("/Envelope/Body/stageRoomingListReservationData/request/roomingListReservationTOList", BaseSoapCommands.ADD_NODE.commandAppend("processingDate"));
            setRequestNodeValueByXPath("/Envelope/Body/stageRoomingListReservationData/request/roomingListReservationTOList/processingDate", value);
        }
    }

    public void setRowNumber(String value) {
        try {
            setRequestNodeValueByXPath("/Envelope/Body/stageRoomingListReservationData/request/roomingListReservationTOList/rowNumber", value);
        } catch (XPathNotFoundException e) {
            setRequestNodeValueByXPath("/Envelope/Body/stageRoomingListReservationData/request/roomingListReservationTOList", BaseSoapCommands.ADD_NODE.commandAppend("rowNumber"));
            setRequestNodeValueByXPath("/Envelope/Body/stageRoomingListReservationData/request/roomingListReservationTOList/rowNumber", value);
        }
    }

    public void setCheckShared(String value) {
        try {
            setRequestNodeValueByXPath("/Envelope/Body/stageRoomingListReservationData/request/roomingListReservationTOList/checkShared", value);
        } catch (XPathNotFoundException e) {
            setRequestNodeValueByXPath("/Envelope/Body/stageRoomingListReservationData/request/roomingListReservationTOList", BaseSoapCommands.ADD_NODE.commandAppend("checkShared"));
            setRequestNodeValueByXPath("/Envelope/Body/stageRoomingListReservationData/request/roomingListReservationTOList/checkShared", value);
        }
    }

    public void setRenderFlag(String value) {
        try {
            setRequestNodeValueByXPath("/Envelope/Body/stageRoomingListReservationData/request/roomingListReservationTOList/renderFlag", value);
        } catch (XPathNotFoundException e) {
            setRequestNodeValueByXPath("/Envelope/Body/stageRoomingListReservationData/request/roomingListReservationTOList", BaseSoapCommands.ADD_NODE.commandAppend("renderFlag"));
            setRequestNodeValueByXPath("/Envelope/Body/stageRoomingListReservationData/request/roomingListReservationTOList/renderFlag", value);
        }
    }

    public void setCheckReservation(String value) {
        try {
            setRequestNodeValueByXPath("/Envelope/Body/stageRoomingListReservationData/request/roomingListReservationTOList/checkReservation", value);
        } catch (XPathNotFoundException e) {
            setRequestNodeValueByXPath("/Envelope/Body/stageRoomingListReservationData/request/roomingListReservationTOList", BaseSoapCommands.ADD_NODE.commandAppend("checkReservation"));
            setRequestNodeValueByXPath("/Envelope/Body/stageRoomingListReservationData/request/roomingListReservationTOList/checkReservation", value);
        }
    }

    public void setChecked(String value) {
        try {
            setRequestNodeValueByXPath("/Envelope/Body/stageRoomingListReservationData/request/roomingListReservationTOList/checked", value);
        } catch (XPathNotFoundException e) {
            setRequestNodeValueByXPath("/Envelope/Body/stageRoomingListReservationData/request/roomingListReservationTOList", BaseSoapCommands.ADD_NODE.commandAppend("checked"));
            setRequestNodeValueByXPath("/Envelope/Body/stageRoomingListReservationData/request/roomingListReservationTOList/checked", value);
        }
    }

    // Getters

    public String getProcessId() {
        return getResponseNodeValueByXPath("/Envelope/Body/stageRoomingListReservationDataResponse/stageMassOptionsOutput/processId");
    }

    public String getProcessCode() {
        return getResponseNodeValueByXPath("/Envelope/Body/stageRoomingListReservationDataResponse/stageMassOptionsOutput/processCode");
    }
}
