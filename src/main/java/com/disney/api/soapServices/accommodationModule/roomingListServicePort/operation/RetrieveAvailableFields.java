package com.disney.api.soapServices.accommodationModule.roomingListServicePort.operation;

import com.disney.api.soapServices.accommodationModule.roomingListServicePort.RoomingListServicePort;
import com.disney.utils.XMLTools;

public class RetrieveAvailableFields extends RoomingListServicePort {
    public RetrieveAvailableFields(String environment) {
        super(environment);

        // Generate a request from a project xml file
        setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("retrieveAvailableFields")));
        generateServiceContext();
        removeComments();
        removeWhiteSpace();
    }
}
