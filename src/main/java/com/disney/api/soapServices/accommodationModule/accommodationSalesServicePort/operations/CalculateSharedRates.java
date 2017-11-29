package com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations;

import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.AccommodationSalesServicePort;
import com.disney.utils.XMLTools;

public class CalculateSharedRates extends AccommodationSalesServicePort {

    public CalculateSharedRates(String environment, String scenario) {
        super(environment);

        // Generate a request from a project xml file
        setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("calculateSharedRates")));

        generateServiceContext();
        setRequestNodeValueByXPath(getTestScenario(getService(), getOperation(), scenario));
        removeComments();
        removeWhiteSpace();
    }

    public String getpackageCode() {
        return getResponseNodeValueByXPath("/Envelope/Body/calculateSharedRatesResponse/splitRateWithTotalTO/accommodations/packageCode");
    }

    public String getguaranteeStatus() {
        return getResponseNodeValueByXPath("/Envelope/Body/calculateSharedRatesResponse/splitRateWithTotalTO/accommodations/guaranteeStatus");
    }

    public void setTcgID(String value) {

        setRequestNodeValueByXPath("/Envelope/Body/calculateSharedRates/request/accommodations/travelComponentGroupingId", value);
    }

    public void setTcID(String value) {

        setRequestNodeValueByXPath("/Envelope/Body/calculateSharedRates/request/accommodations/travelComponentId", value);
    }

    public void setShared(String value) {

        setRequestNodeValueByXPath("/Envelope/Body/calculateSharedRates/request/accommodations/shared", value);
    }

    public String getShared() {

        return getResponseNodeValueByXPath("/Envelope/Body/calculateSharedRatesResponse/splitRateWithTotalTO/accommodations/shared");
    }

    public String getRateDetailsAccommOne() {

        return getResponseNodeValueByXPath("/Envelope/Body/calculateSharedRatesResponse/splitRateWithTotalTO/accommodations/rateDetails/basePrice");
    }

    public String getRateDetailsAccommTwo() {

        return getResponseNodeValueByXPath("/Envelope/Body/calculateSharedRatesResponse//splitRateWithTotalTO/accommodations[2]/rateDetails/basePrice");
    }

    public String getTotalRateAmount() {

        return getResponseNodeValueByXPath("/Envelope/Body/calculateSharedRatesResponse/splitRateWithTotalTO/totalRateAmount/rate/amount");
    }

}
