package com.disney.api.restServices.folio.folioService.chargeAccount.retrieveGuests;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;

import com.disney.api.restServices.core.Headers.HeaderType;
import com.disney.api.restServices.core.RestResponse;
import com.disney.api.restServices.core.RestService;
import com.disney.api.restServices.folio.folioService.chargeAccount.retrieveGuests.request.RetrieveGuestsRequest;
import com.disney.test.utils.Randomness;

public class retrieveGuests {
	private RestService restService;
	private String resource = "/retrieveguests";
	private String referenceName;
	private String referenceValue;
	private String sourceAccountingCenter;
	public retrieveGuests(RestService restService, String resource){
		
		this.restService = restService;
		this.resource = resource + this.resource;
	}
	
	
	
	
	public RestResponse sendGetRequest(String resource){
		List<NameValuePair> params = new ArrayList<NameValuePair>();
	    params.add(new BasicNameValuePair("referenceName", referenceName));
	    params.add(new BasicNameValuePair("referenceValue", referenceValue));
	    params.add(new BasicNameValuePair("sourceAccountingCenter", sourceAccountingCenter));
	   // setGetRequest(params);
		return restService.sendGetRequest(resource, HeaderType.REST);
		
	}
	
	public RestResponse sendPutRequest(RetrieveGuestsRequest request){	
		String json = restService.getJsonFromObject(request);
		return restService.sendPutRequest(resource, HeaderType.REST,json);		
	}
	
	//To submit a REST call without the authorization token information
	public RestResponse sendPutRequestWithMissingAuthToken(RetrieveGuestsRequest request){
		String json = restService.getJsonFromObject(request);
		return restService.sendPutRequest(resource, HeaderType.REST_NOAuth, json);
	}
	
}
