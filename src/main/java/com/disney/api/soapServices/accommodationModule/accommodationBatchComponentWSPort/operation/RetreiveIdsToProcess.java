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
	int index = getNumberOfRequestNodesByXPath("/Envelope/Body/retreiveIdsToProcessResp/retreiveIdsToProcessResponse") + 1;
	
	public String getRetreiveIdsToProcessResponse(String index) {
		return getResponseNodeValueByXPath("/Envelope/Body/retreiveIdsToProcessResp/retreiveIdsToProcessResponse["+index+"]");
	}
	public Integer  getRetreiveIdsToProcessResponse(){
    	return getNumberOfResponseNodesByXPath("/Envelope/Body/retreiveIdsToProcessResp/retreiveIdsToProcessResponse");
    }
	public String getProcessDataIdList() {
		return getResponseNodeValueByXPath("/Envelope/Body/retreiveIdsToProcessResp/retreiveIdsToProcessResponse/processDataIdList");
	}
	public String getProcessType() {
		return getResponseNodeValueByXPath("/Envelope/Body/retreiveIdsToProcessResp/retreiveIdsToProcessResponse/processType");
	}
	public String getProcessId(){
		return getResponseNodeValueByXPath("/Envelope/Body/retreiveIdsToProcess/processId");
	}
	
}
