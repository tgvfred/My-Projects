package com.disney.api.soapServices.accommodationModule.accommodationBatchComponentWSPort.operation;

import org.testng.SkipException;

import com.disney.api.soapServices.accommodationModule.accommodationBatchComponentWSPort.AccommodationBatchComponentWSPort;
import com.disney.api.soapServices.core.exceptions.XPathNotFoundException;
import com.disney.utils.XMLTools;

public class GetStagedRecordsForMassModify extends AccommodationBatchComponentWSPort {

    public GetStagedRecordsForMassModify(String environment) {
        super(environment);
        if (true) {
            throw new SkipException("This SOAP method is no longer supported. It is to be removed in a later iteration - WWA 10/03/17");
        }

        // Generate a request from a project xml file
        setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("getStagedRecordsForMassModify")));
        generateServiceContext();

        removeComments();
        removeWhiteSpace();
    }

    // Setter
    public void setProcessDataId(String value) {
        setRequestNodeValueByXPath(
                "/Envelope/Body/getStagedRecordsForMassModify/processDataId", value);
    }

    // Getters
    public String getStartDate() {
        return getResponseNodeValueByXPath("/Envelope/Body/getStagedRecordsForMassModifyResponse/return/roomDetail/resortPeriod/startDate");
    }

    public String getEndDate() {
        return getResponseNodeValueByXPath("/Envelope/Body/getStagedRecordsForMassModifyResponse/return/roomDetail/resortPeriod/endDate");
    }

    public String getPackageCode() {
        return getResponseNodeValueByXPath("/Envelope/Body/getStagedRecordsForMassModifyResponse/return/roomDetail/packageCode");
    }

    public String getResortCode() {
        return getResponseNodeValueByXPath("/Envelope/Body/getStagedRecordsForMassModifyResponse/return/roomDetail/resortCode");
    }

    public String getRoomType() {
        return getResponseNodeValueByXPath("/Envelope/Body/getStagedRecordsForMassModifyResponse/return/roomDetail/roomTypeCode");
    }

    public String getBlockCode() {
        try {
            return getResponseNodeValueByXPath("/Envelope/Body/getStagedRecordsForMassModifyResponse/return/roomDetail/blockCode");
        } catch (XPathNotFoundException e) {
            return null;
        }
    }

    public String getTcgId() {
        return getResponseNodeValueByXPath("/Envelope/Body/getStagedRecordsForMassModifyResponse/return/roomDetail/travelComponentGroupingId");
    }

    public String getTcId() {
        return getResponseNodeValueByXPath("/Envelope/Body/getStagedRecordsForMassModifyResponse/return/roomDetail/travelComponentId");
    }

    public String getTpsId() {
        return getResponseNodeValueByXPath("/Envelope/Body/getStagedRecordsForMassModifyResponse/return/travelPlanSegmentId");
    }

    public String getInventoryReasonCode() {
        return getResponseNodeValueByXPath("/Envelope/Body/getStagedRecordsForMassModifyResponse/return/roomDetail/inventoryOverrideReason");
    }

    public String getInventoryContactName() {
        return getResponseNodeValueByXPath("/Envelope/Body/getStagedRecordsForMassModifyResponse/return/roomDetail/inventoryOverrideContactName");
    }

    public String getGuestId() {
        return getResponseNodeValueByXPath("/Envelope/Body/getStagedRecordsForMassModifyResponse/return/travelPlanGuest/guestId");
    }

    public String getFirstName() {
        return getResponseNodeValueByXPath("/Envelope/Body/getStagedRecordsForMassModifyResponse/return/roomDetail/roomReservationDetail/guestReferenceDetails/guest/firstName");
    }

    public String getLastName() {
        return getResponseNodeValueByXPath("/Envelope/Body/getStagedRecordsForMassModifyResponse/return/roomDetail/roomReservationDetail/guestReferenceDetails/guest/lastName");
    }

    public String getPreferredLanguage() {
        return getResponseNodeValueByXPath("/Envelope/Body/getStagedRecordsForMassModifyResponse/return/travelPlanGuest/preferredLanguage");
    }

    public String getAdultTicket() {
        return getResponseNodeValueByXPath("/Envelope/Body/getStagedRecordsForMassModifyResponse/return/roomDetail/ticketDetails/adultTicket");
    }

    public String getHardTicketedEvent() {
        return getResponseNodeValueByXPath("/Envelope/Body/getStagedRecordsForMassModifyResponse/return/roomDetail/ticketDetails/hardTicketedEvent");
    }

    public String getBaseAdmissionProductId() {
        return getResponseNodeValueByXPath("/Envelope/Body/getStagedRecordsForMassModifyResponse/return/roomDetail/ticketDetails/baseAdmissionProductId");
    }

    public String getAge() {
        return getResponseNodeValueByXPath("/Envelope/Body/getStagedRecordsForMassModifyResponse/return/roomDetail/ticketDetails/guestReference/age");
    }

    public String getAgeType() {
        return getResponseNodeValueByXPath("/Envelope/Body/getStagedRecordsForMassModifyResponse/return/roomDetail/ticketDetails/guestReference/ageType");
    }

    public String getTicketGuestFirstName() {
        return getResponseNodeValueByXPath("/Envelope/Body/getStagedRecordsForMassModifyResponse/return/roomDetail/ticketDetails/guestReference/guest/firstName");
    }

    public String getTicketGuestLastName() {
        return getResponseNodeValueByXPath("/Envelope/Body/getStagedRecordsForMassModifyResponse/return/roomDetail/ticketDetails/guestReference/guest/lastName");
    }

    public String getPartOfPackage() {
        return getResponseNodeValueByXPath("/Envelope/Body/getStagedRecordsForMassModifyResponse/return/roomDetail/ticketDetails/partOfPackage");
    }
}
