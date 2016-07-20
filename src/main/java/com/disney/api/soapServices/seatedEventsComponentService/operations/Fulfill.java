package com.disney.api.soapServices.seatedEventsComponentService.operations;

import com.disney.api.soapServices.seatedEventsComponentService.SeatedEventsComponentService;
import com.disney.utils.XMLTools;

public class Fulfill extends SeatedEventsComponentService{
	public Fulfill(String environment, String scenario) {
		super(environment);
		//Generate a request from a project xml file
		setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("fulfill")));
		setRequestNodeValueByXPath(getTestScenario(getService(), getOperation(), scenario));
		generateServiceContext();
		removeComments() ;
		removeWhiteSpace();
	}
	
	/**
	 * Sets external reference number
	 * @param value - external reference number
	 */
	public void setExternalReferenceNumber(String value){setRequestNodeValueByXPath("/Envelope/Body/fulfill/cirqueRequest/externalReferenceNumber", value);}
	/**
	 * Sets communication channel
	 * @param value - communication channel
	 */
	public void setCommunicationChannel(String value){setRequestNodeValueByXPath("/Envelope/Body/fulfill/cirqueRequest/communicationChannel", value);}
	/**
	 * Sets operating area
	 * @param value - operating area
	 */
	public void setOperatingArea(String value){setRequestNodeValueByXPath("/Envelope/Body/fulfill/cirqueRequest/operatingArea", value);}
	/**
	 * Sets reservation number
	 * @param value - reservation number
	 */
	public void setReservationNumber(String value){setRequestNodeValueByXPath("/Envelope/Body/fulfill/cirqueRequest/reservationNumber", value);}
	/**
	 * Sets sales channel
	 * @param value - sales channel
	 */
	public void setSalesChannel(String value){setRequestNodeValueByXPath("/Envelope/Body/fulfill/cirqueRequest/salesChannel", value);}
}
