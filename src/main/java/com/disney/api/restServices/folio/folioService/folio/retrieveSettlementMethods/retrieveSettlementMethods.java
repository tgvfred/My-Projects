package com.disney.api.restServices.folio.folioService.folio.retrieveSettlementMethods;

import org.apache.http.Header;

import com.disney.api.restServices.core.RestResponse;
import com.disney.api.restServices.core.RestService;
import com.disney.api.restServices.core.Headers.HeaderType;
import com.disney.api.restServices.folio.folioService.folio.retrieveSettlementMethods.request.RetrieveSettlementMethodsRequest;

public class retrieveSettlementMethods {
	private RestService restService;
	private String resource = "/retrieveSettlementMethods";
	public retrieveSettlementMethods(RestService restService, String resource){
		
		this.restService = restService;
		this.resource = resource + this.resource;
		
	}
	
	public RestResponse sendPutRequest(RetrieveSettlementMethodsRequest request){	
		String json = restService.getJsonFromObject(request);
		return restService.sendPutRequest(resource, HeaderType.REST, json);		
	}
	
	//To submit a REST call without the authorization token information
	public RestResponse sendPutRequestWithMissingAuthToken(RetrieveSettlementMethodsRequest request){
		String json = restService.getJsonFromObject(request);
		return restService.sendPutRequest(resource, HeaderType.REST_NOAuth, json);
	}

}
