package com.disney.api.restServices.folio.chargeAccountServiceV2.chargeAccount.retrieve;

import org.apache.http.Header;
import org.apache.http.message.BasicHeader;

import com.disney.api.restServices.core.RestResponse;
import com.disney.api.restServices.core.RestService;
import com.disney.test.utils.Randomness;

public class Retrieve {
	private RestService restService;
	private String resource = "/retrieve";
	public Retrieve(RestService restService, String resource){
		this.restService = restService;
		this.resource = resource + this.resource;
	}
	
	public RestResponse sendPutRequest(String json){	
		Header[] headers =  {
	   		    new BasicHeader("Content-type", "application/json;charset=utf-8")
	   		    ,new BasicHeader("Accept", "application/json")
	   		    ,new BasicHeader("username", "test116.user")
	   		    ,new BasicHeader("messageId", Randomness.generateMessageId())
	   		    ,new BasicHeader("Connection", "keep-alive")
	   		    ,new BasicHeader("conversationId",  Randomness.generateConversationId())
	   		    ,new BasicHeader("requestedTimestamp", Randomness.generateCurrentXMLDatetime() + ".000-04:00")
	   		};
		return restService.sendPutRequest(resource, json);		
	}
}
