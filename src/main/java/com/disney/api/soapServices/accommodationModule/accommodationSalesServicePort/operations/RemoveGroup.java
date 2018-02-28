
package com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations;

import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.AccommodationSalesServicePort;
import com.disney.api.soapServices.core.BaseSoapCommands;
import com.disney.api.soapServices.core.exceptions.XPathNotFoundException;
import com.disney.utils.XMLTools;

public class RemoveGroup extends AccommodationSalesServicePort {

    public RemoveGroup(String environment) {
        super(environment);

        // Generate a request from a project xml file
        setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("removeGroup")));

        generateServiceContext();
        removeComments();
        removeWhiteSpace();
    }

    public void setTravelComponentGroupingId(String value) {
        try {
            setRequestNodeValueByXPath("/Envelope/Body/removeGroup/request/travelComponentGroupingId", value);
        } catch (XPathNotFoundException e) {
            setRequestNodeValueByXPath("/Envelope/Body/removeGroup/request", BaseSoapCommands.ADD_NODE.commandAppend("travelComponentGroupingId"));
            setRequestNodeValueByXPath("/Envelope/Body/removeGroup/request/travelComponentGroupingId", value);
        }
    }

    public void setExternalReferenceInfo(String externalReferenceCode, String externalReferenceNumber, String externalReferenceSource) {
        setRequestNodeValueByXPath("/Envelope/Body/removeGroup/request/externalReference/externalReferenceCode", externalReferenceCode);
        setRequestNodeValueByXPath("/Envelope/Body/removeGroup/request/externalReference/externalReferenceNumber", externalReferenceNumber);
        setRequestNodeValueByXPath("/Envelope/Body/removeGroup/request/externalReference/externalReferenceSource", externalReferenceSource);
        setRequestNodeValueByXPath("/Envelope/Body/removeGroup/request/externalReference/externalReferenceType", BaseSoapCommands.REMOVE_NODE.toString());
    }

}
