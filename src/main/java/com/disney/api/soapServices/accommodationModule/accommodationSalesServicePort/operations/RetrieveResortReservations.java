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

    public void setReservationNumber(String value) {
        setRequestNodeValueByXPath("/Envelope/Body/retrieveResortReservations/retrieveResortReservationsRequest/reservationNumber", value);
    }

    public int getNumberOfPartyRoles() {
        return getNumberOfResponseNodesByXPath("/Envelope/Body/retrieveResortReservationsResponse/resortReservations/partyRoles");
    }

    public String getTravelPlanSegmentId() {
        return getResponseNodeValueByXPath("/Envelope/Body/retrieveResortReservationsResponse/resortReservations/travelPlanSegmentId");
    }
}