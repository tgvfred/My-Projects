package com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations;

import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.AccommodationSalesServicePort;
import com.disney.utils.XMLTools;

public class RetrieveCancellationPolicy extends AccommodationSalesServicePort{
	public RetrieveCancellationPolicy(String environment, String scenario) {
		super(environment);
		//Generate a request from a project xml file
		setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("retrieveCancellationPolicy")));
	    generateServiceContext();
	    setRequestNodeValueByXPath(getTestScenario(getService(), getOperation(), scenario));
		removeComments() ;
		removeWhiteSpace();
		}
	
	  public void setTravelComponentGroupingId(String tcg_id){
	  setRequestNodeValueByXPath("/Envelope/Body/retrieveCancellationPolicy/request/travelComponentGroupingId", tcg_id);
	   }
	  public void setTravelPlanSegmentId(String tps_id){
	  setRequestNodeValueByXPath("/Envelope/Body/retrieveCancellationPolicy/request/travelPlanSegmentId", tps_id);
	   }
	  public String getcancelFee(){
			return getResponseNodeValueByXPath("/Envelope/Body/retrieveCancellationPolicyResponse/cancellationPolicyResponse/cancelFee");
		}
}