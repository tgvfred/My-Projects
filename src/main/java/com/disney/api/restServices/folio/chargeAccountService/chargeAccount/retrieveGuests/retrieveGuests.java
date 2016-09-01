package com.disney.api.restServices.folio.chargeAccountService.chargeAccount.retrieveGuests;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicHeader;

import com.disney.api.restServices.core.Headers.HeaderType;
import com.disney.api.restServices.core.RestResponse;
import com.disney.api.restServices.core.RestService;
import com.disney.api.restServices.folio.chargeAccountService.chargeAccount.retrieveGuests.request.RetrieveGuestsRequest;
import com.disney.api.restServices.folio.chargeAccountService.chargeAccount.updatePin.request.UpdatePinRequest;
import com.disney.test.utils.Randomness;

public class retrieveGuests {
	private RestService restService;
	private String resource = "/retrieveGuests";
	public retrieveGuests(RestService restService, String resource){
		
		this.restService = restService;
		this.resource = resource + this.resource;
	}
	
	public RestResponse sendPutRequest(RetrieveGuestsRequest request){	
		String json = restService.getJsonFromObject(request);
		return restService.sendPutRequest(resource, HeaderType.REST,json);		
	}
	
	public RestResponse sendPutRequestWithMissingAuthToken(RetrieveGuestsRequest request){
		String json = restService.getJsonFromObject(request);
		return restService.sendPutRequest(resource, HeaderType.REST_NOAuth, json);
	}
	
}
