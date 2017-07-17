package com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations;

import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.AccommodationSalesServicePort;
import com.disney.utils.XMLTools;
import com.disney.utils.exceptions.NoDataFromVirtualTableException;

public class RetrieveResortReservations extends AccommodationSalesServicePort {
    public RetrieveResortReservations(String environment) {
        super(environment);
        setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("retrieveResortReservations")));
        removeComments();
        removeWhiteSpace();
        generateServiceContext();
    }

    public RetrieveResortReservations(String environment, String scenario) {
        this(environment);
        try {
            setRequestNodeValueByXPath(getTestScenario(getService(), getOperation(), scenario));
        } catch (NoDataFromVirtualTableException e) {
            setEnvironmentServiceURL("AccommodationSalesServicePort", environment);
            setRequestNodeValueByXPath(getTestScenario(getService(), getOperation(), scenario));
        }
    }

    /*
     * Request Setters
     */
    public void setReservationNumber(String value) {
        setRequestNodeValueByXPath("/Envelope/Body/retrieveResortReservations/retrieveResortReservationsRequest/reservationNumber", value);
    }

    /*
     * Response Getters
     */
    public int getNumberOfResortReservations() {
        return getNumberOfResponseNodesByXPath("/Envelope/Body/retrieveResortReservationsResponse/resortReservations");
    }

    public String getPackagePlanType() {
        return getResponseNodeValueByXPath("/Envelope/Body/retrieveResortReservationsResponse/resortReservations/packagePlanType");
    }

    public int getNumberOfPartyRoles() {
        return getNumberOfResponseNodesByXPath("/Envelope/Body/retrieveResortReservationsResponse/resortReservations/partyRoles");
    }

    public String getReservationStatus() {
        return getResponseNodeValueByXPath("/Envelope/Body/retrieveResortReservationsResponse/resortReservations/reservationStatus");
    }

    public String getResortEndDate() {
        return getResponseNodeValueByXPath("/Envelope/Body/retrieveResortReservationsResponse/resortReservations/resortEndDate").replace('T', ' ').trim();
    }

    public String getResortStartDate() {
        return getResponseNodeValueByXPath("/Envelope/Body/retrieveResortReservationsResponse/resortReservations/resortStartDate").replace('T', ' ').trim();
    }

    public String getTravelComponentGroupingId() {
        return getResponseNodeValueByXPath("/Envelope/Body/retrieveResortReservationsResponse/resortReservations/travelComponentGroupingID");
    }

    public String getTravelPlanId() {
        return getResponseNodeValueByXPath("/Envelope/Body/retrieveResortReservationsResponse/resortReservations/travelPlanId");
    }

    public String getTravelPlanSegmentId() {
        return getResponseNodeValueByXPath("/Envelope/Body/retrieveResortReservationsResponse/resortReservations/travelPlanSegmentId");
    }

    public String getVipLevel() {
        return getResponseNodeValueByXPath("/Envelope/Body/retrieveResortReservationsResponse/resortReservations/vipLevel");
    }
}