package com.disney.api.soapServices.scheduledEventsServicePort.operations;

import com.disney.api.soapServices.scheduledEventsServicePort.ScheduledEventsServicePort;
import com.disney.utils.XMLTools;

public class RetrieveSalesChannels extends ScheduledEventsServicePort{
	public RetrieveSalesChannels(String environment) {
		super(environment);
		//Generate a request from a project xml file
		setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("retrieveSalesChannels")));
		//setRequestNodeValueByXPath(getTestScenario(getService(), getOperation(), scenario));
		generateServiceContext();
		removeComments() ;
		removeWhiteSpace();
	}
	
	public int getNumberOfSalesChannels(){
		return XMLTools.getNodeList(getResponseDocument(), "/Envelope/Body/retrieveSalesChannelsResponse/salesChannels").getLength();		
	}
}