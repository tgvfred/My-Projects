package com.disney.api.soapServices.accommodationModule.accommodationSalesComponentServicePort.operations;

import com.disney.api.soapServices.accommodationModule.accommodationSalesComponentServicePort.AccommodationSalesComponentServicePort;
import com.disney.utils.XMLTools;
import com.disney.utils.exceptions.NoDataFromVirtualTableException;

public class Cancel extends AccommodationSalesComponentServicePort {
    public Cancel(String environment) {
        super(environment);

        setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("cancel")));
        removeComments();
        removeWhiteSpace();
        generateServiceContext();
    }

    public Cancel(String environment, String scenario) {
        this(environment);
        try {
            setRequestNodeValueByXPath(getTestScenario(getService(), getOperation(), scenario));
        } catch (NoDataFromVirtualTableException e) {
            setEnvironmentServiceURL("AccommodationSalesComponentServicePort", environment);
            setRequestNodeValueByXPath(getTestScenario(getService(), getOperation(), scenario));
        }
    }

    /*
     * Request Setters
     */
    public void setCancelDate(String value) {
        setRequestNodeValueByXPath("/Envelope/Body/cancel/request/cancelDate", value);
    }

    public void setTravelComponentGroupingId(String value) {
        setRequestNodeValueByXPath("/Envelope/Body/cancel/request/travelComponentGroupingId", value);
    }

    /*
     * Response Getters
     */
    public String getCancellationNumber() {
        return getResponseNodeValueByXPath("/Envelope/Body/cancelResponse/response/cancellationNumber");
    }

    public String getTravelPlanId() {
        return getResponseNodeValueByXPath("/Envelope/Body/cancelResponse/response/travelPlanId");
    }
}
