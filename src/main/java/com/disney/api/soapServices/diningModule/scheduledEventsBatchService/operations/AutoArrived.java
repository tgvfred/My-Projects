package com.disney.api.soapServices.diningModule.scheduledEventsBatchService.operations;

import com.disney.api.soapServices.diningModule.scheduledEventsBatchService.ScheduledEventsBatchService;
import com.disney.utils.XMLTools;

public class AutoArrived extends ScheduledEventsBatchService{
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
