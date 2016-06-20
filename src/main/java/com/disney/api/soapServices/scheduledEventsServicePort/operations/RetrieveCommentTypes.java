package com.disney.api.soapServices.scheduledEventsServicePort.operations;

import com.disney.api.soapServices.scheduledEventsServicePort.ScheduledEventsServicePort;
import com.disney.utils.XMLTools;

public class RetrieveCommentTypes extends ScheduledEventsServicePort{
	public RetrieveCommentTypes(String environment) {
		super(environment);
		//Generate a request from a project xml file
		setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("retrieveCommentTypes")));
		//setRequestNodeValueByXPath(getTestScenario(getService(), getOperation(), scenario));
		generateServiceContext();
		removeComments() ;
		removeWhiteSpace();
	}
	
	public int getNumberOfCommentTypes(){
		return XMLTools.getNodeList(getResponseDocument(), "/Envelope/Body/retrieveCommentTypesResponse/commentTypes").getLength();		
	}
}