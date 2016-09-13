package com.disney.api.restServices.guestLink.guestLinkService.retrieveLinkedTransGuestIdByTransGuestId;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.disney.api.restServices.core.Headers.HeaderType;
import com.disney.api.restServices.core.RestResponse;
import com.disney.api.restServices.core.RestService;

public class linkedTransGuestIdByTransGuestId {
	private RestService restService;
	private String resource ="/linkedtransguestidbytransguestid";
	
	public linkedTransGuestIdByTransGuestId(RestService restService, String resource){
		this.restService = restService;
		this.resource = resource + this.resource;
	}
	
	public RestResponse sendGetRequest(String IdType, String IdValues){
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("id-type",IdType));
		params.add(new BasicNameValuePair("id-values",IdValues));
		return restService.sendGetRequest(resource, HeaderType.REST,params);
	}
	public RestResponse sendGetRequestWithMissingAuthToken(String IdType, String IdValues){
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("id-type",IdType));
		params.add(new BasicNameValuePair("id-values",IdValues));
		return restService.sendGetRequest(resource, HeaderType.REST_NOAuth,params);
	}
}