package com.disney.api.soapServices.accommodationModule.accommodationBatchComponentWSPort.operation;

import static com.disney.api.soapServices.accommodationModule.helpers.AccommodationBaseTest.isValid;

import com.disney.api.soapServices.accommodationModule.accommodationBatchComponentWSPort.AccommodationBatchComponentWSPort;
import com.disney.api.soapServices.core.BaseSoapCommands;
import com.disney.utils.XMLTools;

public class RetreiveIdsToProcess extends AccommodationBatchComponentWSPort{
	
public RetreiveIdsToProcess(String environment, String scenario) {
		super(environment);
		 // Generate a request from a project xml file
        setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("retreiveIdsToProcess")));
        generateServiceContext();
        //setRequestNodeValueByXPath(getService(), getOperation());
        removeComments();
        removeWhiteSpace();
    }
	//Setters
	public void setProcessId(String value) {
        setRequestNodeValueByXPath("/Envelope/Body/retreiveIdsToProcess/processId", value);
    }
		
	//Getters
	public String getRetreiveIdsToProcessResponse() {
		return getResponseNodeValueByXPath("/Envelope/Body/retreiveIdsToProcessResp/retreiveIdsToProcessResponse");
	}
	public String getProcessDataIdList() {
		return getResponseNodeValueByXPath("/Envelope/Body/retreiveIdsToProcessResp/retreiveIdsToProcessResponse/processDataIdList");
	}
	public String getProcessType() {
		return getResponseNodeValueByXPath("/Envelope/Body/retreiveIdsToProcessResp/retreiveIdsToProcessResponse/processType");
	}
	
}
