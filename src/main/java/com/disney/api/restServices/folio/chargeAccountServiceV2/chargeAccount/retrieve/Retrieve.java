package com.disney.api.restServices.folio.chargeAccountServiceV2.chargeAccount.retrieve;

import org.apache.http.HttpResponse;

import com.disney.api.restServices.core.RestService;
import com.disney.api.restServices.folio.chargeAccountServiceV2.chargeAccount.ChargeAccount;

public class Retrieve {
	private RestService restService;
	private String resource = "/retrieve";
	public Retrieve(RestService restService, String resource){
		this.restService = restService;
		this.resource = resource + this.resource;
	}
	
	public HttpResponse sendPutRequest(String json){		
		return restService.sendPutRequest(restService.getTdmURL(resource), json);		
	}
}
