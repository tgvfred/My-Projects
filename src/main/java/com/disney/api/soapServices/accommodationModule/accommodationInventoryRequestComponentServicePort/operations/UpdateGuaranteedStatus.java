package com.disney.api.soapServices.accommodationModule.accommodationInventoryRequestComponentServicePort.operations;

import com.disney.api.soapServices.accommodationModule.accommodationInventoryRequestComponentServicePort.AccommodationInventoryRequestComponentServicePort;
import com.disney.utils.XMLTools;

public class UpdateGuaranteedStatus extends AccommodationInventoryRequestComponentServicePort {

    public UpdateGuaranteedStatus(String environment, String scenario) {
        super(environment);

        // Generate a request from a project xml file
        setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("updateGuaranteedStatus")));

        generateServiceContext();
        setRequestNodeValueByXPath(getTestScenario(getService(), getOperation(), scenario));

        removeComments();
        removeWhiteSpace();
    }

    public UpdateGuaranteedStatus(String environment) {
        super(environment);

        // Generate a request from a project xml file
        setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("updateGuaranteedStatus")));

        generateServiceContext();

        removeComments();
        removeWhiteSpace();
    }
    // hmm

    public void setRequest(String value) {
        setRequestNodeValueByXPath("/Envelope/Body/updateGuaranteedStatus/request", value);

    }

    public void setGuaranteedStatusFlag(String value) {

        setRequestNodeValueByXPath("/Envelope/Body/updateGuaranteedStatus/request/guaranteedStatusFlag", value);

    }

    public void setOwnerReferenceNumber(String value) {

        setRequestNodeValueByXPath("/Envelope/Body/updateGuaranteedStatus/request/ownerReferenceNumber", value);

    }

    public void setOwnerReferenceType(String value) {

        setRequestNodeValueByXPath("/Envelope/Body/updateGuaranteedStatus/request/ownerReferenceType", value);
    }

    public String getAssignmentOwnerId() {

        return getResponseNodeValueByXPath("/Envelope/Body/updateGuaranteedStatusResponse/return/assignmentOwnerId");
    }
}
