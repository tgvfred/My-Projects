package com.disney.api.soapServices.travelPlanSegmentServicePort.operations;

import com.disney.api.soapServices.travelPlanSegmentServicePort.TravelPlanSegmentServicePort;
import com.disney.utils.XMLTools;

public class UpdateMessagingStatus extends TravelPlanSegmentServicePort{

	public UpdateMessagingStatus(String environment) {
		super(environment);

		//Generate a request from a project xml file
		setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("updateMessagingStatus")));
	
		removeComments() ;
		removeWhiteSpace();
		
		generateServiceContext();
	}
	
	public void setTravelPlanSegment(String value){
		setRequestNodeValueByXPath("/Envelope/Body/updateMessagingStatus/updateMessagingStatusRequest/travelPlanSegmentIds", value);
	}

	public void setMessagingIndicator(String value){
		setRequestNodeValueByXPath("/Envelope/Body/updateMessagingStatus/updateMessagingStatusRequest/messagingIndicator", value);
	}
	
}
