package com.disney.api.restServices.folio.chargeAccountServiceV2.chargeAccount.retrieve;

import com.disney.api.restServices.core.RestResponse;
import com.disney.api.restServices.core.RestService;

public class Retrieve {
	private RestService restService;
	private String resource = "/retrieve";
	public Retrieve(RestService restService, String resource){
		this.restService = restService;
		this.resource = resource + this.resource;
	}
	
	public RestResponse sendPutRequest(String json){		
		return restService.sendPutRequest(resource, json);		
	}
}
