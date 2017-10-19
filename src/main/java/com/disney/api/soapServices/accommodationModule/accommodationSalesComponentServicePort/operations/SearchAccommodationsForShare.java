package com.disney.api.soapServices.accommodationModule.accommodationSalesComponentServicePort.operations;

import com.disney.api.soapServices.accommodationModule.accommodationSalesComponentServicePort.AccommodationSalesComponentServicePort;
import com.disney.utils.XMLTools;

public class SearchAccommodationsForShare extends AccommodationSalesComponentServicePort {

    public SearchAccommodationsForShare(String environment, String scenario) {
        super(environment);

        // Generate a request from a project xml file
        setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("searchAccommodationsForShare")));

        setRequestNodeValueByXPath(getTestScenario(getService(), getOperation(), scenario));
        removeComments();
        removeWhiteSpace();
    }

    /Envelope/Body/searchAccommodationsForShare/request/travelPlanViewTO/arrivalDate

}
