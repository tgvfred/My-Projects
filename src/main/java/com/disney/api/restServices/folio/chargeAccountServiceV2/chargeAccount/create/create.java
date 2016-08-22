package com.disney.api.restServices.folio.chargeAccountServiceV2.chargeAccount.create;

import org.apache.http.Header;

import com.disney.api.restServices.core.RestResponse;
import com.disney.api.restServices.core.RestService;
import com.disney.api.restServices.core.Headers.HeaderType;

public class create {
	private RestService restService;
	private String resource = "/create";
	public create(RestService restService, String resource){
		
		this.restService = restService;
		this.resource = resource + this.resource;
		
	}
	
	public RestResponse sendPutRequest(String json){	
		
		return restService.sendPutRequest(resource, HeaderType.REST, json);		
	}

}
