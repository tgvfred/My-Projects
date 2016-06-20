package com.disney.api.soapServices.scheduledEventsComponentService.operations;

import org.w3c.dom.NodeList;

import com.disney.api.soapServices.scheduledEventsComponentService.ScheduledEventsComponentService;
import com.disney.utils.XMLTools;

public class RetrieveTravelPlanSegmentsForAutoArrival extends ScheduledEventsComponentService{
	public RetrieveTravelPlanSegmentsForAutoArrival(String environment, String scenario) {
		super(environment);

		//Generate a request from a project xml file
		setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("retrieveTravelPlanSegmentsForAutoArrival")));
		generateServiceContext();
		setRequestNodeValueByXPath(getTestScenario(getService(), getOperation(), scenario));
		removeComments() ;
		removeWhiteSpace();
	}

	public void setProcessDate(String value){setRequestNodeValueByXPath("/Envelope/Body/retrieveTravelPlanSegmentsForAutoArrival/request/processDate", value);}
	public void setSourceAccountingCenter(String value){setRequestNodeValueByXPath("/Envelope/Body/retrieveTravelPlanSegmentsForAutoArrival/request/sourceAccountingCenter", value);}
	
	public String getTravelPlanIdByIndex(String index){return getResponseNodeValueByXPath("/Envelope/Body/retrieveTravelPlanSegmentsForAutoArrivalResponse/return["+index+"]/travelPlanId");}
	public String getTravelPlanSegmentIdByIndex(String index){return getResponseNodeValueByXPath("/Envelope/Body/retrieveTravelPlanSegmentsForAutoArrivalResponse/return["+index+"]/travelPlanSegmentId");}
	public NodeList getAllReturnNodes(){return XMLTools.getNodeList(getResponseDocument(), "/Envelope/Body/retrieveTravelPlanSegmentsForAutoArrivalResponse/return");}
}
