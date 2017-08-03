package com.disney.api.soapServices.accommodationModule.accommodationBatchComponentWSPort.operation;

import com.disney.api.soapServices.accommodationModule.accommodationBatchComponentWSPort.AccommodationBatchComponentWSPort;
import com.disney.utils.XMLTools;

public class GetStagedRecordsForCancel extends AccommodationBatchComponentWSPort {

    public GetStagedRecordsForCancel(String environment, String scenario) {
        super(environment);

        // Generate a request from a project xml file
        setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("getStagedRecordsForCancel")));
        generateServiceContext();
        setRequestNodeValueByXPath(getTestScenario(getService(), getOperation(), scenario));
        removeComments();
        removeWhiteSpace();

    }

    // REQUEST
    public void setProcessDataId(String value) {
        setRequestNodeValueByXPath("/Envelope/Body/getStagedRecordsForCancel/processDataId[1]", value);
    }

    public void setProcessDataIdTwo(String value) {
        setRequestNodeValueByXPath("/Envelope/Body/getStagedRecordsForCancel/processDataId[2]", value);
    }

    // RESPONSE
    public String getReturn() {
        return getResponseNodeValueByXPath("/Envelope/Body/getStagedRecordsForCancelResponse/return");
    }

    public String getCancelContactName() {
        return getResponseNodeValueByXPath("/Envelope/Body/getStagedRecordsForCancelResponse/return/cancelContactName");
    }

    public String getCancelDate() {
        return getResponseNodeValueByXPath("/Envelope/Body/getStagedRecordsForCancelResponse/return/cancelDate");
    }

    public String getCancelReasonCode() {
        return getResponseNodeValueByXPath("/Envelope/Body/getStagedRecordsForCancelResponse/return/cancelReasonCode");
    }

    public String getOverridden() {
        return getResponseNodeValueByXPath("/Envelope/Body/getStagedRecordsForCancelResponse/return/overridden");
    }

    public String getWaived() {
        return getResponseNodeValueByXPath("/Envelope/Body/getStagedRecordsForCancelResponse/return/waived");
    }

    public String getOverriddenCancelFee() {
        return getResponseNodeValueByXPath("/Envelope/Body/getStagedRecordsForCancelResponse/return/overriddenCancelFee");
    }

    public String getTravelComponentGroupingId() {
        return getResponseNodeValueByXPath("/Envelope/Body/getStagedRecordsForCancelResponse/return/travelComponentGroupingId");
    }

    public String getCancelTickets() {
        return getResponseNodeValueByXPath("/Envelope/Body/getStagedRecordsForCancelResponse/return/cancelTickets");
    }

}
