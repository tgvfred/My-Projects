package com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations;

import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.AccommodationSalesServicePort;
import com.disney.utils.XMLTools;

public class BookWithPay extends AccommodationSalesServicePort{
	public BookWithPay(String environment, String scenario) {
		super(environment);

		//Generate a request from a project xml file
		setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("bookWithPay")));
	    setRequestNodeValueByXPath(getTestScenario(getService(), getOperation(), scenario));
		generateServiceContext();
		removeComments() ;
		removeWhiteSpace();

	}
	
	public void setInventoryTrackingId(String value){
		setRequestNodeValueByXPath("/Envelope/Body/bookWithPay/paymentRequest/inventoryTrackingId", value);
		setRequestNodeValueByXPath("/Envelope/Body/bookWithPay/reservationRequest/roomDetail/InventoryTrackingId", value);
		setRequestNodeValueByXPath("/Envelope/Body/bookWithPay/paymentRequest/basicOrderDetails/reservationReferenceNo", value);		
	}
	

	public void setMemberRefNumber(String value){
		setRequestNodeValueByXPath("/Envelope/Body/bookWithPay/reservationRequest/externalReference/externalReferenceNumber", value);
		setRequestNodeValueByXPath("/Envelope/Body/bookWithPay/reservationRequest/travelPlanGuest/membershipDetail/membershipId", value);			
	}

	public void setMemberNumber(String value){
		setRequestNodeValueByXPath("/Envelope/Body/bookWithPay/paymentRequest/membershipId", value);
		setRequestNodeValueByXPath("/Envelope/Body/bookWithPay/reservationRequest/roomDetail/roomReservationDetail/guestReferenceDetails/guest/membershipDetail/membershipId", value);	
		
	}

	public void setMemberMembershipID(String value){
		setRequestNodeValueByXPath("/Envelope/Body/bookWithPay/reservationRequest/roomDetail/externalReferences/externalReferenceNumber", value);	
		
	}

	public void setContractID(String value){
		setRequestNodeValueByXPath("/Envelope/Body/bookWithPay/paymentRequest/pointsPayments/payingContractSummary/payingContracts/contractId", value);			
	}
	public void setArrivalDate(String arrivalDate){
		setRequestNodeValueByXPath("//bookWithPay/reservationRequest/roomDetail/resortPeriod/startDate", arrivalDate);
	}	

	public void setArrivalDateDaysOut(String arrivalDaysOut){
			setRequestNodeValueByXPath("//bookWithPay/reservationRequest/roomDetail/resortPeriod/startDate", "fx:GetDate; Daysout:" + arrivalDaysOut);
	}	

	public void setDepartureDate(String deptDate){
			setRequestNodeValueByXPath("//book/request/roomDetail/resortPeriod/endDate", deptDate);
	}
	
	public void setDepartureDateDaysOut(String deptDateDaysOut){
			setRequestNodeValueByXPath("//book/request/roomDetail/resortPeriod/endDate", "fx:GetDate; Daysout:" + deptDateDaysOut);
	}
	
	public void setPackageCode(String packageCode){
		setRequestNodeValueByXPath("//bookWithPay/reservationRequest/roomDetail/packageCode", packageCode);
	}
	
	public void setResortCode(String resortCode){
		setRequestNodeValueByXPath("//bookWithPay/reservationRequest/roomDetail/resortCode", resortCode);
	}
	
	public void setRoomTypeCode(String roomTypeCode){
		setRequestNodeValueByXPath("//bookWithPay/reservationRequest/roomDetail/roomTypeCode", roomTypeCode);
	}

	public void setLocationID(String locationID){
		setRequestNodeValueByXPath("//bookWithPay/reservationRequest/locationId", locationID);
	}
	
	public void setReservationType(String reservationType){
		setRequestNodeValueByXPath("//bookWithPay/reservationRequest/roomDetail/reservationType", reservationType);
	}
	
	public void setSalesChannel(String salesChannel){
		setRequestNodeValueByXPath("//bookWithPay/reservationRequest/salesChannel", salesChannel);
	}
	
	public void setMembershipID(String membershipID){
		setRequestNodeValueByXPath("//bookWithPay/reservationRequest/travelPlanGuest/membershipDetail/membershipId", membershipID);
	}
	public void setPrimaryGuestFirstName(String name){
		setRequestNodeValueByXPath("//guest/firstName", name);
		setRequestNodeValueByXPath("//travelPlanGuest/firstName", name);
	}
	
	public void setPrimaryGuestLastName(String name){
		setRequestNodeValueByXPath("//guest/lastName", name);
		setRequestNodeValueByXPath("//travelPlanGuest/lastName", name);
	}
	
	public void setPhoneNumber(String phone){
		setRequestNodeValueByXPath("//bookWithPay/reservationRequest/roomDetail/roomReservationDetail/guestReferenceDetails/guest/phoneDetails/number", phone);
	}
		
	public void setEmail(String email){
		setRequestNodeValueByXPath("//bookWithPay/reservationRequest/roomDetail/roomReservationDetail/guestReferenceDetails/guest/emailDetails/address", email);
	}
	
	public void setGuestid(String value){
		setRequestNodeValueByXPath("/Envelope/Body/bookWithPay/reservationRequest/travelPlanGuest/guestId", value);
		setRequestNodeValueByXPath("/Envelope/Body/bookWithPay/reservationRequest/roomDetail/roomReservationDetail/guestReferenceDetails/guest/guestId", value);
	}
	
	public void setPartyId(String value){
		setRequestNodeValueByXPath("/Envelope/Body/bookWithPay/reservationRequest/roomDetail/roomReservationDetail/guestReferenceDetails/guest/partyId", value);
		setRequestNodeValueByXPath("/Envelope/Body/bookWithPay/reservationRequest/travelPlanGuest/partyId", value);
	}
	
	public String getTravelPlanId(){
		return getResponseNodeValueByXPath("/Envelope/Body/bookWithPayResponse/bookWithPayResponse/travelPlanId");
	}

	public String getTravelPlanSegmentId(){
		return getResponseNodeValueByXPath("/Envelope/Body/bookWithPayResponse/bookWithPayResponse/travelPlanSegmentId");
	}
	
	public String getTravelComponentGroupingId(){
		return getResponseNodeValueByXPath("/Envelope/Body/bookWithPayResponse/bookWithPayResponse/roomDetails/travelComponentGroupingId");
	}
	
	public String getTravelComponentId(){
		return getResponseNodeValueByXPath("/Envelope/Body/bookWithPayResponse/bookWithPayResponse/roomDetails/travelComponentId");
	}
	
	public String getGuestId(){
		return getResponseNodeValueByXPath("/Envelope/Body/bookWithPayResponse/bookWithPayResponse/roomDetails[1]/roomReservationDetail/guestReferenceDetails/guest/partyId");
	}
	
}
