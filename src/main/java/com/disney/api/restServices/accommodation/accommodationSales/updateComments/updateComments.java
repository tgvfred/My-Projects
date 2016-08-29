package com.disney.api.restServices.accommodation.accommodationSales.updateComments;

import com.disney.api.restServices.accommodation.accommodationSales.updateComments.request.UpdateCommentsRequest;
import com.disney.api.restServices.core.RestService;
import com.disney.api.restServices.core.Headers.HeaderType;
import com.disney.api.restServices.core.RestResponse;

public class updateComments {

	private RestService restService;
	private String resource ="/updateComments";
	
	public updateComments(RestService restService, String resource) {
		this.restService = restService;
		this.resource = resource + this.resource;
	}
	
	public RestResponse sendPutRequest(UpdateCommentsRequest request){
		String json = restService.getJsonFromObject(request);
		return restService.sendPutRequest(resource, HeaderType.REST, json);	
	}
}
