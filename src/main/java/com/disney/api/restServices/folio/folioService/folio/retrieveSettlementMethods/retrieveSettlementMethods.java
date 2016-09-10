package com.disney.api.restServices.folio.folioService.folio.retrieveSettlementMethods;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

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
	
	public RestResponse sendGetRequest(String referenceName, String referenceValue, String folioType){
		List<NameValuePair> params = new ArrayList<NameValuePair>();
	    params.add(new BasicNameValuePair("referenceName", referenceName));
	    params.add(new BasicNameValuePair("referenceValue", referenceValue));
	    params.add(new BasicNameValuePair("folioType", folioType));
		return restService.sendGetRequest(resource, HeaderType.REST, params);
	}
	
	public RestResponse sendGetRequestWithMissingAuthToken(String referenceName, String referenceValue, String folioType){
		List<NameValuePair> params = new ArrayList<NameValuePair>();
	    params.add(new BasicNameValuePair("referenceName", referenceName));
	    params.add(new BasicNameValuePair("referenceValue", referenceValue));
	    params.add(new BasicNameValuePair("folioType", folioType));
		return restService.sendGetRequest(resource, HeaderType.REST_NOAuth, params);
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
