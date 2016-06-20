package com.disney.api.soapServices.scheduledEventsServicePort.operations;

import com.disney.api.soapServices.scheduledEventsServicePort.ScheduledEventsServicePort;
import com.disney.utils.XMLTools;

public class RetrieveCommunicationChannels extends ScheduledEventsServicePort{
	public RetrieveCommunicationChannels(String environment) {
		super(environment);
		//Generate a request from a project xml file
		setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("retrieveCommunicationChannels")));
		//setRequestNodeValueByXPath(getTestScenario(getService(), getOperation(), scenario));
		generateServiceContext();
		removeComments() ;
		removeWhiteSpace();
	}
	
	public int getNumberOfCommunicationChannels(){
		return XMLTools.getNodeList(getResponseDocument(), "/Envelope/Body/retrieveCommunicationChannelsResponse/communicationChannels").getLength();		
	}
}