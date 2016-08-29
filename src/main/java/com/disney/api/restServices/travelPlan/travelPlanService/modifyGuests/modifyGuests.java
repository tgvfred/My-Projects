package com.disney.api.restServices.travelPlan.travelPlanService.modifyGuests;

import com.disney.api.restServices.core.RestService;
import com.disney.api.restServices.core.Headers.HeaderType;
import com.disney.api.restServices.core.RestResponse;
import com.disney.api.restServices.travelPlan.travelPlanService.modifyGuests.request.ModifyGuestsRequest;

public class modifyGuests {
	private RestService restService;
	private String resource = "/modifyGuests";
	
	public modifyGuests(RestService restService, String resource) {
		this.restService = restService;
		this.resource = resource + this.resource;
	}
	
	public RestResponse sendPutRequest(ModifyGuestsRequest request){
		String json = restService.getJsonFromObject(request);
		return restService.sendPutRequest(resource, HeaderType.REST, json);	
	}
}
