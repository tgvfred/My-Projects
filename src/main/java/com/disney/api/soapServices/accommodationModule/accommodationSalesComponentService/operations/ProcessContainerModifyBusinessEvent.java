package com.disney.api.soapServices.accommodationModule.accommodationSalesComponentService.operations;

import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.AccommodationSalesServicePort;
import com.disney.utils.XMLTools;

public class ProcessContainerModifyBusinessEvent extends AccommodationSalesServicePort {
    public ProcessContainerModifyBusinessEvent(String environment, String scenario) {
        super(environment);

        // Generate a request from a project xml file
        setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("processContainerModifyBusinessEvent")));
        generateServiceContext();
        setRequestNodeValueByXPath(getTestScenario(getService(), getOperation(), scenario));
        removeComments();
        removeWhiteSpace();
    }

    public ProcessContainerModifyBusinessEvent(String environment) {
        super(environment);

        // Generate a request from a project xml file
        setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("processContainerModifyBusinessEvent")));
        generateServiceContext();

        removeComments();
        removeWhiteSpace();
    }

    public void setTravelPlanSegmentID(String value) {
        setRequestNodeValueByXPath("/Envelope/Body/processContainerModifyBusinessEvent/request/travelPlanSegmentId", value);

    }

    public void setByPassFreeze(String value) {
        setRequestNodeValueByXPath("/Envelope/Body/processContainerModifyBusinessEvent/request/byPassFreeze", value);

    }

    public void setExternalReferenceType(String value) {
        setRequestNodeValueByXPath("/Envelope/Body/processContainerModifyBusinessEvent/request/externalReferenceDetail/externalReferenceType", value);

    }

    public void setExternalReferenceCode(String value) {
        setRequestNodeValueByXPath("/Envelope/Body/processContainerModifyBusinessEvent/request/externalReferenceDetail/externalReferenceCode", value);

    }

    public void setExternalReferenceSource(String value) {
        setRequestNodeValueByXPath("/Envelope/Body/processContainerModifyBusinessEvent/request/externalReferenceDetail/externalReferenceSource", value);

    }

    public void setExternalReferenceNumber(String value) {
        setRequestNodeValueByXPath("/Envelope/Body/processContainerModifyBusinessEvent/request/externalReferenceDetail/externalReferenceNumber", value);

    }

    public void setAttemptAutoReinstate(String value) {
        setRequestNodeValueByXPath("/Envelope/Body/processContainerModifyBusinessEvent/request/attemptAutoReinstate", value);

    }

}
