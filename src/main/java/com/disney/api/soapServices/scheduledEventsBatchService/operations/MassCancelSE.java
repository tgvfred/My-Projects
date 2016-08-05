package com.disney.api.soapServices.scheduledEventsBatchService.operations;

import com.disney.api.soapServices.scheduledEventsBatchService.ScheduledEventsBatchService;
import com.disney.utils.XMLTools;

public class MassCancelSE extends ScheduledEventsBatchService{
	public MassCancelSE(String environment) {
		super(environment);
		setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("massCancelSE")));
		generateServiceContext();
		removeComments() ;
		removeWhiteSpace();
	}	

	public void setTravelPlanSegmentId(String value){setRequestNodeValueByXPath("/Envelope/Body/massCancelSE/travelPlanSegmentId", value);}
	public void setReasonText(String value){setRequestNodeValueByXPath("/Envelope/Body/massCancelSE/reasonText", value);}
}
