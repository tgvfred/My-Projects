package com.disney.api.soapServices.scheduledEventsServicePort.operations;

import com.disney.api.soapServices.scheduledEventsServicePort.ScheduledEventsServicePort;
import com.disney.utils.XMLTools;

public class RetrieveSpecialNeeds extends ScheduledEventsServicePort{
	public RetrieveSpecialNeeds(String environment) {
		super(environment);
		//Generate a request from a project xml file
		setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("retrieveSpecialNeeds")));
		//setRequestNodeValueByXPath(getTestScenario(getService(), getOperation(), scenario));
		generateServiceContext();
		removeComments() ;
		removeWhiteSpace();
	}
	
	public int getNumberOfSpecialNeeds(){
		return XMLTools.getNodeList(getResponseDocument(), "/Envelope/Body/retrieveSpecialNeedsResponse/specialNeeds").getLength();		
	}
}