package com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations;

import java.util.AbstractMap.SimpleImmutableEntry;
import java.util.HashSet;
import java.util.Map.Entry;
import java.util.Set;

import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.AccommodationSalesServicePort;
import com.disney.api.soapServices.core.BaseSoapCommands;
import com.disney.utils.XMLTools;

public class GetOptionsByFilter extends AccommodationSalesServicePort {
    public GetOptionsByFilter(String environment) {
        super(environment);

        setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("getOptionsByFilter")));
        generateServiceContext();
        removeComments();
        removeWhiteSpace();

        setRequestNodeValueByXPath("/Envelope/Body/getOptionsByFilter/optionFilters", BaseSoapCommands.REMOVE_NODE.toString());
    }

    public void setAccommodationSalesOptionsEnum(String value) {
        setRequestNodeValueByXPath("/Envelope/Body/getOptionsByFilter/accommodationSalesOptionsEnum", value);
    }

    public void addOptionFilters(String key, String value) {
        int index = getNumberOfRequestNodesByXPath("/Envelope/Body/getOptionsByFilter/optionFilters") + 1;

        setRequestNodeValueByXPath("/Envelope/Body/getOptionsByFilter", BaseSoapCommands.ADD_NODE.commandAppend("optionFilters"));
        if (key != null) {
            setRequestNodeValueByXPathAndAddNode("/Envelope/Body/getOptionsByFilter/optionFilters[" + index + "]/key", key);
        }
        if (value != null) {
            setRequestNodeValueByXPathAndAddNode("/Envelope/Body/getOptionsByFilter/optionFilters[" + index + "]/value", value);
        }
    }

    public Set<Entry<String, String>> getResponseOptionKeyValuePairs() {
        Set<Entry<String, String>> toreturn = new HashSet<>();
        for (int i = getNumberOfResponseNodesByXPath("/Envelope/Body/getOptionsByFilterResponse/response"); i > 0; i--) {
            toreturn.add(new SimpleImmutableEntry<String, String>(
                    getResponseNodeValueByXPath("/Envelope/Body/getOptionsByFilterResponse/response[" + i + "]/optionKey"),
                    getResponseNodeValueByXPath("/Envelope/Body/getOptionsByFilterResponse/response[" + i + "]/optionValue")));
        }
        return toreturn;
    }
}
