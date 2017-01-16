package com.disney.api.soapServices.accommodationModule.accommodationServiceV2.operations;

import com.disney.api.soapServices.accommodationModule.accommodationServiceV2.AccommodationServiceV2;
import com.disney.utils.Randomness;
import com.disney.utils.XMLTools;

public class Book extends AccommodationServiceV2{
	
	public Book(String environment, String scenario) {
		super(environment);
		
		//Generate a request from a project xml file
		setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("book")));		   	
		setRequestNodeValueByXPath(getTestScenario(getService(), getOperation(), scenario));
		setRequestNodeValueByXPath("/Envelope/Header/ServiceContext/@conversationId", Randomness.generateConversationId());
		setRequestNodeValueByXPath("/Envelope/Header/ServiceContext/@messageId", Randomness.generateMessageId());
		setRequestNodeValueByXPath("/Envelope/Header/ServiceContext/@creationTime", Randomness.generateCurrentXMLDatetime());
		removeComments() ;
		removeWhiteSpace();
		 
	}
	
	public String getTravelPlanId(){
		return getResponseNodeValueByXPath("/Envelope/Body/bookResponse/bookAccommodationResponse/travelPlanRequest/@travelPlanId");
	}

	public String getTravelPlanSegmentId(){
		return getResponseNodeValueByXPath("/Envelope/Body/bookResponse/bookAccommodationResponse/travelPlanRequest/travelPlanSegments/@travelPlanSegmentId");
	}
	
	public String getTravelComponentGroupingId(){
		return getResponseNodeValueByXPath("/Envelope/Body/bookResponse/bookAccommodationResponse/travelPlanRequest/travelPlanSegments/travelComponentGroups/@travelComponentGroupId");
	}
	
	public String getTravelComponentId(){
		return getResponseNodeValueByXPath("/Envelope/Body/bookResponse/bookAccommodationResponse/travelPlanRequest/travelPlanSegments/travelComponentGroups/accommodationComponentReference/@id");
	}
	
	public String getPartyId(){
		return getResponseNodeValueByXPath("/Envelope/Body/bookResponse/bookAccommodationResponse/bookAccommodationResult/guestResponses/@guestId");
	}
	
	public void setDepartureDate(String deptDateDaysOut){
		if(deptDateDaysOut.contains("fx:GetDate; Daysout:")){
			setRequestNodeValueByXPath("/Envelope/Body/book/bookAccommodationRequest/@departureDate", deptDateDaysOut);
			setRequestNodeValueByXPath("/Envelope/Body/book/travelPlanRequest/@areaDepartureDate", deptDateDaysOut);
		}else{
			setRequestNodeValueByXPath("/Envelope/Body/book/bookAccommodationRequest/@departureDate", "fx:GetDate; Daysout:" + deptDateDaysOut);
			setRequestNodeValueByXPath("/Envelope/Body/book/travelPlanRequest/@areaDepartureDate", "fx:GetDate; Daysout:" + deptDateDaysOut);
		}
	}

	public void setArrivalDate(String arrivalDaysOut){
		if(arrivalDaysOut.contains("fx:GetDate; Daysout:")){
			setRequestNodeValueByXPath("/Envelope/Body/book/travelPlanRequest/@areaArrivalDate", arrivalDaysOut);
			setRequestNodeValueByXPath("/Envelope/Body/book/bookAccommodationRequest/@arrivalDate", arrivalDaysOut);
		}else{
			setRequestNodeValueByXPath("/Envelope/Body/book/bookAccommodationRequest/@arrivalDate", "fx:GetDate; Daysout:" + arrivalDaysOut);
			setRequestNodeValueByXPath("/Envelope/Body/book/travelPlanRequest/@areaArrivalDate", "fx:GetDate; Daysout:" + arrivalDaysOut);			
		}
	}	

	public void setFreezeId(String freezeId){
		setRequestNodeValueByXPath("/Envelope/Body/book/bookAccommodationRequest/@freezeId", freezeId);
	}
	
	public void setPackageCode(String packageCode){
		setRequestNodeValueByXPath("/Envelope/Body/book/bookAccommodationRequest/@packageCode", packageCode);
	}
	
	public void setResortCode(String resortCode){
		setRequestNodeValueByXPath("/Envelope/Body/book/bookAccommodationRequest/@resortCode", resortCode);
	}
	
	public void setRoomTypeCode(String roomTypeCode){
		setRequestNodeValueByXPath("/Envelope/Body/book/bookAccommodationRequest/@roomType", roomTypeCode);
	}
	
	public void setSalesChannel(String salesChannel){
		setRequestNodeValueByXPath("/Envelope/Body/book/businessContext/@salesChannel", salesChannel);
	}
	
	public void setCommunicationChannel(String communicationChannel){
		setRequestNodeValueByXPath("/Envelope/Body/book/businessContext/@communicationChannel", communicationChannel);
	}

	public void setPointOfOrigin(String pointOfOrigin){
		setRequestNodeValueByXPath("/Envelope/Body/book/businessContext/@pointOfOrigin", pointOfOrigin);
	}
	
	public void setInitiatingRequestorId(String initiatingRequestorId){
		setRequestNodeValueByXPath("/Envelope/Header/ServiceContext/@initiatingRequestorId", initiatingRequestorId);
	}
	
	/**
	 * TODO: Add setHouseHoldInfo
	 */
}
