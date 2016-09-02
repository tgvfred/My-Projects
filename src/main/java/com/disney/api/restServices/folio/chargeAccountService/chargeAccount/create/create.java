package com.disney.api.restServices.folio.chargeAccountService.chargeAccount.create;

import org.apache.http.Header;

import com.disney.api.restServices.core.RestResponse;
import com.disney.api.restServices.core.RestService;
import com.disney.api.restServices.core.Headers.HeaderType;
import com.disney.api.restServices.folio.chargeAccountService.chargeAccount.create.request.CreateRequest;
import com.disney.api.restServices.folio.chargeAccountService.chargeAccount.retrieveGuests.request.RetrieveGuestsRequest;

public class create {
	private RestService restService;
	private String resource = "/create";
	public create(RestService restService, String resource){
		
		this.restService = restService;
		this.resource = resource + this.resource;
		
	}
	
	public RestResponse sendPostRequest(CreateRequest request){	
		String json = restService.getJsonFromObject(request);
		return restService.sendPostRequest(resource, HeaderType.REST, json);		
	}
	
	//To submit a REST call without the authorization token information
	public RestResponse sendPutRequestWithMissingAuthToken(CreateRequest request){
		String json = restService.getJsonFromObject(request);
		return restService.sendPutRequest(resource, HeaderType.REST_NOAuth, json);
	}

}
