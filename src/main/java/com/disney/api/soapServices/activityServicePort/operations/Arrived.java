package com.disney.api.soapServices.activityServicePort.operations;

import com.disney.api.soapServices.activityServicePort.ActivityService;
import com.disney.utils.XMLTools;

public class Arrived extends ActivityService {
	public Arrived(String environment, String scenario) {
		super(environment);
		//Generate a request from a project xml file
		setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("arrived")));
//		System.out.println(getRequest());
	
		
		generateServiceContext();			
		setRequestNodeValueByXPath(getTestScenario(getService(), getOperation(), scenario));
		removeComments() ;
		removeWhiteSpace();
	}
	
	public void setReservationNumber(String value){setRequestNodeValueByXPath("/Envelope/Body/arrived/arrivedActivityComponentRequest/reservationNumber", value);}
	public void setSalesChannel(String value){setRequestNodeValueByXPath("/Envelope/Body/arrived/arrivedActivityComponentRequest/salesChannel", value);}
	public void setCommunicationChannel(String value){setRequestNodeValueByXPath("/Envelope/Body/arrived/arrivedActivityComponentRequest/communicationChannel", value);}
	public String getArrivalStatus(){return getResponseNodeValueByXPath("/Envelope/Body/arrivedActivityComponentResponse/status");}
}