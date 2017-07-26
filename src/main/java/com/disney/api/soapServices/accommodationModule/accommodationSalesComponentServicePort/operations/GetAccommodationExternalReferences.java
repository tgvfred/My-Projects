package com.disney.api.soapServices.accommodationModule.accommodationSalesComponentServicePort.operations;

import com.disney.api.soapServices.accommodationModule.accommodationSalesComponentServicePort.AccommodationSalesComponentServicePort;
import com.disney.api.soapServices.core.BaseSoapCommands;
import com.disney.api.soapServices.core.exceptions.XPathNotFoundException;
import com.disney.utils.XMLTools;

public class GetAccommodationExternalReferences extends AccommodationSalesComponentServicePort {

    public GetAccommodationExternalReferences(String environment) {
        super(environment);

        // Generate a request from a project xml file
        setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("getAccommodationExternalReferences")));
        removeComments();
        removeWhiteSpace();
        generateServiceContext();
    }

    public GetAccommodationExternalReferences(String environment, String scenario) {
        super(environment);

        // Generate a request from a project xml file
        setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("getAccommodationExternalReferences")));
        generateServiceContext();
        // setRequestNodeValueByXPath(getTestScenario("/services/accommodationSalesServicePort/RetrieveSummay/RetrieveSummay.xls", scenario));
        setRequestNodeValueByXPath(getTestScenario(getService(), getOperation(), scenario));
        removeComments();
        removeWhiteSpace();
    }

    // Setters

    public void setTravelPlanSegmentId(String value) {
        try {
            setRequestNodeValueByXPath("/Envelope/Body/getAccommodationExternalReferences/request/travelPlanSegmentId", value);
        } catch (XPathNotFoundException e) {
            setRequestNodeValueByXPath("/Envelope/Body/getAccommodationExternalReferences/request", BaseSoapCommands.ADD_NODE.commandAppend("travelPlanSegmentId"));
            setRequestNodeValueByXPath("/Envelope/Body/getAccommodationExternalReferences/request/travelPlanSegmentId", value);
        }
    }

    public void setTravelComponentGroupingId(String value) {
        try {
            setRequestNodeValueByXPath("/Envelope/Body/getAccommodationExternalReferences/request/travelComponentGroupingId", value);
        } catch (XPathNotFoundException e) {
            setRequestNodeValueByXPath("/Envelope/Body/getAccommodationExternalReferences/request", BaseSoapCommands.ADD_NODE.commandAppend("travelComponentGroupingId"));
            setRequestNodeValueByXPath("/Envelope/Body/getAccommodationExternalReferences/request/travelComponentGroupingId", value);
        }
    }

    // Getters

    public String getExternalReferenceNumber() {
        return getResponseNodeValueByXPath("/Envelope/Body/getAccommodationExternalReferencesResponse/return/externalReferenceNumber");
    }

    int index = getNumberOfRequestNodesByXPath("/Envelope/Body/getAccommodationExternalReferencesResponse/return/externalReferenceNumber") + 1;

    public String getExternalReferenceNumberByIndex(String index) {
        return getResponseNodeValueByXPath("/Envelope/Body/getAccommodationExternalReferencesResponse/return[" + index + "]/externalReferenceNumber");
    }

    public String getExternalReferenceSource() {
        return getResponseNodeValueByXPath("/Envelope/Body/getAccommodationExternalReferencesResponse/return/externalReferenceSource");
    }

    int index2 = getNumberOfRequestNodesByXPath("/Envelope/Body/getAccommodationExternalReferencesResponse/return/externalReferenceSource") + 1;

    public String getExternalReferenceSourceByIndex(String index2) {
        return getResponseNodeValueByXPath("/Envelope/Body/getAccommodationExternalReferencesResponse/return[" + index2 + "]/externalReferenceSource");
    }
}
