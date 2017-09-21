package com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations;

import static com.disney.api.soapServices.accommodationModule.helpers.AccommodationBaseTest.isValid;

import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.AccommodationSalesServicePort;
import com.disney.api.soapServices.core.BaseSoapCommands;
import com.disney.utils.XMLTools;

public class CalculateUnsharedRates extends AccommodationSalesServicePort {

    /*
     * The below values are set as 1 with the understanding that a guest already exists prior to adding/setting a new guest
     */
    int Guest = 1;
    int Guest2 = 1;
    int Guest3 = 1;
    int Guest4 = 1;

    public CalculateUnsharedRates(String environment, String scenario) {
        super(environment);
        // Generate a request from a project xml file
        setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("calculateUnsharedRates")));
        setRequestNodeValueByXPath(getTestScenario(getService(), getOperation(), scenario));
        removeComments();
        removeWhiteSpace();
        generateServiceContext();
    }

    public void addUnsharedChainUnsharedRoomDetailGuestReferenceDetails() {
        String baseXpath = "/Envelope/Body/calculateUnsharedRates/request/unSharedChain/shareRoomDetails/unSharedRoomDetail/roomReservationDetail";
        setRequestNodeValueByXPath(baseXpath, BaseSoapCommands.ADD_NODE.commandAppend("guestReferenceDetails"));
        Guest++;
        baseXpath = "/Envelope/Body/calculateUnsharedRates/request/unSharedChain/shareRoomDetails/unSharedRoomDetail/roomReservationDetail/guestReferenceDetails[" + Guest + "]";
        setRequestNodeValueByXPath(baseXpath, BaseSoapCommands.ADD_NODE.commandAppend("age"));
        setRequestNodeValueByXPath(baseXpath, BaseSoapCommands.ADD_NODE.commandAppend("ageType"));
        setRequestNodeValueByXPath(baseXpath, BaseSoapCommands.ADD_NODE.commandAppend("guest"));
        setRequestNodeValueByXPath(baseXpath + "/guest", BaseSoapCommands.ADD_NODE.commandAppend("suffix"));
        setRequestNodeValueByXPath(baseXpath + "/guest", BaseSoapCommands.ADD_NODE.commandAppend("title"));
        setRequestNodeValueByXPath(baseXpath + "/guest", BaseSoapCommands.ADD_NODE.commandAppend("firstName"));
        setRequestNodeValueByXPath(baseXpath + "/guest", BaseSoapCommands.ADD_NODE.commandAppend("lastName"));
        setRequestNodeValueByXPath(baseXpath + "/guest", BaseSoapCommands.ADD_NODE.commandAppend("middleName"));
        setRequestNodeValueByXPath(baseXpath + "/guest", BaseSoapCommands.ADD_NODE.commandAppend("partyId"));
        setRequestNodeValueByXPath(baseXpath + "/guest", BaseSoapCommands.ADD_NODE.commandAppend("phoneDetails"));
        setRequestNodeValueByXPath(baseXpath + "/guest", BaseSoapCommands.ADD_NODE.commandAppend("addressDetails"));
        setRequestNodeValueByXPath(baseXpath + "/guest", BaseSoapCommands.ADD_NODE.commandAppend("emailDetails"));
        setRequestNodeValueByXPath(baseXpath + "/guest", BaseSoapCommands.ADD_NODE.commandAppend("membershipDetail"));
        setRequestNodeValueByXPath(baseXpath + "/guest", BaseSoapCommands.ADD_NODE.commandAppend("doNotMailIndicator"));
        setRequestNodeValueByXPath(baseXpath + "/guest", BaseSoapCommands.ADD_NODE.commandAppend("doNotPhoneIndicator"));
        setRequestNodeValueByXPath(baseXpath + "/guest", BaseSoapCommands.ADD_NODE.commandAppend("preferredLanguage"));
        setRequestNodeValueByXPath(baseXpath + "/guest", BaseSoapCommands.ADD_NODE.commandAppend("dclGuestId"));
        setRequestNodeValueByXPath(baseXpath + "/guest", BaseSoapCommands.ADD_NODE.commandAppend("dclTransferCode"));
        setRequestNodeValueByXPath(baseXpath + "/guest", BaseSoapCommands.ADD_NODE.commandAppend("guestId"));
        setRequestNodeValueByXPath(baseXpath + "/guest", BaseSoapCommands.ADD_NODE.commandAppend("guestTypeName"));
        setRequestNodeValueByXPath(baseXpath + "/guest", BaseSoapCommands.ADD_NODE.commandAppend("favouriteCharacter"));
        setRequestNodeValueByXPath(baseXpath + "/guest", BaseSoapCommands.ADD_NODE.commandAppend("active"));
        setRequestNodeValueByXPath(baseXpath + "/guest", BaseSoapCommands.ADD_NODE.commandAppend("guestIdReferences"));
        setRequestNodeValueByXPath(baseXpath + "/guest/guestIdReferences", BaseSoapCommands.ADD_NODE.commandAppend("type"));
        setRequestNodeValueByXPath(baseXpath + "/guest/guestIdReferences", BaseSoapCommands.ADD_NODE.commandAppend("value"));
        setRequestNodeValueByXPath(baseXpath + "/guest", BaseSoapCommands.ADD_NODE.commandAppend("gender"));
        setRequestNodeValueByXPath(baseXpath + "/guest", BaseSoapCommands.ADD_NODE.commandAppend("dob"));
        setRequestNodeValueByXPath(baseXpath, BaseSoapCommands.ADD_NODE.commandAppend("purposeOfVisit"));
        setRequestNodeValueByXPath(baseXpath, BaseSoapCommands.ADD_NODE.commandAppend("role"));
        setRequestNodeValueByXPath(baseXpath, BaseSoapCommands.ADD_NODE.commandAppend("correlationID"));
        setRequestNodeValueByXPath(baseXpath, BaseSoapCommands.ADD_NODE.commandAppend("experienceMediaDetails"));
    }

    public void setUnsharedChainUnsharedRoomDetailGuestReferenceDetails() {
        String baseXpath = "/Envelope/Body/calculateUnsharedRates/request/unSharedChain/shareRoomDetails/unSharedRoomDetail/roomReservationDetail";
        baseXpath = "/Envelope/Body/calculateUnsharedRates/request/unSharedChain/shareRoomDetails/unSharedRoomDetail/roomReservationDetail/guestReferenceDetails[" + Guest + "]/";
        String tempXpath = baseXpath;
        setAge(baseXpath, "25");
        setAgeType(baseXpath, "ADULT");
        tempXpath = baseXpath + "guest/";
        setSuffix(tempXpath, BaseSoapCommands.REMOVE_NODE.toString());
        setTitle(tempXpath, "Mr.");
        setFirstName(tempXpath, "John");
        setLastName(tempXpath, "David");
        setMiddleName(tempXpath, BaseSoapCommands.REMOVE_NODE.toString());
        setPartyId(tempXpath, "0");
        setPhoneDetails(tempXpath, BaseSoapCommands.REMOVE_NODE.toString());
        setAddressDetails(tempXpath, BaseSoapCommands.REMOVE_NODE.toString());
        setEmailDetails(tempXpath, BaseSoapCommands.REMOVE_NODE.toString());
        // tempXpath = tempXpath + "membershipDetail/";
        setMembershipDetail(tempXpath, BaseSoapCommands.REMOVE_NODE.toString());
        setDoNotMailIndicator(tempXpath, "false");
        setDoNotPhoneIndicator(tempXpath, "false");
        setPreferredLanguage(tempXpath, BaseSoapCommands.REMOVE_NODE.toString());
        setDclGuestId(tempXpath, "0");
        setDclTransferCode(tempXpath, BaseSoapCommands.REMOVE_NODE.toString());
        setGuestId(tempXpath, "0");
        setGuestTypeName(tempXpath, BaseSoapCommands.REMOVE_NODE.toString());
        setFavouriteCharacter(tempXpath, "Belle");
        setActive(tempXpath, "false");
        setGender(tempXpath, "Male");
        setDOB(tempXpath, "1984-07-28T00:00:00");
        setPurposeOfVisit(baseXpath, "Leisure");
        setRole(baseXpath, "Guest");
        setCorrelationId(baseXpath, "0");
        setExperienceMediaDetails(baseXpath, BaseSoapCommands.REMOVE_NODE.toString());
        tempXpath = tempXpath + "guestIdReferences/";
        setType(tempXpath, BaseSoapCommands.REMOVE_NODE.toString());
        setValue(tempXpath, BaseSoapCommands.REMOVE_NODE.toString());

        // Guest++;
    }

    public void addUnsharedChainSharedRoomDetailGuestReferenceDetails() {
        String baseXpath = "/Envelope/Body/calculateUnsharedRates/request/unSharedChain/shareRoomDetails/sharedRoomDetail/roomReservationDetail";
        setRequestNodeValueByXPath(baseXpath, BaseSoapCommands.ADD_NODE.commandAppend("guestReferenceDetails"));
        Guest2++;
        baseXpath = "/Envelope/Body/calculateUnsharedRates/request/unSharedChain/shareRoomDetails/sharedRoomDetail/roomReservationDetail/guestReferenceDetails[" + Guest2 + "]";

        setRequestNodeValueByXPath(baseXpath, BaseSoapCommands.ADD_NODE.commandAppend("age"));
        setRequestNodeValueByXPath(baseXpath, BaseSoapCommands.ADD_NODE.commandAppend("ageType"));
        setRequestNodeValueByXPath(baseXpath, BaseSoapCommands.ADD_NODE.commandAppend("guest"));
        setRequestNodeValueByXPath(baseXpath + "/guest", BaseSoapCommands.ADD_NODE.commandAppend("suffix"));
        setRequestNodeValueByXPath(baseXpath + "/guest", BaseSoapCommands.ADD_NODE.commandAppend("title"));
        setRequestNodeValueByXPath(baseXpath + "/guest", BaseSoapCommands.ADD_NODE.commandAppend("firstName"));
        setRequestNodeValueByXPath(baseXpath + "/guest", BaseSoapCommands.ADD_NODE.commandAppend("lastName"));
        setRequestNodeValueByXPath(baseXpath + "/guest", BaseSoapCommands.ADD_NODE.commandAppend("middleName"));
        setRequestNodeValueByXPath(baseXpath + "/guest", BaseSoapCommands.ADD_NODE.commandAppend("partyId"));
        setRequestNodeValueByXPath(baseXpath + "/guest", BaseSoapCommands.ADD_NODE.commandAppend("phoneDetails"));
        setRequestNodeValueByXPath(baseXpath + "/guest", BaseSoapCommands.ADD_NODE.commandAppend("addressDetails"));
        setRequestNodeValueByXPath(baseXpath + "/guest", BaseSoapCommands.ADD_NODE.commandAppend("emailDetails"));
        setRequestNodeValueByXPath(baseXpath + "/guest", BaseSoapCommands.ADD_NODE.commandAppend("membershipDetail"));
        setRequestNodeValueByXPath(baseXpath + "/guest", BaseSoapCommands.ADD_NODE.commandAppend("doNotMailIndicator"));
        setRequestNodeValueByXPath(baseXpath + "/guest", BaseSoapCommands.ADD_NODE.commandAppend("doNotPhoneIndicator"));
        setRequestNodeValueByXPath(baseXpath + "/guest", BaseSoapCommands.ADD_NODE.commandAppend("preferredLanguage"));
        setRequestNodeValueByXPath(baseXpath + "/guest", BaseSoapCommands.ADD_NODE.commandAppend("dclGuestId"));
        setRequestNodeValueByXPath(baseXpath + "/guest", BaseSoapCommands.ADD_NODE.commandAppend("dclTransferCode"));
        setRequestNodeValueByXPath(baseXpath + "/guest", BaseSoapCommands.ADD_NODE.commandAppend("guestId"));
        setRequestNodeValueByXPath(baseXpath + "/guest", BaseSoapCommands.ADD_NODE.commandAppend("guestTypeName"));
        setRequestNodeValueByXPath(baseXpath + "/guest", BaseSoapCommands.ADD_NODE.commandAppend("favouriteCharacter"));
        setRequestNodeValueByXPath(baseXpath + "/guest", BaseSoapCommands.ADD_NODE.commandAppend("active"));
        setRequestNodeValueByXPath(baseXpath + "/guest", BaseSoapCommands.ADD_NODE.commandAppend("guestIdReferences"));
        setRequestNodeValueByXPath(baseXpath + "/guest/guestIdReferences", BaseSoapCommands.ADD_NODE.commandAppend("type"));
        setRequestNodeValueByXPath(baseXpath + "/guest/guestIdReferences", BaseSoapCommands.ADD_NODE.commandAppend("value"));
        setRequestNodeValueByXPath(baseXpath + "/guest", BaseSoapCommands.ADD_NODE.commandAppend("gender"));
        setRequestNodeValueByXPath(baseXpath + "/guest", BaseSoapCommands.ADD_NODE.commandAppend("dob"));
        setRequestNodeValueByXPath(baseXpath, BaseSoapCommands.ADD_NODE.commandAppend("purposeOfVisit"));
        setRequestNodeValueByXPath(baseXpath, BaseSoapCommands.ADD_NODE.commandAppend("role"));
        setRequestNodeValueByXPath(baseXpath, BaseSoapCommands.ADD_NODE.commandAppend("correlationID"));
        setRequestNodeValueByXPath(baseXpath, BaseSoapCommands.ADD_NODE.commandAppend("experienceMediaDetails"));
    }

    public void setUnsharedChainSharedRoomDetailGuestReferenceDetails() {
        String baseXpath = "/Envelope/Body/calculateUnsharedRates/request/unSharedChain/shareRoomDetails/sharedRoomDetail/roomReservationDetail";
        baseXpath = "/Envelope/Body/calculateUnsharedRates/request/unSharedChain/shareRoomDetails/sharedRoomDetail/roomReservationDetail/guestReferenceDetails[" + Guest2 + "]/";
        String tempXpath = baseXpath;

        setAge(baseXpath, "25");
        setAgeType(baseXpath, "ADULT");
        tempXpath = baseXpath + "guest/";
        setSuffix(tempXpath, BaseSoapCommands.REMOVE_NODE.toString());
        setTitle(tempXpath, "Mr.");
        setFirstName(tempXpath, "John");
        setLastName(tempXpath, "David");
        setMiddleName(tempXpath, BaseSoapCommands.REMOVE_NODE.toString());
        setPartyId(tempXpath, "0");
        setPhoneDetails(tempXpath, BaseSoapCommands.REMOVE_NODE.toString());
        setAddressDetails(tempXpath, BaseSoapCommands.REMOVE_NODE.toString());
        setEmailDetails(tempXpath, BaseSoapCommands.REMOVE_NODE.toString());
        setMembershipDetail(tempXpath, BaseSoapCommands.REMOVE_NODE.toString());
        setDoNotMailIndicator(tempXpath, "false");
        setDoNotPhoneIndicator(tempXpath, "false");
        setPreferredLanguage(tempXpath, BaseSoapCommands.REMOVE_NODE.toString());
        setDclGuestId(tempXpath, "0");
        setDclTransferCode(tempXpath, BaseSoapCommands.REMOVE_NODE.toString());
        setGuestId(tempXpath, "0");
        setGuestTypeName(tempXpath, BaseSoapCommands.REMOVE_NODE.toString());
        setFavouriteCharacter(tempXpath, "Belle");
        setActive(tempXpath, "false");
        setGender(tempXpath, "Male");
        setDOB(tempXpath, "1984-07-28T00:00:00");
        setPurposeOfVisit(baseXpath, "Leisure");
        setRole(baseXpath, "Guest");
        setCorrelationId(baseXpath, "0");
        setExperienceMediaDetails(baseXpath, BaseSoapCommands.REMOVE_NODE.toString());
        tempXpath = tempXpath + "guestIdReferences/";
        setType(tempXpath, BaseSoapCommands.REMOVE_NODE.toString());
        setValue(tempXpath, BaseSoapCommands.REMOVE_NODE.toString());
    }

    public void addUnsharedAccommUnsharedRoomDetailGuestReferenceDetails() {
        String baseXpath = "/Envelope/Body/calculateUnsharedRates/request/unsharedAccomadation/unSharedRoomDetail/roomReservationDetail";
        setRequestNodeValueByXPath(baseXpath, BaseSoapCommands.ADD_NODE.commandAppend("guestReferenceDetails"));
        Guest3++;
        baseXpath = "/Envelope/Body/calculateUnsharedRates/request/unsharedAccomadation/unSharedRoomDetail/roomReservationDetail/guestReferenceDetails[" + Guest3 + "]";

        setRequestNodeValueByXPath(baseXpath, BaseSoapCommands.ADD_NODE.commandAppend("age"));
        setRequestNodeValueByXPath(baseXpath, BaseSoapCommands.ADD_NODE.commandAppend("ageType"));
        setRequestNodeValueByXPath(baseXpath, BaseSoapCommands.ADD_NODE.commandAppend("guest"));
        setRequestNodeValueByXPath(baseXpath + "/guest", BaseSoapCommands.ADD_NODE.commandAppend("suffix"));
        setRequestNodeValueByXPath(baseXpath + "/guest", BaseSoapCommands.ADD_NODE.commandAppend("title"));
        setRequestNodeValueByXPath(baseXpath + "/guest", BaseSoapCommands.ADD_NODE.commandAppend("firstName"));
        setRequestNodeValueByXPath(baseXpath + "/guest", BaseSoapCommands.ADD_NODE.commandAppend("lastName"));
        setRequestNodeValueByXPath(baseXpath + "/guest", BaseSoapCommands.ADD_NODE.commandAppend("middleName"));
        setRequestNodeValueByXPath(baseXpath + "/guest", BaseSoapCommands.ADD_NODE.commandAppend("partyId"));
        setRequestNodeValueByXPath(baseXpath + "/guest", BaseSoapCommands.ADD_NODE.commandAppend("phoneDetails"));
        setRequestNodeValueByXPath(baseXpath + "/guest", BaseSoapCommands.ADD_NODE.commandAppend("addressDetails"));
        setRequestNodeValueByXPath(baseXpath + "/guest", BaseSoapCommands.ADD_NODE.commandAppend("emailDetails"));
        setRequestNodeValueByXPath(baseXpath + "/guest", BaseSoapCommands.ADD_NODE.commandAppend("membershipDetail"));
        setRequestNodeValueByXPath(baseXpath + "/guest", BaseSoapCommands.ADD_NODE.commandAppend("doNotMailIndicator"));
        setRequestNodeValueByXPath(baseXpath + "/guest", BaseSoapCommands.ADD_NODE.commandAppend("doNotPhoneIndicator"));
        setRequestNodeValueByXPath(baseXpath + "/guest", BaseSoapCommands.ADD_NODE.commandAppend("preferredLanguage"));
        setRequestNodeValueByXPath(baseXpath + "/guest", BaseSoapCommands.ADD_NODE.commandAppend("dclGuestId"));
        setRequestNodeValueByXPath(baseXpath + "/guest", BaseSoapCommands.ADD_NODE.commandAppend("dclTransferCode"));
        setRequestNodeValueByXPath(baseXpath + "/guest", BaseSoapCommands.ADD_NODE.commandAppend("guestId"));
        setRequestNodeValueByXPath(baseXpath + "/guest", BaseSoapCommands.ADD_NODE.commandAppend("guestTypeName"));
        setRequestNodeValueByXPath(baseXpath + "/guest", BaseSoapCommands.ADD_NODE.commandAppend("favouriteCharacter"));
        setRequestNodeValueByXPath(baseXpath + "/guest", BaseSoapCommands.ADD_NODE.commandAppend("active"));
        setRequestNodeValueByXPath(baseXpath + "/guest", BaseSoapCommands.ADD_NODE.commandAppend("guestIdReferences"));
        setRequestNodeValueByXPath(baseXpath + "/guest/guestIdReferences", BaseSoapCommands.ADD_NODE.commandAppend("type"));
        setRequestNodeValueByXPath(baseXpath + "/guest/guestIdReferences", BaseSoapCommands.ADD_NODE.commandAppend("value"));
        setRequestNodeValueByXPath(baseXpath + "/guest", BaseSoapCommands.ADD_NODE.commandAppend("gender"));
        setRequestNodeValueByXPath(baseXpath + "/guest", BaseSoapCommands.ADD_NODE.commandAppend("dob"));
        setRequestNodeValueByXPath(baseXpath, BaseSoapCommands.ADD_NODE.commandAppend("purposeOfVisit"));
        setRequestNodeValueByXPath(baseXpath, BaseSoapCommands.ADD_NODE.commandAppend("role"));
        setRequestNodeValueByXPath(baseXpath, BaseSoapCommands.ADD_NODE.commandAppend("correlationID"));
        setRequestNodeValueByXPath(baseXpath, BaseSoapCommands.ADD_NODE.commandAppend("experienceMediaDetails"));
    }

    public void setUnsharedAccommUnsharedRoomDetailGuestReferenceDetails() {
        String baseXpath = "/Envelope/Body/calculateUnsharedRates/request/unsharedAccomadation/unSharedRoomDetail/roomReservationDetail";
        baseXpath = "/Envelope/Body/calculateUnsharedRates/request/unsharedAccomadation/unSharedRoomDetail/roomReservationDetail/guestReferenceDetails [" + Guest3 + "]/";
        String tempXpath = baseXpath;

        setAge(baseXpath, "25");
        setAgeType(baseXpath, "ADULT");
        tempXpath = tempXpath + "guest/";
        setSuffix(tempXpath, BaseSoapCommands.REMOVE_NODE.toString());
        setTitle(tempXpath, "Mr.");
        setFirstName(tempXpath, "John");
        setLastName(tempXpath, "David");
        setMiddleName(tempXpath, BaseSoapCommands.REMOVE_NODE.toString());
        setPartyId(tempXpath, "0");
        setPhoneDetails(tempXpath, BaseSoapCommands.REMOVE_NODE.toString());
        setAddressDetails(tempXpath, BaseSoapCommands.REMOVE_NODE.toString());
        setEmailDetails(tempXpath, BaseSoapCommands.REMOVE_NODE.toString());
        setMembershipDetail(tempXpath, BaseSoapCommands.REMOVE_NODE.toString());
        setDoNotMailIndicator(tempXpath, "false");
        setDoNotPhoneIndicator(tempXpath, "false");
        setPreferredLanguage(tempXpath, BaseSoapCommands.REMOVE_NODE.toString());
        setDclGuestId(tempXpath, "0");
        setDclTransferCode(tempXpath, BaseSoapCommands.REMOVE_NODE.toString());
        setGuestId(tempXpath, "0");
        setGuestTypeName(tempXpath, BaseSoapCommands.REMOVE_NODE.toString());
        setFavouriteCharacter(tempXpath, "Belle");
        setActive(tempXpath, "false");
        setGender(tempXpath, "Male");
        setDOB(tempXpath, "1984-07-28T00:00:00");
        setPurposeOfVisit(baseXpath, "Leisure");
        setRole(baseXpath, "Guest");
        setCorrelationId(baseXpath, "0");
        setExperienceMediaDetails(baseXpath, BaseSoapCommands.REMOVE_NODE.toString());
        tempXpath = tempXpath + "guestIdReferences/";
        setType(tempXpath, BaseSoapCommands.REMOVE_NODE.toString());
        setValue(tempXpath, BaseSoapCommands.REMOVE_NODE.toString());

        Guest++;
    }

    public void addUnsharedAccommSharedRoomDetailGuestReferenceDetails() {
        String baseXpath = "/Envelope/Body/calculateUnsharedRates/request/unsharedAccomadation/sharedRoomDetail/roomReservationDetail";
        setRequestNodeValueByXPath(baseXpath, BaseSoapCommands.ADD_NODE.commandAppend("guestReferenceDetails"));
        Guest4++;
        baseXpath = "/Envelope/Body/calculateUnsharedRates/request/unsharedAccomadation/sharedRoomDetail/roomReservationDetail/guestReferenceDetails[" + Guest4 + "]";

        setRequestNodeValueByXPath(baseXpath, BaseSoapCommands.ADD_NODE.commandAppend("age"));
        setRequestNodeValueByXPath(baseXpath, BaseSoapCommands.ADD_NODE.commandAppend("ageType"));
        setRequestNodeValueByXPath(baseXpath, BaseSoapCommands.ADD_NODE.commandAppend("guest"));
        setRequestNodeValueByXPath(baseXpath + "/guest", BaseSoapCommands.ADD_NODE.commandAppend("suffix"));
        setRequestNodeValueByXPath(baseXpath + "/guest", BaseSoapCommands.ADD_NODE.commandAppend("title"));
        setRequestNodeValueByXPath(baseXpath + "/guest", BaseSoapCommands.ADD_NODE.commandAppend("firstName"));
        setRequestNodeValueByXPath(baseXpath + "/guest", BaseSoapCommands.ADD_NODE.commandAppend("lastName"));
        setRequestNodeValueByXPath(baseXpath + "/guest", BaseSoapCommands.ADD_NODE.commandAppend("middleName"));
        setRequestNodeValueByXPath(baseXpath + "/guest", BaseSoapCommands.ADD_NODE.commandAppend("partyId"));
        setRequestNodeValueByXPath(baseXpath + "/guest", BaseSoapCommands.ADD_NODE.commandAppend("phoneDetails"));
        setRequestNodeValueByXPath(baseXpath + "/guest", BaseSoapCommands.ADD_NODE.commandAppend("addressDetails"));
        setRequestNodeValueByXPath(baseXpath + "/guest", BaseSoapCommands.ADD_NODE.commandAppend("emailDetails"));
        setRequestNodeValueByXPath(baseXpath + "/guest", BaseSoapCommands.ADD_NODE.commandAppend("membershipDetail"));
        setRequestNodeValueByXPath(baseXpath + "/guest", BaseSoapCommands.ADD_NODE.commandAppend("doNotMailIndicator"));
        setRequestNodeValueByXPath(baseXpath + "/guest", BaseSoapCommands.ADD_NODE.commandAppend("doNotPhoneIndicator"));
        setRequestNodeValueByXPath(baseXpath + "/guest", BaseSoapCommands.ADD_NODE.commandAppend("preferredLanguage"));
        setRequestNodeValueByXPath(baseXpath + "/guest", BaseSoapCommands.ADD_NODE.commandAppend("dclGuestId"));
        setRequestNodeValueByXPath(baseXpath + "/guest", BaseSoapCommands.ADD_NODE.commandAppend("dclTransferCode"));
        setRequestNodeValueByXPath(baseXpath + "/guest", BaseSoapCommands.ADD_NODE.commandAppend("guestId"));
        setRequestNodeValueByXPath(baseXpath + "/guest", BaseSoapCommands.ADD_NODE.commandAppend("guestTypeName"));
        setRequestNodeValueByXPath(baseXpath + "/guest", BaseSoapCommands.ADD_NODE.commandAppend("favouriteCharacter"));
        setRequestNodeValueByXPath(baseXpath + "/guest", BaseSoapCommands.ADD_NODE.commandAppend("active"));
        setRequestNodeValueByXPath(baseXpath + "/guest", BaseSoapCommands.ADD_NODE.commandAppend("guestIdReferences"));
        setRequestNodeValueByXPath(baseXpath + "/guest/guestIdReferences", BaseSoapCommands.ADD_NODE.commandAppend("type"));
        setRequestNodeValueByXPath(baseXpath + "/guest/guestIdReferences", BaseSoapCommands.ADD_NODE.commandAppend("value"));
        setRequestNodeValueByXPath(baseXpath + "/guest", BaseSoapCommands.ADD_NODE.commandAppend("gender"));
        setRequestNodeValueByXPath(baseXpath + "/guest", BaseSoapCommands.ADD_NODE.commandAppend("dob"));
        setRequestNodeValueByXPath(baseXpath, BaseSoapCommands.ADD_NODE.commandAppend("purposeOfVisit"));
        setRequestNodeValueByXPath(baseXpath, BaseSoapCommands.ADD_NODE.commandAppend("role"));
        setRequestNodeValueByXPath(baseXpath, BaseSoapCommands.ADD_NODE.commandAppend("correlationID"));
        setRequestNodeValueByXPath(baseXpath, BaseSoapCommands.ADD_NODE.commandAppend("experienceMediaDetails"));
    }

    public void setUnsharedAccommSharedRoomDetailGuestReferenceDetails() {
        String baseXpath = "/Envelope/Body/calculateUnsharedRates/request/unsharedAccomadation/sharedRoomDetail/roomReservationDetail";
        baseXpath = "/Envelope/Body/calculateUnsharedRates/request/unsharedAccomadation/sharedRoomDetail/roomReservationDetail/guestReferenceDetails[" + Guest4 + "]/";
        String tempXpath = baseXpath;

        setAge(baseXpath, "25");
        setAgeType(baseXpath, "ADULT");
        tempXpath = tempXpath + "guest/";
        setSuffix(tempXpath, BaseSoapCommands.REMOVE_NODE.toString());
        setTitle(tempXpath, "Mr.");
        setFirstName(tempXpath, "John");
        setLastName(tempXpath, "David");
        setMiddleName(tempXpath, BaseSoapCommands.REMOVE_NODE.toString());
        setPartyId(tempXpath, "0");
        setPhoneDetails(tempXpath, BaseSoapCommands.REMOVE_NODE.toString());
        setAddressDetails(tempXpath, BaseSoapCommands.REMOVE_NODE.toString());
        setEmailDetails(tempXpath, BaseSoapCommands.REMOVE_NODE.toString());
        setMembershipDetail(tempXpath, BaseSoapCommands.REMOVE_NODE.toString());
        setDoNotMailIndicator(tempXpath, "false");
        setDoNotPhoneIndicator(tempXpath, "false");
        setPreferredLanguage(tempXpath, BaseSoapCommands.REMOVE_NODE.toString());
        setDclGuestId(tempXpath, "0");
        setDclTransferCode(tempXpath, BaseSoapCommands.REMOVE_NODE.toString());
        setGuestId(tempXpath, "0");
        setGuestTypeName(tempXpath, BaseSoapCommands.REMOVE_NODE.toString());
        setFavouriteCharacter(tempXpath, "Belle");
        setActive(tempXpath, "false");
        setGender(tempXpath, "Male");
        setDOB(tempXpath, "1984-07-28T00:00:00");
        setPurposeOfVisit(baseXpath, "Leisure");
        setRole(baseXpath, "Guest");
        setCorrelationId(baseXpath, "0");
        setExperienceMediaDetails(baseXpath, BaseSoapCommands.REMOVE_NODE.toString());
        tempXpath = tempXpath + "guestIdReferences/";
        setType(tempXpath, BaseSoapCommands.REMOVE_NODE.toString());
        setValue(tempXpath, BaseSoapCommands.REMOVE_NODE.toString());

        Guest++;
    }

    // setters

    public void setAge(String baseXpath, String value) {
        if (isValid(value)) {
            setRequestNodeValueByXPath(baseXpath + "age", value);
        }
    }

    public void setAgeType(String baseXpath, String value) {
        if (isValid(value)) {
            setRequestNodeValueByXPath(baseXpath + "ageType", value);
        }
    }

    public void setTitle(String baseXpath, String value) {
        if (isValid(value)) {
            setRequestNodeValueByXPath(baseXpath + "title", value);
        }
    }

    public void setSuffix(String baseXpath, String value) {
        if (isValid(value)) {
            setRequestNodeValueByXPath(baseXpath + "suffix", value);
        }
    }

    public void setFirstName(String baseXpath, String value) {
        if (isValid(value)) {
            setRequestNodeValueByXPath(baseXpath + "firstName", value);
        }
    }

    public void setLastName(String baseXpath, String value) {
        if (isValid(value)) {
            setRequestNodeValueByXPath(baseXpath + "lastName", value);
        }
    }

    public void setMiddleName(String baseXpath, String value) {
        if (isValid(value)) {
            setRequestNodeValueByXPath(baseXpath + "middleName", value);
        }
    }

    public void setPartyId(String baseXpath, String value) {
        if (isValid(value)) {
            setRequestNodeValueByXPath(baseXpath + "partyId", value);
        }
    }

    public void setPhoneDetails(String baseXpath, String value) {
        if (isValid(value)) {
            setRequestNodeValueByXPath(baseXpath + "phoneDetails", value);
        }
    }

    public void setAddressDetails(String baseXpath, String value) {
        if (isValid(value)) {
            setRequestNodeValueByXPath(baseXpath + "addressDetails", value);
        }
    }

    public void setEmailDetails(String baseXpath, String value) {
        if (isValid(value)) {
            setRequestNodeValueByXPath(baseXpath + "emailDetails", value);
        }
    }

    public void setMembershipDetail(String baseXpath, String value) {
        if (isValid(value)) {
            setRequestNodeValueByXPath(baseXpath + "membershipDetail", value);
        }
    }

    public void setDoNotMailIndicator(String baseXpath, String value) {
        if (isValid(value)) {
            setRequestNodeValueByXPath(baseXpath + "doNotMailIndicator", value);
        }
    }

    public void setDoNotPhoneIndicator(String baseXpath, String value) {
        if (isValid(value)) {
            setRequestNodeValueByXPath(baseXpath + "doNotPhoneIndicator", value);
        }
    }

    public void setPreferredLanguage(String baseXpath, String value) {
        if (isValid(value)) {
            setRequestNodeValueByXPath(baseXpath + "preferredLanguage", value);
        }
    }

    public void setDclGuestId(String baseXpath, String value) {
        if (isValid(value)) {
            setRequestNodeValueByXPath(baseXpath + "dclGuestId", value);
        }
    }

    public void setDclTransferCode(String baseXpath, String value) {
        if (isValid(value)) {
            setRequestNodeValueByXPath(baseXpath + "dclTransferCode", value);
        }
    }

    public void setGuestId(String baseXpath, String value) {
        if (isValid(value)) {
            setRequestNodeValueByXPath(baseXpath + "guestId", value);
        }
    }

    public void setGuestTypeName(String baseXpath, String value) {
        if (isValid(value)) {
            setRequestNodeValueByXPath(baseXpath + "guestTypeName", value);
        }
    }

    public void setFavouriteCharacter(String baseXpath, String value) {
        if (isValid(value)) {
            setRequestNodeValueByXPath(baseXpath + "favouriteCharacter", value);
        }
    }

    public void setActive(String baseXpath, String value) {
        if (isValid(value)) {
            setRequestNodeValueByXPath(baseXpath + "active", value);
        }
    }

    public void setType(String baseXpath, String value) {
        if (isValid(value)) {
            setRequestNodeValueByXPath(baseXpath + "type", value);
        }
    }

    public void setValue(String baseXpath, String value) {
        if (isValid(value)) {
            setRequestNodeValueByXPath(baseXpath + "value", value);
        }
    }

    public void setGender(String baseXpath, String value) {
        if (isValid(value)) {
            setRequestNodeValueByXPath(baseXpath + "gender", value);
        }
    }

    public void setDOB(String baseXpath, String value) {
        if (isValid(value)) {
            setRequestNodeValueByXPath(baseXpath + "dob", value);
        }
    }

    public void setPurposeOfVisit(String baseXpath, String value) {
        if (isValid(value)) {
            setRequestNodeValueByXPath(baseXpath + "purposeOfVisit", value);
        }
    }

    public void setRole(String baseXpath, String value) {
        if (isValid(value)) {
            setRequestNodeValueByXPath(baseXpath + "role", value);
        }
    }

    public void setCorrelationId(String baseXpath, String value) {
        if (isValid(value)) {
            setRequestNodeValueByXPath(baseXpath + "correlationID", value);
        }
    }

    public void setExperienceMediaDetails(String baseXpath, String value) {
        if (isValid(value)) {
            setRequestNodeValueByXPath(baseXpath + "experienceMediaDetails", value);
        }
    }

    public void setUnsharedChainUnsharedRoomDetailTCGId(String value) {
        setRequestNodeValueByXPath("/Envelope/Body/calculateUnsharedRates/request/unSharedChain/shareRoomDetails/unSharedRoomDetail/travelComponentGroupingId", value);
    }

    public void setUnsharedChainUnsharedRoomDetailTCId(String value) {
        setRequestNodeValueByXPath("/Envelope/Body/calculateUnsharedRates/request/unSharedChain/shareRoomDetails/unSharedRoomDetail/travelComponentId", value);
    }

    public void setUnsharedChainSharedRoomDetailTCGId(String value) {
        setRequestNodeValueByXPath("/Envelope/Body/calculateUnsharedRates/request/unSharedChain/shareRoomDetails/sharedRoomDetail/travelComponentGroupingId", value);
    }

    public void setUnsharedChainSharedRoomDetailTCId(String value) {
        setRequestNodeValueByXPath("/Envelope/Body/calculateUnsharedRates/request/unSharedChain/shareRoomDetails/sharedRoomDetail/travelComponentId", value);
    }

    public void setUnsharedChainShareRoomDetailsTPSId(String value) {
        setRequestNodeValueByXPath("/Envelope/Body/calculateUnsharedRates/request/unSharedChain/shareRoomDetails/travelPlanSegmentId", value);
    }

    public void setUnsharedAccommodationUnSharedRoomDetailsTCGId(String value) {
        setRequestNodeValueByXPath("/Envelope/Body/calculateUnsharedRates/request/unsharedAccomadation/unSharedRoomDetail/travelComponentGroupingId", value);
    }

    public void setUnsharedAccommodationUnSharedRoomDetailsTCId(String value) {
        setRequestNodeValueByXPath("/Envelope/Body/calculateUnsharedRates/request/unsharedAccomadation/unSharedRoomDetail/travelComponentId", value);
    }

    public void setUnsharedAccommodationSharedRoomDetailsTCGId(String value) {
        setRequestNodeValueByXPath("/Envelope/Body/calculateUnsharedRates/request/unsharedAccomadation/sharedRoomDetail/travelComponentGroupingId", value);
    }

    public void setUnsharedAccommodationSharedRoomDetailsTCId(String value) {
        setRequestNodeValueByXPath("/Envelope/Body/calculateUnsharedRates/request/unsharedAccomadation/sharedRoomDetail/travelComponentId", value);
    }

    public void setUnsharedAccommodationTPSId(String value) {
        setRequestNodeValueByXPath("/Envelope/Body/calculateUnsharedRates/request/unsharedAccomadation/travelPlanSegmentId", value);
    }

    public void setUnsharedChainUnsharedRoomSpecialNeedsRequest(String value) {
        setRequestNodeValueByXPath("/Envelope/Body/calculateUnsharedRates/request/unSharedChain/shareRoomDetails/unSharedRoomDetail/specialNeedsRequested", value);
    }

    public void setUnsharedChainSharedRoomSpecialNeedsRequest(String value) {
        setRequestNodeValueByXPath("/Envelope/Body/calculateUnsharedRates/request/unSharedChain/shareRoomDetails/sharedRoomDetail/specialNeedsRequested", value);
    }

    public void setUnsharedAccommUnsharedRoomSpecialNeedsRequest(String value) {
        setRequestNodeValueByXPath("/Envelope/Body/calculateUnsharedRates/request/unsharedAccomadation/unSharedRoomDetail/specialNeedsRequested", value);
    }

    public void setUnsharedAccommSharedRoomSpecialNeedsRequest(String value) {
        setRequestNodeValueByXPath("/Envelope/Body/calculateUnsharedRates/request/unsharedAccomadation/sharedRoomDetail/specialNeedsRequested", value);
    }

    public void setUnsharedChainUnsharedRoomShared(String value) {
        setRequestNodeValueByXPath("/Envelope/Body/calculateUnsharedRates/request/unSharedChain/shareRoomDetails/unSharedRoomDetail/shared", value);
    }

    public void setUnsharedChainSharedRoomShared(String value) {
        setRequestNodeValueByXPath("/Envelope/Body/calculateUnsharedRates/request/unSharedChain/shareRoomDetails/sharedRoomDetail/shared", value);
    }

    public void setUnsharedAccommUnsharedRoomShared(String value) {
        setRequestNodeValueByXPath("/Envelope/Body/calculateUnsharedRates/request/unsharedAccomadation/unSharedRoomDetail/shared", value);
    }

    public void setUnsharedAccommSharedRoomShared(String value) {
        setRequestNodeValueByXPath("/Envelope/Body/calculateUnsharedRates/request/unsharedAccomadation/sharedRoomDetail/shared", value);
    }

    // getters
    public String getUnsharedChainUnsharedRoomShared() {
        return getResponseNodeValueByXPath("/Envelope/Body/calculateUnsharedRatesResponse/splitRateWithTotalTO/shareChains/shareRoomDetails/unSharedRoomDetail/shared");
    }

    public String getUnsharedChainSharedRoomShared() {
        return getResponseNodeValueByXPath("/Envelope/Body/calculateUnsharedRatesResponse/splitRateWithTotalTO/shareChains/shareRoomDetails/sharedRoomDetail/shared");
    }

    public String getUnsharedAccommUnsharedRoomShared() {
        return getResponseNodeValueByXPath("/Envelope/Body/calculateUnsharedRatesResponse/splitRateWithTotalTO/unsharedAccommodation/unSharedRoomDetail/shared");
    }

    public String getUnsharedAccommSharedRoomShared() {
        return getResponseNodeValueByXPath("/Envelope/Body/calculateUnsharedRatesResponse/splitRateWithTotalTO/unsharedAccommodation/sharedRoomDetail/shared");
    }

    public String getUnsharedChainUnsharedRoomSpecialNeedsRequest() {
        return getResponseNodeValueByXPath("/Envelope/Body/calculateUnsharedRatesResponse/splitRateWithTotalTO/shareChains/shareRoomDetails/unSharedRoomDetail/specialNeedsRequested");
    }

    public String getUnsharedChainSharedRoomSpecialNeedsRequest() {
        return getResponseNodeValueByXPath("/Envelope/Body/calculateUnsharedRatesResponse/splitRateWithTotalTO/shareChains/shareRoomDetails/sharedRoomDetail/specialNeedsRequested");
    }

    public String getUnsharedAccommUnsharedRoomSpecialNeedsRequest() {
        return getResponseNodeValueByXPath("/Envelope/Body/calculateUnsharedRatesResponse/splitRateWithTotalTO/unsharedAccommodation/unSharedRoomDetail/specialNeedsRequested");
    }

    public String getUnsharedAccommSharedRoomSpecialNeedsRequest() {
        return getResponseNodeValueByXPath("/Envelope/Body/calculateUnsharedRatesResponse/splitRateWithTotalTO/unsharedAccommodation/sharedRoomDetail/specialNeedsRequested");
    }

    public String getfreezeId() {
        return getResponseNodeValueByXPath("/Envelope/Body/calculateUnsharedRatesResponse/splitRateWithTotalTO/shareChains/shareRoomDetails/unSharedRoomDetail/freezeId");
    }

    public String getinventoryStatus() {
        return getResponseNodeValueByXPath("/Envelope/Body/calculateUnsharedRatesResponse/splitRateWithTotalTO/shareChains/shareRoomDetails/unSharedRoomDetail/inventoryStatus");
    }

    public String getUnsharedRoomDetailsAdditionalCharge() {
        return getResponseNodeValueByXPath("/Envelope/Body/calculateUnsharedRatesResponse/splitRateWithTotalTO/shareChains/shareRoomDetails/unSharedRoomDetail/rateDetails/additionalCharge");
    }

    public String getUnsharedRoomDetailsAdditionalChargeOveridden() {
        return getResponseNodeValueByXPath("/Envelope/Body/calculateUnsharedRatesResponse/splitRateWithTotalTO/shareChains/shareRoomDetails/unSharedRoomDetail/rateDetails/additionalChargeOverridden");
    }

    public String getUnsharedRoomDetailsBasePrice() {
        return getResponseNodeValueByXPath("/Envelope/Body/calculateUnsharedRatesResponse/splitRateWithTotalTO/shareChains/shareRoomDetails/unSharedRoomDetail/rateDetails/basePrice");
    }

    public String getUnsharedRoomDetailsDate() {
        return getResponseNodeValueByXPath("/Envelope/Body/calculateUnsharedRatesResponse/splitRateWithTotalTO/shareChains/shareRoomDetails/unSharedRoomDetail/rateDetails/date");
    }

    public String getUnsharedRoomDetailsDayCount() {
        return getResponseNodeValueByXPath("/Envelope/Body/calculateUnsharedRatesResponse/splitRateWithTotalTO/shareChains/shareRoomDetails/unSharedRoomDetail/rateDetails/dayCount");
    }

    public String getUnsharedRoomDetailsOveridden() {
        return getResponseNodeValueByXPath("/Envelope/Body/calculateUnsharedRatesResponse/splitRateWithTotalTO/shareChains/shareRoomDetails/unSharedRoomDetail/rateDetails/overidden");
    }

    public String getUnsharedRoomDetailsShared() {
        return getResponseNodeValueByXPath("/Envelope/Body/calculateUnsharedRatesResponse/splitRateWithTotalTO/shareChains/shareRoomDetails/unSharedRoomDetail/rateDetails/shared");
    }

    public String getUnsharedRoomDetailsNetPrice() {
        return getResponseNodeValueByXPath("/Envelope/Body/calculateUnsharedRatesResponse/splitRateWithTotalTO/shareChains/shareRoomDetails/unSharedRoomDetail/rateDetails/netPrice");
    }

    public String getUnsharedRoomDetailsPointsValue() {
        return getResponseNodeValueByXPath("/Envelope/Body/calculateUnsharedRatesResponse/splitRateWithTotalTO/shareChains/shareRoomDetails/unSharedRoomDetail/rateDetails/pointsValue");
    }

    public String getSharedRoomDetailsAdditionalCharge() {
        return getResponseNodeValueByXPath("/Envelope/Body/calculateUnsharedRatesResponse/splitRateWithTotalTO/unsharedAccommodation/sharedRoomDetail/rateDetails/additionalCharge");
    }

    public String getSharedRoomDetailsAdditionalChargeOveridden() {
        return getResponseNodeValueByXPath("/Envelope/Body/calculateUnsharedRatesResponse/splitRateWithTotalTO/unsharedAccommodation/sharedRoomDetail/rateDetails/additionalChargeOverridden");
    }

    public String getSharedRoomDetailsBasePrice() {
        return getResponseNodeValueByXPath("/Envelope/Body/calculateUnsharedRatesResponse/splitRateWithTotalTO/unsharedAccommodation/sharedRoomDetail/rateDetails/basePrice");
    }

    public String getSharedRoomDetailsDate() {
        return getResponseNodeValueByXPath("/Envelope/Body/calculateUnsharedRatesResponse/splitRateWithTotalTO/unsharedAccommodation/sharedRoomDetail/rateDetails/date");
    }

    public String getSharedRoomDetailsDayCount() {
        return getResponseNodeValueByXPath("/Envelope/Body/calculateUnsharedRatesResponse/splitRateWithTotalTO/unsharedAccommodation/sharedRoomDetail/rateDetails/dayCount");
    }

    public String getSharedRoomDetailsOveridden() {
        return getResponseNodeValueByXPath("/Envelope/Body/calculateUnsharedRatesResponse/splitRateWithTotalTO/unsharedAccommodation/sharedRoomDetail/rateDetails/overidden");
    }

    public String getSharedRoomDetailsShared() {
        return getResponseNodeValueByXPath("/Envelope/Body/calculateUnsharedRatesResponse/splitRateWithTotalTO/unsharedAccommodation/sharedRoomDetail/rateDetails/shared");
    }

    public String getSharedRoomDetailsNetPrice() {
        return getResponseNodeValueByXPath("/Envelope/Body/calculateUnsharedRatesResponse/splitRateWithTotalTO/unsharedAccommodation/sharedRoomDetail/rateDetails/netPrice");
    }

    public String getSharedRoomDetailsPointsValue() {
        return getResponseNodeValueByXPath("/Envelope/Body/calculateUnsharedRatesResponse/splitRateWithTotalTO/unsharedAccommodation/sharedRoomDetail/rateDetails/pointsValue");
    }

    public String getShareChainSharedRoomDetailsAdditionalCharge() {
        return getResponseNodeValueByXPath("/Envelope/Body/calculateUnsharedRatesResponse/splitRateWithTotalTO/shareChains/shareRoomDetails/sharedRoomDetail/rateDetails/additionalCharge");
    }

    public String getShareChainSharedRoomDetailsAdditionalChargeOveridden() {
        return getResponseNodeValueByXPath("/Envelope/Body/calculateUnsharedRatesResponse/splitRateWithTotalTO/shareChains/shareRoomDetails/sharedRoomDetail/rateDetails/additionalChargeOverridden");
    }

    public String getShareChainSharedRoomDetailsBasePrice() {
        return getResponseNodeValueByXPath("/Envelope/Body/calculateUnsharedRatesResponse/splitRateWithTotalTO/shareChains/shareRoomDetails/sharedRoomDetail/rateDetails/basePrice");
    }

    public String getShareChainSharedRoomDetailsDate() {
        return getResponseNodeValueByXPath("/Envelope/Body/calculateUnsharedRatesResponse/splitRateWithTotalTO/shareChains/shareRoomDetails/sharedRoomDetail/rateDetails/date");
    }

    public String getShareChainSharedRoomDetailsDayCount() {
        return getResponseNodeValueByXPath("/Envelope/Body/calculateUnsharedRatesResponse/splitRateWithTotalTO/shareChains/shareRoomDetails/sharedRoomDetail/rateDetails/dayCount");
    }

    public String getShareChainSharedRoomDetailsOveridden() {
        return getResponseNodeValueByXPath("/Envelope/Body/calculateUnsharedRatesResponse/splitRateWithTotalTO/shareChains/shareRoomDetails/sharedRoomDetail/rateDetails/overidden");
    }

    public String getShareChainSharedRoomDetailsShared() {
        return getResponseNodeValueByXPath("/Envelope/Body/calculateUnsharedRatesResponse/splitRateWithTotalTO/shareChains/shareRoomDetails/sharedRoomDetail/rateDetails/shared");
    }

    public String getShareChainSharedRoomDetailsNetPrice() {
        return getResponseNodeValueByXPath("/Envelope/Body/calculateUnsharedRatesResponse/splitRateWithTotalTO/shareChains/shareRoomDetails/sharedRoomDetail/rateDetails/netPrice");
    }

    public String getShareChainSharedRoomDetailsPointsValue() {
        return getResponseNodeValueByXPath("/Envelope/Body/calculateUnsharedRatesResponse/splitRateWithTotalTO/shareChains/shareRoomDetails/sharedRoomDetail/rateDetails/pointsValue");
    }

    public String getUnSharedRoomDetailsAdditionalCharge() {
        return getResponseNodeValueByXPath("/Envelope/Body/calculateUnsharedRatesResponse/splitRateWithTotalTO/unsharedAccommodation/unSharedRoomDetail/rateDetails/additionalCharge");
    }

    public String getUnSharedRoomDetailsAdditionalChargeOveridden() {
        return getResponseNodeValueByXPath("/Envelope/Body/calculateUnsharedRatesResponse/splitRateWithTotalTO/unsharedAccommodation/unSharedRoomDetail/rateDetails/additionalChargeOverridden");
    }

    public String getUnSharedRoomDetailsBasePrice() {
        return getResponseNodeValueByXPath("/Envelope/Body/calculateUnsharedRatesResponse/splitRateWithTotalTO/unsharedAccommodation/unSharedRoomDetail/rateDetails/basePrice");
    }

    public String getUnSharedRoomDetailsDate() {
        return getResponseNodeValueByXPath("/Envelope/Body/calculateUnsharedRatesResponse/splitRateWithTotalTO/unsharedAccommodation/unSharedRoomDetail/rateDetails/date");
    }

    public String getUnSharedRoomDetailsDayCount() {
        return getResponseNodeValueByXPath("/Envelope/Body/calculateUnsharedRatesResponse/splitRateWithTotalTO/unsharedAccommodation/unSharedRoomDetail/rateDetails/dayCount");
    }

    public String getUnSharedRoomDetailsOveridden() {
        return getResponseNodeValueByXPath("/Envelope/Body/calculateUnsharedRatesResponse/splitRateWithTotalTO/unsharedAccommodation/unSharedRoomDetail/rateDetails/overidden");
    }

    public String getUnSharedRoomDetailsShared() {
        return getResponseNodeValueByXPath("/Envelope/Body/calculateUnsharedRatesResponse/splitRateWithTotalTO/unsharedAccommodation/unSharedRoomDetail/rateDetails/shared");
    }

    public String getUnSharedRoomDetailsNetPrice() {
        return getResponseNodeValueByXPath("/Envelope/Body/calculateUnsharedRatesResponse/splitRateWithTotalTO/unsharedAccommodation/unSharedRoomDetail/rateDetails/netPrice");
    }

    public String getUnSharedRoomDetailsPointsValue() {
        return getResponseNodeValueByXPath("/Envelope/Body/calculateUnsharedRatesResponse/splitRateWithTotalTO/unsharedAccommodation/unSharedRoomDetail/rateDetails/pointsValue");
    }
}