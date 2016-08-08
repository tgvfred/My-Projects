package com.disney.api.soapServices.activityModule.activityServicePort.operations;

import com.disney.api.soapServices.activityModule.activityServicePort.ActivityService;
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

	/**
	 * Sets the reservation number in the SOAP request
	 * @param value - reservation number
	 */
	public void setReservationNumber(String value){setRequestNodeValueByXPath("/Envelope/Body/cancel/cancelActivityComponentRequest/reservationNumber", value);}	
	/**
	 * Gets the cancellation number from the SOAP response
	 * @return - cancellation number
	 */
	public String getCancellationConfirmationNumber(){return getResponseNodeValueByXPath("/Envelope/Body/cancelActivityComponentResponse/cancellationNumber");}
}