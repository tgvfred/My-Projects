package com.disney.api.restServices.folio.PaymentV2.folioPaymentV2.establishSettlementMethod;

import com.disney.api.restServices.core.RestResponse;
import com.disney.api.restServices.core.RestService;
import com.disney.api.restServices.folio.PaymentV2.folioPaymentV2.establishSettlementMethod.request.EstablishSettlementMethodRequest;
import com.disney.api.restServices.folio.folioService.folio.retrieveSettlementMethods.request.RetrieveSettlementMethodsRequest;
import com.disney.api.restServices.core.Headers.HeaderType;

public class establishSettlementMethod {
	private RestService restService;
	private String resource = "/establishSettlementMethod";
	
	public establishSettlementMethod (RestService restService, String resource){
		this.restService = restService;
		this.resource = resource + this.resource;
	}
	public RestResponse sendPutRequest(EstablishSettlementMethodRequest request){
		String json = restService.getJsonFromObject(request);
		return restService.sendPutRequest(resource, HeaderType.REST, json);		
	}
	
	//To submit a REST call without the authorization token information
	public RestResponse sendPutRequestWithMissingAuthToken(EstablishSettlementMethodRequest request){
		String json = restService.getJsonFromObject(request);
		return restService.sendPutRequest(resource, HeaderType.REST_NOAuth, json);
	}
}
