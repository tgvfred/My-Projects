package com.disney.api.soapServices.accommodationModule.accommodationSalesComponentService.operations;

import com.disney.api.soapServices.accommodationModule.accommodationSalesComponentService.AccommodationSalesComponentService;
import com.disney.utils.XMLTools;

public class ValidateShareRules extends AccommodationSalesComponentService {

    public ValidateShareRules(String environment, String scenario) {
        super(environment);
        setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("validateShareRules")));
        generateServiceContext();
        setRequestNodeValueByXPath(getTestScenario(getService(), getOperation(), scenario));
        removeComments();
        removeWhiteSpace();
    }

}
