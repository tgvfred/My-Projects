package com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations;

import static com.disney.api.soapServices.accommodationModule.helpers.AccommodationBaseTest.MEMBERSHIP_EXP_DATE;
import static com.disney.api.soapServices.accommodationModule.helpers.AccommodationBaseTest.MEMBERSHIP_GUEST_MEMBERSHIP_ID;
import static com.disney.api.soapServices.accommodationModule.helpers.AccommodationBaseTest.MEMBERSHIP_ID;
import static com.disney.api.soapServices.accommodationModule.helpers.AccommodationBaseTest.MEMBERSHIP_POLICY_ID;
import static com.disney.api.soapServices.accommodationModule.helpers.AccommodationBaseTest.MEMBERSHIP_PROD_CHANNEL_ID;
import static com.disney.api.soapServices.accommodationModule.helpers.AccommodationBaseTest.MEMBERSHIP_TYPE;
import static com.disney.api.soapServices.accommodationModule.helpers.AccommodationBaseTest.PROFILE_CODE;
import static com.disney.api.soapServices.accommodationModule.helpers.AccommodationBaseTest.PROFILE_DESCRIPTION;
import static com.disney.api.soapServices.accommodationModule.helpers.AccommodationBaseTest.PROFILE_ID;
import static com.disney.api.soapServices.accommodationModule.helpers.AccommodationBaseTest.PROFILE_ROUTINGS_NAME;
import static com.disney.api.soapServices.accommodationModule.helpers.AccommodationBaseTest.PROFILE_SELECTABLE;
import static com.disney.api.soapServices.accommodationModule.helpers.AccommodationBaseTest.PROFILE_TYPE;
import static com.disney.api.soapServices.accommodationModule.helpers.AccommodationBaseTest.getAgeTypeByAge;
import static com.disney.api.soapServices.accommodationModule.helpers.AccommodationBaseTest.isValid;

import java.util.HashMap;
import java.util.Map;

import com.disney.AutomationException;
import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.AccommodationSalesServicePort;
import com.disney.api.soapServices.accommodationModule.helpers.AccommodationBaseTest;
import com.disney.api.soapServices.bussvcsModule.organizationServiceV2.operations.SearchOrganizationByMembershipId;
import com.disney.api.soapServices.core.BaseSoapCommands;
import com.disney.api.soapServices.core.exceptions.XPathNotFoundException;
import com.disney.utils.Randomness;
import com.disney.utils.Sleeper;
import com.disney.utils.TestReporter;
import com.disney.utils.XMLTools;
import com.disney.utils.dataFactory.guestFactory.Address;
import com.disney.utils.dataFactory.guestFactory.Email;
import com.disney.utils.dataFactory.guestFactory.Guest;
import com.disney.utils.dataFactory.guestFactory.Phone;

public class ReplaceAllForTravelPlanSegment extends AccommodationSalesServicePort {
    private int totalRate;
    private Map<String, String> agencyDetails;

    public int getTotalRate() {
        return totalRate;
    }

    public void setTotalRate(int totalRate) {
        this.totalRate = totalRate;
    }

    public Map<String, String> getAgencyDetails() {
        return agencyDetails;
    }

    public void setAgencyDetails(Map<String, String> agencyDetails) {
        this.agencyDetails = agencyDetails;
    }

    public ReplaceAllForTravelPlanSegment(String environment, String scenario) {
        super(environment);
        // Generate a request from a project xml file
        setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("replaceAllForTravelPlanSegment")));
        generateServiceContext();
        setRequestNodeValueByXPath(getTestScenario(getService(), getOperation(), scenario));
        removeComments();
        removeWhiteSpace();
    }

    // *********************************************************************************************************
    // *********************************************************************************************************
    // *********************************************************************************************************

    // ********************************************Setters******************************************************

    // *********************************************************************************************************
    // *********************************************************************************************************
    // *********************************************************************************************************

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

    public void setInternalComments(String commentText, String commentType, String auditStatus) {
        String baseXpath = "//replaceAllForTravelPlanSegment/request/internalComments";
        int numInternalComments = getNumberOfRequestNodesByXPath(baseXpath);
        if (numInternalComments == 0) {
            addInternalComments(baseXpath, numInternalComments);
            numInternalComments++;
        }
        baseXpath += "[" + numInternalComments + "]/";
        setCommentsCommentText(baseXpath, commentText);
        setCommentsCommentType(baseXpath, commentType);
        setAuditDetails(baseXpath, "AutoJUnit.us", Randomness.generateCurrentXMLDate(), "AutoJUnit.us", Randomness.generateCurrentXMLDate(), auditStatus);
    }

    public void addInternalComments(String baseXpath, int intnumInternalComments) {
        String nodeName = "internalComments";
        setRequestNodeValueByXPath(baseXpath.replace("/" + nodeName, ""), BaseSoapCommands.ADD_NODE.commandAppend("internalComments"));
        intnumInternalComments++;
        baseXpath = baseXpath + "[" + String.valueOf(intnumInternalComments) + "]";
        setRequestNodeValueByXPath(baseXpath, BaseSoapCommands.ADD_NODE.commandAppend("commentText"));
        setRequestNodeValueByXPath(baseXpath, BaseSoapCommands.ADD_NODE.commandAppend("commentType"));
        addAuditDetail(baseXpath);
    }

    public void addInternalComments(int intnumInternalComments) {
        String baseXpath = "//replaceAllForTravelPlanSegment/request/internalComments";
        String nodeName = "internalComments";
        setRequestNodeValueByXPath(baseXpath.replace("/" + nodeName, ""), BaseSoapCommands.ADD_NODE.commandAppend("internalComments"));
        intnumInternalComments++;
        baseXpath = baseXpath + "[" + String.valueOf(intnumInternalComments) + "]";
        setRequestNodeValueByXPath(baseXpath, BaseSoapCommands.ADD_NODE.commandAppend("commentText"));
        setRequestNodeValueByXPath(baseXpath, BaseSoapCommands.ADD_NODE.commandAppend("commentType"));
        addAuditDetail(baseXpath);
    }

    public void addAuditDetail(String baseXpath) {
        int numAuditDetails = getNumberOfRequestNodesByXPath(baseXpath + "/auditDetail");
        setRequestNodeValueByXPath(baseXpath.replace("/auditDetail", ""), BaseSoapCommands.ADD_NODE.commandAppend("auditDetail"));
        numAuditDetails++;
        if (!baseXpath.contains("auditDetail")) {
            baseXpath += "/auditDetail[" + String.valueOf(numAuditDetails) + "]";
        }
        setRequestNodeValueByXPath(baseXpath, BaseSoapCommands.ADD_NODE.commandAppend("createdBy"));
        setRequestNodeValueByXPath(baseXpath, BaseSoapCommands.ADD_NODE.commandAppend("createdDate"));
        setRequestNodeValueByXPath(baseXpath, BaseSoapCommands.ADD_NODE.commandAppend("updatedBy"));
        setRequestNodeValueByXPath(baseXpath, BaseSoapCommands.ADD_NODE.commandAppend("updatedDate"));
        setRequestNodeValueByXPath(baseXpath, BaseSoapCommands.ADD_NODE.commandAppend("status"));
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

    public void setReservationDetail_Profiles(Map<String, String> profileData) {
        String baseXpath = "/Envelope/Body/replaceAllForTravelPlanSegment/request/roomDetails/roomReservationDetail/profiles";
        int numNodes = getNumberOfRequestNodesByXPath("/Envelope/Body/replaceAllForTravelPlanSegment/request/roomDetails/roomReservationDetail/profiles");
        if (numNodes == 0) {
            addReservationDetail_Profiles(baseXpath, numNodes);
        }
        baseXpath = "/Envelope/Body/replaceAllForTravelPlanSegment/request/roomDetails/roomReservationDetail";
        setAuditDetails(baseXpath += "/profiles/", "AutoJUnit.us", Randomness.generateCurrentXMLDate(), "AutoJUnit.us", Randomness.generateCurrentXMLDate(), BaseSoapCommands.REMOVE_NODE.toString());
        baseXpath = "/Envelope/Body/replaceAllForTravelPlanSegment/request/roomDetails/roomReservationDetail";
        setProfileCode(baseXpath, profileData.get(PROFILE_CODE));
        setProfileDescription(baseXpath, profileData.get(PROFILE_DESCRIPTION));
        setProfileId(baseXpath, profileData.get(PROFILE_ID));
        setProfileName(baseXpath, profileData.get(PROFILE_ROUTINGS_NAME));
        setProfileProfileType(baseXpath, profileData.get(PROFILE_TYPE));
        setProfileSelectable(baseXpath, profileData.get(PROFILE_SELECTABLE));
        setProfileType(baseXpath, BaseSoapCommands.REMOVE_NODE.toString());
        // baseXpath = "/Envelope/Body/replaceAllForTravelPlanSegment/request/roomDetails/roomReservationDetail";
        // setAuditDetails(baseXpath += "/profiles/", "AutoJUnit.us", Randomness.generateCurrentXMLDate(), "AutoJUnit.us", Randomness.generateCurrentXMLDate(),
        // BaseSoapCommands.REMOVE_NODE.toString());
    }

    public void addReservationDetail_Profiles(String baseXpath, int numNodes) {
        setRequestNodeValueByXPath(baseXpath.replace("/profiles", ""), BaseSoapCommands.ADD_NODE.commandAppend("profiles"));
        setRequestNodeValueByXPath(baseXpath, BaseSoapCommands.ADD_NODE.commandAppend(PROFILE_CODE));
        setRequestNodeValueByXPath(baseXpath, BaseSoapCommands.ADD_NODE.commandAppend(PROFILE_DESCRIPTION));
        setRequestNodeValueByXPath(baseXpath, BaseSoapCommands.ADD_NODE.commandAppend(PROFILE_ID));
        setRequestNodeValueByXPath(baseXpath, BaseSoapCommands.ADD_NODE.commandAppend(PROFILE_SELECTABLE));
        setRequestNodeValueByXPath(baseXpath, BaseSoapCommands.ADD_NODE.commandAppend(PROFILE_TYPE));
        setRequestNodeValueByXPath(baseXpath, BaseSoapCommands.ADD_NODE.commandAppend(PROFILE_ROUTINGS_NAME.split("_")[0]));
        setRequestNodeValueByXPath(baseXpath + "/" + PROFILE_ROUTINGS_NAME.split("_")[0], BaseSoapCommands.ADD_NODE.commandAppend(PROFILE_ROUTINGS_NAME.split("_")[1]));
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

    public void setTravelPlanGuest_GuestIdReferences(String type, String value) {
        String baseXpath = "/Envelope/Body/replaceAllForTravelPlanSegment/request/travelPlanGuest/guestIdReferences";
        int numNodes = getNumberOfRequestNodesByXPath(baseXpath);
        if (numNodes == 0) {
            addGuestIdReferences(baseXpath, numNodes);
            numNodes++;
        }
        setGuestIdReferencesType(baseXpath, type);
        setGuestIdReferencesValue(baseXpath, value);
    }

    public void addGuestIdReferences(String baseXpath, int numNodes) {
        setRequestNodeValueByXPath(baseXpath.replace("/guestIdReferences", ""), BaseSoapCommands.ADD_NODE.commandAppend("guestIdReferences"));
        setRequestNodeValueByXPath(baseXpath, BaseSoapCommands.ADD_NODE.commandAppend("type"));
        setRequestNodeValueByXPath(baseXpath, BaseSoapCommands.ADD_NODE.commandAppend("value"));
    }

    public void setTravelPlanGuest(Guest guest) {
        String baseXpath = "//replaceAllForTravelPlanSegment/request/travelPlanGuest";
        setGuest(baseXpath, guest);
    }

    public void setRoomDetails_SourceExtRef(String code, String number, String source, String type) {
        String baseXpath = "//replaceAllForTravelPlanSegment/request/roomDetails/sourceExternalReference/";
        int numNodes = getNumberOfRequestNodesByXPath(baseXpath.substring(0, baseXpath.lastIndexOf("/")));
        if (numNodes == 0) {
            setRequestNodeValueByXPath("//roomDetails", BaseSoapCommands.ADD_NODE.commandAppend("sourceExternalReference"));
        }
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
        String baseXpath = "//replaceAllForTravelPlanSegment/request/roomDetails/roomReservationDetail/comments";
        int numComments = getNumberOfRequestNodesByXPath(baseXpath);
        if (numComments == 0) {
            addRoomDetails_RoomReservationDetail_Comments(baseXpath, numComments);
        }
        baseXpath += "/";
        setAuditDetails(baseXpath, "AutoJUnit.us", Randomness.generateCurrentXMLDate(), "AutoJUnit.us", Randomness.generateCurrentXMLDate(), auditStatus);
        baseXpath = baseXpath.replace("/comments", "");
        setCommentsCommentText(baseXpath, commentText);
        setCommentsDefault(baseXpath, defaultIndicator);
        setCommentsFrom(baseXpath, from);
        setCommentsRountingsName(baseXpath, routingsName);
        setCommentsTo(baseXpath, to);
    }

    public void addRoomDetails_RoomReservationDetail_Comments(String baseXpath, int numComments) {
        String nodeName = "comments";
        setRequestNodeValueByXPath(baseXpath.replace("/" + nodeName, ""), BaseSoapCommands.ADD_NODE.commandAppend(nodeName));
        numComments++;
        baseXpath += "[" + String.valueOf(numComments) + "]";
        setRequestNodeValueByXPath(baseXpath, BaseSoapCommands.ADD_NODE.commandAppend(AccommodationBaseTest.COMMENT_TEXT));
        setRequestNodeValueByXPath(baseXpath, BaseSoapCommands.ADD_NODE.commandAppend(AccommodationBaseTest.COMMENT_TO));
        setRequestNodeValueByXPath(baseXpath, BaseSoapCommands.ADD_NODE.commandAppend(AccommodationBaseTest.COMMENT_FROM));
        setRequestNodeValueByXPath(baseXpath, BaseSoapCommands.ADD_NODE.commandAppend("default"));
        setRequestNodeValueByXPath(baseXpath, BaseSoapCommands.ADD_NODE.commandAppend("routings"));
        setRequestNodeValueByXPath(baseXpath + "/routings", BaseSoapCommands.ADD_NODE.commandAppend("name"));
        addAuditDetail(baseXpath + "/auditDetail");
    }

    public void setRoomDetails_RoomReservationDetail_GuestRefDetails(Guest guest) {
        String baseXpath = "//replaceAllForTravelPlanSegment/request/roomDetails/roomReservationDetail/guestReferenceDetails/";
        setRequestNodeValueByXPath(baseXpath + "/age", guest.getAge());
        setRequestNodeValueByXPath(baseXpath + "/ageType", getAgeTypeByAge(guest.getAge()));
        setGuest(baseXpath + "guest/", guest);
    }

    public void setRoomDetails_RoomReservationDetail_GuestRefDetails_MembershipDetails(String expDate, String membershipType, String membershipId, String policyId, String prodChannelId, String guestMembershipId) {
        String baseXpath = "//replaceAllForTravelPlanSegment/request/roomDetails/roomReservationDetail/guestReferenceDetails/guest/membershipDetail";
        int numNodes = getNumberOfRequestNodesByXPath(baseXpath);
        if (numNodes == 0) {
            addMembershipDetails(baseXpath, numNodes);
        }
        setMembershipDetailsExpirationDate(baseXpath, expDate);
        setMembershipDetailsGuestMembershipId(baseXpath, guestMembershipId);
        setMembershipDetailsMembershipId(baseXpath, membershipId);
        setMembershipDetailsMembershipType(baseXpath, membershipType);
        setMembershipDetailsPolicyId(baseXpath, policyId);
        setMembershipDetailsProdChannelId(baseXpath, prodChannelId);
    }

    public void addMembershipDetails(String baseXpath, int numNodes) {
        setRequestNodeValueByXPath(baseXpath.replace("/membershipDetail", ""), BaseSoapCommands.ADD_NODE.commandAppend("membershipDetail"));
        setRequestNodeValueByXPath(baseXpath, BaseSoapCommands.ADD_NODE.commandAppend(MEMBERSHIP_EXP_DATE));
        setRequestNodeValueByXPath(baseXpath, BaseSoapCommands.ADD_NODE.commandAppend(MEMBERSHIP_GUEST_MEMBERSHIP_ID));
        setRequestNodeValueByXPath(baseXpath, BaseSoapCommands.ADD_NODE.commandAppend(MEMBERSHIP_ID));
        setRequestNodeValueByXPath(baseXpath, BaseSoapCommands.ADD_NODE.commandAppend(MEMBERSHIP_POLICY_ID));
        setRequestNodeValueByXPath(baseXpath, BaseSoapCommands.ADD_NODE.commandAppend(MEMBERSHIP_PROD_CHANNEL_ID));
        setRequestNodeValueByXPath(baseXpath, BaseSoapCommands.ADD_NODE.commandAppend(MEMBERSHIP_TYPE));
    }

    public void setRoomDetails_RoomReservationDetail_GuestRefDetails_GuestIdRefs(String type, String value) {
        String baseXpath = "//replaceAllForTravelPlanSegment/request/roomDetails/roomReservationDetail/guestReferenceDetails/guest/guestIdReferences";
        int numNodes = getNumberOfRequestNodesByXPath(baseXpath);
        if (numNodes == 0) {
            addGuestIdReferences(baseXpath, numNodes);
            numNodes++;
        }
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
        int numConfirmationDetails = getNumberOfResponseNodesByXPath("/Envelope/Body/replaceAllForTravelPlanSegment/request/confirmationDetails");
        if (numConfirmationDetails == 0) {
            addConfirmationDetails(numConfirmationDetails);
        }
        String baseXpath = "//confirmationDetails/guestDetail/";
        setConfirmationDetails_ConfirmationDetailId(id);
        setConfirmationDetails_ConfirmationIndicator(indicator);
        setConfirmationDetails_ConfirmationType(type);
        setConfirmationDetails_DefaultConfirmationIndicator(defaultConfirmationIndicator);
        setConfirmationDetails_Individual(individual);
        setConfirmationDetails_JdoSequenceNumber(jdoSeqNumber);
        setConfirmationDetails_LocatorId(locatorId);
        setConfirmationDetails_PartyId(partyId);
        setConfirmationDetails_ContactName(guest.getFirstName() + " " + guest.getLastName());
        setConfirmationDetails_FirstName(guest.getFirstName());
        setConfirmationDetails_LastName(guest.getLastName());

        // Age and age type nodes are created for a typical guest. This is not required for the confirmation guest
        try {
            setRequestNodeValueByXPath("/Envelope/Body/replaceAllForTravelPlanSegment/request/confirmationDetails/age", BaseSoapCommands.REMOVE_NODE.toString());
        } catch (XPathNotFoundException e) {

        }
        try {
            setRequestNodeValueByXPath("/Envelope/Body/replaceAllForTravelPlanSegment/request/confirmationDetails/ageType", BaseSoapCommands.REMOVE_NODE.toString());
        } catch (XPathNotFoundException e) {

        }
        setGuest(baseXpath, guest);
    }

    public void addConfirmationDetails(int numConfirmationDetails) {
        String baseXpath = "/Envelope/Body/replaceAllForTravelPlanSegment/request";
        addGuest("/Envelope/Body/replaceAllForTravelPlanSegment/request/confirmationDetails", false, false, "guestDetail");

        // setRequestNodeValueByXPath(baseXpath, BaseSoapCommands.ADD_NODE.commandAppend("confirmationDetails"));
        numConfirmationDetails++;
        baseXpath += "/confirmationDetails[" + String.valueOf(numConfirmationDetails) + "]";
        setRequestNodeValueByXPath(baseXpath, BaseSoapCommands.ADD_NODE.commandAppend("confirmationDetailId"));
        setRequestNodeValueByXPath(baseXpath, BaseSoapCommands.ADD_NODE.commandAppend("confirmationIndicator"));
        setRequestNodeValueByXPath(baseXpath, BaseSoapCommands.ADD_NODE.commandAppend("confirmationType"));
        setRequestNodeValueByXPath(baseXpath, BaseSoapCommands.ADD_NODE.commandAppend("contactName"));
        setRequestNodeValueByXPath(baseXpath, BaseSoapCommands.ADD_NODE.commandAppend("defaultConfirmationIndicator"));
        setRequestNodeValueByXPath(baseXpath, BaseSoapCommands.ADD_NODE.commandAppend("firstName"));
        setRequestNodeValueByXPath(baseXpath, BaseSoapCommands.ADD_NODE.commandAppend("individual"));
        setRequestNodeValueByXPath(baseXpath, BaseSoapCommands.ADD_NODE.commandAppend("jdoSequenceNumber"));
        setRequestNodeValueByXPath(baseXpath, BaseSoapCommands.ADD_NODE.commandAppend("lastName"));
        setRequestNodeValueByXPath(baseXpath, BaseSoapCommands.ADD_NODE.commandAppend("locatorId"));
        setRequestNodeValueByXPath(baseXpath, BaseSoapCommands.ADD_NODE.commandAppend("partyId"));
    }

    public void setReservationDetail_GuestRefDetails_Guest_GuestIdRefs(String type, String value) {
        String baseXpath = "//replaceAllForTravelPlanSegment/request/reservationDetail/guestReferenceDetails/guest";
        setGuestIdReferencesType(baseXpath, type);
        setGuestIdReferencesValue(baseXpath, value);
    }

    public void addReservationDetail_GuestReferenceDetailGuest(Boolean addMembership, Boolean addGuestIdReferences, Guest guest) {
        String baseXpath = "/Envelope/Body/replaceAllForTravelPlanSegment/request/reservationDetail/guestReferenceDetails";
        addAndSetGuest(baseXpath, addMembership, addGuestIdReferences, guest, "guest");
    }

    public void addRoomDetails_RoomReservationDetail_GuestReferenceDetailGuest(Boolean addMembership, Boolean addGuestIdReferences, Guest guest) {
        String baseXpath = "/Envelope/Body/replaceAllForTravelPlanSegment/request/roomDetails/roomReservationDetail/guestReferenceDetails";
        int numGuestNodes = addAndSetGuest(baseXpath, addMembership, addGuestIdReferences, guest, "guest");

        baseXpath = "/Envelope/Body/replaceAllForTravelPlanSegment/request/roomDetails/roomReservationDetail/guestReferenceDetails[" + String.valueOf(numGuestNodes) + "]";
        setRequestNodeValueByXPath(baseXpath + "/age", guest.getAge());
        setRequestNodeValueByXPath(baseXpath + "/ageType", getAgeTypeByAge(guest.getAge()));
    }

    public void addTicketDetails_GuestReferenceGuest(Boolean addMembership, Boolean addGuestIdReferences, Guest guest) {
        String baseXpath = "/Envelope/Body/replaceAllForTravelPlanSegment/request/roomDetails/ticketDetails/guestReference";
        addAndSetGuest(baseXpath, addMembership, addGuestIdReferences, guest, "guest");
    }

    public int addAndSetGuest(String xPath, Boolean addMembership, Boolean addGuestIdReferences, Guest guest, String guestNodeName) {
        int numGuestNodes = addGuest(xPath, addMembership, addGuestIdReferences, guestNodeName);
        setGuest(xPath + "[" + String.valueOf(numGuestNodes) + "]" + "/guest", guest);
        return numGuestNodes;
    }

    public int addGuest(String xPath, Boolean addMembership, Boolean addGuestIdReferences, String guestNodeName) {
        int numExistingGuests = getNumberOfRequestNodesByXPath(xPath);
        // Add a new parent node
        String[] arrTemp = xPath.split("/");
        String strTemp = arrTemp[arrTemp.length - 1];
        String tempXpath = xPath.replace("/" + strTemp, "");
        setRequestNodeValueByXPath(tempXpath, BaseSoapCommands.ADD_NODE.commandAppend(strTemp));
        numExistingGuests++;
        // Create the new xPath
        xPath += "[" + String.valueOf(numExistingGuests) + "]";

        // Add a "guest" node
        String baseXpath = xPath;
        setRequestNodeValueByXPath(baseXpath, BaseSoapCommands.ADD_NODE.commandAppend(guestNodeName));
        setRequestNodeValueByXPath(baseXpath, BaseSoapCommands.ADD_NODE.commandAppend("age"));
        setRequestNodeValueByXPath(baseXpath, BaseSoapCommands.ADD_NODE.commandAppend("ageType"));
        baseXpath += "/" + guestNodeName;
        // Add the remaining nodes
        setRequestNodeValueByXPath(baseXpath, BaseSoapCommands.ADD_NODE.commandAppend("title"));
        setRequestNodeValueByXPath(baseXpath, BaseSoapCommands.ADD_NODE.commandAppend("firstName"));
        setRequestNodeValueByXPath(baseXpath, BaseSoapCommands.ADD_NODE.commandAppend("lastName"));
        setRequestNodeValueByXPath(baseXpath, BaseSoapCommands.ADD_NODE.commandAppend("middleName"));
        setRequestNodeValueByXPath(baseXpath, BaseSoapCommands.ADD_NODE.commandAppend("partyId"));
        setRequestNodeValueByXPath(baseXpath, BaseSoapCommands.ADD_NODE.commandAppend("doNotMailIndicator"));
        setRequestNodeValueByXPath(baseXpath, BaseSoapCommands.ADD_NODE.commandAppend("doNotPhoneIndicator"));
        setRequestNodeValueByXPath(baseXpath, BaseSoapCommands.ADD_NODE.commandAppend("preferredLanguage"));
        setRequestNodeValueByXPath(baseXpath, BaseSoapCommands.ADD_NODE.commandAppend("dclGuestId"));
        setRequestNodeValueByXPath(baseXpath, BaseSoapCommands.ADD_NODE.commandAppend("guestId"));
        setRequestNodeValueByXPath(baseXpath, BaseSoapCommands.ADD_NODE.commandAppend("active"));
        setRequestNodeValueByXPath(baseXpath, BaseSoapCommands.ADD_NODE.commandAppend("dob"));
        setRequestNodeValueByXPath(baseXpath, BaseSoapCommands.ADD_NODE.commandAppend("phoneDetails"));
        setRequestNodeValueByXPath(baseXpath, BaseSoapCommands.ADD_NODE.commandAppend("addressDetails"));
        setRequestNodeValueByXPath(baseXpath, BaseSoapCommands.ADD_NODE.commandAppend("emailDetails"));

        baseXpath += "/phoneDetails";
        setRequestNodeValueByXPath(baseXpath, BaseSoapCommands.ADD_NODE.commandAppend("locatorId"));
        setRequestNodeValueByXPath(baseXpath, BaseSoapCommands.ADD_NODE.commandAppend("guestLocatorId"));
        setRequestNodeValueByXPath(baseXpath, BaseSoapCommands.ADD_NODE.commandAppend("primary"));
        setRequestNodeValueByXPath(baseXpath, BaseSoapCommands.ADD_NODE.commandAppend("deviceType"));
        setRequestNodeValueByXPath(baseXpath, BaseSoapCommands.ADD_NODE.commandAppend("extension"));
        setRequestNodeValueByXPath(baseXpath, BaseSoapCommands.ADD_NODE.commandAppend("number"));

        baseXpath = xPath + "/" + guestNodeName + "/addressDetails";
        setRequestNodeValueByXPath(baseXpath, BaseSoapCommands.ADD_NODE.commandAppend("locatorId"));
        setRequestNodeValueByXPath(baseXpath, BaseSoapCommands.ADD_NODE.commandAppend("guestLocatorId"));
        setRequestNodeValueByXPath(baseXpath, BaseSoapCommands.ADD_NODE.commandAppend("primary"));
        setRequestNodeValueByXPath(baseXpath, BaseSoapCommands.ADD_NODE.commandAppend("addressLine1"));
        setRequestNodeValueByXPath(baseXpath, BaseSoapCommands.ADD_NODE.commandAppend("city"));
        setRequestNodeValueByXPath(baseXpath, BaseSoapCommands.ADD_NODE.commandAppend("country"));
        setRequestNodeValueByXPath(baseXpath, BaseSoapCommands.ADD_NODE.commandAppend("postalCode"));
        setRequestNodeValueByXPath(baseXpath, BaseSoapCommands.ADD_NODE.commandAppend("state"));
        setRequestNodeValueByXPath(baseXpath, BaseSoapCommands.ADD_NODE.commandAppend("regionName"));

        baseXpath = xPath + "/" + guestNodeName + "/emailDetails";
        setRequestNodeValueByXPath(baseXpath, BaseSoapCommands.ADD_NODE.commandAppend("locatorId"));
        setRequestNodeValueByXPath(baseXpath, BaseSoapCommands.ADD_NODE.commandAppend("guestLocatorId"));
        setRequestNodeValueByXPath(baseXpath, BaseSoapCommands.ADD_NODE.commandAppend("primary"));
        setRequestNodeValueByXPath(baseXpath, BaseSoapCommands.ADD_NODE.commandAppend("address"));

        if (isValid(addMembership) && addMembership == true) {
            baseXpath = xPath + "/" + guestNodeName;
            setRequestNodeValueByXPath(baseXpath, BaseSoapCommands.ADD_NODE.commandAppend("membershipDetail"));
            baseXpath = xPath + "/" + guestNodeName + "/membershipDetail";
            setRequestNodeValueByXPath(baseXpath, BaseSoapCommands.ADD_NODE.commandAppend("expirationDate"));
            setRequestNodeValueByXPath(baseXpath, BaseSoapCommands.ADD_NODE.commandAppend("memberShipType"));
            setRequestNodeValueByXPath(baseXpath, BaseSoapCommands.ADD_NODE.commandAppend("membershipId"));
            setRequestNodeValueByXPath(baseXpath, BaseSoapCommands.ADD_NODE.commandAppend("policyId"));
            setRequestNodeValueByXPath(baseXpath, BaseSoapCommands.ADD_NODE.commandAppend("productChannelId"));
            setRequestNodeValueByXPath(baseXpath, BaseSoapCommands.ADD_NODE.commandAppend("guestMembershipId"));
        }

        if (isValid(addGuestIdReferences) && addGuestIdReferences == true) {
            baseXpath = xPath + "/" + guestNodeName;
            setRequestNodeValueByXPath(baseXpath, BaseSoapCommands.ADD_NODE.commandAppend("guestIdReferences"));
            baseXpath = xPath + "/" + guestNodeName + "/guestIdReferences";
            setRequestNodeValueByXPath(baseXpath, BaseSoapCommands.ADD_NODE.commandAppend("type"));
            setRequestNodeValueByXPath(baseXpath, BaseSoapCommands.ADD_NODE.commandAppend("value"));
        }
        return numExistingGuests;
    }

    public void setGuest(String baseXpath, Guest guest) {
        if (!isValid(guest)) {
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

    public void setGuestPhone(String baseXpath, Phone phone) {
        if (!isValid(phone)) {
            throw new AutomationException("The phone object cannot be null");
        }
        setRequestNodeValueByXPath(baseXpath + "/phoneDetails/locatorId", phone.getLocatorId());
        setRequestNodeValueByXPath(baseXpath + "/phoneDetails/guestLocatorId", "0");
        setRequestNodeValueByXPath(baseXpath + "/phoneDetails/primary", "true");
        setRequestNodeValueByXPath(baseXpath + "/phoneDetails/deviceType", "HANDSET");
        setRequestNodeValueByXPath(baseXpath + "/phoneDetails/extension", "0");
        setRequestNodeValueByXPath(baseXpath + "/phoneDetails/number", phone.getNumber());
    }

    public void setGuestAddress(String baseXpath, Address address) {
        if (!isValid(address)) {
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

    public void setGuestEmail(String baseXpath, Email email) {
        if (email == null) {
            throw new AutomationException("The email object cannot be null");
        }
        setRequestNodeValueByXPath(baseXpath + "/emailDetails/locatorId", email.getLocatorId());
        setRequestNodeValueByXPath(baseXpath + "/emailDetails/guestLocatorId", "0");
        setRequestNodeValueByXPath(baseXpath + "/emailDetails/primary", "true");
        setRequestNodeValueByXPath(baseXpath + "/emailDetails/address", email.getEmail());
    }

    public void setAuditDetails(String baseXpath, String createdBy, String createdDate, String updatedBy, String updatedDate, String status) {
        int numNodes = 0;
        if (baseXpath.endsWith("/")) {
            numNodes = getNumberOfRequestNodesByXPath(baseXpath + "auditDetail");
        } else {
            numNodes = getNumberOfRequestNodesByXPath(baseXpath + "/auditDetail");
        }
        if (numNodes == 0) {
            addAuditDetails(baseXpath);
        }
        setAuditDetailsCreatedBy(baseXpath, createdBy);
        setAuditDetailsCreatedDate(baseXpath, createdDate);
        setAuditDetailsUpdatedBy(baseXpath, updatedBy);
        setAuditDetailsUpdatedDate(baseXpath, updatedDate);
        setAuditDetailsStatus(baseXpath, status);
    }

    public void addAuditDetails(String baseXpath) {
        if (baseXpath.endsWith("/")) {
            baseXpath = baseXpath.substring(0, baseXpath.lastIndexOf("/"));
        }
        setRequestNodeValueByXPath(baseXpath, BaseSoapCommands.ADD_NODE.commandAppend("auditDetail"));
        setRequestNodeValueByXPath(baseXpath + "/auditDetail", BaseSoapCommands.ADD_NODE.commandAppend("createdBy"));
        setRequestNodeValueByXPath(baseXpath + "/auditDetail", BaseSoapCommands.ADD_NODE.commandAppend("createdDate"));
        setRequestNodeValueByXPath(baseXpath + "/auditDetail", BaseSoapCommands.ADD_NODE.commandAppend("updatedBy"));
        setRequestNodeValueByXPath(baseXpath + "/auditDetail", BaseSoapCommands.ADD_NODE.commandAppend("updatedDate"));
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

    public void setTravelPlanGuest_Guest_MembershipDetails(String expDate, String membershipType, String membershipId, String policyId, String prodChannelId, String guestMembershipId) {
        String baseXpath = "/Envelope/Body/replaceAllForTravelPlanSegment/request/travelPlanGuest/membershipDetail";
        int numNodes = getNumberOfRequestNodesByXPath(baseXpath);
        if (numNodes == 0) {
            addMembershipDetails(baseXpath, numNodes);
        }

        setMembershipDetailsExpirationDate(baseXpath, expDate);
        setMembershipDetailsGuestMembershipId(baseXpath, guestMembershipId);
        setMembershipDetailsMembershipId(baseXpath, membershipId);
        setMembershipDetailsMembershipType(baseXpath, membershipType);
        setMembershipDetailsPolicyId(baseXpath, policyId);
        setMembershipDetailsProdChannelId(baseXpath, prodChannelId);
    }

    public void setGatheringDetail(String id, String name, String type) {
        int numGatherings = getNumberOfRequestNodesByXPath("/Envelope/Body/replaceAllForTravelPlanSegment/request/gatheringDetail");
        if (numGatherings == 0) {
            addGathering(numGatherings);
        }
        setGateringDetailId(id);
        setGateringDetailName(name);
        setGateringDetailType(type);
    }

    public void addGathering(int numGatherings) {
        String baseXpath = "/Envelope/Body/replaceAllForTravelPlanSegment/request";
        setRequestNodeValueByXPath(baseXpath, BaseSoapCommands.ADD_NODE.commandAppend("gatheringDetail"));
        numGatherings++;
        baseXpath += "/gatheringDetail[" + String.valueOf(numGatherings) + "]";
        setRequestNodeValueByXPath(baseXpath, BaseSoapCommands.ADD_NODE.commandAppend("gatheringId"));
        setRequestNodeValueByXPath(baseXpath, BaseSoapCommands.ADD_NODE.commandAppend("gatheringName"));
        setRequestNodeValueByXPath(baseXpath, BaseSoapCommands.ADD_NODE.commandAppend("gatheringType"));
    }

    public void setGateringDetailId(String value) {
        if (isValid(value)) {
            setRequestNodeValueByXPath("//replaceAllForTravelPlanSegment/request/gatheringDetail/gatheringId", value);
        } else {
            throw new AutomationException("The Gathering Detail ID cannot be null");
        }
    }

    public void setGateringDetailName(String value) {
        if (isValid(value)) {
            setRequestNodeValueByXPath("//replaceAllForTravelPlanSegment/request/gatheringDetail/gatheringName", value);
        } else {
            throw new AutomationException("The Gathering Detail name cannot be null");
        }
    }

    public void setGateringDetailType(String value) {
        if (isValid(value)) {
            setRequestNodeValueByXPath("//replaceAllForTravelPlanSegment/request/gatheringDetail/gatheringType", value);
        } else {
            throw new AutomationException("The Gathering Detail type cannot be null");
        }
    }

    public void setRoomDetails_ResortPeriod(String startDate, String endDate) {
        setRoomDetails_ResortPeriodStartDate(startDate);
        setRoomDetails_ResortPeriodEndDate(endDate);
    }

    public void setAreaPeriod(String startDate, String endDate) {
        setAreaPeriodEndDate(endDate);
        setAreaPeriodStartDate(startDate);
    }

    public void setTaxExemptDetails(String number, String type) {
        setTaxExemptDetailCertificateNumber(number);
        setTaxExemptDetailType(type);
    }

    public void setTravelAgency(String agencyId) {
        int numTravelAgencies = getNumberOfRequestNodesByXPath("/Envelope/Body/replaceAllForTravelPlanSegment/request/travelAgency");
        if (numTravelAgencies == 0) {
            addTravelAgency();
        }

        int maxTries = 10;
        boolean success = false;
        SearchOrganizationByMembershipId search = new SearchOrganizationByMembershipId(getEnvironment(), "Main");

        for (int tries = 0; tries < maxTries; tries++) {
            try {
                search.setOrganizationMembershipName(BaseSoapCommands.REMOVE_NODE.toString());
            } catch (XPathNotFoundException e) {
            }
            search.setOrganizationMembershipValue(agencyId);
            search.sendRequest();
            if (search.getResponseStatusCode().equals("200")) {
                success = true;
                break;
            } else {
                Sleeper.sleep(Randomness.randomNumberBetween(3, 7) * 1000);
            }
        }
        if (!success) {
            TestReporter.log("An error occurred searching for an organization by ID [" + agencyId + "].");
            TestReporter.log("\nREQUEST: " + search.getRequest());
            TestReporter.log("\nRESPONSE: " + search.getRequest());
        }

        setAgencyDetails(new HashMap<String, String>());
        getAgencyDetails().put("iataNumber", agencyId);
        getAgencyDetails().put("agencyName", search.getName());
        getAgencyDetails().put("agencyOdsId", search.getId());
        getAgencyDetails().put("guestTravelAgencyId", "0");
        getAgencyDetails().put("agentId", search.getFirstAgentId());
        getAgencyDetails().put("guestAgentId", "0");
        getAgencyDetails().put("confirmationLocatorValue", "0");
        getAgencyDetails().put("guestConfirmationLocationId", "0");
        getAgencyDetails().put("locatorId", search.getAddressLocatorId());
        getAgencyDetails().put("guestLocatorId", "0");
        getAgencyDetails().put("locatorUseType", "UNKNOWN");
        getAgencyDetails().put("primary", "true");
        getAgencyDetails().put("addressLine1", search.getAddress1());
        getAgencyDetails().put("city", search.getCity());
        getAgencyDetails().put("country", search.getCountry());
        getAgencyDetails().put("postalCode", search.getPostalCode());
        getAgencyDetails().put("state", search.getState());
        getAgencyDetails().put("name", search.getName());

        setTravelAgencyAgencyIataNumber(agencyId);
        setTravelAgencyAgencyName(getAgencyDetails().get("name"));
        setTravelAgencyAgencyOdsId(getAgencyDetails().get("agencyOdsId"));
        setTravelAgencyGuestTravelAgencyId(getAgencyDetails().get("guestTravelAgencyId"));
        setTravelAgencyAgentId(getAgencyDetails().get("agentId"));
        setTravelAgencyConfirmationLocatorValue(getAgencyDetails().get("confirmationLocatorValue"));
        setTravelAgencyGuestAgentId(getAgencyDetails().get("guestAgentId"));
        setTravelAgencyGuestConfirmationLocationId(getAgencyDetails().get("guestConfirmationLocationId"));

        setTravelAgency_PrimaryAddressAddressLine1(getAgencyDetails().get("addressLine1"));
        setTravelAgency_PrimaryAddressLocatorId(getAgencyDetails().get("locatorId"));
        setTravelAgency_PrimaryAddressGuestLocatorId(getAgencyDetails().get("guestLocatorId"));
        setTravelAgency_PrimaryAddressLocatorUseType(getAgencyDetails().get("locatorUseType"));
        setTravelAgency_PrimaryAddressPrimary(getAgencyDetails().get("primary"));
        setTravelAgency_PrimaryAddressCity(getAgencyDetails().get("city"));
        setTravelAgency_PrimaryAddressCountry(getAgencyDetails().get("country"));
        setTravelAgency_PrimaryAddressPostalCode(getAgencyDetails().get("postalCode"));
        setTravelAgency_PrimaryAddressState(getAgencyDetails().get("state"));
    }

    public void addTravelAgency() {
        String baseXpath = "/Envelope/Body/replaceAllForTravelPlanSegment/request";
        setRequestNodeValueByXPath(baseXpath, BaseSoapCommands.ADD_NODE.commandAppend("travelAgency"));
        baseXpath += "/travelAgency";
        setRequestNodeValueByXPath(baseXpath, BaseSoapCommands.ADD_NODE.commandAppend("agencyIataNumber"));
        setRequestNodeValueByXPath(baseXpath, BaseSoapCommands.ADD_NODE.commandAppend("agencyName"));
        setRequestNodeValueByXPath(baseXpath, BaseSoapCommands.ADD_NODE.commandAppend("agencyOdsId"));
        setRequestNodeValueByXPath(baseXpath, BaseSoapCommands.ADD_NODE.commandAppend("guestTravelAgencyId"));
        setRequestNodeValueByXPath(baseXpath, BaseSoapCommands.ADD_NODE.commandAppend("agentId"));
        setRequestNodeValueByXPath(baseXpath, BaseSoapCommands.ADD_NODE.commandAppend("guestAgentId"));
        setRequestNodeValueByXPath(baseXpath, BaseSoapCommands.ADD_NODE.commandAppend("confirmationLocatorValue"));
        setRequestNodeValueByXPath(baseXpath, BaseSoapCommands.ADD_NODE.commandAppend("guestConfirmationLocationId"));
        setRequestNodeValueByXPath(baseXpath, BaseSoapCommands.ADD_NODE.commandAppend("primaryAddress"));

        baseXpath += "/primaryAddress";
        setRequestNodeValueByXPath(baseXpath, BaseSoapCommands.ADD_NODE.commandAppend("locatorId"));
        setRequestNodeValueByXPath(baseXpath, BaseSoapCommands.ADD_NODE.commandAppend("guestLocatorId"));
        setRequestNodeValueByXPath(baseXpath, BaseSoapCommands.ADD_NODE.commandAppend("locatorUseType"));
        setRequestNodeValueByXPath(baseXpath, BaseSoapCommands.ADD_NODE.commandAppend("primary"));
        setRequestNodeValueByXPath(baseXpath, BaseSoapCommands.ADD_NODE.commandAppend("addressLine1"));
        setRequestNodeValueByXPath(baseXpath, BaseSoapCommands.ADD_NODE.commandAppend("city"));
        setRequestNodeValueByXPath(baseXpath, BaseSoapCommands.ADD_NODE.commandAppend("country"));
        setRequestNodeValueByXPath(baseXpath, BaseSoapCommands.ADD_NODE.commandAppend("postalCode"));
        setRequestNodeValueByXPath(baseXpath, BaseSoapCommands.ADD_NODE.commandAppend("state"));
    }

    @Deprecated
    public void setTravelAgent(Guest guest) {
        // TODO: add functionality to add a travel agency at a high level
        // setGuest("//replaceAllForTravelPlanSegment/request/travelAgent", guest);
        // setTravelAgent_GuestIdReferences(type, value);
        // setTravelAgent_MembershipDetails(expDate, membershipType, membershipId, policyId, prodChannelId, guestMembershipId);
    }

    public void setLocationIds(String locationId) {
        setLocationId(locationId);
        setRoomDetailsLocationId(locationId);
    }

    public void setReservationDetail_ModContactAndReason(String contact, String reason) {
        String baseXpath = "/Envelope/Body/replaceAllForTravelPlanSegment/request/reservationDetail/";
        setRoomModificationContactName(baseXpath, contact);
        setRoomModificationReason(baseXpath, reason);
    }

    public void setRoomDetails_RoomReservationDetail_ModContactAndReason(String contact, String reason) {
        String baseXpath = "/Envelope/Body/replaceAllForTravelPlanSegment/request/roomDetails/roomReservationDetail/";
        setRoomModificationContactName(baseXpath, contact);
        setRoomModificationReason(baseXpath, reason);
    }

    public void setReservationDetail_AdditionalOccupants(String additionalOccupants) {
        String baseXpath = "/Envelope/Body/replaceAllForTravelPlanSegment/request/reservationDetail/";
        setAdditionalOccupants(baseXpath, additionalOccupants);
    }

    public void setRoomDetails_RoomReservationDetail_AdditionalOccupants(String additionalOccupants) {
        String baseXpath = "/Envelope/Body/replaceAllForTravelPlanSegment/request/roomDetails/roomReservationDetail/";
        setAdditionalOccupants(baseXpath, additionalOccupants);
    }

    public void setOfferIdentifiers(String candidateId, String requestId, String quoteId) {
        setOfferIdentifiersCandidateId(candidateId);
        setOfferIdentifiersRequestId(requestId);
        setOfferIdentifiersQuoteId(quoteId);
    }

    // *********************************************************************************************************
    // *********************************************************************************************************
    // *********************************************************************************************************

    // ********************************************Component Setters********************************************

    // *********************************************************************************************************
    // *********************************************************************************************************
    // *********************************************************************************************************

    public void setConfirmationDetails_ContactName(String value) {
        String xpath = "//replaceAllForTravelPlanSegment/request/confirmationDetails/contactName";
        if (isValid(value)) {
            setRequestNodeValueByXPath(xpath, value);
        } else {
            setRequestNodeValueByXPath(xpath, "0");
        }
    }

    public void setConfirmationDetails_FirstName(String value) {
        String xpath = "//replaceAllForTravelPlanSegment/request/confirmationDetails/firstName";
        if (isValid(value)) {
            setRequestNodeValueByXPath(xpath, value);
        } else {
            setRequestNodeValueByXPath(xpath, "0");
        }
    }

    public void setConfirmationDetails_LastName(String value) {
        String xpath = "//replaceAllForTravelPlanSegment/request/confirmationDetails/lastName";
        if (isValid(value)) {
            setRequestNodeValueByXPath(xpath, value);
        } else {
            setRequestNodeValueByXPath(xpath, "0");
        }
    }

    public void setConfirmationDetails_ConfirmationDetailId(String value) {
        String xpath = "//replaceAllForTravelPlanSegment/request/confirmationDetails/confirmationDetailId";
        if (isValid(value)) {
            setRequestNodeValueByXPath(xpath, value);
        } else {
            setRequestNodeValueByXPath(xpath, "0");
        }
    }

    public void setConfirmationDetails_ConfirmationIndicator(String value) {
        String xpath = "//replaceAllForTravelPlanSegment/request/confirmationDetails/confirmationIndicator";
        if (isValid(value)) {
            setRequestNodeValueByXPath(xpath, value);
        } else {
            setRequestNodeValueByXPath(xpath, "0");
        }
    }

    public void setConfirmationDetails_ConfirmationType(String value) {
        String xpath = "//replaceAllForTravelPlanSegment/request/confirmationDetails/confirmationType";
        if (isValid(value)) {
            setRequestNodeValueByXPath(xpath, value);
        } else {
            setRequestNodeValueByXPath(xpath, "Email");
        }
    }

    public void setConfirmationDetails_DefaultConfirmationIndicator(String value) {
        String xpath = "//replaceAllForTravelPlanSegment/request/confirmationDetails/defaultConfirmationIndicator";
        if (isValid(value)) {
            setRequestNodeValueByXPath(xpath, value);
        } else {
            setRequestNodeValueByXPath(xpath, "Email");
        }
    }

    public void setConfirmationDetails_Individual(String value) {
        String xpath = "//replaceAllForTravelPlanSegment/request/confirmationDetails/individual";
        if (isValid(value)) {
            setRequestNodeValueByXPath(xpath, value);
        } else {
            setRequestNodeValueByXPath(xpath, "false");
        }
    }

    public void setConfirmationDetails_JdoSequenceNumber(String value) {
        String xpath = "//replaceAllForTravelPlanSegment/request/confirmationDetails/jdoSequenceNumber";
        if (isValid(value)) {
            setRequestNodeValueByXPath(xpath, value);
        } else {
            setRequestNodeValueByXPath(xpath, "0");
        }
    }

    public void setConfirmationDetails_LocatorId(String value) {
        String xpath = "//replaceAllForTravelPlanSegment/request/confirmationDetails/locatorId";
        if (isValid(value)) {
            setRequestNodeValueByXPath(xpath, value);
        } else {
            setRequestNodeValueByXPath(xpath, "0");
        }
    }

    public void setConfirmationDetails_PartyId(String value) {
        String xpath = "//replaceAllForTravelPlanSegment/request/confirmationDetails/partyId";
        if (isValid(value)) {
            setRequestNodeValueByXPath(xpath, value);
        } else {
            setRequestNodeValueByXPath(xpath, "0");
        }
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
        if (isValid(value)) {
            setRequestNodeValueByXPath("//replaceAllForTravelPlanSegment/request/roomDetails/locationId", value);
        } else {
            throw new AutomationException("The location id cannot be null or empty");
        }
    }

    public void setRoomDetailsShared(String value) {
        setRequestNodeValueByXPath("//replaceAllForTravelPlanSegment/request/roomDetails/shared", value);
    }

    public void setRoomDetailsSpecialNeedsRequested(String value) {
        setRequestNodeValueByXPath("//replaceAllForTravelPlanSegment/request/roomDetails/specialNeedsRequested", value);
    }

    public void setRoomDetails_ResortPeriodEndDate(String value) {
        setEndDate("//replaceAllForTravelPlanSegment/request/roomDetails/resortPeriod/", value);
    }

    public void setRoomDetails_ResortPeriodStartDate(String value) {
        setStartDate("//replaceAllForTravelPlanSegment/request/roomDetails/resortPeriod/", value);
    }

    public void setEndDate(String baseXpath, String value) {
        if (isValid(value)) {
            setRequestNodeValueByXPath(baseXpath + "endDate", value);
        } else {
            throw new AutomationException("The end date cannot be null");
        }
    }

    public void setStartDate(String baseXpath, String value) {
        if (isValid(value)) {
            setRequestNodeValueByXPath(baseXpath + "startDate", value);
        } else {
            throw new AutomationException("The start date cannot be null");
        }
    }

    public void setTaxExemptDetailCertificateNumber(String value) {
        if (isValid(value)) {
            setRequestNodeValueByXPath("//replaceAllForTravelPlanSegment/request/taxExemptDetail/taxExemptCertificateNumber", value);
        } else {
            throw new AutomationException("The tax exempt detail certificate number cannot be null or empty");
        }
    }

    public void setTaxExemptDetailType(String value) {
        if (isValid(value)) {
            setRequestNodeValueByXPath("//replaceAllForTravelPlanSegment/request/taxExemptDetail/taxExemptType", value);
        } else {
            throw new AutomationException("The tax exempt detail type cannot be null or empty");
        }
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

    public void setCommentsCommentText(String baseXpath, String value) {
        if (isValid(value)) {
            if (baseXpath.contains("internalComments")) {
                setRequestNodeValueByXPath(baseXpath + "commentText", value);
            } else {
                setRequestNodeValueByXPath(baseXpath + "comments/commentText", value);
            }
        }
    }

    public void setCommentsCommentType(String baseXpath, String value) {
        if (isValid(value)) {
            if (baseXpath.contains("internalComments")) {
                setRequestNodeValueByXPath(baseXpath + "commentType", value);
            } else {
                setRequestNodeValueByXPath(baseXpath + "comments/commentType", value);
            }
        }
    }

    public void setCommentsDefault(String baseXpath, String value) {
        if (isValid(value)) {
            setRequestNodeValueByXPath(baseXpath + "comments/default", value);
        }
    }

    public void setCommentsFrom(String baseXpath, String value) {
        if (isValid(value)) {
            setRequestNodeValueByXPath(baseXpath + "comments/from", value);
        }
    }

    public void setCommentsTo(String baseXpath, String value) {
        if (isValid(value)) {
            setRequestNodeValueByXPath(baseXpath + "comments/to", value);
        }
    }

    public void setCommentsRountingsName(String baseXpath, String value) {
        if (isValid(value)) {
            setRequestNodeValueByXPath(baseXpath + "comments/routings/name", value);
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
        if (isValid(value)) {
            setRequestNodeValueByXPath("//replaceAllForTravelPlanSegment/request/locationId", value);
        } else {
            throw new AutomationException("The location id cannot be null or empty");
        }
    }

    public void setAreaPeriodEndDate(String value) {
        setEndDate("//replaceAllForTravelPlanSegment/request/areaPeriod/", value);
    }

    public void setAreaPeriodStartDate(String value) {
        setStartDate("//replaceAllForTravelPlanSegment/request/areaPeriod/", value);
    }

    public void setExtRefType(String baseXpath, String value) {
        if (isValid(value)) {
            try {
                setRequestNodeValueByXPath(baseXpath + "externalReferenceType", value);
            } catch (XPathNotFoundException e) {
                setRequestNodeValueByXPath(baseXpath.substring(0, baseXpath.lastIndexOf("/")), BaseSoapCommands.ADD_NODE.commandAppend("externalReferenceType"));
                setRequestNodeValueByXPath(baseXpath + "externalReferenceType", value);
            }
        }
    }

    public void setExtRefCode(String baseXpath, String value) {
        if (isValid(value)) {
            try {
                setRequestNodeValueByXPath(baseXpath + "externalReferenceCode", value);
            } catch (XPathNotFoundException e) {
                setRequestNodeValueByXPath(baseXpath.substring(0, baseXpath.lastIndexOf("/")), BaseSoapCommands.ADD_NODE.commandAppend("externalReferenceCode"));
                setRequestNodeValueByXPath(baseXpath + "externalReferenceCode", value);
            }
        }
    }

    public void setExtRefNumber(String baseXpath, String value) {
        if (isValid(value)) {
            try {
                setRequestNodeValueByXPath(baseXpath + "externalReferenceNumber", value);
            } catch (XPathNotFoundException e) {
                setRequestNodeValueByXPath(baseXpath.substring(0, baseXpath.lastIndexOf("/")), BaseSoapCommands.ADD_NODE.commandAppend("externalReferenceNumber"));
                setRequestNodeValueByXPath(baseXpath + "externalReferenceNumber", value);
            }
        }
    }

    public void setExtRefSource(String baseXpath, String value) {
        if (isValid(value)) {
            try {
                setRequestNodeValueByXPath(baseXpath + "externalReferenceSource", value);
            } catch (XPathNotFoundException e) {
                setRequestNodeValueByXPath(baseXpath.substring(0, baseXpath.lastIndexOf("/")), BaseSoapCommands.ADD_NODE.commandAppend("externalReferenceSource"));
                setRequestNodeValueByXPath(baseXpath + "externalReferenceSource", value);
            }
        }
    }

    public void setExperienceMediaDetailsId(String baseXpath, String value) {
        if (isValid(value)) {
            setRequestNodeValueByXPath(baseXpath + "experienceMediaDetails/id", value);
        }
    }

    public void setExperienceMediaDetailsOptOut(String baseXpath, String value) {
        if (isValid(value)) {
            setRequestNodeValueByXPath(baseXpath + "experienceMediaDetails/mediaCustomization/optOut", value);
        }
    }

    public void setExperienceMediaDetailsOptOutReason(String baseXpath, String value) {
        if (isValid(value)) {
            setRequestNodeValueByXPath(baseXpath + "experienceMediaDetails/mediaCustomization/optOutReason", value);
        }
    }

    public void setExperienceMediaDetailsColor(String baseXpath, String value) {
        if (isValid(value)) {
            setRequestNodeValueByXPath(baseXpath + "experienceMediaDetails/mediaCustomization/color", value);
        }
    }

    public void setExperienceMediaDetailsPrintedName(String baseXpath, String value) {
        if (isValid(value)) {
            setRequestNodeValueByXPath(baseXpath + "experienceMediaDetails/mediaCustomization/printedName", value);
        }
    }

    public void setExperienceMediaDetailsOptOutAvailable(String baseXpath, String value) {
        if (isValid(value)) {
            setRequestNodeValueByXPath(baseXpath + "experienceMediaDetails/mediaCustomization/optOutAvailable", value);
        }
    }

    public void setAuditDetailsCreatedBy(String baseXpath, String value) {
        if (isValid(value)) {
            setRequestNodeValueByXPath(baseXpath + "auditDetail/createdBy", value);
        }
    }

    public void setAuditDetailsCreatedDate(String baseXpath, String value) {
        if (isValid(value)) {
            setRequestNodeValueByXPath(baseXpath + "auditDetail/createdDate", value);
        }
    }

    public void setAuditDetailsUpdatedBy(String baseXpath, String value) {
        if (isValid(value)) {
            setRequestNodeValueByXPath(baseXpath + "auditDetail/updatedBy", value);
        }
    }

    public void setAuditDetailsUpdatedDate(String baseXpath, String value) {
        if (isValid(value)) {
            setRequestNodeValueByXPath(baseXpath + "auditDetail/updatedDate", value);
        }
    }

    public void setAuditDetailsStatus(String baseXpath, String value) {
        if (isValid(value)) {
            try {
                setRequestNodeValueByXPath(baseXpath + "auditDetail/status", value);
            } catch (XPathNotFoundException e) {
                setRequestNodeValueByXPath(baseXpath + "auditDetail", BaseSoapCommands.ADD_NODE.commandAppend("status"));
                setRequestNodeValueByXPath(baseXpath + "auditDetail/status", value);
            }
        }
    }

    public void setProfileCode(String baseXpath, String value) {
        if (isValid(value)) {
            setRequestNodeValueByXPath(baseXpath + "/profiles/code", value);
        }
    }

    public void setProfileDescription(String baseXpath, String value) {
        if (isValid(value)) {
            setRequestNodeValueByXPath(baseXpath + "/profiles/description", value);
        }
    }

    public void setProfileId(String baseXpath, String value) {
        if (isValid(value)) {
            setRequestNodeValueByXPath(baseXpath + "/profiles/id", value);
        }
    }

    public void setProfileProfileType(String baseXpath, String value) {
        if (isValid(value)) {
            setRequestNodeValueByXPath(baseXpath + "/profiles/profileType", value);
        }
    }

    public void setProfileName(String baseXpath, String value) {
        if (isValid(value)) {
            setRequestNodeValueByXPath(baseXpath + "/profiles/routings/name", value);
        }
    }

    public void setProfileSelectable(String baseXpath, String value) {
        switch (value) {
            case "Y":
                value = "true";
                break;
            case "N":
                value = "false";
                break;
            default:
                break;
        }
        if (isValid(value)) {
            setRequestNodeValueByXPath(baseXpath + "/profiles/selectable", value);
        }
    }

    public void setProfileType(String baseXpath, String value) {
        if (isValid(value)) {
            try {
                setRequestNodeValueByXPath(baseXpath + "/profiles/type", Randomness.generateCurrentXMLDate());
            } catch (XPathNotFoundException e) {
                setRequestNodeValueByXPath(baseXpath + "/profiles", BaseSoapCommands.ADD_NODE.commandAppend("type"));
                setRequestNodeValueByXPath(baseXpath + "/profiles/type", Randomness.generateCurrentXMLDate());
            }
        }
    }

    public void setMembershipDetailsExpirationDate(String baseXpath, String value) {
        if (isValid(value)) {
            setRequestNodeValueByXPath(baseXpath + "/expirationDate", value);
        }
    }

    public void setMembershipDetailsMembershipType(String baseXpath, String value) {
        if (isValid(value)) {
            setRequestNodeValueByXPath(baseXpath + "/memberShipType", value);
        }
    }

    public void setMembershipDetailsMembershipId(String baseXpath, String value) {
        if (isValid(value)) {
            setRequestNodeValueByXPath(baseXpath + "/membershipId", value);
        }
    }

    public void setMembershipDetailsPolicyId(String baseXpath, String value) {
        if (isValid(value)) {
            setRequestNodeValueByXPath(baseXpath + "/policyId", value);
        }
    }

    public void setMembershipDetailsProdChannelId(String baseXpath, String value) {
        if (isValid(value)) {
            setRequestNodeValueByXPath(baseXpath + "/productChannelId", value);
        }
    }

    public void setMembershipDetailsGuestMembershipId(String baseXpath, String value) {
        if (isValid(value)) {
            setRequestNodeValueByXPath(baseXpath + "/guestMembershipId", value);
        }
    }

    public void setGuestIdReferencesType(String baseXpath, String value) {
        if (isValid(value)) {
            setRequestNodeValueByXPath(baseXpath + "/type", value);
        }
    }

    public void setGuestIdReferencesValue(String baseXpath, String value) {
        if (isValid(value)) {
            setRequestNodeValueByXPath(baseXpath + "/value", value);
        }
    }

    public void setByPassFreeze(String value) {
        if (isValid(value)) {
            setRequestNodeValueByXPath("//byPassFreeze", value);
        } else {
            throw new AutomationException("The bypass freeze value cannot be null or empty");
        }
    }

    public void setContactName(String value) {
        if (isValid(value)) {
            setRequestNodeValueByXPath("//contactName", value);
        } else {
            throw new AutomationException("The contact name cannot be null or empty");
        }
    }

    public void setReplaceAll(String value) {
        if (isValid(value)) {
            setRequestNodeValueByXPath("//replaceAll", value);
        } else {
            throw new AutomationException("The replace all value cannot be null or empty");
        }
    }

    public void setSecurityValue(String value) {
        if (isValid(value)) {
            try {
                setRequestNodeValueByXPath("//securityValue", value);
            } catch (XPathNotFoundException e) {
                setRequestNodeValueByXPath("/Envelope/Body/replaceAllForTravelPlanSegment/request", BaseSoapCommands.ADD_NODE.commandAppend("securityValue"));
                setRequestNodeValueByXPath("//securityValue", value);
            }
        } else {
            throw new AutomationException("The security value cannot be null or empty");
        }
    }

    public void setSentToProperty(String value) {
        if (isValid(value)) {
            setRequestNodeValueByXPath("//sentToProperty", value);
        } else {
            throw new AutomationException("The sent to property value cannot be null or empty");
        }
    }

    public void setTagNumber(String value) {
        if (isValid(value)) {
            setRequestNodeValueByXPath("//tagNumber", value);
        } else {
            throw new AutomationException("The tag number cannot be null or empty");
        }
    }

    public void setRoomModificationContactName(String baseXpath, String value) {
        if (isValid(value)) {
            setRequestNodeValueByXPath(baseXpath + "roomModificationContactName", value);
        } else {
            throw new AutomationException("The room modification contact name cannot be null or empty");
        }
    }

    public void setRoomModificationReason(String baseXpath, String value) {
        if (isValid(value)) {
            setRequestNodeValueByXPath(baseXpath + "roomModificationReason", value);
        } else {
            throw new AutomationException("The room modification contact name cannot be null or empty");
        }
    }

    public void setAdditionalOccupants(String baseXpath, String value) {
        if (isValid(value)) {
            setRequestNodeValueByXPath(baseXpath + "additionalOccupants", value);
        } else {
            throw new AutomationException("The additional occupants cannot be null or empty");
        }
    }

    public void setCheckoutDateTime(String baseXpath, String value) {
        if (isValid(value)) {
            setRequestNodeValueByXPath(baseXpath + "checkOutDateTime", value);
        } else {
            throw new AutomationException("The checkout dateTime cannot be null or empty");
        }
    }

    public void setFPLOSid(String baseXpath, String value) {
        if (isValid(value)) {
            setRequestNodeValueByXPath(baseXpath + "fplosId", value);
        } else {
            throw new AutomationException("The FPLOS ID cannot be null or empty");
        }
    }

    public void setRateCategoryCode(String baseXpath, String value) {
        if (isValid(value)) {
            setRequestNodeValueByXPath(baseXpath + "rateCategoryCode", value);
        } else {
            throw new AutomationException("The rate category code cannot be null or empty");
        }
    }

    public void setRoomNumber(String value) {
        if (isValid(value)) {
            setRequestNodeValueByXPath("//roomNumber", value);
        } else {
            throw new AutomationException("The room number cannot be null or empty");
        }
    }

    public void setInventoryOverrideReason(String value) {
        if (isValid(value)) {
            setRequestNodeValueByXPath("//inventoryOverrideReason", value);
        } else {
            throw new AutomationException("The inventory override reason cannot be null or empty");
        }
    }

    public void setInventoryOverrideContactName(String value) {
        if (isValid(value)) {
            setRequestNodeValueByXPath("//inventoryOverrideContactName", value);
        } else {
            throw new AutomationException("The inventory override contact name cannot be null or empty");
        }
    }

    public void setOfferIdentifiersCandidateId(String value) {
        if (isValid(value)) {
            setRequestNodeValueByXPath("/Envelope/Body/replaceAllForTravelPlanSegment/request/roomDetails/offerIdentifiers/candidate_id", value);
        } else {
            throw new AutomationException("The offer identifiers candidate ID cannot be null or empty");
        }
    }

    public void setOfferIdentifiersRequestId(String value) {
        if (isValid(value)) {
            setRequestNodeValueByXPath("/Envelope/Body/replaceAllForTravelPlanSegment/request/roomDetails/offerIdentifiers/request_id", value);
        } else {
            throw new AutomationException("The offer identifiers request ID cannot be null or empty");
        }
    }

    public void setOfferIdentifiersQuoteId(String value) {
        if (isValid(value)) {
            setRequestNodeValueByXPath("/Envelope/Body/replaceAllForTravelPlanSegment/request/roomDetails/offerIdentifiers/quote_id", value);
        } else {
            throw new AutomationException("The offer identifiers quote ID cannot be null or empty");
        }
    }

    // /Envelope/Body/replaceAllForTravelPlanSegment/request/roomDetails/rateDetails/rackRate/ ?
    // /Envelope/Body/replaceAllForTravelPlanSegment/request/roomDetails/rateDetails/rackRate/ ?

    public void setRateDetails(int numRates, int startDaysOut) {
        int dayCount = 0;
        int daysOut = startDaysOut;
        String baseXpath = "/Envelope/Body/replaceAllForTravelPlanSegment/request/roomDetails/rateDetails/";
        int numExistingRates = getNumberOfRequestNodesByXPath("/Envelope/Body/replaceAllForTravelPlanSegment/request/roomDetails/rateDetails");
        setTotalRate(setRateDetailsValues(baseXpath, startDaysOut, dayCount));
        // If more than one rate details is to be set....
        if (numRates > dayCount) {
            // Loop over the remaining rate details
            for (dayCount++; dayCount < numRates; dayCount++) {
                daysOut++;
                // If the next rate details is not expected to exist, add it and set the values
                // Else just set the values
                if (dayCount > numExistingRates) {
                    numExistingRates++;
                    addRateDetailsNode(numExistingRates);
                }
                setTotalRate(getTotalRate() + setRateDetailsValues(baseXpath.replace("rateDetails/", "rateDetails[" + String.valueOf(numExistingRates) + "]/"), daysOut, dayCount));
            }
        }
    }

    public void addRateDetailsNode(int nextNodeIndex) {
        String baseXpath = "/Envelope/Body/replaceAllForTravelPlanSegment/request/roomDetails";
        setRequestNodeValueByXPath(baseXpath, BaseSoapCommands.ADD_NODE.commandAppend("rateDetails"));
        baseXpath = baseXpath + "/rateDetails[" + String.valueOf(nextNodeIndex) + "]";
        setRequestNodeValueByXPath(baseXpath, BaseSoapCommands.ADD_NODE.commandAppend("additionalCharge"));
        setRequestNodeValueByXPath(baseXpath, BaseSoapCommands.ADD_NODE.commandAppend("additionalChargeOverridden"));
        setRequestNodeValueByXPath(baseXpath, BaseSoapCommands.ADD_NODE.commandAppend("basePrice"));
        setRequestNodeValueByXPath(baseXpath, BaseSoapCommands.ADD_NODE.commandAppend("date"));
        setRequestNodeValueByXPath(baseXpath, BaseSoapCommands.ADD_NODE.commandAppend("dayCount"));
        setRequestNodeValueByXPath(baseXpath, BaseSoapCommands.ADD_NODE.commandAppend("overidden"));
        setRequestNodeValueByXPath(baseXpath, BaseSoapCommands.ADD_NODE.commandAppend("shared"));
        setRequestNodeValueByXPath(baseXpath, BaseSoapCommands.ADD_NODE.commandAppend("netPrice"));
        setRequestNodeValueByXPath(baseXpath, BaseSoapCommands.ADD_NODE.commandAppend("pointsValue"));
        setRequestNodeValueByXPath(baseXpath, BaseSoapCommands.ADD_NODE.commandAppend("rackRate"));
        baseXpath = baseXpath + "/rackRate";
        setRequestNodeValueByXPath(baseXpath, BaseSoapCommands.ADD_NODE.commandAppend("date"));
        setRequestNodeValueByXPath(baseXpath, BaseSoapCommands.ADD_NODE.commandAppend("rate"));
    }

    public void setRateDetails(String numRates, int startDaysOut) {
        setRateDetails(Integer.parseInt(numRates), startDaysOut);
    }

    public int setRateDetailsValues(String baseXpath, int daysOut, int dayCount) {
        return setRateDetailsValues(baseXpath, daysOut, String.valueOf(dayCount));
    }

    public int setRateDetailsValues(String baseXpath, int daysOut, String dayCount) {
        int defaultRackRate = Randomness.randomNumberBetween(1, 100);
        setRateDetails_RackRateDate(baseXpath, Randomness.generateCurrentXMLDate(daysOut));
        setRateDetails_RackRateRate(baseXpath, defaultRackRate);
        setRateDetailsAdditionalCharge(baseXpath, "0");
        setRateDetailsAdditionalChargeOverridden(baseXpath, "false");
        setRateDetailsBasePrice(baseXpath, defaultRackRate);
        setRateDetailsDate(baseXpath, Randomness.generateCurrentXMLDate(daysOut));
        setRateDetailsDayCount(baseXpath, dayCount);
        setRateDetailsNetPrice(baseXpath, defaultRackRate);
        setRateDetailsShared(baseXpath, "false");
        return defaultRackRate;
    }

    public void setRackRate(String baseXpath, String date, String rate) {
        setRateDetails_RackRateDate(baseXpath, date);
        setRateDetails_RackRateRate(baseXpath, rate);
    }

    public void setRackRate(String baseXpath, String date, int rate) {
        setRateDetails_RackRateDate(baseXpath, date);
        setRateDetails_RackRateRate(baseXpath, String.valueOf(rate));
    }

    public void setRateDetails_RackRateDate(String baseXpath, String value) {
        if (isValid(value)) {
            setRequestNodeValueByXPath(baseXpath + "date", value);
        } else {
            throw new AutomationException("The rate details rack rate date cannot be null or empty");
        }
    }

    public void setRateDetails_RackRateRate(String baseXpath, String value) {
        if (isValid(value)) {
            setRequestNodeValueByXPath(baseXpath + "rate", value);
        } else {
            throw new AutomationException("The rate details rack rate rate cannot be null or empty");
        }
    }

    public void setRateDetails_RackRateRate(String baseXpath, Integer value) {
        if (isValid(value)) {
            setRequestNodeValueByXPath(baseXpath + "rate", String.valueOf(value));
        } else {
            throw new AutomationException("The rate details rack rate rate cannot be null or empty");
        }
    }

    public void setRateDetailsAdditionalCharge(String baseXpath, String value) {
        if (isValid(value)) {
            setRequestNodeValueByXPath(baseXpath + "additionalCharge", value);
        } else {
            throw new AutomationException("The rate details additional charge cannot be null or empty");
        }
    }

    public void setRateDetailsAdditionalChargeOverridden(String baseXpath, String value) {
        if (isValid(value)) {
            setRequestNodeValueByXPath(baseXpath + "additionalChargeOverridden", value);
        } else {
            throw new AutomationException("The rate details additional charge overridden cannot be null or empty");
        }
    }

    public void setRateDetailsBasePrice(String baseXpath, String value) {
        if (isValid(value)) {
            setRequestNodeValueByXPath(baseXpath + "basePrice", value);
        } else {
            throw new AutomationException("The rate details base price cannot be null or empty");
        }
    }

    public void setRateDetailsBasePrice(String baseXpath, Integer value) {
        if (isValid(value)) {
            setRequestNodeValueByXPath(baseXpath + "basePrice", String.valueOf(value));
        } else {
            throw new AutomationException("The rate details base price cannot be null or empty");
        }
    }

    public void setRateDetailsDate(String baseXpath, String value) {
        if (isValid(value)) {
            setRequestNodeValueByXPath(baseXpath + "date", value);
        } else {
            throw new AutomationException("The rate details date cannot be null or empty");
        }
    }

    public void setRateDetailsDayCount(String baseXpath, String value) {
        if (isValid(value)) {
            setRequestNodeValueByXPath(baseXpath + "dayCount", value);
        } else {
            throw new AutomationException("The rate details day count cannot be null or empty");
        }
    }

    public void setRateDetailsDayCount(String baseXpath, Integer value) {
        if (isValid(value)) {
            setRequestNodeValueByXPath(baseXpath + "dayCount", String.valueOf(value));
        } else {
            throw new AutomationException("The rate details day count cannot be null or empty");
        }
    }

    public void setRateDetailsOveridden(String baseXpath, String value) {
        if (isValid(value)) {
            setRequestNodeValueByXPath(baseXpath + "overidden", value);
        } else {
            throw new AutomationException("The rate details overidden flag cannot be null or empty");
        }
    }

    public void setRateDetailsShared(String baseXpath, String value) {
        if (isValid(value)) {
            setRequestNodeValueByXPath(baseXpath + "shared", value);
        } else {
            throw new AutomationException("The rate details shared flag cannot be null or empty");
        }
    }

    public void setRateDetailsNetPrice(String baseXpath, String value) {
        if (isValid(value)) {
            setRequestNodeValueByXPath(baseXpath + "netPrice", value);
        } else {
            throw new AutomationException("The rate details net price cannot be null or empty");
        }
    }

    public void setRateDetailsNetPrice(String baseXpath, Integer value) {
        if (isValid(value)) {
            setRequestNodeValueByXPath(baseXpath + "netPrice", String.valueOf(value));
        } else {
            throw new AutomationException("The rate details net price cannot be null or empty");
        }
    }

    // *********************************************************************************************************
    // *********************************************************************************************************
    // *********************************************************************************************************

    // ********************************************Getters******************************************************

    // *********************************************************************************************************
    // *********************************************************************************************************
    // *********************************************************************************************************

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

    public String getPartyId(String index) {
        return getResponseNodeValueByXPath("/Envelope/Body/replaceAllForTravelPlanSegmentResponse/response/roomDetails/roomReservationDetail/guestReferenceDetails[" + index + "]/guest/partyId");
    }
}