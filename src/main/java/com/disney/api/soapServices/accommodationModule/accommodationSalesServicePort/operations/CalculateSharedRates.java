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

    public String getBookingDate() {

        return getResponseNodeValueByXPath("/Envelope/Body/calculateSharedRatesResponse/splitRateWithTotalTO/accommodations/bookingDate");

    }

    public String getBookingDateRQ() {

        return getRequestNodeValueByXPath("/Envelope/Body/calculateSharedRates/request/accommodations/bookingDate");

    }

    public String getInventoryStatus() {

        return getResponseNodeValueByXPath("/Envelope/Body/calculateSharedRatesResponse/splitRateWithTotalTO/accommodations/inventoryStatus");

    }

    public String getInventoryStatusRQ() {

        return getRequestNodeValueByXPath("/Envelope/Body/calculateSharedRates/request/accommodations/inventoryStatus");

    }

    public String getOverideFreeze() {
        return getResponseNodeValueByXPath("/Envelope/Body/calculateSharedRatesResponse/splitRateWithTotalTO/accommodations/overideFreeze");

    }

    public String getOverideFreezeRQ() {
        return getRequestNodeValueByXPath("/Envelope/Body/calculateSharedRates/request/accommodations/overideFreeze");

    }

    public String getPackageCode() {
        return getResponseNodeValueByXPath("/Envelope/Body/calculateSharedRatesResponse/splitRateWithTotalTO/accommodations/packageCode");

    }

    public String getPackageCodeRQ() {
        return getRequestNodeValueByXPath("/Envelope/Body/calculateSharedRates/request/accommodations/packageCode");

    }

    public String getResortCode() {
        return getResponseNodeValueByXPath("/Envelope/Body/calculateSharedRatesResponse/splitRateWithTotalTO/accommodations/resortCode");

    }

    public String getResortCodeRQ() {
        return getRequestNodeValueByXPath("/Envelope/Body/calculateSharedRates/request/accommodations/resortCode");

    }

    public String getResortPeriodSD() {
        return getResponseNodeValueByXPath("/Envelope/Body/calculateSharedRatesResponse/splitRateWithTotalTO/accommodations/resortPeriod/startDate");

    }

    public String getResortPeriodSDRQ() {
        return getRequestNodeValueByXPath("/Envelope/Body/calculateSharedRates/request/accommodations/resortPeriod/startDate");

    }

    public String getResortPeriodED() {

        return getResponseNodeValueByXPath("/Envelope/Body/calculateSharedRatesResponse/splitRateWithTotalTO/accommodations/resortPeriod/endDate");

    }

    public String getResortPeriodEDRQ() {

        return getRequestNodeValueByXPath("/Envelope/Body/calculateSharedRates/request/accommodations/resortPeriod/endDate");

    }

    public String getFirstName() {
        return getResponseNodeValueByXPath("/Envelope/Body/calculateSharedRatesResponse/splitRateWithTotalTO/accommodations/roomReservationDetail/guestReferenceDetails/guest/firstName");

    }

    public String getFirstNameRQ() {
        return getRequestNodeValueByXPath("/Envelope/Body/calculateSharedRates/request/accommodations/roomReservationDetail/guestReferenceDetails/guest/firstName");

    }

    public String getLastName() {

        return getResponseNodeValueByXPath("/Envelope/Body/calculateSharedRatesResponse/splitRateWithTotalTO/accommodations/roomReservationDetail/guestReferenceDetails/guest/lastName");

    }

    public String getLastNameRQ() {

        return getRequestNodeValueByXPath("/Envelope/Body/calculateSharedRates/request/accommodations/roomReservationDetail/guestReferenceDetails/guest/lastName");

    }

    public String getPhoneDetailsNumber() {
        return getResponseNodeValueByXPath("/Envelope/Body/calculateSharedRatesResponse/splitRateWithTotalTO/accommodations/roomReservationDetail/guestReferenceDetails/guest/phoneDetails/number");

    }

    public String getPhoneDetailsNumberRQ() {
        return getRequestNodeValueByXPath("/Envelope/Body/calculateSharedRates/request/accommodations//roomReservationDetail/guestReferenceDetails/guest/phoneDetails/number");

    }

    public String getAddressLine1() {
        return getResponseNodeValueByXPath("/Envelope/Body/calculateSharedRatesResponse/splitRateWithTotalTO/accommodations/roomReservationDetail/guestReferenceDetails/guest/addressDetails/addressLine1");

    }

    public String getAddressLine1RQ() {
        return getRequestNodeValueByXPath("/Envelope/Body/calculateSharedRates/request/accommodations/roomReservationDetail/guestReferenceDetails/guest/addressDetails/addressLine1");

    }

    public String getEmailAddress() {

        return getResponseNodeValueByXPath("/Envelope/Body/calculateSharedRatesResponse/splitRateWithTotalTO/accommodations/roomReservationDetail/guestReferenceDetails/guest/emailDetails/address");

    }

    public String getEmailAddressRQ() {

        return getRequestNodeValueByXPath("/Envelope/Body/calculateSharedRates/request/accommodations/roomReservationDetail/guestReferenceDetails/guest/emailDetails/address");

    }

    public String getDoNotMailIndicator() {
        return getResponseNodeValueByXPath("/Envelope/Body/calculateSharedRatesResponse/splitRateWithTotalTO/accommodations/roomReservationDetail/guestReferenceDetails/guest/doNotMailIndicator");

    }

    public String getDoNotMailIndicatorRQ() {
        return getRequestNodeValueByXPath("/Envelope/Body/calculateSharedRates/request/accommodations/roomReservationDetail/guestReferenceDetails/guest/doNotMailIndicator");

    }

    public String getDoNotPhoneIndicator() {
        return getResponseNodeValueByXPath("/Envelope/Body/calculateSharedRatesResponse/splitRateWithTotalTO/accommodations/roomReservationDetail/guestReferenceDetails/guest/doNotPhoneIndicator");

    }

    public String getDoNotPhoneIndicatorRQ() {
        return getRequestNodeValueByXPath("/Envelope/Body/calculateSharedRates/request/accommodations/roomReservationDetail/guestReferenceDetails/guest/doNotPhoneIndicator");

    }

    public String getTcg() {
        return getResponseNodeValueByXPath("/Envelope/Body/calculateSharedRatesResponse/splitRateWithTotalTO/accommodations/travelComponentGroupingId");

    }

    public String getTcID() {

        return getResponseNodeValueByXPath("/Envelope/Body/calculateSharedRatesResponse/splitRateWithTotalTO/accommodations/travelComponentId");

    }

    public String getTravelStatus() {
        return getResponseNodeValueByXPath("/Envelope/Body/calculateSharedRatesResponse/splitRateWithTotalTO/accommodations/travelStatus");

    }

    public String getTravelStatusRQ() {
        return getRequestNodeValueByXPath("/Envelope/Body/calculateSharedRates/request/accommodations/travelStatus");

    }

    public String getShared() {

        return getResponseNodeValueByXPath("/Envelope/Body/calculateSharedRatesResponse/splitRateWithTotalTO/accommodations/shared");
    }

    public String getSharedRQ() {

        return getRequestNodeValueByXPath("/Envelope/Body/calculateSharedRates/request/accommodations/shared");
    }

    public String getRateDetailsAccommOne() {

        return getResponseNodeValueByXPath("/Envelope/Body/calculateSharedRatesResponse/splitRateWithTotalTO/accommodations/rateDetails/basePrice");
    }

    public String getRateDetailsAccommTwo() {

        return getResponseNodeValueByXPath("/Envelope/Body/calculateSharedRatesResponse//splitRateWithTotalTO/accommodations[2]/rateDetails/basePrice");
    }

    public String getTotalRateAmount() {

        return getResponseNodeValueByXPath("/Envelope/Body/calculateSharedRatesResponse/splitRateWithTotalTO/totalRates/rate/amount");
    }

}
