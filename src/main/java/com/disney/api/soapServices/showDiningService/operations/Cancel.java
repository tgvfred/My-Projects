package com.disney.api.soapServices.showDiningService.operations;

import com.disney.api.soapServices.showDiningService.ShowDiningService;
import com.disney.utils.XMLTools;

public class Cancel extends ShowDiningService {
	public Cancel(String environment, String scenario) {
		super(environment);
		//Generate a request from a project xml file
		setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("cancel")));
		
		generateServiceContext();			
		setRequestNodeValueByXPath(getTestScenario(getService(), getOperation(), scenario));
		removeComments() ;
		removeWhiteSpace();
	}
	
	public void setTravelPlanSegmentId(String value){
		setRequestNodeValueByXPath("/Envelope/Body/cancel/cancelShowDiningRequest/reservationNumber", value);
	}
	
	public String getCancellationConfirmationNumber(){
		return getResponseNodeValueByXPath("/Envelope/Body/cancelShowDiningResponse/cancellationNumber");
	}
}
