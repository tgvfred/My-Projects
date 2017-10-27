
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
    public String getPartyId() {
        return getResponseNodeValueByXPath("//travelPlanInfo/travelPlanGuests/guest[1]/partyId");
    }

    public String getGuestId() {
        return getResponseNodeValueByXPath("//travelPlanInfo/travelPlanGuests/guest[1]/guestId");
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
        return getResponseNodeValueByXPath("//travelPlanInfo/travelPlanGuests/guest[1]/firstName");
    }

    public String getLastName() {
        return getResponseNodeValueByXPath("//travelPlanInfo/travelPlanGuests/guest[1]/lastName/");
    }

    public String getPhone() {
        return getResponseNodeValueByXPath("//travelPlanInfo/travelPlanGuests/guest[1]/phoneDetails/number");
    }

    public String getAddress() {
        return getResponseNodeValueByXPath("//travelPlanInfo/travelPlanGuests/guest[1]/addressDetails/addressLine1");
    }

    public String getEmail() {
        return getResponseNodeValueByXPath("//travelPlanInfo/travelPlanGuests/guest[1]/emailDetails/address");
    }

    public String getPPFirstName() {
        return getResponseNodeValueByXPath("//travelPlanInfo/primaryParty/guest[1]/firstName");
    }

    public String getPPLastName() {
        return getResponseNodeValueByXPath("//travelPlanInfo/primaryParty/guest[1]/lastName/");
    }

    public String getPPPhone() {
        return getResponseNodeValueByXPath("//travelPlanInfo/primaryParty/guest[1]/phoneDetails/number");
    }

    public String getPPAddress() {
        return getResponseNodeValueByXPath("//travelPlanInfo/primaryParty/guest[1]/addressDetails/addressLine1");
    }

    public String getPPEmail() {
        return getResponseNodeValueByXPath("//travelPlanInfo/primaryParty/guest[1]/emailDetails/address");
    }

    public String getTravelPlanId() {
        return getResponseNodeValueByXPath("//travelPlanInfo/travelPlanId");
    }

    public String getTravelStatus() {
        return getResponseNodeValueByXPath("//travelPlanInfo/travelStatus");
    }

    public String getRoomReadyNotificationInfoTP() {
        return getResponseNodeValueByXPath("//travelPlanInfo/roomReadyNotificationInformation/travelPlanId");
    }

    public String getRoomReadyNotificationInfoRequired() {
        return getResponseNodeValueByXPath("//travelPlanInfo/roomReadyNotificationInformation/required");
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
        return getResponseNodeValueByXPath("//travelPlanInfo/travelPlanSegments/areaPeriod/startDate");
    }

    public String getAreadPeriodED() {
        return getResponseNodeValueByXPath("//travelPlanInfo/travelPlanSegments/areaPeriod/endDate");
    }

    public String getCancellationNumber() {
        return getResponseNodeValueByXPath("//travelPlanInfo/travelPlanSegments/cancellationNumber");
    }

    public String getCelebrationCount() {
        return getResponseNodeValueByXPath("//travelPlanInfo/travelPlanSegments/celebrationCount");
    }

    public String getGuaranteed() {
        return getResponseNodeValueByXPath("//travelPlanInfo/travelPlanSegments/guaranteed");
    }

    public String getPeriodSD() {
        return getResponseNodeValueByXPath("//travelPlanInfo/travelPlanSegments/period/startDate");
    }

    public String getPeriodED() {
        return getResponseNodeValueByXPath("//travelPlanInfo/travelPlanSegments/period/endDate");
    }

    public String getStatus() {
        return getResponseNodeValueByXPath("//travelPlanInfo/travelPlanSegments/status");
    }

    public String getTPSfirstName() {
        return getResponseNodeValueByXPath("//travelPlanInfo/travelPlanSegments/primaryGuest/firstName");
    }

    public String getTPSLasttName() {
        return getResponseNodeValueByXPath("//travelPlanInfo/travelPlanSegments/primaryGuest/lastName");
    }

    public String getTPSphone() {
        return getResponseNodeValueByXPath("//travelPlanInfo/travelPlanSegments/primaryGuest/phoneDetails/number");
    }

    public String getTPSaddress() {
        return getResponseNodeValueByXPath("//travelPlanInfo/travelPlanSegments/primaryGuest/addressDetails/address");
    }

    public String getTPSemail() {
        return getResponseNodeValueByXPath("//travelPlanInfo/travelPlanSegments/primaryGuest/emailDetails/address");
    }

    public String getTPSPartyId() {
        return getResponseNodeValueByXPath("//travelPlanInfo/travelPlanSegments/primaryGuest/partyId");
    }

    public String getTPSGuestId() {
        return getResponseNodeValueByXPath("//travelPlanInfo/travelPlanSegments/primaryGuest/guestId");
    }

    public String getTravelPlanIdTPS() {
        return getResponseNodeValueByXPath("//travelPlanInfo/travelPlanSegments/travelPlanId");
    }

    public String getTravelPlanSegmentIdTPS() {
        return getResponseNodeValueByXPath("//travelPlanInfo/travelPlanSegments/travelPlanSegmentId");
    }

    public String getOnsiteMessagingEnabled() {
        return getResponseNodeValueByXPath("//travelPlanInfo/travelPlanSegments/onsiteMessagingEnabled");
    }

    public String getBundleDetailPresent() {
        return getResponseNodeValueByXPath("//travelPlanInfo/travelPlanSegments/bundleDetailPresent");
    }

    public String getVipLevel() {
        return getResponseNodeValueByXPath("//travelPlanInfo/travelPlanSegments/vipLevel");
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
        return getResponseNodeValueByXPath("//travelPlanInfo/travelPlanSegments/componentGroupings/accommodation/bookDate");

    }

    public String getAccommComponentId() {
        return getResponseNodeValueByXPath("//travelPlanInfo/travelPlanSegments/componentGroupings/accommodation/componentId");

    }

    public String getAccommFacilityId() {
        return getResponseNodeValueByXPath("//travelPlanInfo/travelPlanSegments/componentGroupings/accommodation/facilityId");

    }

    public String getAccommGroupTeamId() {
        return getResponseNodeValueByXPath("//travelPlanInfo/travelPlanSegments/componentGroupings/accommodation/groupTeamId");

    }

    public String getGuestReferencesfirstName() {
        return getResponseNodeValueByXPath("//travelPlanInfo/travelPlanSegments/componentGroupings/accommodation/guestReferences/guest/firstName");
    }

    public String getGuestReferencesLastName() {
        return getResponseNodeValueByXPath("//travelPlanInfo/travelPlanSegments/componentGroupings/accommodation/guestReferences/guest/lastName");
    }

    public String getGuestReferencesphone() {
        return getResponseNodeValueByXPath("//travelPlanInfo/travelPlanSegments/componentGroupings/accommodation/guestReferences/guest/phoneDetails/number");
    }

    public String getGuestReferencesaddress() {
        return getResponseNodeValueByXPath("//travelPlanInfo/travelPlanSegments/componentGroupings/accommodation/guestReferences/guest/addressDetails/address");
    }

    public String getGuestReferencesemail() {
        return getResponseNodeValueByXPath("//travelPlanInfo/travelPlanSegments/componentGroupings/accommodation/guestReferences/guest/emailDetails/address");
    }

    public String getGuestReferencesPartyId() {
        return getResponseNodeValueByXPath("//travelPlanInfo/travelPlanSegments/componentGroupings/accommodation/guestReferences/guest/partyId");
    }

    public String getGuestReferencesGuestId() {
        return getResponseNodeValueByXPath("//travelPlanInfo/travelPlanSegments/componentGroupings/accommodation/guestReferences/guest/guestId");
    }

    public String getAccommPackageCode() {
        return getResponseNodeValueByXPath("//travelPlanInfo/travelPlanSegments/componentGroupings/accommodation/packageCode");
    }

    public String getAccommPeriodSD() {
        return getResponseNodeValueByXPath("//travelPlanInfo/travelPlanSegments/componentGroupings/accommodation/period/startDate");

    }

    public String getAccommPeriodED() {
        return getResponseNodeValueByXPath("//travelPlanInfo/travelPlanSegments/componentGroupings/accommodation/period/endDate");

    }

    public String getAccommProfiles() {
        return getResponseNodeValueByXPath("//travelPlanInfo/travelPlanSegments/componentGroupings/accommodation/profiles");

    }

    public String getAccommRSR() {
        return getResponseNodeValueByXPath("//travelPlanInfo/travelPlanSegments/componentGroupings/accommodation/rsr");

    }

    public String getAccommWholesaler() {
        return getResponseNodeValueByXPath("//travelPlanInfo/travelPlanSegments/componentGroupings/accommodation/wholesaler");

    }

    public String getAccommRates() {
        return getResponseNodeValueByXPath("//travelPlanInfo/travelPlanSegments/componentGroupings/accommodation/rates");

    }

    public String getAccommResortCode() {
        return getResponseNodeValueByXPath("//travelPlanInfo/travelPlanSegments/componentGroupings/accommodation/resortCode");

    }

    public String getAccommRoomTypeCode() {
        return getResponseNodeValueByXPath("//travelPlanInfo/travelPlanSegments/componentGroupings/accommodation/roomTypeCode");

    }

    public String getAccommStatus() {
        return getResponseNodeValueByXPath("//travelPlanInfo/travelPlanSegments/componentGroupings/accommodation/status");

    }

    public String getAccommShared() {
        return getResponseNodeValueByXPath("//travelPlanInfo/travelPlanSegments/componentGroupings/accommodation/shared");

    }

    public String getAccommReservationType() {
        return getResponseNodeValueByXPath("//travelPlanInfo/travelPlanSegments/componentGroupings/accommodation/reservationType");

    }

    public String getAccommRoomOnly() {
        return getResponseNodeValueByXPath("//travelPlanInfo/travelPlanSegments/componentGroupings/accommodation/roomOnly");

    }

    public String getAccommSpecialNeedsRequested() {
        return getResponseNodeValueByXPath("//travelPlanInfo/travelPlanSegments/componentGroupings/accommodation/specialNeedsRequested");

    }

    public String getAccommTravelComponentGroupingId() {
        return getResponseNodeValueByXPath("//travelPlanInfo/travelPlanSegments/componentGroupings/travelComponentGroupingId");
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
        return getResponseNodeValueByXPath("//travelPlanInfo/travelPlanSegments/componentGroupings/accommodation/ticketDetails/status");

    }

    public String getTicketGuestId() {
        return getResponseNodeValueByXPath("//travelPlanInfo/travelPlanSegments/componentGroupings/accommodation/ticketDetails/guestReference/guest/guestId");

    }

    public String getTicketAgeType() {
        return getResponseNodeValueByXPath("//travelPlanInfo/travelPlanSegments/componentGroupings/accommodation/ticketDetails/guestReference/ageType");

    }

    public String getTicketCompoonentId() {
        return getResponseNodeValueByXPath("//travelPlanInfo/travelPlanSegments/componentGroupings/accommodation/ticketDetails/componentId");

    }

    public String getTicketCode() {
        return getResponseNodeValueByXPath("//travelPlanInfo/travelPlanSegments/componentGroupings/ticketDetails/code");
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
        setRequestNodeValueByXPath("//request/locationId", locationId);
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
