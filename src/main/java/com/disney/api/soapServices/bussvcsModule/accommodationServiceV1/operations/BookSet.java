package com.disney.api.soapServices.bussvcsModule.accommodationServiceV1.operations;

import com.disney.api.soapServices.bussvcsModule.accommodationServiceV1.AccommodationServiceV1;
import com.disney.utils.XMLTools;

public class BookSet extends AccommodationServiceV1{
	
	public BookSet(String environment, String scenario) {
		super(environment);
		
		//Generate a request from a project xml file
		setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("bookSet")));
		setRequestNodeValueByXPath(getTestScenario(getService(), getOperation(), scenario));
		removeComments() ;
		removeWhiteSpace();		 
	}
	
	public String getGlobalGuestId(){
		return getResponseNodeValueByXPath("/Envelope/Body/bookSetResponseWrapper/bookAccommodationResponseSet/bookAccommodationResponseDetail/personResponse/@globalGuestId");
	}
	
	public String getExternalPersonId(){
		return getResponseNodeValueByXPath("/Envelope/Body/bookSetResponseWrapper/bookAccommodationResponseSet/bookAccommodationResponseDetail/personResponse/externalPersonId/@id");
	}

	public String getAccommodationComponentReference(){
		return getResponseNodeValueByXPath("/Envelope/Body/bookSetResponseWrapper/bookAccommodationResponseSet/bookAccommodationResponseDetail/accommodationComponentReference/@id");
	}

	public String getTravelPlanSegmentId(){
		return getResponseNodeValueByXPath("/Envelope/Body/bookSetResponseWrapper/bookAccommodationResponseSet/travelPlanSegmentResponse/@travelPlanSegmentId");
	}
	
	public String getTravelComponentGroupAccommodationComponentReference(){
		return getResponseNodeValueByXPath("/Envelope/Body/bookSetResponseWrapper/bookAccommodationResponseSet/travelPlanSegmentResponse/travelComponentGroup/accommodationComponentReference/@id");
	}
	
	public String getTravelComponentGroupId(){
		return getResponseNodeValueByXPath("/Envelope/Body/bookSetResponseWrapper/bookAccommodationResponseSet/travelPlanSegmentResponse/travelComponentGroup/@travelComponentGroupId");
	}
	
	public void setResortCode(String value){
		setRequestNodeValueByXPath("/Envelope/Body/bookSet/bookAccommodationRequestSet/bookAccommodationRequest/@resortCode", value);
	}

	public void setRoomType(String value){
		setRequestNodeValueByXPath("/Envelope/Body/bookSet/bookAccommodationRequestSet/bookAccommodationRequest/@roomType", value);
	}

	public void setArrivalDate(String value){
		setRequestNodeValueByXPath("/Envelope/Body/bookSet/bookAccommodationRequestSet/bookAccommodationRequest/@arrivalDate", value);
		setRequestNodeValueByXPath("/Envelope/Body/bookSet/travelPlanRequest/@areaArrivalDate", value);
	}

	public void setDepartureDate(String value){
		setRequestNodeValueByXPath("/Envelope/Body/bookSet/bookAccommodationRequestSet/bookAccommodationRequest/@departureDate", value);
		setRequestNodeValueByXPath("/Envelope/Body/bookSet/travelPlanRequest/@areaDepartureDate", value);
	}
	
	public void setPackageCode(String value){
		setRequestNodeValueByXPath("/Envelope/Body/bookSet/packageCode", value);
	}

	public void setPrimaryGuestAge(String value){
		setRequestNodeValueByXPath("/Envelope/Body/bookSet/personSet/person/@atLeastThisAge", value);
	}

	public void setPrimaryGuestFirstName(String value){
		setRequestNodeValueByXPath("/Envelope/Body/bookSet/personSet/person/personName/@firstName", value);
	}

	public void setPrimaryGuestLastName(String value){
		setRequestNodeValueByXPath("/Envelope/Body/bookSet/personSet/person/personName/@lastName", value);
	}

	public void setPrimaryGuestAddress(String value){
		setRequestNodeValueByXPath("/Envelope/Body/bookSet/personSet/person/postalAddress/addressLine", value);
	}

	public void setPrimaryGuestCity(String value){
		setRequestNodeValueByXPath("/Envelope/Body/bookSet/personSet/person/postalAddress/@city", value);
	}

	public void setPrimaryGuestState(String value){
		setRequestNodeValueByXPath("/Envelope/Body/bookSet/personSet/person/postalAddress/@state", value);
	}

	public void setPrimaryGuestZipCode(String value){
		setRequestNodeValueByXPath("/Envelope/Body/bookSet/personSet/person/postalAddress/@zipCode", value);
	}

	public void setPrimaryGuestPhoneNumber(String value){
		setRequestNodeValueByXPath("/Envelope/Body/bookSet/personSet/person/telecomAddress/@number", value);
	}

	public void setPrimaryGuestEmailAddress(String value){
		setRequestNodeValueByXPath("/Envelope/Body/bookSet/personSet/person/emailAddress/@emailAddress", value);
	}
	
}
