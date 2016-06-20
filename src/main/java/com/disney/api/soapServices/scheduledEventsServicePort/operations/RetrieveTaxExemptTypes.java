package com.disney.api.soapServices.scheduledEventsServicePort.operations;

import com.disney.api.soapServices.scheduledEventsServicePort.ScheduledEventsServicePort;
import com.disney.utils.XMLTools;

public class RetrieveTaxExemptTypes extends ScheduledEventsServicePort{
	public RetrieveTaxExemptTypes(String environment) {
		super(environment);
		//Generate a request from a project xml file
		setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("retrieveTaxExemptTypes")));
		//setRequestNodeValueByXPath(getTestScenario(getService(), getOperation(), scenario));
		generateServiceContext();
		removeComments() ;
		removeWhiteSpace();
	}
	
	public int getNumberOfTaxExemptTypes(){
		return XMLTools.getNodeList(getResponseDocument(), "/Envelope/Body/retrieveTaxExemptTypesResponse/taxExemptTypes").getLength();		
	}
}