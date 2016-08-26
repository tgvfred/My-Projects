package com.disney.api.restServices.folio.chargeAccountService.chargeAccount.updatePin;

import org.apache.http.Header;
import org.apache.http.message.BasicHeader;

import com.disney.api.restServices.core.RestResponse;
import com.disney.api.restServices.core.RestService;
import com.disney.api.restServices.core.Headers.HeaderType;
import com.disney.api.restServices.folio.chargeAccountService.chargeAccount.updatePin.request.UpdatePinRequest;

import com.disney.test.utils.Randomness;

public class updatePin {
	private RestService restService;
	private String resource = "/updatePin";
	
	public updatePin(RestService restService, String resource){
		this.restService = restService;
		this.resource = resource + this.resource;
	}
	
	public RestResponse sendPutRequest(UpdatePinRequest request){	
		String json = restService.getJsonFromObject(request);
		return restService.sendPutRequest(resource, HeaderType.REST, json);		
	
	}
}
