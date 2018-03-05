package com.disney.api.soapServices.accommodationModule.accommodationSalesComponentService.operations;

import com.disney.api.soapServices.accommodationModule.accommodationSalesComponentService.AccommodationSalesComponentService;
import com.disney.utils.XMLTools;

public class UnshareAccommodations extends AccommodationSalesComponentService {

    public UnshareAccommodations(String environment) {
        super(environment);

        // Generate a request from a project xml file
        setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("unshareAccommodations")));
        removeComments();
        removeWhiteSpace();
        generateServiceContext();
    }

    public UnshareAccommodations(String environment, String scenario) {
        super(environment);

        // Generate a request from a project xml file
        setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("unshareAccommodations")));
        generateServiceContext();

        setRequestNodeValueByXPath(getTestScenario(getService(), getOperation(), scenario));
        removeComments();
        removeWhiteSpace();
    }

    public void setBlockCode(String index, String value) {
        setRequestNodeValueByXPath("/Envelope/Body/unshareAccommodations/request/unsharedAccommodations/unSharedRoomDetail[" + index + "]/blockCode", value);
    }

    public void setBookingDate(String index, String value) {
        setRequestNodeValueByXPath("/Envelope/Body/unshareAccommodations/request/unsharedAccommodations/unSharedRoomDetail[" + index + "]/bookingDate", value);
    }

    public void setCheckOutDateTime(String index, String value) {
        setRequestNodeValueByXPath("/Envelope/Body/unshareAccommodations/request/unsharedAccommodations/unSharedRoomDetail[" + index + "]/checkOutDateTime", value);
    }

    public void setExternalReferenceType(String index, String value) {
        setRequestNodeValueByXPath("/Envelope/Body/unshareAccommodations/request/unsharedAccommodations/unSharedRoomDetail[" + index + "]/externalReferences/externalReferenceType", value);
    }

    public void setExternalReferenceCode(String index, String value) {
        setRequestNodeValueByXPath("/Envelope/Body/unshareAccommodations/request/unsharedAccommodations/unSharedRoomDetail[" + index + "]/externalReferences/externalReferenceCode", value);
    }

    public void setExternalReferenceSource(String index, String value) {
        setRequestNodeValueByXPath("/Envelope/Body/unshareAccommodations/request/unsharedAccommodations/unSharedRoomDetail[" + index + "]/externalReferences/externalReferenceNumber", value);
    }

    public void setExternalReferenceNumber(String index, String value) {
        setRequestNodeValueByXPath("/Envelope/Body/unshareAccommodations/request/unsharedAccommodations/unSharedRoomDetail[" + index + "]/externalReferences/externalReferenceSource", value);
    }

    public void setFplosId(String index, String value) {
        setRequestNodeValueByXPath("/Envelope/Body/unshareAccommodations/request/unsharedAccommodations/unSharedRoomDetail[" + index + "]/fplosId", value);
    }

    public void setFreezeId(String index, String value) {
        setRequestNodeValueByXPath("/Envelope/Body/unshareAccommodations/request/unsharedAccommodations/unSharedRoomDetail[" + index + "]/freezeId", value);
    }

    public void setInventoryTrackingId(String index, String value) {
        setRequestNodeValueByXPath("/Envelope/Body/unshareAccommodations/request/unsharedAccommodations/unSharedRoomDetail[" + index + "]/InventoryTrackingId", value);
    }

    public void setGuaranteeStatus(String index, String value) {
        setRequestNodeValueByXPath("/Envelope/Body/unshareAccommodations/request/unsharedAccommodations/unSharedRoomDetail[" + index + "]/guaranteeStatus", value);
    }

    public void setInventoryStatus(String index, String value) {
        setRequestNodeValueByXPath("/Envelope/Body/unshareAccommodations/request/unsharedAccommodations/unSharedRoomDetail[" + index + "]/inventoryStatus", value);
    }

    public void setLocationId(String index, String value) {
        setRequestNodeValueByXPath("/Envelope/Body/unshareAccommodations/request/unsharedAccommodations/unSharedRoomDetail[" + index + "]/locationId", value);
    }

    public void setModificationDate(String index, String value) {
        setRequestNodeValueByXPath("/Envelope/Body/unshareAccommodations/request/unsharedAccommodations/unSharedRoomDetail[" + index + "]/modificationDate", value);
    }

    public void setOverrideFreeze(String index, String value) {
        setRequestNodeValueByXPath("/Envelope/Body/unshareAccommodations/request/unsharedAccommodations/unSharedRoomDetail[" + index + "]/overideFreeze", value);
    }

    public void setPackageCode(String index, String value) {
        setRequestNodeValueByXPath("/Envelope/Body/unshareAccommodations/request/unsharedAccommodations/unSharedRoomDetail[" + index + "]/packageCode", value);
    }

    public void setRateCategoryCode(String index, String value) {
        setRequestNodeValueByXPath("/Envelope/Body/unshareAccommodations/request/unsharedAccommodations/unSharedRoomDetail[" + index + "]/rateCategoryCode", value);
    }

    public void setAdditionalCharge(String index, String value) {
        setRequestNodeValueByXPath("/Envelope/Body/unshareAccommodations/request/unsharedAccommodations/unSharedRoomDetail[" + index + "]/rateDetails/additionalCharge", value);
    }

    public void setAdditionalChargeOverride(String index, String value) {
        setRequestNodeValueByXPath("/Envelope/Body/unshareAccommodations/request/unsharedAccommodations/unSharedRoomDetail[" + index + "]/rateDetails/additionalChargeOverridden", value);
    }

    public void setRateDetailsBasePrice(String index, String value) {
        setRequestNodeValueByXPath("/Envelope/Body/unshareAccommodations/request/unsharedAccommodations/unSharedRoomDetail[" + index + "]/rateDetails/basePrice", value);
    }

    public void setRateDetailsDate(String index, String value) {
        setRequestNodeValueByXPath("/Envelope/Body/unshareAccommodations/request/unsharedAccommodations/unSharedRoomDetail[" + index + "]/rateDetails/date", value);
    }

    public void setRateDetailsDayCount(String index, String value) {
        setRequestNodeValueByXPath("/Envelope/Body/unshareAccommodations/request/unsharedAccommodations/unSharedRoomDetail[" + index + "]/rateDetails/dayCount", value);
    }

    public void setRateDetailsOverriden(String index, String value) {
        setRequestNodeValueByXPath("/Envelope/Body/unshareAccommodations/request/unsharedAccommodations/unSharedRoomDetail[" + index + "]/rateDetails/overidden", value);
    }

    public void setRateDetailsShared(String index, String value) {
        setRequestNodeValueByXPath("/Envelope/Body/unshareAccommodations/request/unsharedAccommodations/unSharedRoomDetail[" + index + "]/rateDetails/shared", value);
    }

    public void setShared(String index, String value) {
        setRequestNodeValueByXPath("/Envelope/Body/unshareAccommodations/request/unsharedAccommodations/unSharedRoomDetail[" + index + "]/shared", value);
    }

    public void setRackRateDate(String index, String value) {
        setRequestNodeValueByXPath("/Envelope/Body/unshareAccommodations/request/unsharedAccommodations/unSharedRoomDetail[" + index + "]/rateDetails/rackRate/date", value);
    }

    public void setRackRateRate(String index, String value) {
        setRequestNodeValueByXPath("/Envelope/Body/unshareAccommodations/request/unsharedAccommodations/unSharedRoomDetail[" + index + "]/rateDetails/rackRate/rate", value);
    }

    public void setNetPrice(String index, String value) {
        setRequestNodeValueByXPath("/Envelope/Body/unshareAccommodations/request/unsharedAccommodations/unSharedRoomDetail[" + index + "]/rateDetails/netPrice", value);
    }

    public void setPointsValue(String index, String value) {
        setRequestNodeValueByXPath("/Envelope/Body/unshareAccommodations/request/unsharedAccommodations/unSharedRoomDetail[" + index + "]/rateDetails/pointsValue", value);
    }

    public void setCode(String index, String value) {
        setRequestNodeValueByXPath("/Envelope/Body/unshareAccommodations/request/unsharedAccommodations/unSharedRoomDetail[" + index + "]/resortCode", value);
    }

    public void setResortEndDate(String index, String value) {
        setRequestNodeValueByXPath("/Envelope/Body/unshareAccommodations/request/unsharedAccommodations/unSharedRoomDetail[" + index + "]/resortPeriod/endDate", value);
    }

    public void setResortStartDate(String index, String value) {
        setRequestNodeValueByXPath("/Envelope/Body/unshareAccommodations/request/unsharedAccommodations/unSharedRoomDetail[" + index + "]/resortPeriod/startDate", value);
    }

    public void setRoomNumber(String index, String value) {
        setRequestNodeValueByXPath("/Envelope/Body/unshareAccommodations/request/unsharedAccommodations/unSharedRoomDetail[" + index + "]/roomNumber", value);
    }

    public void setCommentsCreatedBy(String index, String value) {
        setRequestNodeValueByXPath("/Envelope/Body/unshareAccommodations/request/unsharedAccommodations/unSharedRoomDetail[" + index + "]/roomReservationDetail/comments/auditDetail/createdBy", value);
    }

    public void setCommentsCreatedDate(String index, String value) {
        setRequestNodeValueByXPath("/Envelope/Body/unshareAccommodations/request/unsharedAccommodations/unSharedRoomDetail[" + index + "]/roomReservationDetail/comments/auditDetail/createdDate", value);
    }

    public void setCommentsUpdatedBy(String index, String value) {
        setRequestNodeValueByXPath("/Envelope/Body/unshareAccommodations/request/unsharedAccommodations/unSharedRoomDetail[" + index + "]/roomReservationDetail/comments/auditDetail/updatedBy", value);
    }

    public void setCommentsUpdateDate(String index, String value) {
        setRequestNodeValueByXPath("/Envelope/Body/unshareAccommodations/request/unsharedAccommodations/unSharedRoomDetail[" + index + "]/roomReservationDetail/comments/auditDetail/updatedDate", value);
    }

    public void setCommentsStatus(String index, String value) {
        setRequestNodeValueByXPath("/Envelope/Body/unshareAccommodations/request/unsharedAccommodations/unSharedRoomDetail[" + index + "]/roomReservationDetail/comments/auditDetail/status", value);
    }

    public void setCommentsCommentText(String index, String value) {
        setRequestNodeValueByXPath("/Envelope/Body/unshareAccommodations/request/unsharedAccommodations/unSharedRoomDetail[" + index + "]/roomReservationDetail/comments/commentText", value);
    }

    public void setResortCode(String index, String value) {
        setRequestNodeValueByXPath("/Envelope/Body/unshareAccommodations/request/unsharedAccommodations/unSharedRoomDetail[" + index + "]/resortCode", value);
    }

    public void setCmmentsDefault(String index, String value) {
        setRequestNodeValueByXPath("/Envelope/Body/unshareAccommodations/request/unsharedAccommodations/unSharedRoomDetail[" + index + "]/roomReservationDetail/comments/default", value);
    }

    public void setCommentsFrom(String index, String value) {
        setRequestNodeValueByXPath("/Envelope/Body/unshareAccommodations/request/unsharedAccommodations/unSharedRoomDetail[" + index + "]/roomReservationDetail/comments/from", value);
    }

    public void setCommentsRoutingName(String index, String value) {
        setRequestNodeValueByXPath("/Envelope/Body/unshareAccommodations/request/unsharedAccommodations/unSharedRoomDetail[" + index + "]/roomReservationDetail/comments/routings/name", value);
    }

    public void setCommentsTo(String index, String value) {
        setRequestNodeValueByXPath("/Envelope/Body/unshareAccommodations/request/unsharedAccommodations/unSharedRoomDetail[" + index + "]/roomReservationDetail/comments/to", value);
    }

    public void setGuestAge(String index, String value) {
        setRequestNodeValueByXPath("/Envelope/Body/unshareAccommodations/request/unsharedAccommodations/unSharedRoomDetail[" + index + "]/roomReservationDetail/guestReferenceDetails/age", value);
    }

    public void setGuestType(String index, String value) {
        setRequestNodeValueByXPath("/Envelope/Body/unshareAccommodations/request/unsharedAccommodations/unSharedRoomDetail[" + index + "]/roomReservationDetail/guestReferenceDetails/ageType", value);
    }

    public void setGuestSuffix(String index, String value) {
        setRequestNodeValueByXPath("/Envelope/Body/unshareAccommodations/request/unsharedAccommodations/unSharedRoomDetail[" + index + "]/roomReservationDetail/guestReferenceDetails/guest/suffix", value);
    }

    public void setGuestTitle(String index, String value) {
        setRequestNodeValueByXPath("/Envelope/Body/unshareAccommodations/request/unsharedAccommodations/unSharedRoomDetail[" + index + "]/roomReservationDetail/guestReferenceDetails/guest/title", value);
    }

    public void setGuestFirstName(String index, String value) {
        setRequestNodeValueByXPath("/Envelope/Body/unshareAccommodations/request/unsharedAccommodations/unSharedRoomDetail[" + index + "]/roomReservationDetail/guestReferenceDetails/guest/firstName", value);
    }

    public void setGuestLastName(String index, String value) {
        setRequestNodeValueByXPath("/Envelope/Body/unshareAccommodations/request/unsharedAccommodations/unSharedRoomDetail[" + index + "]/roomReservationDetail/guestReferenceDetails/guest/lastName", value);
    }

    public void setGuestMiddleName(String index, String value) {
        setRequestNodeValueByXPath("/Envelope/Body/unshareAccommodations/request/unsharedAccommodations/unSharedRoomDetail[" + index + "]/roomReservationDetail/guestReferenceDetails/guest/middleName", value);
    }

    public void setGuestPartyId(String index, String value) {
        setRequestNodeValueByXPath("/Envelope/Body/unshareAccommodations/request/unsharedAccommodations/unSharedRoomDetail[" + index + "]/roomReservationDetail/guestReferenceDetails/guest/partyId", value);
    }

    public void setGuestLocatorId(String index, String value) {
        setRequestNodeValueByXPath("/Envelope/Body/unshareAccommodations/request/unsharedAccommodations/unSharedRoomDetail[" + index + "]/roomReservationDetail/guestReferenceDetails/guest/phoneDetails/locatorId", value);
    }

    public void setGuestDoNotMailIndicator(String index, String value) {
        setRequestNodeValueByXPath("/Envelope/Body/unshareAccommodations/request/unsharedAccommodations/unSharedRoomDetail[" + index + "]/roomReservationDetail/guestReferenceDetails/guest/doNotMailIndicator", value);
    }

    public void setGuestDoNotPhoneIndicator(String index, String value) {
        setRequestNodeValueByXPath("/Envelope/Body/unshareAccommodations/request/unsharedAccommodations/unSharedRoomDetail[" + index + "]/roomReservationDetail/guestReferenceDetails/guest/doNotPhoneIndicator", value);
    }

    public void setGuestId(String index, String value) {
        setRequestNodeValueByXPath("/Envelope/Body/unshareAccommodations/request/unsharedAccommodations/unSharedRoomDetail[" + index + "]/roomReservationDetail/guestReferenceDetails/guest/guestId", value);
    }

    public void setGuestDclGuestId(String index, String value) {
        setRequestNodeValueByXPath("/Envelope/Body/unshareAccommodations/request/unsharedAccommodations/unSharedRoomDetail[" + index + "]/roomReservationDetail/guestReferenceDetails/guest/dclGuestId", value);
    }

    public void setGuestActive(String index, String value) {
        setRequestNodeValueByXPath("/Envelope/Body/unshareAccommodations/request/unsharedAccommodations/unSharedRoomDetail[" + index + "]/roomReservationDetail/guestReferenceDetails/guest/active", value);
    }

    public void setGuestPurposeOfVisit(String index, String value) {
        setRequestNodeValueByXPath("/Envelope/Body/unshareAccommodations/request/unsharedAccommodations/unSharedRoomDetail[" + index + "]/roomReservationDetail/guestReferenceDetails/purposeOfVisit", value);
    }

    public void setCorrelationId(String index, String value) {
        setRequestNodeValueByXPath("/Envelope/Body/unshareAccommodations/request/unsharedAccommodations/unSharedRoomDetail[" + index + "]/roomReservationDetail/guestReferenceDetails/correlationID", value);
    }

    public void setRoomTypeCode(String index, String value) {
        setRequestNodeValueByXPath("/Envelope/Body/unshareAccommodations/request/unsharedAccommodations/unSharedRoomDetail[" + index + "]/roomTypeCode", value);
    }

    public void setRsrReservation(String index, String value) {
        setRequestNodeValueByXPath("/Envelope/Body/unshareAccommodations/request/unsharedAccommodations/unSharedRoomDetail[" + index + "]/rsrReservation", value);
    }

    public void setTcgId(String index, String value) {
        setRequestNodeValueByXPath("/Envelope/Body/unshareAccommodations/request/unsharedAccommodations/unSharedRoomDetail[" + index + "]/travelComponentGroupingId", value);
    }

    public void setTcId(String index, String value) {
        setRequestNodeValueByXPath("/Envelope/Body/unshareAccommodations/request/unsharedAccommodations/unSharedRoomDetail[" + index + "]/travelComponentId", value);
    }

    public void setTravelStatus(String index, String value) {
        setRequestNodeValueByXPath("/Envelope/Body/unshareAccommodations/request/unsharedAccommodations/unSharedRoomDetail[" + index + "]/travelStatus", value);
    }

    public void setUnsharedRoomTpsId(String index, String value) {
        setRequestNodeValueByXPath("/Envelope/Body/unshareAccommodations/request/unsharedAccommodations/travelPlanSegmentId[" + index + "]", value);
    }

}