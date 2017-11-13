package com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations;

import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.AccommodationSalesServicePort;
import com.disney.utils.XMLTools;

public class RemoveRateOverride extends AccommodationSalesServicePort {

    public RemoveRateOverride(String environment, String scenario) {
        super(environment);

        // Generate a request from a project xml file
        setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("removeRateOverride")));

        generateServiceContext();
        setRequestNodeValueByXPath(getTestScenario(getService(), getOperation(), scenario));

        removeComments();
        removeWhiteSpace();
    }

    public void settravelPlanSegmentId(String travelPlanSegmentId) {
        setRequestNodeValueByXPath("/*[local-name(.)='Envelope'][1]/*[local-name(.)='Body'][1]/*[local-name(.)='removeRateOverride'][1]/*[local-name(.)='request'][1]/*[local-name(.)='travelPlanSegmentId'][1]", travelPlanSegmentId);
    }

    public void setaccomComponentId(String accomComponentId) {
        setRequestNodeValueByXPath("/*[local-name(.)='Envelope'][1]/*[local-name(.)='Body'][1]/*[local-name(.)='removeRateOverride'][1]/*[local-name(.)='request'][1]/*[local-name(.)='accomComponentId'][1]", accomComponentId);
    }

    public void setlocationId(String locationId) {
        setRequestNodeValueByXPath("/*[local-name(.)='Envelope'][1]/*[local-name(.)='Body'][1]/*[local-name(.)='removeRateOverride'][1]/*[local-name(.)='request'][1]/*[local-name(.)='locationId'][1]", locationId);
    }
}
