package com.disney.api.soapServices.eventDiningService.operations;

import com.disney.api.soapServices.eventDiningService.EventDiningService;
import com.disney.utils.XMLTools;

public class Cancel extends EventDiningService {
	public Cancel(String environment, String scenario) {
		super(environment);
		//Generate a request from a project xml file
		setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("cancel")));
		
		generateServiceContext();				
        setRequestNodeValueByXPath(getTestScenario(getService(), getOperation(), scenario));
		removeComments() ;
		removeWhiteSpace();
	}

	public void setReservationNumber(String value){
		setRequestNodeValueByXPath("/Envelope/Body/cancel/cancelEventDiningRequest/reservationNumber", value);
	}
	
	public String getCancellationConfirmationNumber(){
		return getResponseNodeValueByXPath("/Envelope/Body/cancelEventDiningResponse/cancellationNumber");
	}
}
