package com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations;

import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.AccommodationSalesServicePort;
import com.disney.utils.XMLTools;

/**
 * @deprecated deprecated in 8.5
 * @author phlej001
 *
 */
@Deprecated
public class ReplaceGuestId extends AccommodationSalesServicePort {

    public ReplaceGuestId(String environment, String scenario) {
        super(environment);
        // Generate a request from a project xml file
        setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("replaceGuestId")));
        generateServiceContext();
        setRequestNodeValueByXPath(getTestScenario(getService(), getOperation(), scenario));
        removeComments();
        removeWhiteSpace();
    }

    public void setguestId(String guestId) {
        setRequestNodeValueByXPath("/Envelope/Body/replaceGuestId/transactionGuestId", guestId);
    }
}