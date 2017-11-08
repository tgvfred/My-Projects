
package com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations;

import java.util.Arrays;

import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.AccommodationSalesServicePort;
import com.disney.api.soapServices.core.BaseSoapCommands;
import com.disney.api.soapServices.core.exceptions.XPathNotFoundException;
import com.disney.utils.XMLTools;
import com.disney.utils.dataFactory.database.Recordset;
import com.disney.utils.dataFactory.database.databaseImpl.OracleDatabase;

public class Retrieve extends AccommodationSalesServicePort {

    public Retrieve(String environment, String scenario) {
        super(environment);

        // Generate a request from a project xml file
        setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("retrieve")));

        generateServiceContext();
        setRequestNodeValueByXPath(getTestScenario(getService(), getOperation(), scenario));
        removeComments();
        removeWhiteSpace();
    }

    public Retrieve(String environment) {
        super(environment);

        // Generate a request from a project xml file
        setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("retrieve")));

        generateServiceContext();
        removeComments();
        removeWhiteSpace();
    }

    // getters

    public int getGuestCount() {
        return getNumberOfResponseNodesByXPath("//travelPlanInfo/travelPlanGuests");
    }

    public String getPartyId() {
        return getResponseNodeValueByXPath("//travelPlanInfo/travelPlanGuests/guest[1]/partyId");
    }

    public String getGuestId() {
        return getResponseNodeValueByXPath("//travelPlanInfo/travelPlanGuests/guest[1]/guestId");
    }

    public String getGuestId(String index) {
        return getResponseNodeValueByXPath("//travelPlanInfo/travelPlanGuests[" + index + "]/guest/guestId");
    }

    public String getTravelPlanSegmentId() {
        return getResponseNodeValueByXPath("//travelPlanInfo/travelPlanSegments/travelPlanSegmentId");

    }

    public String getTravelComponentGroupingId() {
        return getResponseNodeValueByXPath("//travelPlanInfo/travelPlanSegments/componentGroupings/travelComponentGroupingId");
    }

    public String getTravelComponentId() {
        return getResponseNodeValueByXPath("//travelPlanInfo/travelPlanSegments/componentGroupings/accommodation/componentId");
    }

    public String getBlockCode() {
        return getResponseNodeValueByXPath("/Envelope/Body/retrieveResponse/travelPlanInfo/travelPlanSegments/componentGroupings/accommodation/blockCode");
    }

    public String getTicketGroup() {
        return getResponseNodeValueByXPath("/Envelope/Body/retrieveResponse/travelPlanInfo/travelPlanSegments/componentGroupings/accommodation/ticketGroup");
    }

    public String getExternalRefNum() {
        return getResponseNodeValueByXPath("/Envelope/Body/retrieveResponse/travelPlanInfo/travelPlanSegments/componentGroupings/accommodation/externalReferenceDetails/externalReferenceNumber");
    }

    public String getExternalRefSource() {
        return getResponseNodeValueByXPath("/Envelope/Body/retrieveResponse/travelPlanInfo/travelPlanSegments/componentGroupings/accommodation/externalReferenceDetails/externalReferenceSource");
    }

    // Ext ref info ------------------------------

    public String getTPSExternalRefType() {
        return getResponseNodeValueByXPath("/Envelope/Body/retrieveResponse/travelPlanInfo/travelPlanSegments/externalReference/externalReferenceType");
    }

    public String getTPSExternalRefCode() {
        return getResponseNodeValueByXPath("/Envelope/Body/retrieveResponse/travelPlanInfo/travelPlanSegments/externalReference/externalReferenceCode");
    }

    public String getTPSExternalRefNumber() {
        return getResponseNodeValueByXPath("/Envelope/Body/retrieveResponse/travelPlanInfo/travelPlanSegments/externalReference/externalReferenceNumber");
    }

    public String getTPSExternalRefSource() {
        return getResponseNodeValueByXPath("/Envelope/Body/retrieveResponse/travelPlanInfo/travelPlanSegments/externalReference/externalReferenceSource");
    }

    public String getSecurityLevel() {
        return getResponseNodeValueByXPath("/Envelope/Body/retrieveResponse/travelPlanInfo/travelPlanSegments/securityLevel");
    }

    public String getGatheringId() {
        return getResponseNodeValueByXPath("/Envelope/Body/retrieveResponse/travelPlanInfo/travelPlanSegments/gatheringDetail/gatheringId");
    }

    public String getGatheringName() {
        return getResponseNodeValueByXPath("/Envelope/Body/retrieveResponse/travelPlanInfo/travelPlanSegments/gatheringDetail/gatheringName");
    }

    public String getGatheringType() {
        return getResponseNodeValueByXPath("/Envelope/Body/retrieveResponse/travelPlanInfo/travelPlanSegments/gatheringDetail/gatheringType");
    }

    public String getCommentText() {
        return getResponseNodeValueByXPath("/Envelope/Body/retrieveResponse/travelPlanInfo/travelPlanSegments/comments/commentText");
    }

    public String getCommentType() {
        return getResponseNodeValueByXPath("/Envelope/Body/retrieveResponse/travelPlanInfo/travelPlanSegments/comments/commentType");
    }
    // base validation
    /*
     * Validate travelPlanInfo.travelPlanGuests name, phone, address, email, party ID, guest ID
     * Validate travelPlanInfo.period
     * Validate travelPlanInfo.primaryParty name, phone, address, email, party ID, guest ID
     * Validate travelPlanInfo.travelPlanId
     * TPS Validation
     * Validate travelPlanInfo.travelStatus
     * Validate travelPlanInfo.roomReadyNotificationInformation
     */

    public String getFirstName() {
        return getResponseNodeValueByXPath("Envelope/Body/retrieveResponse/travelPlanInfo/travelPlanGuests/guest/firstName");
    }

    public String getLastName() {
        return getResponseNodeValueByXPath("Envelope/Body/retrieveResponse/travelPlanInfo/travelPlanGuests/guest/lastName");
    }

    public String getPhone() {
        return getResponseNodeValueByXPath("Envelope/Body/retrieveResponse/travelPlanInfo/travelPlanGuests/guest/phoneDetails/number");
    }

    public String getAddress() {
        return getResponseNodeValueByXPath("Envelope/Body/retrieveResponse/travelPlanInfo/travelPlanGuests/guest/addressDetails/addressLine1");
    }

    // TP Address Details --------------------------------------------------------------------------------------------------------------
    public int getTPAddressDetailsCount() {
        return getNumberOfResponseNodesByXPath("Envelope/Body/retrieveResponse/travelPlanInfo/travelPlanGuests/guest/addressDetails");
    }

    public String getTPAddressDetailsPrimary(String index) {
        return getResponseNodeValueByXPath("Envelope/Body/retrieveResponse/travelPlanInfo/travelPlanGuests/guest/addressDetails[" + index + "]/primary");
    }

    public String getTPAddressDetailsAddressLine1(String index) {
        return getResponseNodeValueByXPath("Envelope/Body/retrieveResponse/travelPlanInfo/travelPlanGuests/guest/addressDetails[" + index + "]/addressLine1");
    }

    public String getTPAddressDetailsCity(String index) {
        return getResponseNodeValueByXPath("Envelope/Body/retrieveResponse/travelPlanInfo/travelPlanGuests/guest/addressDetails[" + index + "]/city");
    }

    public String getTPAddressDetailsCountry(String index) {
        return getResponseNodeValueByXPath("Envelope/Body/retrieveResponse/travelPlanInfo/travelPlanGuests/guest/addressDetails[" + index + "]/country");
    }

    public String getTPAddressDetailsPostalCode(String index) {
        return getResponseNodeValueByXPath("Envelope/Body/retrieveResponse/travelPlanInfo/travelPlanGuests/guest/addressDetails[" + index + "]/postalCode");
    }

    public String getTPAddressDetailsState(String index) {
        return getResponseNodeValueByXPath("Envelope/Body/retrieveResponse/travelPlanInfo/travelPlanGuests/guest/addressDetails[" + index + "]/state");
    }

    public String getTPAddressDetailsRegion(String index) {
        return getResponseNodeValueByXPath("Envelope/Body/retrieveResponse/travelPlanInfo/travelPlanGuests/guest/addressDetails[" + index + "]/regionName");
    }

    // TP Email Details --------------------------------------------------------------------------------------------------------------

    public int getTPEmailDetailsCount() {
        return getNumberOfResponseNodesByXPath("/Envelope/Body/retrieveResponse/travelPlanInfo/travelPlanGuests/guest/emailDetails");
    }

    public String getTPEmailDetailsPrimary(String index) {
        return getResponseNodeValueByXPath("/Envelope/Body/retrieveResponse/travelPlanInfo/travelPlanGuests/guest/emailDetails[" + index + "]/primary");
    }

    public String getTPEmailDetailsAddress(String index) {
        return getResponseNodeValueByXPath("/Envelope/Body/retrieveResponse/travelPlanInfo/travelPlanGuests/guest/emailDetails[" + index + "]/address");
    }

    // TP Phone Details --------------------------------------------------------------------------------------------------------------

    public int getTPPhoneDetailsCount() {
        return getNumberOfResponseNodesByXPath("/Envelope/Body/retrieveResponse/travelPlanInfo/travelPlanGuests/guest/phoneDetails");
    }

    public String getTPPhoneDetailsPrimary(String index) {
        return getResponseNodeValueByXPath("/Envelope/Body/retrieveResponse/travelPlanInfo/travelPlanGuests/guest/phoneDetails[" + index + "]/primary");
    }

    public String getTPPhoneDetailsNumber(String index) {
        return getResponseNodeValueByXPath("/Envelope/Body/retrieveResponse/travelPlanInfo/travelPlanGuests/guest/phoneDetails[" + index + "]/number");
    }

    // PP Address Details ---------------------------------------------------------------------------------------------------------------

    public int getPrimaryPtyAddressDetailsCount() {
        return getNumberOfResponseNodesByXPath("/Envelope/Body/retrieveResponse/travelPlanInfo/primaryParty/guest/addressDetails");
    }

    public String getPPAddressDetailsPrimary(String index) {
        return getResponseNodeValueByXPath("/Envelope/Body/retrieveResponse/travelPlanInfo/primaryParty/guest/addressDetails[" + index + "]/primary");
    }

    public String getPPAddressDetailsAddressLine1(String index) {
        return getResponseNodeValueByXPath("Envelope/Body/retrieveResponse/travelPlanInfo/primaryParty/guest/addressDetails[" + index + "]/addressLine1");
    }

    public String getPPAddressDetailsCity(String index) {
        return getResponseNodeValueByXPath("Envelope/Body/retrieveResponse/travelPlanInfo/primaryParty/guest/addressDetails[" + index + "]/city");
    }

    public String getPPAddressDetailsCountry(String index) {
        return getResponseNodeValueByXPath("Envelope/Body/retrieveResponse/travelPlanInfo/primaryParty/guest/addressDetails[" + index + "]/country");
    }

    public String getPPAddressDetailsPostalCode(String index) {
        return getResponseNodeValueByXPath("Envelope/Body/retrieveResponse/travelPlanInfo/primaryParty/guest/addressDetails[" + index + "]/postalCode");
    }

    public String getPPAddressDetailsState(String index) {
        return getResponseNodeValueByXPath("Envelope/Body/retrieveResponse/travelPlanInfo/primaryParty/guest/addressDetails[" + index + "]/state");
    }

    public String getPPAddressDetailsRegion(String index) {
        return getResponseNodeValueByXPath("Envelope/Body/retrieveResponse/travelPlanInfo/primaryParty/guest/addressDetails[" + index + "]/regionName");
    }

    public String getPPFirstName() {
        return getResponseNodeValueByXPath("Envelope/Body/retrieveResponse/travelPlanInfo/primaryParty/guest/firstName");
    }

    public String getPPLastName() {
        return getResponseNodeValueByXPath("Envelope/Body/retrieveResponse/travelPlanInfo/primaryParty/guest/lastName");
    }

    public String getPPPhone() {
        return getResponseNodeValueByXPath("Envelope/Body/retrieveResponse/travelPlanInfo/primaryParty/guest/phoneDetails/number");
    }

    public String getPPAddress() {
        return getResponseNodeValueByXPath("Envelope/Body/retrieveResponse/travelPlanInfo/primaryParty/guest/addressDetails/addressLine1");
    }

    public String getPPEmail() {
        return getResponseNodeValueByXPath("Envelope/Body/retrieveResponse/travelPlanInfo/primaryParty/guest/emailDetails/address");
    }

    // PP Email Details --------------------------------------------------------------------------------------------------------------

    public int getPPEmailDetailsCount() {
        return getNumberOfResponseNodesByXPath("/Envelope/Body/retrieveResponse/travelPlanInfo/primaryParty/guest/emailDetails");
    }

    public String getPPEmailDetailsPrimary(String index) {
        return getResponseNodeValueByXPath("/Envelope/Body/retrieveResponse/travelPlanInfo/primaryParty/guest/emailDetails[" + index + "]/primary");
    }

    public String getPPEmailDetailsAddress(String index) {
        return getResponseNodeValueByXPath("/Envelope/Body/retrieveResponse/travelPlanInfo/primaryParty/guest/emailDetails[" + index + "]/address");
    }

    // PP Phone Details --------------------------------------------------------------------------------------------------------------

    public int getPPPhoneDetailsCount() {
        return getNumberOfResponseNodesByXPath("/Envelope/Body/retrieveResponse/travelPlanInfo/primaryParty/guest/phoneDetails");
    }

    public String getPPPhoneDetailsPrimary(String index) {
        return getResponseNodeValueByXPath("/Envelope/Body/retrieveResponse/travelPlanInfo/primaryParty/guest/phoneDetails[" + index + "]/primary");
    }

    public String getPPPhoneDetailsNumber(String index) {
        return getResponseNodeValueByXPath("/Envelope/Body/retrieveResponse/travelPlanInfo/primaryParty/guest/phoneDetails[" + index + "]/number");
    }

    // Component Groupings Address Details --------------------------------------------------------------------------------------------

    public int getCompGroupingsAddressDetailsCount() {
        return getNumberOfResponseNodesByXPath("/Envelope/Body/retrieveResponse/travelPlanInfo/travelPlanSegments/componentGroupings/accommodation/guestReferences/guest/addressDetails");
    }

    public String getTCGAddressDetailsPrimary(String index) {
        return getResponseNodeValueByXPath("/Envelope/Body/retrieveResponse/travelPlanInfo/travelPlanSegments/componentGroupings/accommodation/guestReferences/guest/addressDetails[" + index + "]/primary");
    }

    public String getTCGAddressDetailsAddressLine1(String index) {
        return getResponseNodeValueByXPath("Envelope/Body/retrieveResponse/travelPlanInfo/travelPlanSegments/componentGroupings/accommodation/guestReferences/guest/addressDetails[" + index + "]/addressLine1");
    }

    public String getTCGAddressDetailsCity(String index) {
        return getResponseNodeValueByXPath("Envelope/Body/retrieveResponse/travelPlanInfo/travelPlanSegments/componentGroupings/accommodation/guestReferences/guest/addressDetails[" + index + "]/city");
    }

    public String getTCGAddressDetailsCountry(String index) {
        return getResponseNodeValueByXPath("Envelope/Body/retrieveResponse/travelPlanInfo/travelPlanSegments/componentGroupings/accommodation/guestReferences/guest/addressDetails[" + index + "]/country");
    }

    public String getTCGAddressDetailsPostalCode(String index) {
        return getResponseNodeValueByXPath("Envelope/Body/retrieveResponse/travelPlanInfo/travelPlanSegments/componentGroupings/accommodation/guestReferences/guest/addressDetails[" + index + "]/postalCode");
    }

    public String getTCGAddressDetailsState(String index) {
        return getResponseNodeValueByXPath("Envelope/Body/retrieveResponse/travelPlanInfo/travelPlanSegments/componentGroupings/accommodation/guestReferences/guest/addressDetails[" + index + "]/state");
    }

    public String getTCGAddressDetailsRegion(String index) {
        return getResponseNodeValueByXPath("Envelope/Body/retrieveResponse/travelPlanInfo/travelPlanSegments/componentGroupings/accommodation/guestReferences/guest/addressDetails[" + index + "]/regionName");
    }

    // TCG Email Details --------------------------------------------------------------------------------------------------------------

    public int getTCGEmailDetailsCount() {
        return getNumberOfResponseNodesByXPath("/Envelope/Body/retrieveResponse/travelPlanInfo/travelPlanSegments/componentGroupings/accommodation/guestReferences/guest/emailDetails");
    }

    public String getTCGEmailDetailsPrimary(String index) {
        return getResponseNodeValueByXPath("/Envelope/Body/retrieveResponse/travelPlanInfo/travelPlanSegments/componentGroupings/accommodation/guestReferences/guest/emailDetails[" + index + "]/primary");
    }

    public String getTCGEmailDetailsAddress(String index) {
        return getResponseNodeValueByXPath("/Envelope/Body/retrieveResponse/travelPlanInfo/travelPlanSegments/componentGroupings/accommodation/guestReferences/guest/emailDetails[" + index + "]/address");
    }

    // TCG Phone Details --------------------------------------------------------------------------------------------------------------

    public int getTCGPhoneDetailsCount() {
        return getNumberOfResponseNodesByXPath("/Envelope/Body/retrieveResponse/travelPlanInfo/travelPlanSegments/componentGroupings/accommodation/guestReferences/guest/phoneDetails");
    }

    public String getTCGPhoneDetailsPrimary(String index) {
        return getResponseNodeValueByXPath("/Envelope/Body/retrieveResponse/travelPlanInfo/travelPlanSegments/componentGroupings/accommodation/guestReferences/guest/phoneDetails[" + index + "]/primary");
    }

    public String getTCGPhoneDetailsNumber(String index) {
        return getResponseNodeValueByXPath("/Envelope/Body/retrieveResponse/travelPlanInfo/travelPlanSegments/componentGroupings/accommodation/guestReferences/guest/phoneDetails[" + index + "]/number");
    }

    // TPS Address Details --------------------------------------------------------------------------------------------

    public int getTPSAddressDetailsCount() {
        return getNumberOfResponseNodesByXPath("/Envelope/Body/retrieveResponse/travelPlanInfo/travelPlanSegments/primaryGuest/addressDetails");
    }

    public String getTPSAddressDetailsPrimary(String index) {
        return getResponseNodeValueByXPath("/Envelope/Body/retrieveResponse/travelPlanInfo/travelPlanSegments/primaryGuest/addressDetails[" + index + "]/primary");
    }

    public String getTPSAddressDetailsAddressLine1(String index) {
        return getResponseNodeValueByXPath("Envelope/Body/retrieveResponse/travelPlanInfo/travelPlanSegments/primaryGuest/addressDetails[" + index + "]/addressLine1");
    }

    public String getTPSAddressDetailsCity(String index) {
        return getResponseNodeValueByXPath("Envelope/Body/retrieveResponse/travelPlanInfo/travelPlanSegments/primaryGuest/addressDetails[" + index + "]/city");
    }

    public String getTPSAddressDetailsCountry(String index) {
        return getResponseNodeValueByXPath("Envelope/Body/retrieveResponse/travelPlanInfo/travelPlanSegments/primaryGuest/addressDetails[" + index + "]/country");
    }

    public String getTPSAddressDetailsPostalCode(String index) {
        return getResponseNodeValueByXPath("Envelope/Body/retrieveResponse/travelPlanInfo/travelPlanSegments/primaryGuest/addressDetails[" + index + "]/postalCode");
    }

    public String getTPSAddressDetailsState(String index) {
        return getResponseNodeValueByXPath("Envelope/Body/retrieveResponse/travelPlanInfo/travelPlanSegments/primaryGuest/addressDetails[" + index + "]/state");
    }

    public String getTPSAddressDetailsRegion(String index) {
        return getResponseNodeValueByXPath("Envelope/Body/retrieveResponse/travelPlanInfo/travelPlanSegments/primaryGuest/addressDetails[" + index + "]/regionName");
    }

    // TPS Email Details --------------------------------------------------------------------------------------------------------------

    public int getTPSEmailDetailsCount() {
        return getNumberOfResponseNodesByXPath("/Envelope/Body/retrieveResponse/travelPlanInfo/travelPlanSegments/primaryGuest/emailDetails");
    }

    public String getTPSEmailDetailsPrimary(String index) {
        return getResponseNodeValueByXPath("/Envelope/Body/retrieveResponse/travelPlanInfo/travelPlanSegments/primaryGuest/emailDetails[" + index + "]/primary");
    }

    public String getTPSEmailDetailsAddress(String index) {
        return getResponseNodeValueByXPath("/Envelope/Body/retrieveResponse/travelPlanInfo/travelPlanSegments/primaryGuest/emailDetails[" + index + "]/address");
    }

    // TPS Phone Details --------------------------------------------------------------------------------------------------------------

    public int getTPSPhoneDetailsCount() {
        return getNumberOfResponseNodesByXPath("/Envelope/Body/retrieveResponse/travelPlanInfo/travelPlanSegments/primaryGuest/phoneDetails");
    }

    public String getTPSPhoneDetailsPrimary(String index) {
        return getResponseNodeValueByXPath("/Envelope/Body/retrieveResponse/travelPlanInfo/travelPlanSegments/primaryGuest/phoneDetails[" + index + "]/primary");
    }

    public String getTPSPhoneDetailsNumber(String index) {
        return getResponseNodeValueByXPath("/Envelope/Body/retrieveResponse/travelPlanInfo/travelPlanSegments/primaryGuest/phoneDetails[" + index + "]/number");
    }

    /// ---------------------------------------

    public String getEmail() {
        return getResponseNodeValueByXPath("Envelope/Body/retrieveResponse/travelPlanInfo/travelPlanGuests/guest/emailDetails/address");
    }

    public String getTravelPlanId() {
        return getResponseNodeValueByXPath("Envelope/Body/retrieveResponse/travelPlanInfo/travelPlanId");
    }

    public String getTravelStatus() {
        return getResponseNodeValueByXPath("Envelope/Body/retrieveResponse/travelPlanInfo/travelStatus");
    }

    public String getRoomReadyNotificationInfoTP() {
        return getResponseNodeValueByXPath("Envelope/Body/retrieveResponse/travelPlanInfo/roomReadyNotificationInformation/travelPlanId");
    }

    public String getRoomReadyNotificationInfoRequired() {
        return getResponseNodeValueByXPath("Envelope/Body/retrieveResponse/travelPlanInfo/roomReadyNotificationInformation/required");
    }

    /*
     * Tps validation
     * Validate travelPlanInfo.travelPlanSegments.areaPeriod
     * Validate travelPlanInfo.travelPlanSegments.cancellationNumber
     * Validate travelPlanInfo.travelPlanSegments.celebrationCount
     * TCG Validation
     * Validate travelPlanInfo.travelPlanSegments.confirmationDetails
     * Validate travelPlanInfo.travelPlanSegments.guaranteed
     * Validate travelPlanInfo.travelPlanSegments.period
     * Validate travelPlanInfo.travelPlanSegments.status
     * Validate travelPlanInfo.travelPlanSegments.primaryGuest name, phone, address, email, party ID, guest ID
     * Validate travelPlanInfo.travelPlanSegments.travelPlanId
     * Validate travelPlanInfo.travelPlanSegments.travelPlanSegmentId
     * Validate travelPlanInfo.travelPlanSegments.vipLevel
     * Validate travelPlanInfo.travelPlanSegments.bundleDetailPresent
     * Validate travelPlanInfo.travelPlanSegments.onsiteMessagingEnabled
     */

    public String getAreadPeriodSD() {
        return getResponseNodeValueByXPath("/Envelope/Body/retrieveResponse/travelPlanInfo/travelPlanSegments/areaPeriod/startDate");
    }

    public String getAreadPeriodED() {
        return getResponseNodeValueByXPath("/Envelope/Body/retrieveResponse/travelPlanInfo/travelPlanSegments/areaPeriod/endDate");
    }

    public String getCancellationNumber() {
        return getResponseNodeValueByXPath("/Envelope/Body/retrieveResponse/travelPlanInfo/travelPlanSegments/cancellationNumber");
    }

    public String getCelebrationCount() {
        return getResponseNodeValueByXPath("/Envelope/Body/retrieveResponse/travelPlanInfo/travelPlanSegments/celebrationCount");
    }

    public String getGuaranteed() {
        return getResponseNodeValueByXPath("/Envelope/Body/retrieveResponse/travelPlanInfo/travelPlanSegments/guaranteed");
    }

    public String getPeriodSD() {
        return getResponseNodeValueByXPath("/Envelope/Body/retrieveResponse/travelPlanInfo/travelPlanSegments/period/startDate");
    }

    public String getPeriodED() {
        return getResponseNodeValueByXPath("/Envelope/Body/retrieveResponse/travelPlanInfo/travelPlanSegments/period/endDate");
    }

    public String getStatus() {
        return getResponseNodeValueByXPath("/Envelope/Body/retrieveResponse/travelPlanInfo/travelPlanSegments/status");
    }

    public String getTPSfirstName() {
        return getResponseNodeValueByXPath("/Envelope/Body/retrieveResponse/travelPlanInfo/travelPlanSegments/primaryGuest/firstName");
    }

    public String getTPSLasttName() {
        return getResponseNodeValueByXPath("/Envelope/Body/retrieveResponse/travelPlanInfo/travelPlanSegments/primaryGuest/lastName");
    }

    public String getTPSphone() {
        return getResponseNodeValueByXPath("/Envelope/Body/retrieveResponse/travelPlanInfo/travelPlanSegments/primaryGuest/phoneDetails/number");
    }

    public String getTPSaddress() {
        return getResponseNodeValueByXPath("/Envelope/Body/retrieveResponse/travelPlanInfo/travelPlanSegments/primaryGuest/addressDetails/addressLine1");
    }

    public String getTPSemail() {
        return getResponseNodeValueByXPath("/Envelope/Body/retrieveResponse/travelPlanInfo/travelPlanSegments/primaryGuest/emailDetails/address");
    }

    public String getTPSPartyId() {
        return getResponseNodeValueByXPath("/Envelope/Body/retrieveResponse/travelPlanInfo/travelPlanSegments/primaryGuest/partyId");
    }

    public String getTPSGuestId() {
        return getResponseNodeValueByXPath("/Envelope/Body/retrieveResponse/travelPlanInfo/travelPlanSegments/primaryGuest/guestId");
    }

    public String getTravelPlanIdTPS() {
        return getResponseNodeValueByXPath("/Envelope/Body/retrieveResponse/travelPlanInfo/travelPlanSegments/travelPlanId");
    }

    public String getTravelPlanSegmentIdTPS() {
        return getResponseNodeValueByXPath("/Envelope/Body/retrieveResponse/travelPlanInfo/travelPlanSegments/travelPlanSegmentId");
    }

    public String getOnsiteMessagingEnabled() {
        return getResponseNodeValueByXPath("/Envelope/Body/retrieveResponse/travelPlanInfo/travelPlanSegments/onsiteMessagingEnabled");
    }

    public String getBundleDetailPresent() {
        return getResponseNodeValueByXPath("/Envelope/Body/retrieveResponse/travelPlanInfo/travelPlanSegments/bundleDetailPresent");
    }

    public String getVipLevel() {
        return getResponseNodeValueByXPath("/Envelope/Body/retrieveResponse/travelPlanInfo/travelPlanSegments/vipLevel");
    }
    /*
     * Tcg validation
     * Validate travelPlanInfo.travelPlanSegments.componentGroupings.accommodation.bookDate
     * Validate travelPlanInfo.travelPlanSegments.componentGroupings.accommodation.componentId
     * Validate travelPlanInfo.travelPlanSegments.componentGroupings.accommodation.facilityId
     * Validate travelPlanInfo.travelPlanSegments.componentGroupings.accommodation.groupTeamId
     * Validate travelPlanInfo.travelPlanSegments.componentGroupings.accommodation.guestReferences name, phone, address, email, party ID, guest ID
     * Validate travelPlanInfo.travelPlanSegments.componentGroupings.accommodation.packageCode
     * Validate travelPlanInfo.travelPlanSegments.componentGroupings.accommodation.period
     * Validate travelPlanInfo.travelPlanSegments.componentGroupings.accommodation.profiles
     * Validate travelPlanInfo.travelPlanSegments.componentGroupings.accommodation.RSR
     * Validate travelPlanInfo.travelPlanSegments.componentGroupings.accommodation.wholesaler
     * Validate travelPlanInfo.travelPlanSegments.componentGroupings.accommodation.rates
     * Validate travelPlanInfo.travelPlanSegments.componentGroupings.accommodation.resortCode
     * Validate travelPlanInfo.travelPlanSegments.componentGroupings.accommodation.roomTypeCode
     * Validate travelPlanInfo.travelPlanSegments.componentGroupings.accommodation.status
     * Validate travelPlanInfo.travelPlanSegments.componentGroupings.accommodation.shared
     * Validate travelPlanInfo.travelPlanSegments.componentGroupings.accommodation.reservationType
     * Validate travelPlanInfo.travelPlanSegments.componentGroupings.accommodation.roomOnly
     * Validate travelPlanInfo.travelPlanSegments.componentGroupings.accommodation.specialNeedsRequested
     * Validate travelPlanInfo.travelPlanSegments.componentGroupings.travelComponentGroupingId
     */

    public String getAccommBookDate() {
        return getResponseNodeValueByXPath("/Envelope/Body/retrieveResponse/travelPlanInfo/travelPlanSegments/componentGroupings/accommodation/bookDate");

    }

    public String getAccommComponentId() {
        return getResponseNodeValueByXPath("/Envelope/Body/retrieveResponse/travelPlanInfo/travelPlanSegments/componentGroupings/accommodation/componentId");

    }

    public String getAccommFacilityId() {
        return getResponseNodeValueByXPath("/Envelope/Body/retrieveResponse/travelPlanInfo/travelPlanSegments/componentGroupings/accommodation/facilityId");

    }

    public String getAccommGroupTeamId() {
        return getResponseNodeValueByXPath("/Envelope/Body/retrieveResponse/travelPlanInfo/travelPlanSegments/componentGroupings/accommodation/groupTeamId");

    }

    // Guest References --------------------------------------------------------------------------------------------------------------------------------------------

    public String getGuestReferencesfirstName(int j, int i) {
        return getResponseNodeValueByXPath("/Envelope/Body/retrieveResponse/travelPlanInfo/travelPlanSegments/componentGroupings[" + j + "]/accommodation/guestReferences[" + i + "]/guest/firstName");
    }

    public String getGuestReferencesLastName(int j, int i) {
        return getResponseNodeValueByXPath("/Envelope/Body/retrieveResponse/travelPlanInfo/travelPlanSegments/componentGroupings[" + j + "]/accommodation/guestReferences[" + i + "]/guest/lastName");
    }

    public String getGuestReferencesphone(int j, int i) {
        return getResponseNodeValueByXPath("/Envelope/Body/retrieveResponse/travelPlanInfo/travelPlanSegments/componentGroupings[" + j + "]/accommodation/guestReferences[" + i + "]/guest/phoneDetails/number");
    }

    public String getGuestReferencesaddress(int j, int i) {
        return getResponseNodeValueByXPath("/Envelope/Body/retrieveResponse/travelPlanInfo/travelPlanSegments/componentGroupings[" + j + "]/accommodation/guestReferences[" + i + "]/guest/addressDetails/addressLine1");
    }

    public String getGuestReferencesemail(int j, int i) {
        return getResponseNodeValueByXPath("/Envelope/Body/retrieveResponse/travelPlanInfo/travelPlanSegments/componentGroupings[" + j + "]/accommodation/guestReferences[" + i + "]/guest/emailDetails/address");
    }

    public String getGuestReferencesPartyId(int j, int i) {
        return getResponseNodeValueByXPath("/Envelope/Body/retrieveResponse/travelPlanInfo/travelPlanSegments/componentGroupings[" + j + "]/accommodation/guestReferences[" + i + "]/guest/partyId");
    }

    public String getGuestReferencesGuestId(int j, int i) {
        return getResponseNodeValueByXPath("/Envelope/Body/retrieveResponse/travelPlanInfo/travelPlanSegments/componentGroupings[" + j + "]/accommodation/guestReferences[" + i + "]/guest/guestId");
    }

    // Guestid References --------------------------------------------------------------------------------------------------------------------------------------

    public String getGuestIdReferencesType() {
        return getResponseNodeValueByXPath("/Envelope/Body/retrieveResponse/travelPlanInfo/travelPlanGuests/guest/guestIdReferences/type");
    }

    public String getGuestIdReferencesValue() {
        return getResponseNodeValueByXPath("/Envelope/Body/retrieveResponse/travelPlanInfo/travelPlanGuests/guest/guestIdReferences/value");
    }

    public String getPPGuestIdReferencesType() {
        return getResponseNodeValueByXPath("/Envelope/Body/retrieveResponse/travelPlanInfo/primaryParty/guest/guestIdReferences/type");
    }

    public String getPPGuestIdReferencesValue() {
        return getResponseNodeValueByXPath("/Envelope/Body/retrieveResponse/travelPlanInfo/primaryParty/guest/guestIdReferences/value");
    }

    public String getTCGGuestIdReferencesType() {
        return getResponseNodeValueByXPath("/Envelope/Body/retrieveResponse/travelPlanInfo/travelPlanSegments/componentGroupings/accommodation/guestReferences/guest/guestIdReferences/type");
    }

    public String getTCGGuestIdReferencesValue() {
        return getResponseNodeValueByXPath("/Envelope/Body/retrieveResponse/travelPlanInfo/travelPlanSegments/componentGroupings/accommodation/guestReferences/guest/guestIdReferences/value");
    }

    public String getTPSGuestIdReferencesType() {
        return getResponseNodeValueByXPath("/Envelope/Body/retrieveResponse/travelPlanInfo/travelPlanSegments/primaryGuest/guestIdReferences/type");
    }

    public String getTPSGuestIdReferencesValue() {
        return getResponseNodeValueByXPath("/Envelope/Body/retrieveResponse/travelPlanInfo/travelPlanSegments/primaryGuest/guestIdReferences/value");
    }

    public String getAccommPackageCode() {
        return getResponseNodeValueByXPath("/Envelope/Body/retrieveResponse/travelPlanInfo/travelPlanSegments/componentGroupings/accommodation/packageCode");
    }

    public String getAccommPeriodSD() {
        return getResponseNodeValueByXPath("/Envelope/Body/retrieveResponse/travelPlanInfo/travelPlanSegments/componentGroupings/accommodation/period/startDate");

    }

    public String getAccommPeriodED() {
        return getResponseNodeValueByXPath("/Envelope/Body/retrieveResponse/travelPlanInfo/travelPlanSegments/componentGroupings/accommodation/period/endDate");

    }

    public String getAccommProfiles() {
        return getResponseNodeValueByXPath("/Envelope/Body/retrieveResponse/travelPlanInfo/travelPlanSegments/componentGroupings/accommodation/profiles");

    }

    public String getAccommRSR() {
        return getResponseNodeValueByXPath("/Envelope/Body/retrieveResponse/travelPlanInfo/travelPlanSegments/componentGroupings/accommodation/rsr");

    }

    public String getAccommWholesaler() {
        return getResponseNodeValueByXPath("/Envelope/Body/retrieveResponse/travelPlanInfo/travelPlanSegments/componentGroupings/accommodation/wholesaler");

    }

    public String getAccommRates() {
        return getResponseNodeValueByXPath("/Envelope/Body/retrieveResponse/travelPlanInfo/travelPlanSegments/componentGroupings/accommodation/rates");

    }

    public String getAccommRatesDate(String index) {
        return getResponseNodeValueByXPath("/Envelope/Body/retrieveResponse/travelPlanInfo/travelPlanSegments/componentGroupings/accommodation/rates[" + index + "]/date");
    }

    public int getAccommRatesCount() {
        return getNumberOfResponseNodesByXPath("/Envelope/Body/retrieveResponse/travelPlanInfo/travelPlanSegments/componentGroupings/accommodation/rates");
    }

    public String getAccommResortCode() {
        return getResponseNodeValueByXPath("/Envelope/Body/retrieveResponse/travelPlanInfo/travelPlanSegments/componentGroupings/accommodation/resortCode");

    }

    public String getAccommRoomTypeCode() {
        return getResponseNodeValueByXPath("/Envelope/Body/retrieveResponse/travelPlanInfo/travelPlanSegments/componentGroupings/accommodation/roomTypeCode");

    }

    public String getAccommStatus() {
        return getResponseNodeValueByXPath("/Envelope/Body/retrieveResponse/travelPlanInfo/travelPlanSegments/componentGroupings/accommodation/status");

    }

    public String getAccommShared() {
        return getResponseNodeValueByXPath("/Envelope/Body/retrieveResponse/travelPlanInfo/travelPlanSegments/componentGroupings/accommodation/shared");

    }

    public String getAccommReservationType() {
        return getResponseNodeValueByXPath("/Envelope/Body/retrieveResponse/travelPlanInfo/travelPlanSegments/componentGroupings/accommodation/reservationType");

    }

    public String getAccommRoomOnly() {
        return getResponseNodeValueByXPath("/Envelope/Body/retrieveResponse/travelPlanInfo/travelPlanSegments/componentGroupings/accommodation/roomOnly");

    }

    public String getAccommSpecialNeedsRequested() {
        return getResponseNodeValueByXPath("/Envelope/Body/retrieveResponse/travelPlanInfo/travelPlanSegments/componentGroupings/accommodation/specialNeedsRequested");

    }

    public String getAccommTravelComponentGroupingId() {
        return getResponseNodeValueByXPath("/Envelope/Body/retrieveResponse/travelPlanInfo/travelPlanSegments/componentGroupings/travelComponentGroupingId");
    }

    /*
     * tickets validation
     * Validate travelPlanInfo.travelPlanSegments.componentGroupings.accommodation.ticketDetails.status
     * Validate travelPlanInfo.travelPlanSegments.componentGroupings.accommodation.ticketDetails.guestReference.guest.guestId
     * Validate travelPlanInfo.travelPlanSegments.componentGroupings.accommodation.ticketDetails.guestReference.ageType
     * Validate travelPlanInfo.travelPlanSegments.componentGroupings.accommodation.ticketDetails.componentId
     * Validate travelPlanInfo.travelPlanSegments.componentGroupings.accommodation.ticketDetails.code
     */
    public String getTicketStatus() {
        return getResponseNodeValueByXPath("/Envelope/Body/retrieveResponse/travelPlanInfo/travelPlanSegments/componentGroupings/accommodation/ticketDetails/status");

    }

    public String getTicketGuestId() {
        return getResponseNodeValueByXPath("/Envelope/Body/retrieveResponse/travelPlanInfo/travelPlanSegments/componentGroupings/accommodation/ticketDetails/guestReference/guest/guestId");

    }

    public String getTicketAgeType() {
        return getResponseNodeValueByXPath("/Envelope/Body/retrieveResponse/travelPlanInfo/travelPlanSegments/componentGroupings/accommodation/ticketDetails/guestReference/ageType");

    }

    public String getTicketComponentId() {
        return getResponseNodeValueByXPath("/Envelope/Body/retrieveResponse/travelPlanInfo/travelPlanSegments/componentGroupings/accommodation/ticketDetails/componentId");

    }

    public String getTicketCode() {
        return getResponseNodeValueByXPath("/Envelope/Body/retrieveResponse/travelPlanInfo/travelPlanSegments/componentGroupings/accommodation/ticketDetails/code");
    }

    public String getAuditDetailsStatus(int i) {

        return getResponseNodeValueByXPath("/Envelope/Body/retrieveResponse/travelPlanInfo/travelPlanSegments/auditDetails[" + i + "]/status");

    }

    public String getRoomNumber() {

        return getResponseNodeValueByXPath("/Envelope/Body/retrieveResponse/travelPlanInfo/travelPlanSegments/componentGroupings/accommodation/roomNumber");

    }

    public String getProfileCodeBooking() {
        return getResponseNodeValueByXPath("/Envelope/Body/replaceAllForTravelPlanSegmentResponse/response/roomDetails/roomReservationDetail/profiles/code");

    }

    public String getProfileCodeRetrieveRQ(int i) {

        return getResponseNodeValueByXPath("/Envelope/Body/retrieveResponse/travelPlanInfo/travelPlanSegments/componentGroupings/accommodation/profiles[" + i + "]/code");

    }

    public String getProfileIDRetrieveRQ(int i) {

        return getResponseNodeValueByXPath("/Envelope/Body/retrieveResponse/travelPlanInfo/travelPlanSegments/componentGroupings/accommodation/profiles[" + i + "]/id");

    }

    public String getProfileTypeRetrieveRQ(int i) {

        return getResponseNodeValueByXPath("/Envelope/Body/retrieveResponse/travelPlanInfo/travelPlanSegments/componentGroupings/accommodation/profiles[" + i + "]/profileType");

    }

    public String getProfileRoutingNameRetrieveRQ(int i) {

        return getResponseNodeValueByXPath("/Envelope/Body/retrieveResponse/travelPlanInfo/travelPlanSegments/componentGroupings/accommodation/profiles[" + i + "]/routings/name");

    }
    // travel agency getters

    public String getAgencyName() {
        return getResponseNodeValueByXPath("/Envelope/Body/retrieveResponse/travelPlanInfo/travelPlanSegments/travelAgency/agencyName");

    }

    public String getAgencyOdsId() {
        return getResponseNodeValueByXPath("/Envelope/Body/retrieveResponse/travelPlanInfo/travelPlanSegments/travelAgency/agencyOdsId");

    }

    public String getGuestTravelAgencyId() {

        return getResponseNodeValueByXPath("/Envelope/Body/retrieveResponse/travelPlanInfo/travelPlanSegments/travelAgency/guestTravelAgencyId");

    }

    public String getAgentId() {
        return getResponseNodeValueByXPath("/Envelope/Body/retrieveResponse/travelPlanInfo/travelPlanSegments/travelAgency/agentId");

    }

    public String getGuestAgentId() {
        return getResponseNodeValueByXPath("/Envelope/Body/retrieveResponse/travelPlanInfo/travelPlanSegments/travelAgency/guestAgentId");

    }

    public String getConfirmationLocatorValue() {

        return getResponseNodeValueByXPath("/Envelope/Body/retrieveResponse/travelPlanInfo/travelPlanSegments/travelAgency/confirmationLocatorValue");

    }

    public String getGuestConfirmationLocationId() {
        return getResponseNodeValueByXPath("/Envelope/Body/retrieveResponse/travelPlanInfo/travelPlanSegments/travelAgency/guestConfirmationLocationId");

    }

    // olci getters

    public String getOnlineCheckInIndicator() {

        return getResponseNodeValueByXPath("/Envelope/Body/retrieveResponse/travelPlanInfo/onLineCheckInIndicator");

    }

    public String[] getTravelComponentIDs(int numberOfTcIds) {
        String[] TC_IDs = new String[numberOfTcIds];
        int loopCounter;

        for (loopCounter = 1; loopCounter <= numberOfTcIds; loopCounter++) {
            String xpath = "/Envelope/Body/retrieveResponse/travelPlanInfo/travelPlanSegments/componentGroupings[" + String.valueOf(loopCounter) + "]/accommodation/componentId";
            TC_IDs[loopCounter - 1] = getResponseNodeValueByXPath(xpath);
        }

        return TC_IDs;
    }

    public String getAddressLocatorId() {
        return getResponseNodeValueByXPath("//travelPlanInfo/travelPlanGuests/guest/addressDetails/locatorId");
    }

    public String[] getTravelComponentGroupingIDs(int numberOfTcgIds) {
        String[] TCG_IDs = new String[numberOfTcgIds];
        int loopCounter;

        for (loopCounter = 1; loopCounter <= numberOfTcgIds; loopCounter++) {
            String xpath = "/Envelope/Body/retrieveResponse/travelPlanInfo/travelPlanSegments/componentGroupings[" + String.valueOf(loopCounter) + "]/travelComponentGroupingId";
            TCG_IDs[loopCounter - 1] = getResponseNodeValueByXPath(xpath);
        }

        return TCG_IDs;
    }

    public String queryAndGetRandomTravelPlanId() {
        OracleDatabase db = new OracleDatabase("Sleepy", "Dreams");
        Recordset rs = new Recordset(db.getResultSet("Select * FROM RES_MGMT.TPS WHERE ROWNUM < 10"));

        // System.out.println("Example 1");
        for (rs.moveFirst(); rs.hasNext(); rs.moveNext()) {
            // System.out.println(rs.getValue("TPS_ID"));
        }

        // System.out.println();
        // System.out.println("Example 2");
        rs.moveFirst();
        String report = "";
        for (int row = 0; rs.hasNext(); rs.moveNext(), row++) {
            report = "|";
            for (int column = 1; column < rs.getColumnCount(); column++) {
                report += rs.getValue(column, row) + " | ";
            }

            // System.out.println(report);

        }

        // System.out.println();
        // System.out.println();
        // rs.print();
        return "";
    }

    // setters
    public void setTravelPlanId(String TP_ID) {
        setRequestNodeValueByXPath("//request/travelPlanId", TP_ID);
    }

    public void setLocationId(String locationId) {
        setRequestNodeValueByXPath("Envelope/Body/retrieve/request/locationId", locationId);
    }

    public void setSiebelTravelPlanId(String value) {

        setRequestNodeValueByXPath("Envelope/Body/retrieve/request/siebelTravelPlanId", value);
    }

    public String[] getGuestIds(int numberOfGuests) {
        String[] guestIds = new String[numberOfGuests];

        for (int loopCounter = 1; loopCounter <= numberOfGuests; loopCounter++) {
            String xpath = "/Envelope/Body/retrieveResponse/travelPlanInfo/travelPlanGuests[" + String.valueOf(loopCounter) + "]/guest/guestId";
            guestIds[loopCounter - 1] = getResponseNodeValueByXPath(xpath);
        }

        Arrays.sort(guestIds);

        return guestIds;
    }

    public String[] getFacilityIds(int numberOfFacilityIds) {
        String[] facilityIds = new String[numberOfFacilityIds];

        for (int loopCounter = 1; loopCounter <= numberOfFacilityIds; loopCounter++) {
            String xpath = "/Envelope/Body/retrieveResponse/travelPlanInfo/travelPlanSegments/componentGroupings[" + String.valueOf(loopCounter) + "]/accommodation/facilityId";
            facilityIds[loopCounter - 1] = getResponseNodeValueByXPath(xpath);
        }

        return facilityIds;
    }

    public String getFacilityId() {
        return getResponseNodeValueByXPath("/Envelope/Body/retrieveResponse/travelPlanInfo/travelPlanSegments/componentGroupings/accommodation/facilityId");
    }

    public void setTravelPlanSegmentId(String value) {
        try {
            setRequestNodeValueByXPath("/Envelope/Body/retrieve/request/travelPlanSegmentId", value);
        } catch (XPathNotFoundException e) {
            setRequestNodeValueByXPath("/Envelope/Body/retrieve/request", BaseSoapCommands.ADD_NODE.commandAppend("travelPlanSegmentId"));
            setRequestNodeValueByXPath("/Envelope/Body/retrieve/request/travelPlanSegmentId", value);
        }
    }

    // base validation getters

}
