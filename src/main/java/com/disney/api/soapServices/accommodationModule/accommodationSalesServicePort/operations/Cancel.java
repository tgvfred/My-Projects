package com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations;

import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.AccommodationSalesServicePort;
import com.disney.api.soapServices.core.BaseSoapCommands;
import com.disney.api.soapServices.core.exceptions.XPathNotFoundException;
import com.disney.utils.XMLTools;

public class Cancel extends AccommodationSalesServicePort {
    public Cancel(String environment, String scenario) {
        super(environment);

        // Generate a request from a project xml file
        setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("cancel")));

        generateServiceContext();
        setRequestNodeValueByXPath(getTestScenario(getService(), getOperation(), scenario));
        removeComments();
        removeWhiteSpace();

    }

    public void setCancelDate(String value) {
        setRequestNodeValueByXPath("/Envelope/Body/cancel/request/cancelDate", value);
    }

    public void setTravelComponentGroupingId(String value) {
        setRequestNodeValueByXPath("/Envelope/Body/cancel/request/travelComponentGroupingId", value);
    }

    public String getCancellationNumber() {
        return getResponseNodeValueByXPath("/Envelope/Body/cancelResponse/response/cancellationNumber");
    }

    public String getTravelPlanId() {
        return getResponseNodeValueByXPath("/Envelope/Body/cancelResponse/response/travelPlanId");
    }

    public void setRequest(String value) {
        setRequestNodeValueByXPath("/Envelope/Body/cancel/request", value);
    }

    public void setExternalReferenceCode(String value) {
        try {
            setRequestNodeValueByXPath("/Envelope/Body/cancel/request/externalReferenceDetail/externalReferenceCode", value);
        } catch (XPathNotFoundException e) {
            setRequestNodeValueByXPath("/Envelope/Body/cancel/request/externalReferenceDetail", BaseSoapCommands.ADD_NODE.commandAppend("externalReferenceCode"));
            setRequestNodeValueByXPath("/Envelope/Body/cancel/request/externalReferenceDetail/externalReferenceCode", value);
        }
    }

    public void setExternalReferenceType(String value) {
        try {
            setRequestNodeValueByXPath("/Envelope/Body/cancel/request/externalReferenceDetail/externalReferenceType", value);
        } catch (XPathNotFoundException e) {
            setRequestNodeValueByXPath("/Envelope/Body/cancel/request/externalReferenceDetail", BaseSoapCommands.ADD_NODE.commandAppend("externalReferenceType"));
            setRequestNodeValueByXPath("/Envelope/Body/cancel/request/externalReferenceDetail/externalReferenceType", value);
        }
    }

    public void setExternalReferenceNumber(String value) {
        try {
            setRequestNodeValueByXPath("/Envelope/Body/cancel/request/externalReferenceDetail/externalReferenceNumber", value);
        } catch (XPathNotFoundException e) {
            setRequestNodeValueByXPath("/Envelope/Body/cancel/request/externalReferenceDetail", BaseSoapCommands.ADD_NODE.commandAppend("externalReferenceNumber"));
            setRequestNodeValueByXPath("/Envelope/Body/cancel/request/externalReferenceDetail/externalReferenceNumber", value);
        }
    }

    public void setExternalReferenceSource(String value) {
        try {
            setRequestNodeValueByXPath("/Envelope/Body/cancel/request/externalReferenceDetail/externalReferenceSource", value);
        } catch (XPathNotFoundException e) {
            setRequestNodeValueByXPath("/Envelope/Body/cancel/request/externalReferenceDetail", BaseSoapCommands.ADD_NODE.commandAppend("externalReferenceSource"));
            setRequestNodeValueByXPath("/Envelope/Body/cancel/request/externalReferenceDetail/externalReferenceSource", value);
        }
    }

}
