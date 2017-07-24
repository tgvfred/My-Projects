package com.disney.api.soapServices.accommodationModule.accommodationSalesComponentServicePort.operations;

import com.disney.api.soapServices.accommodationModule.accommodationSalesComponentServicePort.AccommodationSalesComponentServicePort;
import com.disney.api.soapServices.core.BaseSoapCommands;
import com.disney.api.soapServices.core.exceptions.XPathNotFoundException;
import com.disney.utils.XMLTools;

public class SearchPackage extends AccommodationSalesComponentServicePort {

    public SearchPackage(String environment) {
        super(environment);

        // Generate a request from a project xml file
        setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("searchPackage")));
        removeComments();
        removeWhiteSpace();
        generateServiceContext();
    }

    public SearchPackage(String environment, String scenario) {
        super(environment);

        // Generate a request from a project xml file
        setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("searchPackage")));
        generateServiceContext();
        // setRequestNodeValueByXPath(getTestScenario("/services/accommodationSalesServicePort/RetrieveSummay/RetrieveSummay.xls", scenario));
        setRequestNodeValueByXPath(getTestScenario(getService(), getOperation(), scenario));
        removeComments();
        removeWhiteSpace();
    }

    // Setters

    public void setBookingDate(String value) {
        try {
            setRequestNodeValueByXPath("/Envelope/Body/searchPackage/request/bookingDate", value);
        } catch (XPathNotFoundException e) {
            setRequestNodeValueByXPath("/Envelope/Body/searchPackage/request", BaseSoapCommands.ADD_NODE.commandAppend("bookingDate"));
            setRequestNodeValueByXPath("/Envelope/Body/searchPackage/request/bookingDate", value);
        }
    }

    public void setPackageCode(String value) {
        try {
            setRequestNodeValueByXPath("/Envelope/Body/searchPackage/request/code", value);
        } catch (XPathNotFoundException e) {
            setRequestNodeValueByXPath("/Envelope/Body/searchPackage/request", BaseSoapCommands.ADD_NODE.commandAppend("code"));
            setRequestNodeValueByXPath("/Envelope/Body/searchPackage/request/code", value);
        }
    }

    public void setPackageDescription(String value) {
        try {
            setRequestNodeValueByXPath("/Envelope/Body/searchPackage/request/description", value);
        } catch (XPathNotFoundException e) {
            setRequestNodeValueByXPath("/Envelope/Body/searchPackage/request", BaseSoapCommands.ADD_NODE.commandAppend("description"));
            setRequestNodeValueByXPath("/Envelope/Body/searchPackage/request/description", value);
        }
    }

    public void setResortArrivalDate(String value) {
        try {
            setRequestNodeValueByXPath("/Envelope/Body/searchPackage/request/resortArrivalDate", value);
        } catch (XPathNotFoundException e) {
            setRequestNodeValueByXPath("/Envelope/Body/searchPackage/request", BaseSoapCommands.ADD_NODE.commandAppend("resortArrivalDate"));
            setRequestNodeValueByXPath("/Envelope/Body/searchPackage/request/resortArrivalDate", value);
        }
    }

    public void setSalesChannelIDs(String value) {
        try {
            setRequestNodeValueByXPath("/Envelope/Body/searchPackage/request/salesChannelIDs", value);
        } catch (XPathNotFoundException e) {
            setRequestNodeValueByXPath("/Envelope/Body/searchPackage/request", BaseSoapCommands.ADD_NODE.commandAppend("salesChannelIDs"));
            setRequestNodeValueByXPath("/Envelope/Body/searchPackage/request/salesChannelIDs", value);
        }
    }

    // Getters
    public String getPackageCode() {
        return getResponseNodeValueByXPath("/Envelope/Body/searchPackageResponse/return/code");
    }

    public String getPackageDescription() {
        return getResponseNodeValueByXPath("/Envelope/Body/searchPackageResponse/return/description");
    }

    int index = getNumberOfRequestNodesByXPath("/Envelope/Body/searchPackageResponse/return/description") + 1;

    public String getPackageDescriptionByPackageCode(String pkgCode) {
        return getResponseNodeValueByXPath("/Envelope/Body/searchPackageResponse/return/code[text()='" + pkgCode + "']/../description");
    }
}
