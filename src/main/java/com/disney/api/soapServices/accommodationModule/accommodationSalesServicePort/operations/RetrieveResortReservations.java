package com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

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

    public String getPackageCode() {
        return getResponseNodeValueByXPath("/Envelope/Body/retrieveResortReservationsResponse/resortReservations/packageCode");
    }

    public String getPackageProductId() {
        return getResponseNodeValueByXPath("/Envelope/Body/retrieveResortReservationsResponse/resortReservations/packageProductId");
    }

    public String getPackagePlanType() {
        return getResponseNodeValueByXPath("/Envelope/Body/retrieveResortReservationsResponse/resortReservations/packagePlanType");
    }

    public int getNumberOfPartyRoles() {
        return getNumberOfResponseNodesByXPath("/Envelope/Body/retrieveResortReservationsResponse/resortReservations[1]/partyRoles");
    }

    public Map<Integer, String> getPartyRoleGuests() {
        Map<Integer, String> toreturn = new HashMap<>();
        for (int i = getNumberOfPartyRoles(); i > 0; i--) {
            String fname = getResponseNodeValueByXPath("/Envelope/Body/retrieveResortReservationsResponse/resortReservations/partyRoles[" + i + "]/guest/firstName");
            String lname = getResponseNodeValueByXPath("/Envelope/Body/retrieveResortReservationsResponse/resortReservations/partyRoles[" + i + "]/guest/lastName");
            String ID = getResponseNodeValueByXPath("/Envelope/Body/retrieveResortReservationsResponse/resortReservations/partyRoles[" + i + "]/guest/guestId");
            toreturn.put(Integer.parseInt(ID.trim()), (fname.trim() + " " + lname.trim()).toUpperCase());
        }

        return toreturn;
    }

    public Entry<Integer, String> getPrimaryGuest() {
        String fname = getResponseNodeValueByXPath("/Envelope/Body/retrieveResortReservationsResponse/resortReservations/primaryGuest/firstName");
        String lname = getResponseNodeValueByXPath("/Envelope/Body/retrieveResortReservationsResponse/resortReservations/primaryGuest/lastName");
        String ID = getResponseNodeValueByXPath("/Envelope/Body/retrieveResortReservationsResponse/resortReservations/primaryGuest/guestId");
        return new AbstractMap.SimpleEntry<Integer, String>(Integer.parseInt(ID.trim()), (fname.trim() + " " + lname.trim()).toUpperCase());
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

    public String getRoomTypeCode() {
        return getResponseNodeValueByXPath("/Envelope/Body/retrieveResortReservationsResponse/resortReservations/roomTypeCode");
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