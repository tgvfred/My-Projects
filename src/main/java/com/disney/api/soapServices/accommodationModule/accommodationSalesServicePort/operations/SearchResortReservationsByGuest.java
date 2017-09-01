package com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations;

import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.AccommodationSalesServicePort;
import com.disney.utils.XMLTools;

public class SearchResortReservationsByGuest extends AccommodationSalesServicePort {

    public SearchResortReservationsByGuest(String environment, String scenario) {
        super(environment);

        // Generate a request from a project xml file
        setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("searchResortReservationsByGuest")));

        generateServiceContext();
        setRequestNodeValueByXPath(getTestScenario(getService(), getOperation(), scenario));

        removeComments();
        removeWhiteSpace();
    }

    public SearchResortReservationsByGuest(String environment) {
        super(environment);
        setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("searchResortReservationsByGuest")));
        removeComments();
        removeWhiteSpace();
        generateServiceContext();
    }

    // setters
    // public void setreservationNum(String reservationNumber) {
    // setRequestNodeValueByXPath("/*[local-name(.)='Envelope'][1]/*[local-name(.)='Body'][1]/*[local-name(.)='searchResortReservationsByGuest'][1]/*[local-name(.)='searchResortReservationsRequest'][1]/*[local-name(.)='reservationNumber'][1]", reservationNumber);
    // }

    public void setAgencyIataNumber(String agencyIataNumber) {

        setRequestNodeValueByXPath("/Envelope/Body/searchResortReservationsByGuest/searchResortReservationsRequest/agencyIataNumber", agencyIataNumber);

    }

    public void setGuestLastName(String guestLastName) {

        setRequestNodeValueByXPath("/Envelope/Body/searchResortReservationsByGuest/searchResortReservationsRequest/guestLastName", guestLastName);

    }

    public void setReservationNumber(String reservationNumber) {

        setRequestNodeValueByXPath("/Envelope/Body/searchResortReservationsByGuest/searchResortReservationsRequest/reservationNumber", reservationNumber);
    }

    public void setPostalCode(String postalCode) {

        setRequestNodeValueByXPath("/Envelope/Body/searchResortReservationsByGuest/searchResortReservationsRequest/postalCode", postalCode);

    }

    public void setGuestFirstName(String guestFirstName) {

        setRequestNodeValueByXPath("/Envelope/Body/searchResortReservationsByGuest/searchResortReservationsRequest/guestFirstName", guestFirstName);

    }

    public void setResortCode(String resortCode) {

        setRequestNodeValueByXPath("/Envelope/Body/searchResortReservationsByGuest/searchResortReservationsRequest/resortCode", resortCode);

    }

    public void setArrivalDate(String arrivalDate) {

        setRequestNodeValueByXPath("/Envelope/Body/searchResortReservationsByGuest/searchResortReservationsRequest/arrivalDate", arrivalDate);

    }

    // getters
    public String getReservationStatus() {

        return getResponseNodeValueByXPath("/Envelope/Body/searchResortReservationsByGuestResponse/resortReservations/reservationStatus");
    }

    public String getPartyRoles() {

        return getResponseNodeValueByXPath("/Envelope/Body/searchResortReservationsByGuestResponse/resortReservations/partyRoles/");
    }

    public String getVipLevel() {

        return getResponseNodeValueByXPath("/Envelope/Body/searchResortReservationsByGuestResponse/resortReservations/vipLevel");

    }

    public String getAgencyIataNumber() {
        return getResponseNodeValueByXPath("/Envelope/Body/searchResortReservationsByGuestResponse/resortReservations/agencyIataNumber");
    }

    public String getGuestLastName() {
        return getResponseNodeValueByXPath("/Envelope/Body/searchResortReservationsByGuestResponse/resortReservations/partyRoles/guest/guestLastName");
    }

    public String getReservationNumber() {
        return getResponseNodeValueByXPath("/Envelope/Body/searchResortReservationsByGuestResponse/resortReservations[1]/travelPlanSegmentId");
    }

    public String getPostalCode() {
        return getResponseNodeValueByXPath("/Envelope/Body/searchResortReservationsByGuestResponse/resortReservations/postalCode");
    }

    public String getGuestFirstName() {
        return getResponseNodeValueByXPath("/Envelope/Body/searchResortReservationsByGuestResponse/resortReservations/partyRoles/guest/guestFirstName");
    }

    public String getResortCode() {
        return getResponseNodeValueByXPath("/Envelope/Body/searchResortReservationsByGuestResponse/resortReservations/resortCode");
    }

    public String getArrivalDate() {
        return getResponseNodeValueByXPath("/Envelope/Body/searchResortReservationsByGuestResponse/resortReservations/arrivalDate").replace('T', ' ').trim();
    }

    public String getResortStartDate() {

        return getResponseNodeValueByXPath("/Envelope/Body/searchResortReservationsByGuestResponse/resortReservations/resortStartDate").replace('T', ' ').substring(0, 10).replace(':', ' ').trim();
    }

    public String getResortEndDate() {

        return getResponseNodeValueByXPath("/Envelope/Body/searchResortReservationsByGuestResponse/resortReservations/resortEndDate").replace('T', ' ').substring(0, 10).replace(':', ' ').trim();
    }

}
