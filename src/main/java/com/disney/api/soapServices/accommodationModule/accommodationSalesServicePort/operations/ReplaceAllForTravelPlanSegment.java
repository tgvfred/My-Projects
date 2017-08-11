package com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations;

import static com.disney.api.soapServices.accommodationModule.helpers.AccommodationBaseTest.getAgeTypeByAge;
import static com.disney.api.soapServices.accommodationModule.helpers.AccommodationBaseTest.isValid;

import com.disney.AutomationException;
import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.AccommodationSalesServicePort;
import com.disney.api.soapServices.accommodationModule.helpers.AccommodationBaseTest;
import com.disney.api.soapServices.core.BaseSoapCommands;
import com.disney.api.soapServices.core.exceptions.XPathNotFoundException;
import com.disney.utils.Randomness;
import com.disney.utils.XMLTools;
import com.disney.utils.dataFactory.guestFactory.Address;
import com.disney.utils.dataFactory.guestFactory.Email;
import com.disney.utils.dataFactory.guestFactory.Guest;
import com.disney.utils.dataFactory.guestFactory.Phone;

public class ReplaceAllForTravelPlanSegment extends AccommodationSalesServicePort {
    private int totalRate;

    public int getTotalRate() {
        return totalRate;
    }

    public void setTotalRate(int totalRate) {
        this.totalRate = totalRate;
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
        }
        baseXpath += "/";
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

    private void addAuditDetail(String baseXpath) {
        int numAuditDetails = getNumberOfRequestNodesByXPath(baseXpath);
        setRequestNodeValueByXPath(baseXpath.replace("/auditDetail", ""), BaseSoapCommands.ADD_NODE.commandAppend("auditDetail"));
        numAuditDetails++;
        setRequestNodeValueByXPath(baseXpath + "[" + String.valueOf(numAuditDetails) + "]/auditDetail", "createdBy");
        setRequestNodeValueByXPath(baseXpath + "[" + String.valueOf(numAuditDetails) + "]/auditDetail", "createdDate");
        setRequestNodeValueByXPath(baseXpath + "[" + String.valueOf(numAuditDetails) + "]/auditDetail", "updatedBy");
        setRequestNodeValueByXPath(baseXpath + "[" + String.valueOf(numAuditDetails) + "]/auditDetail", "updatedDate");
        setRequestNodeValueByXPath(baseXpath + "[" + String.valueOf(numAuditDetails) + "]/auditDetail", "status");
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
        String baseXpath = "//replaceAllForTravelPlanSegment/request/roomDetails/roomReservationDetail/comments";
        int numComments = getNumberOfRequestNodesByXPath(baseXpath);
        if (numComments == 0) {
            addRoomDetails_RoomReservationDetail_Comments(baseXpath, numComments);
        }
        baseXpath += "/";
        setAuditDetails(baseXpath, "AutoJUnit.us", Randomness.generateCurrentDatetime(), "AutoJUnit.us", Randomness.generateCurrentDatetime(), auditStatus);
        setCommentsCommentText(baseXpath, commentText);
        setCommentsDefault(baseXpath, defaultIndicator);
        setCommentsFrom(baseXpath, from);
        setCommentsRountingsName(baseXpath, routingsName);
        setCommentsTo(baseXpath, to);
    }

    private void addRoomDetails_RoomReservationDetail_Comments(String baseXpath, int numComments) {
        String nodeName = "comments";
        setRequestNodeValueByXPath(baseXpath.replace("/" + nodeName, ""), BaseSoapCommands.ADD_NODE.commandAppend(nodeName));
        numComments++;
        baseXpath += "/" + nodeName + "[" + String.valueOf(numComments) + "]";
        setRequestNodeValueByXPath(baseXpath, BaseSoapCommands.ADD_NODE.commandAppend(AccommodationBaseTest.COMMENT_TEXT));
        setRequestNodeValueByXPath(baseXpath, BaseSoapCommands.ADD_NODE.commandAppend(AccommodationBaseTest.COMMENT_TO));
        setRequestNodeValueByXPath(baseXpath, BaseSoapCommands.ADD_NODE.commandAppend(AccommodationBaseTest.COMMENT_FROM));
        setRequestNodeValueByXPath(baseXpath, BaseSoapCommands.ADD_NODE.commandAppend("default"));
        addAuditDetail(baseXpath + "/auditDetail");
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
        int numConfirmationDetails = getNumberOfResponseNodesByXPath("/Envelope/Body/replaceAllForTravelPlanSegment/request/confirmationDetails");
        if (numConfirmationDetails == 0) {
            addConfirmationDetails(numConfirmationDetails);
        }
        String baseXpath = "//confirmationDetails/guestDetail/";
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

    private void addConfirmationDetails(int numConfirmationDetails) {
        String baseXpath = "/Envelope/Body/replaceAllForTravelPlanSegment/request";
        setRequestNodeValueByXPath(baseXpath, BaseSoapCommands.ADD_NODE.commandAppend("confirmationDetails"));
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
        addGuest("/Envelope/Body/replaceAllForTravelPlanSegment/request/confirmationDetails", false, false, "guestDetail");
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
        addAndSetGuest(baseXpath, addMembership, addGuestIdReferences, guest, "guest");
    }

    public void addTicketDetails_GuestReferenceGuest(Boolean addMembership, Boolean addGuestIdReferences, Guest guest) {
        String baseXpath = "/Envelope/Body/replaceAllForTravelPlanSegment/request/roomDetails/ticketDetails/guestReference";
        addAndSetGuest(baseXpath, addMembership, addGuestIdReferences, guest, "guest");
    }

    public void addAndSetGuest(String xPath, Boolean addMembership, Boolean addGuestIdReferences, Guest guest, String guestNodeName) {
        int numGuestNodes = addGuest(xPath, addMembership, addGuestIdReferences, guestNodeName);
        setGuest(xPath + "[" + String.valueOf(numGuestNodes) + "]" + "/guest", guest);
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
        baseXpath += "/guest";
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

        baseXpath = xPath + "/guest/addressDetails";
        setRequestNodeValueByXPath(baseXpath, BaseSoapCommands.ADD_NODE.commandAppend("locatorId"));
        setRequestNodeValueByXPath(baseXpath, BaseSoapCommands.ADD_NODE.commandAppend("guestLocatorId"));
        setRequestNodeValueByXPath(baseXpath, BaseSoapCommands.ADD_NODE.commandAppend("primary"));
        setRequestNodeValueByXPath(baseXpath, BaseSoapCommands.ADD_NODE.commandAppend("addressLine1"));
        setRequestNodeValueByXPath(baseXpath, BaseSoapCommands.ADD_NODE.commandAppend("city"));
        setRequestNodeValueByXPath(baseXpath, BaseSoapCommands.ADD_NODE.commandAppend("country"));
        setRequestNodeValueByXPath(baseXpath, BaseSoapCommands.ADD_NODE.commandAppend("postalCode"));
        setRequestNodeValueByXPath(baseXpath, BaseSoapCommands.ADD_NODE.commandAppend("state"));
        setRequestNodeValueByXPath(baseXpath, BaseSoapCommands.ADD_NODE.commandAppend("regionName"));

        baseXpath = xPath + "/guest/emailDetails";
        setRequestNodeValueByXPath(baseXpath, BaseSoapCommands.ADD_NODE.commandAppend("locatorId"));
        setRequestNodeValueByXPath(baseXpath, BaseSoapCommands.ADD_NODE.commandAppend("guestLocatorId"));
        setRequestNodeValueByXPath(baseXpath, BaseSoapCommands.ADD_NODE.commandAppend("primary"));
        setRequestNodeValueByXPath(baseXpath, BaseSoapCommands.ADD_NODE.commandAppend("address"));

        if (isValid(addMembership) && addMembership == true) {
            baseXpath = xPath + "/guest";
            setRequestNodeValueByXPath(baseXpath, BaseSoapCommands.ADD_NODE.commandAppend("membershipDetail"));
            baseXpath = xPath + "/guest/membershipDetail";
            setRequestNodeValueByXPath(baseXpath, BaseSoapCommands.ADD_NODE.commandAppend("expirationDate"));
            setRequestNodeValueByXPath(baseXpath, BaseSoapCommands.ADD_NODE.commandAppend("memberShipType"));
            setRequestNodeValueByXPath(baseXpath, BaseSoapCommands.ADD_NODE.commandAppend("membershipId"));
            setRequestNodeValueByXPath(baseXpath, BaseSoapCommands.ADD_NODE.commandAppend("policyId"));
            setRequestNodeValueByXPath(baseXpath, BaseSoapCommands.ADD_NODE.commandAppend("productChannelId"));
            setRequestNodeValueByXPath(baseXpath, BaseSoapCommands.ADD_NODE.commandAppend("guestMembershipId"));
        }

        if (isValid(addGuestIdReferences) && addGuestIdReferences == true) {
            baseXpath = xPath + "/guest";
            setRequestNodeValueByXPath(baseXpath, BaseSoapCommands.ADD_NODE.commandAppend("guestIdReferences"));
            baseXpath = xPath + "/guest/guestIdReferences";
            setRequestNodeValueByXPath(baseXpath, BaseSoapCommands.ADD_NODE.commandAppend("type"));
            setRequestNodeValueByXPath(baseXpath, BaseSoapCommands.ADD_NODE.commandAppend("value"));
        }
        return numExistingGuests;
    }

    private void setGuest(String baseXpath, Guest guest) {
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

    private void setGuestPhone(String baseXpath, Phone phone) {
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

    private void setGuestAddress(String baseXpath, Address address) {
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

    public void setGatheringDetail(String id, String name, String type) {
        int numGatherings = getNumberOfRequestNodesByXPath("/Envelope/Body/replaceAllForTravelPlanSegment/request/gatheringDetail");
        if (numGatherings == 0) {
            addGathering(numGatherings);
        }
        setGateringDetailId(id);
        setGateringDetailName(name);
        setGateringDetailType(type);
    }

    private void addGathering(int numGatherings) {
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

    public void setTravelAgency() {
        int numTravelAgencies = getNumberOfRequestNodesByXPath("/Envelope/Body/replaceAllForTravelPlanSegment/request/travelAgency");
        if (numTravelAgencies == 0) {
            addTravelAgency();
        }
        setTravelAgencyAgencyIataNumber("99999998");
        setTravelAgencyAgencyName("SYSTEM SUPPORT TEST AGENCY");
        setTravelAgencyAgencyOdsId("423996915");
        setTravelAgencyGuestTravelAgencyId("0");
        setTravelAgencyAgentId("1788217880");
        setTravelAgencyConfirmationLocatorValue("0");
        setTravelAgencyGuestAgentId("0");
        setTravelAgencyGuestConfirmationLocationId("0");

        setTravelAgency_PrimaryAddressAddressLine1("11234 Minnie Mouse Lane");
        setTravelAgency_PrimaryAddressLocatorId("827970903");
        setTravelAgency_PrimaryAddressGuestLocatorId("0");
        setTravelAgency_PrimaryAddressLocatorUseType("UNKNOWN");
        setTravelAgency_PrimaryAddressPrimary("true");
        setTravelAgency_PrimaryAddressCity("Anaheim");
        setTravelAgency_PrimaryAddressCountry("USA");
        setTravelAgency_PrimaryAddressPostalCode("92805");
        setTravelAgency_PrimaryAddressState("CA");
    }

    private void addTravelAgency() {
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

    private void setCommentsCommentText(String baseXpath, String value) {
        if (isValid(value)) {
            setRequestNodeValueByXPath(baseXpath + "comments/commentText", value);
        }
    }

    private void setCommentsCommentType(String baseXpath, String value) {
        if (isValid(value)) {
            setRequestNodeValueByXPath(baseXpath + "comments/commentType", value);
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

    private void setExtRefType(String baseXpath, String value) {
        if (isValid(value)) {
            try {
                setRequestNodeValueByXPath(baseXpath + "externalReferenceType", value);
            } catch (XPathNotFoundException e) {
                setRequestNodeValueByXPath(baseXpath.substring(0, baseXpath.lastIndexOf("/")), BaseSoapCommands.ADD_NODE.commandAppend("externalReferenceType"));
                setRequestNodeValueByXPath(baseXpath + "externalReferenceType", value);
            }
        }
    }

    private void setExtRefCode(String baseXpath, String value) {
        if (isValid(value)) {
            try {
                setRequestNodeValueByXPath(baseXpath + "externalReferenceCode", value);
            } catch (XPathNotFoundException e) {
                setRequestNodeValueByXPath(baseXpath.substring(0, baseXpath.lastIndexOf("/")), BaseSoapCommands.ADD_NODE.commandAppend("externalReferenceCode"));
                setRequestNodeValueByXPath(baseXpath + "externalReferenceCode", value);
            }
        }
    }

    private void setExtRefNumber(String baseXpath, String value) {
        if (isValid(value)) {
            try {
                setRequestNodeValueByXPath(baseXpath + "externalReferenceNumber", value);
            } catch (XPathNotFoundException e) {
                setRequestNodeValueByXPath(baseXpath.substring(0, baseXpath.lastIndexOf("/")), BaseSoapCommands.ADD_NODE.commandAppend("externalReferenceNumber"));
                setRequestNodeValueByXPath(baseXpath + "externalReferenceNumber", value);
            }
        }
    }

    private void setExtRefSource(String baseXpath, String value) {
        if (isValid(value)) {
            try {
                setRequestNodeValueByXPath(baseXpath + "externalReferenceSource", value);
            } catch (XPathNotFoundException e) {
                setRequestNodeValueByXPath(baseXpath.substring(0, baseXpath.lastIndexOf("/")), BaseSoapCommands.ADD_NODE.commandAppend("externalReferenceSource"));
                setRequestNodeValueByXPath(baseXpath + "externalReferenceSource", value);
            }
        }
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

    private int setRateDetailsValues(String baseXpath, int daysOut, int dayCount) {
        return setRateDetailsValues(baseXpath, daysOut, String.valueOf(dayCount));
    }

    private int setRateDetailsValues(String baseXpath, int daysOut, String dayCount) {
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
}