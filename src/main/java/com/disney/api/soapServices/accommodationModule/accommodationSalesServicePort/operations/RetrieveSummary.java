package com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations;

import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.AccommodationSalesServicePort;
import com.disney.api.soapServices.core.BaseSoapCommands;
import com.disney.api.soapServices.core.exceptions.XPathNotFoundException;
import com.disney.utils.XMLTools;

public class RetrieveSummary extends AccommodationSalesServicePort {

    public RetrieveSummary(String environment) {
        super(environment);

        // Generate a request from a project xml file
        setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("retrieveSummary")));
        removeComments();
        removeWhiteSpace();
        generateServiceContext();
    }

    public RetrieveSummary(String environment, String scenario) {
        super(environment);

        // Generate a request from a project xml file
        setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("retrieveSummary")));
        generateServiceContext();
        // setRequestNodeValueByXPath(getTestScenario("/services/accommodationSalesServicePort/RetrieveSummay/RetrieveSummay.xls", scenario));
        setRequestNodeValueByXPath(getTestScenario(getService(), getOperation(), scenario));
        removeComments();
        removeWhiteSpace();
    }

    // Setters
    public void setRequestTravelComponentGroupingId(String tcgId) {
        setRequestNodeValueByXPath("/Envelope/Body/retrieveSummary/request/travelComponentGroupingIds", tcgId);
    }

    int index3 = getNumberOfRequestNodesByXPath("/Envelope/Body/retrieveSummaryResponse/accommodationsSummaryDetails/accommodationDetail/guestReferences") + 1;

    public void setRequestTravelComponentGroupingIdIndex(String index3, String tcgId) {
        setRequestNodeValueByXPath("/Envelope/Body/retrieveSummary/request/travelComponentGroupingIds[" + index3 + "]", tcgId);
    }

    public void setRequestTravelComponentGroupingIdIndexAdd(String index3, String tcgId) {
        try {
            setRequestNodeValueByXPath("/Envelope/Body/retrieveSummary/request/travelComponentGroupingIds[" + index3 + "]", tcgId);
        } catch (XPathNotFoundException e) {
            setRequestNodeValueByXPath("/Envelope/Body/retrieveSummary/request", BaseSoapCommands.ADD_NODE.commandAppend("travelComponentGroupingIds"));
            setRequestNodeValueByXPath("/Envelope/Body/retrieveSummary/request/travelComponentGroupingIds[" + index3 + "]", tcgId);
        }
    }

    public void setRequestTravelComponentId(String value) {
        try {
            setRequestNodeValueByXPath("/Envelope/Body/retrieveSummary/request/travelComponentIds", value);
        } catch (XPathNotFoundException e) {
            setRequestNodeValueByXPath("/Envelope/Body/retrieveSummary/request", BaseSoapCommands.ADD_NODE.commandAppend("travelComponentIds"));
            setRequestNodeValueByXPath("/Envelope/Body/retrieveSummary/request/travelComponentIds", value);
        }
    }

    public void setRequestRetrieveTaxExempt(String value) {
        try {
            setRequestNodeValueByXPath("/Envelope/Body/retrieveSummary/request/retrieveTaxExempt", value);
        } catch (XPathNotFoundException e) {
            setRequestNodeValueByXPath("/Envelope/Body/retrieveSummary/request", BaseSoapCommands.ADD_NODE.commandAppend("retrieveTaxExempt"));
            setRequestNodeValueByXPath("/Envelope/Body/retrieveSummary/request/retrieveTaxExempt", value);
        }
    }

    // Getters
    public String getStatus() {
        return getResponseNodeValueByXPath("/Envelope/Body/retrieveSummaryResponse/accommodationsSummaryDetails/accommodationDetail/status");
    }

    public String getRSR() {
        return getResponseNodeValueByXPath("/Envelope/Body/retrieveSummaryResponse/accommodationsSummaryDetails/accommodationDetail/RSR");
    }

    public String getShared() {
        return getResponseNodeValueByXPath("/Envelope/Body/retrieveSummaryResponse/accommodationsSummaryDetails/accommodationDetail/shared");
    }

    public String getADA() {
        return getResponseNodeValueByXPath("/Envelope/Body/retrieveSummaryResponse/accommodationsSummaryDetails/accommodationDetail/specialNeedsRequested");
    }

    public String getNumberofAdults() {
        return getResponseNodeValueByXPath("/Envelope/Body/retrieveSummaryResponse/accommodationsSummaryDetails/accommodationDetail/partyMixInfo/numberOfAdults");
    }

    public String getNumberofChildren() {
        return getResponseNodeValueByXPath("/Envelope/Body/retrieveSummaryResponse/accommodationsSummaryDetails/accommodationDetail/partyMixInfo/numberOfChildren");
    }

    public String getTaxExemptCertificateNumber() {
        return getResponseNodeValueByXPath("/Envelope/Body/retrieveSummaryResponse/accommodationsSummaryDetails/taxExemptDetail/taxExemptCertificateNumber");
    }

    public String getTaxExemptType() {
        return getResponseNodeValueByXPath("/Envelope/Body/retrieveSummaryResponse/accommodationsSummaryDetails/taxExemptDetail/taxExemptType");
    }

    public String getTicketDetails() {
        return getResponseNodeValueByXPath("/Envelope/Body/retrieveSummaryResponse/accommodationsSummaryDetails/accommodationDetail/ticketDetails");
    }

    public String getTicketGroup() {
        try {
            return getResponseNodeValueByXPath("/Envelope/Body/retrieveSummaryResponse/accommodationsSummaryDetails/accommodationDetail/ticketGroup");
        } catch (XPathNotFoundException e) {
            return null;
        }
    }

    int index = getNumberOfRequestNodesByXPath("/Envelope/Body/retrieveSummaryResponse/accommodationsSummaryDetails/accommodationDetail/guestReferences") + 1;

    public String getGuestReferenceDetails(String index) {
        return getResponseNodeValueByXPath("/Envelope/Body/retrieveSummaryResponse/accommodationsSummaryDetails/accommodationDetail/guestReferences[" + index + "]");
    }

    public Integer getGuestReferenceDetails() {
        return getNumberOfResponseNodesByXPath("/Envelope/Body/retrieveSummaryResponse/accommodationsSummaryDetails/accommodationDetail/guestReferences");
    }

    int index1 = getNumberOfRequestNodesByXPath("/Envelope/Body/retrieveSummaryResponse/accommodationsSummaryDetails") + 1;

    public String getAccommodationsSummaryDetails(String index1) {
        return getResponseNodeValueByXPath("/Envelope/Body/retrieveSummaryResponse/accommodationsSummaryDetails[" + index1 + "]");
    }

    public Integer getAccommodationsSummaryDetails() {
        return getNumberOfResponseNodesByXPath("/Envelope/Body/retrieveSummaryResponse/accommodationsSummaryDetails");
    }

    public String getRoomOnlyStatus() {
        return getResponseNodeValueByXPath("/Envelope/Body/retrieveSummaryResponse/accommodationsSummaryDetails/accommodationDetail/roomOnly");
    }

    int index2 = getNumberOfRequestNodesByXPath("/Envelope/Body/retrieveSummaryResponse/accommodationsSummaryDetails/travelComponentGroupingId") + 1;

    public String getTravelComponentGroupingId(String index2) {
        return getResponseNodeValueByXPath("/Envelope/Body/retrieveSummaryResponse/accommodationsSummaryDetails[" + index2 + "]/travelComponentGroupingId");
    }

    int index4 = getNumberOfRequestNodesByXPath("/Envelope/Body/retrieveSummaryResponse/accommodationsSummaryDetails/travelPlanSegmentId") + 1;

    public String getTravelPlanSegmentId(String index4) {
        return getResponseNodeValueByXPath("/Envelope/Body/retrieveSummaryResponse/accommodationsSummaryDetails[" + index4 + "]/travelPlanSegmentId");
    }

    int index5 = getNumberOfRequestNodesByXPath("/Envelope/Body/retrieveSummaryResponse/accommodationsSummaryDetails/accommodationDetail/guestReferences/guest/addressDetails") + 1;

    public String getAddressDetails(String index5) {
        return getResponseNodeValueByXPath("/Envelope/Body/retrieveSummaryResponse/accommodationsSummaryDetails/accommodationDetail/guestReferences/guest/addressDetails[" + index5 + "]");
    }

    public String getPackageCode() {
        return getResponseNodeValueByXPath("/Envelope/Body/retrieveSummaryResponse/accommodationsSummaryDetails/accommodationDetail/packageCode");
    }

    public String getPackageDescription() {
        return getResponseNodeValueByXPath("/Envelope/Body/retrieveSummaryResponse/accommodationsSummaryDetails/accommodationDetail/packageDescription");
    }

    public String getSalesChannelId() {
        return getResponseNodeValueByXPath("/Envelope/Body/retrieveSummaryResponse/accommodationsSummaryDetails/accommodationDetail/salesChannelId");
    }

    public String getBookDate() {
        return getResponseNodeValueByXPath("/Envelope/Body/retrieveSummaryResponse/accommodationsSummaryDetails/accommodationDetail/bookDate");
    }

    public String getStartDate() {
        return getResponseNodeValueByXPath("/Envelope/Body/retrieveSummaryResponse/accommodationsSummaryDetails/accommodationDetail/period/startDate");
    }

    // Here in case you need it
    // public void setguestEnteredInRoomIndicator(String value) {
    // try{setRequestNodeValueByXPath("/Envelope/Body/search/request/guestEnteredInRoomIndicator", value);}
    // catch(XPathNotFoundException e){
    // setRequestNodeValueByXPath("/Envelope/Body/search/request", BaseSoapCommands.ADD_NODE.commandAppend("guestEnteredInRoomIndicator"));
    // setRequestNodeValueByXPath("/Envelope/Body/search/request/guestEnteredInRoomIndicator", value);
    // }
    // }
}
