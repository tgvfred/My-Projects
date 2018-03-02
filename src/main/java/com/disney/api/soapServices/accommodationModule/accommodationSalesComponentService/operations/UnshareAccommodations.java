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
        setRequestNodeValueByXPath("/Envelope/Body/unshareAccommodations/request/shareChains/shareRoomDetails[" + index + "]/unSharedRoomDetail/blockCode", value);
    }

    public void setBookingDate(String index, String value) {
        setRequestNodeValueByXPath("/Envelope/Body/unshareAccommodations/request/shareChains/shareRoomDetails[" + index + "]/unSharedRoomDetail/bookingDate", value);
    }

    public void setCheckOutDateTime(String index, String value) {
        setRequestNodeValueByXPath("/Envelope/Body/unshareAccommodations/request/shareChains/shareRoomDetails[" + index + "]/unSharedRoomDetail/checkOutDateTime", value);
    }

    public void setExternalReferenceType(String index, String value) {
        setRequestNodeValueByXPath("/Envelope/Body/unshareAccommodations/request/shareChains/shareRoomDetails[" + index + "]/unSharedRoomDetail/externalReferences/externalReferenceType", value);
    }

    public void setExternalReferenceCode(String index, String value) {
        setRequestNodeValueByXPath("/Envelope/Body/unshareAccommodations/request/shareChains/shareRoomDetails[" + index + "]/unSharedRoomDetail/externalReferences/externalReferenceCode", value);
    }

    public void setExternalReferenceSource(String index, String value) {
        setRequestNodeValueByXPath("/Envelope/Body/unshareAccommodations/request/shareChains/shareRoomDetails[" + index + "]/unSharedRoomDetail/externalReferences/externalReferenceNumber", value);
    }

    public void setExternalReferenceNumber(String index, String value) {
        setRequestNodeValueByXPath("/Envelope/Body/unshareAccommodations/request/shareChains/shareRoomDetails[" + index + "]/unSharedRoomDetail/externalReferences/externalReferenceSource", value);
    }

    public void setFplosId(String index, String value) {
        setRequestNodeValueByXPath("/Envelope/Body/unshareAccommodations/request/shareChains/shareRoomDetails[" + index + "]/unSharedRoomDetail/fplosId", value);
    }

    public void setFreezeId(String index, String value) {
        setRequestNodeValueByXPath("/Envelope/Body/unshareAccommodations/request/shareChains/shareRoomDetails[" + index + "]/unSharedRoomDetail/freezeId", value);
    }

    public void setInventoryTrackingId(String index, String value) {
        setRequestNodeValueByXPath("/Envelope/Body/unshareAccommodations/request/shareChains/shareRoomDetails[" + index + "]/unSharedRoomDetail/InventoryTrackingId", value);
    }

    public void setGuaranteeStatus(String index, String value) {
        setRequestNodeValueByXPath("/Envelope/Body/unshareAccommodations/request/shareChains/shareRoomDetails[" + index + "]/unSharedRoomDetail/guaranteeStatus", value);
    }

    public void setInventoryStatus(String index, String value) {
        setRequestNodeValueByXPath("/Envelope/Body/unshareAccommodations/request/shareChains/shareRoomDetails[" + index + "]/unSharedRoomDetail/inventoryStatus", value);
    }

    public void setModificationDate(String index, String value) {
        setRequestNodeValueByXPath("/Envelope/Body/unshareAccommodations/request/shareChains/shareRoomDetails[" + index + "]/unSharedRoomDetail/modificationDate", value);
    }

    public void setOverrideFreeze(String index, String value) {
        setRequestNodeValueByXPath("/Envelope/Body/unshareAccommodations/request/shareChains/shareRoomDetails[" + index + "]/unSharedRoomDetail/overideFreeze", value);
    }

    public void setPackageCode(String index, String value) {
        setRequestNodeValueByXPath("/Envelope/Body/unshareAccommodations/request/shareChains/shareRoomDetails[" + index + "]/unSharedRoomDetail/packageCode", value);
    }

    public void setRateCategoryCode(String index, String value) {
        setRequestNodeValueByXPath("/Envelope/Body/unshareAccommodations/request/shareChains/shareRoomDetails[" + index + "]/unSharedRoomDetail/rateCategoryCode", value);
    }

    public void setAdditionalCharge(String index, String value) {
        setRequestNodeValueByXPath("/Envelope/Body/unshareAccommodations/request/shareChains/shareRoomDetails[" + index + "]/unSharedRoomDetail/rateDetails/additionalCharge", value);
    }

    public void setAdditionalChargeOverride(String index, String value) {
        setRequestNodeValueByXPath("/Envelope/Body/unshareAccommodations/request/shareChains/shareRoomDetails[" + index + "]/unSharedRoomDetail/rateDetails/additionalChargeOverridden", value);
    }

    public void setRateDetailsBasePrice(String index, String value) {
        setRequestNodeValueByXPath("/Envelope/Body/unshareAccommodations/request/shareChains/shareRoomDetails[" + index + "]/unSharedRoomDetail/rateDetails/basePrice", value);
    }

    public void setRateDetailsDate(String index, String value) {
        setRequestNodeValueByXPath("/Envelope/Body/unshareAccommodations/request/shareChains/shareRoomDetails[" + index + "]/unSharedRoomDetail/rateDetails/date", value);
    }

    public void setRateDetailsDayCount(String index, String value) {
        setRequestNodeValueByXPath("/Envelope/Body/unshareAccommodations/request/shareChains/shareRoomDetails[" + index + "]/unSharedRoomDetail/rateDetails/dayCount", value);
    }

    public void setRateDetailsOverriden(String index, String value) {
        setRequestNodeValueByXPath("/Envelope/Body/unshareAccommodations/request/shareChains/shareRoomDetails[" + index + "]/unSharedRoomDetail/rateDetails/overidden", value);
    }

    public void setRateDetailsShared(String index, String value) {
        setRequestNodeValueByXPath("/Envelope/Body/unshareAccommodations/request/shareChains/shareRoomDetails[" + index + "]/unSharedRoomDetail/rateDetails/shared", value);
    }

    public void setShared(String index, String value) {
        setRequestNodeValueByXPath("/Envelope/Body/unshareAccommodations/request/shareChains/shareRoomDetails[" + index + "]/unSharedRoomDetail/shared", value);
    }

    public void setRackRateDate(String index, String value) {
        setRequestNodeValueByXPath("/Envelope/Body/unshareAccommodations/request/shareChains/shareRoomDetails[" + index + "]/unSharedRoomDetail/rateDetails/rackRate/date", value);
    }

    public void setRackRateRate(String index, String value) {
        setRequestNodeValueByXPath("/Envelope/Body/unshareAccommodations/request/shareChains/shareRoomDetails[" + index + "]/unSharedRoomDetail/rateDetails/rackRate/rate", value);
    }

    public void setNetPrice(String index, String value) {
        setRequestNodeValueByXPath("/Envelope/Body/unshareAccommodations/request/shareChains/shareRoomDetails[" + index + "]/unSharedRoomDetail/rateDetails/netPrice", value);
    }

    public void setPointsValue(String index, String value) {
        setRequestNodeValueByXPath("/Envelope/Body/unshareAccommodations/request/shareChains/shareRoomDetails[" + index + "]/unSharedRoomDetail/rateDetails/pointsValue", value);
    }

    public void setCode(String index, String value) {
        setRequestNodeValueByXPath("/Envelope/Body/unshareAccommodations/request/shareChains/shareRoomDetails[" + index + "]/unSharedRoomDetail/resortCode", value);
    }

    public void setResortEndDate(String index, String value) {
        setRequestNodeValueByXPath("/Envelope/Body/unshareAccommodations/request/shareChains/shareRoomDetails[" + index + "]/unSharedRoomDetail/resortPeriod/endDate", value);
    }

    public void setResortStartDate(String index, String value) {
        setRequestNodeValueByXPath("/Envelope/Body/unshareAccommodations/request/shareChains/shareRoomDetails[" + index + "]/unSharedRoomDetail/resortPeriod/startDate", value);
    }

    public void setRoomNumber(String index, String value) {
        setRequestNodeValueByXPath("/Envelope/Body/unshareAccommodations/request/shareChains/shareRoomDetails[" + index + "]/unSharedRoomDetail/roomNumber", value);
    }

    public void setCommentsCreatedBy(String index, String value) {
        setRequestNodeValueByXPath("/Envelope/Body/unshareAccommodations/request/shareChains/shareRoomDetails[" + index + "]/unSharedRoomDetail/roomReservationDetail/comments/auditDetail/createdBy", value);
    }

    public void setCommentsCreatedDate(String index, String value) {
        setRequestNodeValueByXPath("/Envelope/Body/unshareAccommodations/request/shareChains/shareRoomDetails[" + index + "]/unSharedRoomDetail/roomReservationDetail/comments/auditDetail/createdDate", value);
    }

    public void setCommentsUpdatedBy(String index, String value) {
        setRequestNodeValueByXPath("/Envelope/Body/unshareAccommodations/request/shareChains/shareRoomDetails[" + index + "]/unSharedRoomDetail/roomReservationDetail/comments/auditDetail/updatedBy", value);
    }

    public void setCommentsUpdateDate(String index, String value) {
        setRequestNodeValueByXPath("/Envelope/Body/unshareAccommodations/request/shareChains/shareRoomDetails[" + index + "]/unSharedRoomDetail/roomReservationDetail/comments/auditDetail/updatedDate", value);
    }

    public void setCommentsStatus(String index, String value) {
        setRequestNodeValueByXPath("/Envelope/Body/unshareAccommodations/request/shareChains/shareRoomDetails[" + index + "]/unSharedRoomDetail/roomReservationDetail/comments/auditDetail/status", value);
    }

    public void setCommentsCommentText(String index, String value) {
        setRequestNodeValueByXPath("/Envelope/Body/unshareAccommodations/request/shareChains/shareRoomDetails[" + index + "]/unSharedRoomDetail/roomReservationDetail/comments/commentText", value);
    }

    public void setResortCode(String index, String value) {
        setRequestNodeValueByXPath("/Envelope/Body/unshareAccommodations/request/shareChains/shareRoomDetails[" + index + "]/unSharedRoomDetail/resortCode", value);
    }

    public void setCmmentsDefault(String index, String value) {
        setRequestNodeValueByXPath("/Envelope/Body/unshareAccommodations/request/shareChains/shareRoomDetails[" + index + "]/unSharedRoomDetail/roomReservationDetail/comments/default", value);
    }

    public void setCommentsFrom(String index, String value) {
        setRequestNodeValueByXPath("/Envelope/Body/unshareAccommodations/request/shareChains/shareRoomDetails[" + index + "]/unSharedRoomDetail/roomReservationDetail/comments/from", value);
    }

    public void setCommentsRoutingName(String index, String value) {
        setRequestNodeValueByXPath("/Envelope/Body/unshareAccommodations/request/shareChains/shareRoomDetails[" + index + "]/unSharedRoomDetail/roomReservationDetail/comments/routings/name", value);
    }

    public void setCommentsTo(String index, String value) {
        setRequestNodeValueByXPath("/Envelope/Body/unshareAccommodations/request/shareChains/shareRoomDetails[" + index + "]/unSharedRoomDetail/roomReservationDetail/comments/to", value);
    }

    public void setGuestAge(String index, String value) {
        setRequestNodeValueByXPath("/Envelope/Body/unshareAccommodations/request/shareChains/shareRoomDetails[" + index + "]/unSharedRoomDetail/roomReservationDetail/guestReferenceDetails/age", value);
    }

    public void setGuestType(String index, String value) {
        setRequestNodeValueByXPath("/Envelope/Body/unshareAccommodations/request/shareChains/shareRoomDetails[" + index + "]/unSharedRoomDetail/roomReservationDetail/guestReferenceDetails/ageType", value);
    }

    public void setGuestSuffix(String index, String value) {
        setRequestNodeValueByXPath("/Envelope/Body/unshareAccommodations/request/shareChains/shareRoomDetails[" + index + "]/unSharedRoomDetail/roomReservationDetail/guestReferenceDetails/guest/suffix", value);
    }

    public void setGuestTitle(String index, String value) {
        setRequestNodeValueByXPath("/Envelope/Body/unshareAccommodations/request/shareChains/shareRoomDetails[" + index + "]/unSharedRoomDetail/roomReservationDetail/guestReferenceDetails/guest/title", value);
    }

    public void setGuestFirstName(String index, String value) {
        setRequestNodeValueByXPath("/Envelope/Body/unshareAccommodations/request/shareChains/shareRoomDetails[" + index + "]/unSharedRoomDetail/roomReservationDetail/guestReferenceDetails/guest/firstName", value);
    }

    public void setGuestLastName(String index, String value) {
        setRequestNodeValueByXPath("/Envelope/Body/unshareAccommodations/request/shareChains/shareRoomDetails[" + index + "]/unSharedRoomDetail/roomReservationDetail/guestReferenceDetails/guest/lastName", value);
    }

    public void setGuestMiddleName(String index, String value) {
        setRequestNodeValueByXPath("/Envelope/Body/unshareAccommodations/request/shareChains/shareRoomDetails[" + index + "]/unSharedRoomDetail/roomReservationDetail/guestReferenceDetails/guest/middleName", value);
    }

    public void setGuestPartyId(String index, String value) {
        setRequestNodeValueByXPath("/Envelope/Body/unshareAccommodations/request/shareChains/shareRoomDetails[" + index + "]/unSharedRoomDetail/roomReservationDetail/guestReferenceDetails/guest/partyId", value);
    }

    public void setGuestLocatorId(String index, String value) {
        setRequestNodeValueByXPath("/Envelope/Body/unshareAccommodations/request/shareChains/shareRoomDetails[" + index + "]/unSharedRoomDetail/roomReservationDetail/guestReferenceDetails/guest/phoneDetails/locatorId", value);
    }

    public void setGuestDoNotMailIndicator(String index, String value) {
        setRequestNodeValueByXPath("/Envelope/Body/unshareAccommodations/request/shareChains/shareRoomDetails[" + index + "]/unSharedRoomDetail/roomReservationDetail/guestReferenceDetails/guest/doNotMailIndicator", value);
    }

    public void setGuestDoNotPhoneIndicator(String index, String value) {
        setRequestNodeValueByXPath("/Envelope/Body/unshareAccommodations/request/shareChains/shareRoomDetails[" + index + "]/unSharedRoomDetail/roomReservationDetail/guestReferenceDetails/guest/doNotPhoneIndicator", value);
    }

    public void setGuestId(String index, String value) {
        setRequestNodeValueByXPath("/Envelope/Body/unshareAccommodations/request/shareChains/shareRoomDetails[" + index + "]/unSharedRoomDetail/roomReservationDetail/guestReferenceDetails/guest/guestId", value);
    }

    public void setGuestDclGuestId(String index, String value) {
        setRequestNodeValueByXPath("/Envelope/Body/unshareAccommodations/request/shareChains/shareRoomDetails[" + index + "]/unSharedRoomDetail/roomReservationDetail/guestReferenceDetails/guest/dclGuestId", value);
    }

    public void setGuestActive(String index, String value) {
        setRequestNodeValueByXPath("/Envelope/Body/unshareAccommodations/request/shareChains/shareRoomDetails[" + index + "]/unSharedRoomDetail/roomReservationDetail/guestReferenceDetails/guest/active", value);
    }

    public void setGuestPurposeOfVisit(String index, String value) {
        setRequestNodeValueByXPath("/Envelope/Body/unshareAccommodations/request/shareChains/shareRoomDetails[" + index + "]/unSharedRoomDetail/roomReservationDetail/guestReferenceDetails/purposeOfVisit", value);
    }

    public void setCorrelationId(String index, String value) {
        setRequestNodeValueByXPath("/Envelope/Body/unshareAccommodations/request/shareChains/shareRoomDetails[" + index + "]/unSharedRoomDetail/roomReservationDetail/guestReferenceDetails/correlationID", value);
    }

    public void setRoomTypeCode(String index, String value) {
        setRequestNodeValueByXPath("/Envelope/Body/unshareAccommodations/request/shareChains/shareRoomDetails[" + index + "]/unSharedRoomDetail/roomTypeCode", value);
    }

    public void setRsrReservation(String index, String value) {
        setRequestNodeValueByXPath("/Envelope/Body/unshareAccommodations/request/shareChains/shareRoomDetails[" + index + "]/unSharedRoomDetail/rsrReservation", value);
    }

    public void setTcgId(String index, String value) {
        setRequestNodeValueByXPath("/Envelope/Body/unshareAccommodations/request/shareChains/shareRoomDetails[" + index + "]/unSharedRoomDetail/travelComponentGroupingId", value);
    }

    public void setTcId(String index, String value) {
        setRequestNodeValueByXPath("/Envelope/Body/unshareAccommodations/request/shareChains/shareRoomDetails[" + index + "]/unSharedRoomDetail/travelComponentId", value);
    }

    public void setTravelStatus(String index, String value) {
        setRequestNodeValueByXPath("/Envelope/Body/unshareAccommodations/request/shareChains/shareRoomDetails[" + index + "]/unSharedRoomDetail/travelStatus", value);
    }

    public void setUnsharedRoomTpsId(String index, String value) {
        setRequestNodeValueByXPath("/Envelope/Body/unshareAccommodations/request/shareChains/shareRoomDetails[" + index + "]/travelPlanSegmentId", value);
    }

}