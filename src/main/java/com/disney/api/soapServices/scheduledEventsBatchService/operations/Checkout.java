package com.disney.api.soapServices.scheduledEventsBatchService.operations;

import com.disney.api.soapServices.scheduledEventsBatchService.ScheduledEventsBatchService;
import com.disney.utils.XMLTools;

public class Checkout extends ScheduledEventsBatchService{
	public Checkout(String environment) {
		super(environment);
		//Generate a request from a project xml file
		setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("checkout")));
		//setRequestNodeValueByXPath(getTestScenario(getService(), getOperation(), scenario));
		generateServiceContext();
		removeComments() ;
		removeWhiteSpace();
	}
	public void setTravelPlanSegmentId(String tpsId){
		setRequestNodeValueByXPath("/Envelope/Body/checkout/checkoutRequest/travelPlanSegmentId", tpsId);
	}
	
	public boolean isSuccessfullyCheckedOut(){
		return getResponseNodeValueByXPath("/Envelope/Body/checkoutResponse/checkoutResponseDetail/isSuccess").equals("true");		
	}

	public String getReservationNumber(){return getResponseNodeValueByXPath("/Envelope/Body/checkoutResponse/checkoutResponseDetail/travelPLanSegmentId");}
}