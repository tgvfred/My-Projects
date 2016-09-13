package com.disney.api.restServices.folio.chargeAccountService.chargeAccount.retrieve;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import com.disney.api.restServices.core.RestResponse;
import com.disney.api.restServices.core.RestService;
import com.disney.api.restServices.core.Headers.HeaderType;
import com.disney.api.restServices.folio.chargeAccountService.chargeAccount.retrieve.request.RetrieveRequest;
import com.disney.api.restServices.folio.chargeAccountService.chargeAccount.retrieveGuests.request.RetrieveGuestsRequest;
import com.disney.test.utils.Randomness;

public class Retrieve {
	private RestService restService;
	private String resource = "/retrieve";
	public Retrieve(RestService restService, String resource){
		this.restService = restService;
		this.resource = resource + this.resource;
	}
	
	
	public RestResponse sendGetRequest(String referenceName, String referenceValue, String chargeAccountId){
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		if (referenceName != ""){
			params.add(new BasicNameValuePair("referenceName", referenceName));
		}
	    if (referenceValue !=""){
	    	params.add(new BasicNameValuePair("referenceValue", referenceValue));
	    }
	    if (chargeAccountId !=""){
	    	params.add(new BasicNameValuePair("chargeAccountId", chargeAccountId));
		}
	    return restService.sendGetRequest(resource, HeaderType.REST, params);
	}
	
	public RestResponse sendGetRequestWithMissingAuthToken(String referenceName, String referenceValue, String chargeAccountId){
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		if (referenceName != ""){
			params.add(new BasicNameValuePair("referenceName", referenceName));
		}
	    if (referenceValue !=""){
	    	params.add(new BasicNameValuePair("referenceValue", referenceValue));
	    }
	    if (chargeAccountId !=""){
	    	params.add(new BasicNameValuePair("chargeAccountId", chargeAccountId));
		}
		return restService.sendGetRequest(resource, HeaderType.REST_NOAuth, params);
	}
	
	
	public RestResponse sendPutRequest(RetrieveRequest request){	
		String json = restService.getJsonFromObject(request);
		return restService.sendPutRequest(resource, HeaderType.REST, json);		
	
	}
	//To submit a REST call without the authorization token information
	public RestResponse sendPutRequestWithMissingAuthToken(RetrieveRequest request){
		String json = restService.getJsonFromObject(request);
		return restService.sendPutRequest(resource, HeaderType.REST_NOAuth, json);
	}
}
