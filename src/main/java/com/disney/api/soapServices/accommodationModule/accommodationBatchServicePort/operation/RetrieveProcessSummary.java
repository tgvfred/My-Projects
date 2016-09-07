package com.disney.api.soapServices.accommodationModule.accommodationBatchServicePort.operation;

import org.w3c.dom.NodeList;

import com.disney.api.soapServices.accommodationModule.accommodationBatchServicePort.AccommodationBatchServicePort;
import com.disney.utils.XMLTools;

public class RetrieveProcessSummary extends AccommodationBatchServicePort{
	private NodeList processSummaryDetails;
	private NodeList processIds;
	private NodeList processTypes;
	
	public RetrieveProcessSummary(String environment, String scenario) {
		super(environment);

		//Generate a request from a project xml file
		setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("retrieveProcessSummary")));
		generateServiceContext();
		setRequestNodeValueByXPath(getTestScenario(getService(), getOperation(), scenario));
		removeComments() ;
		removeWhiteSpace();
	}

	public void setSubmittedBy(String value){setRequestNodeValueByXPath("/Envelope/Body/retrieveProcessSummary/request/submittedBy", value);}
	public void setProcessName(String value){setRequestNodeValueByXPath("/Envelope/Body/retrieveProcessSummary/request/processName", value);}
	public void setFromDate(String value){setRequestNodeValueByXPath("/Envelope/Body/retrieveProcessSummary/request/fromDate", value);}
	public void setToDate(String value){setRequestNodeValueByXPath("/Envelope/Body/retrieveProcessSummary/request/toDate", value);}
	public void setProcessType(String value){setRequestNodeValueByXPath("/Envelope/Body/retrieveProcessSummary/request/processType", value);}
	
	public NodeList getProcessSummaryDetails(){
		processSummaryDetails = XMLTools.getNodeList(getResponseDocument(), "/Envelope/Body/retrieveProcessSummaryResp/retrieveProcessSummaryResp/processSummaryDetails");
		return processSummaryDetails;
	}
	public NodeList getProcessIds(){
		processIds = XMLTools.getNodeList(getResponseDocument(), "/Envelope/Body/retrieveProcessSummaryResp/retrieveProcessSummaryResp/processSummaryDetails/processId");
		return processIds;
	}
	public NodeList getProcessTypes(){
		processTypes = XMLTools.getNodeList(getResponseDocument(), "/Envelope/Body/retrieveProcessSummaryResp/retrieveProcessSummaryResp/processSummaryDetails/processtype");
		return processTypes;
	}
	public String[][] getProcessIdsAndTypes(){
		String[][] idsAndTypes;
		NodeList ids = getProcessIds();
		NodeList types = getProcessTypes();
		int max = (ids.getLength() >= types.getLength()) ? ids.getLength() : types.getLength();
		idsAndTypes = new String[max][2];
		String id;
		String type;
		for(int i = 0; i < ids.getLength(); i++){
			id = "";
			type = "";
			try{id = ids.item(i).getTextContent();}catch(Exception e){}
			try{type = types.item(i).getTextContent();}catch(Exception e){}
			idsAndTypes[i][0] = id;
			idsAndTypes[i][1] = type;
		}
		return idsAndTypes;
	}
	public String getFirstProcessId(){
		if(processIds == null) processTypes = XMLTools.getNodeList(getResponseDocument(), "/Envelope/Body/retrieveProcessSummaryResp/retrieveProcessSummaryResp/processSummaryDetails/processtype");
		return processIds.item(0).getTextContent();
	}
	public String getFirstProcessType(){
		if(processTypes == null) processTypes = XMLTools.getNodeList(getResponseDocument(), "/Envelope/Body/retrieveProcessSummaryResp/retrieveProcessSummaryResp/processSummaryDetails/processtype");
		return processTypes.item(0).getTextContent();
	}
}
