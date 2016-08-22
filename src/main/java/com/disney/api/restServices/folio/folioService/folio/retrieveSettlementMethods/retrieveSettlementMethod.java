package com.disney.api.restServices.folio.folioService.folio.retrieveSettlementMethods;

import org.apache.http.Header;

import com.disney.api.restServices.core.RestResponse;
import com.disney.api.restServices.core.RestService;
import com.disney.api.restServices.core.Headers.HeaderType;

public class retrieveSettlementMethod {
	private RestService restService;
	private String resource = "/retrieveSettlementMethod";
	public retrieveSettlementMethod(RestService restService, String resource){
		
		this.restService = restService;
		this.resource = resource + this.resource;
		
	}
	
	public RestResponse sendPutRequest(String json){	
		
		return restService.sendPutRequest(resource, HeaderType.REST, json);		
	}

}
