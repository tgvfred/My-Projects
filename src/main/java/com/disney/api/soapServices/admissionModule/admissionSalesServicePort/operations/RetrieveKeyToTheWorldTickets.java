package com.disney.api.soapServices.admissionModule.admissionSalesServicePort.operations;

import com.disney.api.soapServices.admissionModule.admissionSalesServicePort.AdmissionSalesServicePort;
import com.disney.utils.XMLTools;
//import com.disney.wdpr.tdm.util.tdod.clientutil.TdodClientUtil;

public class RetrieveKeyToTheWorldTickets extends AdmissionSalesServicePort{
	
	public RetrieveKeyToTheWorldTickets(String environment, String scenario) {
		super(environment);
		//Generate a request from a project xml file
		setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("retrieveKeyToTheWorldTickets")));
	
		generateServiceContext();
		System.out.println(getRequest());
		setRequestNodeValueByXPath(getTestScenario(getService(), getOperation(), scenario));
		removeComments() ;
		removeWhiteSpace();

	}
	
	public void setTravelPlanSegmentId(String value){
		setRequestNodeValueByXPath("/Envelope/Body/retrieveKeyToTheWorldTickets/arg0/externalReferenceNumber", value);
	}
	
	public String getAdmissionComponentId(){
		return getResponseNodeValueByXPath("/Envelope/Body/retrieveKeyToTheWorldTicketsResponse/return/componentId");
	}
	
	public String getBaseAdmissionProductId(){
		return getResponseNodeValueByXPath("/Envelope/Body/retrieveKeyToTheWorldTicketsResponse/return/baseAdmissionProductId");
	}
	
	public String getAdmissionComponentId(String element){
		return getResponseNodeValueByXPath("/Envelope/Body/retrieveKeyToTheWorldTicketsResponse/return["+element+"]/componentId");
	}
	
	public String getBaseAdmissionProductId(String element){
		return getResponseNodeValueByXPath("/Envelope/Body/retrieveKeyToTheWorldTicketsResponse/return["+element+"]/baseAdmissionProductId");
	}
}
