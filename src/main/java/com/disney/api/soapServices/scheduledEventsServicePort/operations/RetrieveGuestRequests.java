package com.disney.api.soapServices.scheduledEventsServicePort.operations;

import com.disney.api.soapServices.scheduledEventsServicePort.ScheduledEventsServicePort;
import com.disney.utils.XMLTools;

public class RetrieveGuestRequests extends ScheduledEventsServicePort{
	public RetrieveGuestRequests(String environment) {
		super(environment);
		//Generate a request from a project xml file
		setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("retrieveGuestRequests")));
		//setRequestNodeValueByXPath(getTestScenario(getService(), getOperation(), scenario));
		generateServiceContext();
		removeComments() ;
		removeWhiteSpace();
	}
	
	public int getNumberOfGuestRequests(){
		return XMLTools.getNodeList(getResponseDocument(), "/Envelope/Body/retrieveGuestRequestsResponse/guestRequests").getLength();		
	}
}