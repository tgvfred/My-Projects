package com.disney.api.restServices.folio.chargeAccountService.chargeAccount.retrieveGuests;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicHeader;

import com.disney.api.restServices.core.Headers.HeaderType;
import com.disney.api.restServices.core.RestResponse;
import com.disney.api.restServices.core.RestService;
import com.disney.test.utils.Randomness;

public class retrieveGuests {
	private RestService restService;
	private String resource = "/retrieveGuests";
	public retrieveGuests(RestService restService, String resource){
		
		this.restService = restService;
		this.resource = resource + this.resource;
	}
	
	public RestResponse sendPutRequest(String json){	
		
		return restService.sendPutRequest(resource, HeaderType.REST,json);		
	}
	
	
}
