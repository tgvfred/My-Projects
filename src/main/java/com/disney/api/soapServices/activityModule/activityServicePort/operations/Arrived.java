package com.disney.api.soapServices.activityModule.activityServicePort.operations;

import com.disney.api.soapServices.activityModule.activityServicePort.ActivityService;
import com.disney.utils.XMLTools;

public class Arrived extends ActivityService {
	public Arrived(String environment, String scenario) {
		super(environment);
		//Generate a request from a project xml file
		setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("arrived")));
		
		generateServiceContext();			
		setRequestNodeValueByXPath(getTestScenario(getService(), getOperation(), scenario));
		removeComments() ;
		removeWhiteSpace();
	}
	
	/**
	 * Sets the reservation number in the SOAP request
	 * @param value - reservation number
	 */
	public void setReservationNumber(String value){setRequestNodeValueByXPath("/Envelope/Body/arrived/arrivedActivityComponentRequest/reservationNumber", value);}
	/**
	 * Sets the sales channel in the SOAP request
	 * @param value - sales channel
	 */
	public void setSalesChannel(String value){setRequestNodeValueByXPath("/Envelope/Body/arrived/arrivedActivityComponentRequest/salesChannel", value);}
	/**
	 * Sets the communications channel in the SOAP request
	 * @param value - communications channel
	 */
	public void setCommunicationChannel(String value){setRequestNodeValueByXPath("/Envelope/Body/arrived/arrivedActivityComponentRequest/communicationChannel", value);}
	/**
	 * Gets the status from updating the reservation to 'Arrived'
	 * @return - status from updating the reservation to 'Arrived'
	 */
	public String getArrivalStatus(){return getResponseNodeValueByXPath("/Envelope/Body/arrivedActivityComponentResponse/status");}
}