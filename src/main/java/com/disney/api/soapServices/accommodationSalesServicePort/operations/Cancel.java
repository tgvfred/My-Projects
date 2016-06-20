package com.disney.api.soapServices.accommodationSalesServicePort.operations;

import com.disney.api.soapServices.accommodationSalesServicePort.AccommodationSalesServicePort;
import com.disney.utils.XMLTools;

public class Cancel extends AccommodationSalesServicePort{
	public Cancel(String environment, String scenario) {
		super(environment);

		//Generate a request from a project xml file
		setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("cancel")));
	
		generateServiceContext();
		System.out.println(getRequest());
	    setRequestNodeValueByXPath(getTestScenario(getService(), getOperation(), scenario));
		removeComments() ;
		removeWhiteSpace();

	}
	
	public void setCancelDate(String value){
		setRequestNodeValueByXPath("/Envelope/Body/cancel/request/cancelDate", value);
	}
	
	public void setTravelComponentGroupingId(String value){
		setRequestNodeValueByXPath("/Envelope/Body/cancel/request/travelComponentGroupingId", value);
	}
	
	public String getCancellationNumber(){
		return getResponseNodeValueByXPath("/Envelope/Body/cancelResponse/response/cancellationNumber");
	}
	
	public String getTravelPlanId(){
		return getResponseNodeValueByXPath("/Envelope/Body/cancelResponse/response/travelPlanId");
	}
}
