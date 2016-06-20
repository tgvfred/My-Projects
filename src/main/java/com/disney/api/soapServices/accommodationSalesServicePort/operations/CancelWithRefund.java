package com.disney.api.soapServices.accommodationSalesServicePort.operations;

import com.disney.api.soapServices.accommodationSalesServicePort.AccommodationSalesServicePort;
import com.disney.utils.XMLTools;

public class CancelWithRefund extends AccommodationSalesServicePort{
	public CancelWithRefund(String environment, String scenario) {
		super(environment);
		//Generate a request from a project xml file
		setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("cancelWithRefund")));
	    generateServiceContext();
	    setRequestNodeValueByXPath(getTestScenario(getService(), getOperation(), scenario));
		removeComments() ;
		removeWhiteSpace();
		}
	
	  public void setTravelComponentGroupingId(String tcg_id){
	  setRequestNodeValueByXPath("/Envelope/Body/cancelWithRefund/request/travelComponentGroupingId", tcg_id);
	   }
	 
	  public String getTravelPlanId(){
			return getResponseNodeValueByXPath("/Envelope/Body/cancelWithRefundResponse/response/response/travelPlanId");
		}
	  public String getTravelPlanSegmentId(){
			return getResponseNodeValueByXPath("/Envelope/Body/cancelWithRefundResponse/response/response/travelPlanSegmentId");
		}
	  
}