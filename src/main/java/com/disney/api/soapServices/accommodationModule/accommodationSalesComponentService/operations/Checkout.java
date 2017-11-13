package com.disney.api.soapServices.accommodationModule.accommodationSalesComponentService.operations;

import com.disney.api.soapServices.accommodationModule.accommodationSalesComponentService.AccommodationSalesComponentService;
import com.disney.utils.XMLTools;

public class Checkout extends AccommodationSalesComponentService {

    public Checkout(String environment, String scenario) {
        super(environment);

        // Generate a request from a project xml file
        setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("checkout")));

        setRequestNodeValueByXPath(getTestScenario(getService(), getOperation(), scenario));
        removeComments();
        removeWhiteSpace();
    }

    // Getters
    public String getEarlyCheckoutReason() {
        return getResponseNodeValueByXPath("/Envelope/Body/checkout/request/earlyCheckOutReasonCode");
    }

    public String getIsBellServiceRequired() {
        return getResponseNodeValueByXPath("/Envelope/Body/checkout/request/isBellServiceRequired");
    }

    public String getIsSameRoomNumberAssigned() {
        return getResponseNodeValueByXPath("/Envelope/Body/checkout/request/isSameRoomNumberAssigned");
    }

    public String getTravelComponentGroupingId() {
        return getResponseNodeValueByXPath("/Envelope/Body/checkout/request/travelComponentGroupingId");
    }

    public String getExternalReferenceType() {
        return getResponseNodeValueByXPath("/Envelope/Body/checkout/request/externalReferenceDetail/externalReferenceType");
    }

    public String getExternalReferenceCode() {
        return getResponseNodeValueByXPath("/Envelope/Body/checkout/request/externalReferenceDetail/externalReferenceCode");
    }

    public String getExternalReferenceNumber() {
        return getResponseNodeValueByXPath("/Envelope/Body/checkout/request/externalReferenceDetail/externalReferenceNumber");
    }

    public String getExternalReferenceSource() {
        return getResponseNodeValueByXPath("/Envelope/Body/checkout/request/externalReferenceDetail/externalReferenceSource");
    }

    public String getCheckoutDate() {
        return getResponseNodeValueByXPath("/Envelope/Body/checkout/request/checkoutDate");
    }

    public String getLocationId() {
        return getResponseNodeValueByXPath("/Envelope/Body/checkout/request/locationId");
    }

    // Setters
    public void setEarlyCheckOutReason(String value) {
        setRequestNodeValueByXPath("/Envelope/Body/checkout/request/earlyCheckOutReasonCode", value);
    }

    public void setIsBellServiceRequired(String value) {
        setRequestNodeValueByXPath("/Envelope/Body/checkout/request/isBellServiceRequired", value);
    }

    public void setIsSameRoomNumberAssigned(String value) {
        setRequestNodeValueByXPath("/Envelope/Body/checkout/request/isSameRoomNumberAssigned", value);
    }

    public void setTravelComponentGroupingId(String value) {
        setRequestNodeValueByXPath("/Envelope/Body/checkout/request/travelComponentGroupingId", value);
    }

    public void setExternalReferenceType(String value) {
        setRequestNodeValueByXPath("/Envelope/Body/checkout/request/externalReferenceDetail/externalReferenceType", value);
    }

    public void setExternalReferenceCode(String value) {
        setRequestNodeValueByXPath("/Envelope/Body/checkout/request/externalReferenceDetail/externalReferenceCode", value);
    }

    public void setExternalReferenceNumber(String value) {
        setRequestNodeValueByXPath("/Envelope/Body/checkout/request/externalReferenceDetail/externalReferenceNumber", value);
    }

    public void setExternalReferenceSource(String value) {
        setRequestNodeValueByXPath("/Envelope/Body/checkout/request/externalReferenceDetail/externalReferenceSource", value);
    }

    public void setCheckoutDate(String value) {
        setRequestNodeValueByXPath("/Envelope/Body/checkout/request/checkoutDate", value);
    }

    public void setLocationId(String value) {
        setRequestNodeValueByXPath("/Envelope/Body/checkout/request/locationId", value);
    }

}
