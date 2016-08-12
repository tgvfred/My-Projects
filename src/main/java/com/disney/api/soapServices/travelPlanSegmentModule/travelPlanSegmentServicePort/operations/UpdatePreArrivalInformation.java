package com.disney.api.soapServices.travelPlanSegmentModule.travelPlanSegmentServicePort.operations;

import com.disney.api.soapServices.travelPlanSegmentModule.ServicePort.TravelPlanSegmentServicePort;
import com.disney.test.utils.Randomness;
import com.disney.utils.XMLTools;

public class UpdatePreArrivalInformation extends TravelPlanSegmentServicePort{

	public UpdatePreArrivalInformation(String environment, String scenario) {
		super(environment);

		//Generate a request from a project xml file
		setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("updatePreArrivalInformation")));
	
		setRequestNodeValueByXPath(getTestScenario(getService(), getOperation(), scenario));
		removeComments() ;
		removeWhiteSpace();
		
		setRequestNodeValueByXPath("/Envelope/Body/updatePreArrivalInformation/context/conversationId", Randomness.generateConversationId());
		setRequestNodeValueByXPath("/Envelope/Body/updatePreArrivalInformation/context/messageId", Randomness.generateMessageId());
		setRequestNodeValueByXPath("/Envelope/Body/updatePreArrivalInformation/context/requestTimeStamp", Randomness.generateCurrentDatetime());
		setRequestNodeValueByXPath("/Envelope/Body/updatePreArrivalInformation/context/userName", "NGEONEVIEW");
	}
	
	public String getSuccessStatus(){
		return getResponseNodeValueByXPath("/Envelope/Body/updatePreArrivalInformationResponse/updatePreArrivalInfoOutput/success");
	}

	public void setTravelPlanSegment(String value){
		setRequestNodeValueByXPath("/Envelope/Body/updatePreArrivalInformation/request/travelPlanSegmentId", value);
	}
	
	public void setTermsAndConditionsVersionNumber(String value){
		setRequestNodeValueByXPath("/Envelope/Body/updatePreArrivalInformation/request/preArrivalInfo/termsAndConditionsVersionNumber", value);
	}
	
	public String setTermsAndConditionsVersionNumber(){
		return getRequestNodeValueByXPath("/Envelope/Body/updatePreArrivalInformation/request/preArrivalInfo/termsAndConditionsVersionNumber");
	}
	
	public void setArrivalTime(String value){
		setRequestNodeValueByXPath("/Envelope/Body/updatePreArrivalInformation/request/preArrivalInfo/arrivalTime", value);
	}
	
	public String getArrivalTime(){
		return getRequestNodeValueByXPath("/Envelope/Body/updatePreArrivalInformation/request/preArrivalInfo/arrivalTime");
	}
	
	public void setDepartureTime(String value){
		setRequestNodeValueByXPath("/Envelope/Body/updatePreArrivalInformation/request/preArrivalInfo/departureTime", value);
	}
	
	public String getDepartureTime(){
		return getRequestNodeValueByXPath("/Envelope/Body/updatePreArrivalInformation/request/preArrivalInfo/departureTime");
	}
	
	public void setcontactType(String value){
		setRequestNodeValueByXPath("/Envelope/Body/updatePreArrivalInformation/request/preArrivalInfo/onsiteContactDetails/contactType", value);
	}
	
	public String getcontactType(){
		return getRequestNodeValueByXPath("/Envelope/Body/updatePreArrivalInformation/request/preArrivalInfo/onsiteContactDetails/contactType");
	}
	
	public void setcontactValue(String value){
		setRequestNodeValueByXPath("/Envelope/Body/updatePreArrivalInformation/request/preArrivalInfo/onsiteContactDetails/contactValue", value);
	}
	
	public String getcontactValue(){
		return getRequestNodeValueByXPath("/Envelope/Body/updatePreArrivalInformation/request/preArrivalInfo/onsiteContactDetails/contactValue");
	}
}
