package com.disney.api.soapServices.accommodationModule.accommodationSalesComponentService.operations;

import com.disney.api.soapServices.accommodationModule.accommodationSalesComponentService.AccommodationSalesComponentService;
import com.disney.utils.XMLTools;

public class AutoReinstate extends AccommodationSalesComponentService {

    public AutoReinstate(String environment, String scenario) {
        super(environment);

        // Generate a request from a project xml file
        setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("autoReinstate")));

        setRequestNodeValueByXPath(getTestScenario(getService(), getOperation(), scenario));
        removeComments();
        removeWhiteSpace();
    }

    public AutoReinstate(String environment) {
        super(environment);

        // Generate a request from a project xml file
        setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("autoReinstate")));

        removeComments();
        removeWhiteSpace();
    }

    // setters
    public void setSalesChannel(String value) {
        setRequestNodeValueByXPath("/Envelope/Body/autoReinstate/request/salesChannel", value);
    }

    public void setCommunicationChannel(String value) {
        setRequestNodeValueByXPath("/Envelope/Body/autoReinstate/request/communicationChannel", value);
    }

    public void setFreezeId(String value) {
        setRequestNodeValueByXPath("/Envelope/Body/autoReinstate/request/roomDetail/freezeId", value);
    }

    public void setTravelComponentGroupingId(String value) {
        setRequestNodeValueByXPath("/Envelope/Body/autoReinstate/request/roomDetail/travelComponentGroupingId", value);
    }

    public void setInventoryOverrideReason(String value) {
        setRequestNodeValueByXPath("/Envelope/Body/autoReinstate/request/roomDetail/inventoryOverrideReason", value);
    }

    public void setInventoryOverrideContactName(String value) {
        setRequestNodeValueByXPath("/Envelope/Body/autoReinstate/request/roomDetail/inventoryOverrideContactName", value);
    }

}
