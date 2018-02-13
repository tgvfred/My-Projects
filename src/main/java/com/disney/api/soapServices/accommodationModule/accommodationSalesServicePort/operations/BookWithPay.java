package com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations;

import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.AccommodationSalesServicePort;
import com.disney.api.soapServices.core.BaseSoapCommands;
import com.disney.api.soapServices.core.exceptions.XPathNotFoundException;
import com.disney.utils.XMLTools;

/**
 * @deprecated deprecated in 8.5. Now in DVCSales
 * @author phlej001
 *
 */
@Deprecated
public class BookWithPay extends AccommodationSalesServicePort {
    public BookWithPay(String environment, String scenario) {
        super(environment);

        // Generate a request from a project xml file
        setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("bookWithPay")));
        setRequestNodeValueByXPath(getTestScenario(getService(), getOperation(), scenario));
        generateServiceContext();
        removeComments();
        removeWhiteSpace();

    }

    public void setInventoryTrackingId(String value) {
        setRequestNodeValueByXPath("/Envelope/Body/bookWithPay/paymentRequest/inventoryTrackingId", value);
        setRequestNodeValueByXPath("/Envelope/Body/bookWithPay/reservationRequest/roomDetail/InventoryTrackingId", value);
        setRequestNodeValueByXPath("/Envelope/Body/bookWithPay/paymentRequest/basicOrderDetails/reservationReferenceNo", value);
    }

    public void setMemberRefNumber(String value) {
        setRequestNodeValueByXPath("/Envelope/Body/bookWithPay/reservationRequest/externalReference/externalReferenceNumber", value);
        setRequestNodeValueByXPath("/Envelope/Body/bookWithPay/reservationRequest/travelPlanGuest/membershipDetail/membershipId", value);
    }

    public void setMemberNumber(String value) {
        setRequestNodeValueByXPath("/Envelope/Body/bookWithPay/paymentRequest/membershipId", value);
        setRequestNodeValueByXPath("/Envelope/Body/bookWithPay/reservationRequest/roomDetail/roomReservationDetail/guestReferenceDetails/guest/membershipDetail/membershipId", value);

    }

    public void setMemberMembershipID(String value) {
        setRequestNodeValueByXPath("/Envelope/Body/bookWithPay/reservationRequest/roomDetail/externalReferences/externalReferenceNumber", value);

    }

    public void setContractID(String value) {
        setRequestNodeValueByXPath("/Envelope/Body/bookWithPay/paymentRequest/pointsPayments/payingContractSummary/payingContracts/contractId", value);
    }

    public void setArrivalDate(String arrivalDate) {
        setRequestNodeValueByXPath("//bookWithPay/reservationRequest/roomDetail/resortPeriod/startDate", arrivalDate);
    }

    public void setArrivalDateDaysOut(String arrivalDaysOut) {
        setRequestNodeValueByXPath("//bookWithPay/reservationRequest/roomDetail/resortPeriod/startDate", "fx:GetDate; Daysout:" + arrivalDaysOut);
    }

    public void setDepartureDate(String deptDate) {
        setRequestNodeValueByXPath("//bookWithPay/reservationRequest/roomDetail/resortPeriod/endDate", deptDate);
    }

    public void setDepartureDateDaysOut(String deptDateDaysOut) {
        setRequestNodeValueByXPath("//bookWithPay/reservationRequest/roomDetail/resortPeriod/endDate", "fx:GetDate; Daysout:" + deptDateDaysOut);
    }

    public void setRoomPackageCode(String packageCode) {
        setRequestNodeValueByXPath("//bookWithPay/reservationRequest/roomDetail/packageCode", packageCode);
    }

    public void setComponentPackageCode(String packageCode) {
        try {
            setRequestNodeValueByXPath("//bookWithPay/reservationRequest/roomDetail/componentDetail/packageCode", packageCode);
        } catch (XPathNotFoundException e) {
            setRequestNodeValueByXPath("//bookWithPay/reservationRequest/roomDetail", BaseSoapCommands.ADD_NODE.commandAppend("componentDetail"));
            setRequestNodeValueByXPath("//bookWithPay/reservationRequest/roomDetail/componentDetail", BaseSoapCommands.ADD_NODE.commandAppend("packageCode"));
            setRequestNodeValueByXPath("//bookWithPay/reservationRequest/roomDetail/componentDetail/packageCode", packageCode);
        }
    }

    public void setResortCode(String resortCode) {
        setRequestNodeValueByXPath("//bookWithPay/reservationRequest/roomDetail/resortCode", resortCode);
    }

    public void setRoomTypeCode(String roomTypeCode) {
        setRequestNodeValueByXPath("//bookWithPay/reservationRequest/roomDetail/roomTypeCode", roomTypeCode);
    }

    public void setLocationID(String locationID) {
        setRequestNodeValueByXPath("//bookWithPay/reservationRequest/locationId", locationID);
    }

    public void setReservationType(String reservationType) {
        setRequestNodeValueByXPath("//bookWithPay/reservationRequest/roomDetail/reservationType", reservationType);
    }

    public void setSalesChannel(String salesChannel) {
        setRequestNodeValueByXPath("//bookWithPay/reservationRequest/salesChannel", salesChannel);
    }

    public void setMembershipID(String membershipID) {
        setRequestNodeValueByXPath("//bookWithPay/reservationRequest/travelPlanGuest/membershipDetail/membershipId", membershipID);
    }

    public void setPrimaryGuestFirstName(String name) {
        setRequestNodeValueByXPath("//guest/firstName", name);
        setRequestNodeValueByXPath("//travelPlanGuest/firstName", name);
    }

    public void setPrimaryGuestLastName(String name) {
        setRequestNodeValueByXPath("//guest/lastName", name);
        setRequestNodeValueByXPath("//travelPlanGuest/lastName", name);
    }

    public void setAddress1(String value) {
        setRequestNodeValueByXPath("/Envelope/Body/bookWithPay/reservationRequest/travelPlanGuest/addressDetails/addressLine1", value);
        setRequestNodeValueByXPath("//bookWithPay/reservationRequest/roomDetail/roomReservationDetail/guestReferenceDetails/guest/addressDetails/addressLine1", value);
    }

    public void setCity(String value) {
        setRequestNodeValueByXPath("/Envelope/Body/bookWithPay/reservationRequest/travelPlanGuest/addressDetails/city", value);
        setRequestNodeValueByXPath("//bookWithPay/reservationRequest/roomDetail/roomReservationDetail/guestReferenceDetails/guest/addressDetails/city", value);
    }

    public void setPostalCode(String value) {
        setRequestNodeValueByXPath("/Envelope/Body/bookWithPay/reservationRequest/travelPlanGuest/addressDetails/postalCode", value);
        setRequestNodeValueByXPath("//bookWithPay/reservationRequest/roomDetail/roomReservationDetail/guestReferenceDetails/guest/addressDetails/postalCode", value);
    }

    public void setState(String value) {
        setRequestNodeValueByXPath("/Envelope/Body/bookWithPay/reservationRequest/travelPlanGuest/addressDetails/state", value);
        setRequestNodeValueByXPath("//bookWithPay/reservationRequest/roomDetail/roomReservationDetail/guestReferenceDetails/guest/addressDetails/state", value);
    }

    public void setPhoneNumber(String phone) {
        setRequestNodeValueByXPath("/Envelope/Body/bookWithPay/reservationRequest/travelPlanGuest/phoneDetails/number", phone);
        setRequestNodeValueByXPath("//bookWithPay/reservationRequest/roomDetail/roomReservationDetail/guestReferenceDetails/guest/phoneDetails/number", phone);
    }

    public void setEmail(String email) {
        setRequestNodeValueByXPath("/Envelope/Body/bookWithPay/reservationRequest/travelPlanGuest/emailDetails/address", email);
        setRequestNodeValueByXPath("//bookWithPay/reservationRequest/roomDetail/roomReservationDetail/guestReferenceDetails/guest/emailDetails/address", email);
    }

    public void setGuestid(String value) {
        setRequestNodeValueByXPath("/Envelope/Body/bookWithPay/reservationRequest/travelPlanGuest/guestId", value);
        setRequestNodeValueByXPath("/Envelope/Body/bookWithPay/reservationRequest/roomDetail/roomReservationDetail/guestReferenceDetails/guest/guestId", value);
    }

    public void setPartyId(String value) {
        setRequestNodeValueByXPath("/Envelope/Body/bookWithPay/reservationRequest/roomDetail/roomReservationDetail/guestReferenceDetails/guest/partyId", value);
        setRequestNodeValueByXPath("/Envelope/Body/bookWithPay/reservationRequest/travelPlanGuest/partyId", value);
    }

    public String getTravelPlanId() {
        return getResponseNodeValueByXPath("/Envelope/Body/bookWithPayResponse/bookWithPayResponse/travelPlanId");
    }

    public String getTravelPlanSegmentId() {
        return getResponseNodeValueByXPath("/Envelope/Body/bookWithPayResponse/bookWithPayResponse/travelPlanSegmentId");
    }

    public String getTravelComponentGroupingId() {
        return getResponseNodeValueByXPath("/Envelope/Body/bookWithPayResponse/bookWithPayResponse/roomDetails/travelComponentGroupingId");
    }

    public String getTravelComponentId() {
        return getResponseNodeValueByXPath("/Envelope/Body/bookWithPayResponse/bookWithPayResponse/roomDetails/travelComponentId");
    }

    public String getGuestId() {
        return getResponseNodeValueByXPath("/Envelope/Body/bookWithPayResponse/bookWithPayResponse/roomDetails[1]/roomReservationDetail/guestReferenceDetails/guest/partyId");
    }

}
