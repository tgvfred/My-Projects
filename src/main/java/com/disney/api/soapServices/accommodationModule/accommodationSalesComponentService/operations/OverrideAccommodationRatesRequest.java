package com.disney.api.soapServices.accommodationModule.accommodationSalesComponentService.operations;

import com.disney.api.soapServices.accommodationModule.accommodationSalesComponentService.AccommodationSalesComponentService;
import com.disney.utils.XMLTools;

public class OverrideAccommodationRatesRequest extends AccommodationSalesComponentService {

    public OverrideAccommodationRatesRequest(String environment, String scenario) {
        super(environment);

        // Generate a request from a project xml file
        setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("overrideAccommodationRates")));

        generateServiceContext();
        setRequestNodeValueByXPath(getTestScenario(getService(), getOperation(), scenario));

        removeComments();
        removeWhiteSpace();
    }

    public OverrideAccommodationRatesRequest(String environment) {
        super(environment);

        // Generate a request from a project xml file
        setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("overrideAccommodationRates")));

        generateServiceContext();

        removeComments();
        removeWhiteSpace();
    }

    public void setTpsID(String value) {
        setRequestNodeValueByXPath("/Envelope/Body/overrideAccommodationRates/request/travelPlanSegmentId", value);
    }

    public void setTcgId(String value) {
        setRequestNodeValueByXPath("/Envelope/Body/overrideAccommodationRates/request/travelComponentGroupingId", value);
    }

    public void setAdditionalCharge(String value) {
        setRequestNodeValueByXPath("/Envelope/Body/overrideAccommodationRates/request/rateDetails/additionalCharge", value);
    }

    public void setAdditionalChargeOverridden(String value) {
        setRequestNodeValueByXPath("/Envelope/Body/overrideAccommodationRates/request/rateDetails/additionalChargeOverridden", value);
    }

    public void setBasePrice(String value) {
        setRequestNodeValueByXPath("/Envelope/Body/overrideAccommodationRates/request/rateDetails/basePrice", value);
    }

    public void setDate(String value) {
        setRequestNodeValueByXPath("/Envelope/Body/overrideAccommodationRates/request/rateDetails/date", value);
    }

    public void setDayCount(String value) {
        setRequestNodeValueByXPath("/Envelope/Body/overrideAccommodationRates/request/rateDetails/dayCount", value);
    }

    public void setOverridden(String value) {
        setRequestNodeValueByXPath("/Envelope/Body/overrideAccommodationRates/request/rateDetails/overidden", value);
    }

    public void setShared(String value) {
        setRequestNodeValueByXPath("/Envelope/Body/overrideAccommodationRates/request/rateDetails/shared", value);
    }

    public void setRackRateDate(String value) {
        setRequestNodeValueByXPath("/Envelope/Body/overrideAccommodationRates/request/rateDetails/rackRate/date", value);
    }

    public void setRackRateRate(String value) {
        setRequestNodeValueByXPath("/Envelope/Body/overrideAccommodationRates/request/rateDetails/rackRate/rate", value);
    }

    public void setNetPrice(String value) {
        setRequestNodeValueByXPath("/Envelope/Body/overrideAccommodationRates/request/rateDetails/netPrice", value);
    }

    public void setPointsValue(String value) {
        setRequestNodeValueByXPath("/Envelope/Body/overrideAccommodationRates/request/rateDetails/pointsValue", value);
    }

    public void setOverrideReason(String value) {
        setRequestNodeValueByXPath("/Envelope/Body/overrideAccommodationRates/request/overrideReason", value);
    }

    public void setLocationId(String value) {
        setRequestNodeValueByXPath("/Envelope/Body/overrideAccommodationRates/request/locationId", value);
    }

    public void setContactName(String value) {
        setRequestNodeValueByXPath("/Envelope/Body/overrideAccommodationRates/request/contactName", value);
    }

    public void setOverrideRate(String value) {

        setRequestNodeValueByXPath("/Envelope/Body/overrideAccommodationRates/request/overrideRate", value);
    }

    public void setSharedLinks(String value) {
        setRequestNodeValueByXPath("/Envelope/Body/overrideAccommodationRates/request/sharedLinks", value);
    }

    public void setRequest(String value) {
        setRequestNodeValueByXPath("/Envelope/Body/overrideAccommodationRates/request", value);
    }

    public void setExtRefDetail(String value) {

        setRequestNodeValueByXPath("/Envelope/Body/overrideAccommodationRates/request/externalReferenceDetail", value);

    }

    public void setRateDetails(String value) {

        setRequestNodeValueByXPath("/Envelope/Body/overrideAccommodationRates/request/rateDetails", value);
    }

    public void setExternalReferenceNumber(String value) {

        setRequestNodeValueByXPath("/Envelope/Body/overrideAccommodationRates/request/externalReferenceDetail/externalReferenceNumber", value);
    }

    public void setExternalReferenceCode(String value) {

        setRequestNodeValueByXPath("/Envelope/Body/overrideAccommodationRates/request/externalReferenceDetail/externalReferenceCode", value);
    }

    public void setExternalReferenceSource(String value) {
        setRequestNodeValueByXPath("/Envelope/Body/overrideAccommodationRates/request/externalReferenceDetail/externalReferenceSource", value);
    }

    public void setSourceExternalReferenceDetail(String value) {

        setRequestNodeValueByXPath("/Envelope/Body/overrideAccommodationRates/request/sourceExternalReferenceDetail/", value);
    }

    public void setExternalReferenceType(String value) {

        setRequestNodeValueByXPath("/Envelope/Body/overrideAccommodationRates/request/externalReferenceDetail/externalReferenceType", value);
    }

    public String getTcgID() {

        return getResponseNodeValueByXPath("/Envelope/Body/overrideAccommodationRatesResponse/return/travelComponentGroupingId");

    }

    public String getTcID() {

        return getResponseNodeValueByXPath("/Envelope/Body/overrideAccommodationRatesResponse/return/travelComponentId");
    }

    public String getTpsId() {

        return getResponseNodeValueByXPath("/Envelope/Body/overrideAccommodationRatesResponse/return/travelPlanSegmentId");

    }

    public String getTravelStatus() {
        return getResponseNodeValueByXPath("/Envelope/Body/overrideAccommodationRatesResponse/return/travelStatus");

    }

    public String getRoomTypeCode() {

        return getResponseNodeValueByXPath("/Envelope/Body/overrideAccommodationRatesResponse/return/roomTypeCode");
    }

}