package com.disney.api.restServices.folio.chargeAccountServiceV2.chargeAccount.updatePin;

import org.apache.http.Header;
import org.apache.http.message.BasicHeader;

import com.disney.api.restServices.core.RestResponse;
import com.disney.api.restServices.core.RestService;
import com.disney.api.restServices.core.Headers.HeaderType;
import com.disney.test.utils.Randomness;

public class updatePin {
	private RestService restService;
	private String resource = "/retrieveGuests";
	public updatePin(RestService restService, String resource){
		this.restService = restService;
		this.resource = resource + this.resource;
	}
	
	public RestResponse sendPutRequest(String json){	
		
		return restService.sendPutRequest(resource, HeaderType.REST, json);		
	}
}
