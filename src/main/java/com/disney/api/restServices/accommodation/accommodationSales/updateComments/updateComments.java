package com.disney.api.restServices.accommodation.accommodationSales.updateComments;

import com.disney.api.restServices.accommodation.accommodationSales.updateComments.request.UpdateCommentsRequest;
import com.disney.api.restServices.core.RestService;
import com.disney.api.restServices.core.Headers.HeaderType;
import com.disney.api.restServices.folio.folioService.folio.retrieveSettlementMethods.request.RetrieveSettlementMethodsRequest;
import com.disney.api.restServices.core.RestResponse;

public class updateComments {

	private RestService restService;
	private String resource ="/updatecomments";
	
	public updateComments(RestService restService, String resource) {
		this.restService = restService;
		this.resource = resource + this.resource;
	}
	
	public RestResponse sendPutRequest(UpdateCommentsRequest request){
		String json = restService.getJsonFromObject(request);
		return restService.sendPutRequest(resource, HeaderType.REST, json);	
	}
	
	//To submit a REST call without the authorization token information
	public RestResponse sendPutRequestWithMissingAuthToken(UpdateCommentsRequest request){
		String json = restService.getJsonFromObject(request);
		return restService.sendPutRequest(resource, HeaderType.REST_NOAuth, json);
	}
}
