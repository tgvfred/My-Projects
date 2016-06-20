package com.disney.api.soapServices.scheduledEventsServicePort.operations;

import com.disney.api.soapServices.scheduledEventsServicePort.ScheduledEventsServicePort;
import com.disney.utils.XMLTools;

public class RetrieveBookingStatusValues extends ScheduledEventsServicePort{
	public RetrieveBookingStatusValues(String environment) {
		super(environment);
		//Generate a request from a project xml file
		setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("retrieveBookingStatusValues")));
		//setRequestNodeValueByXPath(getTestScenario(getService(), getOperation(), scenario));
		generateServiceContext();
		removeComments() ;
		removeWhiteSpace();
	}
	
	public int getNumberOfBookingStatusValues(){
		return XMLTools.getNodeList(getResponseDocument(), "/Envelope/Body/retrieveBookingStatusValuesResponse/bookingStatusValues").getLength();		
	}
}