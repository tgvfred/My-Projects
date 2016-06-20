package com.disney.api.soapServices.activityServicePort.operations;

import com.disney.api.soapServices.activityServicePort.ActivityService;
import com.disney.utils.XMLTools;

public class Retrieve extends ActivityService {
	public Retrieve(String environment, String scenario) {
		super(environment);
		setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("retrieve")));
		
		generateServiceContext();			
		setRequestNodeValueByXPath(getTestScenario(getService(), getOperation(), scenario));
		removeComments() ;
		removeWhiteSpace();
	}

	public void setReservationNumber(String value){
		setRequestNodeValueByXPath("/Envelope/Body/retrieve/retrieveActivityComponentRequest/reservationNumber", value);
	}
	
	public String getReservationNumber(){
		return getResponseNodeValueByXPath("/Envelope/Body/retrieve/retrieveActivityComponentRequest/reservationNumber");    	
	}
	
	public void setSalesChannel(String value){
		setRequestNodeValueByXPath("/Envelope/Body/retrieve/retrieveActivityComponentRequest/salesChannel", value);
	}
	
	public void setCommunicationsChannel(String value){
		setRequestNodeValueByXPath("/Envelope/Body/retrieve/retrieveActivityComponentRequest/communicationChannel", value);
	}
	
	public String getPartyId(){
		return getResponseNodeValueByXPath("/Envelope/Body/retrieveActivityComponentResponse/activityReservation/primaryGuest/partyId");
	}
}