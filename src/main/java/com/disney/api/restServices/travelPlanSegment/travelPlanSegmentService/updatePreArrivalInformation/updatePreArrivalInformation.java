package com.disney.api.restServices.travelPlanSegment.travelPlanSegmentService.updatePreArrivalInformation;

import com.disney.api.restServices.core.RestResponse;
import com.disney.api.restServices.core.RestService;
import com.disney.api.restServices.travelPlanSegment.travelPlanSegmentService.updatePreArrivalInformation.request.UpdatePreArrivalInformationRequest;
import com.disney.api.restServices.core.Headers.HeaderType;

public class updatePreArrivalInformation {
	private RestService restService;
	private String resource ="/updateprearrivalinformation";
	
	public updatePreArrivalInformation(RestService restService, String resource) {
		this.restService = restService;
		this.resource = resource + this.resource;
	}
	public RestResponse sendPutRequest(UpdatePreArrivalInformationRequest request){
		String json = restService.getJsonFromObject(request);
		return restService.sendPutRequest(resource, HeaderType.REST, json);	
	}
	
	//To submit a REST call without the authorization token information
	public RestResponse sendPutRequestWithMissingAuthToken(UpdatePreArrivalInformationRequest request){
		String json = restService.getJsonFromObject(request);
		return restService.sendPutRequest(resource, HeaderType.REST_NOAuth, json);
	}
}
