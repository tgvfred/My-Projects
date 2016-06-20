package com.disney.api.soapServices.scheduledEventsServicePort.operations;

import com.disney.api.soapServices.scheduledEventsServicePort.ScheduledEventsServicePort;
import com.disney.utils.XMLTools;

public class RetrieveAlaCarteCancelReasons extends ScheduledEventsServicePort{
	public RetrieveAlaCarteCancelReasons(String environment) {
		super(environment);
		//Generate a request from a project xml file
		setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("retrieveAlaCarteCancelReasons")));
		//setRequestNodeValueByXPath(getTestScenario(getService(), getOperation(), scenario));
		generateServiceContext();
		removeComments() ;
		removeWhiteSpace();
	}
	
	public int getNumberOfAlcCancelReasons(){
		return XMLTools.getNodeList(getResponseDocument(), "/Envelope/Body/retrieveAlaCarteCancelReasonsResponse/alaCarteCancelReasons").getLength();		
	}
}