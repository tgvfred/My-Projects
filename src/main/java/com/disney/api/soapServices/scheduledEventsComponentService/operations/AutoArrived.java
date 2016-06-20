package com.disney.api.soapServices.scheduledEventsComponentService.operations;

import com.disney.api.soapServices.scheduledEventsComponentService.ScheduledEventsComponentService;
import com.disney.utils.XMLTools;

public class AutoArrived extends ScheduledEventsComponentService{
	public AutoArrived(String environment, String scenario) {
		super(environment);

		//Generate a request from a project xml file
		setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("autoArrived")));
		generateServiceContext();
		setRequestNodeValueByXPath(getTestScenario(getService(), getOperation(), scenario));
		removeComments() ;
		removeWhiteSpace();
	}

	public void setTpsId(String value){setRequestNodeValueByXPath("/Envelope/Body/autoArrived/tpsIds", value);}
}
