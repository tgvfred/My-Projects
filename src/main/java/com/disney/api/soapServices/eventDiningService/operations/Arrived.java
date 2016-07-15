package com.disney.api.soapServices.eventDiningService.operations;

import com.disney.api.soapServices.eventDiningService.EventDiningService;
import com.disney.utils.XMLTools;

public class Arrived extends EventDiningService {
	public Arrived(String environment, String scenario) {
		super(environment);
		//Generate a request from a project xml file
		setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("arrived")));	
		
		generateServiceContext();			
		setRequestNodeValueByXPath(getTestScenario(getService(), getOperation(), scenario));
		removeComments() ;
		removeWhiteSpace();
	}
	
	public void setReservationNumber(String value){setRequestNodeValueByXPath("/Envelope/Body/arrived/arrivedEventDiningRequest/reservationNumber", value);}
	public void setSalesChannel(String value){setRequestNodeValueByXPath("/Envelope/Body/arrived/arrivedEventDiningRequest/salesChannel", value);}
	public void setCommunicationChannel(String value){setRequestNodeValueByXPath("/Envelope/Body/arrived/arrivedEventDiningRequest/communicationChannel", value);}
	public String getArrivalStatus(){return getResponseNodeValueByXPath("/Envelope/Body/arrivedEventDiningResponse/status");}
}
