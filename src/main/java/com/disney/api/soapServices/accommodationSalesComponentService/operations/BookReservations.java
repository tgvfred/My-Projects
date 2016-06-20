package com.disney.api.soapServices.accommodationSalesComponentService.operations;

import com.disney.api.soapServices.accommodationSalesComponentService.AccommodationSalesComponentService;
import com.disney.utils.XMLTools;

public class BookReservations extends AccommodationSalesComponentService{
	public BookReservations(String environment, String scenario) {
		super(environment);
		
		setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("bookReservations")));
		generateServiceContext();
		setRequestNodeValueByXPath(getTestScenario(getService(), getOperation(), scenario));
		
		removeComments() ;
		removeWhiteSpace();
	}
	
	public void setAreaPeriodEndDate(String value){ 
		setRequestNodeValueByXPath("/Envelope/Body/bookReservations/request/roomReservationRequest/areaPeriod/endDate", value);
	}
	
	public void setAreaPeriodStartDate(String value){
		setRequestNodeValueByXPath("/Envelope/Body/bookReservations/request/roomReservationRequest/areaPeriod/startDate", value);
	}
	
	public void setCommunicationsChannel(String value){
		setRequestNodeValueByXPath("/Envelope/Body/bookReservations/request/roomReservationRequest/communicationChannel", value);
	}
	
	public void setExternalReferenceNumber(String value){
		setRequestNodeValueByXPath("/Envelope/Body/bookReservations/request/roomReservationRequest/externalReference/externalReferenceNumber", value);
	}
	
	public void setExternalReferenceSource(String value){
		setRequestNodeValueByXPath("/Envelope/Body/bookReservations/request/roomReservationRequest/externalReference/externalReferenceSource", value);
	}
	
	public void setBlockCode(String value){
		switch (value) {
		case "1905":
			value = "01905";
			break;
		case "1825":
			value = "01825";
			break;
		case "4250":
			value = "04250";
			break;
		case "4268":
			value = "04268";
			break;
		default:
			break;
		}
		
		setRequestNodeValueByXPath("/Envelope/Body/bookReservations/request/roomReservationRequest/roomDetails/blockCode", value);
	}
	
	public void setBookingDate(String value){
		setRequestNodeValueByXPath("/Envelope/Body/bookReservations/request/roomReservationRequest/roomDetails/bookingDate", value);
	}
	
	public void setFreezeId(String value){
		setRequestNodeValueByXPath("/Envelope/Body/bookReservations/request/roomReservationRequest/roomDetails/freezeId", value);
	}
	
	public void setGuarenteeStatus(String value){
		switch (value) {
		case "1905":
			value = "01905";
			break;
		case "1825":
			value = "01825";
			break;
		case "4250":
			value = "04250";
			break;
		case "4268":
			value = "04268";
			break;
		default:
			break;
		}
		
		setRequestNodeValueByXPath("/Envelope/Body/bookReservations/request/roomReservationRequest/roomDetails/guaranteeStatus", value);
	}
	
	public void setPackageCode(String value){
		setRequestNodeValueByXPath("/Envelope/Body/bookReservations/request/roomReservationRequest/roomDetails/packageCode", value);
	}
	
	public void setResortCode(String value){
		setRequestNodeValueByXPath("/Envelope/Body/bookReservations/request/roomReservationRequest/roomDetails/resortCode", value);
	}
	
	public void setResortAreaEndDate(String value){
		setRequestNodeValueByXPath("/Envelope/Body/bookReservations/request/roomReservationRequest/roomDetails/resortPeriod/endDate", value);
	}
	
	public void setResortAreaStartDate(String value){
		setRequestNodeValueByXPath("/Envelope/Body/bookReservations/request/roomReservationRequest/roomDetails/resortPeriod/startDate", value);
	}
	
	public void setRoomTypeCode(String value){
		setRequestNodeValueByXPath("/Envelope/Body/bookReservations/request/roomReservationRequest/roomDetails/roomTypeCode", value);
	}
	
	public void setSalesChannel(String value){
		setRequestNodeValueByXPath("/Envelope/Body/bookReservations/request/roomReservationRequest/salesChannel", value);
	}
	
	public void setTravelPlanGuestFirstName(String value){
		setRequestNodeValueByXPath("/Envelope/Body/bookReservations/request/roomReservationRequest/travelPlanGuest/firstName", value);
	}
	
	public void setTravelPlanGuestLastName(String value){
		setRequestNodeValueByXPath("/Envelope/Body/bookReservations/request/roomReservationRequest/travelPlanGuest/lastName", value);
	}
	
	public void setTravelPlanGuestAddress1(String value){
		setRequestNodeValueByXPath("/Envelope/Body/bookReservations/request/roomReservationRequest/travelPlanGuest/addressDetails/addressLine1", value);
	}
	
	public void setTravelPlanGuestCity(String value){
		setRequestNodeValueByXPath("/Envelope/Body/bookReservations/request/roomReservationRequest/travelPlanGuest/addressDetails/city", value);
	}
	
	public void setTravelPlanGuestCountry(String value){
		setRequestNodeValueByXPath("/Envelope/Body/bookReservations/request/roomReservationRequest/travelPlanGuest/addressDetails/country", value);
	}
	
	public void setTravelPlanGuestZip(String value){
		setRequestNodeValueByXPath("/Envelope/Body/bookReservations/request/roomReservationRequest/travelPlanGuest/addressDetails/postalCode", value);
	}
	
	public void setTravelPlanGuestState(String value){
		setRequestNodeValueByXPath("/Envelope/Body/bookReservations/request/roomReservationRequest/travelPlanGuest/addressDetails/state", value);
	}
	
	public void setGuestReservationDetailsGuestFirstName(String value, String index){
		setRequestNodeValueByXPath("/Envelope/Body/bookReservations/request/roomReservationRequest/roomDetails/roomReservationDetail/guestReferenceDetails["+index+"]/guest/firstName", value);
	}
	
	public void setGuestReservationDetailsGuestLastName(String value, String index){
		setRequestNodeValueByXPath("/Envelope/Body/bookReservations/request/roomReservationRequest/roomDetails/roomReservationDetail/guestReferenceDetails["+index+"]/guest/lastName", value);
	}
	
	public void setGuestReservationDetailsGuestAddress1(String value, String index){
		setRequestNodeValueByXPath("/Envelope/Body/bookReservations/request/roomReservationRequest/roomDetails/roomReservationDetail/guestReferenceDetails["+index+"]/guest/addressDetails/addressLine1", value);
	}
	
	public void setGuestReservationDetailsGuestCity(String value, String index){
		setRequestNodeValueByXPath("/Envelope/Body/bookReservations/request/roomReservationRequest/roomDetails/roomReservationDetail/guestReferenceDetails["+index+"]/guest/addressDetails/city", value);
	}
	
	public void setGuestReservationDetailsGuestCountry(String value, String index){
		setRequestNodeValueByXPath("/Envelope/Body/bookReservations/request/roomReservationRequest/roomDetails/roomReservationDetail/guestReferenceDetails["+index+"]/guest/addressDetails/country", value);
	}
	
	public void setGuestReservationDetailsGuestPostalCode(String value, String index){
		setRequestNodeValueByXPath("/Envelope/Body/bookReservations/request/roomReservationRequest/roomDetails/roomReservationDetail/guestReferenceDetails["+index+"]/guest/addressDetails/postalCode", value);
	}
	
	public void setGuestReservationDetailsGuestState(String value, String index){
		setRequestNodeValueByXPath("/Envelope/Body/bookReservations/request/roomReservationRequest/roomDetails/roomReservationDetail/guestReferenceDetails["+index+"]/guest/addressDetails/state", value);
	}
	
	public String getTravelComponentGroupingId(){
		return getResponseNodeValueByXPath("/Envelope/Body/bookReservationsResponse/return/roomDetails/travelComponentGroupingId");
	}
	
	public String getTravelComponentId(){
		return getResponseNodeValueByXPath("/Envelope/Body/bookReservationsResponse/return/roomDetails/travelComponentId");
	}
	
	public String getTravelPlanId(){
		return getResponseNodeValueByXPath("/Envelope/Body/bookReservationsResponse/return/travelPlanId");
	}
	
	public String getTravelPlanSegmentId(){
		return getResponseNodeValueByXPath("/Envelope/Body/bookReservationsResponse/return/travelPlanSegmentId");
	}
	
	public void setProfileCode(String value, String index){
		setRequestNodeValueByXPath("/Envelope/Body/bookReservations/request/roomReservationRequest/roomDetails/roomReservationDetail/profiles["+index+"]/code", value);
	}
	public void setProfileCode(String value, int index){setProfileCode(value, String.valueOf(index));}
	
	public void setProfileDescription(String value, String index){
		setRequestNodeValueByXPath("/Envelope/Body/bookReservations/request/roomReservationRequest/roomDetails/roomReservationDetail/profiles["+index+"]/description", value);
	}
	public void setProfileDescription(String value, int index){setProfileDescription(value, String.valueOf(index));}
	
	public void setProfileType(String value, String index){
		setRequestNodeValueByXPath("/Envelope/Body/bookReservations/request/roomReservationRequest/roomDetails/roomReservationDetail/profiles["+index+"]/profileType", value);
	}
	public void setProfileType(String value, int index){setProfileType(value, String.valueOf(index));}
	
	public void setProfileRountingsName(String value, String index){
		setRequestNodeValueByXPath("/Envelope/Body/bookReservations/request/roomReservationRequest/roomDetails/roomReservationDetail/profiles["+index+"]/routings/name", value);
	}
	public void setProfileRountingsName(String value, int index){setProfileRountingsName(value, String.valueOf(index));}
	
	
	
	
	
	
	public String getProfileCode(String index){
		return getResponseNodeValueByXPath("/Envelope/Body/bookReservationsResponse/return/roomDetails/roomReservationDetail/profiles["+index+"]/code");
	}
	public String getProfileCode(int index){return getProfileCode(String.valueOf(index));}
	
	public String getProfileDescription(String index){
		return getResponseNodeValueByXPath("/Envelope/Body/bookReservationsResponse/return/roomDetails/roomReservationDetail/profiles["+index+"]/description");
	}
	public String getProfileDescription(int index){return getProfileDescription(String.valueOf(index));}
	
	public String getProfileType(String index){
		return getResponseNodeValueByXPath("/Envelope/Body/bookReservationsResponse/return/roomDetails/roomReservationDetail/profiles["+index+"]/profileType");
	}
	public String getProfileType(int index){return getProfileType(String.valueOf(index));}
	
	public String getProfileRountingsName(String index){
		return getResponseNodeValueByXPath("/Envelope/Body/bookReservationsResponse/return/roomDetails/roomReservationDetail/profiles["+index+"]/routings/name");
	}
	public String getProfileRountingsName(int index){return getProfileRountingsName(String.valueOf(index));}
}