package com.disney.api.soapServices.scheduledEventsComponentService.operations;

import com.disney.api.soapServices.scheduledEventsComponentService.ScheduledEventsComponentService;
import com.disney.utils.XMLTools;

public class AssociateLevelNEntitlementId extends ScheduledEventsComponentService{
	public AssociateLevelNEntitlementId(String environment, String scenario) {
		super(environment);

		//Generate a request from a project xml file
		setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("associateLevelNEntitlementId")));
	
		setRequestNodeValueByXPath(getTestScenario(getService(), getOperation(), scenario));
		generateServiceContext();
		removeComments() ;
		removeWhiteSpace();
	}

	public void setTpsId(String value){setRequestNodeValueByXPath("/Envelope/Body/associateLevelNEntitlementId/tpsId", value);}
}
