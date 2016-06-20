package com.disney.api.soapServices.scheduledEventsServicePort.operations;

import com.disney.api.soapServices.scheduledEventsServicePort.ScheduledEventsServicePort;
import com.disney.utils.XMLTools;

public class RetrieveCancelReasons extends ScheduledEventsServicePort{
	public RetrieveCancelReasons(String environment) {
		super(environment);
		//Generate a request from a project xml file
		setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("retrieveCancelReasons")));
		//setRequestNodeValueByXPath(getTestScenario(getService(), getOperation(), scenario));
		generateServiceContext();
		removeComments() ;
		removeWhiteSpace();
	}
	
	public int getNumberOfCancelReasons(){
		return XMLTools.getNodeList(getResponseDocument(), "/Envelope/Body/retrieveCancelReasonsResponse/cancelReasons").getLength();		
	}
}