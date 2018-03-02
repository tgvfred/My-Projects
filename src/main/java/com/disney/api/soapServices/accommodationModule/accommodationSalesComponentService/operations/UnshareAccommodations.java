package com.disney.api.soapServices.accommodationModule.accommodationSalesComponentService.operations;

import com.disney.api.soapServices.accommodationModule.accommodationSalesComponentService.AccommodationSalesComponentService;
import com.disney.utils.XMLTools;

public class UnshareAccommodations extends AccommodationSalesComponentService {

    public UnshareAccommodations(String environment) {
        super(environment);

        // Generate a request from a project xml file
        setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("shareAccommodations")));
        removeComments();
        removeWhiteSpace();
        generateServiceContext();
    }

    public UnshareAccommodations(String environment, String scenario) {
        super(environment);

        // Generate a request from a project xml file
        setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("shareAccommodations")));
        generateServiceContext();

        setRequestNodeValueByXPath(getTestScenario(getService(), getOperation(), scenario));
        removeComments();
        removeWhiteSpace();
    }

    public void setBlockCode(String value) {
        setRequestNodeValueByXPath("/Envelope/Body/unshareAccommodations/request/shareChains/shareRoomDetails/unSharedRoomDetail/blockCode", value);
    }

    public void setBookingDate(String value) {
        setRequestNodeValueByXPath("/Envelope/Body/unshareAccommodations/request/shareChains/shareRoomDetails/unSharedRoomDetail/bookingDate", value);
    }

    public void setCheckOutDateTime(String value) {
        setRequestNodeValueByXPath("/Envelope/Body/unshareAccommodations/request/shareChains/shareRoomDetails/unSharedRoomDetail/checkOutDateTime", value);
    }

    public void setExternalReferenceType(String value) {
        setRequestNodeValueByXPath("/Envelope/Body/unshareAccommodations/request/shareChains/shareRoomDetails/unSharedRoomDetail/externalReferences/externalReferenceType", value);
    }

    public void setExternalReferenceCode(String value) {
        setRequestNodeValueByXPath("/Envelope/Body/unshareAccommodations/request/shareChains/shareRoomDetails/unSharedRoomDetail/externalReferences/externalReferenceCode", value);
    }

    public void setExternalReferenceSource(String value) {
        setRequestNodeValueByXPath("/Envelope/Body/unshareAccommodations/request/shareChains/shareRoomDetails/unSharedRoomDetail/externalReferences/externalReferenceNumber", value);
    }

    public void setExternalReferenceNumber(String value) {
        setRequestNodeValueByXPath("/Envelope/Body/unshareAccommodations/request/shareChains/shareRoomDetails/unSharedRoomDetail/externalReferences/externalReferenceSource", value);
    }

    public void setFplosId(String value) {
        setRequestNodeValueByXPath("/Envelope/Body/unshareAccommodations/request/shareChains/shareRoomDetails/unSharedRoomDetail/fplosId", value);
    }

    public void setFreezeId(String value) {
        setRequestNodeValueByXPath("/Envelope/Body/unshareAccommodations/request/shareChains/shareRoomDetails/unSharedRoomDetail/freezeId", value);
    }

    public void setInventoryTrackingId(String value) {
        setRequestNodeValueByXPath("/Envelope/Body/unshareAccommodations/request/shareChains/shareRoomDetails/unSharedRoomDetail/InventoryTrackingId", value);
    }

    public void setGuaranteeStatus(String value) {
        setRequestNodeValueByXPath("/Envelope/Body/unshareAccommodations/request/shareChains/shareRoomDetails/unSharedRoomDetail/guaranteeStatus", value);
    }

    public void setInventoryStatus(String value) {
        setRequestNodeValueByXPath("/Envelope/Body/unshareAccommodations/request/shareChains/shareRoomDetails/unSharedRoomDetail/inventoryStatus", value);
    }

    public void setModificationDate(String value) {
        setRequestNodeValueByXPath("/Envelope/Body/unshareAccommodations/request/shareChains/shareRoomDetails/unSharedRoomDetail/modificationDate", value);
    }

    public void setOverrideFreeze(String value) {
        setRequestNodeValueByXPath("/Envelope/Body/unshareAccommodations/request/shareChains/shareRoomDetails/unSharedRoomDetail/overideFreeze", value);
    }

    public void setPackageCode(String value) {
        setRequestNodeValueByXPath("/Envelope/Body/unshareAccommodations/request/shareChains/shareRoomDetails/unSharedRoomDetail/packageCode", value);
    }

    public void setRateCategoryType(String value) {
        setRequestNodeValueByXPath("/Envelope/Body/unshareAccommodations/request/shareChains/shareRoomDetails/unSharedRoomDetail/rateCategoryCode", value);
    }

    public void setAdditionalCharge(String value) {
        setRequestNodeValueByXPath("/Envelope/Body/unshareAccommodations/request/shareChains/shareRoomDetails/unSharedRoomDetail/rateDetails/additionalCharge", value);
    }

    public void setAdditionalChargeOverride(String value) {
        setRequestNodeValueByXPath("/Envelope/Body/unshareAccommodations/request/shareChains/shareRoomDetails/unSharedRoomDetail/rateDetails/additionalChargeOverridden", value);
    }

    public void setBasePrice(String value) {
        setRequestNodeValueByXPath("/Envelope/Body/unshareAccommodations/request/shareChains/shareRoomDetails/unSharedRoomDetail/rateDetails/basePrice", value);
    }

    public void setDate(String value) {
        setRequestNodeValueByXPath("/Envelope/Body/unshareAccommodations/request/shareChains/shareRoomDetails/unSharedRoomDetail/rateDetails/date", value);
    }

    public void setDayCount(String value) {
        setRequestNodeValueByXPath("/Envelope/Body/unshareAccommodations/request/shareChains/shareRoomDetails/unSharedRoomDetail/rateDetails/dayCount", value);
    }

    public void setOverriden(String value) {
        setRequestNodeValueByXPath("/Envelope/Body/unshareAccommodations/request/shareChains/shareRoomDetails/unSharedRoomDetail/rateDetails/overidden", value);
    }

    public void setShared(String value) {
        setRequestNodeValueByXPath("/Envelope/Body/unshareAccommodations/request/shareChains/shareRoomDetails/unSharedRoomDetail/rateDetails/shared", value);
    }

    public void setRackRateDate(String value) {
        setRequestNodeValueByXPath("/Envelope/Body/unshareAccommodations/request/shareChains/shareRoomDetails/unSharedRoomDetail/rateDetails/rackRate/date", value);
    }

    public void setRackRateRate(String value) {
        setRequestNodeValueByXPath("/Envelope/Body/unshareAccommodations/request/shareChains/shareRoomDetails/unSharedRoomDetail/rateDetails/rackRate/rate", value);
    }

    public void setNetPrice(String value) {
        setRequestNodeValueByXPath("/Envelope/Body/unshareAccommodations/request/shareChains/shareRoomDetails/unSharedRoomDetail/rateDetails/netPrice", value);
    }

    public void setPointsValue(String value) {
        setRequestNodeValueByXPath("/Envelope/Body/unshareAccommodations/request/shareChains/shareRoomDetails/unSharedRoomDetail/rateDetails/pointsValue", value);
    }

    public void setCode(String value) {
        setRequestNodeValueByXPath("/Envelope/Body/unshareAccommodations/request/shareChains/shareRoomDetails/unSharedRoomDetail/resortCode", value);
    }

    public void setResortEndDate(String value) {
        setRequestNodeValueByXPath("/Envelope/Body/unshareAccommodations/request/shareChains/shareRoomDetails/unSharedRoomDetail/resortPeriod/endDate", value);
    }

    public void setResortStartDate(String value) {
        setRequestNodeValueByXPath("/Envelope/Body/unshareAccommodations/request/shareChains/shareRoomDetails/unSharedRoomDetail/resortPeriod/startDate", value);
    }

}