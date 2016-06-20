package com.disney.api.soapServices.scheduledEventsServicePort.operations;

import com.disney.api.soapServices.scheduledEventsServicePort.ScheduledEventsServicePort;
import com.disney.utils.XMLTools;

public class RetrieveCelebrations extends ScheduledEventsServicePort{
	public RetrieveCelebrations(String environment) {
		super(environment);
		//Generate a request from a project xml file
		setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("retrieveCelebrations")));
		//setRequestNodeValueByXPath(getTestScenario(getService(), getOperation(), scenario));
		generateServiceContext();
		removeComments() ;
		removeWhiteSpace();
	}
	
	public int getNumberOfCelebrations(){
		return XMLTools.getNodeList(getResponseDocument(), "/Envelope/Body/retrieveCelebrationsResponse/celebrations").getLength();		
	}
}