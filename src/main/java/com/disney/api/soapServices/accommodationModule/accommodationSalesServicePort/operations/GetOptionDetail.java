package com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations;

import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.AccommodationSalesServicePort;
import com.disney.utils.XMLTools;

public class GetOptionDetail extends AccommodationSalesServicePort {
    public GetOptionDetail(String environment, String scenario) {
        super(environment);

        // Generate a request from a project xml file
        setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("getOptionDetail")));
        generateServiceContext();
        setRequestNodeValueByXPath(getTestScenario(getService(), getOperation(), scenario));
        removeComments();
        removeWhiteSpace();

    }

    public GetOptionDetail(String environment) {
        super(environment);
        setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("getOptionDetail")));
        removeComments();
        removeWhiteSpace();
        generateServiceContext();
    }

    // setters

    public void setOptionKeyVal(String optionKeyVal) {

        setRequestNodeValueByXPath("/Envelope/Body/getOptionDetail/optionKeyVal", optionKeyVal);

    }

    public void setAccommodationSalesOptionsEnum(String accommodationSalesOptionsEnum) {

        setRequestNodeValueByXPath("/Envelope/Body/getOptionDetail/accommodationSalesOptionsEnum", accommodationSalesOptionsEnum);

    }

    public void setOptionType(String optionType) {

        setRequestNodeValueByXPath("/Envelope/Body/getOptions/optionType", optionType);
    }

    public String getOptionKey() {

        return getResponseNodeValueByXPath("/Envelope/Body/getOptionDetailResponse/response/optionKey");
    }

    public String getOptionValue() {

        return getResponseNodeValueByXPath("/Envelope/Body/getOptionDetailResponse/response/optionValue");
    }
}
// going to add to this