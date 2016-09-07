
package com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations;

import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.AccommodationSalesServicePort;
import com.disney.utils.XMLTools;

public class Modify extends AccommodationSalesServicePort{
	
	public Modify(String environment, String scenario) {
		super(environment);
		
		//Generate a request from a project xml file
		setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("modify")));
		generateServiceContext();
		
		//Retrofit to use new method which is designed to use Virtual Table data warehouse
		//setRequestNodeValueByXPath(getTestScenario("/services/accommodationSalesServicePort/modify/modifyInput.xls", scenario));			
	    setRequestNodeValueByXPath(getTestScenario(getService(), getOperation(), scenario));
		
	    
	    removeComments() ;
		removeWhiteSpace();
	}
	
	
	public void setTravelPlanId(String TP_ID){
		setRequestNodeValueByXPath("/Envelope/Body/modify/request/travelPlanId", TP_ID);
	}
	
	
	public void setTravelComponentGroupingId(String Tcg_ID){
		setRequestNodeValueByXPath("/Envelope/Body/modify/request/roomDetail/travelComponentGroupingId",Tcg_ID);
	}
	
	public void setTravelComponentId(String tc_ID){
		setRequestNodeValueByXPath("/Envelope/Body/modify/request/roomDetail/travelComponentId", tc_ID);
	}
	
	public void setTravelPlanSegmentId(String tps_ID){
		setRequestNodeValueByXPath("/Envelope/Body/modify/request/travelPlanSegmentId",tps_ID);
	}
	
	
	public String getPackageCode(){
		return getResponseNodeValueByXPath("/Envelope/Body/modifyResponse/modifyResponse/roomDetails/packageCode");
	}
	public String getResortCode(){
		return getResponseNodeValueByXPath("/Envelope/Body/modifyResponse/modifyResponse/roomDetails/resortCode");
	}
}
