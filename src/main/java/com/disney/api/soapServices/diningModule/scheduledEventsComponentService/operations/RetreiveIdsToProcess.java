package com.disney.api.soapServices.diningModule.scheduledEventsComponentService.operations;

import com.disney.api.soapServices.diningModule.scheduledEventsComponentService.ScheduledEventsComponentService;
import com.disney.utils.XMLTools;

public class RetreiveIdsToProcess extends ScheduledEventsComponentService{
	public RetreiveIdsToProcess(String environment, String scenario) {
		super(environment);

		//Generate a request from a project xml file
		setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("retreiveIdsToProcess")));
		generateServiceContext();
		setRequestNodeValueByXPath(getTestScenario(getService(), getOperation(), scenario));
		removeComments() ;
		removeWhiteSpace();
	}

	public void setProcessId(String value){setRequestNodeValueByXPath("/Envelope/Body/retreiveIdsToProcess/processId", value);}
	
	public String getProcessDataIdList(){return getResponseNodeValueByXPath("/Envelope/Body/retreiveIdsToProcessResp/retreiveIdsToProcessResponse/processDataIdList");}
	public String getProcessType(){return getResponseNodeValueByXPath("/Envelope/Body/retreiveIdsToProcessResp/retreiveIdsToProcessResponse/processType");}
}
