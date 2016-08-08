package com.disney.api.soapServices.diningModule.eventDiningService.operations;

import com.disney.api.soapServices.diningModule.eventDiningService.EventDiningService;
import com.disney.utils.XMLTools;

public class NoShow extends EventDiningService {
	public NoShow(String environment, String scenario) {
		super(environment);
		//Generate a request from a project xml file
		setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("noShow")));
//		System.out.println(getRequest());	
		
		generateServiceContext();			
		setRequestNodeValueByXPath(getTestScenario(getService(), getOperation(), scenario));
		removeComments() ;
		removeWhiteSpace();
	}

	public void setReservationNumber(String value){setRequestNodeValueByXPath("/Envelope/Body/noShow/noShowEventDining/reservationNumber", value);}
	public void setSalesChannel(String value){setRequestNodeValueByXPath("/Envelope/Body/noShow/noShowEventDining/salesChannel", value);}
	public void setCommunicationChannel(String value){setRequestNodeValueByXPath("/Envelope/Body/noShow/noShowEventDining/communicationChannel", value);}
	public String getCancellationNumber(){return getResponseNodeValueByXPath("/Envelope/Body/noShowEventDiningResponse/cancellationNumber");}
}