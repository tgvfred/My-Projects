package com.disney.api.soapServices.accommodationModule.accommodationBatchComponentWSPort.operation;

import com.disney.api.soapServices.accommodationModule.accommodationBatchComponentWSPort.AccommodationBatchComponentWSPort;
import com.disney.utils.XMLTools;

public class GetStagedRecordsForMassModify extends AccommodationBatchComponentWSPort {

    public GetStagedRecordsForMassModify(String environment) {
        super(environment);

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

    public String getTcgId() {
        return getResponseNodeValueByXPath("/Envelope/Body/getStagedRecordsForMassModifyResponse/return/roomDetail/travelComponentGroupingId");
    }

    public String getTcId() {
        return getResponseNodeValueByXPath("/Envelope/Body/getStagedRecordsForMassModifyResponse/return/roomDetail/travelComponentId");
    }

    public String getTpsId() {
        return getResponseNodeValueByXPath("/Envelope/Body/getStagedRecordsForMassModifyResponse/return/travelPlanSegmentId");
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

}
