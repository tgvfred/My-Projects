package com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations;

import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.AccommodationSalesServicePort;
import com.disney.utils.XMLTools;

public class ReplaceAllForTravelPlanSegment extends AccommodationSalesServicePort{
    public ReplaceAllForTravelPlanSegment(String environment, String scenario) {
		super(environment);
		//Generate a request from a project xml file
		setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("replaceAllForTravelPlanSegment")));
		generateServiceContext();
	    setRequestNodeValueByXPath(getTestScenario(getService(), getOperation(), scenario));
        removeComments() ;
		removeWhiteSpace(); 
   }
    public void setTravelComponentGroupingId(String tcg_id){
    	setRequestNodeValueByXPath("/Envelope/Body/replaceAllForTravelPlanSegment/request/roomDetails/travelComponentGroupingId", tcg_id);
    }
    public void setTravelComponentId(String tc_id){
    	  setRequestNodeValueByXPath("/Envelope/Body/replaceAllForTravelPlanSegment/request/roomDetails/travelComponentId", tc_id);
    }
    public void setTravelPlanId(String tp_id){
    	  setRequestNodeValueByXPath("/Envelope/Body/replaceAllForTravelPlanSegment/request/travelPlanId", tp_id);
    }
    public void setTravelPlanSegementId(String tps_id){
    	  setRequestNodeValueByXPath("/Envelope/Body/replaceAllForTravelPlanSegment/request/travelPlanSegmentId", tps_id);
    }
    public String getTravelComponentGroupingId(){
		return getResponseNodeValueByXPath("/Envelope/Body/replaceAllForTravelPlanSegmentResponse/response/roomDetails/travelComponentGroupingId");
    }
    public String getTravelComponentId(){
		return getResponseNodeValueByXPath("/Envelope/Body/replaceAllForTravelPlanSegmentResponse/response/roomDetails/travelComponentId");
    }
}