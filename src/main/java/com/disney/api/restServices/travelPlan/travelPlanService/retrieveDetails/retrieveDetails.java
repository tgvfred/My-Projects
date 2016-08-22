package com.disney.api.restServices.travelPlan.travelPlanService.retrieveDetails;

import org.apache.http.Header;

import com.disney.api.restServices.core.RestResponse;
import com.disney.api.restServices.core.RestService;
import com.disney.api.restServices.core.Headers.HeaderType;

public class retrieveDetails {
	private RestService restService;
	private String resource = "/retrieveSettlementMethod";
	public retrieveDetails(RestService restService, String resource){
		
		this.restService = restService;
		this.resource = resource + this.resource;
		
	}
	
	public RestResponse sendPutRequest(String json){	
		
		return restService.sendPutRequest(resource, HeaderType.REST, json);		
	}
}
