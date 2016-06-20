package com.disney.api.soapServices.scheduledEventsServicePort.operations;

import com.disney.api.soapServices.scheduledEventsServicePort.ScheduledEventsServicePort;
import com.disney.utils.XMLTools;

public class RetrieveInventoryOverideReasons extends ScheduledEventsServicePort{
	public RetrieveInventoryOverideReasons(String environment) {
		super(environment);
		//Generate a request from a project xml file
		setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("retrieveInventoryOverideReasons")));
		//setRequestNodeValueByXPath(getTestScenario(getService(), getOperation(), scenario));
		generateServiceContext();
		removeComments() ;
		removeWhiteSpace();
	}
	
	public int getNumberOfInventoryOverideReasons(){
		return XMLTools.getNodeList(getResponseDocument(), "/Envelope/Body/retrieveInventoryOverideReasonsResponse/inventoryOverideReasons").getLength();		
	}
}