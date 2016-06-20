package com.disney.api.soapServices.scheduledEventsServicePort.operations;

import com.disney.api.soapServices.scheduledEventsServicePort.ScheduledEventsServicePort;
import com.disney.utils.XMLTools;

public class RetrieveReprintReasons extends ScheduledEventsServicePort{
	public RetrieveReprintReasons(String environment) {
		super(environment);
		//Generate a request from a project xml file
		setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("retrieveReprintReasons")));
		//setRequestNodeValueByXPath(getTestScenario(getService(), getOperation(), scenario));
		generateServiceContext();
		removeComments() ;
		removeWhiteSpace();
	}
	
	public int getNumberOfReprintReasons(){
		return XMLTools.getNodeList(getResponseDocument(), "/Envelope/Body/retrieveReprintReasonsResponse/reprintReasons").getLength();		
	}
}