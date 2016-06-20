package com.disney.api.soapServices.scheduledEventsServicePort.operations;

import com.disney.api.soapServices.scheduledEventsServicePort.ScheduledEventsServicePort;
import com.disney.utils.XMLTools;

public class RetrieveMassCancelReasons extends ScheduledEventsServicePort{
	public RetrieveMassCancelReasons(String environment) {
		super(environment);
		//Generate a request from a project xml file
		setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("retrieveMassCancelReasons")));
		//setRequestNodeValueByXPath(getTestScenario(getService(), getOperation(), scenario));
		generateServiceContext();
		removeComments() ;
		removeWhiteSpace();
	}
	
	public int getNumberOfMassCancelReasons(){
		return XMLTools.getNodeList(getResponseDocument(), "/Envelope/Body/retrieveMassCancelReasonsResponse/massCancelReasons").getLength();		
	}
}