package com.disney.api.soapServices.accommodationModule.accommodationBatchServicePort.operation;

import com.disney.api.soapServices.accommodationModule.accommodationBatchServicePort.AccommodationBatchServicePort;
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

}
