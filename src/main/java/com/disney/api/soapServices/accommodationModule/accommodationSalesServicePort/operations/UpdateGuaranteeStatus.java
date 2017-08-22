package com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations;

import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.AccommodationSalesServicePort;
import com.disney.utils.XMLTools;

public class UpdateGuaranteeStatus extends AccommodationSalesServicePort {

    public UpdateGuaranteeStatus(String environment) {
        super(environment);

        // Generate a request from a project xml file
        setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("updateGuaranteeStatus")));
        removeComments();
        removeWhiteSpace();
        generateServiceContext();
    }

    public UpdateGuaranteeStatus(String environment, String scenario) {
        super(environment);

        // Generate a request from a project xml file
        setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("updateGuaranteeStatus")));
        generateServiceContext();
        // setRequestNodeValueByXPath(getTestScenario("/services/accommodationSalesServicePort/RetrieveSummay/RetrieveSummay.xls", scenario));
        setRequestNodeValueByXPath(getTestScenario(getService(), getOperation(), scenario));
        removeComments();
        removeWhiteSpace();
    }

    // Setters
    public void setRequestTravelComponentGroupingId(String tcgId) {
        setRequestNodeValueByXPath("/Envelope/Body/updateGuaranteeStatus/request/travelComponentGroupingId", tcgId);
    }

    public void setRequestguaranteedByEnum(String Genum) {
        setRequestNodeValueByXPath("/Envelope/Body/updateGuaranteeStatus/request/guaranteedByEnum", Genum);
    }

    // Getters

    public String getStatus() {
        return getResponseNodeValueByXPath("/Envelope/Body/retrieveSummaryResponse/accommodationsSummaryDetails/accommodationDetail/status");
    }

}
