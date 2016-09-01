package com.disney.api.soapServices.diningModule.scheduledEventsBatchService.operations;

import com.disney.api.soapServices.diningModule.scheduledEventsBatchService.ScheduledEventsBatchService;
import com.disney.utils.XMLTools;

public class MassCancelReservation extends ScheduledEventsBatchService{
	public MassCancelReservation(String environment) {
		super(environment);
		setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("massCancelReservation")));
		generateServiceContext();
		removeComments() ;
		removeWhiteSpace();
	}	

	public void setTravelPlanSegmentId(String value){setRequestNodeValueByXPath("/Envelope/Body/massCancelReservation/travelPlanSegmentId", value);}
	public void setReasonText(String value){setRequestNodeValueByXPath("/Envelope/Body/massCancelReservation/reasonText", value);}
}
