package com.disney.api.soapServices.activityServicePort.operations;

import com.disney.api.soapServices.activityServicePort.ActivityService;
import com.disney.utils.XMLTools;

public class Cancel extends ActivityService {
	public Cancel(String environment, String scenario) {
		super(environment);
		setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("cancel")));
		generateServiceContext();	
		
        generateServiceContext();			
        setRequestNodeValueByXPath(getTestScenario(getService(), getOperation(), scenario));
		removeComments() ;
		removeWhiteSpace();
	}

	public void setReservationNumber(String value){
		setRequestNodeValueByXPath("/Envelope/Body/cancel/cancelActivityComponentRequest/reservationNumber", value);
	}
	
	public String getCancellationConfirmationNumber(){
		return getResponseNodeValueByXPath("/Envelope/Body/cancelActivityComponentResponse/cancellationNumber");
	}
}
