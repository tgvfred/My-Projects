package com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations;

import static com.disney.api.soapServices.accommodationModule.helpers.AccommodationBaseTest.getAgeTypeByAge;
import static com.disney.api.soapServices.accommodationModule.helpers.AccommodationBaseTest.isValid;

import com.disney.AutomationException;
import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.AccommodationSalesServicePort;
import com.disney.utils.Randomness;
import com.disney.utils.XMLTools;
import com.disney.utils.dataFactory.guestFactory.Address;
import com.disney.utils.dataFactory.guestFactory.Email;
import com.disney.utils.dataFactory.guestFactory.Guest;
import com.disney.utils.dataFactory.guestFactory.Phone;

public class ReplaceAllForTravelPlanSegment extends AccommodationSalesServicePort {
    public ReplaceAllForTravelPlanSegment(String environment, String scenario) {
        super(environment);
        // Generate a request from a project xml file
        setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("replaceAllForTravelPlanSegment")));
        generateServiceContext();
        setRequestNodeValueByXPath(getTestScenario(getService(), getOperation(), scenario));
        removeComments();
        removeWhiteSpace();
    }

    public void setResExternalReference(String code, String number, String source, String type) {
        String baseXpath = "//replaceAllForTravelPlanSegment/request/resExternalReferences/";
        setExtRefCode(baseXpath, code);
        setExtRefNumber(baseXpath, number);
        setExtRefSource(baseXpath, source);
        setExtRefType(baseXpath, type);
    }

    public void setExternalReference(String code, String number, String source, String type) {
        String baseXpath = "//replaceAllForTravelPlanSegment/request/externalReference/";
        setExtRefCode(baseXpath, code);
        setExtRefNumber(baseXpath, number);
        setExtRefSource(baseXpath, source);
        setExtRefType(baseXpath, type);
    }

    public void setInternalComments(String commentText, String defaultIndicator, String from, String rountingName, String to, String auditStatus) {
        String baseXpath = "//replaceAllForTravelPlanSegment/request/internalComments/";
        setCommentsCommentText(baseXpath, commentText);
        setCommentsDefault(baseXpath, defaultIndicator);
        setCommentsFrom(baseXpath, from);
        setCommentsRountingsName(baseXpath, rountingName);
        setCommentsTo(baseXpath, to);
        setAuditDetails(baseXpath, "AutoJUnit.us", Randomness.generateCurrentXMLDate(), "AutoJUnit.us", Randomness.generateCurrentXMLDate(), auditStatus);
    }

    public void setReservationDetail_Comments(String commentText, String defaultIndicator, String from, String rountingName, String to, String auditStatus) {
        String baseXpath = "//replaceAllForTravelPlanSegment/request/reservationDetail/comments/";
        setCommentsCommentText(baseXpath, commentText);
        setCommentsDefault(baseXpath, defaultIndicator);
        setCommentsFrom(baseXpath, from);
        setCommentsRountingsName(baseXpath, rountingName);
        setCommentsTo(baseXpath, to);
        setAuditDetails(baseXpath, "AutoJUnit.us", Randomness.generateCurrentXMLDate(), "AutoJUnit.us", Randomness.generateCurrentXMLDate(), auditStatus);
    }

    public void setReservationDetail_GuestRefDetails(Guest guest) {
        String baseXpath = "//replaceAllForTravelPlanSegment/request/reservationDetail/guestReferenceDetails/guest";
        setRequestNodeValueByXPath(baseXpath + "/age", guest.getAge());
        setRequestNodeValueByXPath(baseXpath + "/ageType", getAgeTypeByAge(guest.getAge()));
        setGuest(baseXpath, guest);
    }

    public void setReservationDetail_GuestRefDetails_ExpMediaDetails(String id, String optOut, String optOutReason, String color, String printedName, String optOutAvailable) {
        String baseXpath = "//replaceAllForTravelPlanSegment/request/reservationDetail/guestReferenceDetails";
        setExperienceMediaDetailsId(baseXpath, id);
        setExperienceMediaDetailsOptOut(baseXpath, optOut);
        setExperienceMediaDetailsOptOutReason(baseXpath, optOutReason);
        setExperienceMediaDetailsColor(baseXpath, color);
        setExperienceMediaDetailsPrintedName(baseXpath, printedName);
        setExperienceMediaDetailsOptOutAvailable(baseXpath, optOutAvailable);
    }

    public void setReservationDetail_Profiles(String code, String description, String id, String name, String profileType, String selectable, String status, String type) {
        String baseXpath = "//replaceAllForTravelPlanSegment/request/reservationDetail";
        setProfileCode(baseXpath, code);
        setProfileDescription(baseXpath, description);
        setProfileId(baseXpath, id);
        setProfileName(baseXpath, name);
        setProfileProfileType(baseXpath, profileType);
        setProfileSelectable(baseXpath, selectable);
        setProfileType(baseXpath, type);
        setAuditDetails(baseXpath + "/profiles", "AutoJUnit.us", Randomness.generateCurrentXMLDate(), "AutoJUnit.us", Randomness.generateCurrentXMLDate(), status);
    }

    public void setTravelAgent_MembershipDetails(String expDate, String membershipType, String membershipId, String policyId, String prodChannelId, String guestMembershipId) {
        String baseXpath = "//replaceAllForTravelPlanSegment/request/travelAgent/membershipDetail";
        setMembershipDetailsExpirationDate(baseXpath, expDate);
        setMembershipDetailsGuestMembershipId(baseXpath, guestMembershipId);
        setMembershipDetailsMembershipId(baseXpath, membershipId);
        setMembershipDetailsMembershipType(baseXpath, membershipType);
        setMembershipDetailsPolicyId(baseXpath, policyId);
        setMembershipDetailsProdChannelId(baseXpath, prodChannelId);
    }

    public void setTravelAgent_GuestIdReferences(String type, String value) {
        String baseXpath = "//replaceAllForTravelPlanSegment/request/travelAgent";
        setGuestIdReferencesType(baseXpath, type);
        setGuestIdReferencesValue(baseXpath, value);
    }

    public void setTravelPlanGuest(Guest guest) {
        String baseXpath = "//replaceAllForTravelPlanSegment/request/travelPlanGuest";
        setGuest(baseXpath, guest);
    }

    public void setRoomDetails_SourceExtRef(String code, String number, String source, String type) {
        String baseXpath = "//replaceAllForTravelPlanSegment/request/roomDetails/sourceExternalReference/";
        setExtRefCode(baseXpath, code);
        setExtRefNumber(baseXpath, number);
        setExtRefSource(baseXpath, source);
        setExtRefType(baseXpath, type);
    }

    public void setRoomDetails_TicketDetails(Guest guest, String autditDetailsStatus, String baseAdmissionProductId, String code, String selectable, String ticketDescription) {
        String baseXpath = "//replaceAllForTravelPlanSegment/request/roomDetails/ticketDetails/";
        setRequestNodeValueByXPath(baseXpath + "baseAdmissionProductId", baseAdmissionProductId);
        setRequestNodeValueByXPath(baseXpath + "code", code);
        setRequestNodeValueByXPath(baseXpath + "selectable", selectable);
        setRequestNodeValueByXPath(baseXpath + "ticketDescription", ticketDescription);
        setAuditDetails(baseXpath, "AutoJUnit.us", Randomness.generateCurrentXMLDate(), "AutoJUnit.us", Randomness.generateCurrentXMLDate(), autditDetailsStatus);
        setRequestNodeValueByXPath(baseXpath + "guestReference/age", guest.getAge());
        setRequestNodeValueByXPath(baseXpath + "guestReference/ageType", getAgeTypeByAge(guest.getAge()));
        setGuest(baseXpath + "guestReference/guest/", guest);
    }

    public void setRoomDetails_TicketDetails_ExpMediaDetails(String id, String optOut, String optOutReason, String color, String printedName, String optOutAvailable) {
        String baseXpath = "//replaceAllForTravelPlanSegment/request/roomDetails/ticketDetails/guestReference/";
        setExperienceMediaDetailsId(baseXpath, id);
        setExperienceMediaDetailsOptOut(baseXpath, optOut);
        setExperienceMediaDetailsOptOutReason(baseXpath, optOutReason);
        setExperienceMediaDetailsColor(baseXpath, color);
        setExperienceMediaDetailsPrintedName(baseXpath, printedName);
        setExperienceMediaDetailsOptOutAvailable(baseXpath, optOutAvailable);
    }

    public void setRoomDetails_RoomReservationDetail_Comments(String auditStatus, String commentText, String defaultIndicator, String from, String routingsName, String to) {
        String baseXpath = "//replaceAllForTravelPlanSegment/request/roomDetails/roomReservationDetail/comments/";
        setAuditDetails(baseXpath, "AutoJUnit.us", Randomness.generateCurrentDatetime(), "AutoJUnit.us", Randomness.generateCurrentDatetime(), auditStatus);
        setCommentsCommentText(baseXpath, commentText);
        setCommentsDefault(baseXpath, defaultIndicator);
        setCommentsFrom(baseXpath, from);
        setCommentsRountingsName(baseXpath, routingsName);
        setCommentsTo(baseXpath, to);
    }

    public void setRoomDetails_RoomReservationDetail_GuestRefDetails(Guest guest) {
        String baseXpath = "//replaceAllForTravelPlanSegment/request/roomDetails/roomReservationDetail/guestReferenceDetails/";
        setRequestNodeValueByXPath(baseXpath + "/age", guest.getAge());
        setRequestNodeValueByXPath(baseXpath + "/ageType", getAgeTypeByAge(guest.getAge()));
        setGuest(baseXpath + "guest/", guest);
    }

    public void setRoomDetails_RoomReservationDetail_GuestRefDetails_MembershipDetails(String expDate, String membershipType, String membershipId, String policyId, String prodChannelId, String guestMembershipId) {
        String baseXpath = "//replaceAllForTravelPlanSegment/request/roomDetails/roomReservationDetail/guestReferenceDetails/guest/";
        setMembershipDetailsExpirationDate(baseXpath, expDate);
        setMembershipDetailsGuestMembershipId(baseXpath, guestMembershipId);
        setMembershipDetailsMembershipId(baseXpath, membershipId);
        setMembershipDetailsMembershipType(baseXpath, membershipType);
        setMembershipDetailsPolicyId(baseXpath, policyId);
        setMembershipDetailsProdChannelId(baseXpath, prodChannelId);
    }

    public void setRoomDetails_RoomReservationDetail_GuestRefDetails_GuestIdRefs(String type, String value) {
        String baseXpath = "//replaceAllForTravelPlanSegment/request/roomDetails/roomReservationDetail/guestReferenceDetails/guest";
        setGuestIdReferencesType(baseXpath, type);
        setGuestIdReferencesValue(baseXpath, value);
    }

    public void setRoomDetails_RoomReservationDetail_GuestRefDetails_ExpMediaDetails(String id, String optOut, String optOutReason, String color, String printedName, String optOutAvailable) {
        String baseXpath = "//replaceAllForTravelPlanSegment/request/roomDetails/roomReservationDetail/guestReferenceDetails/";
        setExperienceMediaDetailsId(baseXpath, id);
        setExperienceMediaDetailsOptOut(baseXpath, optOut);
        setExperienceMediaDetailsOptOutReason(baseXpath, optOutReason);
        setExperienceMediaDetailsColor(baseXpath, color);
        setExperienceMediaDetailsPrintedName(baseXpath, printedName);
        setExperienceMediaDetailsOptOutAvailable(baseXpath, optOutAvailable);
    }

    public void setRoomDetails_RoomReservationDetail_GuestRefDetails_Profiles(String code, String description, String id, String name, String profileType, String selectable, String status, String type) {
        String baseXpath = "//replaceAllForTravelPlanSegment/request/roomDetails/roomReservationDetail/profiles/";
        setAuditDetails(baseXpath, "AutoJUnit.us", Randomness.generateCurrentXMLDate(), "AutoJUnit.us", Randomness.generateCurrentXMLDate(), status);
        setProfileCode(baseXpath, code);
        setProfileDescription(baseXpath, description);
        setProfileId(baseXpath, id);
        setProfileName(baseXpath, name);
        setProfileProfileType(baseXpath, profileType);
        setProfileSelectable(baseXpath, selectable);
        setProfileType(baseXpath, type);
    }

    public void setRoomDetails_ExternalRefs(String code, String number, String source, String type) {
        String baseXpath = "//replaceAllForTravelPlanSegment/request/roomDetails/externalReferences/";
        setExtRefCode(baseXpath, code);
        setExtRefNumber(baseXpath, number);
        setExtRefSource(baseXpath, source);
        setExtRefType(baseXpath, type);
    }

    public void setConfirmationDetails(String id, String indicator, String type, String defaultConfirmationIndicator, String individual, String jdoSeqNumber, String locatorId, String partyId, Guest guest) {
        String baseXpath = "//replaceAllForTravelPlanSegment/request/confirmationDetails/guestDetail/";
        setConfirmationDetails_ConfirmationDetailId(indicator);
        setConfirmationDetails_ConfirmationIndicator(indicator);
        setConfirmationDetails_ConfirmationType(type);
        setConfirmationDetails_DefaultConfirmationIndicator(defaultConfirmationIndicator);
        setConfirmationDetails_Individual(individual);
        setConfirmationDetails_JdoSequenceNumber(jdoSeqNumber);
        setConfirmationDetails_LocatorId(locatorId);
        setConfirmationDetails_PartyId(partyId);
        setGuest(baseXpath, guest);
    }

    private void setConfirmationDetails_ConfirmationDetailId(String value) {
        String xpath = "//replaceAllForTravelPlanSegment/request/confirmationDetails/confirmationDetailId";
        if (isValid(value)) {
            setRequestNodeValueByXPath(xpath, value);
        } else {
            setRequestNodeValueByXPath(xpath, "0");
        }
    }

    private void setConfirmationDetails_ConfirmationIndicator(String value) {
        String xpath = "//replaceAllForTravelPlanSegment/request/confirmationDetails/confirmationIndicator";
        if (isValid(value)) {
            setRequestNodeValueByXPath(xpath, value);
        } else {
            setRequestNodeValueByXPath(xpath, "0");
        }
    }

    private void setConfirmationDetails_ConfirmationType(String value) {
        String xpath = "//replaceAllForTravelPlanSegment/request/confirmationDetails/confirmationType";
        if (isValid(value)) {
            setRequestNodeValueByXPath(xpath, value);
        } else {
            setRequestNodeValueByXPath(xpath, "Email");
        }
    }

    private void setConfirmationDetails_DefaultConfirmationIndicator(String value) {
        String xpath = "//replaceAllForTravelPlanSegment/request/confirmationDetails/defaultConfirmationIndicator";
        if (isValid(value)) {
            setRequestNodeValueByXPath(xpath, value);
        } else {
            setRequestNodeValueByXPath(xpath, "Email");
        }
    }

    private void setConfirmationDetails_Individual(String value) {
        String xpath = "//replaceAllForTravelPlanSegment/request/confirmationDetails/individual";
        if (isValid(value)) {
            setRequestNodeValueByXPath(xpath, value);
        } else {
            setRequestNodeValueByXPath(xpath, "false");
        }
    }

    private void setConfirmationDetails_JdoSequenceNumber(String value) {
        String xpath = "//replaceAllForTravelPlanSegment/request/confirmationDetails/jdoSequenceNumber";
        if (isValid(value)) {
            setRequestNodeValueByXPath(xpath, value);
        } else {
            setRequestNodeValueByXPath(xpath, "0");
        }
    }

    private void setConfirmationDetails_LocatorId(String value) {
        String xpath = "//replaceAllForTravelPlanSegment/request/confirmationDetails/locatorId";
        if (isValid(value)) {
            setRequestNodeValueByXPath(xpath, value);
        } else {
            setRequestNodeValueByXPath(xpath, "0");
        }
    }

    private void setConfirmationDetails_PartyId(String value) {
        String xpath = "//replaceAllForTravelPlanSegment/request/confirmationDetails/partyId";
        if (isValid(value)) {
            setRequestNodeValueByXPath(xpath, value);
        } else {
            setRequestNodeValueByXPath(xpath, "0");
        }
    }

    private void setGuest(String baseXpath, Guest guest) {
        if (isValid(guest)) {
            throw new AutomationException("The guest object cannot be null");
        }
        // setRequestNodeValueByXPath(baseXpath + "/suffix", guest.getSuffix());
        setRequestNodeValueByXPath(baseXpath + "/title", guest.getTitle());
        setRequestNodeValueByXPath(baseXpath + "/firstName", guest.getFirstName());
        setRequestNodeValueByXPath(baseXpath + "/lastName", guest.getLastName());
        setRequestNodeValueByXPath(baseXpath + "/middleName", guest.getMiddleName());
        setRequestNodeValueByXPath(baseXpath + "/partyId", guest.getPartyId());
        setRequestNodeValueByXPath(baseXpath + "/doNotMailIndicator", "true");
        setRequestNodeValueByXPath(baseXpath + "/doNotPhoneIndicator", "true");
        setRequestNodeValueByXPath(baseXpath + "/preferredLanguage", guest.getLanguagePreference());
        setRequestNodeValueByXPath(baseXpath + "/dclGuestId", "0");
        setRequestNodeValueByXPath(baseXpath + "/guestId", guest.getGuestId());
        setRequestNodeValueByXPath(baseXpath + "/active", "true");
        setRequestNodeValueByXPath(baseXpath + "/dob", guest.getBirthDate());
        setGuestPhone(baseXpath, guest.primaryPhone());
        setGuestAddress(baseXpath, guest.primaryAddress());
        setGuestEmail(baseXpath, guest.primaryEmail());

    }

    private void setGuestPhone(String baseXpath, Phone phone) {
        if (isValid(phone)) {
            throw new AutomationException("The phone object cannot be null");
        }
        setRequestNodeValueByXPath(baseXpath + "/phoneDetails/locatorId", phone.getLocatorId());
        setRequestNodeValueByXPath(baseXpath + "/phoneDetails/guestLocatorId", "0");
        setRequestNodeValueByXPath(baseXpath + "/phoneDetails/primary", "true");
        setRequestNodeValueByXPath(baseXpath + "/phoneDetails/deviceType", "HANDSET");
        setRequestNodeValueByXPath(baseXpath + "/phoneDetails/extension", "0");
        setRequestNodeValueByXPath(baseXpath + "/phoneDetails/number", phone.getNumber());
    }

    private void setGuestAddress(String baseXpath, Address address) {
        if (isValid(address)) {
            throw new AutomationException("The address object cannot be null");
        }
        setRequestNodeValueByXPath(baseXpath + "/addressDetails/locatorId", address.getLocatorId());
        setRequestNodeValueByXPath(baseXpath + "/addressDetails/guestLocatorId", "0");
        setRequestNodeValueByXPath(baseXpath + "/addressDetails/primary", "true");
        setRequestNodeValueByXPath(baseXpath + "/addressDetails/addressLine1", address.getAddress1());
        setRequestNodeValueByXPath(baseXpath + "/addressDetails/city", address.getCity());
        setRequestNodeValueByXPath(baseXpath + "/addressDetails/country", address.getCountry());
        setRequestNodeValueByXPath(baseXpath + "/addressDetails/postalCode", address.getZipCode());
        setRequestNodeValueByXPath(baseXpath + "/addressDetails/state", address.getState());
        setRequestNodeValueByXPath(baseXpath + "/addressDetails/regionName", address.getStateAbbv());
    }

    private void setGuestEmail(String baseXpath, Email email) {
        if (email == null) {
            throw new AutomationException("The email object cannot be null");
        }
        setRequestNodeValueByXPath(baseXpath + "/emailDetails/locatorId", email.getLocatorId());
        setRequestNodeValueByXPath(baseXpath + "/emailDetails/guestLocatorId", "0");
        setRequestNodeValueByXPath(baseXpath + "/emailDetails/primary", "true");
        setRequestNodeValueByXPath(baseXpath + "/emailDetails/address", email.getEmail());
    }

    private void setAuditDetails(String baseXpath, String createdBy, String createdDate, String updatedBy, String updatedDate, String status) {
        setAuditDetailsCreatedBy(baseXpath, createdBy);
        setAuditDetailsCreatedDate(baseXpath, createdDate);
        setAuditDetailsUpdatedBy(baseXpath, updatedBy);
        setAuditDetailsUpdatedDate(baseXpath, updatedDate);
        setAuditDetailsStatus(baseXpath, status);
    }

    public void setReservationDetail_GuestRefDetails_Guest_MembershipDetails(String expDate, String membershipType, String membershipId, String policyId, String prodChannelId, String guestMembershipId) {
        String baseXpath = "//replaceAllForTravelPlanSegment/request/reservationDetail/guestReferenceDetails/guest";
        setMembershipDetailsExpirationDate(baseXpath, expDate);
        setMembershipDetailsGuestMembershipId(baseXpath, guestMembershipId);
        setMembershipDetailsMembershipId(baseXpath, membershipId);
        setMembershipDetailsMembershipType(baseXpath, membershipType);
        setMembershipDetailsPolicyId(baseXpath, policyId);
        setMembershipDetailsProdChannelId(baseXpath, prodChannelId);
    }

    public void setRoomDetailsTeamName(String value) {
        setRequestNodeValueByXPath("//replaceAllForTravelPlanSegment/request/roomDetails/teamName", value);
    }

    public void setRoomDetailsTicketGroup(String value) {
        setRequestNodeValueByXPath("//replaceAllForTravelPlanSegment/request/roomDetails/ticketGroup", value);
    }

    public void setRoomDetailsTravelComponentGroupingId(String value) {
        setRequestNodeValueByXPath("//replaceAllForTravelPlanSegment/request/roomDetails/travelComponentGroupingId", value);
    }

    public void setRoomDetailsTravelComponentId(String value) {
        setRequestNodeValueByXPath("//replaceAllForTravelPlanSegment/request/roomDetails/travelComponentId", value);
    }

    public void setRoomDetailsTravelStatus(String value) {
        setRequestNodeValueByXPath("//replaceAllForTravelPlanSegment/request/roomDetails/travelStatus", value);
    }

    public void setRoomDetailsBlockCode(String value) {
        setRequestNodeValueByXPath("//replaceAllForTravelPlanSegment/request/roomDetails/blockCode", value);
    }

    public void setRoomDetailsBookingDate(String value) {
        setRequestNodeValueByXPath("//replaceAllForTravelPlanSegment/request/roomDetails/bookingDate", value);
    }

    public void setRoomDetailsFreezeId(String value) {
        setRequestNodeValueByXPath("//replaceAllForTravelPlanSegment/request/roomDetails/freezeId", value);
    }

    public void setRoomDetailsInventoryTrackingId(String value) {
        setRequestNodeValueByXPath("//replaceAllForTravelPlanSegment/request/roomDetails/InventoryTrackingId", value);
    }

    public void setRoomDetailsGuaranteeStatus(String value) {
        setRequestNodeValueByXPath("//replaceAllForTravelPlanSegment/request/roomDetails/guaranteeStatus", value);
    }

    public void setRoomDetailsInventoryStatus(String value) {
        setRequestNodeValueByXPath("//replaceAllForTravelPlanSegment/request/roomDetails/inventoryStatus", value);
    }

    public void setRoomDetailsModificationDate(String value) {
        setRequestNodeValueByXPath("//replaceAllForTravelPlanSegment/request/roomDetails/modificationDate", value);
    }

    public void setRoomDetailsOverideFreeze(String value) {
        setRequestNodeValueByXPath("//replaceAllForTravelPlanSegment/request/roomDetails/overideFreeze", value);
    }

    public void setRoomDetailsPackageCode(String value) {
        setRequestNodeValueByXPath("//replaceAllForTravelPlanSegment/request/roomDetails/packageCode", value);
    }

    public void setRoomDetailsResortCode(String value) {
        setRequestNodeValueByXPath("//replaceAllForTravelPlanSegment/request/roomDetails/resortCode", value);
    }

    public void setRoomDetailsRoomTypeCode(String value) {
        setRequestNodeValueByXPath("//replaceAllForTravelPlanSegment/request/roomDetails/roomTypeCode", value);
    }

    public void setRoomDetailsRsrReservation(String value) {
        setRequestNodeValueByXPath("//replaceAllForTravelPlanSegment/request/roomDetails/rsrReservation", value);
    }

    public void setRoomDetailsSalesChannelId(String value) {
        setRequestNodeValueByXPath("//replaceAllForTravelPlanSegment/request/roomDetails/salesChannelId", value);
    }

    public void setRoomDetailsReservationType(String value) {
        setRequestNodeValueByXPath("//replaceAllForTravelPlanSegment/request/roomDetails/reservationType", value);
    }

    public void setRoomDetailsLocationId(String value) {
        setRequestNodeValueByXPath("//replaceAllForTravelPlanSegment/request/roomDetails/locationId", value);
    }

    public void setRoomDetailsShared(String value) {
        setRequestNodeValueByXPath("//replaceAllForTravelPlanSegment/request/roomDetails/shared", value);
    }

    public void setRoomDetailsSpecialNeedsRequested(String value) {
        setRequestNodeValueByXPath("//replaceAllForTravelPlanSegment/request/roomDetails/specialNeedsRequested", value);
    }

    public void setRoomDetails_ResortPeriodEndDate(String value) {
        setRequestNodeValueByXPath("//replaceAllForTravelPlanSegment/request/roomDetails/resortPeriod/endDate", value);
    }

    public void setRoomDetails_ResortPeriodStartDate(String value) {
        setRequestNodeValueByXPath("//replaceAllForTravelPlanSegment/request/roomDetails/resortPeriod/startDate", value);
    }

    public void setTaxExemptDetailCertificateNumber(String value) {
        setRequestNodeValueByXPath("//replaceAllForTravelPlanSegment/request/taxExemptDetail/taxExemptCertificateNumber", value);
    }

    public void setTaxExemptDetailType(String value) {
        setRequestNodeValueByXPath("//replaceAllForTravelPlanSegment/request/taxExemptDetail/taxExemptType", value);
    }

    public void setTravelAgencyAgencyIataNumber(String value) {
        setRequestNodeValueByXPath("//replaceAllForTravelPlanSegment/request/travelAgency/agencyIataNumber", value);
    }

    public void setTravelAgencyAgencyName(String value) {
        setRequestNodeValueByXPath("//replaceAllForTravelPlanSegment/request/travelAgency/agencyName", value);
    }

    public void setTravelAgencyAgencyOdsId(String value) {
        setRequestNodeValueByXPath("//replaceAllForTravelPlanSegment/request/travelAgency/agencyOdsId", value);
    }

    public void setTravelAgencyGuestTravelAgencyId(String value) {
        setRequestNodeValueByXPath("//replaceAllForTravelPlanSegment/request/travelAgency/guestTravelAgencyId", value);
    }

    public void setTravelAgencyAgentId(String value) {
        setRequestNodeValueByXPath("//replaceAllForTravelPlanSegment/request/travelAgency/agentId", value);
    }

    public void setTravelAgencyGuestAgentId(String value) {
        setRequestNodeValueByXPath("//replaceAllForTravelPlanSegment/request/travelAgency/guestAgentId", value);
    }

    public void setTravelAgencyConfirmationLocatorValue(String value) {
        setRequestNodeValueByXPath("//replaceAllForTravelPlanSegment/request/travelAgency/confirmationLocatorValue", value);
    }

    public void setTravelAgencyGuestConfirmationLocationId(String value) {
        setRequestNodeValueByXPath("//replaceAllForTravelPlanSegment/request/travelAgency/guestConfirmationLocationId", value);
    }

    public void setTravelAgencyConfirmationType(String value) {
        setRequestNodeValueByXPath("//replaceAllForTravelPlanSegment/request/travelAgency/confirmationType", value);
    }

    public void setTravelAgencyStatus(String value) {
        setRequestNodeValueByXPath("//replaceAllForTravelPlanSegment/request/travelAgency/status", value);
    }

    public void setTravelAgency_PrimaryAddressLocatorId(String value) {
        setRequestNodeValueByXPath("//replaceAllForTravelPlanSegment/request/travelAgency/primaryAddress/locatorId", value);
    }

    public void setTravelAgency_PrimaryAddressGuestLocatorId(String value) {
        setRequestNodeValueByXPath("//replaceAllForTravelPlanSegment/request/travelAgency/primaryAddress/guestLocatorId", value);
    }

    public void setTravelAgency_PrimaryAddressLocatorUseType(String value) {
        setRequestNodeValueByXPath("//replaceAllForTravelPlanSegment/request/travelAgency/primaryAddress/locatorUseType", value);
    }

    public void setTravelAgency_PrimaryAddressPrimary(String value) {
        setRequestNodeValueByXPath("//replaceAllForTravelPlanSegment/request/travelAgency/primaryAddress/primary", value);
    }

    public void setTravelAgency_PrimaryAddressAddressLine1(String value) {
        setRequestNodeValueByXPath("//replaceAllForTravelPlanSegment/request/travelAgency/primaryAddress/addressLine1", value);
    }

    public void setTravelAgency_PrimaryAddressCity(String value) {
        setRequestNodeValueByXPath("//replaceAllForTravelPlanSegment/request/travelAgency/primaryAddress/city", value);
    }

    public void setTravelAgency_PrimaryAddressCountry(String value) {
        setRequestNodeValueByXPath("//replaceAllForTravelPlanSegment/request/travelAgency/primaryAddress/country", value);
    }

    public void setTravelAgency_PrimaryAddressPostalCode(String value) {
        setRequestNodeValueByXPath("//replaceAllForTravelPlanSegment/request/travelAgency/primaryAddress/postalCode", value);
    }

    public void setTravelAgency_PrimaryAddressState(String value) {
        setRequestNodeValueByXPath("//replaceAllForTravelPlanSegment/request/travelAgency/primaryAddress/state", value);
    }

    public void setTravelAgency_PrimaryAddressRegionName(String value) {
        setRequestNodeValueByXPath("//replaceAllForTravelPlanSegment/request/travelAgency/primaryAddress/regionName", value);
    }

    public void setTravelAgentFirstName(String value) {
        setRequestNodeValueByXPath("//replaceAllForTravelPlanSegment/request/travelAgent/firstName", value);
    }

    public void setTravelAgentLastName(String value) {
        setRequestNodeValueByXPath("//replaceAllForTravelPlanSegment/request/travelAgent/lastName", value);
    }

    public void setTravelAgentMiddleName(String value) {
        setRequestNodeValueByXPath("//replaceAllForTravelPlanSegment/request/travelAgent/middleName", value);
    }

    public void setTravelAgentPartyId(String value) {
        setRequestNodeValueByXPath("//replaceAllForTravelPlanSegment/request/travelAgent/partyId", value);
    }

    public void setTravelAgent_PhoneDetailsLocatorId(String value) {
        setRequestNodeValueByXPath("//replaceAllForTravelPlanSegment/request/travelAgent/phoneDetails/locatorId", value);
    }

    public void setTravelAgent_PhoneDetailsGuestLocatorId(String value) {
        setRequestNodeValueByXPath("//replaceAllForTravelPlanSegment/request/travelAgent/phoneDetails/guestLocatorId", value);
    }

    public void setTravelAgent_PhoneDetailsPrimary(String value) {
        setRequestNodeValueByXPath("//replaceAllForTravelPlanSegment/request/travelAgent/phoneDetails/primary", value);
    }

    public void setTravelAgent_PhoneDetailsDeviceType(String value) {
        setRequestNodeValueByXPath("//replaceAllForTravelPlanSegment/request/travelAgent/phoneDetails/deviceType", value);
    }

    public void setTravelAgent_PhoneDetailsExtension(String value) {
        setRequestNodeValueByXPath("//replaceAllForTravelPlanSegment/request/travelAgent/phoneDetails/extension", value);
    }

    public void setTravelAgent_PhoneDetailsNumber(String value) {
        setRequestNodeValueByXPath("//replaceAllForTravelPlanSegment/request/travelAgent/phoneDetails/number", value);
    }

    public void setTravelAgent_AddressLocatorId(String value) {
        setRequestNodeValueByXPath("//replaceAllForTravelPlanSegment/request/travelAgent/addressDetails/locatorId", value);
    }

    public void setTravelAgent_AddressGuestLocatorId(String value) {
        setRequestNodeValueByXPath("//replaceAllForTravelPlanSegment/request/travelAgent/addressDetails/guestLocatorId", value);
    }

    public void setTravelAgent_AddressPrimary(String value) {
        setRequestNodeValueByXPath("//replaceAllForTravelPlanSegment/request/travelAgent/addressDetails/primary", value);
    }

    public void setTravelAgent_AddressAddressLine1(String value) {
        setRequestNodeValueByXPath("//replaceAllForTravelPlanSegment/request/travelAgent/addressDetails/addressLine1", value);
    }

    public void setTravelAgent_AddressCity(String value) {
        setRequestNodeValueByXPath("//replaceAllForTravelPlanSegment/request/travelAgent/addressDetails/city", value);
    }

    public void setTravelAgent_AddressCountry(String value) {
        setRequestNodeValueByXPath("//replaceAllForTravelPlanSegment/request/travelAgent/addressDetails/country", value);
    }

    public void setTravelAgent_AddressPostalCode(String value) {
        setRequestNodeValueByXPath("//replaceAllForTravelPlanSegment/request/travelAgent/addressDetails/postalCode", value);
    }

    public void setTravelAgent_AddressState(String value) {
        setRequestNodeValueByXPath("//replaceAllForTravelPlanSegment/request/travelAgent/addressDetails/state", value);
    }

    public void setTravelAgent_AddressRegionName(String value) {
        setRequestNodeValueByXPath("//replaceAllForTravelPlanSegment/request/travelAgent/addressDetails/regionName", value);
    }

    public void setTravelAgent_EmailDetailsLocatorId(String value) {
        setRequestNodeValueByXPath("//replaceAllForTravelPlanSegment/request/travelAgent/emailDetails/locatorId", value);
    }

    public void setTravelAgent_EmailDetailsGuestLocatorId(String value) {
        setRequestNodeValueByXPath("//replaceAllForTravelPlanSegment/request/travelAgent/emailDetails/guestLocatorId", value);
    }

    public void setTravelAgent_EmailDetailsLocatorUseType(String value) {
        setRequestNodeValueByXPath("//replaceAllForTravelPlanSegment/request/travelAgent/emailDetails/locatorUseType", value);
    }

    public void setTravelAgent_EmailDetailsPrimary(String value) {
        setRequestNodeValueByXPath("//replaceAllForTravelPlanSegment/request/travelAgent/emailDetails/primary", value);
    }

    public void setTravelAgent_EmailDetailsAddress(String value) {
        setRequestNodeValueByXPath("//replaceAllForTravelPlanSegment/request/travelAgent/emailDetails/address", value);
    }

    public void setReservationDetail_GuestRefDetails_PurposeOfVisit(String value) {
        setRequestNodeValueByXPath("//replaceAllForTravelPlanSegment/request/reservationDetail/guestReferenceDetails/purposeOfVisit", value);
    }

    public void setReservationDetail_GuestRefDetails_Role(String value) {
        setRequestNodeValueByXPath("//replaceAllForTravelPlanSegment/request/reservationDetail/guestReferenceDetails/role", value);
    }

    public void setReservationDetail_GuestRefDetails_CorrelationId(String value) {
        setRequestNodeValueByXPath("//replaceAllForTravelPlanSegment/request/reservationDetail/guestReferenceDetails/correlationID", value);
    }

    private void setCommentsCommentText(String baseXpath, String value) {
        if (isValid(value)) {
            setRequestNodeValueByXPath(baseXpath + "comments/commentText", value);
        }
    }

    private void setCommentsDefault(String baseXpath, String value) {
        if (isValid(value)) {
            setRequestNodeValueByXPath(baseXpath + "comments/default", value);
        }
    }

    private void setCommentsFrom(String baseXpath, String value) {
        if (isValid(value)) {
            setRequestNodeValueByXPath(baseXpath + "comments/from", value);
        }
    }

    private void setCommentsTo(String baseXpath, String value) {
        if (isValid(value)) {
            setRequestNodeValueByXPath(baseXpath + "comments/to", value);
        }
    }

    private void setCommentsRountingsName(String baseXpath, String value) {
        if (isValid(value)) {
            setRequestNodeValueByXPath(baseXpath + "routings/name", value);
        }
    }

    public void setTravelComponentGroupingId(String tcg_id) {
        setRequestNodeValueByXPath("//replaceAllForTravelPlanSegment/request/roomDetails/travelComponentGroupingId", tcg_id);
    }

    public void setTravelComponentId(String tc_id) {
        setRequestNodeValueByXPath("//replaceAllForTravelPlanSegment/request/roomDetails/travelComponentId", tc_id);
    }

    public void setTravelPlanId(String tp_id) {
        setRequestNodeValueByXPath("//replaceAllForTravelPlanSegment/request/travelPlanId", tp_id);
    }

    public void setTravelPlanSegementId(String tps_id) {
        setRequestNodeValueByXPath("//replaceAllForTravelPlanSegment/request/travelPlanSegmentId", tps_id);
    }

    public void setCommunicationChannel(String value) {
        setRequestNodeValueByXPath("//replaceAllForTravelPlanSegment/request/communicationChannel", value);
    }

    public void setSalesChannel(String value) {
        setRequestNodeValueByXPath("//replaceAllForTravelPlanSegment/request/salesChannel", value);
    }

    public void setVipLevel(String value) {
        setRequestNodeValueByXPath("//replaceAllForTravelPlanSegment/request/vipLevel", value);
    }

    public void setLocationId(String value) {
        setRequestNodeValueByXPath("//replaceAllForTravelPlanSegment/request/locationId", value);
    }

    public void setAreaPeriodEndDate(String value) {
        setRequestNodeValueByXPath("//replaceAllForTravelPlanSegment/request/areaPeriod/endDate", value);
    }

    public void setAreaPeriodStartDate(String value) {
        setRequestNodeValueByXPath("//replaceAllForTravelPlanSegment/request/areaPeriod/startDate", value);
    }

    private void setExtRefType(String baseXpath, String value) {
        if (isValid(value)) {
            setRequestNodeValueByXPath(baseXpath + "externalReferenceType", value);
        }
    }

    private void setExtRefCode(String baseXpath, String value) {
        if (isValid(value)) {
            setRequestNodeValueByXPath(baseXpath + "externalReferenceCode", value);
        }
    }

    private void setExtRefNumber(String baseXpath, String value) {
        if (isValid(value)) {
            setRequestNodeValueByXPath(baseXpath + "externalReferenceNumber", value);
        }
    }

    private void setExtRefSource(String baseXpath, String value) {
        if (isValid(value)) {
            setRequestNodeValueByXPath(baseXpath + "externalReferenceSource", value);
        }
    }

    public void setGateringDetailId(String value) {
        setRequestNodeValueByXPath("//replaceAllForTravelPlanSegment/request/gatheringDetail/gatheringId", value);
    }

    public void setGateringDetailName(String value) {
        setRequestNodeValueByXPath("//replaceAllForTravelPlanSegment/request/gatheringDetail/gatheringName", value);
    }

    public void setGateringDetailType(String value) {
        setRequestNodeValueByXPath("//replaceAllForTravelPlanSegment/request/gatheringDetail/gatheringType", value);
    }

    private void setExperienceMediaDetailsId(String baseXpath, String value) {
        if (isValid(value)) {
            setRequestNodeValueByXPath(baseXpath + "experienceMediaDetails/id", value);
        }
    }

    private void setExperienceMediaDetailsOptOut(String baseXpath, String value) {
        if (isValid(value)) {
            setRequestNodeValueByXPath(baseXpath + "experienceMediaDetails/mediaCustomization/optOut", value);
        }
    }

    private void setExperienceMediaDetailsOptOutReason(String baseXpath, String value) {
        if (isValid(value)) {
            setRequestNodeValueByXPath(baseXpath + "experienceMediaDetails/mediaCustomization/optOutReason", value);
        }
    }

    private void setExperienceMediaDetailsColor(String baseXpath, String value) {
        if (isValid(value)) {
            setRequestNodeValueByXPath(baseXpath + "experienceMediaDetails/mediaCustomization/color", value);
        }
    }

    private void setExperienceMediaDetailsPrintedName(String baseXpath, String value) {
        if (isValid(value)) {
            setRequestNodeValueByXPath(baseXpath + "experienceMediaDetails/mediaCustomization/printedName", value);
        }
    }

    private void setExperienceMediaDetailsOptOutAvailable(String baseXpath, String value) {
        if (isValid(value)) {
            setRequestNodeValueByXPath(baseXpath + "experienceMediaDetails/mediaCustomization/optOutAvailable", value);
        }
    }

    private void setAuditDetailsCreatedBy(String baseXpath, String value) {
        if (isValid(value)) {
            setRequestNodeValueByXPath(baseXpath + "auditDetail/createdBy", value);
        }
    }

    private void setAuditDetailsCreatedDate(String baseXpath, String value) {
        if (isValid(value)) {
            setRequestNodeValueByXPath(baseXpath + "auditDetail/createdDate", value);
        }
    }

    private void setAuditDetailsUpdatedBy(String baseXpath, String value) {
        if (isValid(value)) {
            setRequestNodeValueByXPath(baseXpath + "auditDetail/updatedBy", value);
        }
    }

    private void setAuditDetailsUpdatedDate(String baseXpath, String value) {
        if (isValid(value)) {
            setRequestNodeValueByXPath(baseXpath + "auditDetail/updatedDate", value);
        }
    }

    private void setAuditDetailsStatus(String baseXpath, String value) {
        if (isValid(value)) {
            setRequestNodeValueByXPath(baseXpath + "auditDetail/status", value);
        }
    }

    private void setProfileCode(String baseXpath, String value) {
        if (isValid(value)) {
            setRequestNodeValueByXPath(baseXpath + "/profiles/code", Randomness.generateCurrentXMLDate());
        }
    }

    private void setProfileDescription(String baseXpath, String value) {
        if (isValid(value)) {
            setRequestNodeValueByXPath(baseXpath + "/profiles/description", Randomness.generateCurrentXMLDate());
        }
    }

    private void setProfileId(String baseXpath, String value) {
        if (isValid(value)) {
            setRequestNodeValueByXPath(baseXpath + "/profiles/id", Randomness.generateCurrentXMLDate());
        }
    }

    private void setProfileProfileType(String baseXpath, String value) {
        if (isValid(value)) {
            setRequestNodeValueByXPath(baseXpath + "/profiles/profileType", Randomness.generateCurrentXMLDate());
        }
    }

    private void setProfileName(String baseXpath, String value) {
        if (isValid(value)) {
            setRequestNodeValueByXPath(baseXpath + "/profiles/routings/name", Randomness.generateCurrentXMLDate());
        }
    }

    private void setProfileSelectable(String baseXpath, String value) {
        if (isValid(value)) {
            setRequestNodeValueByXPath(baseXpath + "/profiles/selectable", Randomness.generateCurrentXMLDate());
        }
    }

    private void setProfileType(String baseXpath, String value) {
        if (isValid(value)) {
            setRequestNodeValueByXPath(baseXpath + "/profiles/type", Randomness.generateCurrentXMLDate());
        }
    }

    private void setMembershipDetailsExpirationDate(String baseXpath, String value) {
        if (isValid(value)) {
            setRequestNodeValueByXPath(baseXpath + "/expirationDate", value);
        }
    }

    private void setMembershipDetailsMembershipType(String baseXpath, String value) {
        if (isValid(value)) {
            setRequestNodeValueByXPath(baseXpath + "/memberShipType", value);
        }
    }

    private void setMembershipDetailsMembershipId(String baseXpath, String value) {
        if (isValid(value)) {
            setRequestNodeValueByXPath(baseXpath + "/membershipId", value);
        }
    }

    private void setMembershipDetailsPolicyId(String baseXpath, String value) {
        if (isValid(value)) {
            setRequestNodeValueByXPath(baseXpath + "/policyId", value);
        }
    }

    private void setMembershipDetailsProdChannelId(String baseXpath, String value) {
        if (isValid(value)) {
            setRequestNodeValueByXPath(baseXpath + "/productChannelId", value);
        }
    }

    private void setMembershipDetailsGuestMembershipId(String baseXpath, String value) {
        if (isValid(value)) {
            setRequestNodeValueByXPath(baseXpath + "/guestMembershipId", value);
        }
    }

    public void setReservationDetail_GuestRefDetails_Guest_GuestIdRefs(String type, String value) {
        String baseXpath = "//replaceAllForTravelPlanSegment/request/reservationDetail/guestReferenceDetails/guest";
        setGuestIdReferencesType(baseXpath, type);
        setGuestIdReferencesValue(baseXpath, value);
    }

    private void setGuestIdReferencesType(String baseXpath, String value) {
        if (isValid(value)) {
            setRequestNodeValueByXPath(baseXpath + "/guestIdReferences/type", value);
        }
    }

    private void setGuestIdReferencesValue(String baseXpath, String value) {
        if (isValid(value)) {
            setRequestNodeValueByXPath(baseXpath + "/guestIdReferences/value", value);
        }
    }

    public String getTravelComponentGroupingId() {
        return getResponseNodeValueByXPath("//replaceAllForTravelPlanSegmentResponse/response/roomDetails/travelComponentGroupingId");
    }

    public String getTravelComponentId() {
        return getResponseNodeValueByXPath("//replaceAllForTravelPlanSegmentResponse/response/roomDetails/travelComponentId");
    }

    public String getGuestId() {
        return getResponseNodeValueByXPath("//replaceAllForTravelPlanSegmentResponse/response/roomDetails/roomReservationDetail/guestReferenceDetails/guest/guestId");
    }

    public String getTravelPlanId() {
        return getResponseNodeValueByXPath("//replaceAllForTravelPlanSegmentResponse/response/travelPlanId");
    }

    public String getTravelPlanSegmentId() {
        return getResponseNodeValueByXPath("//replaceAllForTravelPlanSegmentResponse/response/travelPlanSegmentId");
    }
}