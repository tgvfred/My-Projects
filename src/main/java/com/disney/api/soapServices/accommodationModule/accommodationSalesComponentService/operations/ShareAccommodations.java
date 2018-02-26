package com.disney.api.soapServices.accommodationModule.accommodationSalesComponentService.operations;

import com.disney.api.soapServices.accommodationModule.accommodationSalesComponentService.AccommodationSalesComponentService;
import com.disney.utils.XMLTools;

public class ShareAccommodations extends AccommodationSalesComponentService {

    public ShareAccommodations(String environment) {
        super(environment);

        // Generate a request from a project xml file
        setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("shareAccommodations")));
        removeComments();
        removeWhiteSpace();
        generateServiceContext();
    }

    public ShareAccommodations(String environment, String scenario) {
        super(environment);

        // Generate a request from a project xml file
        setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("shareAccommodations")));
        generateServiceContext();
        // setRequestNodeValueByXPath(getTestScenario("/services/accommodationSalesServicePort/RetrieveSummay/RetrieveSummay.xls", scenario));
        setRequestNodeValueByXPath(getTestScenario(getService(), getOperation(), scenario));
        removeComments();
        removeWhiteSpace();
    }

}
