package com.disney.api.soapServices.accommodationModule.accommodationBatchServicePort.operation;

import com.disney.api.soapServices.accommodationModule.accommodationBatchServicePort.AccommodationBatchServicePort;
import com.disney.utils.XMLTools;

public class RetrieveAvailableFields extends AccommodationBatchServicePort {
    public RetrieveAvailableFields(String environment) {
        super(environment);

        // Generate a request from a project xml file
        setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("retrieveAvailableFields")));
        generateServiceContext();
        removeComments();
        removeWhiteSpace();
    }
}
