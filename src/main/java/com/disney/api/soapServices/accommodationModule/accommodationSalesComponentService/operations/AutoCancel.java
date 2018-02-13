package com.disney.api.soapServices.accommodationModule.accommodationSalesComponentService.operations;

import com.disney.api.soapServices.accommodationModule.accommodationSalesComponentService.AccommodationSalesComponentService;
import com.disney.utils.XMLTools;

/**
 * @deprecated deprecated in 8.5. Now under AccommodationSalesBatchService
 * @author phlej001
 *
 */
@Deprecated
public class AutoCancel extends AccommodationSalesComponentService {

    public AutoCancel(String environment, String scenario) {
        super(environment);

        // Generate a request from a project xml file
        setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("autoCancel")));

        setRequestNodeValueByXPath(getTestScenario(getService(), getOperation(), scenario));
        removeComments();
        removeWhiteSpace();
    }

    public AutoCancel(String environment) {
        super(environment);

        // Generate a request from a project xml file
        setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("autoCancel")));

        removeComments();
        removeWhiteSpace();
    }

    public void setTravelComponentGroupingId(String value) {
        setRequestNodeValueByXPath("/Envelope/Body/autoCancel/travelComponentGroupingId", value);
    }
}
