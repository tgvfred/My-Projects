package com.disney.api.soapServices.accommodationModule.accommodationSalesComponentService.operations;

import com.disney.api.soapServices.accommodationModule.accommodationSalesComponentService.AccommodationSalesComponentService;
import com.disney.utils.XMLTools;

public class ShareAccommodations extends AccommodationSalesComponentService {

    public ShareAccommodations(String environment) {
        super(environment);

        // Generate a request from a project xml file
        setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("shareAccommodations")));
        // generateServiceContext();
        removeComments();
        removeWhiteSpace();

    }

    public ShareAccommodations(String environment, String scenario) {
        super(environment);

        // Generate a request from a project xml file
        setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("shareAccommodations")));
        generateServiceContext();
        setRequestNodeValueByXPath(getTestScenario("/services/accommodationSalesComponentServicePort/shareAccommodations/Main.xls", getOperation(), scenario));
        // setRequestNodeValueByXPath(getTestScenario(getService(), getOperation(), scenario));

        generateServiceContext();

        // setRequestNodeValueByXPath(getTestScenario(getService(), getOperation(), scenario));
        removeComments();
        removeWhiteSpace();
    }

    public void setAccommodation(String value) {
        setRequestNodeValueByXPath("/Envelope/Body/shareAccommodations/request/accommodations", value);
    }

    public void setRequest(String value) {
        setRequestNodeValueByXPath("/Envelope/Body/shareAccommodations/request", value);
    }

    public void setBookingDate(String value) {
        setRequestNodeValueByXPath("Envelope/Body/shareAccommodations/request/accommodations/bookingDate", value);

    }

    public void setFreezeId(String value) {
        setRequestNodeValueByXPath("Envelope/Body/shareAccommodations/request/accommodations/freezeId", value);
    }

    public void setGuaranteeStatus(String value) {
        setRequestNodeValueByXPath("Envelope/Body/shareAccommodations/request/accommodations/guaranteeStatus", value);

    }

    public void setInventoryStatus(String value) {
        setRequestNodeValueByXPath("Envelope/Body/shareAccommodations/request/accommodations/inventoryStatus", value);

    }

    public void setOverrideFreeze(String value) {
        setRequestNodeValueByXPath("Envelope/Body/shareAccommodations/request/accommodations/overideFreeze", value);
    }

    public void setPackageCode(String value) {
        setRequestNodeValueByXPath("Envelope/Body/shareAccommodations/request/accommodations/packageCode", value);
    }

    public void setResortCode(String value) {
        setRequestNodeValueByXPath("Envelope/Body/shareAccommodations/request/accommodations/resortCode", value);
    }

    public void setResortEndDate(String value) {
        setRequestNodeValueByXPath("Envelope/Body/shareAccommodations/request/accommodations/resortPeriod/endDate", value);
    }

    public void setResortStartDate(String value) {
        setRequestNodeValueByXPath("Envelope/Body/shareAccommodations/request/accommodations/resortPeriod/startDate", value);
    }

    public void setRoomTypeCode(String value) {
        setRequestNodeValueByXPath("Envelope/Body/shareAccommodations/request/accommodations/roomTypeCode", value);
    }

    public void setRSRReservations(String value) {
        setRequestNodeValueByXPath("Envelope/Body/shareAccommodations/request/accommodations/rsrReservation", value);
    }

    public void setTcgId(String value) {
        setRequestNodeValueByXPath("Envelope/Body/shareAccommodations/request/accommodations/travelComponentGroupingId", value);
    }

    public void setTcId(String value) {
        setRequestNodeValueByXPath("Envelope/Body/shareAccommodations/request/accommodations/travelComponentId", value);
    }

    public void setTravelStatus(String value) {
        setRequestNodeValueByXPath("Envelope/Body/shareAccommodations/request/accommodations/travelStatus", value);
    }

    public void setLocationId(String value) {
        setRequestNodeValueByXPath("Envelope/Body/shareAccommodations/request/accommodations/locationId", value);
    }

    public void setShared(String value) {
        setRequestNodeValueByXPath("Envelope/Body/shareAccommodations/request/accommodations/shared", value);

    }

    public void setSpecialNeedsRequested(String value) {
        setRequestNodeValueByXPath("Envelope/Body/shareAccommodations/request/accommodations/specialNeedsRequested", value);
    }

    public String getTcgIndex1() {

        return getResponseNodeValueByXPath("Envelope/Body/shareAccommodationsResponse/return/shareChainDetails/shareRoomDetails/sharedRoomDetail/travelComponentGroupingId");
    }

    public String getTcgIndex2() {

        return getResponseNodeValueByXPath("Envelope/Body/shareAccommodationsResponse/return/shareChainDetails/shareRoomDetails[2]/sharedRoomDetail/travelComponentGroupingId");
    }

    public String getTcIndex1() {

        return getResponseNodeValueByXPath("Envelope/Body/shareAccommodationsResponse/return/shareChainDetails/shareRoomDetails/sharedRoomDetail/travelComponentId");
    }

    public String getTcIndex2() {

        return getResponseNodeValueByXPath("Envelope/Body/shareAccommodationsResponse/return/shareChainDetails/shareRoomDetails[2]/sharedRoomDetail/travelComponentId");
    }

    public String getBookingDate() {

        return getResponseNodeValueByXPath("Envelope/Body/shareAccommodationsResponse/return/shareChainDetails/shareRoomDetails/sharedRoomDetail/bookingDate");
    }

    public String getTravelStatus() {

        return getResponseNodeValueByXPath("Envelope/Body/shareAccommodationsResponse/return/shareChainDetails/shareRoomDetails/sharedRoomDetail/travelStatus");
    }

}
