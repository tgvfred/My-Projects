package com.disney.api.soapServices.diningModule.scheduledEventsServicePort.operations;

import com.disney.api.soapServices.diningModule.scheduledEventsServicePort.ScheduledEventsServicePort;
import com.disney.utils.XMLTools;

public class MassCancelSE extends ScheduledEventsServicePort{
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
