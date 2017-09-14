package com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations;

import java.util.HashMap;
import java.util.Map;

import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.AccommodationSalesServicePort;
import com.disney.api.soapServices.core.exceptions.XPathNotFoundException;
import com.disney.utils.XMLTools;

public class GetOptions extends AccommodationSalesServicePort {
    public GetOptions(String environment, String scenario) {
        super(environment);

        // Generate a request from a project xml file
        setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("getOptions")));
        generateServiceContext();
        setRequestNodeValueByXPath(getTestScenario(getService(), getOperation(), scenario));
        removeComments();
        removeWhiteSpace();

    }

    public void setOptionType(String optionType) {

        setRequestNodeValueByXPath("/Envelope/Body/getOptions/optionType", optionType);
    }

    public void setOptionEnum(String value) {
        setRequestNodeValueByXPath("/Envelope/Body/getOptions/accommodationSalesOptionsEnum", value);
    }

    public String getoptionKey() {
        return getResponseNodeValueByXPath("/Envelope/Body/getOptionsResponse/response[1]/optionKey");
    }

    public String getoptionValue() {
        return getResponseNodeValueByXPath("/Envelope/Body/getOptionsResponse/response[1]/optionValue");
    }

    public Map<String, String> getKeyValuePairs() {
        Map<String, String> keyValuePairs = new HashMap<String, String>();
        int numberOfKeyValuePairs = getNumberOfKeyValuePairs();
        for (int i = 0; i < numberOfKeyValuePairs; i++) {
            keyValuePairs.put(getResponseNodeValueByXPath("/Envelope/Body/getOptionsResponse/response[" + String.valueOf(i + 1) + "]/optionKey"), getResponseNodeValueByXPath("/Envelope/Body/getOptionsResponse/response[" + String.valueOf(i + 1) + "]/optionValue"));
        }

        return keyValuePairs;
    }

    public int getNumberOfKeyValuePairs() {
        int numberOfKeyValuePairs = 0;
        try {
            return XMLTools.getNodeList(getResponseDocument(), "/Envelope/Body/getOptionsResponse/response").getLength();
        } catch (XPathNotFoundException e) {
        }
        return numberOfKeyValuePairs;
    }
}